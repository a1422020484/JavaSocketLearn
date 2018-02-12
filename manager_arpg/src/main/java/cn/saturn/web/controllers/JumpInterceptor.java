package cn.saturn.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

/**
 * 跳转记录(基础上次跳转的页面)
 *
 */
public class JumpInterceptor extends ControllerInterceptorAdapter {
	public JumpInterceptor() {
		this.setPriority(0);
	}

	@Override
	protected Object before(Invocation inv) throws Exception {
		HttpServletRequest request = inv.getRequest();
		String method = request.getMethod();

		// 如果是GET才处理
		if (method.equals("GET")) {
			// url
			String url = Utils.getInvUrl(inv);
			// 检测是否是跳转jump
			if (url.indexOf("/jump/") < 0) {
				setPrevUrl(inv, url);
			}
		}
		// System.err.println("ser prevUrl:" + url);

		// 正常
		return super.before(inv);
	}

	@Override
	public void afterCompletion(final Invocation inv, Throwable ex) throws Exception {
	}

	@Override
	protected Object after(Invocation inv, Object instruction) throws Exception {

		// HttpServletRequest request = inv.getRequest();
		// HttpSession session = request.getSession();
		// String url = (String) session.getAttribute("_prevUrl");

		// url
		// String url = Utils.getInvUrl(inv);
		// session.setAttribute("_prevUrl", url);
		// System.err.println("ser prevUrl:" + url);
		return super.after(inv, instruction);
	}

	public static final String prevKey = "_prevUrl";

	public static void setPrevUrl(Invocation inv, String url) {
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();

		// 只记录mainView的页面
		String arg = Utils.findArg(url, "mainView");
		if (arg == null || arg.length() <= 0) {
			return;
		}

		// 清除参数
		url = Utils.setArg(url, "mainView", "false");
		session.setAttribute(prevKey, url);
	}

	public static String getPrevUrl(Invocation inv) {
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		return (String) session.getAttribute(prevKey);
	}
}