package nettyTest.netty3Test.codecTest1.codeTest2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CodeClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public boolean isSharable() {
		System.out.println("==============handler-sharable==============");
		return super.isSharable();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel-register==============");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel-unregister==============");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel-active==============");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel-inactive==============");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		System.out.println("==============channel-read==============");
		System.out.println("the msg type is " + msg.getClass().getName());
//		ByteBuf msgByteBuf = (ByteBuf) msg;
		Integer msgInteger = (Integer) msg;
		System.out.println("服务器端接收到的客户端的数字是" + msgInteger);
		System.out.println("client" + ctx.channel().id().asLongText());
		System.out.println("服务器向客户端写入整型数字 String 2001");
		ctx.writeAndFlush(new Integer(2001));
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==============channel-read-complete==============");
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
