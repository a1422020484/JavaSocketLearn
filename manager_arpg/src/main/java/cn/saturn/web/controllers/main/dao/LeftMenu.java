package cn.saturn.web.controllers.main.dao;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import cn.saturn.web.controllers.MainViewInterceptor;

public class LeftMenu extends Menu {

	public LeftMenu(String name, String url, String icon, String mode) {
		super(name, url, icon, mode);
	}

	public LeftMenu(String name, String url, String icon) {
		this(name, url, icon, null);
	}

	public String createACode() {
		if (url == null || url.length() <= 0) {
			return "<a href=\"javascript:;\">";
		}
		// return "<a href=\"" + url + "\">";
		String nUrl = MainViewInterceptor.viewUrl(url);
		// return "<a href=\"" + nUrl + "\" target=\"menuFrame\" >";

		// System.out.println("menu: url" + nUrl);

		return "<a href=\"" + nUrl + "\" target=\"menuFrame\" >";
	}

	@Override
	public void jspWrite(JspWriter out) throws IOException {
		// jspWriter.print(arg0);

		// 渲染菜单
		// out.println("<a href=\"" + url + "\" target=\"menuFrame\" >");
		// out.println("<a href=\"" + url + "\" target=\"_top\" >");
		out.println(this.createACode());
		out.println("<i class=\"" + icon + "\"></i>");
		out.println("<span class=\"font-bold\">" + this.getName() + "</span>");
		out.println("</a>");

	}

}
