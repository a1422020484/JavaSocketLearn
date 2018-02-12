package cn.saturn.web.code.action;

import cn.saturn.web.code.Head;
import cn.saturn.web.code.cdkey.domain.CDKey;
import cn.saturn.web.code.cdkey.domain.CDKeyManager;
import cn.saturn.web.code.login.domain.Account;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.RBMessage;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import proto.ProtocolWeb;
import proto.ProtocolWeb.GetCDKeyWCS;
import proto.ProtocolWeb.GetCDKeyWSC;
import proto.ProtocolWeb.UploadCDKeyWCS;
import proto.ProtocolWeb.UploadCDKeyWSC;
import xzj.core.dispatch.Response;
import xzj.core.dispatch.ResponseFactory;
import xzj.core.dispatch.annotation.Action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuangyuetao
 */
@Action
public class CDKeyAction {
	public static final int keyState_success = 1;
	public static final int keyState_fail = 0;
	
	//public static final int keyState_error = -1;
	//public static final int keyState_nofind = 0;
	//public static final int keyState_ok = 1;
	//public static final int keyState_isget = 2;
	//public static final int keyState_isHold = 3;

	@Action(id = Head.H17001, isEncrypted = false)
	public synchronized Response GetCDKey(GetCDKeyWCS cs) throws Exception {
		// 读取渠道信息
		String key = cs.getKey();
		if (StringUtils.isEmpty(key)) {
			return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1028")).build());
		}

		// 读取CDKey
		CDKey cdkey = CDKeyManager.getCdKey(key); // dao.get(key);
		if (cdkey == null) {
			return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1029")).build());
		}
		
		//判断是否达到数量上限
		if(cdkey.getUseLimit()>0){
			if(cdkey.getUsedNum() >= cdkey.getUseLimit()){
				return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1037")).build());
			}
		}

		// 判断是否领取过
		if (cdkey.isEnable()) {
			return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1030")).build());
		}

		// 判断是否过期
		Date nowTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date afterTime = sdf.parse(cdkey.getOverTime());
		if (afterTime.getTime() - nowTime.getTime() < 0) {
			return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1031")).build());
		}
		
		//判断个人是否达到上限
		if (cdkey.getUseCount() > 0) {
			if (CDKeyManager.getUsedNum(cdkey, cs.getPlayerid(), cs.getServerid()) >= cdkey.getUseCount()) {
				return ResponseFactory
						.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1036")).build());
			}
		}else{
			if (CDKeyManager.getUsedNum(cdkey, cs.getPlayerid(), cs.getServerid()) >= 0) {
				return ResponseFactory
						.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1030")).build());
			}
		}

		// 判断是否是在平台过滤
		String subPlatform = cs.getSubPlatform();
		subPlatform = subPlatform == null ? "" : subPlatform;
		String cdkPlatform = cdkey.getPlatformLimit();
		cdkPlatform = cdkPlatform == null ? "" : cdkPlatform;

		// not null 表示开启平台过滤
		if (!cdkPlatform.equals("")) {
			List<String> cdkPlatforms = ListExtUtil.arrayToList(cdkPlatform.split(";"));

			// 带 !
			if (cdkPlatform.contains("!")) {
				// 如果配置!xx 找到这个平台,返回没找到 xx
				if (cdkPlatforms.contains("!" + subPlatform)) {
					return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1032")).build());
				}
			}
			// 如果配置中没有这个平台,返回没找到
			else if (!cdkPlatforms.contains(subPlatform)) {
				return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1032")).build());
			}
		}

		if (cdkey.getPlayerid() != 0) {
			// 如果有人领取过,返回, 全局礼包 用户始终为 0
			return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1033")).build());
		}

		Account account = AccountManager.getAccount(cs.getAccountid());
		
		if(account==null)
		{
			// 用户不存在
			return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1034")).build());
		}
		
		int playerId = cs.getPlayerid();
		String hcdkeyTypes = account.getCdkTypes();
		Map<Integer, List<Integer>> types = new HashMap<>();
		// 查找是否有领取过同类型礼包
		if (!StringUtils.isEmpty(hcdkeyTypes)) {
			try {
				types = JSON.parseObject(hcdkeyTypes, Map.class);// JSON.parseObject(hcdkeyTypes);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (types != null && types.containsKey(playerId)) {
				List<Integer> cdkTypes = types.get(playerId);
				int count = 0;
				for(Integer i : cdkTypes){
					if(cdkey.getType() == i)
						count ++;
				}
				if(count >= cdkey.getUseCount()){
					return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1036")).build()); 
				}
//				if (cdkTypes.contains(cdkey.getType())) {
//					// 有领取过这个类型的礼包
//					return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1035")).build());
//				}
			}
		}

		if (!cdkey.isGlobal()) {
			int serverid = cs.getServerid();
			int playerid = cs.getPlayerid();
			// 标记领取
			cdkey.setEnable(true);
			cdkey.setPlayerid(playerid);
			cdkey.setServerid(serverid);
			cdkey.setUsedNum(cdkey.getUsedNum()+1);
			try {
				boolean rs = CDKeyManager.insertOrUpdate(cdkey);
				if (!rs) {
					return ResponseFactory.ok(GetCDKeyWSC.newBuilder().setEnable(keyState_fail).setMsg(RBMessage.get("1035")).build()); // 修改失败
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			cdkey.setUsedNum(cdkey.getUsedNum()+1);
			CDKeyManager.insertOrUpdate(cdkey);
		}

		// 更新用户领取的 礼包类型
		List<Integer> value = new ArrayList<>();
		if (types.containsKey(playerId)) {
			value = types.get(playerId);
		}

		value.add(cdkey.getType());
		types.put(playerId, value);
		try {
			account.setCdkTypes(JSON.toJSONString(types));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cdkey.isGlobal())
			AccountManager.update(account);

		// 返回数据
		GetCDKeyWSC.Builder b = GetCDKeyWSC.newBuilder();
		b.setEnable(keyState_success);
		b.setAward(cdkey.getAward());
		b.setGlobal(cdkey.isGlobal());
		b.setType(cdkey.getType());
		return ResponseFactory.ok(b.build());
	}

	// @Action(id = Head.H17002, user = User.System)
	@Action(id = Head.H17002, isEncrypted = false)
	public Response UpdataCDKey(UploadCDKeyWCS cs) throws Exception {

		final int batchCount = 2000;
		List<CDKey> batchList = new ArrayList<>();

		// 插入cdkey
		int count = cs.getKeyCount();
		for (int i = 0; i < count; i++) {
			ProtocolWeb.CDKey cdkey = cs.getKey(i);
			if (cdkey == null) {
				continue;
			}
			// 获取数据
			String key = cdkey.getKey();
			if (StringUtils.isEmpty(key)) {
				continue; // 空
			}
			String award = cdkey.getAward();
			boolean global = cdkey.getGlobal();

			// 生成并插入
			CDKey cdKey = new CDKey();
			cdKey.setKey(key);
			cdKey.setAward(award);
			cdKey.setGlobal(global);
			batchList.add(cdKey);

			// 加入批量列表
			if (batchList.size() >= batchCount || i >= count - 1) {
				try {
					// 批量插入
					CDKeyManager.insertOrUpdate(batchList);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 清除
				batchList.clear();
			}
		}

		// 返回结果
		UploadCDKeyWSC.Builder b = UploadCDKeyWSC.newBuilder();
		return ResponseFactory.ok(b.build());
	}

}
