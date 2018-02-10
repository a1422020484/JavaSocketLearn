package nettyServer.system.dao;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author 王亮亮   2017.5
 *
 * @param <K>
 * @param <V>
 */
public class AbstractDoubleEntity<K, V extends AbstractEntity<?>> extends Entity{
	private static final Logger logger = LogManager.getLogger(AbstractDoubleEntity.class);
	
	//数据缓存
	private final ConcurrentMap<K, V> map = new ConcurrentHashMap<K, V>();
	//数据缓存备份,用于异常恢复
	private final ConcurrentMap<K, V> mapBak = new ConcurrentHashMap<K, V>();
	//需要被删除的数据
	private final ConcurrentMap<K, V> deleteMap = new ConcurrentHashMap<K, V>();
	//被删除数据的备份,用于异常恢复
	private final ConcurrentMap<K, V> deleteMapBak = new ConcurrentHashMap<K, V>();
	
	public void setDeleteK(K k){
		V v = map.remove(k);
		if(v != null){
			deleteMap.put(k, v);
		}
	}
	
//	public void notifyData(K k){
//		V v = map.get(k);
//		v.notifyData();
//	}
	
	/**
	 * 被删除整个AbstractDoubleEntity
	 * @param deleteList
	 * @throws Exception
	 */
	public void getAllDelete(List<Object> deleteList){
		try {
			for(Entry<K, V> entry:map.entrySet()){
				deleteList.add(entry.getValue().getEntity());
			}
			for(Entry<K, V> entry:deleteMap.entrySet()){
				deleteList.add(entry.getValue().getEntity());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * AbstractDoubleEntity中被删除的部分
	 * @param deleteList
	 * @throws Exception
	 */
	public void getDelete(List<Object> deleteList){
		try {
			for(Entry<K, V> entry:deleteMap.entrySet()){
				deleteList.add(entry.getValue().getEntity());
			}
			deleteMapBak.clear();
			deleteMapBak.putAll(deleteMap);
			deleteMap.clear();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 获取被更改的数据
	 */
	public void getNotifyData(List<Object> notifyList){
		try {
			if(isNotify()){
				this.reset();//在放入notifyList之前进行reset,如果放在后面,在執行sql和reset的中间可能还会有notifyData
				mapBak.clear();
				for(Entry<K, V> entityK:map.entrySet()){
					V v = entityK.getValue();
					if(v.isNotify()){
						v.reset();
						notifyList.add(v.getEntity());
						mapBak.put(entityK.getKey(), v);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 用于恢复删除异常
	 */
	public void recoverDelete(){
		deleteMap.putAll(deleteMapBak);
		deleteMapBak.clear();
	}
	
	/**
	 * 用于恢复更改数据异常
	 */
	public void recoverNotifyData(){
		this.notifyData();
		int now = (int)(System.currentTimeMillis()/1000L);
		//恢复异常的时候顺便更改下过期时间,防止错误次数过多,时间过长,导致数据被清掉
		setLastSelectTime(now);
		for(Entry<K, V> entityK:mapBak.entrySet()){
			V v = map.get(entityK.getKey());
			if(v != null){
				v.notifyData();
			}
		}
		mapBak.clear();
	}

	public ConcurrentMap<K, V> getMap() {
		return map;
	}

	@Override
	public String toString() {
		return "AbstractDoubleEntity []" + this;
	}
	
}
