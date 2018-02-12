package nettyServer.util;

import org.apache.http.HttpResponse;

/**
 * 
 * 
 * @author yangxp
 */
public interface HttpResponseHandler {

	public void handle(HttpResponse resp);
	
}
