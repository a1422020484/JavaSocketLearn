package cn.saturn.web.controllers.power;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.saturn.operation.OperationExt;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.power.dao.AccountWarnWhiteDAO;
import cn.saturn.web.controllers.power.dao.AccountWarnWhiteModel;
import cn.saturn.web.controllers.power.dao.ChannelPayDAO;
import cn.saturn.web.controllers.power.dao.ChannelPayModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb.AccountInfo;
import proto.ProtocolWeb.BannedListWCS;
import proto.ProtocolWeb.BannedListWSC;
import proto.ProtocolWeb.BannedPlayerWCS;
import proto.ProtocolWeb.BannedPlayerWSC;
import proto.ProtocolWeb.GetPlayerInfoAllWCS;
import proto.ProtocolWeb.GetPlayerInfoAllWSC;

@Path("clockwhite")
public class ClockwhiteController {
	
	@Resource
	AccountWarnWhiteDAO accountWarnDAO;
	
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/power/clockwhite/list");
	}
	
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.banned_page)
	@Get("list")
	public String banned(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);
		
		request.setAttribute("tableUrl", Utils.url(inv, "power/clockwhite/list"));
		request.setAttribute("toAddUrl", Utils.url(inv, "power/clockwhite/toAdd"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "power/clockwhite/toDelete"));
		
		// 查找服务器
		ServerModel serverModel = ServerUtils.getServer(srvId, true);
		if (serverModel == null) {
			request.setAttribute("curSrvId", 0);
			request.setAttribute("srvCode", "error");
			return "clockwhite"; // 不处理	
		}

		// 设置服务器名称
		srvId = (int) serverModel.getId();
		request.setAttribute("curSrvId", srvId);
		request.setAttribute("srvCode", serverModel.getSrvStr0());
		
		return "clockwhite";
	
	}
	
	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("toAdd")
	public String toAdd(Invocation inv, @Param("srvid") String srvid, @Param("roleid") String id) {
		
		//srvid=5&roleid=252525
		
		if (StringUtils.isEmpty(srvid.trim())) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器id错误");
		}
		
		if (StringUtils.isEmpty(id.trim())) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "角色id不能为空");
		}
		
		int serverId=Integer.valueOf(srvid.trim());
		String IdStr=id.trim();
		
		// 连接服务器
		IClient client = ServerUtils.createClient(serverId);
		if (client == null) {
		return "@[" + WebUtils.serverStr(serverId) + "]服务器连接错误"; // 不处理
		}

		// 发送消息
		GetPlayerInfoAllWCS.Builder msgb = GetPlayerInfoAllWCS.newBuilder();
		msgb.setPlayerText(IdStr);
		// 发送并等待回馈
		GetPlayerInfoAllWSC retMsg = client.call(Head.H10006, msgb.build(), GetPlayerInfoAllWSC.class);
		if (retMsg == null) {
			return "@查无此人-" + IdStr; // 不处理
		}

		// 读取角色信息
		/*int roleid = retMsg.getPlayerId();
		String playername = retMsg.getName();
		long accountId = retMsg.getAccountId();
		String account = retMsg.getAccount();
		*/
		AccountWarnWhiteModel  accountWarnModel =new AccountWarnWhiteModel();
		
		accountWarnModel.setAccountid(retMsg.getAccount());
		accountWarnModel.setRoleid(retMsg.getPlayerId());
		accountWarnModel.setPlayername(retMsg.getName());
		accountWarnModel.setLevel(retMsg.getLv());
		accountWarnModel.setViplevel(retMsg.getVip());
		//默认设置昨天水晶为0;
		accountWarnModel.setYesdcrystal(0);
		accountWarnModel.setTodcrystal(retMsg.getCrystal());
		accountWarnModel.setGold(retMsg.getGold());
		accountWarnModel.setSrvid(serverId);
		
		accountWarnDAO.insertOrUpdate(accountWarnModel);
			
		// 成功
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功");
	}

	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("id") String id) {

		System.out.println(id);
		// 检查账号id
		if (StringUtils.isEmpty(id)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "不能为空");
		}
		//channelPayDao.delete(id);
		int intid=Integer.parseInt(id);
		accountWarnDAO.delete(intid);
		// 成功
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功");
	}
	
	@Post("list")
	public String listJson(Invocation inv,@Param("page") int page,@Param("rows") int rows) throws IOException {
		
		//分页
//		int start=page==1?0:rows*(page-1);
//		int count =accountWarnDAO.count(1);
//		List<AccountWarnModel> list = accountWarnDAO.getAccountWarnModelList(start, rows);
		List<AccountWarnWhiteModel> list = accountWarnDAO.getAccountWarnModelListAll();
		//List<AccountWarnModel> list0=new ArrayList<AccountWarnModel>();
		
		
		for(AccountWarnWhiteModel accountWarnModel:list){
			
			
			int roleId = accountWarnModel.getRoleid();
			String  roleIdStr=String.valueOf(roleId);
			int SrvId=accountWarnModel.getSrvid();
			
			try{
			// 连接服务器
			IClient client = ServerUtils.createClient(SrvId);
			// 发送消息
			GetPlayerInfoAllWCS.Builder msgb = GetPlayerInfoAllWCS.newBuilder();
			msgb.setPlayerText(roleIdStr);
			// 发送并等待回馈
			GetPlayerInfoAllWSC retMsg = client.call(Head.H10006, msgb.build(), GetPlayerInfoAllWSC.class);
			
			accountWarnModel.setAccountid(retMsg.getAccount());
			accountWarnModel.setRoleid(retMsg.getPlayerId());
			accountWarnModel.setPlayername(retMsg.getName());
			accountWarnModel.setLevel(retMsg.getLv());
			accountWarnModel.setViplevel(retMsg.getVip());
			accountWarnModel.setTodcrystal(retMsg.getCrystal());
			
			accountWarnModel.setGold(retMsg.getGold());
			accountWarnModel.setSrvid(SrvId);
			//list0.add(accountWarnModel);
			}catch(Exception e){
				e.printStackTrace();
				accountWarnModel.setAccountid("服务连接失败:账号未知");
				accountWarnModel.setRoleid(roleId);
				accountWarnModel.setPlayername("服务连接失败:信息未知");
				accountWarnModel.setLevel(0);
				accountWarnModel.setViplevel(0);
				accountWarnModel.setTodcrystal(0);
				accountWarnModel.setGold(0);
				accountWarnModel.setSrvid(SrvId);
			}
		}
		//按cystal降序排序 
		Collections.sort(list, new Comparator<AccountWarnWhiteModel>() {

			@Override
			public int compare(AccountWarnWhiteModel o1, AccountWarnWhiteModel o2) {
//				return Integer.compare(o1.getTodcrystal(), o2.getTodcrystal());
				if (o1.getTodcrystal() < o2.getTodcrystal()) {
					return 1;
				}else{
				return -1;
				}
			}
		});
		

		
		// 转换接口
		BsgridUtils.IConvert<AccountWarnWhiteModel> action = new BsgridUtils.IConvert<AccountWarnWhiteModel>() {

			@Override
			public boolean convert(AccountWarnWhiteModel obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("accountid", obj.getAccountid());
				map.put("roleid", obj.getRoleid());
				map.put("playername",obj.getPlayername());
				map.put("level", obj.getLevel());
				map.put("viplevel", obj.getViplevel());
				map.put("todcrystal", obj.getTodcrystal());
				map.put("gold", obj.getGold());
				map.put("srvid", obj.getSrvid());
				return true;				
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		//String jsonStr = OperationExt.queryToEasyuiJson(listDatas,count);
		String jsonStr = OperationExt.queryToJson(listDatas);
		return "@" + jsonStr;
	}
	
}
