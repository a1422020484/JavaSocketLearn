package cn.saturn.web.code.activation.domain;

import java.util.Date;

/**
 * 激活码模型类
 *
 * @author qiuweimin
 */
public class Activation {
    private String code;
    private Date closeTime;
    private String account;
    private boolean invaild;

    public String getCode() {
        return code;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public boolean isInvaild() {
        return invaild;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public void setInvaild(boolean invaild) {
        this.invaild = invaild;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


}
