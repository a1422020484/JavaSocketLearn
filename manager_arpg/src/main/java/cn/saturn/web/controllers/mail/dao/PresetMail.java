package cn.saturn.web.controllers.mail.dao;

import java.util.ArrayList;
import java.util.List;

import cn.saturn.web.gamedata.ItemMsg;
import proto.Protocol;

public class PresetMail {
	protected String mailMsg;
	protected List<ItemMsg> awardList;

	public String getMailMsg() {
		return mailMsg;
	}

	public void setMailMsg(String mailMsg) {
		this.mailMsg = mailMsg;
	}

	public List<ItemMsg> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<ItemMsg> awardList) {
		this.awardList = awardList;
	}

	/**
	 * 返回ProtoItemMsg
	 * 
	 * @return
	 */
	public List<Protocol.ItemMsg> toProtoItemMsg() {
		List<Protocol.ItemMsg> lists = new ArrayList<>();
		for (ItemMsg item : awardList) {
			Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(item.getId())
					.setItemCount(item.getCount()).build();
			lists.add(itemMsg);
		}

		return lists;
	}
}
