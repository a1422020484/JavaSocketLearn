package cn.saturn.web.code.login.domain;

import java.util.Date;

public class LoginLogModel {
    private long account_id; // 账号
    private int server_id; // 区id
    private String platform; // 平台
    private Date register_time;// 注册时间
    private Date last_log_time; // 登录时间

    public LoginLogModel() {
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    public Date getLast_log_time() {
        return last_log_time;
    }

    public void setLast_log_time(Date last_log_time) {
        this.last_log_time = last_log_time;
    }
}
