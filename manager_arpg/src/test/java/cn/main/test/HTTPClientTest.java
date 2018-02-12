package cn.main.test;

import xzj.core.util.HttpUtils;

public class HTTPClientTest {
	public static void main(String[] args) {
		HttpUtils.create("http://203.195.254.101/pay/createorder")
//		.addParams(params)
//		.addParam("sign", sign)
        .post();
	}
}
