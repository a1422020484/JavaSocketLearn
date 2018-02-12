package cn.saturn.web.utils.sftp;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.ResultMsg;

public abstract class ServerTask {
	private final Logger log = LoggerFactory.getLogger(LogType.xftp);

	final ServerXFtpModel xftp;
	final AsyncAction action;

	public ServerTask(ServerXFtpModel xftp, AsyncAction a) {
		this.xftp = xftp;
		this.action = a;
	}

	public void run() {
		JSch jsch = new JSch();
		Session session = null;
		try {
			String host = xftp.getHost();
			log.info("[ ftp ] {} connection..", host);
			session = jsch.getSession(xftp.getUser_name(), host, xftp.getPort());
			session.setPassword(xftp.getPassword());

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setTimeout(60000);
			session.connect();
			_run(session);
			session.disconnect();
			log.info("[ ftp ] ftp {} dis connection..", host);
		} catch (JSchException e) {
			//System.out.println(e.getMessage());
			this.action.add(new ResultMsg(ResultMsg.fail,xftp.getS_id()+" 连接失败!"));
		}finally{
			if(session != null && session.isConnected()){
				session.disconnect();
			}
		}
	}

	public AsyncAction getAction() {
		return action;
	}

	protected abstract void _run(Session session);

}
