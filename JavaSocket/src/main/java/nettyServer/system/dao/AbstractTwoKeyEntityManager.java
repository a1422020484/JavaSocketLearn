package nettyServer.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
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
 * @Description:  
 * 				getValue(k) 获取全部  
 * 				getSimpleEntity(map,t,K) 获取map中 k 相关的数据 为null的时候new 一个新的 
 * 				notifyData(t,k) 标记为更改
 */
public abstract class AbstractTwoKeyEntityManager<T, K, V extends AbstractEntity<?>> extends EntityManager{
	
	private static Logger logger = LogManager.getLogger(AbstractTwoKeyEntityManager.class);
	
	private final ConcurrentMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = new ConcurrentHashMap<T, MGConcurrentHashMap<K, V>>();
	private final ConcurrentMap<T, MGConcurrentHashMap<K, V>> deleteTwoKeyMap = new ConcurrentHashMap<T, MGConcurrentHashMap<K, V>>();
	
	public ConcurrentMap<T, MGConcurrentHashMap<K, V>> getMap() {
		return twoKeyMap;
	}
	
	/**
	 * 删除指定key的最后的value,该方法是删除t的value
	 * @param t
	 * @param k
	 */
	public void setDeleteT(T t){
		MGConcurrentHashMap<K, V> map = twoKeyMap.remove(t);
		if(map != null){
			MGConcurrentHashMap<K, V> deleteMap = deleteTwoKeyMap.get(t);
			if(deleteMap == null){
				deleteMap = new MGConcurrentHashMap<K, V>();
				deleteTwoKeyMap.put(t, deleteMap);
			}
			deleteMap.putAll(map);
		}
	}
	
	/**
	 * 删除指定key的值,该方法是删除的t,k的value,容器不删除
	 * @param t
	 * @param k
	 */
	public void setDeleteK(T t, K k){
		MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
		if(map != null){
			V v = map.remove(k);
			if(v != null){
//				if(map.isEmpty()){//容器还是不删除的好
//					twoKeyMap.remove(t);
//				}
				MGConcurrentHashMap<K, V> deleteMap = deleteTwoKeyMap.get(t);
				if(deleteMap == null){
					deleteMap = new MGConcurrentHashMap<K, V>();
					deleteTwoKeyMap.put(t, deleteMap);
				}
				deleteMap.put(k, v);
			}
		}
	}
	
	public void notifyData(T t, K k){
		MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
		if(map != null){
			map.notifyData();
			V v = map.get(k);
			v.notifyData();
		}
	}
	
	protected abstract MGConcurrentHashMap<K, V> get(T t);
	
	//如果需要在从数据库查询出来的时候做处理则Override这个方法
	protected void firstGet(T t, MGConcurrentHashMap<K, V> map){}
	
	public final MGConcurrentHashMap<K, V> getValue(T t){
		MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
		if(map == null){
			map = get(t);
			if(map == null){
				logger.error("[AbstractTwoKeyEntityManager] get(t) null t=" + t);
				return null;
			}
			if(twoKeyMap.putIfAbsent(t, map) == null){
				firstGet(t, map);
			}
		}
		map.setLastSelectTime((int)(System.currentTimeMillis()/1000L));
		return map;
	}
	
	public void save(T t) {
		try {
			if(deleteTwoKeyMap.containsKey(t)){
				try{
					List<Object> deleteList = new ArrayList<Object>();
					MGConcurrentHashMap<K, V> deleteMap = deleteTwoKeyMap.get(t);
					for(Map.Entry<K, V> entityK:deleteMap.entrySet()){
						deleteList.add(entityK.getValue().getEntity());
					}
					PersistenceDAO.getInstantce().delete(deleteList);
				} catch (Exception e){
					logger.error(e.getMessage(), e);
					return;
				}
				deleteTwoKeyMap.remove(t);
			}
			
			if(twoKeyMap.containsKey(t)){
				List<Object> notifyList = new ArrayList<Object>();
				MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
				Set<K> setK = new HashSet<K>();
				
				if(map.isNotify()){
					map.reset();//在放入notifyList之前进行reset,如果放在后面,在執行sql和reset的中间可能还会有notifyData
					for(Map.Entry<K, V> entityK:map.entrySet()){
						if(entityK.getValue().isNotify()){
							entityK.getValue().reset();
							notifyList.add(entityK.getValue().getEntity());
							setK.add(entityK.getKey());
						}
					}
				}
				
				try {
					PersistenceDAO.getInstantce().margeData(notifyList);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
					if(map != null){
						map.notifyData();
						for(K k:setK){
							V v = map.get(k);
							if(v != null){
								v.notifyData();
							}
						}
					}
				}
				ReadWriteLock rwlock = map.getRwlock();
				rwlock.writeLock().lock();
				try {
					if(map.getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - map.getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
						twoKeyMap.remove(t);
					}
				} finally {
					rwlock.writeLock().unlock();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void save() throws Exception{
		synchronized (this) {
			if(!deleteTwoKeyMap.isEmpty()){
				try{
					List<Object> deleteList = new ArrayList<Object>();
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
				deleteTwoKeyMap.clear();
			}
			
			if(!twoKeyMap.isEmpty()){
				List<Object> notifyList = new ArrayList<Object>();
				Map<T, Set<K>> notifyMap = new HashMap<T, Set<K>>();
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
				try {
					PersistenceDAO.getInstantce().margeData(notifyList);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
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
	
	protected abstract V newMGInstance(T t, K k);
	
	public final V getSimpleEntity(MGConcurrentHashMap<K, V> map, T t, K k){
		V v = map.get(k);
		if(v == null){
			v = newMGInstance(t, k);
			V temV = map.putIfAbsent(k, v);
			v = temV == null? v:temV;
		}
		return v;
	}
	
	@Override
	public void save(ExecutorService executorService) throws Exception{//保存数据时用一个单独线程去跑,防止前一个数据的保存影响到后一个
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					save();
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
	}
	
}
