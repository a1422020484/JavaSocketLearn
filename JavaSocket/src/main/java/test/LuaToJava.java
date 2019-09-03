package test;

import java.util.Date;

public class LuaToJava {

	public int name = 1;
	public String address = "shanghai";

	public static void main(String[] args) {
		int arg1 = 1;
		int arg2 = 2;
		LuaToJava ss = new LuaToJava();
		ss.getAddd();
		ss.getAddd2();
		int arg3 = arg1 + arg2;
		
		Date d = new Date();
	}

	private int getAddd() {
		return 123;
	}

	private int getAddd2() {
		return 123;
	}
}
