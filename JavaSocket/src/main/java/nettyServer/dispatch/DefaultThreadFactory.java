package nettyServer.dispatch;

import java.util.concurrent.ThreadFactory;

import nettyServer.util.CoreConfig;

public class DefaultThreadFactory implements ThreadFactory {
	private final ThreadGroup group;
	private final String namePrefix;
	private final int priority = CoreConfig.intValue("ActionTaskExecutorThreadPriority");

	DefaultThreadFactory(int id) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "action thread pool-" + id;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix, 0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != priority)
			t.setPriority(priority);
		return t;
	}
}
