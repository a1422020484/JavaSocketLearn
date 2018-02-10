package nettyServer.system.dao.entity;

public class Player_count {

	private String playerId;

	private int countType;

	private int playerCount;

	private long countTime;

	private String additionInfomation;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getCountType() {
		return countType;
	}

	public void setCountType(int countType) {
		this.countType = countType;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public long getCountTime() {
		return countTime;
	}

	public void setCountTime(long countTime) {
		this.countTime = countTime;
	}

	public String getAdditionInfomation() {
		return additionInfomation;
	}

	public void setAdditionInfomation(String additionInfomation) {
		this.additionInfomation = additionInfomation;
	}

}
