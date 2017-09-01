package nettyTest.netty3Test.test1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandlerDemo1B extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive from ClientHandlerDemo1B");
		ctx.fireChannelActive();
	}
}
