package java8Test.construct;

public class HRouter {

	private Hpoint hpoint;

	private HRouter(Hpoint hpoint) {
		this.hpoint = hpoint;
	}

	public static void main(String[] args) {
		new HRouter((classA, classB) -> {
			classA.doSomethingA();
			classB.doSomethingB();
			return true;
		}).runTouter();

		new Thread(() -> {
			System.out.println("run");
		}).start();
	}
	
	public void runTouter() {
		this.hpoint.take(new ClassA(), new ClassB());
	}
	
}

class ClassA {
	public void doSomethingA() {
		System.out.println("class A doSomething");
	}
}

class ClassB {
	public void doSomethingB() {
		System.out.println("class B doSomething");
	}
}
