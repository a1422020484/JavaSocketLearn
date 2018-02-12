package cn.saturn.web.controllers.statistics.dao;

import cn.saturn.web.model.auto.IModel;

public class SummarySqlModel implements IModel {
	protected long id;
	protected String sql;
	protected String title;
	protected String table;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
}
