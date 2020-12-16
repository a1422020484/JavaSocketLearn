package matchMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestMatch {
    //存储所有的匹配玩家数据,可以存储在数据库中,同样也可以用concurrentHashMap来存储(真实环境一定是多个线程操作该数据的)
    public static Map<Long, MatchingTeamInfo> matchTeamInfos = new HashMap<>();

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                addTestData();
                new MatchForBattleTask().process();
                long end = System.currentTimeMillis();
                System.err.println("耗时:" + (end - now) + "毫秒!");
            }
        };
        //每隔5秒钟执行一次
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.SECONDS);
    }

    /**
     * 测试代码生成测试数据
     */
    private static void addTestData() {
        long baseId = System.currentTimeMillis() / 1000;
        int createTeamNum = getRandomBetween(5000, 7000);
        int index = 0;

        for (int i = 0; i < createTeamNum; ++i) {
            MatchingTeamInfo maInfo = new MatchingTeamInfo();
            maInfo.serverId = i;
            maInfo.teamid = baseId + index;
            TestMatch.matchTeamInfos.put(maInfo.teamid, maInfo);

            int teamNum = getRandomBetween(1, 5);
            for (int j = 0; j < teamNum; ++j) {
                MatchingRoleInfo matchrole = new MatchingRoleInfo();
                long temp = createTeamNum;
                long roleId = (temp << 32) + i + j;
                matchrole.roleId = roleId;
                matchrole.score = getRandomBetween(0, 3000);
                maInfo.roleinfos.add(matchrole);
            }
            index++;
        }
    }

    public static int getRandomBetween(final int start, final int end) {
        Random random = new Random();
        return end > start ? random.nextInt(end - start + 1) + start : random.nextInt(start - end + 1) + end;
    }
}