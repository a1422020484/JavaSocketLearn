package cn.saturn.web.controllers.cdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.alibaba.fastjson.JSONObject;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.cdk.dao.PresetUCCDK;
import cn.saturn.web.controllers.cdk.dao.UCGift;
import cn.saturn.web.controllers.cdk.dao.UCGiftDAO;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.PresetActivityUtils;
import cn.saturn.web.utils.PresetCDKUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("uc")
public class UCCDKController {

	@Resource
	UCGiftDAO giftDao;
	
	@Resource
	PresetDAO presetDAO;

	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/cdk/uc/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/cdk/uc/add"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/cdk/uc/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/cdk/uc/toDelete"));
		Utils.setAttributeValue(inv, "uploadUrl", Utils.url(inv, "/cdk/uc/upload"));

		return "uc/list";
	}

	@UserAuthority(PageModel.cdk_page)
	@Get("add")
	public String add(Invocation inv) {
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/cdk/uc/toEdit"));

		PresetModel model = null;
		PresetUCCDK presetData = null;
		model = new PresetModel();
		//model.resetId();
		model.setType(PresetManager.presetType_uccdk);
		presetData = new PresetUCCDK();
		presetData.setId((int) PresetManager.getInstance().newId());

		model.setValue(presetData);
		//PresetManager.getInstance().insertByDAO(model);
		int modelId=presetDAO.insertNew(model);
		
		Utils.setAttributeValue(inv, "id", modelId);
		return "uc/edit";
	}

	@UserAuthority(PageModel.cdk_page)
	@Get("edit")
	public String edit(Invocation inv) {
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/cdk/uc/toEdit"));
		long id = Utils.getLongParameter(inv, "id");
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id 不存在.");

		PresetUCCDK presetData = model.getValue(PresetUCCDK.class);
		if (presetData == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "presetData 不存在.");

		Utils.setAttributeValue(inv, "id", id);
		Utils.setAttributeValue(inv, "name", presetData.getName());
		Utils.setAttributeValue(inv, "num", presetData.getNum());
		Utils.setAttributeValue(inv, "type", presetData.getType());
		Utils.setAttributeValue(inv, "gift_id", presetData.getGift_id());
		return "uc/edit";
	}

	@UserAuthority(PageModel.uc_cdk_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("name") String name, @Param("id") long id, @Param("num") int num,
			@Param("type") int type, @Param("gift_id") String gfit_id) throws Throwable {
		PresetModel model = null;
		PresetUCCDK presetData = null;

		model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");

		presetData = model.getValue(PresetUCCDK.class);
		if (presetData == null) {
			presetData = new PresetUCCDK();
		}

		presetData.setCreateTime(WebUtils.Html.getDate0(DateUtils.getNowDay()));
		presetData.setNum(num);
		presetData.setType(type);
		presetData.setName(name);
		presetData.setGift_id(gfit_id);

		// 设置
		StringBuilder sb = new StringBuilder();
		List<String> lst = ListExtUtil.arrayToList(presetData.getAwards());
		for (int i = 0; i < lst.size(); i++)
			sb.append(lst.get(i)).append(i < lst.size() - 1 ? ";" : "");

		UCGift gift = new UCGift();
		gift.setGiftId(gfit_id);
		gift.setContent(sb.toString());
		gift.setCount(num);
		gift.setType(type);
		gift.setTitle(name);
		
		giftDao.insertOrUpdate(gift);
		// 删除 redis 中的数据
	    RedisUtils.del(RedisKeys.K_SERVER);
		
		model.setValue(presetData);

		PresetManager.getInstance().updateByDAO(model);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "创建uc cdk 成功.");
	}

	@LoginCheck
	@UserAuthority(PageModel.uc_cdk_page)
	@Post("saveAward")
	public String saveRewAndReq(Invocation inv, @Param("id") long id, @Param("item_id") long item_id,
			@Param("item_num") long item_num) {
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";
		PresetUCCDK presetData = model.getValue(PresetUCCDK.class);

		// 条件
		List<String> awardList = ListExtUtil.arrayToList(presetData.getAwards());
		awardList.add(item_id + "_" + item_num);
		if (awardList.size() > 20)
			return "@{'msg':'必须小于20个'}";
		presetData.setAwards(ListExtUtil.listToArray(awardList, new String[0]));

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		String result = PresetCDKUtils.getJsonItem(awardList.size() - 1, item_id, item_num);
		// 转成json
		return "@" + result;
	}

	@LoginCheck
	@UserAuthority(PageModel.uc_cdk_page)
	@Post("updateAward")
	public String updateAward(Invocation inv, @Param("id") long id, @Param("row") int row,
			@Param("item_id") int item_id, @Param("item_num") int item_num) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";
		PresetUCCDK presetData = model.getValue(PresetUCCDK.class);
		// 条件
		String[] awards = presetData.getAwards();
		awards[row] = PresetCDKUtils.getAwards(item_id, item_num);
		presetData.setAwards(awards);

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		String result = PresetCDKUtils.getJsonItem(row, item_id, item_num);
		// 转成json
		return "@" + result;
	}

	@UserAuthority(PageModel.uc_cdk_page)
	@Post("getRewAndReq")
	public String getRewAndReq(Invocation inv, @Param("id") long id) {

		String[] awards = null;
		PresetModel model = null;
		PresetUCCDK presetData = null;
		model = PresetManager.getInstance().get(id);
		if (null != model) {
			presetData = model.getValue(PresetUCCDK.class);
			awards = presetData.getAwards();
		}

		int a_size = (awards != null) ? awards.length : 0;
		// 重构使用json 来拼接
		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", a_size);
		dataObj.put("id", id);

		List<Object> rowsList = new ArrayList<>();
		for (int i = 0; i < a_size; i++) {
			Map<Object, Object> rowsItem = new HashMap<>();
			rowsItem.put("id", i);

			String itemStr = awards[i];
			String[] items = itemStr.split("_");
			if (items.length == 2) {
				rowsItem.put("item_id", items[0]);
				int itemId = Integer.parseInt(items[0]);
				String itemName = PresetActivityUtils.getItemAndChestById(itemId);
				rowsItem.put("item_name", itemName);
				rowsItem.put("item_num", items[1]);
			}
			rowsList.add(rowsItem);
		}

		dataObj.put("rows", rowsList);
		String result = JSONObject.toJSONString(dataObj);
		return "@" + result;
	}

	// 删除预设页面
	@LoginCheck
	@UserAuthority(PageModel.uc_cdk_page)
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "/cdk/uc/list");
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
		PresetUCCDK presetData = model.getValue(PresetUCCDK.class);

		if (presetData == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");

		giftDao.remove(presetData.getGift_id());
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "cdk/uc/list?mainView=true");
	}

	@Get("showAward")
	public String showAward(Invocation inv) {
		return "uc/cdk_award";
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
				PresetUCCDK presetData = model.getValue(PresetUCCDK.class);
				if (presetData != null) {
					map.put("name", presetData.getName());
					map.put("type", presetData.getType());
					map.put("ctime", presetData.getCreateTime());
					map.put("content", presetData.getAwards());
					map.put("count", presetData.getNum());
					map.put("giftId", presetData.getGift_id());
				}
				return true;
			}
		};

		return PresetManager.listJson(inv, PresetManager.presetType_uccdk, action);
	}
}
