package nettyTest.netty3Test.codecTest1.codeTest2;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

/**
 * @author Administrator encode Integer ==> String decode String ==> Integer
 */
public class MyMessageToMessageCodec extends MessageToMessageCodec<ByteBuf, Integer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("========***************MyMessageToMessageCodec Integer String msg " + msg);
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

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("========***************MyMessageToMessageCodec decode Integer msg ");
		if (in.readableBytes() >= 4) { // 2
			out.add(in.readInt()); // 3
		}
	}

}
