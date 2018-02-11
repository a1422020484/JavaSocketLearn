package nettyServer.client;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import nettyServer.util.HttpResponseHandler;
import nettyServer.util.HttpUtils;
import nettyServer.util.MsgUtils;
import nettyServer.util.RSAUtils;
import proto.Friend;
import protobuf.userLogin.UserLogin;

/**
 * 游戏服客户端
 * 
 * @author xiezuojie
 */
public class GameClient {

	// private String ip;
	// private int port;
	private String uri;

	private String sessionId;

	private Map<Integer, ResponseHandler> handlers = new HashMap<>();
	private ResponseHandler errHandler;

	public final int MaxDataLen = 8 * 1024 * 1024;
	public final int MaxMessageLen = 4 * 1024 * 1024;

	public GameClient(String ip, int port) {
		// this.ip = ip;
		// this.port = port;
		this.uri = "http://" + ip + ":" + port;
	}

	public static void main(String[] args) {
		GameClient client = new GameClient("127.0.0.1",9501);
		Friend.Builder builder = Friend.newBuilder();
		builder.setPlayerId(1);
		builder.setName("yang");
		builder.setIconId("IconId");
		client.send(9601,builder.build());
	}
	
	public GameClient(String uri) {
		// this.ip = ip;
		// this.port = port;
		this.uri = uri;
	}

	public void send(int headId, MessageLite proto) {
		try {
			send0(headId, proto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send0(int headId, MessageLite proto) throws Exception {
		ByteBuf buf = Unpooled.buffer();
		// sessionid
		if (sessionId != null) {
			byte[] sessionData = sessionId.getBytes("utf-8");
			buf.writeByte(sessionData.length);
			buf.writeBytes(sessionData);

		} else {
			buf.writeByte(0);
		}
		// headid
		buf.writeInt(headId);
		if (proto != null) {
			byte[] protodata = proto.toByteArray();
			ResponseHandler handler = handlers.get(headId);
			if (handler != null) {
				if (handler.isSystem()) {
					protodata = RSAUtils.encryptByPublicKey(protodata);
				} else if (handler.isEncrypted() && sessionId != null) {
					byte[] sessionIdEncrypt = sessionId.getBytes();
					MsgUtils.encrypt(sessionIdEncrypt, MsgUtils.otherKey);
					MsgUtils.encrypt(protodata, sessionIdEncrypt);
				}
			}
			buf.writeBytes(protodata);
		}
		int len = buf.writerIndex();
		byte[] data = new byte[len];
		buf.getBytes(0, data);
		// System.arraycopy(buf.array(), 0, data, 0, len);;
		HttpUtils.create(uri).post(data, new HttpResponseHandler() {
			@Override
			public void handle(HttpResponse resp) {
				HttpEntity entity = resp.getEntity();
				if (!entity.isStreaming())
					return;
				int totalLen = (int) entity.getContentLength();
				if (totalLen == 0) {
					return; // 可能没有数据
				}
				if (totalLen > MaxDataLen) {
					throw new RuntimeException("返回的数据包长度(" + totalLen + ")超过最大长度:" + MaxDataLen);
				}
				byte[] data = new byte[totalLen];
				try {
					InputStream is = entity.getContent();
					BufferedInputStream bis = new BufferedInputStream(is);
					int offset = 0;
					byte[] temp = new byte[1024];
					while (true) {
						int size = bis.read(temp);
						if (size <= 0) {
							break;
						}
						// 复制写入
						System.arraycopy(temp, 0, data, offset, size);
						offset += size;
					}
					// bis.read(data);
					ByteBuf buf = Unpooled.wrappedBuffer(data);
					while (true) {
						int partLen = buf.readInt(); // 消息体长度
						byte code = buf.readByte();
						int headId = buf.readInt();
						if (partLen > MaxMessageLen) {
							throw new RuntimeException("返回的消息体长度(" + partLen + ")超过最大长度:" + MaxMessageLen + " headId:" + headId);
						}
						// System.err.println("len:" + partLen + "-" + headId);
						// System.err.println("index:" + buf.readerIndex() + "-" + totalLen + " r:" + partLen);
						byte[] protodata = new byte[partLen - 5];
						buf.readBytes(protodata);
						// 解密
						ResponseHandler handler = handlers.get(headId);
						if (handler != null) {
							if (handler.isSystem()) {
								// 不解密
							} else if (handler.isEncrypted() && sessionId != null) {
								byte[] sessionIdEncrypt = sessionId.getBytes();
								MsgUtils.encrypt(sessionIdEncrypt, MsgUtils.otherKey);
								MsgUtils.encrypt(protodata, sessionIdEncrypt);
							}
						}
						Response r = new Response(code == 0, headId, protodata);
						if (code == 0) {
							if (handler != null) {
								try {
									handler.handle(r);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								System.err.println("未找到处理器:" + headId);
							}
						} else {
							if (errHandler != null) {
								errHandler.handle(r);
							}
						}
						if (buf.readerIndex() == totalLen) {
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 添加返回结果处理器,当结果是错误时,将使用{@link #setErrHandler(ResponseHandler)}所设置的处理器.
	 * 
	 * @param handler
	 */
	public void addHandler(ResponseHandler handler) {
		handlers.put(handler.getHeadId(), handler);
	}

	/**
	 * 当返回结果是错误时,使用此处理器
	 * 
	 * @param errHandler
	 */
	public void setErrHandler(ResponseHandler errHandler) {
		this.errHandler = errHandler;
	}

	public static class Response {
		public boolean isSuccess;
		public int headId;
		public byte[] protobufData;

		public Response(boolean isSuccess, int headId, byte[] protobufData) {
			super();
			this.isSuccess = isSuccess;
			this.headId = headId;
			this.protobufData = protobufData;
		}
	}

	public static interface ResponseHandler {
		public int getHeadId();

		public void handle(Response r) throws Exception;

		public boolean isEncrypted();

		public boolean isSystem();
	}

	public static class ResponseHandlerAdaptor implements ResponseHandler {
		@Override
		public int getHeadId() {
			return 0;
		}

		@Override
		public void handle(Response r) throws Exception {

		}

		@Override
		public boolean isEncrypted() {
			return true;
		}

		@Override
		public boolean isSystem() {
			return false;
		}
	}

	public static class SystemResponseHandlerAdaptor implements ResponseHandler {
		@Override
		public int getHeadId() {
			return 0;
		}

		@Override
		public void handle(Response r) throws Exception {

		}

		@Override
		public boolean isEncrypted() {
			return true;
		}

		@Override
		public boolean isSystem() {
			return true;
		}
	}
}
