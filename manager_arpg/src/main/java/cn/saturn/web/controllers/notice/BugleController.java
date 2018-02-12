package cn.saturn.web.controllers.notice;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;

import proto.ProtocolWeb;
import proto.ProtocolWeb.GetBugleListWCS;
import proto.ProtocolWeb.GetBugleListWSC;
import proto.ProtocolWeb.RemoveBugleWCS;
import proto.ProtocolWeb.RemoveBugleWSC;
import proto.ProtocolWeb.SendBugleWCS;
import proto.ProtocolWeb.SendBugleWSC;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.mail.dao.PresetBugle;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.SaturnClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;

/**
 * 走马灯
 *
 */
@Path("")
public class BugleController {

	// @MainView
	// @Get("")
	// public String index(Invocation inv) throws Throwable {
	// return Utils.redirect(inv, "/notice/list");
	// }

	// @MainView
	// @LoginCheck
	// @Get("blacklist")
	// public String blacklist(Invocation inv) throws Throwable {
	//
	// return "blacklist";
	// }

	// @MainView
	// @LoginCheck
	// @Get("chat")
	// public String chat(Invocation inv) throws Throwable {
	//
	// return "chat";
	// }
	
	@Resource
	PresetDAO presetDAO;
	
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.notice_page)
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("tableUrl", Utils.url(inv, "/notice/listJson"));
		request.setAttribute("addUrl", Utils.url(inv, "/notice/edit"));
		request.setAttribute("editUrl", Utils.url(inv, "/notice/edit"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/notice/toDelete"));
		request.setAttribute("sendUrl", Utils.url(inv, "/notice/bugle"));
		request.setAttribute("srvListUrl", Utils.url(inv, "/notice/srvList"));

		return "buglepreset_list";
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// 转换接口
		BsgridUtils.IConvert<PresetModel> action = new BsgridUtils.IConvert<PresetModel>() {

			@Override
			public boolean convert(PresetModel model, Map<String, Object> map) {
				map.put("id", model.getId());
				map.put("remark", model.getRemark());

				// 解析数据
				PresetBugle presetData = model.getValue(PresetBugle.class);
				if (presetData != null) {
					map.put("msg", presetData.getMsg());
					map.put("msg", presetData.getMsg());

					// // 写入奖励
					// List<PresetMail.ItemMsg> awardList =
					// presetMail.getAwardList();
					// int count = (awardList != null) ? awardList.size() : 0;
					// for (int i = 0; i < count; i++) {
					// PresetMail.ItemMsg itemMsg = awardList.get(i);
					//
					// String numStr = String.format("%02d", i);
					// String nameStr = "awardId_" + numStr;
					// String cnameStr = "awardNum_" + numStr;
					//
					// request.setAttribute(nameStr, itemMsg.getId());
					// request.setAttribute(cnameStr, itemMsg.getCount());
					// }
				}

				return true;
			}

		};

		return PresetManager.listJson(inv, PresetManager.presetType_bugle, action);
	}

	// 删除预设页面
	@LoginCheck
	@UserAuthority(PageModel.notice_page)
	@Get("toDelete")
	public String toRemove(Invocation inv, @Param("id") int id) {
		if (id < 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id:) 不能为空！");
		}

		String defualUrl = Utils.url(inv, "/mail/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功!", "notice/list?mainView=true");
	}

	// 编辑预设页面
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.notice_page)
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		// request.setAttribute("srvIdStr", "disabled=\"disabled\" ");
		// request.setAttribute("recvIdStr", "disabled=\"disabled\" ");
		// request.setAttribute("allSendStr", "disabled=\"disabled\" ");

		if (id <= 0) {
			request.setAttribute("btnStr", "新增");
			request.setAttribute("intervalTime", 1);
		} else {
			PresetModel model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据:" + id);
			}

			String remark = model.getRemark();
			request.setAttribute("remark", remark);

			// 解析数据
			PresetBugle presetData = model.getValue(PresetBugle.class);
			if (presetData != null) {
				request.setAttribute("msg", presetData.getMsg());
				request.setAttribute("intervalTime", presetData.getIntervalTime());
				request.setAttribute("loopCount", presetData.getLoopCount());
				request.setAttribute("resetCount", presetData.getResetCount());
			}

			request.setAttribute("btnStr", "修改");
		}
		request.setAttribute("eidtUrl", Utils.url(inv, "/notice/toEdit"));
		return "buglepreset_edit";
	}

	@UserAuthority(PageModel.notice_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("id") int id, @Param("msgStr") String msgStr,
			@Param("intervalTime") int intervalTime, @Param("resetCount") int resetCount,
			@Param("loopCount") int loopCount, @Param("remark") String remark) throws Throwable {

		if (StringUtils.isEmpty(msgStr)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "msgStr:) 不能为空！");
		}

		// 编辑
		PresetModel model = null;
		PresetBugle presetData = null;
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			// model.setId(id);
			//model.resetId();
			model.setType(PresetManager.presetType_bugle);
		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据:" + id);
			}
			presetData = model.getValue(PresetBugle.class);

		}
		if (presetData == null) {
			presetData = new PresetBugle();
		}

		model.setRemark(remark);
		presetData.setMsg(msgStr);
		presetData.setIntervalTime(intervalTime);
		presetData.setLoopCount(loopCount);
		presetData.setResetCount(resetCount);
		// 设置数据
		if (!model.setValue(presetData)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "数据保存失败.");
		}

		// 提交
		if (id <= 0) {
			//PresetManager.getInstance().insertByDAO(model);
			presetDAO.insertNew(model);
		} else {
			//PresetManager.getInstance().updateByDAO(model);
			presetDAO.insertOrUpdateNew(model);
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "处理成功.", "notice/list?mainView=true");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.notice_page)
	@Get("bugle")
	public String bugle(Invocation inv, @Param("id") int presetId) throws Throwable {
		DateFormat showFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 设置当前日期 2015-01-01T00:00
		int intervalTime = 1;
		int loopCount = 1;
		String msg = "";
		Date d = new Date();
		String nowDateStr = showFormat.format(d);

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("startDate", nowDateStr);

		// 编辑
		if (presetId > 0) {
			PresetModel model = PresetManager.getInstance().get(presetId);
			// 解析数据
			PresetBugle presetData = model.getValue(PresetBugle.class);
			if (presetData != null) {
				msg = presetData.getMsg();
				intervalTime = presetData.getIntervalTime();
				//int resetCount = presetData.getResetCount();
				// if (resetCount > 1) {
				// long endTimeL = nowTimeL + (intervalTime * 1000L) *
				// resetCount;
				// endTimeStr = WebUtils.Html.getDateTimeStr(endTimeL);
				// }
			}
		}

		request.setAttribute("msg", msg);
		request.setAttribute("loopCount", loopCount);
		request.setAttribute("intervalTime", intervalTime);
		request.setAttribute("endDate", nowDateStr);

		request.setAttribute("eidtUrl", Utils.url(inv, "/notice/toSendBugle"));
		return "bugle";
	}

	// 开始发送时间
	private static final Calendar startC = Calendar.getInstance();
	// 结束发送时间
	private static final Calendar endC = Calendar.getInstance();

	@LoginCheck
	@UserAuthority(PageModel.notice_page)
	@Post("toSendBugle")
	public String toSendBugle(Invocation inv, @Param("msgStr") String msgStr, @Param("startDate") String startDate,
			@Param("intervalTime") int intervalTime, @Param("endDate") String endDate,
			@Param("loopCount") int loopCount) throws Throwable {

		DateFormat showFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

		if (StringUtils.isEmpty(msgStr)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "消息不能为空.");
		}

		// 开始时间
		if (StringUtils.isEmpty(startDate)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "结束时间错误.");
		}
		if (StringUtils.isEmpty(endDate)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "结束时间错误.");
		}

		startC.setTime(showFormat.parse(startDate));
		endC.setTime(showFormat.parse(endDate));

		// 解析时间
		long startTimeL = startC.getTimeInMillis();
		if (startTimeL <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "开始日期格式错误.");
		}
		long endTimeL = endC.getTimeInMillis();
		if (endTimeL <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "结束日期格式错误.");
		}

		long dTimeL = endTimeL - startTimeL;
		if (dTimeL < 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "开始日期必须小于等于结束日期.");
		}

		String srvIdStrs = inv.getParameter("srvId");
		if (srvIdStrs == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID为空.");
		}

		// 解析出服务器ID列表
		int[] srvIds = ServerUtils.getSrvIds(srvIdStrs);
		int count = (srvIds != null) ? srvIds.length : 0;
		if (count <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID数量为空.");
		}

		// 计算重复次数 (按照秒钟发)
		int resetCount = 0;
		if (intervalTime > 0) {
			resetCount = (int) ((dTimeL) / (intervalTime * 1000));
		}
		resetCount = (resetCount > 0) ? resetCount : 1;

		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);

		// StringBuffer strBuf = new StringBuffer();

		// 遍历发送
		StringBuffer strBufSendSucc = new StringBuffer();
		StringBuffer strBufSendFail = new StringBuffer();

		for (int i = 0; i < count; i++) {
			int srvId = srvIds[i];
			ServerModel serverModel = serverComponent.find(srvId);
			if (serverModel == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID" + srvId + "不存在");
			}
			// 连接服务器
			IClient client = serverModel.createClient();
			if (client == null) {
				return "@"
						+ PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器[" + serverModel.getSrvStr0() + "] Url配置错误");
			}
			// 发送消息
			SendBugleWCS.Builder msgb = SendBugleWCS.newBuilder();
			msgb.setMsgStr(msgStr);
			msgb.setStartTime(startC.getTimeInMillis());
			msgb.setIntervalTime(intervalTime);
			msgb.setResetCount(resetCount);
			msgb.setLoopCount(loopCount);

			// 发送并等待回馈
			SendBugleWSC retMsg = client.call(Head.H12201, msgb.build(), SendBugleWSC.class);
			if (retMsg == null) {
				strBufSendFail.append("<div style='color:red;'>服务器[" + serverModel.getSrvStr0() + "] 发送失败:"
						+ client.getErrStr() + "</div><br/>");
			} else
				strBufSendSucc.append(serverModel.getSrvStr0() + " 已经发送成功.<br/>");
		}
		// String defualUrl = Utils.url(inv, "/notice/bugle");
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO,
				"发送成功!<br/> " + strBufSendFail.toString() + strBufSendSucc.toString());
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.notice_page)
	@Get("srvList")
	public String bugle_list(Invocation inv, @Param("srvId") int srvId) throws Throwable {
		if (srvId <= 0) {
			srvId = WebUtils.checkSrvId(srvId, inv.getRequest(), true);
		}
		// srvId = 1;

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("curSrvId", srvId);
		request.setAttribute("tableUrl", Utils.url(inv, "/notice/listJsonS?srvId=" + srvId));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/notice/toSrvDelete"));
		request.setAttribute("resetUrl", Utils.url(inv, "/notice/srvList"));
		request.setAttribute("backUrl", Utils.url(inv, "/notice/list"));

		return "bugle_list";
	}

	@Get("toSrvDelete")
	public String toSrvDelete(Invocation inv, @Param("srvId") int srvId, @Param("id") int id) throws IOException {
		if (id <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id为空");
		}
		// 连接服务器
		SaturnClient client = (SaturnClient) ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 执行删除
		RemoveBugleWCS msg = RemoveBugleWCS.newBuilder().setId(id).build();
		RemoveBugleWSC retMsg = client.call(Head.H12203, msg, RemoveBugleWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "成功");
	}

	@UserAuthority(PageModel.notice_page)
	@Post("listJsonS")
	public String listJsonS(Invocation inv, @Param("srvId") int srvId) throws IOException {
		if (srvId <= 0) {
			return BsgridUtils.emptyJson;
		}
		DateFormat showFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

		HttpServletRequest request = inv.getRequest();

		// 读取参数
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		int totalRows = 0;
		totalRows = Utils.getIntParameter(inv, "srvBugleCheck_" + srvId);
		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);

		// 访问服务器获取数据
		// 连接服务器
		SaturnClient client = (SaturnClient) ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 发送消息
		GetBugleListWCS.Builder b = GetBugleListWCS.newBuilder();
		b.setCount(pageSize);
		b.setStart(startRow);
		if (totalRows <= 0) {
			b.setGetcount(true); // 读取数量
		}

		// 发送并等待回馈
		GetBugleListWSC retMsg = client.call(Head.H12202, b.build(), GetBugleListWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "访问失败");
		}
		// 遍历
		int maxCount = retMsg.getMaxcount();
		totalRows = (maxCount > 0) ? maxCount : totalRows;

		// 遍历
		List<ProtocolWeb.BugleItem> list = retMsg.getListList();

		// 转换数据
		BsgridUtils.IConvert<ProtocolWeb.BugleItem> action = new BsgridUtils.IConvert<ProtocolWeb.BugleItem>() {

			@Override
			public boolean convert(ProtocolWeb.BugleItem model, Map<String, Object> map) {
				map.put("id", model.getId());
				map.put("msgStr", model.getMsgStr());

				long startTime = model.getStartTime();
				long resetCount = model.getResetCount();
				int intervalTime = model.getIntervalTime();
				long endTime = startTime + resetCount * intervalTime;
				map.put("startTime", showFormat.format(new Date(startTime)));
				map.put("endTime", showFormat.format(new Date(endTime)));
				map.put("intervalTime", (int) (intervalTime / 1000));
				// map.put("resetCount", model.getResetCount());
				map.put("loopCount", model.getLoopCount());

				return true;
			}

		};
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);
		return "@" + jsonStr;
	}
}
