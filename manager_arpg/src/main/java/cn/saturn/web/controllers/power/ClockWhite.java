package cn.saturn.web.controllers.power;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.saturn.web.code.Head;

import cn.saturn.web.controllers.power.dao.AccountWarnWhiteDAO;
import cn.saturn.web.controllers.power.dao.AccountWarnWhiteModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.utils.ServerUtils;
import proto.ProtocolWeb.GetPlayerInfoAllWCS;
import proto.ProtocolWeb.GetPlayerInfoAllWSC;

/**
 * 定时更新白名单的昨日水晶，今日水晶
 * 
 * @author Administrator
 *
 */

@Component
public class ClockWhite {

	@Resource
	AccountWarnWhiteDAO accountWarnWhiteDAO;

	public void updateClockWhite() {
		List<AccountWarnWhiteModel> accountWarnWhiteModelList = accountWarnWhiteDAO.getAccountWarnModelListAll();
		int whitecount=(accountWarnWhiteModelList !=null)?accountWarnWhiteModelList.size():0;
		if(whitecount !=0){
		for (AccountWarnWhiteModel accountWarnWhiteModel : accountWarnWhiteModelList) {

			try {
				int serverId = accountWarnWhiteModel.getSrvid();
				String IdStr = String.valueOf(accountWarnWhiteModel.getRoleid());

				IClient client = ServerUtils.createClient(serverId);

				// 发送消息
				GetPlayerInfoAllWCS.Builder msgb = GetPlayerInfoAllWCS.newBuilder();
				msgb.setPlayerText(IdStr);
				// 发送并等待回馈
				GetPlayerInfoAllWSC retMsg = client.call(Head.H10006, msgb.build(), GetPlayerInfoAllWSC.class);

				accountWarnWhiteModel.setAccountid(retMsg.getAccount());
				accountWarnWhiteModel.setRoleid(retMsg.getPlayerId());
				accountWarnWhiteModel.setPlayername(retMsg.getName());
				accountWarnWhiteModel.setLevel(retMsg.getLv());
				accountWarnWhiteModel.setViplevel(retMsg.getVip());
				// 设置今天水晶为昨天水晶;
				accountWarnWhiteModel.setYesdcrystal(accountWarnWhiteModel.getTodcrystal());
				// 重新获取今日水晶
				accountWarnWhiteModel.setTodcrystal(retMsg.getCrystal());
				accountWarnWhiteModel.setGold(retMsg.getGold());
				accountWarnWhiteModel.setSrvid(serverId);
			} catch (Exception e) {
			}

		}
		}
		accountWarnWhiteDAO.insertOrUpdateList(accountWarnWhiteModelList);

	}

}
