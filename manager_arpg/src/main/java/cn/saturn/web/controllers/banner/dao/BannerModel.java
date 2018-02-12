package cn.saturn.web.controllers.banner.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.activity.dao.PresetActivity;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.DateUtils;
import proto.ProtocolWeb.ActivityItem;
import proto.ProtocolWeb.GetActivityListWCS;
import proto.ProtocolWeb.GetActivityListWSC;
import zyt.spring.component.ComponentManager;

public class BannerModel {
	private transient final long banner1 = 180200001;
	private transient final long banner2 = 180200002;
	private transient final long banner3 = 180200003;
	private transient final long banner4 = 180200004;

	protected long billboardId; // 展示图id
	protected int billboardType = 1; // 展示图类型
	protected int billboardOrder; // 展示图排序
	protected String billboardResPath; // 展示图文件

	public static List<BannerModel> createBanners(PresetBanner banner, int svrId, List<PresetModel> activitieList) {
		List<BannerModel> models = new ArrayList<>();
		String[] banners = null;
		if(banner !=null)
			banners = banner.getBanners();
		if (banners != null && banners.length <= 4) {

			for (int i = 0; i < banners.length; i++) {
				BannerModel model = new BannerModel();
				model.billboardResPath = banners[i];
				int index = i+1;
				
				// 设置展示图id 
				if(index == 1)
					model.billboardId = model.banner1;
				if(index == 2)
					model.billboardId = model.banner2;
				if(index == 3)
					model.billboardId = model.banner3;
				if(index == 4)
					model.billboardId = model.banner4;
				
				// 设置展示图排序
				model.billboardOrder = index;
				
				models.add(model);
			}
		}
		if(banner !=null && ArrayUtils.contains(banner.getSrvs(), svrId) )
			return addActBanners(models, svrId, activitieList);
		else
			return addActBanners(new ArrayList<>(), svrId, activitieList);
	}
	
	private static List<BannerModel> addActBanners(List<BannerModel> models, int svrId, List<PresetModel> activitieList){
		if(activitieList == null || activitieList.size()==0)
			return models;
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		ServerModel server = serverComponent.getDAO().get(svrId);
		IClient client = server.createClient();
		if(client == null)
			return models;
		final GetActivityListWCS.Builder b = GetActivityListWCS.newBuilder();
		// 发送并等待回馈
		GetActivityListWSC retMsg = null;
		retMsg = client.call(Head.H19002, b.build(), GetActivityListWSC.class);
		if(retMsg == null)
			return models;
		List<ActivityItem> items = retMsg.getActivitysList();
		
		
		BannerModel model;
		PresetActivity activity = null;
		for(PresetModel presetModel : activitieList){
			boolean isOpen = false;
			try {
				activity = presetModel.getValue(PresetActivity.class);
//				if(activity.getOpen_state() == 1){
//					ServerModel sm = serverComponent.find(svrId);
//					String openTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), activity.getOpenDate()>0?activity.getOpenDate()-1:0);
//					String closeTimeStr = DateUtils.getAddDayStr(sm.getOpen_time(), activity.getCloseDate()>0?activity.getCloseDate():0);
//					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//					Date openDate = format.parse(openTimeStr);
//					Date closeDate = format.parse(closeTimeStr);
//					Date nowDate = new Date();
//					if(nowDate.after(openDate) && nowDate.before(closeDate))
//						isOpen = true;
//				}
				for(ActivityItem item : items){
					if(item.getId() == activity.getId()){
						int startDayTime = item.getStartDayTime();
						int endDayTime = item.getEndDayTime();
						Date nowDate = new Date();
						Date openDate = new Date(1000l*startDayTime);
						Date closeDate = new Date(endDayTime<=0?System.currentTimeMillis()+86400000:1000l*endDayTime+86400000);
//						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(nowDate.after(openDate) && nowDate.before(closeDate))
							isOpen = true;
						break;
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
//			System.out.println("actId->"+activity.getId()+",isOpen->"+isOpen);
//			if(activity == null)
//				continue;
			if(!isOpen)
				continue;
			if(StringUtils.isEmpty(activity.getWinPhoto()))
				continue;
//			if(!ArrayUtils.contains(activity.getSrvIds(), svrId))
//				continue;
			int size = models.size();
			model = new BannerModel();
			model.billboardId = activity.getId();
			model.billboardOrder = size+1;
			model.billboardResPath = activity.getWinPhoto();
			
			if(models.contains(model))
				continue;
			models.add(model);
		}
		model = new BannerModel();
		model.billboardId=180200006;
		model.billboardType=3;
		model.billboardOrder=0;
		model.billboardResPath = "null";
		models.add(model);
		//System.out.println("svrId->"+svrId+",models->"+models);
		return models;
	}
	

	@Override
	public boolean equals(Object obj) {
		BannerModel model = (BannerModel) obj;
		return StringUtils.equals(this.billboardResPath, model.billboardResPath);
	}

	@Override
	public String toString() {
		return "BannerModel [billboardId=" + billboardId + ", billboardType=" + billboardType + ", billboardOrder="
				+ billboardOrder + ", billboardResPath=" + billboardResPath + "]";
	}

}
