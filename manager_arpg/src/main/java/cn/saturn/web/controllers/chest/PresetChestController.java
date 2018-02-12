package cn.saturn.web.controllers.chest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.controllers.chest.dao.PresetChest;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * @author rodking
 *
 */
@Path("PresetChest")
public class PresetChestController {

	/**
	 * html 中 easyui 框架中的<br/>
	 * grid 控件数据<br />
	 * 
	 * @param inv
	 * @param id
	 * @param aid
	 * @return
	 */
	@Post("getGrid2EasyUi")
	public String getGrid2EasyUi(Invocation inv, @Param("id") long id, @Param("a_id") int aid) {
		List<PresetModel> pMgrs = PresetManager.getPresetList(PresetManager.presetType_chest);
		List<PresetChest> list = new ArrayList<>();

		for (PresetModel model : pMgrs) {
			PresetChest presetDrop = model.getValue(PresetChest.class);
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
				PresetChest model = list.get(i);
				int chestId = model.getId();
				rowItem.put("chestId", chestId);
				String chestName = model.getChestName();
				rowItem.put("chestName", chestName);
				String chestDescription = model.getChestDesc();
				rowItem.put("chestDescription", chestDescription);
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
	public String getSelect2EasyUi(Invocation inv) {

		List<PresetModel> pMgrs = PresetManager.getPresetList(PresetManager.presetType_chest);
		List<PresetChest> list = new ArrayList<>();

		for (PresetModel model : pMgrs) {
			PresetChest presetDrop = model.getValue(PresetChest.class);
			if (presetDrop != null)
				list.add(presetDrop);
		}

		int size = list.size();
		List<Object> lists = new ArrayList<>();
		Map<Object, Object> defaultMap = new HashMap<>();
		defaultMap.put("id", 0);
		defaultMap.put("name", "--请选择--");
		lists.add(defaultMap);

		for (int i = 0; i < size; i++) {
			Map<Object, Object> itemMap = new HashMap<>();
			PresetChest model = list.get(i);
			itemMap.put("id", model.getId());
			itemMap.put("name", model.getId() + "  " + model.getChestName());
			lists.add(itemMap);
		}

		return "@" + JSONObject.toJSONString(lists);
	}

}
