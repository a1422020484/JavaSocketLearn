package cn.saturn.web.utils;

import java.util.List;

public class ResultMsgUtils {

	/**
	 * 将 List<> 转化为 html <br>
	 * 
	 * @param resultMsg
	 * @return
	 */
	@SafeVarargs
	public static String getResult2Msg(List<ResultMsg>... resultMsg) {
		StringBuilder sb = new StringBuilder();
		for (List<ResultMsg> msgs : resultMsg) {
			if (msgs != null) {
				for (ResultMsg msg : msgs)
					sb.append(msg.toHtmlString());
			}
		}

		return sb.toString();
	}
}
