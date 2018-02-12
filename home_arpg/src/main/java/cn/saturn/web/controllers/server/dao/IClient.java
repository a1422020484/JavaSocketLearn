package cn.saturn.web.controllers.server.dao;

public interface IClient {
	public <T> T call(int actionId, Object msg, Class<T> clazz);

	public String getErrStr();
}
