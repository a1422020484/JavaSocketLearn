package nettyServer.util.cache;

import java.util.List;

import nettyServer.util.CoreConfig;

/**
 * 缓存管理器,多线程下可安全使用
 * 
 * @author yangxp
 */
public final class MemoryCacheManager implements EntityCacheManager {

	LRUCache<Integer, Entities> c = new LRUCache<>(CoreConfig.intValue("CachedPlayerNums"));

	public MemoryCacheManager() {
		c.setListener((v) -> {
            Entities en = (Entities) v;
            CachedDataPersistService.addToUnsaveQueue(en);
        });
	}

//	/**
//	 * @param entity
//	 *            缓存实体
//	 * @throws NullPointerException
//	 *             当参数entity为null时抛出异常.
//	 */
//	public void setEntity(PersistEntity entity) throws NullPointerException {
//		if (entity == null) {
//			throw new NullPointerException();
//		}
//		int pId = entity.getPlayerId();
//		Entities en = getEntities(pId);
//		en.setEntity(entity);
//	}

	@Override
	public <T extends PersistEntity> boolean hasEntity(int playerId, Class<T> clazz) {
		Entities en = c.get(playerId);
		if (en == null) {
			return false;	//不存在实体直接返回false
		}
		// Entities en = getEntities(playerId);
		return en.hasEntity(clazz);
	}

	/**
	 * @param playerId
	 * @param clazz
	 * @return 缓存实体,当从缓存获取的实体为null时,会执行{@link CachedDataManager#initCache(int)}加载缓存并返回.
	 */
	public <T extends PersistEntity> T getEntity(int playerId, Class<T> clazz) {
		Entities en = getEntities(playerId);
		return en.getEntity(clazz);
	}

//	@Override
//	public <T extends PersistEntity> void remove(int playerId, Class<T> clazz) {
//		Entities en = getEntities(playerId);
//		en.remove(clazz);
//	}

	/**
	 * @param playerId
	 * @return 缓存实体集合
	 */
	public Entities getEntities(int playerId) {
		Entities en = c.get(playerId);
		if (en == null) {
			synchronized (this) {
				en = CachedDataPersistService.takeFromUnsaveQueue(playerId);
				if (en == null) {
					en = new Entities(playerId);
				}
				Entities old = c.putIfAbsent(playerId, en);
				if (old != null) {
					en = old;
				}
			}
		}
		return en;
	}
	
	@Override
	public Entities getEntitiesIfExisting(int playerId) {
		Entities en = c.get(playerId);
		return en;
	}

	@Override
	public List<Entities> getAllEntities() {
		return c.getAll();
	}

	@Override
	public void remove(int playerId) {
		Entities en = c.remove(playerId);
		if (en != null) {
			CachedDataPersistService.addToUnsaveQueue(en);
		}
	}

}
