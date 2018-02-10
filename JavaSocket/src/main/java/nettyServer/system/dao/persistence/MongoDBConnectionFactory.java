package nettyServer.system.dao.persistence;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import nettyServer.system.dao.GMConnectionFactory;

public class MongoDBConnectionFactory {

	private static final Logger logger = LogManager.getLogger(GMConnectionFactory.class);
	
	private final static String DB_CONFIG_FILE = "mongodb.properties";
	
	private static String mongodb_url = "";
	
	
	public static void init() {
		try {
			// 读取db.properties文件
			Properties props = new Properties();

			/**
			 * 使用类路径的读取方式 / : 斜杠表示classpath的根目录 在java项目下，classpath的根目录从bin目录开始
			 * 在web项目下，classpath的根目录从WEB-INF/classes目录开始
			 */
			InputStream in = new MongoDBConnectionFactory().getClass().getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
			// 加载文件
			props.load(in);
			// 读取信息
			mongodb_url = props.getProperty("mongodb_url");
			if(StringUtils.isEmpty(mongodb_url)){
				throw new Exception("[mongodb_url is empty]");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static MongoClient createMongoDBClientWithURI() {
		init();
		// 另一种通过URI初始化
		// mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
		MongoClientURI connectionString = new MongoClientURI(mongodb_url);
		return new MongoClient(connectionString);
	}
}
