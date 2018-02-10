package sinaData;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UIData {

	public static UIData instance = new UIData();

	public static UIData getInstance() {
		return instance;
	}

	private JFrame mainFrame = new JFrame("LoL");
	private JLabel nameJlabel = new JLabel("");
	private JLabel todayOpenPriceJlabel = new JLabel("");
	private JButton startButton = new JButton("开始监控");

	public UIData() {
		mainFrame.setSize(400, 300);
		mainFrame.setLocation(200, 200);
		mainFrame.setLayout(null);
		startButton.setBounds(50, 50, 280, 30);
		startButton.addActionListener(Lei.listenerStart);
		mainFrame.add(nameJlabel);
		mainFrame.add(startButton);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JLabel getNameJlabel() {
		return nameJlabel;
	}

	public void setNameJlabel(JLabel nameJlabel) {
		this.nameJlabel = nameJlabel;
	}

	public JFrame getF() {
		return mainFrame;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public JLabel getTodayOpenPriceJlabel() {
		return todayOpenPriceJlabel;
	}

	public void setTodayOpenPriceJlabel(JLabel todayOpenPriceJlabel) {
		this.todayOpenPriceJlabel = todayOpenPriceJlabel;
	}

}
