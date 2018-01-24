package lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class First {
	public static void main(String[] args) {
		// forEachTest();
		// innerClass();
		sort();
	}

	public static void forEachTest() {
		String[] atp = { "Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer", "Andy Murray", "Tomas Berdych", "Juan Martin Del Potro" };
		List<String> players = Arrays.asList(atp);

		// 以前的循环方式
		for (String player : players) {
			System.out.print(player + "; ");
		}
		System.out.println("=====");
		// 使用 lambda 表达式以及函数操作(functional operation)
		players.forEach((player) -> System.out.print(player + "; "));
		System.out.println("=====");
		// 在 Java 8 中使用双冒号操作符(double colon operator)
		players.forEach(System.out::println);
	}

	/**
	 * 使用匿名内部类
	 */
	public static void innerClass() {
		// 使用Runnable
		Runnable race1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("i am form runnale innerClass");
			}
		};

		// 或者使用 lambda expression
		Runnable race2 = () -> System.out.println("Hello world !");
		race1.run();
		race2.run();

		// 关于线程的方法
		new Thread() {
			@Override
			public void run() {
				System.out.println("iam frm  Thread ==== ");
			}
		}.start();

		new Thread(() -> System.out.println("from Thread landmdm!")).start();
	}

	/**
	 * sort 使用Lambdas排序集合
	 */
	public static void sort() {
		String[] atp = { "Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer", "Andy Murray", "Tomas Berdych", "Juan Martin Del Potro", "Richard Gasquet", "John Isner" };

		// 1.1 使用匿名内部类根据 name 排序 players
		Arrays.sort(atp, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return (s1.compareTo(s2));
			}
		});

		List<String> players = Arrays.asList(atp);
		players.forEach(System.out::println);
		
		
		Comparator<String> sortByName = (String s1, String s2) -> (s1.compareTo(s2));
		Arrays.sort(atp, sortByName);

		// 1.3 也可以采用如下形式:
		Arrays.sort(atp, (String s1, String s2) -> (s1.compareTo(s2)));
	}
}
