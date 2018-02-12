package cn.saturn.web.utils;

import java.util.ArrayList;
import java.util.List;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerModel;
import proto.ProtocolWeb.SendGMCmdWCS;
import proto.ProtocolWeb.SendGMCmdWSC;

/**
 * 协议工具 <br>
 * 1. 用来发送协议 <br>
 * 
 * @author rodking<br>
 *
 */
public class ProtocolTools {

	/**
	 * GM 指令发送 <br>
	 * 
	 * @param serverModel
	 * @param cmds
	 * @return
	 */
	public static List<ResultMsg> toSendGMCmdWSC(ServerModel serverModel, List<String> cmds) {
		List<ResultMsg> resultMsg = new ArrayList<>();
		// 连接服务器
		IClient client = serverModel.createClient();

		if (client != null) {
			for (String cmd : cmds) {
				SendGMCmdWCS.Builder msgb = SendGMCmdWCS.newBuilder();
				msgb.setCmd(cmd);
				SendGMCmdWSC retMsg = client.call(Head.H11102, msgb.build(), SendGMCmdWSC.class);

				if (retMsg == null) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, serverModel.getName() + " : " + cmd + " 发送失败!"));
				}else
					resultMsg.add(new ResultMsg(ResultMsg.succ, serverModel.getName() + " : " + retMsg.getRetMsg()));
			}
		} else {
			resultMsg.add(new ResultMsg(ResultMsg.fail, "gm"));
		}

		return resultMsg;
	}
}
