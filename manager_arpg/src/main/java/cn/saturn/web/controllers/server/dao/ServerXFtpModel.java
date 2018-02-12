package cn.saturn.web.controllers.server.dao;

public class ServerXFtpModel {
	private long id;
	private int s_id; 			// 服务器id
	private String host; 		// host
	private int port; 			// 端口
	private String user_name;   // ftp 用户名 
	private String password; 	// ftp 密码
	private String game_path; 	// 上传目录
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGame_path() {
		return game_path;
	}
	public void setGame_path(String game_path) {
		this.game_path = game_path;
	}
	
}
