package cn.saturn.web.utils;

public class ResultMsg {
	public final static int succ = 1;
	public final static int fail = 0;

	int type; // 消息类型
	String str; // 消息内容

	public ResultMsg(int type, String str) {
		this.type = type;
		this.str = str;
	}

	public int getType() {
		return type;
	}

	public String getStr() {
		return str;
	}

	public String toHtmlString() {
		StringBuilder sb = new StringBuilder();
		boolean br = type == ResultMsg.fail;
		sb.append("<span");
		sb.append(br ? " style='color:red' >" : ">").append(str);
		sb.append("</span><br/>");

		return sb.toString();
	}

	@Override
	public String toString() {
		return toHtmlString();
	}
}
