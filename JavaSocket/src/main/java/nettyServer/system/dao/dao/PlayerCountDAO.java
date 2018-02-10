package nettyServer.system.dao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nettyServer.system.dao.MGConcurrentHashMap;
import nettyServer.system.dao.persistence.ConnectionManager;
import nettyServer.system.dao.persistence.EntityDAO;
import nettyServer.system.dao.realEntity.PlayerCount;
import nettyServer.util.DebugUtil;

/**
 * 次数统计
 * @author LP
 *
 */
public class PlayerCountDAO extends EntityDAO{
	
	private static final Logger logger = LogManager.getLogger(PlayerCountDAO.class);
	
	
//	/**
//	 * 查询
//	 * @param playerId
//	 * @return
//	 */
//	public PlayerCount selectPlayerCount(String playerId,Integer countType) {
//		String sql = "SELECT * FROM " + table + " WHERE playerId = ? AND countType=?";
//		if (Strings.isNullOrEmpty(sql)) {
//			logger.error("sql sentence is null.");
//			return null;
//		}
//		PlayerCount playerCount=new PlayerCount();
//		Connection conn = null;
//		PreparedStatement pstat = null;
//		ResultSet resultSet = null;
//		try {
//			conn = getConnection();
//			pstat = conn.prepareStatement(sql);
//			pstat.setObject(1, playerId);
//			pstat.setObject(2, countType);
//			if(logger.isDebugEnabled()){
//				logger.debug("execute sql=[" + pstat.toString() + "]");
//			}
//			resultSet = pstat.executeQuery();
//			while (resultSet.next()) {
//				playerCount.setPlayerId(resultSet.getString("playerId"));
//				playerCount.setCountType(resultSet.getInt("countType"));
//				playerCount.setPlayerCount(resultSet.getInt("playerCount"));
//				playerCount.setCountTime(resultSet.getLong("countTime"));
//			}
//		} catch (SQLException e) {
//			logger.error(DebugUtil.printStack(e));
//		} finally {
//			if (resultSet != null) {
//				try {
//					resultSet.close();
//				} catch (SQLException e) {
//				}
//			}
//			if (pstat != null) {
//				try {
//					pstat.close();
//				} catch (SQLException e) {
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					
//				}
//			}
//		}
//		return playerCount;
//	}
	
	
	public MGConcurrentHashMap<Byte, PlayerCount> getEntity(String id) {
		String sql = "select * from player_count where playerId = ?";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		MGConcurrentHashMap<Byte, PlayerCount> map = new MGConcurrentHashMap<Byte, PlayerCount>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				byte countType = rs.getByte("countType");
				PlayerCount playerCount = new PlayerCount(id, countType);
				playerCount.setPlayerCount(rs.getInt("playerCount"));
				playerCount.setCountTime(rs.getLong("countTime"));
				playerCount.setAdditionInfomation(rs.getString("additionInfomation"));
				map.put(countType, playerCount);
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
		return map;
	}
	
//	
//	/**
//	 * 插入
//	 * @param PlayerCount
//	 * @return
//	 */
//	public boolean insertPlayerCount(PlayerCount playerCount){
//		boolean ret = false;
//		String sql = getInstertSql();
//		if (Strings.isNullOrEmpty(sql)) {
//			logger.error("sql sentence is null.");
//			return false;
//		}
//
//		Connection conn = null;
//		PreparedStatement pstat = null;
//		try {
//			conn = getConnection();
//			pstat = conn.prepareStatement(sql);
//			pstat.setObject(1, playerCount.getPlayerId());
//			pstat.setObject(2, playerCount.getCountType());
//			pstat.setObject(3, playerCount.getPlayerCount());
//			pstat.setObject(4, playerCount.getCountTime());
//			ret = pstat.executeUpdate() > 0;
//			if(logger.isDebugEnabled()){
//				logger.debug("execute sql=["+pstat.toString()+"]");
//			}
//		} catch (SQLException e) {
//			logger.error(DebugUtil.printStack(e));
//		} finally {
//			if (pstat != null) {
//				try {
//					pstat.close();
//				} catch (SQLException e) {
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					
//				}
//			}
//		}
//		return ret;
//	}
//	
//	
//	/**
//	 * 修改
//	 * @param PlayerCount
//	 * @return
//	 */
//	public boolean updatePlayerCount(PlayerCount playerCount){
//		boolean ret = false;
//		String sql = getUpdateSql();
//		if (Strings.isNullOrEmpty(sql)) {
//			logger.error("sql sentence is null.");
//			return false;
//		}
//
//		Connection conn = null;
//		PreparedStatement pstat = null;
//		try {
//			conn = getConnection();
//			pstat = conn.prepareStatement(sql);
//			pstat.setObject(1, playerCount.getPlayerCount());
//			pstat.setObject(2, playerCount.getCountTime());
//			pstat.setObject(3, playerCount.getPlayerId());
//			pstat.setObject(4, playerCount.getCountType());
//			ret = pstat.executeUpdate() > 0;
//			if(logger.isDebugEnabled()){
//				logger.debug("execute sql=["+pstat.toString()+"]");
//			}
//		} catch (SQLException e) {
//			logger.error(DebugUtil.printStack(e));
//		} finally {
//			if (pstat != null) {
//				try {
//					pstat.close();
//				} catch (SQLException e) {
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					
//				}
//			}
//		}
//		return ret;
//	}

}
