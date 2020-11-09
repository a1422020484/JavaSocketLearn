package nettyServer.util;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import nettyServer.listener.ServerStartupListener;
import nettyServer.templet.manager.TempletManager;
import nettyServer.util.cache.Entities;
import nettyServer.util.cache.EntityCacheManager;
import nettyServer.util.cache.MemoryCacheManager;
import nettyServer.util.cache.PersistEntity;

/**
 * 供游戏内获取各种数据: 服务service, 数据管理器manager, 缓存数据cachedData
 * 
 * @author yangxp
 */
@Component
public final class GameExplorer implements ApplicationContextAware, ServerStartupListener {

	static EntityCacheManager cm = new MemoryCacheManager();

	/**
	 * @param playerId
	 * @param clazz
	 * @return 缓存中是否存在这个实体
	 */
	public static <T extends PersistEntity> boolean hasEntity(int playerId, Class<T> clazz) {
		if (!GlobalPlayerIds.containsKey(playerId)) {
			return false;
		}
		return cm.hasEntity(playerId, clazz);
	}

	/**
	 * 从缓存获取实体,在缓存未获取到实体时,将从数据库读取数据并放到缓存
	 * 
	 * {@link MemoryCacheManager#getEntity(int, Class)}
	 * 
	 * @param playerId
	 * @param clazz
	 * @return 缓存实体,如果这个玩家不存在,那么返回null,否则,返回已存在的缓存内实体,没有则返回null
	 */
	public static <T extends PersistEntity> T getEntity(int playerId, Class<T> clazz) {
//		if (!GlobalPlayerIds.containsKey(playerId)) {
//			return null;
//		}
		return cm.getEntity(playerId, clazz);
	}

	/**
	 * 
	 * @param playerId
	 * @param clazz
	 * @return 缓存实体,如果这个玩家不存在,那么返回null,否则,返回已存在的缓存内实体,没有则返回null
	 */
	public static <T extends PersistEntity> T getEntityIfExisting(int playerId, Class<T> clazz) {
		if (!GlobalPlayerIds.containsKey(playerId)) {
			return null;
		}
		if (cm.hasEntity(playerId, clazz)) {
			return cm.getEntity(playerId, clazz);
		}
		return null;
	}

	/**
	 * @param playerId
	 * @return 缓存实体集,如果这个玩家不存在,那么返回null,否则,返回已存在的缓存内实体集,没有则创建一个并返回
	 */
	public static Entities getEntities(int playerId) {
		if (!GlobalPlayerIds.containsKey(playerId)) {
			return null;
		}
		return cm.getEntities(playerId);
	}

	/**
	 * @param playerId
	 * @return 缓存实体集,如果这个玩家不存在,那么返回null,否则,返回已存在的缓存内实体集,没有则返回null
	 */
	public static Entities getEntitiesIfExisting(int playerId) {
		if (!GlobalPlayerIds.containsKey(playerId)) {
			return null;
		}
		return cm.getEntitiesIfExisting(playerId);
	}

	/**
	 * @return 所有实体列表,在外部不改变列表结构的情况下这个列表的内部结构不会发生变化
	 */
	public static List<Entities> getAllEntities() {
		return cm.getAllEntities();
	}
	
	/**
	 * 删除与指定玩家相关的所有缓存
	 * @param playerId
	 */
	public static void removeEntities(int playerId) {
		cm.remove(playerId);
		return;
	}

	public static class Services {
	}

	public static class Managers {
//		注册所有实体管理类
		
		public static TempletManager templetManager;
	}

	protected static ApplicationContext context;

	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public static Object getBean(String str) {
		return context.getBean(str);
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
//		初始化所有实体管理类
		
		Managers.templetManager = ctx.getBean(TempletManager.class);
	}

	// 全服的玩家Id集合
	private static ConcurrentHashMap<Integer, Boolean> GlobalPlayerIds = new ConcurrentHashMap<>();
	// 全服的玩家Name集合
	private static HashMap<String, Boolean> GlobalPlayerNames = new HashMap<>();

	/**
	 * 添加到全服玩家ID
	 * 
	 * @param playerId
	 */
	public static void addGlobalPlayerId(int playerId) {
		GlobalPlayerIds.putIfAbsent(playerId, Boolean.TRUE);
	}

	/**
	 * 检查是否存在指定玩家,全服唯一
	 * 
	 * @param playerId
	 * @return 是否存在指定玩家ID的玩家,true存在,false不存在
	 */
	public static boolean isPlayerExisting(int playerId) {
		return GlobalPlayerIds.containsKey(playerId);
	}

	/**
	 * 添加到全服玩家Name
	 * 
	 * @param playerName
	 * @return 添加是否成功,当要添加的名字已存在时添加失败,true成功,false失败
	 */
	public static boolean addGlobalPlayerName(String playerName) {
		synchronized (GlobalPlayerNames) {
			Boolean pre = GlobalPlayerNames.put(playerName, Boolean.TRUE);
			return pre == null;
		}
	}

	/**
	 * 替换名字
	 * 
	 * @param sName
	 * @param tName
	 * @return 替换名字是否成功,当要替换的名字已存在时替换失败,true成功,false失败
	 */
	public static boolean replaceGlobalPlayerName(String sName, String tName) {
		synchronized (GlobalPlayerNames) {
			Boolean pre = GlobalPlayerNames.put(tName, Boolean.TRUE);
			if (pre != null) {
				return false;
			}
			GlobalPlayerNames.remove(sName);
			return true;
		}
	}

	/**
	 * 检查是否存在指定玩家,全服唯一
	 * 
	 * @param playerName
	 * @return 是否存在指定玩家Name的玩家,true存在,false不存在
	 */
	public static boolean isPlayerExisting(String playerName) {
		synchronized (GlobalPlayerNames) {
			return GlobalPlayerNames.containsKey(playerName);
		}
	}

//	@Resource
//	PlayerDAO playerDAO;
//
//	@Override
//	public void started() {
//		/*
//		 * 每次查询10W条
//		 */
//		final int n = 100000;
//		int idn = 0;
//		for (;;) {
//			List<PlayerIdName> list = playerDAO.getIdNames(idn, n);
//			if (list.size() == 0) {
//				break;
//			}
//			for (PlayerIdName idname : list) {
//				addGlobalPlayerId(idname.getId());
//				addGlobalPlayerName(idname.getName());
//			}
//			if (list.size() < n) {
//				break;
//			}
//			idn += n;
//		}
//	}
	
	@Override
	public void started() {
		
	}
}
