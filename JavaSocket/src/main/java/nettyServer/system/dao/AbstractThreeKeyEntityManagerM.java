package nettyServer.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nettyServer.system.dao.commonDao.PersistenceDAO;
import nettyServer.util.Constant;

/**
 * 
 * @author 王亮亮   2016.12
 *
 * @param <T>
 * @param <K>
 * @param <V>
 */
public abstract class AbstractThreeKeyEntityManagerM<M, T, K, V extends AbstractEntity<?>> extends AbstractThreeKeyEntityManager<M, T, K, V>{
	
	private static Logger logger = LogManager.getLogger(AbstractThreeKeyEntityManagerM.class);
	
	protected abstract MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> get(M m);
	
	//如果需要在从数据库查询出来的时候做处理则Override这个方法
	protected void firstGet(M m, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> map){}
	
	public final MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> getValue(M m){
		MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> map = this.threeKeyMap.get(m);
		if(map == null){
			map = get(m);
			if(map == null){
				logger.error("[AbstractThreeKeyEntityManager] get(m) null m=" + m);
				return null;
			}
			if(threeKeyMap.putIfAbsent(m, map) == null){
				firstGet(m, map);
			}
		}
		map.setLastSelectTime((int)(System.currentTimeMillis()/1000L));
		return map;
	}

	public void save(M m){
		try {
			if(deleteThreeKeyMap.containsKey(m)){
				try{
					List<Object> deleteList = new ArrayList<Object>();
					MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> deleteTwoKeyMap = deleteThreeKeyMap.get(m);
					for(ConcurrentMap.Entry<T, MGConcurrentHashMap<K, V>> entityT:deleteTwoKeyMap.entrySet()){
						for(Map.Entry<K, V> entityK:entityT.getValue().entrySet()){
							deleteList.add(entityK.getValue().getEntity());
						}
					}
					PersistenceDAO.getInstantce().delete(deleteList);
				} catch (Exception e){
					logger.error(e.getMessage(),e);
					return;
				}
				deleteThreeKeyMap.remove(m);
			}
			
			if(threeKeyMap.containsKey(m)){
				List<Object> notifyList = new ArrayList<Object>();
				Map<T, Set<K>> notifyMap = new HashMap<T, Set<K>>();
				
				MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = threeKeyMap.get(m);
				if(twoKeyMap.isNotify()){
					twoKeyMap.reset();//在放入notifyList之前进行reset,如果放在后面,在執行sql和reset的中间可能还会有notifyData
					for(ConcurrentMap.Entry<T, MGConcurrentHashMap<K, V>> entityT:twoKeyMap.entrySet()){
						if(entityT.getValue().isNotify()){
							Set<K> setK = new HashSet<K>();
							entityT.getValue().reset();//在放入notifyList之前进行reset,如果放在后面,在執行sql和reset的中间可能还会有notifyData
							for(Map.Entry<K, V> entityK:entityT.getValue().entrySet()){
								if(entityK.getValue().isNotify()){
									entityK.getValue().reset();
									notifyList.add(entityK.getValue().getEntity());
									setK.add(entityK.getKey());
								}
							}
							notifyMap.put(entityT.getKey(), setK);
						}
					}
				}
				try {
					PersistenceDAO.getInstantce().margeData(notifyList);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
					twoKeyMap.notifyData();
					for(Map.Entry<T, Set<K>> entityT:notifyMap.entrySet()){
						MGConcurrentHashMap<K, V> map = twoKeyMap.get(entityT.getKey());
						if(map != null){
							map.notifyData();
							for(K k:entityT.getValue()){
								V v = map.get(k);
								if(v != null){
									v.notifyData();
								}
							}
						}
					}
				}
				ReadWriteLock rwlock = twoKeyMap.getRwlock();
				rwlock.writeLock().lock();
				try {
					if(twoKeyMap.getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - twoKeyMap.getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
						threeKeyMap.remove(m);
					}
				} finally {
					rwlock.writeLock().unlock();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 这个过期时间检测第一层的MGConcurrentHashMap
	 */
	protected void checkOverdue(){
		for(ConcurrentMap.Entry<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>> entityM:threeKeyMap.entrySet()){
			ReadWriteLock rwlock = entityM.getValue().getRwlock();
			rwlock.writeLock().lock();
			try {
				if(entityM.getValue().getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - entityM.getValue().getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
					threeKeyMap.remove(entityM.getKey());
				}
			} finally {
				rwlock.writeLock().unlock();
			}
		}
	}
	
}
