package nettyServer.util;

import javax.annotation.Resource;

import nettyServer.dispatch.Request;

/**
 * 
 * 
 * @author xiezuojie
 */
public abstract class AbstractAction {
	@Resource protected Request req;
}
