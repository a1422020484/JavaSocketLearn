package cn.saturn.web.controllers.menu.dao;

import java.util.List;
public class MenuModels {

	private MenuModel model;
	private List<MenuModels> models;

	public MenuModels(MenuModel model) {
		this.model = model;
	}

	public long getId() {
		return model.getId();
	}

	public String getText() {
		return model.getText();
	}

	public String getUrl() {
		return model.getUrl();
	}

	public String getState() {
		return models == null ? null : "closed";
	}

	public List<MenuModels> getChildren() {
		return models;
	}

	public void setModels(List<MenuModels> models) {
		this.models = models;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
}
