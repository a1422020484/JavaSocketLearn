package cn.saturn.web.code.platform.impl;

/**
 * 狐狸平台
 *
 * @author zhuangyuetao
 */
//@Component
//public class XyImpl implements PlatformInterface {
//	public static final String flag = "xy";
//	public static final Logger log = LoggerFactory.getLogger(flag);
//	public static final String[] params = new String[] {};
//
//	public static final String appId = PTConfig.val("XyAppId"); // client_Id
//	public static final String appKey = PTConfig.val("XyAppKey"); // client_secret
//	public static final String url = PTConfig.val("XyVerifyUrl");
//
//	@Override
//	public String[] requireParams() {
//		return params;
//	}
//
//	@Override
//	public String ptFlag() {
//		return flag;
//	}
//
//	@Override
//	public LoginResponse login(Map<String, String> params) {
//		String tokenKey = params.getAccount("tokenKey");
//		String sign = MD5.encode(appKey + tokenKey);
//
//		// 请求
//		String resp = HttpUtils.create(url).addParam("tokenKey", tokenKey).addParam("sign", sign).post();
//		Resp rs = JSON.parseObject(resp, Resp.class);
//		if (rs == null) {
//			return LoginResponse.Timeout;
//		}
//
//		// 返回数据
//		LoginResponse lr = null;
//		if (rs.code == 0) {
//			lr = new LoginResponse();
//			lr.userInfo.account = rs.data.guid;
//		} else {
//			log.error(flag + "|{}|{}", rs.code, rs.msg);
//			lr = new LoginResponse(1, rs.msg);
//		}
//		return lr;
//	}
//
//	public static class Resp {
//		protected int code;
//		protected String msg;
//		protected Data data;
//
//		public int getCode() {
//			return code;
//		}
//
//		public void setCode(int code) {
//			this.code = code;
//		}
//
//		public String getMsg() {
//			return msg;
//		}
//
//		public void setMsg(String msg) {
//			this.msg = msg;
//		}
//
//		public Data getData() {
//			return data;
//		}
//
//		public void setData(Data data) {
//			this.data = data;
//		}
//
//		public static class Data {
//			protected String guid;
//			protected String username;
//			protected String refer_type;
//			protected String refer_name;
//
//			public String getGuid() {
//				return guid;
//			}
//
//			public void setGuid(String guid) {
//				this.guid = guid;
//			}
//
//			public String getUsername() {
//				return username;
//			}
//
//			public void setUsername(String username) {
//				this.username = username;
//			}
//
//			public String getRefer_type() {
//				return refer_type;
//			}
//
//			public void setRefer_type(String refer_type) {
//				this.refer_type = refer_type;
//			}
//
//			public String getRefer_name() {
//				return refer_name;
//			}
//
//			public void setRefer_name(String refer_name) {
//				this.refer_name = refer_name;
//			}
//		}
//
//	}
//
//	// public static void main(String[] args) {
//	// String tokenKey = "a43cd7a510fc3b06792a0cb509b58415";
//	// String sign = MD5.encode(appKey + tokenKey);
//	//
//	// System.out.println(sign);
//	//
//	// }
//
//}
