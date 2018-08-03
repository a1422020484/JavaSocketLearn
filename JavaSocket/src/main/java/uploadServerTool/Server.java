package uploadServerTool;

public class Server {
	protected String name;
	protected String domain;
	protected String host;
	protected int port;
	protected String username;
	protected String password;
	protected String keyFile;
	protected String uploadPath;
	protected String localPath;
	protected String restartCommand;
	protected String startCommand;
	protected String stopCommand;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUploadPath() {
		return this.uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getLocalPath() {
		return this.localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getKeyFile() {
		return this.keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

	public String getRestartCommand() {
		return this.restartCommand;
	}

	public void setRestartCommand(String restartCommand) {
		this.restartCommand = restartCommand;
	}

	public String getStartCommand() {
		return this.startCommand;
	}

	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}

	public String getStopCommand() {
		return this.stopCommand;
	}

	public void setStopCommand(String stopCommand) {
		this.stopCommand = stopCommand;
	}
}
