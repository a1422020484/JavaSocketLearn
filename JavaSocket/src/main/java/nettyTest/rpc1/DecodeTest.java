package nettyTest.rpc1;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class DecodeTest {

	public static void main(String[] args) {
		
	}

}

class ToIntegerDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() >= 4) {
			out.add(in.readInt());
		}
	}
}
