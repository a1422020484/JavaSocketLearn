package cn.saturn.web.controllers.cdk.dao;

/**
 * @author xiezuojie
 */
public class UCGift {

    private String giftId;
    private int type;
    private String content;
    private int count;
    private String title;

    // getter/setter
    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
    public String toString() {
        return "UCGift{" +
                "giftId='" + giftId + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", count=" + count +
                '}';
    }
}
