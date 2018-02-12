package cn.saturn.web.code;

/**
 *
 * 
 * @author zuojie.x
 */
public interface Head {
	/** 错误码, 账号被禁止. */
	public static final int errCode_banned = -1000;

	// /** 关闭服务器 */
	// public static final int H999 = 999;
	// /** 心跳 */
	// public static final int H1000 = 1000;
	// /** 登陆 */
	// public static final int H1001 = 1001;
	// /** 创建角色 */
	// public static final int H1002 = 1002;
	// /** 获取随机角色名 */
	// public static final int H1003 = 1003;
	// /** 重新登陆 */
	// public static final int H1004 = 1004;
	//
	// /** 获取建筑列表 */
	// public static final int H1101 = 1101;
	// /** 建筑升级 */
	// public static final int H1102 = 1102;
	//
	// /** 获取指定玩家的主城 */
	// public static final int H1111 = 1111;
	//
	// // 冒险者商店======================================
	// /** 商品列表 */
	// public static final int H1121 = 1121;
	// /** 购买 */
	// public static final int H1122 = 1122;
	// /** 刷新列表 */
	// public static final int H1123 = 1123;
	//
	// // 训练所======================================
	// /** 训练列表 */
	// public static final int H1131 = 1131;
	// /** 开始训练 */
	// public static final int H1132 = 1132;
	// /** 结束训练 */
	// public static final int H1133 = 1133;
	//
	// // 铁匠铺======================================
	// /** 锻造装备 */
	// public static final int H1141 = 1141;
	//
	// // 光之圣殿====================================
	// /** 获取圣殿信息 */
	// public static final int H1151 = 1151;
	// /** 兑换水晶 */
	// public static final int H1152 = 1152;
	//
	// // 矿井========================================
	// /** 更新可领取矿石数 */
	// public static final int H1161 = 1161;
	// /** 领取矿石 */
	// public static final int H1162 = 1162;
	//
	// // 贸易站========================================
	// /** 获取贸易信息 */
	// public static final int H1171 = 1171;
	// /** 开始贸易 */
	// public static final int H1172 = 1172;
	// /** 加速贸易 */
	// public static final int H1173 = 1173;
	// /** 取消贸易 */
	// public static final int H1174 = 1174;
	// /** 完成贸易 */
	// public static final int H1175 = 1175;
	//
	// // 光之塔========================================
	// /** 获取光之塔信息 */
	// public static final int H1181 = 1181;
	// /** 选择光源 */
	// public static final int H1182 = 1182;
	//
	// // 冒险========================================
	// /** 获取章节列表 */
	// public static final int H1201 = 1201;
	// /** 开始某个关卡 */
	// public static final int H1202 = 1202;
	// /** 通关某个关卡 */
	// public static final int H1203 = 1203;
	// /** 领取章节奖励 */
	// public static final int H1211 = 1211;
	// /** 扫荡信息 */
	// public static final int H1212 = 1212;
	// /** 扫荡 */
	// public static final int H1213 = 1213;
	//
	// // 龙之回廊====================================
	// /** 获取龙之回廊信息 */
	// public static final int H1241 = 1241;
	// /** 开始攻打某个龙之回廊 */
	// public static final int H1242 = 1242;
	// /** 通关攻打某个龙之回廊 */
	// public static final int H1243 = 1243;
	//
	// // 十二星宫====================================
	// /** 获取十二星宫信息 */
	// public static final int H1261 = 1261;
	// /** 领取通关难度奖励 */
	// public static final int H1262 = 1262;
	// /** 重置星宫 */
	// public static final int H1263 = 1263;
	// /** 获取十二星宫商店 */
	// public static final int H1271 = 1271;
	// /** 刷新十二星宫商店 */
	// public static final int H1272 = 1272;
	// /** 竞技十二星宫购买 */
	// public static final int H1273 = 1273;
	//
	// // 卡片========================================
	// /** 获取卡片列表 */
	// public static final int H1401 = 1401;
	// /** 卡片升级 */
	// public static final int H1402 = 1402;
	// /** 卡片强化 */
	// public static final int H1403 = 1403;
	// /** 卡片升阶 */
	// public static final int H1404 = 1404;
	// /** 卡片升星 */
	// public static final int H1405 = 1405;
	//
	// /** 穿上装备 */
	// public static final int H1411 = 1411;
	// /** 装备强化 */
	// public static final int H1413 = 1413;
	//
	// // 卡牌友情值====================================
	// /** 获取组合友情值 */
	// public static final int H1431 = 1431;
	//
	// // 阵形========================================
	// /** 阵形列表 */
	// public static final int H1501 = 1501;
	// /** 更新阵型 */
	// public static final int H1502 = 1502;
	//
	// // 竞技场======================================
	// /** 获取竞技场信息 */
	// public static final int H1601 = 1601;
	// /** 刷新/获取对手列表 */
	// public static final int H1602 = 1602;
	// /** 开始挑战 */
	// public static final int H1603 = 1603;
	// /** 结束挑战 */
	// public static final int H1604 = 1604;
	// /** 获取战斗记录,竞技场,掠夺战 */
	// public static final int H1605 = 1605;
	// /** 获取录像 */
	// public static final int H1606 = 1606;
	// /** 获取排行榜 */
	// public static final int H1607 = 1607;
	// /** 获取竞技场商店 */
	// public static final int H1608 = 1608;
	// /** 刷新竞技场商店 */
	// public static final int H1609 = 1609;
	// /** 竞技场商店购买 */
	// public static final int H1610 = 1610;
	//
	// // 掠夺战======================================
	// /** 掠夺战信息 */
	// public static final int H1621 = 1621;
	// /** 刷新/获取对手列表 */
	// public static final int H1622 = 1622;
	// /** 开始掠夺战 */
	// public static final int H1623 = 1623;
	// /** 结束掠夺战 */
	// public static final int H1624 = 1624;
	// /** 领取宝箱奖励 */
	// public static final int H1625 = 1625;
	// /** 查看反击目标 */
	// public static final int H1631 = 1631;
	// /** 开始反击 */
	// public static final int H1632 = 1632;
	// /** 结束反击 */
	// public static final int H1633 = 1633;
	//
	// // 提醒========================================
	// /** 更新提醒 */
	// public static final int H5000 = 5000;
	//
	// // 聊天========================================
	// /** 获取聊天记录 */
	// public static final int H5001 = 5001;
	// /** 发送聊天记录 */
	// public static final int H5002 = 5002;
	// /** 拉黑玩家 */
	// public static final int H5003 = 5003;
	// /** 投诉玩家 */
	// public static final int H5004 = 5004;
	// /** 读取拉黑名单 */
	// public static final int H5005 = 5005;
	// /** 读取公告消息 */
	// public static final int H5006 = 5006;
	//
	// // 邮件========================================
	// /** 获取邮件信息 */
	// public static final int H5101 = 5101;
	// /** 发送邮件信息 */
	// public static final int H5102 = 5102;
	// /** 删除邮件 */
	// public static final int H5103 = 5103;
	// /** 领取邮件奖励 */
	// public static final int H5104 = 5104;
	// /** 全部领取 */
	// public static final int H5105 = 5105;
	// /** 新邮件 */
	// public static final int H5106 = 5106;
	// // 好友========================================
	// /** 读取好友列表 */
	// public static final int H5201 = 5201;
	// /** 搜索好友 */
	// public static final int H5202 = 5202;
	// /** 好友申请 */
	// public static final int H5203 = 5203;
	// /** 读取申请添加你的好友邀请 */
	// public static final int H5204 = 5204;
	// /** 接受新的好友请求消息 */
	// public static final int H5210 = 5210;
	// /** 读取你发送出去的好友邀请(刷新) */
	// public static final int H5205 = 5205;
	// /** 取消(拒绝)好友邀请 */
	// public static final int H5206 = 5206;
	// /** 确定好友邀请 */
	// public static final int H5207 = 5207;
	// /** 删除好友 */
	// public static final int H5208 = 5208;
	// /** 友情值贈送 */
	// public static final int H5209 = 5209;
	//
	// // 提醒========================================
	// /** 更新提醒 */
	// public static final int H5300 = 5300;
	//
	// // 设置========================================
	// /** 提交设置 */
	// public static final int H6001 = 6001;
	// /** 读取设置 */
	// public static final int H6005 = 6005;
	// /** 改名提交 */
	// public static final int H6002 = 6002;
	// /** 修改头像url */
	// public static final int H6003 = 6003;
	// /** 修改头像框 */
	// public static final int H6004 = 6004;
	// /** 兑换兑换码 */
	// public static final int H6101 = 6101;
	// /** 发送bug回馈 */
	// public static final int H6201 = 6201;
	// /** 获取每日登陆信息 */
	// public static final int H6301 = 6301;
	// /** 领取登陆奖励(当天) */
	// public static final int H6302 = 6302;
	//
	// // 运营========================================
	// /** 获取活动主界面列表 */
	// public static final int H6502 = 6502;
	// /** 活動更新下发 */
	// public static final int H6503 = 6503;
	// /** 领取充值返还 */
	// public static final int H6504 = 6504;
	// /** 领取活动奖励 */
	// public static final int H6505 = 6505;
	// /** 读取活动状态 */
	// public static final int H6506 = 6506;
	// /** 读取广告版列表 */
	// public static final int H6501 = 6501;
	// /** 广告消息 */
	// public static final int H6507 = 6507;
	//
	// // 新手引导======================================
	// /** 开始新手引导 */
	// public static final int H6601 = 6601;
	// /** 完成新手引导 */
	// public static final int H6602 = 6602;
	//
	// // 模块开启======================================
	// /** 模块信息获取 */
	// public static final int H6701 = 6701;
	// /** 开启模块 */
	// public static final int H6702 = 6702;
	//
	// // 背包========================================
	// /** 读取背包信息 */
	// public static final int H7001 = 7001;
	// /** 打开物品, 和新增物品消息 */
	// public static final int H7002 = 7002;
	// /** 出售物品 */
	// public static final int H7003 = 7003;
	//
	// // 商城========================================
	// /** 读取商城页面信息 */
	// public static final int H7100 = 7100;
	// /** 充值 */
	// public static final int H7101 = 7101;
	// /** 购买金币 */
	// public static final int H7102 = 7102;
	// /** 购买行动力 */
	// public static final int H7103 = 7103;
	// /** 读取行动力购买信息 */
	// public static final int H7104 = 7104;
	// /** 购买月卡 */
	// public static final int H7105 = 7105;
	// /** 购买限时礼包 */
	// public static final int H7106 = 7106;
	// /** 召唤英雄(单次) */
	// public static final int H7201 = 7201;
	// /** 召唤英雄(十次) */
	// public static final int H7202 = 7202;
	// /** 物品抽奖(单次) */
	// public static final int H7301 = 7301;
	// /** 物品抽奖(十次) */
	// public static final int H7302 = 7302;
	// /** VIP信息获取 */
	// public static final int H7401 = 7401;
	//
	// // 任务成就========================================
	// /** 读取任务成就列表 */
	// public static final int H8001 = 8001;
	// /** 完成任务成就 */
	// public static final int H8002 = 8002;
	// /** 任务成就更新 */
	// public static final int H8003 = 8003;
	// /** 获取任务累计奖励 */
	// public static final int H8004 = 8004;
	// /** 获取成就累计奖励 */
	// public static final int H8005 = 8005;
	// // 行动力========================================
	// /** 读取体力信息 */
	// public static final int H9101 = 9101;

	// 充值
	public static final int H1802 = 1802;

	// Web访问========================================
	/** 读取服务器列表 */
	public static final int H10001 = 10001;

	/** 登陆验证 */
	public static final int H10002 = 10002;

	/** 登陆检测 */
	public static final int H10003 = 10003;

	/** 注册账号(自营平台) */
	public static final int H10007 = 10007;

	/** 修改密码 */
	public static final int H10008 = 10008;

	/** 更新角色 */
	public static final int H10004 = 10004;

	/** 读取用户信息 */
	public static final int H10005 = 10005;

	/** 读取用户全部信息 */
	public static final int H10006 = 10006;

	// Web操作========================================
	/** 发送邮件 */
	public static final int H11001 = 11001;
	/** 获取全局邮件列表 */
	public static final int H11002 = 11002;
	/** 删除全局邮件 */
	public static final int H11003 = 11003;

	/** GM指令列表读取 */
	public static final int H11101 = 11101;
	/** GM指令发送 */
	public static final int H11102 = 11102;

	// Web公告========================================
	/** 系统聊天发送 */
	public static final int H12001 = 12001;

	/** 获取黑名单 */
	public static final int H12101 = 12101;
	/** 拉黑玩家 */
	public static final int H12102 = 12102;

	/** 走马灯 */
	public static final int H12201 = 12201;
	/** 走马灯列表 */
	public static final int H12202 = 12202;
	/** 删除走马灯 */
	public static final int H12203 = 12203;
	// Web权限========================================
	/** 封号 */
	public static final int H13001 = 13001;
	/** 获取封号列表 */
	public static final int H13002 = 13002;

	// 日志========================================
	/** 获取日志信息 */
	public static final int H14001 = 14001;
	/** 获取系统日志内容 */
	public static final int H14004 = 14004;

	// bug========================================
	/** 获取bug信息 */
	public static final int H14101 = 14101;
	/** 提交bug信息 */
	public static final int H14102 = 14102;
	/** 删除bug信息 */
	public static final int H14103 = 14103;

	// 服务器=======================================
	/** 关服通知提醒 */
	public static final int H9201 = 9201;
	/** 执行关闭 */
	public static final int H14002 = 14002;

	// 版本=======================================
	/** 检测版本号信息 */
	public static final int H16001 = 16001;
	/** 小版本号信息 */
	public static final int H16002 = 16002;

	// 充值=======================================
	/** 创建订单 */
	public static final int H40001 = 40001;

	// cdkey=======================================
	/** cdkey */
	public static final int H17001 = 17001;
	/** 上传cdkey */
	public static final int H17002 = 17002;

	// 统计数据=======================================
	/** 统计数据 */
	public static final int H18001 = 18001;

	// 活动=======================================
	/** 设置活动 */
	public static final int H19001 = 19001;
	/** 获取活动列表 */
	public static final int H19002 = 19002;
	/** 删除活动 */
	public static final int H19003 = 19003;
	/** 唯一活动弹窗 */
	public static final int H19004 = 19004;

	// 激活码验证
	// public static final int H20001 = 20001;
	/** 上传激活码 **/
	public static final int H20002 = 20002;

	/** 上传用户激活**/
	public static final int H21000 = 21000;
	
	/** 配置闪光交换精灵 **/
    public static final int H22001 = 22001;

	// 错误=======================================
	/** 错误消息 **/
	public static final int H0 = 0;
	
	/** 扭蛋积分特卖  **/
    public static final int H251011 = 251011;
    
    /** 额外活动掉落 **/
    public static final int H2520001 = 2520001;
    
    // 口袋王牌=======================================
    
    /** 450001 限时卡包 **/
    public static final int H450001= 450001;
    
    /** 450002 聊天公告 **/
    public static final int H450002=450002;
}
