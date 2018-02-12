package cn.saturn.web.controllers.power;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import cn.saturn.operation.OperationExt;
import cn.saturn.web.code.action.LoginAction;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.code.login.domain.AccountModel;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
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
import zyt.spring.cache.entity.EntityFactorys;
import zyt.utils.TimeUtils;

@Path("vindicator")
public class VindicatorController {
	public static final String defualPath = "/power/vindicator/list";

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/power/vindicator/list");
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.vindicator_page)
	@Get("list")
	public String banned(Invocation inv, @Param("srvId") int srvId) throws Throwable {

		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();
		srvId = WebUtils.checkSrvId(srvId, request);

		request.setAttribute("tableUrl", Utils.url(inv, "/power/vindicator/listJson"));
		request.setAttribute("toAddUrl", Utils.url(inv, "/power/vindicator/toAdd"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/power/vindicator/toDelete"));

		return "vindicator";
	}
	@UserAuthority(PageModel.vindicator_page)
	@Get("toAdd")
	public String toAdd(Invocation inv, @Param("accountId") int accountId, @Param("accountName") String accountName, @Param("platfrom") String platfrom) {
		// 判断是否是用id添加
		if (accountId > 0) {
			
		} else {
			// 检查账号名称
			if (accountName == null) {
				return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "账号名不能为空");
			}
			platfrom = (!StringUtils.isEmpty(platfrom)) ? platfrom : LoginAction.defaultPt;
		}

		// 获取账号
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		AccountModel accountModel = null;
		if (accountId > 0) {
			// 直接用id查找
			accountModel = AccountManager.getAccount(accountId);
		} else {
			// 使用账号与渠道查找
			accountModel = AccountManager.getAccount(accountName, platfrom);
		}
		if (accountModel == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到账号");
		}

		// 检查状态
		if (accountModel.isVindicator() == true) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "已经存在列表中");
		}

		// 修改数据
		accountModel.setVindicator(true);
		AccountManager.update(accountModel);

		RedisUtils.del(RedisKeys.K_ACCOUNT);
		// 成功
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功");
	}

	@UserAuthority(PageModel.vindicator_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("accountId") int accountId) {

		// 检查账号ID
		if (accountId <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "账号ID不能为空");
		}

		// 获取账号
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		AccountModel accountModel = AccountManager.getAccount(accountId);
		if (accountModel == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到这个账号");
		}

		// 修改状态
		if (!accountModel.isVindicator()) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "这个账号不在名单中");
		}

		// 修改数据
		accountModel.setVindicator(false);
		boolean result0 = AccountManager.update(accountModel);
		if (!result0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
		}

		RedisUtils.del(RedisKeys.K_ACCOUNT);
		// 成功
		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// return "@";

		HttpServletRequest request = inv.getRequest();
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		// 读取参数
		//int totalRows = accountManager.getDAO().getVindicatorCount();
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		int curPage = Integer.parseInt(request.getParameter("page"));

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// 遍历
		List<AccountModel> list = AccountManager.getVindicator(startRow, pageSize);

		// 转换接口
		BsgridUtils.IConvert<AccountModel> action = new BsgridUtils.IConvert<AccountModel>() {

			@Override
			public boolean convert(AccountModel obj, Map<String, Object> map) {
				map.put("accountId", obj.getId());
				map.put("accountName", obj.getAccount());
				map.put("platfrom", obj.getPlatform());
				map.put("createTime", TimeUtils.getTimeStr(obj.getCreateTime()));
				return true;
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		String jsonStr = OperationExt.queryToJson(listDatas);
		return "@" + jsonStr;
	}

}
