package cn.saturn.web.controllers.jump;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.var.Flash;

@Path("")
public class JumpController {

	public static String jump(Invocation inv, String msg, String url) {
		Flash flash = inv.getFlash();

		// String url = Utils.url(inv, "/login", "");

		flash.add("href", url);
		flash.add("message", msg);
		return Utils.redirect(inv, "/jump");
	}

	public static String jump(Invocation inv, String msg) {
		return jump(inv, msg, "javascript:history.back()");
	}

	public static String error(Invocation inv, String error, String url) {
		Flash flash = inv.getFlash();

		// String url = Utils.url(inv, "/login", "");

		flash.add("href", url);
		flash.add("error", error);
		return Utils.redirect(inv, "/jump/error");
	}

	public static String error(Invocation inv, String error) {
		return error(inv, error, "javascript:history.back()");
	}

	@Get("")
	public String index(Invocation inv, Flash flash) throws Throwable {
		String href = flash.get("href");
		String msg = flash.get("message");

		if (StringUtils.isBlank(href)) {
			// href = Utils.url(inv, "/login");
			// msg = "返回登录页面";
			return Utils.redirect(inv, "/main");
		}

		HttpServletRequest request = inv.getRequest();
		// 参数设置
		request.setAttribute("message", msg);
		// request.setAttribute("jumpUrl", request.getContextPath() + "/login.jsp");
		request.setAttribute("jumpUrl", href);
		// request.setAttribute("jumpUrl", "javascript:history.back()");
		request.setAttribute("waitSecond", "3");

		return "jump";
	}

	@Get("error")
	public String error(Invocation inv, Flash flash) throws Throwable {
		String href = flash.get("href");
		String msg = flash.get("error");

		if (StringUtils.isBlank(href)) {
			// href = Utils.url(inv, "/login");
			// msg = "返回登录页面";
			return Utils.redirect(inv, "/main");
		}

		HttpServletRequest request = inv.getRequest();
		// 参数设置
		request.setAttribute("error", msg);
		request.setAttribute("jumpUrl", href);
		// request.setAttribute("jumpUrl", "javascript:history.back()");
		request.setAttribute("waitSecond", "3");

		return "jump";
	}

}
