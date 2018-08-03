package uploadServerTool;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public final class ServerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static ServerTableModel instance;
	List<Server> servers = new ArrayList();
	List<Object[]> data = new ArrayList();
	String[] title = {"0", "domain", "server" };
	Class<?>[] columnClass = { Boolean.class, String.class, String.class };
	boolean[] canEdit = { true };

	public static ServerTableModel getInstance() {
		if (instance == null) {
			instance = new ServerTableModel();
		}
		return instance;
	}

	public void addServer(Server server) {
		Object[] d = new Object[3];
		d[0] = Boolean.FALSE;
		d[1] = server.getDomain();
		d[2] = server.getName();
		this.data.add(d);
		this.servers.add(server);
	}

	public List<Server> getSelectedServers() {
		List<Server> list = new ArrayList();
		for (int i = 0; i < this.data.size(); i++) {
			Object[] arr = (Object[]) this.data.get(i);
			Boolean b = (Boolean) arr[0];
			if ((b != null) && (b.booleanValue())) {
				list.add((Server) this.servers.get(i));
			}
		}
		return list;
	}

	public void selectAll() {
		for (int i = 0; i < this.data.size(); i++) {
			Object[] arr = (Object[]) this.data.get(i);
			arr[0] = Boolean.TRUE;
		}
		fireTableDataChanged();
	}

	public void unSelectAll() {
		for (int i = 0; i < this.data.size(); i++) {
			Object[] arr = (Object[]) this.data.get(i);
			arr[0] = Boolean.FALSE;
		}
		fireTableDataChanged();
	}

	public void invertSelected() {
		for (int i = 0; i < this.data.size(); i++) {
			Object[] arr = (Object[]) this.data.get(i);
			Boolean b = (Boolean) arr[0];
			arr[0] = ((b != null) && (b.booleanValue()) ? Boolean.FALSE : Boolean.TRUE);
		}
		fireTableDataChanged();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return this.canEdit[columnIndex];
	}

	public String getColumnName(int column) {
		return this.title[column];
	}

	public Class<?> getColumnClass(int columnIndex) {
		return this.columnClass[columnIndex];
	}

	public int getRowCount() {
		return this.servers.size();
	}

	public int getColumnCount() {
		return this.title.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Object[]) this.data.get(rowIndex))[columnIndex];
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		((Object[]) this.data.get(rowIndex))[columnIndex] = aValue;
		fireTableDataChanged();
	}
}
