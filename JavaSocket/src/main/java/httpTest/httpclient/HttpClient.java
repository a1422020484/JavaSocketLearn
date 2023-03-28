package httpTest.httpclient;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author zhoujunhua
 * @create 2020/8/4 15:40
 */
public class HttpClient {

    private static class Holder {
        private static HttpClient CLIENT = new HttpClient();
    }

    private final Logger logger = LogManager.getLogger(getClass());
    private CloseableHttpClient client;
    private volatile boolean init;

    private HttpClient() {
    }

    public static HttpClient ins() {
        return Holder.CLIENT;
    }

    public void init() throws Exception {
        if (init) {
            return;
        }
        synchronized (this) {
            if (init) {
                return;
            }
            // 连接超时3s，读取超时5s，总超时6s，连接存活复用与否交给连接管理器自动管理
            client = HttpClients.custom().setDefaultRequestConfig(
                            RequestConfig.custom().setConnectionRequestTimeout(6000).setConnectTimeout(3000)
                                    .setSocketTimeout(5000).build()).setMaxConnPerRoute(1000)
                    .setMaxConnTotal(3000).build();
            init = true;
        }
    }

    public void close() {
        if (!init) {
            return;
        }
        synchronized (this) {
            if (!init) {
                return;
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            init = false;
        }
    }

    /**
     * 发送head请求
     * @param url
     * @return
     */
    public String head(String url) {
        return head(url, null);
    }

    /**
     * 发送head请求
     * @param url
     * @param headers
     * @return
     */
    public String head(String url, Map<String, String> headers) {
        HttpHead httpHead = new HttpHead(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHead.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpHead);
            if (response != null) {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() >= HttpStatus.SC_OK &&
                        statusLine.getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES) {
                    return "OK";
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 发送get请求
     * @param url
     * @return
     */
    public String get(String url) {
        return get(url, null);
    }

    /**
     * 发送get请求
     * @param url
     * @param headers
     * @return
     */
    public String get(String url, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            if (response != null) {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * POST请求
     * @param url
     * @param params
     * @return
     */
    public String post(String url, String params) {
        return post(url, null, params);
    }

    /**
     * POST请求
     * @param url
     * @param params
     * @param  headers
     * @return
     */
    public String post(String url, String params, Map<String, String> headers) {
        return post(url, null, params, headers);
    }

    /**
     * post请求
     * @param url
     * @param contentType
     * @param params
     * @return
     */
    public String post(String url, ContentType contentType, String params) {
        return post(url, contentType, params, null);
    }

    /**
     * post请求
     * @param url
     * @param contentType
     * @param params
     * @param headers
     * @return
     */
    public String post(String url, ContentType contentType, String params, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setEntity(
                new NStringEntity(params, contentType == null ? ContentType.APPLICATION_FORM_URLENCODED : contentType));
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            if (response != null) {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * WebServer请求方法
     *
     * @param url
     */
    @SuppressWarnings("unchecked")
    public <V> HttpResponseMsg<V> post(String url, HttpRequestMsg<V> requestMsg) throws Exception {
        String result = post(url, "param=" + URLEncoder.encode(JSON.toJSONString(requestMsg), StandardCharsets.UTF_8.name()));
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return JSON.parseObject(result, HttpResponseMsg.class);
    }

}
