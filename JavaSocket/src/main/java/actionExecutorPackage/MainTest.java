package actionExecutorPackage;

import java.util.LinkedHashMap;

public class MainTest<K, V> {

	public LinkedHashMap<K, V> linkedHashMap;

	public static void main(String[] args) {
		ActionExecutor<ActionTask> actionExecutor = new ActionExecutor<>(10000);
		actionExecutor.execute(new ActionTask() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				System.out.println("MainTest run");
			}

			@Override
			public Integer getPlayerId() {
				return 1;
			}
		});
		actionExecutor.stop();
		System.out.println(Runtime.getRuntime().availableProcessors());
		MainTest mainTest = new MainTest();
	}

	public MainTest() {
		linkedHashMap = new LinkedHashMap<K, V>(1000, 0.75f, true) {

			private static final long serialVersionUID = 1L;

			public int a = 10;
			public int doSomething() {
				int b = 100;
				return b;
			};
			
		};
	}
	
//	public LinkedHashMap<K, V> getLinkedHashMap() {
//		return linkedHashMap;
//	}
}
