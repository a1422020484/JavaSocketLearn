package cn.saturn.web.code.platform.impl;

import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.LoginResponse.UserInfo;
import cn.saturn.web.code.platform.impl.DangLeImpl.Resp;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.code.platform.utils.Base64;
import cn.saturn.web.code.platform.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xzj.core.util.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author zzz
 */
@Component
public class TapfunsDBImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String tapfunsAnd1001GameCode = PTConfig.val("TapfunsAnd1001GameCode");
	final String tapfunsAnd1002GameCode = PTConfig.val("TapfunsAnd1002GameCode");
	final String tapfunsAnd1003GameCode = PTConfig.val("TapfunsAnd1003GameCode");
	final String tapfunsAnd1004GameCode = PTConfig.val("TapfunsAnd1004GameCode");
	final String tapfunsAnd1005GameCode = PTConfig.val("TapfunsAnd1005GameCode");
	final String tapfunsAnd1006GameCode = PTConfig.val("TapfunsAnd1006GameCode");
	final String tapfunsAnd1007GameCode = PTConfig.val("TapfunsAnd1007GameCode");
	final String tapfunsAnd1008GameCode = PTConfig.val("TapfunsAnd1008GameCode");
	final String tapfunsAnd1009GameCode = PTConfig.val("TapfunsAnd1009GameCode");
	final String tapfunsAnd1010GameCode = PTConfig.val("TapfunsAnd1010GameCode");
	
	final String tapfunsIos2001GameCode = PTConfig.val("TapfunsIos2001GameCode");
	final String tapfunsIos2002GameCode = PTConfig.val("TapfunsIos2002GameCode");
	final String tapfunsIos2003GameCode = PTConfig.val("TapfunsIos2003GameCode");
	final String tapfunsIos2004GameCode = PTConfig.val("TapfunsIos2004GameCode");
	final String tapfunsIos2005GameCode = PTConfig.val("TapfunsIos2005GameCode");
	final String tapfunsIos2006GameCode = PTConfig.val("TapfunsIos2006GameCode");
	final String tapfunsIos2007GameCode = PTConfig.val("TapfunsIos2007GameCode");
	final String tapfunsIos2008GameCode = PTConfig.val("TapfunsIos2008GameCode");
	final String tapfunsIos2009GameCode = PTConfig.val("TapfunsIos2009GameCode");
	final String tapfunsIos2010GameCode = PTConfig.val("TapfunsIos2010GameCode");

//	final String tapfunsAnd7121GameCode = PTConfig.val("TapfunsAnd7121GameCode");
//	final String tapfunsAnd7122GameCode = PTConfig.val("TapfunsAnd7122GameCode");
//	final String tapfunsAnd7123GameCode = PTConfig.val("TapfunsAnd7123GameCode");
//	final String tapfunsAnd7124GameCode = PTConfig.val("TapfunsAnd7124GameCode");
//	final String tapfunsAnd7125GameCode = PTConfig.val("TapfunsAnd7125GameCode");
//	final String tapfunsAnd7126GameCode = PTConfig.val("TapfunsAnd7126GameCode");
//	final String tapfunsAnd7127GameCode = PTConfig.val("TapfunsAnd7127GameCode");
//	final String tapfunsAnd7128GameCode = PTConfig.val("TapfunsAnd7128GameCode");
//	final String tapfunsAnd7129GameCode = PTConfig.val("TapfunsAnd7129GameCode");
//	final String tapfunsAnd7130GameCode = PTConfig.val("TapfunsAnd7130GameCode");
//	final String tapfunsAnd7131GameCode = PTConfig.val("TapfunsAnd7131GameCode");
//	final String tapfunsAnd7132GameCode = PTConfig.val("TapfunsAnd7132GameCode");
//	final String tapfunsAnd7133GameCode = PTConfig.val("TapfunsAnd7133GameCode");
//	final String tapfunsAnd7134GameCode = PTConfig.val("TapfunsAnd7134GameCode");
//	final String tapfunsAnd7135GameCode = PTConfig.val("TapfunsAnd7135GameCode");
//	final String tapfunsAnd7136GameCode = PTConfig.val("TapfunsAnd7136GameCode");
//	final String tapfunsAnd7137GameCode = PTConfig.val("TapfunsAnd7137GameCode");
//	final String tapfunsIos7138GameCode = PTConfig.val("TapfunsIos7138GameCode");
//	final String tapfunsIos7139GameCode = PTConfig.val("TapfunsIos7139GameCode");
//	final String tapfunsIos7140GameCode = PTConfig.val("TapfunsIos7140GameCode");
//	final String tapfunsIos7141GameCode = PTConfig.val("TapfunsIos7141GameCode");
//	final String tapfunsIos7142GameCode = PTConfig.val("TapfunsIos7142GameCode");
//	final String tapfunsIos7143GameCode = PTConfig.val("TapfunsIos7143GameCode");
//	final String tapfunsIos7144GameCode = PTConfig.val("TapfunsIos7144GameCode");
//	final String tapfunsIos7145GameCode = PTConfig.val("TapfunsIos7145GameCode");
//	final String tapfunsIos7146GameCode = PTConfig.val("TapfunsIos7146GameCode");
//	final String tapfunsIos7147GameCode = PTConfig.val("TapfunsIos7147GameCode");
//	
//	final String tapfunsAnd7151GameCode = PTConfig.val("TapfunsAnd7151GameCode");
//	final String tapfunsAnd7152GameCode = PTConfig.val("TapfunsAnd7152GameCode");
//	final String tapfunsAnd7153GameCode = PTConfig.val("TapfunsAnd7153GameCode");
//	final String tapfunsAnd7154GameCode = PTConfig.val("TapfunsAnd7154GameCode");
//	final String tapfunsAnd7155GameCode = PTConfig.val("TapfunsAnd7155GameCode");
//	final String tapfunsAnd7156GameCode = PTConfig.val("TapfunsAnd7156GameCode");

	final String url = PTConfig.val("TapfunsVerifyUrl");

	String[] params = new String[] { "subPt", "uid", "token" };

	@Override
	public String[] requireParams() {
		return params;
	}

	@Override
	public String ptFlag() {
		return "tapfuns";
	}

	@Override
	public LoginResponse login(Map<String, String> params) {

		String tapfunsGameCode = "";

		String uid = params.get("uid");
		String str = params.get("token");
		String[] strs = str.split("\\|");
		log.error("tapfuns|{}", str);
		String timeStamp = strs[0];
		String token = strs[1];

		String subPlatform = params.get("subPt");
		if (subPlatform != null && subPlatform.equals("1001")) {
			tapfunsGameCode = tapfunsAnd1001GameCode;
		} else if (subPlatform != null && subPlatform.equals("1002")) {
			tapfunsGameCode = tapfunsAnd1002GameCode;
		} else if (subPlatform != null && subPlatform.equals("1003")) {
			tapfunsGameCode = tapfunsAnd1003GameCode;
		} else if (subPlatform != null && subPlatform.equals("1004")) {
			tapfunsGameCode = tapfunsAnd1004GameCode;
		} else if (subPlatform != null && subPlatform.equals("1005")) {
			tapfunsGameCode = tapfunsAnd1005GameCode;
		} else if (subPlatform != null && subPlatform.equals("1006")) {
			tapfunsGameCode = tapfunsAnd1006GameCode;
		} else if (subPlatform != null && subPlatform.equals("1007")) {
			tapfunsGameCode = tapfunsAnd1007GameCode;
		} else if (subPlatform != null && subPlatform.equals("1008")) {
			tapfunsGameCode = tapfunsAnd1008GameCode;
		} else if (subPlatform != null && subPlatform.equals("1009")) {
			tapfunsGameCode = tapfunsAnd1009GameCode;
		} else if (subPlatform != null && subPlatform.equals("1010")) {
			tapfunsGameCode = tapfunsAnd1010GameCode;
		} else if (subPlatform != null && subPlatform.equals("2001")) {
			tapfunsGameCode = tapfunsIos2001GameCode;
		} else if (subPlatform != null && subPlatform.equals("2002")) {
			tapfunsGameCode = tapfunsIos2002GameCode;
		} else if (subPlatform != null && subPlatform.equals("2003")) {
			tapfunsGameCode = tapfunsIos2003GameCode;
		} else if (subPlatform != null && subPlatform.equals("2004")) {
			tapfunsGameCode = tapfunsIos2004GameCode;
		} else if (subPlatform != null && subPlatform.equals("2005")) {
			tapfunsGameCode = tapfunsIos2005GameCode;
		} else if (subPlatform != null && subPlatform.equals("2006")) {
			tapfunsGameCode = tapfunsIos2006GameCode;
		} else if (subPlatform != null && subPlatform.equals("2007")) {
			tapfunsGameCode = tapfunsIos2007GameCode;
		} else if (subPlatform != null && subPlatform.equals("2008")) {
			tapfunsGameCode = tapfunsIos2008GameCode;
		} else if (subPlatform != null && subPlatform.equals("2009")) {
			tapfunsGameCode = tapfunsIos2009GameCode;
		} else if (subPlatform != null && subPlatform.equals("2010")) {
			tapfunsGameCode = tapfunsIos2010GameCode;
		} 
//		else if (subPlatform != null && subPlatform.equals("7123")) {
//			tapfunsGameCode = tapfunsAnd7123GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7124")) {
//			tapfunsGameCode = tapfunsAnd7124GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7125")) {
//			tapfunsGameCode = tapfunsAnd7125GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7126")) {
//			tapfunsGameCode = tapfunsAnd7126GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7127")) {
//			tapfunsGameCode = tapfunsAnd7127GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7128")) {
//			tapfunsGameCode = tapfunsAnd7128GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7129")) {
//			tapfunsGameCode = tapfunsAnd7129GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7130")) {
//			tapfunsGameCode = tapfunsAnd7130GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7131")) {
//			tapfunsGameCode = tapfunsAnd7131GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7132")) {
//			tapfunsGameCode = tapfunsAnd7132GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7133")) {
//			tapfunsGameCode = tapfunsAnd7133GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7134")) {
//			tapfunsGameCode = tapfunsAnd7134GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7135")) {
//			tapfunsGameCode = tapfunsAnd7135GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7136")) {
//			tapfunsGameCode = tapfunsAnd7136GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7137")) {
//			tapfunsGameCode = tapfunsAnd7137GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7138")) {
//			tapfunsGameCode = tapfunsIos7138GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7139")) {
//			tapfunsGameCode = tapfunsIos7139GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7140")) {
//			tapfunsGameCode = tapfunsIos7140GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7141")) {
//			tapfunsGameCode = tapfunsIos7141GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7142")) {
//			tapfunsGameCode = tapfunsIos7142GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7143")) {
//			tapfunsGameCode = tapfunsIos7143GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7144")) {
//			tapfunsGameCode = tapfunsIos7144GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7145")) {
//			tapfunsGameCode = tapfunsIos7145GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7146")) {
//			tapfunsGameCode = tapfunsIos7146GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7147")) {
//			tapfunsGameCode = tapfunsIos7147GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7151")) {
//			tapfunsGameCode = tapfunsAnd7151GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7152")) {
//			tapfunsGameCode = tapfunsAnd7152GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7153")) {
//			tapfunsGameCode = tapfunsAnd7153GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7154")) {
//			tapfunsGameCode = tapfunsAnd7154GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7155")) {
//			tapfunsGameCode = tapfunsAnd7155GameCode;
//		} else if (subPlatform != null && subPlatform.equals("7156")) {
//			tapfunsGameCode = tapfunsAnd7156GameCode;
//		}

		String resp = HttpUtils.create(url).addParam("uid", uid).addParam("game_code", tapfunsGameCode)
				.addParam("tokenid", token).addParam("timestamp", timeStamp).post();
		log.error("tapfuns|{}", resp);

		Resp rs = JSON.parseObject(resp, Resp.class);
		if (rs == null) {
			return LoginResponse.Timeout;
		}

		LoginResponse lr = null;
		if (rs.ret.equals("0")) {
			lr = new LoginResponse();
			lr.userInfo = new UserInfo();
			lr.userInfo.account = uid;
		} else {
			log.error("tapfuns|{}|{}", rs.ret, rs.msg);
			lr = new LoginResponse(1, rs.msg);
		}
		return lr;
	}

	static class Resp {
		String ret;
		String msg;

		public String getRet() {
			return ret;
		}

		public void setRet(String ret) {
			this.ret = ret;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
