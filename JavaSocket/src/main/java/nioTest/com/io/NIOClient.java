package nioTest.com.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {

	private static int flag = 1;

	private static int blockSize = 4096;

	private static ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);

	private static ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);

	private final static InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 10280);

	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		/* 打开选择器 */
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		System.out.println(selector.isOpen());
		/* 注册链接操作 */
		socketChannel.connect(serverAddress);

		/* 遍历选择器的KEY */
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client;

		String receiveText;
		String sendText;

		while (true) {
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				iterator.remove();
				if (selectionKey.isConnectable()) {
					System.out.println("client connect...");
					client = (SocketChannel) selectionKey.channel();
					if (client.isConnectionPending()) {
						client.finishConnect();
						System.out.println("客户端完成连接操作");
						sendBuffer.clear();
						sendBuffer.put("Hello, Server.".getBytes());
						sendBuffer.flip();
						client.write(sendBuffer);
					}
					/* 注册读 */
					client.register(selector, SelectionKey.OP_READ);
				}
				if (selectionKey.isReadable()) {
					receiveBuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					int count = client.read(receiveBuffer);
					if (count > 0) {
						receiveText = new String(receiveBuffer.array(), 0, count);
						System.out.println("客户端接收到数据：" + receiveText);
						client.register(selector, SelectionKey.OP_WRITE);
					}
				}
				if (selectionKey.isWritable()) {
					sendBuffer.clear();
					client = (SocketChannel) selectionKey.channel();
					sendText = "Msg send to Server->" + flag++;
					sendBuffer.put(sendText.getBytes());
					sendBuffer.flip();
					client.write(sendBuffer);
					System.out.println("客户端发送数据给服务端：" + sendText);
					client.register(selector, SelectionKey.OP_READ);
				}
			}
			selectionKeys.clear();
			selector.select();
		}
	}
}
