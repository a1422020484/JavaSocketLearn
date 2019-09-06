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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.po.User;

public class MybatisDemo {

	private static ApplicationContext context;
	
	static {
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}

	public static void main(String[] args) throws Exception {
		inistrancedSpring();
		inistrancedMybatis();
//		Thread.sleep(1000);
		MybatisMgr mybatisMgr = (MybatisMgr) context.getBean("mybatisMgr");
//		mybatisMgr.queryName(6);
		User user = new User();
		user.setId(5);
		user.setName("zz");
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
//		User userNew1 = new User();
//		userNew1.setName("yang");
//		userNew1.setPassword("ff");
//		userNew1.setAge(88);
//		userNew1.setEmail("test");
//		userNew1.setRole("admin");
//		
//		System.out.println(mybatisMgr.insertOneUser(userNew1));// 插入的条数
//		System.out.println(userNew1.getId());// 返回刚才出入最新数据的主键
		
		List<User> userAddList = new ArrayList<>();
		userAddList.add(User.buildUser());
		userAddList.add(User.buildUser());
		userAddList.add(User.buildUser());
		mybatisMgr.insertMoreUser(userAddList);
		
//		WebSocketServer.inistrancedNetty();
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
