package protobuf.protobufTest1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protobuf.FirstProtobuf;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		FirstProtobuf.testBuf req = (FirstProtobuf.testBuf) msg;
		if ("Lilinfeng".equalsIgnoreCase(req.getUrl())) {
			System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
			ctx.writeAndFlush(resp(req.getID()));
		}
	}

	private FirstProtobuf.testBuf resp(int subReqID) {
		FirstProtobuf.testBuf.Builder builder = FirstProtobuf.testBuf.newBuilder();
		builder.setID(10);
		builder.setUrl("http://xxx.jpg");
		FirstProtobuf.testBuf info = builder.build();
		return info;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();// 发生异常，关闭链路
	}
}
