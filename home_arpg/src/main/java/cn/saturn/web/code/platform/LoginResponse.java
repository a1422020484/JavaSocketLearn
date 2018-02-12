package cn.saturn.web.code.platform;

/**
 * 登陆返回数据
 *
 * @author xiezuojie
 */
public class LoginResponse {

    public static final LoginResponse DebugError = new LoginResponse(1, "未开启Debug");
    public static final LoginResponse PTError = new LoginResponse(1, "平台标识错误");
    public static final LoginResponse ParamError = new LoginResponse(1, "参数错误");
    public static final LoginResponse Timeout = new LoginResponse(1, "登录超时");

    private int code; // 0成功,1失败
    private String token; // 用于登陆游戏服的令牌
    private String errMsg; // 登陆失败时的错误提示
    private String ext; // 扩展数据
    //	private String account;
//	private String password;
    protected boolean autoRegister = true;

    public transient UserInfo userInfo; // 登录成功时需要完善用户信息

    public LoginResponse() {
        code = 0;
    }

    /**
     * @param code   0成功,1失败
     * @param errMsg
     */
    public LoginResponse(int code, String errMsg) {
        this.code = code;
        if (errMsg == null) {
            this.errMsg = "undefined";
        } else {
            this.errMsg = errMsg;
        }
    }

    /**
     * 从平台获取的用户信息
     *
     * @author xiezuojie
     */
    public static class UserInfo {
        public String account; // 从平台获取的帐号,或唯一标识,id等
        public String password;
        public String thirdUserId; // 如果是来自通用平台,那么需要记录原平台的用户id
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isAutoRegister() {
        return autoRegister;
    }

    public void setAutoRegister(boolean autoRegister) {
        this.autoRegister = autoRegister;
    }

}
