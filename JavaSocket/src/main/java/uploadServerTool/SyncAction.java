package uploadServerTool;

public class SyncAction extends AsyncAction {
	private static final long serialVersionUID = 1L;

	public SyncAction() {
		super("Sync");
	}

	public ServerTask createTask(Server s) {
		return new SyncTask(s);
	}
}
