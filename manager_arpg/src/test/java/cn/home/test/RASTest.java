package cn.home.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.tribes.util.Arrays;

import xzj.core.util.RSAUtils;
import cn.saturn.web.code.action.LoginAction;

public class RASTest {

	public static void main(String[] args) {

		String url = "key=123&a=abc";

		byte[] protobufData = url.getBytes();
		try {
			System.out.println("s:" + Arrays.toString(protobufData));
			protobufData = RSAUtils.encryptByPublicKey(protobufData);
			System.out.println("e:" + Arrays.toString(protobufData));
			protobufData = RSAUtils.decryptByPrivateKey(protobufData);
			System.out.println("d:" + Arrays.toString(protobufData));
			System.out.println(new String(protobufData));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
