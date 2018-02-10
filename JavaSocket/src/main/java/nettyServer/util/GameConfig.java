package nettyServer.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import nettyServer.util.cache.CachedDataPersistStrategy;
import nettyServer.util.resource.ResourceLoader;


/**
 * 游戏配置工具
 * 
 * @author xiezuojie
 */
@Component
public class GameConfig extends ResourceLoader {
	private static Map<String, Map<Class<?>, Serializable>> map;

    /** 是否是调试模式 */
    public static boolean IsDebug = CoreConfig.booleanValue("Debug");
    /** 服务器id */
    public static int ServerId = CoreConfig.intValue("ServerId");

    public static String HostAddress;
    public static String ServerUrl;
    static {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
//        HostAddress = inetAddress.getHostAddress();
        try {
            Enumeration<NetworkInterface> netIf = NetworkInterface.getNetworkInterfaces();
            while (netIf.hasMoreElements()) {
                NetworkInterface nwi = netIf.nextElement();
                Enumeration<InetAddress> address = nwi.getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress in = address.nextElement();
                    if (in.isSiteLocalAddress()) {
                        HostAddress = in.getHostAddress();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            HostAddress = "unknown";
        }
        ServerUrl = "http://" + HostAddress + ":" + CoreConfig.stringValue("game.tcp.port");
        // FIXME 当机器有双网卡时,读取的IP是不明确的,会导致PVP战斗结束后无法正确的通知到游戏服
    }

    /** 日志是否持久化到数据库 */
    public static boolean LOG_TO_DB = true;
    /** 日志持久化的时间间隔(毫秒) */
	public static int LOG_PERSIST_INTERVAL;
	/** 日志队列最大数 */
	public static int LOG_QUEUE_MAX;
	/** 一次写数据库的记录条数 */
	public static int LOG_BATCH_SIZE;

	/** 在场景中的位置失效时间(毫秒),超过此时间未同步时将从场景移除 */
	public static volatile int AREA_POS_TIMEOUT;
    public static float SceneVisibleRangeR;
    public static float SceneVisibleRangeRReload;
	public static float SceneVisibleRangeRSqu;
	public static float SceneVisibleRangeRReloadSqu;


	/** 狩猎场的场景区域id */
    public static int HuntAreaId;
    public static Set<Integer> BattleAreaIds = new HashSet<>();

	/** 初始场景id */
    public static int AreaIdInit;
    public static int CheckPointIdInit;
    public static float AreaInitDir;
    public static float AreaInitX;
    public static float AreaInitY;
    public static float AreaInitZ;
	/** 错误的位置Y */
    public static float ErrorPosY;
	/** 允许注册的玩家数,指进入游戏并创建角色的数量 */
    public static int MaxAllowedRegPlayer;

    /** 是否过滤Emoji符号 */
    public static boolean EmojiFilter;
    public static Pattern EmojiPattern;

    /** 扭蛋在抽到好卡时过滤掉指定Card'id的公告 */
    public static HashSet<Integer> LotteryFilterCardIdsSet;

    public static Pattern ChatContentCheckPattern; // nullable
    public static int ChatContentCheckRegexMatchs;
    /** 检测阵型上card时过滤未激活的阵型 */
    public static HashSet<Integer> FormationActiveIdsSet;

    public static boolean RingUpdateEnable;

    //月卡获取VIP升级经验值
    public static List<Integer> MonthCardGainVipExpList;

    //开启战力验证阵型type
    public static HashSet<Integer> validateFightingCapacitiesOpenFormatioinTypeSet;

    /** #货币的兑换率, 1元=?水晶 默认中国大陆货币兑换率 */
    public static int CurrencyExchange = 10;

	private void initStatic() {
		CachedDataPersistStrategy.strategy = intVal("CachedDataPersistStrategy");
        LOG_TO_DB = GameConfig.boolVal("LogToDB");
        LOG_PERSIST_INTERVAL = GameConfig.intVal("LogPersistInterval") * 1000;
		LOG_QUEUE_MAX = GameConfig.intVal("LogQueueMax");
		LOG_BATCH_SIZE = GameConfig.intVal("LogBatchSize");

        SceneVisibleRangeR = GameConfig.floatVal("SceneVisibleRange-R");
        SceneVisibleRangeRSqu = SceneVisibleRangeR * SceneVisibleRangeR;
        SceneVisibleRangeRReload = GameConfig.intVal("SceneVisibleRange-R-Reload");
        SceneVisibleRangeRReloadSqu = SceneVisibleRangeRReload * SceneVisibleRangeRReload;

		AREA_POS_TIMEOUT = GameConfig.intVal("AreaPosTimeout") * 1000;
        HuntAreaId = GameConfig.intVal("HuntAreaId");
        AreaIdInit = GameConfig.intVal("AreaIdInit");
        CheckPointIdInit = GameConfig.intVal("CheckPointIdInit");
        AreaInitDir = GameConfig.floatVal("AreaInitDir");
        AreaInitX = GameConfig.floatVal("AreaInitX");
        AreaInitY = GameConfig.floatVal("AreaInitY");
        AreaInitZ = GameConfig.floatVal("AreaInitZ");

        ErrorPosY = GameConfig.floatVal("ErrorPosY");
        MaxAllowedRegPlayer = GameConfig.intVal("MaxAllowedRegPlayer");
        EmojiFilter = GameConfig.boolVal("EmojiFilter");

        try {
            String emojiFilterRegex = GameConfig.stringVal("EmojiFilterRegex");
            if (StringUtils.isNotBlank(emojiFilterRegex)) {
                EmojiPattern = Pattern.compile(emojiFilterRegex);
            } else {
                EmojiPattern = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashSet<Integer> LotteryFilterCardIdsSet = new HashSet<>(32);
        String lotteryFilterCardIds = GameConfig.stringVal("LotteryFilterCardIds");
        if (lotteryFilterCardIds != null) {
            String[] lotteryFilterCardIdsArr = lotteryFilterCardIds.split(",");
            for (String filterId : lotteryFilterCardIdsArr) {
                try {
                    int filterIdInt = Integer.valueOf(filterId);
                    LotteryFilterCardIdsSet.add(filterIdInt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        GameConfig.LotteryFilterCardIdsSet = LotteryFilterCardIdsSet;

        HashSet<Integer> FormationActiveIdsSet = new HashSet<>(32);
        String formationActiveIdsSet = GameConfig.stringVal("FormationActiveIds");
        if (formationActiveIdsSet != null) {
            String[] formationActiveIdsArr = formationActiveIdsSet.split(",");
            for (String filterId : formationActiveIdsArr) {
                try {
                    int filterIdInt = Integer.valueOf(filterId);
                    FormationActiveIdsSet.add(filterIdInt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        GameConfig.FormationActiveIdsSet = FormationActiveIdsSet;

        HashSet<Integer> validateIdsSet = new HashSet<>(32);
        String validateIds = GameConfig.stringVal("ValidateFightingCapacitiesOpenFormatioinType");
        if (validateIds != null) {
            String[] validateIdsArr = validateIds.split(",");
            for (String filterId : validateIdsArr) {
                try {
                    int filterIdInt = Integer.valueOf(filterId);
                    validateIdsSet.add(filterIdInt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        GameConfig.validateFightingCapacitiesOpenFormatioinTypeSet = validateIdsSet;


        List<Integer> monthCardGainVipExpList = new ArrayList<>();
        String monthGainExpStr = GameConfig.stringVal("MonthCardGainVipExp");
        if(null != monthGainExpStr){
            String[] monthCardGainVipExpArr = monthGainExpStr.split(",");
            try {
                for(String gainExp: monthCardGainVipExpArr){
                    monthCardGainVipExpList.add(Integer.valueOf(gainExp));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        GameConfig.MonthCardGainVipExpList = monthCardGainVipExpList;

        String chatContentCheckRegexStr = GameConfig.stringVal("ChatContentCheckRegex");
        if (StringUtils.isNotBlank(chatContentCheckRegexStr)) {
            try {
                GameConfig.ChatContentCheckPattern = Pattern.compile(chatContentCheckRegexStr);
                GameConfig.ChatContentCheckRegexMatchs = intVal("ChatContentCheckRegexMatchs");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            GameConfig.ChatContentCheckPattern = null;
        }

        RingUpdateEnable = boolVal("RingUpdateEnable");

        CurrencyExchange = intVal("CurrencyExchange_" + GameUtils.Locale.toString());

        String battleAreaIdsStr = stringVal("BattleAreaIds");
        if (StringUtils.isNotEmpty(battleAreaIdsStr)) {
            String[] arr = battleAreaIdsStr.split(",");
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < arr.length; i++) {
                set.add(Integer.valueOf(arr[i]));
            }
            BattleAreaIds = set;
        }
    }
	
	@SuppressWarnings("unchecked")
	private static <T> T val(String key, Class<T> clazz) {
		Map<Class<?>, Serializable> objMap = map.get(key);
		if (objMap == null)
			return null;
		Object obj = objMap.get(clazz);
		if (obj != null) {
			return (T) obj;
		} else {
			// 默认就是String
			String str = (String) objMap.get(String.class);
			Serializable ret = null;
			if (String.class.isAssignableFrom(clazz)) {
				ret = str;
			} else if (Integer.class.isAssignableFrom(clazz)) {
				ret = Integer.valueOf(str);
			} else if (Float.class.isAssignableFrom(clazz)) {
				ret = Float.valueOf(str);
			} else if (Boolean.class.isAssignableFrom(clazz)) {
				ret = Boolean.valueOf(str);
			}
			objMap.put(clazz, ret);
			return (T) ret;
		}
	}
	
	@Override
	public void load(InputStream is) throws IOException {
		Properties prop = new Properties();
		prop.load(is);
		Map<String, Map<Class<?>, Serializable>> map = new HashMap<>();
		for (Object key : prop.keySet()) {
			String k = (String) key;
			String v = prop.getProperty(k);
			Map<Class<?>, Serializable> claMap = new ConcurrentHashMap<>();
			claMap.put(String.class, v);
			map.put(k, claMap);
		}
		GameConfig.map = map;
		initStatic();
	}

	@Override
	public String getResourceName() {
		return "Config";
	}
	
	/**
	 * @param key
	 * @return 与key匹配的string值
	 */
	public static String stringVal(String key) {
		return val(key, String.class);
	}
	
	/**
	 * 
	 * @param key
	 * @return 与key匹配的int值
	 */
	public static Integer intVal(String key) {
		return val(key, Integer.class);
	}
	
//	/**
//	 * 
//	 * @param key
//	 * @param def
//	 * @return 与key匹配的int值,当不存在与key关联的值时返回指定默认值def.
//	 */
//	public static int intVal(String key, int def) {
//		Integer v = val(key, Integer.class);
//		return v != null ? v : def;
//	}
	
	/**
	 * @param key
	 * @return 与key匹配的float值
	 */
	public static Float floatVal(String key) {
		return val(key, Float.class);
	}
	
	/**
	 * @param key
	 * @return 与key匹配的boolean值
	 */
	public static Boolean boolVal(String key) {
		return val(key, Boolean.class);
	}


    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("0|1|2|3|4|5|6|7|8|9|一|二|三|四|五|六|七|八|九|十|壹|贰|叁|肆|伍|陆|柒|捌|玖|拾|①|②|③|④|⑤|⑥|⑦|⑧|⑨|⑩|⑴|⑵|⑶|⑷|⑸|⑹|⑺|⑻|⑼|⑽|⑿|⒀|⒁|⒂|⒃|⒄|⒅|⒆|⒇|⒈|⒉|⒊|⒋|⒌|⒍|⒎|⒏|⒐|⒑|⒒|⒓|⒔|⒕|⒖|⒗|⒘|⒙|⒚|⒛");
        long l1 = System.currentTimeMillis();
        Matcher matcher = pattern.matcher("abcdesef二wewe六sfef1efrvxliefr454lkmseflij3lkjefwelif1lkseifji44skemflewfftkk一");
        int c = 0;
        while (true) {
            if (matcher.find()) {
                c++;
            } else {
                break;
            }
        }
        long l2 = System.currentTimeMillis();
        System.out.println("用时:" + (l2 - l1));
        System.out.println("c:" + c);
    }
}
