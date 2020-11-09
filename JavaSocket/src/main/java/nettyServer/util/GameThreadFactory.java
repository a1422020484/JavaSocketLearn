package nettyServer.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 游戏服用线程工厂
 * 
 * @author yangxp
 */
public class GameThreadFactory implements ThreadFactory {
    private final String namePrefix;
    private final int priority;
    private final boolean daemon;
    private final AtomicInteger nextId = new AtomicInteger();

    /**
     * @param namePrefix 线程名称前缀
     */
    public GameThreadFactory(String namePrefix) {
    	this(namePrefix, 5);
    }
    
    /**
     * @param namePrefix 线程名称前缀
     * @param priority 线程优先级
     */
    public GameThreadFactory(String namePrefix, int priority) {
    	this(namePrefix, priority, false);
	}
    
    /**
     * @param namePrefix 线程名称前缀
     * @param priority 线程优先级
     * @param daemon 是否守护进程
     */
    public GameThreadFactory(String namePrefix, int priority, boolean daemon) {
        this.namePrefix = namePrefix;
        this.priority = priority;
        this.daemon = daemon;
	}

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, namePrefix + " - " + nextId.incrementAndGet());
        if (t.isDaemon()) {
            if (!daemon) {
                t.setDaemon(false);
            }
        } else {
            if (daemon) {
                t.setDaemon(true);
            }
        }
        if(t.getPriority() != priority)
            t.setPriority(priority);
        return t;
    }
}