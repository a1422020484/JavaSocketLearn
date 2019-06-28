package threadExecutor;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerTest {

	public static void main(String[] args) throws Exception {
		TimerTest tt = new TimerTest();
		// tt.timerTest(1000);
		// tt.CountTime(10);
		tt.ScheduleExecutorTest();
		// tt.ScheduleExecutorDelayTest();
	}

	public void CountTime(int limitSec) throws Exception {
		System.out.println("Count from " + limitSec);
		while (limitSec > 0) {
			System.out.println("remians " + --limitSec + " s");
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println("Time is out");
	}

	/**
	 * dosomething运行完之后如果大于1秒则立即运行下一次，如果小于1秒则等至1秒
	 */
	public void ScheduleExecutorTest() {
		ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
		timer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("beef" + System.currentTimeMillis() / 1000);

			}
		}, 1, 1, TimeUnit.SECONDS);
	}

	/**
	 * 线程运行完了 然后再等1秒
	 */
	public void ScheduleExecutorDelayTest() {
		ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
		timer.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("beef" + System.currentTimeMillis() / 1000);

			}
		}, 1, 1, TimeUnit.SECONDS);
	}

	public void timerTest(int limitSec) throws InterruptedException {
		System.out.println("count down from " + limitSec + " s ");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int i = 0;

			public void run() {
				System.out.println("Time remians " + i++ + " s");
			}
		}, 0, 1000);
		TimeUnit.SECONDS.sleep(limitSec);
		timer.cancel();
		System.out.println("Time is out!");
	}
}
