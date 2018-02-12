package nettyServer.util;

/**
 * Builder
 * 
 * @author yangxp
 */
public interface Builder<T> {
	/**
	 * 创建指定的类
	 * 
	 * @return {@link T}
	 */
	T build();
}
