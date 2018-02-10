package nettyServer.dispatch;

import org.springframework.stereotype.Component;

/**
 * Request外观类,提供给控制器类使用
 *
 * @author zuojie.x
 */
@Component
public class RequestFacade implements Request {

    private Request getRequest() {
        return ThreadLocalRequest.get();
    }

    @Override
    public Integer getPlayerId() {
        return getRequest().getPlayerId();
    }
    
    @Override
    public void setSession(Session session) {
    	getRequest().setSession(session);
    }

    @Override
    public Session getSession() {
        return getRequest().getSession();
    }

    @Override
    public String getHostAddress() {
    	Request r = getRequest();
    	return r != null ? getRequest().getHostAddress() : null;
    }

    @Override
    public int getActionId() {
        return getRequest().getActionId();
    }

    @Override
    public Object[] getParams() {
        return getRequest().getParams();
    }

	@Override
	public String getSessionId() {
		return getRequest().getSessionId();
	}
}
