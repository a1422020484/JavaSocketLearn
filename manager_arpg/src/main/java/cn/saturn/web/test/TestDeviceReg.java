package cn.saturn.web.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.saturn.web.utils.MD5Util;

public class TestDeviceReg {
	
	private final static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	private final static String MD5_KEY = "gmcs_test_key";
	
	public static void main(String[] args) {
		String str = "http://localhost:8080/home/device/reg?deviceUI=";
		String device ="test";
		String OS ="android";
		String res = format.format(new Date());
		String MD5 = MD5Util.MD5(device + OS + res + MD5_KEY);
		
		System.out.println(str+device+"&OS="+OS+"&reg_time="+res+"&MD5="+MD5);
	}
}
