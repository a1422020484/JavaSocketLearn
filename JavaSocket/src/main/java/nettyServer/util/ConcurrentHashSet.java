package nettyServer.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author yangxp
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements
		Serializable {
	private static final long serialVersionUID = 764221314243971928L;

	private transient ConcurrentHashMap<E, Object> map;
	
	private static final Object PRESENT = new Object();

	public ConcurrentHashSet() {
		map = new ConcurrentHashMap<>();
	}
	
	/**
	 * @param initialCapacity the initial capacity of the hash map
	 */
	public ConcurrentHashSet(int initialCapacity) {
		map = new ConcurrentHashMap<>(initialCapacity);
	}
	
	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	@Override
	public boolean add(E e) {
		map.putIfAbsent(e, PRESENT);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}

	@Override
	public boolean remove(Object o) {
		return map.remove(o, PRESENT);
	}

	@Override
	public void clear() {
		map.clear();
	}

	/**
	 * @see {@link ConcurrentHashMap#keySet()}.iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	@Override
	public int size() {
		return map.size();
	}

}
