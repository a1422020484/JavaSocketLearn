package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.impl.HeziImpl.Resp;
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
public class KaoPuNewImpl implements PlatformInterface {
	public static final String flag = "login";
    static final Logger log = LoggerFactory.getLogger(flag);

	// 口袋2安卓

	// String[] params = new String[] { "gameid", "uid", "token" };
	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "kaopunew";
	}


    @Override
    public LoginResponse login(Map<String, String> params) {
    	String subPlatform = params.get("subPt");
		String uid = params.get("uid");//openId
		String token = params.get("token");//请求拼接窜
		token = token.replaceAll("\\|", "&");
		log.debug("kaopu|token:{}",token);
        // {"code":1,"msg":"验证成功","sign":"7a96bb2c9f6bdbde7b43a7a7f05208f1","r":"0","data":null}
		 if (uid == null) {
             return LoginResponse.ParamError;
         }
		 
        try {
            String resp = HttpUtils.create(token).get();
            Resp rs = JSON.parseObject(resp, Resp.class);
            
            if (rs == null)
	            return LoginResponse.Timeout;
	        LoginResponse lr = null;
	        if (rs.code==1) {
	            lr = new LoginResponse();
	            lr.userInfo = new UserInfo();
	            lr.userInfo.account = uid;
	            return lr;
	        } else {
	            log.error("靠谱|{}|{}", rs.code, rs.msg);
	            lr = new LoginResponse(1, rs.getMsg());
	        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    static class Resp {
        int code;
        String msg;
        String r;
        String data;
        
        public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getR() {
			return r;
		}

		public void setR(String r) {
			this.r = r;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

    }

}
