package five;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yangxp
 * @date 2017年8月9日 下午3:10:12
 * 时间有重复，所以总是间隔一个大的值。
 * cancel()
 */
public class TimerTest2 {

	public static Timer timer = new Timer();

	public static void main(String[] args) {
		try {
			MyTask2 task2 = new MyTask2();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2017-08-09 15:05:00";
			Date dateRef = sdf.parse(dateString);
			System.out.println("字符串时间：" + dateRef.toLocaleString() + " 当前时间：" + new Date().toLocaleString());
//			timer.schedule(task2, dateRef, 1000);
			timer.scheduleAtFixedRate(task2, dateRef, 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class MyTask2 extends TimerTask {

	public static int count ;
	
	@Override
	public void run() {
		System.out.println("peer ==== " + System.currentTimeMillis());
		count++;
		if(count == 10){
			// 将自身从人物队列中被移除，其他人物不受影响。
			this.cancel();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
