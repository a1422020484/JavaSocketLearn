package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		List<Integer> array = new ArrayList<Integer>();

		for (int i = 0; i < 10000; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					array.add(1);
				}

			});
			thread.start();
			thread.join();
		}
		System.out.println(array.size());
		System.exit(0);
	}
}
