package uploadServerTool;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class CleanAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public CleanAction() {
		super("clean");
	}

	public void actionPerformed(ActionEvent e) {
		ShowMessageListener.getInstance().clean();
	}
}
