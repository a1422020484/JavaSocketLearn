package nettyTest.netty3Test.codecTest1;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * @author Administrator
 * 这个编解码机器有毛病
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		System.out.println("IntegerToStringDecoder Integer msg = " + msg);
		out.add(String.valueOf(msg));
	}

}
