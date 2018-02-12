package cn.saturn.web.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * list 扩展工具 <br>
 * 
 * @author rodking
 *
 */
public class ListExtUtil {

	/**
	 * 将 T[] 转化为List<T> <br>
	 * if T[] == null return new ArrayList<>();
	 * 
	 * @param arrays
	 * @return
	 */
	public static <T> List<T> arrayToList(T[] arrays) {
		List<T> list = new ArrayList<>();
		if (arrays != null)
			list.addAll(Arrays.asList(arrays));
		return list;
	}

	/**
	 * 将 list<T> 转化为 T[]<br>
	 * 如果为空 返回null <br>
	 * 
	 * @param list
	 * @param a
	 * @return
	 */
	public static <T> T[] listToArray(List<T> list, T[] a) {
		if (list.isEmpty())
			return null;
		return list.toArray(a);
	}

}