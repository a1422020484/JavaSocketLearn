package nettyServer.system;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import nettyServer.listener.ServerStartupListener;
import nettyServer.system.dbscheam.Schema;

/**
 * 更新数据库
 * 
 * @author xiezuojie
 */
@Component
public class DBUpdateListener implements ServerStartupListener {
	
	String updateFile = "/update.sql";

//	@Resource
//	DataSource dataSource;
//    @Resource
//    DBLockDAO dbLockDAO;

    @Override
	public void started() {
//
//        String[] sqls = readUpdateSql();
//		if (sqls != null) {
//			try {
//				Connection conn = dataSource.getConnection();
//				conn.setAutoCommit(false);
//				Statement ps = conn.createStatement();
//				for (String sql : sqls) {
//                    ps.addBatch(sql);
//				}
//				ps.executeBatch();
//				ps.close();
//				conn.commit();
//				conn.setAutoCommit(true);
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//				throw new RuntimeException("执行update.sql失败!");
//			}
//			deleteUpdateSql();
//		}
//
//        Schema.verifySchema();
//        checkLock();
	}

    /**
     * 每个游戏数据库只允许一个游戏服来连接
     * 在游戏服s1连接成功后创建锁,在游戏服s2再次连接到此数据库时不允许连接成功,避免配置错误导致游戏数据错乱的致命错误
     */
//    private void checkLock() {
//        Integer serverId = dbLockDAO.getLock();
//        boolean err = false;
//        int errServerId = 0;
//        if (serverId == null || serverId == 0) {
//            // 创建锁
//            try {
//                dbLockDAO.insertLock(GameConfig.ServerId); // 受主键约束,要么成功,要么抛出异常
//            } catch (Exception e) {
//                // 可能已存在数据,数据插入失败(被其它服务器抢先)
//                e.printStackTrace();
//                errServerId = dbLockDAO.getLock();
//                err = true;
//            }
//        } else {
//            // 已存在的服务器id是否就是本服务器id?
//            if (serverId != GameConfig.ServerId) {
//                // 错误
//                errServerId = serverId;
//                err = true;
//            }
//        }
//
//        if (err) {
//            System.err.println("服务器启动失败::数据库已连接到游戏服:" + errServerId + " !");
//            System.exit(0);
//        }
//    }

	private String[] readUpdateSql() {
		URL url = this.getClass().getResource(updateFile);
		if (url == null) {
			return null;
		}
		File file = new File(url.getPath());
		if (!file.exists()) {
			return null;
		}
		
		String[] sqls = null;
		try {
			StringBuffer buf = new StringBuffer(512);
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			while (true) {
				String line = raf.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				if (StringUtils.isBlank(line)) {
					continue;
				}
				if (line.startsWith("--")) {
					continue;
				}
				buf.append(line);
			}
			if (buf.length() > 0) {
				sqls = buf.toString().split(";");
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("update.sql文件读取失败!");
		}
		if (sqls == null) {
			deleteUpdateSql();
		}
		return sqls;
	}
	
	private void deleteUpdateSql() {
		URL url = this.getClass().getResource(updateFile);
		if (url == null) {
			return;
		}
		File file = new File(url.getPath());
		if (file.exists()) {
			file.delete();
		}
	}

}
