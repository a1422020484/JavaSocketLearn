package uploadServerTool;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class ServerTask implements Runnable {
	public static final int code_error = 1;
	public static final int code_normal = 0;
	List<ServerTaskListener> listeners;
	final Server server;

	public ServerTask(Server server) {
		this.server = server;
	}

	public void run() {
		JSch jsch = new JSch();
		try {
			Session session = jsch.getSession(this.server.username, this.server.host, this.server.port);

			String keyFile = this.server.getKeyFile();
			if ((keyFile == null) || (keyFile.length() <= 0)) {
				session.setPassword(this.server.password);
			} else {
				jsch.addIdentity(keyFile);
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect(10000);
			_run(session);
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	protected abstract void _run(Session paramSession);

	public void upMsg(int code, String msg) {
		if (this.listeners == null) {
			return;
		}
		for (ServerTaskListener l : this.listeners) {
			l.update(code, msg);
		}
	}

	public void upMsgln(int code, String msg) {
		upMsg(code, msg + "\n");
	}

	public void upMsgFinish() {
		upMsgln(0, "     ok.");
	}

	public void upMsgFailure() {
		upMsgln(1, "     failure!");
	}

	public void addListener(ServerTaskListener listener) {
		if (this.listeners == null) {
			this.listeners = new ArrayList();
		}
		this.listeners.add(listener);
	}
}
