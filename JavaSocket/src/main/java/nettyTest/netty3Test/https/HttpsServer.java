package nettyTest.netty3Test.https;

import javax.net.ssl.SSLEngine;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

public class HttpsServer {

	public static void start(final int port) throws Exception {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		try {
			serverBootstrap.channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024).group(boss, worker).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					SSLEngine sslEngine = SSLContextFactory.getSslContext().createSSLEngine();
					sslEngine.setUseClientMode(false);
					ch.pipeline().addLast(new SslHandler(sslEngine));
					ch.pipeline().addLast("http-decoder", new HttpServerCodec());
					ch.pipeline().addLast(new HttpsSeverHandler());
				}
			});
			ChannelFuture future = serverBootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}

	}

	public static void main(String[] args) throws Exception {
		start(7000);
	}

}
