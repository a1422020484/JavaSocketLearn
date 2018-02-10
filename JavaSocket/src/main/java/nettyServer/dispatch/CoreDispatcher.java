package nettyServer.dispatch;

import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import nettyServer.dispatch.annotation.Action;
import nettyServer.dispatch.annotation.Action.User;
import nettyServer.util.CoreConfig;
import nettyServer.util.LogKey;
import nettyServer.util.MsgUtils;

/**
 * 请求转发器
 *
 * @author zuojie.x
 */
public class CoreDispatcher {
	private static final Logger log = LoggerFactory.getLogger(LogKey.CORE);

	private static final long ActionInvokeTimeWarn = CoreConfig.longValue("ActionInvokeTimeWarn");

	public static void dispatch(final Request request) {
		final Request req = request;
		final ActionInvoker actionExecutor = ActionManager.getActionInvoker(req);
		// 控制器的注解
		final Action action = actionExecutor.getActionAnnotation();
		ActionTaskService.exec(new ActionTask() {
			@Override
			public Integer getPlayerId() {
				Integer l = null;
				if (action.user() == User.System) {
					l = -10002;
				} else {
					// 控制器的注解
					if (action.isLogon()) {
						l = -10001; // 表示是来自登录请求
					} else if (action.isCreatePlayer()){
						l = -10004; // 创建角色
					} else {
						l = req.getPlayerId();
						if (l == null) {
							l = -10003; // 表示是其它请求
						}
					}
				}
				return l;
			}

			@Override
			public void run() {
				ThreadLocalRequest.set(req);
				Session session = req.getSession();
				try {
					if (User.Player == action.user()) {
						if (!action.isLogon()) {
							if (action.loginCheck() 
									&& session == null) {
								log.debug("IP[{}]请求ID[{}],用户未登录!", req.getHostAddress(), req.getActionId());
								write(action, ResponseFactory.USER_NOT_LOGIN);
								return;
							} else {
								if (!action.isCreatePlayer() && !session.isPlayerCreated()) {
									log.debug("IP[{}]请求ID[{}],未创建角色!", req.getHostAddress(), req.getActionId());
									write(action, ResponseFactory.USER_PLAYER_NOT_CREATED);
									return;
								}
							}
						}
					} else if (User.System == action.user()) {
					}
					if (log.isDebugEnabled()) {
						log.debug(req.toString());
					}
					long start = System.currentTimeMillis();
					if (!action.isLogon() && !action.isCreatePlayer()) {
						for (ActionFilter f : ActionFilters.filters) {
							f.before(request);
						}
					}
					Object exeRs = null;
					try {
						exeRs = actionExecutor.execute();
					} catch (Exception e) {
						e.printStackTrace();
						exeRs = ResponseFactory.SERVER_ERROR;
					}
					if (!action.isLogon() && !action.isCreatePlayer()) {
						for (ActionFilter f : ActionFilters.filters) {
							f.after(request.getPlayerId(), request, exeRs);
						}
					}
					long useTime = System.currentTimeMillis() - start;
					if (useTime > ActionInvokeTimeWarn) {
						log.warn("{}执行时间超过{}ms! 用时:{}", new Object[] { actionExecutor, ActionInvokeTimeWarn, useTime });
					}
					if (exeRs != null) {
						if (exeRs instanceof MultiResponse) {
							write(action, (MultiResponse) exeRs);
						} else if (exeRs instanceof Response) {
							Response response = (Response) exeRs;
							((DefaultResponse) response).setActionId(req.getActionId());
							write(action, response);
						} else {
							// 类型不是Response时,按成功处理
							write(action, ResponseFactory.ok(req.getActionId(), (MessageLite) exeRs));
						}
					}
//					// 超时不返回
//					if (action.timeout() > 0 && useTime > action.timeout()) {
//						return;
//					}
				} finally {
					// 删除线程变量request
					ThreadLocalRequest.remove();
				}
			}
		});
	}

	/**
	 * @param action
	 * @param resp
	 */
	static void write(Action action, Response resp) {
		List<Response> list = new ArrayList<>(16);
		list.add(resp);
		DefaultRequest req = (DefaultRequest) ThreadLocalRequest.get();
		Session session = req.getSession();
		if (session != null) {
			list.addAll(session.takeMsg());
		}
		write(action, list);
		
//		if (log.isDebugEnabled()) {
//			log.debug(resp.toString());
//		}
//		DefaultRequest req = (DefaultRequest) ThreadLocalRequest.get();
//		ChannelHandlerContext ctx = req.getChannelHandlerContext();
//		HttpRequest httpRequest = req.getHttpRequest();
//		
//		Object rs = resp.getResultDTO();
//		byte[] all = null;
//		ByteBuf allBuf = null;
//		if (rs != null) {
//			MessageLite msglite = (MessageLite) rs;
//			byte[] protoData = msglite.toByteArray();
//			if (action.isEncrypted()) {
//				if (req.getSessionId() == null) {
//					throw new RuntimeException("Action ID:" + resp.getActionId() + " 需要对返回的数据加密,但sessionId为空!");
//				}
//				byte[] sessionIdEncrypt = req.getSessionId().getBytes();
//				MsgUtils.encrypt(sessionIdEncrypt, MsgUtils.otherKey);
//				MsgUtils.encrypt(protoData, sessionIdEncrypt);
//			}
//			all = new byte[protoData.length + 9];
//			allBuf = Unpooled.wrappedBuffer(all);
//			allBuf.writerIndex(0);
//			allBuf.writeInt(all.length - 4);
//			allBuf.writeByte(resp.getCode());
//			allBuf.writeInt(resp.getActionId());
//			allBuf.writeBytes(protoData);
//			// 如果是system用户,对数据进行加密
//		} else {
//			all = new byte[9];
//			allBuf = Unpooled.wrappedBuffer(all);
//			allBuf.writerIndex(0);
//			allBuf.writeInt(5);
//			allBuf.writeByte(resp.getCode());
//			allBuf.writeInt(resp.getActionId());
//		}
//		
//		HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//		HttpHeaders.setContentLength(httpResponse, all.length);
//		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
//		httpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, mimeTypesMap.getContentType("application/octet-stream"));
//		if (HttpHeaders.isKeepAlive(httpRequest)) {
//			httpResponse.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//		}
//
//		ctx.channel().write(httpResponse);
//		ctx.channel().write(allBuf);
//		ChannelFuture lastContentFuture = ctx.channel().writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
//		// Decide whether to close the connection or not.
//		if (!HttpHeaders.isKeepAlive(httpRequest)) {
//			// Close the connection when the whole content is written out.
//			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
//		}
	}
	
	static void write(Action action, MultiResponse multiResp) {
		List<Response> list = multiResp.getResponses();
		DefaultRequest req = (DefaultRequest) ThreadLocalRequest.get();
		Session session = req.getSession();
		if (session != null) {
			list.addAll(session.takeMsg());
		}
		write(action, list);
	}
	
	static void write(Action action, List<Response> responses) {
		if (log.isDebugEnabled()) {
			log.debug(responses.toString());
		}
		DefaultRequest req = (DefaultRequest) ThreadLocalRequest.get();
		ChannelHandlerContext ctx = req.getChannelHandlerContext();
		HttpRequest httpRequest = req.getHttpRequest();
		
		int totalLen = 0;
		for (Response resp : responses) {
			totalLen += 9; // 必有的 code(byte) + actionId(int)
			Object rs = resp.getResultDTO();
			if (rs != null) {
				totalLen += ((MessageLite) rs).getSerializedSize();
			}
		}
		byte[] all = new byte[totalLen];
		ByteBuf allBuf = Unpooled.wrappedBuffer(all);
		allBuf.writerIndex(0);
		
		byte[] sessionIdEncrypt = null;
		if (req.getSession() != null) {
			sessionIdEncrypt = req.getSessionId().getBytes();
			MsgUtils.encrypt(sessionIdEncrypt, MsgUtils.otherKey);
		}
		for (Response resp : responses) {
			Object rs = resp.getResultDTO();
			if (rs != null) {
				MessageLite msglite = (MessageLite) rs;
				byte[] protoData = msglite.toByteArray();
				ActionInvoker actionInvoker = ActionManager.getActionInvoker(resp.getActionId());
				// Action.isEncrypted()默认是true,当某个actionId并没有绑定Action时,默认为是加密的
				if (actionInvoker != null && actionInvoker.getActionAnnotation().user() == User.System) {
					// 不加密
				} else if (actionInvoker == null || actionInvoker.getActionAnnotation().isEncrypted()) {
					if (sessionIdEncrypt == null) {
						throw new RuntimeException("Action ID:" + resp.getActionId() + " 需要对返回的数据加密,但sessionId为空!");
					}
					MsgUtils.encrypt(protoData, sessionIdEncrypt);
				}
				allBuf.writeInt(msglite.getSerializedSize() + 5);
				allBuf.writeByte(resp.getCode());
				allBuf.writeInt(resp.getActionId());
				allBuf.writeBytes(protoData);
			} else {
				allBuf.writeInt(5);
				allBuf.writeByte(resp.getCode());
				allBuf.writeInt(resp.getActionId());
			}
		}
		
		HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		HttpHeaders.setContentLength(httpResponse, all.length);
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		httpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, mimeTypesMap.getContentType("application/octet-stream"));
		if (HttpHeaders.isKeepAlive(httpRequest)) {
			httpResponse.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		ctx.channel().write(httpResponse);
		ctx.channel().write(allBuf);
		ChannelFuture lastContentFuture = ctx.channel().writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		// Decide whether to close the connection or not.
		if (!HttpHeaders.isKeepAlive(httpRequest)) {
			// Close the connection when the whole content is written out.
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
