package java8Test.streamTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.RandomUtils;

public class StreamTest {
	public static void main(String[] args) {
		sumMumberArray();
	}
	
	public static void sumMumberArray() {
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		int sum = numbers1.stream().reduce(6, (a, b) -> a + b);
		int sum2 = numbers1.stream().reduce(6, Integer::sum);
		Optional<Integer> num3 = numbers1.stream().reduce(Integer::max);
		System.out.println(sum);
		System.out.println(sum2);
		System.out.println(num3.get());
	}
	
	public static void numberDo() {
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);
		List<int[]> numbers = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[] { i, j })).collect(Collectors.toList());
		numbers.stream().forEach(e -> System.out.println(Arrays.toString(e)));
	}
	
	public static void wordArrayTest() {
		String[] words = { "hello", "world" };
		
		List<String> distinctWord = Arrays.asList(words).stream()
						.map(m->m.split(""))// 将字符串分割
						.flatMap(Arrays::stream)// 多个字符串数组合并
						.distinct()
						.collect(Collectors.toList());
		System.out.println(distinctWord);
	}
	
	public static void personTest() {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 10; i++) {
			int randomAge = RandomUtils.nextInt(100);
			Person person = new Person("tt" + randomAge, randomAge);
			persons.add(person);
		}

		persons.forEach(e -> System.out.println(e.toString()));
		// 升序
		List<String> nameList1 = persons.stream().filter(p -> p.getAge() > 50)
						.sorted(Comparator.comparing(Person::getAge))
						.map(Person::getName)
						.collect(Collectors.toList());
		// 降序
		List<String> nameList2 = persons.stream().filter(p -> p.getAge() > 50)
						.sorted(Comparator.comparing(Person::getAge).reversed())
						.map(Person::getName)
						.collect(Collectors.toList());
		
		nameList1.forEach(e -> {
			System.out.println(e);
		});
		System.out.println("============================");
		nameList2.forEach(e -> {
			System.out.println(e);
		});
	}
}

