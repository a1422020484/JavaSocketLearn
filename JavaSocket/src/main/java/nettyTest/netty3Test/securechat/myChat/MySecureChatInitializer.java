package nettyTest.netty3Test.securechat.myChat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class MySecureChatInitializer extends ChannelInitializer<SocketChannel> {

	private SslContext sslContext;

	public MySecureChatInitializer(SslContext sslContext) {
		this.sslContext = sslContext;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(sslContext.newHandler(ch.alloc(), MySecureChatClient.HOST, MySecureChatClient.PORT));

		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new StringEncoder());

		pipeline.addLast(new MysecureChatClientHandler());

	}
}
