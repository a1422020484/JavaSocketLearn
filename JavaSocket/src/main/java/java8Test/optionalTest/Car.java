package java8Test.optionalTest;

import java.util.Optional;

public class Car {
	private Optional<String> carName;
	private Optional<Insurance> insurance;

	public Optional<String> getCarName() {
		return carName;
	}

	public void setCarName(Optional<String> carName) {
		this.carName = carName;
	}

	public Optional<Insurance> getInsurance() {
		return insurance;
	}

	public void setInsurance(Optional<Insurance> insurance) {
		this.insurance = insurance;
	}

}
