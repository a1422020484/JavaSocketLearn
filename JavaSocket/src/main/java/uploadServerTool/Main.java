package uploadServerTool;

import com.thoughtworks.xstream.XStream;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;

public class Main {
	private static Main main;
	private volatile boolean actionLock;
	private JFrame frame;
	private JTable table;
	private ServerTableModel STModel;
	private Action stopAction;
	private Action syncAction;
	private Action startAction;
	private Action restartAction;
	private Action deployAction;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = Main.getInstance();
					window.frame.setDefaultCloseOperation(3);
					window.frame.setSize(800, 600);
					window.frame.setVisible(true);
					window.loadServers();
				} catch (Exception e) {
					e.printStackTrace();

					String errStr = e.getLocalizedMessage();
					ShowMessageListener.getInstance().update(1, "init is err!!!\nerror :" + errStr);
				}
			}
		});
	}

	private Main() {
		initialize();
	}

	public static Main getInstance() {
		if (main == null) {
			main = new Main();
		}
		return main;
	}

	void loadServers() {
		ShowMessageListener.getInstance().update(1, "servers begin");
		File file = new File("servers.xml");
		ShowMessageListener.getInstance().update(1, file.getPath() + " == " + file.getName());
		XStream xs = new XStream();
		ShowMessageListener.getInstance().update(1, file.getPath() + " ---= " + file.getName());
		xs.alias("servers", List.class);
		xs.alias("server", Server.class);
		ShowMessageListener.getInstance().update(1, file.getPath() + " ---- " + file.getName());
		List<Server> servers = (List) xs.fromXML(file);
		for (Server s : servers) {
			this.STModel.addServer(s);
		}
		ShowMessageListener.getInstance().update(1, "read servers.xml over");
		this.table.updateUI();
	}

	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(3);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		this.frame.getContentPane().add(toolBar, "North");

		toolBar.addSeparator();

		JButton stopBtn = new JButton();
		this.stopAction = new StopAction();
		this.stopAction.setEnabled(false);
		stopBtn.setAction(this.stopAction);
		toolBar.add(stopBtn);

		JButton syncBtn = new JButton();
		this.syncAction = new SyncAction();
		this.syncAction.setEnabled(false);
		syncBtn.setAction(this.syncAction);
		toolBar.add(syncBtn);

		JButton startBtn = new JButton();
		this.startAction = new StartAction();
		this.startAction.setEnabled(false);
		startBtn.setAction(this.startAction);
		toolBar.add(startBtn);

		JButton restartBtn = new JButton();
		this.restartAction = new RestartAction();
		this.restartAction.setEnabled(false);
		restartBtn.setAction(this.restartAction);
		toolBar.add(restartBtn);

		toolBar.addSeparator();

		JButton deployBtn = new JButton();
		this.deployAction = new DeployAction();
		this.deployAction.setEnabled(false);
		deployBtn.setAction(this.deployAction);
		toolBar.add(deployBtn);

		JPanel panel = new JPanel();
		this.frame.getContentPane().add(panel, "Center");
		panel.setLayout(new BorderLayout(0, 0));

		this.STModel = ServerTableModel.getInstance();
		this.STModel.addTableModelListener(new ServerTableModelListener());

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "North");
		panel_1.setLayout(new BoxLayout(panel_1, 0));

		JButton allbtn = new JButton("All");
		allbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.this.STModel.selectAll();
				Main.this.table.updateUI();
			}
		});
		allbtn.setToolTipText("all select");
		panel_1.add(allbtn);

		JButton nonebtn = new JButton("None");
		nonebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.this.STModel.unSelectAll();
				Main.this.table.updateUI();
			}
		});
		nonebtn.setToolTipText("none select");
		panel_1.add(nonebtn);

		JButton invertbtn = new JButton("Invert");
		invertbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.this.STModel.invertSelected();
				Main.this.table.updateUI();
			}
		});
		invertbtn.setToolTipText("invert selected");
		panel_1.add(invertbtn);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(0);
		panel.add(splitPane, "Center");
		this.table = new JTable(this.STModel);
		this.table.setBorder(new LineBorder(Color.LIGHT_GRAY));

		JScrollPane scrollPane1 = new JScrollPane(this.table);
		splitPane.setLeftComponent(scrollPane1);
		splitPane.setDividerLocation(200);

		JPanel panel_3 = new JPanel();
		splitPane.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, "Center");

		JTextArea textArea = new JTextArea();
		textArea.setAutoscrolls(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		ShowMessageListener.getInstance().setTextArea(textArea);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);

		popupMenu.add(new CleanAction());
	}

	public void lockActions() {
		this.actionLock = true;
		updateActionState();
	}

	public void unlockActions() {
		this.actionLock = false;
		updateActionState();
	}

	void updateActionState() {
		if ((!this.actionLock) && (this.STModel.getSelectedServers().size() > 0)) {
			this.stopAction.setEnabled(true);
			this.syncAction.setEnabled(true);
			this.startAction.setEnabled(true);
			this.restartAction.setEnabled(true);
			this.deployAction.setEnabled(true);
		} else {
			this.stopAction.setEnabled(false);
			this.syncAction.setEnabled(false);
			this.startAction.setEnabled(false);
			this.restartAction.setEnabled(false);
			this.deployAction.setEnabled(false);
		}
	}

	private static void addPopup(Component component, JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
//				Main.this.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
