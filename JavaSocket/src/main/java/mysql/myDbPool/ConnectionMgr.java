package mysql.myDbPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionMgr {

	private final static Queue<Connection> poos = new LinkedBlockingDeque<Connection>();

	private int maxConnections = 10;
	private int minConnections = 1;
	private String driverName = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/study";
	private static final String username = "root";
	private static final String password = "root";

	public ConnectionMgr() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createDataSource() throws SQLException {
		for (int i = 0; i < maxConnections; i++) {
			Connection connection = DriverManager.getConnection(URL, username, password);
			addConnection(connection);
		}
	}

	public void addConnection(Connection connection) {
		poos.offer(connection);
	}

	public Connection borrowConnection() {
		return poos.poll();
	}

}
