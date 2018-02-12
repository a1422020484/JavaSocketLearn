package cn.saturn.web.controllers.power;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import cn.saturn.operation.OperationExt;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.power.dao.VindicatorIpDAO;
import cn.saturn.web.controllers.power.dao.VindicatorIpModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
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

@Path("vindicatorIp")
public class VindicatorIPController {

	@Resource
	VindicatorIpDAO dao;

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/power/vindicatorIp/list");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("list")
	public String banned(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);

		request.setAttribute("tableUrl", Utils.url(inv, "/power/vindicatorIp/listJson"));
		request.setAttribute("toAddUrl", Utils.url(inv, "/power/vindicatorIp/toAdd"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/power/vindicatorIp/toDelete"));

		return "vindicatorIp";
	}

	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("toAdd")
	public String toAdd(Invocation inv, @Param("ip") String ip, @Param("note") String note) {
		if (StringUtils.isEmpty(ip)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "账号名不能为空");
		}

		VindicatorIpModel model = new VindicatorIpModel();
		model.setNote(note);
		model.setIp(ip);
		dao.insertOrUpdate(model);

		RedisUtils.del(RedisKeys.K_VINDICATOR_IP);
		// 成功
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功");
	}

	@UserAuthority(PageModel.vindicatorIp_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("ip") String ip) {

		// 检查账号Ip
		if (StringUtils.isEmpty(ip)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "账号ip不能为空");
		}
		dao.remove(ip);
		RedisUtils.del(RedisKeys.K_VINDICATOR_IP);
		// 成功
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {

		//HttpServletRequest request = inv.getRequest();
		// 读取参数
		//int pageSize = Integer.parseInt(request.getParameter("rows"));
		//int curPage = Integer.parseInt(request.getParameter("page"));

		// 计算起始和结束
		//int startRow = BsgridUtils.startRow(curPage, pageSize);
		// 遍历
		List<VindicatorIpModel> list = dao.getList();//.getVindicatorIp(startRow, pageSize);
		// 转换接口
		BsgridUtils.IConvert<VindicatorIpModel> action = new BsgridUtils.IConvert<VindicatorIpModel>() {

			@Override
			public boolean convert(VindicatorIpModel obj, Map<String, Object> map) {
				map.put("ip", obj.getIp());
				map.put("note", obj.getNote());
				return true;
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		String jsonStr = OperationExt.queryToJson(listDatas);
		return "@" + jsonStr;
	}

}
