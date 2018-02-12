package cn.saturn.web.controllers.power;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

public class VindicatorIp extends zyt.spring.component.resource.Resource {
	private List<String> values = new ArrayList<>();

	public VindicatorIp() {
		super();
		this.reload();
	}

	@Override
	protected boolean reload0() {
		// boolean result = super.reload0();
		// return result;

		return LogType.reload0(this);
	}

	@Override
	protected void reload(InputStream is) throws Exception {
		// 读取数据
		int size = is.available();
		byte[] buffer = new byte[size];
		size = is.read(buffer, 0, size);

		// 转化成字符串
		String jsonStr = new String(buffer, "UTF-8");
		// System.out.println("jsonStr:" + jsonStr);

		// 读取数据
		List<String> types = JSON.parseArray(jsonStr, String.class);
		this.values.clear();
		this.values.addAll(types);
		// System.out.println("types:" + types);
	}

	@Override
	public String getResourceFile() {
		// return "/config.properties";
		return "//resource/vindicatorIp.json";
	}

	public List<String> getValues() {
		return values;
	}

	public String[] getValues0() {
		return values.toArray(new String[] {});
	}

	public boolean find(String value) {
		int count = (values != null) ? values.size() : 0;
		for (int i = 0; i < count; i++) {
			String str = values.get(i);
			if (StringUtils.isEmpty(str)) {
				continue;
			}

			// 判断是否是IP段
			int indexOf = str.indexOf('-');
			if (indexOf >= 0) {
				String startIp = str.substring(0, indexOf);
				String endIp = str.substring(indexOf + 1);

				// System.out.println(startIp + " " + endIp);

				// 判断是否在范围内
				int a = value.compareTo(startIp);
				int b = value.compareTo(endIp);
				if (a >= 0 && b <= 0) {
					return true;
				}

				// 跳过了
				continue;
			}

			// 直接判断ip
			if (str.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String[] vindicatorIps = {
				// 自家ip
				"119.137.34.1-119.137.34.255",
				// 联想
				"124.126.118.153",
				// UC IP白名单
				"14.152.64.73", "14.152.64.74", "183.232.46.73", "183.232.46.74", "122.13.84.73", "122.13.84.74",

				// 9游
				"221.228.102.128", "221.228.102.80", "221.228.102.96", "112.25.246.128", "211.103.25.32", "211.103.26.112", "153.35.88.128", "153.35.91.112", "221.6.115.224",
				// 9游 IP白名单段
				"221.228.102.129-221.228.102.254", "221.228.102.81-221.228.102.94", "221.228.102.97-221.228.102.126", "112.25.246.129-112.25.246.254", "211.103.25.33-211.103.25.62",
				"211.103.26.113-211.103.26.126", "153.35.88.129-153.35.88.254", "153.35.91.113-153.35.91.126", "221.6.115.225-221.6.115.254",

				// 华为
				"183.62.195.194", };
		String jsonStr = JSON.toJSONString(vindicatorIps);

		VindicatorIp vindicatorIp = new VindicatorIp();
		List<String> types = JSON.parseArray(jsonStr, String.class);
		vindicatorIp.values.addAll(types);

		String ip = "119.137.34.198";
		System.out.println(vindicatorIp.find(ip));

	}
}
