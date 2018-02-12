package nettyServer.util.cache;

import nettyServer.util.CoreConfig;


/**
 * 
 * 
 * @author yangxp
 */
public final class CachedDataPersistStrategy {

	/**
	 * 保存策略:
	 * <br>
	 * <li>1.立即保存,在一个Action执行完成后立即更新所有更新过的数据,在自己线程内执行,适合调试时使用.</li>
	 * <li>2.每隔一段时间定时执行保存,在其它线程执行,适合运行时使用.</li>
	 */
	public static volatile int strategy = CoreConfig.intValue("DefaultCachedDataPersistStrategy");
}
