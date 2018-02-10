package nettyServer.system.dao;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nettyServer.util.Constant;

public abstract class AbstractThreeKeyEntityManagerMT<M, T, K, V extends AbstractEntity<?>> extends AbstractThreeKeyEntityManager<M, T, K, V>{

	private static Logger logger = LogManager.getLogger(AbstractThreeKeyEntityManagerMT.class);
	
	//如果需要在从数据库查询出来的时候做处理则Override这个方法
	protected void firstGetMT(M m, T t, MGConcurrentHashMap<K, V> map){}
	
	protected abstract MGConcurrentHashMap<K, V> get(M m, T t);
	
	public final MGConcurrentHashMap<K, V> getValue(M m, T t){
		MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = this.threeKeyMap.get(m);
		if(twoKeyMap == null){
			twoKeyMap = new MGConcurrentHashMap<>();
			MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> temMap = this.threeKeyMap.putIfAbsent(m, twoKeyMap);
			twoKeyMap = temMap == null? twoKeyMap:temMap;
		}
		MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
		if(map == null){
			map = get(m, t);
			if(map == null){
				logger.error("[AbstractThreeKeyEntityManager] get(m) null m=" + m);
				return null;
			}
			MGConcurrentHashMap<K, V> temMap = twoKeyMap.putIfAbsent(t, map);
			map = temMap == null? map:temMap;
			if(threeKeyMap.putIfAbsent(m, twoKeyMap) == null){
				firstGetMT(m, t, map);
			}
		}
		int now = (int)(System.currentTimeMillis()/1000L);
		map.setLastSelectTime(now);
		return map;
	}
	
	/**
	 * 这个过期时间只检测第二层的MGConcurrentHashMap
	 */
	protected void checkOverdue(){
		for(ConcurrentMap.Entry<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>> entityM:threeKeyMap.entrySet()){
			MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = entityM.getValue();
			for(ConcurrentMap.Entry<T, MGConcurrentHashMap<K, V>> entityT:twoKeyMap.entrySet()){
				ReadWriteLock rwlock = entityT.getValue().getRwlock();
				rwlock.writeLock().lock();
				try {
					if(entityT.getValue().getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - entityT.getValue().getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
						twoKeyMap.remove(entityT.getKey());
					}
				} finally {
					rwlock.writeLock().unlock();
				}
			}
		}
	}
	
}
