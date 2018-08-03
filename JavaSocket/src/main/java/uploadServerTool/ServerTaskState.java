package uploadServerTool;

public class ServerTaskState {
	public boolean hasError;
	public String msg;

	public ServerTaskState(boolean hasError, String msg) {
		this.hasError = hasError;
		this.msg = msg;
	}
}
