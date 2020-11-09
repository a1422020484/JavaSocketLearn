package nettyServer.util.cache;

import java.util.List;

/**
 * 
 * 
 * @author yangxp
 */
public interface EntityCacheManager {

//	void setEntity(PersistEntity entity);
	
	/**
	 * @param playerId
	 * @param clazz
	 * @return 是否存在指定类型的实体
	 */
	<T extends PersistEntity> boolean hasEntity(int playerId, Class<T> clazz);
	
	/**
	 * @param playerId
	 * @param clazz
	 * @return 获取指定类型的实体,如果缓存不存在实体,将从数据源获取,最后没有实体数据时返回null.
	 */
	<T extends PersistEntity> T getEntity(int playerId, Class<T> clazz);
	
	/**
	 * @param playerId
	 * @return 与玩家对应的实体集,如果不存在,那么创建一个并返回
	 */
	Entities getEntities(int playerId);
	
	/**
	 * @param playerId
	 * @return 与玩家对应的实体集,如果不存在,那么返回null
	 */
	Entities getEntitiesIfExisting(int playerId);
	
	/**
	 * 删除与指定玩家相关的所有缓存
	 * @param playerId
	 */
	void remove(int playerId);
	
//	<T extends PersistEntity> void remove(int playerId, Class<T> clazz);
	
	/**
	 * @return 所有实体列表,在外部不改变列表结构的情况下这个列表的内部结构不会发生变化
	 */
	List<Entities> getAllEntities();
}
