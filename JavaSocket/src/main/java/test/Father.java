package test;

public class Father {
	
	public final static String name = "";
	
	public static void main(String[] args) {
		Father f = new Son();
		System.out.println(f.getClass());
	}
}

class Son extends Father {

}
