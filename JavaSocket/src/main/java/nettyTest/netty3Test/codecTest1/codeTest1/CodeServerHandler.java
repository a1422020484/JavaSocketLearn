package nettyTest.netty3Test.codecTest1.codeTest1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CodeServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel--register==============");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel--unregistered==============");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel--inactive==============");
	}

	// 连接成功后，向server发送消息
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel--active==============");
		System.out.println("向客户端端写入 String 2000数字");
		ctx.writeAndFlush("2000");
	}

	// 接收server端的消息，并打印出来
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("==============channel--read==============");
		System.out.println("the msg type is " + msg.getClass().getName());

//		Integer result = (Integer) msg;
		System.out.println("接收到服务器数据，该数字是" + msg.toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
