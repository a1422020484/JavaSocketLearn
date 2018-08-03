package uploadServerTool;

public class DeployAction extends AsyncAction {
	private static final long serialVersionUID = 1L;

	public DeployAction() {
		super("Deploy");
	}

	public ServerTask createTask(Server s) {
		return new DeployTask(s);
	}
}
