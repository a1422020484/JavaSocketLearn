package nettyTest.netty3Test2;

import nettyTest.firstTest5.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class TimeClient {

	public static void main(String[] args) {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch ){
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
			// Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 workerGroup.shutdownGracefully();
		}
	}
}
