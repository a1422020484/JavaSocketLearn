package nettyServer.util.timer;

/**
 * 定时处理器
 * 
 * @author yangxp
 */
public interface TimerHandler {
	/**
	 * 定时器的处理细节
	 * 
	 * @param timerRuleTime 根据时间规则计算出的当次执行时间点(毫秒)
	 * @throws Exception
	 */
	void handle(long timerRuleTime) throws Exception;
	
}
