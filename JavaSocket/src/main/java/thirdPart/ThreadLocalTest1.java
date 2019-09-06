package thirdPart;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalTest1 {
	private static ThreadLocal<User> local = new ThreadLocal<User>();
	private static ThreadLocal<User> local2 = new ThreadLocal<User>();

	private int test = nextHashCode();

	private static AtomicInteger nextHashCode = new AtomicInteger(0);

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		User user1 = new User("zhangz", 99);
		User user2 = new User("zhasfdaangz", 99);
		System.out.println(Thread.currentThread().getName());
		local.set(user1);
		local2.set(user1);
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				local2.set(user2);
//				User user1T2 = local2.get();
//				System.out.println(user1T2.getName());
//			}
//
//		}).start();
		local.get().setName("ff1");;
		local2.get().setName("ff2");;
		User user1T1 = local.get();
		User user1T2 = local2.get();
		System.out.println(user1T1.getName());
		System.out.println(user1T2.getName());

	}

	private static int nextHashCode() {
		return nextHashCode.getAndAdd(0x61c88647);
	}

	static class User {
		private String name;
		private int age;

		public User(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

	}
}
