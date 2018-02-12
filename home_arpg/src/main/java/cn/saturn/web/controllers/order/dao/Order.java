package cn.saturn.web.controllers.order.dao;

import java.util.Date;

/**
 * 订单
 * 
 * @author xiezuojie
 */
public class Order {
	private long orderId; // 订单id
	private Date createTime; // 创建时间
	private Date finishTime; // 完成时间
	private int goodsId; // 商品id
	private float goodsPrice; // 商品价格
	private int serverId; // 服id
	private int accountId; // 玩家帐号id
	private String accountName; // 玩家帐号名
	private String playerName; // 玩家角色名
	private String platform; // 平台
	private float amount; // 金额
	private String platformOrderNo; // 平台订单号
	private String platformOrderNoSign; // 生成方式,MD5(platform+platform_order_no),用来检查重复订单
	private String payType; // 支付方式,由平台提供
	private int serverState; // 游戏服状态,0正常,1未能连接到游戏服,2游戏服返回异常
	private String ext; // 扩展内容
	
	
	public Order() {
		super();
	}

	public Order(long orderId, Date createTime, Date finishTime, int serverId, int accountId, String accountName,
			String playerName, String platform, float amount, String platformOrderNo, String platformOrderNoSign,
			String payType, int serverState, String ext) {
		super();
		this.orderId = orderId;
		this.createTime = createTime;
		this.finishTime = finishTime;
		this.serverId = serverId;
		this.accountId = accountId;
		this.accountName = accountName;
		this.playerName = playerName;
		this.platform = platform;
		this.amount = amount;
		this.platformOrderNo = platformOrderNo;
		this.platformOrderNoSign = platformOrderNoSign;
		this.payType = payType;
		this.serverState = serverState;
		this.ext = ext;
		this.goodsId = 1000000;
		this.goodsPrice = 0;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public float getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getPlatformOrderNo() {
		return platformOrderNo;
	}

	public void setPlatformOrderNo(String platformOrderNo) {
		this.platformOrderNo = platformOrderNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getServerState() {
		return serverState;
	}

	public void setServerState(int serverState) {
		this.serverState = serverState;
	}
	
	public String getPlatformOrderNoSign() {
		return platformOrderNoSign;
	}
	
	public void setPlatformOrderNoSign(String platformOrderNoSign) {
		this.platformOrderNoSign = platformOrderNoSign;
	}
	
	public String getExt() {
		return ext;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}

}
