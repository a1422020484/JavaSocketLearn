package cn.saturn.web.controllers.winpush.dao;

/**
 * Created by rodking on 16/8/4.
 */
public class PresetWinpush {
    protected int id; // banner id
    protected boolean isOpenTime; // 是否使用开服时间
    protected String sendNormalTime;    // 使用普通时间
    protected int sendOpenTime; // 使用开服时间
    protected int srvs[]; // 需要发送的服务器
    protected String pushs[]; // 弹窗图名称
    protected Integer orders[]; // 弹窗图顺序

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

	public String[] getPushs() {
		return pushs;
	}

	public void setPushs(String[] pushs) {
		this.pushs = pushs;
	}

	public Integer[] getOrders() {
		return orders;
	}

	public void setOrders(Integer[] orders) {
		this.orders = orders;
	}

}
