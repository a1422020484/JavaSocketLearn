package SyncFrame;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PkSession {
    private static Logger log = LogManager.getLogger("PkSession");
    /**
     * 战斗的会话id
     */
    private int sessionId = 0;
    /**
     * 战斗状态，0是初始等待状态,1是战斗中，2,是战斗正常结束,3是战斗异常结束
     */
    private int pkState = 0;
    /**
     * 创建时间
     */
    private long createTime = System.currentTimeMillis();
    /**
     * 第一个玩家连上开始等待其他玩家的时间
     */
    private long startWaitTime = 0;
    /**
     * 战斗开始时间
     */
    private long startTime = 0;
    /**
     * 所有玩家信息
     */
    List<PkPlayer> pkPlayers = new ArrayList<PkPlayer>();
    /**
     * 掉线玩家的AI挂靠
     */
    private List<Framesync.StringStringKeyValue> playerAI = new ArrayList<Framesync.StringStringKeyValue>();
    /**
     * 小怪的AI挂靠
     */
    private List<Framesync.IntStringKeyValue> npcAI = new ArrayList<Framesync.IntStringKeyValue>();
    /**
     * 帧同步数据
     */
    private Map<Integer, Framesync.FrameSyncDataArray> fsdaMap = new ConcurrentHashMap<Integer, Framesync.FrameSyncDataArray>();
    /**
     * 帧序号
     */
    private AtomicInteger serverFrameSeq = new AtomicInteger();
    /**
     * 上一帧的运行的时间
     */
    private long preFrameTime = 0;
    /**
     * 结束帧序号
     */
    private AtomicInteger endFrameIndex = new AtomicInteger(0);
    /**
     * 准备帧
     */
    Framesync.FrameSyncDataArray waitFrame =  Framesync.FrameSyncDataArray.newBuilder().setPkSessionId(sessionId).setFrameIndex(0).build();
    /**
     * 合并的操作队列
     */
    private ArrayBlockingQueue<Framesync.FrameSyncDataArray> cachedOpList = new ArrayBlockingQueue<Framesync.FrameSyncDataArray>(500);

    /**
     * 等待第一个人连入的时间（秒）
     */
    public static int FIRST_JOIN_WAIT_TIME = 120;
    /**
     * 第一个人连入后，等待其他人连入的时间（秒）
     */
    public static int OTHER_JOIN_WAIT_TIME = 30;
    /**
     * 掉线等待时间（秒）
     */
    public static int OFFLINE_WAIT_TIME = 60;
    /**
     * 转AI时间（秒）
     */
    public static int CHANGE_AI_TIME = 5;

    public PkSession(int sessionId) {
        super();
        this.sessionId = sessionId;
    }

    public List<PkPlayer> getPkPlayers() {
        return pkPlayers;
    }


    public void setPkPlayers(List<PkPlayer> pkPlayers) {
        this.pkPlayers = pkPlayers;
    }

    public int getSessionId() {
        return sessionId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public boolean stopSession() {
        log.info(this.sessionId + "|stopSession|");
        pkState = 2;
        return true;
    }

    /**
     * runFrame是否正在运行
     */
    private AtomicBoolean runningFlag = new AtomicBoolean(false);

    public void runFrame() {
        try {
            if (runningFlag.get()) {
                log.warn(sessionId + "|runFrame is running|frameIndex|" + serverFrameSeq.get());
                return;
            }
            runningFlag.set(true);
            long startTime = System.currentTimeMillis();
            try{
                doRunFrame();
            }catch(Throwable t){
                log.error(sessionId + "|doRunFrame|err|frameIndex|" + serverFrameSeq.get(), t);
            }finally{
                runningFlag.set(false);
            }
            long endTime = System.currentTimeMillis();
            log.info(sessionId + "|runFrame|useTime|" + (endTime - startTime) + "ms|frameIndex|" + serverFrameSeq.get());
        } catch (Throwable e) {
            log.error(sessionId + "|runFrame|err|frameIndex|" + serverFrameSeq.get(), e);
        }
    }


    public void doRunFrame() {
        long now = System.currentTimeMillis();
        int minLastSyncFrameIndex = getMinLastSyncFrameIndex();
        log.info(sessionId + "|" + Thread.currentThread() + "|开始主动同步帧|最小同步帧id是"+ minLastSyncFrameIndex + "|serverFrameSeq:" + serverFrameSeq);

        if (pkState > 1) {// 已经结束
            log.error(sessionId + "|" + Thread.currentThread() + "|" + serverFrameSeq + "pkend,pkState=" + pkState + "|so|stopSession");
            PkSessionManager.manager.stopPkSession(sessionId);
            return;
        }
        if (pkState==0&&!isAnyOnePlayerConnected()) {
            if (now - createTime > PkSession.FIRST_JOIN_WAIT_TIME * 1000L) {
                log.error(sessionId + "|isAnyOnePlayerConnected|超过" + PkSession.FIRST_JOIN_WAIT_TIME + "秒等待时间没任何人连上");
                pkState = 3;
            }
            log.warn(sessionId + "|isAnyOnePlayerConnected==false" + pkPlayers);
            return;
        }
        if (pkState==0&&!isAllPlayerConnected(now)) {
            // 给所有连上的准备好的玩家发等待帧
            if (!waitFrame.getSyncsList().isEmpty()) {
                for (PkPlayer pkPlayer : pkPlayers) {
                    if (pkPlayer.getReadyTime() > 0 && pkPlayer.getOffLineTime() <= 0) {
                        pkPlayer.send(waitFrame);
                    }
                }
            }
            log.warn(sessionId + "|isAllPlayerConnected==false" + pkPlayers);
            cachedOpList = new ArrayBlockingQueue<Framesync.FrameSyncDataArray>(500);
            return;
        }
        if (pkState==0&&!isAllPlayerReady(now)) {
            if (now - startWaitTime > PkSession.OTHER_JOIN_WAIT_TIME * 1000L) {
                log.error(sessionId + "|isAllPlayerReady|超过" + PkSession.OTHER_JOIN_WAIT_TIME + "秒等待时间没任何人准备");
                pkState = 3;
            }
            log.warn(sessionId + "|isAllPlayerReady=false" + pkPlayers);
            cachedOpList = new ArrayBlockingQueue<Framesync.FrameSyncDataArray>(500);
            return;
        }
        // 大家已经就绪，且未标记为可以开始战斗，则插入start命令
        if (startTime == 0) {
            // 构造所有玩家的开始战斗的命令字信息
            cachedOpList = new ArrayBlockingQueue<Framesync.FrameSyncDataArray>(500);
            cachedOpList.add(waitFrame);// 所有人发一次等待帧，否则之前的逻辑最后一个人没收到过等待帧
            for (PkPlayer pkPlayer : pkPlayers) {
                Framesync.FrameSyncDataArray.Builder fsdab = Framesync.FrameSyncDataArray.newBuilder();
                Framesync.SyncMechaInfo smi = Framesync.SyncMechaInfo.newBuilder().setZoneId(pkPlayer.getZoneId())
                        .setPlayerId(pkPlayer.getPlayerId()).build();
                fsdab.setFrameIndex(0).setPkSessionId(sessionId).setSyncObj(smi);
                FrameSyncData fsd = FrameSyncDataUtil.getFrameSyncData(
                        FrameSyncStartData.newBuilder().setPosition(UnityVector3.newBuilder().setX(0).setY(0).setZ(0)));
                fsdab.addSyncs(fsd);
                cachedOpList.add(fsdab.build());
            }
            startTime = now;
            // 分配AI
            assignAI();
            log.info(sessionId + "|pkstart");
            pkState = 1;
        }
        if (isAllPlayerOffLine(now)) {
            log.error(sessionId + "|isAllPlayerOffLine|");
            pkState = 3;
            return;
        }
        if (isAllPlayerEnd()) {
            if (endFrameIndex.get() == 0) {
                // 进行战斗校验,插入check_result命令
                Framesync.FrameSyncDataArray.Builder fsdab = Framesync.FrameSyncDataArray.newBuilder();
                Framesync.FrameSyncData fsd = FrameSyncDataUtil.getFrameSyncData(FrameSyncCheckResultData.newBuilder());
                fsdab.addSyncs(fsd);
                cachedOpList.add(fsdab.build());
                endFrameIndex.set(serverFrameSeq.get() + 1);
                log.info(sessionId +"|endFrameIndex|" + endFrameIndex);
            } else {
                if (minLastSyncFrameIndex >= endFrameIndex.get()) {// 确认所有玩家都收到结束帧
                    log.info(sessionId + "|AllPlayerConfirmResult|");
                    pkState = 2;
                    return;
                }
                log.info(sessionId + "|waitConfirmResult|");
            }
        }
        int maxLastSyncFrameIndex = getMaxLastSyncFrameIndex();
        if (serverFrameSeq.get() - maxLastSyncFrameIndex > 1800) {
            log.error(sessionId + "|maxLastSyncFrameIndex==" + maxLastSyncFrameIndex + "|but|serverFrameSeq=="+ serverFrameSeq + "|最大确认帧超过1800帧，退出");
            this.pkState = 3;
            return;
        }

        log.info(sessionId + "|" + serverFrameSeq + "开始生成同步帧数据");
        int frameIndex = serverFrameSeq.incrementAndGet();// 帧序号累加
        long crtTime = System.currentTimeMillis();
        long deltaTime = crtTime - preFrameTime;
        if (preFrameTime <= 0) {
            deltaTime = 30;// 默认30毫秒
        }
        preFrameTime = crtTime;

        Framesync.FrameSyncDataArray.Builder fsdab = Framesync.FrameSyncDataArray.newBuilder().setDeltaTimeFloat(deltaTime * 0.001F).setTotalTime(crtTime - startTime);
        fsdab.setPkSessionId(sessionId).setFrameIndex(frameIndex);
        fsdab.setRandomSeed(ThreadLocalRandom.current().nextInt(0, 9999));

        // AI
        log.info(sessionId + "|playerAI|" + this.playerAI.toString() + "|npcAI|" + this.npcAI.toString());
        fsdab.addAllPlayerAI(this.playerAI);
        fsdab.addAllNpcAI(this.npcAI);

        // 开始合并所有玩家的操作数据
        ArrayBlockingQueue<Framesync.FrameSyncDataArray> cachedOpListTmp = cachedOpList;
        cachedOpList = new ArrayBlockingQueue<Framesync.FrameSyncDataArray>(500);
        // TODO 可以进行重复指令的去重操作，减少包大小。另外UDP每个包大小有限制，如果超过大小，就得舍弃一些非关键帧
        for (Framesync.FrameSyncDataArray fsda : cachedOpListTmp) {
            for (Framesync.FrameSyncData fsd : fsda.getSyncsList()) {
                if (!fsd.hasSyncObj()) {
                    fsd = fsd.toBuilder().setSyncObj(fsda.getSyncObj()).build();
                }
                fsdab.addSyncs(fsd);
            }
        }

        Framesync.FrameSyncDataArray fsda4CrtFrame = fsdab.build();
        fsdaMap.put(fsda4CrtFrame.getFrameIndex(), fsda4CrtFrame);
        log.info(sessionId + "|logFrameIndex|" + fsda4CrtFrame.getFrameIndex() + "|size|"+ fsda4CrtFrame.getSerializedSize());

        for (PkPlayer pkPlayer : pkPlayers) {
            if (pkPlayer.getOffLineTime() == 0) {
                //发送当前帧
                pkPlayer.send(fsda4CrtFrame);
                //补帧
                if (pkPlayer.getIoSession() != null) {
                    int zhuizhenCount = frameIndex - pkPlayer.getLastSyncFrameSeq();
                    if (zhuizhenCount >= 10) {// 相差大于10帧
                        log.warn(this.sessionId + "|" + frameIndex + "|needzhuizhen" + zhuizhenCount + "|"+ pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId());
                        for (int lastFrameIndex = pkPlayer.getLastSyncFrameSeq() + 1; lastFrameIndex <= pkPlayer
                                .getLastSyncFrameSeq() + Math.min(5, zhuizhenCount/5); lastFrameIndex++) {
                            Framesync.FrameSyncDataArray fsdaTmp = fsdaMap.get(lastFrameIndex);// 追帧补发数据，最高5倍速追帧
                            if (fsdaTmp != null) {
                                long startTime = System.currentTimeMillis();
                                pkPlayer.send(fsdaTmp);
                                log.info(this.sessionId + "|zhuizhen|getFrameIndex=" + fsdaTmp.getFrameIndex() + "|useTime|"+(System.currentTimeMillis()-startTime)+"|"+pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId());
                            } else {
                                log.error(sessionId + "|runFrame|senddatanull|" + lastFrameIndex + "|for|"+ pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId());
                            }
                        }
                    }
                }
            } else {
                log.error(sessionId + "|runFrame|but|offline|for|" + pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId());
            }
        }
    }

    /**
     * 判断是否有任意一个玩家连上了战斗服务器
     *
     * @return
     */
    private boolean isAnyOnePlayerConnected() {
        if (startWaitTime == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否所有玩家都连上了战斗服务器
     *
     * @return
     */
    private boolean isAllPlayerConnected(Long now) {
        for (PkPlayer pkPlayer : pkPlayers) {
            if (pkPlayer.getConnectedTime() <= 0 && pkPlayer.getOffLineTime() == 0) {
                if (now - startWaitTime > PkSession.OTHER_JOIN_WAIT_TIME * 1000L) {// 玩家超过等待时间还没连上，则视为掉线，忽略该玩家
                    dealPlayerOffline(pkPlayer, now);
                    log.warn(this.sessionId + "|isAllPlayerConnected|but|" + pkPlayer.getPlayerId() + "@"+ pkPlayer.getZoneId() + "|超过" + PkSession.OTHER_JOIN_WAIT_TIME + "秒没连上，设置为掉线");
                } else {
                    // log.i(this.sessionId + "|isAllPlayerConnected|but|" + pkPlayer.getPlayerId() + "@"+ pkPlayer.getZoneId());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否所有玩家都准备好
     *
     * @return
     */
    private boolean isAllPlayerReady(Long now) {
        for (PkPlayer pkPlayer : pkPlayers) {
            if (pkPlayer.getReadyTime() <= 0 && pkPlayer.getOffLineTime() == 0) {
                if (now - startWaitTime > PkSession.OTHER_JOIN_WAIT_TIME * 1000L) {// 玩家等待时间还没准备，则视为掉线，忽略该玩家
                    dealPlayerOffline(pkPlayer, now);
                    log.warn(this.sessionId + "|isAllPlayerReady|but|" + pkPlayer.getPlayerId() + "@"+ pkPlayer.getZoneId() + "|超过" + PkSession.OTHER_JOIN_WAIT_TIME + "秒没准备，设置为掉线");
                } else {
                    // log.w(this.sessionId + "|isAllPlayerReady|but|" +pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId()+"|getReadyTime=" + pkPlayer.getReadyTime());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否所有玩家掉线
     *
     * @return
     */
    private boolean isAllPlayerOffLine(Long now) {
        for (PkPlayer pkPlayer : pkPlayers) {
            int frameDelta = serverFrameSeq.get() - pkPlayer.getLastSyncFrameSeq();//帧差
            if(pkPlayer.getChangeAiTime()<=0){
                if (frameDelta > PkSession.CHANGE_AI_TIME *30){//转AI
                    dealPlayerChangeAi(pkPlayer, now);
                    log.warn(this.sessionId + "|isAllPlayerOffLine|" + pkPlayer.getPlayerId() + "@"+ pkPlayer.getZoneId() + "|帧差超过" + PkSession.CHANGE_AI_TIME * 30 + "，转为AI");
                }
            }
            if (pkPlayer.getOffLineTime() <= 0) {
                if (frameDelta > PkSession.OFFLINE_WAIT_TIME * 30) {//掉线
                    dealPlayerOffline(pkPlayer, now);
                    log.warn(this.sessionId + "|isAllPlayerOffLine|" + pkPlayer.getPlayerId() + "@"+ pkPlayer.getZoneId() + "|帧差超过" + PkSession.OFFLINE_WAIT_TIME * 30 + "，设置为掉线");
                }
            }
        }
        for (PkPlayer pkPlayer : pkPlayers) {
            if (pkPlayer.getOffLineTime() <= 0) {
                return false;
            }
        }
        log.info(this.sessionId + "|AllPlayerOffLine|");
        return true;
    }

    /**
     * 判断是否所有玩家都上报了战斗结束
     *
     * @return
     */
    private boolean isAllPlayerEnd() {
        for (PkPlayer pkPlayer : pkPlayers) {
            if (pkPlayer.getEndTime() == 0 && pkPlayer.getOffLineTime() == 0) {// 掉线的玩家不能影响其他玩家战斗结束
                return false;
            }
        }
        log.info(this.sessionId + "|AllPlayerEnd|");
        return true;
    }


    /**
     * 获取所有玩家成功同步的最小的帧id，只有小于这个id的帧操作，才能被清理 掉线的玩家不参与计算
     *
     * @return
     */
    private int getMinLastSyncFrameIndex() {
        int frameIndex = 2000000;
        for (PkPlayer pkPlayer : pkPlayers) {
            if (pkPlayer.getOffLineTime() <= 0) {// 掉线的玩家不参与计算
                if (frameIndex > pkPlayer.getLastSyncFrameSeq()) {
                    frameIndex = pkPlayer.getLastSyncFrameSeq();
                }
            }
        }
        return frameIndex;
    }

    /**
     * 获取所有玩家成功同步的最大同步帧id,用于判断没有人上报停止战斗
     * @return
     */
    private int getMaxLastSyncFrameIndex() {
        int frameIndex = 0;
        for (PkPlayer pkPlayer : pkPlayers) {
            if (frameIndex < pkPlayer.getLastSyncFrameSeq()) {
                frameIndex = pkPlayer.getLastSyncFrameSeq();
            }
        }
        return frameIndex;
    }

    /**
     * 记录玩家上报的操作
     * 网络层收到数据后，判断sessionId是本场战斗，则通过这个方法上报玩家操作
     * @param fsda
     * @return
     */
    public boolean addFrameSyncDataArray(Framesync.FrameSyncDataArray fsda, IoSession ioSession) {
        try{
            if (this.pkState >= 2) {// 战斗结束了，上报的操作忽略掉
                log.warn(sessionId + "|addFramesync.FrameSyncDataArray|but|pkState=" + pkState);
                return false;
            }
            for (PkPlayer pkPlayer : pkPlayers) {
                Framesync.SyncMechaInfo smi = fsda.getSyncObj();
                if (pkPlayer.getZoneId() == smi.getZoneId() && pkPlayer.getPlayerId() == smi.getPlayerId()) {
                    String myKey = pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId();
                    log.info(sessionId + "|addFramesync.FrameSyncDataArray|玩家上报数据|" + myKey + "|ioSession|" + ioSession + "|");
                    // 先看是不是重复收到的数据，重复的直接丢掉
                    if (fsda.getClientSeq() > 0) {
                        if (pkPlayer.isDealedClientSeq(fsda.getClientSeq())) {
                            log.debug(sessionId + "|addFramesync.FrameSyncDataArray|repeat ClientSeq" + pkPlayer.getPlayerIdStr() + "|" + fsda.getClientSeq()+ "|in|" + pkPlayer.getReceivedClientSeqSet().size());
                            return false;
                        }
                        if (pkPlayer.getReceivedClientSeqMax() > fsda.getClientSeq()) {
                            log.debug(sessionId + "|addFramesync.FrameSyncDataArray|recived larger seq" + pkPlayer.getPlayerIdStr() + "|" + fsda.getClientSeq()+ "<=" + pkPlayer.getReceivedClientSeqMax());
                            return false;
                        }
                        pkPlayer.addDealedClientSeq(fsda.getClientSeq());
                    }
                    if (startWaitTime == 0) {
                        startWaitTime = System.currentTimeMillis();
                    }
                    // 找到是战斗中的玩家，则判断状态是否进入ok状态
                    if (pkPlayer.getOffLineTime() > 0) {// 玩家已判定为掉线,不支持重连
                        log.error(sessionId + "|addFramesync.FrameSyncDataArray|收到已掉线玩家发来的同步数据|" + myKey + "|忽略不合并");
                        return false;
                    }
                    if (ioSession != null && pkPlayer.getIoSession() != null) {
                        if (!ioSession.getRemoteAddress().equals(pkPlayer.getIoSession().getRemoteAddress())) {//远程地址不一样，说明是重新连接的
                            log.warn(sessionId + "|addFramesync.FrameSyncDataArray|playerChangeClientAddr|" + pkPlayer.getPlayerIdStr()+ "|from|" + pkPlayer.getIoSession() + "|to|" + ioSession);
                        }
                    }
                    // 每次都重设ioSession,就算ioSession改变（Ip端口改变），也继续让玩家玩
                    pkPlayer.setIoSession(ioSession);
                    if (pkPlayer.getConnectedTime() <= 0) {// 是尚未连接先连接
                        pkPlayer.setConnectedTime(System.currentTimeMillis());
                        log.info(sessionId + "|addFramesync.FrameSyncDataArray|" + myKey + ",开始连上来了|"+"|ioSession|" + ioSession);
                        return true;
                    }

                    if (pkPlayer.getReadyTime() <= 0) {
                        for (Framesync.FrameSyncData fsd : fsda.getSyncsList()) {
                            if (Framesync.FrameSyncDataType.FRAME_SYNC_READY.equals(fsd.getFrameSyncDataType())) {
                                log.info(sessionId + "|addFramesync.FrameSyncDataArray|goready|");
                                waitFrame = waitFrame.toBuilder().addSyncs(fsd.toBuilder().setSyncObj(smi)).build();
                                pkPlayer.setReadyTime(System.currentTimeMillis());
                                return true;
                            }
                        }
                        log.info(sessionId + "|need|FRAME_SYNC_READY|but|" + smi);
                        return false;
                    }
                    if (pkPlayer.getLastSyncFrameSeq() < fsda.getFrameIndex()) {
                        log.info(sessionId + "|addFramesync.FrameSyncDataArray|updateFrameIndex|" + pkPlayer.getLastSyncFrameSeq()+ "|to|" + fsda.getFrameIndex() + "|" + smi.getPlayerId() + "@" + pkPlayer.getZoneId());
                        pkPlayer.setLastSyncFrameSeq(fsda.getFrameIndex());// 更新上报的最近处理过的帧id
                    }
                    if(pkPlayer.getChangeAiTime()>0){
                        //转AI的又连上来了
                        log.info(sessionId + "|addFramesync.FrameSyncDataArray|" + myKey + "|转AI之后又连上来了|");
                        if(serverFrameSeq.get() - pkPlayer.getLastSyncFrameSeq()<PkSession.CHANGE_AI_TIME *30){
                            log.info(sessionId + "|addFramesync.FrameSyncDataArray|" + myKey + "|追帧结束，去除AI|");
                            pkPlayer.setChangeAiTime(0);
                            assignAI();
                        }
                    }
                    if (startTime != 0) {
                        // 把玩家操作缓存起来,等服务器帧驱动同步起来
                        log.info(this.sessionId + "|startTime=" + startTime + "|addop|");
                        Framesync.FrameSyncDataArray.Builder fsdab = fsda.toBuilder().clearSyncs();
                        for (Framesync.FrameSyncData fsd : fsda.getSyncsList()) {
                            // 玩家只能上报自己和自己跑的AI
                            if (!(smi.getPlayerId() == fsd.getSyncObj().getPlayerId()
                                    && smi.getZoneId() == fsd.getSyncObj().getZoneId())) {// 不是自己
                                String objKey = fsd.getSyncObj().getPlayerId() + "@" + fsd.getSyncObj().getZoneId();
                                boolean isPlayer = false;
                                boolean isOk = false;
                                for (Framesync.StringStringKeyValue offLinePlayer : playerAI) {
                                    if (offLinePlayer.getKey().equals(objKey)) {// 是掉线玩家
                                        isPlayer = true;
                                        if (offLinePlayer.getValue().equals(myKey)) {// 是挂在自己身上的
                                            isOk = true;
                                        }
                                        break;
                                    }
                                }
                                if (!isPlayer) {// 不是掉线玩家，判NPC
                                    int yushu = fsd.getSyncObj().getMechaId() % 5;
                                    for (Framesync.IntStringKeyValue npc : npcAI) {
                                        if (yushu == npc.getKey()) {
                                            if (npc.getValue().equals(myKey)) {
                                                isOk = true;
                                            }
                                            break;
                                        }
                                    }
                                }
                                if (!isOk) {// 不符合的数据吞掉
                                    log.info(this.sessionId + "|吞掉玩家上报的不是自己和自己跑的AI的数据|" + myKey+"|fsd|"+fsd);
                                    continue;
                                }
                            }
                            switch (fsd.getFrameSyncDataType()) {
                                case FRAME_SYNC_NOOP:// 把noop吞掉
                                case FRAME_SYNC_NPC_DEAD:// 上报的怪物死亡。在考虑是否要把这个吐掉，不进行同步，让各个客户端各自上报，服务器拿来比较，决策是否有人作弊。
                                case FRAME_SYNC_CHECK_RESULT:// 战斗校验通知吞掉，这个通知只能服务器发出去，不能玩家发
                                case FRAME_SYNC_READY:// ready也吞掉，由服务器生成下发
                                    break;
                                case FRAME_SYNC_END:// 上报战斗结束
                                    log.info(this.sessionId + "|receiveEndData|" + pkPlayer.getPlayerId() + "@"+ pkPlayer.getZoneId());
                                    try {
                                        if (pkPlayer.getEndTime() == 0) {
                                            FrameSyncEndData endData = FrameSyncEndData.parseFrom(fsd.getFrameSyncBytes());
                                            pkPlayer.setEndData(endData);
                                            pkPlayer.setEndTime(System.currentTimeMillis());
                                        }
                                    } catch (InvalidProtocolBufferException e) {
                                        log.error(this.sessionId + "|parseEndDataFail|" + fsda, e);
                                    }
                                    break;
                                default:
                                    fsdab.addSyncs(fsd);
                                    break;
                            }
                        }
                        if (!fsdab.getSyncsList().isEmpty()) {
                            cachedOpList.add(fsdab.build());
                        }
                        return true;
                    } else {
                        log.info(this.sessionId + "|startTime=" + startTime + "|so|donot|add|op|" + fsda);
                        return false;
                    }
                }
            }
            log.error(sessionId + "|addFramesync.FrameSyncDataArray|but|noPlayer|" + fsda + "|pkPlayers==" + pkPlayers);
            return false;
        }catch(Throwable t){
            log.error(sessionId + "|addFramesync.FrameSyncDataArray|ERROR|",t);
        }
        return false;
    }

    /**
     * 玩家转AI
     * @param changeAiPlayer
     * @param now
     */
    public void dealPlayerChangeAi(PkPlayer changeAiPlayer, Long now){
        changeAiPlayer.setIoSession(null);
        changeAiPlayer.setChangeAiTime(now);
        log.warn(this.sessionId + "|转AI|" + changeAiPlayer.getPlayerId() + "@" + changeAiPlayer.getZoneId());
        // 重新分配AI
        assignAI();
    }
    /**
     * 处理玩家掉线
     *
     * @return
     */
    public void dealPlayerOffline(PkPlayer offLinePkPlayer, Long now) {
        offLinePkPlayer.setIoSession(null);
        offLinePkPlayer.setOffLineTime(now);
        if(offLinePkPlayer.getChangeAiTime()<=0){
            offLinePkPlayer.setChangeAiTime(now);
        }
        log.warn(this.sessionId + "|设置为掉线|" + offLinePkPlayer.getPlayerId() + "@" + offLinePkPlayer.getZoneId());
        // 重新分配AI
        assignAI();
    }

    /**
     * 分配AI
     */
    public void assignAI() {
        List<String> changeAiPlayer = new ArrayList<String>();
        List<String> onLinePlayer = new ArrayList<String>();
        for (PkPlayer pkPlayer : pkPlayers) {
            if (pkPlayer.getChangeAiTime() > 0) {
                changeAiPlayer.add(pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId());
            } else {
                onLinePlayer.add(pkPlayer.getPlayerId() + "@" + pkPlayer.getZoneId());
            }
        }
        if (onLinePlayer.isEmpty()) {//没有人在线了
            return;
        }
        List<Framesync.IntStringKeyValue> npcAITmp = new ArrayList<Framesync.IntStringKeyValue>();
        for (int i = 0; i < 5; i++) {
            npcAITmp.add(Framesync.IntStringKeyValue.newBuilder().setKey(i).setValue(onLinePlayer.get(i % onLinePlayer.size()))
                    .build());
        }
        this.npcAI = npcAITmp;

        List<Framesync.StringStringKeyValue> playerAITmp = new ArrayList<Framesync.StringStringKeyValue>();
        for (int i = 0; i < changeAiPlayer.size(); i++) {
            playerAITmp.add(Framesync.StringStringKeyValue.newBuilder().setKey(changeAiPlayer.get(i))
                    .setValue(onLinePlayer.get(i % onLinePlayer.size())).build());
        }
        this.playerAI = playerAITmp;
        log.info(this.sessionId + "|assignAI|" + "|playerAI|" + playerAITmp.toString() + "|npcAI|"+ npcAITmp.toString());
    }

}


