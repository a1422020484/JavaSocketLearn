package cn.saturn.web.code.cache;

import java.util.ArrayList;
import java.util.List;
import cn.saturn.web.code.update.ServerTimer;

/**
 * dao 缓存数据<br>
 * 定点清理数据 20分钟 清理一次<br>
 * 
 * @author rodking
 *
 */
public abstract class ModelDaoCacheDestory<V> extends ServerTimer implements CacheDestory {
	public long modifyTime = 0l;

	public List<V> lists = new ArrayList<>();

	public ModelDaoCacheDestory() {
		super(1, "ModelDaoCache", 20 * 60 * 1000);
	}

	/**
	 * <code> if lists == null </code><br>
	 * <code>load reas</code><br>
	 * 
	 * @return
	 */
	public synchronized boolean load() {
		// 刷新更新时间
		updateModifyTime();
//		if (lists.size() == 0) {
//			onLoad();
//		}
		lists.clear();
		onLoad();
		return true;
	}

	protected abstract boolean onLoad();

	public boolean destory() {
		lists.clear();
		return true;
	}

	@Override
	protected void update(int count) {
		// 定点时间到了就清理数据
		destory();
	}

	/**
	 * 更新时间<br>
	 */
	public void updateModifyTime() {
		modifyTime = System.currentTimeMillis();
	}
}
