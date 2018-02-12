package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 靠谱
 *
 * @author xiezuojie
 */
@Component
public class KaoPuImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("kaopu");
    static final String flag = "kaopu";

    String[] params = new String[]{"url"};

    @Override
    public String ptFlag() {
        return flag;
    }

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String url = params.get("url");
        url = url.replaceAll("\\|", "&");

        // {"code":1,"msg":"验证成功","sign":"7a96bb2c9f6bdbde7b43a7a7f05208f1","r":"0","data":null}
        String openid = null;
        try {
            String resp = HttpUtils.create(url).get();
            JSONObject json = JSON.parseObject(resp);
            int code = json.getIntValue("code");
            if (code != 1) {
                String msg = json.getString("msg");
                return new LoginResponse(1, msg);
            }
            List<NameValuePair> nvlist = URLEncodedUtils.parse(url, Charset.forName("utf-8"));
            for (NameValuePair nv : nvlist) {
                if ("openid".equals(nv.getName())) {
                    openid = nv.getValue();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (openid == null) {
            return LoginResponse.ParamError;
        }

        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = openid;
        return lr;
    }

}
