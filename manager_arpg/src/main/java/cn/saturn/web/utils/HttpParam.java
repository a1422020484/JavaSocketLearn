package cn.saturn.web.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * http param 参数工具类
 * 
 * @author rodking
 *
 */
public class HttpParam {
	/**
	 * 解析类似 xxx=xx&xx=xx&xxx=xx <br>
	 * 封装为一个 map<string,string>();<br>
	 * 
	 * @param paramStr
	 * @return
	 */
	public static Map<String, String> parse(String paramStr) {
		Map<String, String> paramMap = new HashMap<>();
		String strs[] = paramStr.split("&");
		for (String str : strs) {
			String[] earchStr = str.split("=");
			if (earchStr.length == 2)
				paramMap.put(earchStr[0], earchStr[1]);
		}

		return paramMap;

	}
}
