package cn.saturn.web.code.action;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import cn.saturn.web.code.Head;
import cn.saturn.web.code.activation.domain.ActivationDAO;
import cn.saturn.web.code.activation.domain.ActivationModel;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.code.login.domain.AccountModel;
import cn.saturn.web.controllers.mail.dao.ParamManager;
import cn.saturn.web.controllers.mail.dao.ParamModel;
import cn.saturn.web.controllers.notice.dao.NoticeManager;
import cn.saturn.web.controllers.notice.dao.NoticeModel;
import cn.saturn.web.controllers.power.VindicatorIp;
import cn.saturn.web.controllers.server.dao.SectionManager;
import cn.saturn.web.controllers.server.dao.SectionModel;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.GameExplorer;
import cn.saturn.web.utils.Utils;
import proto.ProtocolWeb;
import proto.ProtocolWeb.ServerSection;
import proto.ProtocolWeb.UpLoadActivationCodeWCS;
import proto.ProtocolWeb.UploadActivationCodeWSC;
import xzj.core.dispatch.Response;
import xzj.core.dispatch.ResponseFactory;
import xzj.core.dispatch.annotation.Action;
import xzj.core.util.MD5;
import zyt.spring.cache.entity.EntityFactorys;

/**
 * 
 * 
 * @author zhuangyuetao
 * 
 */
@Action
public class LoginAction {
	public final static int defualtSrvId = Utils.config.get("defualtSrvId", 0);

	public final static boolean randomKey = Utils.config.get("randomKey", true);
	public final static boolean pwdMd5 = Utils.config.get("pwdMd5", true);
	public final static boolean pwdCheck = Utils.config.get("pwdCheck", true);
	public final static String rapidStr = "rapid";
	public final static String defaultPt = Utils.config.get("defualtPt", "Saturn"); // 默认注册平台
	public final static String debugPwd = Utils.config.get("debugPwd", "Saturn2015"); // 测试登陆密码
	public final static VindicatorIp vindicatorIp = new VindicatorIp(); // 白名单的IP列表
	protected static final Logger logger = LoggerFactory.getLogger("login");

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
	public ActivationDAO activationDao;

	ConcurrentMap<String, String[]> ptParams = new ConcurrentHashMap<>();

	// 上传激活码
	@Action(id = Head.H20002, isEncrypted = false)
	public Response LoadActivationCode(UpLoadActivationCodeWCS cs) {
		List<ActivationModel> batchList = new ArrayList<>();

		int batchCount = 100;
		int count = cs.getCodeCount();
		for (int i = 0; i < count; i++) {

			proto.ProtocolWeb.ActivationCode activationCode = cs.getCode(i);

			String code = activationCode.getCode();

			if (StringUtils.isEmpty(code)) {
				continue;// 为空
			}
			ActivationModel model = new ActivationModel();
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
			return ResponseFactory.failure("账号错误");
		}
		accountName = accountName.replace(" ", ""); // 清除空字符
		// 检测密码
		if (StringUtils.isBlank(newPassword)) {
			return ResponseFactory.failure("新密码不能为空");
		}

		// 检测修改密码
		String password0 = msgcs.getPassword();
		String password = (password0 != null) ? password0 : "";
		if (newPassword.equals(password)) {
			return ResponseFactory.failure("修改密码相同");
		}

		// 密码md5转换
		if (pwdMd5) {
			password = MD5.encode(password); // md5加密
		}

		// 检测是否存在账号
		//AccountManager accountManager = EntityFactorys.getFactory(AccountManager.class);
		AccountModel accountModel = AccountManager.getAccount(accountName, defaultPt);
		if (accountModel == null) {
			return ResponseFactory.failure("不存在账号");
		}

		// 检验密码
		if (pwdCheck) {
			String checkPwd = accountModel.getPassword();
			if (!checkPwd.equals(password0)) {
				return ResponseFactory.failure("密码错误");
			}
		}

		if (pwdMd5) {
			newPassword = MD5.encode(newPassword); // md5加密
		}

		// 修改密码
		accountModel.setPassword(newPassword);
		AccountManager.update(accountModel);

		// 返回数据
		ProtocolWeb.ChangePasswordSC.Builder b = ProtocolWeb.ChangePasswordSC.newBuilder();
		return ResponseFactory.ok(b.build());
	}


	/**
	 * 获取服务器区信息
	 * 
	 * @return
	 */
	public static List<ProtocolWeb.ServerSection> getServerSections(String platform, String version) {
		List<SectionModel> list = SectionManager.getSectionList();

		// 生成列表
		List<ServerSection> retList = new ArrayList<ServerSection>();
		int size = (list != null) ? list.size() : 0;
		for (int i = 0; i < size; i++) {
			SectionModel sectionModel = list.get(i);
			if (sectionModel.getState() == SectionModel.state_Hide || !sectionModel.filtrate(platform, version)) {
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
	 * 获取游戏全局参数
	 * 
	 * @param paramName
	 * @param clazz
	 * @return
	 */
	public static <T> T getGlobalParam(String paramName, Class<T> clazz) {
		// 使用redis共享区列表
		ParamModel model = null;
		if (GameExplorer.redisEnable) {
			String jsonStr = RedisUtils.get("globalParam");
			// String jsonStr = "{\"theme\":1}";
			try {
				model = JSON.parseObject(jsonStr, ParamModel.class);
			} catch (Exception e) {
			}
		}

		// 使用本地变量
		if (model == null) {
			model = ParamManager.getGlobalParam();
		}
		// 检测是否为空
		if (model == null) {
			return null;
		}

		// 返回数据
		T param = model.get(paramName, clazz);
		return param;
	}
}
