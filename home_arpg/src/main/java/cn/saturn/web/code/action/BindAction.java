package cn.saturn.web.code.action;

import cn.saturn.web.code.Head;
import cn.saturn.web.code.bind.domain.AccountBind;
import cn.saturn.web.code.bind.domain.AccountBindManager;
import cn.saturn.web.utils.RBMessage;
import proto.Protocol.PlayerInfo;
import proto.ProtocolWeb.UpdatePlayerWCS;
import proto.ProtocolWeb.UpdatePlayerWSC;
import xzj.core.dispatch.Response;
import xzj.core.dispatch.ResponseFactory;
import xzj.core.dispatch.annotation.Action;
import xzj.core.dispatch.annotation.Action.User;

import java.util.Date;

/**
 * @author zhuangyuetao
 */
@Action
public class BindAction {
    @Action(id = Head.H10004, user = User.System)
    public Response updatePlayer(UpdatePlayerWCS msgcs) {
        PlayerInfo playerInfo = msgcs.getPlayerInfo();
        int playerId = playerInfo.getId();
        if (playerId <= 0) {
            return ResponseFactory.failure(RBMessage.get("1027"));
        }

        // 读取绑定信息
        long accountId = msgcs.getAccountId();
        int srvId = msgcs.getSrvId();


        // 获取数据
//		if (accountBinds == null) {
//			return ResponseFactory.failure("不存在该账号ID");
//		}

        // LockManager.instance.writeLock(accountId);

//        Map<Integer, AccountBind> accountBinds = AccountBindsManager.getAccountBindMap(accountId);
        // 查找
        AccountBind accountBind = AccountBindManager.getAccountBind(accountId, srvId);
        if (accountBind == null) {
            // 创建
            accountBind = new AccountBind();
            accountBind.setAccountId(accountId);
            accountBind.setSrvId(srvId);
            accountBind.setCreateTime(new Date());
        }
        // 修改数据
        accountBind.setPlayerId(playerInfo.getId());
        accountBind.setPlayerLv(playerInfo.getLv());
        accountBind.setPlayerName(playerInfo.getName());
        accountBind.setIconUrl(playerInfo.getIconUrl());
        accountBind.setIconFrame(playerInfo.getIconFrame());
        accountBind.setFightingCapacity(playerInfo.getFightingCapacity());
        accountBind.setVipLv(playerInfo.getVipLv());

        // 保存
        if (accountBind.getId() <= 0) {
            // 新增
            AccountBindManager.insert(accountBind);
        } else {
            // 修改
            AccountBindManager.insertOrUpdate(accountBind);
        }

        // LockManager.instance.writeUnlock(accountId);
        // 返回信息
        UpdatePlayerWSC.Builder retb = UpdatePlayerWSC.newBuilder();
        return ResponseFactory.ok(retb.build());
    }
}
