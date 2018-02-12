package cn.saturn.web.controllers.power;

import cn.saturn.web.code.action.LoginAction;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.code.login.domain.AccountModel;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import xzj.core.util.MD5;
import zyt.spring.cache.entity.EntityFactorys;

@Path("accountPassword")
public class AccountPasswordController {
	public static final String defualPath = "/power/accountPassword/show";

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, defualPath);
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.power_account_page)
	@Get("show")
	public String show(Invocation inv) throws Throwable {

		return "apwd";
	}
	
	@UserAuthority(PageModel.power_account_page)
	@Post("toChange")
	public String toChange(Invocation inv, @NotBlank @Param("account") String account, @NotBlank @Param("password") String password, @NotBlank @Param("checkPwd") String checkPwd) {
		//String url = Utils.url(inv, defualPath);

		// 检测2个密码是否相同
		if (!password.equals(checkPwd)) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "密码不相同");
		}

		// 检测是否存在账号
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		AccountModel model = AccountManager.getAccount(account, LoginAction.defaultPt); // 只能改自己平台的
		if (model == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "不存在账号");
		}

		// 密码加密
		if (LoginAction.pwdMd5) {
			password = MD5.encode(password); // md5加密
		}
		// 执行修改
		model.setPassword(password);
		AccountManager.update(model);

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功","power/accountPassword/show?mainView=true");
	}

}
