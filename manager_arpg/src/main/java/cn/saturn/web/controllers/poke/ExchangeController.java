package cn.saturn.web.controllers.poke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.poke.dao.PetModelManager;
import cn.saturn.web.controllers.poke.dao.PmBlinkExchangeModel;
import cn.saturn.web.controllers.poke.dao.PmBlinkExchangeModelManager;
import cn.saturn.web.controllers.poke.dao.PokeHunterModel;
import cn.saturn.web.controllers.poke.dao.PokeHunterModelManager;
import cn.saturn.web.controllers.poke.dao.PresetExchange;
import cn.saturn.web.controllers.server.dao.IClient;
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
import proto.ProtocolWeb.SetBlinkExchangeCS;
import proto.ProtocolWeb.SetBlinkExchangeSC;
import zyt.spring.component.ComponentManager;

/**
 * 
 * @author rodking
 *
 */
@Path("Exchange")
public class ExchangeController {

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
		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/poke/Exchange/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/poke/Exchange/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/poke/Exchange/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/poke/Exchange/toDelete"));
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/poke/Exchange/send"));
		Utils.setAttributeValue(inv, "checkUrl", Utils.url(inv, "/poke/Exchange/srvList"));

		return "exchange_list";
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
				PresetExchange presetDrop = model.getValue(PresetExchange.class);
				if (presetDrop != null) {
					map.put("openTime", presetDrop.getOpenTime());
					map.put("petId",
							presetDrop.getFirstId() + "," + presetDrop.getSecondId() + "," + presetDrop.getThirdId());
					map.put("selectSrvIds", presetDrop.getSrvs());
				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_exchange, action);
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
			PresetExchange presetData = model.getValue(PresetExchange.class);
			if (presetData != null) {
				request.setAttribute("cid", presetData.getId());
				request.setAttribute("cidStr", "disabled=\"disabled\"  ");

				request.setAttribute("openTime", presetData.getOpenTime());
				request.setAttribute("first", presetData.getFirstId());
				request.setAttribute("second", presetData.getSecondId());
				request.setAttribute("third", presetData.getThirdId());
				
				Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
			}

			request.setAttribute("btnStr", "修改");
		}

		request.setAttribute("editUrl", Utils.url(inv, "/poke/Exchange/toEdit"));

		return "exchange_edit";
	}

	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id, @Param("cid") int cid,
			@Param("open_state") int open_state, @Param("openDate") int openDate, @Param("openTime") String openTime,
			@Param("first") int first,
			@Param("second") int second, 
			@Param("third") int third,
			@Param("remark") String remark, @Param("srvIds") String srvIdStrs) throws Throwable {

		if (first == 0 || second == 0 || third == 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR,
					"闪光精灵"+(first == 0 ? "一" : second == 0 ? "二" : "三"));

		// 编辑
		PresetModel model = null;
		PresetExchange presetData = null;
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			//model.resetId();
			
			model.setType(PresetManager.presetType_exchange);
			presetData = new PresetExchange();

			// 检测pid是否被使用了
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_exchange);
			int count = (all != null) ? all.size() : 0;
			for (int i = 0; i < count; i++) {
				PresetModel model0 = all.get(i);
				PresetExchange presetData0 = model0.getValue(PresetExchange.class);
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
			presetData = model.getValue(PresetExchange.class);
			if (presetData == null) {
				presetData = new PresetExchange();
			}
		}

		model.setRemark(remark);
		presetData.setOpenTime(openTime);
		presetData.setFirstId(first);
		presetData.setSecondId(second);
		presetData.setThirdId(third);
		
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

	public void pokeUpdateByTime(PresetExchange presetData) {
		if (presetData.getOpen_state() == 0 && presetData.getOpenTime().equals(DateUtils.getNowDay())) {
//			pokeMgr.writeReplace(presetData);

			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerModel> sModel = new ArrayList<>();
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
					sModel.add(sm);
				isSvr = false;
			}
			sendToSvr(sModel, presetData);
		}
	}

	private void pokeUpdateByDate(PresetExchange presetData) {
		// 2.按照开服时间
		try {
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerModel> sModel = new ArrayList<>();
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
						sModel.add(sm);
						isSvr = false;
					}
				}
			}

			// 如果有满足条件的这里统一发送
			if (presetData != null && sModel.size() > 0) {
//				pokeMgr.writeReplace(presetData);
				sendToSvr(sModel, presetData);

				// 发送完毕后清除数据为下一次计算做准备
				sModel.clear();
				presetData = null;
			}

		} catch (Exception e) {
		}
	}
	
	private void sendToSvr(List<ServerModel> list, PresetExchange presetData){
		List<Integer> ids = new ArrayList<>();
		ids.add(presetData.getFirstId());
		ids.add(presetData.getSecondId());
		ids.add(presetData.getThirdId());
		SetBlinkExchangeCS.Builder builder = SetBlinkExchangeCS.newBuilder();
		builder.addAllBlinkIds(ids);
		if(list != null && list.size()>0){
			IClient client;
			for(ServerModel model : list){
				client = model.createClient();
				client.call(Head.H22001, builder.build(), SetBlinkExchangeSC.class);
			}
		}
	}

	// 删除热点精灵配置
	@LoginCheck
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "poke/Exchange/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "poke/Exchange/list?mainView=true");
	}

	@MainView
	@LoginCheck
	@Get("send")
	public String send(Invocation inv) throws Throwable {
		
		HttpServletRequest request = inv.getRequest();
		List<PresetModel> models = PresetManager.getPresetList(PresetManager.presetType_exchange);
		if (null != models) {
			for (PresetModel model : models) {
				PresetExchange presetData = model.getValue(PresetExchange.class);
				if (null != presetData) {
					String presetStr = presetData.getFirstId() + ";" + presetData.getSecondId() + ";"
							+ presetData.getThirdId();

					request.setAttribute("id", presetData.getId());
					request.setAttribute("presetDes", presetStr);

				}
			}
		}

		return "exchange_send";
	}

	@LoginCheck
	@Post("toSend")
	public String toSend(Invocation inv, @Param("srvIds") String srvIdStrs, @Param("pokeId") int pokeId)
			throws Throwable {

		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];

		// 拿出选择的
		List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_exchange);
		PresetExchange pPoke = null;

		for (PresetModel model : pModels) {
			PresetExchange m = model.getValue(PresetExchange.class);
			if (m != null && m.getId() == pokeId) {
				pPoke = m;
				break;
			}
		}

//		pokeMgr.writeReplace(pPoke);

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
		List<PresetModel> pMgrs = PresetManager.getPresetList(PresetManager.presetType_exchange);
		List<PresetExchange> list = new ArrayList<>();

		for (PresetModel model : pMgrs) {
			PresetExchange presetDrop = model.getValue(PresetExchange.class);
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
				PresetExchange model = list.get(i);
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

		List<PmBlinkExchangeModel> list = PmBlinkExchangeModelManager.getModels();
		int size = list.size();
		List<Object> lists = new ArrayList<>();
		Map<Object, Object> defMap = new HashMap<>();
		defMap.put("id", 0);
		defMap.put("name", "--请选择--");
		lists.add(defMap);
		for (int i = 0; i < size; i++) {
			Map<Object, Object> rowItem = new HashMap<>();
			PmBlinkExchangeModel model = list.get(i);

			rowItem.put("id", model.getTargetBlinkPetId());
			rowItem.put("name", model.getTargetBlinkPetId() + "  " + PetModelManager.getModelByPetId(model.getTargetBlinkPetId()).getPetName());

			lists.add(rowItem);
		}

		return "@" + JSONObject.toJSONString(lists);
	}

}
