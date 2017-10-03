package oioTest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class OioServer {

	public static void main(String[] args) throws Exception {
		OioServer os = new OioServer();
		os.server(8888);
	}

	public void server(int port) throws Exception {
		final ServerSocket socket = new ServerSocket(port);
		try {
			final Socket clientSocket = socket.accept();
			System.out.println("Accept connection form " + clientSocket);
			new Thread(new Runnable() {
				@Override
				public void run() {
					OutputStream out;
					try {
						out = clientSocket.getOutputStream();
						out.write("HI !!! \r\n".getBytes(Charset.forName("UTF-8")));
						out.flush();
						clientSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							clientSocket.close();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
