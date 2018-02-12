package cn.saturn.web.controllers.item.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.thoughtworks.xstream.XStream;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.Utils;
import zyt.spring.component.ComponentManager;

@Component
public class ItemModelManager implements ComponentManager.IComponent {
	private static Map<Integer, ItemModel> models;
	private static Map<Integer, ItemModel> petIdModels;
	/** 除了按照类型获取外,其他地方没有用到, 不需要考虑宝箱 **/
	private static List<ItemModel> modelList;
	public static final Logger log = LoggerFactory.getLogger(LogType.core);

	public static List<ItemModel> getModels() {
		return modelList;
	}

	public static ItemModel getById(int petId) {
		return models.get(petId);
	}

	public static ItemModel getByPetId(int petId) {
		return petIdModels.get(petId);
	}

	public String getResourceName() {
		return Utils.resourceFolder + "game/items.xml";
	}

	public void load(InputStream is) throws IOException {
		log.info(" start load game/items.xml");
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("items", ItemModel.class);

		@SuppressWarnings("unchecked")
		List<ItemModel> list = (List<ItemModel>) xs.fromXML(is);
		Map<Integer, ItemModel> models = new HashMap<>();
		Map<Integer, ItemModel> petIdModels = new HashMap<>();
		for (ItemModel m : list) {
			models.put(m.getItemId(), m);
			if (m.getPetId() > 0) {
				petIdModels.put(m.getPetId(), m);
			}
		}
		log.info("load end game/items.xml");
		ItemModelManager.models = Collections.unmodifiableMap(models);
		ItemModelManager.petIdModels = Collections.unmodifiableMap(petIdModels);
		ItemModelManager.modelList = Collections.unmodifiableList(list);
	}

	@Override
	public boolean reload(ApplicationContext context) {
		InputStream is = null;
		try {
			is = new FileInputStream(new File(getResourceName()));
			load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

}
