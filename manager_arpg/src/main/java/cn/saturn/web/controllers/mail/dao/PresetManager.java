package cn.saturn.web.controllers.mail.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.model.auto.ModelManager;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;

@Component
public class PresetManager extends ModelManager<PresetModel, PresetDAO> implements ComponentManager.IComponent {
	public static final int presetType_mail = 1;
	public static final int presetType_bugle = 2;
	/** 活动*/
	public static final int presetType_activity = 3;
	/** 掉落表配置 */
	public static final int presetType_chest = 4;
	
	/** 热点精灵 */
	public static final int presetType_poke = 5;

	/** cdk 配置 */
	public static final int presetType_cdk = 6;

	/** uc cdk */
	public static final int presetType_uccdk = 7;

	/** 广告图展示 */
	public static final int presetType_banner = 8;
	
	/** 弹窗 */
	public static final int presetType_winpush = 9;
	
	/** 闪光交换 */
	public static final int presetType_exchange = 10;
	
	/** 礼包配置*/
	public static final int presetType_gift = 11;
	
	/** 积分特卖*/
	public static final int presetType_shop = 12;
	
	/** 额外掉落*/
	public static final int presetType_dropExtra = 13;
	
	/** 限时卡包*/
	public static final int presetType_timerCard = 14;
	
	/** 口袋王牌弹窗信息*/
	public static final int presetType_noticeWp = 15;

	protected static PresetManager instance;
	
	protected static final Logger logger = LoggerFactory.getLogger(LogType.detelePreset);
	
	@Resource
	PresetDAO dao;

	@Override
	public boolean reload(ApplicationContext context) {
		this.init();
		instance = this;
		return true;
	}

	@Override
	public void release() {

	}

	public static PresetManager getInstance() {
		return instance;
	}

	@Override
	protected PresetDAO getDAO() {
		return dao;
	}

	public static List<PresetModel> getPresetList(int type) {
		PresetManager presetManager = getInstance();
		List<PresetModel> list = presetManager.dao.get(type, 0, 10000);
		return list;
	}

	public static String listJson(Invocation inv, int type, BsgridUtils.IConvert<PresetModel> action)
			throws IOException {
		PresetManager presetManager = getInstance();
		HttpServletRequest request = inv.getRequest();

		// 读取参数
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		int totalRows = 0;

		// 获取数量
		totalRows = Utils.getIntParameter(inv, "presetcount" + type);
		if (totalRows <= 0) {
			totalRows = presetManager.dao.getCount(type);
		}

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);
		// 遍历
		List<PresetModel> list = presetManager.dao.get(type, startRow, pageSize);

		// // 转换接口
		// BsgridUtils.IConvert<PresetModel> action = new
		// BsgridUtils.IConvert<PresetModel>() {
		//
		// @Override
		// public boolean convert(PresetModel model, Map<String, Object> map) {
		// map.put("id", model.getId());
		// map.put("remark", model.getRemark());
		// return true;
		// }
		//
		// };
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);
		return "@" + jsonStr;
	}
	
	public static String toDelete(Invocation inv, int id, String succeedUrl) {
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("user");
		String  userName=(userModel !=null)?userModel.getUsername():"unknow";
	
		logger.info("name:"+userName+",id:"+id+",url:"+succeedUrl);
		instance.dao.remove(id);
		return "success";
		// return JumpController.jump(inv, "删除成功", succeedUrl);
	}

	@Override
	public void insertByDAO(PresetModel model) {
		super.insertByDAO(model);
	}

	@Override
	public boolean updateByDAO(PresetModel model) {
		return super.updateByDAO(model);
	}

	@Override
	public void removeByDAO(long id) {
		super.removeByDAO(id);
	}

	public PresetModel get(long id) {
		return dao.get(id);
	}
	
	public List<PresetModel> getByType(int type) {
		return dao.getByType(type);
	}
}
