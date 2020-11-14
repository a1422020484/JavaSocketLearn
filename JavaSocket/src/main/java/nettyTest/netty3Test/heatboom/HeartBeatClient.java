package nettyTest.netty3Test.heatboom;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class HeartBeatClient {

	public static EventLoopGroup group;

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		HeartBeatClient.group = group;
		connect(group);
	}

	public static void connect(EventLoopGroup group) throws Exception {
		ChannelFuture future = null;
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new LoggingHandler(LogLevel.INFO)).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
					p.addLast("decoder", new StringDecoder());
					p.addLast("encoder", new StringEncoder());
					p.addLast(new HeartBeatClientHandler());
				}
			});

//			Runtime.getRuntime().addShutdownHook(new ShotDownHookNet());
			future = b.connect("127.0.0.1", 8800).sync();
			future.channel().closeFuture().sync();
		} finally {
			// 断了之后释放资源
			// group.shutdownGracefully();
			// 断开重连
			if (null != future) {
				if (future.channel() != null && future.channel().isOpen()) {
					future.channel().close();
				}
			}
			System.out.println("准备重连");
			connect(group);
			System.out.println("重连成功");


		}
	}

	static class ShotDownHookNet extends Thread{
		@Override
		public void run() {
			HeartBeatClient.group.shutdownGracefully();
			System.out.println("client shutdown graceFully");
		}
	}
}
