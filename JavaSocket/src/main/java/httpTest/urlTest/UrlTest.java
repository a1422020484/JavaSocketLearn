package httpTest.urlTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class UrlTest {
	private final static String address = "http://www.baidu.com";

	public static void main(String[] args) throws Exception {
	}

	@Test
	public void urlTest1() throws Exception {
		URL url = new URL(address);
		InputStream in = url.openStream();// 这个和urlTest2 方法的含义是一样的
		printInputStream(in);
	}

	@Test
	public void urlTest2() throws Exception {
		URL url = new URL(address);
		URLConnection urlcon = url.openConnection();
		InputStream in = urlcon.getInputStream();
		printInputStream(in);
	}

	@Test
	public void urlTest3() throws Exception {
		URL url = new URL("http://www.yhfund.com.cn");
		HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
		InputStream in = urlcon.getInputStream();
		printInputStream(in);
	}

	public static void printInputStream(InputStream in) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1204];
		int len = 1;
		while ((len = in.read(buffer)) != -1) {
			output.write(buffer, 0, len);
		}
		System.out.println(new String(output.toByteArray()));
	}
}
