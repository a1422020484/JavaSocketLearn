package cn.saturn.web.code.login.domain;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface AccountDAO {
		
	String TABLE = "`account`";
    String FIELDS = "account, password, platform, platform_ext, prev_srv_id,login_key," +
            "create_time,vindicator,systemInfo,login_time,lastip,version," +
            "accountActived,subPlatform,third_user_id,cdk_types,special,specialcode,games,adPlatform,adSubPlatform,adVersion,idfa,allowChangePwd";

    @SQL("select id," + FIELDS + " from " + TABLE + " where id=:1 limit 1")
    AccountModel get(Long accountId);

    @SQL("select id," + FIELDS + " from " + TABLE + " where account=:1 and platform=:2 limit 1")
    AccountModel get(String account, String platform);

    @SQL("select id," + FIELDS + " from " + TABLE + " where third_user_id=:1 limit 1")
    AccountModel get(String thirdUserId);

    // @SQL("select id," + FIELDS + " from " + TABLE + " where account=:1 and password=:2 and platform=:3 limit 1")
    // AccountModel getAccount(String account, String password, String platfrom);

    @SQL("replace into " + TABLE + " (`id`, " + FIELDS + ") values(" +
            ":1.id,:1.account,:1.password,:1.platform,:1.platformExt,:1.prevSrvId," +
            ":1.loginKey,:1.createTime,:1.vindicator,:1.systemInfo," +
            ":1.loginTime,:1.lastip,:1.version,:1.accountActived,:1.subPlatform," +
            ":1.thirdUserId,:1.cdkTypes,:1.special,:1.specialcode,:1.games,:1.adPlatform,:1.adSubPlatform,:1.adVersion,:1.idfa,:1.allowChangePwd" +
            ") ")
    int insertOrUpdate(AccountModel account);

    @ReturnGeneratedKeys
    @SQL("insert into " + TABLE + " (" + FIELDS + ")  values(" +
            ":1.account,:1.password,:1.platform,:1.platformExt,:1.prevSrvId," +
            ":1.loginKey,:1.createTime,:1.vindicator,:1.systemInfo," +
            ":1.loginTime, :1.lastip, :1.version,:1.accountActived,:1.subPlatform," +
            ":1.thirdUserId,:1.cdkTypes,:1.special,:1.specialcode,:1.games,:1.adPlatform,:1.adSubPlatform,:1.adVersion,:1.idfa,:1.allowChangePwd" +
            ")")
    Long insert(AccountModel account);

    @SQL("select id," + FIELDS + " from " + TABLE + " where vindicator!=0 limit :1,:2")
    List<AccountModel> getVindicator(int start, int size);

    @SQL("select platform from " + TABLE + " GROUP BY account.platform")
    List<String> getPlatforms();

    @SQL("select count(*) from " + TABLE + " where vindicator!=0")
    Integer getVindicatorCount();
	
    @SQL("select adPlatform from " + TABLE + " GROUP BY account.adPlatform")
    List<String> getAdPlatforms();
    
    @SQL("select adSubPlatform from " + TABLE + " GROUP BY account.adSubPlatform")
    List<String> getAdSubPlatforms();
	
	/*String TABLE = "`account`";
	String FIELDS = "account, password, platform, platform_ext, prev_srv_id,login_key,create_time,vindicator,systemInfo,login_time,lastip,version,accountActived,subPlatform";

	@SQL("select id," + FIELDS + " from " + TABLE + " where id=:1 limit 1")
	public AccountModel get(Long accountId);

	@SQL("select id," + FIELDS + " from " + TABLE + " where account=:1 and platform=:2 limit 1")
	public AccountModel get(String account, String platfrom);

	// @SQL("select id," + FIELDS + " from " + TABLE + " where account=:1 and password=:2 and platform=:3 limit 1")
	// public AccountModel get(String account, String password, String platfrom);

	@SQL("replace into " + TABLE + " (`id`, " + FIELDS
			+ ") values(:1.id,:1.account,:1.password,:1.platform,:1.platformExt,:1.prevSrvId,:1.loginKey,:1.createTime,:1.vindicator,:1.systemInfo,:1.loginTime,:1.lastip,:1.version,:1.accountActived,:1.subPlatform) ")
	public int insertOrUpdate(AccountModel accountModel);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE + " (" + FIELDS + ")  values(:1.account,:1.password,:1.platform,:1.platformExt,:1.prevSrvId,:1.loginKey,:1.createTime,:1.vindicator,:1.systemInfo,:1.loginTime, :1.lastip, :1.version,:1.accountActived,:1.subPlatform)")
	public Long insert(AccountModel accountModel);

	@SQL("select id," + FIELDS + " from " + TABLE + " where vindicator!=0 limit :1,:2")
	public List<AccountModel> getVindicator(int start, int size);

	@SQL("select platform from "+TABLE+" GROUP BY account.platform")
	public List<String> getPlatforms();
	
	@SQL("select count(*) from " + TABLE + " where vindicator!=0")
	public Integer getVindicatorCount();*/
	
}
   