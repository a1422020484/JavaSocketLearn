package nettyServer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nettyServer.item.Award;
import nettyServer.util.annotation.NotNull;

/**
 * 字符串转换工具集
 *
 * @author yangxp
 */
public class ToStringUtils {
	
	/**
	 * 将列表转换为字符串,例separator为',',转换后的格式: ss,ss,ss
	 * 
	 * @param list 列表
	 * @param separator 分隔符
	 * @return 转换后的字符串,转换失败时返回""
	 */
	@NotNull
	public static <T> String listToString(List<T> list, String separator) {
		if (list == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder(256);
		for (T t : list) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			sb.append(t.toString());
		}
		return sb.toString();
	}
	
	/** 
     * 计算位数 
     * @param str 
     * @return 
     */  
    public static int calculatePlaces(String str)  
    {  
        int m = 0;  
        char arr[] = str.toCharArray();  
        for(int i=0;i<arr.length;i++)  
        {  
            char c = arr[i];  
            if((c >= 0x0391 && c <= 0xFFE5))  //中文字符  
            {  
                m = m + 2;  
            }  
            else if((c>=0x0000 && c<=0x00FF)) //英文字符  
            {  
                m = m + 1;  
            }  
        }  
        return m;  
    }  
	
    /**
     * 将字符串转换为整数列表,例"1;2",separator为';',转换后的格式:1,2
     * @param str
     * @param separator
     * @return
     */
    public static List<Integer> parseListInt(String str) {
    	return parseListInt(str, ";");
    }
    public static List<Integer> parseListInt(String str, String separator) {
    	List<Integer> list = new ArrayList<>();
    	try {
    		if(str == null || separator == null || str.isEmpty()) {
    			return list;
    		}
    		String[] arrStr = str.split(separator);
    		for(String s : arrStr) {
    			list.add(Integer.parseInt(s));
    		}
    	} catch(Exception e) {
    		GameUtils.RuntimeLog.error("字符串转换为List<Integer>的时候报错！str:{}, separator:{}", str, separator);
    		list.clear();
    	}
    	return list;
    }
    
    /**
     * 将字符串转换为整数列表,例"1.0;2.0",separator为';',转换后的格式:1.0,2.0
     * @param str
     * @param separator
     * @return
     */
    public static List<Float> parseListFloat(String str) {
    	return parseListFloat(str, ";");
    }
    public static List<Float> parseListFloat(String str, String separator) {
    	List<Float> list = new ArrayList<>();
    	try {
    		if(str == null || separator == null || str.isEmpty()) {
    			return list;
    		}
    		String[] arrStr = str.split(separator);
    		for(String s : arrStr) {
    			list.add(Float.parseFloat(s));
    		}
    	} catch(Exception e) {
    		GameUtils.RuntimeLog.error("字符串转换为List<Float>的时候报错！str:{}, separator:{}", str, separator);
    		list.clear();
    	}
    	return list;
    }
    
    /**
     * 字符串转换为Award对象
     * @param className Model类名
     * @param modelId Model主键
     * @param itemIds 物品id字符串 例如:1;2
     * @param itemNums 物品数量字符串 例如100;200
     * @return
     */
    public static Award parseStringToAward(String className, int modelId, String itemIds, String itemNums) {
    	Award award = new Award();
    	List<Integer> itemIdsList = ToStringUtils.parseListInt(itemIds);
		List<Integer> itemNumsList = ToStringUtils.parseListInt(itemNums);
		int size = itemIdsList.size();
		if(size != itemNumsList.size()) {
			GameUtils.RuntimeLog.error("字符串转换为物品时，发现itemIds和ItemNums不对称!请查阅模版!className:{}, modelId:{}", className, modelId);
			size = Math.min(size, itemNumsList.size());
		}
		if(size == 0) {
			GameUtils.RuntimeLog.error("字符串转换为物品时，发现itemIds和ItemNums配置错误!请查阅模版!className:{}, modelId:{}", className, modelId);
		}
		for(int i = 0; i < size; i++) {
			award.addItem(itemIdsList.get(i), itemNumsList.get(i));
		}
		return award;
    }
    
    /**
     * 具有对应关系的两个字符串转换为Map对象
     * @param modelId Model主键
     * @param str1 字符串1 例如:1;2
     * @param str2 字符串2 例如100;200
     * @return
     */
    public static Map<Integer, Integer> parseStringToMap(int modelId, String str1, String str2) throws Exception {
    	Map<Integer, Integer> maps = new HashMap<>();
    	List<Integer> list1 = ToStringUtils.parseListInt(str1);
		List<Integer> list2 = ToStringUtils.parseListInt(str2);
		int size = list1.size();
		if(size != list2.size()) {
			throw new Exception();
		}
		for(int i = 0; i < size; i++) {
			maps.put(list1.get(i), list2.get(i));
		}
		return maps;
    }
    
    public static Map<Integer, Integer> parseStringToMap(String str) throws Exception {
    	Map<Integer, Integer> map = new LinkedHashMap<>();
    	String[] split1 =  str.split("\\|");
    	for(String s : split1) {
    		String[] split2 = s.split(";");
    		if(split2.length == 2) {
    			int key = Integer.parseInt(split2[0]);
    			int value = Integer.parseInt(split2[1]);
    			map.put(key, value);
    		} else {
    			throw new Exception("字符串配置错误!str:" + str);
    		}
    	}
    	return map;
    }
    
    public static void main(String[] args) throws Exception {
    	Map<Integer, Integer> maps = parseStringToMap("5;3|15;4|25;4|60;5|85;6|110;7");
    	int posGrade = 30;
    	int addValue = 0;
		int preKey = 0;
    	for(Entry<Integer, Integer> en : maps.entrySet()) {
			int key = en.getKey();
			int value = en.getValue();
			int temp = key - preKey;
			if(posGrade > key) {
				addValue += temp * value;
				preKey = key;
			} else {
				addValue += (posGrade - preKey) * value;
				break;
			}
		}
    	System.out.println(addValue);
	}
    
    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String toUpperCase4Index(String string) {
        char[] methodName = string.toCharArray();
        methodName[0] = toUpperCase(methodName[0]);
        return String.valueOf(methodName);
    }
    
    /**
     * 字符转成大写
     *
     * @param chars
     * @return
     */
    public static char toUpperCase(char chars) {
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        return chars;
    }
}
