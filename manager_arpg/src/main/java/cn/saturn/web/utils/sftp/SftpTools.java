package cn.saturn.web.utils.sftp;

import java.util.List;

import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.ResultMsg;

public class SftpTools {

	public static synchronized List<ResultMsg> exec(List<ServerXFtpModel> servers,String folder) {
		SyncAction action = new SyncAction();
		action.beginRun(servers,folder);
		return action.getMgr();
	}
}
