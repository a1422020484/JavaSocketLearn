package nettyTest.netty3Test;

public class EchoClient {

	static final boolean SSL = System.getProperty("ssl") != null;
	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
	
	
	public static void main(String[] args) {

	}

}
