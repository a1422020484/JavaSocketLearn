package nettyTest.netty3Test.test1.test1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandlerDemo1 extends SimpleChannelInboundHandler<String> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ServerHandlerDemo1 ==== ");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("server channelRead0 ===" + msg);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server channelRead ===" + msg);
	}
}
