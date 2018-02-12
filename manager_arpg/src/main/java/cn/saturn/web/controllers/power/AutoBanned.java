package cn.saturn.web.controllers.power;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.power.dao.AccountWarnDAO;
import cn.saturn.web.controllers.power.dao.AccountWarnSealDAO;
import cn.saturn.web.controllers.power.dao.AccountWarnSealModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import proto.ProtocolWeb.BannedPlayerWCS;
import proto.ProtocolWeb.BannedPlayerWSC;

/**
 * 自动封号
 * @author Administrator
 *
 */

@Component
public class AutoBanned {
	
	@Resource
	AccountWarnDAO accountWarnDAO;
	
	public void queryAutoBannedInfo() {
		
		/*Date toDayStart=null;
		Date toDayend=null;
		try {
			toDayend = DateUtils.getAddDayLastTime(new Date(), 0);
			toDayStart=DateUtils.getAddDayZeroPoint(new Date(), 0);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		List<AccountWarnSealModel>  accountWarnSealModelList=accountWarnDAO.getAutoAccountWarnAllModelListAll();
		
		int accountWarnSealModelListSize =(accountWarnSealModelList !=null)?accountWarnSealModelList.size():0;
		if(accountWarnSealModelListSize !=0){
			try{
			for(AccountWarnSealModel accountWarnSealModel:accountWarnSealModelList){
				int playerId =accountWarnSealModel.getRoleid();
				int srvId =accountWarnSealModel.getSrvid();
				
				// 连接服务器
				IClient client = ServerUtils.createClient(srvId);
				
				// 发送消息
				BannedPlayerWCS.Builder msgb = BannedPlayerWCS.newBuilder();
				msgb.setPlayerId(playerId);
				msgb.setEnable(true);

				// 发送并等待回馈
				BannedPlayerWSC retMsg = client.call(Head.H13001, msgb.build(), BannedPlayerWSC.class);
				
				RedisUtils.del(RedisKeys.K_ACCOUNT);
			}
			}catch (Exception e) {
			}
		}
		
	}

}
