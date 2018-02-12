package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiezuojie
 */
@Component
public class YingYongHuiImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("yingyonghui");
    static final String flag = "yingyonghui";

    String YingYongHuiLoginId = PTConfig.val("YingYongHuiLoginId");
    String YingYongHuiLoginKey = PTConfig.val("YingYongHuiLoginKey");
    String YingYongHuiAppId = PTConfig.val("YingYongHuiAppId");
    String YingYongHuiAppKey = PTConfig.val("YingYongHuiAppKey");
    String YingYongHuiVerifyUrl = PTConfig.val("YingYongHuiVerifyUrl");

    String[] params = new String[]{"ticket"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return flag;
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String ticket = params.get("ticket");
//		String login_id = params.getAccount("login_id");
//		String login_key = params.getAccount("login_key");

        String resp = HttpUtils.create(YingYongHuiVerifyUrl)
                .addParam("ticket", ticket)
                .addParam("login_id", YingYongHuiLoginId)
                .addParam("login_key", YingYongHuiLoginKey)
                .get();
        Resp rs = null;
        try {
            rs = JSON.parseObject(resp, Resp.class);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (rs == null) {
            return LoginResponse.Timeout;
        }

        if (!"0".equals(rs.status)) {
            return new LoginResponse(1, rs.message);
        }

        Data data = null;
        try {
            data = JSON.parseObject(rs.data, Data.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return new LoginResponse(1, "data error");
        }
        if (!data.valid) {
            return new LoginResponse(1, "valid error");
        }

        LoginResponse lr = null;
        lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = data.user_id;
        return lr;
    }

    static class Resp {
        String data;
        String status;
        String message;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    static class Data {
        String nick_name;
        boolean valid;
        String user_name;
        String phone;
        String avatar_url;
        boolean actived;
        String email;
        String ticket;
        String create_time;
        String user_id;
        String role_type;
        String account_type;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public boolean isActived() {
            return actived;
        }

        public void setActived(boolean actived) {
            this.actived = actived;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRole_type() {
            return role_type;
        }

        public void setRole_type(String role_type) {
            this.role_type = role_type;
        }

        public String getAccount_type() {
            return account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
        }
    }
}
