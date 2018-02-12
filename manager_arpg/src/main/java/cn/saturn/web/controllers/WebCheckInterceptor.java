package cn.saturn.web.controllers;

import java.lang.annotation.Annotation;

import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

/**
 * 权限检测
 *
 */
public class WebCheckInterceptor extends ControllerInterceptorAdapter {
	public static final boolean webAction = Utils.config.get("webAction", true);

	public WebCheckInterceptor() {
		this.setPriority(1000);
	}

	@Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return null;
	}

	@Override
	protected Object before(Invocation inv) throws Exception {
		if (!webAction) {
			return "@home服务不提供web页面功能";
		}
		// 正常
		return super.before(inv);
	}

	// @Override
	// public void afterCompletion(final Invocation inv, Throwable ex) throws
	// Exception {
	// }
	//
	// @Override
	// protected Object after(Invocation inv, Object instruction) throws
	// Exception {
	// return super.after(inv, instruction);
	// }

}