package cn.saturn.web.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.saturn.operation.ListRowMapper;
import cn.saturn.operation.ReportSqlTemp;
import zyt.spring.component.ComponentManager;

public class DataSourceOperation implements ComponentManager.IComponent {

	@Resource
	javax.sql.DataSource dataSource;

	static JdbcTemplate dsTemplate;

	@Override
	public boolean reload(ApplicationContext context) {
		dsTemplate = new JdbcTemplate(dataSource);
		return true;
	}

	@Override
	public void release() {
	}

	/**
	 * 执行sql 返回数据集合 <br>
	 * 在平台数据库中拿取数据库<br>
	 * 
	 * @param sql
	 * @param title
	 *            表头字段
	 * @return
	 */
	public static List<String[]> query(String sql, String[] title) {
		List<String[]> values = dsTemplate.query(sql, new ListRowMapper(title));
		return values;
	}

	/**
	 * 将数据库中的查询sql拿出来<br>
	 * 并组装成可以 执行的sql <br>
	 * 返回组装对象 <br>
	 * 固定表 report_sql <br>
	 * TODO 以后可以优化通用表查询
	 * 
	 * @param tempid
	 *            sql_id 数据库中的查询sql id
	 * @return
	 */
	public static ReportSqlTemp queryTempSql(int tempid) {
		String[] title = new String[] { "id", "sql", "title_en", "title_ch" };
		String sql = "select * from report_sql where id = '" + tempid + "' ";
		List<String[]> out = query(sql, title);
		String[] str = out.get(0);
		ReportSqlTemp temp = new ReportSqlTemp(str[1], str[2].split(","), str[3].split(","));
		return temp;
	}
}
