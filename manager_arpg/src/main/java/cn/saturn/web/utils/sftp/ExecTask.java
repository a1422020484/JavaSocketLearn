package cn.saturn.web.utils.sftp;

import java.io.InputStream;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;

public abstract class ExecTask extends ServerTask {
	
	public ExecTask(ServerXFtpModel xftp,AsyncAction a) {
		super(xftp,a);
	}

	@Override
	protected void _run(Session session) {
		String[] commands = getCommand();
		if (commands == null)
			throw new NullPointerException("commands is null");

		for (String command : commands)
			exec(session, command);
	}

	protected void exec(Session session, String command) {
		try {
			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(command);
			InputStream in = channel.getInputStream();
			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			in.close();

			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract String[] getCommand();

}
