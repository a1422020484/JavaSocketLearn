package nettyServer.system.dao;

import nettyServer.system.dao.dao.PlayerCountDAO;

public class PlayerDAOManager {

	// 1.玩家一些计数信息
	private final PlayerCountDAO playerCountDao = new PlayerCountDAO();

	private static PlayerDAOManager instance = new PlayerDAOManager();

	private PlayerDAOManager() {
	}

	public static PlayerDAOManager getInstance() {
		return instance;
	}

	public PlayerCountDAO getPlayerCountDao() {
		return playerCountDao;
	}

}
