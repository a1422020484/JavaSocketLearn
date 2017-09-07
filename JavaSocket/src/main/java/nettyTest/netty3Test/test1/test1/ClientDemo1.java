package nettyTest.netty3Test.test1.test1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientDemo1 {

	public static void main(String[] args) throws Exception {

		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				// p.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				// Delimiters.lineDelimiter()));
//				 p.addLast("decoder", new StringDecoder());
//				 p.addLast("encoder", new StringEncoder());
				p.addLast(new ClientHandlerDemo1());
			}
		});
		try {

			ChannelFuture future = b.connect("127.0.0.1", 8800).sync();
			// 控制台输入
//			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//			for (;;) {
//				String line = in.readLine();
//				if (line == null) {
//					continue;
//				}
				/*
				 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾 之所以用\r\n结尾 是因为我们在handler中添加了
				 * DelimiterBasedFrameDecoder 帧解码。
				 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
				 */
//				future.channel().writeAndFlush(line + "\r\n");
//			}
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
