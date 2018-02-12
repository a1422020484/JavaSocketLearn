package cn.operation.test;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.saturn.operation.Operation;

public class OperationTest {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 创建template
		javax.sql.DataSource dataSource = (javax.sql.DataSource) context.getBean("loginDb");
		JdbcTemplate template = new JdbcTemplate(dataSource);

		// // 输出支付信息
		// long time = TimeUtils.toTimes("2015-12-07", TimeUtils.dayFormat);
		// for (int i = 0; i < 5; i++) {
		// time += TimeUtils.oneDayTimeL;
		// String dayStr = TimeUtils.toString(time, TimeUtils.dayFormat);
		// int[] payInfos = Operation.payInfo(template, dayStr, "%");
		// int[] userInfos = Operation.userInfo(template, dayStr, "%");
		//
		// String[] remains = new String[7];
		// for (int j = 0; j < 7; j++) {
		// float[] remains0 = Operation.userRemain(template, dayStr, "%", j + 1);
		// float r = 100.0f * remains0[0];
		// remains[j] = MessageFormat.format("{0,number,0.00}%", r);
		// }
		//
		// System.out.println(dayStr + " : " + Arrays.toString(userInfos) + " " + Arrays.toString(payInfos) + " " + Arrays.toString(remains));
		// }

		// Map<String, Map<String, Object>> map = Operation.statistics(template, "2015-12-07");
		// // System.out.println("map:" + map);
		//
		// Iterator<Map.Entry<String, Map<String, Object>>> iter = map.entrySet().iterator();
		// while (iter.hasNext()) {
		// Map.Entry<String, Map<String, Object>> entry = iter.next();
		// String key = entry.getKey();
		// Map<String, Object> map0 = entry.getValue();
		// System.out.println(key + "\t:\t" + map0);
		// }

		long startTimeL, endTimeL, useTimeL;

		String dayStr = "2015-12-22";
		// 算法1
		startTimeL = System.currentTimeMillis();
		Map<String, Object> map = Operation.newPayUser(template, dayStr, 0);
		endTimeL = System.currentTimeMillis();
		useTimeL = endTimeL - startTimeL;
		System.out.println(useTimeL + " " + map);

		// 算法2
		startTimeL = System.currentTimeMillis();
		Map<String, Object> map2 = Operation.newPayCount(template, dayStr, 0);
		endTimeL = System.currentTimeMillis();
		useTimeL = endTimeL - startTimeL;
		System.out.println(useTimeL + " " + map2);
	}

}
