package httpTest.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * HTTP响应结果
 *
 * @author lip.li
 */
public class HttpResponseMsg<V> {

    private int status;

    private String info = "";

    private V body;

    public HttpResponseMsg() {

    }

    public HttpResponseMsg(V body) {
        this.body = body;
    }

    public HttpResponseMsg(int status) {
        this.status = status;
    }

    public HttpResponseMsg(int status, String info) {
        this.status = status;
        this.info = info;
    }

    public HttpResponseMsg(short status, String info) {
        this.status = status;
        this.info = info;
    }

    public HttpResponseMsg(int status, V body) {
        this.status = status;
        this.body = body;
    }

    public HttpResponseMsg(int status, String info, V body) {
        this.status = status;
        this.info = info;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public V getBody() {
        return body;
    }

    public void setBody(V body) {
        this.body = body;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    public V convertBody(Class<V> clazz) {
        if (body == null) {

            return null;
        }
        if (body instanceof JSONObject) {
            return ((JSONObject) body).toJavaObject(clazz);
        }
        return clazz.cast(body);
    }
}
