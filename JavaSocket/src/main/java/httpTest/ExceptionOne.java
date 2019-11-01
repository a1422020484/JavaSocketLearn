package httpTest;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionOne {
	
	private String name = "";
	private String address;

	public static void main(String[] args) {
		ExceptionOne on = new ExceptionOne();
		System.out.println(on.name);
		System.out.println(on.address);
		
		
		int a = 5;
		int b = 6;
		int c = 7;
		try {
			int f = a / (b / c);
		} catch (Exception e) {
			System.out.println(getErrorMessage(e));
			
		}

	}

	public static String getErrorMessage(Exception e) {
		if (null == e) {
			return "";
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
