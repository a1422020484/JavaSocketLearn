package unity3dSocket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * unity3d 服务端
 * 
 * @author lm
 *
 */
public class U3dServer implements Runnable {

	public void run() {

		ServerSocket u3dServerSocket = null;

		while (true) {

			try {

				u3dServerSocket = new ServerSocket(8000);
				System.out.println("u3d服务已经启动,监听8000端口");
				while (true) {
					Socket socket = u3dServerSocket.accept();
					System.out.println("客户端接入");
					new RequestReceiver(socket).start();
				}
			} catch (IOException e) {
				System.err.println("服务器接入失败");
				if (u3dServerSocket != null) {
					try {
						u3dServerSocket.close();
					} catch (IOException ioe) {
					}
					u3dServerSocket = null;
				}
			}

			// 服务延时重启
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}

		}

	}

	/**
	 * 客户端请求接收线程
	 * 
	 * @author lm
	 *
	 */
	class RequestReceiver extends Thread {

		/** 报文长度字节数 */
		private int messageLengthBytes = 1024;

		private Socket socket;

		/** socket输入处理流 */
		private BufferedInputStream bis = null;

		public RequestReceiver(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// 获取socket中的数据
				bis = new BufferedInputStream(socket.getInputStream());
				byte[] buf = new byte[messageLengthBytes];
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				/**
				 * 在Socket报文传输过程中,应该明确报文的域
				 */
				while (true) {

					/*
					 * 这种业务处理方式是根据不同的报文域,开启线程,采用不同的业务逻辑进行处理 依据业务需求而定
					 */
					// 读取字节数组中的内容
					bis.read(buf);
					// 输出
					System.out.println(new String(buf, "utf-8"));
					OutputStream out = socket.getOutputStream();
					// 向客户端传输数据的字节数组
					out.write(new String("i am server").getBytes());

				}

			} catch (IOException e) {
				System.err.println("读取报文出错");
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
					socket = null;
				}
			}

		}
	}
}
