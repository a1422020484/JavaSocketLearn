package spring.myBatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import nettyTest.netty3Test.webSocket.server.WebSocketServer;

public class MybatisDemo {

	private static ApplicationContext context;

	public static void main(String[] args) throws Exception {
		inistrancedSpring();
		inistrancedMybatis();
		WebSocketServer.inistrancedNetty();
		MybatisMgr mybatisMgr = (MybatisMgr) context.getBean("mybatisMgr");
		mybatisMgr.queryName();
	}

	public static void inistrancedSpring() {
		context = new ClassPathXmlApplicationContext("classpath:mybatis/mybatis-spring.xml");// 此文件放在SRC目录下

	}

	public static void inistrancedMybatis() throws IOException {
		String resource = "mybatis/mybatis-single.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		sqlMapper.openSession();
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Connection conn = null;
		transactionFactory.newTransaction(conn);
	}

}
