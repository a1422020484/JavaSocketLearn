package cn.saturn.web.controllers.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;

import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.account.dao.AuthorityModel;
import cn.saturn.web.controllers.account.dao.SettingAuthorityDAO;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.menu.dao.MenuManager;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("settingAuthority")
public class SettingAuthorityController {
	
	@Autowired
	private SettingAuthorityDAO settingAuthorityDAO;
	
	
	public SettingAuthorityDAO getSettingAuthorityDAO() {
		return settingAuthorityDAO;
	}

	public void setSettingAuthorityDAO(SettingAuthorityDAO settingAuthorityDAO) {
		this.settingAuthorityDAO = settingAuthorityDAO;
	}

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/account/settingAuthority/basePage");
	}

	@LoginCheck
	//@UserAuthority(PageModel.power_banIp_page)
	@Get("basePage")
	public String banned(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);
		request.setAttribute("memuTableUrl",Utils.url(inv, "/account/settingAuthority/memuListJson"));
		request.setAttribute("authorityTableUrl", Utils.url(inv, "/account/settingAuthority/authorityListJson"));
		request.setAttribute("getSelectedMenu", Utils.url(inv, "/account/settingAuthority/getSelectedMenu"));
		request.setAttribute("updateOrInsertAuthority", Utils.url(inv, "/account/settingAuthority/updateOrInsertAuthority"));
		request.setAttribute("insertAuthority", Utils.url(inv, "/account/settingAuthority/insertAuthority"));

		return "settingAuthority";
	}
	
	@LoginCheck
	@Get("memuListJson")
	public  String   memuListJson(){
		MenuManager mMgr = ComponentManager.getComponent(MenuManager.class);
		return "@" + mMgr.getTreeMenu(10);
	}
	
	@LoginCheck
	@Get("authorityListJson")
	public  String   authorityListJson(){
		
		List<AuthorityModel> list = settingAuthorityDAO.getList();
		String jsonString = this.convertListToStringJson(list);
		return "@" + jsonString;
	}
	
	
	private String convertListToStringJson(List<AuthorityModel> list){

		List jsonList = new ArrayList();
		Map headMap = new HashMap();
		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();

		for (AuthorityModel model : list) {
			
			if(!model.getAuthority_id().equals("10")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", model.getAuthority_id());
			map.put("text", model.getAuthority_name());
			tempList.add(map);
			}
		}

		headMap.put("id", "-1");
		headMap.put("text", "权限");
		headMap.put("state", "open");
		
		headMap.put("children", tempList);
		jsonList.add(headMap);
		JSONArray json = new JSONArray();
		json.addAll(jsonList);	 				
		return json.toString();
	}
	
	@LoginCheck
	@Get("getSelectedMenu")
	public String getSelectedMenu(@Param("id") String id){
		List<String> list  = settingAuthorityDAO.getSelectedMenu(id);
		JSONArray json = new JSONArray();
		json.addAll(list);		
		return "@"+json.toString();
		
	}
	
	@LoginCheck
	@Post("updateOrInsertAuthority")
	public String updateOrInsertAuthority(@Param("authorityNodesId") String authorityNodesId ,@Param("authorityNodesIds")String authorityNodesIds  ){
		if(authorityNodesIds==null||authorityNodesIds.replaceAll(" ", "").equals("")){
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.WARNING, "提交数据错误");
		}
		String authorityId = authorityNodesId.replaceAll(" ", "");
		String[] arrayMenuNodes = authorityNodesIds.replace("[", "").replace("]", "").replaceAll(" ", "").split(","); //字符串转素组
		if(authorityNodesId==null||authorityNodesId.replaceAll(" ", "").equals("")) 
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.WARNING, "提交数据错误");
		
		settingAuthorityDAO.delete(authorityId);
		for(int i=0;i<arrayMenuNodes.length;i++){			
			if(arrayMenuNodes[i].equals("")||arrayMenuNodes[i]==null) continue;
			String menuId = arrayMenuNodes[i].replaceAll(" ", "");
				settingAuthorityDAO.updateOrInsertAuthority(authorityId, menuId);						
		}		
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功");
		
	}
	
	@LoginCheck
	@Post("insertAuthority")
	public String insertAuthority(@Param("name") String authorityName){
		
		if(authorityName==null||authorityName.replaceAll(" ", "").equals("")){
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.WARNING, "数据不能为空！");
		}		
		int  count  = settingAuthorityDAO.isExistSameCode(authorityName.replaceAll(" ", ""));		
		if(count>=1){
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.WARNING, "权限名称已经存在！");
			
		}		
		settingAuthorityDAO.insertAuthority(authorityName.replaceAll(" ", ""));		
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功");
		
	}
	
}
