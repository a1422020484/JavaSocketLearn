package uploadServerTool;

public class RestartAction extends AsyncAction {
	private static final long serialVersionUID = 1L;

	public RestartAction() {
		super("Restart");
	}

	public ServerTask createTask(Server s) {
		return new RestartTask(s);
	}
}
