package uploadServerTool;

public class StopTask extends ExecTask {
	public StopTask(Server server) {
		super(server);
	}

	protected String[] getCommand() {
		return new String[] { this.server.getStopCommand() };
	}
}
