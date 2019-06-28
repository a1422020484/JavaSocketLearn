package java8Test.transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {
	public static List<Transaction> transactions;
	
	public static void main(String[] args) {
		
		Trader raoul = new Trader("raoul", "Cambridge");
		Trader mario = new Trader("mario", "Milan");
		Trader alan = new Trader("alan", "Cambridge");
		Trader brian = new Trader("brian", "Cambridge");
		
		transactions = Arrays.asList(
						new Transaction(brian, 2011, 300),
						new Transaction(raoul, 2012, 1000),
						new Transaction(raoul, 2011, 400),
						new Transaction(mario, 2012, 710),
						new Transaction(mario, 2012, 700),
						new Transaction(alan, 2012, 950)
						); 
		q1();
		q2();
		q3();
		q4();
		q5();
		q5();
		q6();
		q7();
		q8();
	}
	
	public static void q1() {
		List<Transaction> t1 = transactions.stream().filter(e -> e.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
		t1.stream().forEach(e -> System.out.println(e.getValue()));
	}
	
	public static void q2() {
		List<String> citys = transactions.stream().map(e -> e.getTrader().getCity()).distinct().collect(Collectors.toList());
		System.out.println(citys);
	}
	
	public static void q3() {
		List<String> traders = transactions.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).map(Transaction::getTrader).sorted(Comparator.comparing(Trader::getName)).map(Trader::getName).distinct().collect(Collectors.toList());
		System.out.println(traders);
	}
	
	public static void q4() {
		List<String> traders = transactions.stream().map(Transaction::getTrader).sorted(Comparator.comparing(Trader::getName)).map(Trader::getName).distinct().collect(Collectors.toList());
		System.out.println(traders);
	}
	
	public static void q5() {
		boolean isInMilan = transactions.stream().anyMatch(e -> e.getTrader().getCity().equals("Milan"));
		System.out.println(isInMilan);
	}
	
	public static void q6() {
		int sum = transactions.stream().filter(e -> e.getTrader().getCity().equals("Cambridge")).map(e -> e.getValue()).reduce(0, Integer::sum);
		System.out.println(sum);
	}
	
	public static void q7() {
//		int sumValue = transactions.stream().map(e -> e.getValue()).reduce(0, (a, b) -> a + b);
		Optional<Integer> maxValue = transactions.stream().map(e -> e.getValue()).reduce(Integer::max);
		System.out.println(maxValue.get());
	}
	
	public static void q8() {
		Optional<Transaction> optTransaction = transactions.stream().sorted(Comparator.comparing(Transaction::getValue)).findFirst();
		System.out.println(optTransaction.get().getTrader().getName());
	}
}
