/**
 *   Copyright 2013-2015 Sophia
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package nettyServer.system.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class GMConnectionFactory {
	private static final Logger logger = LogManager.getLogger(GMConnectionFactory.class);

	private static final String DB_CONFIG_FILE = "gm.properties";
	
	private static String url = null;
	private static String user = null;
	private static String password = null;
	private static String driverClass = null;

	public static GMConnectionFactory INSTANCE = new GMConnectionFactory();

	private GMConnectionFactory() {
		init();
	}

	public static GMConnectionFactory getInstance() {
		return INSTANCE;
	}

	public void init() {
		try {
			// 读取db.properties文件
			Properties props = new Properties();

			/**
			 * 使用类路径的读取方式 / : 斜杠表示classpath的根目录 在java项目下，classpath的根目录从bin目录开始
			 * 在web项目下，classpath的根目录从WEB-INF/classes目录开始
			 */
			InputStream in = getClass().getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
			// 加载文件
			props.load(in);
			// 读取信息
			url = props.getProperty("url");
			user = props.getProperty("user");
			password = props.getProperty("password");
			driverClass = props.getProperty("driverClass");
			// 注册驱动程序
			Class.forName(driverClass);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return connection;
	}

}
