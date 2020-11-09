package nettyServer.util;

import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP工具类
 *
 * @author yangxp
 */
public class HttpUtils {
	
	/**
	 * @param uri 将要请求的uri地址,需要指定scheme(http|https)
	 * @return Builder
	 */
	public static HttpUtilBuilder create(String uri) {
		return new HttpUtilBuilder(uri);
	}
	
	public static class HttpUtilBuilder {
		String uri;
		Map<String, String> params = new HashMap<>();
		String charset = "utf-8"; // 默认utf-8
		int socketTimeout = 20000;
		int connectTimeout = 10000;
		int connectionRequestTimeout = 10000;

		HttpUtilBuilder(String uri) {
			this.uri = uri;
		}

		/**
		 * 添加参数
		 * @param k
		 * @param v
		 * @return Builder
		 */
		public HttpUtilBuilder addParam(String k, String v) {
			params.put(k, v);
			return this;
		}
		
		/**
		 * 设置编码
		 * @param charset
		 */
		public HttpUtilBuilder setCharset(String charset) {
			this.charset = charset;
			return this;
		}
		
		public HttpUtilBuilder setSocketTimeout(int socketTimeout) {
			this.socketTimeout = socketTimeout;
			return this;
		}
		
		public HttpUtilBuilder setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}
		
		public HttpUtilBuilder setConnectionRequestTimeout(int connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
			return this;
		}

		/**
		 * 发送Get请求<br>
		 * 默认:<br>
		 * <li>charset=utf-8</li>
		 * <li>socketTimeout=20000</li>
		 * <li>connectTimeout=10000</li>
		 * <li>connectionRequestTimeout=10000</li>
		 * @return 服务器返回的内容
		 */
		public String get() {
			String content = null;
			try {
				CloseableHttpClient httpClient = null;
				if (uri.startsWith("https")) {
					SSLContext sslcontext = SSLContexts.custom()
							.loadTrustMaterial(null, new TrustStrategy() {
								@Override
								public boolean isTrusted(
										final X509Certificate[] chain,
										final String authType)
										throws CertificateException {
									return true;
								}
							}).build();
					httpClient = HttpClients.custom().setSslcontext(sslcontext).build();
				} else {
					httpClient = HttpClients.createDefault();
				}
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectionRequestTimeout(connectionRequestTimeout)
						.setConnectTimeout(connectTimeout)
						.setSocketTimeout(socketTimeout).build();
				StringBuilder b = new StringBuilder(uri);
				if (b.indexOf("?") == -1)
					b.append("?");
				for (Map.Entry<String, String> en : params.entrySet()) {
					b.append("&").append(en.getKey()).append("=").append(en.getValue());
				}
				HttpGet get = new HttpGet(URLDecoder.decode(b.toString(), charset));
				get.setConfig(requestConfig);
				CloseableHttpResponse resp;
				resp = httpClient.execute(get);
				content = EntityUtils.toString(resp.getEntity());
				HttpClientUtils.closeQuietly(resp);
				HttpClientUtils.closeQuietly(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return content;
		}

		/**
		 * 发送Post请求<br>
		 * 默认:<br>
		 * <li>charset=utf-8</li>
		 * <li>socketTimeout=20000</li>
		 * <li>connectTimeout=10000</li>
		 * <li>connectionRequestTimeout=10000</li>
		 * @return 服务器返回的内容,请求失败时返回null.
		 */
		public String post() {
			String content = null;
			try {
				CloseableHttpClient httpClient = null;
				if (uri.startsWith("https")) {
					SSLContext sslcontext = SSLContexts.custom()
							.loadTrustMaterial(null, new TrustStrategy() {
								@Override
								public boolean isTrusted(
										final X509Certificate[] chain,
										final String authType)
										throws CertificateException {
									return true;
								}
							}).build();
					httpClient = HttpClients.custom().setSslcontext(sslcontext).build();
				} else {
					httpClient = HttpClients.createDefault();
				}
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectionRequestTimeout(connectionRequestTimeout)
						.setConnectTimeout(connectTimeout)
						.setSocketTimeout(socketTimeout).build();
				HttpPost post = new HttpPost(uri);
				post.setConfig(requestConfig);
				List<NameValuePair> nvps = new ArrayList<>();
				for (Map.Entry<String, String> en : params.entrySet()) {
					nvps.add(new BasicNameValuePair(en.getKey(), en.getValue()));
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, charset));
				CloseableHttpResponse resp = httpClient.execute(post);
				content = EntityUtils.toString(resp.getEntity());
				HttpClientUtils.closeQuietly(resp);
				HttpClientUtils.closeQuietly(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return content;
		}

		/**
		 * 发送Post请求,忽略添加的params,内容只能是参数data表示的字节数组.<br>
		 * 默认:<br>
		 * <li>charset=utf-8</li>
		 * <li>socketTimeout=20000</li>
		 * <li>connectTimeout=10000</li>
		 * <li>connectionRequestTimeout=10000</li>
		 * @return 服务器返回的内容,请求失败时返回null.
		 */
		public void post(byte[] data, HttpResponseHandler handler) {
			try {
				CloseableHttpClient httpClient = null;
				if (uri.startsWith("https")) {
					SSLContext sslcontext = SSLContexts.custom()
							.loadTrustMaterial(null, new TrustStrategy() {
								@Override
								public boolean isTrusted(
										final X509Certificate[] chain,
										final String authType)
										throws CertificateException {
									return true;
								}
							}).build();
					httpClient = HttpClients.custom().setSslcontext(sslcontext).build();
				} else {
					httpClient = HttpClients.createDefault();
				}
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectionRequestTimeout(connectionRequestTimeout)
						.setConnectTimeout(connectTimeout)
						.setSocketTimeout(socketTimeout).build();
				HttpPost post = new HttpPost(uri);
				post.setConfig(requestConfig);
				post.setEntity(new ByteArrayEntity(data));
				CloseableHttpResponse resp = httpClient.execute(post);
				handler.handle(resp);
				HttpClientUtils.closeQuietly(resp);
				HttpClientUtils.closeQuietly(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	static class TestRun implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 100; i ++) {
				String c = HttpUtils.create("http://www.baidu.com")
						.addParam("a", "a")
						.get();
				System.out.println(c);
			}
		}
	}
	
	public static void main(String[] args) {
		new Thread(new TestRun()).start();
		new Thread(new TestRun()).start();
		
	}
}
