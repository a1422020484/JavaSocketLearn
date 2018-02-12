package cn.saturn.web.redis;

import cn.saturn.web.code.bind.domain.AccountBind;
import cn.saturn.web.code.login.domain.Account;
import cn.saturn.web.controllers.notice.domain.Notice;
import cn.saturn.web.controllers.param.dao.Param;
import cn.saturn.web.controllers.server.dao.Package;
import cn.saturn.web.controllers.server.dao.Section;
import cn.saturn.web.controllers.server.dao.Server;
import cn.saturn.web.controllers.server.dao.Version;
import cn.saturn.web.code.login.domain.ShieldModel;
import cn.saturn.web.code.cdkey.domain.CDKey;
import cn.saturn.web.controllers.ucapi.gift.UCGift;

/**
 * Redis的缓存key
 *
 * @author xiezuojie
 */
public class RedisKeys {

    /**
     * 帐号<br/>
     * hset<br/>
     * k: Long {@link Account#getId()}<br/>
     * v: JSON {@link Account}
     */
    public static final String K_ACCOUNT = "k_home_account";

    /**
     * 帐号<br/>
     * hset<br/>
     * k: String {@link Account#getAccount()} + {@link Account#getPlatform()}<br/>
     * v: JSON {@link Account}
     */
    public static final String K_ACCOUNT_NAME = "k_home_account_name";

    /**
     * 帐号<br/>
     * hset<br/>
     * k: Long {@link Account#getId()}<br/>
     * v: Integer The login Token
     */
    public static final String K_ACCOUNT_LOGIN_TOKEN = "k_home_account_login_token";

    /**
     * 帐号各服数据<br/>
     * hset<br/>
     * k: Long {@link AccountBind#getAccountId()}<br/>
     * v: JSON List&lt;{@link AccountBind}&gt; 这个帐号相关的所有AccountBind列表
     */
    public static final String K_ACCOUNT_BIND = "k_home_account_bind";

    /**
     * 帐号各服数据<br/>
     * hset<br/>
     * k: String {@link AccountBind#getAccountId()} + "_" + {@link AccountBind#getSrvId()}<br/>
     * v: JSON {@link AccountBind}
     */
    public static final String K_ACCOUNT_BIND_ACT_SRV = "k_home_account_bind_act_srv";

    /**
     * 服务器<br/>
     * hset<br/>
     * k: Integer {@link Server#getId()}<br/>
     * v: JSON {@link Server}
     */
    public static final String K_SERVER = "k_home_server";

    /**
     * 服务器分区<br/>
     * hset<br/>
     * k: Integer {@link Section#getId()}<br/>
     * v: JSON {@link Section}
     */
    public static final String K_SERVER_SECTION = "k_home_server_section";

    /**
     * 版本号<br/>
     * hset<br/>
     * k: Integer {@link Version#getId()}<br/>
     * v: JSON {@link Version}
     */
    public static final String K_VERSION = "k_home_version";

    /**
     * 资源包<br/>
     * hset<br/>
     * k: Integer {@link Package#getId()}<br/>
     * v: JSON {@link Package}
     */
    public static final String K_PACKAGE = "k_home_package";

    /**
     * 参数表<br/>
     * hset<br/>
     * k: String {@link Param#getId()} + "_" + {@link Param#getType()}<br/>
     * v: JSON {@link Param}
     */
    public static final String K_PARAM = "k_home_param";

    /**
     * 登录公告<br/>
     * hset<br/>
     * k: Integer {@link Notice#getId()}
     * v: JSON {@link Notice}
     */
    public static final String K_NOTICE = "k_home_notice";

    /**
     * 维护者ip<br/>
     * set<br/>
     * v: JSON List&lt;String&gt; IP列表
     */
    public static final String K_VINDICATOR_IP = "k_home_vindicator_ip";

    /**
     * 封ip<br/>
     * hset<br/>
     * k: String IP Address
     * v: String 空值
     */
    public static final String K_BAN_IP = "k_home_ban_ip";

    /**
     * CDKey<br/>
     * hset<br/>
     * k: String {@link CDKey#key}<br/>
     * v: JSON {@link CDKey}
     */
    public static final String K_CDKEY = "k_home_cd_kdy";

    /**
     * 设置关闭游戏xx 版本 xx系统功能<br/>
     * set<br/>
     * k: String {@link ShieldModel#getVersion()}
     * v: JSON {@link ShieldModel}
     */
    public static final String K_SHIELD_SYS = "j_shield_sys";

    /**
     * UC礼包<br/>
     * hset<br/>
     * k: String {@link UCGift}
     * v: JSON {@link UCGift}
     */
    public static final String K_UC_GIFT = "k_home_uc_gift";

}
