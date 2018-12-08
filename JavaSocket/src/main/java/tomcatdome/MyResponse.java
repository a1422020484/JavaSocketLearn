package tomcatdome;

import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {
	private OutputStream outputStream;

	public MyResponse(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void write(String content) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("HTTP/1.1 200 OK\n")
				.append("Content-type:text/html\n")
				.append("\r\n")
				.append("<html><title>test</title><head>")
				.append(content)
				.append("</head></body></html>");
		outputStream.write(stringBuffer.toString().getBytes());
		outputStream.close();
	}
}
