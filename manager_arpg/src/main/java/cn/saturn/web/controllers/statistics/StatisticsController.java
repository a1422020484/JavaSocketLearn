package cn.saturn.web.controllers.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.jdbc.core.JdbcTemplate;

import proto.ProtocolWeb;
import proto.ProtocolWeb.StatisticsWCS;
import zyt.spring.component.ComponentManager;
import cn.saturn.operation.Operation;
import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.Utils;

@Path("")
public class StatisticsController {
	@Resource
	javax.sql.DataSource dataSource;

	@MainView
	@LoginCheck
	@Get("total")
	public String statistics(Invocation inv) throws Throwable {

		// String dayStr = TimeUtils.getTimeStr(new Date(), "yyyy-MM-dd");
		String dayTime = TimeUtils.getTodayStr();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/totalInfo"));
		request.setAttribute("dayTime", dayTime);

		// int pressTime = Utils.getSessionValue(inv, "pressTime", int.class);

		return "total";
	}

	@Get("totalInfo")
	public String totalInfo(Invocation inv, @Param("dayStr") String dayStr, @Param("platform") String platform) throws Throwable {
		// String platform = "%";
		// platform = (!StringUtils.isEmpty(platform)) ? platform : "%";

		// 创建template
		// javax.sql.DataSource dataSource = (javax.sql.DataSource) GameExplorer.get("dataSource");
		// javax.sql.DataSource dataSource = (javax.sql.DataSource) GameExplorer.get("readDataSource");
		JdbcTemplate template = new JdbcTemplate(dataSource);

		// key名称
		final String[] keynames = new String[] { "平台", "新增用户", "活跃用户", "新增付费用户", "当日付费用户", "新增付费额", "当日付费额", "次日留存", "3日留存", "4日留存", "5日留存", "6日留存", "7日留存" };
		final String[] keys = new String[] { "newUser", "activeUser", "newPayUser", "dayPayUser", "newPayCount", "dayPayCount", "remain01", "remain02", "remain03", "remain04", "remain05", "remain06", };

		// 统计数据
		Map<String, Map<String, Object>> map = Operation.statistics(template, dayStr);
		String tableStr = statisticsTableShow(map, keys, keynames);

		// 表数据
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("tableStr", tableStr);

		return "table";
	}

	@MainView
	@LoginCheck
	@Get("server")
	public String server(Invocation inv) throws Throwable {
		// String dayStr = TimeUtils.getTimeStr(new Date(), "yyyy-MM-dd");
		String dayTime = TimeUtils.getTodayStr();

		// 设置参数
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("sendUrl", Utils.url(inv, "/statistics/serverInfo"));
		request.setAttribute("dayTime", dayTime);

		// int pressTime = Utils.getSessionValue(inv, "pressTime", int.class);

		return "serverSt";
		// return "@";
	}

	@Get("serverInfo")
	public String serverInfo(Invocation inv, @Param("dayStr") String dayStr, @Param("platform") String platform, @Param("srvId") int srvId) throws Throwable {
		// String url = Utils.url(inv, "/statistics/server");

		// 读取服务器ID
		// int srvId = ServerModel.getSrvId(srvCode);

		// 查找服务器
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel serverModel = serverComponent.find(srvId);
		if (serverModel == null) {
			return "@服务器不存在";
		}

		// 连接服务器
		IClient client = serverModel.createClient();
		if (client == null) {
			return "@服务器Url配置错误";
		}

		// 发送消息
		StatisticsWCS.Builder cs = StatisticsWCS.newBuilder();
		cs.setDayStr(dayStr);

		ProtocolWeb.StatisticsWSC retMsg = client.call(Head.H18001, cs.build(), ProtocolWeb.StatisticsWSC.class);
		if (retMsg == null) {
			return "@查询失败:" + client.getErrStr();
		}

		// final String[] keynames = new String[] { "平台", "新增用户", "活跃用户", "次日留存", "3日留存", "4日留存", "5日留存", "6日留存", "7日留存" };
		// final String[] keys = new String[] { "newUser", "activeUser", "remain01", "remain02", "remain03", "remain04", "remain05", "remain06", };
		final String[] keynames = new String[] { "平台", "新增用户", "活跃用户", "新增付费用户", "当日付费用户", "新增付费额", "当日付费额", "次日留存", "3日留存", "4日留存", "5日留存", "6日留存", "7日留存" };
		final String[] keys = new String[] { "newUser", "activeUser", "newPayUser", "dayPayUser", "newPayCount", "dayPayCount", "remain01", "remain02", "remain03", "remain04", "remain05", "remain06", };

		// 统计数据
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		JdbcTemplate template = new JdbcTemplate(dataSource);

		// 转化数据
		int infoCount = retMsg.getInfoCount();
		for (int i = 0; i < infoCount; i++) {
			ProtocolWeb.PlatformInfo info = retMsg.getInfo(i);
			String key = info.getPlatform();
			if (key == null) {
				continue;
			}
			// 如果是全部
			Map<String, Object> map0 = getMap(info);

			// 添加支付信息
			// 当日充值
			Map<String, Object> dayPayUsers = Operation.dayPayUser(template, dayStr, srvId);
			Operation.wirteTo(map, "dayPayUser", dayPayUsers);

			// 当日新增充值用户
			Map<String, Object> newPayUsers = Operation.newPayUser(template, dayStr, srvId);
			Operation.wirteTo(map, "newPayUser", newPayUsers);

			// 当日新增充值总额(新增用户)
			Map<String, Object> newPayCounts = Operation.newPayCount(template, dayStr, srvId);
			Operation.wirteTo(map, "newPayCount", newPayCounts);

			// 当日充值数
			Map<String, Object> dayPayCounts = Operation.dayPayCount(template, dayStr, srvId);
			Operation.wirteTo(map, "dayPayCount", dayPayCounts);

			map.put(key, map0);
		}
		String tableStr = statisticsTableShow(map, keys, keynames);

		// 表数据
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("tableStr", tableStr);

		return "table";

	}

	protected String statisticsTableShow(Map<String, Map<String, Object>> map, String[] keys, String[] keynames) {
		// 文本输出
		StringBuffer strBuf = new StringBuffer();
		// strBuf.append("@");
		strBuf.append("<div class=\"div_from_aoto\" style=\"width: 1024px;\">");
		strBuf.append("<table class=\"tb\">");

		// key名称
		final int nameCount = (keynames != null) ? keynames.length : 0;
		// 遍历key
		strBuf.append("</tr>");
		for (int i = 0; i < nameCount; i++) {
			String key0 = keynames[i];
			strBuf.append("<td>");
			strBuf.append(key0);
			strBuf.append("</td>");
		}
		strBuf.append("</tr>");

		// 统计数据
		int lineIndex = 0;

		// 全服
		String key = "%";
		Map<String, Object> map0 = map.get(key);
		lineStr(keys, strBuf, key, map0, lineIndex++);

		// 遍历数据
		Iterator<Map.Entry<String, Map<String, Object>>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Map<String, Object>> entry = iter.next();
			key = entry.getKey();
			if (key == null) {
				continue;
			}

			map0 = entry.getValue();

			// 如果是全部
			if (key.equals("%")) {
				continue;
			}

			// System.out.println(key + "\t:\t" + map0);
			lineStr(keys, strBuf, key, map0, (lineIndex++) % 2);
		}

		strBuf.append("</table>");
		strBuf.append("</div>");
		return strBuf.toString();
	}

	protected static Map<String, Object> getMap(ProtocolWeb.PlatformInfo info) {
		Map<String, Object> map0 = new java.util.HashMap<>();
		int count = info.getKeysCount();
		for (int i = 0; i < count; i++) {
			String key = info.getKeys(i);
			int value = info.getValues(i);
			// 插入参数
			map0.put(key, Long.valueOf(value));
		}

		return map0;
	}

	protected static boolean lineStr(String[] keys, StringBuffer strBuf, String platform, Map<String, Object> map0, int type) {
		int keyCount = (keys != null) ? keys.length : 0;

		strBuf.append("</tr>");

		String typeStr = (type == 0) ? "showTbA" : "showTbB";
		strBuf.append("<td class=\"" + typeStr + "\">");
		strBuf.append(platform);
		strBuf.append("</td>");

		// 遍历
		for (int i = 0; i < keyCount; i++) {
			String key0 = keys[i];
			Object value = map0.get(key0);
			long value0 = (value != null) ? (Long) value : 0;

			// 写入参数
			strBuf.append("<td class=\"" + typeStr + "\">");
			strBuf.append(value0);
			strBuf.append("</td>");
		}

		strBuf.append("</tr>");
		return true;
	}
}
