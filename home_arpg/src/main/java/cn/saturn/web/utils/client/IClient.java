package cn.saturn.web.utils.client;

public interface IClient {

	public <T> T call(int actionId, Object msg, Class<T> clazz);

	public String getErrStr();
}
