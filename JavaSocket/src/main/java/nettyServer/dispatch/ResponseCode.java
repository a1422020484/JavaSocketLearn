package nettyServer.dispatch;

/**
 * 返回码
 *
 * @author zuojie.x
 */
public enum ResponseCode {
	/**
	 * 正常返回
	 */
	OK(0, "正常"),
	/**
	 * 业务异常
	 */
	FAILURE(1, "业务异常"), ;

	public final int code;
	public final String desc;

	private ResponseCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
