package nettyServer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 简单工具类
 *
 * @author yangxp
 */
public final class Tool {

	/**
	 * @return UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * @param key
	 * @param clazz
	 * @return 系统属性
	 */
	public static <T> T getSystemProperty(String key, Class<T> clazz) {
		return convertBaseType(System.getProperty(key), clazz);
	}

	/**
	 * <ul>
	 * <li>如果obj是null,那么返回null</li>
	 * <li>如果obj是String类型,并且T是基本类型,那么将obj的值转换为基本类型</li>
	 * <li>如果obj不是String类型,那么直接将obj转换成T类型</li>
	 * <li></li>
	 * </ul>
	 *
	 * @param obj
	 * @param cla
	 * @return 转换结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertType(Object obj, Class<T> cla) {
		if (obj instanceof String)
			return convertBaseType((String) obj, cla);
		else
			return (T) obj;
	}

	/**
	 * <ul>
	 * <li>如果value是null,那么返回null</li>
	 * <li>如果T是基本类型,那么将value转换为基本类型</li>
	 * </ul>
	 *
	 * @param value
	 *            字符串
	 * @param cla
	 *            待转换的类型
	 * @return 转换结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertBaseType(String value, Class<T> cla) {
		if (value == null)
			return null;
		if (byte.class.isAssignableFrom(cla) || Byte.class.isAssignableFrom(cla))
			return (T) Byte.valueOf(value);
		else if (short.class.isAssignableFrom(cla) || Short.class.isAssignableFrom(cla))
			return (T) Short.valueOf(value);
		else if (int.class.isAssignableFrom(cla) || Integer.class.isAssignableFrom(cla))
			return (T) Integer.valueOf(value);
		else if (long.class.isAssignableFrom(cla) || Long.class.isAssignableFrom(cla))
			return (T) Long.valueOf(value);
		else if (float.class.isAssignableFrom(cla) || Float.class.isAssignableFrom(cla))
			return (T) Float.valueOf(value);
		else if (double.class.isAssignableFrom(cla) || Double.class.isAssignableFrom(cla))
			return (T) Double.valueOf(value);
		else if (boolean.class.isAssignableFrom(cla) || Boolean.class.isAssignableFrom(cla))
			return (T) Boolean.valueOf(value);
		else
			return null;
	}

	/**
	 * 检查两个参数是否是相同类型 <li>c1==c2</li> <li>
	 * c1.isAssignableFrom(c2)或c2.isAssignableFrom(c1)</li> <li>
	 * c1是c2的封装类型或c2是c1的封装类型</li>
	 *
	 * @param c1
	 *            Class类型1
	 * @param c2
	 *            Class类型2
	 * @return 两个参数是否是相同类型
	 */
	public static boolean isSameType(Class<?> c1, Class<?> c2) {
		if (c1 == c2)
			return true;
		if (c1.isAssignableFrom(c2) || c2.isAssignableFrom(c1))
			return true;
		// 匹配封装类型
		if ((Byte.class.isAssignableFrom(c1) && byte.class.isAssignableFrom(c2)) || (byte.class.isAssignableFrom(c1) && Byte.class.isAssignableFrom(c2)))
			return true;
		if ((Boolean.class.isAssignableFrom(c1) && boolean.class.isAssignableFrom(c2)) || (boolean.class.isAssignableFrom(c1) && Boolean.class.isAssignableFrom(c2)))
			return true;
		if ((Character.class.isAssignableFrom(c1) && char.class.isAssignableFrom(c2)) || (char.class.isAssignableFrom(c1) && Character.class.isAssignableFrom(c2)))
			return true;
		if ((Short.class.isAssignableFrom(c1) && short.class.isAssignableFrom(c2)) || (short.class.isAssignableFrom(c1) && Short.class.isAssignableFrom(c2)))
			return true;
		if ((Integer.class.isAssignableFrom(c1) && int.class.isAssignableFrom(c2)) || (int.class.isAssignableFrom(c1) && Integer.class.isAssignableFrom(c2)))
			return true;
		if ((Long.class.isAssignableFrom(c1) && long.class.isAssignableFrom(c2)) || (long.class.isAssignableFrom(c1) && Long.class.isAssignableFrom(c2)))
			return true;
		if ((Float.class.isAssignableFrom(c1) && float.class.isAssignableFrom(c2)) || (float.class.isAssignableFrom(c1) && Float.class.isAssignableFrom(c2)))
			return true;
		if ((Double.class.isAssignableFrom(c1) && double.class.isAssignableFrom(c2)) || (double.class.isAssignableFrom(c1) && Double.class.isAssignableFrom(c2)))
			return true;
		// default
		return false;
	}

	/**
	 * 例:<br/>
	 * 参数是:java.lang.String, java.lang.String<br/>
	 * 那么返回:String, String
	 *
	 * @param classes
	 * @return 类的简单描述
	 */
	public static String getClassesDesc(Class<?>[] classes) {
		StringBuilder msg = new StringBuilder(16);
		int len = classes.length;
		for (int i = 0; i < len; i++) {
			if (i > 0)
				msg.append(",");
			msg.append(classes[i].getSimpleName());
		}
		return msg.toString();
	}

	/**
	 * @param f
	 * @return 字段所在类的读方法(get or is), 如果不存在此方法, 返回null
	 */
	public static Method methodReader(Field f) {
		try {
			return f.getDeclaringClass().getMethod(methodReaderName(f), new Class<?>[0]);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param f
	 * @return 字段所在类的读方法名(get or is)
	 */
	public static String methodReaderName(Field f) {
		String fName = f.getName();
		StringBuilder builder = new StringBuilder();
		// 如果字段是boolean,需要特殊处理
		if (Boolean.class.isAssignableFrom(f.getType()) || boolean.class.isAssignableFrom(f.getType())) {
			String name = f.getName();
			// 如果不是以is开头,或长度不超过2位,或第三位不是大写,都在前面加is并且首字母大写
			if (!(name.startsWith("is") && name.length() > 2 && Character.isUpperCase(name.charAt(2)))) {
				builder.append("is");
				builder.append(fName.substring(0, 1).toUpperCase());
				builder.append(fName.substring(1));
			} else {
				builder.append(name);
			}
		} else {
			builder.append("get");
			builder.append(fName.substring(0, 1).toUpperCase());
			builder.append(fName.substring(1));
		}
		return builder.toString();
	}
}
