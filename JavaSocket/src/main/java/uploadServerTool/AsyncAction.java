package uploadServerTool;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;

public abstract class AsyncAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public AsyncAction(String string) {
		super(string);
	}

	public void actionPerformed(ActionEvent e) {
		Main.getInstance().lockActions();
		Thread t = new Thread(new Runnable() {
			public void run() {
				AsyncAction.this.beforeRun();
				List<Server> servers = ServerTableModel.getInstance().getSelectedServers();
				for (Server s : servers) {
					ServerTask t = AsyncAction.this.createTask(s);
					t.addListener(ShowMessageListener.getInstance());
					t.upMsgln(0, "server: " + s.host + " " + s.name + "(" + s.domain + ")");
					t.run();
					t.upMsgln(0, "");
				}
				AsyncAction.this.afterRun();
				Main.getInstance().unlockActions();
			}
		});
		t.start();
	}

	public abstract ServerTask createTask(Server paramServer);

	public void beforeRun() {
	}

	public void afterRun() {
	}
}
