package cn.saturn.web.controllers.activity.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.WebUtils;
import proto.ProtocolWeb;
import proto.ProtocolWeb.SetActivityWCS;

public class PresetActivity {
	public static final int activityType_cf = 1; // 充值返还
	public static final int activityType_tztw = 2; // 挑战天王奖励
	public static final int activityType_12g = 3; // 十二星宫N倍奖励活动
	public static final int activityType_ldz = 4; // 龙子之回廊奖励
	public static final int activityType_kh = 5; // 新版狂欢
	public static final int activityType_cj = 6; // 冲级奖励活动
	public static final int activityType_tllq = 7; // 体力领取活动
	public static final int activityType_yk = 8; // 月卡领取活动
	public static final int activityType_sc = 9; // 首冲
	public static final int activityType_xs = 10; // 限时
	public static final int activityType_mrcz = 11; // 每日充值
	public static final int activityType_dbcz = 12; // 单笔充值
	public static final int activityType_ljxh = 13; // 累计消耗
	public static final int activityType_wpdh = 14; // 物品兑换
	public static final int activityType_phjl = 15; // 排行奖励
	public static final int activityType_zhzk = 16; // 召唤折扣
	public static final int activityType_zhjl = 17; // 召唤奖励
	public static final int activityType_czsf = 18; // 充值双返
	public static final int activityType_czfl2 = 19; // 充值返利(百分比)
	public static final int activityType_normal = 20; // 通用模板(无逻辑)
	public static final int activityType_cjfl10 = 21; // 抽奖返利(水晶10连抽)
	public static final int activityType_23 = 23; // 抽奖返利(水晶10连抽)
	public static final int activityType_24 = 24; // 抽奖返利(水晶10连抽)
	public static final int activityType_vip25 = 25; // vip月卡
	public static final int activityType_26 = 26; // 抽奖返利(水晶10连抽)
	public static final int activityType_27 = 27; // 抽奖返利(水晶10连抽)
	public static final int activityType_vip28 = 28; // VIP特权
	public static final int activeType_theme = 29; // 主题活动
	public static final int activeType_zhuan_pan = 30; // 转盘活动
	public static final int activeType_tian_tian = 31; // 天天礼包
	public static final int activeType_shen_sou = 32; // 限时神兽
	public static final int activeType_xun_huan = 33;//循环充值
	public static final int activeType_zhu_ti = 34;//主题精灵
	public static final int activeType_xingweidh = 35;//行为类型兑换
	public static final int activeType_chongzhi_paihang = 36;//充值排行
	public static final int activeType_xiaohao_paihang = 37;//消耗排行
	public static final int activeType_chongzhi_fanli = 38;//充值装盘返利
	public static final int activeType_czjifen_dh = 39;//[充值]积分兑换
	public static final int activeType_xhjifen_dh = 40;//[消耗]积分兑换
	public static final int activeType_shuangbei_jinyan = 41;//双倍经验活动
	public static final int activeType_shuangbei_diaoluo = 42;//双倍掉落活动
	public static final int activeType_quanfu_chongzhi = 47; // 全服充值活动
	public static final int activeType_shenmi_libao = 48; // 神秘礼包
	public static final int activeType_qiri_huodong = 49; // 七日活动
	public static final int activeType_quanfu_huodong = 50; // 全服活动
	public static final int activeType_VIP_chengzhang = 51; //VIP成长基金
	
	// 设计到排行榜奖励的活动限定id
	public static final int activityId_turntable = 180000026;// 转盘
	public static final int activityId_cr = 180000028;// 消费排行(限时神兽)

	public static final String[] typeStrs = new String[] { "未知", "充值返还", "挑战天王奖励", "十二星宫翻倍奖励", "掠夺战翻倍奖励", "登陆奖励",
			"冲级奖励", "体力领取", "月卡奖励", "首冲", "限时礼包", "每日充值", "单笔充值", "累计消费", "物品兑换", "排行榜奖励", "召唤折扣", "召唤奖励", "充值双返",
			"充值返利(百分比)", "通用模板", "10连抽奖励", "23", "24", "VIP月卡", "26", "27", "VIP特权", "主题活动", "转盘活动", "天天活动礼包", "限时神兽", 
			"循环充值", "主题精灵", "行为类型兑换", "充值排行", "消耗排行", "充值转盘返利", "充值积分兑换", "消耗积分兑换", "双倍经验活动", "双倍掉落活动"};
	
	public static final String[] clazzStrs = new String[] { "普通活动", "充值活动" };

	protected int id; // 活动ID
	protected int cid; // 副本ID
	protected int typeId;// 获得类型模版id
	protected int type; // 活动类型
	protected int order; // 排序
	protected String tital; // 标题
	protected String icon;
	protected String intro; // 简介
	protected String tips; // 提示
	protected int startDayTime; // 开启日期
	protected int endDayTime; // 开启日期
	protected int clazz; // 活动类型, 0.普通活动 1.支付活动
	protected String[] awards; // 奖励
	protected String[] requires; // 需求条件
	protected String[] petIds;//精灵Id
	protected String[] adImages;//广告图
	protected int index; // 活动版本(用于重置活动)
	protected int speArg; // 额外参数
	protected int[] srvIds; // 激活的服务器列表
	protected int openDate; // 以开服时间计算的，打开活动天数
	protected int closeDate; // 一开服时间计算的，关闭活动天数
	protected int open_state;// 开服时间类型, 0.普通开服时间 1.开服天数计算
	protected String category; // 分类
	protected String winPhoto;//广告图
	protected String period;//时间段
	protected String extraParams;//额外参数
	protected int winPhotoIndex;//广告图排序
	
	// type 30 号数据
	protected String[] itemList; // 轮盘物品清单
	protected String[] turnTableCsAward; // 轮盘次数奖励
	protected long useItem; // 消耗物品id
	protected int usetNum; // 消耗物品数量
	protected int freeNum; // 免费转盘次数
	protected int openRanking; // 是否打开转盘奖励

	// 带排行榜功能需要一个延时参数
	protected int delayTime;// 延长时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getTital() {
		return tital;
	}

	public void setTital(String tital) {
		this.tital = tital;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getStartDayTime() {
		return startDayTime;
	}

	public void setStartDayTime(int startDayTime) {
		this.startDayTime = startDayTime;
	}

	public int getEndDayTime() {
		return endDayTime;
	}

	public void setEndDayTime(int endDayTime) {
		this.endDayTime = endDayTime;
	}

	public int getClazz() {
		return clazz;
	}

	public void setClazz(int clazz) {
		this.clazz = clazz;
	}

	public String[] getRequires() {
		return requires;
	}

	public void setRequires(String[] requires) {
		this.requires = requires;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public int getSpeArg() {
		return speArg;
	}

	public void setSpeArg(int speArg) {
		this.speArg = speArg;
	}

	public String[] getAwards() {
		return awards;
	}

	public void setAwards(String[] awards) {
		this.awards = awards;
	}

	public int[] getSrvIds() {
		return srvIds;
	}

	public void setSrvIds(int[] srvIds) {
		this.srvIds = srvIds;
	}

	public int getOpenDate() {
		return openDate;
	}

	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}

	public int getOpen_state() {
		return open_state;
	}

	public int getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(int closeDate) {
		this.closeDate = closeDate;
	}

	public void setOpen_state(int open_state) {
		this.open_state = open_state;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String[] getItemList() {
		return itemList;
	}

	public void setItemList(String[] itemList) {
		this.itemList = itemList;
	}

	public String[] getTurnTableCsAward() {
		return turnTableCsAward;
	}

	public void setTurnTableCsAward(String[] turnTableCsAward) {
		this.turnTableCsAward = turnTableCsAward;
	}

	public long getUseItem() {
		return useItem;
	}

	public void setUseItem(long useItem) {
		this.useItem = useItem;
	}

	public int getUsetNum() {
		return usetNum;
	}

	public void setUsetNum(int usetNum) {
		this.usetNum = usetNum;
	}

	public int getFreeNum() {
		return freeNum;
	}

	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
	}

	public int getOpenRanking() {
		return openRanking;
	}

	public void setOpenRanking(int openRanking) {
		this.openRanking = openRanking;
	}

	public String[] getPetIds() {
		return petIds;
	}

	public void setPetIds(String[] petIds) {
		this.petIds = petIds;
	}

	public String[] getAdImages() {
		return adImages;
	}

	public void setAdImages(String[] adImages) {
		this.adImages = adImages;
	}

	public String getWinPhoto() {
		return winPhoto;
	}

	public void setWinPhoto(String winPhoto) {
		this.winPhoto = winPhoto;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(String extraParams) {
		this.extraParams = extraParams;
	}
	
	public int getWinPhotoIndex() {
		return winPhotoIndex;
	}

	public void setWinPhotoIndex(int winPhotoIndex) {
		this.winPhotoIndex = winPhotoIndex;
	}

	public boolean isOpen(){
		if(this.open_state == 1)
			return false;
		Date date = new Date();
		if(this.startDayTime <= 0 && this.endDayTime <= 0)
			return true;
		if(this.startDayTime <= 0){
			if(new Date(1000l*this.endDayTime + 86400000l).after(date))
				return true;
		}
		if(this.endDayTime <= 0){
			if(new Date(1000l*this.startDayTime).before(date)){
				return true;
			}
		}
		if(new Date(1000l*this.endDayTime + 86400000l).after(date) && new Date(1000l*this.startDayTime).before(date)){
			return true;
		}
		return false;
	}

	/**
	 * 返回提交后的错误信息<br>
	 * 
	 * @return
	 */
	public List<ResultMsg> onSumitMsg() {
		List<ResultMsg> msgs = new ArrayList<>();

		if (open_state == 1 && openDate <= 0)
			msgs.add(new ResultMsg(ResultMsg.fail, "请填写正确的, 以开服时间计算的 活动开服天数"));

		if (open_state == 1 && closeDate <= 0)
			msgs.add(new ResultMsg(ResultMsg.fail, "请填写正确的, 以开服时间计算的 活动关闭天数"));

		if (open_state == 1 && closeDate - openDate < 0)
			msgs.add(new ResultMsg(ResultMsg.fail, "请填写正确的, 以开服时间计算的  活动开服天数必须小于关闭天数"));

		if (open_state == 0 && DateUtils.daysBetween(endDayTime, startDayTime) < 0)
			msgs.add(new ResultMsg(ResultMsg.fail, "开启时间必须 大于0 "));

//		if (type == PresetActivity.activeType_tian_tian && speArg <= 0)
//			msgs.add(new ResultMsg(ResultMsg.fail, "天天礼包,充值金额必须大于 0 "));

		if (type == PresetActivity.activeType_tian_tian && open_state == 1 && closeDate - openDate > 7)
			msgs.add(new ResultMsg(ResultMsg.fail, "天天礼包,开启时间必须 小于等于 7 "));

		if (type == PresetActivity.activeType_tian_tian && open_state == 0
				&& DateUtils.daysBetween(endDayTime, startDayTime) > 7)
			msgs.add(new ResultMsg(ResultMsg.fail, "天天礼包,开启时间必须 小于等于 7 "));

		return msgs;
	}

	public SetActivityWCS.Builder toProtoMsgBuilder() {

		SetActivityWCS.Builder b = SetActivityWCS.newBuilder();
		// base data 写入
		// 排行榜判断奖励判定
		if (type == activeType_zhuan_pan) // 如果是转盘
		{
			// b.setId(activityId_turntable);
			b.setId(id);
			b.setDelayTime(24);
		} else if (type == activeType_shen_sou) // 如果是限时神兽
		{
			// b.setId(activityId_cr);
			b.setId(id);
			b.setDelayTime(24);
		} else if(type == activeType_chongzhi_paihang || type==activeType_xiaohao_paihang ||type==activeType_quanfu_chongzhi){
			b.setId(id);
			b.setDelayTime(24);
		} else
			b.setId(id); // 普通

		b.setCid(cid);
		if (icon != null)
			b.setIcon(icon);
		if (intro != null)
			b.setIntro(intro);
		if (tips != null)
			b.setTips(tips);

		b.setTital(tital);
		b.setType(type);
		b.setOrder(order);
		b.setClazz(clazz);
		b.setSpeArg(speArg);
		b.addAllTimeInDay(this.period==null?new ArrayList<>():Arrays.asList(this.period.split(";")));
		b.setExtraParams(this.extraParams == null?"":this.extraParams);

		if (type == activeType_zhuan_pan) {
			List<String> csAwards = ListExtUtil.arrayToList(turnTableCsAward);
			b.addAllCsAwards(csAwards);

			List<String> itemLists = ListExtUtil.arrayToList(itemList);
			b.addAllItemList(itemLists);
			b.setUseItem((int) useItem);
			b.setUseNum(usetNum);
			b.setFreeNum(freeNum);
			b.setOpenRanking(openRanking);
		}

		// 判断发送时间
		if (open_state == 0) {
			b.setStartDayTime(getStartDayTime());
			b.setEndDayTime(getEndDayTime());
		}

		b.setEnable(0);
		
		//活动排序
		b.setWinPhotoIndex(winPhotoIndex);
		// 写入奖励
		List<String> awardList = ListExtUtil.arrayToList(awards);
		b.addAllAwards(awardList);

		// 写入需求
		List<String> reqList = ListExtUtil.arrayToList(requires);
		b.addAllRequires(reqList);

		List<String> petIdList = ListExtUtil.arrayToList(petIds);
		b.addAllPetIds(petIdList);
		
		List<String> adImageList = ListExtUtil.arrayToList(adImages);
		b.addAllAdImages(adImageList);
		return b;
	}

	public ProtocolWeb.SetActivityWCS toProtoByBuilder(final SetActivityWCS.Builder b, String srvOpenTime,
			boolean enable,String srvIds) throws ParseException {
		// 状态转换
		b.setEnable(enable ? 0 : 1);
		// 开服时间
		if (open_state == 1) {
			String openTime = srvOpenTime;
			String closeTime = srvOpenTime;
			openTime = DateUtils.getAddDayStr(openTime, openDate - 1);
			closeTime = DateUtils.getAddDayStr(closeTime, closeDate - 1);

			int startDayTime = WebUtils.Html.getDate0(openTime);
			int endDayTime = WebUtils.Html.getDate0(closeTime);
			b.setStartDayTime(startDayTime);
			b.setEndDayTime(endDayTime);
		}
		String srvId=srvIds.trim().substring(0,srvIds.length()-1);
		b.setServerIds(srvId);
		return b.build();
	}
}
