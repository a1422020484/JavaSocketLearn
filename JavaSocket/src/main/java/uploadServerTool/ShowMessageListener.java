package uploadServerTool;

import javax.swing.JTextArea;

public class ShowMessageListener implements ServerTaskListener {
	private static ShowMessageListener instance;
	JTextArea textArea;

	public static ShowMessageListener getInstance() {
		if (instance == null) {
			instance = new ShowMessageListener();
		}
		return instance;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public void clean() {
		this.textArea.setText("");
	}

	public void update(int code, String msg) {
		this.textArea.append(msg);
		this.textArea.setCaretPosition(this.textArea.getText().length());
		this.textArea.paintImmediately(this.textArea.getBounds());
	}
}
