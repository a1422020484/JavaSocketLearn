package cn.saturn.web.utils;


import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpMethod; 
import org.apache.commons.httpclient.HttpStatus; 
import org.apache.commons.httpclient.URIException; 
import org.apache.commons.httpclient.methods.GetMethod; 
import org.apache.commons.httpclient.methods.PostMethod; 
import org.apache.commons.httpclient.params.HttpMethodParams; 
import org.apache.commons.httpclient.util.URIUtil; 
import org.apache.commons.lang.StringUtils; 
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry; 

/** 
* HTTP工具箱 
* 
* @author
*/

@Component
public  class HttpTookit { 
		
		private static final int TIME_OUT = 5;
        //private static Log log = LogFactory.getLog(HttpTookit.class); 
        protected static final Logger logger = LoggerFactory.getLogger(LogType.http);
        
        /** 
         * 执行一个HTTP GET请求，返回请求响应的HTML 
         * 
         * @param url                 请求的URL地址 
         * @param queryString 请求的查询参数,可以为null 
         * @param charset         字符集 
         * @param pretty            是否美化 
         * @return 返回请求响应的HTML 
         */ 
        public static String doGet(String url, String queryString, String charset, boolean pretty) { 
                StringBuffer response = new StringBuffer(); 
                HttpClient client = new HttpClient(); 
                HttpMethod method = new GetMethod(url); 
                try { 
                        if (StringUtils.isNotBlank(queryString)) 
                                //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串 
                                method.setQueryString(URIUtil.encodeQuery(queryString)); 
                        client.executeMethod(method); 
                        if (method.getStatusCode() == HttpStatus.SC_OK) { 
                                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset)); 
                                String line; 
                                while ((line = reader.readLine()) != null) { 
                                        if (pretty) 
                                                response.append(line).append(System.getProperty("line.separator")); 
                                        else 
                                                response.append(line); 
                                } 
                                reader.close(); 
                        } 
                } catch (URIException e) { 
                	logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e); 
                } catch (IOException e) { 
                	logger.error("执行HTTP Get请求" + url + "时，发生异常！", e); 
                } finally { 
                        method.releaseConnection(); 
                } 
                return response.toString(); 
        } 

        /** 
         * 执行一个HTTP POST请求，返回请求响应的HTML 
         * 
         * @param url         请求的URL地址 
         * @param params    请求的查询参数,可以为null 
         * @param charset 字符集 
         * @param pretty    是否美化 
         * @return 返回请求响应的HTML 
         */ 
        public static String doPost(String url, Map<String, String> params, String charset, boolean pretty) { 
                StringBuffer response = new StringBuffer(); 
                HttpClient client = new HttpClient(); 
                HttpMethod method = new PostMethod(url); 
                //设置Http Post数据 
                if (params != null) { 
                        HttpMethodParams p = new HttpMethodParams(); 
                        for (Map.Entry<String, String> entry : params.entrySet()) { 
                                p.setParameter(entry.getKey(), entry.getValue()); 
                        } 
                        method.setParams(p); 
                } 
                try { 
                        client.executeMethod(method); 
                        if (method.getStatusCode() == HttpStatus.SC_OK) { 
                                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset)); 
                                String line; 
                                while ((line = reader.readLine()) != null) { 
                                        if (pretty) 
                                                response.append(line).append(System.getProperty("line.separator")); 
                                        else 
                                                response.append(line); 
                                } 
                                reader.close(); 
                        } 
                } catch (IOException e) { 
                	logger.error("执行HTTP Post请求" + url + "时，发生异常！", e); 
                } finally { 
                        method.releaseConnection(); 
                } 
                return response.toString(); 
        }
        
        public static String sentPost(String httpUrl, String postBody, String encoding, Map<String, String> headerMap) {
    		HttpURLConnection httpCon = null;
    		String responseBody = null;
    		URL url = null;
    		try {
    			url = new URL(httpUrl);
    		} catch (MalformedURLException e1) {
    			return null;
    		}
    		try {
    			httpCon = (HttpURLConnection) url.openConnection();
    		} catch (IOException e1) {
    			return null;
    		}
    		if (httpCon == null) {
    			return null;
    		}
    		httpCon.setDoOutput(true);
    		httpCon.setConnectTimeout(TIME_OUT * 1000);
    		httpCon.setReadTimeout(TIME_OUT * 1000);
    		httpCon.setDoOutput(true);
    		httpCon.setUseCaches(false);
    		try {
    			httpCon.setRequestMethod("POST");
    		} catch (ProtocolException e1) {
    			return null;
    		}
    		if (headerMap != null) {
    			Iterator<Entry<String, String>> iterator = headerMap.entrySet().iterator();
    			while (iterator.hasNext()) {
    				Entry<String, String> entry = iterator.next();
    				httpCon.addRequestProperty(entry.getKey(), entry.getValue());
    			}
    		}
    		OutputStream output;
    		try {
    			output = httpCon.getOutputStream();
    		} catch (IOException e1) {
    			return null;
    		}
    		try {
    			output.write(postBody.getBytes(encoding));
    		} catch (UnsupportedEncodingException e1) {
    			return null;
    		} catch (IOException e1) {
    			return null;
    		}
    		try {
    			output.flush();
    			output.close();
    		} catch (IOException e1) {
    			return null;
    		}


    		// 开始读取返回的内容
    		InputStream in;
    		try {
    			in = httpCon.getInputStream();
    		} catch (IOException e1) {
    			return null;
    		}
    		/**
    		 * 这个方法可以在读写操作前先得知数据流里有多少个字节可以读取。
    		 * 需要注意的是，如果这个方法用在从本地文件读取数据时，一般不会遇到问题，
    		 * 但如果是用于网络操作，就经常会遇到一些麻烦。
    		 * 比如，Socket通讯时，对方明明发来了1000个字节，但是自己的程序调用available()方法却只得到900，或者100，甚至是0，
    		 * 感觉有点莫名其妙，怎么也找不到原因。
    		 * 其实，这是因为网络通讯往往是间断性的，一串字节往往分几批进行发送。
    		 * 本地程序调用available()方法有时得到0，这可能是对方还没有响应，也可能是对方已经响应了，但是数据还没有送达本地。
    		 * 对方发送了1000个字节给你，也许分成3批到达，这你就要调用3次available()方法才能将数据总数全部得到。
    		 * 
    		 * 经常出现size为0的情况，导致下面readCount为0使之死循环(while (readCount != -1) {xxxx})，出现死机问题
    		 */
    		int size = 0;
    		try {
    			size = in.available();
    		} catch (IOException e1) {
    			return null;
    		}
    		if (size == 0) {
    			size = 1024;
    		}
    		byte[] readByte = new byte[size];
    		// 读取返回的内容
    		int readCount = -1;
    		try {
    			readCount = in.read(readByte, 0, size);
    		} catch (IOException e1) {
    			return null;
    		}
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		while (readCount != -1) {
    			baos.write(readByte, 0, readCount);
    			try {
    				readCount = in.read(readByte, 0, size);
    			} catch (IOException e) {
    				return null;
    			}
    		}
    		try {
    			responseBody = new String(baos.toByteArray(), encoding);
    		} catch (UnsupportedEncodingException e) {
    			return null;
    		} finally {
    			if (httpCon != null) {
    				httpCon.disconnect();
    			}
    			if (baos != null) {
    				try {
    					baos.close();
    				} catch (IOException e) {
    				}
    			}
    		}
    		
    		return responseBody;
    	}

        public static void main(String[] args) { 
                //String y = doGet("http://video.sina.com.cn/life/tips.html", null, "GBK", true);
        	String y = doGet("http://localhost:8080/advertise/adver/await/waittimes", "time=52&platform=xiaomi&version=1&games=kdnx27", "UTF-8", true);
                System.out.println(y); 
        } 
}       