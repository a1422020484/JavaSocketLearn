package javaNetTest;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class GameConfig {

	public static String HostAddress;
	public static String ServerUrl;

	public static void main(String[] args) {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			throw new Error(e);
		}
		// HostAddress = inetAddress.getHostAddress();
		try {
			Enumeration<NetworkInterface> netIf = NetworkInterface.getNetworkInterfaces();
			while (netIf.hasMoreElements()) {
				NetworkInterface nwi = netIf.nextElement();
				Enumeration<InetAddress> address = nwi.getInetAddresses();
				while (address.hasMoreElements()) {
					InetAddress in = address.nextElement();
					if (in.isSiteLocalAddress()) {
						HostAddress = in.getHostAddress();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			HostAddress = "unknown";
		}
		ServerUrl = "http://" + HostAddress + ":" + 9527;
		
		
		
		// 有小米wifi时候 ： http://192.168.123.1:9527
		// 没有wifi时候 ： http://10.0.0.132:9527
		System.out.println(ServerUrl);
	}
	
	public void doSomething1() {
		
	}
}
