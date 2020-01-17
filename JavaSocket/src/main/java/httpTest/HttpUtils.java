package httpTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import jodd.net.URLDecoder;

public class HttpUtils {

	public static HttpUtilBuilder create(String uri) {
		return new HttpUtilBuilder(uri);
	}

	public static class HttpUtilBuilder {
		private String uri;
		private Map<String, String> params = new HashMap<>();
		private String charset = "utf-8";
		private int socketTimeout = 20000;
		private int connectTimeout = 10000;
		private int connectionRequestTimeout = 10000;

		HttpUtilBuilder(String uri) {
			this.uri = uri;
		}

		public HttpUtilBuilder addParam(String k, String v) {
			params.put(k, v);
			return this;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public Map<String, String> getParams() {
			return params;
		}

		public void setParams(Map<String, String> params) {
			this.params = params;
		}

		public String getCharset() {
			return charset;
		}

		public void setCharset(String charset) {
			this.charset = charset;
		}

		public int getSocketTimeout() {
			return socketTimeout;
		}

		public void setSocketTimeout(int socketTimeout) {
			this.socketTimeout = socketTimeout;
		}

		public int getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public int getConnectionRequestTimeout() {
			return connectionRequestTimeout;
		}

		public void setConnectionRequestTimeout(int connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
		}

		public String get() {
			String content = null;
			CloseableHttpClient httpClient = null;
			CloseableHttpResponse resp = null;
			try {

				if (uri.startsWith("https")) {

				} else {
					httpClient = HttpClients.createDefault();
				}

				RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
				StringBuilder b = new StringBuilder(uri);
				if (b.indexOf("?") == -1) {
					b.append("?");
				}
				for (Map.Entry<String, String> en : params.entrySet()) {
					b.append("&").append(en.getKey()).append("=").append(en.getValue());
				}

				HttpGet get = new HttpGet(URLDecoder.decode(b.toString(), charset));
				get.setConfig(requestConfig);
				resp = httpClient.execute(get);
				content = EntityUtils.toString(resp.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HttpClientUtils.closeQuietly(resp);
				HttpClientUtils.closeQuietly(httpClient);
			}
			return content;
		}

		public void post(byte[] data, HttpResponseHandler handler) {
			CloseableHttpClient httpClient = null;
			CloseableHttpResponse resp = null;
			try {

				if (uri.startsWith("https")) {

				} else {
					httpClient = HttpClients.createDefault();
				}

				RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
				HttpPost post = new HttpPost(uri);
				post.setConfig(requestConfig);
				post.setEntity(new ByteArrayEntity(data));
				resp = httpClient.execute(post);
				handler.handler(resp);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				HttpClientUtils.closeQuietly(resp);
				HttpClientUtils.closeQuietly(httpClient);
			}
		}
	}

	static class TestRun implements Runnable {
		@Override
		public void run() {
			// for (int i = 0; i < 100; i++) {
//			String c = HttpUtils.create("http://www.baidu.com").addParam("a", "a").get();
			String c = HttpUtils.create("http://localhost:8080/ServletTest/App").addParam("username1", "yang").get();
			 System.out.println(c);
			// }
		}
	}

	public static void main(String[] args) {
		new Thread(new TestRun()).start();
//		new Thread(new TestRun()).start();

	}
}
