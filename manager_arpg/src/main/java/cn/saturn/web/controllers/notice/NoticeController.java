package cn.saturn.web.controllers.notice;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.notice.dao.NoticeDAO;
import cn.saturn.web.controllers.notice.dao.NoticeManager;
import cn.saturn.web.controllers.notice.dao.NoticeModel;
import cn.saturn.web.controllers.server.dao.ServerMergeDAO;
import cn.saturn.web.controllers.server.dao.ServerMergeModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.StringExtUtil;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import zyt.spring.component.ComponentManager;

@Path("notice")
public class NoticeController {

	@Resource
	NoticeManager noticeMgr;
	
	@Resource
	ServerMergeDAO serverMergeDAO;
	
	@Resource
	NoticeDAO noticeDAO;
	
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.logic_notice_page)
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/notice/notice/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/notice/notice/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/notice/notice/edit"));
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/notice/notice/toEdite"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/notice/notice/toDelete"));

		return "notice_list";
	}

	@MainView
	@LoginCheck
	@UserAuthority(PageModel.logic_notice_page)
	@Get("edit")
	public String edit(Invocation inv) throws Throwable {
		int id = Utils.getIntParameter(inv, "id");
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/notice/notice/toEdite"));
		Utils.setAttributeValue(inv, "id", id);
		if (id > 0) {
			NoticeModel model = noticeMgr.getDAO().get(id);
			if (model != null) {
				String enableStr = model.isEnable() ? "checked=\"checked\"" : "";
				Utils.setAttributeValue(inv, "enableStr", enableStr);
				Utils.setAttributeValue(inv, "notice", model.getNotice());
				// 服务器ID
				int[] srvIds0 = ServerUtils.getSrvIds(model.getS_id());
				final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
				Utils.setAttributeValue(inv, "selectSrvIds", srvIds);
				Utils.setAttributeValue(inv, "imgs", model.getImgs());
			}
		}
		return "notice_edit";
	}

	@LoginCheck
	@UserAuthority(PageModel.logic_notice_page)
	@Post("toEdite")
	public String toNotice(Invocation inv, @NotBlank @Param("id") int id, @Param("notice") String notice,
			@Param("enable") boolean enable, @Param("srvIds") String srvIdStrs, @Param("imgs") String imgs) {
		NoticeModel noticeModel = null;
		if (id <= 0) {
			noticeModel = new NoticeModel();
			noticeModel.setId(0);
		} else {
			noticeModel = new NoticeModel();
			noticeModel.setId(id);
		}

		noticeModel = noticeMgr.makNoticeModel(noticeModel);

		// 修改公告
		noticeModel.setNotice(notice);
		noticeModel.setImgs(imgs.trim());
		// 如果要开启就检测,已开启的服务器中是否有重复的
		if (enable) {
			List<NoticeModel> models = noticeMgr.getDAO().getEnable();
			if (models != null && !models.isEmpty()) {
				int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
				final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
				for (NoticeModel m : models) {
					for (int srv : srvIds) {
						List<ServerMergeModel> ServerMergeModelList =serverMergeDAO.getServerMergeModelPid(srv);
						
						for(ServerMergeModel ServerMergeModel:ServerMergeModelList){
						// 是否有匹配的服务器id
						boolean isMatch = m.contantSid(ServerMergeModel.getId());
						if (id != m.getId() && isMatch) {
							// 匹配成功返回错误
							return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR,
									"修改失败,服务器 " + srv + " 已经设置了开服公告," + m.getId());
						}
						}
					}
				}
			}
		}
		noticeModel.setEnable(enable);
		StringBuffer SrvIdS=new StringBuffer();
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
			boolean first=true;	
			for (int srv : srvIds) {
				List<ServerMergeModel> ServerMergeModelList =serverMergeDAO.getServerMergeModelPid(srv);
				
				for(ServerMergeModel ServerMergeModel:ServerMergeModelList){
				if(first){
				SrvIdS.append(String.valueOf(ServerMergeModel.getId()));
				first=false;
				}
				else{
				SrvIdS.append(",").append(String.valueOf(ServerMergeModel.getId()));
				}
				}
			}
		String srvStrs=(srvIds0 != null)?SrvIdS.toString():srvIdStrs;	
		noticeModel.setS_id(srvStrs);
		//noticeModel.setS_id(srvIdStrs);
		// 更新信息
		NoticeManager noticeManager = ComponentManager.getComponent(NoticeManager.class);
		noticeManager.getDAO().insertOrUpdate(noticeModel);

		RedisUtils.del(RedisKeys.K_NOTICE);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		return noticeMgr.listJson(inv);
	}
	
	@Get("toDelete")
	public String Delete(@Param("id") int id){
		
		noticeDAO.delete(id);
		RedisUtils.del(RedisKeys.K_NOTICE);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功");
		
	}
}
