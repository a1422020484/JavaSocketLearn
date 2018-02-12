package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author zhuangyuetao
 */
public class YijieMzwJuHeTool {
    public static final String flag = "yijie-mzwjuhe";
    public static final Logger log = LoggerFactory.getLogger(flag);

    public static final String YijieMzwJuHeAppId = filterBracket(PTConfig.val("YijieMzwJuHeAppId"));
    public static final String YijieVerifyUrl = PTConfig.val("YijieVerifyUrl");

    public static final String[] params = new String[]{"sdk", "uin", "sess"};

    public static LoginResponse login(Map<String, String> params) {
        String sdk = params.get("sdk");
        try {
            sdk = URLDecoder.decode(sdk, "utf-8");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        sdk = filterBracket(sdk);
        String uin = params.get("uin");
        String sess = "";
        try {
            sess = URLEncoder.encode(params.get("sess"), "utf-8");
        } catch (UnsupportedEncodingException e1) {
        }
        if (log.isDebugEnabled()) {
            log.debug("Yijie LoginVerify sdk:{},uin:{},sess:{}", new Object[]{sdk, uin, sess});
        }

        String resp = null;
        try {
            resp = HttpUtils.create(YijieVerifyUrl)
                    .urlDecode(false)
                    .addParam("sdk", sdk)
                    .addParam("app", YijieMzwJuHeAppId)
                    .addParam("uin", uin)
                    .addParam("sess", sess)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!"0".equals(resp)) {
            return new LoginResponse(1, resp);
        }

        // 返回数据
        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uin;
        return lr;
    }

    /**
     * 过滤掉参数中的'括号{}'
     *
     * @param s
     * @return
     */
    private static String filterBracket(String s) {
        char[] arr = s.toCharArray();
        char[] dst = new char[arr.length];
        int idx = 0;
        for (int i = 0, len = arr.length; i < len; i++) {
            if (arr[i] == '{'
                    || arr[i] == '}') {
                continue;
            }
            dst[idx++] = arr[i];
        }

        return new String(dst, 0, idx);
    }

}
