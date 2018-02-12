package cn.saturn.web.controllers.main.dao;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import zyt.component.node.Node;

public class Menu extends Node<Menu, String> {
	protected String url;
	protected String icon;
	protected String mode;

	public Menu(String name, String url, String icon, String mode) {
		super(name);
		this.url = url;
		this.icon = icon;
		this.mode = mode;
	}

	public Menu[] getChilds() {
		return (Menu[]) childs.toArray(new Menu[] {});
	}

	public String getName() {
		return this.getId();
	}

	public String getUrl() {
		return url;
	}

	public String createACode() {
		if (url == null || url.length() <= 0) {
			return "<a>";
		}
		return "<a href=\"" + url + "\">";
	}

	public String createCode() {
		return getName();
	}

	public void jspWrite(JspWriter out) throws IOException {
		// jspWriter.print(arg0);

		// 渲染菜单
		out.println(this.createACode());
		out.println("<i class=\"" + icon + "\"></i>");
		out.println(createCode());
		out.println("</a>");

	}


	@Override
	public String toString() {
		return "Menu [name=" + getName() + "," + getUrl() + "]";
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
