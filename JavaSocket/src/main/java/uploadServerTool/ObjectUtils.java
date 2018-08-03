package uploadServerTool;

class ObjectUtils {
	public static <T> T get(Object obj, Class<T> cls) {
		if ((obj != null) && (cls.isInstance(obj))) {
			return (T) obj;
		}
		return null;
	}

	public static <T> T baseValue(String value, Class<T> cls) {
		T obj = baseValue0(value, cls);
		if (obj == null) {
			return defualtValue(cls);
		}
		return obj;
	}

	public static <T> T baseValue0(String value, Class<T> cls) {
		if (value == null) {
			return null;
		}
		try {
			if ((Byte.TYPE.isAssignableFrom(cls)) || (Byte.class.isAssignableFrom(cls))) {
				return (T) Byte.valueOf(value);
			}
			if ((Short.TYPE.isAssignableFrom(cls)) || (Short.class.isAssignableFrom(cls))) {
				return (T) Short.valueOf(value);
			}
			if ((Integer.TYPE.isAssignableFrom(cls)) || (Integer.class.isAssignableFrom(cls))) {
				return (T) Integer.valueOf(value);
			}
			if ((Long.TYPE.isAssignableFrom(cls)) || (Long.class.isAssignableFrom(cls))) {
				return (T) Long.valueOf(value);
			}
			if ((Float.TYPE.isAssignableFrom(cls)) || (Float.class.isAssignableFrom(cls))) {
				return (T) Float.valueOf(value);
			}
			if ((Double.TYPE.isAssignableFrom(cls)) || (Double.class.isAssignableFrom(cls))) {
				return (T) Double.valueOf(value);
			}
			if ((Boolean.TYPE.isAssignableFrom(cls)) || (Boolean.class.isAssignableFrom(cls))) {
				return (T) Boolean.valueOf(value);
			}
			if (String.class.isAssignableFrom(cls)) {
				return (T) value;
			}
		} catch (Exception localException) {
		}
		return null;
	}

	public static <T> T defualtValue(Class<T> cls) {
		if ((Byte.TYPE.isAssignableFrom(cls)) || (Byte.class.isAssignableFrom(cls))) {
			return (T) Byte.valueOf((byte) 0);
		}
		if ((Short.TYPE.isAssignableFrom(cls)) || (Short.class.isAssignableFrom(cls))) {
			return (T) Short.valueOf((short) 0);
		}
		if ((Integer.TYPE.isAssignableFrom(cls)) || (Integer.class.isAssignableFrom(cls))) {
			return (T) Integer.valueOf(0);
		}
		if ((Long.TYPE.isAssignableFrom(cls)) || (Long.class.isAssignableFrom(cls))) {
			return (T) Long.valueOf(0L);
		}
		if ((Float.TYPE.isAssignableFrom(cls)) || (Float.class.isAssignableFrom(cls))) {
			return (T) Float.valueOf(0.0F);
		}
		if ((Double.TYPE.isAssignableFrom(cls)) || (Double.class.isAssignableFrom(cls))) {
			return (T) Double.valueOf(0.0D);
		}
		if ((Boolean.TYPE.isAssignableFrom(cls)) || (Boolean.class.isAssignableFrom(cls))) {
			return (T) Boolean.valueOf(false);
		}
		return null;
	}
}
