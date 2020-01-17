package nettyTest.netty4test1;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
	private ServerSocket serverSocket;
	private static ExecutorService threadPool = Executors.newSingleThreadExecutor();

	public void init(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void read() {
		if (serverSocket == null) {
			return;
		}
		Socket socket = null;
		while (true) {
			try {
				socket = serverSocket.accept();
				// 交给线程池处理，避免遗漏其他客户端的请求
				threadPool.execute(new RequestHandler(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class RequestHandler implements Runnable {
		private Socket socket;

		public RequestHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			InputStream inputStream = null;
			try {
				inputStream = socket.getInputStream();
				byte[] bytes = new byte[10240];
				StringBuffer sb = new StringBuffer();
				int len = 0;
				while ((len = inputStream.read(bytes)) != -1) {
					sb.append(new String(bytes, 0, len, "UTF-8"));
				}
				System.out.println("msg from client : " + sb);
				inputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
        try {
            BioServer readServer = new BioServer();
            readServer.init(8080);
            readServer.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
