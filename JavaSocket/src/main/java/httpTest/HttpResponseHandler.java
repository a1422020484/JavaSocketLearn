package httpTest;

import org.apache.http.HttpResponse;

public interface HttpResponseHandler {
	public void handler(HttpResponse resp);
}
