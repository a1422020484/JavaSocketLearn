package hotReload2.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import hotReload.ResourceLoader;


/**
 * @author yangxp 矿石数据
 */
@Component
public class MineralDataModelManager extends ResourceLoader {

	private static Map<Integer, MineralDataModel> models;

	@Override
	public void load(InputStream is) throws IOException {
		XStream xs = new XStream();
		xs.ignoreUnknownElements();
		xs.alias("list", List.class);
		xs.alias("mineralData", MineralDataModel.class);
		@SuppressWarnings("unchecked")
		List<MineralDataModel> list = (List<MineralDataModel>) xs.fromXML(is);
		MineralDataModelManager.models = Collections.unmodifiableMap(list.stream().collect(Collectors.toMap(MineralDataModel::getMineralId, m -> m)));

	}

	@Override
    public String getResourceName() {
        return "mineralData";
    }

}
