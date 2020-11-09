package nettyServer.util.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import nettyServer.util.annotation.ThreadSafe;

/**
 * LRU算法实现的缓存
 * 
 * @author yangxp
 */
@ThreadSafe
public class LRUCache<K, V> {
	
	private int cacheSize;
	private LinkedHashMap<K, V> c;
	private RemoveListener listener;
	
	public LRUCache(int cacheSize) {
		this.cacheSize = cacheSize;
		this.c = new LinkedHashMap<K, V>(cacheSize, 0.75f, true) {
			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(java.util.Map.Entry<K,V> eldest) {
				boolean b = size() > LRUCache.this.cacheSize;
				if (b) {
					if (listener != null) {
						/*
						 * 此处添加异步任务注意,在异步任务执行时可能再次加载缓存,当实体从缓存移除时又会执行异步任务,造成死循环
						 */
						listener.handle(eldest.getValue());
					}
				}
				return b;
			}
		};
	}
	
	/**
	 * 设置移除监听器
	 * @param listener
	 */
	public void setListener(RemoveListener listener) {
		this.listener = listener;
	}
	
	/**
	 * 设置缓存数量最大值
	 * @param cacheSize
	 */
	public synchronized void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	
	/**
	 * @param k
	 * @param v
	 * @return V
	 * @see HashMap#put(Object, Object)
	 */
	public synchronized V put(K k, V v) {
		return c.put(k, v);
	}
	
	/**
	 * @param k
	 * @param v
	 * @return V
	 * @see ConcurrentMap#putIfAbsent(Object, Object)
	 */
	public synchronized V putIfAbsent(K k, V v) {
		if (!c.containsKey(k)) {
			return c.put(k, v);
		} else {
			return c.get(k);
		}
	}
	
	/**
	 * @param k
	 * @return V
	 * @see LinkedHashMap#get(Object)
	 */
	public synchronized V get(K k) {
		return c.get(k);
	}
	
	/**
	 * @return List<V>
	 */
	public synchronized List<V> getAll() {
		return new ArrayList<>(c.values());
	}

	/**
	 * @param k
	 * @return V
	 * @see HashMap#remove(Object)
	 */
	public synchronized V remove(K k) {
		return c.remove(k);
	}
	
	/**
	 * 移除监听器,在数据从缓存中移除时调用
	 * 
	 * @author yangxp
	 */
	interface RemoveListener {
		/**
		 * @param v 将要被删除的值
		 */
		void handle(Object v);
	}
}
