package cn.saturn.web.controllers.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.ShieldSysDAO;
import cn.saturn.web.controllers.server.dao.ShieldSysModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("shield")
public class ShieldSysController {

	@Resource
	ShieldSysDAO shieldDAO;

	@UserAuthority(PageModel.ShieldSys_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/server/shield/list");
	}

	@MainView
	@UserAuthority(PageModel.ShieldSys_page)
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		HttpServletRequest request = inv.getRequest();

		request.setAttribute("tableUrl", Utils.url(inv, "/server/shield/listJson"));
		request.setAttribute("addUrl", Utils.url(inv, "/server/shield/edit"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/server/shield/toDelete"));
		request.setAttribute("editUrl", Utils.url(inv, "/server/shield/edit"));

		return "shield/list";
	}

	@MainView
	@UserAuthority(PageModel.ShieldSys_page)
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") long id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		if (id > 0) {
			// 编辑
			ShieldSysModel model = shieldDAO.find(id);
			if (model != null) {
				// 找到对应的数据
				request.setAttribute("id", id);
				request.setAttribute("version", model.getVersion());
				request.setAttribute("closedSubPlatform", model.getClosedSubPlatform());
				request.setAttribute("redeemSys", model.getRedeemSys());
				request.setAttribute("webSite", model.getWebSite());
				request.setAttribute("contact", model.getContactCust());
				request.setAttribute("rankingSys", model.getRankingSys());
				request.setAttribute("monthCard", model.getMonthCard());
				request.setAttribute("silentDownloadRes", model.getSilentDownloadRes());
				request.setAttribute("fbShare", model.getFbShare());
				request.setAttribute("abPay", model.getAbPay());
				request.setAttribute("weixin", model.getWeixin());
				request.setAttribute("btn", "修改");

			} else {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没找到对应信息");
			}
		} else {
			// 新增
			request.setAttribute("btn", "新增");
		}
		request.setAttribute("toEditUrl", Utils.url(inv, "/server/shield/toEdit"));
		return "shield/edit";
	}

	@LoginCheck
	@UserAuthority(PageModel.ShieldSys_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("id") int id, @Param("redeemSys") int redeemSys,
			@Param("version") String version,@Param("closedSubPlatform") String closedSubPlatform, 
			@Param("webSite") int webSite, @Param("contact") int contact,
			@Param("rankingSys") int rankingSys, @Param("monthCard") int monthCard,
			@Param("silentDownloadRes") int silentDownloadRes, @Param("fbShare") int fbShare, 
			@Param("abPay") int abPay,@Param("weixin") int weixin) {
		String defaultUrl = "server/shield/list?mainView=true";

		if (StringUtils.isEmpty(version)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "版本不能为空");
		}

		boolean isAdd = false;

		// 读取服务器
		ShieldSysModel model = shieldDAO.find(id);
		if (model == null) {
			// 新增
			model = new ShieldSysModel();
			isAdd = true;
		}

		// 修改参数
		model.setVersion(version.trim());
		model.setClosedSubPlatform(closedSubPlatform);
		model.setRedeemSys(redeemSys);
		model.setWebSite(webSite);
		model.setContactCust(contact);
		model.setRankingSys(rankingSys);
		model.setMonthCard(monthCard);
		model.setSilentDownloadRes(silentDownloadRes);
		model.setFbShare(fbShare);
		model.setAbPay(abPay);
		model.setWeixin(weixin);

		if (isAdd)
			shieldDAO.insert(model);
		else
			shieldDAO.update(model);

		RedisUtils.del(RedisKeys.K_SHIELD_SYS);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, (isAdd ? "添加成功" : "更改成功"), defaultUrl);
	}

	@LoginCheck
	@UserAuthority(PageModel.ShieldSys_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("id") long id) throws Throwable {
		shieldDAO.remove(id);
		ShieldSysModel model = shieldDAO.find(id);

		RedisUtils.del(RedisKeys.K_SHIELD_SYS);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, (model == null ? "删除成功" : "删除失败"),
				"server/shield/list?mainView=true");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// return "@";

		HttpServletRequest request = inv.getRequest();
		List<ShieldSysModel> shieldSysList = shieldDAO.getList();
		int size = shieldSysList.size();
		// 读取参数
		int totalRows = size;
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// Object totalRows0 = request.getParameter("totalRows");

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);
		// 遍历
		List<ShieldSysModel> list = shieldDAO.getList(startRow, pageSize);

		// 转换接口
		BsgridUtils.IConvert<ShieldSysModel> action = new BsgridUtils.IConvert<ShieldSysModel>() {

			@Override
			public boolean convert(ShieldSysModel obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("version", obj.getVersion());
				map.put("redeemSys", obj.getRedeemSys() == 0 ? "关闭" : "开启");
				map.put("webSite", obj.getWebSite() == 0 ? "关闭" : "开启");
				map.put("contact", obj.getContactCust() == 0 ? "关闭" : "开启");
				map.put("rankingSys", obj.getRankingSys() == 0 ? "关闭" : "开启");
				map.put("monthCard", obj.getMonthCard() == 0 ? "关闭" : "开启");
				map.put("silentDownloadRes", obj.getSilentDownloadRes() == 0 ? "关闭" : "开启");
				map.put("fbShare", obj.getFbShare() == 0 ? "关闭" : "开启");
				map.put("abPay", obj.getAbPay() == 0 ? "关闭" : "开启");
				map.put("weixin", obj.getWeixin() == 0 ? "关闭" : "开启");
				return true;
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);
		return "@" + jsonStr;
	}
}
