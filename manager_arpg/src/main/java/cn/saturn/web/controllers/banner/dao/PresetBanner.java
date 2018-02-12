package cn.saturn.web.controllers.banner.dao;

/**
 * Created by rodking on 16/8/4.
 */
public class PresetBanner {
    protected int id; // banner id
    protected boolean isOpenTime; // 是否使用开服时间
    protected String sendNormalTime;    // 使用普通时间
    protected int sendOpenTime; // 使用开服时间
    protected int srvs[]; // 需要发送的服务器
    protected String banners[]; // 弹窗图名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpenTime() {
        return isOpenTime;
    }

    public void setOpenTime(boolean openTime) {
        isOpenTime = openTime;
    }

    public String getSendNormalTime() {
        return sendNormalTime;
    }

    public void setSendNormalTime(String sendNormalTime) {
        this.sendNormalTime = sendNormalTime;
    }

    public int getSendOpenTime() {
        return sendOpenTime;
    }

    public void setSendOpenTime(int sendOpenTime) {
        this.sendOpenTime = sendOpenTime;
    }

    public int[] getSrvs() {
        return srvs;
    }

    public void setSrvs(int[] srvs) {
        this.srvs = srvs;
    }

    public String[] getBanners() {
        return banners;
    }

    public void setBanners(String[] banners) {
        this.banners = banners;
    }
}
