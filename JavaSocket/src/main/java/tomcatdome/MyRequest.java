package tomcatdome;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MyRequest {
	private String url;
	private String method;
	private Map<String, String> paramMap;

	public MyRequest(InputStream inputStream) throws IOException {
		StringBuilder httpRequeset = new StringBuilder();
		byte[] httpRequestByte = new byte[1024];
		int length = 0;
		if ((length = inputStream.read(httpRequestByte)) > 0) {
			httpRequeset.append(new String(httpRequestByte, 0, length));
		}
		System.out.println("httpRequeset = { " + httpRequeset + " } ");
		if (httpRequeset.length() <= 0) {
			return;
		}
		String httpHead = httpRequeset.toString().split("\n")[0];
		String urlAndParms = httpHead.split("\\s")[1];
		String[] urlAndParmsArray = urlAndParms.split("\\?");
		url = urlAndParmsArray[0];
		if (urlAndParmsArray.length > 1) {
			String[] params = urlAndParmsArray[1].split("\\&");
			for (String param : params) {
				if (paramMap == null) {
					paramMap = new HashMap<>();
				}
				paramMap.put(param.split("\\=")[0], param.split("\\=")[1]);
			}
		}
		method = httpHead.split("\\s")[0];
		System.out.println(" MyRequest = { " + this + " } ");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	@Override
	public String toString() {
		return "MyRequest{" +
				"url='" + url + '\'' +
				", method='" + method + '\'' +
				", paramMap=" + paramMap +
				'}';
	}
}
