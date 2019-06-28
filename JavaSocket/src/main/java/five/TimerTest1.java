package five;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yangxp
 * @date 2017年8月9日 下午2:54:21
 * <p>
 * 计划时间大于当前时间
 */
public class TimerTest1 {

	private static Timer timer = new Timer();
//	private static Timer timer = new Timer(true);设置为守护线程

	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2017-08-09 14:55:00";
			Date dateRef = sdf.parse(dateString);
			System.out.println("字符串时间：" + dateRef.toLocaleString() + " 当前时间：" + new Date().toLocaleString());
			timer.schedule(task1, dateRef);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
			System.out.println("运行了！时间为： " + new Date());
		}
	}
}
