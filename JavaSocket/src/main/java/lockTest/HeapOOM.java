package lockTest;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
	
	
	// -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
	static class OMMObject {

	}

	public static void main(String[] args) {
//		java.lang.OutOfMemoryError: Java heap space
//		ommObjectError();
		
//		java.lang.StackOverflowError
		getStaticError();
	}
	
	public static void ommObjectError() {
		List<OMMObject> list = new ArrayList<>();
		while (true) {
			list.add(new OMMObject());
		}
	}
	
	public static void getStaticError() {
		while(true) {
			getStaticError();
			new OMMObject();
		}
	}
}
