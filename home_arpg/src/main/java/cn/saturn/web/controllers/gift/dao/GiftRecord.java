package cn.saturn.web.controllers.gift.dao;

import java.util.Date;

/**
 * @author xiezuojie
 */
public class GiftRecord {
    private long id;
    private String giftId;
    private int serverId;
    private int playerId;
    private String accountId;
    private Date receiveTime;
    private String uniqueKey;

//    /**
//     * 生成唯一key,每天唯一
//     */
//    public void genUniqueKey() {
//        Calendar c = Calendar.getInstance();
//        c.setTime(receiveTime);
//        int day = c.get(Calendar.DAY_OF_YEAR);
//        uniqueKey = "" + serverId + playerId + giftId + day;
//    }

    // getter/setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

	@Override
	public String toString() {
		return "GiftRecord [id=" + id + ", giftId=" + giftId + ", serverId=" + serverId + ", playerId=" + playerId
				+ ", accountId=" + accountId + ", receiveTime=" + receiveTime + ", uniqueKey=" + uniqueKey + "]";
	}

}
