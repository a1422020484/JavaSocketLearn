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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * pengyowuan平台
 *
 * @author zhuangyuetao
 */
@Component
public class pengyouwanImpl implements PlatformInterface {
    public static final String flag = "pengyouwan";
    public static final Logger log = LoggerFactory.getLogger(flag);
    public static final String[] params = new String[]{"uid", "token"};

    public static final String appKey = PTConfig.val("PengyouwanAppKey");
    public static final String secretkey = PTConfig.val("PengyouwanAppSecretkey");
    public static final String url = PTConfig.val("PengyouwanVerifyUrl");

    /**
     * 参数 类型 说明 是否必须<br>
     * username String 玩家平台帐号 是<br>
     * token String 登录时返回令牌 是<br>
     * pid int 为我方分配参数 是<br>
     * sign String 数据签名，算法如下 是<br>
     * ●签名加密说明<br>
     * sign = md5("pid="+pid+"&token="+token+"&username="+username+key)<br>
     * 密钥 key由我方分配<br>
     */
    @Override
    public LoginResponse login(Map<String, String> params) {
        String uid = params.get("uid");
        String tid = RandomNum.createRandomString(16);
        String token = params.get("token");
        //System.out.println(tid);

//		Map<String, String> uiddata = new HashMap<>();
//				uiddata.put("uid", uid);
//
//				Map<String, String> tiddata = new HashMap<>();
//				tiddata.put("tid", tid);
//				Map<String, String> tokendata = new HashMap<>();
//				tokendata.put("token", token);
        // 生成签名
//		StringBuffer strBuf = new StringBuffer();
//		strBuf.append("appKey=");
//		strBuf.append(appKey);
//		strBuf.append("&uid=");
//		strBuf.append(uid);
//		strBuf.append("&token=");
//		strBuf.append(token);
//		strBuf.append(secretkey);
//		String sign = MD5.encode(strBuf.toString());

        Map<String, Object> p = new HashMap<>();

        p.put("tid", tid);
        p.put("token", token);
        p.put("uid", uid);
        // 请求
//		String resp = HttpUtils.create(url).addParam("uid", uid).addParam("tid", tid).addParam("token", token).urlDecode(false).post();
//		//resp = resp.replace("\"data\":[]", "\"data\":{}"); // 空字符处理
//		Resp rs = JSON.parseObject(resp, Resp.class);
//		if (rs == null) {
//			return LoginResponse.Timeout;
//		}

        String content = null;
        try {
            //String sss = JSON.toJSONString(p);
            content = HttpUtils.create(url).post(JSON.toJSONString(p).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        if (content == null) {
            return LoginResponse.Timeout;
        }
        Resp resp = null;
        try {
            resp = JSON.parseObject(content, Resp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resp == null) {
            return LoginResponse.Timeout;
        }

        // 返回数据
//		LoginResponse lr = null;
//		if (rs.ack == 200) {
//			lr = new LoginResponse();
//			lr.userInfo = new UserInfo();
//			lr.userInfo.account = uid;
//		} else {
//			log.error(flag + "|{}|{}", rs.ack, rs.msg);
//			lr = new LoginResponse(1, rs.msg);
//		}
//		return lr;
        LoginResponse lr = null;
        if (resp.ack == 200) {
            lr = new LoginResponse();
            lr.userInfo = new UserInfo();
            lr.userInfo.account = uid;
        } else {
//			log.error("UC|{}|{}|{}", sid, rs.state.code, rs.state.msg);
            lr = new LoginResponse(1, resp.msg);
        }
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

    public static class Resp {
        //		String resultCode;
        int ack = 0;
        String msg;
        String data;

//		public String getResultCode() {
//			return resultCode;
//		}
//
//		public void setResultCode(String resultCode) {
//			this.resultCode = resultCode;
//		}

        public static class Data {
            protected String uid;
            protected String token;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getToken() {
                return token;
            }

            public void setUsername(String token) {
                this.token = token;
            }

        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getack() {
            return ack;
        }

        public void setAck(int ack) {
            this.ack = ack;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

    // public static void main(String[] args) {
    // String tokenKey = "a43cd7a510fc3b06792a0cb509b58415";
    // String sign = MD5.encode(appKey + tokenKey);
    //
    // System.out.println(sign);
    //
    // }

    public static class RandomNum {
        private static char ch[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
                'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1'};//最后又重复两个0和1，因为需要凑足数组长度为64

        private static Random random = new Random();

        //生成指定长度的随机字符串
        public static synchronized String createRandomString(int length) {
            if (length > 0) {
                int index = 0;
                char[] temp = new char[length];
                int num = random.nextInt();
                for (int i = 0; i < length % 5; i++) {
                    temp[index++] = ch[num & 63];//取后面六位，记得对应的二进制是以补码形式存在的。
                    num >>= 6;//63的二进制为:111111
                    // 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
                }
                for (int i = 0; i < length / 5; i++) {
                    num = random.nextInt();
                    for (int j = 0; j < 5; j++) {
                        temp[index++] = ch[num & 63];
                        num >>= 6;
                    }
                }
                return new String(temp, 0, length);
            } else if (length == 0) {
                return "";
            } else {
                throw new IllegalArgumentException();
            }
        }

        //根据指定个数，测试随机字符串函数的重复率
//		  public static double rateOfRepeat(int number) {
//		    int repeat = 0;
//		    String[] str = new String[number];
//		    for (int i = 0; i < number; i++) {//生成指定个数的字符串
//		      str[i] = RandomNum.createRandomString(10);
//		    }
//		    for (int i = 0; i < number; i++) {//查找是否有相同的字符串
//		      for (int j = i + 1; j < number - 1; j++) {
//		        if (str[i].equals(str[j]))
//		          repeat++;
//		      }
//		    }
//		    return ((double) repeat) / number;
//		  }
//
//		  public static void main(String[] args) {
//		    System.out.println(RandomNum.createRandomString(16));//生成16位随机字符串
//		    double rate = RandomNum.rateOfRepeat(10000);//测试10000次的重复率
//		    System.out.println("重复率:" + rate);
//		  }
    }

}
