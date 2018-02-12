package cn.saturn.web.controllers.power;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.saturn.web.code.Head;

import cn.saturn.web.controllers.power.dao.AccountWarnSealDAO;
import cn.saturn.web.controllers.power.dao.AccountWarnSealModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.ServerUtils;
import proto.ProtocolWeb.AccountInfo;
import proto.ProtocolWeb.BannedListWCS;
import proto.ProtocolWeb.BannedListWSC;

/**
 *获取解封账号,
 * 
 * @author Administrator
 *
 */

@Component
public class BannedUnset {

	@Resource
	AccountWarnSealDAO accountWarnSealDAO;

	public void queryBannedInfo() {
		List<ServerModel> serverModelList = ServerComponent.getOperateServerList();
		int serverModelListSize = (serverModelList != null) ? serverModelList.size() : 0;
		
		//获取昨天的00:00:00;
		Date yesterDate = null;
		try {
			yesterDate = DateUtils.getAddDayZeroPoint(new Date(), -1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		List<AccountWarnSealModel> AccountWarnSealList = new ArrayList<AccountWarnSealModel>();
		if (serverModelListSize != 0) {
			for (ServerModel server : serverModelList) {
				try {
					int srvId = Integer.parseInt(String.valueOf(server.getId()));

					// 查找服务器
					ServerModel serverModel = ServerUtils.getServer(srvId, true);

					// 设置服务器名称
					srvId = (int) serverModel.getId();

					// 连接服务器
					IClient client = serverModel.createClient();

					// 发送消息
					BannedListWCS.Builder msgb = BannedListWCS.newBuilder();
					// 发送并等待回馈
					BannedListWSC retMsg = client.call(Head.H13002, msgb.build(), BannedListWSC.class);

					// 提取数据
					List<AccountInfo> accountInfolist = retMsg.getAccountListList();
					
					int accountInfolistSize=(accountInfolist != null)?accountInfolist.size():0;
					if(accountInfolistSize != 0){
					for (AccountInfo accountInfo : accountInfolist) {
						AccountWarnSealModel accountWarnSealModel = new AccountWarnSealModel();
						accountWarnSealModel.setAccountid(accountInfo.getAccount());
						accountWarnSealModel.setRoleid(accountInfo.getPlayerId());
						accountWarnSealModel.setPlayername(accountInfo.getPlayerName());
						accountWarnSealModel.setLevel(accountInfo.getPlayerLv());
						// 封号接口无水晶，金币，VIP等级,用0代替
						accountWarnSealModel.setViplevel(0);
						accountWarnSealModel.setCrystal(0);
						accountWarnSealModel.setGold(0);
						accountWarnSealModel.setSrvid(srvId);
						AccountWarnSealList.add(accountWarnSealModel);
					}
				}
				} catch (Exception e) {
				}
			}
		}
		accountWarnSealDAO.insertOrUpdate(AccountWarnSealList);

	}

}
