package cn.saturn.web.code.action;

import cn.saturn.web.code.CodeServlet;
import cn.saturn.web.code.Head;
import cn.saturn.web.code.activation.domain.Activation;
import cn.saturn.web.code.activation.domain.ActivationDAO;
import cn.saturn.web.code.bind.domain.AccountBind;
import cn.saturn.web.code.bind.domain.AccountBindManager;
import cn.saturn.web.code.login.domain.*;
import cn.saturn.web.code.platform.LoginResponse;
import cn.saturn.web.code.platform.PTConfig;
import cn.saturn.web.code.platform.PTManager;
import cn.saturn.web.code.platform.PlatformInterface;
import cn.saturn.web.controllers.adv.dao.AdvManager;
import cn.saturn.web.controllers.device.dao.AccountDeviceDAO;
import cn.saturn.web.controllers.device.dao.AccountDeviceModel;
import cn.saturn.web.controllers.notice.domain.Notice;
import cn.saturn.web.controllers.notice.domain.NoticeManager;
import cn.saturn.web.controllers.param.dao.Param;
import cn.saturn.web.controllers.param.dao.ParamManager;
import cn.saturn.web.controllers.power.BanIpManager;
import cn.saturn.web.controllers.power.VindicatorIpManager;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.Section;
import cn.saturn.web.controllers.server.dao.SectionManager;
import cn.saturn.web.controllers.server.dao.Server;
import cn.saturn.web.controllers.server.dao.ServerManager;
import cn.saturn.web.controllers.ucapi.gift.UCGiftManager;
import cn.saturn.web.controllers.ucapi.gift.UCGiftRecord;
import cn.saturn.web.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

import proto.Protocol.PrivilegeInfoCS;
import proto.Protocol;
import proto.ProtocolWeb;
import proto.ProtocolWeb.*;
import xzj.core.dispatch.Response;
import xzj.core.dispatch.ResponseFactory;
import xzj.core.dispatch.annotation.Action;
import xzj.core.dispatch.annotation.Action.User;
import xzj.core.util.MD5;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuangyuetao
 */
@Action
public class LoginAction {
	public final static int defaultSrvId = Config.intVal("defaultSrvId");

	public final static boolean randomKey = Config.booleanVal("randomKey");
	public final static boolean pwdCheck = Config.booleanVal("pwdCheck");
	public final static String rapidStr = "rapid";
	public final static String defaultPt = Config.val("defaultPt"); // 默认注册平台
	public final static String debugPwd = Config.val("debugPwd"); // 测试登陆密码
	public final static String autoAccbegin = Config.val("autoAccbegin"); // 一键注册默认账号前缀
	public final static String autoPwd = Config.val("autoPwd"); // 一键注册默认密码
	public final static String allServerChatadd = Config.val("allServerChatadd"); // 全服聊天地址
	public final static boolean isconfigAdv = Config.booleanVal("isconfigAdv"); // 是否配置发送广告平台
	public final static boolean deviceSealConf = Config.booleanVal("deviceSealConf"); // 是否开启设备封停，防止跨平台;
	public final static String deviceSealfail = Config.val("deviceSealfail"); // 失败的设备信息判断;
	
	protected static final Logger logger = LoggerFactory.getLogger("login");
	

	private static int special =1;//特权账号
	
	@Resource
	AccountDeviceDAO accountDeviceDAO;
	
	// 解析url
	public static boolean UrlDecode(String url, Map<String, String> params) {
		List<NameValuePair> values = URLEncodedUtils.parse(url, Charset.forName("UTF-8"));
		for (NameValuePair value : values) {
			String key = value.getName();
			String value0 = value.getValue();
			params.put(key, value0);
		}
		return true;
	}

	@Resource
	ActivationDAO activationDao;
	@Resource
	LoginLogDAO loginLogDao;

	ConcurrentMap<String, String[]> ptParams = new ConcurrentHashMap<>();

	@Action(id = Head.H10002, isEncrypted = false, isLogon = true)
	public Object login(LoginWCS msgcs) {
        String ip = CodeServlet.getCallIp(); // 获取访问IP
        ip = (ip != null) ? ip : "";

        // ip是否被封?
        if (BanIpManager.isBanIp(ip)) {
            return ResponseFactory.failure(RBMessage.get("1000"));
        }
        
        String idfa=msgcs.getIdfa() !=null ? msgcs.getIdfa():"";
        
        if(StringUtils.isNotBlank(idfa.trim())&& deviceSealConf && (! idfa.contains(deviceSealfail))){
        	DeviceSeal deviceSeal= DeviceSealManager.getDeviceSeal(idfa);
        	
        	if(deviceSeal != null){
        		return ResponseFactory.failure(RBMessage.get("1038"));
        	}
        }
        
		// 获取账号信息
		String accountName = msgcs.getAccount();
		boolean rapid = msgcs.getRapid(); // 是否快速登录
		String ext = msgcs.getExt(); // 扩展数据,以uri形式编码
		// 设备信息
		String systemInfo = EmojiFilter.filterEmoji(msgcs.getSystemInfo());
		String password = msgcs.getPassword();
		String platform = msgcs.getPlatform();
		String platformExt = null;
		String version = msgcs.getVersion();
		String subPlatform = msgcs.getSubPlatform(); // 子渠道
		String thirdUserId = null; // 第三方渠道用户id,例,易接的子渠道用户id
		
        String games=msgcs.getGames() != null ? msgcs.getGames() : "";
        String adPlatform=msgcs.getAdPlatform() !=null ? msgcs.getAdPlatform() : "";
        String adSubPlatform=msgcs.getAdSubPlatform() !=null ?msgcs.getAdSubPlatform() :"";
        String adVersion=msgcs.getAdVersion() !=null ?msgcs.getAdVersion() :"";
		
		
		// 自动创建判断(通常用于平台处理)
		boolean autoCreate = false;
		boolean pwdCheck = LoginAction.pwdCheck; // 是否验证密码
		String retExt = null; // 返回扩展数据

		// System.out.println("remoteIpAdress:" + ip);
		// 解析额外参数
		Map<String, String> params = new HashMap<>();
		params.put("ext", ext); // 例: key=123&a=abc
		UrlDecode(ext, params); // 解析参数并设置
		// System.out.printf("ext params>>>>%s\n", ext);

		if (logger.isDebugEnabled()) {
			logger.debug("params: ip={} version={} accountName={} platform={} subPlatform={} ext={}",
					ip, version, accountName, platform, subPlatform, ext);
		}

		// 判断账号是否使用测试登陆方式
		boolean debuglogin = false;
		long debugid = 0L;
		String account0 = params.get("account");
		if (!StringUtils.isEmpty(account0) && account0.charAt(0) == '@') {
			// 判断密码是否正确
			String password0 = params.get("password");
			if (debugPwd.equals(password0)) {
				try {
					debugid = Long.valueOf(account0.substring(1));
				} catch (Exception e) {
					return ResponseFactory.failure(RBMessage.get("1001"));
				}
				debuglogin = true; // 测试登陆开始
				pwdCheck = false;
				autoCreate = false;
			}
		}

		// 根据快速登录处理
		if (rapid) {
			// 快速登录, 包括默认平台也同样处理
			autoCreate = true;
			platform = defaultPt + "_" + rapidStr;
		} else if (debuglogin) {
		} else {
			platform = params.get("pt");

			// 官网登陆, 渠道包含Saturn_开头的都用 Saturn平台处理
			if (!platform.equals(defaultPt) && platform.startsWith(defaultPt)) {
				platformExt = platform;
				platform = defaultPt;
			}

			// 获取平台
			PlatformInterface pi = PTManager.getPT(platform);
			if (pi == null) {
				return ResponseFactory.failure(RBMessage.get("1002"));
			}

			// 获取参数列表
			String[] requireParams = ptParams.get(platform);
			if (requireParams == null) {
				requireParams = pi.requireParams();
				if (requireParams != null) {
					String[] old = ptParams.putIfAbsent(platform, requireParams);
					if (old != null) {
						requireParams = old;
					}
				}
			}

			// 检测参数
			if (requireParams != null) {
				for (String s : requireParams) {
					if (!params.containsKey(s)) {
//						return ResponseFactory.failure(platform + "参数错误,需要参数: " + Arrays.toString(requireParams));
						return ResponseFactory.failure(RBMessage.get("1003", platform, Arrays.toString(requireParams)));
					}
				}
			}

			// CodeServlet.logBack.info("login eee" + pi + " " + pi.ptFlag() + "
			// " + pi.getClass().toGenericString());

			// 执行登陆
			LoginResponse lr = pi.login(params);
			if (lr.getCode() != 0) {
				return ResponseFactory.failure(RBMessage.get("1004", lr.getErrMsg()));
			}
			// 重新获取账号密码
			accountName = lr.userInfo.account;
			password = lr.userInfo.password; // 正常来说渠道账号不要密码
			password = (password != null) ? password : ""; // 不能为null
			autoCreate = lr.isAutoRegister(); // 自动创建关联账号
			thirdUserId = lr.userInfo.thirdUserId;
			if (StringUtils.isBlank(thirdUserId)) {
				thirdUserId = params.get("thirdPartyUid");
			}
			retExt = lr.getExt();
		}

		// 随机登陆key
		int loginKey = 0;
		if (randomKey) {
			loginKey = (int) (Math.random() * 999999);
		}
		// boolean isNewAccount = false;
		// 验证检测
		Account account = null;
		// 获取账号对象
		if (debuglogin && debugid > 0) {
			account = AccountManager.getAccount(debugid); // debug获取, 直接通过Id获取
		} else {
			account = AccountManager.getAccount(accountName, platform);
		}
		if (account == null) {
			// 判断是否自动创建(快速登陆或者平台验证登陆)
			if (!autoCreate) {
				return ResponseFactory.failure(RBMessage.get("1005")); // 自平台登陆
			}

			// 创建账号
			account = new Account(accountName, password);
			account.setPlatform((platform != null) ? platform : "");
			account.setPlatformExt(platformExt);
			account.setLoginKey(loginKey);
			account.setSystemInfo(systemInfo);
			account.setLastip(ip);
			account.setVersion(version);
			account.setSubPlatform((subPlatform != null) ? subPlatform : "");
			account.setThirdUserId(thirdUserId);
			//统计推广广告的数据
			account.setGames((games !=null)? games :"");
			account.setAdPlatform(adPlatform );
			account.setAdSubPlatform(adSubPlatform );
			account.setAdVersion(adVersion );
			account.setIdfa(idfa);
			
			//效验广告  idfa
	        if(isconfigAdv && StringUtils.isNotEmpty(idfa.trim())  && StringUtils.isEmpty(account.adPlatform.trim())){
	        	queue.offer(account);
	        }
			
			// 插入
			boolean result = AccountManager.insert(account);
			if (!result) {
				return ResponseFactory.failure(RBMessage.get("1006"));
			}
			//新统计设备信息
			try{
				Map<String, String> sysparams = HttpParam.parse(systemInfo);
		        String deviceUI = sysparams.get("deviceUI");
		        String OS = sysparams.get("OS");
		        String deviceModel = sysparams.get("deviceModel");	
				
		        AccountDeviceModel   accountDevice=new AccountDeviceModel();
		        accountDevice.setOS(OS);
		        accountDevice.setDeviceUI(deviceUI);
		        accountDevice.setDeviceModel(deviceModel);
		        accountDevice.setSystemInfo(systemInfo);
		        accountDevice.setPlatform(platform);
		        accountDevice.setSubplatform(subPlatform);
		        accountDevice.setReg_time(new Date());
		        accountDeviceDAO.insert(accountDevice);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			// isNewAccount = true;
		} else {
			// 验证密码(都是null, 或者相同)
			if (pwdCheck) {
				String checkPwd = account.getPassword();
				if (!checkPwd.equals(password)) {
					return ResponseFactory.failure(RBMessage.get("1007"));
				}
			}
			boolean update = false; // 更新标示
			// 修改ip
			String lastip = account.getLastip();
			if (lastip == null || !lastip.equals(ip)) {
				account.setLastip(ip);
				update = true;
			}

			// 信息记录
			String nsystemInfo = account.getSystemInfo();
			if (systemInfo != null && (nsystemInfo == null /* * || nsystemInfo.equals(systemInfo) */)) {
				account.setSystemInfo(systemInfo);
				update = true;
			}

			// 记录登陆key
			if (randomKey) {
				account.setLoginKey(loginKey);
				update = true;
			}

			// 修改版本
			String version0 = account.getVersion();
			if (version != null && (version0 == null || !version0.equals(version))) {
				account.setVersion(version);
				update = true;
			}
			//修改游戏账号标识ios,android
			String games0=account.getGames();
			if (games != null && (games0 == null || !games0.equals(games))) {
				account.setGames(games);
				update = true;
			}
			
			String idfa0=account.getIdfa();
			//用广告平台时，获取成功设备后不再更新设备信息;
			if (idfa != null && ( StringUtils.isEmpty(idfa0.trim()) )) {
			//设备信息封停跨平台是，每次更新设备信息;
			//if (idfa != null ) {
				account.setIdfa(idfa);
				update = true;
			}
			
			String adPlatform0=account.getAdPlatform();
			if (isconfigAdv  && (!StringUtils.isEmpty(account.getIdfa().trim()))  &&   ( StringUtils.isEmpty(adPlatform0.trim()) )) {
				queue.offer(account);
				
			}
			
			//&&   ( StringUtils.isEmpty(adPlatform0.trim()) )
			
			// 判断是否需要更新修改
			if (update) {
				AccountManager.update(account);
			}
		}

		// 获取账号信息
		long accountId = account.getId();
		accountName = account.getAccount();
		// 输出
		logger.info("ip={} platform={} accountId={} accountName={} subPlatform={} ext={}", ip, platform, accountId,
				accountName, subPlatform, ext);

		// 用redis储存loginkey
		// RedisUtils.hset(RedisKeys.K_ACCOUNT_LOGIN_TOKEN,
		// String.valueOf(account.getId()), String.valueOf(loginKey));

		// 判断是否是维护者账号
		boolean vindicator = account.isVindicator();
		if (!vindicator && StringUtils.isNotEmpty(ip)) {
			// 判断是否是维护者ip
			vindicator = VindicatorIpManager.isVindicatorIp(ip);
		}

		// 登陆验证成功, 读取账号ID
		// 生成密钥2
		String loginCode = LoginKeyUtils.encrypt(new LoginKeyUtils.KeyObject(accountId, "", "", loginKey));
		if (loginCode == null) {
			return ResponseFactory.failure(RBMessage.get("1008")); // 生成密鈅失败
		}

		platform = account.getSubPlatform();
		// 读取服务器列表
		List<ServerSection> sectionList = getServerSections(platform, version);
		List<ServerItem> srvList = getServerItems(accountId, sectionList, vindicator, platform, version);
        int serverCount = (srvList != null) ? srvList.size() : 0;
        if (serverCount == 0) {
            return ResponseFactory.failure(RBMessage.get("1009"));
        }

		// 上次登录, 默认第一个
		int srvId = account.getPrevSrvId();

		// 读取公告内容
		Notice noticeModel = NoticeManager.getNotice("" + srvId);
		String notice = noticeModel == null?"":noticeModel.getNotice();
		String imgs = noticeModel == null?"":noticeModel.getImgs();
		if (srvId == 0) {
			ServerItem serverItem = null;
			// 获取配置中默认服务器
			if (defaultSrvId > 0 && defaultSrvId < serverCount) {
				serverItem = srvList.get(defaultSrvId);
			}
			// 如果默认不存在, 则按第一个
			if (serverItem == null) {
				if (serverCount > 0) {
					serverItem = srvList.get(0);// 先获取第一个
					// 遍历获取第一个推荐的(如果有推荐)
					for (int i = 0; i < serverCount; i++) {
						ServerItem serverItem0 = srvList.get(i);
						if (serverItem0.getRecommend()) {
							serverItem = serverItem0;
							break;
						}
					}
				}
			}

			// 读取服务器ID
			srvId = (serverItem != null) ? serverItem.getSrvId() : 0;
		}

		// 读取主题信息
		Integer theme0 = getGlobalParam(Param.theme, Integer.class);
		int theme = (theme0 != null) ? theme0 : 0;

		// 获取客服信息
		String csInfo = getGlobalParam(Param.csInfo, String.class);

		// 获取屏蔽当前版本的系统
		ShieldModel shieldModel = ShieldSysManager.getShieldSysList(version);

		// 返回信息
		LoginWSC.Builder retb = LoginWSC.newBuilder();
		retb.setCode(loginCode);
		retb.setAccountId(accountId);
		retb.setAccountName(accountName);
		retb.setPrevSrvId(srvId);
		if (retExt != null) {
			retb.setExt(retExt);
		}
		// 公告信息
		if (!StringUtils.isBlank(notice)) {
			retb.setNotice(notice);
		}
		if(!StringUtils.isBlank(imgs)){
			try {
				String[] imgsArray=imgs.split(",");
				List<String> imgsList=new ArrayList<String>();
				for (int i = 0; i < imgsArray.length; i++) {
					imgsList.add(imgsArray[i]);
			      }
				retb.addAllNoticeImgs(imgsList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 写入服务器列表
		if (srvList != null) {
			retb.addAllSrvList(srvList);
		}
		// 写入服务器区列表
		if (sectionList != null) {
			retb.addAllSectionList(sectionList);
		}
		// 主题
		if (theme != 0) {
			retb.setTheme(theme);
		}
		// 客服信息
		if (csInfo != null) {
			retb.setCsIndo(csInfo);
		}
		// 屏蔽当前版本的系统
		if (shieldModel != null) {
			String[] subPlatforms = new String[]{};
			if(!StringUtils.isEmpty(shieldModel.getClosedSubPlatform())){
				subPlatforms = shieldModel.getClosedSubPlatform().split(",");
			}
			/*运营要求的策略，如果配了子渠道，默认全部关闭*/
			if(Arrays.asList(subPlatforms).contains(subPlatform))
				retb.addAllShieldSys(ShieldModel.allClosedSys);
			else
				retb.addAllShieldSys(ListExtUtil.arrayToList(shieldModel.getShieldSys()));
		}
		if (allServerChatadd != null) {
			retb.setAllServerChatAdress(allServerChatadd);
		}

		/*// rodking 记录玩家登录
		if (null != account) {
			// 记录玩家登录信息
			LoginLogModel loginModel = new LoginLogModel();
			loginModel.setServer_id(account.getPrevSrvId());
			loginModel.setPlatform(account.getPlatform());
			loginModel.setAccount_id(account.getId());
			loginModel.setRegister_time(account.getCreateTime());
			loginModel.setLast_log_time(new Date());
			loginLogDao.insert(loginModel);
		}*/

		return ResponseFactory.ok(Head.H10002, retb.build());
	}

	@Action(id = Head.H10007, isEncrypted = false)
	public Object Register(ProtocolWeb.RegisterCS msgcs) throws Exception {

		// 子平台注册
		String account = msgcs.getAccount();
		String password = msgcs.getPassword();
		String subPlatform = msgcs.getSubPlatform(); // 子渠道
		
		String games=msgcs.getGames() != null ? msgcs.getGames() : "";
        String adPlatform=msgcs.getAdPlatform() !=null ? msgcs.getAdPlatform() : "";
        
        String adSubPlatform=msgcs.getAdSubPlatform() !=null ?msgcs.getAdSubPlatform() :"";
        String adVersion=msgcs.getAdVersion() !=null ?msgcs.getAdVersion() :"";
        String idfa=msgcs.getIdfa() !=null ? msgcs.getIdfa():"";
		// 检测数据
		if (StringUtils.isBlank(account)) {
			return ResponseFactory.failure(RBMessage.get("1010"));
		}
		if (account.indexOf(" ") > 0) {
			return ResponseFactory.failure(RBMessage.get("1011"));
		}
		// 检测密码
		if (StringUtils.isBlank(password)) {
			return ResponseFactory.failure(RBMessage.get("1012"));
		}
		String platform = msgcs.getPlatform();
		String platformExt = null;
		platform = (!StringUtils.isEmpty(platform)) ? platform : defaultPt;
		if (!platform.equals(defaultPt) && platform.startsWith(defaultPt)) {
			platformExt = platform;
			platform = defaultPt;
		}

		// 检测是否存在账号
		Account accountModel = AccountManager.getAccount(account, platform);
		if (accountModel != null) {
			return ResponseFactory.failure(RBMessage.get("1013"));
		}

		// 激活码验证
		if (PTConfig.booleanVal("activationEnable")) {

			String code = msgcs.getActivationCode();
			// 检测激活码
			if (StringUtils.isBlank(code)) {
				// System.out.println(code);
				return ResponseFactory.failure(RBMessage.get("1014"));
			}
			Activation aModel = activationDao.get(code);
			if (null == aModel) {
				// 激活码未找到
				return ResponseFactory.failure(RBMessage.get("1015"));
			}
			if (aModel.isInvaild()) {
				// 激活码以使用过
				return ResponseFactory.failure(RBMessage.get("1016"));
			}
			aModel.setInvaild(true);
			aModel.setAccount(account);
			activationDao.insertOrUpdate(aModel);
			Integer activationInsertResult = activationDao.insertOrUpdate(aModel);
			int c = (null != activationInsertResult) ? activationInsertResult : 0;
			if (c <= 0) {
				return ResponseFactory.failure(RBMessage.get("1017"));// 修改失败
			}
		}

		// 密码md5转换
		String pwdMd5Str = MD5.encode(password);

		// 新建账号并且注册
		accountModel = new Account(account, pwdMd5Str);
		accountModel.setPlatform(platform);
		accountModel.setPlatformExt(platformExt);
		accountModel.setAccountActived(true);
		accountModel.setSubPlatform((subPlatform != null) ? subPlatform : "");
		accountModel.setGames(games );
		accountModel.setAdPlatform(adPlatform );
		accountModel.setAdSubPlatform(adSubPlatform );
		accountModel.setAdVersion(adVersion);
		accountModel.setIdfa(idfa);
		// 创建账号
		boolean result = AccountManager.insert(accountModel);
		if (!result) {
			return ResponseFactory.failure(RBMessage.get("1006"));
		}
		if(isconfigAdv && StringUtils.isNotEmpty(idfa.trim())){
			queue.offer(accountModel);
		}
		// // 注册返回信息
		// ProtocolWeb.RegisterSC.Builder b =
		// ProtocolWeb.RegisterSC.newBuilder();
		// return ResponseFactory.ok(b.build());

		// 转成登陆操作
		LoginWCS.Builder loginBuilder = LoginWCS.newBuilder();
		// loginBuilder.setAccount(account);
		// loginBuilder.setPassword(password);
		// loginBuilder.setPlatform(platform);
		loginBuilder.setAccount("");
		loginBuilder.setPassword("");
		loginBuilder.setPlatform(platform);
		loginBuilder.setSubPlatform(subPlatform);
		loginBuilder.setVersion(msgcs.getVersion());
		
		loginBuilder.setGames(games);
		loginBuilder.setAdPlatform(subPlatform);
		loginBuilder.setAdSubPlatform(adSubPlatform);
		loginBuilder.setAdVersion(adVersion);
		loginBuilder.setIdfa(idfa);
		
		loginBuilder.setExt("pt=" + platform + "&account=" + account + "&password=" + password);
		return login(loginBuilder.build());
	}
	
	//一键注册
	@Action(id = Head.H10010, isEncrypted = false, isLogon = true)
	public Object AutoRegister(AutoRegisterWCS msgcs) {
        String ip = CodeServlet.getCallIp(); // 获取访问IP
        ip = (ip != null) ? ip : "";
        // 获取账号信息
 		String platform = msgcs.getPlatform();
 		String version = msgcs.getVersion();
 		String  activationCode= msgcs.getActivationCode();
 		String subPlatform = msgcs.getSubPlatform(); // 子渠道
        String games=msgcs.getGames() != null ? msgcs.getGames() : "";
        String adPlatform=msgcs.getAdPlatform() !=null ? msgcs.getAdPlatform() : "";
        String adSubPlatform=msgcs.getAdSubPlatform() !=null ?msgcs.getAdSubPlatform() :"";
        String adVersion=msgcs.getAdVersion() !=null ?msgcs.getAdVersion() :"";
        String idfa=msgcs.getIdfa() !=null ? msgcs.getIdfa():"";
        
        String platformExt = null;
        platform = (!StringUtils.isEmpty(platform)) ? platform : defaultPt;
		if (!platform.equals(defaultPt) && platform.startsWith(defaultPt)) {
			platformExt = platform;
			platform = defaultPt;
		}
        
		//autoPwd
		//autoAccbegin
		
        // 密码md5转换
 		String pwdMd5Str = MD5.encode(autoPwd);
 		
 		String account=null;
 		//后面修改
 		
 		for(int i=0;i<200;i++){
 			int code=(int) (Math.random() * 999999999);
 			
 			Account accountModel= AccountManager.getAccount(autoAccbegin+code, platform);
 			if(accountModel==null){
 				account=autoAccbegin+code;
 				break;
 			}
 			i++;
 		}
 		// 新建账号并且注册
 		Account accountModel = new Account(account, pwdMd5Str);
 		accountModel.setPlatform(platform);
 		accountModel.setPlatformExt(platformExt);
 		accountModel.setVersion(version);
 		accountModel.setAccountActived(false);
 		accountModel.setSubPlatform((subPlatform != null) ? subPlatform : "");
 		accountModel.setGames(games );
 		accountModel.setAdPlatform(adPlatform );
 		accountModel.setAdSubPlatform(adSubPlatform );
 		accountModel.setAdVersion(adVersion);
 		accountModel.setIdfa(idfa);
 		//自动注册账号  0不允许,1允许修改密码
 		accountModel.setAllowChangePwd(1);
 		
 		boolean result = AccountManager.insert(accountModel);
		if (!result) {
			return ResponseFactory.failure(RBMessage.get("1006"));
		}
		
		//效验广告  idfa
        if(isconfigAdv && StringUtils.isNotEmpty(idfa.trim()) ){
        	queue.offer(accountModel);
        }
 		
 		AutoRegisterWSC.Builder builder=AutoRegisterWSC.newBuilder();
 		builder.setAccount(account);
 		builder.setPassword(autoPwd);
 		
        return ResponseFactory.ok(builder.build());
	}

	// 上传激活码
	@Action(id = Head.H20002, isEncrypted = false)
	public Response LoadActivationCode(UpLoadActivationCodeWCS cs) {
		List<Activation> batchList = new ArrayList<>();

		int batchCount = 100;
		int count = cs.getCodeCount();
		for (int i = 0; i < count; i++) {

			proto.ProtocolWeb.ActivationCode activationCode = cs.getCode(i);

			String code = activationCode.getCode();

			if (StringUtils.isEmpty(code)) {
				continue;// 为空
			}
			Activation model = new Activation();
			model.setCode(code);
			model.setCloseTime(Calendar.getInstance().getTime());
			model.setAccount("0");
			batchList.add(model);

			if (batchCount <= batchList.size() || (count - 1) < i) {
				activationDao.insertOrUpdate(batchList);

				// 清空列表
				batchList.clear();
			}
		}

		// 返回结果
		UploadActivationCodeWSC.Builder builder = UploadActivationCodeWSC.newBuilder();
		return ResponseFactory.ok(builder.build());
	}

	@Action(id = Head.H10008, isEncrypted = false)
	public Response ChangePassword(ProtocolWeb.ChangePasswordCS msgcs) throws Exception {
		// 获取数据
		String accountName = msgcs.getAccount();
		String newPassword = msgcs.getNewPassword();
		// 检测账号信息
		if (accountName == null || accountName.length() <= 0) {
			return ResponseFactory.failure(RBMessage.get("1018"));
		}
		accountName = accountName.replace(" ", ""); // 清除空字符
		// 检测密码
		if (StringUtils.isBlank(newPassword)) {
			return ResponseFactory.failure(RBMessage.get("1019"));
		}
		// 检测修改密码
		String password = msgcs.getPassword();
		password = (password != null) ? password : "";
		if (newPassword.equals(password)) {
			return ResponseFactory.failure(RBMessage.get("1020"));
		}

		// 密码md5转换
		password = MD5.encode(password); // md5加密

		// 检测是否存在账号
		Account account = AccountManager.getAccount(accountName, null);
		if (account == null) {
			return ResponseFactory.failure(RBMessage.get("1005"));
		}

		// 检验密码
		if (pwdCheck) {
			String checkPwd = account.getPassword();
			if (!checkPwd.equals(password)) {
				return ResponseFactory.failure(RBMessage.get("1007"));
			}
		}

		newPassword = MD5.encode(newPassword); // md5加密

		// 修改密码
		account.setPassword(newPassword);
		AccountManager.update(account);

		// 返回数据
		ProtocolWeb.ChangePasswordSC.Builder b = ProtocolWeb.ChangePasswordSC.newBuilder();
		return ResponseFactory.ok(b.build());
	}
	
	@Action(id = Head.H10009, isEncrypted = false)
	public Object checkShieldSys(CheckShieldSysWCS msgcs) {
		String version = msgcs.getVersion();
		String subPlatform = msgcs.getSubPlatform(); // 子渠道
		
		// 获取屏蔽当前版本的系统
		ShieldModel shieldModel = ShieldSysManager.getShieldSysList(version);
		// 返回信息
		CheckShieldSysWSC.Builder retb = CheckShieldSysWSC.newBuilder();
		// 屏蔽当前版本的系统
		if (shieldModel != null) {
			String[] subPlatforms = new String[]{};
			if(!StringUtils.isEmpty(shieldModel.getClosedSubPlatform())){
				subPlatforms = shieldModel.getClosedSubPlatform().split(",");
			}
			/*运营要求的策略，如果配了子渠道，默认全部关闭*/
			if(Arrays.asList(subPlatforms).contains(subPlatform))
				retb.addAllShieldSys(ShieldModel.allClosedSys);
			else
				retb.addAllShieldSys(ListExtUtil.arrayToList(shieldModel.getShieldSys()));
		}
		
		return  ResponseFactory.ok(retb.build());
	}
	

	@Action(id = Head.H10003, user = User.System)
	public Response loginCheck(LoginCheckWCS msgcs) throws Exception {
        String ip = msgcs.getClientIp();

        // ip是否被封?
        if (BanIpManager.isBanIp(ip)) {
            return ResponseFactory.failure(RBMessage.get("1021"));
        }

		String loginCode = msgcs.getCode();
		int srvId = msgcs.getSrvId(); // 验证服务器
		Server serverModel = ServerManager.getServer(srvId);
		if (serverModel == null) {
			return ResponseFactory.failure(RBMessage.get("1022"));
		}

		// TODO 版本验证
		// // 检测这个登录码是否正确
		// AccountModel accountModel = LoginKeys.instance.find(loginCode);
		// if (accountModel == null) {
		// return ResponseFactory.failure("验证错误");
		// }
		// // 清除验证码
		// LoginKeys.instance.remove(loginCode);
		// // 读取账号信息
		// long accountId = accountModel.getId();
		// String accountName = accountModel.getAccount();

		// 解码
		LoginKeyUtils.KeyObject keyObj = LoginKeyUtils.decode(loginCode);
		if (keyObj == null) {
			return ResponseFactory.failure(RBMessage.get("1023"));
		}
		long accountId = keyObj.accountId;
		// String accountName = keyObj.accountName;
		// String platform = keyObj.platform;

		// 获取account
		// AccountModel accountModel = accountManager.getAccount(accountName,
		// platform);
		// platform);
		Account account = AccountManager.getAccount(accountId);
		if (account == null) {
			if (Utils.isDebug) {
				return ResponseFactory.failure(RBMessage.get("1024", accountId));
			} else {
				return ResponseFactory.failure(RBMessage.get("1024", ""));
			}
		}
		String accountName = account.getAccount();
		String platform = account.getPlatform();

		// 检测服务器是否关闭
		if (serverModel.getState() == Server.SRV_STATE_CLOSE || serverModel.getState() == Server.SRV_STATE_HIDE) {
			boolean vindicator = account.isVindicator();
			if (!vindicator) {
				// 如果不是维护帐户,验证IP是否属于维护者IP
				// String ip = CodeServlet.getCallIp(); // 获取访问IP
				if (StringUtils.isNotEmpty(ip)) {
					vindicator = VindicatorIpManager.isVindicatorIp(ip);
				}
			}

			if (!vindicator) {
				String text = serverModel.getMaintainText();
				text = (text != null && text.length() > 0) ? text : RBMessage.get("1025");
				return ResponseFactory.failure(text);
			}
		}

		// 获取loginKey
		int loginKey = account.getLoginKey();
		// if (RedisUtils.RedisEnable) {
		// String value = RedisUtils.hget(RedisKeys.K_ACCOUNT_LOGIN_TOKEN,
		// String.valueOf(accountId));
		// if (value == null) {
		// return ResponseFactory.failure("没有找到对应的验证码.");
		// }
		// loginKey = Integer.valueOf(value);
		// } else {
		// loginKey = account.getLoginKey();
		// }
		
		//验证服务器信息
		boolean vindicator = account.isVindicator();
		if (!vindicator && StringUtils.isNotEmpty(ip)) {
			// 判断是否是维护者ip
			vindicator = VindicatorIpManager.isVindicatorIp(ip);
		}
		
		String version =account.getVersion();
		
		List<ServerSection> sectionList = getServerSections(platform, version);
		List<ServerItem> srvList = getServerItems(accountId, sectionList, vindicator, platform, version);
		
		boolean rightSrvId=false;
		for(ServerItem  srv:srvList){
			if(srvId==srv.getSrvId()){
				rightSrvId=true;
			}
		}
		if(!rightSrvId){
			ResponseFactory.failure(RBMessage.get("1022"));
		}
		
		// 验证key
		if (randomKey) {
			if (loginKey != keyObj.key) {
				return ResponseFactory.failure(RBMessage.get("1026"));
			}
		}

		// 标记服务器信息
		if (account.getPrevSrvId() != srvId) {
			account.setPrevSrvId(srvId);
		}
		// 更新服务器时间
		account.setLoginTime(new Date());
		//AccountManager.update(account);

		// 判断是否为快速登录
		String platform0 = platform;
		int findIndex = platform.indexOf("_" + rapidStr);
		boolean rapid = (findIndex > 0);
		if (rapid) {
			// 快速登录处理
			platform0 = platform.substring(0, findIndex);
		}

		// 返回信息
		LoginCheckWSC.Builder retb = LoginCheckWSC.newBuilder();
		retb.setAccountId(accountId);
		retb.setAccountName(accountName);
		retb.setPlatform(platform0);
		retb.setIsRapid(rapid);
		String subPlatform = account.getSubPlatform();
		retb.setChildPlatform((subPlatform != null) ? subPlatform : "");
		if (account.getThirdUserId() != null) {
			retb.setThirdUserId(account.getThirdUserId());
		}
		
		// 记录玩家登录
		if (null != account) {
		// 记录玩家登录信息
		LoginLogModel loginModel = new LoginLogModel();
		loginModel.setServer_id(account.getPrevSrvId());
		loginModel.setPlatform(account.getPlatform());
		loginModel.setAccount_id(account.getId());
		loginModel.setRegister_time(account.getCreateTime());
		loginModel.setLast_log_time(new Date());
		loginLogDao.insert(loginModel);
		}
		
		//查看是否需要发送特权
		try{
		if (special == account.getSpecial()) {
		String special=(null==account.getSpecialcode())?"":account.getSpecialcode();
		PrivilegeInfoCS.Builder b=PrivilegeInfoCS.newBuilder();
		b.setAccount(account.getId());
		b.setPrivilegeIds(special);
		b.setExt(account.getPlatform());
		//Server serverModel=ServerManager.getServer(srvId);
		IClient client = serverModel.createClient();
		if (null !=client) {
			proto.Protocol.PrivilegeInfoSC retMsg=client.call(Head.H1008, b.build(), proto.Protocol.PrivilegeInfoSC.class);
			int state = retMsg.getPrivilegeInfoState();
		}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		account.setSpecial(0);
		AccountManager.update(account);
		return ResponseFactory.ok(retb.build());
		}	
		
		/*Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        AccountBind accountBind = AccountBindManager.getAccountBind(account.getId(), account.getPrevSrvId());
        String uniqueKey = srvId + "-" +account.getId() + "-" + account.getSpecialcode()+ "-" + year + dayOfYear;
        	if( ! UCGiftManager.isExists(uniqueKey)){
        		PrivilegeInfoCS.Builder b=PrivilegeInfoCS.newBuilder();
        		b.setAccount(account.getId());
        		b.setPrivilegeIds(special);
        		b.setExt(account.getPlatform());
        		//Server serverModel=ServerManager.getServer(srvId);
        		IClient client = serverModel.createClient();
        		if (null !=client) {
        			proto.Protocol.PrivilegeInfoSC retMsg=client.call(Head.H1008, b.build(), proto.Protocol.PrivilegeInfoSC.class);
        			
        			int state = retMsg.getPrivilegeInfoState();
        			//特权激活成功，恢复账号为无特权账号
					if(state == 1){
						//记录特权领取
						UCGiftRecord giftRecord = new UCGiftRecord();
			            giftRecord.setGiftId(account.getPlatform()+"-"+special);
			            giftRecord.setServerId(srvId);
			            giftRecord.setPlayerId((int)account.getId());
			            giftRecord.setUcAccountId(account.getAccount());
			            giftRecord.setReceiveTime(new Date());
			            giftRecord.setUniqueKey(uniqueKey);
			            UCGiftManager.insertGiftRecord(giftRecord);
					}
					
        			
        		}
        	}*/
      

	/**
	 * 获取服务器区信息
	 *
	 * @return
	 */
	public static List<ProtocolWeb.ServerSection> getServerSections(String platform, String version) {
		List<Section> list = SectionManager.getSectionList();

		// 生成列表
		List<ServerSection> retList = new ArrayList<ServerSection>();
		int size = (list != null) ? list.size() : 0;
		for (int i = 0; i < size; i++) {
			Section sectionModel = list.get(i);
			if (sectionModel.getState() == Section.STATE_HIDE || !sectionModel.filtrate(platform, version)) {
				continue; // 隐藏不下发
			}
			retList.add(sectionModel.toProtobuf());
		}

		return retList;
	}

	public static int compare0(int a, int b) {
		return (a > b) ? 1 : ((a == b) ? 0 : -1);
	}

	/**
	 * 获取服务器列表信息
	 *
	 * @return
	 */
	public static List<ServerItem> getServerItems(long accountId, List<ServerSection> sectionList, boolean vindicator,
			String platform, String version) {

		// 服务器组件
		List<Server> list = ServerManager.getServerList();
		int count = (list != null) ? list.size() : 0;

		// 生成对应表
		Map<Integer, ServerSection> sectionMap = new HashMap<>();
		Map<Integer, Integer> sectionMap0 = new HashMap<>();
		if (sectionList != null && sectionList.size() > 0) {
			for (ServerSection section : sectionList) {
				sectionMap.put(section.getSectionId(), section);
				sectionMap0.put(section.getSectionId(), 0);
			}
		}

		// 关联信息
		Map<Integer, AccountBind> accountBinds = AccountBindManager.getAccountBindMap(accountId);
		// AccountBinds accountBinds = null;
		// if (accountId > 0) {
		// accountBinds = GameExplorer.getEntity(accountId, AccountBinds.class);
		// }

		// 筛选出可显示的服务列表
		List<Server> serverList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			Server server = list.get(i);
			// 过滤隐藏, 如果是维护者账号就直接显示
			if (!vindicator && server.getState() == Server.SRV_STATE_HIDE) {
				// System.out.println("非维护者账号：" + model.getName());
				continue; // 隐藏不下发
			}

			// 检测区列表是否存在
			int sectionId = server.getSection();
			if (!sectionMap.containsKey(sectionId)) {
				// System.out.println("版本问题不下发:" + model.getName());
				continue;
			}

			// 用户信息
			boolean hashPlayer = false;
			if (accountBinds != null) {
				int srvId = server.getId();
				AccountBind bind = accountBinds.get(srvId);
				if (bind != null) {
					hashPlayer = true;
				}
			}
			// 筛选处理
			if (!server.filtrate(platform, version) && !hashPlayer) {
				continue;
			}

			// 区列表+1
			int value = sectionMap0.get(sectionId);
			sectionMap0.put(sectionId, value + 1);

			// 加入列表
			serverList.add(server);
		}

		// 移除没有服务器的区列表
		Iterator<Map.Entry<Integer, Integer>> iter = sectionMap0.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter.next();
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			// 获取区信息
			ServerSection sction = sectionMap.get(key);
			if (value == null || sction == null) {
				continue;
			}
			// 判断有无服务器
			if (value > 0) {
				continue;
			}
			// 移除空区
			sectionList.remove(sction);
		}

		// 服务器排序
		count = (serverList != null) ? serverList.size() : 0; // 下发服务器数量
		if (count >= 2) {
			// 排序
			Collections.sort(serverList, new Comparator<Server>() {
				@Override
				public int compare(Server o1, Server o2) {
					int ar = o1.getRecommend();
					int br = o2.getRecommend();
					if (ar < br) {
						return 1;
					} else if (ar == br) {
						// 判断优先级
						int a = o1.getPriority();
						int b = o2.getPriority();
						if (a < b) {
							return 1;
						} else if (a == b) {
							// 优先级相等, 按照ID排序
							int aid = o1.getId();
							int bid = o2.getId();
							return compare0(bid, aid);
						}
						return -1;
					}
					return -1;
				}
			});
		}

		// 返回列表
		List<ServerItem> retList = new ArrayList<ServerItem>();
		for (int i = 0; i < count; i++) {
			Server server = serverList.get(i);
			// 生成数据
			ServerItem.Builder sib = ServerItem.newBuilder();
			sib.setSrvId(server.getId());
			sib.setSrvName(server.getName());
			sib.setSrvUrl(server.getUrl());
			sib.setSection(server.getSection());
			sib.setRecommend(server.getRecommend() != 0);
			if (vindicator) {
				// 维护者账号强制正常
				sib.setState(Server.SRV_STATE_NORMAL); // 强制正常显示
			} else {
				// 正常状态
				sib.setState(server.getState());
			}

			// 设置维护文本
			String maintainText = server.getMaintainText();
			if (maintainText != null) {
				sib.setMaintainText(maintainText);
			}

			// 用户信息
			if (accountBinds != null) {
				int srvId = server.getId();
				AccountBind bindModel = accountBinds.get(srvId);
				if (bindModel != null) {
					sib.setPlayerInfo(bindModel.toProtocol());
				}
			}

			// 添加数据
			retList.add(sib.build());
		}
		return retList;
	}

	/**
	 * 获取游戏全局参数
	 *
	 * @param paramName
	 * @param clazz
	 * @return
	 */
	public static <T> T getGlobalParam(String paramName, Class<T> clazz) {
		Param model = ParamManager.getGlobalParam();
		// 检测是否为空
		if (model == null) {
			return null;
		}

		// 返回数据
		T param = model.get(paramName, clazz);
		return param;
	}

	@Action(id = Head.H0, user = User.System)
	public Response errCall() {
		return null;
	}
	
	 
    public static final BlockingQueue<Account> queue = new LinkedBlockingQueue<>();
	
	static {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					//System.out.println("account++=");
					try {
						Account account = queue.take();
						//System.out.println("account1="+account.toString());
						AdvManager.toCheck(account);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	public static void main(String[] args) {
		
		/*Account(long id, String account, String password, String platform, String platformExt, int prevSrvId,
				int loginKey, Date createTime, boolean vindicator, String systemInfo, Date loginTime, String lastip,
				String version, boolean accountActived, String subPlatform, String thirdUserId, String cdkTypes,
				int special, String specialcode, String games, String adPlatform, String adSubPlatform, String adVersion,
				String idfa, int allowChangePwd)*/
		
		/*Account account1 =new Account(11, "account1", "ps1","pt","ptexe", 1,
				123, new Date(), false, "sysinfo", new Date(), "123.1.1.1",
				"1.0.1", false, "spt", "thirdUserId", "cdk",
				0, "specialcode", "ios", "adpt", "adspt", "adv",
				"E256D002-2BA2-4C30-A23F-12F34C326AF8", 1);
		
		Account account2 =new Account(12, "account2", "ps1","pt","ptexe", 1,
				123, new Date(), false, "sysinfo", new Date(), "123.1.1.1",
				"1.0.1", false, "spt", "thirdUserId", "cdk",
				0, "specialcode", "ios", "adpt", "adspt", "adv",
				"E256D002-2BA2-4C30-A23F-12F34C326AF8", 1);*/
		
		Account account3 =new Account(86, "tx705246422", "e10adc3949ba59abbe56e057f20f883e","Saturn_H5","ptexe", 1,
				123, new Date(), false, "sysinfo", new Date(), "123.1.1.1",
				"1.0.1", false, "spt", "thirdUserId", "cdk",
				0, "specialcode", "ios", "adpt", "adspt", "adv",
				"60463F1E-A739-42AD-BD0D-4DE80006B82F", 1);
		
	
		//Account  ac=new Account(86,"tx705246422", password=e10adc3949ba59abbe56e057f20f883e, platform=Saturn_H5, platformExt=null, prevSrvId=0, loginKey=759168, createTime=2017-12-22 10:38:53.0, vindicator=false, systemInfo=deviceModel=iPhone7,2&deviceName=My Phone！&deviceUI=18F4D253-4D67-4D45-ADE3-EF42FE4D15AE&gpuID=0&gpuName=Apple A8 GPU&gpuVID=0&gpuVer=Metal&gpuMem=256&gpuPFR=-1&gpuSL=30&cpuCount=2&sysMem=989&OS=iOS 10.3.3&dpi=326&screenSize=1334x750, loginTime=null, lastip=61.148.221.114, version=2.0.1, accountActived=false, subPlatform=1001, thirdUserId=null, cdkTypes=null, special=0, specialcode=null, games=Ios, adPlatform=, adSubPlatform=, adVersion=, idfa=60463F1E-A739-42AD-BD0D-4DE80006B82F, allowChangePwd=1)

//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		/*queue.offer(account1);
		queue.offer(account2);*/
		queue.offer(account3);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
