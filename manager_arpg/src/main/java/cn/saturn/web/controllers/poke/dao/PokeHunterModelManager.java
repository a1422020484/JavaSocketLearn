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
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.FileUtils;
import cn.saturn.web.utils.ProtocolTools;
import cn.saturn.web.utils.StringExtUtil;
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
public class PokeHunterModelManager extends OnTimeFileCache implements ComponentManager.IComponent {
	/** 初级狩猎场 */
	public final static int first_poker_hunter = 1;
	/** 中级狩猎场 */
	public final static int second_poker_hunter = 2;
	/** 高级狩猎场 */
	public final static int third_poker_hunter = 3;

	protected static List<PokeHunterModel> models;
	protected static List<PokeHunterModel> firstModels;
	protected static List<PokeHunterModel> secondModels;
	protected static List<PokeHunterModel> thirdModels;

	public static final String writeSingle = "send0";
	private static final String fileName = "pokeHunter.xml";
	private static final String readPath = Utils.resourceFolder + "game/" + fileName;
	private static final String writePath = Utils.resourceFolder + writeSingle + "/socket/conf/resource/" + fileName;

	public static List<PokeHunterModel> getListByType(int id) {
		if (id == first_poker_hunter)
			return firstModels;
		if (id == second_poker_hunter)
			return secondModels;
		return thirdModels;
	}

	public static List<PokeHunterModel> gets() {
		return models;
	}

	@Override
	public String getReadResPath() {
		return readPath;
	}

	@Override
	public String getWriteResName() {
		return writePath;
	}

	public void onLoad(InputStream is) throws IOException {
		log.info(" start load {}", fileName);
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("pokeHunter", PokeHunterModel.class);

		@SuppressWarnings("unchecked")
		List<PokeHunterModel> list = (List<PokeHunterModel>) xs.fromXML(is);

		final List<PokeHunterModel> models = new ArrayList<>();
		final List<PokeHunterModel> first = new ArrayList<>();
		final List<PokeHunterModel> second = new ArrayList<>();
		final List<PokeHunterModel> third = new ArrayList<>();
		for (PokeHunterModel d : list) {
			models.add(d);
			if (d.getHuntingType() == first_poker_hunter)
				first.add(d);
			if (d.getHuntingType() == second_poker_hunter)
				second.add(d);
			if (d.getHuntingType() == third_poker_hunter)
				third.add(d);
		}

		log.info("load end {}", fileName);
		PokeHunterModelManager.models = Collections.unmodifiableList(models);
		PokeHunterModelManager.firstModels = Collections.unmodifiableList(first);
		PokeHunterModelManager.secondModels = Collections.unmodifiableList(second);
		PokeHunterModelManager.thirdModels = Collections.unmodifiableList(third);
	}

	@Override
	public boolean reload(ApplicationContext context) {
		return load();
	}

	/**
	 * 替换部分精灵并重构文件<br>
	 * 
	 * @param pPoke
	 * @return
	 */
	public boolean writeReplace(PresetPokeHunter pPoke) {
		List<PokeHunterModel> list = new ArrayList<>();
		// 修改从文件中拿取的需要更换的数据
		for (PokeHunterModel m : models) {
			if (m != null) {
				if (pPoke != null) {
					if (m.getPokeId() == pPoke.getFirstId()
							&& m.getHuntingType() == PokeHunterModelManager.first_poker_hunter) {
						list.add(PokeHunterModel.create(m, pPoke.getFirst_min_bd(), pPoke.getFirst_max_bd()));
						continue;
					} else if (m.getPokeId() == pPoke.getSecondId()
							&& m.getHuntingType() == PokeHunterModelManager.second_poker_hunter) {
						list.add(PokeHunterModel.create(m, pPoke.getSecond_min_bd(), pPoke.getSecond_max_bd()));
						continue;
					} else if (m.getPokeId() == pPoke.getThirdId()
							&& m.getHuntingType() == PokeHunterModelManager.third_poker_hunter) {
						list.add(PokeHunterModel.create(m, pPoke.getThird_min_bd(), pPoke.getThird_max_bd()));
						continue;
					}
				}
				list.add(m);
			}
		}

		return writeFile(list);
	}

	public void sendRefreshCode(List<ServerModel> srvlist) {
		// 刷新指令
		for (ServerModel serverModel : srvlist) {
			List<String> cmds = StringExtUtil.toList("/refreshHunt 3", ";");
			ProtocolTools.toSendGMCmdWSC(serverModel, cmds);
		}
	}

	private boolean writeFile(List<? extends PokeHunterModel> list) {
		if (list == null)
			return false;
		XStream xs = new XStream();
		xs.autodetectAnnotations(true);
		xs.alias("list", List.class);
		xs.alias("pokeHunter", PokeHunterModel.class);
		xs.setMode(XStream.NO_REFERENCES);
		return FileUtils.writeXmlFile(xs, getWriteResName(), list);
	}

	@Override
	@Deprecated
	public void release() {
	}
}
