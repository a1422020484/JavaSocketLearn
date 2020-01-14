package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

public class MysqlDemo {
	private static final String URL = "jdbc:mysql://localhost:3306/study";
	private static final String username = "root";
	private static final String password = "root";
	private static final AtomicInteger atomicInt = new AtomicInteger();

	@Before
	public void initConnection() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		MysqlDemo md = new MysqlDemo();
		Class.forName("com.mysql.jdbc.Driver");
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				try {
					md.queryNormal();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

	public void insert() {

	}

	@Test
	public void queryNormal() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, username, password);
		PreparedStatement ps = connection.prepareStatement("select * from dept");

		ResultSet rs = ps.executeQuery(); 
		connection.close();
	}

	@Test
	public void queryDBpool() {

	}
}
