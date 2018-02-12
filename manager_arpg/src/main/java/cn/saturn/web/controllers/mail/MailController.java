package cn.saturn.web.controllers.mail;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.Protocol;
import proto.ProtocolWeb.GetMailListWCS;
import proto.ProtocolWeb.GetMailListWSC;
import proto.ProtocolWeb.RemoveMailWCS;
import proto.ProtocolWeb.RemoveMailWSC;
import proto.ProtocolWeb.SendMailWCS;
import proto.ProtocolWeb.SendMailWSC;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.controllers.logs.dao.LogDAO;
import cn.saturn.web.controllers.logs.dao.LogModel;
import cn.saturn.web.controllers.mail.dao.ActionResult;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetMail;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.SaturnClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.gamedata.ItemMsg;
import cn.saturn.web.utils.AuthorityUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;

@Path("")
public class MailController {
	@Resource
	LogDAO logDao;
	
	@Resource
	PresetDAO presetDAO;

	@UserAuthority(PageModel.mail_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/mail/send");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.mail_page)
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("tableUrl", Utils.url(inv, "/mail/listJson"));
		request.setAttribute("addUrl", Utils.url(inv, "/mail/edit"));
		request.setAttribute("editUrl", Utils.url(inv, "/mail/edit"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/mail/toDelete"));
		request.setAttribute("sendUrl", Utils.url(inv, "/mail/send"));
		request.setAttribute("checkUrl", Utils.url(inv, "/mail/srvList"));

		return "mailpreset_list";
	}

	public String showAddCondition(Invocation inv) {
		return "mail_condition";
	}

	@LoginCheck
	@Post("saveRewAndReq")
	public String saveRewAndReq(Invocation inv, @Param("id") long id, @Param("item_id") int item_id,
			@Param("item_num") long item_num) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";
		PresetMail presetData = model.getValue(PresetMail.class);
		// 条件
		List<ItemMsg> awards = presetData.getAwardList();

		if (null != awards) {
			if (awards.size() >= 7)
				return "@{'msg':'必须小于7个'}";

			ItemMsg itemMsg = ItemMsg.create(item_id, (int) item_num);
			if (null != itemMsg)
				awards.add(itemMsg);
		}

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("item_id", item_id);
		jsonMap.put("item_num", item_num);
		jsonMap.put("item_name", getChestOrItemName(item_id));
		// 转成json
		return "@" + JSON.toJSONString(jsonMap);
	}

	@LoginCheck
	@Post("updateRewAndReq")
	public String updateRewAndReq(Invocation inv, @Param("id") long id, @Param("row") int row,
			@Param("item_id") int item_id, @Param("item_num") long item_num) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";
		PresetMail presetData = model.getValue(PresetMail.class);
		// 条件
		List<ItemMsg> requires = presetData.getAwardList();
		ItemMsg itemMsg = ItemMsg.create(item_id, (int) item_num);
		if (null != itemMsg)
			requires.set(row, itemMsg);
		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);

		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", row);
		jsonMap.put("item_id", item_id);
		jsonMap.put("item_num", item_num);

		// 转成json
		return "@" + JSON.toJSONString(jsonMap);
	}

	@Post("getRewAndReq")
	public String getRewAndReq(Invocation inv, @Param("id") long id) {

		List<ItemMsg> awards = null;
		PresetMail presetData = null;
		PresetModel model = PresetManager.getInstance().get(id);

		if (null != model) {
			presetData = model.getValue(PresetMail.class);
			awards = presetData.getAwardList();
		}

		int max_size = (awards != null) ? awards.size() : 0;
		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", max_size);
		dataObj.put("id", id);
		List<Map<Object, Object>> dataList = new ArrayList<>();
		if(awards==null)
			return "@" + "奖励列表为空!!!";
		int rowId=-1;
		for (ItemMsg award : awards) {
			Map<Object, Object> listMap = new HashMap<>();
			rowId=rowId+1;
			listMap.put("id", rowId);
			listMap.put("item_id", award.getId());
			listMap.put("item_num", award.getCount());
			ItemModel iModel = ItemModelManager.getById(award.getId());
			if (null != iModel)
				listMap.put("item_name", iModel.getItemName());
			dataList.add(listMap);
		}

		dataObj.put("rows", dataList);
		return "@" + JSONObject.toJSON(dataObj);
	}

	@LoginCheck
	@Post("destroyRewAndReq")
	public String destroyRewAndReq(Invocation inv, @Param("id") int id, @Param("row") int row) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + "{\"success\":false}";
		PresetMail presetData = model.getValue(PresetMail.class);

		List<ItemMsg> awards = presetData.getAwardList();
		awards.remove(row);

		presetData.setAwardList(awards);

		if (!model.setValue(presetData))
			return "@" + "{\"success\":false}";

		PresetManager.getInstance().insertByDAO(model);

		return "@" + "{\"success\":true}";
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
				PresetMail presetMail = model.getValue(PresetMail.class);
				if (presetMail != null) {
					map.put("mailMsg", presetMail.getMailMsg());

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

		return PresetManager.listJson(inv, PresetManager.presetType_mail, action);
	}

	// 删除预设页面
	@LoginCheck
	@UserAuthority(PageModel.mail_page)
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "/mail/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功！", "mail/list?mainView=true");
	}

	// 编辑预设页面
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.mail_page)
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();

		if (id <= 0) {
			request.setAttribute("btnStr", "新增");
			request.setAttribute("isNew", "true");
		} else {
			PresetModel model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据:" + id);
			}

			request.setAttribute("isNew", "false");
			String remark = model.getRemark();
			request.setAttribute("remark", remark);

			// 解析数据
			PresetMail presetMail = model.getValue(PresetMail.class);
			if (presetMail != null) {
				request.setAttribute("mailMsg", presetMail.getMailMsg());

				// 写入奖励
				List<cn.saturn.web.gamedata.ItemMsg> awardList = presetMail.getAwardList();
				int count = (awardList != null) ? awardList.size() : 0;
				for (int i = 0; i < count; i++) {
					cn.saturn.web.gamedata.ItemMsg itemMsg = awardList.get(i);

					String numStr = String.format("%02d", i);
					String nameStr = "awardId_" + numStr;
					String cnameStr = "awardNum_" + numStr;

					request.setAttribute(nameStr, itemMsg.getId());
					request.setAttribute(cnameStr, itemMsg.getCount());
				}
			}

			request.setAttribute("btnStr", "修改");
		}
		request.setAttribute("eidtUrl", Utils.url(inv, "/mail/toEdit"));
		return "mailpreset_edit";
	}

	@UserAuthority(PageModel.mail_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("id") int id, @NotBlank @Param("msgTxt") String mailMsg,
			@Param("remark") String remark) throws Throwable {
		// 获取奖励
		List<ItemMsg> awardList = new ArrayList<>();
		// 编辑
		PresetModel model = null;
		PresetMail presetMail = null;
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			// model.setId(id);
			//model.resetId();
			model.setType(PresetManager.presetType_mail);
			presetMail = new PresetMail();
		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			}
			presetMail = model.getValue(PresetMail.class);
			if (presetMail == null) {
				presetMail = new PresetMail();
			}
			awardList = presetMail.getAwardList();
		}

		model.setRemark(remark);
		presetMail.setMailMsg(mailMsg);
		presetMail.setAwardList(awardList);
		// 设置数据
		if (!model.setValue(presetMail)) {
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

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "处理成功", "mail/list?mainView=true");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.mail_page)
	@Get("send")
	public String send(Invocation inv, @Param("id") int presetId) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/mail/toSend"));

		if (presetId > 0) {
			PresetModel model = PresetManager.getInstance().get(presetId);
			if (model != null) {

				String remark = model.getRemark();
				request.setAttribute("remark", remark);
				request.setAttribute("select_server", "0");

				// 解析数据
				PresetMail presetMail = model.getValue(PresetMail.class);
				if (presetMail != null) {
					request.setAttribute("mailMsg", presetMail.getMailMsg());
				}
			}
		}

		return "mail_send";
	}

	@LoginCheck
	@UserAuthority(PageModel.mail_page)
	@Post("toSend")
	public String toSend(Invocation inv, @Param("recvId") String recvId0, @Param("msgTxt") String txt)
			throws Throwable {
		int presetId = Utils.getIntParameter(inv, "id");

		PresetModel presetModel = PresetManager.getInstance().get(presetId);
		if (presetModel == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "邮件不存在");
		}

		// 检测文本
		if (StringUtils.isEmpty(txt)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "文本内容不能为空");
		}

		// 读取服务器ID
		int srvId = Utils.getIntParameter(inv, "srvId");
		if (srvId <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID错误");
		}

		

		// 1. 更新数据
		PresetMail presetData = presetModel.getValue(PresetMail.class);
		presetData.setMailMsg(txt);
		presetModel.setValue(presetData);
		PresetManager.getInstance().updateByDAO(presetModel);

		int select_server = Utils.getIntParameter(inv, "select_server");

		List<ResultMsg> resultMsg = new ArrayList<>();
		
		// 多服务器发送
		if (select_server == 0) {
			String srvIds = Utils.getParameter(inv, "srvIds");
			if (StringUtils.isEmpty(srvIds)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "多服务器发送请选择服务器");
			}
			
			// 遍历服务器,发送邮件
			String[] idStrs = srvIds.split(",");
			//日志记录
			List<Integer> srvIdsM = new ArrayList<>();
			
			
			for (String idStr : idStrs) {
				int serverId = Integer.parseInt(idStr);
				
				// 查找服务器
				ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
				ServerModel serverModel = serverComponent.find(serverId);
				if (serverModel == null) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID" + srvId + "不存在");
				}
				//加入日志记录中
				srvIdsM.add(serverId);
				// 连接服务器
				IClient client = serverModel.createClient();
				if (client == null) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器[" + serverModel.getSrvStr0() + "] Url配置错误");
				}
				// 全服发送
				ActionResult result = sendMail(client, -1, txt, presetData.toProtoItemMsg());
				if (!result.isSucceed()) {
					resultMsg.add(new ResultMsg(ResultMsg.fail,"服务器[" + serverModel.getSrvStr0() + "] 发送失败:" + result.msg));
				}
			}
			//多服记录日志
			HttpServletRequest request = inv.getRequest();
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("user");
			LogModel logModel = new LogModel();
			
			Map<String, Object> map = new HashMap<>();
			map.put("ctx", presetModel.getRemark());
			map.put("reward", presetModel.getInfo());
			map.put("srvIds", srvIdsM);
			map.put("recipientId", new String[] { "-100" });

			logModel.setUser_id(userModel == null?0:userModel.getId());
			logModel.setUser_name(userModel == null?"unknow":userModel.getUsername());
			logModel.setType(LogModel.email_type);
			logModel.setContent(JSONObject.toJSON(map).toString());
			logDao.insert(logModel);
			
		}else{
		
		// 检测是否全服发送
		String allSendStr = inv.getParameter("allSend");
		boolean allSend = (allSendStr != null);
		int[] recvIds = null;
		int recvCount = 0;
		// 检测用户id
		if (!allSend) {
			// 分析ID
			String[] recvStrs = recvId0.split(";");
			recvCount = (recvStrs != null) ? recvStrs.length : 0;
			// 非全服发送时检测id
			if (recvCount <= 0) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "用户ID错误");
			}

			// 遍历生成ID
			recvIds = new int[recvCount];
			for (int i = 0; i < recvCount; i++) {
				String str = recvStrs[i];
				try {
					recvIds[i] = Integer.valueOf(str);
				} catch (Exception ex) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "用户ID[" + i + "]内容错误");
				}
				// 判断ID是否符合
				if (recvIds[i] <= 0) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "用户ID[" + i + "]不存在");
				}
			}

		} else {
			// 不发送id
			recvIds = null;
		}
		//日志记录的服务器list
		List<Integer> srvIdsM = new ArrayList<>();
		// 获取奖励
		List<Protocol.ItemMsg> itemList = presetData.toProtoItemMsg();

		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(srvId);
		if (serverModel == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID" + srvId + "不存在");
		}
		//加入日志记录中
		srvIdsM.add(srvId);
		
		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器[" + serverModel.getSrvStr0() + "] Url配置错误");
		}

		if (allSend) {
			// 全服发送
			ActionResult result = sendMail(client, -1, txt, itemList);
			if (!result.isSucceed()) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR,
						"服务器[" + serverModel.getSrvStr0() + "] 发送失败:" + result.msg);
			}
		} else {
			// 遍历发送单个发送
			for (int i = 0; i < recvCount; i++) {
				int recvId = recvIds[i];
				// 发送邮件
				ActionResult result = sendMail(client, recvId, txt, itemList);
				if (!result.isSucceed()) {
					return "@"
							+ PageNoticeUtil.notic(PageNoticeUtil.ERROR, "错误发送[" + i + "-" + recvId + "]" + result.msg);
				}
			}
		}
		//单服记录日志
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		LogModel logModel = new LogModel();
		
		Map<String, Object> map = new HashMap<>();
		map.put("ctx", presetModel.getRemark());
		//map.put("ctx", txt);
		//map.put("mailMsg", presetModel.getRemark());
		map.put("reward", presetModel.getInfo());
		map.put("srvIds", srvIdsM);
		map.put("recipientId", allSend ? new String[] { "-100" } : recvId0.split(";"));

		logModel.setUser_id(userModel == null?0:userModel.getId());
		logModel.setUser_name(userModel == null?"unknow":userModel.getUsername());
		logModel.setType(LogModel.email_type);
		logModel.setContent(JSONObject.toJSON(map).toString());
		logDao.insert(logModel);		
		}

		// 记录日志
		/*HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		LogModel logModel = new LogModel();
		List<String> srvIds = new ArrayList<>();
		srvIds.add(serverModel.getSrvStr0());
		Map<String, Object> map = new HashMap<>();
		map.put("ctx", txt);
		map.put("reward", itemList);
		map.put("srvIds", srvIds);
		map.put("recipientId", allSend ? new String[] { "-100" } : recvId0.split(";"));

		logModel.setUser_id(userModel.getId());
		logModel.setUser_name(userModel.getUsername());
		logModel.setType(LogModel.email_type);
		logModel.setContent(JSONObject.toJSON(map).toString());
		logDao.insert(logModel);*/
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "发送成功", "mail/list?mainView=true");
	}
	
	protected static ActionResult sendMail0(IClient client, int recvId, String msg, List<ItemMsg> itemLists) {
		List<Protocol.ItemMsg> lists = new ArrayList<>();
		for(ItemMsg item:itemLists){
			Protocol.ItemMsg itemMsg = Protocol.ItemMsg.newBuilder().setItemId(item.getId()).setItemCount(item.getCount())
					.build();
			lists.add(itemMsg);
		}
		
		return sendMail(client,recvId,msg,lists);
	}

	/**
	 * @param srvId
	 * @param recvId
	 *            -1为全局
	 * @param msg
	 * @param itemList
	 * @return
	 */
	protected static ActionResult sendMail(IClient client, int recvId, String msg, List<Protocol.ItemMsg> itemList) {
		boolean allSend = recvId == -1;

		// // 查找服务器
		// ServerComponent serverComponent =
		// ComponentManager.getComponent(ServerComponent.class);
		// ServerModel serverModel = serverComponent.find(srvId);
		// if (serverModel == null) {
		// return ActionResult.Error("服务器不存在");
		// }
		//
		// // 连接服务器
		// IClient client = serverModel.createClient();
		// if (client == null) {
		// return ActionResult.Error("服务器Url配置错误");
		// }

		// 发送消息
		SendMailWCS.Builder msgb = SendMailWCS.newBuilder();
		msgb.setMsgStr(msg); // 邮件消息
		if (allSend) {
			msgb.setAllSend(allSend); // 全局发送
			msgb.setRecvId(0+"");
		} else {
			msgb.setRecvId(recvId+"");
		}
		msgb.addAllItemList(itemList); // 物品ID

		// 发送并等待回馈
		SendMailWSC retMsg = client.call(Head.H11001, msgb.build(), SendMailWSC.class);
		if (retMsg == null) {
			return ActionResult.Error("发送失败:" + client.getErrStr());
		}

		return ActionResult.Ok("发送成功");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.mail_page)
	@Get("srvList")
	public String mail_list(Invocation inv, @Param("srvId") int srvId) throws Throwable {
		if (srvId <= 0) {
			srvId = WebUtils.checkSrvId(srvId, inv.getRequest(), true);
		}
		// srvId = 1;

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("curSrvId", srvId);
		request.setAttribute("tableUrl", Utils.url(inv, "/mail/listJsonS?srvId=" + srvId));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/mail/toSrvDelete"));
		request.setAttribute("resetUrl", Utils.url(inv, "/mail/srvList"));
		request.setAttribute("backUrl", Utils.url(inv, "/mail/list"));

		return "mail_list";
	}

	@UserAuthority(PageModel.mail_page)
	@Get("toSrvDelete")
	public String toSrvDelete(Invocation inv, @Param("srvId") int srvId, @Param("id") int id) throws IOException {
		HttpServletRequest request = inv.getRequest();
		int authority = AuthorityUtils.getAuthority(request);
		if (!AuthorityUtils.isAdminAuthority(authority))
			return "@管理员才能执行此操作！";

		if (id <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id为空");
		}
		// 连接服务器
		SaturnClient client = (SaturnClient) ServerUtils.createClient(srvId);
		if (client == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器错误");
		}

		// 执行删除
		RemoveMailWCS msg = RemoveMailWCS.newBuilder().setId(id).build();
		RemoveMailWSC retMsg = client.call(Head.H11003, msg, RemoveMailWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "成功", "mail/list?mainView=true");
	}

	public static final DateFormat showFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Post("listJsonS")
	public String listJsonS(Invocation inv, @Param("srvId") int srvId) throws IOException {
		if (srvId <= 0) {
			return BsgridUtils.emptyJson;
		}

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
		GetMailListWCS.Builder b = GetMailListWCS.newBuilder();
		b.setCount(pageSize);
		b.setStart(startRow);
		if (totalRows <= 0) {
			b.setGetcount(true); // 读取数量
		}

		// 发送并等待回馈
		GetMailListWSC retMsg = client.call(Head.H11002, b.build(), GetMailListWSC.class);
		if (retMsg == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "访问失败");
		}
		// 遍历
		int maxCount = retMsg.getMaxcount();
		totalRows = (maxCount > 0) ? maxCount : totalRows;

		// 遍历
		List<Protocol.MailMsg> list = retMsg.getListList();

		// 转换数据
		BsgridUtils.IConvert<Protocol.MailMsg> action = new BsgridUtils.IConvert<Protocol.MailMsg>() {

			@Override
			public boolean convert(Protocol.MailMsg model, Map<String, Object> map) {
				map.put("id", model.getMsgId());
				map.put("msgStr", model.getMsgStr());

				long startTime = model.getMsgTime();
				int liveTime = model.getLiveTime();
				long endTime = startTime + liveTime * 1000;
				map.put("startTime", showFormat.format(new Date(startTime)));
				map.put("endTime", showFormat.format(new Date(endTime)));

				// 奖励文本
				StringBuffer strBuf = new StringBuffer();
				List<Protocol.ItemMsg> list = model.getAwardListList();
				int awardCount = model.getAwardListCount();
				for (int i = 0; i < awardCount; i++) {
					Protocol.ItemMsg itemMsg = list.get(i);
					int itemId = itemMsg.getItemId();
					int itemCount = itemMsg.getItemCount();
					// 生成文本
					strBuf.append(itemId);
					strBuf.append("_");
					strBuf.append(itemCount);
					strBuf.append(";");
				}
				map.put("awardStr", strBuf.toString());

				return true;
			}

		};
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);
		return "@" + jsonStr;
	}
	
	private String getChestOrItemName(int id){
		
		ItemModel itemModel = ItemModelManager.getById(id);
		ChestModel chestModel = ChestModelManager.get(id);
		
		if(itemModel!=null)
			return itemModel.getItemName();
		if(chestModel!=null)
			return chestModel.getChestName();
		
		
		return "";
	}
}
