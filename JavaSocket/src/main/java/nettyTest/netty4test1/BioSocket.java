package nettyTest.netty4test1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BioSocket {
	private String host;
	private int port;

	public Socket init() {
		try {
			Socket socket = new Socket(host, port);
			return socket;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public BioSocket(String host, int port) throws IOException {
		this.port = port;
		this.host = host;
	}

	public void write() {
		while (true) {
			Socket socket = null;
			OutputStream outputStream = null;

			try {
				socket = init();
				outputStream = socket.getOutputStream();
				Scanner scanner = new Scanner(System.in);
				String str = scanner.nextLine();
				outputStream.write(str.getBytes());
				outputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		try {
			BioSocket writeClient = new BioSocket("localhost", 8080);
			Thread writeThread = new Thread(writeClient::write);
			writeThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
