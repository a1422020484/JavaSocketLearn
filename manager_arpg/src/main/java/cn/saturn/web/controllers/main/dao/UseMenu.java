package cn.saturn.web.controllers.main.dao;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class UseMenu extends Menu {
	public UseMenu(String name, String url, String icon) {
		super(name, url, icon, null);
	}

	@Override
	public void jspWrite(JspWriter out) throws IOException {
		super.jspWrite(out);
	}

}
