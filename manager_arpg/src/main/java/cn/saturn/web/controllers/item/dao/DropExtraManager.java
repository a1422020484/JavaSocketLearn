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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.Utils;
import xzj.core.util.resource.ResourceLoader;
import zyt.spring.component.ComponentManager;

@Component
public class DropExtraManager implements ComponentManager.IComponent{
	
	private static Map<Integer, DropExtraModel> models;
	
	private static List<DropExtraModel> modelList;
	public static final Logger log = LoggerFactory.getLogger(LogType.core);

	public static List<DropExtraModel> getModels() {
		return modelList;
	}

	public String getResourceName() {
		return Utils.resourceFolder + "game/dropExtra.xml";
	}

	public void load(InputStream is) throws IOException {
		log.info(" start load game/dropExtra.xml");
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("dropExtra", DropExtraModel.class);

		@SuppressWarnings("unchecked")
		List<DropExtraModel> list = (List<DropExtraModel>) xs.fromXML(is);
		Map<Integer, DropExtraModel> models = new HashMap<>();
		for (DropExtraModel m : list) {
			models.put(m.getDropId(), m);	
		}
		log.info("load end game/dropExtra.xml");
		DropExtraManager.models = Collections.unmodifiableMap(models);
		DropExtraManager.modelList = Collections.unmodifiableList(list);
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
