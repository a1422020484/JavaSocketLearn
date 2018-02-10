package nettyServer.dispatch;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author xiezuojie
 */
public class DefaultMultiResponse implements MultiResponse {
	
	List<Response> responses;

	public DefaultMultiResponse() {
		responses = new ArrayList<Response>(4);
	}
	
	@Override
	public List<Response> getResponses() {
		return responses;
	}

	@Override
	public String toString() {
		return "MultiResponse [responses=" + responses + "]";
	}

}
