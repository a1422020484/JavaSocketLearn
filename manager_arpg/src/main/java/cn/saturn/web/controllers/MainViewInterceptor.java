package cn.saturn.web.controllers;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.var.Flash;

import org.apache.commons.lang.StringUtils;

import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.utils.Utils;

/**
 * 主页面转换
 *
 */
public class MainViewInterceptor extends ControllerInterceptorAdapter {

	public MainViewInterceptor() {
		this.setPriority(1000);
	}

	@Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return MainView.class;
	}

	@Override
	protected Object before(Invocation inv) throws Exception {
		// if(true){return Boolean.TRUE;}

		// 正常
		// return super.before(inv);
		// http://127.0.0.1:8080/home/notice/blacklist?srvId=1
		// 跳转数据
		// String tag = inv.getParameter("mainView");

		// 从flash中读取
		Flash flash = inv.getFlash();
		String tag = flash.get("mainView");
		if (tag == null) {
			// 从参数中读取
			tag = inv.getParameter("mainView");
		}
		if (!StringUtils.isBlank(tag) && tag.equals("true")) {
			return Boolean.TRUE;
		}

		// System.out.println(request.getRequestURL());
		// System.out.println(request.getRequestURI());
		// System.out.println(request.getServletPath());
		// System.out.println(request.getQueryString());

		// 转成main页面

		// 生成源url
		HttpServletRequest request = inv.getRequest();
		String baseUrl = request.getRequestURI();
		int index = baseUrl.indexOf("/home/main");
		if (index >= 0) {
			return Boolean.TRUE; // 不能重复跳转
		}

		// 获取完整的url
		String url = Utils.getUrl(request);

		// 跳转
		inv.addFlash("frameUrl", viewUrl(url));
		return Utils.redirect(inv, "/main");
	}

	@Override
	public void afterCompletion(final Invocation inv, Throwable ex) throws Exception {
	}

	public final static String key = "redirect_main";

//	@Override
//	protected Object after(Invocation inv, Object instruction) throws Exception {
//		if (true) {
//			// 统一在begin处理
//			return super.after(inv, instruction);
//		}
//		// 跳转数据
//		// HttpServletRequest request = inv.getRequest();
//		// HttpSession session = request.getSession();
//		// String tag = (String) session.getAttribute(key);
//		// if (!StringUtils.isBlank(tag)) {
//		// session.removeAttribute(key);
//		// return instruction;
//		// }
//
//		// 跳转数据
//		String tag = inv.getParameter("mainView");
//		if (!StringUtils.isBlank(tag)) {
//			return instruction;
//		}
//
//		HttpServletRequest request = inv.getRequest();
//		// System.out.println(request.getRequestURL());
//		// System.out.println(request.getRequestURI());
//		// System.out.println(request.getServletPath());
//		// System.out.println(request.getQueryString());
//
//		// 转成main页面
//		String url = request.getRequestURI() + "?" + request.getQueryString();
//		inv.addFlash("frameUrl", viewUrl(url));
//
//		return Utils.redirect(inv, "/main");
//
//		// // 直接转接画面(错误)
//		// ServletContext context = inv.getServletContext();
//		// RequestDispatcher rd = context.getRequestDispatcher("/main");// 获取请求转发对象(RequestDispatcher)
//		// rd.forward(inv.getRequest(), inv.getResponse());// 调用forward方法实现请求转发
//		//
//		// return ":continue";
//
//	}

	public static String viewUrl(String url) {
		if (url == null) {
			return null;
		}
		url = Utils.setArg(url, "mainView", "true");
		return url;

	}

}