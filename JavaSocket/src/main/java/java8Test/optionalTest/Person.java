package java8Test.optionalTest;

import java.util.Optional;

public class Person {

	private Optional<String> personName;
	private Optional<Car> car;

	public Optional<String> getPersonName() {
		return personName;
	}

	public void setPersonName(Optional<String> personName) {
		this.personName = personName;
	}

	public Optional<Car> getCar() {
		return car;
	}

	public void setCar(Optional<Car> car) {
		this.car = car;
	}

}
