package uploadServerTool;

public class StartTask extends ExecTask {
	public StartTask(Server server) {
		super(server);
	}

	protected String[] getCommand() {
		return new String[] { this.server.getStartCommand() };
	}
}
