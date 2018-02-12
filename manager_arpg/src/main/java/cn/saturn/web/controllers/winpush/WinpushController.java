package cn.saturn.web.controllers.winpush;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.saturn.web.controllers.banner.dao.BannerModel;
import cn.saturn.web.controllers.banner.dao.BannerModelManager;
import cn.saturn.web.controllers.banner.dao.PresetBanner;
import org.apache.commons.lang.StringUtils;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.mail.dao.ActionResult;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.server.dao.ServerXFtpDAO;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.controllers.winpush.dao.PresetWinpush;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.sftp.SftpTools;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb;
import zyt.spring.component.ComponentManager;

@Path("")
public class WinpushController {

	@Resource
	ServerXFtpDAO serverXFtpDao;

	@Resource
	BannerModelManager bannerModelMgr;
	
	@Resource
	PresetDAO presetDAO;

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.winpush_page)
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/winpush/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/winpush/add"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/winpush/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/winpush/toDelete"));
		Utils.setAttributeValue(inv, "uploadUrl", Utils.url(inv, "/winpush/upload"));

		return "list";
	}

	@UserAuthority(PageModel.winpush_page)
	@Get("add")
	public String add(Invocation inv) {
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/winpush/toEdit"));

		PresetModel model = null;
		PresetBanner presetData = null;
		model = new PresetModel();
		//model.resetId();
		model.setType(PresetManager.presetType_winpush);
		presetData = new PresetBanner();
		presetData.setId((int) PresetManager.getInstance().newId());

		model.setValue(presetData);
		//PresetManager.getInstance().insertByDAO(model);
		int modelId=presetDAO.insertNew(model);
		Utils.setAttributeValue(inv, "id", modelId);
		return "edit";
	}

	@UserAuthority(PageModel.winpush_page)
	@Get("edit")
	public String edit(Invocation inv) {
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/winpush/toEdit"));
		long id = Utils.getLongParameter(inv, "id");
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id 不存在.");

		PresetWinpush presetData = model.getValue(PresetWinpush.class);
		if (presetData == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "presetData 不存在.");

		Utils.setAttributeValue(inv, "id", id);
		Utils.setAttributeValue(inv, "open_state", presetData.isOpenTime() ? 1 : 0);
		Utils.setAttributeValue(inv, "openTime", presetData.getSendOpenTime());
		Utils.setAttributeValue(inv, "normalTime", presetData.getSendNormalTime());
		Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
		Utils.setAttributeValue(inv, "remark", model.getRemark());
		String[] pushs = presetData.getPushs();
		Integer[] orders = presetData.getOrders();

		if (pushs != null) {
			for (int i = 0; i < pushs.length; i++) {
				Utils.setAttributeValue(inv, "winpush" + (i + 1), pushs[i]);
			}
		}
		if(orders !=null){
			for(int i = 0; i < orders.length; i++){
				Utils.setAttributeValue(inv, "pushorder" + (i + 1), orders[i].intValue());
			}
		}
		return "edit";
	}

	@UserAuthority(PageModel.winpush_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("open_state") int open_state, @Param("id") long id,
			@Param("normalTime") String normalTime, @Param("openTime") int openTime, @Param("srvIds") String srvIdStrs,
			@Param("winpush1") String winpush1, @Param("winpush2") String winpush2, @Param("winpush3") String winpush3,
			@Param("winpush4") String winpush4, @Param("pushorder1") int pushorder1, @Param("pushorder2") int pushorder2, @Param("pushorder3") int pushorder3,
			@Param("pushorder4") int pushorder4, @Param("remark") String remark) throws Throwable {
		PresetModel model = null;
		PresetWinpush presetData = null;

		model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");

		presetData = model.getValue(PresetWinpush.class);
		if (presetData == null) {
			presetData = new PresetWinpush();
		}
		model.setRemark(remark);

		presetData.setSendNormalTime(normalTime);
		presetData.setSendOpenTime(openTime);
		presetData.setOpenTime(open_state == 1);

		// 保存banner图标
		List<String> pushList = new ArrayList<>();
		if (!StringUtils.isEmpty(winpush1))
			pushList.add(winpush1);
		if (!StringUtils.isEmpty(winpush2))
			pushList.add(winpush2);
		if (!StringUtils.isEmpty(winpush3))
			pushList.add(winpush3);
		if (!StringUtils.isEmpty(winpush4))
			pushList.add(winpush4);
		
		List<Integer> orderList = new ArrayList<>();
		orderList.add(pushorder1);
		orderList.add(pushorder2);
		orderList.add(pushorder3);
		orderList.add(pushorder4);

		presetData.setPushs(ListExtUtil.listToArray(pushList, new String[] {}));
		presetData.setOrders(ListExtUtil.listToArray(orderList, new Integer[] {}));

		// 保存服务器id
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
		presetData.setSrvs(srvIds);
		model.setValue(presetData);
		PresetManager.getInstance().updateByDAO(model);

		// 发送检查
//		bannerUpdateByTime(presetData);
//		bannerUpdateByDate(presetData);
		List<ResultMsg> msgs = send2Svr(presetData);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "创建或更改弹窗成功."+msgs);
	}
	
	//push到游戏服务器
	public List<ResultMsg> send2Svr(PresetWinpush presetData){
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> svrlist = serverComponent.getList();
		int[] svrs =  presetData.getSrvs();
		if(svrs == null || svrs.length==0)
			return null;
		boolean status = false;
		List<ResultMsg> resultMsg = new ArrayList<>();
		List<String> pushs = Arrays.asList(getPushOrder(presetData.getPushs(), presetData.getOrders())); 
		for(ServerModel sm : svrlist){
			for(int i=0; i<svrs.length; i++){
				if(svrs[i] == sm.getId()){
					status = true;
					break;
				}
			}
			if(status){
				//doSomething
				IClient client = sm.createClient();
				if(client!=null){
					ProtocolWeb.WinPushCS.Builder builder = ProtocolWeb.WinPushCS.newBuilder();
					builder.addAllPushs(pushs);
					ProtocolWeb.WinPushSC winPushSC = client.call(Head.H19004, builder.build(), ProtocolWeb.WinPushSC.class);
					if(winPushSC == null){
						resultMsg.add(new ResultMsg(ResultMsg.fail,"<br>服务器[" + sm.getSrvStr0() + "] 发送失败"));
					}
				}
			}
			status = false;
		}
		if(resultMsg.size() == 0)
			resultMsg.add(new ResultMsg(ResultMsg.succ,"发送服务器成功"));
		return resultMsg;
	}
	
	

	/*public void bannerUpdateByTime(PresetBanner presetData) {
		if (!presetData.isOpenTime() && presetData.getSendNormalTime().equals(DateUtils.getNowDay())) {
			bannerModelMgr.writeFile(BannerModel.createBanners(presetData));

			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel = new ArrayList<>();
			for (ServerModel sm : srvlist) {
				ServerXFtpModel model = serverXFtpDao.getBySid((int) sm.getId());
				if (model != null)
					sModel.add(model);
			}
			SftpTools.exec(sModel, BannerModelManager.writeSingle);
		}
	}

	private void bannerUpdateByDate(PresetBanner presetData) {
		// 2.按照开服时间
		try {
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel = new ArrayList<>();

			for (ServerModel sm : srvlist) {
				// 遍历服务器模块
				ServerXFtpModel sftpModel = serverXFtpDao.getBySid((int) sm.getId());
				if (sftpModel != null) {
					String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), presetData.getSendOpenTime());
					// 如果时间能匹配上
					if (presetData.isOpenTime() && openTimeStr.equals(DateUtils.getNowDay())) {
						sModel.add(sftpModel);
					}
				}
			}

			// 如果有满足条件的这里统一发送
			if (presetData != null && sModel.size() > 0) {
				bannerModelMgr.writeFile(BannerModel.createBanners(presetData));
				SftpTools.exec(sModel, BannerModelManager.writeSingle);

				// 发送完毕后清除数据为下一次计算做准备
				sModel.clear();
				presetData = null;
			}

		} catch (Exception e) {
		}
	}
*/
	// 删除预设页面
	@LoginCheck
	@UserAuthority(PageModel.winpush_page)
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "/winpush/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "winpush/list?mainView=true");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {

		// 转换接口
		BsgridUtils.IConvert<PresetModel> action = new BsgridUtils.IConvert<PresetModel>() {

			@Override
			public boolean convert(PresetModel model, Map<String, Object> map) {
				map.put("id", model.getId());
				map.put("remark", model.getRemark());
				// 解析数据
				PresetWinpush presetData = model.getValue(PresetWinpush.class);
				if (presetData != null) {

					map.put("srvs", presetData.getSrvs());
					
					String[] pushs = presetData.getPushs();
					Integer[] orders = presetData.getOrders();
					
					map.put("orders", getPushOrder(pushs, orders));
				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_winpush, action);

	}
	
	private String[] getPushOrder(String[] pushs, Integer[] orders){
		String[] pushNames = new String[]{"闪光来袭","轮盘活动","限时神兽","天天活动"};
		List<String> result = new ArrayList<>();
		if(orders != null){
			int lengh = orders.length;
			while(lengh>0){
				int max = Integer.MAX_VALUE;
				int index = 0;
				for(int i = 0; i<orders.length; i++){
					if(orders[i] >0 && orders[i]<max){
						max = orders[i];
						index = i;
					}
				}
				if(!"0".equals(pushs[index]))
				result.add(pushNames[index]);
				orders[index] = -1;
				lengh--;
			}
		}
		return result.toArray(new String[result.size()]);
	}
}
