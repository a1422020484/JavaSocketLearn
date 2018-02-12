package cn.saturn.web.controllers.mail;

import javax.servlet.http.HttpServletRequest;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang.StringUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.mail.dao.ParamManager;
import cn.saturn.web.controllers.mail.dao.ParamModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;

@Path("param")
public class ParamController {
	public static final String defualUrl = "/mail/param/index";

	@UserAuthority(PageModel.game_param_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, defualUrl);
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.game_param_page)
	@Get("index")
	public String main(Invocation inv) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/mail/param/toEdit"));

		// 获取全局参数
		ParamModel model = ParamManager.getGlobalParam();

		// 获取主题信息
		Integer theme = model.get(ParamModel.theme, Integer.class);
		request.setAttribute("theme", theme);

		// 读取客服信息
		String csInfo = model.get(ParamModel.csInfo, String.class);
		if (!StringUtils.isEmpty(csInfo)) {
			request.setAttribute("csInfo", csInfo);
		}

		return "param";
	}

	@UserAuthority(PageModel.game_param_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("theme") int theme, @Param("csInfo") String csInfo) throws Throwable {

		ParamModel model = ParamManager.getGlobalParam();
		model.set(ParamModel.theme, theme);
		model.set(ParamModel.csInfo, csInfo);

		// 储存
		ParamManager.updataGlobalParam();

		try {
			RedisUtils.del(RedisKeys.K_PARAM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功","mail/param/index?mainView=true");	
	}

}
