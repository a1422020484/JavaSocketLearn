package cn.home.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanIpTest {
	
	public static boolean isIpv4(String ipAddress) {

		String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();

	}

public static void main(String[] args) {
	/*String ip1="1234546565";
	String ip2="123.454.65.65";
	String ip3="1234.54.65.65";
	String ip4="123.45.46.56";
	String ip5="12.34.54.65.65";
	String ip6=null;
	
	
	System.out.println(isIpv4(ip1)+"---"+ip1);
	System.out.println(isIpv4(ip2)+"---"+ip2);
	System.out.println(isIpv4(ip3)+"---"+ip3);
	System.out.println(isIpv4(ip4)+"---"+ip4);
	System.out.println(isIpv4(ip5)+"---"+ip5);
	System.out.println(isIpv4(ip6)+"---"+ip6);*/
	
	String arrayStr ="[1,12,13,14,15,31,32,34,35,36,39,41,42]";
	String[] array = arrayStr.replace("[", "").replace("]", "").split(",");
	System.out.println(array);
	
	
	
}	
	
}

