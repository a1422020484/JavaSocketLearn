package cn.saturn.web.controllers.poke;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.poke.dao.NoticeWp;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb;
import proto.ProtocolWeb.CGChatNoticeWCS;
import zyt.utils.ArrayUtils;
import zyt.utils.ListUtils;

/**
 * 口袋王牌聊天公告
 * @author Administrator
 *
 */

@Path("noticewp")
public class NoticeKdwpController {
	
	
	@Resource
	PresetDAO presetDAO;
	
	public static final String defualUrl = "/poke/noticewp/show";
	
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, defualUrl);
	}

	@MainView
	@LoginCheck
	@Get("show")
	public String show(Invocation inv) throws Throwable {
		
		HttpServletRequest request = inv.getRequest();
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/poke/noticewp/toEdit"));
		request.setAttribute("btnStr", "修改");
		List<PresetModel>  presetModelList=PresetManager.getInstance().getByType(PresetManager.presetType_noticeWp);
		//聊天公告只存在一条,处理异步产生多条数据时，显示最后一条
		if(presetModelList.size()>=1){
			for(PresetModel  presetModel:presetModelList){
				NoticeWp presetData = presetModel.getValue(NoticeWp.class);
				request.setAttribute("id", presetModel.getId());
				
				request.setAttribute("notice", presetData.getNotice());
				Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
			}
			
		}else{
			PresetModel model = new PresetModel();
			// model.resetId();

			model.setType(PresetManager.presetType_noticeWp);
			NoticeWp presetData = new NoticeWp();
			presetData.setNotice("");
			presetData.setSrvs(new int[0]);
			model.setValue(presetData);
			
			int presetId =presetDAO.insertNew(model);
			model.setId(presetId);
			request.setAttribute("id", presetId);
			request.setAttribute("notice", presetData.getNotice());
			Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
			
		}

		return "noticewpinfo";
	}
	
	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id,
			@Param("notice") String notice,  @Param("srvIds") String srvIdStrs) throws Throwable {

		// 编辑
		PresetModel model = null;
		NoticeWp presetData = null;
		
		model = PresetManager.getInstance().get(id);
		if (model == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			
		}
		presetData = model.getValue(NoticeWp.class);
		presetData.setNotice(notice);
		// 保存服务器id
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];
		presetData.setSrvs(srvIds);

		// 设置数据
		if (!model.setValue(presetData))

		{
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "数据保存失败.");
		}
		// 提交
		if (id <= 0) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "数据错误");
		}
		//数据保存
		presetDAO.insertOrUpdateNew(model);
		
		List<ResultMsg> resultMsg = new ArrayList<>();

		final List<Integer> succeedList = new ArrayList<Integer>();
		final NoticeWp noticeData = model.getValue(NoticeWp.class);
		
		// 遍历执行
		boolean result = ServerUtils.operateServerAction(new ListUtils.IAction<ServerModel>() {
			@Override
			public boolean action(ServerModel serverModel, Iterator<?> iter) {
				long srvId = serverModel.getId();

				boolean enable = Utils.ArrayUtils.find(srvIds, (int) srvId);
				if (!enable) {
					return true; // 没有选上, 现在也没打算开启, 跳过.
				}

				// 连接服务器
				IClient client = serverModel.createClient();
				if (client == null) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, "服务器(" + srvId + ")没有配置url"));
					return true;
				}

				// 发送并等待回馈
				//ProtocolWeb.LimitShopWSC sendMsg = null;
				try {
					CGChatNoticeWCS.Builder b = CGChatNoticeWCS.newBuilder();
						b.setMsg(noticeData.getNotice());
					ProtocolWeb.CGChatNoticeWSC retMsg = client.call(Head.H450002, b.build(),
							ProtocolWeb.CGChatNoticeWSC.class);
					
					int state = retMsg.getStatus();
					if (state == 0) {
						succeedList.add((int) srvId);
					}

					if (state >= 1) {
						resultMsg.add(new ResultMsg(ResultMsg.fail,
								"消息发送服务器：(" + serverModel.getSrvStr0() + ") 失败   :" + retMsg.getMsg()+":"+String.valueOf(state)));
						
					}

				} catch (Exception e) {
					resultMsg.add(new ResultMsg(ResultMsg.fail,  "服务器ID:" + srvId + "发送失败;"));
					return false; // 失败不管
				}

				return true;
			}
		});

		int[] selectSrvIds0 = ArrayUtils.toIntArray(succeedList);
		presetData.setSrvs(selectSrvIds0);
		model.setValue(presetData);
		PresetManager.getInstance().updateByDAO(model);

		if (!result) {
			resultMsg.add(new ResultMsg(ResultMsg.fail, "消息("  + ")同步服务器失败;"));
		}

		StringBuilder sb = new StringBuilder();
		resultMsg.add(new ResultMsg(ResultMsg.succ, "发送完毕!"));
		// 发送的消息
		for (ResultMsg msg : resultMsg) {
			sb.append(msg.toHtmlString());
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());
	}

}
