package nettyServer.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * key重复时值累加的HashMap
 * 
 * @param <K>
 * @param <V>
 *
 * @author yangxp
 */
public class AccumulativeHashMap<K, V extends Number> extends HashMap<K, V> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	public AccumulativeHashMap() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * @param initialCapacity
	 */
	public AccumulativeHashMap(int initialCapacity) {
		super(initialCapacity);
	}
	
	/**
	 * {@inheritDoc}
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public AccumulativeHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	/**
	 * {@inheritDoc}
	 * @param m
	 */
	public AccumulativeHashMap(Map<? extends K, ? extends V> m) {
		super(m);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		V v =  super.put(key, value);
		if (v != null) {
			if (v instanceof Integer)
				super.put(key, (V) Integer.valueOf(value.intValue() + v.intValue()));
			else if (v instanceof Long)
				super.put(key, (V) Long.valueOf(value.longValue() + v.longValue()));
			else if (v instanceof Float)
				super.put(key, (V) Float.valueOf(value.floatValue() + v.floatValue()));
			else if (v instanceof Double)
				super.put(key, (V) Double.valueOf(value.doubleValue() + v.doubleValue()));
			else if (v instanceof Byte)
				super.put(key, (V) Byte.valueOf((byte) (value.byteValue() + v.byteValue())));
			else if (v instanceof Short)
				super.put(key, (V) Short.valueOf((short) (value.shortValue() + v.shortValue())));
			else if (v instanceof AtomicInteger)
				((AtomicInteger) value).addAndGet(((AtomicInteger) v).get());
			else if (v instanceof AtomicLong)
				((AtomicLong) value).addAndGet(((AtomicLong) v).get());
			else if (v instanceof BigInteger)
				super.put(key, (V) ((BigInteger) value).add(((BigInteger) v)));
			else if (v instanceof BigDecimal)
				super.put(key, (V) ((BigDecimal) value).add(((BigDecimal) v)));
		}
		return v;
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> en : m.entrySet()) {
			put(en.getKey(), en.getValue());
		}
	}
}
