package nettyServer.templet.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import nettyServer.util.cache.PersistEntity;

/**
 * @author yangxp get方法从数据库中获得数据
 */
@Component
public class Templet extends PersistEntity {

	// type:int(11)
	private int playerId;
	// type:varchar(32)
	private String templetName;
	private String templetAddress;
	// type:text
	private List<Long> friendList = new ArrayList<>();
	// type:Date
	private Date createTime;
	// type:bigint(20)
	private Long createTimeL;
	// type:mediumblob
	private Map<Long, proto.Friend> friendMap;

	public Templet() {

	}

	public Templet(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	public String getTempletName() {
		return templetName;
	}

	public void setTempletName(String templetName) {
		this.templetName = templetName;
	}

	public String getTempletAddress() {
		return templetAddress;
	}

	public void setTempletAddress(String templetAddress) {
		this.templetAddress = templetAddress;
	}

	public List<Long> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<Long> friendList) {
		this.friendList = friendList;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public Long getCreateTimeL() {
		return createTimeL;
	}

	public void setCreateTimeL(Long createTimeL) {
		this.createTimeL = createTimeL;
	}

	public Map<Long, proto.Friend> getFriendMap() {
		return friendMap;
	}

	public void setFriendMap(Map<Long, proto.Friend> friendMap) {
		this.friendMap = friendMap;
	}

	@Override
	protected PersistEntity cloneEntity() {
		try {
			Templet clone = (Templet) super.clone();
			clone.cloneSource = this;
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
