package matchMethod.method2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MatchTask {

    public static int max = 1000;
    public static int min  = 10;
    private static Logger log = LogManager.getLogger("PkSessionManager");

    /**
     * 匹配线程
     */
    private static ScheduledExecutorService sec = Executors.newSingleThreadScheduledExecutor();

    /**
     * 每个人需要匹配到的玩家数量
     */
    private static int NEED_MATCH_PLAYER_COUNT = 1;
    /**
     * 匹配池
     */
    private static ConcurrentHashMap<Integer, MatchPoolPlayerInfo> playerPool = new ConcurrentHashMap<Integer, MatchPoolPlayerInfo>();


    static {
        sec.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                matchProcess(playerPool);
            }
        }, 1, 1, TimeUnit.SECONDS);//每隔1秒匹配一次
    }

    /**
     * 把玩家放入匹配池
     *
     * @param playerId
     * @param rank
     * @return
     */
    public static void putPlayerIntoMatchPool(int playerId, int rank) {
        MatchPoolPlayerInfo playerInfo = new MatchPoolPlayerInfo(playerId, rank);
        playerPool.put(playerId, playerInfo);
    }

    /**
     * 把玩家从匹配池移除
     *
     * @param playerId
     */
    public static void removePlayerFromMatchPool(int playerId) {
        playerPool.remove(playerId);
    }

    private static void matchProcess(ConcurrentHashMap<Integer, MatchPoolPlayerInfo> playerPool) {
        long startTime = System.currentTimeMillis();
        log.debug("执行匹配开始|开始时间|" + startTime);
        try {
            //先把匹配池中的玩家按分数分布
            TreeMap<Integer, HashSet<MatchPoolPlayerInfo>> pointMap = new TreeMap<Integer, HashSet<MatchPoolPlayerInfo>>();
            for (MatchPoolPlayerInfo matchPlayer : playerPool.values()) {
                //在匹配池中是时间太长，直接移除
                if ((System.currentTimeMillis() - matchPlayer.getStartMatchTime()) > 60 * 60 * 1000) {
                    log.warn(matchPlayer.getPlayerId() + "在匹配池中是时间超过一个小时，直接移除");
                    removePlayerFromMatchPool(matchPlayer.getPlayerId());
                    continue;
                }
                HashSet<MatchPoolPlayerInfo> set = pointMap.get(matchPlayer.getRank());
                if (set == null) {
                    set = new HashSet<MatchPoolPlayerInfo>();
                    set.add(matchPlayer);
                    pointMap.put(matchPlayer.getRank(), set);
                } else {
                    set.add(matchPlayer);
                }
            }

            for (HashSet<MatchPoolPlayerInfo> sameRankPlayers : pointMap.values()) {
                boolean continueMatch = true;
                while (continueMatch) {
                    //找出同一分数段里，等待时间最长的玩家，用他来匹配，因为他的区间最大
                    //如果他都不能匹配到，等待时间比他短的玩家更匹配不到
                    MatchPoolPlayerInfo oldest = null;
                    for (MatchPoolPlayerInfo playerMatchPoolInfo : sameRankPlayers) {
                        if (oldest == null) {
                            oldest = playerMatchPoolInfo;
                        } else if (playerMatchPoolInfo.getStartMatchTime() < oldest.getStartMatchTime()) {
                            oldest = playerMatchPoolInfo;
                        }
                    }
                    if (oldest == null) {
                        break;
                    }
                    log.debug(oldest.getPlayerId() + "|为该分数上等待最久时间的玩家开始匹配|rank|" + oldest.getRank());

                    long now = System.currentTimeMillis();
                    int waitSecond = (int) ((now - oldest.getStartMatchTime()) / 1000);

                    log.debug(oldest.getPlayerId() + "|当前时间已经等待的时间|waitSecond|" + waitSecond + "|当前系统时间|" + now + "|开始匹配时间|" + oldest.getStartMatchTime());

                    //按等待时间扩大匹配范围
                    float c2 = 1.5f;
                    int c3 = 5;
                    int c4 = 100;

                    float u = (float) Math.pow(waitSecond, c2);
                    u = u + c3;
                    u = (float) Math.round(u);
                    u = Math.min(u, c4);



                    log.debug(oldest.getPlayerId() + "|本次搜索rank范围下限|" + min + "|rank范围上限|" + max);

                    int middle = oldest.getRank();

                    List<MatchPoolPlayerInfo> matchPoolPlayer = new ArrayList<MatchPoolPlayerInfo>();
                    //从中位数向两边扩大范围搜索
                    for (int searchRankUp = middle, searchRankDown = middle; searchRankUp <= max || searchRankDown >= min; searchRankUp++, searchRankDown--) {
                        HashSet<MatchPoolPlayerInfo> thisRankPlayers = pointMap.getOrDefault(searchRankUp, new HashSet<MatchPoolPlayerInfo>());
                        if (searchRankDown != searchRankUp && searchRankDown > 0) {
                            thisRankPlayers.addAll(pointMap.getOrDefault(searchRankDown, new HashSet<MatchPoolPlayerInfo>()));
                        }
                        if (!thisRankPlayers.isEmpty()) {
                            if (matchPoolPlayer.size() < NEED_MATCH_PLAYER_COUNT) {
                                Iterator<MatchPoolPlayerInfo> it = thisRankPlayers.iterator();
                                while (it.hasNext()) {
                                    MatchPoolPlayerInfo player = it.next();
                                    if (player.getPlayerId() != oldest.getPlayerId()) {//排除玩家本身
                                        if (matchPoolPlayer.size() < NEED_MATCH_PLAYER_COUNT) {
                                            matchPoolPlayer.add(player);
                                            log.debug(oldest.getPlayerId() + "|匹配到玩家|" + player.getPlayerId() + "|rank|" + player.getRank());
                                            //移除
                                            it.remove();
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }

                    if (matchPoolPlayer.size() == NEED_MATCH_PLAYER_COUNT) {
                        log.debug(oldest.getPlayerId() + "|匹配到玩家数量够了|提交匹配成功处理");
                        //自己也匹配池移除
                        sameRankPlayers.remove(oldest);
                        //匹配成功处理
                        matchPoolPlayer.add(oldest);
                        //TODO 把配对的人提交匹配成功处理
                        //matchSuccessProcess(matchPoolPlayer);
                    } else {
                        //本分数段等待时间最长的玩家都匹配不到，其他更不用尝试了
                        continueMatch = false;
                        log.debug(oldest.getPlayerId() + "|匹配到玩家数量不够，取消本次匹配");
                        //归还取出来的玩家
                        for (MatchPoolPlayerInfo player : matchPoolPlayer) {
                            HashSet<MatchPoolPlayerInfo> sameRankPlayer = pointMap.get(player.getRank());
                            sameRankPlayer.add(player);
                        }
                    }
                }
            }
        } catch (Throwable t) {
            log.error("match|error", t);
        }
        long endTime = System.currentTimeMillis();
        log.debug("执行匹配结束|结束时间|" + endTime + "|耗时|" + (endTime - startTime) + "ms");
    }

    private static class MatchPoolPlayerInfo {
        private int playerId;//玩家ID
        private int rank;//玩家分数
        private long startMatchTime;//开始匹配时间


        private MatchPoolPlayerInfo(int playerId, int rank) {
            super();
            this.playerId = playerId;
            this.rank = rank;
            this.startMatchTime = System.currentTimeMillis();
        }

        public int getPlayerId() {
            return playerId;
        }

        public int getRank() {
            return rank;
        }

        public long getStartMatchTime() {
            return startMatchTime;
        }
    }

}
