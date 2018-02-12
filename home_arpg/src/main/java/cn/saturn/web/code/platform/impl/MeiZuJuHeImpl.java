package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.HttpUtils;

import java.util.Map;

/**
 * @author zzz
 */
@Component
public class MeiZuJuHeImpl implements PlatformInterface {
    public static final String flag = "meizujuhe";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"subPt", "uid", "token"};

    public static final String MeizuJuheAppId = PTConfig.val("MeiZuJuHeAppId");
    public static final String MeizuJuheAppKey = PTConfig.val("MeiZuJuHeAppKey");
    public static final String MeizuJuheAppSecret = PTConfig.val("MeiZuJuHeAppSecret");
    public static final String MeizuJuheVerifyUrl = PTConfig.val("MeiZuJuHeVerifyUrl");

    @Override
    public LoginResponse login(Map<String, String> params) {

        String uid = params.get("uid");
        String token = params.get("token");

        Long timeStamp = System.currentTimeMillis();
        String respCtx = HttpUtils.create(MeizuJuheVerifyUrl)
        		.addParam("app_id", MeizuJuheAppId)
        		.addParam("token", token)
                .addParam("ts", String.valueOf(timeStamp))
                .addParam("uid", uid)
                .post();

        Resp resp = null;
        try {
            resp = JSON.parseObject(respCtx, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resp == null) {
            return LoginResponse.Timeout;
        }
        if (!"200".equals(resp.code)) {
            return new LoginResponse(1, resp.code);
        }

        String accoutId = resp.value.uid;

        // 返回
        LoginResponse lr = new LoginResponse();
        lr.userInfo = new UserInfo();
        lr.userInfo.account = accoutId;
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

    static class Resp {
    	
		String code;
        String message;
        Value value;
        
        public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Value getValue() {
			return value;
		}

		public void setValue(Value value) {
			this.value = value;
		}
        
        public static class Value {
        	String uid;

			public String getUid() {
				return uid;
			}

			public void setUid(String uid) {
				this.uid = uid;
			}
        }
    }

}
