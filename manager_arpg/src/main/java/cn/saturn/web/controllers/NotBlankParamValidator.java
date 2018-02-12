package cn.saturn.web.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.ParamValidator;
import net.paoding.rose.web.paramresolver.ParamMetaData;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.jump.JumpController;

//参数验证
public class NotBlankParamValidator implements ParamValidator {
	// 先检测参数是否贴有特定标签
	@Override
	public boolean supports(ParamMetaData metaData) {
		// 检测是否贴有NotBlank.class
		return metaData.getAnnotation(NotBlank.class) != null;
	}

	// 检测是否空, 如果空返回失败, 如果不是就不返回
	@Override
	public Object validate(ParamMetaData metaData, Invocation inv, Object target, Errors errors) {
		String paramName = metaData.getParamName();
		String value = inv.getParameter(paramName);
		if (StringUtils.isBlank(value)) {

			// throw new Throwable(paramName + " 参数不能为空");

			// return "@" + paramName + " 参数不能为空";

			// String prevUrl = JumpInterceptor.getPrevUrl(inv);
			// if (prevUrl == null || prevUrl.length() <= 0) {
			// prevUrl = Utils.url(inv, "/");
			// }
			//
			// return JumpController.error(inv, "参数'" + paramName + "'不能为空", prevUrl);
			return JumpController.error(inv, "参数'" + paramName + "'不能为空");
		}
		return null;
	}

}
