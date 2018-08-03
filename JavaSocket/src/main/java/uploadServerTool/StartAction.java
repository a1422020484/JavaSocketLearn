package uploadServerTool;

public class StartAction extends AsyncAction {
	private static final long serialVersionUID = 1L;

	public StartAction() {
		super("Start");
	}

	public ServerTask createTask(Server s) {
		return new StartTask(s);
	}
}
