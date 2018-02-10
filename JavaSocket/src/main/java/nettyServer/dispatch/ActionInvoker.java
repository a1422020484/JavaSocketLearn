package nettyServer.dispatch;

import java.lang.reflect.Method;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import nettyServer.dispatch.annotation.Action;
import nettyServer.util.annotation.NotNull;
import nettyServer.util.param.ParamReader;
import nettyServer.util.param.ProtobufReflectReader;

/**
 * Action执行类
 *
 * @author zuojie.x
 */
public class ActionInvoker {
	Object actionObj; // 原对象
	Class<?> actionClass; // 方法所在的class
	Action annotation; // 注解
	String actionDesc;

	Method action;

	// reflectasm
	MethodAccess access;
	int accessIndex;

	ParamReader paramReader;

	static Class<?>[] methodParameterTypes = new Class[] { byte[].class };

	/**
	 * @param actionObj
	 *            方法所在的原对象
	 * @param action
	 *            用来执行的方法
	 */
	ActionInvoker(Object actionObj, Method action) {
		Class<?>[] params = action.getParameterTypes();
		if (params.length > 1) {
			throw new RuntimeException("最多只能有一个参数,并且是MessageLite的子类,参见protobuf " + actionDesc);
		} else if (params.length == 1) {
			// 检查参数类型是否正确
			Class<?> paramClass = params[0];
			if (!MessageLite.class.isAssignableFrom(paramClass)) {
				throw new Error("参数必须是MessageLite的子类,参见protobuf " + actionDesc);
			}
			try {
				paramClass.getMethod("parseFrom", methodParameterTypes);
			} catch (Exception e) {
				throw new Error("参数类没有parseFrom(byte[] data)方法,请检查生成类,支持的protobuf版本2.6.1 " + actionDesc);
			}
		}
		this.actionObj = actionObj;
		this.action = action;
		actionClass = actionObj.getClass();
		annotation = action.getAnnotation(Action.class);
		actionDesc = ActionInvoker.generateDesc(actionClass.getSimpleName(), action.getName(), params);
		access = MethodAccess.get(actionObj.getClass());
		accessIndex = access.getIndex(action.getName(), params);
		paramReader = new ProtobufReflectReader(action);
	}

	/**
	 * @return 执行的返回结果
	 */
	Object execute() {
		Request req = ThreadLocalRequest.get();
		try {
			Object[] p = req.getParams();
			return access.invoke(actionObj, accessIndex, p);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @return 执行的返回结果
	 */
	public Object execute(Object[] p) {
		try {
			return access.invoke(actionObj, accessIndex, p);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @return 注解Action的id值
	 */
	int getActionId() {
		return annotation.id();
	}

	/**
	 * @return 获取控制器的Action注释
	 */
	@NotNull
	public Action getActionAnnotation() {
		return annotation;
	}

	/**
	 * @return 每个Action的描述, 见 {@link ActionInvoker#generateDesc(String, String, Object[])}
	 */
	String getActionDesc() {
		return actionDesc;
	}

	/**
	 * 从缓冲区读取数据解析出参数列表
	 *
	 * @param buf
	 * @return 解析得到的参数列表
	 */
	public Object[] parseArguments(ByteBuf buf) {
		return paramReader.read(buf);
	}

	/**
	 * 从缓冲区读取数据解析出参数列表
	 *
	 * @param data
	 * @return 解析得到的参数列表
	 */
	public Object[] parseArguments(byte[] data) {
		return paramReader.read(data);
	}

	private static String getObjectSimpleName(Object obj) {
		String simpleName = "";
		Class<?> cla = null;
		if (obj instanceof Class<?>)
			cla = (Class<?>) obj;
		else
			cla = obj.getClass();
		if (byte.class.isAssignableFrom(cla))
			simpleName = "Byte";
		else if (boolean.class.isAssignableFrom(cla))
			simpleName = "Boolean";
		else if (char.class.isAssignableFrom(cla))
			simpleName = "Character";
		else if (short.class.isAssignableFrom(cla))
			simpleName = "Short";
		else if (int.class.isAssignableFrom(cla))
			simpleName = "Integer";
		else if (long.class.isAssignableFrom(cla))
			simpleName = "Long";
		else if (float.class.isAssignableFrom(cla))
			simpleName = "Float";
		else if (double.class.isAssignableFrom(cla))
			simpleName = "Double";
		else
			simpleName = cla.getSimpleName();

		return simpleName;
	}

	/**
	 * 生成描述,例:pecan.ActionExecutor.generalKey(String,String,Class[])
	 *
	 * @param actionClassName
	 * @param actionName
	 * @param params
	 * @return 生成的描述
	 */
	protected static String generateDesc(String actionClassName, String actionName, Object[] params) {
		StringBuilder builder = new StringBuilder(128);
		builder.append(actionClassName).append(".");
		builder.append(actionName).append("(");
		int i = 0;
		for (Object obj : params) {
			if (i++ > 0)
				builder.append(",");
			builder.append(getObjectSimpleName(obj));
		}
		builder.append(")");
		return builder.toString();
	}

	@Override
	public String toString() {
		return actionDesc;
	}
}
