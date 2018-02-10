package nettyServer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	public static Connection getConnection( String url, String user, String password ){
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("加载成功！");
		} catch (ClassNotFoundException e) {
			System.out.println("加载失败！");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("连接成功！");
			 return conn;
		} catch (SQLException e) {

			System.out.println("连接失败");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Connection getConnection( String dataBase ){
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("加载成功！");
		} catch (ClassNotFoundException e) {
			System.out.println("加载失败！");
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/" + dataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
//		String url = "jdbc:mysql://119.29.28.51:9500/poke_home?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true";
//					  jdbc:mysql://119.29.28.51:3306/poke_home?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true
		String user = "root";
		String password = "admin";
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("连接成功！");
			 return conn;
		} catch (SQLException e) {

			System.out.println("连接失败");
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	
	public static void main(String[] args) {
		Connection conn = DBUtil.getConnection("logtest");
		String sql = "select * from user";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while( rs.next() ){
				System.out.println(rs.getString(1) + "," + rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println("mysql操作错误");
			e.printStackTrace();
		}

		
		
	}
	
}
