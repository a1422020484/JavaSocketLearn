package nettyServer.system.dao.saver;

import java.util.Map;

import nettyServer.system.dao.AbstractTwoKeyEntityManager;
import nettyServer.system.dao.MGConcurrentHashMap;
import nettyServer.system.dao.PlayerDAOManager;
import nettyServer.system.dao.realEntity.PlayerCount;

public class PlayerCountSaver extends AbstractTwoKeyEntityManager<String, Byte, PlayerCount> {

	@Override
	protected MGConcurrentHashMap<Byte, PlayerCount> get(String t) {
		return PlayerDAOManager.getInstance().getPlayerCountDao().getEntity(t);
	}

	@Override
	protected PlayerCount newMGInstance(String t, Byte k) {
		return new PlayerCount(t, k);
	}

}
