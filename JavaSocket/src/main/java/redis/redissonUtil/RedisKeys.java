package redis.redissonUtil;

/**
 * 游戏中用到的Redis的Key定义在这里
 */
public class RedisKeys {

    /*
     * key命名约定
     * 1 key 功能间使用冒号分隔符
     *   Eg：public:user:cache:string:xxx; public:user:token:hash:xxx
     * 2 key采用小写结构，不采用驼峰格式，中间利用下滑线 _ 做连接 例如：user_name、user_pass。
     * 3 key的长度限制在128字节之内，既可以节省空间，又可以加快查询速度。
     */

//	/**
//	 * 充值活动
//	 * hmset<br/>
//	 */
//	public static final String K_CHARGE_ACTIVE_SIGN = "k_charge_active_sign";
//
//    /**
//     * 充值活动
//     * hmset<br/>
//     */
//    public static final String K_CHARGE_ACTIVE_SIGN = "k_charge_active_sign";
//
//
//    /**
//     * 公会据点战
//     * hmset<br/>
//     */
//    public static final String K_GUILD_WAR_SIGN = "k_guild_war_sign";
//
//    /**
//     * 公会据点战个人排行
//     * hmset<br/>
//     */
//    public static final String K_GUILD_WAR_PERSON_SIGN = "k_guild_war_person_sign";
//
//    /**
//     * 全服目标活动
//     */
//    public static final String K_ALL_SERVER_TARGET = "k_all_server_target";
//
//    /**
//     * 全服目标活动
//     */
//    public static final String K_ALL_SERVER_PERSON_TARGET = "k_all_server_person_target";


    /**
     * 公共数据
     */
    public static final class Public {
        /**
         * 在Redis初始化数据时使用此锁
         */
        public static final String INITIALIZE_LOCK = "public:initialize:lock";
        /**
         * 角色名:角色ID 对照表
         * <p>
         * 永不过期
         */
        public static final String PLAYER_NAME__ID = "public:player:name__id:hash";
        /**
         * 账号+平台:角色ID 对照表
         * <p>
         * 永不过期
         */
        public static final String ACCOUNT_NAME_PLATFORM__ID = "public:player:account_name_platform__id:hash";
        /**
         * 记录玩家当前所在线
         * 角色ID:服务器ID
         * <p>
         * 永不过期
         */
        public static final String PLAYER_ID__LINE = "public:player:id__line:hash";

        /**
         * type: hash
         * <p>
         * 全局配置
         * <p>
         * 永不过期
         */
        public static final String CONFIG = "public:config:string";
    }

    /**
     * 玩家私有数据
     */
    public static final class Private {
        // 使用Hash Tag使同一个玩家的数据分布在同一个Redis节点上
        // Key格式：private:{%玩家ID%}:数据类型:Redis类型
        // 参数：%d 玩家id

        /**
         * 单玩家数据，hash表包含所有1对1类型的数据
         */
        public static final String ONE_TO_ONE = "private:{%d}:one_to_one:hash";

        // 1对N ==========================================================================
        public static final String BUILDING = "private:{%d}:building:hash";
        public static final String CARD = "private:{%d}:card:hash";
        public static final String FRIEND = "private:{%d}:friend:hash";
        public static final String INCUBATOR = "private:{%d}:incubator:hash";
        public static final String ITEM = "private:{%d}:item:hash";
        public static final String MESSAGE = "private:{%d}:message:hash";
        public static final String RECORD = "private:{%d}:record:hash";
        public static final String ROUGH_TRAINING = "private:{%d}:rough_training:hash";
        public static final String RUNE = "private:{%d}:rune:hash";
        public static final String TASK = "private:{%d}:task:hash";
        // 1对N ==========================================================================

        /**
         * 所有玩家数据相关的Key
         */
        public static final String[] KEYS_DATA = new String[]{
                ONE_TO_ONE, BUILDING, CARD, FRIEND, INCUBATOR, ITEM, MESSAGE, RECORD, ROUGH_TRAINING, RUNE, TASK
        };

        /**
         * 切换服务器锁
         */
        public static final String CHANGE_SERVER_LINE_LOCK = "private:{%d}:change_line:lock";
    }

    public static final class OneToOne {
        // 变量命名统一使用大小，Redis-key统一使用小写，别搞个人主义
        public static final String ACCOUNT = "account";
        public static final String PLAYER = "player";
        public static final String ADVENTURE = "adventure";
        public static final String ARENA = "arena";
        public static final String CARD_DATA = "card_data";
        public static final String DOUBLE_BATTLE = "double_battle";
        public static final String FEEDER_MAP = "feeder_map";
        public static final String FORMATIONS = "formations";
        public static final String HANDBOOK = "handbook";
        public static final String HOME = "home";
        public static final String INCENSE = "incense";
        public static final String INV_CODE = "inv_code";
        public static final String LIMIT_SHOP_INFO = "limit_shop_info";
        public static final String LOVER = "lover";
        public static final String MODULE = "module";
        public static final String MOUNT = "mount";
        public static final String OPTIONAL_TASK = "optional_task";
        public static final String PACKSACK = "packsack";
        public static final String PARAM = "param";
        public static final String PET_COLLECT = "pet_collect";
        public static final String PLAYER_CHAT_TIME = "player_chat_time";
        public static final String PLAYER_CHECK_POINT_CHALLENGE_INFO = "player_check_point_challenge_info";
        public static final String PLAYER_ONLINE_GIFT = "player_online_gift";
        public static final String PLAYER_SCENE = "player_scene";
        public static final String PVP_BATTLE = "pvp_battle";
        public static final String ROLE_SKIN = "role_skin";
        public static final String SEND_TASK = "send_task";
        public static final String SETTING = "setting";
        public static final String TECHNICAL_MACHINE_INFO = "technical_machine_info";
        public static final String TITLE = "title";
        public static final String ZODIAC = "zodiac";

        public static final String LUXURY_INFO = "luxury_info"; // 豪华版信息
    }

}
