package uploadServerTool;

public class StopAction extends AsyncAction {
	private static final long serialVersionUID = 1L;

	public StopAction() {
		super("Stop");
	}

	public ServerTask createTask(Server s) {
		return new StopTask(s);
	}
}
