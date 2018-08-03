package uploadServerTool;

public class RestartTask extends ExecTask {
	public RestartTask(Server server) {
		super(server);
	}

	protected String[] getCommand() {
		String restartCommand = this.server.getRestartCommand();
		if ((restartCommand != null) && (restartCommand.length() > 0)) {
			return new String[] { restartCommand };
		}
		return new String[] { this.server.getStopCommand(), this.server.getStartCommand() };
	}
}
