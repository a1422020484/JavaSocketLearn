package matchMethod;

import java.util.ArrayList;
import java.util.List;

public class MatchingTeamInfo {
    public int serverId;
    public long teamid;
    public List<MatchingRoleInfo> roleinfos;

    public MatchingTeamInfo() {
        this.roleinfos = new ArrayList<>();
    }
}
