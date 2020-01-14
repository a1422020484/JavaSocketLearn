package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

public class MysqlDemoPool {
	private static final String URL = "jdbc:mysql://localhost:3306/study";
	private static final String username = "root";
	private static final String password = "root";
	private static BasicDataSource datasource = new BasicDataSource();

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		MysqlDemoPool mdp = new MysqlDemoPool();

		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl(URL);
		datasource.setUsername(username);
		datasource.setPassword(password);

		datasource.setInitialSize(10);
		datasource.setMaxActive(8);
		datasource.setMaxIdle(5);
		datasource.setMinIdle(1);

		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				try {
					Connection connection = datasource.getConnection();
					System.out.println(connection.hashCode());
					mdp.queryNormal(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}).start();
		}
		long end = System.currentTimeMillis();

		// for (int i = 0; i < 100; i++) {
		// Connection connection = datasource.getConnection();
		// System.out.println(connection.hashCode());
		// mdp.queryNormal(connection);
		// }

		System.out.println(end - begin);
	}

	public void queryNormal(Connection connection) throws SQLException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement ps = connection.prepareStatement("select * from dept");
		ResultSet rs = ps.executeQuery();
		connection.close();
	}

}
