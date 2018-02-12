package nettyServer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangxp
 *
 */
public class GameUtils {
	/**
	 * 通用运行时日志
	 */
	public static final Logger RuntimeLog = LoggerFactory.getLogger("game.runtime");
	
	/**
	 * 区服id
	 */
	public static final int ServerId = CoreConfig.intValue("ServerId");

	/**
	 * 合并的服务器id列表
	 * 此列表始终包含本服id
	 */
	public static final HashSet<Integer> MergedServerIds = new HashSet<>();
	
	/**
	 * 语言,地区
	 */
	public static Locale Locale = null;
	
	static {
		String localCfg = CoreConfig.stringValue("Locale");
		if (StringUtils.isBlank(localCfg)) {
			localCfg = "zh_CN";
		}
		String[] arr = localCfg.split("\\_");
		if (arr.length != 2) {
			arr = new String[] {"zh", "CN"};
		}
		Locale = new Locale(arr[0], arr[1]);

		try {
			String mergedServerIds = CoreConfig.stringValue("MergedServerIds");
			if (StringUtils.isNotBlank(mergedServerIds)) {
                String[] arr2 = mergedServerIds.split(",");
				for (String str : arr2) {
					MergedServerIds.add(Integer.valueOf(str));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		MergedServerIds.add(ServerId);
	}

	/**
	 * @return 默认所有值都为0的Number实例
	 */
	public static Number zero() {
		return new Number() {
			private static final long serialVersionUID = 4938803269936507244L;

			@Override
			public long longValue() {
				return 0;
			}

			@Override
			public int intValue() {
				return 0;
			}

			@Override
			public float floatValue() {
				return 0;
			}

			@Override
			public double doubleValue() {
				return 0;
			}
		};
	}

    /**
     *
     * @return 运行程序的目录完整路径,以'/'结尾
     */
    public static String getPwd() {
        URL url = GameUtils.class.getResource("/");
        String path = url.getPath();
        try {
            path = URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        System.out.println("PATH '/' : " + path);
        return path;
    }

	/**
	 * @param logFileRelPath 文件的相对于日志目录的相对路径,例: 日志目录是/data/log, 那么/data/log/console.log的相对路径就是console.log
	 * @return 日志文件的File,在文件不存在时返回null
	 */
	public static File getLogFile(String logFileRelPath) {
		String logHome = System.getProperty("LogHome");
		if (StringUtils.isBlank(logHome)) {
			throw new RuntimeException("没有找到系统属性'LogHome',请检查logback.xml中的'LogHome'配置!");
		}
		if (!logHome.endsWith(File.separator)) {
			logHome += File.separator;
		}
		File f = new File(logHome + logFileRelPath);
		if (f.exists()) {
			return f;
		}
		return null;
	}
	
	/**
	 * @param logFileRelPath 文件的相对于日志目录的相对路径,例: 日志目录是/data/log, 那么/data/log/console.log的相对路径就是console.log
	 * @return 日志文件内容,文件不存在时返回""
	 */
	public static String getLogFileContent(String logFileRelPath) {
		File f = getLogFile(logFileRelPath);
		if (f == null) {
			return "";
		}
		
		String ret = "";
		try (InputStream is = new FileInputStream(f)) {
			ret = IOUtils.toString(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		return ret;
	}

    /**
     * 创建全服唯一的玩家id
     * globalPlayerId规则与Center一致
     * @param serverId
     * @param playerId
     * @return 全服唯一id
     */
    public static long genGlobalPlayerId(int serverId, int playerId) {
        return serverId * 10000000000L + playerId;
    }

    /**
     * 将全服唯一玩家id(globalPlayerId)转换为serverId和playerId
     * globalPlayerId规则与Center一致
     * @param globalPlayerId
     * @return [0]=serverId [1]=playerId
     */
    public static int[] unpackGlobalPlayerId(long globalPlayerId) {
        int[] arr = new int[2];
        arr[0] = (int) (globalPlayerId / 10000000000L);
        arr[1] = (int) (globalPlayerId % 10000000000L);
        return arr;
    }

}
