package cn.saturn.web.controllers.mail.dao;

public class ActionResult {
	public static final int code_succeed = 1;
	public static final int code_error = 0;
	public final int code;
	public final String msg;

	public ActionResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public boolean isSucceed() {
		return code > 0;
	}

	public static ActionResult Ok(String msg) {
		return new ActionResult(code_succeed, msg);
	}

	public static ActionResult Error(String msg) {
		return new ActionResult(code_error, msg);
	}
}
