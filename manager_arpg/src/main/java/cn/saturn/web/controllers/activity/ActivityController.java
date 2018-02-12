package cn.saturn.web.controllers.activity;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;

import proto.ProtocolWeb;
import proto.ProtocolWeb.ActivityItem;
import proto.ProtocolWeb.GetActivityListWCS;
import proto.ProtocolWeb.GetActivityListWSC;
import proto.ProtocolWeb.SetActivityWCS;
import proto.ProtocolWeb.SetActivityWSC;
import xzj.core.util.MD5;
import zyt.spring.component.ComponentManager;
import zyt.utils.ArrayUtils;
import zyt.utils.ListUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import cn.saturn.operation.Operation;
import cn.saturn.operation.OperationExt;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.activity.dao.ActivityMgr;
import cn.saturn.web.controllers.activity.dao.ActivityModel;
import cn.saturn.web.controllers.activity.dao.PresetActivity;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.banner.dao.NextDayBannerTimer;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.pay.dao.PayModel;
import cn.saturn.web.controllers.pay.dao.PayModelDAO;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.EasyUiUtils;
import cn.saturn.web.utils.HttpTookit;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.PresetActivityUtils;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;

@Path("")
public class ActivityController {
	
	//支付地址
	//private static String payaddr = Config.val("payaddr");
	//支付key
	private static String paykey = Config.val("paykey");
	
	
	@Resource
	ChestModelManager chestMgr;

	@Resource
	ItemModelManager itemMgr;
	
	@Resource
	PresetDAO presetDAO;
	
	@Resource
	PayModelDAO payModelDAO;
	
	@Resource
	NextDayBannerTimer nextDayBannerTimer;

	// static String regStr = "";
	// static String cateStr = "";

	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/activity/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/activity/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/activity/edit"));
		Utils.setAttributeValue(inv, "copyAddUrl", Utils.url(inv, "/activity/copyAdd"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/activity/toDelete"));
		Utils.setAttributeValue(inv, "toCategoryCopys", Utils.url(inv, "/activity/toCategoryCopys"));
		Utils.setAttributeValue(inv, "sends", Utils.url(inv, "/activity/sends"));

		return "activitypreset_list";
	}

	// start type 30 号转盘 增加转盘次数 addTimes
	@Get("showType30AddTimes")
	public String showType30AddTimes(Invocation inc) {
		return "showType30AddTimes";
	}

	@LoginCheck
	@Post("saveType30AddTimes")
	public String saveType30AddTimes(Invocation inv, @Param("id") long id, @Param("item_id") int id01,
			@Param("cz_1") int cz_1, @Param("cs_1") int cs_1, @Param("cz_2") int cz_2, @Param("cs_2") int cs_2,
			@Param("cz_3") int cz_3, @Param("cs_3") int cs_3, @Param("cz_4") int cz_4, @Param("cs_4") int cs_4,
			@Param("type") int type) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";
		PresetActivity presetData = model.getValue(PresetActivity.class);

		// 物品清单
		List<String> turnTableCsAward = ListExtUtil.arrayToList(presetData.getTurnTableCsAward());
		String itemListStr = PresetActivityUtils.toTurnTableCsAward(type, id01, cz_1, cs_1, cz_2, cs_2, cz_3, cs_3,
				cz_4, cs_4);
		turnTableCsAward.add(itemListStr);
		if (turnTableCsAward.size() > 30)
			return "@{'msg':'必须小于30个'}";
		presetData.setTurnTableCsAward(ListExtUtil.listToArray(turnTableCsAward, new String[0]));
		presetData.setSpeArg(type);

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);

		String result = PresetActivityUtils.toTurnTableCsAwardJson(turnTableCsAward.size() - 1, type, id01, cz_1, cs_1,
				cz_2, cs_2, cz_3, cs_3, cz_4, cs_4);
		// 转成json
		return "@" + result;
	}

	@LoginCheck
	@Post("updateType30AddTimes")
	public String updateType30AddTimes(Invocation inv, @Param("id") long id, @Param("row") int row,
			@Param("item_id") int id01, @Param("cz_1") int cz_1, @Param("cs_1") int cs_1, @Param("cz_2") int cz_2,
			@Param("cs_2") int cs_2, @Param("cz_3") int cz_3, @Param("cs_3") int cs_3, @Param("cz_4") int cz_4,
			@Param("cs_4") int cs_4, int type) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";
		PresetActivity presetData = model.getValue(PresetActivity.class);
		// 物品清单
		String[] turnTableCsAward = presetData.getTurnTableCsAward();
		turnTableCsAward[row] = PresetActivityUtils.toTurnTableCsAward(type, id01, cz_1, cs_1, cz_2, cs_2, cz_3, cs_3,
				cz_4, cs_4);
		presetData.setTurnTableCsAward(turnTableCsAward);
		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";
		presetData.setSpeArg(type);
		PresetManager.getInstance().insertByDAO(model);
		String result = PresetActivityUtils.toTurnTableCsAwardJson(turnTableCsAward.length - 1, type, id01, cz_1, cs_1,
				cz_2, cs_2, cz_3, cs_3, cz_4, cs_4);
		// 转成json
		return "@" + result;
	}

	@LoginCheck
	@Post("delType30AddTimes")
	public String delType30AddTimes(Invocation inv, @Param("id") int id, @Param("row") int row) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + EasyUiUtils.getFail();

		PresetActivity presetData = model.getValue(PresetActivity.class);

		// 物品
		List<String> addTimeList = ListExtUtil.arrayToList(presetData.getTurnTableCsAward());
		addTimeList.remove(row);
		presetData.setTurnTableCsAward(ListExtUtil.listToArray(addTimeList, new String[0]));

		if (!model.setValue(presetData))
			return "@" + EasyUiUtils.getFail();
		PresetManager.getInstance().insertByDAO(model);
		return "@" + EasyUiUtils.getSuccess();
	}

	@Post("getType30AddTimes")
	public String getType30AddTimes(Invocation inv, @Param("id") long id, @Param("a_id") int aid) {

		List<String> turnTableCsAward = null;
		PresetModel model = null;
		PresetActivity presetData = null;
		model = PresetManager.getInstance().get(id);

		if (null != model) {
			presetData = model.getValue(PresetActivity.class);
			turnTableCsAward = ListExtUtil.arrayToList(presetData.getTurnTableCsAward());
		}

		return "@" + PresetActivityUtils.toAddTimesEasyUIGrid(turnTableCsAward, presetData.getSpeArg(), id);
	}

	// end type 30 号转盘 增加转盘次数 addTimes
	// start type 30 号转盘 转盘物品清单 ItemList

	@Get("showType30ItemList")
	public String showType30ItemList(Invocation inv) {
		return "showType30ItemList";
	}

	@LoginCheck
	@Post("saveType30ItemList")
	public String saveType30ItemList(Invocation inv, @Param("id") long id, @Param("item_id") int id01,
			@Param("item_num") int num, @Param("item_order") int order, @Param("item_weight") int weight,
			@Param("item_min_cs") int min_cs, @Param("item_max_cs") int max_cs) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";
		PresetActivity presetData = model.getValue(PresetActivity.class);

		// 物品清单
		List<String> itemList = ListExtUtil.arrayToList(presetData.getItemList());
		String itemListStr = PresetActivityUtils.toItemList(id01, num, order, weight, min_cs, max_cs);
		itemList.add(itemListStr);
		if (itemList.size() > 12)
			return "@{'msg':'必须小于12个'}";
		presetData.setItemList(ListExtUtil.listToArray(itemList, new String[0]));

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);

		String result = PresetActivityUtils.toItemListJson(itemList.size() - 1, id01, num, order, weight, min_cs,
				max_cs);
		// 转成json
		return "@" + result;
	}

	@LoginCheck
	@Post("delType30ItemList")
	public String delType30ItemList(Invocation inv, @Param("id") int id, @Param("row") int row) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + EasyUiUtils.getFail();

		PresetActivity presetData = model.getValue(PresetActivity.class);

		// 物品
		List<String> itemList = ListExtUtil.arrayToList(presetData.getItemList());
		itemList.remove(row);
		presetData.setItemList(ListExtUtil.listToArray(itemList, new String[0]));

		if (!model.setValue(presetData))
			return "@" + EasyUiUtils.getFail();

		PresetManager.getInstance().insertByDAO(model);

		return "@" + EasyUiUtils.getSuccess();
	}

	@LoginCheck
	@Post("updateType30ItemList")
	public String updateType30ItemList(Invocation inv, @Param("id") long id, @Param("row") int row,
			@Param("item_id") int id01, @Param("item_num") int num, @Param("item_order") int order,
			@Param("item_weight") int weight, @Param("item_min_cs") int min_cs, @Param("item_max_cs") int max_cs) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";
		PresetActivity presetData = model.getValue(PresetActivity.class);
		// 物品清单
		String[] itemList = presetData.getItemList();
		itemList[row] = PresetActivityUtils.toItemList(id01, num, order, weight, min_cs, max_cs);
		presetData.setItemList(itemList);

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		String result = PresetActivityUtils.toItemListJson(itemList.length - 1, id01, num, order, weight, min_cs,
				max_cs);
		// 转成json
		return "@" + result;
	}

	@Post("getType30ItemList")
	public String getType30ItemList(Invocation inv, @Param("id") long id, @Param("a_id") int aid) {

		List<String> itemList = null;
		PresetModel model = null;
		PresetActivity presetData = null;
		model = PresetManager.getInstance().get(id);

		if (null != model) {
			presetData = model.getValue(PresetActivity.class);
			itemList = ListExtUtil.arrayToList(presetData.getItemList());
		}

		return "@" + PresetActivityUtils.toItemListEasyUIGrid(itemList, id);
	}

	// end type 30 号转盘 转盘物品清单 ItemList

	@Get("showRewAndReq")
	public String showRewAndReq(Invocation inv) {
		return "activity_condition";
	}

	@LoginCheck
	@Post("delAwardAndReq")
	public String delRewAndReq(Invocation inv, @Param("id") int id, @Param("row") int row) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + EasyUiUtils.getFail();

		PresetActivity presetData = model.getValue(PresetActivity.class);

		// 条件
		List<String> reqList = ListExtUtil.arrayToList(presetData.getRequires());
		reqList.remove(row);
		presetData.setRequires(ListExtUtil.listToArray(reqList, new String[0]));

		// 需求
		List<String> rewList = ListExtUtil.arrayToList(presetData.getAwards());
		rewList.remove(row);
		presetData.setAwards(ListExtUtil.listToArray(rewList, new String[0]));

		if (!model.setValue(presetData))
			return "@" + EasyUiUtils.getFail();

		PresetManager.getInstance().insertByDAO(model);

		return "@" + EasyUiUtils.getSuccess();
	}

	@LoginCheck
	@Post("saveRewAndReq")
	public String saveRewAndReq(Invocation inv, @Param("id") long id, @Param("req") String req,
			@Param("item_id_01") int id01, @Param("item_num_01") long num01, @Param("item_id_02") int id02,
			@Param("item_num_02") long num02, @Param("item_id_03") int id03, @Param("item_num_03") long num03,
			@Param("item_id_04") int id04, @Param("item_num_04") long num04) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";
		PresetActivity presetData = model.getValue(PresetActivity.class);

		// 条件
		List<String> reqList = ListExtUtil.arrayToList(presetData.getRequires());
		reqList.add(req);
		if(PresetActivity.activeType_qiri_huodong ==presetData.getType()){
			if (reqList.size() > 200)
				return "@{'msg':'七日活动必须小于等于200个'}";
		}else{
			if (reqList.size() > 20)
				return "@{'msg':'必须小于等于20个'}";
		}
		presetData.setRequires(ListExtUtil.listToArray(reqList, new String[0]));

		// 需求
		String rewStr = getRewards(id01, num01, id02, num02, id03, num03, id04, num04);
		List<String> rewList = ListExtUtil.arrayToList(presetData.getAwards());
		rewList.add(rewStr);
		presetData.setAwards(ListExtUtil.listToArray(rewList, new String[0]));

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);

		int[] ids0 = new int[] { id01, id02, id03, id04 };
		int[] nums = new int[] { (int) num01, (int) num02, (int) num03, (int) num04 };
		String result = PresetActivityUtils.getJsonItem4(rewList.size() - 1, req, ids0, nums);
		// 转成json
		return "@" + result;
	}

	@LoginCheck
	@Post("updateRewAndReq")
	public String updateRewAndReq(Invocation inv, @Param("id") long id, @Param("row") int row, @Param("req") String req,
			@Param("item_id_01") int id01, @Param("item_num_01") long num01, @Param("item_id_02") int id02,
			@Param("item_num_02") long num02, @Param("item_id_03") int id03, @Param("item_num_03") long num03,
			@Param("item_id_04") int id04, @Param("item_num_04") long num04) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";
		PresetActivity presetData = model.getValue(PresetActivity.class);
		// 条件
		String[] requires = presetData.getRequires();
		requires[row] = req;
		// 需求
		String[] rewards = presetData.getAwards();
		rewards[row] = getRewards(id01, num01, id02, num02, id03, num03, id04, num04);
		presetData.setRequires(requires);
		presetData.setAwards(rewards);

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		int[] ids0 = new int[] { id01, id02, id03, id04 };
		int[] nums = new int[] { (int) num01, (int) num02, (int) num03, (int) num04 };
		String result = PresetActivityUtils.getJsonItem4(row, req, ids0, nums);
		// 转成json
		return "@" + result;
	}

	private String getRewards(long iName1, long iNum1, long iName2, long iNum2, long iName3, long iNum3, long iName4,
			long iNum4) {
		StringBuilder sb = new StringBuilder();

		mergeInt(sb, iName1, iNum1);
		mergeInt(sb, iName2, iNum2);
		mergeInt(sb, iName3, iNum3);
		mergeInt(sb, iName4, iNum4);
		String str = sb.toString();
		if (str.length() > 3)
			return str.substring(0, str.length() - 1);
		return str;
	}

	private boolean mergeInt(StringBuilder sb, long iName, long iNum) {
		if (iName > 0 && iNum > 0) {
			sb.append(iName).append("_").append(iNum).append(";");
			return true;
		}
		return false;
	}

	@Post("getRewAndReq")
	public String getRewAndReq(Invocation inv, @Param("id") long id, @Param("a_id") int aid) {

		String[] requires = null;
		String[] awards = null;
		ActivityModel aModel = null;
		PresetModel model = null;
		PresetActivity presetData = null;
		ActivityMgr aMgr = ComponentManager.getComponent(ActivityMgr.class);

		model = PresetManager.getInstance().get(id);

		if (null != model) {
			presetData = model.getValue(PresetActivity.class);
			aModel = aMgr.find(aid);
			requires = presetData.getRequires();
			awards = presetData.getAwards();
		}

		int r_size = (requires != null) ? requires.length : 0;
		int a_size = (awards != null) ? awards.length : 0;
		int max_size = Math.max(r_size, a_size);
		// 重构使用json 来拼接
		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", max_size);
		dataObj.put("id", id);

		List<Object> rowsList = new ArrayList<>();
		for (int i = 0; i < max_size; i++) {
			Map<Object, Object> rowsItem = new HashMap<>();
			if (i < r_size) {
				rowsItem.put("id", i);
				rowsItem.put("req", requires[i]);
				String str = "";
				if (aModel != null) {
					str = PresetActivityUtils.getConditionDesc(aModel.getCondition_desc(), requires[i],
							aModel.getType(), presetData.getSpeArg());
				}
				rowsItem.put("name", str);
			}
			if (i < a_size) {
				String award = awards[i];
				String[] aws = award.split(";");
				for (int j = 0; j < aws.length; j++) {
					String itemStr = aws[j];
					String[] items = itemStr.split("_");
					if (items.length == 2) {
						int index = j + 1;
						rowsItem.put("item_id_0" + index, items[0]);
						rowsItem.put("item_num_0" + index, items[1]);

						int itemId = Integer.parseInt(items[0]);
						String itemName = PresetActivityUtils.getItemAndChestById(itemId);
						rowsItem.put("item_name_0" + index, itemName);
					}
				}
			}

			rowsList.add(rowsItem);
		}

		dataObj.put("rows", rowsList);
		String result = JSONObject.toJSONString(dataObj);
		return "@" + result;
	}

	String titleFilterStr = ""; // 标题过滤
	String categoryFilterStr = ""; // 分组过滤

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {

		// 标题过滤str
		titleFilterStr = Utils.getSessionValue(inv, "titleFilterStr", String.class);
		categoryFilterStr = Utils.getSessionValue(inv, "categoryFilterStr", String.class);

		String isSubmit = inv.getParameter("isSubmit");
		if (null != isSubmit && isSubmit.equals("1")) {
			titleFilterStr = inv.getParameter("titleFilterStr");
			categoryFilterStr = inv.getParameter("categoryFilterStr");

			Utils.setSessionValue(inv, "titleFilterStr", titleFilterStr);
			Utils.setSessionValue(inv, "categoryFilterStr", categoryFilterStr);
		}

		if (StringUtils.isEmpty(titleFilterStr))
			titleFilterStr = "";

		if (StringUtils.isEmpty(categoryFilterStr))
			categoryFilterStr = "";

		// 转换接口
		BsgridUtils.IConvert<PresetModel> action = new BsgridUtils.IConvert<PresetModel>() {

			@Override
			public boolean convert(PresetModel model, Map<String, Object> map) {
				map.put("id", model.getId());
				map.put("remark", model.getRemark());

				// 解析数据
				PresetActivity presetData = model.getValue(PresetActivity.class);
				if (presetData != null) {

					String title = StringUtils.isEmpty(presetData.getTital()) ? "" : presetData.getTital();
					String categoryStr = StringUtils.isEmpty(presetData.getCategory()) ? "" : presetData.getCategory();

					if (!title.contains(titleFilterStr))
						return false;

					if (!categoryStr.contains(categoryFilterStr))
						return false;

					map.put("pid", presetData.getId());
					map.put("tital", presetData.getTital());
					map.put("category", presetData.getCategory());
					// 处理时间
					int startDayTime = presetData.getStartDayTime();
					int endDayTime = presetData.getEndDayTime();
					if (startDayTime <= 0 && endDayTime <= 0) {
						map.put("startTime", "无限期");
						map.put("endTime", "无限期");
					} else {
						map.put("startTime", TimeUtils.toString(startDayTime, "yyyy-MM-dd"));
						map.put("endTime", TimeUtils.toString(endDayTime, "yyyy-MM-dd"));
					}

					map.put("clazz", PresetActivity.clazzStrs[presetData.getClazz()]);

					ActivityMgr actMgr = ComponentManager.getComponent(ActivityMgr.class);
					ActivityModel actModel = actMgr.find(presetData.getTypeId());
					map.put("type", actModel==null?"找不到类型":actModel.getName());
				}

				return true;
			}

		};

		return PresetManager.listJson(inv, PresetManager.presetType_activity, action);
	}

	@Get("getById")
	public String getById(Invocation inv, @Param("id") int id) throws Throwable {
		ActivityMgr actMgr = ComponentManager.getComponent(ActivityMgr.class);
		ActivityModel model = actMgr.find(id);
		String result = "";
		if (model != null)
			result = model.getJson();
		return "@" + result;
	}

	@Get("getById2")
	public String getById2(Invocation inv, @Param("id") int id) throws Throwable {
		ActivityMgr actMgr = ComponentManager.getComponent(ActivityMgr.class);
		ActivityModel model = actMgr.find(id);
		String result = "";
		if (model != null) {
			Map<Object, Object> jsonMap = new HashMap<Object, Object>();
			jsonMap.put("condition_desc", model.getCondition_desc());

			result = JSONObject.toJSONString(jsonMap);
		}
		return "@" + result;
	}

	// 编辑预设页面
	@MainView
	@LoginCheck
	@Get("copyAdd")
	public String copyAdd(Invocation inv, @Param("id") int id) throws Throwable {

		HttpServletRequest request = inv.getRequest();

		// 时间
		int startDayTime = 0;
		int endDayTime = 0;
		request.setAttribute("id", "");
		ActivityMgr actMgr = ComponentManager.getComponent(ActivityMgr.class);
		List<ActivityModel> actModel = actMgr.getList();
		request.setAttribute("actModel", actModel);

		if (id <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "必须选择一个id");

		} else {
			request.setAttribute("isNew", "true");
			request.setAttribute("cidStr", "disabled=\"disabled\" type=\"hidden\" ");
			request.setAttribute("btnStr", "新增");

			PresetModel model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据:" + id);
			}
			String remark = model.getRemark();
			request.setAttribute("remark", remark);

			// 解析数据
			PresetActivity presetData = model.getValue(PresetActivity.class);
			if (presetData != null) {
				request.setAttribute("pid", presetData.getId());
				request.setAttribute("pidStr", "disabled=\"disabled\" ");
				request.setAttribute("cid", presetData.getCid());
				request.setAttribute("tital", presetData.getTital());

				// 处理时间
				startDayTime = presetData.getStartDayTime();
				endDayTime = presetData.getEndDayTime();

				// 写入参数
				request.setAttribute("clazz", presetData.getClazz());
				request.setAttribute("type", presetData.getType());
				request.setAttribute("intro", presetData.getIntro());
				request.setAttribute("tips", presetData.getTips());
				request.setAttribute("speArg", presetData.getSpeArg()); // 特殊参数
				request.setAttribute("order", presetData.getOrder());
				request.setAttribute("icon", presetData.getIcon());
				request.setAttribute("typeId", presetData.getTypeId());
				request.setAttribute("winPhoto", presetData.getWinPhoto());
				request.setAttribute("period", presetData.getPeriod());
				request.setAttribute("extraParams", presetData.getExtraParams());
				request.setAttribute("winPhotoIndex", presetData.getWinPhotoIndex());

				// 转盘拷贝额外参数
				if (presetData.getType() == PresetActivity.activeType_zhuan_pan) {
					request.setAttribute("openRanking", presetData.getOpenRanking());
					request.setAttribute("freeNum", presetData.getFreeNum());
					request.setAttribute("useItemId", presetData.getUseItem());
					request.setAttribute("useNum", presetData.getUsetNum());

					String itemList = "";
					if (presetData.getItemList() != null)
						itemList = JSONObject.toJSONString(presetData.getItemList());

					String turnTableCsAwards = "";
					if (presetData.getTurnTableCsAward() != null)
						turnTableCsAwards = JSONObject.toJSONString(presetData.getTurnTableCsAward());

					request.setAttribute("itemList", itemList);
					request.setAttribute("turnTableCsAwards", turnTableCsAwards);
				}

				String requires = "";
				if (presetData.getRequires() != null)
					requires = JSONObject.toJSONString(presetData.getRequires());

				String awards = "";
				if (presetData.getAwards() != null)
					awards = JSONObject.toJSONString(presetData.getAwards());
				
				String petIds = "";
				if(presetData.getPetIds() != null)
					petIds = JSONObject.toJSONString(presetData.getPetIds());
				
				String adImages = "";
				if(presetData.getAdImages() != null)
					adImages = JSONObject.toJSONString(presetData.getAdImages());

				request.setAttribute("requires", requires);
				request.setAttribute("awards", awards);
				request.setAttribute("petIds", petIds);
				request.setAttribute("adImages", adImages);
				// 获取选择的服务器列表
				int[] srvIds = presetData.getSrvIds();
				request.setAttribute("selectSrvIds", srvIds);
			}
		}

		// 时间处理
		if (startDayTime != 0) {
			request.setAttribute("startTime", TimeUtils.toString(startDayTime, "yyyy-MM-dd"));
		}
		if (endDayTime != 0) {
			request.setAttribute("endTime", TimeUtils.toString(endDayTime, "yyyy-MM-dd"));
		}

		request.setAttribute("eidtUrl", Utils.url(inv, "/activity/toEdit"));
		return "activitypreset_edit";
	}

	@MainView
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {

		HttpServletRequest request = inv.getRequest();

		// 时间
		int startDayTime = 0;
		int endDayTime = 0;
		request.setAttribute("id", id);
		ActivityMgr actMgr = ComponentManager.getComponent(ActivityMgr.class);
		List<ActivityModel> actModel = actMgr.getList();
		request.setAttribute("actModel", actModel);

		if (id <= 0) {
			request.setAttribute("isNew", "true");
			request.setAttribute("cidStr", "disabled=\"disabled\" type=\"hidden\" ");
			request.setAttribute("btnStr", "新增");
			request.setAttribute("openDate", 0);
			request.setAttribute("closeDate", 0);
			request.setAttribute("open_state", 0);
		} else {
			PresetModel model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据:" + id);
			}
			request.setAttribute("isNew", "false");
			String remark = model.getRemark();
			request.setAttribute("remark", remark);

			// 解析数据
			PresetActivity presetData = model.getValue(PresetActivity.class);
			if (presetData != null) {
				request.setAttribute("pid", presetData.getId());
				request.setAttribute("pidStr", "disabled=\"disabled\" ");
				request.setAttribute("cid", presetData.getCid());
				request.setAttribute("cidStr", "disabled=\"disabled\" type=\"hidden\" ");
				request.setAttribute("tital", presetData.getTital());

				// 处理时间
				startDayTime = presetData.getStartDayTime();
				endDayTime = presetData.getEndDayTime();

				// 写入参数
				request.setAttribute("clazz", presetData.getClazz());
				request.setAttribute("type", presetData.getType());
				request.setAttribute("intro", presetData.getIntro());
				request.setAttribute("tips", presetData.getTips());
				request.setAttribute("speArg", presetData.getSpeArg()); // 特殊参数
				request.setAttribute("order", presetData.getOrder());
				request.setAttribute("icon", presetData.getIcon());
				request.setAttribute("typeId", presetData.getTypeId());
				request.setAttribute("open_state", presetData.getOpen_state());
				request.setAttribute("openDate", presetData.getOpenDate());
				request.setAttribute("closeDate", presetData.getCloseDate());
				request.setAttribute("useItemId", presetData.getUseItem());
				request.setAttribute("useNum", presetData.getUsetNum());
				request.setAttribute("freeNum", presetData.getFreeNum());
				request.setAttribute("openRanking", presetData.getOpenRanking());
				request.setAttribute("winPhoto", presetData.getWinPhoto());
				request.setAttribute("period", presetData.getPeriod());
				request.setAttribute("extraParams", presetData.getExtraParams());
				request.setAttribute("winPhotoIndex", presetData.getWinPhotoIndex());
				
				// 写入条件
				/*
				 * String[] requires = presetData.getRequires(); int size =
				 * (requires != null) ? requires.length : 0; for (int i = 0; i <
				 * size; i++) { String require = requires[i]; String keyName =
				 * String.format("require_%d", i); request.setAttribute(keyName,
				 * require); }
				 * 
				 * // 写入奖励 String[] awards = presetData.getAwards(); size =
				 * (awards != null) ? awards.length : 0; for (int i = 0; i <
				 * size; i++) { String award = awards[i]; String keyName =
				 * String.format("award_%d", i); request.setAttribute(keyName,
				 * award); }
				 */

				// 获取选择的服务器列表
				int[] srvIds = presetData.getSrvIds();
				request.setAttribute("selectSrvIds", srvIds);
			}

			request.setAttribute("btnStr", "修改");
		}

		// 时间处理
		if (startDayTime != 0) {
			request.setAttribute("startTime", TimeUtils.toString(startDayTime, "yyyy-MM-dd"));
		}
		if (endDayTime != 0) {
			request.setAttribute("endTime", TimeUtils.toString(endDayTime, "yyyy-MM-dd"));
		}

		request.setAttribute("eidtUrl", Utils.url(inv, "/activity/toEdit"));
		return "activitypreset_edit";
	}

	@Post("toActiveType")
	public String toEdit(Invocation inv, @Param("activeType") int type) {
		return "";
	}

	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id, @Param("pid") int pid, @Param("cid") int cid,
			@Param("remark") String remark, @Param("intro") String intro, @NotBlank @Param("tital") String tital,
			@Param("type") String typeStr, @Param("clazz") int clazz, @Param("startTime") String startTimeStr,
			@Param("endTime") String endTimeStr, @Param("tips") String tips, @Param("speArg") int speArg,
			@Param("order") int order, @Param("icon") String icon, @Param("srvIds") String srvIdStrs,
			@Param("open_state") int open_state, @Param("openDate") int openDate, @Param("closeDate") int closeDate,
			@Param("speArgType") int speArgType, @Param("useItemId") long useItemId, @Param("useNum") int useNum,
			@Param("freeNum") int freeNum, @Param("openRanking") int openRanking, @Param("winPhoto") String winPhoto,@Param("speArgTypequanfu") int speArgTypequanfu,
			@Param("winPhotoIndex") int winPhotoIndex,@Param("period") String period, @Param("extraParams") String extraParams) throws Throwable {

		// 编辑
		PresetModel model = null;
		PresetActivity presetData = null;
		if (id <= 0) {
			if (pid <= 0) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "活动ID不能为空.");
			}
			// 新增
			model = new PresetModel();
			// model.setId(id);
			model.resetId();
			model.setType(PresetManager.presetType_activity);
			presetData = new PresetActivity();

			// 检测pid是否被使用了
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_activity);
			int count = (all != null) ? all.size() : 0;
			for (int i = 0; i < count; i++) {
				PresetModel model0 = all.get(i);
				PresetActivity presetData0 = model0.getValue(PresetActivity.class);
				if (presetData0 == null) {
					continue;
				}
				// 检测ID
				if (presetData0.getId() == pid) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "活动ID已存在相同.");
				}
			}

			presetData.setId(pid);
			presetData.setCid(cid);

			String requires = inv.getParameter("requires");
			if (StringUtils.isNotEmpty(requires)) {
				List<String> jRequires = JSONObject.parseArray(requires, String.class);
				presetData.setRequires(jRequires.toArray(new String[0]));
			}

			String awards = inv.getParameter("awards");
			if (StringUtils.isNotEmpty(awards)) {
				List<String> jwards = JSONObject.parseArray(awards, String.class);
				presetData.setAwards(jwards.toArray(new String[0]));
			}

			String itemList = inv.getParameter("itemList");
			if (StringUtils.isNotEmpty(itemList)) {
				List<String> jItemList = JSONObject.parseArray(itemList, String.class);
				presetData.setItemList(jItemList.toArray(new String[0]));
			}

			String turnTableCsAwards = inv.getParameter("turnTableCsAwards");
			if (StringUtils.isNotEmpty(turnTableCsAwards)) {
				List<String> jTurnTableCsAwards = JSONObject.parseArray(turnTableCsAwards, String.class);
				presetData.setTurnTableCsAward(jTurnTableCsAwards.toArray(new String[0]));
			}

		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			}
			presetData = model.getValue(PresetActivity.class);
			if (presetData == null) {
				presetData = new PresetActivity();
			}
		}

		String[] types = typeStr.split(",");
		int  typeInt=Integer.parseInt(types[0]);
		
		int spe=0;
		if(presetData.getType() == PresetActivity.activeType_zhuan_pan){
			 spe=speArgType;
		}else if(presetData.getType() == PresetActivity.activeType_quanfu_huodong){
			spe=speArgTypequanfu;
		}else{
			spe=speArg;
		}
		
		model.setRemark(remark);
		presetData.setIntro(intro);
		presetData.setTital(tital);
		presetData.setType(Integer.parseInt(types[0]));
		presetData.setTypeId(Integer.parseInt(types[1]));
		presetData.setClazz(clazz);
		presetData.setTips(tips);
		presetData.setSpeArg(spe);
		presetData.setOrder(order);
		presetData.setIcon(icon);
		presetData.setUseItem(useItemId);
		presetData.setUsetNum(useNum);
		presetData.setFreeNum(freeNum);
		presetData.setOpenRanking(openRanking);
		
		// 时间
		int startDayTime = WebUtils.Html.getDate0(startTimeStr);
		int endDayTime = WebUtils.Html.getDate0(endTimeStr);
		
		boolean isWinPhoto = !StringUtils.equals(winPhoto, presetData.getWinPhoto())
				||startDayTime!=presetData.getStartDayTime()
				||endDayTime!=presetData.getEndDayTime()
				||open_state != presetData.getOpen_state()
				||openDate != presetData.getOpenDate()
				||closeDate != presetData.getCloseDate();
		
		presetData.setWinPhotoIndex(winPhotoIndex);
		presetData.setOpen_state(open_state);
		presetData.setOpenDate(openDate);
		presetData.setCloseDate(closeDate);
		
		presetData.setStartDayTime(startDayTime);
		
		presetData.setEndDayTime(endDayTime);
		
		presetData.setWinPhoto(winPhoto);
		
		presetData.setPeriod(period);
		
		presetData.setExtraParams(extraParams);
				
		// 数据验证
		List<ResultMsg> resultMsg = presetData.onSumitMsg();
		if (!resultMsg.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ResultMsg msg : resultMsg) {
				sb.append(msg.toHtmlString());
			}
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());
		}

		// 设置数据
		if (!model.setValue(presetData)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "数据保存失败.");
		}

		// 提交
		if (id <= 0) {
			//PresetManager.getInstance().insertByDAO(model);
			int  modelId=presetDAO.insertNew(model);
			model.setId(modelId);
		} else {
			//PresetManager.getInstance().updateByDAO(model);
			presetDAO.insertOrUpdate(model);
		}

		// 选择过的服务器列表
		final int[] selectSrvIds = presetData.getSrvIds();

		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs); // 当前选择的服务器列表
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
		

		// 创建消息生成器
		SetActivityWCS.Builder b = presetData.toProtoMsgBuilder();

		// 群发服务器
		List<Integer> succeedList = new ArrayList<>();

		resultMsg.add(new ResultMsg(ResultMsg.succ, "更新活动成功！"));

		final PresetActivity sendPreset = presetData;
		// 遍历执行
		boolean result = ServerUtils.operateServerAction(new ListUtils.IAction<ServerModel>() {
			@Override
			public boolean action(ServerModel serverModel, Iterator<?> iter) {
				long srvId = serverModel.getId();
				// 判断是否在列表中
				boolean enable = Utils.ArrayUtils.find(srvIds, (int) srvId);
				// 没有选上, 现在也没打算开启, 跳过.
				if (!enable)
					return true;

				// 存在列表中的, 和勾选上的都统一更新一遍.
				// 连接服务器
				IClient client = serverModel.createClient();
				if (client == null) {
					return true;
				}

				// 发送并等待回馈
				ProtocolWeb.SetActivityWCS sendMsg = null;
				try {
					sendMsg = sendPreset.toProtoByBuilder(b, serverModel.getOpen_time(), enable,srvIdStrs);
				} catch (ParseException e) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, "服务器(" + srvId + ")开服时间计算错误"));
					return false;
				}
				ProtocolWeb.SetActivityWSC retMsg = client.call(Head.H19001, sendMsg, ProtocolWeb.SetActivityWSC.class);
				if (retMsg == null) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, serverModel.getSrvStr0() + "发送失败"));
					return false; // 失败不管
				}

				int state = retMsg.getStatus();
				if (state == 1) {
					succeedList.add((int) srvId); // 成功服务器列表
				} else {

					resultMsg.add(new ResultMsg(ResultMsg.fail,
							"消息发送服务器：(" + serverModel.getSrvStr0() + ") 失败   :" + retMsg.getMsg()));
				}

				// 成功处理
				return true;
			}
		});
		if (!result) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "同步服务器失败.");
		}
		
		//全服充值活动
		/*if( PresetActivity.activeType_quanfu_chongzhi==typeInt){
			Map<String,String> payMap=new HashMap<>();
			payMap.put("type", String.valueOf(typeInt));
			payMap.put("id", String.valueOf(pid)); 
			payMap.put("startDayTime", String.valueOf(startDayTime));
			payMap.put("endDayTime",String.valueOf( endDayTime));
			payMap.put("serverIds", srvIdStrs);
			String paysign=MD5.encode(pid+startDayTime+endDayTime+paykey);
			payMap.put("sign", paysign);
			payMap.put("speArg", String.valueOf(speArg));
		String	sendStr="type="+String.valueOf(typeInt)+"&id="+String.valueOf(pid)+"&startDayTime="+startDayTime+"&endDayTime="+endDayTime+"&serverIds="+srvIdStrs+"&sign="+paysign+"&speArg="+speArg;
		
		List<PayModel> payModelList=payModelDAO.getPayModelList();
		
		if(payModelList.size()==0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "请配置支付服务器");
		int count=0;
		
		for(PayModel payModel:payModelList){
			
			String payad=payModel.getPayaddr().trim()+"/chargeActive";
			String report=HttpTookit.doGet(payad, sendStr, "UTF-8", true);
			int reportInt=0;
			try{
				reportInt=Integer.valueOf(report.replaceAll("\r|\n", "").trim());
				count=count+reportInt;
			} catch (Exception e) {

			}
		}
	
		//发送成功判断，0失败，1成功;
		if(count !=payModelList.size())
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "支付服务器发送失败");
		}*/
		
		// 重新生成服务器列表
		int[] selectSrvIds0 = ArrayUtils.toIntArray(succeedList);
		presetData.setSrvIds(selectSrvIds0);
		String oldValue = model.getValue();
		if (model.setValue(presetData)) {
			String nowValue = model.getValue();
			if (!nowValue.equals(oldValue)) {
				PresetManager.getInstance().updateByDAO(model);
			}
		}

		resultMsg.add(new ResultMsg(ResultMsg.succ, "发送完毕!"));
		StringBuilder sb = new StringBuilder();
		for (ResultMsg msg : resultMsg) {
			sb.append(msg.toHtmlString());
		}
		
		if(isWinPhoto){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						nextDayBannerTimer.bannerUpdateByTime(srvIds);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}).start();
		}
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());
	}

	@MainView
	@LoginCheck
	@Get("sends")
	public String sends(Invocation inv, @Param("ids") String ids) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		inv.getRequest().setAttribute("ids", ids);
		request.setAttribute("toSendstUrl", Utils.url(inv, "/activity/toSends"));
		return "activitypreset_sends.jsp";
	}

	@MainView
	@LoginCheck
	@Post("toSends")
	public String toSends(Invocation inv, @Param("ids") String ids, @Param("srvIds") String srvIdStrs)
			throws Throwable {

		if (StringUtils.isEmpty(ids))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id 不能为空.");

		if (StringUtils.isEmpty(srvIdStrs))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "srvIdStrs 不能为空.");

		String[] idStrs = ids.split(",");

		List<ResultMsg> resultMsg = new ArrayList<>();
		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
		
		//活动发送支付服务器
		for (String idStr : idStrs) {
			int id = Integer.parseInt(idStr);
			PresetModel model = PresetManager.getInstance().get(id);
			if (model != null) {
				PresetActivity presetData = model.getValue(PresetActivity.class);
				int typeInt= presetData.getType();
				if( PresetActivity.activeType_quanfu_chongzhi==typeInt){
					int pid=presetData.getId();
					int open_state = presetData.getOpen_state(); //开服时间类型, 0.普通开服时间 1.开服天数计算
					if ( 1==open_state){
						return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "全服活动不能用开服时间");
					}
					int startDayTime =presetData.getStartDayTime();
					int endDayTime =presetData.getEndDayTime();
					int  speArg= presetData.getSpeArg();
					//全服充值活动
					Map<String,String> payMap=new HashMap<>();
					payMap.put("type", String.valueOf(typeInt));
					payMap.put("id", String.valueOf(pid)); 
					payMap.put("startDayTime", String.valueOf(startDayTime));
					payMap.put("endDayTime",String.valueOf( endDayTime));
					payMap.put("serverIds", srvIdStrs);
					String paysign=MD5.encode(pid+startDayTime+endDayTime+paykey);
					payMap.put("sign", paysign);
					payMap.put("speArg", String.valueOf(speArg));
					String	sendStr="type="+String.valueOf(typeInt)+"&id="+String.valueOf(pid)+"&startDayTime="+startDayTime+"&endDayTime="+endDayTime+"&serverIds="+srvIdStrs+"&sign="+paysign+"&speArg="+speArg;
					
					List<PayModel> payModelList=payModelDAO.getPayModelList();
					
					if(payModelList.size()==0)
						return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "请配置支付服务器");
					int count=0;
					
					for(PayModel payModel:payModelList){
						
						String payad=payModel.getPayaddr().trim()+"/chargeActive";
						String report=HttpTookit.doGet(payad, sendStr, "UTF-8", true);
						int reportInt=0;
						try{
							reportInt=Integer.valueOf(report.replaceAll("\r|\n", "").trim());
							count=count+reportInt;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				
					//发送成功判断，0失败，1成功;
					if(count !=payModelList.size())
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "支付服务器发送失败");
				}
				
			}
		}
		
		//活动发送游戏服
		for (String idStr : idStrs) {
			int id = Integer.parseInt(idStr);
			PresetModel model = PresetManager.getInstance().get(id);
			if (model != null) {
				final List<Integer> succeedList = new ArrayList<>();
				final PresetActivity presetData = model.getValue(PresetActivity.class);

				presetData.setSrvIds(srvIds);

				final SetActivityWCS.Builder b = presetData.toProtoMsgBuilder();
				// 遍历执行
				boolean result = ServerUtils.operateServerAction(new ListUtils.IAction<ServerModel>() {
					@Override
					public boolean action(ServerModel serverModel, Iterator<?> iter) {
						long srvId = serverModel.getId();

						boolean enable = Utils.ArrayUtils.find(srvIds, (int) srvId);
						if (!enable) {
							return true; // 没有选上, 现在也没打算开启, 跳过.
						}
						// 存在列表中的, 和勾选上的都统一更新一遍.

						// 连接服务器
						IClient client = serverModel.createClient();
						if (client == null) {
							resultMsg.add(new ResultMsg(ResultMsg.fail, "服务器(" + srvId + ")没有配置url"));
							return true;
						}

						// 发送并等待回馈
						ProtocolWeb.SetActivityWCS sendMsg = null;
						try {
							sendMsg = presetData.toProtoByBuilder(b, serverModel.getOpen_time(), enable,srvIdStrs);
						} catch (Exception e) {
							resultMsg.add(new ResultMsg(ResultMsg.fail, "服务器(" + srvId + ")开服时间计算错误"));
						}
						ProtocolWeb.SetActivityWSC retMsg = client.call(Head.H19001, sendMsg,
								ProtocolWeb.SetActivityWSC.class);
						if (retMsg == null) {
							resultMsg.add(new ResultMsg(ResultMsg.fail, "消息(" + idStr + ") 发送(" + srvId + ")发送失败;"));
							return false; // 失败不管
						}

						int state = retMsg.getStatus();
						if (state != 1) {
							resultMsg.add(new ResultMsg(ResultMsg.fail,
									"消息发送服务器：(" + serverModel.getSrvStr0() + ") 失败   :" + retMsg.getMsg()));
						} else {
							succeedList.add((int) srvId);
						}

						return true;
					}
				});

				int[] selectSrvIds0 = ArrayUtils.toIntArray(succeedList);
				presetData.setSrvIds(selectSrvIds0);
				model.setValue(presetData);
				PresetManager.getInstance().updateByDAO(model);

				if (!result) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, "消息(" + idStr + ")同步服务器失败;"));
				}
			}
		}
		
		
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					nextDayBannerTimer.bannerUpdateByTime(srvIds);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		}).start();

		StringBuilder sb = new StringBuilder();
		resultMsg.add(new ResultMsg(ResultMsg.succ, "发送完毕!"));
		// 发送的消息
		for (ResultMsg msg : resultMsg) {
			sb.append(msg.toHtmlString());
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());
	}

	// 删除预设页面
	@LoginCheck
	@Get("toCategoryCopys")
	public String toCategoryCopys(Invocation inv, @Param("ids") String ids, @Param("category") String category) {
		if (StringUtils.isEmpty(category)) {
			return "@分类创建错误";
		}

		if (StringUtils.isEmpty(ids)) {
			return "@ids不能为空";
		}

		String[] idStrs = ids.split(",");
		long maxActivityId = getMaxPresetActivity();
		for (String idStr : idStrs) {
			int id = Integer.parseInt(idStr);
			PresetModel model = PresetManager.getInstance().get(id);
			if (null != model) {
				PresetModel modelNew = new PresetModel();
				// model.setId(id);
				modelNew.resetId();
				modelNew.setType(PresetManager.presetType_activity);
				modelNew.setInfo(model.getInfo());
				modelNew.setRemark(model.getRemark());

				maxActivityId++;
				PresetActivity pActivity = model.getValue(PresetActivity.class);
				pActivity.setId((int) maxActivityId);
				pActivity.setCid((int) maxActivityId);
				pActivity.setCategory(category);
				modelNew.setValue(pActivity);

				PresetManager.getInstance().insertByDAO(modelNew);
			}
		}

		return "@创建成功";
	}

	public long getMaxPresetActivity() {
		long max = 0;
		List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_activity);
		for (PresetModel pModel : pModels) {
			PresetActivity presetData = pModel.getValue(PresetActivity.class);
			if (presetData.getId() > max)
				max = presetData.getId();
		}

		return max;
	}

	// 删除预设页面
	@LoginCheck
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "/activity/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "activity/list?mainView=true");
	}

	@MainView
	@LoginCheck
	@Get("list2")
	public String list2(Invocation inv) throws Throwable {
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();
		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("servers", servers);
		return "activitypreset_list2";
	}

	@Post("listJsonByServer")
	public String listJsonByServer(Invocation inv, @Param("srvId") int srvId) throws IOException {

		// 标题过滤str
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel server = serverComponent.getDAO().get(srvId);

		if (server == null)
			return "@" + OperationExt.queryToJson(null);

		IClient client = server.createClient();
		if (client == null)
			return "@" + OperationExt.queryToJson(null);

		final GetActivityListWCS.Builder b = GetActivityListWCS.newBuilder();
		// 发送并等待回馈
		GetActivityListWSC retMsg = null;
		retMsg = client.call(Head.H19002, b.build(), GetActivityListWSC.class);

		if (retMsg == null)
			return "@" + OperationExt.queryToJson(null);

		List<ActivityItem> items = retMsg.getActivitysList();

		List<Map<String, Object>> lst = new ArrayList<>();

		for (ActivityItem item : items) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", item.getId());

			// map.put("cid", item.getCid());
			map.put("type", getActTypeName(item.getType()));
			map.put("order", item.getOrder());

			int startDayTime = item.getStartDayTime();
			int endDayTime = item.getEndDayTime();
			if (startDayTime <= 0 && endDayTime <= 0) {
				map.put("startTime", "无限期");
				map.put("endTime", "无限期");
			} else {
				map.put("startTime", TimeUtils.toString(startDayTime, "yyyy-MM-dd"));
				map.put("endTime", TimeUtils.toString(endDayTime, "yyyy-MM-dd"));
			}
			lst.add(map);
		}

		return "@" + OperationExt.queryToJson(lst);
	}

	@MainView
	@LoginCheck
	@Get("list3")
	public String list3(Invocation inv) throws Throwable {

		// 设置参数
		List<PresetModel> presetModel = PresetManager.getInstance().getPresetList(PresetManager.presetType_activity);
		List<PresetActivity> presetDatas = new ArrayList<>();
		for (PresetModel model : presetModel) {
			PresetActivity presetData = model.getValue(PresetActivity.class);
			if (presetData != null)
				presetDatas.add(presetData);
		}

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("presetModel", presetDatas);

		return "activitypreset_list3";
	}

	@Post("listJsonByActivityId")
	public String listJsonByActivityId(Invocation inv, @Param("id") int id) throws IOException {
		List<PresetModel> presetModel = PresetManager.getInstance().getPresetList(PresetManager.presetType_activity);
		PresetActivity presetData = null;
		for (PresetModel model : presetModel) {
			PresetActivity presetDataTemp = model.getValue(PresetActivity.class);
			if (presetDataTemp != null && presetDataTemp.getId() == id) {
				presetData = presetDataTemp;
				break;
			}
		}
		if (presetModel == null)
			return "@" + OperationExt.queryToJson(null);

		if (presetData == null)
			return "@" + OperationExt.queryToJson(null);

		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> servers = serverComponent.getDAO().getList();

		if (servers == null || servers.isEmpty())
			return "@" + OperationExt.queryToJson(null);

		List<Map<String, Object>> lst = new ArrayList<>();
		for (ServerModel server : servers) {
			IClient client = server.createClient();
			if (client == null)
				continue;
				//return "@" + OperationExt.queryToJson(null);

			final GetActivityListWCS.Builder b = GetActivityListWCS.newBuilder();
			b.setActivityId(id);
			// 发送并等待回馈
			GetActivityListWSC retMsg = null;
			retMsg = client.call(Head.H19002, b.build(), GetActivityListWSC.class);
			if (retMsg == null)
				continue;

			List<ActivityItem> items = retMsg.getActivitysList();

			if (!items.isEmpty()) {
				ActivityItem item = items.get(0);
				Map<String, Object> map = new HashMap<>();
				map.put("id", server.getId());
				map.put("name", server.getName());
				map.put("a_id", item.getId());
				map.put("a_type", getActTypeName(item.getType()));
				int startDayTime = item.getStartDayTime();
				int endDayTime = item.getEndDayTime();
				if (startDayTime <= 0 && endDayTime <= 0) {
					map.put("startTime", "无限期");
					map.put("endTime", "无限期");
				} else {
					map.put("startTime", TimeUtils.toString(startDayTime, "yyyy-MM-dd"));
					map.put("endTime", TimeUtils.toString(endDayTime, "yyyy-MM-dd"));
				}
				lst.add(map);
			}
		}

		return "@" + OperationExt.queryToJson(lst);
	}
	
	private String getActTypeName(int type){
		ActivityMgr activityMgr = ComponentManager.getComponent(ActivityMgr.class);
		List<ActivityModel> activityModels = activityMgr.getList();
		for(ActivityModel model : activityModels){
			if(model.getType() == type)
				return model.getName();
		}
		return "未知活动类型:"+type;
	}
}
