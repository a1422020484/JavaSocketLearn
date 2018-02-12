package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class TongBuLvImpl implements PlatformInterface {
    public static final String flag = "tongbulv";
    static final Logger log = LoggerFactory.getLogger(flag);

    String TongBuLvSupplyId = PTConfig.val("TongBuLvSupplyId");
    String TongBuLvGameId = PTConfig.val("TongBuLvGameId");
    String TongBuLvSupplierKey = PTConfig.val("TongBuLvSupplierKey");
    String TongBuLvSupplierLoginKey = PTConfig.val("TongBuLvSupplierLoginKey");
    String TongBuLvVerifyUrl = PTConfig.val("TongBuLvVerifyUrl");

    String[] params = new String[]{"uid", "token"};

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String token = params.get("token");

        long t = System.currentTimeMillis() / 1000;
        StringBuilder b = new StringBuilder();
        b.append("access_token=").append(token);
        b.append("&supplier_id=").append(TongBuLvSupplyId);
        b.append("&t=").append(t);
        b.append("&uid=").append(uid);
        b.append(TongBuLvSupplierLoginKey);
        String sign = MD5.encode(b.toString());

        String resp = HttpUtils.create(TongBuLvVerifyUrl)
                .addParam("access_token", token)
                .addParam("supplier_id", TongBuLvSupplyId)
                .addParam("t", String.valueOf(t))
                .addParam("uid", uid)
                .addParam("sign", sign)
                .post();
        try {
            JSONObject json = JSON.parseObject(resp);
            String code = json.getString("code");
            if (!"1".equals(code)) {
                return new LoginResponse(1, json.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoginResponse.Timeout;
        }

        // 返回信息
        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = uid;
        return lr;
    }

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

}
