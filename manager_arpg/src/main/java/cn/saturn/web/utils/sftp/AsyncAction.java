package cn.saturn.web.utils.sftp;

import java.util.ArrayList;
import java.util.List;

import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.ResultMsg;

public abstract class AsyncAction {

	private List<ResultMsg> mgr = new ArrayList<>();

	public abstract ServerTask createTask(ServerXFtpModel s,String folder);

	public void add(ResultMsg result) {
		mgr.add(result);
	}

	public List<ResultMsg> getMgr() {
		return mgr;
	}

	public void beforeRun() {
	}

	public void afterRun() {
	}
}
