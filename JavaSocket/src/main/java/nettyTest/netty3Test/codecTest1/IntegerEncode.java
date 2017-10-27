package nettyTest.netty3Test.codecTest1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class IntegerEncode extends MessageToMessageEncoder<Integer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		System.out.println("IntegerEncode = " + msg);
		// 把int转换为字节数组
		byte[] result = new byte[4];
		result[0] = (byte) (msg.intValue() >>> 24);// 取最高8位放到0下标
		result[1] = (byte) (msg.intValue() >>> 16);// 取次高8为放到1下标
		result[2] = (byte) (msg.intValue() >>> 8); // 取次低8位放到2下标
		result[3] = (byte) (msg.intValue()); // 取最低8位放到3下标

		ByteBuf encoded = ctx.alloc().buffer(5);
		encoded.writeBytes(result);
		out.add(encoded);
	}

}
