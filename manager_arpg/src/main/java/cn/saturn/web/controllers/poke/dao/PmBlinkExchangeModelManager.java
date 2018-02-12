package cn.saturn.web.controllers.poke.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import cn.saturn.web.code.cache.OnTimeFileCache;
import cn.saturn.web.utils.Utils;
import zyt.spring.component.ComponentManager;

@Component
public class PmBlinkExchangeModelManager extends OnTimeFileCache implements ComponentManager.IComponent {

	protected static List<PmBlinkExchangeModel> models;

	private static final String fileName = "pmBlinkExchange.xml";
	private static final String readPath = Utils.resourceFolder + "game/" + fileName;


	public static List<PmBlinkExchangeModel> gets() {
		return models;
	}

	@Override
	public String getReadResPath() {
		return readPath;
	}

	public void onLoad(InputStream is) throws IOException {
		log.info(" start load {}", fileName);
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("pmBlinkExchange", PmBlinkExchangeModel.class);

		@SuppressWarnings("unchecked")
		List<PmBlinkExchangeModel> list = (List<PmBlinkExchangeModel>) xs.fromXML(is);

		final List<PmBlinkExchangeModel> models = new ArrayList<>();
		for (PmBlinkExchangeModel d : list) {
			models.add(d);
		}

		log.info("load end {}", fileName);
		PmBlinkExchangeModelManager.models = Collections.unmodifiableList(models);
	}
	
	public static List<PmBlinkExchangeModel> getModels(){
		return models;
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
