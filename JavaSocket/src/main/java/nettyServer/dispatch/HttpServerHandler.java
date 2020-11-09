package nettyServer.dispatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import nettyServer.dispatch.annotation.Action;
import nettyServer.dispatch.annotation.Action.User;
import nettyServer.util.CoreConfig;
import nettyServer.util.LogKey;
import nettyServer.util.MsgUtils;
import nettyServer.util.RSAUtils;

/**
 * 用http方式实现短连接
 * 接收http请求,只解析post数据
 * 以http方式返回
 * 
 * @author yangxp
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	private final Logger log = LoggerFactory.getLogger(LogKey.CORE);

	// private HttpPostRequestDecoder decoder;

	private HttpRequest request;
	private boolean isPost;

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	}

	public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		// msg的类型可能是DefaultHttpRequest或LastHttpContent
		if (msg instanceof HttpRequest) {
			HttpRequest request = this.request = (HttpRequest) msg;
//			log.debug("客户端请求方式:" + request.getMethod());
			if (request.getMethod().equals(HttpMethod.POST)) {
				isPost = true;
			} else {
				log.error("连接方式不是POST,断开socket");
				ctx.close();
				return;
			}
		}
		if (isPost) {
			if (msg instanceof HttpContent) {
				HttpContent chunk = (HttpContent) msg;
				ByteBuf buf = chunk.content();
				/*
				 * byte 会话id字符串的长度
				 * string 会话id,每次登录成功后由服务器分配的唯一字符串
				 * int 协议号
				 * data[] protobuf数据体
				 */
				int readableSize = buf.readableBytes();
				if (readableSize == 0) {
					log.error("sessionid长度错误");
					ctx.close();
					buf.release();
					return;
				}
				String sessionId = null;
				byte[] sessionIdData = null;
				byte sessionIdLen = buf.readByte(); // byte max 127
				if (sessionIdLen > 0) {
					readableSize = buf.readableBytes();
					if (readableSize < sessionIdLen) {
						log.error("读取sessionid错误,可读取字节长度({})少于必须读取长度({})", readableSize, sessionIdLen);
						ctx.close();
						buf.release();
						return;
					}
					sessionIdData = new byte[sessionIdLen];
					buf.readBytes(sessionIdData);
					sessionId = new String(sessionIdData, "utf-8");
				}
				
				readableSize = buf.readableBytes();
				if (readableSize < 4) {
					log.error("读取headid错误,可读取字节长度({})少于必须读取长度(4)", readableSize);
					ctx.close();
					buf.release();
					return;
				}
				
				int headId = buf.readInt();
//				log.debug("sessionid:{}, headid:{}", sessionid, headid);
				ActionInvoker invoker = ActionManager.getActionInvoker(headId);
				if (invoker == null) {
					log.error("没有找到匹配的Action ID:{}.", headId);
					ctx.close();
					buf.release();
					return;
				}
				Action action = invoker.getActionAnnotation();
				
				// 以下是protobuf数据
				readableSize = buf.readableBytes();
				if (readableSize > CoreConfig.MaxDataLength) {
					log.error("读取protobufData错误,可读取字节长度({})大于允许包最大长度,配置: MaxDataLength=({})", readableSize, CoreConfig.MaxDataLength);
					ctx.close();
					buf.release();
					return;
				}
				byte[] protobufData = new byte[readableSize];
				buf.readBytes(protobufData);
				if (action.user() == User.System) {
					// 对系统用户数据解密
					if (protobufData.length == 0) {
						log.error("Action ID:{} 系统用户请求必须有参数数据.", headId);
						ctx.close();
						buf.release();
						return;
					}
					try {
						protobufData = RSAUtils.encryptByPublicKey(protobufData);
						protobufData = RSAUtils.decryptByPrivateKey(protobufData);
					} catch (Exception e) {
//						e.printStackTrace();
						log.error("Action ID:{} RSA解密系统用户数据失败.", headId);
						ctx.close();
						buf.release();
						return;
					}
				} else {
					if (action.isEncrypted()) {
						if (sessionIdLen == 0) {
							log.error("Action ID:{} 需要对请求的数据解密,但sessionId为空.", headId);
							ctx.close();
							buf.release();
							return;
						}
						// 对数据解密
						MsgUtils.encrypt(sessionIdData, MsgUtils.otherKey);
						MsgUtils.encrypt(protobufData, sessionIdData);
					}
				}

				Object[] params = null;
				try {
					params = invoker.parseArguments(protobufData);
				} catch (Exception e) {
					log.error("Action ID:{} 的参数解析异常.", headId);
					ctx.close();
					buf.release();
					return;
				}
				DefaultRequest req = new DefaultRequest(sessionId, headId, params);
				if (sessionId != null) {
					req.setSession(SessionManager.getSession(sessionId));
				}
				req.setChannelHandlerContext(ctx);
				req.setHttpRequest(request);
				CoreDispatcher.dispatch(req);
//				Response resp = CoreDispatcher.dispatch(req);
//				if (log.isDebugEnabled()) {
//					log.debug(resp.toString());
//				}
//				Object dto = resp.getResultDTO();
//				byte[] all = null;
//				ByteBuf allBuf = null;
//				if (dto != null) {
//					MessageLite msglite = (MessageLite) dto;
//					all = new byte[msglite.getSerializedSize() + 9];
//					allBuf = Unpooled.wrappedBuffer(all);
//					allBuf.writerIndex(0);
//					allBuf.writeInt(all.length - 4);
//					allBuf.writeByte(resp.getCode());
//					allBuf.writeInt(resp.getActionId());
//					allBuf.writeBytes(msglite.toByteArray());
//				} else {
//					all = new byte[9];
//					allBuf = Unpooled.wrappedBuffer(all);
//					allBuf.writerIndex(0);
//					allBuf.writeInt(5);
//					allBuf.writeByte(resp.getCode());
//					allBuf.writeInt(resp.getActionId());
//				}
////				log.debug("out: {}" + Arrays.toString(all));
//
//				HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//				HttpHeaders.setContentLength(response, all.length);
//				MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
//				response.headers().set(HttpHeaders.Names.CONTENT_TYPE, mimeTypesMap.getContentType("application/octet-stream"));
//				if (HttpHeaders.isKeepAlive(request)) {
//					response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//				}
//
//				ctx.write(response);
//				ctx.write(allBuf);
//				ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
//				// Decide whether to close the connection or not.
//				if (!HttpHeaders.isKeepAlive(request)) {
//					// Close the connection when the whole content is written out.
//					lastContentFuture.addListener(ChannelFutureListener.CLOSE);
//				}
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		messageReceived(ctx, msg);
	}
}