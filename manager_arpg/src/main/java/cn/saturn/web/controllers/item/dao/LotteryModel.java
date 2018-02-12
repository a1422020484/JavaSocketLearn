package cn.saturn.web.controllers.item.dao;

public class LotteryModel {
	
	private  int lotteryId;
	private  String lotteryName;
	private  int orderId;
	private  int lotteryType;
	private  String lotteryPicture;
	private  int lotteryPriceType;
	private  int lotteryPrice;
	private  int lottery10Price;
	private  int lotteryCumulativeNum;
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getLotteryPicture() {
		return lotteryPicture;
	}
	public void setLotteryPicture(String lotteryPicture) {
		this.lotteryPicture = lotteryPicture;
	}
	public int getLotteryPriceType() {
		return lotteryPriceType;
	}
	public void setLotteryPriceType(int lotteryPriceType) {
		this.lotteryPriceType = lotteryPriceType;
	}
	public int getLotteryPrice() {
		return lotteryPrice;
	}
	public void setLotteryPrice(int lotteryPrice) {
		this.lotteryPrice = lotteryPrice;
	}
	public int getLottery10Price() {
		return lottery10Price;
	}
	public void setLottery10Price(int lottery10Price) {
		this.lottery10Price = lottery10Price;
	}
	public int getLotteryCumulativeNum() {
		return lotteryCumulativeNum;
	}
	public void setLotteryCumulativeNum(int lotteryCumulativeNum) {
		this.lotteryCumulativeNum = lotteryCumulativeNum;
	}
	
	public LotteryModel() {
		super();
	}
	public LotteryModel(int lotteryId, String lotteryName, int orderId, int lotteryType, String lotteryPicture,
			int lotteryPriceType, int lotteryPrice, int lottery10Price, int lotteryCumulativeNum) {
		super();
		this.lotteryId = lotteryId;
		this.lotteryName = lotteryName;
		this.orderId = orderId;
		this.lotteryType = lotteryType;
		this.lotteryPicture = lotteryPicture;
		this.lotteryPriceType = lotteryPriceType;
		this.lotteryPrice = lotteryPrice;
		this.lottery10Price = lottery10Price;
		this.lotteryCumulativeNum = lotteryCumulativeNum;
	}
	@Override
	public String toString() {
		return "LotteryModel [lotteryId=" + lotteryId + ", lotteryName=" + lotteryName + ", orderId=" + orderId
				+ ", lotteryType=" + lotteryType + ", lotteryPicture=" + lotteryPicture + ", lotteryPriceType="
				+ lotteryPriceType + ", lotteryPrice=" + lotteryPrice + ", lottery10Price=" + lottery10Price
				+ ", lotteryCumulativeNum=" + lotteryCumulativeNum + "]";
	}
	
	

}
