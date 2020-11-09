package nettyServer.util.param;

import io.netty.buffer.ByteBuf;
import nettyServer.util.CoreConfig;

/**
 * 参数读取
 *
 * @author yangxp
 */
public abstract class ParamReader {
	
	static final String ByteBufDesc = "io/netty/buffer/ByteBuf";

	static String stringEncoding = CoreConfig.stringValue("ActionParamStringEncoding");

	/**
	 * 从字节缓存区顺序读取与参数类型匹配的值(boolean,byte,int...)<br/>
	 *
	 * @param buf
	 * @return 与Action参数对应的值数组
	 */
	public abstract Object[] read(ByteBuf buf);
	
	/**
	 * 从字节数组顺序读取与参数类型匹配的值(boolean,byte,int...)<br/>
	 *
	 * @param buf
	 * @return 与Action参数对应的值数组
	 */
	public abstract Object[] read(byte[] data);
}
