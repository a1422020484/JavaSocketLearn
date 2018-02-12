package cn.saturn.web.controllers.poke;

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
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
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
import proto.ProtocolWeb.LimitShopItem;
import proto.ProtocolWeb.LimitShopWCS;
import zyt.utils.ArrayUtils;
import zyt.utils.ListUtils;

@Path("pokeshop")
public class PokeShopController {

	@Resource
	ServerDAO serverDAO;
	
	@Resource
	PresetDAO presetDAO;
	
	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/poke/pokeshop/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/poke/pokeshop/edit"));
		Utils.setAttributeValue(inv, "editUrl", Utils.url(inv, "/poke/pokeshop/edit"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/poke/pokeshop/toDelete"));
		Utils.setAttributeValue(inv, "sendUrl", Utils.url(inv, "/poke/pokeshop/send"));
		Utils.setAttributeValue(inv, "checkUrl", Utils.url(inv, "/poke/pokeshop/srvList"));

		return "pokeshop_list";
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
				PresetPokeShop presetDrop = model.getValue(PresetPokeShop.class);
				if (presetDrop != null) {
					map.put("pid", presetDrop.getId());
					map.put("openTime",((presetDrop.getOpen_state()==0)?presetDrop.getOpenTime()+"至"+presetDrop.getEndTime():"开服第"+presetDrop.getOpenDate()+"至"+presetDrop.getEndDate()+"天"));
					map.put("pokeId",
							presetDrop.getFirstId() + "," + presetDrop.getSecondId() + "," + presetDrop.getThirdId());
					map.put("selectSrvIds", presetDrop.getSrvs());
				}
				return true;
			}

		};
		return PresetManager.listJson(inv, PresetManager.presetType_shop, action);
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
			PresetPokeShop presetData = model.getValue(PresetPokeShop.class);
			if (presetData != null) {
				request.setAttribute("cid", presetData.getId());
				request.setAttribute("cidStr", "disabled=\"disabled\"  ");

				request.setAttribute("open_state", presetData.getOpen_state());
				request.setAttribute("openDate", presetData.getOpenDate());
				request.setAttribute("endDate", presetData.getEndDate());

				request.setAttribute("openTime", presetData.getOpenTime());
				request.setAttribute("endTime", presetData.getEndTime());

				request.setAttribute("first", presetData.getFirstId());
				request.setAttribute("first_count", presetData.getFirst_count());
				request.setAttribute("first_limit", presetData.getFirst_limit());
				request.setAttribute("first_price", presetData.getFirst_price());

				request.setAttribute("second", presetData.getSecondId());
				request.setAttribute("second_count", presetData.getSecond_count());
				request.setAttribute("second_limit", presetData.getSecond_limit());
				request.setAttribute("second_price", presetData.getSecond_price());

				request.setAttribute("third", presetData.getThirdId());
				request.setAttribute("third_count", presetData.getThird_count());
				request.setAttribute("third_limit", presetData.getThird_limit());
				request.setAttribute("third_price", presetData.getThird_price());

				Utils.setAttributeValue(inv, "selectSrvIds", presetData.getSrvs());
			}

			request.setAttribute("btnStr", "修改");
		}

		request.setAttribute("editUrl", Utils.url(inv, "/poke/pokeshop/toEdit"));

		return "pokeshop_edit";
	}

	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id, @Param("cid") int cid,
			@Param("open_state") int open_state, @Param("openDate") int openDate, @Param("endDate") int endDate,
			@Param("openTime") String openTime, @Param("endTime") String endTime, @Param("first") int first,
			@Param("first_count") int first_count, @Param("first_limit") int first_limit,
			@Param("first_price") int first_price, @Param("second") int second, @Param("second_count") int second_count,
			@Param("second_limit") int second_limit, @Param("second_price") int second_price, @Param("third") int third,
			@Param("third_count") int third_count, @Param("third_limit") int third_limit,
			@Param("third_price") int third_price, @Param("remark") String remark, @Param("srvIds") String srvIdStrs)
			throws Throwable {

		if (first == 0 || second == 0 || third == 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR,
					(first == 0 ? "热卖物品1" : second == 0 ? "热卖物品2" : "热卖物品3") + "未选择");

		if (first_price <= 0 || (first_count <= 0 || first_limit <= 0))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "热卖物品1数量必须大于 0");

		if (third_price <= 0 || (second_count <= 0 || second_limit <= 0))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "热卖物品2数量必须大于 0");

		if (third_price <= 0 || (third_count <= 0 || third_limit <= 0))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "热卖物品3数量必须大于 0");
		
		
		/*if (open_state == 0 || (third_count <= 0 || third_limit <= 0))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "开始时间必须小于结束时间");
		
		if (open_state == 1 || openDate >endDate )
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "开始时间必须小于结束时间");*/
		// 编辑
		PresetModel model = null;
		PresetPokeShop presetData = null;
		if (id <= 0) {
			// 新增
			model = new PresetModel();
			//model.resetId();
			
			model.setType(PresetManager.presetType_shop);
			presetData = new PresetPokeShop();

			// 检测pid是否被使用了
			List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_shop);
			int count = (all != null) ? all.size() : 0;
			for (int i = 0; i < count; i++) {
				PresetModel model0 = all.get(i);
				PresetPokeShop presetData0 = model0.getValue(PresetPokeShop.class);
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
				/*if ((open_state == 1 && presetData0.getOpenDate() <=(openDate) ||presetData0.getEndDate() <=(openDate)) && presetData0.getEndDate() >=(openDate) ) {
					return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同的开服日期.");
				}*/
			}

			presetData.setId(cid);
		} else {
			// 修改
			model = PresetManager.getInstance().get(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没有找到数据.");
			}
			presetData = model.getValue(PresetPokeShop.class);
			if (presetData == null) {
				presetData = new PresetPokeShop();
			}
		}

		model.setRemark(remark);
		presetData.setOpenTime(openTime);
		presetData.setEndTime(endTime);
		presetData.setOpenDate(openDate);
		presetData.setEndDate(endDate);
		presetData.setOpen_state(open_state);

		presetData.setFirstId(first);
		presetData.setFirst_count(first_count);
		presetData.setFirst_limit(first_limit);
		presetData.setFirst_price(first_price);

		presetData.setSecondId(second);
		presetData.setSecond_count(second_count);
		presetData.setSecond_limit(second_limit);
		presetData.setSecond_price(second_price);

		presetData.setThirdId(third);
		presetData.setThird_count(third_count);
		presetData.setThird_limit(third_limit);
		presetData.setThird_price(third_price);

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
			//PresetManager.getInstance().insertByDAO(model);
			presetDAO.insertNew(model);
		} else {
			//PresetManager.getInstance().updateByDAO(model);
			presetDAO.insertOrUpdateNew(model);
		}

		// 发送检查

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "处理成功.<br>");
	}

	// 删除
	@LoginCheck
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {

		PresetManager.toDelete(inv, id, "defualUrl");
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "poke/pokeshop/list?mainView=true");
	}

	@MainView
	@LoginCheck
	@Get("send")
	public String send(Invocation inv) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		List<PresetModel> models = PresetManager.getPresetList(PresetManager.presetType_shop);
		if (null != models) {
			for (PresetModel model : models) {
				PresetPokeShop presetData = model.getValue(PresetPokeShop.class);
				if (null != presetData) {
					String presetStr = presetData.getFirstId() + ";" + presetData.getSecondId() + ";"
							+ presetData.getThirdId();

					request.setAttribute("id", presetData.getId());
					request.setAttribute("presetDes", presetStr);

				}
			}
		}

		return "pokeshop_send";
	}

	@LoginCheck
	@Post("toSend")
	public String toSend(Invocation inv, @Param("srvIds") String srvIdStrs, @Param("pokeId") int pokeId,
			@Param("ids") String ids) throws Throwable {

		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];

		// ArrayList<Integer> srvList= (ArrayList) Arrays.asList(srvIds0);

		// 拿出选择的
		// List<PresetModel> pModels =
		// PresetManager.getPresetList(PresetManager.presetType_shop);

		if (pokeId <= 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "id 不能为空.");

		if (StringUtils.isEmpty(srvIdStrs))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "srvIdStrs 不能为空.");

		/* String[] idStrs = ids.split(","); */

		List<ResultMsg> resultMsg = new ArrayList<>();

	/*	//时间戳
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = sdf.parse( "2017-04-15 21:00:00" );
		Date date2 = sdf.parse( "2017-04-14 21:20:00" );
		
		Date date3 = sdf.parse( "2017-04-15 21:20:00" );
		Date date4 = sdf.parse( "2017-04-14 21:30:00" );
		
		int t1=(int)(date1.getTime()/1000);
		int t2=(int)(date2.getTime()/1000);
		int t3=(int)(date3.getTime()/1000);
		int t4=(int)(date4.getTime()/1000);*/
		// for (String idStr : idStrs) {
		// int id = Integer.parseInt(idStr);
		int id = pokeId;
		PresetModel model = PresetManager.getInstance().get(id);
		if (model != null) {
			final List<Integer> succeedList = new ArrayList<Integer>();
			final PresetPokeShop presetData = model.getValue(PresetPokeShop.class);

			
			final LimitShopWCS.Builder b = LimitShopWCS.newBuilder();

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
						LimitShopWCS.Builder b = LimitShopWCS.newBuilder();
						b.setId(presetData.getId());

						if (presetData.getOpen_state() == 0) {
							
							/*if(presetData.getId()==15003){
								b.setBeginTime(t1);
								b.setEndTime(t2);
							}
							else if(presetData.getId()==15004){
								b.setBeginTime(t3);
								b.setEndTime(t4);
							}else{
								b.setBeginTime(WebUtils.Html.getDate0(presetData.getOpenTime()));
								b.setEndTime(WebUtils.Html.getDate0(presetData.getEndTime()));
							}*/
							b.setBeginTime(WebUtils.Html.getDate0(presetData.getOpenTime()));
							b.setEndTime(WebUtils.Html.getDate0(presetData.getEndTime()));
						}
						if (presetData.getOpen_state() == 1) {
							int openDate = presetData.getOpenDate();
							int endDate = presetData.getEndDate();
							String srvOpenTime = serverModel.getOpen_time();

							String pokeOpenTime = TimeUtils.getParam(srvOpenTime, openDate);
							String pokeEndTime = TimeUtils.getParam(srvOpenTime, endDate);
							
							b.setBeginTime(WebUtils.Html.getDate0(pokeOpenTime));
							b.setEndTime(WebUtils.Html.getDate0(pokeEndTime));
						}

						List<LimitShopItem> items = new ArrayList<>();
						{
							LimitShopItem.Builder bfirst = LimitShopItem.newBuilder();
							bfirst.setId(presetData.getFirstId());
							bfirst.setNum(presetData.getFirst_count());
							bfirst.setLimit(presetData.getFirst_limit());
							bfirst.setPrice(presetData.getFirst_price());
							items.add(bfirst.build());

							LimitShopItem.Builder bsecond = LimitShopItem.newBuilder();
							bsecond.setId(presetData.getSecondId());
							bsecond.setNum(presetData.getSecond_count());
							bsecond.setLimit(presetData.getSecond_limit());
							bsecond.setPrice(presetData.getSecond_price());
							items.add(bsecond.build());

							LimitShopItem.Builder bthird = LimitShopItem.newBuilder();
							bthird.setId(presetData.getThirdId());
							bthird.setNum(presetData.getThird_count());
							bthird.setLimit(presetData.getThird_limit());
							bthird.setPrice(presetData.getThird_price());
							items.add(bthird.build());

						}
						b.addAllItems(items);
												
						ProtocolWeb.LimitShopWSC retMsg = client.call(Head.H251011, b.build(),
								ProtocolWeb.LimitShopWSC.class);

						int state = retMsg.getStatus();
						if(state == 0){
							succeedList.add((int) srvId);
						}
						
						if (state >= 1) {
							resultMsg.add(new ResultMsg(ResultMsg.fail,
									"消息发送服务器：(" + serverModel.getSrvStr0() + ") 失败   :" + retMsg.getMsg()));
							 Date currentTime = new Date();
							 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							 String dateString = formatter.format(currentTime);
							System.out.println(dateString+":游戏服务器返回retMsg.Status:"+state);
						}

					 } catch (Exception e) {
						resultMsg.add(new ResultMsg(ResultMsg.fail, "消息ID:" + pokeId + "服务器ID:" + srvId + "发送失败;"));
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
				resultMsg.add(new ResultMsg(ResultMsg.fail, "消息(" + pokeId + ")同步服务器失败;"));
			}
		}
		// }

		StringBuilder sb = new StringBuilder();
		resultMsg.add(new ResultMsg(ResultMsg.succ, "发送完毕!"));
		// 发送的消息
		for (ResultMsg msg : resultMsg) {
			sb.append(msg.toHtmlString());
		}

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());

	}

	@Post("getGrid2EasyUi")
	public String getGrid2EasyUi(Invocation inv) throws Throwable {
		// StringBuilder sb = new StringBuilder();
		List<PresetModel> pMgrs = PresetManager.getPresetList(PresetManager.presetType_shop);
		List<PresetPokeShop> list = new ArrayList<PresetPokeShop>();

		/*
		 * for (PresetModel model : pMgrs) { PresetPokeShop presetDrop =
		 * model.getValue(PresetPokeShop.class); if (presetDrop != null)
		 * list.add(presetDrop); }
		 */

		Map<Object, Object> dataObj = new HashMap<>();
		dataObj.put("total", list.size());
		List<Object> rowsList = new ArrayList<>();

		for (int i = 0; i < pMgrs.size(); i++) {
			Map<Object, Object> rowItem = new HashMap<>();
			rowItem.put("id", i);
			if (i < pMgrs.size()) {
				PresetPokeShop model = pMgrs.get(i).getValue(PresetPokeShop.class);
				PresetModel presetModel = pMgrs.get(i);
				int pokeId = (int) presetModel.getId();
				String pokeDescription = model.getFirstId() + ";" + model.getSecondId() + ";" + model.getThirdId();
				rowItem.put("pokeId", pokeId);
				rowItem.put("pokeDescription", pokeDescription);
			}

			rowsList.add(rowItem);
		}

		dataObj.put("rows", rowsList);

		return "@" + JSONObject.toJSONString(dataObj);
	}

	/**
	 * html 中 easyui 框架中的<br/>
	 * select 控件数据<br />
	 * 
	 * @param inv
	 * @return
	 */
	@Post("getSelect2EasyUi")
	public String getSelect2EasyUi(Invocation inv, @Param("type") int type) {

		/*
		 * List<PokeHunterModel> list =
		 * PokeHunterModelManager.getListByType(type); int size = list.size();
		 * List<Object> lists = new ArrayList<>(); Map<Object, Object> defMap =
		 * new HashMap<>(); defMap.put("id", 0); defMap.put("name", "--请选择--");
		 * lists.add(defMap); for (int i = 0; i < size; i++) { Map<Object,
		 * Object> rowItem = new HashMap<>(); PokeHunterModel model =
		 * list.get(i);
		 * 
		 * rowItem.put("id", model.getPokeId()); rowItem.put("name",
		 * model.getPokeId() + "  " + model.getPokeName());
		 * 
		 * lists.add(rowItem); }
		 */

		List<ItemModel> list1 = ItemModelManager.getModels();
		List<ChestModel> list2 = ChestModelManager.getModels();
		List<Object> objList = new ArrayList<Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", "0");
		map.put("name", "--请选择--");
		objList.add(map);

		for (int i = 0; i < list1.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			ItemModel item = list1.get(i);
			itemMap.put("id", item.getItemId());
			itemMap.put("name", item.getItemId() + "  " + item.getItemName());
			objList.add(itemMap);
		}

		for (int i = 0; i < list2.size(); i++) {
			Map<Object, Object> itemMap = new HashMap<Object, Object>();
			ChestModel item = list2.get(i);
			itemMap.put("id", item.getChestId());
			itemMap.put("name", item.getChestId() + "  " + item.getChestName());
			objList.add(itemMap);
		}

		return "@" + JSONObject.toJSONString(objList);
	}

}
