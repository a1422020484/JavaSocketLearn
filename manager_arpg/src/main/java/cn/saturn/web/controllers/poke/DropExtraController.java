package cn.saturn.web.controllers.poke;

import net.paoding.rose.web.annotation.Path;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.DropExtraModel;
import cn.saturn.web.controllers.item.dao.DropExtraManager;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.controllers.poke.dao.DropExtra;
import cn.saturn.web.controllers.poke.dao.PresetPokeShop;
import cn.saturn.web.controllers.server.dao.IClient;
import cn.saturn.web.controllers.server.dao.ServerDAO;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import proto.ProtocolWeb;
import proto.ProtocolWeb.DropExtraItem;
import proto.ProtocolWeb.DropExtraWCS;
import proto.ProtocolWeb.LimitShopItem;
import proto.ProtocolWeb.LimitShopWCS;
import zyt.utils.ArrayUtils;
import zyt.utils.ListUtils;

/**
 * 额外掉落活动
 * 
 * @author zh
 *
 */

@Path("dropExtra")
public class DropExtraController {

	@Resource
	ServerDAO serverDAO;

	@Resource
	PresetDAO presetDAO;

	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/poke/dropExtra/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/poke/dropExtra/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/poke/dropExtra/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/poke/dropExtra/toDelete"));
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/poke/dropExtra/send"));
		Utils.setAttributeValue(inv, "checkUrl", Utils.url(inv, "/poke/dropExtra/srvList"));

		return "dropExtra_list";
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
				DropExtra presetDrop = model.getValue(DropExtra.class);
				if (presetDrop != null) {
					map.put("pid", presetDrop.getId());
					map.put("openTime",
							((presetDrop.getOpen_state() == 0)
									? presetDrop.getOpenTime() + "至" + presetDrop.getEndTime()
									: "开服第" + presetDrop.getOpenDate() + "至" + presetDrop.getEndDate() + "天"));
					map.put("selectSrvIds", presetDrop.getSrvs());
				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_dropExtra, action);
	}

	@MainView
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("openTime", DateUtils.getNowDay());

		if (id <= 0) {
			request.setAttribute("isNew", "true");
			request.setAttribute("cidStr", "");
			request.setAttribute("btnStr", "新增");
		} else {
			PresetModel model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据:" + id);
			}

			request.setAttribute("isNew", "false");
			String remark = model.getRemark();
			request.setAttribute("remark", remark);

			// 解析数据
			DropExtra presetData = model.getValue(DropExtra.class);
			if (presetData != null) {
				request.setAttribute("cid", presetData.getId());
				request.setAttribute("cidStr", "disabled=\"disabled\"  ");

				request.setAttribute("dropname", presetData.getDropname());
				request.setAttribute("open_state", presetData.getOpen_state());
				request.setAttribute("openDate", presetData.getOpenDate());
				request.setAttribute("endDate", presetData.getEndDate());

				request.setAttribute("openTime", presetData.getOpenTime());
				request.setAttribute("endTime", presetData.getEndTime());

				Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
			}

			request.setAttribute("btnStr", "修改");
		}

		request.setAttribute("editUrl", Utils.url(inv, "/poke/dropExtra/toEdit"));

		return "dropExtra_edit";
	}

	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id, @Param("cid") int cid,
			@Param("dropname") String dropname, @Param("open_state") int open_state, @Param("openDate") int openDate,
			@Param("endDate") int endDate, @Param("openTime") String openTime, @Param("endTime") String endTime,
			@Param("remark") String remark, @Param("srvIds") String srvIdStrs) throws Throwable {

		// 编辑
		PresetModel model = null;
		DropExtra presetData = null;
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			// model.resetId();

			model.setType(PresetManager.presetType_dropExtra);
			presetData = new DropExtra();

			// 检测pid是否被使用了
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_dropExtra);
			int count = (all != null) ? all.size() : 0;
			for (int i = 0; i < count; i++) {
				PresetModel model0 = all.get(i);
				DropExtra presetData0 = model0.getValue(DropExtra.class);
				if (presetData0 == null) {
					continue;
				}
				// 检测ID
				if (presetData0.getId() == cid) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "ID已存在相同.");
				}

				// 检测日期
				if (open_state == 0 && presetData0.getOpenTime().equals(openTime)) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同的日期.");
				}
				// 检测开服日期
				/*
				 * if ((open_state == 1 && presetData0.getOpenDate()
				 * <=(openDate) ||presetData0.getEndDate() <=(openDate)) &&
				 * presetData0.getEndDate() >=(openDate) ) { return "@" +
				 * PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同的开服日期."); }
				 */
			}

			presetData.setId(cid);
		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			}
			presetData = model.getValue(DropExtra.class);
			if (presetData == null) {
				presetData = new DropExtra();
			}
		}

		model.setRemark(remark);
		presetData.setOpenTime(openTime);
		presetData.setEndTime(endTime);
		presetData.setOpenDate(openDate);
		presetData.setEndDate(endDate);
		presetData.setOpen_state(open_state);

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

			int presetId =presetDAO.insertNew(model);
			model.setId(presetId);
		} else {
			presetDAO.insertOrUpdateNew(model);
		}

		List<ResultMsg> resultMsg = new ArrayList<>();

		final List<Integer> succeedList = new ArrayList<Integer>();
		final DropExtra dropExtraData = model.getValue(DropExtra.class);
		final DropExtraWCS.Builder b = DropExtraWCS.newBuilder();

		// 遍历执行
		boolean result = ServerUtils.operateServerAction(new ListUtils.IAction<ServerModel>() {
			@Override
			public boolean action(ServerModel serverModel, Iterator<?> iter) {
				long srvId = serverModel.getId();

				boolean enable = Utils.ArrayUtils.find(srvIds, (int) srvId);
				if (!enable) {
					return true; // 没有选上, 现在也没打算开启, 跳过.
				}
				// 存在列表中的, 和勾选上的都统一更新一遍.

				// 连接服务器
				IClient client = serverModel.createClient();
				if (client == null) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, "服务器(" + srvId + ")没有配置url"));
					return true;
				}

				// 发送并等待回馈
				ProtocolWeb.LimitShopWSC sendMsg = null;
				try {
					DropExtraWCS.Builder b = DropExtraWCS.newBuilder();
					b.setId(dropExtraData.getId());

					if (dropExtraData.getOpen_state() == 0) {

						b.setBeginTime(WebUtils.Html.getDate0(dropExtraData.getOpenTime()));
						b.setEndTime(WebUtils.Html.getDate0(dropExtraData.getEndTime()));
					}
					if (dropExtraData.getOpen_state() == 1) {
						int openDate = dropExtraData.getOpenDate();
						int endDate = dropExtraData.getEndDate();
						String srvOpenTime = serverModel.getOpen_time();

						String pokeOpenTime = TimeUtils.getParam(srvOpenTime, openDate);
						String pokeEndTime = TimeUtils.getParam(srvOpenTime, endDate);

						b.setBeginTime(WebUtils.Html.getDate0(pokeOpenTime));
						b.setEndTime(WebUtils.Html.getDate0(pokeEndTime));
					}
					List<DropExtraModel> DropExtraModelList = DropExtraManager.getModels();

					List<DropExtraItem> items = new ArrayList<>();
					for (DropExtraModel dropExtraModel : DropExtraModelList) {
						DropExtraItem.Builder bfirst = DropExtraItem.newBuilder();
						bfirst.setDropId(dropExtraModel.getDropId());
						bfirst.setDropDescription(dropExtraModel.getDropDescription());
						bfirst.setItem1(dropExtraModel.getItem_1());
						bfirst.setNum1(dropExtraModel.getNum_1());
						bfirst.setProbability1(dropExtraModel.getProbability_1());
						bfirst.setItem2(dropExtraModel.getItem_2());
						bfirst.setNum2(dropExtraModel.getNum_2());
						bfirst.setProbability2(dropExtraModel.getProbability_2());
						bfirst.setItem3(dropExtraModel.getItem_3());
						bfirst.setNum3(dropExtraModel.getNum_3());
						bfirst.setProbability3(dropExtraModel.getProbability_3());
						bfirst.setItem4(dropExtraModel.getItem_4());
						bfirst.setNum4(dropExtraModel.getNum_4());
						bfirst.setProbability4(dropExtraModel.getProbability_4());
						bfirst.setItem5(dropExtraModel.getItem_5());
						bfirst.setNum5(dropExtraModel.getNum_5());
						bfirst.setProbability5(dropExtraModel.getProbability_5());
						items.add(bfirst.build());
					}

					b.addAllItems(items);

					ProtocolWeb.DropExtraWSC retMsg = client.call(Head.H2520001, b.build(),
							ProtocolWeb.DropExtraWSC.class);

					int state = retMsg.getStatus();
					if (state == 0) {
						succeedList.add((int) srvId);
					}

					if (state >= 1) {
						resultMsg.add(new ResultMsg(ResultMsg.fail,
								"消息发送服务器：(" + serverModel.getSrvStr0() + ") 失败   :" + retMsg.getMsg()+":"+String.valueOf(state)));
						
					}

				} catch (Exception e) {
					resultMsg.add(new ResultMsg(ResultMsg.fail, "消息ID:" + cid + "服务器ID:" + srvId + "发送失败;"));
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
			resultMsg.add(new ResultMsg(ResultMsg.fail, "消息(" + cid + ")同步服务器失败;"));
		}

		StringBuilder sb = new StringBuilder();
		resultMsg.add(new ResultMsg(ResultMsg.succ, "发送完毕!"));
		// 发送的消息
		for (ResultMsg msg : resultMsg) {
			sb.append(msg.toHtmlString());
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());

	}

	// 删除
	@LoginCheck
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {

		PresetManager.toDelete(inv, id, "defualUrl");
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "poke/dropExtra/list?mainView=true");
	}
	
	@Post("getSelect2EasyUi")
	public String getSelect2EasyUi(Invocation inv, @Param("type") int type) {

		List<DropExtraModel> list1 = DropExtraManager.getModels();
		
		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", "0");
		map.put("name", "--请选择--");
		objList.add(map);

		for (int i = 0; i < list1.size(); i++) {
			Map<Object, Object> dropExtraMap = new HashMap<Object, Object>();
			DropExtraModel drop = list1.get(i);
			dropExtraMap.put("id", drop.getDropId());
			dropExtraMap.put("name", drop.getDropId() + "  " + drop.getDropDescription()+ " 掉落1： " + drop.getItem_1()+ "_" + drop.getNum_1()+ "_" + drop.getProbability_1() +" 掉落2： " + drop.getItem_2()+ "_" + drop.getNum_2()+ "_" + drop.getProbability_2()+" 掉落3： " + drop.getItem_3()+ "_" + drop.getNum_3()+ "_" + drop.getProbability_3()+" 掉落4： " + drop.getItem_4()+ "_" + drop.getNum_4()+ "_" + drop.getProbability_4()+" 掉落5： " + drop.getItem_5()+ "_" + drop.getNum_5()+ "_" + drop.getProbability_5());
			objList.add(dropExtraMap);
		}

		return "@" + JSONObject.toJSONString(objList);
	}

}
