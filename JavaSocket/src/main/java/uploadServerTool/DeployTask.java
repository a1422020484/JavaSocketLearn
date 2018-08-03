package uploadServerTool;

public class DeployTask extends TaskGroup {
	ServerTask[] group = { new StopTask(this.server), new SyncTask(this.server), new StartTask(this.server) };

	public DeployTask(Server server) {
		super(server);
	}

	protected ServerTask[] getGroup() {
		return this.group;
	}

	public void addListener(ServerTaskListener listener) {
		super.addListener(listener);
		for (ServerTask task : this.group) {
			task.addListener(listener);
		}
	}
}
