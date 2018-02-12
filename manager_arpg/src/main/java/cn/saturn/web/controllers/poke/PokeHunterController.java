package cn.saturn.web.controllers.poke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.banner.dao.BannerModelManager;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.poke.dao.PokeHunterModel;
import cn.saturn.web.controllers.poke.dao.PokeHunterModelManager;
import cn.saturn.web.controllers.poke.dao.PresetPokeHunter;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.server.dao.ServerXFtpDAO;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ResultMsgUtils;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.sftp.SftpTools;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import zyt.spring.component.ComponentManager;

/**
 * 
 * @author rodking
 *
 */
@Path("PokeHunter")
public class PokeHunterController {

	@Resource
	PokeHunterModelManager pokeMgr;

	@Resource
	ServerXFtpDAO serverXFtpDao;
	
	@Resource
	PresetDAO presetDAO;
	
	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/poke/PokeHunter/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/poke/PokeHunter/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/poke/PokeHunter/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/poke/PokeHunter/toDelete"));
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/poke/PokeHunter/send"));
		Utils.setAttributeValue(inv, "checkUrl", Utils.url(inv, "/poke/PokeHunter/srvList"));

		return "pokeHunter_list";
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
				PresetPokeHunter presetDrop = model.getValue(PresetPokeHunter.class);
				if (presetDrop != null) {
					map.put("openTime", presetDrop.getOpenTime());
					map.put("pokeId",
							presetDrop.getFirstId() + "," + presetDrop.getSecondId() + "," + presetDrop.getThirdId());
					map.put("selectSrvIds", presetDrop.getSrvs());
				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_poke, action);
	}

	@MainView
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("openTime", DateUtils.getNowDay());

		if (id <= 0) {
			request.setAttribute("isNew", "true");
			request.setAttribute("cidStr", "");
			request.setAttribute("btnStr", "新增");
		} else {
			PresetModel model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据:" + id);
			}

			request.setAttribute("isNew", "false");
			String remark = model.getRemark();
			request.setAttribute("remark", remark);

			// 解析数据
			PresetPokeHunter presetData = model.getValue(PresetPokeHunter.class);
			if (presetData != null) {
				request.setAttribute("cid", presetData.getId());
				request.setAttribute("cidStr", "disabled=\"disabled\"  ");

				request.setAttribute("openTime", presetData.getOpenTime());
				request.setAttribute("first", presetData.getFirstId());
				request.setAttribute("second", presetData.getSecondId());
				request.setAttribute("third", presetData.getThirdId());

				request.setAttribute("first_min_bd", presetData.getFirst_min_bd());
				request.setAttribute("first_max_bd", presetData.getFirst_max_bd());
				request.setAttribute("second_min_bd", presetData.getSecond_min_bd());
				request.setAttribute("second_max_bd", presetData.getSecond_max_bd());
				request.setAttribute("third_min_bd", presetData.getThird_min_bd());
				request.setAttribute("third_max_bd", presetData.getThird_max_bd());
				Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
			}

			request.setAttribute("btnStr", "修改");
		}

		request.setAttribute("editUrl", Utils.url(inv, "/poke/PokeHunter/toEdit"));

		return "pokeHunter_edit";
	}

	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id, @Param("cid") int cid,
			@Param("open_state") int open_state, @Param("openDate") int openDate, @Param("openTime") String openTime,
			@Param("first") int first, @Param("first_min_bd") int first_min_bd, @Param("first_max_bd") int first_max_bd,
			@Param("second") int second, @Param("second_min_bd") int second_min_bd,
			@Param("second_max_bd") int second_max_bd, @Param("third") int third,
			@Param("third_min_bd") int third_min_bd, @Param("third_max_bd") int third_max_bd,
			@Param("remark") String remark, @Param("srvIds") String srvIdStrs) throws Throwable {

		if (first == 0 || second == 0 || third == 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR,
					(first == 0 ? "初级" : second == 0 ? "中级" : "高级") + "热点精灵");

		if (first_min_bd < 0 || first_max_bd < 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "初级狩猎场保底数必须大于等于 0");

		if (second_min_bd < 0 || second_max_bd < 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "中级狩猎场保底数必须大于等于 0");

		if (third_min_bd < 0 || third_max_bd < 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "高级狩猎场保底数必须大于等于 0");

		if (first_max_bd - first_min_bd < 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "初级狩猎场  最低保底数不能大于最高保底数");

		if (second_max_bd - second_min_bd < 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "中级狩猎场 最低保底数不能大于最高保底数");

		if (third_max_bd - third_min_bd < 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "高级狩猎场 最低保底数不能大于最高保底数");

		// 编辑
		PresetModel model = null;
		PresetPokeHunter presetData = null;
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			//model.resetId();
			model.setType(PresetManager.presetType_poke);
			presetData = new PresetPokeHunter();

			// 检测pid是否被使用了
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_poke);
			int count = (all != null) ? all.size() : 0;
			for (int i = 0; i < count; i++) {
				PresetModel model0 = all.get(i);
				PresetPokeHunter presetData0 = model0.getValue(PresetPokeHunter.class);
				if (presetData0 == null) {
					continue;
				}
				// 检测ID
				if (presetData0.getId() == cid) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "ID已存在相同.");
				}

				// 检测日期
				if (presetData0.getOpenTime().equals(openTime)) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同的日期.");
				}
			}

			presetData.setId(cid);
		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			}
			//model.setId(id);
			presetData = model.getValue(PresetPokeHunter.class);
			if (presetData == null) {
				presetData = new PresetPokeHunter();
			}
		}

		model.setRemark(remark);
		presetData.setOpenTime(openTime);
		presetData.setFirstId(first);
		presetData.setSecondId(second);
		presetData.setThirdId(third);

		presetData.setFirst_min_bd(first_min_bd);
		presetData.setFirst_max_bd(first_max_bd);
		presetData.setSecond_min_bd(second_min_bd);
		presetData.setSecond_max_bd(second_max_bd);
		presetData.setThird_max_bd(third_max_bd);
		presetData.setThird_min_bd(third_min_bd);
		presetData.setOpenDate(openDate);
		presetData.setOpen_state(open_state);

		// 保存服务器id
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
		presetData.setSrvs(srvIds);

		// 设置数据
		if (!model.setValue(presetData))

		{
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

		// 发送检查
		pokeUpdateByTime(presetData);
		pokeUpdateByDate(presetData);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "处理成功.<br>");
	}

	public void pokeUpdateByTime(PresetPokeHunter presetData) {
		if (presetData.getOpen_state() == 0 && presetData.getOpenTime().equals(DateUtils.getNowDay())) {
			pokeMgr.writeReplace(presetData);

			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel = new ArrayList<>();
			int[] openSvrs = presetData.getSrvs();
			if(openSvrs == null || openSvrs.length == 0)
				return;
			boolean isSvr = false;
			for (ServerModel sm : srvlist) {
				ServerXFtpModel model = serverXFtpDao.getBySid((int) sm.getId());
				
				for(int i : openSvrs){
					if(i == sm.getId()){
						isSvr = true;
						break;
					}
				}
				if (isSvr && model != null)
					sModel.add(model);
				isSvr = false;
			}
			SftpTools.exec(sModel, PokeHunterModelManager.writeSingle);
		}
	}

	private void pokeUpdateByDate(PresetPokeHunter presetData) {
		// 2.按照开服时间
		try {
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel = new ArrayList<>();
			int[] openSvrs = presetData.getSrvs();
			if(openSvrs == null || openSvrs.length == 0)
				return;
			boolean isSvr = false;
			for (ServerModel sm : srvlist) {
				// 遍历服务器模块
				ServerXFtpModel sftpModel = serverXFtpDao.getBySid((int) sm.getId());
				if (sftpModel != null) {
					String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), presetData.getOpenDate());
					// 如果时间能匹配上
					if (presetData.getOpen_state() == 1 && openTimeStr.equals(DateUtils.getNowDay())) {
						
						for(int i : openSvrs){
							if(i == sm.getId()){
								isSvr = true;
								break;
							}
						}
						if(isSvr)
						sModel.add(sftpModel);
						isSvr = false;
					}
				}
			}

			// 如果有满足条件的这里统一发送
			if (presetData != null && sModel.size() > 0) {
				pokeMgr.writeReplace(presetData);
				SftpTools.exec(sModel, PokeHunterModelManager.writeSingle);

				// 发送完毕后清除数据为下一次计算做准备
				sModel.clear();
				presetData = null;
			}

		} catch (Exception e) {
		}
	}

	// 删除热点精灵配置
	@LoginCheck
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "poke/PokeHunter/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "poke/PokeHunter/list?mainView=true");
	}

	@MainView
	@LoginCheck
	@Get("send")
	public String send(Invocation inv) throws Throwable {
		
		HttpServletRequest request = inv.getRequest();
		List<PresetModel> models = PresetManager.getPresetList(PresetManager.presetType_poke);
		if (null != models) {
			for (PresetModel model : models) {
				PresetPokeHunter presetData = model.getValue(PresetPokeHunter.class);
				if (null != presetData) {
					String presetStr = presetData.getFirstId() + ";" + presetData.getSecondId() + ";"
							+ presetData.getThirdId();

					request.setAttribute("id", presetData.getId());
					request.setAttribute("presetDes", presetStr);

				}
			}
		}

		return "pokeHunter_send";
	}

	@LoginCheck
	@Post("toSend")
	public String toSend(Invocation inv, @Param("srvIds") String srvIdStrs, @Param("pokeId") int pokeId)
			throws Throwable {

		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];

		// 拿出选择的
		List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_poke);
		PresetPokeHunter pPoke = null;

		for (PresetModel model : pModels) {
			PresetPokeHunter m = model.getValue(PresetPokeHunter.class);
			if (m != null && m.getId() == pokeId) {
				pPoke = m;
				break;
			}
		}

		pokeMgr.writeReplace(pPoke);

		// 获取要发送服务器id
		List<ResultMsg> sConfMsg = new ArrayList<>();
		List<ServerXFtpModel> sModel = new ArrayList<>();
		for (int srvId : srvIds) {
			ServerXFtpModel model = serverXFtpDao.getBySid(srvId);
			if (model != null)
				sModel.add(model);
			else
				sConfMsg.add(new ResultMsg(ResultMsg.fail, "服务器： " + srvId + " 没有配置"));
		}

		// 发送文件并获得反馈消息
		List<ResultMsg> msgs = SftpTools.exec(sModel, PokeHunterModelManager.writeSingle);

		// 等待 2秒后上传刷新指令
		//Thread.sleep(2 * 1000l);

		// 刷新热点精灵
		//List<ResultMsg> sendRetMsg = new ArrayList<>();
		//for (int srvId : srvIds) {
		//	ServerModel serverModel = ServerUtils.getServer(srvId, true);
		//	List<String> cmds = StringExtUtil.toList("/refreshHunt 3", ";");
		//	sendRetMsg = ProtocolTools.toSendGMCmdWSC(serverModel, cmds);
		//}

		String retStr = ResultMsgUtils.getResult2Msg(sConfMsg, msgs);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, retStr);
	}

	@Post("getGrid2EasyUi")
	public String getGrid2EasyUi(Invocation inv) throws Throwable {
		// StringBuilder sb = new StringBuilder();
		List<PresetModel> pMgrs = PresetManager.getPresetList(PresetManager.presetType_poke);
		List<PresetPokeHunter> list = new ArrayList<>();

		for (PresetModel model : pMgrs) {
			PresetPokeHunter presetDrop = model.getValue(PresetPokeHunter.class);
			if (presetDrop != null)
				list.add(presetDrop);
		}

		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", list.size());
		List<Object> rowsList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			Map<Object, Object> rowItem = new HashMap<>();
			rowItem.put("id", i);
			if (i < list.size()) {
				PresetPokeHunter model = list.get(i);
				int pokeId = model.getId();
				String pokeDescription = model.getFirstId() + ";" + model.getSecondId() + ";" + model.getThirdId();
				rowItem.put("pokeId", pokeId);
				rowItem.put("pokeDescription", pokeDescription);
			}

			rowsList.add(rowItem);
		}

		dataObj.put("rows", rowsList);

		return "@" + JSONObject.toJSONString(dataObj);
	}

	/**
	 * html 中 easyui 框架中的<br/>
	 * select 控件数据<br />
	 * 
	 * @param inv
	 * @return
	 */
	@Post("getSelect2EasyUi")
	public String getSelect2EasyUi(Invocation inv, @Param("type") int type) {

		List<PokeHunterModel> list = PokeHunterModelManager.getListByType(type);
		int size = list.size();
		List<Object> lists = new ArrayList<>();
		Map<Object, Object> defMap = new HashMap<>();
		defMap.put("id", 0);
		defMap.put("name", "--请选择--");
		lists.add(defMap);
		for (int i = 0; i < size; i++) {
			Map<Object, Object> rowItem = new HashMap<>();
			PokeHunterModel model = list.get(i);

			rowItem.put("id", model.getPokeId());
			rowItem.put("name", model.getPokeId() + "  " + model.getPokeName());

			lists.add(rowItem);
		}

		return "@" + JSONObject.toJSONString(lists);
	}

}
