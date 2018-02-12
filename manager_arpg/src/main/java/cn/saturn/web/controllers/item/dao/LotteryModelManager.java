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
public class LotteryModelManager implements ComponentManager.IComponent {
	
	private static Map<Integer, LotteryModel> models;
	private static List<LotteryModel> modelList;
	
	public static final Logger log = LoggerFactory.getLogger(LogType.core);

	public static List<LotteryModel> getModels() {
		return modelList;
	}
	
	public static LotteryModel getModelById(int lotteryId) {
		LotteryModel LotteryModel=null;
		for(LotteryModel model:modelList){
			if(lotteryId==model.getLotteryId()){
				LotteryModel=model;
			}
		}
		return LotteryModel;
	}
	
	public String getResourceName() {
		return Utils.resourceFolder + "game/lottery.xml";
	}

	public void load(InputStream is) throws IOException {
		log.info(" start load game/lottery.xml");
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("lottery", LotteryModel.class);

		@SuppressWarnings("unchecked")
		List<LotteryModel> list = (List<LotteryModel>) xs.fromXML(is);
		Map<Integer, LotteryModel> models = new HashMap<>();
		for (LotteryModel m : list) {
			models.put(m.getLotteryId(), m);
		}
		log.info("load end game/lottery.xml");
		LotteryModelManager.models = Collections.unmodifiableMap(models);
		LotteryModelManager.modelList = Collections.unmodifiableList(list);

		
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
