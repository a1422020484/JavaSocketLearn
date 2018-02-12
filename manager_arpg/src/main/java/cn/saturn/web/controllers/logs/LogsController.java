package cn.saturn.web.controllers.logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cn.saturn.operation.OperationExt;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.controllers.logs.dao.LogDAO;
import cn.saturn.web.controllers.logs.dao.LogModel;
import cn.saturn.web.controllers.statistics.dao.DateParam;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.Invocation;

@Path("")
public class LogsController {

	@Autowired
	LogDAO logDao;
	
	private final String[] mail_title = new String[] { "id","备注", "平台用户", "发送对象(服务器)", "发送时间", "发送对象(全服邮件|单用户)",  "邮件内容", "奖励物品" };
	private final String[] gm_title = new String[] { "id","发送指令", "平台用户", "发送时间", "发送对象(服务器)" };
	private final String[] vpay_title = new String[] { "id","平台用户", "发送对象(服务器)", "发送时间", "接受者(player_id)", "金额", "商品id" };
	private final String[] chest_title = new String[] { "id","平台用户", "发送对象(服务器)", "发送时间", "接受者(player_id)", "金额", "商品id" };
	
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.logs_page)
	@Get("show")
	public String logsShow(Invocation inv, @Param("type") int type) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/logs/"));
		request.setAttribute("dayTime", DateUtils.getNowDay());
		return "logs";
	}

	@LoginCheck
	@UserAuthority(PageModel.logs_page)
	@Get("info")
	public String logsInfo(Invocation inv, @Param("type") int type, @Param("isSelect") int isSelect) throws Throwable {
		return "";
	}

	@Post("getList")
	public String getLst(Invocation inv, @Param("type") int type, @Param("startTime") String startTime,
			@Param("endTime") String endTime) throws Throwable {

		DateParam param = new DateParam();
		String resultTime = "";
		resultTime = param.getParam(startTime, endTime);
		if (param.isError(resultTime))
			return resultTime;

		List<LogModel> models = logDao.getByType(type, resultTime);
		List<String[]> results = new ArrayList<>();
		for (LogModel model : models) {
			String sendTime = model.getLog_time();
			String czName = model.getUser_name();
			String[] strs = getContext(model.getId(),sendTime, czName, model.getContent(), type);
			results.add(strs);
		}

		return "@" + OperationExt.queryToJson(getTitleNames(type), results).toString();
	}

	@UserAuthority(PageModel.logs_page)
	@Post("toRemove")
	public String toRemove(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		if(userModel==null)
			return "@权限未知";	
		if(UserModel.authority_admin !=userModel.getAuthority())
			return "@权限不足";
		logDao.remove(id);
		return "@删除成功";
	}

	public String[] getContext(long id,String sendTime, String czName, String context, int type) {  
		
		JSONObject jsonObj = JSONObject.parseObject(context);
		if (type == 1) {
			String recipientId = jsonObj.getString("recipientId").replace("-100", "全服对象");
			String reward = jsonObj.getString("reward");
			JSONObject jsonObj1 = JSONObject.parseObject(reward);
			String awardlist=jsonObj1.getString("awardList");
			String mailMsg=jsonObj1.getString("mailMsg");
			
			List<ItemModel> list1 = ItemModelManager.getModels();
			List<ChestModel> list2 = ChestModelManager.getModels();
			List<Map<Object, Object>> objList = new ArrayList<Map<Object, Object>>();
			Map<Object, Object> map = new HashMap<Object, Object>();
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
			List<Map<String, Object>> mapList = JSON.parseObject(awardlist,new TypeReference<ArrayList<Map<String, Object>>>(){});
			List<Map<String, Object>> jspList=new ArrayList<Map<String, Object>>();
			//List<List<Map<String, Object>>> jsList=new ArrayList<List<Map<String, Object>>>();
			for(Map<String, Object> mapjson:mapList){
				Map<String, Object> countMap=new HashMap<String, Object>();
				
				int  objId=(Integer)mapjson.get("id");
				int  objCount=(Integer)mapjson.get("count");
				for(Map<Object, Object> obj:objList){
					int itemId=(Integer)obj.get("id");
					if(objId==itemId){
						Object itemName=obj.get("name");
						Map<String, Object> nameMap=new HashMap<String, Object>();
						nameMap.put("名称", itemName);
						jspList.add(nameMap);
					}
					countMap.put("数量", objCount);
				}
				
				
				jspList.add(countMap);
			}
			
			String rewardMail=JSONObject.toJSON(jspList).toString();
			
			
			String ctx = jsonObj.getString("ctx");
			String srvIds = jsonObj.getString("srvIds");
			return new String[] { id+"",ctx, czName, srvIds, sendTime, recipientId,mailMsg, rewardMail};
		} else if (type == 2) {
			String srvIds = jsonObj.getString("srvIds");
			String ctx = jsonObj.getString("ctx");
			return new String[] { id+"",ctx, czName, sendTime, srvIds };
		} else if (type == 3){
			String recipientId = jsonObj.getString("recipientId").replace("-100", "全服对象");
			String money = jsonObj.getString("money");
			String srvIds = jsonObj.getString("srvIds");
			String goodCode = jsonObj.getString("goodCode");
			return new String[] { id+"",czName, srvIds, sendTime, recipientId, money, goodCode };
		}else {
			String srvIds = jsonObj.getString("srvIds");
			String chestIds = jsonObj.getString("chestIds");
			return new String[] { id+"",czName, srvIds, sendTime, srvIds,chestIds };
		}
	}

	public String[] getTitleNames(int type) {
		if (type == 1)
			return mail_title;
		else if (type == 2)
			return gm_title;
		else if(type == 3)
			return vpay_title;
		else 
			return chest_title;
			
	}
}
