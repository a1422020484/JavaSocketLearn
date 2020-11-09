package httpTest;

import httpTest.GameClient.Response;

public interface ResponseHandler {
	public int getHeadId();

	public void handle(Response r) throws Exception;

	public boolean isEncrypted();

	public boolean isSystem();

}
