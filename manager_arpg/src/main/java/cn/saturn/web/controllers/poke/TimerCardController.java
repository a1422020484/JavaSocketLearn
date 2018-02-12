package cn.saturn.web.controllers.poke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.item.dao.DropExtraManager;
import cn.saturn.web.controllers.item.dao.DropExtraModel;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import cn.saturn.web.controllers.item.dao.LotteryModel;
import cn.saturn.web.controllers.item.dao.LotteryModelManager;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.poke.dao.DropExtra;
import cn.saturn.web.controllers.poke.dao.TimerCard;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb;
import proto.ProtocolWeb.CGCardPackageTime;
import proto.ProtocolWeb.CGCardPackageTimeLImitWCS;
import proto.ProtocolWeb.CGCardPackageTimeLimit;
import proto.ProtocolWeb.DropExtraItem;
import proto.ProtocolWeb.DropExtraWCS;
import zyt.utils.ArrayUtils;
import zyt.utils.ListUtils;

@Path("timerCard")
public class TimerCardController {
	
	@Resource
	ServerDAO serverDAO;

	@Resource
	PresetDAO presetDAO;

	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/poke/timerCard/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/poke/timerCard/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/poke/timerCard/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/poke/timerCard/toDelete"));
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/poke/timerCard/send"));
		Utils.setAttributeValue(inv, "checkUrl", Utils.url(inv, "/poke/timerCard/srvList"));

		return "timercard_list";
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
				TimerCard presetModel = model.getValue(TimerCard.class);
				if (presetModel != null) {
					map.put("pid", presetModel.getId());
					map.put("timeCardName", presetModel.getTimeCardName());
					map.put("openTime",
							((presetModel.getOpen_state() == 0)? presetModel.getOpenTime() + "至" + presetModel.getEndTime()
									: "开服第" + presetModel.getOpenDate() + "至" + presetModel.getEndDate() + "天"));
					map.put("selectSrvIds", presetModel.getSrvs());
					
					map.put("openTimeShow",
							((presetModel.getOpen_state() == 0)? presetModel.getOpenTimeShow() + "至" + presetModel.getEndTimeShow()
									: "开服第" + presetModel.getOpenDateShow() + "至" + presetModel.getEndDateShow() + "天"));
					map.put("selectSrvIds", presetModel.getSrvs());
					
				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_timerCard, action);
	}

	@MainView
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("openTime", DateUtils.getNowDay());
		request.setAttribute("openTimeShow", DateUtils.getNowDay());

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
			TimerCard presetData = model.getValue(TimerCard.class);
			if (presetData != null) {
				request.setAttribute("cid", presetData.getId());
				request.setAttribute("cidStr", "disabled=\"disabled\"  ");

				request.setAttribute("timeCardName", presetData.getTimeCardName());
				request.setAttribute("open_state", presetData.getOpen_state());
				request.setAttribute("openDate", presetData.getOpenDate());
				request.setAttribute("endDate", presetData.getEndDate());
				request.setAttribute("openTime", presetData.getOpenTime());
				request.setAttribute("endTime", presetData.getEndTime());
				
				request.setAttribute("openDateShow", presetData.getOpenDateShow());
				request.setAttribute("endDateShow", presetData.getEndDateShow());
				request.setAttribute("openTimeShow", presetData.getOpenTimeShow());
				request.setAttribute("endTimeShow", presetData.getEndTimeShow());
				
				Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
			}

			request.setAttribute("btnStr", "修改");
		}

		request.setAttribute("editUrl", Utils.url(inv, "/poke/timerCard/toEdit"));

		return "timercard_edit";
	}

	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id, @Param("cid") int cid,
			@Param("timeCardName") String timeCardName, @Param("open_state") int open_state, @Param("openDate") int openDate,
			@Param("endDate") int endDate, @Param("openTime") String openTime, @Param("endTime") String endTime,
			@Param("openDateShow") int openDateShow,@Param("endDateShow") int endDateShow, 
			@Param("openTimeShow") String openTimeShow, @Param("endTimeShow") String endTimeShow,
			@Param("remark") String remark, @Param("srvIds") String srvIdStrs) throws Throwable {

		// 编辑
		PresetModel model = null;
		TimerCard presetData = null;
		
		// 判断日期
		if ( open_state == 0){
			if (!(WebUtils.Html.getDate0(openTimeShow) <=WebUtils.Html.getDate0(openTime)) &&
					(WebUtils.Html.getDate0(openTime) <=WebUtils.Html.getDate0(openTime)) &&
					(WebUtils.Html.getDate0(endTime) <=WebUtils.Html.getDate0(endTimeShow))) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "时间配置错误(开始展示时间<=开始时间<=结束时间<=结束展示时间)");
				
		}
		}
		
		// 检测开服日期
		if (open_state == 1 ){
			if( !(openDateShow <=openDate) && (openDate<=endDate) && (endDate <=endDateShow)  ) {
				
				return "@" +PageNoticeUtil.notic(PageNoticeUtil.ERROR, "时间配置错误(开始展示时间<=开始时间<=结束时间<=结束展示时间)");
			}	
		}
		
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			// model.resetId();

			model.setType(PresetManager.presetType_timerCard);
			presetData = new TimerCard();

			// 检测pid是否被使用了
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_timerCard);
			int count = (all != null) ? all.size() : 0;
			for (int i = 0; i < count; i++) {
				PresetModel model0 = all.get(i);
				TimerCard presetData0 = model0.getValue(TimerCard.class);
				if (presetData0 == null) {
					continue;
				}
				// 检测ID
				if (presetData0.getId() == cid) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "ID已存在相同.");
				}

				// 检测日期
				if (open_state == 0 ) {
					if(!( WebUtils.Html.getDate0(openTimeShow)>=WebUtils.Html.getDate0(presetData0.getEndTimeShow()) || 
						(WebUtils.Html.getDate0(endTimeShow) <= WebUtils.Html.getDate0(presetData0.getOpenTimeShow())) )){
						return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同的日期活动");
					}
				}
				
				// 检测开服日期
				 if (open_state == 1 ) { 
					 if(! (openDateShow>=presetData0.getEndDateShow()) || (endDateShow <=presetData0.getOpenDateShow())){
								return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同的日期活动");
							}
				 }
			}

			presetData.setId(cid);
		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			}
			presetData = model.getValue(TimerCard.class);
			if (presetData == null) {
				presetData = new TimerCard();
			}
		}

		model.setRemark(remark);
		presetData.setTimeCardName(timeCardName);
		presetData.setOpenTime(openTime);
		presetData.setEndTime(endTime);
		presetData.setOpenDate(openDate);
		presetData.setEndDate(endDate);
		presetData.setOpen_state(open_state);
		
		presetData.setOpenTimeShow(openTimeShow);
		presetData.setEndTimeShow(endTimeShow);
		presetData.setOpenDateShow(openDateShow);
		presetData.setEndDateShow(endDateShow);
	
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

			int presetId =presetDAO.insertNew(model);
			model.setId(presetId);
		} else {
			presetDAO.insertOrUpdateNew(model);
		}
		
		List<ResultMsg> resultMsg = new ArrayList<>();

		final List<Integer> succeedList = new ArrayList<Integer>();
		final TimerCard TimerCardData = model.getValue(TimerCard.class);
		
		// 判断是否存在限时礼包ID
		if(StringUtils.isEmpty(TimerCardData.getTimeCardId().trim())){
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功");
		}
		
		//final CGCardPackageTimeLImitWCS.Builder b = CGCardPackageTimeLImitWCS.newBuilder();

		// 遍历执行
		boolean result = ServerUtils.operateServerAction(new ListUtils.IAction<ServerModel>() {
			@Override
			public boolean action(ServerModel serverModel, Iterator<?> iter) {
				long srvId = serverModel.getId();

				boolean enable = Utils.ArrayUtils.find(srvIds, (int) srvId);
				if (!enable) {
					return true; // 没有选上, 现在也没打算开启, 跳过.
				}

				// 连接服务器
				IClient client = serverModel.createClient();
				if (client == null) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, "服务器(" + srvId + ")没有配置url"));
					return true;
				}

				// 发送并等待回馈
				//ProtocolWeb.LimitShopWSC sendMsg = null;
				try {
					CGCardPackageTimeLImitWCS.Builder b = CGCardPackageTimeLImitWCS.newBuilder();
					int  beginTime=0;
					int  endTime=0;
					int  beginTimeShow=0;
					int  endTimeShow=0;
					if (TimerCardData.getOpen_state() == 0) {

						beginTime=WebUtils.Html.getDate0(TimerCardData.getOpenTime());
						endTime =WebUtils.Html.getDate0(TimerCardData.getEndTime());
						beginTimeShow =WebUtils.Html.getDate0(TimerCardData.getOpenTimeShow());
						endTimeShow =WebUtils.Html.getDate0(TimerCardData.getEndTimeShow());
					}
					if (TimerCardData.getOpen_state() == 1) {
						
						int openDate = TimerCardData.getOpenDate();
						int endDate = TimerCardData.getEndDate();
						int openDateShow = TimerCardData.getOpenDateShow();
						int endDateShow = TimerCardData.getEndDateShow();
						
						String srvOpenTime = serverModel.getOpen_time();
						
						String OpenTime1 = TimeUtils.getParam(srvOpenTime, openDate);
						String EndTime1 = TimeUtils.getParam(srvOpenTime, endDate);
						String OpenTimeShow1 = TimeUtils.getParam(srvOpenTime, openDateShow);
						String EndTimeShow1 = TimeUtils.getParam(srvOpenTime, endDateShow);

						beginTime =WebUtils.Html.getDate0(OpenTime1);
						endTime =WebUtils.Html.getDate0(EndTime1);
						beginTimeShow =WebUtils.Html.getDate0(OpenTimeShow1);
						endTimeShow =WebUtils.Html.getDate0(EndTimeShow1);
					}
					
					List<CGCardPackageTimeLimit> list = new ArrayList<>();
					
					CGCardPackageTime.Builder bTime= CGCardPackageTime.newBuilder();
					bTime.setBegin(beginTime);
					bTime.setEnd(endTime);
					bTime.setBeginShow(beginTimeShow);
					bTime.setEndShow(endTimeShow);
		
					
					String timeCardIds = TimerCardData.getTimeCardId();					
					String[] timeCardArray = timeCardIds.split(",");
					List<String>  timeCardList= ListExtUtil.arrayToList(timeCardArray);
					if(0 !=timeCardList.size()){
							for(String timeCardIdStr:timeCardList){
								CGCardPackageTimeLimit.Builder bcard = CGCardPackageTimeLimit.newBuilder();
								bcard.setId(Integer.valueOf(timeCardIdStr));
								bcard.addTimes(bTime);
								list.add(bcard.build());
							}
					}
					
					b.addAllInfos(list);

					ProtocolWeb.CGCardPackageTimeLImitWSC retMsg = client.call(Head.H450001, b.build(),
							ProtocolWeb.CGCardPackageTimeLImitWSC.class);
					
					int state = retMsg.getStatus();
					if (state == 0) {
						succeedList.add((int) srvId);
					}

					if (state >= 1) {
						resultMsg.add(new ResultMsg(ResultMsg.fail,
								"消息发送服务器：(" + serverModel.getSrvStr0() + ") 失败   :" + retMsg.getMsg()+":"+String.valueOf(state)));
						
					}

				} catch (Exception e) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, "消息ID:" + cid + "服务器ID:" + srvId + "发送失败;"));
					return false; // 失败不管
				}

				return true;
			}
		});

		int[] selectSrvIds0 = ArrayUtils.toIntArray(succeedList);
		presetData.setSrvs(selectSrvIds0);
		model.setValue(presetData);
		PresetManager.getInstance().updateByDAO(model);

		if (!result) {
			resultMsg.add(new ResultMsg(ResultMsg.fail, "消息(" + cid + ")同步服务器失败;"));
		}

		StringBuilder sb = new StringBuilder();
		resultMsg.add(new ResultMsg(ResultMsg.succ, "发送完毕!"));
		// 发送的消息
		for (ResultMsg msg : resultMsg) {
			sb.append(msg.toHtmlString());
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());

	}

	// 删除
	@LoginCheck
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {

		PresetManager.toDelete(inv, id, "defualUrl");
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "poke/timerCard/list?mainView=true");
	}
	
	@LoginCheck
	@Post("getSelect2TimerCard")
	public String getSelect2TimerCard(Invocation inv) {
		//类型选择
		int lotteryType=1;
		List<LotteryModel> list = LotteryModelManager.getModels();
		List<LotteryModel> lotterylist=new ArrayList<LotteryModel>();
		
		for(LotteryModel lotteryModel  :list){
			if(lotteryType==lotteryModel.getLotteryType()){
				lotterylist.add(lotteryModel);
			}
		}
		
		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("cardId", "0");
		map.put("cardName", "--请选择--");
		objList.add(map);

		for (int i = 0; i < lotterylist.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			LotteryModel item = lotterylist.get(i);
			itemMap.put("cardId", item.getLotteryId());
			itemMap.put("cardName", item.getLotteryId() + "  " +item.getLotteryName());
			objList.add(itemMap);
		}
		return "@" + JSONObject.toJSONString(objList);
	}
	
	public String showAddCondition(Invocation inv) {
		return "timerCard_id";
	}
	
	@LoginCheck
	@Post("saveRewAndReq")
	public String saveRewAndReq(Invocation inv, @Param("id") int id,@Param("cardId") int cardId, @Param("cardName") String cardName) {
		
		StringBuffer sb = new StringBuffer();
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";
		TimerCard presetData = model.getValue(TimerCard.class);
		// 条件
		String timeCardIds = presetData.getTimeCardId();
		if(StringUtils.isNotEmpty(timeCardIds) ){
			//List<String> timeCardList = java.util.Arrays.asList(timeCardIds);
			String[] timeCardArray = timeCardIds.split(",");
			List<String>  timeCardList= ListExtUtil.arrayToList(timeCardArray);
			if(0 !=timeCardList.size()){
					for(String timeCardIdStr:timeCardList){
						if(String.valueOf(cardId).equals(timeCardIdStr.trim())){
							return "@{'msg':'重复活动ID'}";
						}
					}
					sb.append(presetData.getTimeCardId()).append(",").append(String.valueOf(cardId));
					presetData.setTimeCardId(sb.toString());
				
			}
		}else{
			sb.append(String.valueOf(cardId));
			presetData.setTimeCardId(sb.toString());
		}
		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		LotteryModel  lotteryModel=LotteryModelManager.getModelById(cardId);
		
		jsonMap.put("cardId", cardId);
		jsonMap.put("cardName",  (lotteryModel!=null)? cardId+"-"+lotteryModel.getLotteryName():cardId);
		// 转成json
		return "@" + JSON.toJSONString(jsonMap);
	}
	
	@Post("getRewAndReq")
	public String getRewAndReq(Invocation inv, @Param("id") int id) {
		
		TimerCard presetData=null;
		String timeCardIds=null;
		PresetModel model = PresetManager.getInstance().get(id);

		if (null != model) {
			presetData = model.getValue(TimerCard.class);
		}
		
		timeCardIds= presetData.getTimeCardId();
		boolean isNotNull=StringUtils.isNotEmpty(timeCardIds.trim());
		if(!isNotNull)
			return "@" + "奖励列表为空!!!";
		String[] timeCardArray = timeCardIds.split(",");
		List<String>  timeCardList= ListExtUtil.arrayToList(timeCardArray);
		int max_size = (timeCardList.size() !=0) ? timeCardList.size() : 0;
		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", max_size);
		dataObj.put("id", id);
		List<Map<Object, Object>> dataList = new ArrayList<>();
		
		int lanId=-1;
		if(isNotNull){
			for (String cardId : timeCardList) {
				LotteryModel  lotteryModel=LotteryModelManager.getModelById(Integer.valueOf(cardId));
				Map<Object, Object> listMap = new HashMap<>();
				lanId=lanId+1;
				listMap.put("id", lanId);
				listMap.put("cardId", cardId);
				listMap.put("cardName", (lotteryModel!=null)? cardId+"-"+lotteryModel.getLotteryName():cardId);
				
				dataList.add(listMap);
			}
		}
		dataObj.put("rows", dataList);
		return "@" + JSONObject.toJSON(dataObj);
	}
	
	@LoginCheck
	@Post("updateRewAndReq")
	public String updateRewAndReq(Invocation inv, @Param("id") int id, @Param("row") int row,
			@Param("cardId") int cardId) {
		
		StringBuffer sb = new StringBuffer();
		TimerCard presetData=null;
		String timeCardIds=null;
		PresetModel model = PresetManager.getInstance().get(id);
		
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";
		presetData = model.getValue(TimerCard.class);
		// 条件
		timeCardIds = presetData.getTimeCardId();
		List<String> timeCardList = java.util.Arrays.asList(timeCardIds);
		
		
		//return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "存在重复ID");
		int lanid = -1;
		boolean isFirst=true;
		for(String timeCardId:timeCardList){
			lanid = lanid + 1;
			boolean rowEqual = true;
			if( lanid!=row ){
				if(isFirst){
					sb.append(timeCardId);
					if ((cardId ==Integer.valueOf(timeCardId.trim())) && rowEqual) {
						return "@{'msg':'活动ID重复'}";
					}
				}else{
					sb.append(",").append(timeCardId);
					if ((cardId ==Integer.valueOf(timeCardId.trim())) && rowEqual) {
						return "@{'msg':'活动ID重复'}";
					}
				}
			}else if(lanid==row ){
				rowEqual = false;
				if(isFirst){
					sb.append(cardId);
				}else{
					sb.append(",").append(cardId);
				}
			}
			
		}
		presetData.setTimeCardId(sb.toString());
		
		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		LotteryModel  lotteryModel=LotteryModelManager.getModelById(Integer.valueOf(cardId));
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", row);
		jsonMap.put("cardId", cardId);
		jsonMap.put("cardName", (lotteryModel!=null)? cardId+"-"+lotteryModel.getLotteryName():cardId);

		// 转成json
		return "@" + JSON.toJSONString(jsonMap);
	}
	
	@LoginCheck
	@Post("destroyRewAndReq")
	public String destroyRewAndReq(Invocation inv, @Param("id") int id, @Param("row") int row,
			@Param("timerCardId") String timerCardId,@Param("timerCardName") String timerCardName) {

		StringBuffer sb = new StringBuffer();
		TimerCard presetData=null;
		String timeCardIds=null;
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + "{\"success\":false}";
		presetData = model.getValue(TimerCard.class);
		// 条件
		timeCardIds = presetData.getTimeCardId();
		
		String[] timeCardArray = timeCardIds.split(",");
		List<String>  timeCardList= ListExtUtil.arrayToList(timeCardArray);
		boolean isSplit=false;
		for(String  timeCard:timeCardList){
			if(!timeCard.trim().equals(timerCardId.trim())){
				if(isSplit){
					sb.append(",");
				}
				sb.append(timerCardId.trim());
			}
		}
		presetData.setTimeCardId(sb.toString());
		
		if (!model.setValue(presetData))
			return "@" + "{\"success\":false}";

		PresetManager.getInstance().insertByDAO(model);

		return "@" + "{\"success\":true}";
	}
	
	

}
