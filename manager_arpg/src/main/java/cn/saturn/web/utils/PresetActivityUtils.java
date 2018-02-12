package cn.saturn.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.controllers.activity.dao.PresetActivity;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import xzj.core.util.Tool;

public class PresetActivityUtils {

	/**
	 * 轮盘物品list<br>
	 * 
	 * @param id
	 * @param num
	 * @param order
	 * @param weight
	 * @param min_cs
	 * @param max_cs
	 * @return
	 */
	public static String toItemList(long itemId, int num, int order, int weight, int min_cs, int max_cs) {
		return itemId + "_" + num + "_" + order + "_" + weight + "_" + min_cs + "_" + max_cs;
	}

	/**
	 * 轮盘次数奖励<br>
	 * 
	 * @return
	 */
	public static String toTurnTableCsAward(int type, int itemId, int cz_1, int cs_1, int cz_2, int cs_2, int cz_3,
			int cs_3, int cz_4, int cs_4) {
		String str = "";
		if (type == 1)
			str =  cz_1 + "_0_" + cs_1;
		else if (type == 2)
			str = cz_2 + "_0_" + cs_2;
		else if (type == 3)
			str = itemId + "_" + cz_3 + "_" + cs_3;
		else
			str = cz_4 + "_0_" + cs_4;
		return str;
	}

	public static String toTurnTableCsAwardJson(int row, int type, int itemId, int cz_1, int cs_1, int cz_2, int cs_2,
			int cz_3, int cs_3, int cz_4, int cs_4) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", row);
		jsonMap.put("item_id", itemId);
		String itemName = getItemAndChestById((int) itemId);
		jsonMap.put("item_name", itemName);
		jsonMap.put("cz_1", cz_1);
		jsonMap.put("cs_1", cs_1);
		jsonMap.put("cz_2", cz_2);
		jsonMap.put("cs_2", cs_2);
		jsonMap.put("cz_3", cz_3);
		jsonMap.put("cs_3", cs_3);
		jsonMap.put("cz_4", cz_4);
		jsonMap.put("cs_4", cs_4);
		return JSONObject.toJSONString(jsonMap);
	}

	public static String toItemListJson(int row, long id, int num, int order, int weight, int min_cs, int max_cs) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", row);
		jsonMap.put("item_id", id);
		jsonMap.put("item_num", num);
		String itemName = getItemAndChestById((int) id);
		jsonMap.put("item_name", itemName);
		jsonMap.put("item_order", order);
		jsonMap.put("item_weight", weight);
		jsonMap.put("item_min_cs", min_cs);
		jsonMap.put("item_max_cs", max_cs);
		return JSONObject.toJSONString(jsonMap);
	}

	public static String toItemListEasyUIGrid(List<String> itemList, long id) {

		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", itemList.size());
		dataObj.put("id", id);

		List<Object> rowsList = new ArrayList<>();
		for (int i = 0; i < itemList.size(); i++) {
			Map<Object, Object> rowsItem = new HashMap<>();
			String item = itemList.get(i);
			String[] items = item.split("_");
			rowsItem.put("id", i);

			if (items.length == 6) {
				rowsItem.put("item_id", items[0]);
				rowsItem.put("item_num", items[1]);
				rowsItem.put("item_order", items[2]);
				rowsItem.put("item_weight", items[3]);
				rowsItem.put("item_min_cs", items[4]);
				rowsItem.put("item_max_cs", items[5]);

				int itemId = Integer.parseInt(items[0]);
				String itemName = getItemAndChestById(itemId);
				rowsItem.put("item_name", itemName);

			}

			rowsList.add(rowsItem);
		}

		dataObj.put("rows", rowsList);
		return JSONObject.toJSONString(dataObj);
	}

	public static String toAddTimesEasyUIGrid(List<String> turnTableCsAward, int type, long id) {

		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", turnTableCsAward.size());
		dataObj.put("id", id);

		List<Object> rowsList = new ArrayList<>();
		for (int i = 0; i < turnTableCsAward.size(); i++) {
			Map<Object, Object> rowsItem = new HashMap<>();
			String item = turnTableCsAward.get(i);
			String[] items = item.split("_");
			rowsItem.put("id", i);

			if (items.length == 3) {
				if (type == 1) {
					rowsItem.put("cz_1", items[0]);
					rowsItem.put("cs_1", items[2]);
				} else if (type == 2) {
					rowsItem.put("cz_2", items[0]);
					rowsItem.put("cs_2", items[2]);
				} else if (type == 3) {
					rowsItem.put("item_id", items[0]);
					rowsItem.put("cz_3", items[1]);
					rowsItem.put("cs_3", items[2]);
					int itemId = Integer.parseInt(items[0]);
					String itemName = getItemAndChestById(itemId);
					rowsItem.put("item_name", itemName);
				} else {
					rowsItem.put("cz_4", items[0]);
					rowsItem.put("cs_4", items[2]);
				}

			}

			rowsList.add(rowsItem);
		}

		dataObj.put("rows", rowsList);
		return JSONObject.toJSONString(dataObj);
	}

	/**
	 * 将requires 解析成可理解的内容
	 * 
	 * @param conditionDes
	 * @param require
	 * @return
	 */
	public static String getConditionDesc(String conditionDes, String require, int type, int SpeArgs) {
		if (conditionDes.equals("无"))
			return conditionDes;
		StringBuilder sb = new StringBuilder();
		String[] requiress = require.split(";");
		for (int i = 0; i < requiress.length; i++) {
			String[] requires = requiress[i].split("_");
			if (type == 14 || type == 22 || type == 24 || type == 23 || type == 18) {
				// 条件格式 xx_xx
				if (requires.length == 2) {
					String itemName = getItemName(Tool.convertType(requires[0], Integer.class), type);
					sb.append(String.format(conditionDes, itemName, requires[1]))
							.append(i < requiress.length - 1 ? ";" : "");
				} else if (requires.length == 1) {

					String itemName = getItemName(Tool.convertType(requires[0], Integer.class), type);
					sb.append(String.format(conditionDes, itemName, "")).append(i < requiress.length - 1 ? ";" : "");
				}
			} else {

				if (requires.length >= 1) {
					if (type == PresetActivity.activeType_tian_tian) {
						int value = Integer.parseInt(requires[0]);
						sb.append(String.format("充值{%s}元" + (value == 1 ? "连续{%s}天" : "累计{%s}天"), SpeArgs, value, ""))
								.append(i < requiress.length - 1 ? ";" : "");
					} else // 条件格式 xx
						sb.append(String.format(conditionDes, requires[0], ""))
								.append(i < requiress.length - 1 ? ";" : "");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获得物品id
	 * 
	 * @param itemId
	 * @return
	 */
	public static String getItemName(int itemId, int type) {
		if (type == 14) {
			ItemModel itemModel = ItemModelManager.getById(itemId);
			ChestModel chestModel = ChestModelManager.get(itemId);
			if (null != itemModel)
				return itemModel.getItemId() + "(" + itemModel.getItemName() + ")";

			if (null != chestModel)
				return chestModel.getChestId() + "(" + chestModel.getChestName() + ")";

			return itemId + "()";
		}
		return itemId + "";
	}

	/**
	 * 将每项4个数据变为 josn<br>
	 * 
	 * @param row
	 * @param req
	 * @param ids
	 * @param num
	 * @return
	 */
	public static String getJsonItem4(int row, String req, int[] ids, int[] num) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		int index = 0;
		jsonMap.put("id", row);
		jsonMap.put("req", req);
		for (int i = 0; i < ids.length; i++) {
			index = i + 1;
			jsonMap.put("item_id_0" + index, ids[i]);
			jsonMap.put("item_num_0" + index, num[i]);
			String itemName = getItemAndChestById(ids[i]);
			jsonMap.put("item_name_0" + index, itemName);
		}

		return JSONObject.toJSONString(jsonMap);
	}

	/**
	 * 返回物品名称 <br>
	 * 查找对象 ItemModelManager & ChestModelManager<br>
	 * 
	 * @param id
	 * @return
	 */
	public static String getItemAndChestById(int id) {
		String result = "";

		ItemModel itemModel = ItemModelManager.getById(id);
		ChestModel chestModel = null;

		if (itemModel == null)
			chestModel = ChestModelManager.get(id);
		else
			result = itemModel.getItemName();

		if (chestModel != null)
			result = chestModel.getChestName();

		return result;

	}

}
