package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Test implements Serializable, Cloneable {

	public Test() {

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		if (null == getObject("test")) {
			System.out.println("1");
		}

		if (getObject("test") != null) {
			System.out.println("1");
		}

		try {

			Test test1 = Test.class.newInstance();
			// Test test2 = (Test) Constructor.class.newInstance(Test.class);
			Test test2 = (Test)Class.forName("test.Test").newInstance();
			
			Test test3 = new Test();
			Test test4 = (Test) test3.clone();

			ObjectOutputStream objo = new ObjectOutputStream(new FileOutputStream("Object1.txt"));
			objo.writeObject(new Test());

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Object1.txt"));
			Test test5 = (Test) ois.readObject();
			
			Test test6 = Test.class.getConstructor().newInstance();

			System.out.println(test1 + " - " + test2 + " - " + test3 + " - " + test4 + " - " + test5 + " - " + test6);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Integer getObject(String key) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		return map.get(key);
	}
}
