package spring.myBatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.po.Role;
import spring.po.User;

public class MybatisDemo {

	private static ApplicationContext context;
	
	static {
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}

	public static void main(String[] args) throws Exception {
//		inistrancedSpring();
//		inistrancedMybatis();
//		Thread.sleep(1000);
		MybatisDemo md = new MybatisDemo();
		md.inistrancedSpring();
		md.inistrancedMybatis();
		MybatisMgr mybatisMgr = (MybatisMgr) context.getBean("mybatisMgr");
//		mybatisMgr.queryName(6);
//		mybatisMgr.updateOneUserName(user);
//		mybatisMgr.queryName(6);
//		mybatisMgr.queryNameCached(6);
		
//		使用注解的方式验证二级缓存
//		User user1 = mybatisMgr.queryUserCachedById(6);
//		System.out.println(user1);
//		User user2 = mybatisMgr.queryUserCachedById(6);
//		System.out.println(user2);
		
//		使用eheache插件做缓存架构
//		Role role = mybatisMgr.queryRoleCachedById(1);
//		System.out.println(role);
//		Role role2 = mybatisMgr.queryRoleCachedById(1);
//		System.out.println(role2);
		
//		第二个数据库连接测试
//		mybatisMgr.queryNameTest(1);
		
//		单个数据插入测试
		User userNewOne = User.buildUser();
		System.out.println(mybatisMgr.insertOneUserTransaction(userNewOne));// 插入的条数
		System.out.println(userNewOne.getId());// 返回刚才出入最新数据的主键
		
//		多个数据同时插入
//		List<User> userAddList = new ArrayList<>();
//		userAddList.add(User.buildUser());
//		userAddList.add(User.buildUser());
//		userAddList.add(User.buildUser());
//		mybatisMgr.insertMoreUser(userAddList);
		
//		WebSocketServer.inistrancedNetty();
	}
	@Before
	public void inistrancedSpring() {
		context = new ClassPathXmlApplicationContext("classpath:mybatis/mybatis-spring.xml");// 此文件放在SRC目录下

	}

	@Before
	public void inistrancedMybatis() throws IOException {
		String resource = "mybatis/mybatis-single.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		sqlMapper.openSession();
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Connection conn = null;
		transactionFactory.newTransaction(conn);
	}
	
	@Test
	public void transactionInsertTest() {
		MybatisMgr mybatisMgr = (MybatisMgr) context.getBean("mybatisMgr");
		User userNewOne = User.buildUser();
		System.out.println(mybatisMgr.insertOneUserTransaction(userNewOne));// 插入的条数
		System.out.println(userNewOne.getId());// 返回刚才出入最新数据的主键
	}

	@Test
	public void transactionPropagationTest() {
		MybatisMgr mybatisMgr = (MybatisMgr) context.getBean("mybatisMgr");
		User userNewOne = User.buildUser();
		mybatisMgr.insertOneUserPropagationTransaction(userNewOne, Role.build());// 插入的条数
	}
	
	// PROPAGATION_REQUIRES_NEW
	@Test
	public void transactionPropagationRN() {
		MybatisMgr mybatisMgr = (MybatisMgr) context.getBean("mybatisMgr");
		User userNewOne = User.buildUser();
		mybatisMgr.insertOneUserPRNT(userNewOne);// 插入的条数
	}
}
