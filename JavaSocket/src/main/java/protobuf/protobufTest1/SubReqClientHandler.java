package protobuf.protobufTest1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protobuf.FirstProtobuf;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {
	/**
	 * Creates a client-side handler.
	 */
	public SubReqClientHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		for (int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}

	private FirstProtobuf.testBuf subReq(int i) {
		FirstProtobuf.testBuf.Builder builder = FirstProtobuf.testBuf.newBuilder();
		builder.setID(10);
		builder.setUrl("Lilinfeng");
		FirstProtobuf.testBuf info = builder.build();
		return info;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Receive server response : [" + msg + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
