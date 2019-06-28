package four;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
	public static void main(String[] args) {
		BlockingQueue<Team> q = new DelayQueue<Team>();
		Team t1 = new Team("1", 1, 5000);
		Team t2 = new Team("2", 1, 1000);
		q.offer(t1);
		q.offer(t2);
		long time1 = System.currentTimeMillis();
		try {
			q.take();
			long time2 = System.currentTimeMillis();
			System.out.println(time2 - time1);
			q.take();
			long time3 = System.currentTimeMillis();
			System.out.println(time3 - time1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static class Team implements Delayed {
		private String name;
		private int num;
		private long delayTime;

		public Team(String name, int num, long delayTime) {
			this.name = name;
			this.num = num;
			this.delayTime = delayTime + System.currentTimeMillis();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public long getDelayTime() {
			return delayTime;
		}

		public void setDelayTime(long delayTime) {
			this.delayTime = delayTime;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.NANOSECONDS);
		}

		@Override
		public int compareTo(Delayed o) {
			return 1;
		}

	}

}
