package cn.saturn.web.controllers.banner.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.thoughtworks.xstream.XStream;
import cn.saturn.web.code.cache.OnTimeFileCache;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.utils.FileUtils;
import cn.saturn.web.utils.Utils;
import zyt.spring.component.ComponentManager.IComponent;

/**
 * {@link OnTimeFileCache}<br>
 * 保存接口<br>
 * 
 * @author rodking
 *
 */
@Component
public class BannerModelManager extends OnTimeFileCache implements IComponent {

	protected static Map<Integer, ChestModel> models;
	protected static List<ChestModel> modelList;
	public static final String writeSingle = "send03";
	private static final String fileName = "billboard.xml";
	private static final String readPath = Utils.resourceFolder + "game/" + fileName;
	private static final String writePath = Utils.resourceFolder + writeSingle + "/socket/conf/resource/" + fileName;

	@Override
	public boolean reload(ApplicationContext context) {
		return load();
	}

	@Override
	@Deprecated
	public void release() {
	}

	@Override
	public String getWriteResName() {
		return writePath;
	}

	@Override
	public String getReadResPath() {
		return readPath;
	}

	@Override
	protected void onLoad(InputStream is) throws IOException {
		log.info(" not load {}", fileName);
	}

	public boolean writeFile(List<? extends BannerModel> list) {
		if (list == null)
			return false;

		XStream xs = new XStream();
		xs.autodetectAnnotations(true);
		xs.alias("list", List.class);
		xs.alias("billboard", BannerModel.class);
		xs.setMode(XStream.NO_REFERENCES);
		return FileUtils.writeXmlFile(xs, getWriteResName(), list);
	}

}
