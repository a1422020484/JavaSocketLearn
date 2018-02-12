package cn.home.test;

import java.util.HashMap;
import java.util.Map;

import cn.saturn.web.code.action.LoginAction;

public class UrlTest {

	public static void main(String[] args) {

		String url = "key=123&a=abc";

		Map<String, String> params = new HashMap<String, String>();
		LoginAction.UrlDecode(url, params);

		System.out.println(url + " " + params);
	}

}
