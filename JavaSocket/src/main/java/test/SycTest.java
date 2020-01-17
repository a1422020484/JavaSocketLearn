package test;

public class SycTest {
	private static SycTest singleton;

	private SycTest() {
	}

	public static SycTest getInstance() {
		if (singleton == null) { // 1
			synchronized (SycTest.class) {
				if (singleton == null) {
					singleton = new SycTest(); // 2
				}
			}
		}
		return singleton;
	}

	public synchronized void syncTest() {
		if (singleton == null) { // 1
			if (singleton == null) {
				singleton = new SycTest(); // 2
			}
		}
	}
}
