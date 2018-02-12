package cn.saturn.web.controllers.chest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.saturn.web.controllers.activity.dao.PresetActivity;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.chest.dao.ChestLog;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.chest.dao.PresetChest;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.controllers.logs.dao.LogDAO;
import cn.saturn.web.controllers.logs.dao.LogModel;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerXFtpDAO;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.EasyUiUtils;
import cn.saturn.web.utils.PageModel;
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

/**
 * 宝箱
 * 
 * @author rodking
 *
 */
@Path("")
public class ChestController {

	public static final String chestType_random = "1"; // 随机宝箱
	public static final String chestType_select = "2"; // 选择奖励宝箱
	public static final String chestType_allrandom = "3"; // 全随机宝箱

	/** 白 */
	public static final String chestQuality_white = "1";
	/** 绿 */
	public static final String chestQuality_green = "2";
	/** 蓝 */
	public static final String chestQuality_blue = "3";
	/** 紫 */
	public static final String chestQuality_purple = "4";
	/** 橙 */
	public static final String chestQuality_orange = "5";

	@Resource
	ChestModelManager chestMgr;

	@Resource
	ItemModelManager itemMgr;

	@Resource
	LogDAO logDao;

	@Resource
	ServerDAO serverDao;

	@Resource
	ServerXFtpDAO serverXFtpDao;
	
	@Resource
	PresetDAO presetDAO;
	
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/chest/send");
	}

	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/chest/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/chest/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/chest/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/chest/toDelete"));
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/chest/send"));
		Utils.setAttributeValue(inv, "checkUrl", Utils.url(inv, "/chest/srvList"));

		return "chestpreset_list";
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
				PresetChest presetDrop = model.getValue(PresetChest.class);
				if (presetDrop != null) {
					map.put("chestName", presetDrop.getChestName());
					map.put("chestDesc", presetDrop.getChestDesc());

				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_chest, action);
	}

	@Get("showCondition")
	public String showCondition(Invocation inv) {
		return "chest_condition";
	}

	@LoginCheck
	@UserAuthority(PageModel.page_chest)
	@Post("destroyChestReq")
	public String destroyChestReq(Invocation inv, @Param("id") int id, @Param("row") int row) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + EasyUiUtils.getFail();
		PresetChest presetData = model.getValue(PresetChest.class);

		// 物品列表
		int[] items = presetData.getItems();
		List<Integer> itemList = new ArrayList<>();
		for (int str : items)
			itemList.add(str);

		if (row < itemList.size())
			itemList.remove(row);

		items = new int[itemList.size()];
		for (int i = 0; i < itemList.size(); i++)
			items[0] = itemList.get(i);

		presetData.setItems(items);

		// 物品数量列表
		int[] nums = presetData.getNums();
		List<Integer> numList = new ArrayList<>();
		for (int str : nums)
			numList.add(str);

		if (row < numList.size())
			numList.remove(row);

		nums = new int[numList.size()];
		for (int i = 0; i < numList.size(); i++)
			nums[0] = numList.get(i);

		presetData.setNums(nums);

		// 物品权重
		int[] weights = presetData.getWeights();
		List<Integer> weightList = new ArrayList<>();
		for (int str : weights)
			weightList.add(str);

		if (row < weightList.size())
			weightList.remove(row);

		weights = new int[weightList.size()];
		for (int i = 0; i < weightList.size(); i++)
			weights[0] = weightList.get(i);

		presetData.setWeights(weights);

		// 物品随机
		int[] randoms = presetData.getRandomListIds();
		List<Integer> rList = new ArrayList<>();
		for (int str : randoms)
			rList.add(str);

		if (row < rList.size())
			rList.remove(row);

		randoms = new int[rList.size()];

		for (int i = 0; i < rList.size(); i++)
			randoms[0] = rList.get(i);

		presetData.setRandomListIds(randoms);

		if (!model.setValue(presetData))
			return "@" + EasyUiUtils.getFail();

		PresetManager.getInstance().insertByDAO(model);

		return "@" + EasyUiUtils.getSuccess();
	}

	@LoginCheck
	@UserAuthority(PageModel.page_chest)
	@Post("saveChestRew")
	public String saveChestRew(Invocation inv, @Param("id") long id, @Param("req") String req,
			@Param("item_id") int item_id, @Param("weight") int weight, @Param("num") int num,
			@Param("randomListId") int randomListId, @Param("type") int type) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";

		PresetChest presetData = model.getValue(PresetChest.class);

		if (type == 3 && randomListId == 0)
			return "@{'msg':'随机数量宝箱,必选 randomListId(掉落列表)!'}";

		// 物品列表
		int[] items = presetData.getItems();
		List<Integer> itemList = new ArrayList<>();

		if (null != items) {

			if (items.length >= 20)
				return "@{'msg':'必须小于20个'}";

			for (int item : items)
				itemList.add(item);
		}
		itemList.add(item_id);

		items = new int[itemList.size()];
		for (int i = 0; i < itemList.size(); i++)
			items[i] = itemList.get(i);

		// 物品数量
		int[] nums = presetData.getNums();
		List<Integer> numList = new ArrayList<>();

		if (null != nums) {
			for (int numstr : nums)
				numList.add(numstr);
		}
		numList.add(num);

		nums = new int[numList.size()];
		for (int i = 0; i < numList.size(); i++)
			nums[i] = numList.get(i);

		// 物品权重
		int[] weights = presetData.getWeights();
		List<Integer> weightList = new ArrayList<>();
		if (null != weights) {
			for (int weightstr : weights)
				weightList.add(weightstr);
		}
		weightList.add(weight);

		weights = new int[weightList.size()];
		for (int i = 0; i < weightList.size(); i++)
			weights[i] = weightList.get(i);

		// 物品随机掉落id
		int[] randomListIds = presetData.getRandomListIds();
		List<Integer> randomList = new ArrayList<>();
		if (null != randomListIds) {
			for (int randomstr : randomListIds)
				randomList.add(randomstr);
		}

		randomList.add(randomListId);

		randomListIds = new int[randomList.size()];
		for (int i = 0; i < randomList.size(); i++)
			randomListIds[i] = randomList.get(i);

		// 部分参数验证
		if (num < 1)
			return "@{'msg':'宝箱 数量必须大于 0 '}";

		if (type == ChestModel.chestType_allrandom) {
			if (weight != 100)
				return "@{'msg':'全随机宝箱 权重必须为 100'}";
			if (num != 1)
				return "@{'msg':'全随机宝箱 数量必须为 1 '}";
			if (randomListId == 0)
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "全随随机宝箱中,物品必须配置一个 randomList{}!");
		}

		presetData.setItems(items);
		presetData.setNums(nums);
		presetData.setWeights(weights);
		presetData.setRandomListIds(randomListIds);

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);

		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", itemList.size() - 1);
		jsonMap.put("item_id", item_id);
		jsonMap.put("weight", weight);
		jsonMap.put("num", num);
		jsonMap.put("randomListId", randomListId);

		jsonMap.put("req", req);

		ItemModel itemModel = ItemModelManager.getById(item_id);

		if (itemModel != null)
			jsonMap.put("item_name", itemModel.getItemName());

		// 转成json
		return "@" + JSON.toJSONString(jsonMap);
	}

	@LoginCheck
	@UserAuthority(PageModel.page_chest)
	@Post("updateChestRew")
	public String updateChestRew(Invocation inv, @Param("id") long id, @Param("row") int row,
			@Param("item_id") int item_id, @Param("weight") int weight, @Param("num") int num,
			@Param("randomListId") int randomListId, @Param("type") int type) {
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";

		if (type == 3 && randomListId == 0)
			return "@{'msg':'随机数量宝箱,必选 randomListId(掉落列表)!'}";

		PresetChest presetData = model.getValue(PresetChest.class);

		if (presetData == null)
			return "@{'msg':'找不到数据'}";

		// 物品id 权重 数量 随机列表
		int[] items = presetData.getItems();
		int[] weights = presetData.getWeights();
		int[] nums = presetData.getNums();
		int[] randomLists = presetData.getRandomListIds();

		// 权重 数量 随机列表
		items[row] = item_id;
		weights[row] = weight;
		nums[row] = num;
		randomLists[row] = randomListId;

		// 部分参数验证
		if (num < 1)
			return "@{'msg':'宝箱 数量必须大于 0 '}";

		if (type == ChestModel.chestType_allrandom) {
			if (weight != 100)
				return "@{'msg':'全随机宝箱 权重必须为 100'}";
			if (num != 1)
				return "@{'msg':'全随机宝箱 数量必须为 1 '}";
			if (randomListId == 0)
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "全随随机宝箱中,物品必须配置一个 randomList{}!");
		}

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", row);
		jsonMap.put("item_id", item_id);
		jsonMap.put("weight", weight);
		jsonMap.put("num", num);
		jsonMap.put("randomListId", randomListId);

		ItemModel itemModel = ItemModelManager.getById(item_id);

		if (itemModel != null)
			jsonMap.put("item_name", itemModel.getItemName());

		return "@" + JSON.toJSONString(jsonMap);
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.page_chest)
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();

		Map<String, String> quilitys = new HashMap<>();
		quilitys.put(chestQuality_white, "白");
		quilitys.put(chestQuality_green, "绿");
		quilitys.put(chestQuality_blue, "蓝");
		quilitys.put(chestQuality_purple, "紫");
		quilitys.put(chestQuality_orange, "橙");

		Map<String, String> types = new HashMap<>();
		types.put(chestType_random, "随机宝箱");
		types.put(chestType_select, "选择宝箱");
		types.put(chestType_allrandom, "随机数量宝箱");

		request.setAttribute("quils", quilitys);
		request.setAttribute("types", types);

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
			PresetChest presetData = model.getValue(PresetChest.class);
			if (presetData != null) {
				request.setAttribute("cid", presetData.getId());
				request.setAttribute("cidStr", "disabled=\"disabled\"  ");

				request.setAttribute("chestName", presetData.getChestName());
				request.setAttribute("chestDesc", presetData.getChestDesc());
				request.setAttribute("icon", presetData.getIcon());
				request.setAttribute("quility", presetData.getQuility());
				request.setAttribute("max_count", presetData.getMax_count());
				request.setAttribute("type", presetData.getType());
				request.setAttribute("item_num", presetData.getItem_num());
				request.setAttribute("price", presetData.getPrice());

			}

			request.setAttribute("btnStr", "修改");
		}
		request.setAttribute("eidtUrl", Utils.url(inv, "/chest/toEdit"));
		return "chestpreset_edit";
	}

	@UserAuthority(PageModel.page_chest)
	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id, @Param("cid") int cid,
			@Param("quility") int quility, @Param("type") int type, @Param("item_num") int item_num,
			@Param("name") String name, @Param("desc") String desc, @Param("remark") String remark) throws Throwable {

		if (quility < ChestModel.chestQuality_white || quility > ChestModel.chestQuality_orange)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "请配置正确的宝箱品质.");

		if (type < ChestModel.chestType_random || type > ChestModel.chestType_allrandom)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "请配置正确的宝箱类型.");

		item_num = item_num < 0 ? 1 : item_num;

		int max_count = 999;
		int icon = 169000000 + quility;
		int price = 1000;

		// 编辑
		PresetModel model = null;
		PresetChest presetData = null;
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			//model.resetId();
			model.setType(PresetManager.presetType_chest);
			presetData = new PresetChest();

			// 检测pid是否被使用了
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_chest);
			int count = (all != null) ? all.size() : 0;
			for (int i = 0; i < count; i++) {
				PresetModel model0 = all.get(i);
				PresetActivity presetData0 = model0.getValue(PresetActivity.class);
				if (presetData0 == null) {
					continue;
				}
				// 检测ID
				if (presetData0.getId() == cid) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "宝箱ID已存在相同.");
				}
			}

			if (cid <= 169100000 || cid >= 169200000)
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "宝箱id 必须大于  169100000  小于 169120000 ");

			presetData.setId(cid);
		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			}
			presetData = model.getValue(PresetChest.class);
			if (presetData == null) {
				presetData = new PresetChest();
			}
		}

		model.setRemark(remark);
		presetData.setChestName(name);
		presetData.setChestDesc(desc);
		presetData.setIcon(icon);
		presetData.setQuility(quility);
		presetData.setMax_count(max_count);
		presetData.setType(type);
		presetData.setItem_num(item_num);
		presetData.setPrice(price);

		if ((null == presetData.getItems())
				|| (null != presetData.getItems() && item_num >= presetData.getItems().length))
			item_num = 0;
		if (null != presetData.getItems()) {

			int[] randomList = presetData.getRandomListIds();
			int[] weights = presetData.getWeights();
			int[] itemNums = presetData.getNums();

			// 过滤 全随机宝箱 中没有填写 随机列表的
			if (type == ChestModel.chestType_allrandom && randomList != null) {
				for (int random : randomList) {
					if (random == 0)
						return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "全随随机宝箱中,物品必须配置一个 randomList{}!");
				}

				if (itemNums != null) {
					for (int num : itemNums) {
						if (num != 1)
							return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "全随随机宝箱中,物品数量必须配置为  1");
					}
				}
			}

			// 过滤 选择宝箱 中权重 必须为 100
			if (type == ChestModel.chestType_select && weights != null) {
				for (int weight : weights) {
					if (weight != 100)
						return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "选择宝箱中物品权重必须为 100!");
				}
			}

			// 宝箱数量必须 大于0
			if (itemNums != null) {
				for (int num : itemNums) {
					if (num < 1)
						return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "物品奖励必须大于 0");
				}
			}
		}

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

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "处理成功.<br>");
	}

	@Post("getChestRew")
	public String getChestRew(Invocation inv, @Param("id") long id, @Param("a_id") int aid) {

		int[] items = null;
		int[] weights = null;
		int[] nums = null;
		int[] randomListIds = null;
		PresetModel model = null;
		PresetChest presetData = null;

		model = PresetManager.getInstance().get(id);

		if (null != model) {
			presetData = model.getValue(PresetChest.class);
			items = presetData.getItems();
			weights = presetData.getWeights();
			nums = presetData.getNums();
			randomListIds = presetData.getRandomListIds();
		}

		int i_size = (items != null) ? items.length : 0;
		int w_size = (weights != null) ? weights.length : 0;
		int n_size = (nums != null) ? nums.length : 0;
		int r_size = (randomListIds != null) ? randomListIds.length : 0;

		int max_size = Math.max(i_size, w_size);
		max_size = Math.max(max_size, n_size);
		max_size = Math.max(max_size, r_size);

		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", max_size);
		dataObj.put("id", id);
		List<Object> rowsList = new ArrayList<>();

		for (int i = 0; i < max_size; i++) {
			Map<Object, Object> rowItem = new HashMap<>();
			rowItem.put("id", i);
			if (i < r_size) {
				int item = items[i];
				int weight = weights[i];
				int num = nums[i];
				int randomListId = randomListIds[i];
				ItemModel itemModel = ItemModelManager.getById(item);

				rowItem.put("item_id", item);
				rowItem.put("weight", weight);
				rowItem.put("num", num);
				rowItem.put("randomListId", randomListId);

				if (itemModel != null) {
					rowItem.put("item_name", itemModel.getItemName());
				}
			}

			rowsList.add(rowItem);
		}
		dataObj.put("rows", rowsList);

		return "@" + JSONObject.toJSONString(dataObj);
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.page_chest)
	@Get("send")
	public String send(Invocation inv) throws Throwable {
		// 通过日志表得到已经编辑的数据
		LogModel model = logDao.getLastLog(LogModel.chest_type);
		HttpServletRequest request = inv.getRequest();
		if (null != model) {
			ChestLog chestData = model.getValue(ChestLog.class);
			if (null != chestData) {
				int[] srvIds = chestData.getSrvIds();
				int[] chestIds = chestData.getChestIds();
				request.setAttribute("selectSrvIds", srvIds);
				request.setAttribute("chestIds", JSON.toJSONString(chestIds));
			}
		}

		return "chestpreset_send";
	}

	@LoginCheck
	@UserAuthority(PageModel.page_chest)
	@Post("toSend")
	public String toSend(Invocation inv, @Param("srvIds") String srvIdStrs, @Param("chestIds") String chestIdsStr)
			throws Throwable {
		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];

		// 把配置中的文件读出来
		List<ChestModel> chestModels = ChestModelManager.getModels();
		List<ChestModel> models = new ArrayList<>();
		for (ChestModel model : chestModels)
			models.add(model);

		String[] chestIds = chestIdsStr.split(",");
		// 添加选中的宝箱到
		List<PresetModel> pModels = PresetManager.getPresetList(PresetManager.presetType_chest);
		List<PresetChest> pChests = new ArrayList<>();
		for (PresetModel model : pModels)
			pChests.add(model.getValue(PresetChest.class));

		for (String chestId : chestIds) {
			int id = Integer.parseInt(chestId);
			for (PresetChest chest : pChests) {
				if (chest.getId() == id) {
					ChestModel cModel = new ChestModel(chest);
					models.add(cModel);
				}
			}
		}

		chestMgr.writeFile(models);

		List<ResultMsg> sConfMsg = new ArrayList<>();
		List<ServerXFtpModel> sModel = new ArrayList<>();
		for (int srvId : srvIds) {
			ServerXFtpModel model = serverXFtpDao.getBySid(srvId);
			if (model != null)
				sModel.add(model);
			else
				sConfMsg.add(new ResultMsg(ResultMsg.fail, "服务器： " + srvId + " 没有配置"));

		}

		List<ResultMsg> msgs = SftpTools.exec(sModel, ChestModelManager.writeSingle);

		// 做日志
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");

		Map<String, Object> map = new HashMap<>();
		map.put("srvIds", srvIds);
		map.put("chestIds", chestIds);

		String json = JSONObject.toJSONString(map);

		LogModel logModel = LogModel.create(userModel, LogModel.chest_type, json);
		if (logModel != null)
			logDao.insert(logModel);

		String msg = ResultMsgUtils.getResult2Msg(sConfMsg, msgs);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, msg);
	}

	// 删除宝箱宝箱
	@LoginCheck
	@UserAuthority(PageModel.page_chest)
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "/chest/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "chest/list?mainView=true");
	}

	@Get("showChestAdd")
	public String showChestAdd(Invocation inv) {
		return "chest_add";
	}
}
