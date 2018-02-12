package cn.saturn.web.model.auto;

import java.util.List;

import zyt.component.list.LockList;
import zyt.utils.ListUtils;

public abstract class ModelListManager<T extends IModel, D extends ModelDAO<T>> extends ModelManager<T, D> {
	protected LockList<T> list = new LockList<>(); // lock 数组

	protected List<T> getList() {
		return list.getList(); // 复制了一份
	}

	protected int getSize() {
		return list.size();
	}

	protected List<T> getList(int start, int size) {
		return list.getList(start, size);
	}

	protected T find(long id) {
		ListUtils.IFilter<T> filter = new ListUtils.IFilter<T>() {
			@Override
			public boolean check(T d) {
				return d.getId() == id;
			}
		};
		return list.find(filter);
	}

	protected T remove(long id) {
		T model = find(id);
		if (model == null) {
			return null;
		}

		// 执行删除
		list.remove(model); // 移除列表
		super.removeByDAO(model);
		return model;
	}

	protected boolean add(T model) {
		this.list.add(model);
		// super.insert(model);

		if (model.getId() <= 0) {
			model.setId(super.newId());
		}
		// this.getDAO().insertOrUpdate(model);
		this.insertByDAO(model);
		return true;
	}

	protected boolean update(T model) {
		// 如果没有ID, 更新ID
		if (model.getId() <= 0) {
			model.setId(super.newId());
		}
		// 更新或者插入
		this.updateByDAO(model);
		return true;
	}

}
