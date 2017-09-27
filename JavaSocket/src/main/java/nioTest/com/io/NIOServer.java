package nioTest.com.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	private int flag = 1;

	private int blockSize = 4096;

	private ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);

	private ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);

	private Selector selector;

	public NIOServer(int port) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		// 服务端
		serverSocket.bind(new InetSocketAddress(port));
		// 打开选择器
		selector = Selector.open();

		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server start->" + port);

	}

	// /监听事件
	public void listen() throws IOException {
		while (true) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				/* 业务逻辑 */
				handleKey(selectionKey);
				selector.select();
			}

		}
	}

	/* 处理可以 */
	public void handleKey(SelectionKey selectionKey) throws IOException {
		ServerSocketChannel serverSocketChannel = null;
		SocketChannel client = null;
		String reciveText;
		String sendText;

		int count = 0;
		if (selectionKey.isAcceptable()) {
			serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
			client = serverSocketChannel.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);

		} else if (selectionKey.isReadable()) {
			client = (SocketChannel) selectionKey.channel();
			count = client.read(receiveBuffer);
			if (count > 0) {
				reciveText = new String(receiveBuffer.array(), 0, count);
				System.out.println("服务端接受到客户端的信息：" + reciveText);
				client.register(selector, SelectionKey.OP_WRITE);

			}
		} else if (selectionKey.isWritable()) {
			sendBuffer.clear();
			client = (SocketChannel) selectionKey.channel();
			/* 发送的数据 */
			sendText = "msd send to the client." + flag++;
			sendBuffer.put(sendText.getBytes());
			sendBuffer.flip();
			/* 发送至服务器 */
			client.write(sendBuffer);
			System.out.println("服务端发送给客户端：" + sendText);
			client.register(selector, SelectionKey.OP_READ);
		}

	}

	public static void main(String[] args) throws IOException {
		int port = 10280;
		NIOServer nioServer = new NIOServer(port);
		nioServer.listen();

	}
}
