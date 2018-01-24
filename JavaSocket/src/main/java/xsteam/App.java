package xsteam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;

import xsteam.enity.AbilityModel;

/**
 * @author Administrator xml文件与之实体之间的转换关系。使用XStream来进行转换
 */
public class App {

	private static Map<Integer, AbilityModel> models;

	public static void main(String[] args) throws FileNotFoundException {
		File rootFile = new File("./src/test/resources/resource/ability.xml");
		System.out.println(rootFile.getName());
		InputStream in = new FileInputStream(rootFile);
		XStream xs = new XStream();
		xs.alias("list", List.class);
		xs.alias("ability", AbilityModel.class);
		@SuppressWarnings("unchecked")
		List<AbilityModel> list = (List<AbilityModel>) xs.fromXML(in);
		models = list.stream().collect(Collectors.toMap(AbilityModel::getPetAbilityId, m -> m, (u, v) -> v));
		for (Integer element : models.keySet()) {
			System.out.println(models.get(element).getHp());
		}
	}
}
