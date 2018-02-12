package cn.saturn.web.code.bind.domain;

import java.util.Date;

/**
 * 每个服的数据
 */
public class AccountBind {
    private long id;
    private long accountId;
    private int srvId;
    private int playerId;
    private String playerName;
    private int playerLv;
    private String iconUrl;
    private int iconFrame;
    private int fightingCapacity;
    private int vipLv;
    private Date createTime;

    public AccountBind() {}

    public proto.Protocol.PlayerInfo toProtocol() {
        proto.Protocol.PlayerInfo.Builder b = proto.Protocol.PlayerInfo.newBuilder();
        b.setId(playerId);
        b.setName(playerName);
        b.setLv(playerLv);
        b.setIconUrl((iconUrl != null) ? iconUrl : "");
        b.setIconFrame(this.iconFrame);
        b.setFightingCapacity(fightingCapacity);
        b.setVipLv(vipLv);

        return b.build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSrvId() {
        return srvId;
    }

    public void setSrvId(int srvId) {
        this.srvId = srvId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerLv() {
        return playerLv;
    }

    public void setPlayerLv(int playerLv) {
        this.playerLv = playerLv;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIconFrame() {
        return iconFrame;
    }

    public void setIconFrame(int iconFrame) {
        this.iconFrame = iconFrame;
    }

    public int getFightingCapacity() {
        return fightingCapacity;
    }

    public void setFightingCapacity(int fightingCapacity) {
        this.fightingCapacity = fightingCapacity;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

}
