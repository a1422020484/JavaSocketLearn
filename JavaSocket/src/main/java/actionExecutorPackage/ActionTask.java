package actionExecutorPackage;

/**
 * Action任务
 * 
 */
public interface ActionTask extends Runnable {

	/**
	 * @return 玩家ID,不能为null并且必须大于0.
	 */
	Integer getPlayerId();

	/**
	 * 任务执行具体内容
	 * @see Runnable
	 */
	void run();
}
