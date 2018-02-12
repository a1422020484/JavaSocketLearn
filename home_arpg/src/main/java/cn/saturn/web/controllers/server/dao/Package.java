package cn.saturn.web.controllers.server.dao;

/**
 * 资源包
 */
public class Package {
    public static final int smial_pack = 0;  // 小版本
    public static final int big_pack = 1;     // 大版本

    protected long id;
    protected String platform;
    protected String version;
    protected int resversion;
    protected String resurl;
    protected String notice;
    protected int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getResversion() {
        return resversion;
    }

    public void setResversion(int resversion) {
        this.resversion = resversion;
    }

    public String getResurl() {
        return resurl;
    }

    public void setResurl(String resurl) {
        this.resurl = resurl;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPackStr() {
        return type == big_pack ? "大版本" : "小版本";
    }
}
