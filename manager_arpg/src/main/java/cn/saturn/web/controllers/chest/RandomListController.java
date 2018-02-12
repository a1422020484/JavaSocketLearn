package cn.saturn.web.controllers.chest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.alibaba.fastjson.JSON;
import cn.saturn.web.controllers.chest.dao.RandomNumListModel;
import cn.saturn.web.controllers.chest.dao.RandomNumListModelManager;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 
 * @author rodking
 *
 */
@Path("RandomList")
public class RandomListController {

	@Resource
	RandomNumListModelManager randomMgr;

	/**
	 * html 中 easyui 框架中的<br/>
	 * select 控件数据<br />
	 * 
	 * @param inv
	 * @return
	 */
	@Post("getSelect2EasyUi")
	public String getSelect2EasyUi(Invocation inv) {

		@SuppressWarnings("static-access")
		List<RandomNumListModel> list = randomMgr.getModels();
		int size = list.size();
		List<Object> lists = new ArrayList<>();
		Map<Object, Object> defaultMap = new HashMap<>();
		defaultMap.put("id", 0);
		defaultMap.put("name", "--请选择--");
		lists.add(defaultMap);

		for (int i = 0; i < size; i++) {
			Map<Object, Object> itemMap = new HashMap<>();
			RandomNumListModel model = list.get(i);
			itemMap.put("id", model.getRandomNumListId());
			itemMap.put("name", model.getRandomNumListId() + "  ");
			lists.add(itemMap);
		}

		return "@" + JSON.toJSONString(lists);
	}
}
