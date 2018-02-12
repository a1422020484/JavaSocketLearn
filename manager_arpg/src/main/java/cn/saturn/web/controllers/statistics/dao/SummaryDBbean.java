package cn.saturn.web.controllers.statistics.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.dbcp.BasicDataSource;
import cn.saturn.operation.OperationExt;
import cn.saturn.web.utils.DateUtils;

public class SummaryDBbean {
	
	private BasicDataSource dSource;
	@SuppressWarnings("unused")
	private LogDbConnectionModel logDb;
	private Set<SummarySqlModel> summaryBeans;
	private LogDbSummaryManager logDbSummaryMgr;

	public void setLogDbSummaryMgr(LogDbSummaryManager logDbSummaryMgr) {
		this.logDbSummaryMgr = logDbSummaryMgr;
	}

	public BasicDataSource getdSource() {
		return dSource;
	}

	public SummaryDBbean(LogDbConnectionModel logDb, Set<SummarySqlModel> summaryBeans) {
		this.logDb = logDb;
		this.summaryBeans = summaryBeans;
	}

	public static SummaryDBbean create(LogDbConnectionModel logDb, Set<SummarySqlModel> sumaryBeans) {
		SummaryDBbean bean = null;
		bean = new SummaryDBbean(logDb, sumaryBeans);
		bean.dSource = OperationExt.createDataSource(logDb);
		return bean;
	}

	public Set<SummarySqlModel> getSummaryBeans() {
		return summaryBeans;
	}

	public List<String[]> summaryRun() throws SQLException {

		List<String[]> insterSqls = new ArrayList<String[]>();
		Iterator<SummarySqlModel> it = summaryBeans.iterator();
		while (it.hasNext()) {
			SummarySqlModel model = it.next();
			String sql = createSql(model.sql);
			String[] titles = model.title.split(",");
			String table = model.table;

			try {
				ResultSet rs = exectSummarySql(sql);
				String[] cInsertSqls = createInsertSqls(rs, titles, table);
				// System.out.println(sql);
				if (cInsertSqls.length != 0)
					insterSqls.add(cInsertSqls);
			} catch (Exception e) {
			}
		}

		relase();

		return insterSqls;
	}

	/**
	 * 创建sql查询语句 <br>
	 * 
	 * @param sql
	 * @return
	 */
	protected String createSql(String sql) {
		String yesterday = DateUtils.getYesterdayStr();
		return MessageFormat.format(sql, "select \"" + yesterday + "\" as t_time", yesterday);

	}

	/**
	 * 查询--汇总表<br>
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	protected ResultSet exectSummarySql(String sql) throws SQLException {
		PreparedStatement pstmt;
		pstmt = (PreparedStatement) dSource.getConnection().prepareStatement(sql);
		return pstmt.executeQuery();
	}

	/**
	 * 汇总结果集--汇总到数据库<br>
	 * 
	 * @param rs
	 * @param titles
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	protected String[] createInsertSqls(ResultSet rs, String[] titles, String table) throws SQLException {
		int col = rs.getMetaData().getColumnCount();
		List<String> insertSqls = new ArrayList<String>();
		while (rs.next()) {
			StringBuilder sbStr = new StringBuilder();
			sbStr.append("insert into ").append(table).append(" (");
			for (int i = 0; i < titles.length; i++)
				sbStr.append(titles[i]).append(i != titles.length - 1 ? "," : "");
			sbStr.append(") values (");

			for (int i = 1; i <= col; i++) {
				sbStr.append("'").append(rs.getString(i)).append("'").append(i != col ? "," : "");
			}
			sbStr.append(");");
			insertSqls.add(sbStr.toString());

			// 如果提交sql 大于批量提交的数量
			if (insertSqls.size() >= logDbSummaryMgr.batch_max_size) {
				logDbSummaryMgr.insertSummarySql(insertSqls.toArray(new String[] {}));
				insertSqls.clear();
			}
		}

		return insertSqls.toArray(new String[] {});
	}

	protected void relase() throws SQLException {
		dSource.close();
	}
}
