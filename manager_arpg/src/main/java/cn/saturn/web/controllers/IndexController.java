package cn.saturn.web.controllers;

import cn.saturn.web.controllers.annotation.NotBlank;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("")
public class IndexController {

	@Get
	public String index() throws Throwable {
		// Integer i = null;
		// i++;

		// throw new Throwable("账号密码错误");
		return "redirect:main";
	}

	@Get("test")
	public String test(@NotBlank @Param("id") int id) throws Throwable {

		return "@test";
	}
}
