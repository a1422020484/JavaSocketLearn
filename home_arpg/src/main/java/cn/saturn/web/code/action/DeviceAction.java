package cn.saturn.web.code.action;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.adv.dao.AdvManager;
import cn.saturn.web.controllers.device.dao.DeviceDAO;
import cn.saturn.web.controllers.device.dao.DeviceModel;
import cn.saturn.web.utils.EmojiFilter;
import cn.saturn.web.utils.HttpParam;
import cn.saturn.web.utils.MD5Util;
import proto.ProtocolWeb.RegisterDeviceWCS;
import proto.ProtocolWeb.RegisterDeviceWSC;
import xzj.core.dispatch.Response;
import xzj.core.dispatch.ResponseFactory;
import xzj.core.dispatch.annotation.Action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 设备激活协议
 *
 * @author rodking
 */
@Action
public class DeviceAction {
    private final String MD5_KEY = "gmcs_test_key";

    @Resource
    DeviceDAO deviceDao;
    
    protected static final Logger deviceAction = LoggerFactory.getLogger("deviceAction");

    // 上传激活码
    @Action(id = Head.H21000, isEncrypted = false)
    public Response LoadActivationCode(RegisterDeviceWCS msgcs) {
    	
    	deviceAction.info("deviceAction::="+msgcs.toString()+",,,regTime="+msgcs.getRegTime());
    	
        String md5Str = msgcs.getMd5();
        long regTime = msgcs.getRegTime();
        String sys = EmojiFilter.filterEmoji(msgcs.getSys());
        String platform = msgcs.getPlatform();
        String subPlatform=msgcs.getSubPlatform() !=null? msgcs.getSubPlatform() : "";
        String games=msgcs.getGames() != null ? msgcs.getGames() : "";
        String adPlatform=msgcs.getAdPlatform() !=null ? msgcs.getAdPlatform() : "";
        String adSubPlatform=msgcs.getAdSubPlatform() !=null ?msgcs.getAdSubPlatform() :"";
        String adVersion=msgcs.getAdVersion() !=null ?msgcs.getAdVersion() :"";
        String idfa=msgcs.getIdfa() !=null ? msgcs.getIdfa():"";
        /*String games=msgcs.getGames();
        String adPlatform=msgcs.getAdPlatform();
        String adSubPlatform=msgcs.getAdSubPlatform();
        String adVersion=msgcs.getAdVersion();*/

        RegisterDeviceWSC.Builder rdb = RegisterDeviceWSC.newBuilder();

        if (System.currentTimeMillis() - regTime > 20 * 1000) {
            rdb.setState(0);
            return ResponseFactory.ok(rdb.build());
        }

        if (checkIdentity(md5Str, regTime, sys)) {
            rdb.setState(0);
            return ResponseFactory.ok(rdb.build());
        }

        Map<String, String> params = HttpParam.parse(sys);
        String deviceUI = params.get("deviceUI");
        String OS = params.get("OS");
        String deviceModel = params.get("deviceModel");

        // 把设备数据记录到数据库中
        DeviceModel model = new DeviceModel();
        model.setOS(OS);
        model.setDeviceUI(deviceUI);
        model.setDeviceModel(deviceModel);
        model.setPlatform(platform);
        model.setSystemInfo(sys);
        model.setSubPlatform(subPlatform);
        model.setGames(games);
        model.setAdPlatform(adPlatform);
        model.setAdSubPlatform(adSubPlatform);
        model.setAdVersion(adVersion);
        model.setIdfa(idfa);
        
        //零时解决IOS 没传的games
        String game=null;
        if(!games.trim().toLowerCase().equals("android")){
        	game="ios";	
        }else{
        	game="android";
        }
       // StringUtils.isNotEmpty(games.trim())||
        
        //效验广告  idfa
       /* if(StringUtils.isNotEmpty(idfa.trim())){
        	AdvManager.toCheck(model,game.toLowerCase(), idfa);
        }*/
        
        deviceDao.insert(model);
        
        rdb.setState(1);
        return ResponseFactory.ok(rdb.build());
    }

    public boolean checkIdentity(String md5Str, long regTime, String sys) {
        String str = sys + regTime + MD5_KEY;
        str = MD5Util.MD5(str);
        return md5Str.equals(str);
    }
}
