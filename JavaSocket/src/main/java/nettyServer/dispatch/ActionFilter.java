package nettyServer.dispatch;

/**
 * 
 * 
 * @author xiezuojie
 */
public interface ActionFilter {
	
	/**
	 * 在Action执行之前
	 * @param req 来自玩家的请求,如果是服务端提交的异步任务,那么req为null.
	 */
	public void before(Request req);
	
	/**
	 * 在Action执行之后调用
	 * <br>
	 * <b>注意:在after中有可能再次提交新任务Action,在新任务执行完成后仍然会执行after,此时应该仔细检查是否会引起死循环!</b>
	 * 
	 * @param playerId 此次Action操作关联的玩家id
	 * @param req 来自玩家的请求,如果是服务端提交的异步任务,那么req和resp为null.
	 * @param resp Action返回的结果,结果有可能是null.
	 */
	public void after(Integer playerId, Request req, Object resp);
}
