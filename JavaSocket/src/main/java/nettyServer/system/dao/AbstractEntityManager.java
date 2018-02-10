package nettyServer.system.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 * @param <K>
 * @param <V>
 */
public abstract class AbstractEntityManager<K,V extends AbstractEntity<?>> extends EntityManager{
	
	private static Logger logger = LogManager.getLogger(AbstractEntityManager.class);
	
	private final ConcurrentMap<K, V> map = new ConcurrentHashMap<K, V>();
	private final ConcurrentMap<K, V> deleteMap = new ConcurrentHashMap<K, V>();
	
	public ConcurrentMap<K, V> getMap() {
		return map;
	}
	
	/**
	 * 删除指定key的最后的value,该方法是删除k的value
	 * @param k
	 */
	public void setDelete(K k){
		V v = map.remove(k);
		if(v != null){
			deleteMap.put(k, v);
		}
	}
	
	protected abstract V get(K k);
	
	public V getValue(K k){
		V v = map.get(k);
		if(v == null){
			v = get(k);
			if(v == null){
				logger.error("[AbstractEntityManager] get(k) null k=" + k);
				return null;
			}
			map.putIfAbsent(k, v);
		}
		v.setLastSelectTime((int)(System.currentTimeMillis()/1000L));
		return v;
	}
	
	private boolean deleteK(K k) throws Exception{
		if(deleteMap.containsKey(k)){
			try{
				V v = deleteMap.get(k);
				List<Object> deleteList = new ArrayList<Object>();
				deleteList.add(v.getEntity());
				PersistenceDAO.getInstantce().delete(deleteList);
			} catch (Exception e){
				logger.error(e.getMessage(),e);
				return false;
			}
			deleteMap.remove(k);
		}
		return true;
	}
	
	private void updateK(K k) throws Exception{
		if(map.containsKey(k)){
			V v = map.get(k);
			List<Object> notifyList = new ArrayList<Object>();
			if(v.isNotify()){
				v.reset();//在放入notifyList之前进行reset,在執行sql和reset的中间可能还会有notifyData,放在前面的话,可以把这部分数据留到下次执行,如果放在后面的话,这部分数据更改就会丢失
				notifyList.add(v.getEntity());
			}
			try {
				PersistenceDAO.getInstantce().margeData(notifyList);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
				v.notifyData();
			}
			ReadWriteLock rwlock = v.getRwlock();
			rwlock.writeLock().lock();
			try {
				if(v.getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - v.getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
					//这边加上写锁是为了防止在这个位置产生 entity.getValue().setLastSelectTime(xxx) 的操作
					map.remove(k);
				}
			} finally {
				rwlock.writeLock().unlock();
			}
		}
	}
	
	public void save(K k) {
		try {
			//delete
			if(!deleteK(k)){
				return;
			}
			//update
			updateK(k);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void save() throws Exception{
		synchronized (this) {
			if(!deleteMap.isEmpty()){
				try{
					List<Object> deleteList = new ArrayList<Object>();
					for(ConcurrentMap.Entry<K,V> entity:deleteMap.entrySet()){
						deleteList.add(entity.getValue().getEntity());
					}
					PersistenceDAO.getInstantce().delete(deleteList);
				} catch (Exception e){
					logger.error(e.getMessage(),e);
					return;
				}
				deleteMap.clear();
			}
			
			if(!map.isEmpty()){
				List<Object> notifyList = new ArrayList<Object>();
				Set<K> setK = new HashSet<K>();
				for(ConcurrentMap.Entry<K,V> entity:map.entrySet()){
					if(entity.getValue().isNotify()){
						entity.getValue().reset();//在放入notifyList之前进行reset,如果放在后面,在執行sql和reset的中间可能还会有notifyData
						notifyList.add(entity.getValue().getEntity());
						setK.add(entity.getKey());
					}
				}
				try {
					PersistenceDAO.getInstantce().margeData(notifyList);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
					for(K k:setK){
						V v = map.get(k);
						if(v != null){
							v.notifyData();
						}
					}
				}
				for(ConcurrentMap.Entry<K,V> entity:map.entrySet()){
					ReadWriteLock rwlock = entity.getValue().getRwlock();
					rwlock.writeLock().lock();
					try {
						if(entity.getValue().getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - entity.getValue().getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
							//这边加上写锁是为了防止在这个位置产生 entity.getValue().setLastSelectTime(xxx) 的操作
							map.remove(entity.getKey());
						}
					} finally {
						rwlock.writeLock().unlock();
					}
				}
			}
		}
	}
	
	@Override
	public void save(ExecutorService executorService) throws Exception{//保存数据时用一个单独线程去跑,防止前一个数据的保存影响到后一个
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					save();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}
}
