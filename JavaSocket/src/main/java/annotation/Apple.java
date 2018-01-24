package annotation;

import annotation.FruitColor.Color;

public class Apple {

	@FruitName("Apple")
	private String appleName;

	@FruitColor(fruitColor = Color.RED)
	private String appleColor;

	@FruitProvider(id = 4, name = "陕西", address = "深圳")
	private FruitProvider fruitProvide;

	public void setAppleColor(String appleColor) {
		this.appleColor = appleColor;
	}

	public String getAppleColor() {
		return appleColor;
	}

	public void setAppleName(String appleName) {
		this.appleName = appleName;
	}

	public String getAppleName() {
		return appleName;
	}
	
	public void displayName() {
		System.out.println("水果的名字是：苹果");
	}
}