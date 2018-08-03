package hotReload2.impl;

public class MineralDataModel {
	private int mineralId;
	private String mineralRes;
	private int appearMine;
	private int appearWeight;
	private int mineralTime;
	private int snatchTimeLimit;
	private int defendSuccessEffect;
	private int defendSuccessEffectLimit;
	private int defendFailEffect;
	private int defendFailEffectLimit;
	// 是一个drop表中的id
	private int exploitReward;
	private int grabReward;

	public int getMineralId() {
		return mineralId;
	}

	public void setMineralId(int mineralId) {
		this.mineralId = mineralId;
	}

	public String getMineralRes() {
		return mineralRes;
	}

	public void setMineralRes(String mineralRes) {
		this.mineralRes = mineralRes;
	}

	public int getAppearMine() {
		return appearMine;
	}

	public void setAppearMine(int appearMine) {
		this.appearMine = appearMine;
	}

	public int getAppearWeight() {
		return appearWeight;
	}

	public void setAppearWeight(int appearWeight) {
		this.appearWeight = appearWeight;
	}

	public int getMineralTime() {
		return mineralTime;
	}

	public void setMineralTime(int mineralTime) {
		this.mineralTime = mineralTime;
	}

	public int getSnatchTimeLimit() {
		return snatchTimeLimit;
	}

	public void setSnatchTimeLimit(int snatchTimeLimit) {
		this.snatchTimeLimit = snatchTimeLimit;
	}

	public int getDefendSuccessEffect() {
		return defendSuccessEffect;
	}

	public void setDefendSuccessEffect(int defendSuccessEffect) {
		this.defendSuccessEffect = defendSuccessEffect;
	}

	public int getDefendSuccessEffectLimit() {
		return defendSuccessEffectLimit;
	}

	public void setDefendSuccessEffectLimit(int defendSuccessEffectLimit) {
		this.defendSuccessEffectLimit = defendSuccessEffectLimit;
	}

	public int getDefendFailEffect() {
		return defendFailEffect;
	}

	public void setDefendFailEffect(int defendFailEffect) {
		this.defendFailEffect = defendFailEffect;
	}

	public int getDefendFailEffectLimit() {
		return defendFailEffectLimit;
	}

	public void setDefendFailEffectLimit(int defendFailEffectLimit) {
		this.defendFailEffectLimit = defendFailEffectLimit;
	}

	public int getExploitReward() {
		return exploitReward;
	}

	public void setExploitReward(int exploitReward) {
		this.exploitReward = exploitReward;
	}

	public int getGrabReward() {
		return grabReward;
	}

	public void setGrabReward(int grabReward) {
		this.grabReward = grabReward;
	}

}
