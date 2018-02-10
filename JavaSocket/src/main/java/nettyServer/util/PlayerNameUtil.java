package nettyServer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.springframework.stereotype.Component;

import nettyServer.util.resource.ResourceLoader;

/**
 * 随机玩家昵称
 * 
 * @author zuojie.x
 */
@Component
public final class PlayerNameUtil extends ResourceLoader {
	private static String[] names1;
	private static String[] names2;
	private static String[] names3;
	
	public static final char[] characters = new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	/**
	 * @param gender 性别,0男 1女
	 * @return 随机获取名称
	 */
	public static String randomName() {
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		builder.append(PlayerNameUtil.names1[random.nextInt(PlayerNameUtil.names1.length)]);
		builder.append(PlayerNameUtil.names2[random.nextInt(PlayerNameUtil.names2.length)]);
		builder.append(PlayerNameUtil.names3[random.nextInt(PlayerNameUtil.names3.length)]);
		return builder.toString();
	}

	@Override
	public void load(InputStream is) throws IOException {
		Properties prop = new Properties();
		prop.load(is);
		names1 = prop.getProperty("n1").split("\\,");
		names2 = prop.getProperty("n2").split("\\,");
		names3 = prop.getProperty("n3").split("\\,");
	}
	
	@Override
	public String getResourceName() {
		return "NameWords";
	}
	
//	private static void load() {
//		try {
//			InputStream is = PlayerNameUtil.class
//					.getResourceAsStream(CoreConfig.stringValue("ResourceFolder") + 
//							"/NameWords.properties");
//			Properties prop = new Properties();
//			prop.load(is);
//			names1 = prop.getProperty("n1").split("\\,");
//			names2 = prop.getProperty("n2").split("\\,");
//			names3 = prop.getProperty("n3").split("\\,");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
