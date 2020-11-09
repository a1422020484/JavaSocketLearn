package nettyServer.util;

import javax.annotation.Resource;

import nettyServer.dispatch.Request;

/**
 * 
 * 
 * @author yangxp
 */
public abstract class AbstractAction {
	@Resource protected Request req;
}
