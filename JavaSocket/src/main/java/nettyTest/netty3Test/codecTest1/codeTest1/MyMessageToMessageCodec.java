package nettyTest.netty3Test.codecTest1.codeTest1;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

/**
 * @author Administrator encode Integer ==> String decode String ==> Integer
 * String这种还是用原生的docode
 */
public class MyMessageToMessageCodec extends MessageToMessageCodec<Integer, String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("========***************MyMessageToMessageCodec encode String msg " + msg);
		out.add(Integer.valueOf(msg));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("========***************MyMessageToMessageCodec decode Integer msg " + msg);
		out.add(String.valueOf(msg));
	}

}
