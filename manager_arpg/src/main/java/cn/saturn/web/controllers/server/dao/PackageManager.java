package cn.saturn.web.controllers.server.dao;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import zyt.spring.component.ComponentManager;
import zyt.utils.ListUtils;
import cn.saturn.web.model.auto.ModelListManager;

@Component
public class PackageManager extends ModelListManager<PackageModel, PackageDAO> implements ComponentManager.IComponent {
	@Resource
	private PackageDAO dao;

	public final static Comparator<PackageModel> comparator = new Comparator<PackageModel>() {
		@Override
		public int compare(PackageModel o1, PackageModel o2) {
			String a = o1.getVersion();
			String b = o2.getVersion();
			int c = a.compareTo(b);
			if (c > 0) {
				return 1;
			} else if (c == 0) {
				int a1 = o1.getResversion();
				int b1 = o2.getResversion();
				return (a1 > b1) ? 1 : (a1 == b1) ? 0 : -1;
			}
			return -1;
		}
	};

	@Override
	public boolean reload(ApplicationContext context) {
		init(); // 初始化

		// 从数据库读取数据
		List<PackageModel> list = this.getListByDAO();
		int count = (list != null) ? list.size() : 0;
		if (count > 0) {
			this.list.addAll(list);
		}
		return true;
	}

	@Override
	public void release() {
	}

	@Override
	protected PackageDAO getDAO() {
		return dao;
	}

	@Override
	protected void insertByDAO(PackageModel model) {
		this.getDAO().insertOrUpdate(model);
	}

	@Override
	protected boolean updateByDAO(PackageModel model) {
		this.getDAO().insertOrUpdate(model);
		return true;
	}

	@Override
	public List<PackageModel> getList() {
		return super.getList();
	}

	@Override
	public int getSize() {
		return super.getSize();
	}

	@Override
	public List<PackageModel> getList(int start, int size) {
		return super.getList(start, size);
	}

	@Override
	public PackageModel find(long id) {
		return super.find(id);
	}

	// public List<PackageModel> findByPlatform(String platform) {
	// if (platform == null || platform.length() <= 0) {
	// return null; // 过掉
	// }
	//
	// ListUtils.IFilter<PackageModel> filter = new ListUtils.IFilter<PackageModel>() {
	// @Override
	// public boolean check(PackageModel d) {
	// String checkPlatform = d.getPlatform();
	// if (checkPlatform == null || checkPlatform.length() <= 0) {
	// return false; // 过掉
	// }
	// // 判断是否相等
	// return checkPlatform.equals(platform);
	// }
	// };
	// return list.findAll(filter, 0);
	// }

	// public List<VersionModel> findByPlatform(String platform) {
	// return findByPlatform(list.getList(), platform);
	// }

	public static List<PackageModel> findByPlatform(List<PackageModel> list, String platform, String version) {
		if (platform == null || platform.length() <= 0) {
			return null; // 过掉
		}

		ListUtils.IFilter<PackageModel> filter = new ListUtils.IFilter<PackageModel>() {
			@Override
			public boolean check(PackageModel d) {
				String checkPlatform = d.getPlatform();
				if (checkPlatform == null || checkPlatform.length() <= 0) {
					return false; // 过掉
				}
				// 判断是否相等
				if (!checkPlatform.equals(platform)) {
					return false;
				}

				// 判断大版本信息
				return d.getVersion().equals(version);
			}
		};
		return ListUtils.findAll(list, filter, 0);
	}
	
	public static List<PackageModel> findByPlatformAndType(List<PackageModel> list, String platform, String version,int type) {
		if (platform == null || platform.length() <= 0) {
			return null; // 过掉
		}

		ListUtils.IFilter<PackageModel> filter = new ListUtils.IFilter<PackageModel>() {
			@Override
			public boolean check(PackageModel d) {
				String checkPlatform = d.getPlatform();
				if(type != d.getType())
					return false;// 过掉
				
				if (checkPlatform == null || checkPlatform.length() <= 0) {
					return false; // 过掉
				}
				// 判断是否相等
				if (!checkPlatform.equals(platform)) {
					return false;
				}

				// 判断大版本信息
				return d.getVersion().equals(version);
			}
		};
		return ListUtils.findAll(list, filter, 0);
	}

	public PackageModel find1(String platform, String version, int resvers) {
		List<PackageModel> list = find0(platform, version, resvers, 1);
		int count = (list != null) ? list.size() : 0;
		if (count > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<PackageModel> find0(String platform, String version, int resvers, int maxCount) {
		if (platform == null || platform.length() <= 0) {
			return null; // 过掉
		}
		// platform = (platform != null) ? platform : "";
		final String version0 = (version != null) ? version : "";
		final int resvers0 = resvers;

		ListUtils.IFilter<PackageModel> filter = new ListUtils.IFilter<PackageModel>() {
			@Override
			public boolean check(PackageModel d) {
				// 渠道
				String checkPlatform = d.getPlatform();
				if (StringUtils.isEmpty(checkPlatform)) {
					return false; // 过掉
				}

				boolean cp = checkPlatform.equals(platform);

				String v0 = d.getVersion();
				boolean cv = version0.equals(v0);

				int rv0 = d.getResversion();
				boolean crv = resvers0 == rv0;

				// 判断是否相等
				return cp && cv && crv;
			}
		};
		return list.findAll(filter, maxCount);
	}

	@Override
	public PackageModel remove(long id) {
		return super.remove(id);
	}

	@Override
	public boolean add(PackageModel model) {
		if (super.add(model)) {
			// Collections.sort(this.list, comparator);
			return true;
		}
		return false;
	}

	@Override
	public boolean update(PackageModel model) {
		return super.update(model);
	}

}
