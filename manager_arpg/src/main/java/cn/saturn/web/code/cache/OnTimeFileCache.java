package cn.saturn.web.code.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.saturn.web.code.update.ServerTimer;
import cn.saturn.web.utils.LogType;

/**
 * 定时文件缓存 <br>
 * {@link ServerTime} 继承 <br>
 * 20 分钟刷新一次(刷新缓存)动态更新内容<br>
 * 
 * @author rodking
 *
 */
public abstract class OnTimeFileCache extends ServerTimer {
	public static final Logger log = LoggerFactory.getLogger(LogType.fileCache);
	public long modifyTime = 0l;

	public OnTimeFileCache(int id, String name, int intervalTime) {
		super(id, name, intervalTime);
	}

	public OnTimeFileCache() {
		super(1, "fileCache", 20 * 60 * 1000);
	}

	/**
	 * 获取资源路径<br>
	 * 
	 * @return
	 */
	public abstract String getReadResPath();

	/**
	 * 写资源位置 <br>
	 * 
	 * @return
	 */
	public String getWriteResName() {
		return "";
	}

	/**
	 * 读取文件 <br>
	 */
	public synchronized boolean load() {
		InputStream is = null;
		File f = new File(getReadResPath());
		try {
			is = new FileInputStream(f);
			modifyTime = f.lastModified();
			onLoad(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	protected abstract void onLoad(InputStream is) throws IOException;

	//protected abstract boolean writeFile(List list);
	// public boolean writeFile(List<? extends Object> list);

	@Override
	protected void update(int count) {
		if (isFileUpdate()) {
			load();
		}
	}

	/**
	 * 是否重新加载 (如果文件有修改过)<br>
	 * <code> dx = lastModified - modifyTime</code><br>
	 * <code> if dx > 0</code>
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean isFileUpdate() {
		File f = new File(getReadResPath());
		if (null != f)
			return (f.lastModified() - modifyTime) > 0;

		return false;
	}
}
