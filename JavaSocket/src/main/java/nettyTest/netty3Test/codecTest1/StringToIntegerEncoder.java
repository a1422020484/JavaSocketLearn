package nettyTest.netty3Test.codecTest1;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
/**
 * @author Administrator
 * 这个编解码机器有毛病
 */
public class StringToIntegerEncoder extends MessageToMessageEncoder<String>{

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		System.out.println("StringToIntegerEncoder String msg = " + msg);
		out.add(Integer.valueOf(msg));
	}

}
