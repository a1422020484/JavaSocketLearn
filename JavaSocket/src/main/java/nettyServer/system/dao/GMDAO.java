package nettyServer.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nettyServer.util.DebugUtil;

public class GMDAO{
	
	private static final Logger logger = LogManager.getLogger(GMDAO.class.getName());
	
	private static GMDAO instance = new GMDAO();
	
	public static GMDAO getInstance(){
		return instance;
	}
	
	/**
	 * 更改游戏服务器实际状态
	 * @param serverId
	 * @param status 传入ServerStatus的状态
	 */
	public void updateRealStatus(int serverId, byte status){
		String sql = "update server_info set real_status = ?, server_open = if(server_open = 0, ?, server_open), server_start_time=? where server_id = ?";
		Connection conn = GMConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			int now = (int)(System.currentTimeMillis()/1000);
			ps.setInt(2, now);
			ps.setInt(3, now);
			ps.setInt(4, serverId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(DebugUtil.printStack(e));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	 * 只有在GM服务器通知游戏服务器更改状态的时候才会执行
	 * 更新服务器预发布状态
	 * @param serverId 服务器id
	 */
	public void updatePreAnnouncement(int serverId){
		String sql = "update server_info set pre_announcement = ? where server_id = ?";
		Connection conn = GMConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ServerStatus serverStatus = ServerStatus.getInstance();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, serverStatus.getPreAnnouncement());
			ps.setInt(2, serverId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(DebugUtil.printStack(e));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	 * 查询服务器预发布状态
	 * @param serverId 服务器id
	 */
	public void queryPreAnnouncement(int serverId){
		String sql = "select pre_announcement from server_info where server_id = ?";
		Connection conn = GMConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ServerStatus serverStatus = ServerStatus.getInstance();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, serverId);
			rs = ps.executeQuery();
			if(rs.next()){
				serverStatus.setPreAnnouncement(rs.getByte("pre_announcement"));
			}
		} catch (SQLException e) {
			logger.error(DebugUtil.printStack(e));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	 * 查询GM ip 限制表
	 */
	public void queryIpLimiting(){
		String sql = "select * from ip_limiting";
		Connection conn = GMConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		IpLimitMgr ipLimitMgr = IpLimitMgr.getInstance();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Set<String> blackSet = new HashSet<String>();
			Set<String> whiteSet = new HashSet<String>();
			if(rs.next()){
				byte type = rs.getByte("type");
				if(type == 0){//白名单
					whiteSet.add(rs.getString("ip"));
				} else if(type == 1){//黑名单
					whiteSet.add(rs.getString("ip"));
				}
			}
			ipLimitMgr. setWhiteSet(whiteSet);
			ipLimitMgr.setBlackSet(blackSet);
		} catch (SQLException e) {
			logger.error(DebugUtil.printStack(e));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	 * 查询服务器的开服时间
	 */
	public long queryActivityServerOpenTime(int serverId){
		String sql = "select UNIX_TIMESTAMP(activity_server_open_time) AS activity_server_open_time from server_info where server_id = ?";
		Connection conn = GMConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long activityServerOpenTime = System.currentTimeMillis();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, serverId);
			rs = ps.executeQuery();
			if(rs.next()){
				activityServerOpenTime = rs.getInt("activity_server_open_time") * 1000L;
			}
		} catch (SQLException e) {
			logger.error(DebugUtil.printStack(e));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return activityServerOpenTime;
	}
}
