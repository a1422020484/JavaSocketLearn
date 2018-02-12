package nettyServer.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import nettyServer.dispatch.annotation.Action;
import nettyServer.dispatch.annotation.Action.User;
import nettyServer.util.CoreConfig;

/**
 * Action任务服务 <br>
 * 任务队列编号: <li>10001: 登录操作队列,{@link Action#isLogon()}为true.</li> <li>10002:
 * 系统操作队列,{@link Action#user()}为{@link User#System}.</li> <li>10003:
 * 其它操作队列,指不需要登录并且不属于登录操作和系统操作之外的其它操作,例如获取系统时间.</li>
 * 
 * 
 * @author yangxp
 */
public final class ActionTaskService {


	// 在Action.isLogin==true时使用
	final static int loginThreadsNum = CoreConfig.intValue("LoginThreadsNum");
	private static ActionExecutor<ActionTask> loginExecutor = new ActionExecutor<>(10001, loginThreadsNum);
	
	// 在Action.user==User.System时使用
	final static int systemThreadsNum = CoreConfig.intValue("SystemThreadsNum");
	private static ActionExecutor<ActionTask> systemExecutor = new ActionExecutor<>(10002, systemThreadsNum);
	// 在Action.user==User.Player并且Action.isLogin==false并且playerId==null时使用
	private static ActionExecutor<ActionTask> otherExecutor = new ActionExecutor<>(10003);
	// 在Action.isCreatePlayer==true时使用
	private static ActionExecutor<ActionTask> createPlayerExecutor = new ActionExecutor<>(10004);
	// 玩家登录后操作队列列表
	private static List<ActionExecutor<ActionTask>> executors = new ArrayList<>();
	// 推荐使用的玩家登录后操作列表
	private static volatile ActionExecutor<ActionTask> recommendExecutor = null;

	private static ConcurrentHashMap<Integer, ActionExecutor<ActionTask>> executorMap;
	static {
		final int businessThreadsNum = CoreConfig.intValue("BusinessThreadsNum");
		final int num = businessThreadsNum > 0 ? businessThreadsNum : Runtime.getRuntime().availableProcessors() * 2;

		executorMap = new ConcurrentHashMap<>(2048);

		for (int i = 0; i < num; i++) {
			executors.add(new ActionExecutor<ActionTask>(i));
		}
		
		recommendExecutor = executors.get(0);
		executorMap.put(-10001, loginExecutor);
		executorMap.put(-10002, systemExecutor);
		executorMap.put(-10003, otherExecutor);
		executorMap.put(-10004, createPlayerExecutor);
	}
	
	/**
	 * 服务是否停止
	 */
	private static AtomicBoolean isClose = new AtomicBoolean(false);
	private static boolean isStartHandleLastTasks = false;
	/**
	 * 在服务器停止时,保存由ActionExecutor由于停止而无法处理的任务
	 * 当ActionExecutor调用shutdown()后,剩下的任务还要继续完成,在这些任务完成中可能继续提交任务,这时新任务将被拒绝,
	 * 解决此问题,将新任务放后此队列,最后处理
	 */
	private static Queue<ActionTask> lastTasks = new ConcurrentLinkedQueue<>();
	
	static void exec(ActionTask actionTask) {
		Integer pId = actionTask.getPlayerId();
		if (pId == null) {
			throw new NullPointerException("actionTask.getPlayerId()不能为null!");
		}

		ActionExecutor<ActionTask> executor = executorMap.get(pId);
		if (executor == null) {
			executor = recommendExecutor;
			ActionExecutor<ActionTask> oldExecutor = executorMap.putIfAbsent(pId, executor);
			if (oldExecutor != null) {
				executor = oldExecutor;
			} else {
				executor.inc();
				findRecommendExecutor();
			}
		}
		executor.execute(actionTask);
	}
	
	/**
	 * 提交一个任务到与玩家ID相关联的任务队列,在没有查找到与任务相关联的队列时,框架将任务加入到推荐的队列中
	 * <br>
	 * <b>在玩家之间要互相操作数据时,用此方法来实现异步操作,避免并发导致的数据异常问题</b>
	 *
	 * @param actionTask
	 */
	public static void submit(ActionTask actionTask) {
		if (isClose.get()) {
			if (isStartHandleLastTasks) {
				// 队列已关闭入口
				throw new RuntimeException("任务处理服务已关闭,无法继续处理新任务!");
			}
			lastTasks.add(actionTask);
		} else {
			exec(new ActionFilterTask(actionTask));
		}
	}

	/**
	 * 删除指定的玩家Id与队列的关联
	 *
	 * @param playerId
	 */
	static void remove(Integer playerId) {
		ActionExecutor<ActionTask> executor = executorMap.remove(playerId);
		if (executor != null) {
			executor.dec();
			findRecommendExecutor();
		}
	}

	/**
	 * 找出一个推荐优先使用的队列
	 */
	static void findRecommendExecutor() {
		int old = recommendExecutor.count();
		ActionExecutor<ActionTask> found = null;
		for (ActionExecutor<ActionTask> ex : executors) {
			int c = ex.count();
			if (c < old) {
				old = c;
				found = ex;
			}
		}
		if (found != null) {
			recommendExecutor = found;
		}
	}
	
	private static void handleLastTasks() {
		isStartHandleLastTasks = true;
		ActionTask task = null;
		while ((task = lastTasks.poll()) != null) {
			try {
				task.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 停止服务
	 */
	public static void stop() {
		isClose.set(true);
		loginExecutor.stop();
		systemExecutor.stop();
		otherExecutor.stop();
		for (ActionExecutor<ActionTask> exec : executors) {
			exec.stop();
		}
		
		handleLastTasks();
	}
}
