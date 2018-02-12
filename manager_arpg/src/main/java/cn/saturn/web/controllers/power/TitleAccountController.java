package cn.saturn.web.controllers.power;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.saturn.operation.OperationExt;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.pay.dao.PayModelDAO;
import cn.saturn.web.controllers.power.dao.TitleAccount;
import cn.saturn.web.controllers.power.dao.TitleAccountDAO;
import cn.saturn.web.controllers.power.dao.VindicatorIpDAO;
import cn.saturn.web.controllers.power.dao.VindicatorIpModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.MailUtils;
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
import proto.ProtocolWeb.BannedPlayerWCS;
import proto.ProtocolWeb.BannedPlayerWSC;
import proto.ProtocolWeb.GetBlacklistListWSC;
import proto.ProtocolWeb.SetBlacklistWCS;

/**
 * 全服根据角色名字查询,封号
 * 
 * @author zh
 *
 */

@Path("titleAccount")
public class TitleAccountController {

	@Resource
	TitleAccountDAO titleAccountDAO;

	@Resource
	ServerMergeDAO serverMergeDAO;

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/power/titleAccount/list");
		
	}

	@MainView
	@LoginCheck
	@Get("list")
	public String banned(Invocation inv) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		List<ServerMergeModel> servers = serverMergeDAO.getAllServerMerge();

		request.setAttribute("toSelectUrl", Utils.url(inv, "/power/titleAccount/toSelect"));
		request.setAttribute("toAddUrl", Utils.url(inv, "/power/titleAccount/toAdd"));
		request.setAttribute("toAddban", Utils.url(inv, "/power/titleAccount/toAddBanned"));
		request.setAttribute("servers", servers);
		
		return "titleAccount";
	}

	
	/**
	 * 黑名单
	 * @param inv
	 * @param srvId
	 * @param playerId
	 * @return
	 */
	@Get("toAdd")
	public String toAdd(Invocation inv, @Param("srvId") int srvId, @Param("playerId") int playerId) {

		// 检测服务器ID
		if (srvId <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "未知服务器ID");
		}

		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 发送消息
		SetBlacklistWCS.Builder msgb = SetBlacklistWCS.newBuilder();
		msgb.setPlayerId(playerId);
		msgb.setEnable(1);

		// 发送并等待回馈
		GetBlacklistListWSC retMsg = client.call(Head.H12102, msgb.build(), GetBlacklistListWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, client.getErrStr());
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "黑名单添加成功！");

	}
	
	/**
	 * 封号
	 * @param inv
	 * @param srvId
	 * @param playerId
	 * @return
	 */

	@Get("toAddBanned")
	public String toAddBanned(Invocation inv, @Param("srvId") int srvId, @Param("playerId") int playerId) {

		// 检测服务器ID
		if (srvId <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "未知服务器ID");
		}
		// 连接服务器
		IClient client = ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 发送消息
		BannedPlayerWCS.Builder msgb = BannedPlayerWCS.newBuilder();
		msgb.setPlayerId(playerId);
		msgb.setEnable(true);

		// 发送并等待回馈
		BannedPlayerWSC retMsg = client.call(Head.H13001, msgb.build(), BannedPlayerWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, client.getErrStr());
		}

		RedisUtils.del(RedisKeys.K_ACCOUNT);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "封号成功");

	}

	@Post("toSelect")
	@Get("toSelect")
	public String listJson(Invocation inv, @Param("playerName") String playerName, @Param("rows") int rows,
			@Param("page") int page, @Param("srvId") int srvId) throws IOException {

		/*
		 * HttpServletRequest request = inv.getRequest(); int pageSize =
		 * Integer.parseInt(request.getParameter("rows")); int curPage =
		 * Integer.parseInt(request.getParameter("page")); String playerName=
		 * request.getParameter("playerName");
		 */
		// 计算起始和结束
		int startRow = BsgridUtils.startRowStart(page, rows);

		String name = null;
		if (null == playerName.trim()) {
			name = "%";
		} else {
			name = "%" + playerName.trim() + "%";
		}

		String srvPid = (srvId == -1) ? "%" : srvId + "";

		int total = titleAccountDAO.getCount(name, srvPid);
		// List<TitleAccount> list = titleAccountDAO.getTitleAccount(name);
		List<TitleAccount> list = null;

		if (total > startRow) {
			list = titleAccountDAO.getTitleAccountPage(name, srvPid, startRow, rows);
		} else {
			int start = (int) Math.floor(total / rows);
			list = titleAccountDAO.getTitleAccountPage(name, srvPid, start, rows);
		}

		// 转换接口
		BsgridUtils.IConvert<TitleAccount> action = new BsgridUtils.IConvert<TitleAccount>() {

			@Override
			public boolean convert(TitleAccount obj, Map<String, Object> map) {
				map.put("accountId", obj.getAccount_id());
				map.put("srvId", obj.getPid());
				map.put("srvName", obj.getName());
				map.put("playerId", obj.getPlayer_id());
				map.put("playerName", obj.getPlayer_name());
				map.put("playerLv", obj.getPlayer_lv());
				return true;
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		String jsonStr = OperationExt.queryToEasyuiJson(listDatas, total);
		// String jsonStr = OperationExt.queryToJson(listDatas);
		return "@" + jsonStr;
	}

}
