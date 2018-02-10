package nettyServer.system.dao;

import java.util.ArrayList;
import java.util.List;

import nettyServer.system.dao.saver.PlayerCountSaver;

/**
 * 数据存储管理类
 * 
 * @author admin
 * 
 */
public class PlayerSaverManager {

	// --------------自定义的数据处理类,这边只整理了改动较小的部分---------------------

	// ------------------------父类为EntityManager的-------------

	// 用于保存数据
	private List<EntityManager> entityManagerList = new ArrayList<EntityManager>();

	// 1.玩家一些计数信息
	private PlayerCountSaver playerCountSaver;

	private final static PlayerSaverManager instance = new PlayerSaverManager();

	public PlayerSaverManager() {
		// 1.
		playerCountSaver = new PlayerCountSaver();
		entityManagerList.add(playerCountSaver);
	}
}
