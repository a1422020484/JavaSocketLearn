package java8Test.streamTest;

import java.util.ArrayList;
import java.util.List;

public class MatchTest {
	public static void main(String[] args) {
		List<Integer> elements1 = new ArrayList<>();
		elements1.add(1);
		elements1.add(2);
		elements1.add(3);
		List<Integer> elements2 = new ArrayList<>();
		elements2.add(2);
		elements2.add(3);
		elements2.add(4);

		System.out.println(elements1.stream().anyMatch(e -> e == 3));
		
		elements1.stream().filter(e -> e == 3).findFirst();
	}
}
