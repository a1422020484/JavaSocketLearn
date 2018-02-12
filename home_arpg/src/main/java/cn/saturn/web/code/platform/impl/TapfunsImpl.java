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
public class TapfunsImpl implements PlatformInterface {
	static final Logger log = LoggerFactory.getLogger("login");

	final String tapfunsAndTFGameCode = PTConfig.val("TapfunsAndTFGameCode");
	final String tapfunsAndTFSGameCode = PTConfig.val("TapfunsAndTFSGameCode");
	final String tapfunsIosDLGameCode = PTConfig.val("TapfunsIosDLGameCode");
	final String tapfunsIosTFGameCode = PTConfig.val("TapfunsIosTFGameCode");
	final String tapfunsAnd7022GameCode = PTConfig.val("TapfunsAnd7022GameCode");
	final String tapfunsAnd7023GameCode = PTConfig.val("TapfunsAnd7023GameCode");
	final String tapfunsAnd7024GameCode = PTConfig.val("TapfunsAnd7024GameCode");
	final String tapfunsAnd7025GameCode = PTConfig.val("TapfunsAnd7025GameCode");
	final String tapfunsAnd7026GameCode = PTConfig.val("TapfunsAnd7026GameCode");
	final String tapfunsAnd7027GameCode = PTConfig.val("TapfunsAnd7027GameCode");
	final String tapfunsIos7032GameCode = PTConfig.val("TapfunsIos7032GameCode");
	final String tapfunsIos7033GameCode = PTConfig.val("TapfunsIos7033GameCode");
	final String tapfunsIos7034GameCode = PTConfig.val("TapfunsIos7034GameCode");
	final String tapfunsIos7035GameCode = PTConfig.val("TapfunsIos7035GameCode");
	final String tapfunsIos7036GameCode = PTConfig.val("TapfunsIos7036GameCode");
	final String tapfunsIos7037GameCode = PTConfig.val("TapfunsIos7037GameCode");

	final String tapfunsAnd7119GameCode = PTConfig.val("TapfunsAnd7119GameCode");
	final String tapfunsAnd7120GameCode = PTConfig.val("TapfunsAnd7120GameCode");
	final String tapfunsAnd7121GameCode = PTConfig.val("TapfunsAnd7121GameCode");
	final String tapfunsAnd7122GameCode = PTConfig.val("TapfunsAnd7122GameCode");
	final String tapfunsAnd7123GameCode = PTConfig.val("TapfunsAnd7123GameCode");
	final String tapfunsAnd7124GameCode = PTConfig.val("TapfunsAnd7124GameCode");
	final String tapfunsAnd7125GameCode = PTConfig.val("TapfunsAnd7125GameCode");
	final String tapfunsAnd7126GameCode = PTConfig.val("TapfunsAnd7126GameCode");
	final String tapfunsAnd7127GameCode = PTConfig.val("TapfunsAnd7127GameCode");
	final String tapfunsAnd7128GameCode = PTConfig.val("TapfunsAnd7128GameCode");
	final String tapfunsAnd7129GameCode = PTConfig.val("TapfunsAnd7129GameCode");
	final String tapfunsAnd7130GameCode = PTConfig.val("TapfunsAnd7130GameCode");
	final String tapfunsAnd7131GameCode = PTConfig.val("TapfunsAnd7131GameCode");
	final String tapfunsAnd7132GameCode = PTConfig.val("TapfunsAnd7132GameCode");
	final String tapfunsAnd7133GameCode = PTConfig.val("TapfunsAnd7133GameCode");
	final String tapfunsAnd7134GameCode = PTConfig.val("TapfunsAnd7134GameCode");
	final String tapfunsAnd7135GameCode = PTConfig.val("TapfunsAnd7135GameCode");
	final String tapfunsAnd7136GameCode = PTConfig.val("TapfunsAnd7136GameCode");
	final String tapfunsAnd7137GameCode = PTConfig.val("TapfunsAnd7137GameCode");
	final String tapfunsIos7138GameCode = PTConfig.val("TapfunsIos7138GameCode");
	final String tapfunsIos7139GameCode = PTConfig.val("TapfunsIos7139GameCode");
	final String tapfunsIos7140GameCode = PTConfig.val("TapfunsIos7140GameCode");
	final String tapfunsIos7141GameCode = PTConfig.val("TapfunsIos7141GameCode");
	final String tapfunsIos7142GameCode = PTConfig.val("TapfunsIos7142GameCode");
	final String tapfunsIos7143GameCode = PTConfig.val("TapfunsIos7143GameCode");
	final String tapfunsIos7144GameCode = PTConfig.val("TapfunsIos7144GameCode");
	final String tapfunsIos7145GameCode = PTConfig.val("TapfunsIos7145GameCode");
	final String tapfunsIos7146GameCode = PTConfig.val("TapfunsIos7146GameCode");
	final String tapfunsIos7147GameCode = PTConfig.val("TapfunsIos7147GameCode");
	
	final String tapfunsAnd7151GameCode = PTConfig.val("TapfunsAnd7151GameCode");
	final String tapfunsAnd7152GameCode = PTConfig.val("TapfunsAnd7152GameCode");
	final String tapfunsAnd7153GameCode = PTConfig.val("TapfunsAnd7153GameCode");
	final String tapfunsAnd7154GameCode = PTConfig.val("TapfunsAnd7154GameCode");
	final String tapfunsAnd7155GameCode = PTConfig.val("TapfunsAnd7155GameCode");
	final String tapfunsAnd7156GameCode = PTConfig.val("TapfunsAnd7156GameCode");
	final String tapfunsAnd7157GameCode = PTConfig.val("TapfunsAnd7157GameCode");
	final String tapfunsAnd7158GameCode = PTConfig.val("TapfunsAnd7158GameCode");
	final String tapfunsAnd7159GameCode = PTConfig.val("TapfunsAnd7159GameCode");
	final String tapfunsAnd7160GameCode = PTConfig.val("TapfunsAnd7160GameCode");

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
		if (subPlatform != null && subPlatform.equals("7020")) {
			tapfunsGameCode = tapfunsAndTFGameCode;
		} else if (subPlatform != null && subPlatform.equals("7021")) {
			tapfunsGameCode = tapfunsAndTFSGameCode;
		} else if (subPlatform != null && subPlatform.equals("7030")) {
			tapfunsGameCode = tapfunsIosDLGameCode;
		} else if (subPlatform != null && subPlatform.equals("7031")) {
			tapfunsGameCode = tapfunsIosTFGameCode;
		} else if (subPlatform != null && subPlatform.equals("7022")) {
			tapfunsGameCode = tapfunsAnd7022GameCode;
		} else if (subPlatform != null && subPlatform.equals("7023")) {
			tapfunsGameCode = tapfunsAnd7023GameCode;
		} else if (subPlatform != null && subPlatform.equals("7024")) {
			tapfunsGameCode = tapfunsAnd7024GameCode;
		} else if (subPlatform != null && subPlatform.equals("7025")) {
			tapfunsGameCode = tapfunsAnd7025GameCode;
		} else if (subPlatform != null && subPlatform.equals("7026")) {
			tapfunsGameCode = tapfunsAnd7026GameCode;
		} else if (subPlatform != null && subPlatform.equals("7027")) {
			tapfunsGameCode = tapfunsAnd7027GameCode;
		} else if (subPlatform != null && subPlatform.equals("7032")) {
			tapfunsGameCode = tapfunsIos7032GameCode;
		} else if (subPlatform != null && subPlatform.equals("7033")) {
			tapfunsGameCode = tapfunsIos7033GameCode;
		} else if (subPlatform != null && subPlatform.equals("7034")) {
			tapfunsGameCode = tapfunsIos7034GameCode;
		} else if (subPlatform != null && subPlatform.equals("7035")) {
			tapfunsGameCode = tapfunsIos7035GameCode;
		} else if (subPlatform != null && subPlatform.equals("7036")) {
			tapfunsGameCode = tapfunsIos7036GameCode;
		} else if (subPlatform != null && subPlatform.equals("7037")) {
			tapfunsGameCode = tapfunsIos7037GameCode;
		} else if (subPlatform != null && subPlatform.equals("7119")) {
			tapfunsGameCode = tapfunsAnd7119GameCode;
		} else if (subPlatform != null && subPlatform.equals("7120")) {
			tapfunsGameCode = tapfunsAnd7120GameCode;
		} else if (subPlatform != null && subPlatform.equals("7121")) {
			tapfunsGameCode = tapfunsAnd7121GameCode;
		} else if (subPlatform != null && subPlatform.equals("7122")) {
			tapfunsGameCode = tapfunsAnd7122GameCode;
		} else if (subPlatform != null && subPlatform.equals("7123")) {
			tapfunsGameCode = tapfunsAnd7123GameCode;
		} else if (subPlatform != null && subPlatform.equals("7124")) {
			tapfunsGameCode = tapfunsAnd7124GameCode;
		} else if (subPlatform != null && subPlatform.equals("7125")) {
			tapfunsGameCode = tapfunsAnd7125GameCode;
		} else if (subPlatform != null && subPlatform.equals("7126")) {
			tapfunsGameCode = tapfunsAnd7126GameCode;
		} else if (subPlatform != null && subPlatform.equals("7127")) {
			tapfunsGameCode = tapfunsAnd7127GameCode;
		} else if (subPlatform != null && subPlatform.equals("7128")) {
			tapfunsGameCode = tapfunsAnd7128GameCode;
		} else if (subPlatform != null && subPlatform.equals("7129")) {
			tapfunsGameCode = tapfunsAnd7129GameCode;
		} else if (subPlatform != null && subPlatform.equals("7130")) {
			tapfunsGameCode = tapfunsAnd7130GameCode;
		} else if (subPlatform != null && subPlatform.equals("7131")) {
			tapfunsGameCode = tapfunsAnd7131GameCode;
		} else if (subPlatform != null && subPlatform.equals("7132")) {
			tapfunsGameCode = tapfunsAnd7132GameCode;
		} else if (subPlatform != null && subPlatform.equals("7133")) {
			tapfunsGameCode = tapfunsAnd7133GameCode;
		} else if (subPlatform != null && subPlatform.equals("7134")) {
			tapfunsGameCode = tapfunsAnd7134GameCode;
		} else if (subPlatform != null && subPlatform.equals("7135")) {
			tapfunsGameCode = tapfunsAnd7135GameCode;
		} else if (subPlatform != null && subPlatform.equals("7136")) {
			tapfunsGameCode = tapfunsAnd7136GameCode;
		} else if (subPlatform != null && subPlatform.equals("7137")) {
			tapfunsGameCode = tapfunsAnd7137GameCode;
		} else if (subPlatform != null && subPlatform.equals("7138")) {
			tapfunsGameCode = tapfunsIos7138GameCode;
		} else if (subPlatform != null && subPlatform.equals("7139")) {
			tapfunsGameCode = tapfunsIos7139GameCode;
		} else if (subPlatform != null && subPlatform.equals("7140")) {
			tapfunsGameCode = tapfunsIos7140GameCode;
		} else if (subPlatform != null && subPlatform.equals("7141")) {
			tapfunsGameCode = tapfunsIos7141GameCode;
		} else if (subPlatform != null && subPlatform.equals("7142")) {
			tapfunsGameCode = tapfunsIos7142GameCode;
		} else if (subPlatform != null && subPlatform.equals("7143")) {
			tapfunsGameCode = tapfunsIos7143GameCode;
		} else if (subPlatform != null && subPlatform.equals("7144")) {
			tapfunsGameCode = tapfunsIos7144GameCode;
		} else if (subPlatform != null && subPlatform.equals("7145")) {
			tapfunsGameCode = tapfunsIos7145GameCode;
		} else if (subPlatform != null && subPlatform.equals("7146")) {
			tapfunsGameCode = tapfunsIos7146GameCode;
		} else if (subPlatform != null && subPlatform.equals("7147")) {
			tapfunsGameCode = tapfunsIos7147GameCode;
		} else if (subPlatform != null && subPlatform.equals("7151")) {
			tapfunsGameCode = tapfunsAnd7151GameCode;
		} else if (subPlatform != null && subPlatform.equals("7152")) {
			tapfunsGameCode = tapfunsAnd7152GameCode;
		} else if (subPlatform != null && subPlatform.equals("7153")) {
			tapfunsGameCode = tapfunsAnd7153GameCode;
		} else if (subPlatform != null && subPlatform.equals("7154")) {
			tapfunsGameCode = tapfunsAnd7154GameCode;
		} else if (subPlatform != null && subPlatform.equals("7155")) {
			tapfunsGameCode = tapfunsAnd7155GameCode;
		} else if (subPlatform != null && subPlatform.equals("7156")) {
			tapfunsGameCode = tapfunsAnd7156GameCode;
		} else if (subPlatform != null && subPlatform.equals("7157")) {
			tapfunsGameCode = tapfunsAnd7157GameCode;
		} else if (subPlatform != null && subPlatform.equals("7158")) {
			tapfunsGameCode = tapfunsAnd7158GameCode;
		} else if (subPlatform != null && subPlatform.equals("7159")) {
			tapfunsGameCode = tapfunsAnd7159GameCode;
		} else if (subPlatform != null && subPlatform.equals("7160")) {
			tapfunsGameCode = tapfunsAnd7160GameCode;
		}

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
