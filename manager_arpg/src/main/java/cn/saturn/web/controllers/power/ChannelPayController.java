package cn.saturn.web.controllers.power;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.power.dao.ChannelPayDAO;
import cn.saturn.web.controllers.power.dao.ChannelPayModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("channelpay")
public class ChannelPayController {
	
	@Resource
	ChannelPayDAO channelPayDao;
	
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/power/channelpay/list");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("list")
	public String banned(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);

		request.setAttribute("tableUrl", Utils.url(inv, "/power/channelpay/listJson"));
		request.setAttribute("toAddUrl", Utils.url(inv, "/power/channelpay/toAdd"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/power/channelpay/toDelete"));

		return "channelpay";
	}

	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("toAdd")
	public String toAdd(Invocation inv, @Param("platform") String platform, @Param("sub_platform") String sub_platform ,@Param("remark") String remark ) {
		
		
		String  platformtr =null;
		String  sub_platformtr =null;
		String  remarktr = null;
		
		 platformtr= platform.trim();
		 sub_platformtr =sub_platform.trim();
		 remarktr = remark.trim();
		 
		if (StringUtils.isEmpty(platformtr)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "渠道不能为空");
		}
		ChannelPayModel  model =new ChannelPayModel();
		
		model.setPlatform(platformtr);
		
		if(StringUtils.isEmpty(sub_platformtr)) {
			sub_platformtr = "*";
		}
		
		model.setSub_platform(sub_platformtr);
		
		if(StringUtils.isNotEmpty(remarktr)){
			model.setRemark(remarktr);
		}
		model.setCreattime(new Date());
		channelPayDao.insertOrUpdate(model);
		
		// 成功
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功");
	}

	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("id") String id) {

		// 检查账号id
		if (StringUtils.isEmpty(id)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "渠道不能为空");
		}
		channelPayDao.delete(id);
		
		// 成功
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功");
	}
	
	@Post("listJson")
	public String listJson(Invocation inv,@Param("page") int page,@Param("rows") int rows) throws IOException {

		int start=page==1?0:rows*(page-1);
		
		List<ChannelPayModel> list = channelPayDao.getList(start,rows);
		
		int count=channelPayDao.getCount();
		
		// 转换接口
		BsgridUtils.IConvert<ChannelPayModel> action = new BsgridUtils.IConvert<ChannelPayModel>() {

			@Override
			public boolean convert(ChannelPayModel obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("platform", obj.getPlatform());
				map.put("sub_platform", obj.getSub_platform());
				map.put("creattime", DateUtils.format(obj.getCreattime()));
				map.put("remark", obj.getRemark());
				return true;
				
				
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		//String jsonStr = OperationExt.queryToJson(listDatas);
		String jsonStr = OperationExt.queryToEasyuiJson(listDatas,count);
		
		return "@" + jsonStr;
	}


}
