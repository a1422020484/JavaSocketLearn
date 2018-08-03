package uploadServerTool;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ServerTableModelListener implements TableModelListener {
	public void tableChanged(TableModelEvent e) {
		Main.getInstance().updateActionState();
	}
}
