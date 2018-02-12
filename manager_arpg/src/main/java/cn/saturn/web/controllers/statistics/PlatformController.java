package cn.saturn.web.controllers.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;

import cn.saturn.web.controllers.statistics.dao.PlatformDAO;
import cn.saturn.web.controllers.statistics.dao.PlatformModel;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

@Path("platform")
public class PlatformController {
	@Resource
	PlatformDAO platformDAO;

	@Post("getSelect2PlatformEasyUi")
	public String getSelect2PlatformEasyUi(Invocation inv) {
		List<PlatformModel> platforms = platformDAO.getListPlaform();
		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", "-1");
		map.put("name", "--请选择--");
		objList.add(map);

		for (int i = 0; i < platforms.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			PlatformModel item = platforms.get(i);
			itemMap.put("id", item.getPlatform());
			itemMap.put("name", item.getId() + "  " + item.getPlatform());
			objList.add(itemMap);
		}
		return "@" + JSONObject.toJSONString(objList);
	}

	@Post("getSelect2SubPlatformEasyUi")
	public String getSelect2SubPlatformEasyUi(Invocation inv, @Param("platfrom") String platform) {
		List<PlatformModel> platforms = platformDAO.getListByPlaform(platform);
		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", "-1");
		map.put("name", "--请选择--");
		objList.add(map);

		for (int i = 0; i < platforms.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			PlatformModel item = platforms.get(i);
			itemMap.put("id", item.getSubPlatform_code());
			itemMap.put("name", item.getSubPlatform_code() + "  " + item.getSubPlatform_name());
			objList.add(itemMap);
		}
		return "@" + JSONObject.toJSONString(objList);
	}
}
