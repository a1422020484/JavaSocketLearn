package nettyServer.templet.refdata;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import nettyServer.util.resource.ResourceLoader;

/**
 * @author yangxp 
 */
@Component
public class TempletDataModelManager extends ResourceLoader {

	private static Map<Integer, TempletDataModel> models;

	@Override
	public void load(InputStream is) throws IOException {
		XStream xs = new XStream();
		xs.ignoreUnknownElements();
		xs.alias("list", List.class);
		xs.alias("templetData", TempletDataModel.class);
		@SuppressWarnings("unchecked")
		List<TempletDataModel> list = (List<TempletDataModel>) xs.fromXML(is);
		TempletDataModelManager.models = Collections.unmodifiableMap(list.stream().collect(Collectors.toMap(TempletDataModel::getMineId, m -> m)));
	}

	public static TempletDataModel getModels(int mineId) {
		TempletDataModel mineDataModel = models.get(mineId);
		if (mineDataModel != null) {
			return mineDataModel;
		}
		return null;
	}
	
	public static Map<Integer, TempletDataModel> getModels() {
		return models;
	}

	@Override
    public String getResourceName() {
        return "templetData";
    }
}
