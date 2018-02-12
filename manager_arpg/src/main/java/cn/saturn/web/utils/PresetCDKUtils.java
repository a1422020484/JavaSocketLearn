package cn.saturn.web.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;

public class PresetCDKUtils {

	/**
	 * 将每项4个数据变为 josn<br>
	 * 
	 * @param row
	 * @param req
	 * @param ids
	 * @param num
	 * @return
	 */
	public static String getJsonItem(int row, long item_id, long item_num) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("id", row);
		jsonMap.put("item_id", item_id);
		jsonMap.put("item_num", item_num);
		String itemName = getItemAndChestById((int)item_id);
		jsonMap.put("item_name", itemName);

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
	
	public static String getAwards(int item_id,int item_num){
		return "";
	}

}
