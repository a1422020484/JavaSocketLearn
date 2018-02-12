package cn.saturn.operation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.alibaba.fastjson.JSON;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.statistics.SystemLogParams;
import cn.saturn.web.controllers.statistics.dao.LogDbConnectionModel;
import proto.ProtocolWeb;
import proto.ProtocolWeb.GetSystemLogCS;
import zyt.spring.component.ComponentManager;

/**
 * 增强Operation 报表统计
 * 
 * @author rodking
 */
public class OperationExt extends Operation {

	/**
	 * 执行sql 返回数据集合 <br>
	 * 在平台数据库中拿取数据库<br>
	 * 
	 * @param template
	 *            jdbc
	 * @param sql
	 * @param title
	 *            表头字段
	 * @return
	 */
	public static List<String[]> query(JdbcTemplate template, String sql, String[] title) {
		// System.out.println(sql);
		List<String[]> values = template.query(sql, new ListRowMapper(title));
		return values;
	}

	/**
	 * 将结果转换为easyui-gridview <br>
	 * 中需求的 json
	 * 
	 * @param titleCH
	 *            表头
	 * @param queryOut
	 *            数据库中查询的结果
	 * @param isHead
	 *            json是否包含表头 true 包含, false 不包含
	 * @return
	 */
	public static String queryToJson(String[] titleCH, List<String[]> queryOut, boolean isHead) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("total", queryOut.size());

		// 遍历数据行
		List<Map<String, String>> listData = new LinkedList<Map<String, String>>();
		for (int i = 0; i < queryOut.size(); i++) {
			Map<String, String> rowMap = new HashMap<String, String>();
			for (int j = 0; j < titleCH.length; j++) {
				String value = queryOut.get(i)[j];
				if (value == null || value.equals(""))
					value = "";
				rowMap.put(j + "", value);
			}
			listData.add(rowMap);
		}

		// 是否加入表头
		List<Map<String, String>> listTitle = new LinkedList<Map<String, String>>();
		if (isHead) {
			for (int j = 0; j < titleCH.length; j++) {
				Map<String, String> titleMap = new HashMap<String, String>();
				titleMap.put("field", j + "");
				titleMap.put("title", titleCH[j]);
				listTitle.add(titleMap);
			}
		}

		jsonMap.put("rows", listData);
		// 是否加入表头
		if (isHead)
			jsonMap.put("title", listTitle);

		String str = JSON.toJSONString(jsonMap);
		return str;
	}

	/**
	 * 将结果转换为easyui-gridview <br>
	 * 中需求的 json 包含表头
	 * 
	 * @param titleCH
	 *            表头
	 * @param queryOut
	 *            数据库中查询的结果
	 * @return
	 */
	public static String queryToJson(String[] titleCH, List<String[]> queryOut) {
		return queryToJson(titleCH, queryOut, true);
	}

	/**
	 * 将结果转换为easyui-gridview <br>
	 * 中需求的 json 没有表头<br>
	 * 
	 * @param queryOut
	 *            数据集合
	 * @return
	 */
	public static String queryToJson(List<Map<String, Object>> queryOut) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("total", (queryOut != null) ? queryOut.size() : 0);
		jsonMap.put("rows", (queryOut != null) ? queryOut : new ArrayList<>());
		String str = JSON.toJSONString(jsonMap);
		return str;
	}
	
	/**
	 * 将结果转换为easyui-gridview <br>
	 * 中需求的 json 没有表头<br>
	 * 
	 * @param queryOut
	 *            数据集合
	 * @return
	 */
	public static String queryToEasyuiJson(List<Map<String, Object>> queryOut,int totalCount) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("total", totalCount);                     //表单右下角显示的符合查询条件的所有记录
		jsonMap.put("rows", (queryOut != null) ? queryOut : new ArrayList<>());
		String str = JSON.toJSONString(jsonMap);
		return str;
	}

	/**
	 * 将查询的结果转换为 html 中的table<br>
	 * 包含表头
	 * 
	 * @param keyNames
	 *            表头
	 * @param datas
	 *            sql查询的数据集
	 * @return
	 */
	public static String tableShow(String[] keyNames, List<String[]> datas) {
		// 文本输出
		StringBuffer strBuf = new StringBuffer();
		// strBuf.append("@");
		strBuf.append("<div class=\"div_from_aoto\" style=\"width: 98%;\">");
		strBuf.append("<table  id=\"searchTable\"  align=\"center\" class=\"bsgrid\"  >");

		// key名称
		final int nameCount = (keyNames != null) ? keyNames.length : 0;
		strBuf.append("<thead>");
		// 遍历key
		for (int i = 0; i < nameCount; i++) {
			String key0 = keyNames[i];
			strBuf.append("<th class=\"showTbA\">").append(key0).append("</th>");
		}
		strBuf.append("</thead>");

		// 全服
		lineStr(strBuf, datas);
		strBuf.append("</table>");
		strBuf.append("</div>");
		return strBuf.toString();
	}

	private static boolean lineStr(StringBuffer strBuf, List<String[]> datas) {
		// 遍历
		for (int i = 0; i < datas.size(); i++) {
			strBuf.append("<tr>");
			String[] data = datas.get(i);
			for (int j = 0; j < data.length; j++) {
				String value = data[j];
				// 写入参数
				if (null == value || value.equals(""))
					value = "";
				strBuf.append("<td style=\"text-align: center;\" class=\"lineNoWrap\">");
				strBuf.append(value);
				strBuf.append("</td>");
			}
			strBuf.append("</tr>");
		}
		return true;
	}

	/**
	 * 将数据库中的查询sql拿出来<br>
	 * 并组装成可以 执行的sql <br>
	 * 返回组装对象 <br>
	 * 固定表 report_sql <br>
	 * TODO 以后可以优化通用表查询
	 * 
	 * @param template
	 *            jdbc
	 * @param tempid
	 *            sql_id 数据库中的查询sql id
	 * @return
	 */
	public static ReportSqlTemp queryTempSql(JdbcTemplate template, int tempid) {
		String[] title = new String[] { "id", "sql", "title_en", "title_ch" };
		String sql = "select * from report_sql where id = '" + tempid + "' ";
		List<String[]> out = query(template, sql, title);
		String[] str = out.get(0);
		ReportSqlTemp temp = new ReportSqlTemp(str[1], str[2].split(","), str[3].split(","));
		return temp;
	}

	/**
	 * 创建 basicDataSource
	 * 
	 * @param logDb
	 * @return
	 */
	public static BasicDataSource createDataSource(LogDbConnectionModel logDb) {
		try {
			Properties p = new Properties();
			p.setProperty("url", logDb.getUrl());
			p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
			p.setProperty("password", logDb.getPwd());
			p.setProperty("username", logDb.getRoot());
			return (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 执行sql 返回数据集合 <br>
	 * 到游戏指定的游戏服中拿取数据<br>
	 * 查询的时候创建连接,查询完断开连接<br>
	 * 
	 * @param dSource
	 * @param sql
	 * @param title
	 * @return
	 */
	public static List<String[]> query(BasicDataSource dSource, String sql, String[] title) {
		List<String[]> values = new ArrayList<String[]>();
		try {
			// 到游戏指定的游戏服中拿取数据
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) dSource.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String[] value = new String[title.length];
				for (int i = 0; i < title.length; i++)
					value[i] = rs.getString(i + 1);
				values.add(value);
			}
			dSource.close();
		} catch (Exception e) {
		}
		return values;
	}

	/**
	 * 查询日志路径 <br>
	 * 
	 * @param fileRelPath
	 * @param srvId
	 * @return
	 */
	public static String queryLogFile(int type, int srvId, String dayStr) {
		// 生成文本路径
		String value = SystemLogParams.getParams(type);
		if (value == null)
			return "@没有日志类型";

		String fileRelPath = MessageFormat.format(value, dayStr);
		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(srvId);
		if (serverModel == null)
			return "@服务器不存在";

		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null)
			return "@服务器Url配置错误";
		// 发送消息
		GetSystemLogCS.Builder cs = GetSystemLogCS.newBuilder();
		cs.setLogFileRelPath(fileRelPath);

		ProtocolWeb.GetSystemLogSC retMsg = client.call(Head.H14004, cs.build(), ProtocolWeb.GetSystemLogSC.class);
		if (retMsg == null)
			return "@查询失败:" + client.getErrStr();

		return retMsg.getContent();
	}

	/**
	 * 切割日志文件 2016-06-06 00:00:00|1<br>
	 * 切割时间和数量 <br>
	 * 
	 * @param result
	 * @return
	 */
	public static List<String[]> getResultMsg(String result) {
		List<String[]> list = new ArrayList<String[]>();

		String[] results = result.split("\n");
		for (String res : results) {
			list.add(res.replace("|", "T").split("T"));
		}

		return list;
	}

	/**
	 * 切割日志文件 2016-06-06 00:00:00|1<br>
	 * 切割获得最后一个 <br>
	 * 
	 * @param result
	 * @return
	 */
	public static String[] getResultMsgLast(String result) {
		String[] returnRes = new String[] {};
		String[] results = result.split("\n");
		for (String res : results) {
			returnRes = res.replace("|", "T").split("T");
		}

		return returnRes;
	}
	
	/**
	 * 切割日志文件 2016-06-06 00:00:00|1<br>
	 * 切割获得最后一个 <br>
	 * 
	 * @param result
	 * @return
	 */
	public static String[] getResultMsgLast(String result, String dateStr) {
		String[] returnRes = new String[] {};
		String[] results = result.split("\n");
		for (String res : results) {
			returnRes = res.replace("|", "T").split("T");
			if(dateStr.equals(returnRes[0]))
				return returnRes;
		}

		return new String[]{};
	}
	
	/***
	 * 切割日志文件 2016-06-06 00:00:00|1<br>
	 * 获取全部数据<br>
	 * @param result
	 * @return
	 */
	public static List<String[]> getResultMsgList(String result) {
		List<String[]> resultList = new ArrayList<>();
		String[] returnRes = new String[] {};
		String[] results = result.split("\n");
		for (String res : results) {
			returnRes = res.replace("|", "T").split("T");
			resultList.add(returnRes);
		}

		return resultList;
	}

	/**
	 * @param template
	 * @param sqls
	 */
	public static void batchUpdate(JdbcTemplate template, String[] sqls) {
		try {
			template.batchUpdate(sqls);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
