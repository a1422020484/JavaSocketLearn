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
import xzj.core.util.MD5;

import java.util.Map;

@Component
public class KaiXuanPaPaImpl implements PlatformInterface {

	static final Logger log = LoggerFactory.getLogger("kaixuan");

	final String appId = PTConfig.val("KaixuanPaPaAppId");
	final String appKey = PTConfig.val("KaixuanPaPaAppKey");
	final String appSecret = PTConfig.val("KaixuanPaPaAppSecret");
	final String verifyUrl = PTConfig.val("KaixuanPaPaVerifyUrl");

	String[] params = new String[] { "token" };

    final String verifyUrl0 =PTConfig.val("KaixuanIosVerifyUrl");
    
	@Override
	public LoginResponse login(Map<String, String> params) {
		String token = params.get("token");
        String verifyUrlTemp = verifyUrl;
        
		String subPlatform = params.get("subPt");
		if(subPlatform != null && subPlatform.equals("5005")){
	        verifyUrlTemp = verifyUrl0;
		}
		
		String sign = MD5.encode(token + appKey + appSecret);
		
		//log.debug("rodking (1) {} ", JSON.toJSONString(params));
		//log.debug("rodking (2) {}   {}  {}   {}", appKey,appSecret,verifyUrl,sign);
        long l1 = System.currentTimeMillis();
		String resp = HttpUtils.create(verifyUrlTemp).addParam("appKey", appKey).addParam("token", token)
				.addParam("sign", sign).post();
        long l2 = System.currentTimeMillis();
        long diff = (l2 - l1);
        if (diff > 500) {
            log.info("凯旋|verifyToken|用时:{} 毫秒", diff);
        }

		Resp rs = JSON.parseObject(resp, Resp.class);

		if (rs == null) {
			return LoginResponse.Timeout;
		}
		LoginResponse lr = null;
		if ("200".equals(rs.code)) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = rs.data.getUserId();
            lr.userInfo.thirdUserId = rs.data.thirdId;
		} else {
			log.error("凯旋啪啪|{}|{}", rs.code, rs.error_msg);
			lr = new LoginResponse(Integer.parseInt(rs.code), rs.error_msg);
		}
		return lr;
	}

	@Override
	public String ptFlag() {
		// TODO Auto-generated method stub
		return "kaixuanpapa";
	}

	@Override
	public String[] requireParams() {
		// TODO Auto-generated method stub
		return params;
	}

	static class Resp {
		String code;
		Data data;
		String error_msg;

		public static class Data {
			String userId;
            String thirdId;

			public String getUserId() {
				return userId;
			}

			public void setUserId(String userId) {
				this.userId = userId;
			}

            public String getThirdId() {
                return thirdId;
            }

            public void setThirdId(String thirdId) {
                this.thirdId = thirdId;
            }
        }

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}

		public String getError_msg() {
			return error_msg;
		}

		public void setError_msg(String error_msg) {
			this.error_msg = error_msg;
		}

	}

}
