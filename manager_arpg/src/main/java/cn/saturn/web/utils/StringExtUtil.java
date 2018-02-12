package cn.saturn.web.utils;

import java.util.List;

public class StringExtUtil {
	/**
	 * String is null && length >0<br>
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(String arg) {
		return arg == null || arg.length() == 0;
	}

	/**
	 * String not null && length >0<br>
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(String arg) {
		return arg != null && arg.length() != 0;
	}

	/**
	 * 过滤掉list 中特定的元素 <br>
	 * 
	 * @param old
	 * @param args
	 * @return
	 */
	public static List<String> filter(List<String> old, String... args) {
		for (String arg : args) {
			if (old.contains(arg))
				old.remove(arg);
		}

		return old;
	}

	/**
	 * 将string 切割 变为 List<{@link String}> <br>
	 * 
	 * @param arg
	 * @param regex
	 * @return
	 */
	public static List<String> toList(String arg, String regex) {
		return ListExtUtil.arrayToList(arg.split(regex));
	}
	
	public static String[] toStrings(String args,String regex){
		if(args.isEmpty()) return new String[0];
		return args.split(regex);
	}
}
