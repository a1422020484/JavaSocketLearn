package cn.saturn.web.utils;

public class PageNoticeUtil {
	public static final int ERROR = -1; // 错误
	public static final int NORMAL = 0; // 普通
	public static final int INFO = 1; // 信息
	public static final int QUESTION = 2; // 问题
	public static final int WARNING = 3; // 警告

	
	public static String notic(int type, String context) 
	{
		return notic(type,context,"");
	}
	
	public static String notic(int type, String context,String url) {
		String typeStr = "";
		String title ="";
		switch (type) {
		case ERROR:
			typeStr = "error";
			title = "错误";
			context = "<div style='color:red'>"+ context+"</div>";
			break;
		case NORMAL:
			typeStr = "";
			title = "提示";
			break;
		case INFO:
			typeStr = "info";
			title = "提示";
			break;
		case QUESTION:
			typeStr = "question";
			title = "疑问";
			break;
		default:
			typeStr = "warning";
			title = "警告";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{\"type\":\"").append(typeStr).append("\"");
		sb.append(",\"title\":\"").append(title).append("\"");
		sb.append(",\"redUrl\":\"").append(url).append("\"");
		sb.append(",\"context\":\"").append(context).append("\"}");
		return sb.toString();
	}
}
