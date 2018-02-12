package cn.main.test;

import java.lang.reflect.Method;

import com.google.protobuf.MessageLite;

import xzj.core.client.GameClient.Response;
import xzj.core.client.GameClient.ResponseHandlerAdaptor;

public abstract class TempHandle extends ResponseHandlerAdaptor {
	@Override
	public abstract int getHeadId();

	@Override
	public abstract void handle(Response r);

	public static <T extends MessageLite> TempHandle handle(final int headId, final Class<T> cls) {
		return handle(headId, cls, true, true, false);
	}

	public static <T extends MessageLite> TempHandle systemHandle(final int headId, final Class<T> cls) {
		return handle(headId, cls, true, true, true);
	}

	public static <T extends MessageLite> TempHandle staticHandle(final int headId, final Class<T> cls) {
		return handle(headId, cls, true, false, false);
	}

	public static <T extends MessageLite> TempHandle handle(final int headId, final Class<T> cls, final boolean show, final boolean isEncrypted, final boolean isSystem) {

		TempHandle handle = new TempHandle() {

			@Override
			public int getHeadId() {
				return headId;
			}

			@Override
			public void handle(Response r) {
				if (show == false) {
					return; // 无视
				}
				T msg = parseFrom(cls, r.protobufData);
				String msgStr = msgStr(msg);
				System.out.println("* " + getHeadId() + " * : " + msgStr);
			}

			@Override
			public boolean isSystem() {
				return isSystem;
			}

			@Override
			public boolean isEncrypted() {
				return isEncrypted;
			}

		};

		return handle;
	}

	public static String msgStr(MessageLite msg) {
		String msgStr = msg.toString();
		msgStr = msgStr.replaceAll(" ", "");
		msgStr = msgStr.replaceAll("\n", " ");
		// msgStr = msgStr.replaceAll("  ", "");
		return msgStr;
	}

	@SuppressWarnings("unchecked")
	public static <T> T parseFrom(Class<T> cls, byte[] buffer) {
		Class<?> paramClass = cls;
		// 读取解析函数
		Method parseMethod;
		try {
			parseMethod = paramClass.getMethod("parseFrom", new Class[] { byte[].class });
		} catch (Exception e) {
			throw new RuntimeException("protobuf类没有parseFrom(byte[] data)方法,请检查生成类,支持的protobuf版本2.6.1");
		}
		// 调用解析函数
		Object obj = null;
		try {
			obj = parseMethod.invoke(null, new Object[] { buffer });
		} catch (Exception e) {
			throw new RuntimeException(cls + "参数解析失败!");
		}
		return (T) obj;
	}
}
