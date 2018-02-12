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
import cn.saturn.web.utils.FileUtils;
import cn.saturn.web.utils.Utils;
import zyt.spring.component.ComponentManager;

/**
 * {@link OnTimeFileCache}<br>
 * 带有读取,保存接口<br>
 * 
 * @author rodking
 *
 */
@Component
public class ChestModelManager extends OnTimeFileCache implements ComponentManager.IComponent {
	protected static Map<Integer, ChestModel> models;
	protected static List<ChestModel> modelList;

	private static final String fileName = "chest.xml";
	public static final String writeSingle = "send";
	private static final String readPath = Utils.resourceFolder + "game/" + fileName;
	private static final String writePath = Utils.resourceFolder + writeSingle + "/socket/conf/resource/" + fileName;
	
	
	/** 根据chestId获取数据 */
	public static ChestModel get(int chestId) {
		return models.get(chestId);
	}

	/** 获取数据数组 */
	public static List<ChestModel> getModels() {
		return modelList;
	}

	@Override
	public String getReadResPath() {
		return readPath;
	}

	@Override
	public String getWriteResName() {
		return writePath;
	}

	@Override
	public void onLoad(InputStream is) throws IOException {
		log.info(" start load {}", fileName);
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("chest", ChestModel.class);

		@SuppressWarnings("unchecked")
		List<ChestModel> list = (List<ChestModel>) xs.fromXML(is);
		Map<Integer, ChestModel> models = new HashMap<Integer, ChestModel>();
		for (ChestModel d : list) {
			models.put(d.chestId, d);
		}
		ChestModelManager.models = Collections.unmodifiableMap(models);
		ChestModelManager.modelList = Collections.unmodifiableList(list);

		log.info("load end {}", fileName);
	}

	public boolean writeFile(List<? extends ChestModel> list) {
		if (list == null)
			return false;

		XStream xs = new XStream();
		xs.autodetectAnnotations(true);
		xs.alias("list", List.class);
		xs.alias("chest", ChestModel.class);
		xs.setMode(XStream.NO_REFERENCES);
		return FileUtils.writeXmlFile(xs, getWriteResName(), list);
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
