package nettyServer.util;

/**
 * Builder
 * 
 * @author xiezuojie
 */
public interface Builder<T> {
	/**
	 * 创建指定的类
	 * 
	 * @return {@link T}
	 */
	T build();
}
