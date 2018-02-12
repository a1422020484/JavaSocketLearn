package cn.saturn.web.utils;

import java.text.MessageFormat;
import java.util.List;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.web.controllers.item.dao.GoodsManager;

public class ReportTools {

	/**
	 * 组装查询参数(服务器id) <br>
	 * if == -1 return "%" <br>
	 * else return {@value} +"" <br>
	 * 
	 * @param server_id
	 * @return
	 */
	private static String getServerId(int server_id) {
		return server_id == -1 ? "%" : server_id + "";
	}

	/**
	 * 组装查询参数 (渠道号 qq,uc) <br>
	 * if == -1 return "%" <br>
	 * else return {@value} <br>
	 * 
	 * @param platform
	 * @return
	 */
	private static String getPlatform(String platform) {
		return platform.equals("-1") ? "%" : platform + "";
	}

	/**
	 * 从DB中查询出数据集 <br>
	 * 并组装成json<br>
	 * 
	 * @param template
	 * @param strSql
	 * @param type
	 * @param channelId
	 * @param serverId
	 * @param resultTime
	 * @return
	 */
	public static String select2DB(JdbcTemplate template, String strSql, int type, String channelId, int serverId,
			String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String server_id = getServerId(serverId);
		String platform = getPlatform(channelId);
		String sql = MessageFormat.format(temp.getSql(), strSql, resultTime, server_id, platform);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out);
	}

	/**
	 * 从DB中查询出数据集 <br>
	 * 并组装成json<br>
	 * 
	 * @param template
	 * @param resultTime
	 * @param type
	 * @param channelId
	 * @param serverId
	 * @return
	 */
	public static String select2DB(JdbcTemplate template, String resultTime, int type, String channelId, int serverId) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String server_id = getServerId(serverId);
		String platform = getPlatform(channelId);
		String sql = MessageFormat.format(temp.getSql(), resultTime, server_id, platform);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out, false).toString();
	}

	public static String select2DB(JdbcTemplate template, int type, String channelId, int serverId) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String serverStr = getServerId(serverId);
		String channelStr = getPlatform(channelId);
		String sql = MessageFormat.format(temp.getSql(), serverStr, channelStr);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
	}

	public static String select2DB(JdbcTemplate template, String resultTime, int type, String channelId, int serverId,
			int hardId) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String serverStr = getServerId(serverId);
		String channelStr = getPlatform(channelId);
		String sql = MessageFormat.format(temp.getSql(), resultTime, serverStr, channelStr, hardId + "");
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
	}

	public static String select2DB(JdbcTemplate template, int type, String channelId, String startTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String channelStr = getPlatform(channelId);
		String sql = MessageFormat.format(temp.getSql(), startTime, channelStr);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
	}

	public static String select2DB(JdbcTemplate template, String strSql, int type, String channelId,
			String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String channelStr = getPlatform(channelId);
		String sql = MessageFormat.format(temp.getSql(), strSql, resultTime, channelStr);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
	}

	public static String select2DB(JdbcTemplate template, int type, String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String sql = MessageFormat.format(temp.getSql(), resultTime);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
	}

	public static String select2DB(JdbcTemplate template, int type, int serverId) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String serverStr = getServerId(serverId);
		String sql = MessageFormat.format(temp.getSql(), serverStr);
		List<String[]> out = OperationExt.query(template, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
	}

	public static String select2DB(JdbcTemplate template, BasicDataSource dSource, int type, int serverId) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String sql = MessageFormat.format(temp.getSql(), serverId);
		List<String[]> out = OperationExt.query(dSource, sql, temp.getTitle_en());
		return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
	}
	
	public static String select2DBTime(JdbcTemplate template, BasicDataSource dSource, int type, int serverId,String resultTime) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String sql = MessageFormat.format(temp.getSql(),resultTime);
		List<String[]> out = OperationExt.query(dSource, sql, temp.getTitle_en());
		List<String[]> outer =GoodsManager.selectOut(temp.getTitle_ch(), out, GoodsManager.getAllGoodsMap());
		return OperationExt.queryToJson(temp.getTitle_ch(), outer).toString();
	}
	
	public static List<String[]> select2DBList(JdbcTemplate template, BasicDataSource dSource, int type, int serverId) {
		ReportSqlTemp temp = OperationExt.queryTempSql(template, type);
		String sql = MessageFormat.format(temp.getSql(), serverId);
		List<String[]> out = OperationExt.query(dSource, sql, temp.getTitle_en());
		//return OperationExt.queryToJson(temp.getTitle_ch(), out).toString();
		return  out;
	}
}
