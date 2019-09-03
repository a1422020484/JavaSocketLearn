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
		// The new channel is created by invoking the openSocketChannel method of the system-wide default java.nio.channels.spi.SelectorProvider object.
		// 使用默认的 java.nio.channels.spi.SelectorProvider 对象 的 openSocketChannel 方法创建一个新的 通道channel
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		/* 打开选择器 */
		// The new selector is created by invoking the openSelector method of the system-wide default java.nio.channels.spi.SelectorProvider object.
		// 使用默认的 java.nio.channels.spi.SelectorProvider 对象 的 openSelector 方法创建一个新的 通道选择器
		Selector selector = Selector.open();
		// Registers this channel with the given selector, returning a selection key.
		// 把通道注册到给定的选择器上，返回一个选择键    ======== 用给定的选择器注册此频道，返回选择键。
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		System.out.println(selector.isOpen());
		/* 注册链接操作 */
		socketChannel.connect(serverAddress);

		/* 遍历选择器的KEY */
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel clientChannel;

		String receiveText;
		String sendText;
		
//		SocketChannel socketChannel2 = SocketChannel.open();
//		socketChannel2.configureBlocking(false);
//		socketChannel2.register(selector, SelectionKey.OP_CONNECT);
//		socketChannel2.connect(serverAddress);
		
		selectionKeys = selector.selectedKeys();
		
		while (true) {
//			 Selects a set of keys whose corresponding channels are ready for I/O operations. 
//			This method performs a blocking <a href="#selop">selection
//		     operation.  It returns only after at least one channel is selected,
//		     this selector's {@link #wakeup wakeup} method is invoked, or the current
//		     thread is interrupted, whichever comes first.
			// 选择一组键，其对应通道已准备好进行I/O操作。此方法执行阻塞选择操作。它仅在至少选择一个通道后返回，调用选择器的@link wakeup方法，或中断当前线程，以先到者为准。
			int i = selector.select();
			System.out.println(i);
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				iterator.remove();
				if (selectionKey.isConnectable()) {
					System.out.println("client connect...");
					clientChannel = (SocketChannel) selectionKey.channel();
					if (clientChannel.isConnectionPending()) {
						clientChannel.finishConnect();
						System.out.println("客户端完成连接操作");
						sendBuffer.clear();
						sendBuffer.put("Hello, Server.".getBytes());
						// 在一系列通道读取或 put 操作之后，调用此方法以准备通道写入或相对获取操作的序列。
						sendBuffer.flip();
						clientChannel.write(sendBuffer);
					}
					/* 注册读 */
					clientChannel.register(selector, SelectionKey.OP_READ);
					
				}
				if (selectionKey.isReadable()) {
					receiveBuffer.clear();
					clientChannel = (SocketChannel) selectionKey.channel();
					int count = clientChannel.read(receiveBuffer);
					if (count > 0) {
						receiveText = new String(receiveBuffer.array(), 0, count);
						System.out.println("客户端接收到数据：" + receiveText);
						clientChannel.register(selector, SelectionKey.OP_WRITE);
					}
				}
				if (selectionKey.isWritable()) {
					sendBuffer.clear();
					clientChannel = (SocketChannel) selectionKey.channel();
					sendText = "Msg send to Server->" + flag++;
					sendBuffer.put(sendText.getBytes());
					sendBuffer.flip();
					clientChannel.write(sendBuffer);
					System.out.println("客户端发送数据给服务端：" + sendText);
					clientChannel.register(selector, SelectionKey.OP_READ);
				}
			}
//			selectionKeys.clear();
		}
	}
}
