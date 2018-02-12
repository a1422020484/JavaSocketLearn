package cn.saturn.web.utils;

import java.util.Calendar;

/**
 * 订单ID生成器
 * @author xiezuojie
 */
public final class OrderIdGen {

	private static int inc = 0; // 计数器
	private static int preSec; // 前一秒
	private static long preTime; // 上一次执行时间
	private static final int INC_MAX = 999;
	private static final Calendar c = Calendar.getInstance();
	
	/**
	 * 生成订单ID<br/>
	 * 长度为18位,由6位日期+3位序列值+6位时间+3位随机值组成,例：160706768121414085<br/>
	 * 生成的订单ID不会重复,每秒最多可生成999个<br/>
	 * @return 生成的订单id
	 */
	public static synchronized long generate() {
		if (inc == INC_MAX) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(preTime);
				int ms = cal.get(Calendar.MILLISECOND);
				Thread.sleep(1000 - ms);
			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
		}
		long timeMillis = System.currentTimeMillis();
		preTime = timeMillis;
		c.setTimeInMillis(timeMillis);
		int yyyy = c.get(Calendar.YEAR);
		long yy = (yyyy - (yyyy / 100 * 100 /*2016->2000*/)) * 10000000000000000L;
		long MM = (c.get(Calendar.MONTH) + 1) * 100000000000000L;
		long dd = c.get(Calendar.DAY_OF_MONTH) * 1000000000000L;
		long HH = c.get(Calendar.HOUR_OF_DAY) * 10000000L;
		long mm = c.get(Calendar.MINUTE) * 100000L;
		int ss = c.get(Calendar.SECOND);
		long ssV = ss * 1000L;
		if (preSec != ss) {
			preSec = ss;
			inc = 1;
		} else {
			inc++;
		}
		long tinc = inc  * 1000000000L;
		long nanoTime = System.nanoTime();
		long nanoV = (nanoTime >>> ((inc % 4) + 1)) & 0x000003DF; // '3' len
		return yy + MM + dd + HH + mm + ssV + tinc + nanoV;
	}
	
}
