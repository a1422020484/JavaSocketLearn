package nettyTest.netty3Test.test1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandlerDemo1 extends ChannelInboundHandlerAdapter {

	// @Override
	// public void channelActive(ChannelHandlerContext ctx) throws Exception {
	// // TODO Auto-generated method stub
	// ctx.writeAndFlush("hello world from client");
	// System.out.println("channelActive --------------------------");
	// }
	//
	// @Override
	// public void channelRead(ChannelHandlerContext ctx, Object msg) throws
	// Exception {
	// ctx.writeAndFlush(msg);
	// System.out.println("channelRead --------------------------");
	// }
	//
	// @Override
	// public void channelReadComplete(ChannelHandlerContext ctx) throws
	// Exception {
	// // TODO Auto-generated method stub
	// ctx.flush();
	// System.out.println("channelReadComplete from client");
	// }
	//
	// @Override
	// public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	// throws Exception {
	// cause.printStackTrace();
	// }
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("HelloWorldClientHandler Active");
		ctx.writeAndFlush("Hello Netty Server ,I am a common client");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("HelloWorldClientHandler read Message:" + (String)msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
