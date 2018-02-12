package cn.saturn.web.controllers.server.dao;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.saturn.web.model.auto.ModelListManager;
import zyt.spring.component.ComponentManager;
import zyt.utils.ListUtils;

@Component
public class VersionManager extends ModelListManager<VersionModel, VersionDAO> implements ComponentManager.IComponent {
	public static final int defualID = 1;
	public static final String defualPlatform = "all";

	@Resource
	private VersionDAO dao;

	@Override
	public VersionDAO getDAO() {
		return this.dao;
	}

	public final static Comparator<VersionModel> comparator = new Comparator<VersionModel>() {
		@Override
		public int compare(VersionModel o1, VersionModel o2) {
			String a = o1.getVersion();
			String b = o2.getVersion();
			int c = a.compareTo(b);
			if (c > 0) {
				return 1;
			} else if (c == 0) {
				return 0;
			}
			return -1;
		}
	};

	@Override
	public boolean reload(ApplicationContext context) {
		init(); // 初始化

		// 从数据库读取数据
		List<VersionModel> list = this.getListByDAO();
		int count = (list != null) ? list.size() : 0;
		if (count > 0) {
			this.list.addAll(list);
			// 排序
			// Collections.sort(this.list, comparator);
		}

		// 检测是否存在默认版本信息
		VersionModel model = this.find(defualID);
		if (model == null) {
			long id = this.newId(); // 占用1号ID
			id = 1L;
			// 创建默认版本号
			model = new VersionModel();
			model.setId(id);
			model.setPlatform(defualPlatform);
			model.setUrl("");
			model.setVersion("");
			model.setNotice("");
			this.add(model);
		}
		return true;
	}

	@Override
	public void release() {

	}

	@Override
	protected void insertByDAO(VersionModel model) {
		this.getDAO().insertOrUpdate(model);
	}

	@Override
	protected boolean updateByDAO(VersionModel model) {
		this.getDAO().insertOrUpdate(model);
		return false;
	}

	@Override
	public List<VersionModel> getList() {
		return super.getList();
	}

	@Override
	public int getSize() {
		return super.getSize();
	}

	@Override
	public List<VersionModel> getList(int start, int size) {
		return super.getList(start, size);
	}

	@Override
	public VersionModel find(long id) {
		return super.find(id);
	}

	// public List<VersionModel> findByPlatform(String platform) {
	// return findByPlatform(list.getList(), platform);
	// }

	public static List<VersionModel> findByPlatform(List<VersionModel> list, String platform) {
		if (platform == null || platform.length() <= 0) {
			return null; // 过掉
		}

		ListUtils.IFilter<VersionModel> filter = new ListUtils.IFilter<VersionModel>() {
			@Override
			public boolean check(VersionModel d) {
				String checkPlatform = d.getPlatform();
				if (checkPlatform == null || checkPlatform.length() <= 0) {
					return false; // 过掉
				}
				// 判断是否相等
				return checkPlatform.equals(platform);
			}
		};
		return ListUtils.findAll(list, filter, 0);
	}
	
	public VersionModel find1(String platform, String version) {
		List<VersionModel> list = find0(platform, version, 1);
		int count = (list != null) ? list.size() : 0;
		if (count > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<VersionModel> find0(String platform, String version, int maxCount) {
		if (platform == null || platform.length() <= 0) {
			return null; // 过掉
		}
		// platform = (platform != null) ? platform : "";
		final String version0 = (version != null) ? version : "";

		ListUtils.IFilter<VersionModel> filter = new ListUtils.IFilter<VersionModel>() {
			@Override
			public boolean check(VersionModel d) {
				// 渠道
				String checkPlatform = d.getPlatform();
				if (StringUtils.isEmpty(checkPlatform)) {
					return false; // 过掉
				}

				boolean cp = checkPlatform.equals(platform);

				String v0 = d.getVersion();
				boolean cv = version0.equals(v0);

				// 判断是否相等
				return cp && cv;
			}
		};
		return list.findAll(filter, maxCount);
	}

	@Override
	public VersionModel remove(long id) {
		return super.remove(id);
	}

	@Override
	public boolean add(VersionModel model) {
		if (super.add(model)) {
			// Collections.sort(this.list, comparator);
			return true;
		}
		return false;
	}

	@Override
	public boolean update(VersionModel model) {
		return super.update(model);
	}

}
