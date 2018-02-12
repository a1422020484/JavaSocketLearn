package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 乐视
 *
 * @author xiezuojie
 */
@Component
public class LeTvImpl implements PlatformInterface {
    static final Logger log = LoggerFactory.getLogger("letv");

    String LeTvAppId = PTConfig.val("LeTvAppId");
    String LeTvAccessTokenUrl = PTConfig.val("LeTvAccessTokenUrl");

    String[] params = new String[]{"uid", "access_token"};

    @Override
    public String[] requireParams() {
        return params;
    }

    @Override
    public String ptFlag() {
        return "leshi";
    }

    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String token = params.get("access_token");

        String ctx = HttpUtils.create(LeTvAccessTokenUrl)
                .addParam("client_id", LeTvAppId)
                .addParam("uid", uid)
                .addParam("access_token", token)
                .get();

        Resp rs = null;
        try {
            rs = JSON.parseObject(ctx, Resp.class);
        } catch (Exception e) {
        }
        if (rs == null) {
            return LoginResponse.ParamError;
        }

        if (!"1".equals(rs.getStatus())) {
            return new LoginResponse(1, "(" + rs.error_code + ") " + rs.error);
        }

        if (rs.result == null) {
            return LoginResponse.ParamError;
        }

        LoginResponse lr = null;
        if (StringUtils.isNotBlank(rs.result.letv_uid)) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = rs.result.letv_uid;
        } else {
            lr = LoginResponse.ParamError;
        }
        return lr;
    }

    static class Resp {
        String request;
        Result result;
        String status;
        String error_code;
        String error;

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    static class Result {
        String letv_uid;
        String nickname;
        @JSONField(name = "file_300*300")
        String file_300_300;
        @JSONField(name = "file_200*200")
        String file_200_200;
        @JSONField(name = "file_70*70")
        String file_70_70;
        @JSONField(name = "file_50*50")
        String file_50_50;

        public String getLetv_uid() {
            return letv_uid;
        }

        public void setLetv_uid(String letv_uid) {
            this.letv_uid = letv_uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFile_300_300() {
            return file_300_300;
        }

        public void setFile_300_300(String file_300_300) {
            this.file_300_300 = file_300_300;
        }

        public String getFile_200_200() {
            return file_200_200;
        }

        public void setFile_200_200(String file_200_200) {
            this.file_200_200 = file_200_200;
        }

        public String getFile_70_70() {
            return file_70_70;
        }

        public void setFile_70_70(String file_70_70) {
            this.file_70_70 = file_70_70;
        }

        public String getFile_50_50() {
            return file_50_50;
        }

        public void setFile_50_50(String file_50_50) {
            this.file_50_50 = file_50_50;
        }
    }

}
