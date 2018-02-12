package cn.saturn.web.model.auto;

public interface ModelDAO<T extends IModel> {

	void insertOrUpdate(T model);

	void remove(long id);

	public Long getMaxId();
}
