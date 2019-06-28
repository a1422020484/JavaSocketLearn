package java8Test.optionalTest;

import java.util.Optional;
import java.util.OptionalInt;

public class OptionalTest {

	public static void main(String[] args) {
		// List<Integer> integerList = new ArrayList<>();
		// for (int i = 0; i < 10; i++) {
		// integerList.add(RandomUtils.nextInt(100));
		// }
		// integerList.forEach(e->{
		// System.out.println(e);
		// });

		Optional<Car> car1 = Optional.of(new Car());

		Optional<Car> car2 = Optional.empty();

		Optional<Car> car3 = Optional.ofNullable(car1.get());

		System.out.println(car1.isPresent());
		System.out.println(car2.isPresent());
		System.out.println(car3.isPresent());

		Optional<Insurance> optInsurance = Optional.ofNullable(new Insurance());
		optInsurance.get().setInsuranceName("xpy");
//		Optional<String> name = optInsurance.map(Insurance::getInsuranceName);
//		String nameS = name.orElse("Unknown");
//		System.out.println(nameS);
		car1.get().setInsurance(optInsurance);
		Person person = new Person();
		person.setCar(car1);
		Optional<Person> optPerson = Optional.of(person);
		String insuranceName = optPerson
						.flatMap(Person::getCar)
						.flatMap(Car::getInsurance)
						.map(Insurance::getInsuranceName)
						.orElse("Unknown");
		System.out.println(insuranceName);
		
		Optional<Person> optPerson2 = Optional.of(new Person());
		System.out.println(optPerson2.get().getCar());
		String insuranceName2 = optPerson2
						.filter(p -> p.getCar().isPresent())
						.flatMap(Person::getCar)
						.filter(c -> c.getInsurance().isPresent())
						.flatMap(Car::getInsurance)
						.map(Insurance::getInsuranceName)
						.orElse("unknow2");
		System.out.println(insuranceName2);
	}
	
	public static Optional<Integer> stringToInt(String s){
		try {
			return Optional.of(Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
	
	public static void optInteger(int value) {
		OptionalInt.of(value);
	}
	
}
