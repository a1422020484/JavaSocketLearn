package cn.saturn.web.model.auto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import zyt.utils.Utils;

/**
 * 
 *
 * @param <T>
 *            必须填入DAO类型, 否则没法自动查找带自定义类的函数 无法使用泛型类型作为查找对象
 */
public abstract class ModelManager<T extends IModel, D extends ModelDAO<T>> {

	protected abstract D getDAO();

	private final AtomicLong idMaker = new AtomicLong(); // id生成器

	protected void init() {
		// 读取最大值
		D dao = this.getDAO();
		if (dao != null) {
			Long max = dao.getMaxId();
			idMaker.set(max != null ? max.longValue() : 0L);
		}
	}

	/**
	 * 创建一个新的ID
	 * 
	 * @return
	 */
	public long newId() {
		return idMaker.incrementAndGet();
	}

	@SuppressWarnings("unchecked")
	protected T get(Class<?>[] types, Object... args) {
		// 反射获取调用函数
		try {

			D dao = this.getDAO();
			Class<?> clazz = dao.getClass();

			// 获取对应函数
			Method method = clazz.getMethod("get", types);
			if (method == null) {
				return null;
			}

			// 检测返回值
			if (method.getReturnType() == null) {
				return null;
			}

			// 执行处理
			Object retObj = method.invoke(dao, args);
			return (T) retObj;
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("nofind: getList->" + Arrays.toString(types));
		}

		return null;
	}

	/**
	 * 通过参数调用DAO的getList
	 * 
	 * @param types
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getListByDAO(Class<?>[] types, Object... args) {
		// 反射获取调用函数
		try {

			D dao = this.getDAO();
			Class<?> clazz = dao.getClass();

			// 获取对应函数
			Method method = clazz.getMethod("getList", types);
			if (method == null) {
				return null;
			}

			// 检测返回值
			if (method.getReturnType() != List.class) {
				return null;
			}

			// 执行处理
			Object retObj = method.invoke(dao, args);
			return (List<T>) Utils.ObjectUtils.get(retObj, List.class);
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("nofind: getList->" + Arrays.toString(types));
		}

		return null;
	}

	// protected List<T> getList(Object... args) {
	// Class<?>[] types = GenericityUtils.createTypes(args);
	// return getList(types, args);
	// }

	protected List<T> getListByDAO() {
		return this.getListByDAO(new Class<?>[] {}, new Object[] {});
	}

	protected void insertByDAO(T model) {
		if (model.getId() <= 0L) {
			model.setId(this.newId());
		}
		// getDAO().insertOrUpdate((T)model);
		ReflectUtils.invoke(getDAO(), "insertOrUpdate", new Object[] { model });
	}

	protected boolean updateByDAO(T model) {
		if (model.getId() <= 0L) {
			model.setId(this.newId());
		}
		// (this.getDAO()).insertOrUpdate(model);
		ReflectUtils.invoke(getDAO(), "insertOrUpdate", new Object[] { model });
		return true;
	}

	// protected abstract void insertByDAO(T model);

	// protected abstract boolean updateByDAO(T model);

	// 不支持泛型调用DAO
	// protected abstract boolean insertOrUpdateByDAO(T model);

	protected void removeByDAO(T model) {
		if (model == null) {
			return;
		}
		this.removeByDAO(model.getId());
	}

	protected void removeByDAO(long id) {
		this.getDAO().remove(id);
	}

}

class ReflectUtils {

	// public static Object clone(Object obj) {
	//
	// return null;
	// }

	public static Object copy(Object res) {
		// 调用对象的clone, 保护函数.
		return create(res, res.getClass());
	}

	/**
	 * 复制对象
	 * 
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	public static <T extends P, P> T create(P res, Class<T> cls) {
		try {
			T obj = (T) cls.newInstance();
			copy(res, obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拷贝对象
	 * 
	 * @param res
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static <T extends P, P> boolean copy(P res, T obj) {
		try {
			copy0(res, obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 拷贝对象
	protected static <T extends P, P> T copy0(P res, T obj) throws Exception {
		// 读取源类
		Class<?> classType = res.getClass();
		// 遍历成员变量
		Field[] fields = classType.getDeclaredFields();
		for (Field filed : fields) {
			if (Modifier.isStatic(filed.getModifiers())) {
				continue; // 是静态数据
			}
			if (Modifier.isTransient(filed.getModifiers())) {
				continue; // 临时数据, 跳过不保存
			}

			// 生成获取和设置函数名
			String firstLetter = filed.getName().substring(0, 1).toUpperCase(); // 首字母大写
			String otherString = filed.getName().substring(1);
			String getMethodName = "get" + firstLetter + otherString;
			String setMethodName = "set" + firstLetter + otherString;
			// 通过反射获取函数
			Method getMethod = null;
			Method setMethod = null;
			try {
				getMethod = classType.getMethod(getMethodName, new Class[] {});
				setMethod = classType.getMethod(setMethodName, new Class[] { filed.getType() });
			} catch (Exception e) {
				// e.printStackTrace();
			}

			// 读取变量
			Object value = null;
			if (getMethod != null) {
				value = getMethod.invoke(res, new Object[] {}); // 通过函数获取
			} else {
				value = filed.get(res); // 直接获取
			}
			// 设置变量
			if (setMethod != null) {
				setMethod.invoke(obj, new Object[] { value }); // 通过函数设置
			} else {
				filed.set(obj, value); // 直接设置
			}
		}
		return obj;
	}

	/**
	 * 获取类型函数的返回值
	 * 
	 * @param clazz
	 * @param methodName
	 * @param clazzs
	 * @return
	 */
	public static Class<?> getMethodReturnType(Class<?> clazz, String methodName, Class<?>[] clazzs) {
		try {
			Method method = clazz.getMethod(methodName, clazzs);
			if (method == null) {
				return null; // 木有函数
			}
			return method.getReturnType();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取类的返回值(第一个)
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static Class<?> getMethodReturnType(Class<?> clazz, String methodName) {
		try {
			Method[] methods = clazz.getDeclaredMethods();
			int count = (methods != null) ? methods.length : 0;
			for (int i = 0; i < count; i++) {
				Method method = methods[i];
				if (method.getName().equals(methodName)) {
					return method.getReturnType();
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过函数获取变量: getXXX()
	 * 
	 * @param clazz
	 * @param filedName
	 * @return
	 */
	public static Object getValueOfMethod(Class<?> clazz, String filedName) {
		// 拼裝get函数名
		String firstLetter = filedName.substring(0, 1).toUpperCase(); // 首字母大写
		String otherString = filedName.substring(1);

		String getMethodName = "get" + firstLetter + otherString;
		try {
			Method method = clazz.getMethod(getMethodName, new Class[] {});
			if (method == null) {
				return null; // 木有函数
			}
			Object value = method.invoke(clazz, new Object[] {}); // 通过函数获取
			return value;
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return null;
	}

	/**
	 * 调用函数
	 * 
	 * @param object
	 * @param methodName
	 * @param args
	 * @param clazzs
	 * @return
	 */
	public static Object invoke(Object object, String methodName, Object[] args, Class<?>[] clazzs) {
		try {
			Class<?> clazz = object.getClass();
			Method method = clazz.getMethod(methodName, clazzs);
			if (method == null) {
				return null; // 木有函数
			}
			Object value = method.invoke(object, args); // 通过函数获取
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object invoke(Object object, String methodName, Object[] args) {
		Class<?>[] clazzs = createTypes(args);
		return invoke(object, methodName, args, clazzs);
	}

	public static Class<?>[] createTypes(Object... args) {
		// 读取参数数量
		int argCount = (args != null) ? args.length : 0;
		if (argCount <= 0) {
			return new Class<?>[] {};
		}
		// 遍历生成
		Class<?>[] types = new Class<?>[argCount];
		for (int i = 0; i < argCount; i++) {
			Object arg = args[i];
			if (arg == null) {
				types[i] = Object.class;
				continue;
			}
			types[i] = arg.getClass(); // 读取类型
		}

		return types;
	}

}