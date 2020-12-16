package matchMethod;

import java.util.*;

public class MatchForBattleTask {

    private Set<Long> alreadyMatchTeams = new HashSet<>();

    public void process() {
        if (TestMatch.matchTeamInfos.isEmpty())
            return;
        int totalMatchRoleNum = 0;
        for (Map.Entry<Long, MatchingTeamInfo> entry : TestMatch.matchTeamInfos.entrySet()) {
            totalMatchRoleNum += entry.getValue().roleinfos.size();
        }
        System.err.println("当前正在匹配中的玩家:" + totalMatchRoleNum);
        List<MatchResult> matchResults = new ArrayList<>();//匹配成功的玩家
        Map<Integer, List<MatchTeam>> matchingClassifyTeam = classifyTeams(TestMatch.matchTeamInfos);
        List<MatchGroupResult> matchingGroupResult = getCampMatchGroupResult(matchingClassifyTeam);
        System.err.println("一共整合出:" + matchingGroupResult.size() + "个匹配组");
        if (matchingGroupResult.isEmpty())
            return;
        int size = matchingGroupResult.size();
        Set<Integer> alreadyOccupied = new HashSet<>();

        for (int i = 0; i < size; ++i) {
            if (alreadyOccupied.contains(i))
                continue;
            MatchGroupResult tempGroup = matchingGroupResult.get(i);
            int minVal = Integer.MAX_VALUE;
            MatchResult matchResult = null;
            int saveIndexI = -1;
            int saveIndexJ = -1;
            //这里可以优化下,遍历次数太多了点
            for (int j = 0; j < size; ++j) {
                if (i == j || alreadyOccupied.contains(j))
                    continue;
                int subVal = Math.abs(tempGroup.averageScore - matchingGroupResult.get(j).averageScore);
                //设置个积分差上限,超过这个上限就慢慢等着
                if (subVal > 400)
                    continue;
                if (subVal < minVal) {
                    matchResult = new MatchResult();
                    matchResult.blueGroup = matchingGroupResult.get(i);
                    matchResult.reGroup = matchingGroupResult.get(j);
                    minVal = subVal;
                    saveIndexI = i;
                    saveIndexJ = j;
                }
            }
            if (null != matchResult && saveIndexI != -1 && saveIndexJ != -1) {
                matchResults.add(matchResult);
                alreadyOccupied.add(saveIndexI);
                alreadyOccupied.add(saveIndexJ);
            }
        }
        for (MatchResult matchResult : matchResults) {
            for (long removeTeamId : matchResult.reGroup.teams.keySet())
                TestMatch.matchTeamInfos.remove(removeTeamId);

            for (long removeTeamId : matchResult.blueGroup.teams.keySet())
                TestMatch.matchTeamInfos.remove(removeTeamId);
            //TODO 通知创建战斗场景
        }
        System.err.println("共创建了:" + matchResults.size() + "个战场");
    }

    /**
     * 整合所有的正在匹配的队伍数据,将其按照队伍人数分类,将5个人，4个人.....等等分类存储起来key：为队伍人数,value：队伍信息
     *
     * @param teamMap
     * @return Map<Integer, List < MatchTeam>>
     */
    private Map<Integer, List<MatchTeam>> classifyTeams(Map<Long, MatchingTeamInfo> teamMap) {
        Map<Integer, List<MatchTeam>> classifyResult = new HashMap<>();
        Set<Long> invalidateTeam = new HashSet<Long>();
        for (Map.Entry<Long, MatchingTeamInfo> entry : teamMap.entrySet()) {
            MatchingTeamInfo teamInfo = entry.getValue();
            long teamId = entry.getKey();
            int memNum = teamInfo.roleinfos.size();
            if (memNum <= 0) {
                invalidateTeam.add(teamId);
                continue;
            }
            MatchTeam mt = new MatchTeam();
            mt.serverid = teamInfo.serverId;
            mt.teamid = teamInfo.teamid;
            int totalScore = 0;
            for (MatchingRoleInfo roleInfo : teamInfo.roleinfos) {
                totalScore += roleInfo.score;
                mt.members.put(roleInfo.roleId, new BattleRoleInfo(roleInfo.roleId, roleInfo.score, roleInfo.name));
            }
            mt.totalScore = totalScore;
            mt.averageScore = totalScore / memNum;

            List<MatchTeam> resultList = classifyResult.get(memNum);
            if (null == resultList) {
                resultList = new ArrayList<>();
                classifyResult.put(memNum, resultList);
            }
            resultList.add(mt);
        }
        //删除无效的队伍
        for (long rmTeam : invalidateTeam)
            teamMap.remove(rmTeam);
        return classifyResult;
    }

    private void checkAddMatchGroupResult(List<MatchGroupResult> allTypeMatchResult, MatchGroupResult result) {
        if (null == result)
            return;
        allTypeMatchResult.add(result);
    }

    /**
     * 为当前队伍，填充数据 (如当前队伍中有两人,此时需要再从队伍人数分组中找3个填满,这个3个又可以进一步分为，找 3个一个人的队伍，或者找1个一个的队伍,一个两个人的队伍)
     *
     * @param mTeam             当期队伍
     * @param campClassifyTeams 所有的分组队伍数据
     * @param groupTypes        需要填充的数据
     * @return
     */
    private MatchGroupResult matchMostEqualGroup(MatchTeam mTeam, Map<Integer, List<MatchTeam>> campClassifyTeams, List<Integer> groupTypes) {
        final int myAverageScore = mTeam.averageScore;//当前队伍的平均分
        Set<Long> cacheUsedTeam = new HashSet<>();//缓存已经分配过的队伍
        MatchGroupResult result = new MatchGroupResult();
        int totalScore = 0;
        int totalMemNum = 0;
        if (groupTypes.isEmpty()) {
            cacheUsedTeam.add(mTeam.teamid);
            result.teams.put(mTeam.teamid, mTeam);
            totalScore += mTeam.totalScore;
            totalMemNum += mTeam.members.size();
        } else {
            for (int groupType : groupTypes) {//根据group的信息,去找满足条件的数据
                List<MatchTeam> checkFromList = campClassifyTeams.get(groupType);
                if (null == checkFromList || checkFromList.isEmpty())
                    return null;
                int minVal = Integer.MAX_VALUE;
                MatchTeam currSelectTeam = null;
                for (MatchTeam checkTeam : checkFromList) {
                    if (mTeam.teamid == checkTeam.teamid)
                        continue;
                    if (cacheUsedTeam.contains(checkTeam.teamid) || alreadyMatchTeams.contains(checkTeam.teamid))
                        continue;
                    //从满足条件的队伍组中找到与当前队伍积分最相近的队伍,此处可以设置个最大积分差的限制,挺高公平性
                    int sub = Math.abs(myAverageScore - checkTeam.averageScore);
                    if (sub < minVal) {
                        minVal = sub;
                        currSelectTeam = checkTeam;
                    }
                }
                if (null != currSelectTeam) {
                    cacheUsedTeam.add(currSelectTeam.teamid);
                    result.teams.put(currSelectTeam.teamid, currSelectTeam);
                    totalScore += currSelectTeam.totalScore;
                    totalMemNum += currSelectTeam.members.size();
                } else
                    return null;
            }
        }
        if (cacheUsedTeam.isEmpty() || totalMemNum == 0)
            return null;
        result.averageScore = totalScore / totalMemNum;
        result.totalMemNum = totalMemNum;
        result.totalScore = totalScore;

        return result;
    }

    /**
     * 匹配分配组情况
     * 当队伍中有5个人时,直接放到类型为5的分组中
     * 当队伍中有4个人时,组合情况就是 {4,1}
     * 当队伍中有3个人时,组合情况就是{3,2},{3,1,1}
     * 当队伍中有2个人时,组合情况就是{2,2,1},{2,1,1,1}
     * 当队伍中有1个人时,组合情况及时{1,1,1,1,1}
     *
     * @param mTeam
     * @param classifyTeams
     * @return
     */
    private MatchGroupResult matchBestGroup(MatchTeam mTeam, Map<Integer, List<MatchTeam>> classifyTeams) {
        List<MatchGroupResult> allTypeMatchResult = new ArrayList<>();
        switch (mTeam.members.size()) {
            case 5:
                checkAddMatchGroupResult(allTypeMatchResult, matchMostEqualGroup(mTeam, classifyTeams, Arrays.asList(1)));
                break;
            case 4:
                checkAddMatchGroupResult(allTypeMatchResult, matchMostEqualGroup(mTeam, classifyTeams, Arrays.asList(1)));
                break;
            case 3:
                checkAddMatchGroupResult(allTypeMatchResult, matchMostEqualGroup(mTeam, classifyTeams, Arrays.asList(2)));
                checkAddMatchGroupResult(allTypeMatchResult, matchMostEqualGroup(mTeam, classifyTeams, Arrays.asList(1, 1)));
                break;
            case 2:
                checkAddMatchGroupResult(allTypeMatchResult, matchMostEqualGroup(mTeam, classifyTeams, Arrays.asList(2, 1)));
                checkAddMatchGroupResult(allTypeMatchResult, matchMostEqualGroup(mTeam, classifyTeams, Arrays.asList(2, 1, 1)));
                break;
            case 1:
                checkAddMatchGroupResult(allTypeMatchResult, matchMostEqualGroup(mTeam, classifyTeams, Arrays.asList(1, 1, 1, 1)));
                break;
            default:
                break;
        }

        MatchGroupResult finalResult = null;
        final int mAverageScore = mTeam.averageScore;//当前队伍的平均积分
        int minVal = Integer.MAX_VALUE;
        //进一步再从组合情况中找到积分最相近的一个，然后把自己塞进去
        for (MatchGroupResult result : allTypeMatchResult) {
            int sub = Math.abs(mAverageScore - result.averageScore);
            if (sub < minVal) {
                minVal = sub;
                finalResult = result;
            }
        }
        if (null == finalResult)
            return null;
        finalResult.totalScore += mTeam.totalScore;
        finalResult.totalMemNum += mTeam.members.size();
        finalResult.teams.put(mTeam.teamid, mTeam);
        return finalResult;
    }

    /**
     * 将分类整合后的队伍进一步的去整合,将team中不足5人的队伍补满。
     *
     * @param campTeams
     * @return
     */
    private List<MatchGroupResult> getCampMatchGroupResult(Map<Integer, List<MatchTeam>> campTeams) {
        List<MatchGroupResult> campMatchResult = new ArrayList<>();
        //优先给人数多的队伍分配
        for (int i = 5; i > 0; --i) {
            List<MatchTeam> teams = campTeams.get(i);
            if (null == teams || teams.isEmpty())
                continue;
            for (MatchTeam mt : teams) {
                if (alreadyMatchTeams.contains(mt.teamid))
                    continue;
                MatchGroupResult matchResult = matchBestGroup(mt, campTeams);
                if (null == matchResult)
                    continue;
                campMatchResult.add(matchResult);

                alreadyMatchTeams.addAll(matchResult.teams.keySet());
            }
        }
        return campMatchResult;
    }
}