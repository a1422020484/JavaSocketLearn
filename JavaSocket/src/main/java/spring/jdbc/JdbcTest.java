package spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://127.0.0.1:3306/study";
			String user = "root";
			String password = "root";
			connection = DriverManager.getConnection(url, user, password);

			String sql = "select * from dept where dept_no = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setLong(1, 1L);

			rs = prepareStatement.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("dept_name"));
				System.out.println(rs.getString("db_source"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接，释放资源
			if (rs != null) {
				rs.close();
			}
			if (prepareStatement != null) {
				prepareStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
