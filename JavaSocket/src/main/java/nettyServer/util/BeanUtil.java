package nettyServer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @author yangxp
 *
 */
public class BeanUtil {
	/**
	 * 通过反射获取对象的值
	 * 
	 * @param obj 对象
	 * @param properties 字段名
	 * @return 字段所对应的值
	 * @throws Exception
	 */
	public static Object getMethodValue(Object obj, String properties) throws Exception {
		String methodname = "get" + formatProperties(properties);
		Method method = obj.getClass().getMethod(methodname);
		Object keypropvalue = method.invoke(obj);
		return keypropvalue;
	}
	
	
	/**
	 * 通过反射设置对象的值
	 * 
	 * @param obj 对象
	 * @param properties 字段名
	 * @return 字段所对应的值
	 * @throws Exception
	 */
	public static void setMethodValue(Object obj, String properties,Object value) throws Exception {
		String methodname = "set" + formatProperties(properties);
		Field field = obj.getClass().getDeclaredField(properties);
		Method method = obj.getClass().getMethod(methodname,field.getType());
		method.invoke(obj,value);
	}
	
	
	// 首字母大写
	private static String formatProperties(String name) {
		if (name.length() > 1) {
			if (Character.isLowerCase(name.charAt(0))) {// 小写
				if (Character.isLowerCase(name.charAt(1))) {// 小写
					return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
				} else {
					return String.valueOf(name.charAt(0)).toLowerCase() + name.substring(1);
				}
			} else {
				return name;
			}
		}
		return String.valueOf(name.charAt(0)).toUpperCase();
	}
	
	
	public static <T> T getValue(T obj){
		
		return(T)obj;
	}
}
