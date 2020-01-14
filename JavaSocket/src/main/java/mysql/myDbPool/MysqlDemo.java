package mysql.myDbPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDemo {

	private static final ConnectionMgr connectionMgr = new ConnectionMgr();

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		MysqlDemo mdp = new MysqlDemo();

		connectionMgr.createDataSource();
		long begin = System.currentTimeMillis();
//		for (int i = 0; i < 2000; i++) {
//			new Thread(() -> {
//				try {
//					mdp.queryNormal(connectionMgr.borrowConnection());
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}).start();
//		}
		for (int i = 0; i < 100; i++) {
			mdp.queryNormal(connectionMgr.borrowConnection());
		}
		
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

	public void queryNormal(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("select * from dept");
		ResultSet rs = ps.executeQuery();
	}

}
