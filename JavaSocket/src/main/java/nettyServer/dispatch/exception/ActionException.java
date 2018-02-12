package nettyServer.dispatch.exception;

/**
 * @author yangxp
 *
 */
public class ActionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ActionException() {
		super();
	}

	public ActionException(String msg) {
		super(msg);
	}
}
