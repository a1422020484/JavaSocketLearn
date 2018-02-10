package nettyServer.util;

import org.apache.http.HttpResponse;

/**
 * 
 * 
 * @author xiezuojie
 */
public interface HttpResponseHandler {

	public void handle(HttpResponse resp);
	
}
