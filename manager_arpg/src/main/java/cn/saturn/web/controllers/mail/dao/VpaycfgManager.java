package cn.saturn.web.controllers.mail.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class VpaycfgManager {	
	@Resource
	VpaycfgDAO vpaycfgDAO;
	
	private static Map<String, VpaycfgModel> vMap = new HashMap<>();
	
	private void init(){
		List<VpaycfgModel> list = vpaycfgDAO.get();
		if(list == null || list.size() == 0){
			System.out.println("虚拟支付配置列表为空!!!");
			return;
		}
		vMap.clear();
		for(VpaycfgModel model : list){
			vMap.put(model.getName().toLowerCase(), model);
		}
	}
	
	public VpaycfgModel getVpaycfgModel(String name){
		if(vMap == null || vMap.size()==0)
			init();
		return vMap.get(name.toLowerCase());
	}
}
