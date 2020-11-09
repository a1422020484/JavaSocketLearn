package nettyServer.dispatch;

import nettyServer.util.annotation.ThreadSafe;

/**
 * Request引用保存在线程本地变量中,提供更简单的方式设置和获取request
 *
 * @author yangxp
 */
@ThreadSafe
public class ThreadLocalRequest {
	private final ThreadLocal<Request> local = new ThreadLocal<Request>();

	private static class ThreadLocalRequestHolder {
		private static ThreadLocalRequest localRequest = new ThreadLocalRequest();
	}

	private ThreadLocalRequest() {
	}

	/**
	 * @return {@link ThreadLocalRequest}实例
	 */
	private static ThreadLocalRequest instance() {
		return ThreadLocalRequestHolder.localRequest;
	}

	/**
	 * 设置Request引用到线程本地变量
	 *
	 * @param request
	 */
	public static void set(Request request) {
		getThreadLocal().set(request);
	}

	/**
	 * @return 线程本地变量中获取Request引用
	 */
	public static Request get() {
		return getThreadLocal().get();
	}

	/**
	 * 线程本地变量中删除Request引用
	 */
	public static void remove() {
		getThreadLocal().remove();
	}

	/**
	 * @return {@link ThreadLocal}
	 */
	private static ThreadLocal<Request> getThreadLocal() {
		return instance().local;
	}
}
