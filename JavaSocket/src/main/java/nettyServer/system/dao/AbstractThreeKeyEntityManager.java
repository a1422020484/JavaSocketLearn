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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nettyServer.system.dao.commonDao.PersistenceDAO;

public abstract class AbstractThreeKeyEntityManager<M, T, K, V extends AbstractEntity<?>> extends EntityManager{

	private static Logger logger = LogManager.getLogger(AbstractThreeKeyEntityManagerM.class);
	
	protected final ConcurrentMap<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>> threeKeyMap = new ConcurrentHashMap<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>>();
	protected final ConcurrentMap<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>> deleteThreeKeyMap = new ConcurrentHashMap<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>>();

	public ConcurrentMap<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>> getMap() {
		return threeKeyMap;
	}
	
	public void notifyData(M m, T t, K k){
		MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = threeKeyMap.get(m);
		if(twoKeyMap != null){
			twoKeyMap.notifyData();
			MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
			if(map != null){
				map.notifyData();
				V v = map.get(k);
				v.notifyData();
			}
		}
	}
	
	/**
	 * 删除指定key的值,该方法是删除的m,t,k的value,容器不删除
	 * @param m
	 * @param t
	 * @param k
	 */
	public void setDeleteK(M m, T t, K k){
		MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = threeKeyMap.get(m);
		if(twoKeyMap != null){
			MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
			if(map != null){
				V v = map.remove(k);
				if(v != null){
//					if(map.isEmpty()){//容器还是不删除的好
//						twoKeyMap.remove(t);
//						if(twoKeyMap.isEmpty()){
//							threeKeyMap.remove(m);
//						}
//					}
					MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> deleteTwoKeyMap = deleteThreeKeyMap.get(m);
					if(deleteTwoKeyMap == null){
						deleteTwoKeyMap = new MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>();
						deleteThreeKeyMap.put(m, deleteTwoKeyMap);
					}
					MGConcurrentHashMap<K, V> deleteMap = deleteTwoKeyMap.get(t);
					if(deleteMap == null){
						deleteMap = new MGConcurrentHashMap<K, V>();
						deleteTwoKeyMap.put(t, deleteMap);
					}
					deleteMap.put(k, v);
				}
			}
		}
	}
	
	private void save() throws Exception{
		synchronized (this) {
			if(!deleteThreeKeyMap.isEmpty()){
				try{
					List<Object> deleteList = new ArrayList<Object>();
					for(ConcurrentMap.Entry<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>> entityM:deleteThreeKeyMap.entrySet()){
						for(ConcurrentMap.Entry<T, MGConcurrentHashMap<K, V>> entityT:entityM.getValue().entrySet()){
							for(Map.Entry<K, V> entityK:entityT.getValue().entrySet()){
								deleteList.add(entityK.getValue().getEntity());
							}
						}
					}
					PersistenceDAO.getInstantce().delete(deleteList);
				} catch (Exception e){
					logger.error(e.getMessage(),e);
					return;
				}
				deleteThreeKeyMap.clear();
			}
			
			
			if(!threeKeyMap.isEmpty()){
				List<Object> notifyList = new ArrayList<Object>();
				Map<M, Map<T, Set<K>>> notifyThreeMap = new HashMap<M, Map<T, Set<K>>>();
				for(ConcurrentMap.Entry<M, MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>>> entityM:threeKeyMap.entrySet()){
					if(entityM.getValue().isNotify()){
						Map<T, Set<K>> notifyMap = new HashMap<T, Set<K>>();
						entityM.getValue().reset();//在放入notifyList之前进行reset,如果放在后面,在執行sql和reset的中间可能还会有notifyData
						for(ConcurrentMap.Entry<T, MGConcurrentHashMap<K, V>> entityT:entityM.getValue().entrySet()){
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
						notifyThreeMap.put(entityM.getKey(), notifyMap);
					}
				}
				try {
					PersistenceDAO.getInstantce().margeData(notifyList);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
					for(Map.Entry<M, Map<T ,Set<K>>> entityM:notifyThreeMap.entrySet()){
						MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = threeKeyMap.get(entityM.getKey());
						if(twoKeyMap != null){
							twoKeyMap.notifyData();
						}
						for(Map.Entry<T, Set<K>> entityT:entityM.getValue().entrySet()){
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
				}
				checkOverdue();
			}
		}
	}
	
	protected abstract void checkOverdue();
	
	
	protected abstract V newMGInstance(M m, T t, K k);
	
	public final V getSimpleEntity(MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap, M m, T t, K k){
		MGConcurrentHashMap<K, V> map = twoKeyMap.get(t);
		if(map == null){
			map = new MGConcurrentHashMap<K, V>();
			MGConcurrentHashMap<K, V> temMap = twoKeyMap.putIfAbsent(t, map);
			map = temMap == null? map:temMap;
		}
		V v = map.get(k);
		if(v == null){
			v = newMGInstance(m, t, k);
			V temV = map.putIfAbsent(k, v);
			v = temV == null? v:temV;
		}
		return v;
	}
	
	public final V getSimpleEntity(M m, T t, K k){
		MGConcurrentHashMap<T, MGConcurrentHashMap<K, V>> twoKeyMap = threeKeyMap.get(m);
		if(twoKeyMap == null){
			return null;
		}
		MGConcurrentHashMap<K, V> map = twoKeyMap.get(m);
		if(map == null){
			map = new MGConcurrentHashMap<K, V>();
			MGConcurrentHashMap<K, V> temMap = twoKeyMap.putIfAbsent(t, map);
			map = temMap == null? map:temMap;
		}
		V v = map.get(k);
		if(v == null){
			v = newMGInstance(m, t, k);
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
	protected void delete() throws Exception{
	}
}
