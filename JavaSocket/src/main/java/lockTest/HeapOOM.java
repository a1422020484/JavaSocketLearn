package lockTest;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
	static class OMMObject {

	}

	public static void main(String[] args) {
		List<OMMObject> list = new ArrayList<>();
		while (true) {
			list.add(new OMMObject());
		}
	}
}
