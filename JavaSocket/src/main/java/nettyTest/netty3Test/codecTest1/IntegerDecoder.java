package nettyTest.netty3Test.codecTest1;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class IntegerDecoder extends MessageToMessageDecoder<Integer> {

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		System.out.println("IntegerDecoder" + msg);
		out.add(msg.intValue());
	}

}
