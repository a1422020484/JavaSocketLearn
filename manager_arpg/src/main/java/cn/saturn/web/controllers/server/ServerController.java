package cn.saturn.web.controllers.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.controllers.server.dao.SectionManager;
import cn.saturn.web.controllers.server.dao.SectionModel;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;

@Path("")
public class ServerController {

	@Resource
	ServerDAO serverDAO;

	@Resource
	SectionManager sectionMgr;
	
	@Resource
	ServerMergeDAO serverMergeDAO;
	
	protected static final Logger serverHandlogger = LoggerFactory.getLogger(LogType.serverHand);
	
	@UserAuthority(PageModel.server_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/server/list");
	}

	@UserAuthority(PageModel.server_page)
	@Get("toSctSrv")
	public String toSelectServer(Invocation inv) {
		String value = inv.getParameter("value");

		// 检测登陆状态
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		int curSrvId = WebUtils.setServerSelected(session, value);
		// session.setAttribute("select", "1");
		return "@curSrvId=" + curSrvId;
	}

	@UserAuthority(PageModel.server_page)
	@MainView
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		if (id > 0) {
			// 编辑
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			ServerModel model = serverComponent.find(id);
			if (model != null) {
				// 找到对应的数据
				request.setAttribute("id", id);
				request.setAttribute("srvname", model.getName());
				request.setAttribute("srvurl", model.getUrl());
				request.setAttribute("remark", model.getRemark());
				request.setAttribute("state", model.getState());
				request.setAttribute("section", model.getSection());
				request.setAttribute("maintainText", model.getMaintainText());
				request.setAttribute("priority", model.getPriority());
				request.setAttribute("platforms", model.getPlatforms());
				request.setAttribute("open_time", model.getOpen_time());

				// 操作选择
				String operateStr = (model.isOperate()) ? "checked=\"checked\"" : "";
				request.setAttribute("operateStr", operateStr);

				// 定时开启时间
				int time = model.getOpenTime();
				// long timeL = time * 1000;
				// long nowTimeL = System.currentTimeMillis();
				// long showTimeL = (timeL < nowTimeL) ? nowTimeL : timeL;
				if (time > 0) {
					long showTimeL = (long) time * 1000;
					String openTimeStr = WebUtils.Html.getDateTimeStr(showTimeL);
					request.setAttribute("openTime", openTimeStr);
				}

				// 推荐选择
				String recommendStr = (model.getRecommend() != 0) ? "checked=\"checked\"" : "";
				request.setAttribute("recommendStr", recommendStr);

				request.setAttribute("btn", "修改");
			} else {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没找到对应信息");
			}
		} else {
			// 新增
			request.setAttribute("btn", "新增");
		}

		request.setAttribute("toEdit", Utils.url(inv, "/server/toEdit"));
		return "edit";
	}

	@UserAuthority(PageModel.server_page)
	@LoginCheck
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("id") int id, @Param("newid") int newid,
			@NotBlank @Param("srvname") String srvName, @NotBlank @Param("srvurl") String srvUrl,
			@Param("state") int state, @Param("priority") int priority, @Param("remark") String remark,
			@Param("maintainText") String maintainText, @Param("platforms") String platforms,
			@Param("openTime") String openTime, @Param("open_time") String open_time) {
		// 判断ID是否重复
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		if (newid != id) {
			ServerModel serverModel = serverComponent.find(newid);
			if (serverModel != null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在重复ID");
			}
		}
		// 区
		int section = zyt.utils.Utils.ObjectUtils.baseValue(inv.getParameter("section"), Integer.class);
		String recommendStr = inv.getParameter("recommend");
		int recommend = (recommendStr != null) ? 1 : 0;
		// 操作
		String operateStr = inv.getParameter("operate");
		boolean operate = (operateStr != null);

		// 读取服务器
		ServerModel model = null;
		if (id <= 0) {
			// 检测是否有维护文本
			maintainText = (!StringUtils.isEmpty(maintainText)) ? maintainText : "服务器维护中...";
			// 新增
			model = new ServerModel();
		} else {
			// 判断维护状态文本是否空
			if (state == ServerModel.srvState_Close && StringUtils.isEmpty(maintainText)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "维护状态维护文本不能为空");
			}

			// 修改
			model = serverComponent.find(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
			}
		}
		// 判断定时开服时间
		try {
			int time = WebUtils.Html.getDateTimeLoacl0(openTime);
			if (time > 0) {
				// 判断是否是关闭状态
				if (!(state == ServerModel.srvState_Hide || state == ServerModel.srvState_Close)) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "当前状态不用设置开启时间.");
				}

				// 判断时间是否超过
				long nowTimeL = System.currentTimeMillis();
				int nowTime = (int) (nowTimeL * 0.001);
				if (time > nowTime) {
					model.setOpenTime(time);
				} else {
					// model.setOpenTime(0);
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "定时开启时间过旧.");
				}
			} else {
				model.setOpenTime(0);
			}
		} catch (Exception e) {
		}
		if (open_time == null || open_time.equals(""))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "请填写用于查询的开服时间.");
		// 修改参数
		model.setName(srvName);
		model.setUrl(srvUrl.trim());
		model.setState(state);
		model.setRemark((remark == null) ? "" : remark);
		model.setSection(section);
		model.setRecommend(recommend);
		model.setPriority(priority);
		model.setOperate(operate);
		model.setPlatforms(platforms);
		model.setOpen_time(open_time);
		
		// 设置维护文本
		model.setMaintainText(maintainText);
		if (id == 0 || newid != id) {
			if (id > 0) {
				// 删除旧ID
				ServerModel serverModel = serverComponent.remove(id);
				if (serverModel == null) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
				}
			}
			// 另存新服务器
			model.setId(newid);
			if (!serverComponent.add(model)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
			}
		} else {
			// 直接保存修改保存
			if (!serverComponent.update(model)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
			}
		}
		
		// 删除 redis 中的数据
	    RedisUtils.del(RedisKeys.K_SERVER);
		
		 ServerMergeModel  serverMergeModel =null ;
		 if(id == 0 || newid != id){
			 Date date=new Date();
			 serverMergeModel= new ServerMergeModel();
		 serverMergeModel.setId(newid);
		 serverMergeModel.setName(srvName);
		 serverMergeModel.setPid(newid);
		 serverMergeModel.setState(1);
		 serverMergeModel.setMertime(date);
		 //默认设置服务器为非测试服务器
		 serverMergeModel.setInvalid(1);
		 serverMergeDAO.insertOrUpdate(serverMergeModel);
		 }
		 
		 if(id != 0 || newid == id){
			 
			 serverMergeModel = serverMergeDAO.getServerMergeModel(newid);
			 serverMergeModel.setName(srvName);
			 serverMergeDAO.insertOrUpdate(serverMergeModel);
		 }
		// TODO 创建日志(以后可以优化)
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		int  userId=(userModel == null)?0:userModel.getId();
		String userName=(userModel == null)?"unknow":userModel.getUsername();
		serverHandlogger.info("--单服操作 -- UserId="+userId+",UserName="+userName+",Id="+id+",newid="+newid+",srvName="+srvName+",srvUrl="+
		srvUrl+",state="+state+",priority="+priority+",remark="+remark+",maintainText="+maintainText+",platforms="+platforms+",openTime="+openTime+",open_time="+open_time);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功", "server?mainView=true");

	}

	public static final String defaultValueS = "--";
	public static final int defaultValueI = -1;

	@UserAuthority(PageModel.server_page)
	@MainView
	@LoginCheck
	@Get("batchEdit")
	public String batchEdit(Invocation inv, @Param("ids") String idStrs) throws Throwable {

		// 解析id
		int[] ids = null;
		try {
			String[] strs = idStrs.split(",");
			int count = (strs != null) ? strs.length : 0;
			if (count <= 0) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有选择服务器.");
			}

			// 遍历解析
			ids = new int[count];
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			for (int i = 0; i < count; i++) {
				// 解析id
				int id = Integer.valueOf(strs[i]);
				ids[i] = id;
				// 检测是否存在服务器
				ServerModel model = serverComponent.find(id);
				if (model == null) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID[" + id + "]不存在.");
				}
			}
		} catch (Exception e) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "解析ID错误");
		}

		// 页面生成
		HttpServletRequest request = inv.getRequest();
		// 找到对应的数据
		request.setAttribute("id", idStrs);
		request.setAttribute("idStr", "disabled=\"disabled\" ");

		request.setAttribute("srvname", defaultValueS);
		request.setAttribute("serNameStr", "disabled=\"disabled\" ");

		request.setAttribute("srvurl", defaultValueS);
		request.setAttribute("serUrlStr", "disabled=\"disabled\" ");

		request.setAttribute("remark", defaultValueS);
		request.setAttribute("remarkStr", "disabled=\"disabled\" ");

		// 区
		// request.setAttribute("section", "--");
		request.setAttribute("sectionStr", "disabled=\"disabled\" ");
		request.setAttribute("stateEableE", true);

		// 状态
		request.setAttribute("state", defaultValueI);

		request.setAttribute("priority", defaultValueS);
		request.setAttribute("priorityStr", "disabled=\"disabled\" ");

		// 推荐
		request.setAttribute("recommendStr", "disabled=\"disabled\" ");

		// 操作选择
		request.setAttribute("operateStr", "disabled=\"disabled\" ");

		request.setAttribute("maintainText", defaultValueS);
		request.setAttribute("platforms", defaultValueS);

		// 按钮提交
		request.setAttribute("btn", "批量修改");
		request.setAttribute("toEdit", Utils.url(inv, "/server/toBatchEdit"));

		return "edit";
	}

	@UserAuthority(PageModel.server_page)
	@Post("toBatchEdit")
	public String toBatchEdit(Invocation inv, @Param("id") String idStrs, @Param("state") int state,
			@Param("remark") String remark, @Param("maintainText") String maintainText,
			@Param("platforms") String platforms) {

		// 解析id
		int[] ids = null;
		ServerModel[] servers = null;
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		try {
			String[] strs = idStrs.split(",");
			int count = (strs != null) ? strs.length : 0;
			if (count <= 0) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有选择服务器.");
			}

			// 遍历解析
			ids = new int[count];
			servers = new ServerModel[count];
			for (int i = 0; i < count; i++) {
				// 解析id
				int id = Integer.valueOf(strs[i]);
				ids[i] = id;
				// 检测是否存在服务器
				ServerModel model = serverComponent.find(id);
				if (model == null) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "服务器ID[" + id + "]不存在.");
				}
				servers[i] = model;
			}
		} catch (Exception e) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "解析ID错误");
		}

		// 验证数据是否修改
		remark = (remark != null && !remark.equals(defaultValueS)) ? remark : null;
		maintainText = (maintainText != null && !maintainText.equals(defaultValueS)) ? maintainText : null;
		platforms = (platforms != null && !platforms.equals(defaultValueS)) ? platforms : null;

		// 判断维护状态文本是否空
		if (state == ServerModel.srvState_Close && StringUtils.isEmpty(maintainText)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "维护状态维护文本不能为空");
		}

		// 批量修改
		boolean update = false;
		int count = (servers != null) ? servers.length : 0;
		for (int i = 0; i < count; i++) {
			ServerModel model = servers[i];
			int id = (int) model.getId();
			// 修改数据
			update = false;
			if (remark != null) {
				model.setRemark(remark);
				update = true;
			}
			if (maintainText != null) {
				model.setMaintainText(maintainText);
				update = true;
			}
			if (platforms != null) {
				model.setPlatforms(platforms);
				update = true;
			}
			if (state >= 0) {
				model.setState(state);
				update = true;
			}

			// 判断修改
			if (!update) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有修改[" + i + "]" + id);
			}

			// 修改保存
			// 直接保存修改保存
			if (!serverComponent.update(model)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败[" + i + "]" + id);
			}

			RedisUtils.del(RedisKeys.K_SERVER);

		}
		
		// TODO 创建日志(以后可以优化)
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		int  userId=(userModel == null)?0:userModel.getId();
		String userName=(userModel == null)?"unknow":userModel.getUsername();
		serverHandlogger.info("-多服操作 -- UserId="+userId+",UserName="+userName+",Id="+idStrs+",state="+state+",remark="+remark+",maintainText="+maintainText+",platforms="+platforms);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功", "server?mainView=true");
	}

	@UserAuthority(PageModel.server_page)
	@LoginCheck
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("id") int id) throws Throwable {

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.remove(id);
		if (serverModel == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}
		serverMergeDAO.Delete(id);
		RedisUtils.del(RedisKeys.K_SERVER);
		// TODO 创建日志(以后可以优化)
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		int  userId=(userModel == null)?0:userModel.getId();
		String userName=(userModel == null)?"unknow":userModel.getUsername();
		serverHandlogger.info("-服务器删除 -- UserId="+userId+",UserName="+userName+",Id="+id);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除成功", "server?mainView=true");
	}

	@MainView
	@UserAuthority(PageModel.server_page)
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> list = serverComponent.getList();
		List<SectionModel> lst = sectionMgr.getSectionList();

		request.setAttribute("tableUrl", Utils.url(inv, "/server/listJson"));
		request.setAttribute("addUrl", Utils.url(inv, "/server/edit"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/server/toDelete"));
		request.setAttribute("editUrl", Utils.url(inv, "/server/edit"));
		request.setAttribute("batchEditUrl", Utils.url(inv, "/server/batchEdit"));
		Utils.setAttributeValue(inv, "sections", lst);

		request.setAttribute("list", list);

		return "list";
	}

	int sectionId = -1; // 区分组

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// return "@";
		sectionId = Utils.getSessionValue(inv, "sectionId", Integer.class);
		String isSubmit = inv.getParameter("isSubmit");
		if (null != isSubmit && isSubmit.equals("1")) {
			sectionId = Utils.getIntParameter(inv, "sectionId");
			Utils.setSessionValue(inv, "sectionId", sectionId);
		}
		if (sectionId == 0)
			sectionId = -1;

		HttpServletRequest request = inv.getRequest();
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		int size = serverComponent.getSize();
		// 读取参数
		int totalRows = size;
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// Object totalRows0 = request.getParameter("totalRows");

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);
		// 遍历
		List<ServerModel> list = serverComponent.getList(startRow, pageSize);

		// 转换接口
		BsgridUtils.IConvert<ServerModel> action = new BsgridUtils.IConvert<ServerModel>() {

			@Override
			public boolean convert(ServerModel model, Map<String, Object> map) {
				if (sectionId != -1 && sectionId != model.getSection())
					return false;

				map.put("id", model.getId());
				map.put("name", model.getName());
				map.put("state", model.getStateStr());
				// 是否推荐
				int priority = model.getPriority();
				String recommendStr = (model.getRecommend() == 1) ? "是[" + priority + "]" : "否[" + priority + "]";
				map.put("recommend", recommendStr);
				map.put("platforms", model.getPlatforms());
				map.put("url", model.getUrl());
				map.put("opentime", model.getOpen_time().trim().substring(0,10));
				map.put("remark", model.getRemark());
				// 区信息
				String sectionStr = WebUtils.sectionStr(model.getSection());
				map.put("section", sectionStr);
				return true;
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);

		return "@" + jsonStr;
	}

	@Post("getSelect2EasyUi")
	public String getSelect2EasyUi(Invocation inv) {
		List<ServerModel> servers = serverDAO.getList();
		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", "-1");
		map.put("name", "--请选择--");
		objList.add(map);

		for (int i = 0; i < servers.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			ServerModel item = servers.get(i);
			itemMap.put("id", item.getId());
			itemMap.put("name", item.getId() + "  " + item.getName());
			objList.add(itemMap);
		}
		return "@" + JSONObject.toJSONString(objList);
	}
}
