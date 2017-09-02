package nettyTest.netty3Test.bufferbufTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ServerTest {

	public static void main(String[] args) throws Exception {
		ServerBootstrap b = new ServerBootstrap();

		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					// p.addLast(new LineBasedFrameDecoder(2048));// 粘包
					// 粘包,自定义分隔符
					// p.addLast(new DelimiterBasedFrameDecoder(8192,
					// Delimiters.lineDelimiter()));
					p.addLast(new DelimiterBasedFrameDecoder(8192, new ByteBuf[] { Unpooled.wrappedBuffer(new byte[] { 'i' }) }));
					// p.addLast(new FixedLengthFrameDecoder(23)); // 分包
					p.addLast(new StringDecoder());
					p.addLast(new ServerHandler());
				}
			});
			b.bind(8800).sync().channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
