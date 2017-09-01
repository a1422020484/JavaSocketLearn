package nettyTest.netty3Test.bufferbufTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class ClientTest {

	public static void main(String[] args) throws Exception {
		Bootstrap b = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new StringDecoder());
					p.addLast(new ClientHandler2());
				}
			});
			b.connect("127.0.0.1", 8800).sync().channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}
}
