package cn.saturn.operation;

import java.util.Arrays;

/**
 * Report 查询模版 <br>
 * report_sql 表
 * 
 * @author rodking
 *
 */
public class ReportSqlTemp {
	/** 英文标题 */
	private String[] title_en;
	/** 中文标题 */
	private String[] title_ch;
	/** 查询sql */
	private String sql;

	public ReportSqlTemp(String sql, String[] en, String[] ch) {
		this.sql = sql;
		this.title_en = en;
		this.title_ch = ch;
	}

	public String[] getTitle_en() {
		return title_en;
	}

	public String[] getTitle_ch() {
		return title_ch;
	}

	public String getSql() {
		return sql;
	}

	@Override
	public String toString() {
		return "ReportSqlTemp [title_en=" + Arrays.toString(title_en) + ", title_ch=" + Arrays.toString(title_ch)
				+ ", sql=" + sql + "]";
	}
	
	

}
