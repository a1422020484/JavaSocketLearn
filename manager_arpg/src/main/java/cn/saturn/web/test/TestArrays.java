package cn.saturn.web.test;

import java.util.List;

import cn.saturn.web.utils.ListExtUtil;

public class TestArrays {
	public static void main(String[] args) {
		String[] awards = new String[] { "tt", "123" };
		List<String> awardList = ListExtUtil.arrayToList(awards);

		awardList.add("test");
		awardList.add("chao");
		System.out.println(awardList);
	}
}
