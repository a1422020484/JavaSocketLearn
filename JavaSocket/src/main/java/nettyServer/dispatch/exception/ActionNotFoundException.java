package nettyServer.dispatch.exception;

/**
 * @author yangxp
 */
public class ActionNotFoundException extends ActionException {
	/**  */
	private static final long serialVersionUID = 1L;

	public ActionNotFoundException() {
	}

	public ActionNotFoundException(int actionId) {
		super("没有找到控制器,id:" + actionId);
	}

	public ActionNotFoundException(String actionName) {
		super("没有找到控制器,key:" + actionName);
	}
}
