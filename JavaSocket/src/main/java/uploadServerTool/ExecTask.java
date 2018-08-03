package uploadServerTool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public abstract class ExecTask extends ServerTask {
	public ExecTask(Server server) {
		super(server);
	}

	protected void _run(Session session) {
		String[] commands = getCommand();
		if (commands == null) {
			throw new NullPointerException("commands is null");
		}
		for (String command : commands) {
			if ((command == null) || (command.length() <= 0)) {
				upMsgln(1, "exec: " + command + " is empty.");
			} else {
				exec(session, command);
			}
		}
	}

	protected void exec(Session session, String command) {
		try {
			upMsgln(0, "exec: " + command + " ");
			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(command);
			InputStream in = channel.getInputStream();
			channel.connect();

			upMsgln(0, "{");
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				for (;;) {
					String line = reader.readLine();
					if (line == null) {
						break;
					}
					upMsgln(0, line);
				}
			} catch (Exception e) {
				e.printStackTrace();

				in.close();
				upMsg(0, "}");

				channel.disconnect();
				upMsgFinish();
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			upMsgFailure();
			upMsgln(1, e.getMessage());
		}
	}

	protected abstract String[] getCommand();
}
