package nioTest.nio1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NioServer1 {

	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		ServerSocket serverSocket = serverChannel.socket();

		serverSocket.bind(new InetSocketAddress(8899));

		Selector selector = Selector.open();

		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true) {
			selector.select();
		}
	}
}
