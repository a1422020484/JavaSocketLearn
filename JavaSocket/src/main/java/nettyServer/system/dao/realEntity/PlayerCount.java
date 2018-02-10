package nettyServer.system.dao.realEntity;

import nettyServer.system.dao.AbstractEntity;
import nettyServer.system.dao.entity.Player_count;

/**
 * 次数统计
 * 
 * @author LP
 * 
 */
public class PlayerCount extends AbstractEntity<Player_count>{

	private String playerId;
	//次数类型
	private int countType;
	//数量
	private int playerCount;
	//更改时间
	private long countTime;
	// 附加信息 ,在限时抢购中作为版本号
	private String additionInfomation;
	
	public PlayerCount(String playerId, int countType){
		this.playerId = playerId;
		this.countType = countType;
	}
	
	@Override
	public Player_count getEntity() throws Exception {
		Player_count entity = new Player_count();
		entity.setPlayerId(playerId);
		entity.setCountType(countType);
		entity.setPlayerCount(playerCount);
		entity.setCountTime(countTime);
		entity.setAdditionInfomation(additionInfomation);
		return entity;
	}

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
