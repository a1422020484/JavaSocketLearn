package cn.saturn.web.utils.client;

import java.lang.reflect.Method;
import java.net.URL;

import javax.crypto.IllegalBlockSizeException;

import com.google.protobuf.MessageLite;

import proto.Protocol;
import xzj.core.client.GameClient;
import xzj.core.client.GameClient.Response;
import xzj.core.client.GameClient.ResponseHandlerAdaptor;

public class SaturnClient implements IClient {

	private GameClient client;

	private String errStr = "无法访问";

	private String url = "";

	public SaturnClient(String url) throws Exception {
		// 解析url
		this.url = url;
		URL url0 = new URL(url);
		String host = url0.getHost();
		int port = url0.getPort();
		// System.out.println("Richie:" + url + ":::" + host + "_" + port);
		// 创建服务器
		this.client = new GameClient(host, port);
		// 处理错误
		client.setErrHandler(new ResponseHandlerAdaptor() {

			@Override
			public int getHeadId() {
				return -1;
			}

			@Override
			public void handle(Response r) throws Exception {
				Protocol.ErrSC esc = Protocol.ErrSC.parseFrom(r.protobufData);
				SaturnClient.this.errStr = esc.getMsg();
			}
		});

	}

	@Override
	public <T> T call(final int actionId, Object msg, final Class<T> clazz) {
		return call(actionId, msg, clazz, true, true);
	}

	public <T> T call(final int actionId, Object msg, final Class<T> clazz, boolean system, boolean isEncrypted) {
		if (!(msg instanceof MessageLite)) {
			return null; // 消息类型错误
		}
		// 转化消息
		MessageLite msg0 = (MessageLite) msg;
		final Object[] retAry = new Object[1];

		// 绑定事件监听
		client.addHandler(new ResponseHandlerAdaptor() {
			@Override
			public int getHeadId() {
				return actionId;
			}

			@Override
			public void handle(Response r) throws Exception {
				T msg = parseFrom(clazz, r.protobufData);
				retAry[0] = msg;
			}

			@Override
			public boolean isSystem() {
				return system;
			}

			@Override
			public boolean isEncrypted() {
				return isEncrypted;
			}
		});
		// 访问消息
		try {
			errStr = "并无回馈";
			client.send0(actionId, msg0);
		} catch (IllegalBlockSizeException ex) {
			this.errStr = "数据过长.";
			ex.printStackTrace();
		} catch (Exception ex) {
			this.errStr = ex.toString();
			ex.printStackTrace();
		}

		// 读取返回结果
		@SuppressWarnings("unchecked")
		T retMsg = (T) retAry[0];
		if (retMsg == null) {
			System.err.println("actionID:" + actionId + " 访问错误, err:" + errStr + ", url:" + url);
		}

		return retMsg;
	}

	@SuppressWarnings("unchecked")
	protected static <T> T parseFrom(Class<T> cls, byte[] buffer) {
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
			throw new RuntimeException("参数解析失败!");
		}
		return (T) obj;
	}

	public String getErrStr() {
		return errStr;
	}
}
