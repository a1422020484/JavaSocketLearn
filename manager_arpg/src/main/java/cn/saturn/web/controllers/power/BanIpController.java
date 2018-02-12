package cn.saturn.web.controllers.power;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import cn.saturn.operation.OperationExt;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.power.dao.BanIpDAO;
import cn.saturn.web.controllers.power.dao.BanIpManager;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("banip")
public class BanIpController {

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/power/banip/list");
	}


	@LoginCheck
	@UserAuthority(PageModel.power_banIp_page)
	@Get("list")
	public String banned(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);
		request.setAttribute("tableUrl",Utils.url(inv, "/power/banip/listJson"));
		request.setAttribute("toAddUrl", Utils.url(inv, "/power/banip/insertOrUpdateBanIp"));
		request.setAttribute("toDeleteUrl",Utils.url(inv, "/power/banip/toDelete"));

		return "banip";
	}

	@LoginCheck
	@UserAuthority(PageModel.power_banIp_page)
	@Post("listJson")
	public String listJson(Invocation inv,@Param("ip") String ip,@Param("note") String note) throws IOException {

		HttpServletRequest request = inv.getRequest();
		BanIpDAO banIpDAO = BanIpManager.banIpDAO;
		String ipv4 =null;
		if(ip!=null)
	         ipv4 = ip.replaceAll(" ", "");
		String noteForQuery= null;
		if(note!=null){
			noteForQuery= note.replaceAll(" ", "");
		}
		// 读取参数
		int totalRows = banIpDAO.queryCount(ipv4,noteForQuery);
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int curPage = Integer.parseInt(request.getParameter("page"));
//		if(ip==null||isIpv4(ip.replaceAll(" ", ""))==false){
//			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "IP地址格式错误");
//		}
		
		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// 遍历
		List<BanIp> list = banIpDAO.getList(startRow,pageSize,ipv4,noteForQuery);

		// 转换接口
		BsgridUtils.IConvert<BanIp> action = new BsgridUtils.IConvert<BanIp>() {

			@Override
			public boolean convert(BanIp obj, Map<String, Object> map) {
				map.put("ip", obj.getIp());
				map.put("note", obj.getNote());
				return true;
			}
		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list,action);
		String jsonStr = OperationExt.queryToEasyuiJson(listDatas,totalRows);
		return "@"+jsonStr;
	}
	
	
	@UserAuthority(PageModel.power_banIp_page)
	@Get("insertOrUpdateBanIp")
	public String insertOrUpdateBanIp(Invocation inv, @Param("ip") String ip, @Param("note") String note) {
		
		//校验IP地址格式
		if(ip==null||isIpv4(ip.replaceAll(" ", ""))==false){
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "IP地址格式错误");
		}
	    
		String ipv4=ip.replaceAll(" ", "");
		BanIpDAO banIpDAO = BanIpManager.banIpDAO;		
		banIpDAO.insertOrUpdate(ipv4, note);
				
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "更改成功");
	}
	
	
	@UserAuthority(PageModel.power_banIp_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("ip") String ip) {

		// 检查IP
		if (ip==null||!isIpv4(ip.replaceAll(" ", ""))) {	
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "IP地址格式错误");
		}

		String ipv4 = ip.replaceAll(" ", "");
		BanIpDAO banIpDAO = BanIpManager.banIpDAO;
		int count = banIpDAO.queryCount(ipv4,null);
		if (count ==0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到这个IP信息");
		}

        int deleteCount = banIpDAO.delete(ipv4);
		if (deleteCount<1) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}	
		// 成功
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功");
	}

	
	
	private boolean isIpv4(String ipAddress) {

		String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();

	}

}
