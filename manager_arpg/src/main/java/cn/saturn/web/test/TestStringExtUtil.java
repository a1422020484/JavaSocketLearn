package cn.saturn.web.test;

import java.util.ArrayList;
import java.util.List;

import cn.saturn.web.utils.StringExtUtil;

public class TestStringExtUtil {
	public static void main(String[] args) {
		List<String> old = new ArrayList<>();
		old.add("rodking");
		old.add("test");
		old.add("123");

		System.out.println(old);

		old = StringExtUtil.filter(old, "rodking", "123", "22");

		System.out.println(old);
	}
}
