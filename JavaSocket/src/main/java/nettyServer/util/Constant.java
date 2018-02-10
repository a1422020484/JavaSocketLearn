package nettyServer.util;

import java.util.ArrayList;
import java.util.List;

public class Constant {

	/**
	 * 初始化进度
	 */
	public static final int INIT_PROGRESS = 0;

	public static final short OUT_BOTTING_STATUS = 1; // 挂机
	public static final short OUT_BOTTING_NOSTATUS = 0; // 未挂机

	public static final String NOT_DOUBLEREPAIR = "-1";

	/**
	 * 开场场景
	 */
	public static final String GAME_START_SCENE = "S000";

	/**
	 * 客户端下载场景
	 */
	public static final String CLIENT_DOWNLOAD_SCENE = "S010";

	/**
	 * 主城
	 */
	public static final String PUBLIC_SCENE = "S002";

	/**
	 * 工会晶石
	 */
	public static final String Guilstal_Scene = "S404";

	/**
	 * 武道会 战斗场景
	 */
	public static final String CompeptionFight_Scene = "S420";

	/**
	 * 采矿场
	 */
	public static final String Mining_Scene = "S217";

	/**
	 * 魔宫下层
	 */
	public static final String MONSTERINVASION_DOWN_SCENE = "S218";

	/**
	 * 魔宫上层
	 */
	public static final String MONSTERINVASION_UP_SCENE = "S219";

	/**
	 * 夺宝奇兵
	 */
	public static final String SNATCH_SCENE = "S401";

	/**
	 * 神魔幻境,神鬼玄机
	 */
	public static final String BATTLEFIELD_SCENE = "S402";

	/**
	 * 九龙遁甲1层
	 */
	public static final String TOWERWAR_SCENE_1 = "S411";
	/**
	 * 九龙遁甲2层
	 */
	public static final String TOWERWAR_SCENE_2 = "S412";
	/**
	 * 九龙遁甲3层
	 */
	public static final String TOWERWAR_SCENE_3 = "S413";
	/**
	 * 九龙遁甲4层
	 */
	public static final String TOWERWAR_SCENE_4 = "S414";
	/**
	 * 九龙遁甲5层
	 */
	public static final String TOWERWAR_SCENE_5 = "S415";

	/**
	 * 帮会攻城外
	 */
	public static final String GUILDSIEGE_1 = "S406";

	/**
	 * 帮会攻城内
	 */
	public static final String GUILDSIEGE_2 = "S407";

	/**
	 * 公会押镖场景
	 */
	public static String Union_Escort_Scene;

	private static List<String> list = new ArrayList<String>();

	// 活动预开始时登录需要推送的活动
	public static void loader() {
		register(MonsterAttart_ActivityId);
		register(HolyCryCal_ActivityId);
		register(Compmpetition_ActivityId);
		register(SupernaturaDreamland_ActivityId);
		register(TowerWar_ActivityId);
		register(UnionCrystal_ActivityId);
		register(UnionFight_ActivityId);
		register(MonsterIntrusion_ActivityId);
		register(MonsterSiege_ActivityId);
		register(UnionSiege_ActivityId);
	}

	public static List<String> getActivity() {
		return list;
	}

	private static void register(String ActivityId) {
		list.add(ActivityId);
	}

	// boss奖励类型
	// 伤害排名奖励和最后一击奖励都没有
	public static final byte rewardType_0 = 0;
	// 有伤害排名奖励没有最后一击奖励
	public static final byte rewardType_1 = 1;
	// 有最后一击奖励没有伤害排名奖励
	public static final byte rewardType_2 = 2;
	// 有伤害排名奖励没有最后一击奖励
	public static final byte rewardType_3 = 3;

	public static final byte NULL = -1;

	public static final String NULL_STR = "-1";

	/**
	 * 未双修
	 */
	public static final short NO_DOUBLEREPAIR = 0;
	/**
	 * 已经双修
	 */
	public static final short DOUBLEREPAIR = 1;

	public static final String MonsterSiege_ActivityId = "activity_manage_28";

	// 荒野奇袭活动Id
	public static final String MonsterAttart_ActivityId = "activity_manage_41";

	// 神圣晶石活动Id
	public static final String HolyCryCal_ActivityId = "activity_manage_42";

	// 武道会活动Id
	public static final String Compmpetition_ActivityId = "activity_competition";

	// 神魔幻境活动Id
	public static final String SupernaturaDreamland_ActivityId = "activity_manage_45";

	// 封神之战活动Id
	public static final String TowerWar_ActivityId = "activity_manage_46";

	// 公会晶石活动Id
	public static final String UnionCrystal_ActivityId = "activity_manage_48";

	// 公会争霸活动Id
	public static final String UnionFight_ActivityId = "activity_manage_49";

	// 武道之巅ID
	public static final String Competition_ActivityId = "activity_manage_51";

	// 怪物入侵
	public static final String MonsterIntrusion_ActivityId = "activity_manage_6";

	// 个人押镖
	public static final String Protect_ActivityId = "activity_manage_26";

	// 公会押镖
	public static final String Union_Escort_ActivityId = "activity_manage_29";

	// 家族攻城活动
	public static final String UnionSiege_ActivityId = "activity_manage_52";
	
	//充值返利活动
	public static final String PlayerRebate_ActivityId = "activity_chongzhifanli";
	
	// 帮会守护
	public static final String UnionDefen_ActivityId = "activity_manage_47";
	
	// 离开世界 需要退出活动场景的Id
	public static final String[] ActivityScene = { "S402", "S411", "S412", "S413", "S414", "S415", "S404", "S410", "S420" };

	// 阵营：神
	public static final byte God_Camp = 0;
	// 阵营：魔
	public static final byte Evil_Camp = 1;
	// 所有阵营
	public static final byte All_Camp = -1;

	// 无阵营
	public static final byte Not_Camp = -2;

	// 是否能够进入
	public static final byte No_Enter = 1;
	public static final byte Yes_Enter = 0;

	// 是否拥有封神之剑
	public static final byte HadGod_Saber = 1;
	public static final byte NoHadGod_Saber = 2;

	public static final byte Success = 0;
	public static final byte Fail = 1;

	// 1报名倒计时 2匹配倒计时 3战斗倒计时 4 战斗准备
	public static final byte Enroll_Countdown = 1;
	public static final byte Mate_Countdown = 2;
	public static final byte Fight_Countdown = 3;
	public static final byte FightReady_Countdown = 4;

	public static final String UNION_NAME = "帮会";

	/**
	 * 是否可以报名
	 * 
	 * 
	 */
	public static final byte Yes_Enroll = 1;
	public static final byte Not_Enroll = 2;

	/**
	 * 
	 * 是否是保护时间 （1，是，2否）
	 * 
	 * */
	public static final byte YES_PROTECTTIME = 1;
	public static final byte NOT_PROTECTTIME = 2;

	/** 市场 */
	public static final String Ref_market = "market_1";

	/**
	 * 等级奖励
	 * 
	 * @author Administrator
	 * 
	 */
	public interface PlayerLevelRewardStatus {

		int NOT = 0; // 未领取

		int CAN = 2; // 可领取

		int YES = 3; // 已经领取

	}

	public interface ProtectionTimeType {
		// 保护时间类型 1增加 2移除 3主动获取
		int addProtectionTime = 1;

		int removeProtectionTime = 2;

		int getProtectionTime = 4;

		// 0 表示没有保护时间 非0表示有保护时间
		int noProtectionTimeValue = 0;
	}

	/**
	 * 是否场景无损pk ,无损pk 不掉落不增加pk值 1 是 0否
	 * 
	 * @author Administrator
	 * 
	 */
	public interface ScenePKAndDrop {
		byte add = 1;

		byte notAdd = 0;
	}

	// 角色保护时间
	public static final long roleProtectionTime = 3 * 1000;

	// 缓存在时间(秒)
	public static final int CACHE_CONTINUE_TIME = 1800;

	public static final int MaxMailViewNum = 10;

	public static final String MOUNT_LEVEL = "mountLevel_";

	public static final String FOOTPRINT_LEVEL = "zujiLevel_";

	// 初始坐骑
	public static final String first_mount = "ride_1_0";

	// 初始 幻化等级
	public static final String firstMountLevel_Ref = "mountLevel_0";

	/*** 初始 心法 */
	public static final String INIT_XINFA_REF = "xinfa_1_0";

	/** 初始魂兵 **/
	public static final String INIT_HUNBING_REF = "hunbing_1_0";

	/** 初始足迹 */
	public static final String INTI_FOOTPRINT_REF = "zuji_1_0";

	/** 初始仙器 **/
	public static final String INIT_FARIT_REF = "xianqi_1_0";

	/** 初始法宝 */
	public static final String INIT_TRUMP_REF = "fabao_1";

	/** 初始 法宝等级 **/
	public static final int INIT_TRUMP_LEVEL = 1;

	/** 初始 足迹幻化 */
	public static final String INIT_FOOTPRINTLEVEL_REF = "zujiLevel_0";

	/** 初始格子 **/
	public static final String INIT_SLOT_REF = "bag_gezi_61";

	/** 心法技能 **/
	public static final String INIT_XINFASOUL_REF = "xinfaSoul_";
	/** 魂兵技能 */
	public static final String INIT_HUNBINGSOUL_REF = "hunbing_";
	/** 新手引导 */
	public static final String INIT_GUIDANCE_REF = "guidance_";

	public static final String INIT_BAGSLOT_REF = "bag_gezi_";

	// 公会仓库每天申请次数
	public static final byte UNION_DEPOT_APPLY_NUM = 20;

	// 下面都是从OpenFunction中读取的数据
	/** 坐骑系统开放等级 */
	public static int MOUNT_OPENFUNCTION;
	/** 翅膀系统开放等级 */
	public static int WING_OPENFUNCTION;
	/** 飞升开发等级 */
	public static int FLYUP_OPENFUNCTION;
	/** 好友开放等级 */
	public static int FRIEND_OPENFUNCTION;
	// 创建公会所需等级
	public static int GUILD_OPENFUNCTION;

	/** 拍卖行开放等级 */
	public static int MARKET_OPENFUNCTION;

	/** lable开放等级 */
	public static int LABLE_OPENFUNCTION;
	/** 心法开放等级 */
	public static int XINFA_OPENFUNCTION;

	/** 魂兵 */
	public static int HUNBING_OPENFUNCTION;

	/** 足迹 */
	public static int FOOTPRINT_OPENFUNCTION;

	/** 仙器 */
	public static int FAIRY_OPENFUNCTION;

	/** 法宝 */
	public static int TRUMP_OPENFUNCTION;

	/** 铸魂 */
	public static int FORGESOUL_OPENFUNCTION;

}
