package cn.saturn.web.code.login.domain;

import java.util.ArrayList;
import java.util.List;

import cn.saturn.web.utils.ListExtUtil;

public class ShieldModel {
	private static final int redeemSys = 1;
	private static final int website = 2;
	private static final int contactCust = 3;
	private static final int randkingSys = 4;
	private static final int mothCard = 5;
	private static final int silentDownloadRes = 6;
	private static final int fbShare = 7;
	private static final int abPay = 8;
	private static final int weixin=9;

	protected String version;
	protected String closedSubPlatform;
	protected Integer[] shieldSys;
	public static List<Integer> allClosedSys;
	
	static{
		allClosedSys = new ArrayList<>();
		for(int i=0;i<9;i++){
			allClosedSys.add(i+1);
		}
	}

	public static ShieldModel create(ShieldSysModel model) {
		ShieldModel m = new ShieldModel();
		m.version = model.getVersion();
		m.closedSubPlatform = model.getClosedSubPlatform();
		List<Integer> list = new ArrayList<>();

		if (model.getRedeemSys() == 0)
			list.add(redeemSys);
		if (model.getWebSite() == 0)
			list.add(website);
		if (model.getContactCust() == 0)
			list.add(contactCust);
		if (model.getRankingSys() == 0)
			list.add(randkingSys);
		if (model.getMonthCard() == 0)
			list.add(mothCard);
		if(model.getSilentDownloadRes() == 0)
			list.add(silentDownloadRes);
		if(model.getFbShare() == 0)
			list.add(fbShare);
		if(model.getAbPay() == 0)
			list.add(abPay);
		if(model.getWeixin() == 0)
			list.add(weixin);

		m.setShieldSys(ListExtUtil.listToArray(list, new Integer[] { 0 }));
		return m;

	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer[] getShieldSys() {
		return shieldSys;
	}

	public void setShieldSys(Integer[] shieldSys) {
		this.shieldSys = shieldSys;
	}

	public String getClosedSubPlatform() {
		return closedSubPlatform;
	}

	public void setClosedSubPlatform(String closedSubPlatform) {
		this.closedSubPlatform = closedSubPlatform;
	}

}
