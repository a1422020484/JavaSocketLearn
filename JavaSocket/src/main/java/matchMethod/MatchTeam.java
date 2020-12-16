package matchMethod;

import nettyServer.util.AccumulativeHashMap;

import java.util.HashMap;
import java.util.Map;

public class MatchTeam {
    public Map<Long, BattleRoleInfo> members;
    public int serverid;
    public long teamid;
    public int totalScore;
    public int averageScore;

    public MatchTeam(){
        this.members = new HashMap<>();
    }
}
