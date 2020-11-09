package nettyServer.util;


/**
 * 玩家数据初始化,在玩家数据创建或加载完成后调用,供其它模块创建初始化数据
 * 
 * @author yangxp
 */
public interface PlayerDataInitializer {
	/**
	 * 在玩家数据创建或加载完成后调用,检查模块数据是否存在,或是否需要更新,按需要进行创建和更新
	 * @param playerId
	 */
	void init(int playerId);
}
