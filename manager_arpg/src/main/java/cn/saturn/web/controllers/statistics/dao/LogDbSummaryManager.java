package cn.saturn.web.controllers.statistics.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 日志汇总数据库 <br>
 * 
 * @author rodking
 *
 */
@Component
public class LogDbSummaryManager {
	protected static final Logger logger = LoggerFactory.getLogger("next_day_timer");
	/** 批量提交数据大小  5000 */
	public static final int batch_max_size = 5000; 

	@Resource
	SummarySqlManager summaryMgr; // 日志汇总sql

	@Resource
	LogDbConnectionManager logDbMgr; // 日志汇总连接配置

	@Resource
	javax.sql.DataSource dataSource;

	private Set<LogDbConnectionModel> logBeans = new HashSet<LogDbConnectionModel>();
	private Set<SummarySqlModel> sBeans = new HashSet<SummarySqlModel>();
	private Set<SummaryDBbean> summary = new HashSet<SummaryDBbean>();

	private JdbcTemplate template;

	/**
	 * 加载
	 */
	public void init() {
		template = new JdbcTemplate(dataSource);
	}

	/**
	 * 重新加载
	 */
	public void reLoad() {

		logBeans.clear();
		sBeans.clear();
		summary.clear();

		List<LogDbConnectionModel> lModels = logDbMgr.getList();
		List<SummarySqlModel> sModels = summaryMgr.getList();

		// 加载 log db
		if (null != lModels) {
			for (LogDbConnectionModel logModel : lModels) {
				logBeans.add(logModel);
			}
		}

		// 加载要汇总的 sql 语句
		if (null != sModels) {
			for (SummarySqlModel sModel : sModels) {
				sBeans.add(sModel);
			}
		}

		Iterator<LogDbConnectionModel> it = logBeans.iterator();
		while (it.hasNext()) {
			SummaryDBbean summaryBean = SummaryDBbean.create(it.next(), sBeans);
			if (summaryBean != null)
				summary.add(summaryBean);
		}
	}

	public void exec() {

		Iterator<SummaryDBbean> it = summary.iterator();
		while (it.hasNext()) {
			SummaryDBbean bean = it.next();
			bean.setLogDbSummaryMgr(this);

			try {
				List<String[]> insterSqls = bean.summaryRun();
				for (int j = 0; j < insterSqls.size(); j++) {
					insertSummarySql(insterSqls.get(j));
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 将查询的结果放入到统计表中 <br>
	 * 
	 * @param insterSqls
	 */
	protected void insertSummarySql(String[] insterSqls) {
		try {
			template.batchUpdate(insterSqls);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
