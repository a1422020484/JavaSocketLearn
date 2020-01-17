package httpTest;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import nettyServer.util.MsgUtils;
import nettyServer.util.RSAUtils;

public class GameClient {

	private String uri;
	private String sessionId;

	private Map<Integer, ResponseHandler> handlers = new HashMap<>();
	private ResponseHandler errHandler;

	public final int MaxDataLen = 8 * 1024 * 1024;
	public final int MaxMessageLen = 4 * 1024 * 1024;

	public GameClient(String ip, int port) {
		this.uri = "http://" + ip + ":" + port;
	}

	public GameClient(String uri) {
		this.uri = uri;
	}

	public void send(int headId, MessageLite proto) {
		try {
			send0(headId, proto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void send0(int headId, MessageLite proto) throws Exception {
		// 创建一个byteBuf
		ByteBuf buf = Unpooled.buffer();

		if (sessionId != null) {
			byte[] sessionData = sessionId.getBytes("utf-8");
			buf.writeByte(sessionData.length);
			buf.writeBytes(sessionData);
		} else {
			buf.writeByte(0);
		}
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
		buf.getBytes(0, data); // 复制了一份 buf 的数据到 data

		HttpUtils.create(uri).post(data, new HttpResponseHandler() {
			@Override
			public void handler(HttpResponse resp) {
				HttpEntity entity = resp.getEntity();
				if (!entity.isStreaming())
					return;
				int totalLen = (int) entity.getContentLength();
				if (totalLen == 0)
					return;
				if (totalLen > MaxDataLen) {
					throw new RuntimeException("");
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
						System.arraycopy(temp, 0, data, offset, size);
						offset += size;
					}

					ByteBuf buf = Unpooled.wrappedBuffer(data);
					while (true) {
						int partLen = buf.readInt();
						int code = buf.readByte();
						int headId = buf.readInt();
						if (partLen > MaxMessageLen) {
							throw new RuntimeException();
						}

						byte[] protodata = new byte[partLen - 5];
						buf.readBytes(protodata);// 包含了headId

						ResponseHandler handler = handlers.get(headId);
						if (handler != null) {
							if (handler.isSystem()) {

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

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
	}

	public static class Response {
		public boolean isSuccess;
		public int headId;
		public byte[] protobufData;

		public Response(boolean isSuccess, int headId, byte[] protobufData) {
			this.isSuccess = isSuccess;
			this.headId = headId;
			this.protobufData = protobufData;
		}
	}
}
