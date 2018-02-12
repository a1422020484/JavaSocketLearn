package cn.saturn.web.code.login.domain;

public class ShieldSysModel {
	protected long id; // id
	protected String version; // 版本
	protected String closedSubPlatform;//关闭的子渠道
	protected int redeemSys; // 兑换码
	protected int webSite; // 官网
	protected int contactCust;// 联系客服
	protected int rankingSys; // 排行榜
	protected int monthCard; // 月卡
	protected int silentDownloadRes; // 静默下载屏蔽
	protected int fbShare;//facebook分享
	protected int abPay;//爱贝支付
	protected int weixin;//微信

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getRedeemSys() {
		return redeemSys;
	}

	public void setRedeemSys(int redeemSys) {
		this.redeemSys = redeemSys;
	}

	public int getWebSite() {
		return webSite;
	}

	public void setWebSite(int webSite) {
		this.webSite = webSite;
	}

	public int getContactCust() {
		return contactCust;
	}

	public void setContactCust(int contactCust) {
		this.contactCust = contactCust;
	}

	public int getRankingSys() {
		return rankingSys;
	}

	public void setRankingSys(int rankingSys) {
		this.rankingSys = rankingSys;
	}

	public int getMonthCard() {
		return monthCard;
	}

	public void setMonthCard(int monthCard) {
		this.monthCard = monthCard;
	}

	public int getSilentDownloadRes() {
		return silentDownloadRes;
	}

	public void setSilentDownloadRes(int silentDownloadRes) {
		this.silentDownloadRes = silentDownloadRes;
	}

	public String getClosedSubPlatform() {
		return closedSubPlatform;
	}

	public void setClosedSubPlatform(String closedSubPlatform) {
		this.closedSubPlatform = closedSubPlatform;
	}

	public int getFbShare() {
		return fbShare;
	}

	public void setFbShare(int fbShare) {
		this.fbShare = fbShare;
	}

	public int getAbPay() {
		return abPay;
	}

	public void setAbPay(int abPay) {
		this.abPay = abPay;
	}
	
	public int getWeixin() {
		return weixin;
	}

	public void setWeixin(int weixin) {
		this.weixin = weixin;
	}

	@Override
	public String toString() {
		return "ShieldSysModel [id=" + id + ", version=" + version + ", closedSubPlatform=" + closedSubPlatform
				+ ", redeemSys=" + redeemSys + ", webSite=" + webSite + ", contactCust=" + contactCust + ", rankingSys="
				+ rankingSys + ", monthCard=" + monthCard + ", silentDownloadRes=" + silentDownloadRes + ", fbShare="
				+ fbShare + ", abPay=" + abPay + ", weixin=" + weixin + "]";
	}

}
