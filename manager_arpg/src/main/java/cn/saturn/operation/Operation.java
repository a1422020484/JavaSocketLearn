package cn.saturn.operation;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * 运营对象
 *
 */
public class Operation {

	// 写入map
	public static long writeTo(Map<String, Object> map, List<PlatformValue> values) {
		long total = 0;
		// 写入数据
		int count = (values != null) ? values.size() : 0;
		for (int i = 0; i < count; i++) {
			PlatformValue pvalue = values.get(i);
			String platform = pvalue.getPlatform();
			long value = pvalue.getValue();
			total += value;
			// 加入
			map.put(platform, value);
		}

		return total;
	}

	public static void wirteTo(Map<String, Map<String, Object>> map, String key, Map<String, Object> list) {
		Iterator<Map.Entry<String, Object>> iter = list.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = iter.next();
			String key0 = entry.getKey();
			Object value = entry.getValue();
			// 获取子集合
			Map<String, Object> map0 = map.get(key0);
			if (map0 == null) {
				// 创建子集
				map0 = new HashMap<String, Object>();
				map.put(key0, map0);
			}

			// 写入数据
			map0.put(key, value);
		}

	}

	// 处理统计平台
	public static Map<String, Object> query(JdbcTemplate template, String sql) {
		Map<String, Object> out = new HashMap<>();

		// 获取每个渠道
		List<PlatformValue> values = template.query(sql, BeanPropertyRowMapper.newInstance(PlatformValue.class));

		// 写入数据
		long total = writeTo(out, values);
		out.put("%", total);
		return out;
	}

	// 新增用户
	public static Map<String, Object> newUser(JdbcTemplate template, String dayStr) {
		// 获取每个渠道
		String sql = "SELECT count(*) as value,platform FROM account WHERE create_time like \"{0}%\" GROUP BY platform;";
		sql = MessageFormat.format(sql, dayStr);
		Map<String, Object> out = query(template, sql);
		return out;
	}

	// 活跃用户(当日登陆的)
	public static Map<String, Object> activeUser(JdbcTemplate template, String dayStr) {
		// 当天0点时间
		int today = (int) (TimeUtils.todayTimes(TimeUtils.toTimes(dayStr, "yyyy-MM-dd")) * TimeUtils.secondRate);
		int tomorrow = today + TimeUtils.oneDayTime;

		// 获取每个渠道
		String sql = "SELECT count(*) as value,platform FROM account WHERE UNIX_TIMESTAMP(create_time)<{1,number,0} AND UNIX_TIMESTAMP(login_time)>={0,number,0} GROUP BY platform;";
		sql = MessageFormat.format(sql, today, tomorrow);
		System.out.println(sql);
		Map<String, Object> out = query(template, sql);
		return out;
	}

	// 当日充值用户
	public static Map<String, Object> dayPayUser(JdbcTemplate template, String dayStr, int srvId) {
		String whereStr = "";
		if (srvId > 0) {
			whereStr = " and server_id=" + srvId + " ";
		}
		// 获取每个渠道
		String sql = "SELECT count(distinct account_id) as value,platform FROM `order` WHERE create_time like \"{0}%\"" + whereStr + " GROUP BY platform;";
		sql = MessageFormat.format(sql, dayStr);
		Map<String, Object> out = query(template, sql);
		return out;
	}

	// 当日新增用户充值(当日创建当日付费)
	public static Map<String, Object> newPayUser(JdbcTemplate template, String dayStr, int srvId) {
		// // 当天0点时间
		// int today = (int) (TimeUtils.todayTimes(TimeUtils.toTimes(dayStr, "yyyy-MM-dd")) * TimeUtils.secondRate);
		// int tomorrow = today + TimeUtils.oneDayTime;
		// String sql =
		// "SELECT count(distinct `order`.account_id) as value,account.platform FROM `order` INNER JOIN account ON `order`.account_name=account.account AND `order`.platform=account.platform WHERE UNIX_TIMESTAMP(account.create_time)>={0,number,0} and UNIX_TIMESTAMP(account.create_time)<{1,number,0} GROUP BY account.platform;";
		// sql = MessageFormat.format(sql, today, tomorrow);
		String whereStr = "";
		if (srvId > 0) {
			whereStr = " and server_id=" + srvId + " ";
		}

		// 获取每个渠道
		String sql = "SELECT count(distinct `order`.account_id) as value,account.platform FROM account INNER JOIN `order` ON `order`.account_name=account.account AND `order`.platform=account.platform WHERE account.create_time like \"{0}%\" and `order`.create_time like \"{0}%\" "
				+ whereStr + " GROUP BY account.platform;";
		// String sql =
		// "select count(distinct b.account_id) as value,a.platform from (select * from account where create_time like \"{0}%\") as a inner join (select * from `order` where create_time like \"{0}%\") as b on a.account=b.account_name and a.platform=b.platform GROUP BY a.platform";
		sql = MessageFormat.format(sql, dayStr);
		Map<String, Object> out = query(template, sql);
		return out;

		// return new HashMap<String, Object>();
	}

	// 当日新增用户充值数量
	public static Map<String, Object> newPayCount(JdbcTemplate template, String dayStr, int srvId) {
		// // 获取每个渠道
		// String sql =
		// "SELECT sum(amount) as value,account.platform FROM account INNER JOIN `order` ON `order`.account_name=account.account AND `order`.platform=account.platform WHERE account.create_time like \"{0}%\" and `order`.create_time like \"{0}%\" GROUP BY account.platform;";
		// sql = MessageFormat.format(sql, dayStr);
		// Map<String, Object> out = query(template, sql);
		// return out;

		String whereStr = "";
		if (srvId > 0) {
			whereStr = " and server_id=" + srvId + " ";
		}

		String sql = "SELECT sum(amount) as value,account.platform FROM account INNER JOIN `order` ON `order`.account_name=account.account AND `order`.platform=account.platform WHERE account.create_time like \"{0}%\" and `order`.create_time like \"{0}%\" "
				+ whereStr + " GROUP BY account.platform;";
		sql = MessageFormat.format(sql, dayStr);
		Map<String, Object> out = query(template, sql);
		return out;

		// return new HashMap<String, Object>();
	}

	// 当日充值金额
	public static Map<String, Object> dayPayCount(JdbcTemplate template, String dayStr, int srvId) {
		String whereStr = "";
		if (srvId > 0) {
			whereStr = " and server_id=" + srvId + " ";
		}

		// 获取每个渠道
		String sql = "SELECT sum(amount) as value,platform FROM `order` WHERE create_time like \"{0}%\" " + whereStr + " GROUP BY platform;";
		sql = MessageFormat.format(sql, dayStr);
		Map<String, Object> out = query(template, sql);
		return out;
	}

	// 计算留存
	public static Map<String, Object> remainUser(JdbcTemplate template, String dayStr, int day) {
		// 当天0点时间
		int today = (int) (TimeUtils.todayTimes(TimeUtils.toTimes(dayStr, "yyyy-MM-dd")) * TimeUtils.secondRate);
		int tomorrow = today + day * TimeUtils.oneDayTime; // 计算留存日期
		String tmStr = TimeUtils.toString(tomorrow, "yyyy-MM-dd");

		// 获取每个渠道
		String sql = "SELECT count(*) as value,platform FROM account WHERE create_time like \"{0}%\" and login_time>\"{1}\" GROUP BY platform;";
		sql = MessageFormat.format(sql, dayStr, tmStr);
		Map<String, Object> out = query(template, sql);
		return out;
	}

	// 处理统计平台
	public static Map<String, Map<String, Object>> statistics(JdbcTemplate template, String dayStr) {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();

		// 新增用户
		Map<String, Object> newUsers = newUser(template, dayStr);
		wirteTo(map, "newUser", newUsers);
		// 活跃用户
		Map<String, Object> activeUsers = activeUser(template, dayStr);
		wirteTo(map, "activeUser", activeUsers);

		// 当日充值
		Map<String, Object> dayPayUsers = dayPayUser(template, dayStr, 0);
		wirteTo(map, "dayPayUser", dayPayUsers);

		// 当日新增充值用户
		Map<String, Object> newPayUsers = newPayUser(template, dayStr, 0);
		wirteTo(map, "newPayUser", newPayUsers);

		// 当日新增充值总额(新增用户)
		Map<String, Object> newPayCounts = newPayCount(template, dayStr, 0);
		wirteTo(map, "newPayCount", newPayCounts);

		// 当日充值数
		Map<String, Object> dayPayCounts = dayPayCount(template, dayStr, 0);
		wirteTo(map, "dayPayCount", dayPayCounts);

		// 次日留存
		for (int i = 0; i < 6; i++) {
			int day = i + 1;
			Map<String, Object> remainUsers = remainUser(template, dayStr, day);
			wirteTo(map, String.format("remain%02d", day), remainUsers);
		}
		return map;
	}

	// /**
	// * 支付信息
	// *
	// * @return 新增支付用户, 总支付用户, 当日新增支付
	// *
	// * **/
	// public static int[] payInfo(JdbcTemplate template, String dayStr, String platform) {
	// // 当天0点时间
	// int today = (int) (TimeUtils.todayTimes(TimeUtils.toTimes(dayStr, "yyyy-MM-dd")) * TimeUtils.secondRate);
	// // int yestoday = today - TimeUtils.oneDayTime;
	// int tomorrow = today + TimeUtils.oneDayTime;
	//
	// String sql = null;
	// // 新增充值总数
	// sql = MessageFormat.format("select sum(amount) from `order` where UNIX_TIMESTAMP(create_time)>={0,number,0} and  UNIX_TIMESTAMP(finish_time)<={1,number,0} and `order`.platform LIKE \"{2}\";",
	// today, tomorrow, platform);
	// Integer newPay = template.queryForObject(sql, Integer.class);
	//
	// // 充值总用户
	// sql = MessageFormat.format("select count(distinct account_id) from `order` where UNIX_TIMESTAMP(finish_time)<={0,number,0} and `order`.platform LIKE \"{1}\";", tomorrow, platform);
	// Integer totalUser = template.queryForObject(sql, Integer.class);
	//
	// // 充值总数
	// sql = MessageFormat.format("select sum(amount) from `order` where UNIX_TIMESTAMP(finish_time)<={0,number,0} and `order`.platform LIKE \"{1}\";", tomorrow, platform);
	// Integer totalPay = template.queryForObject(sql, Integer.class);
	//
	// // 昨天的充值总用户
	// sql = MessageFormat.format("select count(distinct account_id) from `order` where UNIX_TIMESTAMP(finish_time)<={0,number,0} and `order`.platform LIKE \"{1}\";", today, platform);
	// Integer ttotalUser = template.queryForObject(sql, Integer.class);
	// Integer newUser = totalUser - ttotalUser;
	//
	// // System.out.println(newUser + "\t" + totalUser + "\t" + newPay + "\t" + totalPay);
	// int newPay0 = (newPay != null) ? newPay : 0;
	// int totalUser0 = (totalUser != null) ? totalUser : 0;
	// int totalPay0 = (totalPay != null) ? totalPay : 0;
	// int newUser0 = (newUser != null) ? newUser : 0;
	//
	// return new int[] { newUser0, totalUser0, newPay0, totalPay0 };
	// }
	//
	// /**
	// * 用户信息
	// *
	// * @return 总用户, 当日新增用户, 当日活跃用户
	// *
	// *
	// * **/
	// public static int[] userInfo(JdbcTemplate template, String dayStr, String platform) {
	// // 当天0点时间
	// int today = (int) (TimeUtils.todayTimes(TimeUtils.toTimes(dayStr, "yyyy-MM-dd")) * TimeUtils.secondRate);
	// // int yestoday = today - TimeUtils.oneDayTime;
	// int tomorrow = today + TimeUtils.oneDayTime;
	//
	// String sql = null;
	// // 获取总新增用户
	// sql = MessageFormat.format("SELECT count(id) FROM `account` where UNIX_TIMESTAMP(create_time)<={0,number,0} and platform LIKE \"{1}\";", tomorrow, platform);
	// Integer totalUser = template.queryForObject(sql, Integer.class);
	//
	// // 获取当天新增用户
	// sql = MessageFormat.format("SELECT count(id) FROM `account` where create_time LIKE \"{0}%\" and platform LIKE \"{1}\";", dayStr, platform);
	// Integer todayUser = template.queryForObject(sql, Integer.class);
	//
	// // 获取当天活跃用户
	// sql = MessageFormat.format("SELECT count(id) FROM `account` where UNIX_TIMESTAMP(login_time)>={1,number,0} and platform LIKE \"{0}\";", platform, today);
	// Integer activeUser = template.queryForObject(sql, Integer.class);
	//
	// // 返回数据
	// int totalUser0 = (totalUser != null) ? totalUser : 0;
	// int todayUser0 = (todayUser != null) ? todayUser : 0;
	// int activeUser0 = (activeUser != null) ? activeUser : 0;
	// return new int[] { totalUser0, todayUser0, activeUser0 };
	// }
	//
	// /** 用户留存 **/
	// public static float[] userRemain(JdbcTemplate template, String dayStr, String platform, int numday) {
	// // 当天0点时间
	// int today = (int) (TimeUtils.todayTimes(TimeUtils.toTimes(dayStr, "yyyy-MM-dd")) * TimeUtils.secondRate);
	// int startday = today - TimeUtils.oneDayTime * numday; // 开始日期
	// // int tomorrow = today + TimeUtils.oneDayTime;
	//
	// // 计算那天以来的总用户
	// String sql = null;
	// // 获取总新增用户
	// sql = MessageFormat.format("select count(account.id) from account where UNIX_TIMESTAMP(create_time)>={0,number,0} and UNIX_TIMESTAMP(create_time)<{1,number,0} and platform LIKE \"{2}\";",
	// startday, today, platform);
	// Integer totalUser = template.queryForObject(sql, Integer.class);
	//
	// // 查出这些用户今天还上限的
	// sql = MessageFormat
	// .format("select count(account.id) from account where UNIX_TIMESTAMP(create_time)>={0,number,0} and UNIX_TIMESTAMP(create_time)<{1,number,0} and UNIX_TIMESTAMP(login_time)>={1,number,0} and platform LIKE \"{2}\";",
	// startday, today, platform);
	// Integer loginUser = template.queryForObject(sql, Integer.class);
	//
	// // 返回数据
	// int totalUser0 = (totalUser != null) ? totalUser : 0;
	// int loginUser0 = (loginUser != null) ? loginUser : 0;
	// float remain = (totalUser0 > 0) ? (loginUser0 / (float) totalUser0) : 0;
	// return new float[] { remain, loginUser0, totalUser0, };
	// }

	// public static class
}
