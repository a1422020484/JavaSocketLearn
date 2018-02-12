package cn.saturn.web.controllers;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.jump.JumpController;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

/**
 * 登陆检测
 *
 */
public class LoginCheckInterceptor extends ControllerInterceptorAdapter {
	public LoginCheckInterceptor() {
		this.setPriority(500);
	}

	@Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return LoginCheck.class;
	}

	@Override
	protected Object before(Invocation inv) throws Exception {
		// 检测登陆状态
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		// 调试模式检测
		boolean value = Utils.config.get("debug", Boolean.class);
		if (value) {
			// debug 判断
			String username = (String) session.getAttribute("userName");
			if (username == null || username.length() <= 0) {
				String url = Utils.url(inv, "login");
				return JumpController.error(inv, "请登陆", url);
			}
		} else {
			// 正常判断
			UserModel userModel = (UserModel) session.getAttribute("user");
			if (userModel == null) {
				String url = Utils.url(inv, "login");
				return JumpController.error(inv, "请登陆", url);
			}
		}

		// 正常
		return super.before(inv);
	}

	@Override
	public void afterCompletion(final Invocation inv, Throwable ex) throws Exception {
	}

	@Override
	protected Object after(Invocation inv, Object instruction) throws Exception {
		return super.after(inv, instruction);
	}

}