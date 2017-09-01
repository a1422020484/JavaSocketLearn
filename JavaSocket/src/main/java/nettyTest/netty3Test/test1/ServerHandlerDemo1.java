package nettyTest.netty3Test.test1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandlerDemo1 extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush("form server !!!!");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// ctx.write(msg);
		// ctx.flush();
		// System.out.println("ServerHandlerDemo1 channelRead0");
		System.out.println("server channelRead..");
		System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
		ctx.write("server write" + msg);
		ctx.flush();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}
