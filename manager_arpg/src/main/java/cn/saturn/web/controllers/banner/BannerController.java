package cn.saturn.web.controllers.banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.saturn.web.controllers.banner.dao.BannerModel;
import cn.saturn.web.controllers.banner.dao.BannerModelManager;
import cn.saturn.web.controllers.banner.dao.PresetBanner;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.controllers.server.dao.ServerXFtpDAO;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.sftp.SftpTools;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import zyt.spring.component.ComponentManager;

@Path("")
public class BannerController {

	@Resource
	ServerXFtpDAO serverXFtpDao;

	@Resource
	BannerModelManager bannerModelMgr;
	
	@Resource
	PresetDAO presetDAO;
	
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.banner_page)
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/banner/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/banner/add"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/banner/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/banner/toDelete"));
		Utils.setAttributeValue(inv, "uploadUrl", Utils.url(inv, "/banner/upload"));

		return "list";
	}

	@UserAuthority(PageModel.banner_page)
	@Get("add")
	public String add(Invocation inv) {
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/banner/toEdit"));

		PresetModel model = null;
		PresetBanner presetData = null;
		model = new PresetModel();
		//model.resetId();
		
		model.setType(PresetManager.presetType_banner);
		presetData = new PresetBanner();
		presetData.setId((int) PresetManager.getInstance().newId());

		model.setValue(presetData);
		//PresetManager.getInstance().insertByDAO(model);
		int modelId=presetDAO.insertNew(model);
		Utils.setAttributeValue(inv, "id", modelId);
		return "edit";
	}

	@UserAuthority(PageModel.banner_page)
	@Get("edit")
	public String edit(Invocation inv) {
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/banner/toEdit"));
		long id = Utils.getLongParameter(inv, "id");
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id 不存在.");

		PresetBanner presetData = model.getValue(PresetBanner.class);
		if (presetData == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "presetData 不存在.");

		Utils.setAttributeValue(inv, "id", id);
		Utils.setAttributeValue(inv, "open_state", presetData.isOpenTime() ? 1 : 0);
		Utils.setAttributeValue(inv, "openTime", presetData.getSendOpenTime());
		Utils.setAttributeValue(inv, "normalTime", presetData.getSendNormalTime());
		Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
		Utils.setAttributeValue(inv, "remark", model.getRemark());
		String[] banners = presetData.getBanners();

		if (banners != null) {
			for (int i = 0; i < banners.length; i++) {
				Utils.setAttributeValue(inv, "banner" + (i + 1), banners[i]);
			}
		}
		return "edit";
	}

	@UserAuthority(PageModel.banner_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("open_state") int open_state, @Param("id") long id,
			@Param("normalTime") String normalTime, @Param("openTime") int openTime, @Param("srvIds") String srvIdStrs,
			@Param("banner1") String banner1, @Param("banner2") String banner2, @Param("banner3") String banner3,
			@Param("banner4") String banner4, @Param("remark") String remark) throws Throwable {
		PresetModel model = null;
		PresetBanner presetData = null;

		model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");

		presetData = model.getValue(PresetBanner.class);
		if (presetData == null) {
			presetData = new PresetBanner();
		}
		model.setRemark(remark);

		presetData.setSendNormalTime(normalTime);
		presetData.setSendOpenTime(openTime);
		presetData.setOpenTime(open_state == 1);

		// 保存banner图标
		List<String> bannerList = new ArrayList<>();
		if (!StringUtils.isEmpty(banner1))
			bannerList.add(banner1);
		if (!StringUtils.isEmpty(banner2))
			bannerList.add(banner2);
		if (!StringUtils.isEmpty(banner3))
			bannerList.add(banner3);
		if (!StringUtils.isEmpty(banner4))
			bannerList.add(banner4);

		presetData.setBanners(ListExtUtil.listToArray(bannerList, new String[] {}));

		// 保存服务器id
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
		presetData.setSrvs(srvIds);
		model.setValue(presetData);
		PresetManager.getInstance().updateByDAO(model);

		// 发送检查
		bannerUpdateByTime(presetData);
		
//		bannerUpdateByDate(presetData);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "创建或更改banner成功.");
	}
	
	public void bannerUpdateByTime(PresetBanner m) {
		try {
			// 1.按照时间发送
			PresetBanner pBanner = null;

			//所有活动
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_activity);
			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			List<ServerXFtpModel> sModel;
			int[] svrIds = m.getSrvs();
			for (ServerModel sm : srvlist) {
				sModel = new ArrayList<>();
				ServerXFtpModel sft = serverXFtpDao.getBySid((int) sm.getId());
			    
				
				if (sft == null)
					continue;
				
				if (m != null) {
					if (!m.isOpenTime() && m.getSendNormalTime().equals(DateUtils.getNowDay())) {
						pBanner = m;
					}
					
					String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), m.getSendOpenTime());
					// 如果时间能匹配上
					if (m.isOpenTime() && openTimeStr.equals(DateUtils.getNowDay())) {
						pBanner = m;
					}
				}
				
				if(!ArrayUtils.contains(svrIds, (int)sm.getId()))
					continue;
				
				sModel.add(sft);
				
				bannerModelMgr.writeFile(BannerModel.createBanners(pBanner, sft.getS_id(), all));
				SftpTools.exec(sModel, BannerModelManager.writeSingle);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public void bannerUpdateByTime(PresetBanner presetData) {
		if (!presetData.isOpenTime() && presetData.getSendNormalTime().equals(DateUtils.getNowDay())) {
			bannerModelMgr.writeFile(BannerModel.createBanners(presetData));

			// 要发送的服务器
			ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
			List<ServerModel> srvlist = serverComponent.getList();
			int[] svrs = presetData.getSrvs();
			if(svrs == null || svrs.length == 0)
				return;
			List<ServerXFtpModel> sModel = new ArrayList<>();
			boolean status = false;
			for (ServerModel sm : srvlist) {
				for(int i =0; i<svrs.length; i++){
					if(svrs[i] == sm.getId())
						status = true;
				}
				if(!status)
					continue;
				ServerXFtpModel model = serverXFtpDao.getBySid((int) sm.getId());
				if (model != null)
					sModel.add(model);
				status = false;
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
			int[] svrs = presetData.getSrvs();
			boolean status = false;
			for (ServerModel sm : srvlist) {
				for(int i =0; i<svrs.length; i++){
					if(svrs[i] == sm.getId())
						status = true;
				}
				if(!status)
					continue;
				
				// 遍历服务器模块
				ServerXFtpModel sftpModel = serverXFtpDao.getBySid((int) sm.getId());
				if (sftpModel != null) {
					String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), presetData.getSendOpenTime());
					// 如果时间能匹配上
					if (presetData.isOpenTime() && openTimeStr.equals(DateUtils.getNowDay())) {
						sModel.add(sftpModel);
					}
				}
				status = false;
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
	@UserAuthority(PageModel.banner_page)
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "/banner/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "banner/list?mainView=true");
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
				PresetBanner presetData = model.getValue(PresetBanner.class);
				if (presetData != null) {
					map.put("openTime", presetData.getSendNormalTime());
					map.put("srvs", presetData.getSrvs());
					map.put("banners", presetData.getBanners());
				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_banner, action);

	}
}
