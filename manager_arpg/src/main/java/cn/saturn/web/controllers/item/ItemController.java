package cn.saturn.web.controllers.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.alibaba.fastjson.JSONObject;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * @author rodking 物品表 key - name
 */
@Path("")
public class ItemController {

	@Resource
	ChestModelManager chestMgr;

	@Resource
	ItemModelManager itemMgr;

	/**
	 * html 中 easyui 框架中的<br/>
	 * select 控件数据<br />
	 * item.xml<br />
	 * 
	 * @param inv
	 * @return
	 */
	@Post("getSelect2EasyUi")
	public String getSelect2EasyUi(Invocation inv) {
		List<ItemModel> list = ItemModelManager.getModels();

		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", "0");
		map.put("name", "--请选择--");
		objList.add(map);

		for (int i = 0; i < list.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			ItemModel item = list.get(i);
			itemMap.put("id", item.getItemId());
			itemMap.put("name", item.getItemId() + "  " + item.getItemName());
			objList.add(itemMap);
		}
		return "@" + JSONObject.toJSONString(objList);
	}

	/**
	 * html 中 easyui 框架中的<br/>
	 * select 控件数据<br />
	 * item.xml,chest.xml<br />
	 * 
	 * @param inv
	 * @return
	 */
	@Post("getSelect2EasyUiAll")
	public String getSelect2EasyUiAll(Invocation inv) {

		List<ItemModel> list1 = ItemModelManager.getModels();
		List<ChestModel> list2 = ChestModelManager.getModels();
		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", "0");
		map.put("name", "--请选择--");
		objList.add(map);

		for (int i = 0; i < list1.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			ItemModel item = list1.get(i);
			itemMap.put("id", item.getItemId());
			itemMap.put("name", item.getItemId() + "  " + item.getItemName());
			objList.add(itemMap);
		}

		for (int i = 0; i < list2.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			ChestModel item = list2.get(i);
			itemMap.put("id", item.getChestId());
			itemMap.put("name", item.getChestId() + "  " + item.getChestName());
			objList.add(itemMap);
		}

		return "@" + JSONObject.toJSONString(objList);
	}
}
