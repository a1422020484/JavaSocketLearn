package SyncFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class PkSessionManager {

    public static PkSessionManager manager = new PkSessionManager();

    private static Logger log = LogManager.getLogger("PkSessionManager");
    /**
     * 单线程定时驱动
     */
    private ScheduledExecutorService sec = Executors.newSingleThreadScheduledExecutor();
    /**
     * 处理帧逻辑的线程池
     */
    private ExecutorService es = Executors.newFixedThreadPool(30);

    private Map<Integer, PkSession> pkSessionMap = new ConcurrentHashMap<Integer, PkSession>();

    {
        sec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    runFrame();
                } catch (Throwable th) {
                    log.error("pkSessionManager.runFrame|error", th);
                }

            }
        }, 33000, 33000, TimeUnit.MICROSECONDS);// 每秒30帧驱动,客户端也设置相同帧率
    }

    /**
     * 对所有战斗会话进行服务器帧驱动
     */
    private void runFrame() {
        final long now = System.currentTimeMillis();
        for (final PkSession pkSession : pkSessionMap.values()) {
            es.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 一场战斗不可能超过20分钟
                        if (now - pkSession.getCreateTime() > 1000L * 60 * 30) {
                            pkSessionMap.remove(pkSession.getSessionId());
                            log.warn("pkSession|timeout|for|" + pkSession);
                        }
                        pkSession.runFrame();
                    } catch (Throwable th) {
                        log.error("pkSession.runFrame|error|sessionId"+pkSession.getSessionId(), th);
                    }
                }
            });
        }
    }

    public PkSession startPkSession(List<PkPlayer> pkPlayers) {
        //TODO 根据自己的规则生成sessionId，这里为了演示随便生成一个
        int sessionId = (int)System.currentTimeMillis();
        PkSession pkSession = new PkSession(sessionId);
        pkSession.setPkPlayers(pkPlayers);
        pkSessionMap.put(pkSession.getSessionId(), pkSession);
        log.info("startPkSession|" + sessionId + "|" + pkSession);
        return pkSession;
    }

    public boolean stopPkSession(int sessionId) {
        PkSession pkSession = getPkSession(sessionId);
        if (pkSession != null) {
            pkSession.stopSession();
            pkSessionMap.remove(sessionId);
        }
        return false;
    }

    public PkSession getPkSession(int sessionId) {
        return pkSessionMap.get(sessionId);
    }
}
