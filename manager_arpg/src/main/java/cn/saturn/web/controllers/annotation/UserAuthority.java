package cn.saturn.web.controllers.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用户权限检测
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAuthority {

	/** 模块id号 */
	int value() default 0; 
}
