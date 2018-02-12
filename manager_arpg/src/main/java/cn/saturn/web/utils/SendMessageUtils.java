package cn.saturn.web.utils;

import com.google.protobuf.GeneratedMessage;

import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerModel;

/**
 * 发送消息工具类 <br>
 * 
 * @author rodking
 *
 */
public class SendMessageUtils<E extends GeneratedMessage> {

	IClient client = null;

	private SendMessageUtils(IClient client) {
		this.client = client;
	}

	public static SendMessageUtils<GeneratedMessage> createSendMessageUtils(ServerModel srvModel) {
		if (srvModel == null)
			return null;

		IClient client = srvModel.createClient();
		if (client == null) {
			return null;
		}

		return new SendMessageUtils<>(client);
	}

	public GeneratedMessage send(int msgType, E msg) {
		return client.call(msgType, msg.toBuilder().build(), msg.getClass());
	}
}
