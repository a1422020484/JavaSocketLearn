package four;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayDequeueTest {

	public static void main(String[] args) {
		BlockingQueue<Team> q = new ArrayBlockingQueue<>(5);
		Team t1 = new Team("1", 1);
		Team t2 = new Team("2", 1);
		q.offer(t1);
		q.offer(t2);
		q.offer(new Team("3", 1));
		q.offer(new Team("4", 1));
		q.offer(new Team("5", 1));

		q.remove(t2);
		q.remove(t1);

		Iterator<Team> iterator = q.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getName());
		}

	}

	static class Team {
		private String name;
		private int num;

		public Team(String name, int num) {
			this.name = name;
			this.num = num;
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

	}

}
