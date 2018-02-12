package cn.saturn.web.controllers.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.saturn.web.controllers.jump.JumpController;
import cn.saturn.web.controllers.login.dao.UserDAO;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.var.Flash;

@Path("")
public class LoginController implements ApplicationContextAware {

	@Autowired
	UserDAO dao;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 检测管理者账号
		UserModel userModel = dao.get("admin");
		if (userModel == null) {
			userModel = new UserModel();
			userModel.setUsername("admin");
			userModel.setAuthority(UserModel.authority_admin);
			// 创建账号
			Integer userId = dao.insert(userModel, "admin");
			if (userId != null) {
				userModel.setId(userId);
			}
		}
	}

	// 登陆页面
	@Get("/")
	public String index(Invocation inv) {
		HttpServletRequest request = inv.getRequest();
		// HttpServletResponse response = inv.getResponse();
		request.setAttribute("title", "登陆");

		return "login";
	}

	@Post("/tologin")
	public String tologin(Invocation inv, @Param("username") String username, @Param("password") String password) throws Exception {
		// insert into `user` (`username`, `password`) values("admin", md5("admin"))

		// 检测是否有该账号
		UserModel userModel = dao.get(username, password);
		if (userModel == null) {
			// 测试账号
			/*if (username.equals("test") && password.equals("saturn20151118")) {
				// 临时账号
				userModel = new UserModel();
				userModel.setId(0);
				userModel.setUsername("test");
				userModel.setAuthority(UserModel.authority_admin);
			} else {
				// throw new Exception("账号密码错误");
				return JumpController.error(inv, "账号密码错误", Utils.url(inv, "login"));
			}*/
			
			return JumpController.error(inv, "账号密码错误", Utils.url(inv, "login"));
		}
		int userId = userModel.getId();
		int authority = userModel.getAuthority();

		// 设置登陆状态
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("userId", userId);
		session.setAttribute("userName", username);
		session.setAttribute("userAuthority", authority);
		session.setAttribute("user", userModel);

		// 进行跳转
		// String ctxPath = request.getContextPath();
		// return "redirect:" + ctxPath + "/main";

		// 添加参数跳转
		inv.addFlash("index", "true");
		return Utils.redirect(inv, "/main");
		// return Utils.predirect(inv, "/main");

	}

	@Get("/tologout")
	public String tologout(Invocation inv, Flash flash) throws Exception {
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		session.invalidate(); // 删除session

		// return Utils.redirect(inv, "/login");
		// inv.removeAttribute(name);

		String url = Utils.url(inv, "/login");
		// flash.add("href", url);
		// flash.add("message", "退出成功");
		// return Utils.redirect(inv, "/jump", "");

		return JumpController.jump(inv, "退出成功", url);
		// return JumpController.error(inv, "退出成功", url);

	}

}
