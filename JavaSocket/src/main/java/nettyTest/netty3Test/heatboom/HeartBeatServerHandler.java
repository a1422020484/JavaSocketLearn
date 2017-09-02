package nettyTest.netty3Test.heatboom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

	private int loss_connect_time = 0;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("serverHandler active ----- ");
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server Read ...");
		System.out.println(ctx.channel().remoteAddress() + "->Server : " + msg.toString());
		super.channelRead(ctx, msg);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				loss_connect_time++;
				System.out.println("5秒钟没有接受到客户端的消息");
				if (loss_connect_time > 2) {
					System.out.println("关闭一个不用的链接");
					ctx.channel().close();
				}
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
