package java8Test.menu;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {
	private static List<Dish> menu = Arrays.asList(
					new Dish("beef", false, 800, Dish.Type.MEAT),
					new Dish("pork", false, 800, Dish.Type.MEAT),
					new Dish("chicken", false, 400, Dish.Type.MEAT),
					new Dish("french", true, 530, Dish.Type.OTHER),
					new Dish("rice", true, 350, Dish.Type.OTHER),
					new Dish("season", true, 120, Dish.Type.OTHER),
					new Dish("pizza", true, 550, Dish.Type.OTHER),
					new Dish("prawns", false, 300, Dish.Type.FISH),
					new Dish("salmon", false, 450, Dish.Type.FISH)
					);
					
	
	public static void main(String[] args) {
		Map<Boolean, List<Dish>> partitionedMenu1 = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
		System.out.println(partitionedMenu1);
		
		Map<Boolean, Map<Dish.Type, List<Dish>>> partitionedMenu2 = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
		System.out.println(partitionedMenu2);
		
		Map<Boolean, Dish> partitionedMenu3 = menu.stream()
						.collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
		System.out.println(partitionedMenu3);
		
	}
}
