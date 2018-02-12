package cn.saturn.web.controllers;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;

import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.jump.JumpController;
import cn.saturn.web.utils.AuthorityUtils;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

/**
 * 权限检测
 *
 */
public class AuthorityCheckInterceptor extends ControllerInterceptorAdapter {
	public AuthorityCheckInterceptor() {
		this.setPriority(400);
		// this.setPriority(1000);
	}

	@Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return UserAuthority.class;
	}

	@Override
	protected Object before(Invocation inv) throws Exception {
		// 检测登陆状态
		HttpServletRequest request = inv.getRequest();
		int authority = AuthorityUtils.getAuthority(request);

		// 检测权限是否足够
		UserAuthority annotation = inv.getAnnotation(UserAuthority.class);
		if (annotation.value() != 0 && !BeanUtils.instantiate(AuthorityUtils.class).checkAuthority(authority, annotation.value())) {
			String url = JumpInterceptor.getPrevUrl(inv);
			return JumpController.error(inv, "权限不足", url);
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