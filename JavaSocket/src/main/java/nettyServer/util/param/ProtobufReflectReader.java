package nettyServer.util.param;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Method;

/**
 * 反射实现,读取protobuf
 * 
 * @author yangxp
 */
public class ProtobufReflectReader extends ParamReader {

	Method actionMethod;
	Method protobufParseMethod;

	public ProtobufReflectReader(Method actionMethod) {
		this.actionMethod = actionMethod;
		Class<?>[] parameterTypes = this.actionMethod.getParameterTypes();
		if (parameterTypes.length > 0) {
			/*
			 * 调用protobuf类的表态方法 parseFrom(byte[] data);
			 */
			Class<?> clazz = parameterTypes[0];
			try {
				protobufParseMethod = clazz.getMethod("parseFrom", new Class[] { byte[].class });
			} catch (Exception e) {
				throw new RuntimeException("protobuf类没有parseFrom(byte[] data)方法,请检查生成类,支持的protobuf版本2.6.1"); 
			}
		}
	}

	@Override
	public Object[] read(ByteBuf buf) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object[] read(byte[] data) {
		if (protobufParseMethod == null) {
			return null;
		}
		try {
			Object o = protobufParseMethod.invoke(null, new Object[] { data });
			return new Object[] { o };
		} catch (Exception e) {
			throw new RuntimeException("protobuf参数解析失败!");
		}
	}

}
