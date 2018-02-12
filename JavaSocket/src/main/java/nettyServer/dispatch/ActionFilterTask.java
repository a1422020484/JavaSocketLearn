package nettyServer.dispatch;

/**
 * 
 * 
 * @author yangxp
 */
public class ActionFilterTask implements ActionTask {
	
	private ActionTask task;

	public ActionFilterTask(ActionTask actionTask) {
		this.task = actionTask;
	}

	@Override
	public Integer getPlayerId() {
		return task.getPlayerId();
	}

	@Override
	public void run() {
		for (ActionFilter f : ActionFilters.filters) {
			f.before(null);
		}
		task.run();
		for (ActionFilter f : ActionFilters.filters) {
			f.after(task.getPlayerId(), null, null);
		}
	}

}
