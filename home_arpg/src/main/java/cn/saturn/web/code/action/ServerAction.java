package cn.saturn.web.code.action;

import cn.saturn.web.code.CodeServlet;
import cn.saturn.web.code.Head;
import cn.saturn.web.code.login.domain.Account;
import cn.saturn.web.code.login.domain.AccountManager;
import cn.saturn.web.controllers.power.VindicatorIpManager;
import org.apache.commons.lang.StringUtils;
import proto.ProtocolWeb.ServerItem;
import proto.ProtocolWeb.ServerListWCS;
import proto.ProtocolWeb.ServerListWSC;
import proto.ProtocolWeb.ServerSection;
import xzj.core.dispatch.Response;
import xzj.core.dispatch.ResponseFactory;
import xzj.core.dispatch.annotation.Action;

import java.util.List;

/**
 * @author zhuangyuetao
 */
@Action
public class ServerAction {

    @Action(id = Head.H10001, isEncrypted = false)
    public Response serverList(ServerListWCS cs) throws Exception {
        // 读取渠道信息
        String platform = cs.getPlatform();
        platform = (platform != null) ? platform : "";
        String version = cs.getVersion();

		// 获取账号ID
		long accountId = cs.getAccountId();
		Account account = AccountManager.getAccount(accountId);

		// 修改为读取子渠道
        if (account != null) {
            platform = account.getSubPlatform();
            platform = (platform != null) ? platform : "";
        }

        // 维护者账号检测
        boolean vindicator = (account != null) ? account.isVindicator() : false;
        if (!vindicator) {
            // 访问的IP
            String ip = CodeServlet.getCallIp(); // 获取访问IP
            ip = (ip != null) ? ip : "";
            if (!StringUtils.isEmpty(ip)) {
                // 判断是否是维护者ip
                vindicator = VindicatorIpManager.isVindicatorIp(ip);
            }
        }

        // 读取服务器列表
        List<ServerSection> sectionList = LoginAction.getServerSections(platform, version);
        List<ServerItem> srvList = LoginAction.getServerItems(accountId, sectionList, vindicator, platform, version);

        // 返回数据
        ServerListWSC.Builder b = ServerListWSC.newBuilder();
        b.addAllSectionList(sectionList);
        b.addAllSrvList(srvList);
        return ResponseFactory.ok(b.build());
    }
}
