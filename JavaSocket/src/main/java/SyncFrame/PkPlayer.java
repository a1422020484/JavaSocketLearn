///*
//package SyncFrame;
//
//import org.apache.mina.core.session.IoSession;
//
//import java.util.Set;
//import java.util.TreeSet;
//
//public class PkPlayer {
//    private int zoneId = 0;
//    private int playerId = 0;
//
//    private int lastSyncFrameSeq = 0;// 最近一次同步到的服务器帧序号，帧序号是递增的
//    private long connectedTime = 0;// 连接到服务器的时间，大于0表示客户端网络联通了
//    private long readyTime = 0;// 准备就绪的时间，大于0表示客户端准备就绪了
//    private long changeAiTime = 0;//被转成AI的时间，大于0表示被转成了AI。转成AI之后可能又会转回来变成0
//    private long offLineTime = 0;// 玩家掉线时间，大于0表示客户端连接掉线了
//    private long endTime = 0;// 玩家上报的战斗结束时间，大于0表示已经上报结束
//    private FrameSyncEndData endData;// 玩家上报的战斗结束信息，用于校验战斗结果
//    private Set<Integer> receivedClientSeqSet = new TreeSet<Integer>();
//    private int receivedClientSeqMax = 0;
//
//    private IoSession ioSession = null;
//
//    public IoSession getIoSession() {
//        return ioSession;
//    }
//
//    public void setIoSession(IoSession ioSession) {
//        this.ioSession = ioSession;
//    }
//
//    public long getOffLineTime() {
//        return offLineTime;
//    }
//
//    public void setOffLineTime(long offLineTime) {
//        this.offLineTime = offLineTime;
//    }
//
//    public int getZoneId() {
//        return zoneId;
//    }
//
//    public void setZoneId(int zoneId) {
//        this.zoneId = zoneId;
//    }
//
//    public int getPlayerId() {
//        return playerId;
//    }
//
//    public void setPlayerId(int playerId) {
//        this.playerId = playerId;
//    }
//
//    public String getPlayerIdStr() {
//        return playerId + "@" + zoneId;
//    }
//
//    public int getLastSyncFrameSeq() {
//        return lastSyncFrameSeq;
//    }
//
//    public void setLastSyncFrameSeq(int lastSyncFrameSeq) {
//        this.lastSyncFrameSeq = lastSyncFrameSeq;
//    }
//
//    public long getConnectedTime() {
//        return connectedTime;
//    }
//
//    public void setConnectedTime(long connectedTime) {
//        this.connectedTime = connectedTime;
//    }
//
//    public long getReadyTime() {
//        return readyTime;
//    }
//
//    public void setReadyTime(long readyTime) {
//        this.readyTime = readyTime;
//    }
//
//    public long getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(long endTime) {
//        this.endTime = endTime;
//    }
//
//    public FrameSyncEndData getEndData() {
//        return endData;
//    }
//
//    public void setEndData(FrameSyncEndData endData) {
//        this.endData = endData;
//    }
//
//    public Set<Integer> getReceivedClientSeqSet() {
//        return receivedClientSeqSet;
//    }
//
//    public boolean isDealedClientSeq(int clientSeq) {
//        return receivedClientSeqSet.contains(clientSeq);
//    }
//
//    public void addDealedClientSeq(int clientSeq) {
//        receivedClientSeqSet.add(clientSeq);
//        if (receivedClientSeqMax < clientSeq) {
//            receivedClientSeqMax = clientSeq;
//        }
//    }
//
//    public int getReceivedClientSeqMax() {
//        return receivedClientSeqMax;
//    }
//
//    public long getChangeAiTime() {
//        return changeAiTime;
//    }
//
//    public void setChangeAiTime(long changeAiTime) {
//        this.changeAiTime = changeAiTime;
//    }
//
//    */
///*
//     * 给玩家发送数据
//     *//*
//
//    public void send(Framesync.FrameSyncDataArray fsda) {
//        if (ioSession != null) {
//            ioSession.write(fsda);
//        }
//    }
//}
//*/
