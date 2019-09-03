package nioTest.nio1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioClient1 {

	private final static InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8899);

	public static void main(String[] args) throws IOException {

		SocketChannel clientChannel = SocketChannel.open();

		clientChannel.configureBlocking(false);

		Selector selector = Selector.open();

		clientChannel.register(selector, SelectionKey.OP_CONNECT);

		clientChannel.connect(address);

		int a = selector.select();
		System.out.println(a);
		Set<SelectionKey> selectionKeys = selector.selectedKeys();
		selectionKeys.stream().forEach(e->{
			System.out.println(e.isConnectable());
		});
		selectionKeys.stream().forEach(System.out::println);
	}
}
