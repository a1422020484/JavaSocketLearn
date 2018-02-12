package cn.saturn.web.controllers.chest.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.thoughtworks.xstream.XStream;
import cn.saturn.web.code.cache.OnTimeFileCache;
import cn.saturn.web.utils.Utils;
import zyt.spring.component.ComponentManager;

/**
 * 读取配置 randomNumList.xml <br>
 * {@link OnTimeFileCache} 继承定时缓存文件中的读取类<br>
 * 
 * @author rodking <br>
 */
@Component
public class RandomNumListModelManager extends OnTimeFileCache implements ComponentManager.IComponent {
	protected static Map<Integer, RandomNumListModel> models;
	protected static List<RandomNumListModel> modelList;

	private static final String fileName = "randomNumList.xml";
	private static final String readPath = Utils.resourceFolder + "game/" + fileName;
	// private static final String writePath = Utils.resourceFolder +
	// "send3/socket/conf/resource/" + fileName;

	@Override
	protected void onLoad(InputStream is) throws IOException {
		log.info(" start load {}", fileName);
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("randomNumList", RandomNumListModel.class);
		@SuppressWarnings("unchecked")
		List<RandomNumListModel> list = (List<RandomNumListModel>) xs.fromXML(is);
		Map<Integer, RandomNumListModel> models = new HashMap<Integer, RandomNumListModel>();
		for (RandomNumListModel d : list) {
			models.put(d.getRandomNumListId(), d);
		}

		log.info("load end  {}", fileName);
		RandomNumListModelManager.models = Collections.unmodifiableMap(models);
		RandomNumListModelManager.modelList = Collections.unmodifiableList(list);
	}

	@Override
	public String getReadResPath() {
		return readPath;
	}

	/** 根据randomNumListId获取数据 */
	public static RandomNumListModel get(int randomNumListId) {
		return models.get(randomNumListId);
	}

	/** 获取数据数组 */
	public static List<RandomNumListModel> getModels() {
		return modelList;
	}

	@Override
	public boolean reload(ApplicationContext context) {
		return load();
	}

	@Override
	@Deprecated
	public void release() {
	}
}
