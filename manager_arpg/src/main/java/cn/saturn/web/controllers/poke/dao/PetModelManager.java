package cn.saturn.web.controllers.poke.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

@Component
public class PetModelManager extends OnTimeFileCache implements ComponentManager.IComponent {

	protected static List<PetModel> models;
	protected static Map<Integer, PetModel> modelMap;

	private static final String fileName = "pets.xml";
	private static final String readPath = Utils.resourceFolder + "game/" + fileName;


	public static List<PetModel> gets() {
		return models;
	}

	@Override
	public String getReadResPath() {
		return readPath;
	}

	public void onLoad(InputStream is) throws IOException {
		log.info(" start load {}", fileName);
		XStream xs = new XStream();
		xs.ignoreUnknownElements();
		xs.alias("list", List.class);
		xs.alias("pets", PetModel.class);

		@SuppressWarnings("unchecked")
		List<PetModel> list = (List<PetModel>) xs.fromXML(is);

		final List<PetModel> models = new ArrayList<>();
		for (PetModel d : list) {
			models.add(d);
		}

		log.info("load end {}", fileName);
		PetModelManager.models = Collections.unmodifiableList(models);
		modelMap = new HashMap<>();
		if(models != null){
			for(PetModel model : models){
				modelMap.put(model.getPetId(), model);
			}
		}
	}
	
	public static List<PetModel> getModels(){
		return models;
	}
	
	public static PetModel getModelByPetId(int petId){
		return modelMap.get(petId);
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
