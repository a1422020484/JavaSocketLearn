package nettyTest.netty3Test.httpcors;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class OkResponseHandler extends SimpleChannelInboundHandler<Object> {
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {
		final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set("custom-response-header", "Some value");
		returnSomething(response, ctx);
	}

	public void returnSomething(FullHttpResponse response, ChannelHandlerContext ctx) {
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
