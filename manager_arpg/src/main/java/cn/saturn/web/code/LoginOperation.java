package cn.saturn.web.code;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.saturn.operation.ListRowMapper;
import zyt.spring.component.ComponentManager;

@Component
public class LoginOperation implements ComponentManager.IComponent {

	@Resource
	javax.sql.DataSource mainDB;

	static JdbcTemplate ldTemplate;

	@Override
	public boolean reload(ApplicationContext context) {
		ldTemplate = new JdbcTemplate(mainDB);
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
		List<String[]> values = ldTemplate.query(sql, new ListRowMapper(title));
		return values;
	}

}
