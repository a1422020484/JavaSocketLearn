
-- -------------------- 平台必须

-- ----------------------------
-- Table structure for activity (活动模版表)
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `condition_desc` varchar(255) DEFAULT NULL COMMENT '条件描述',
  `info` text,
  `tips` text,
  `type` int(255) DEFAULT NULL,
  `activitySpeArgs` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `activityOrder` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('1', '新版狂欢', '登录天数{%s}天', '测试文本', '可在下方列表中查看新版本每日奖励情况', '5', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('2', '冲级送礼', '达到等级{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '6', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('3', '体力领取', '时间段内', '测试文本', '可在下方列表中查看新版本每日奖励情况', '7', '16200007', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('4', '充值返利', '充值{%s}水晶', '测试文本', '可在下方列表中查看新版本每日奖励情况', '1', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('5', '挑战天王', '时间段内', '测试文本', '可在下方列表中查看新版本每日奖励情况', '2', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('6', '专享月卡', '充值{%s}元', '测试文本', '可在下方列表中查看新版本每日奖励情况', '8', '16200008', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('7', '首充礼包', '充值{%s}元', '测试文本', '可在下方列表中查看新版本每日奖励情况', '9', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('8', '每日充值', '充值{%s}水晶', '测试文本', '可在下方列表中查看新版本每日奖励情况', '11', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('9', '单笔充值', '充值{%s}水晶', '测试文本', '可在下方列表中查看新版本每日奖励情况', '12', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('10', '战斗力排行榜活动', '等级达到{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '15', '3', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('11', '图鉴收集排行榜活动', '收集数量达到{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '15', '4', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('12', '对战中心排行榜活动', '对战排名{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '15', '1', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('13', '充值双返活动', '充值达到{%s}水晶', '测试文本', '可在下方列表中查看新版本每日奖励情况', '18', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('14', '水晶累计消耗活动', '累计消耗水晶{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '13', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('15', '百分比充值活动', '累计充值{%s}水晶', '测试文本', '可在下方列表中查看新版本每日奖励情况', '19', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('16', '兑换活动', '充值物品达到{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '14', '9999', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('17', '累计十连抽活动', '10连抽物品达到{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '21', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('18', '成长计划活动', '在等级{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '22', '1000', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('19', 'VIP月卡', '充值{%s}元', '测试文本', '可在下方列表中查看新版本每日奖励情况', '25', '16200009', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('20', 'VIP特权', 'VIP等级达到{%s}', '测试文本', '可在下方列表中查看新版本每日奖励情况', '28', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('21', '主题活动', '{%s}元', '测试文本', '可在下方列表中查看新版本每日奖励情况', '29', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('22', '天天礼包', '{%s}天数', '测试文本', '可在下方列表中查看新版本每日奖励情况', '31', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('23', '限时神兽', '{%s}名次', '测试文本', '可在下方列表中查看新版本每日奖励情况', '32', '170100156', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('24', '转盘活动', '{%s}名次', '测试文本', '可在下方列表中查看新版本每日奖励情况', '30', '170100156', 'activity_banner_01', '1');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL COMMENT '菜单栏位',
  `url` varchar(255) DEFAULT NULL COMMENT '对应的url',
  `pid` int(11) DEFAULT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '首页', null, '-1');
INSERT INTO `menu` VALUES ('2', '服务器', null, '-1');
INSERT INTO `menu` VALUES ('3', '服务器列表', 'server/list?mainView=true', '2');
INSERT INTO `menu` VALUES ('4', '区列表', 'server/section/list?mainView=true', '2');
INSERT INTO `menu` VALUES ('5', '版本号', 'server/version/list?mainView=true', '2');
INSERT INTO `menu` VALUES ('6', '资源包', 'server/package/list?mainView=true', '2');
INSERT INTO `menu` VALUES ('7', '操作', null, '-1');
INSERT INTO `menu` VALUES ('8', '邮件', 'mail/list?mainView=true', '7');
INSERT INTO `menu` VALUES ('9', 'GM指令', 'mail/gm/index?mainView=true', '7');
INSERT INTO `menu` VALUES ('10', '游戏参数', 'mail/param/index?mainView=true', '7');
INSERT INTO `menu` VALUES ('11', '虚拟支付', 'mail/vpay/index?mainView=true', '7');
INSERT INTO `menu` VALUES ('12', '公告', null, '-1');
INSERT INTO `menu` VALUES ('13', '黑名单', 'notice/blacklist/list?mainView=true', '12');
INSERT INTO `menu` VALUES ('14', '走马灯', 'notice/list?mainView=true', '12');
INSERT INTO `menu` VALUES ('15', '登陆公告', 'notice/notice/list?mainView=true', '12');
INSERT INTO `menu` VALUES ('16', '统计', null, '-1');
INSERT INTO `menu` VALUES ('17', '总统计', 'statistics/total?mainView=true', '16');
INSERT INTO `menu` VALUES ('18', '服务器统计', 'statistics/server?mainView=true', '16');
INSERT INTO `menu` VALUES ('19', '系统log', 'statistics/systemLog/show?mainView=true', '16');
INSERT INTO `menu` VALUES ('20', '汇总表', null, '16');
INSERT INTO `menu` VALUES ('21', '日报汇总表', 'statistics/channelReport/total?type=2&mainView=true', '20');
INSERT INTO `menu` VALUES ('22', '渠道汇总表', 'statistics/channelReport/total?type=1&mainView=true', '20');
INSERT INTO `menu` VALUES ('23', '新服数据指标', null, '16');
INSERT INTO `menu` VALUES ('24', '新服收入报表', 'statistics/totalByTime/total?type=3&mainView=true', '23');
INSERT INTO `menu` VALUES ('25', '新服活跃数', 'statistics/totalByTime/total?type=4&mainView=true', '23');
INSERT INTO `menu` VALUES ('26', '用户转化指标', 'statistics/channelReport/total?type=5&mainView=true', '23');
INSERT INTO `menu` VALUES ('27', '充值玩家留存分析表', 'statistics/channelReport/total?type=6&mainView=true', '23');
INSERT INTO `menu` VALUES ('28', '用户LTV分析表', 'statistics/channelReport/total?type=7&mainView=true', '23');
INSERT INTO `menu` VALUES ('30', '活动', 'activity/list?mainView=true', '89');
INSERT INTO `menu` VALUES ('31', '权限', null, '-1');
INSERT INTO `menu` VALUES ('32', '封号', 'power/banned/list?mainView=true', '31');
INSERT INTO `menu` VALUES ('33', '维护账号', 'power/vindicator/list?mainView=true', '31');
INSERT INTO `menu` VALUES ('34', '读取角色信息', 'power/info/show?mainView=true', '31');
INSERT INTO `menu` VALUES ('35', '日志', 'power/log/show?mainView=true', '31');
INSERT INTO `menu` VALUES ('36', 'bug查看', 'power/bug/show?mainView=true', '31');
INSERT INTO `menu` VALUES ('37', '服务器操作', 'power/server/show?mainView=true', '31');
INSERT INTO `menu` VALUES ('38', '修改玩家账号密码', 'power/accountPassword/show?mainView=true', '31');
INSERT INTO `menu` VALUES ('39', '管理', null, '-1');
INSERT INTO `menu` VALUES ('40', '账号', 'account/list?mainView=true', '39');
INSERT INTO `menu` VALUES ('41', '修改密碼', 'account/changePwd?mainView=true', '39');
INSERT INTO `menu` VALUES ('42', '退出', 'login/tologout', '39');
INSERT INTO `menu` VALUES ('43', '统计分析数据', null, '16');
INSERT INTO `menu` VALUES ('44', '日活跃分布图(实时数据)', 'statistics/activeReport/total?type=8&mainView=true', '43');
INSERT INTO `menu` VALUES ('45', '小时在线人数（分区按日）', 'statistics/channelReport/total?type=9&mainView=true', '43');
INSERT INTO `menu` VALUES ('46', '充值区间分析', 'statistics/channelReport/total?type=10&mainView=true', '43');
INSERT INTO `menu` VALUES ('48', '新增付费人数及金额(分区按日)', 'statistics/channelReport/total?type=11&mainView=true', '43');
INSERT INTO `menu` VALUES ('49', '老玩家付费人数及金额(分区按日)', 'statistics/channelReport/total?type=12&mainView=true', '43');
INSERT INTO `menu` VALUES ('50', '充值汇总分服查询', 'statistics/inComeReport/total?type=13&mainView=true', '43');
INSERT INTO `menu` VALUES ('51', '充值汇总分服查询(子表)', 'statistics/inComeReport2/total?type=14&mainView=true', '43');
INSERT INTO `menu` VALUES ('52', '新手任务引导分析', 'statistics/channelReport/total?type=15&mainView=true', '72');
INSERT INTO `menu` VALUES ('53', '玩家首付周期', 'statistics/channelReport/total?type=16&mainView=true', '43');
INSERT INTO `menu` VALUES ('54', '玩家首付等级', 'statistics/channelReport/total?type=15&mainView=true', '43');
INSERT INTO `menu` VALUES ('55', '副本挑战', '', '43');
INSERT INTO `menu` VALUES ('56', '玩家水晶消耗', null, '43');
INSERT INTO `menu` VALUES ('57', '全服玩家消费数据', 'statistics/channelReport/total?type=18&mainView=true', '56');
INSERT INTO `menu` VALUES ('58', 'vip玩家消费数据', 'statistics/channelReport/total?type=19&mainView=true', '56');
INSERT INTO `menu` VALUES ('59', '详细物品消耗及产出', '', '43');
INSERT INTO `menu` VALUES ('60', '详细物品消耗统计', 'statistics/channelReport/total?type=15&mainView=true', '59');
INSERT INTO `menu` VALUES ('61', '详细物品产出统计', 'statistics/channelReport/total?type=15&mainView=true', '59');
INSERT INTO `menu` VALUES ('62', '货币产出及消耗汇总', null, '43');
INSERT INTO `menu` VALUES ('63', '水晶产出&消耗(等级汇总)', 'statistics/crystalLevel/total?type=20&mainView=true', '62');
INSERT INTO `menu` VALUES ('64', '水晶产出详细统计', 'statistics/crystalOutput/total?type=21&mainView=true', '62');
INSERT INTO `menu` VALUES ('66', '冒险副本', 'statistics/FBReport/total?type=17&fb=1&mainView=true', '55');
INSERT INTO `menu` VALUES ('67', '四大天王', 'statistics/FBReport/total?type=17&fb=2&mainView=true', '55');
INSERT INTO `menu` VALUES ('70', '计费档次购买分析(水晶)', 'statistics/crystalCost/total?type=23&mainView=true', '62');
INSERT INTO `menu` VALUES ('71', '货币(产出&消耗)汇总', 'statistics/moneySummary/total?type=24&mainView=true', '62');
INSERT INTO `menu` VALUES ('72', '任务分析', null, '43');
INSERT INTO `menu` VALUES ('73', '任务汇总表', 'statistics/channelReport/total?type=25&mainView=true', '72');
INSERT INTO `menu` VALUES ('74', '等级统计', null, '43');
INSERT INTO `menu` VALUES ('75', '实时等级统计', 'statistics/levelDistribute/total?type=26&mainView=true', '74');
INSERT INTO `menu` VALUES ('76', '平台操作日志', 'logs/show?mainView=true', '7');
INSERT INTO `menu` VALUES ('77', '策划', null, '1');
INSERT INTO `menu` VALUES ('78', '宝箱', 'chest/list?mainView=true', '77');
INSERT INTO `menu` VALUES ('79', '热点精灵', 'poke/PokeHunter/list?mainView=true', '77');
INSERT INTO `menu` VALUES ('80', '配置上传', 'upload?mainView=true', '77');
INSERT INTO `menu` VALUES ('81', 'cdn图片上传', 'upload/cdn?mainView=true', '77');
INSERT INTO `menu` VALUES ('88', '版本系统屏蔽', 'server/shield/list?mainView=true', '2');
INSERT INTO `menu` VALUES ('89', '运营', null, '1');
INSERT INTO `menu` VALUES ('90', 'cdk编辑', 'cdk/list?mainView=true', '89');
INSERT INTO `menu` VALUES ('91', 'ip白名单', 'power/vindicatorIp/list?mainView=true', '31');
INSERT INTO `menu` VALUES ('92', 'UC礼包编辑', 'cdk/uc/list?mainView=true', '89');
INSERT INTO `menu` VALUES ('93', '展示图', 'banner/list?mainView=true', '89');
INSERT INTO `menu` VALUES ('94', 'GM全服指令', 'mail/gmall/index?mainView=true', '7');
INSERT INTO `menu` VALUES ('95', '服务器发送的活动', 'activity/list2?mainView=true', '89');
INSERT INTO `menu` VALUES ('100', '小时数据(时实)', 'statistics/channelReport/total?type=100&mainView=true', '16');
INSERT INTO `menu` VALUES ('101', '当前服务器玩家在线', 'statistics/activeByServerReport/total?type=8&mainView=true', '16');
INSERT INTO `menu` VALUES ('102', '日报(时实)数据', 'statistics/dayOnlineReport/total?type=200&mainView=true', '16');
INSERT INTO `menu` VALUES ('200', '日报汇总表(new)', 'statistics/channelReportNew/total?type=2000&mainView=true', '20');
-- ------------------------- 统计相关
-- ----------------------------
-- Table structure for acu
-- ----------------------------
DROP TABLE IF EXISTS `acu`;
CREATE TABLE `acu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hour_01` int(255) DEFAULT NULL COMMENT '每天的第 1 个小时活跃人数',
  `hour_02` int(255) DEFAULT NULL COMMENT '每天的第 2 个小时活跃人数',
  `hour_03` int(255) DEFAULT NULL COMMENT '每天的第 3 个小时活跃人数',
  `hour_04` int(255) DEFAULT NULL COMMENT '每天的第 4 个小时活跃人数',
  `hour_05` int(255) DEFAULT NULL COMMENT '每天的第 5 个小时活跃人数',
  `hour_06` int(255) DEFAULT NULL COMMENT '每天的第 6 个小时活跃人数',
  `hour_07` int(255) DEFAULT NULL COMMENT '每天的第 7 个小时活跃人数',
  `hour_08` int(255) DEFAULT NULL COMMENT '每天的第 8 个小时活跃人数',
  `hour_09` int(255) DEFAULT NULL COMMENT '每天的第 9 个小时活跃人数',
  `hour_10` int(255) DEFAULT NULL COMMENT '每天的第 10 个小时活跃人数',
  `hour_11` int(255) DEFAULT NULL COMMENT '每天的第 11 个小时活跃人数',
  `hour_12` int(255) DEFAULT NULL COMMENT '每天的第 12 个小时活跃人数',
  `hour_13` int(255) DEFAULT NULL COMMENT '每天的第 13 个小时活跃人数',
  `hour_14` int(255) DEFAULT NULL COMMENT '每天的第 14 个小时活跃人数',
  `hour_15` int(255) DEFAULT NULL COMMENT '每天的第 15 个小时活跃人数',
  `hour_16` int(255) DEFAULT NULL COMMENT '每天的第 16 个小时活跃人数',
  `hour_17` int(255) DEFAULT NULL COMMENT '每天的第 17 个小时活跃人数',
  `hour_18` int(255) DEFAULT NULL COMMENT '每天的第 18 个小时活跃人数',
  `hour_19` int(255) DEFAULT NULL COMMENT '每天的第 19 个小时活跃人数',
  `hour_20` int(255) DEFAULT NULL COMMENT '每天的第 20 个小时活跃人数',
  `hour_21` int(255) DEFAULT NULL COMMENT '每天的第 21 个小时活跃人数',
  `hour_22` int(255) DEFAULT NULL COMMENT '每天的第 22 个小时活跃人数',
  `hour_23` int(255) DEFAULT NULL COMMENT '每天的第 23 个小时活跃人数',
  `hour_24` int(255) DEFAULT NULL COMMENT '每天的第 24 个小时活跃人数',
  `playCount` int(255) DEFAULT NULL COMMENT '活跃人数总统计',
  `server_id` int(255) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `log_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for acu_pcu_summary
-- ----------------------------
DROP TABLE IF EXISTS `acu_pcu_summary`;
CREATE TABLE `acu_pcu_summary` (
  `acu` float(11,0) NOT NULL COMMENT '时间段平均在线人数',
  `pcu` int(11) DEFAULT NULL COMMENT '最高在线的时间段人数',
  `t_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='############## \r\nacu&pcu 统计 \r\nacu 时间段平均在线人数\r\npcu 最高在线的时间段人数';


-- ----------------------------
-- Table structure for crystal_detailed_summary
-- ----------------------------
DROP TABLE IF EXISTS `crystal_detailed_summary`;
CREATE TABLE `crystal_detailed_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL COMMENT '时间',
  `rw_cs` bigint(255) DEFAULT NULL COMMENT '充值次数',
  `rw_sj` bigint(255) DEFAULT NULL COMMENT '充值水晶',
  `dl_cs` bigint(255) DEFAULT NULL COMMENT '活动次数',
  `dl_sj` bigint(255) DEFAULT NULL COMMENT '活动水晶',
  `hd_cs` bigint(255) DEFAULT NULL COMMENT '通关次数',
  `hd_sj` bigint(255) DEFAULT NULL COMMENT '通关水晶',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='# 水晶明细汇总';


-- ----------------------------
-- Table structure for crystal_level
-- ----------------------------
DROP TABLE IF EXISTS `crystal_level`;
CREATE TABLE `crystal_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL COMMENT '创建时间',
  `tOutput` bigint(255) DEFAULT NULL COMMENT '水晶总消耗',
  `tInput` bigint(255) DEFAULT NULL COMMENT '水晶总产出',
  `tKeep` bigint(255) DEFAULT NULL COMMENT '水晶总留存',
  `output1` bigint(255) DEFAULT NULL COMMENT '0~10 水晶消耗',
  `input1` bigint(255) DEFAULT NULL COMMENT '0~10 水晶总产出',
  `keep1` bigint(255) DEFAULT NULL COMMENT '0~10 水晶留存',
  `output2` bigint(255) DEFAULT NULL COMMENT '11~20 水晶消耗',
  `input2` bigint(255) DEFAULT NULL COMMENT '11~20 水晶总产出',
  `keep2` bigint(255) DEFAULT NULL COMMENT '11~20 水晶留存',
  `output3` bigint(255) DEFAULT NULL COMMENT '21~30 水晶消耗',
  `input3` bigint(255) DEFAULT NULL COMMENT '21~30 水晶总产出',
  `keep3` bigint(255) DEFAULT NULL COMMENT '21~30 水晶留存',
  `output4` bigint(255) DEFAULT NULL COMMENT '31~40 水晶消耗',
  `input4` bigint(255) DEFAULT NULL COMMENT '31~40 水晶产出',
  `keep4` bigint(255) DEFAULT NULL COMMENT '31~40 水晶留存',
  `output5` bigint(255) DEFAULT NULL COMMENT '41~50 水晶消耗',
  `input5` bigint(255) DEFAULT NULL COMMENT '41~50 水晶总产出',
  `keep5` bigint(255) DEFAULT NULL COMMENT '41~50 水晶留存',
  `output6` bigint(255) DEFAULT NULL COMMENT '51~60 水晶消耗',
  `input6` bigint(255) DEFAULT NULL COMMENT '51~60 水晶总产出',
  `keep6` bigint(255) DEFAULT NULL COMMENT '51~60 水晶留存',
  `output7` bigint(255) DEFAULT NULL COMMENT '61~70 水晶消耗',
  `input7` bigint(255) DEFAULT NULL COMMENT '69~70 水晶总产出',
  `keep7` bigint(255) DEFAULT NULL COMMENT '61~70 水晶留存',
  `output8` bigint(255) DEFAULT NULL COMMENT '71~80 水晶消耗',
  `input8` bigint(255) DEFAULT NULL COMMENT '71~80 水晶产出',
  `keep8` bigint(255) DEFAULT NULL COMMENT '71~80 水晶留存',
  `output9` bigint(255) DEFAULT NULL COMMENT '80 以上水晶消耗',
  `input9` bigint(255) DEFAULT NULL COMMENT '80 以上水晶总产出',
  `keep9` bigint(255) DEFAULT NULL COMMENT '80 以上留存',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='# 水晶产出&消耗&留存 (等级汇总)';


-- ----------------------------
-- Table structure for crystal_use_summary
-- ----------------------------
DROP TABLE IF EXISTS `crystal_use_summary`;
CREATE TABLE `crystal_use_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL COMMENT '时间',
  `type1` varchar(255) DEFAULT NULL COMMENT '类型 1',
  `type2` varchar(255) DEFAULT NULL COMMENT '类型 2',
  `total` bigint(255) DEFAULT NULL COMMENT '消费总额',
  `isVip` int(255) DEFAULT NULL,
  `rc` bigint(255) DEFAULT NULL COMMENT '人次',
  `rs` bigint(255) DEFAULT NULL COMMENT '人数',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='######### 水晶使用汇总 ########';


-- ----------------------------
-- Table structure for dau_summary
-- ----------------------------
DROP TABLE IF EXISTS `dau_summary`;
CREATE TABLE `dau_summary` (
  `dau` bigint(20) DEFAULT NULL COMMENT '任务id',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `t_time` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='##### 游戏中用户 活跃汇总表';

-- ----------------------------
-- Table structure for f_pay_retention
-- ----------------------------
DROP TABLE IF EXISTS `f_pay_retention`;
CREATE TABLE `f_pay_retention` (
  `create_date` datetime DEFAULT NULL COMMENT '统计日时间',
  `server_id` int(255) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '平台',
  `pay_count01` int(255) DEFAULT NULL COMMENT '付费次日付费人数(今天首付，第二天还登录的)',
  `f_pay_count01` int(255) DEFAULT NULL COMMENT '首付次日付费人数(今天首付，第二天还登录的)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- ----------------------------
-- Table structure for fb_info
-- ----------------------------
DROP TABLE IF EXISTS `fb_info`;
CREATE TABLE `fb_info` (
  `id` bigint(20) NOT NULL,
  `type` int(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fb_info
-- ----------------------------
INSERT INTO `fb_info` VALUES ('110301101', '1', '短裤少年-小阳(尼比市)');
INSERT INTO `fb_info` VALUES ('110301102', '1', '童子军-小军(尼比市)');
INSERT INTO `fb_info` VALUES ('110301103', '1', '童子军-小关(尼比市)');
INSERT INTO `fb_info` VALUES ('110301104', '1', '短裤少年-小影(尼比市)');
INSERT INTO `fb_info` VALUES ('110301105', '1', '捕虫少年-小民(尼比市)');
INSERT INTO `fb_info` VALUES ('110301106', '1', '迷你裙-菜子(尼比市)');
INSERT INTO `fb_info` VALUES ('110301107', '1', '捕虫少年-小明(尼比市)');
INSERT INTO `fb_info` VALUES ('110301109', '1', '迷你裙-小兰(尼比市)');
INSERT INTO `fb_info` VALUES ('110301201', '1', '短裤少年-小凯(7号道路)');
INSERT INTO `fb_info` VALUES ('110301301', '1', '童子军-小波(8号道路)');
INSERT INTO `fb_info` VALUES ('110301302', '1', '捕虫少年-小胜(8号道路)');
INSERT INTO `fb_info` VALUES ('110301401', '1', '短裤少年-小飞(9号道路)');
INSERT INTO `fb_info` VALUES ('110301402', '1', '童子军-小力(9号道路)');
INSERT INTO `fb_info` VALUES ('110301403', '1', '童子军-小强(9号道路)');
INSERT INTO `fb_info` VALUES ('110301501', '1', '迷你裙-小贤(10号道路)');
INSERT INTO `fb_info` VALUES ('110301502', '1', '火箭队(10号道路)');
INSERT INTO `fb_info` VALUES ('110301601', '1', '捕虫少年-小瞳(11号道路)');
INSERT INTO `fb_info` VALUES ('110301602', '1', '短裤少年-小碧(11号道路)');
INSERT INTO `fb_info` VALUES ('110301603', '1', '迷你裙-菜子(11号道路)');
INSERT INTO `fb_info` VALUES ('110301801', '1', '短裤少年-小井(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301802', '1', '迷你裙-小静(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301803', '1', '短裤少年-小彦(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301804', '1', '童子军-小欧(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301805', '1', '童子军-小派(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301806', '1', '捕虫少年-小光(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301807', '1', '迷你裙-小兰(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301808', '1', '迷你裙-向川(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301809', '1', '捕虫少年-小民(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301810', '1', '迷你裙-菜子(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301811', '1', '捕虫少年-小明(华蓝市)');
INSERT INTO `fb_info` VALUES ('110301901', '1', '童子军-小兵(12号道路)');
INSERT INTO `fb_info` VALUES ('110301902', '1', '火箭队(12号道路)');
INSERT INTO `fb_info` VALUES ('110301903', '1', '捕虫少年-小勇(12号道路)');
INSERT INTO `fb_info` VALUES ('110302001', '1', '迷你裙-小欣(13号道路)');
INSERT INTO `fb_info` VALUES ('110302002', '1', '短裤少年-小建(13号道路)');
INSERT INTO `fb_info` VALUES ('110302003', '1', '捕虫少年-铃木(13号道路)');
INSERT INTO `fb_info` VALUES ('110302004', '1', '修炼者(13号道路)');
INSERT INTO `fb_info` VALUES ('110302101', '1', '短裤少年-小武(14号道路)');
INSERT INTO `fb_info` VALUES ('110302102', '1', '捕虫少年-小牧(14号道路)');
INSERT INTO `fb_info` VALUES ('110302103', '1', '火箭队(14号道路)');
INSERT INTO `fb_info` VALUES ('110302104', '1', '童子军-小雨(14号道路)');
INSERT INTO `fb_info` VALUES ('110302201', '1', '捕虫少年-小光(15号道路)');
INSERT INTO `fb_info` VALUES ('110302202', '1', '迷你裙-向川(15号道路)');
INSERT INTO `fb_info` VALUES ('110302203', '1', '短裤少年-小志(15号道路)');
INSERT INTO `fb_info` VALUES ('110302204', '1', '捕虫少年-小明(15号道路)');
INSERT INTO `fb_info` VALUES ('110302205', '1', '迷你裙-小兰(15号道路)');
INSERT INTO `fb_info` VALUES ('110302301', '1', '火箭队(16号道路)');
INSERT INTO `fb_info` VALUES ('110302302', '1', '短裤少年-小奥(16号道路)');
INSERT INTO `fb_info` VALUES ('110302303', '1', '童子军-中村(16号道路)');
INSERT INTO `fb_info` VALUES ('110401103', '1', '迷你裙-嘉山(尼比市道馆)');
INSERT INTO `fb_info` VALUES ('110401104', '1', '短裤少年-小泉(尼比市道馆)');
INSERT INTO `fb_info` VALUES ('110401105', '1', '童子军-川上(尼比市道馆)');
INSERT INTO `fb_info` VALUES ('110401106', '1', '迷你裙-河合(尼比市道馆)');
INSERT INTO `fb_info` VALUES ('110401201', '1', '小茂(7号道路)');
INSERT INTO `fb_info` VALUES ('110401203', '1', '迷你裙-小池(7号道路)');
INSERT INTO `fb_info` VALUES ('110401204', '1', '短裤少年-小业(7号道路)');
INSERT INTO `fb_info` VALUES ('110401205', '1', '捕虫少年-川奈(7号道路)');
INSERT INTO `fb_info` VALUES ('110401206', '1', '迷你裙-小林(7号道路)');
INSERT INTO `fb_info` VALUES ('110401207', '1', '短裤少年-小田(7号道路)');
INSERT INTO `fb_info` VALUES ('110401208', '1', '童子军-小金(7号道路)');
INSERT INTO `fb_info` VALUES ('110401209', '1', '迷你裙-小贵(7号道路)');
INSERT INTO `fb_info` VALUES ('110401302', '1', '捕虫少年-小坂(8号道路)');
INSERT INTO `fb_info` VALUES ('110401303', '1', '迷你裙-古村(8号道路)');
INSERT INTO `fb_info` VALUES ('110401304', '1', '童子军-工藤(8号道路)');
INSERT INTO `fb_info` VALUES ('110401305', '1', '捕虫少年-小岛(8号道路)');
INSERT INTO `fb_info` VALUES ('110401306', '1', '童子军-久保(8号道路)');
INSERT INTO `fb_info` VALUES ('110401307', '1', '短裤少年-北野(8号道路)');
INSERT INTO `fb_info` VALUES ('110401308', '1', '迷你裙-小苍(8号道路)');
INSERT INTO `fb_info` VALUES ('110401401', '1', '捕虫少年-小松(9号道路)');
INSERT INTO `fb_info` VALUES ('110401402', '1', '迷你裙-美奈子(9号道路)');
INSERT INTO `fb_info` VALUES ('110401403', '1', '短裤少年-小冈(9号道路)');
INSERT INTO `fb_info` VALUES ('110401404', '1', '捕虫少年-小林(9号道路)');
INSERT INTO `fb_info` VALUES ('110401405', '1', '迷你裙-丽香(9号道路)');
INSERT INTO `fb_info` VALUES ('110401406', '1', '短裤少年-千明(9号道路)');
INSERT INTO `fb_info` VALUES ('110401407', '1', '童子军-松本(9号道路)');
INSERT INTO `fb_info` VALUES ('110401408', '1', '迷你裙-里子(9号道路)');
INSERT INTO `fb_info` VALUES ('110401409', '1', '捕虫少年-小岭(9号道路)');
INSERT INTO `fb_info` VALUES ('110401501', '1', '火箭队(10号道路)');
INSERT INTO `fb_info` VALUES ('110401502', '1', '短裤少年-增田(10号道路)');
INSERT INTO `fb_info` VALUES ('110401503', '1', '捕虫少年-宫旗(10号道路)');
INSERT INTO `fb_info` VALUES ('110401504', '1', '迷你裙-叶子(10号道路)');
INSERT INTO `fb_info` VALUES ('110401505', '1', '短裤少年-伊田(10号道路)');
INSERT INTO `fb_info` VALUES ('110401506', '1', '童子军-小水(10号道路)');
INSERT INTO `fb_info` VALUES ('110401507', '1', '迷你裙-莎基(10号道路)');
INSERT INTO `fb_info` VALUES ('110401508', '1', '捕虫少年-松万(10号道路)');
INSERT INTO `fb_info` VALUES ('110401509', '1', '短裤少年-小丸(10号道路)');
INSERT INTO `fb_info` VALUES ('110401510', '1', '童子军-小爱(10号道路)');
INSERT INTO `fb_info` VALUES ('110401601', '1', '小茂(11号道路)');
INSERT INTO `fb_info` VALUES ('110401602', '1', '火箭队(11号道路)');
INSERT INTO `fb_info` VALUES ('110401603', '1', '童子军-大辉(11号道路)');
INSERT INTO `fb_info` VALUES ('110401604', '1', '迷你裙-恭子(11号道路)');
INSERT INTO `fb_info` VALUES ('110401605', '1', '短裤少年-一郎(11号道路)');
INSERT INTO `fb_info` VALUES ('110401606', '1', '捕虫少年-小奎(11号道路)');
INSERT INTO `fb_info` VALUES ('110401607', '1', '迷你裙-玲奈(11号道路)');
INSERT INTO `fb_info` VALUES ('110401608', '1', '短裤少年-凉太(11号道路)');
INSERT INTO `fb_info` VALUES ('110401609', '1', '童子军-达也(11号道路)');
INSERT INTO `fb_info` VALUES ('110401610', '1', '迷你裙-朋子(11号道路)');
INSERT INTO `fb_info` VALUES ('110401611', '1', '捕虫少年-七海(11号道路)');
INSERT INTO `fb_info` VALUES ('110401802', '1', '短裤少年-大和(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401803', '1', '捕虫少年-大地(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401804', '1', '迷你裙-小姜(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401805', '1', '短裤少年-小仁(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401806', '1', '童子军-小翼(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401807', '1', '迷你裙-奏太(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401808', '1', '捕虫少年-翔(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401809', '1', '短裤少年-小空(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401810', '1', '童子军-小城(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401811', '1', '小霞(华蓝市道馆)');
INSERT INTO `fb_info` VALUES ('110401901', '1', '火箭队(12号道路)');
INSERT INTO `fb_info` VALUES ('110401902', '1', '童子军-龙(12号道路)');
INSERT INTO `fb_info` VALUES ('110401903', '1', '迷你裙-小薰(12号道路)');
INSERT INTO `fb_info` VALUES ('110401904', '1', '短裤少年-雄太(12号道路)');
INSERT INTO `fb_info` VALUES ('110401905', '1', '捕虫少年-小杰(12号道路)');
INSERT INTO `fb_info` VALUES ('110401906', '1', '迷你裙-阳希(12号道路)');
INSERT INTO `fb_info` VALUES ('110401907', '1', '短裤少年-志也(12号道路)');
INSERT INTO `fb_info` VALUES ('110401908', '1', '童子军-拓也(12号道路)');
INSERT INTO `fb_info` VALUES ('110401909', '1', '迷你裙-小英(12号道路)');
INSERT INTO `fb_info` VALUES ('110401911', '1', '迷你裙-小巧(12号道路)');
INSERT INTO `fb_info` VALUES ('110402001', '1', '短裤少年-小太(13号道路)');
INSERT INTO `fb_info` VALUES ('110402002', '1', '捕虫少年-大浦(13号道路)');
INSERT INTO `fb_info` VALUES ('110402003', '1', '迷你裙-小月(13号道路)');
INSERT INTO `fb_info` VALUES ('110402004', '1', '短裤少年-小路(13号道路)');
INSERT INTO `fb_info` VALUES ('110402005', '1', '童子军-小山田(13号道路)');
INSERT INTO `fb_info` VALUES ('110402006', '1', '迷你裙-花梨(13号道路)');
INSERT INTO `fb_info` VALUES ('110402007', '1', '捕虫少年-小狼(13号道路)');
INSERT INTO `fb_info` VALUES ('110402008', '1', '短裤少年-小斗(13号道路)');
INSERT INTO `fb_info` VALUES ('110402009', '1', '童子军-永井(13号道路)');
INSERT INTO `fb_info` VALUES ('110402101', '1', '火箭队(14号道路)');
INSERT INTO `fb_info` VALUES ('110402102', '1', '迷你裙-原田(14号道路)');
INSERT INTO `fb_info` VALUES ('110402103', '1', '短裤少年-斋腾(14号道路)');
INSERT INTO `fb_info` VALUES ('110402104', '1', '童子军-小松(14号道路)');
INSERT INTO `fb_info` VALUES ('110402105', '1', '迷你裙-小早川(14号道路)');
INSERT INTO `fb_info` VALUES ('110402106', '1', '捕虫少年-小部(14号道路)');
INSERT INTO `fb_info` VALUES ('110402107', '1', '短裤少年-细田(14号道路)');
INSERT INTO `fb_info` VALUES ('110402108', '1', '童子军-小佐(14号道路)');
INSERT INTO `fb_info` VALUES ('110402109', '1', '迷你裙-小见(14号道路)');
INSERT INTO `fb_info` VALUES ('110402110', '1', '短裤少年-小家(14号道路)');
INSERT INTO `fb_info` VALUES ('110402201', '1', '童子军-小夕(15号道路)');
INSERT INTO `fb_info` VALUES ('110402202', '1', '迷你裙-小光(15号道路)');
INSERT INTO `fb_info` VALUES ('110402203', '1', '短裤少年-小景(15号道路)');
INSERT INTO `fb_info` VALUES ('110402204', '1', '捕虫少年-小铭(15号道路)');
INSERT INTO `fb_info` VALUES ('110402205', '1', '迷你裙-铃木(15号道路)');
INSERT INTO `fb_info` VALUES ('110402206', '1', '短裤少年-小亚(15号道路)');
INSERT INTO `fb_info` VALUES ('110402207', '1', '童子军-小响(15号道路)');
INSERT INTO `fb_info` VALUES ('110402208', '1', '迷你裙-小川(15号道路)');
INSERT INTO `fb_info` VALUES ('110402209', '1', '捕虫少年-小野(15号道路)');
INSERT INTO `fb_info` VALUES ('110402301', '1', '短裤少年-小菲(16号道路)');
INSERT INTO `fb_info` VALUES ('110402302', '1', '捕虫少年-景天(16号道路)');
INSERT INTO `fb_info` VALUES ('110402303', '1', '迷你裙-小邱(16号道路)');
INSERT INTO `fb_info` VALUES ('110402304', '1', '短裤少年-加藤(16号道路)');
INSERT INTO `fb_info` VALUES ('110402305', '1', '童子军-小龙(16号道路)');
INSERT INTO `fb_info` VALUES ('110402306', '1', '迷你裙-川田(16号道路)');
INSERT INTO `fb_info` VALUES ('110402307', '1', '捕虫少年-高野(16号道路)');
INSERT INTO `fb_info` VALUES ('110402308', '1', '短裤少年-山口(16号道路)');
INSERT INTO `fb_info` VALUES ('110402309', '1', '童子军-力才(16号道路)');


-- ----------------------------
-- Table structure for fb_summary
-- ----------------------------
DROP TABLE IF EXISTS `fb_summary`;
CREATE TABLE `fb_summary` (
  `fb_id` bigint(20) DEFAULT NULL COMMENT '副本id',
  `t_time` datetime DEFAULT NULL COMMENT '创建时间',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `tz_player` bigint(255) DEFAULT NULL COMMENT '挑战玩家数',
  `tz_num` bigint(20) DEFAULT NULL COMMENT '挑战次数',
  `tz_win_player` bigint(255) DEFAULT NULL COMMENT '挑战完成玩家数',
  `tz_win_player_cs` bigint(255) DEFAULT NULL COMMENT '挑战完成次数'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='# 副本挑战汇总\r\n';

-- ----------------------------
-- Table structure for game_login_user_cache
-- ----------------------------
DROP TABLE IF EXISTS `game_login_user_cache`;
CREATE TABLE `game_login_user_cache` (
  `account_id` bigint(20) DEFAULT NULL,
  `server_id` int(11) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='############### 登录游戏帐号 缓存表';


-- ----------------------------
-- Table structure for game_online_user_cache
-- ----------------------------
DROP TABLE IF EXISTS `game_online_user_cache`;
CREATE TABLE `game_online_user_cache` (
  `t_time` datetime DEFAULT NULL COMMENT '统计时间',
  `num` int(11) DEFAULT NULL COMMENT '在线人数',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='############## \r\n用户在线用户缓存 标记同帐号,上线下线时间';


-- ----------------------------
-- Table structure for game_per_hour_online_summary
-- ----------------------------
DROP TABLE IF EXISTS `game_per_hour_online_summary`;
CREATE TABLE `game_per_hour_online_summary` (
  `hour_01` int(255) DEFAULT NULL COMMENT '每天的第 1 个小时活跃人数',
  `hour_02` int(255) DEFAULT NULL COMMENT '每天的第 2 个小时活跃人数',
  `hour_03` int(255) DEFAULT NULL COMMENT '每天的第 3 个小时活跃人数',
  `hour_04` int(255) DEFAULT NULL COMMENT '每天的第 4 个小时活跃人数',
  `hour_05` int(255) DEFAULT NULL COMMENT '每天的第 5 个小时活跃人数',
  `hour_06` int(255) DEFAULT NULL COMMENT '每天的第 6 个小时活跃人数',
  `hour_07` int(255) DEFAULT NULL COMMENT '每天的第 7 个小时活跃人数',
  `hour_08` int(255) DEFAULT NULL COMMENT '每天的第 8 个小时活跃人数',
  `hour_09` int(255) DEFAULT NULL COMMENT '每天的第 9 个小时活跃人数',
  `hour_10` int(255) DEFAULT NULL COMMENT '每天的第 10 个小时活跃人数',
  `hour_11` int(255) DEFAULT NULL COMMENT '每天的第 11 个小时活跃人数',
  `hour_12` int(255) DEFAULT NULL COMMENT '每天的第 12 个小时活跃人数',
  `hour_13` int(255) DEFAULT NULL COMMENT '每天的第 13 个小时活跃人数',
  `hour_14` int(255) DEFAULT NULL COMMENT '每天的第 14 个小时活跃人数',
  `hour_15` int(255) DEFAULT NULL COMMENT '每天的第 15 个小时活跃人数',
  `hour_16` int(255) DEFAULT NULL COMMENT '每天的第 16 个小时活跃人数',
  `hour_17` int(255) DEFAULT NULL COMMENT '每天的第 17 个小时活跃人数',
  `hour_18` int(255) DEFAULT NULL COMMENT '每天的第 18 个小时活跃人数',
  `hour_19` int(255) DEFAULT NULL COMMENT '每天的第 19 个小时活跃人数',
  `hour_20` int(255) DEFAULT NULL COMMENT '每天的第 20 个小时活跃人数',
  `hour_21` int(255) DEFAULT NULL COMMENT '每天的第 21 个小时活跃人数',
  `hour_22` int(255) DEFAULT NULL COMMENT '每天的第 22 个小时活跃人数',
  `hour_23` int(255) DEFAULT NULL COMMENT '每天的第 23 个小时活跃人数',
  `hour_24` int(255) DEFAULT NULL COMMENT '每天的第 24 个小时活跃人数',
  `server_id` int(255) DEFAULT NULL COMMENT '服务器id',
  `log_time` datetime DEFAULT NULL COMMENT '登录时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for game_valid_user_cache
-- ----------------------------
DROP TABLE IF EXISTS `game_valid_user_cache`;
CREATE TABLE `game_valid_user_cache` (
  `account_id` bigint(20) DEFAULT NULL COMMENT '帐号id',
  `server_id` int(11) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='###########有效用户表  缓存表，用完就会删掉数据';


-- ----------------------------
-- Table structure for guide_info
-- ----------------------------
DROP TABLE IF EXISTS `guide_info`;
CREATE TABLE `guide_info` (
  `id` bigint(20) NOT NULL COMMENT '引导ID',
  `name` varchar(255) DEFAULT NULL COMMENT '引导名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='######### 新手引导  ########';

-- ----------------------------
-- Records of guide_info
-- ----------------------------
INSERT INTO `guide_info` VALUES ('800200001', '点击“自动寻路”按钮');
INSERT INTO `guide_info` VALUES ('800200002', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200003', '打开扭蛋界面');
INSERT INTO `guide_info` VALUES ('800200004', '进行单次金币抽取');
INSERT INTO `guide_info` VALUES ('800200005', '关闭金币抽取界面');
INSERT INTO `guide_info` VALUES ('800200006', '进行单次水晶抽取');
INSERT INTO `guide_info` VALUES ('800200007', '点击精灵获得确认按钮');
INSERT INTO `guide_info` VALUES ('800200008', '关闭水晶抽取界面');
INSERT INTO `guide_info` VALUES ('800200009', '关闭扭蛋机界面');
INSERT INTO `guide_info` VALUES ('800200010', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200011', '打开精灵中心界面');
INSERT INTO `guide_info` VALUES ('800200012', '选择1号栏位精灵');
INSERT INTO `guide_info` VALUES ('800200013', '点击放入背包按钮');
INSERT INTO `guide_info` VALUES ('800200014', '关闭精灵中心界面');
INSERT INTO `guide_info` VALUES ('800200015', '点击“自动寻路”按钮');
INSERT INTO `guide_info` VALUES ('800200016', '点击技能标签页');
INSERT INTO `guide_info` VALUES ('800200017', '点击精灵标签页');
INSERT INTO `guide_info` VALUES ('800200018', '点击道具标签页');
INSERT INTO `guide_info` VALUES ('800200019', '点击技能标签页');
INSERT INTO `guide_info` VALUES ('800200020', '选择第一个技能');
INSERT INTO `guide_info` VALUES ('800200021', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200022', '选择精灵界面');
INSERT INTO `guide_info` VALUES ('800200023', '进行精灵恢复操作');
INSERT INTO `guide_info` VALUES ('800200024', '设置跟随');
INSERT INTO `guide_info` VALUES ('800200025', '关闭窗口');
INSERT INTO `guide_info` VALUES ('800200026', '点击“自动寻路”按钮');
INSERT INTO `guide_info` VALUES ('800200027', '点击屏幕移动到草丛区域');
INSERT INTO `guide_info` VALUES ('800200028', '选择第一个技能');
INSERT INTO `guide_info` VALUES ('800200029', '点击道具标签页');
INSERT INTO `guide_info` VALUES ('800200030', '选择精灵球类别');
INSERT INTO `guide_info` VALUES ('800200031', '使用精灵球');
INSERT INTO `guide_info` VALUES ('800200032', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200033', '选择1号精灵');
INSERT INTO `guide_info` VALUES ('800200034', '点击升级按钮');
INSERT INTO `guide_info` VALUES ('800200035', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200036', '选择1号精灵');
INSERT INTO `guide_info` VALUES ('800200037', '点击突破标签');
INSERT INTO `guide_info` VALUES ('800200038', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200039', '选择1号精灵');
INSERT INTO `guide_info` VALUES ('800200040', '点击进化标签');
INSERT INTO `guide_info` VALUES ('800200041', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200042', '选择1号精灵');
INSERT INTO `guide_info` VALUES ('800200043', '点击强化标签');
INSERT INTO `guide_info` VALUES ('800200044', '点击小地图');
INSERT INTO `guide_info` VALUES ('800200045', '点击区域图鉴');
INSERT INTO `guide_info` VALUES ('800200046', '点击图鉴奖励');
INSERT INTO `guide_info` VALUES ('800200047', '点击小地图');
INSERT INTO `guide_info` VALUES ('800200048', '点击查看世界地图');
INSERT INTO `guide_info` VALUES ('800200049', '点击传送点');
INSERT INTO `guide_info` VALUES ('800200050', '点击“自动寻路”按钮');
INSERT INTO `guide_info` VALUES ('800200051', '打开左侧隐藏菜单');
INSERT INTO `guide_info` VALUES ('800200052', '点击训练家面板');
INSERT INTO `guide_info` VALUES ('800200053', '打开左侧隐藏菜单');
INSERT INTO `guide_info` VALUES ('800200054', '点击角色面板');
INSERT INTO `guide_info` VALUES ('800200055', '选择徽章界面');
INSERT INTO `guide_info` VALUES ('800200056', '选择道馆类别');
INSERT INTO `guide_info` VALUES ('800200057', '关闭界面');
INSERT INTO `guide_info` VALUES ('800200058', '打开任务界面');
INSERT INTO `guide_info` VALUES ('800200059', '选择日常任务');
INSERT INTO `guide_info` VALUES ('800200060', '回城');
INSERT INTO `guide_info` VALUES ('800200061', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200062', '打开活动面板');
INSERT INTO `guide_info` VALUES ('800200063', '打开非实时竞技场界面');
INSERT INTO `guide_info` VALUES ('800200064', '回城');
INSERT INTO `guide_info` VALUES ('800200065', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200066', '打开活动面板');
INSERT INTO `guide_info` VALUES ('800200067', '打开狩猎场');
INSERT INTO `guide_info` VALUES ('800200068', '回城');
INSERT INTO `guide_info` VALUES ('800200069', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200070', '打开活动面板');
INSERT INTO `guide_info` VALUES ('800200071', '打开联盟大赛');
INSERT INTO `guide_info` VALUES ('800200072', '回城');
INSERT INTO `guide_info` VALUES ('800200073', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200074', '打开活动面板');
INSERT INTO `guide_info` VALUES ('800200075', '打开四大天王');
INSERT INTO `guide_info` VALUES ('800200076', '回城');
INSERT INTO `guide_info` VALUES ('800200077', '打开通用菜单');
INSERT INTO `guide_info` VALUES ('800200078', '打开交换界面');

-- ----------------------------
-- Table structure for guide_summary
-- ----------------------------
DROP TABLE IF EXISTS `guide_summary`;
CREATE TABLE `guide_summary` (
  `guide_id` bigint(20) DEFAULT NULL COMMENT '通过的引导id',
  `t_time` datetime DEFAULT NULL COMMENT '创建时间',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `sum_player` bigint(255) DEFAULT NULL COMMENT '玩家数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) DEFAULT NULL COMMENT '物品名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('100000001', '口袋金币');
INSERT INTO `item` VALUES ('100000002', '口袋水晶');
INSERT INTO `item` VALUES ('100000003', '训练师挑战券');
INSERT INTO `item` VALUES ('100000004', '训练师EXP');
INSERT INTO `item` VALUES ('100000005', '口袋精灵EXP');
INSERT INTO `item` VALUES ('100000006', '精灵背包容量');
INSERT INTO `item` VALUES ('100000007', '疲劳度');
INSERT INTO `item` VALUES ('100000008', '对战中心点券');
INSERT INTO `item` VALUES ('100000009', '友情值');
INSERT INTO `item` VALUES ('100000010', '成就点');
INSERT INTO `item` VALUES ('100000011', '公会之证');
INSERT INTO `item` VALUES ('100000012', '联盟商店奖章');
INSERT INTO `item` VALUES ('100000013', '改名卡');
INSERT INTO `item` VALUES ('100000014', '亲密度');
INSERT INTO `item` VALUES ('100000015', '精灵纹章');
INSERT INTO `item` VALUES ('100200001', '宝贝球');
INSERT INTO `item` VALUES ('100200002', '超级球');
INSERT INTO `item` VALUES ('100200003', '高级球');
INSERT INTO `item` VALUES ('100200004', '大师球');
INSERT INTO `item` VALUES ('100200005', '宝贝球');
INSERT INTO `item` VALUES ('100200006', '一般球');
INSERT INTO `item` VALUES ('100200007', '格斗球');
INSERT INTO `item` VALUES ('100200008', '飞行球');
INSERT INTO `item` VALUES ('100200009', '毒气球');
INSERT INTO `item` VALUES ('100200010', '大地球');
INSERT INTO `item` VALUES ('100200011', '岩石球');
INSERT INTO `item` VALUES ('100200012', '捕虫球');
INSERT INTO `item` VALUES ('100200013', '幽灵球');
INSERT INTO `item` VALUES ('100200014', '钢铁球');
INSERT INTO `item` VALUES ('100200015', '火焰球');
INSERT INTO `item` VALUES ('100200016', '潜水球');
INSERT INTO `item` VALUES ('100200017', '飞叶球');
INSERT INTO `item` VALUES ('100200018', '雷电球');
INSERT INTO `item` VALUES ('100200019', '超能球');
INSERT INTO `item` VALUES ('100200020', '急冻球');
INSERT INTO `item` VALUES ('100200021', '龙穴球');
INSERT INTO `item` VALUES ('100200022', '黑暗球');
INSERT INTO `item` VALUES ('100200023', '妖精球');
INSERT INTO `item` VALUES ('100300001', '《一字切》学习机');
INSERT INTO `item` VALUES ('100300002', '《闪光》学习机');
INSERT INTO `item` VALUES ('100300003', '《冲浪》学习机');
INSERT INTO `item` VALUES ('100300004', '《百万吨拳击》学习机');
INSERT INTO `item` VALUES ('100300005', '《旋风刀》学习机');
INSERT INTO `item` VALUES ('100300006', '《剑舞》学习机');
INSERT INTO `item` VALUES ('100300007', '《旋风》学习机');
INSERT INTO `item` VALUES ('100300008', '《百万吨飞腿》学习机');
INSERT INTO `item` VALUES ('100300009', '《猛毒素》学习机');
INSERT INTO `item` VALUES ('100300010', '《独角钻》学习机');
INSERT INTO `item` VALUES ('100300011', '《泰山压顶》学习机');
INSERT INTO `item` VALUES ('100300012', '《猛撞》学习机');
INSERT INTO `item` VALUES ('100300013', '《舍身攻击》学习机');
INSERT INTO `item` VALUES ('100300014', '《泡沫光线》学习机');
INSERT INTO `item` VALUES ('100300015', '《水枪》学习机');
INSERT INTO `item` VALUES ('100300016', '《急冻光线》学习机');
INSERT INTO `item` VALUES ('100300017', '《暴风雪》学习机');
INSERT INTO `item` VALUES ('100300018', '《破坏死光》学习机');
INSERT INTO `item` VALUES ('100300019', '《聚宝功》学习机');
INSERT INTO `item` VALUES ('100300020', '《地狱滚动》学习机');
INSERT INTO `item` VALUES ('100300021', '《返拳》学习机');
INSERT INTO `item` VALUES ('100300022', '《地球上投》学习机');
INSERT INTO `item` VALUES ('100300023', '《愤怒》学习机');
INSERT INTO `item` VALUES ('100300024', '《百万吨吸收》学习机');
INSERT INTO `item` VALUES ('100300025', '《阳光烈焰》学习机');
INSERT INTO `item` VALUES ('100300026', '《龙之怒》学习机');
INSERT INTO `item` VALUES ('100300027', '《十万伏特》学习机');
INSERT INTO `item` VALUES ('100300028', '《打雷》学��机');
INSERT INTO `item` VALUES ('100300029', '《地震》学习机');
INSERT INTO `item` VALUES ('100300030', '《地裂》学习机');
INSERT INTO `item` VALUES ('100300031', '《挖地洞》学习机');
INSERT INTO `item` VALUES ('100300032', '《幻象术》学习机');
INSERT INTO `item` VALUES ('100300033', '《瞬间移动》学习机');
INSERT INTO `item` VALUES ('100300034', '《模仿》学习机');
INSERT INTO `item` VALUES ('100300035', '《影子分身》学习机');
INSERT INTO `item` VALUES ('100300036', '《减半反射》学习机');
INSERT INTO `item` VALUES ('100300037', '《忍忍》学习机');
INSERT INTO `item` VALUES ('100300038', '《挥指功》学习机');
INSERT INTO `item` VALUES ('100300039', '《自爆》学习机');
INSERT INTO `item` VALUES ('100300040', '《炸蛋》学习机');
INSERT INTO `item` VALUES ('100300041', '《大字爆》学习机');
INSERT INTO `item` VALUES ('100300042', '《高速星星》学习机');
INSERT INTO `item` VALUES ('100300043', '《火箭头槌》学习机');
INSERT INTO `item` VALUES ('100300044', '《生蛋》学习机');
INSERT INTO `item` VALUES ('100300045', '《食梦》学习机');
INSERT INTO `item` VALUES ('100300046', '《高空攻击》学习机');
INSERT INTO `item` VALUES ('100300047', '《睡觉》学习机');
INSERT INTO `item` VALUES ('100300048', '《电磁波》学习机');
INSERT INTO `item` VALUES ('100300049', '《幻象波》学习机');
INSERT INTO `item` VALUES ('100300050', '《大爆炸》学习机');
INSERT INTO `item` VALUES ('100300051', '《山崩地裂》学习机');
INSERT INTO `item` VALUES ('100300052', '《三角攻击》学习机');
INSERT INTO `item` VALUES ('100300053', '《替身》学习机');
INSERT INTO `item` VALUES ('100300054', '《百万吨拳击》学习机碎片');
INSERT INTO `item` VALUES ('100300055', '《旋风刀》学习机碎片');
INSERT INTO `item` VALUES ('100300056', '《剑舞》学习机碎片');
INSERT INTO `item` VALUES ('100300057', '《旋风》学习机碎片');
INSERT INTO `item` VALUES ('100300058', '《百万吨飞腿》学习机碎片');
INSERT INTO `item` VALUES ('100300059', '《猛毒素》学习机碎片');
INSERT INTO `item` VALUES ('100300060', '《独角钻》学习机碎片');
INSERT INTO `item` VALUES ('100300061', '《泰山压顶》学习机碎片');
INSERT INTO `item` VALUES ('100300062', '《猛撞》学习机碎片');
INSERT INTO `item` VALUES ('100300063', '《舍身攻击》学习机碎片');
INSERT INTO `item` VALUES ('100300064', '《泡沫光线》学习机碎片');
INSERT INTO `item` VALUES ('100300065', '《水枪》学习机碎片');
INSERT INTO `item` VALUES ('100300066', '《急冻光线》学习机碎片');
INSERT INTO `item` VALUES ('100300067', '《暴风雪》学习机碎片');
INSERT INTO `item` VALUES ('100300068', '《破坏死光》学习机碎片');
INSERT INTO `item` VALUES ('100300069', '《聚宝功》学习机碎片');
INSERT INTO `item` VALUES ('100300070', '《地狱滚动》学习机碎片');
INSERT INTO `item` VALUES ('100300071', '《返拳》学习机碎片');
INSERT INTO `item` VALUES ('100300072', '《地球上投》学习机碎片');
INSERT INTO `item` VALUES ('100300073', '《愤怒》学习机碎片');
INSERT INTO `item` VALUES ('100300074', '《百万吨吸收》学习机碎片');
INSERT INTO `item` VALUES ('100300075', '《阳光烈焰》学习机碎片');
INSERT INTO `item` VALUES ('100300076', '《龙之怒》学习机碎片');
INSERT INTO `item` VALUES ('100300077', '《十万伏特》学习机碎片');
INSERT INTO `item` VALUES ('100300078', '《打雷》学习机碎片');
INSERT INTO `item` VALUES ('100300079', '《地震》学习机碎片');
INSERT INTO `item` VALUES ('100300080', '《地裂》学习机碎片');
INSERT INTO `item` VALUES ('100300081', '《挖地洞》学习机碎片');
INSERT INTO `item` VALUES ('100300082', '《幻象术》学习机碎片');
INSERT INTO `item` VALUES ('100300083', '《瞬间移动》学习机碎片');
INSERT INTO `item` VALUES ('100300084', '《模仿》学习机碎片');
INSERT INTO `item` VALUES ('100300085', '《影子分身》学习机碎片');
INSERT INTO `item` VALUES ('100300086', '《减半反射》学习机碎片');
INSERT INTO `item` VALUES ('100300087', '《忍忍》学习机碎片');
INSERT INTO `item` VALUES ('100300088', '《挥指功》学习机碎片');
INSERT INTO `item` VALUES ('100300089', '《自爆》学习机碎片');
INSERT INTO `item` VALUES ('100300090', '《炸蛋》学习机碎片');
INSERT INTO `item` VALUES ('100300091', '《大字爆》学习机碎片');
INSERT INTO `item` VALUES ('100300092', '《高速星星》学习机碎片');
INSERT INTO `item` VALUES ('100300093', '《火箭头槌》学习机碎片');
INSERT INTO `item` VALUES ('100300094', '《生蛋》学习机碎片');
INSERT INTO `item` VALUES ('100300095', '《食梦》学习机碎片');
INSERT INTO `item` VALUES ('100300096', '《高空攻击》学习机碎片');
INSERT INTO `item` VALUES ('100300097', '《睡觉》学习机碎片');
INSERT INTO `item` VALUES ('100300098', '《电磁波》学习机碎片');
INSERT INTO `item` VALUES ('100300099', '《幻象波》学习机碎片');
INSERT INTO `item` VALUES ('100300100', '《大爆炸》学习机碎片');
INSERT INTO `item` VALUES ('100300101', '《山崩地裂》学习机碎片');
INSERT INTO `item` VALUES ('100300102', '《三角攻击》学习机碎片');
INSERT INTO `item` VALUES ('100300103', '《替身》学习机碎片');
INSERT INTO `item` VALUES ('100300104', '《劲力》学习机');
INSERT INTO `item` VALUES ('100300105', '《飞翔》学习机');
INSERT INTO `item` VALUES ('100300106', '《飞翔》学习机碎片');
INSERT INTO `item` VALUES ('100300107', '《鱼跃龙门》学习机');
INSERT INTO `item` VALUES ('100300108', '《鱼跃龙门》学习机碎片');
INSERT INTO `item` VALUES ('100300109', '《刀背击打》学习机');
INSERT INTO `item` VALUES ('100300110', '《刀背击打》学习机碎片');
INSERT INTO `item` VALUES ('100400001', '美味之水');
INSERT INTO `item` VALUES ('100400002', '塞可汽水');
INSERT INTO `item` VALUES ('100400003', 'PP小补剂');
INSERT INTO `item` VALUES ('100400004', 'PP大补剂');
INSERT INTO `item` VALUES ('100400005', 'PP全补剂');
INSERT INTO `item` VALUES ('100400006', '解毒剂');
INSERT INTO `item` VALUES ('100400007', '灼伤药');
INSERT INTO `item` VALUES ('100400008', '解眠药');
INSERT INTO `item` VALUES ('100400009', '解麻药');
INSERT INTO `item` VALUES ('100400010', '解冻药');
INSERT INTO `item` VALUES ('100400011', '万能药');
INSERT INTO `item` VALUES ('100400012', '黄金的果实');
INSERT INTO `item` VALUES ('100400013', '麻痹果实');
INSERT INTO `item` VALUES ('100400014', '烧烤果实');
INSERT INTO `item` VALUES ('100400015', '冰冻果实');
INSERT INTO `item` VALUES ('100400016', '解毒果实');
INSERT INTO `item` VALUES ('100400017', '薄荷果实');
INSERT INTO `item` VALUES ('100400018', '神奇果实');
INSERT INTO `item` VALUES ('100500001', '炎之石');
INSERT INTO `item` VALUES ('100500002', '水之石');
INSERT INTO `item` VALUES ('100500003', '草之石');
INSERT INTO `item` VALUES ('100500004', '电之石');
INSERT INTO `item` VALUES ('100500005', '岩之石');
INSERT INTO `item` VALUES ('100500006', '龙之石');
INSERT INTO `item` VALUES ('100500007', '飞行石');
INSERT INTO `item` VALUES ('100500008', '幽灵石');
INSERT INTO `item` VALUES ('100500009', '超能石');
INSERT INTO `item` VALUES ('100500010', '一般石');
INSERT INTO `item` VALUES ('100500011', '虫之石');
INSERT INTO `item` VALUES ('100500012', '格斗石');
INSERT INTO `item` VALUES ('100500013', '剧毒石');
INSERT INTO `item` VALUES ('100500014', '冰之石');
INSERT INTO `item` VALUES ('100500015', '钢之石');
INSERT INTO `item` VALUES ('100500016', '恶之石');
INSERT INTO `item` VALUES ('100500017', '地表石');
INSERT INTO `item` VALUES ('100500018', '妖精石');
INSERT INTO `item` VALUES ('100500019', '炎之石(碎片)');
INSERT INTO `item` VALUES ('100500020', '水之石(碎片)');
INSERT INTO `item` VALUES ('100500021', '草之石(碎片)');
INSERT INTO `item` VALUES ('100500022', '电之石(碎片)');
INSERT INTO `item` VALUES ('100500023', '岩之石(碎片)');
INSERT INTO `item` VALUES ('100500024', '龙之石(碎片)');
INSERT INTO `item` VALUES ('100500025', '飞行石(碎片)');
INSERT INTO `item` VALUES ('100500026', '幽灵石(碎片)');
INSERT INTO `item` VALUES ('100500027', '超能石(碎片)');
INSERT INTO `item` VALUES ('100500028', '一般石(碎片)');
INSERT INTO `item` VALUES ('100500029', '虫之石(碎片)');
INSERT INTO `item` VALUES ('100500030', '格斗石(碎片)');
INSERT INTO `item` VALUES ('100500031', '剧毒石(碎片)');
INSERT INTO `item` VALUES ('100500032', '冰之石(碎片)');
INSERT INTO `item` VALUES ('100500033', '钢之石(碎片)');
INSERT INTO `item` VALUES ('100500034', '恶之石(碎片)');
INSERT INTO `item` VALUES ('100500035', '地表石(碎片)');
INSERT INTO `item` VALUES ('100500036', '妖精石(碎片)');
INSERT INTO `item` VALUES ('100500037', '炎之弹珠（小）');
INSERT INTO `item` VALUES ('100500038', '水之弹珠（小）');
INSERT INTO `item` VALUES ('100500039', '草之弹珠（小）');
INSERT INTO `item` VALUES ('100500040', '电之弹珠（小）');
INSERT INTO `item` VALUES ('100500041', '岩之弹珠（小）');
INSERT INTO `item` VALUES ('100500042', '龙之弹珠（小）');
INSERT INTO `item` VALUES ('100500043', '飞行弹珠（小）');
INSERT INTO `item` VALUES ('100500044', '幽灵弹珠（小）');
INSERT INTO `item` VALUES ('100500045', '超能弹珠（小）');
INSERT INTO `item` VALUES ('100500046', '一般弹珠（小）');
INSERT INTO `item` VALUES ('100500047', '虫之弹珠（小）');
INSERT INTO `item` VALUES ('100500048', '格斗弹珠（小）');
INSERT INTO `item` VALUES ('100500049', '剧毒弹珠（小）');
INSERT INTO `item` VALUES ('100500050', '冰之弹珠（小）');
INSERT INTO `item` VALUES ('100500051', '钢之弹珠（小）');
INSERT INTO `item` VALUES ('100500052', '恶之弹珠（小）');
INSERT INTO `item` VALUES ('100500053', '地表弹珠（小）');
INSERT INTO `item` VALUES ('100500054', '妖精弹珠（小）');
INSERT INTO `item` VALUES ('100500055', '炎之弹珠（中）');
INSERT INTO `item` VALUES ('100500056', '水之弹珠（中）');
INSERT INTO `item` VALUES ('100500057', '草之弹珠（中）');
INSERT INTO `item` VALUES ('100500058', '电之弹珠（中）');
INSERT INTO `item` VALUES ('100500059', '岩之弹珠（中）');
INSERT INTO `item` VALUES ('100500060', '龙之弹珠（中）');
INSERT INTO `item` VALUES ('100500061', '飞行弹珠（中）');
INSERT INTO `item` VALUES ('100500062', '幽灵弹珠（中）');
INSERT INTO `item` VALUES ('100500063', '超能弹珠（中）');
INSERT INTO `item` VALUES ('100500064', '一般弹珠（中）');
INSERT INTO `item` VALUES ('100500065', '虫之弹珠（中）');
INSERT INTO `item` VALUES ('100500066', '格斗弹珠（中）');
INSERT INTO `item` VALUES ('100500067', '剧毒弹珠（中）');
INSERT INTO `item` VALUES ('100500068', '冰之弹珠（中）');
INSERT INTO `item` VALUES ('100500069', '钢之弹珠（中）');
INSERT INTO `item` VALUES ('100500070', '恶之弹珠（中）');
INSERT INTO `item` VALUES ('100500071', '地表弹珠（中）');
INSERT INTO `item` VALUES ('100500072', '妖精弹珠（中）');
INSERT INTO `item` VALUES ('100500073', '炎之弹珠（大）');
INSERT INTO `item` VALUES ('100500074', '水之弹珠（大）');
INSERT INTO `item` VALUES ('100500075', '草之弹珠（大）');
INSERT INTO `item` VALUES ('100500076', '电之弹珠（大）');
INSERT INTO `item` VALUES ('100500077', '岩之弹珠（大）');
INSERT INTO `item` VALUES ('100500078', '龙之弹珠（大）');
INSERT INTO `item` VALUES ('100500079', '飞行弹珠（大）');
INSERT INTO `item` VALUES ('100500080', '幽灵弹珠（大）');
INSERT INTO `item` VALUES ('100500081', '超能弹珠（大）');
INSERT INTO `item` VALUES ('100500082', '一般弹珠（大）');
INSERT INTO `item` VALUES ('100500083', '虫之弹珠（大）');
INSERT INTO `item` VALUES ('100500084', '格斗弹珠（大）');
INSERT INTO `item` VALUES ('100500085', '剧毒弹珠（大）');
INSERT INTO `item` VALUES ('100500086', '冰之弹珠（大）');
INSERT INTO `item` VALUES ('100500087', '钢之弹珠（大）');
INSERT INTO `item` VALUES ('100500088', '恶之弹珠（大）');
INSERT INTO `item` VALUES ('100500089', '地表弹珠（大）');
INSERT INTO `item` VALUES ('100500090', '妖精弹珠（大）');
INSERT INTO `item` VALUES ('100500091', '皮丘绘本');
INSERT INTO `item` VALUES ('100500092', '小火龙绘本');
INSERT INTO `item` VALUES ('100500093', '杰尼龟绘本');
INSERT INTO `item` VALUES ('100500094', '妙蛙种子绘本');
INSERT INTO `item` VALUES ('100500095', '波波绘本');
INSERT INTO `item` VALUES ('100500096', '绿毛虫绘本');
INSERT INTO `item` VALUES ('100500097', '独角虫绘本');
INSERT INTO `item` VALUES ('100500098', '尼多兰绘本');
INSERT INTO `item` VALUES ('100500099', '尼多朗绘本');
INSERT INTO `item` VALUES ('100500100', '喇叭芽绘本');
INSERT INTO `item` VALUES ('100500101', '小拳石绘本');
INSERT INTO `item` VALUES ('100500102', '鬼斯绘本');
INSERT INTO `item` VALUES ('100500103', '鲤鱼王绘本');
INSERT INTO `item` VALUES ('100500104', '喵喵绘本');
INSERT INTO `item` VALUES ('100500105', '可达鸭绘本');
INSERT INTO `item` VALUES ('100500106', '阿柏蛇绘本');
INSERT INTO `item` VALUES ('100500107', '六尾绘本');
INSERT INTO `item` VALUES ('100500108', '瓦斯弹绘本');
INSERT INTO `item` VALUES ('100500109', '穿山鼠绘本');
INSERT INTO `item` VALUES ('100500110', '小拉达绘本');
INSERT INTO `item` VALUES ('100500111', '烈雀绘本');
INSERT INTO `item` VALUES ('100500112', '角金鱼绘本');
INSERT INTO `item` VALUES ('100500113', '小海狮绘本');
INSERT INTO `item` VALUES ('100500114', '火球鼠绘本');
INSERT INTO `item` VALUES ('100500115', '皮卡丘绘本');
INSERT INTO `item` VALUES ('100500116', '火恐龙绘本');
INSERT INTO `item` VALUES ('100500117', '卡咪龟绘本');
INSERT INTO `item` VALUES ('100500118', '妙蛙草绘本');
INSERT INTO `item` VALUES ('100500119', '比比鸟绘本');
INSERT INTO `item` VALUES ('100500120', '铁甲蛹绘本');
INSERT INTO `item` VALUES ('100500121', '铁壳昆绘本');
INSERT INTO `item` VALUES ('100500122', '尼多娜绘本');
INSERT INTO `item` VALUES ('100500123', '尼多力诺绘本');
INSERT INTO `item` VALUES ('100500124', '口呆花绘本');
INSERT INTO `item` VALUES ('100500125', '隆隆石绘本');
INSERT INTO `item` VALUES ('100500126', '鬼斯通绘本');
INSERT INTO `item` VALUES ('100500127', '暴鲤龙 绘本');
INSERT INTO `item` VALUES ('100500128', '猫老大绘本');
INSERT INTO `item` VALUES ('100500129', '哥达鸭绘本');
INSERT INTO `item` VALUES ('100500130', '阿柏怪绘本');
INSERT INTO `item` VALUES ('100500131', '九尾绘本');
INSERT INTO `item` VALUES ('100500132', '双弹瓦斯绘本');
INSERT INTO `item` VALUES ('100500133', '穿山王绘本');
INSERT INTO `item` VALUES ('100500134', '拉达绘本');
INSERT INTO `item` VALUES ('100500135', '大嘴雀绘本');
INSERT INTO `item` VALUES ('100500136', '金鱼王绘本');
INSERT INTO `item` VALUES ('100500137', '白海狮绘本');
INSERT INTO `item` VALUES ('100500138', '火岩鼠绘本');
INSERT INTO `item` VALUES ('100500139', '雷丘绘本');
INSERT INTO `item` VALUES ('100500140', '喷火龙绘本');
INSERT INTO `item` VALUES ('100500141', '水箭龟绘本');
INSERT INTO `item` VALUES ('100500142', '妙蛙花绘本');
INSERT INTO `item` VALUES ('100500143', '比雕绘本');
INSERT INTO `item` VALUES ('100500144', '巴大蝴绘本');
INSERT INTO `item` VALUES ('100500145', '大针蜂绘本');
INSERT INTO `item` VALUES ('100500146', '尼多后绘本');
INSERT INTO `item` VALUES ('100500147', '尼多王绘本');
INSERT INTO `item` VALUES ('100500148', '大食花绘本');
INSERT INTO `item` VALUES ('100500149', '隆隆岩绘本');
INSERT INTO `item` VALUES ('100500150', '耿鬼绘本');
INSERT INTO `item` VALUES ('100500151', '皮可西绘本');
INSERT INTO `item` VALUES ('100500152', '九尾绘本');
INSERT INTO `item` VALUES ('100500153', '霸王花绘本');
INSERT INTO `item` VALUES ('100500154', '美丽花绘本');
INSERT INTO `item` VALUES ('100500155', '哥达鸭绘本');
INSERT INTO `item` VALUES ('100500156', '风速狗绘本');
INSERT INTO `item` VALUES ('100500157', '快泳蛙绘本');
INSERT INTO `item` VALUES ('100500158', '牛蛙君绘本');
INSERT INTO `item` VALUES ('100500159', '胡地绘本');
INSERT INTO `item` VALUES ('100500160', '怪力绘本');
INSERT INTO `item` VALUES ('100500161', '毒刺水母绘本');
INSERT INTO `item` VALUES ('100500162', '隆隆岩绘本');
INSERT INTO `item` VALUES ('100500163', '烈焰马绘本');
INSERT INTO `item` VALUES ('100500164', '呆河马绘本');
INSERT INTO `item` VALUES ('100500165', '河马王绘本');
INSERT INTO `item` VALUES ('100500166', '白海狮绘本');
INSERT INTO `item` VALUES ('100500167', '铁甲贝绘本');
INSERT INTO `item` VALUES ('100500168', '迷唇姐绘本');
INSERT INTO `item` VALUES ('100500169', '大钢蛇绘本');
INSERT INTO `item` VALUES ('100500170', '椰蛋树绘本');
INSERT INTO `item` VALUES ('100500171', '铁甲暴龙绘本');
INSERT INTO `item` VALUES ('100500172', '袋龙绘本');
INSERT INTO `item` VALUES ('100500173', '刺龙王绘本');
INSERT INTO `item` VALUES ('100500174', '宝石海星绘本');
INSERT INTO `item` VALUES ('100500175', '飞天螳螂绘本');
INSERT INTO `item` VALUES ('100500176', '暴鲤龙绘本');
INSERT INTO `item` VALUES ('100500177', '乘龙绘本');
INSERT INTO `item` VALUES ('100500178', '水精灵绘本');
INSERT INTO `item` VALUES ('100500179', '雷精灵绘本');
INSERT INTO `item` VALUES ('100500180', '火精灵绘本');
INSERT INTO `item` VALUES ('100500181', '3D龙II绘本');
INSERT INTO `item` VALUES ('100500182', '多刺菊石兽绘本');
INSERT INTO `item` VALUES ('100500183', '镰刀盔绘本');
INSERT INTO `item` VALUES ('100500184', '哈克龙绘本');
INSERT INTO `item` VALUES ('100500185', '大菊花绘本');
INSERT INTO `item` VALUES ('100500186', '火暴兽绘本');
INSERT INTO `item` VALUES ('100500187', '大力鳄绘本');
INSERT INTO `item` VALUES ('100500188', '叉字蝠绘本');
INSERT INTO `item` VALUES ('100500189', '波克基古绘本');
INSERT INTO `item` VALUES ('100500190', '电龙绘本');
INSERT INTO `item` VALUES ('100500191', '太阳精灵绘本');
INSERT INTO `item` VALUES ('100500192', '月精灵绘本');
INSERT INTO `item` VALUES ('100500193', '佛烈托斯绘本');
INSERT INTO `item` VALUES ('100500194', '巨钳螳螂绘本');
INSERT INTO `item` VALUES ('100500195', '赫拉克罗斯绘本');
INSERT INTO `item` VALUES ('100500196', '圈圈熊绘本');
INSERT INTO `item` VALUES ('100500197', '黑鲁加绘本');
INSERT INTO `item` VALUES ('100500198', '顿甲绘本');
INSERT INTO `item` VALUES ('100500199', '快龙绘本');
INSERT INTO `item` VALUES ('100500200', '超梦绘本');
INSERT INTO `item` VALUES ('100500201', '凤王绘本');
INSERT INTO `item` VALUES ('100500202', '洛奇亚绘本');
INSERT INTO `item` VALUES ('100500203', '大岩蛇绘本');
INSERT INTO `item` VALUES ('100500204', '化石翼龙绘本');
INSERT INTO `item` VALUES ('100500205', '卡比兽绘本');
INSERT INTO `item` VALUES ('100500206', '急冻鸟绘本');
INSERT INTO `item` VALUES ('100500207', '闪电鸟绘本');
INSERT INTO `item` VALUES ('100500208', '火焰鸟绘本');
INSERT INTO `item` VALUES ('100500209', '梦幻绘本');
INSERT INTO `item` VALUES ('100500210', '雷公绘本');
INSERT INTO `item` VALUES ('100500211', '炎帝绘本');
INSERT INTO `item` VALUES ('100500212', '水君绘本');
INSERT INTO `item` VALUES ('100500213', '班吉拉绘本');
INSERT INTO `item` VALUES ('100500214', '雪拉比绘本');
INSERT INTO `item` VALUES ('100500215', '请假王绘本');
INSERT INTO `item` VALUES ('100500216', '血翼飞龙绘本');
INSERT INTO `item` VALUES ('100500217', '巨金怪绘本');
INSERT INTO `item` VALUES ('100500218', '雷吉洛克绘本');
INSERT INTO `item` VALUES ('100500219', '雷吉艾斯绘本');
INSERT INTO `item` VALUES ('100500220', '雷吉斯奇鲁绘本');
INSERT INTO `item` VALUES ('100500221', '拉提亚斯绘本');
INSERT INTO `item` VALUES ('100500222', '拉提奥斯绘本');
INSERT INTO `item` VALUES ('100500223', '海皇牙绘本');
INSERT INTO `item` VALUES ('100500224', '古拉顿绘本');
INSERT INTO `item` VALUES ('100500225', '烈空座绘本');
INSERT INTO `item` VALUES ('100500226', '吉拉祈绘本');
INSERT INTO `item` VALUES ('100500227', '迪奥西斯绘本');
INSERT INTO `item` VALUES ('100500228', '班吉拉绘本');
INSERT INTO `item` VALUES ('100500229', '皮卡丘绘本(拼图)');
INSERT INTO `item` VALUES ('100500230', '火恐龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500231', '卡咪龟绘本(拼图)');
INSERT INTO `item` VALUES ('100500232', '妙蛙草绘本(拼图)');
INSERT INTO `item` VALUES ('100500233', '比比鸟绘本(拼图)');
INSERT INTO `item` VALUES ('100500234', '铁甲蛹绘本(拼图)');
INSERT INTO `item` VALUES ('100500235', '铁壳昆绘本(拼图)');
INSERT INTO `item` VALUES ('100500236', '尼多娜绘本(拼图)');
INSERT INTO `item` VALUES ('100500237', '尼多力诺绘本(拼图)');
INSERT INTO `item` VALUES ('100500238', '口呆花绘本(拼图)');
INSERT INTO `item` VALUES ('100500239', '隆隆石绘本(拼图)');
INSERT INTO `item` VALUES ('100500240', '鬼斯通绘本(拼图)');
INSERT INTO `item` VALUES ('100500241', '暴鲤龙 绘本(拼图)');
INSERT INTO `item` VALUES ('100500242', '猫老大绘本(拼图)');
INSERT INTO `item` VALUES ('100500243', '哥达鸭 绘本(拼图)');
INSERT INTO `item` VALUES ('100500244', '阿柏怪绘本(拼图)');
INSERT INTO `item` VALUES ('100500245', '九尾绘本(拼图)');
INSERT INTO `item` VALUES ('100500246', '双弹瓦斯 绘本(拼图)');
INSERT INTO `item` VALUES ('100500247', '穿山王绘本(拼图)');
INSERT INTO `item` VALUES ('100500248', '拉达绘本(拼图)');
INSERT INTO `item` VALUES ('100500249', '大嘴雀绘本(拼图)');
INSERT INTO `item` VALUES ('100500250', '金鱼王绘本(拼图)');
INSERT INTO `item` VALUES ('100500251', '白海狮绘本(拼图)');
INSERT INTO `item` VALUES ('100500252', '火岩鼠绘本(拼图)');
INSERT INTO `item` VALUES ('100500253', '雷丘绘本(拼图)');
INSERT INTO `item` VALUES ('100500254', '喷火龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500255', '水箭龟绘本(拼图)');
INSERT INTO `item` VALUES ('100500256', '妙蛙花绘本(拼图)');
INSERT INTO `item` VALUES ('100500257', '比雕绘本(拼图)');
INSERT INTO `item` VALUES ('100500258', '巴大蝴绘本(拼图)');
INSERT INTO `item` VALUES ('100500259', '大针蜂绘本(拼图)');
INSERT INTO `item` VALUES ('100500260', '尼多后绘本(拼图)');
INSERT INTO `item` VALUES ('100500261', '尼多王绘本(拼图)');
INSERT INTO `item` VALUES ('100500262', '大食花绘本(拼图)');
INSERT INTO `item` VALUES ('100500263', '隆隆石绘本(拼图)');
INSERT INTO `item` VALUES ('100500264', '耿鬼绘本(拼图)');
INSERT INTO `item` VALUES ('100500265', '皮可西绘本(拼图)');
INSERT INTO `item` VALUES ('100500266', '九尾绘本(拼图)');
INSERT INTO `item` VALUES ('100500267', '霸王花绘本(拼图)');
INSERT INTO `item` VALUES ('100500268', '美丽花绘本(拼图)');
INSERT INTO `item` VALUES ('100500269', '哥达鸭绘本(拼图)');
INSERT INTO `item` VALUES ('100500270', '风速狗绘本(拼图)');
INSERT INTO `item` VALUES ('100500271', '快泳蛙绘本(拼图)');
INSERT INTO `item` VALUES ('100500272', '牛蛙君绘本(拼图)');
INSERT INTO `item` VALUES ('100500273', '胡地绘本(拼图)');
INSERT INTO `item` VALUES ('100500274', '怪力绘本(拼图)');
INSERT INTO `item` VALUES ('100500275', '毒刺水母绘本(拼图)');
INSERT INTO `item` VALUES ('100500276', '隆隆岩绘本(拼图)');
INSERT INTO `item` VALUES ('100500277', '烈焰马绘本(拼图)');
INSERT INTO `item` VALUES ('100500278', '呆河马绘本(拼图)');
INSERT INTO `item` VALUES ('100500279', '河马王绘本(拼图)');
INSERT INTO `item` VALUES ('100500280', '白海狮绘本(拼图)');
INSERT INTO `item` VALUES ('100500281', '铁甲贝绘本(拼图)');
INSERT INTO `item` VALUES ('100500282', '迷唇姐绘本(拼图)');
INSERT INTO `item` VALUES ('100500283', '大钢蛇绘本(拼图)');
INSERT INTO `item` VALUES ('100500284', '椰蛋树绘本(拼图)');
INSERT INTO `item` VALUES ('100500285', '铁甲暴龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500286', '袋龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500287', '刺龙王绘本(拼图)');
INSERT INTO `item` VALUES ('100500288', '宝石海星绘本(拼图)');
INSERT INTO `item` VALUES ('100500289', '飞天螳螂绘本(拼图)');
INSERT INTO `item` VALUES ('100500290', '暴鲤龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500291', '乘龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500292', '水精灵绘本(拼图)');
INSERT INTO `item` VALUES ('100500293', '雷精灵绘本(拼图)');
INSERT INTO `item` VALUES ('100500294', '火精灵绘本(拼图)');
INSERT INTO `item` VALUES ('100500295', '3D龙II绘本(拼图)');
INSERT INTO `item` VALUES ('100500296', '多刺菊石兽绘本(拼图)');
INSERT INTO `item` VALUES ('100500297', '镰刀盔绘本(拼图)');
INSERT INTO `item` VALUES ('100500298', '哈克龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500299', '大菊花绘本(拼图)');
INSERT INTO `item` VALUES ('100500300', '火暴兽绘本(拼图)');
INSERT INTO `item` VALUES ('100500301', '大力鳄绘本(拼图)');
INSERT INTO `item` VALUES ('100500302', '叉字蝠绘本(拼图)');
INSERT INTO `item` VALUES ('100500303', '波克基古绘本(拼图)');
INSERT INTO `item` VALUES ('100500304', '电龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500305', '太阳精灵绘本(拼图)');
INSERT INTO `item` VALUES ('100500306', '月精灵绘本(拼图)');
INSERT INTO `item` VALUES ('100500307', '佛烈托斯绘本(拼图)');
INSERT INTO `item` VALUES ('100500308', '巨钳螳螂绘本(拼图)');
INSERT INTO `item` VALUES ('100500309', '赫拉克罗斯绘本(拼图)');
INSERT INTO `item` VALUES ('100500310', '圈圈熊绘本(拼图)');
INSERT INTO `item` VALUES ('100500311', '黑鲁加绘本(拼图)');
INSERT INTO `item` VALUES ('100500312', '顿甲绘本(拼图)');
INSERT INTO `item` VALUES ('100500313', '快龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500314', '超梦绘本(拼图)');
INSERT INTO `item` VALUES ('100500315', '凤王绘本(拼图)');
INSERT INTO `item` VALUES ('100500316', '洛奇亚绘本(拼图)');
INSERT INTO `item` VALUES ('100500317', '大岩蛇绘本(拼图)');
INSERT INTO `item` VALUES ('100500318', '化石翼龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500319', '卡比兽绘本(拼图)');
INSERT INTO `item` VALUES ('100500320', '急冻鸟绘本(拼图)');
INSERT INTO `item` VALUES ('100500321', '闪电鸟绘本(拼图)');
INSERT INTO `item` VALUES ('100500322', '火焰鸟绘本(拼图)');
INSERT INTO `item` VALUES ('100500323', '梦幻绘本(拼图)');
INSERT INTO `item` VALUES ('100500324', '雷公绘本(拼图)');
INSERT INTO `item` VALUES ('100500325', '炎帝绘本(拼图)');
INSERT INTO `item` VALUES ('100500326', '水君绘本(拼图)');
INSERT INTO `item` VALUES ('100500327', '班吉拉绘本(拼图)');
INSERT INTO `item` VALUES ('100500328', '雪拉比绘本(拼图)');
INSERT INTO `item` VALUES ('100500329', '请假王绘本(拼图)');
INSERT INTO `item` VALUES ('100500330', '血翼飞龙绘本(拼图)');
INSERT INTO `item` VALUES ('100500331', '巨金怪绘本(拼图)');
INSERT INTO `item` VALUES ('100500332', '雷吉洛克绘本(拼图)');
INSERT INTO `item` VALUES ('100500333', '雷吉艾斯绘本(拼图)');
INSERT INTO `item` VALUES ('100500334', '雷吉斯奇鲁绘本(拼图)');
INSERT INTO `item` VALUES ('100500335', '拉提亚斯绘本(拼图)');
INSERT INTO `item` VALUES ('100500336', '拉提奥斯绘本(拼图)');
INSERT INTO `item` VALUES ('100500337', '海皇牙绘本(拼图)');
INSERT INTO `item` VALUES ('100500338', '古拉顿绘本(拼图)');
INSERT INTO `item` VALUES ('100500339', '烈空座绘本(拼图)');
INSERT INTO `item` VALUES ('100500340', '吉拉祈绘本(拼图)');
INSERT INTO `item` VALUES ('100500341', '迪奥西斯绘本(拼图)');
INSERT INTO `item` VALUES ('100500342', '班吉拉绘本(拼图)');
INSERT INTO `item` VALUES ('100500343', '大师球碎片');
INSERT INTO `item` VALUES ('100500344', '炎之弹珠（特大）');
INSERT INTO `item` VALUES ('100500345', '水之弹珠（特大）');
INSERT INTO `item` VALUES ('100500346', '草之弹珠（特大）');
INSERT INTO `item` VALUES ('100500347', '电之弹珠（特大）');
INSERT INTO `item` VALUES ('100500348', '岩之弹珠（特大）');
INSERT INTO `item` VALUES ('100500349', '龙之弹珠（特大）');
INSERT INTO `item` VALUES ('100500350', '飞行弹珠（特大）');
INSERT INTO `item` VALUES ('100500351', '幽灵弹珠（特大）');
INSERT INTO `item` VALUES ('100500352', '超能弹珠（特大）');
INSERT INTO `item` VALUES ('100500353', '一般弹珠（特大）');
INSERT INTO `item` VALUES ('100500354', '虫之弹珠（特大）');
INSERT INTO `item` VALUES ('100500355', '格斗弹珠（特大）');
INSERT INTO `item` VALUES ('100500356', '剧毒弹珠（特大）');
INSERT INTO `item` VALUES ('100500357', '冰之弹珠（特大）');
INSERT INTO `item` VALUES ('100500358', '钢之弹珠（特大）');
INSERT INTO `item` VALUES ('100500359', '恶之弹珠（特大）');
INSERT INTO `item` VALUES ('100500360', '地表弹珠（特大）');
INSERT INTO `item` VALUES ('100500361', '妖精弹珠（特大）');
INSERT INTO `item` VALUES ('100600001', '黑色带子');
INSERT INTO `item` VALUES ('100600002', '亮光之粉');
INSERT INTO `item` VALUES ('100600003', '王者之证');
INSERT INTO `item` VALUES ('100600004', '电气球');
INSERT INTO `item` VALUES ('100600005', '先攻之爪');
INSERT INTO `item` VALUES ('100600006', '银色的粉');
INSERT INTO `item` VALUES ('100600007', '护身金币');
INSERT INTO `item` VALUES ('100600008', '净身的护身符');
INSERT INTO `item` VALUES ('100600009', '烟雾球');
INSERT INTO `item` VALUES ('100600010', '振奋精神的头布');
INSERT INTO `item` VALUES ('100600011', '幸运蛋');
INSERT INTO `item` VALUES ('100600012', '对焦镜片');
INSERT INTO `item` VALUES ('100600013', '金属涂层');
INSERT INTO `item` VALUES ('100600014', '吃剩下的东西');
INSERT INTO `item` VALUES ('100600015', '龙之鳞片');
INSERT INTO `item` VALUES ('100600016', '柔软的沙子');
INSERT INTO `item` VALUES ('100600017', '硬石头');
INSERT INTO `item` VALUES ('100600018', '奇迹种子');
INSERT INTO `item` VALUES ('100600019', '黑色的眼镜');
INSERT INTO `item` VALUES ('100600020', '磁铁');
INSERT INTO `item` VALUES ('100600021', '神秘的水滴');
INSERT INTO `item` VALUES ('100600022', '尖锐的鸟嘴');
INSERT INTO `item` VALUES ('100600023', '毒针');
INSERT INTO `item` VALUES ('100600024', '融化不了的冰块');
INSERT INTO `item` VALUES ('100600025', '诅咒护身符');
INSERT INTO `item` VALUES ('100600026', '弯曲的汤匙');
INSERT INTO `item` VALUES ('100600027', '木灰');
INSERT INTO `item` VALUES ('100600028', '龙之牙');
INSERT INTO `item` VALUES ('100600029', '粉红色丝带');
INSERT INTO `item` VALUES ('100600030', '水玉色之带');
INSERT INTO `item` VALUES ('100600031', '幸运之拳');
INSERT INTO `item` VALUES ('100600032', '金属粉');
INSERT INTO `item` VALUES ('100600033', '胖骨头');
INSERT INTO `item` VALUES ('100600034', '大葱');
INSERT INTO `item` VALUES ('100600035', '破坏性的遗传因子');
INSERT INTO `item` VALUES ('100700001', '皮丘');
INSERT INTO `item` VALUES ('100700002', '皮卡丘');
INSERT INTO `item` VALUES ('100700003', '雷丘');
INSERT INTO `item` VALUES ('100700004', '小火龙');
INSERT INTO `item` VALUES ('100700005', '火恐龙');
INSERT INTO `item` VALUES ('100700006', '喷火龙');
INSERT INTO `item` VALUES ('100700007', '杰尼龟');
INSERT INTO `item` VALUES ('100700008', '卡咪龟');
INSERT INTO `item` VALUES ('100700009', '水箭龟');
INSERT INTO `item` VALUES ('100700010', '妙蛙种子');
INSERT INTO `item` VALUES ('100700011', '妙蛙草');
INSERT INTO `item` VALUES ('100700012', '妙蛙花');
INSERT INTO `item` VALUES ('100700013', '波波');
INSERT INTO `item` VALUES ('100700014', '比比鸟');
INSERT INTO `item` VALUES ('100700015', '比雕');
INSERT INTO `item` VALUES ('100700016', '绿毛虫');
INSERT INTO `item` VALUES ('100700017', '铁甲蛹');
INSERT INTO `item` VALUES ('100700018', '巴大蝴');
INSERT INTO `item` VALUES ('100700019', '独角虫');
INSERT INTO `item` VALUES ('100700020', '铁壳昆');
INSERT INTO `item` VALUES ('100700021', '大针蜂');
INSERT INTO `item` VALUES ('100700022', '尼多兰');
INSERT INTO `item` VALUES ('100700023', '尼多娜');
INSERT INTO `item` VALUES ('100700024', '尼多后');
INSERT INTO `item` VALUES ('100700025', '尼多朗');
INSERT INTO `item` VALUES ('100700026', '尼多力诺');
INSERT INTO `item` VALUES ('100700027', '尼多王');
INSERT INTO `item` VALUES ('100700028', '喇叭芽');
INSERT INTO `item` VALUES ('100700029', '口呆花');
INSERT INTO `item` VALUES ('100700030', '大食花');
INSERT INTO `item` VALUES ('100700031', '小拉达');
INSERT INTO `item` VALUES ('100700032', '拉达');
INSERT INTO `item` VALUES ('100700033', '烈雀');
INSERT INTO `item` VALUES ('100700034', '大嘴雀');
INSERT INTO `item` VALUES ('100700035', '阿柏蛇');
INSERT INTO `item` VALUES ('100700036', '阿柏怪');
INSERT INTO `item` VALUES ('100700037', '穿山鼠');
INSERT INTO `item` VALUES ('100700038', '穿山王');
INSERT INTO `item` VALUES ('100700039', '皮宝宝');
INSERT INTO `item` VALUES ('100700040', '皮皮');
INSERT INTO `item` VALUES ('100700041', '皮可西');
INSERT INTO `item` VALUES ('100700042', '六尾');
INSERT INTO `item` VALUES ('100700043', '九尾');
INSERT INTO `item` VALUES ('100700044', '宝宝丁');
INSERT INTO `item` VALUES ('100700045', '胖丁');
INSERT INTO `item` VALUES ('100700046', '胖可丁');
INSERT INTO `item` VALUES ('100700047', '超音蝠');
INSERT INTO `item` VALUES ('100700048', '大嘴蝠');
INSERT INTO `item` VALUES ('100700049', '走路草');
INSERT INTO `item` VALUES ('100700050', '臭臭花');
INSERT INTO `item` VALUES ('100700051', '霸王花');
INSERT INTO `item` VALUES ('100700052', '美丽花');
INSERT INTO `item` VALUES ('100700053', '派拉斯');
INSERT INTO `item` VALUES ('100700054', '派拉斯特');
INSERT INTO `item` VALUES ('100700055', '毛球');
INSERT INTO `item` VALUES ('100700056', '末入蛾');
INSERT INTO `item` VALUES ('100700057', '地鼠');
INSERT INTO `item` VALUES ('100700058', '三地鼠');
INSERT INTO `item` VALUES ('100700059', '喵喵');
INSERT INTO `item` VALUES ('100700060', '猫老大');
INSERT INTO `item` VALUES ('100700061', '可达鸭');
INSERT INTO `item` VALUES ('100700062', '哥达鸭');
INSERT INTO `item` VALUES ('100700063', '猴怪');
INSERT INTO `item` VALUES ('100700064', '火爆猴');
INSERT INTO `item` VALUES ('100700065', '卡蒂狗');
INSERT INTO `item` VALUES ('100700066', '风速狗');
INSERT INTO `item` VALUES ('100700067', '蚊香蝌蚪');
INSERT INTO `item` VALUES ('100700068', '蚊香蛙');
INSERT INTO `item` VALUES ('100700069', '快泳蛙');
INSERT INTO `item` VALUES ('100700070', '牛蛙君');
INSERT INTO `item` VALUES ('100700071', '凯西');
INSERT INTO `item` VALUES ('100700072', '勇吉拉');
INSERT INTO `item` VALUES ('100700073', '胡地');
INSERT INTO `item` VALUES ('100700074', '腕力');
INSERT INTO `item` VALUES ('100700075', '豪力');
INSERT INTO `item` VALUES ('100700076', '怪力');
INSERT INTO `item` VALUES ('100700077', '玛瑙水母');
INSERT INTO `item` VALUES ('100700078', '毒刺水母');
INSERT INTO `item` VALUES ('100700079', '小拳石');
INSERT INTO `item` VALUES ('100700080', '隆隆石');
INSERT INTO `item` VALUES ('100700081', '隆隆岩');
INSERT INTO `item` VALUES ('100700082', '小火马');
INSERT INTO `item` VALUES ('100700083', '烈焰马');
INSERT INTO `item` VALUES ('100700084', '呆呆兽');
INSERT INTO `item` VALUES ('100700085', '呆河马');
INSERT INTO `item` VALUES ('100700086', '河马王');
INSERT INTO `item` VALUES ('100700087', '小磁怪');
INSERT INTO `item` VALUES ('100700088', '三合一磁怪');
INSERT INTO `item` VALUES ('100700089', '大葱鸭');
INSERT INTO `item` VALUES ('100700090', '嘟嘟');
INSERT INTO `item` VALUES ('100700091', '嘟嘟利');
INSERT INTO `item` VALUES ('100700092', '小海狮');
INSERT INTO `item` VALUES ('100700093', '白海狮');
INSERT INTO `item` VALUES ('100700094', '臭泥');
INSERT INTO `item` VALUES ('100700095', '臭臭泥');
INSERT INTO `item` VALUES ('100700096', '大舌贝');
INSERT INTO `item` VALUES ('100700097', '铁甲贝');
INSERT INTO `item` VALUES ('100700098', '鬼斯');
INSERT INTO `item` VALUES ('100700099', '鬼斯通');
INSERT INTO `item` VALUES ('100700100', '耿鬼');
INSERT INTO `item` VALUES ('100700101', '大岩蛇');
INSERT INTO `item` VALUES ('100700102', '大钢蛇');
INSERT INTO `item` VALUES ('100700103', '素利普');
INSERT INTO `item` VALUES ('100700104', '素利拍');
INSERT INTO `item` VALUES ('100700105', '大钳蟹');
INSERT INTO `item` VALUES ('100700106', '巨钳蟹');
INSERT INTO `item` VALUES ('100700107', '雷电球');
INSERT INTO `item` VALUES ('100700108', '顽皮弹');
INSERT INTO `item` VALUES ('100700109', '蛋蛋');
INSERT INTO `item` VALUES ('100700110', '椰蛋树');
INSERT INTO `item` VALUES ('100700111', '可拉可拉');
INSERT INTO `item` VALUES ('100700112', '嘎拉嘎拉');
INSERT INTO `item` VALUES ('100700113', '沙瓦郎');
INSERT INTO `item` VALUES ('100700114', '艾比郎');
INSERT INTO `item` VALUES ('100700115', '大舌头');
INSERT INTO `item` VALUES ('100700116', '瓦斯弹');
INSERT INTO `item` VALUES ('100700117', '双弹瓦斯');
INSERT INTO `item` VALUES ('100700118', '铁甲犀牛');
INSERT INTO `item` VALUES ('100700119', '铁甲暴龙');
INSERT INTO `item` VALUES ('100700120', '吉利蛋');
INSERT INTO `item` VALUES ('100700121', '蔓藤怪');
INSERT INTO `item` VALUES ('100700122', '袋龙');
INSERT INTO `item` VALUES ('100700123', '墨海马');
INSERT INTO `item` VALUES ('100700124', '海刺龙');
INSERT INTO `item` VALUES ('100700125', '刺龙王');
INSERT INTO `item` VALUES ('100700126', '角金鱼');
INSERT INTO `item` VALUES ('100700127', '金鱼王');
INSERT INTO `item` VALUES ('100700128', '海星星');
INSERT INTO `item` VALUES ('100700129', '宝石海星');
INSERT INTO `item` VALUES ('100700130', '吸盘魔偶');
INSERT INTO `item` VALUES ('100700131', '飞天螳螂');
INSERT INTO `item` VALUES ('100700132', '大甲');
INSERT INTO `item` VALUES ('100700133', '肯泰罗');
INSERT INTO `item` VALUES ('100700134', '鲤鱼王');
INSERT INTO `item` VALUES ('100700135', '暴鲤龙');
INSERT INTO `item` VALUES ('100700136', '乘龙');
INSERT INTO `item` VALUES ('100700137', '百变怪');
INSERT INTO `item` VALUES ('100700138', '伊布');
INSERT INTO `item` VALUES ('100700139', '水精灵');
INSERT INTO `item` VALUES ('100700140', '雷精灵');
INSERT INTO `item` VALUES ('100700141', '火精灵');
INSERT INTO `item` VALUES ('100700142', '3D龙');
INSERT INTO `item` VALUES ('100700143', '3D龙II');
INSERT INTO `item` VALUES ('100700144', '菊石兽');
INSERT INTO `item` VALUES ('100700145', '多刺菊石兽');
INSERT INTO `item` VALUES ('100700146', '化石盔');
INSERT INTO `item` VALUES ('100700147', '镰刀盔');
INSERT INTO `item` VALUES ('100700148', '化石翼龙');
INSERT INTO `item` VALUES ('100700149', '卡比兽');
INSERT INTO `item` VALUES ('100700150', '急冻鸟');
INSERT INTO `item` VALUES ('100700151', '闪电鸟');
INSERT INTO `item` VALUES ('100700152', '火焰鸟');
INSERT INTO `item` VALUES ('100700153', '迷你龙');
INSERT INTO `item` VALUES ('100700154', '哈克龙');
INSERT INTO `item` VALUES ('100700155', '快龙');
INSERT INTO `item` VALUES ('100700156', '超梦');
INSERT INTO `item` VALUES ('100700157', '梦幻');
INSERT INTO `item` VALUES ('100700158', '菊草叶');
INSERT INTO `item` VALUES ('100700159', '月桂叶');
INSERT INTO `item` VALUES ('100700160', '大菊花');
INSERT INTO `item` VALUES ('100700161', '火球鼠');
INSERT INTO `item` VALUES ('100700162', '火岩鼠');
INSERT INTO `item` VALUES ('100700163', '火暴兽');
INSERT INTO `item` VALUES ('100700164', '小锯鳄');
INSERT INTO `item` VALUES ('100700165', '蓝鳄');
INSERT INTO `item` VALUES ('100700166', '大力鳄');
INSERT INTO `item` VALUES ('100700167', '尾立');
INSERT INTO `item` VALUES ('100700168', '大尾立');
INSERT INTO `item` VALUES ('100700169', '咕咕');
INSERT INTO `item` VALUES ('100700170', '猫头夜鹰');
INSERT INTO `item` VALUES ('100700171', '芭瓢虫');
INSERT INTO `item` VALUES ('100700172', '安瓢虫');
INSERT INTO `item` VALUES ('100700173', '线球');
INSERT INTO `item` VALUES ('100700174', '阿利多斯');
INSERT INTO `item` VALUES ('100700175', '叉字蝠');
INSERT INTO `item` VALUES ('100700176', '灯笼鱼');
INSERT INTO `item` VALUES ('100700177', '电灯怪');
INSERT INTO `item` VALUES ('100700178', '波克比');
INSERT INTO `item` VALUES ('100700179', '波克基古');
INSERT INTO `item` VALUES ('100700180', '天然雀');
INSERT INTO `item` VALUES ('100700181', '天然鸟');
INSERT INTO `item` VALUES ('100700182', '咩利羊');
INSERT INTO `item` VALUES ('100700183', '绵绵');
INSERT INTO `item` VALUES ('100700184', '电龙');
INSERT INTO `item` VALUES ('100700185', '玛力露');
INSERT INTO `item` VALUES ('100700186', '玛力露丽');
INSERT INTO `item` VALUES ('100700187', '胡说树');
INSERT INTO `item` VALUES ('100700188', '毽子草');
INSERT INTO `item` VALUES ('100700189', '毽子花');
INSERT INTO `item` VALUES ('100700190', '毽子绵');
INSERT INTO `item` VALUES ('100700191', '长尾怪手');
INSERT INTO `item` VALUES ('100700192', '向日种子');
INSERT INTO `item` VALUES ('100700193', '向日花怪');
INSERT INTO `item` VALUES ('100700194', '阳阳玛');
INSERT INTO `item` VALUES ('100700195', '乌波');
INSERT INTO `item` VALUES ('100700196', '沼王');
INSERT INTO `item` VALUES ('100700197', '太阳精灵');
INSERT INTO `item` VALUES ('100700198', '月精灵');
INSERT INTO `item` VALUES ('100700199', '黑暗鸦');
INSERT INTO `item` VALUES ('100700200', '梦妖');
INSERT INTO `item` VALUES ('100700201', '未知图腾');
INSERT INTO `item` VALUES ('100700202', '果然翁');
INSERT INTO `item` VALUES ('100700203', '麒麟奇');
INSERT INTO `item` VALUES ('100700204', '榛果球');
INSERT INTO `item` VALUES ('100700205', '佛烈托斯');
INSERT INTO `item` VALUES ('100700206', '土龙弟弟');
INSERT INTO `item` VALUES ('100700207', '天蝎');
INSERT INTO `item` VALUES ('100700208', '布卢');
INSERT INTO `item` VALUES ('100700209', '布卢皇');
INSERT INTO `item` VALUES ('100700210', '千针鱼');
INSERT INTO `item` VALUES ('100700211', '巨钳螳螂');
INSERT INTO `item` VALUES ('100700212', '壶壶');
INSERT INTO `item` VALUES ('100700213', '赫拉克罗斯');
INSERT INTO `item` VALUES ('100700214', '狃拉');
INSERT INTO `item` VALUES ('100700215', '熊宝宝');
INSERT INTO `item` VALUES ('100700216', '圈圈熊');
INSERT INTO `item` VALUES ('100700217', '熔岩虫');
INSERT INTO `item` VALUES ('100700218', '熔岩蜗牛');
INSERT INTO `item` VALUES ('100700219', '小山猪');
INSERT INTO `item` VALUES ('100700220', '长毛猪');
INSERT INTO `item` VALUES ('100700221', '太阳珊瑚');
INSERT INTO `item` VALUES ('100700222', '铁炮鱼');
INSERT INTO `item` VALUES ('100700223', '章鱼桶');
INSERT INTO `item` VALUES ('100700224', '信使鸟');
INSERT INTO `item` VALUES ('100700225', '巨翅飞鱼');
INSERT INTO `item` VALUES ('100700226', '盔甲鸟');
INSERT INTO `item` VALUES ('100700227', '戴鲁比');
INSERT INTO `item` VALUES ('100700228', '黑鲁加');
INSERT INTO `item` VALUES ('100700229', '小小象');
INSERT INTO `item` VALUES ('100700230', '顿甲');
INSERT INTO `item` VALUES ('100700231', '惊角鹿');
INSERT INTO `item` VALUES ('100700232', '图图犬');
INSERT INTO `item` VALUES ('100700233', '巴尔郎');
INSERT INTO `item` VALUES ('100700234', '柯波朗');
INSERT INTO `item` VALUES ('100700235', '迷唇娃');
INSERT INTO `item` VALUES ('100700236', '迷唇姐');
INSERT INTO `item` VALUES ('100700237', '电击怪');
INSERT INTO `item` VALUES ('100700238', '电击兽');
INSERT INTO `item` VALUES ('100700239', '小鸭嘴龙');
INSERT INTO `item` VALUES ('100700240', '鸭嘴火龙');
INSERT INTO `item` VALUES ('100700241', '大奶罐');
INSERT INTO `item` VALUES ('100700242', '吉利蛋');
INSERT INTO `item` VALUES ('100700243', '幸福蛋');
INSERT INTO `item` VALUES ('100700244', '雷公');
INSERT INTO `item` VALUES ('100700245', '炎帝');
INSERT INTO `item` VALUES ('100700246', '水君');
INSERT INTO `item` VALUES ('100700247', '由基拉');
INSERT INTO `item` VALUES ('100700248', '沙基拉');
INSERT INTO `item` VALUES ('100700249', '班吉拉');
INSERT INTO `item` VALUES ('100700250', '洛奇亚');
INSERT INTO `item` VALUES ('100700251', '凤王');
INSERT INTO `item` VALUES ('100700252', '雪拉比');
INSERT INTO `item` VALUES ('100700253', '闪光皮丘');
INSERT INTO `item` VALUES ('100700254', '闪光皮卡丘');
INSERT INTO `item` VALUES ('100700255', '闪光雷丘');
INSERT INTO `item` VALUES ('100700256', '闪光小火龙');
INSERT INTO `item` VALUES ('100700257', '闪光火恐龙');
INSERT INTO `item` VALUES ('100700258', '闪光喷火龙');
INSERT INTO `item` VALUES ('100700259', '闪光杰尼龟');
INSERT INTO `item` VALUES ('100700260', '闪光卡咪龟');
INSERT INTO `item` VALUES ('100700261', '闪光水箭龟');
INSERT INTO `item` VALUES ('100700262', '闪光妙蛙种子');
INSERT INTO `item` VALUES ('100700263', '闪光妙蛙草');
INSERT INTO `item` VALUES ('100700264', '闪光妙蛙花');
INSERT INTO `item` VALUES ('100700265', '闪光波波');
INSERT INTO `item` VALUES ('100700266', '闪光比比鸟');
INSERT INTO `item` VALUES ('100700267', '闪光比雕');
INSERT INTO `item` VALUES ('100700268', '闪光绿毛虫');
INSERT INTO `item` VALUES ('100700269', '闪光铁甲蛹');
INSERT INTO `item` VALUES ('100700270', '闪光巴大蝴');
INSERT INTO `item` VALUES ('100700271', '闪光独角虫');
INSERT INTO `item` VALUES ('100700272', '闪光铁壳昆');
INSERT INTO `item` VALUES ('100700273', '闪光大针蜂');
INSERT INTO `item` VALUES ('100700274', '闪光尼多兰');
INSERT INTO `item` VALUES ('100700275', '闪光尼多娜');
INSERT INTO `item` VALUES ('100700276', '闪光尼多后');
INSERT INTO `item` VALUES ('100700277', '闪光尼多朗');
INSERT INTO `item` VALUES ('100700278', '闪光尼多力诺');
INSERT INTO `item` VALUES ('100700279', '闪光尼多王');
INSERT INTO `item` VALUES ('100700280', '闪光小拉达');
INSERT INTO `item` VALUES ('100700281', '闪光拉达');
INSERT INTO `item` VALUES ('100700282', '闪光烈雀');
INSERT INTO `item` VALUES ('100700283', '闪光大嘴雀');
INSERT INTO `item` VALUES ('100700284', '闪光阿柏蛇');
INSERT INTO `item` VALUES ('100700285', '闪光阿柏怪');
INSERT INTO `item` VALUES ('100700286', '闪光穿山鼠');
INSERT INTO `item` VALUES ('100700287', '闪光穿山王');
INSERT INTO `item` VALUES ('100700288', '闪光卡比兽');
INSERT INTO `item` VALUES ('100700289', '闪光急冻鸟');
INSERT INTO `item` VALUES ('100700290', '闪光闪电鸟');
INSERT INTO `item` VALUES ('100700291', '闪光火焰鸟');
INSERT INTO `item` VALUES ('100700292', '闪光迷你龙');
INSERT INTO `item` VALUES ('100700293', '闪光哈克龙');
INSERT INTO `item` VALUES ('100700294', '闪光快龙');
INSERT INTO `item` VALUES ('100700295', '闪光超梦');
INSERT INTO `item` VALUES ('100700296', '闪光梦幻');
INSERT INTO `item` VALUES ('100800001', '神奇糖果（小）');
INSERT INTO `item` VALUES ('100800002', '神奇糖果（中）');
INSERT INTO `item` VALUES ('100800003', '神奇糖果（大）');
INSERT INTO `item` VALUES ('100800004', '小型钱包');
INSERT INTO `item` VALUES ('100800005', '大型钱包');
INSERT INTO `item` VALUES ('100800006', '巨型钱包');
INSERT INTO `item` VALUES ('100800007', '神奇胶囊');
INSERT INTO `item` VALUES ('100900001', '初级精灵探测器');
INSERT INTO `item` VALUES ('100900002', '中级精灵探测器');
INSERT INTO `item` VALUES ('100900003', '高级精灵探测器');
INSERT INTO `item` VALUES ('100900004', '特级精灵探测器');
INSERT INTO `item` VALUES ('100900005', '化石翼龙契约');
INSERT INTO `item` VALUES ('100900006', '卡比兽契约');
INSERT INTO `item` VALUES ('100900007', '急冻鸟契约');
INSERT INTO `item` VALUES ('100900008', '闪电鸟契约');
INSERT INTO `item` VALUES ('100900009', '火焰鸟契约');
INSERT INTO `item` VALUES ('100900010', '快龙契约');
INSERT INTO `item` VALUES ('100900011', '超梦契约');
INSERT INTO `item` VALUES ('100900012', '梦幻契约');
INSERT INTO `item` VALUES ('100900020', '化石翼龙契约1号');
INSERT INTO `item` VALUES ('100900021', '化石翼龙契约2号');
INSERT INTO `item` VALUES ('100900022', '化石翼龙契约3号');
INSERT INTO `item` VALUES ('100900023', '化石翼龙契约4号');
INSERT INTO `item` VALUES ('100900024', '卡比兽契约碎片1号');
INSERT INTO `item` VALUES ('100900025', '卡比兽契约碎片2号');
INSERT INTO `item` VALUES ('100900026', '卡比兽契约碎片3号');
INSERT INTO `item` VALUES ('100900027', '卡比兽契约碎片4号');
INSERT INTO `item` VALUES ('100900028', '急冻鸟契约碎片1号');
INSERT INTO `item` VALUES ('100900029', '急冻鸟契约碎片2号');
INSERT INTO `item` VALUES ('100900030', '急冻鸟契约碎片3号');
INSERT INTO `item` VALUES ('100900031', '急冻鸟契约碎片4号');
INSERT INTO `item` VALUES ('100900032', '闪电鸟契约碎片1号');
INSERT INTO `item` VALUES ('100900033', '闪电鸟契约碎片2号');
INSERT INTO `item` VALUES ('100900034', '闪电鸟契约碎片3号');
INSERT INTO `item` VALUES ('100900035', '闪电鸟契约碎片4号');
INSERT INTO `item` VALUES ('100900036', '火焰鸟契约碎片1号');
INSERT INTO `item` VALUES ('100900037', '火焰鸟契约碎片2号');
INSERT INTO `item` VALUES ('100900038', '火焰鸟契约碎片3号');
INSERT INTO `item` VALUES ('100900039', '火焰鸟契约碎片4号');
INSERT INTO `item` VALUES ('100900040', '快龙契约碎片1号');
INSERT INTO `item` VALUES ('100900041', '快龙契约碎片2号');
INSERT INTO `item` VALUES ('100900042', '快龙契约碎片3号');
INSERT INTO `item` VALUES ('100900043', '快龙契约碎片4号');
INSERT INTO `item` VALUES ('100900044', '超梦契约碎片1号');
INSERT INTO `item` VALUES ('100900045', '超梦契约碎片2号');
INSERT INTO `item` VALUES ('100900046', '超梦契约碎片3号');
INSERT INTO `item` VALUES ('100900047', '超梦契约碎片4号');
INSERT INTO `item` VALUES ('100900048', '梦幻契约碎片1号');
INSERT INTO `item` VALUES ('100900049', '梦幻契约碎片2号');
INSERT INTO `item` VALUES ('100900050', '梦幻契约碎片3号');
INSERT INTO `item` VALUES ('100900051', '梦幻契约碎片4号');
INSERT INTO `item` VALUES ('101000001', '喷火龙缰绳');
INSERT INTO `item` VALUES ('101000002', '风速狗缰绳');
INSERT INTO `item` VALUES ('101000003', '嘟嘟利缰绳');
INSERT INTO `item` VALUES ('101000004', '铁甲犀牛缰绳');
INSERT INTO `item` VALUES ('101000005', '肯泰罗缰绳');
INSERT INTO `item` VALUES ('101000006', '化石翼龙缰绳');
INSERT INTO `item` VALUES ('101000007', '闪电鸟缰绳');
INSERT INTO `item` VALUES ('101000008', '急冻鸟缰绳');
INSERT INTO `item` VALUES ('101000009', '火焰鸟缰绳');
INSERT INTO `item` VALUES ('101000010', '乘龙缰绳');
INSERT INTO `item` VALUES ('101000011', '卡比兽缰绳');
INSERT INTO `item` VALUES ('101000012', '暴鲤龙缰绳');
INSERT INTO `item` VALUES ('101000013', '闪光喷火龙缰绳');
INSERT INTO `item` VALUES ('101100001', '喷火龙缰绳碎片1号');
INSERT INTO `item` VALUES ('101100002', '喷火龙缰绳碎片2号');
INSERT INTO `item` VALUES ('101100003', '喷火龙缰绳碎片3号');
INSERT INTO `item` VALUES ('101100004', '喷火龙缰绳碎片4号');
INSERT INTO `item` VALUES ('101100005', '风速狗缰绳碎片1号');
INSERT INTO `item` VALUES ('101100006', '风速狗缰绳碎片2号');
INSERT INTO `item` VALUES ('101100007', '风速狗缰绳碎片3号');
INSERT INTO `item` VALUES ('101100008', '风速狗缰绳碎片4号');
INSERT INTO `item` VALUES ('101100009', '嘟嘟利缰绳碎片1号');
INSERT INTO `item` VALUES ('101100010', '嘟嘟利缰绳碎片2号');
INSERT INTO `item` VALUES ('101100011', '嘟嘟利缰绳碎片3号');
INSERT INTO `item` VALUES ('101100012', '嘟嘟利缰绳碎片4号');
INSERT INTO `item` VALUES ('101100013', '铁甲犀牛缰绳碎片1号');
INSERT INTO `item` VALUES ('101100014', '铁甲犀牛缰绳碎片2号');
INSERT INTO `item` VALUES ('101100015', '铁甲犀牛缰绳碎片3号');
INSERT INTO `item` VALUES ('101100016', '铁甲犀牛缰绳碎片4号');
INSERT INTO `item` VALUES ('101100017', '肯泰罗缰绳碎片1号');
INSERT INTO `item` VALUES ('101100018', '肯泰罗缰绳碎片2号');
INSERT INTO `item` VALUES ('101100019', '肯泰罗缰绳碎片3号');
INSERT INTO `item` VALUES ('101100020', '肯泰罗缰绳碎片4号');
INSERT INTO `item` VALUES ('101100021', '化石翼龙缰绳碎片1号');
INSERT INTO `item` VALUES ('101100022', '化石翼龙缰绳碎片2号');
INSERT INTO `item` VALUES ('101100023', '化石翼龙缰绳碎片3号');
INSERT INTO `item` VALUES ('101100024', '化石翼龙缰绳碎片4号');
INSERT INTO `item` VALUES ('101100025', '闪电鸟缰绳碎片1号');
INSERT INTO `item` VALUES ('101100026', '闪电鸟缰绳碎片2号');
INSERT INTO `item` VALUES ('101100027', '闪电鸟缰绳碎片3号');
INSERT INTO `item` VALUES ('101100028', '闪电鸟缰绳碎片4号');
INSERT INTO `item` VALUES ('101100029', '急冻鸟缰绳碎片1号');
INSERT INTO `item` VALUES ('101100030', '急冻鸟缰绳碎片2号');
INSERT INTO `item` VALUES ('101100031', '急冻鸟缰绳碎片3号');
INSERT INTO `item` VALUES ('101100032', '急冻鸟缰绳碎片4号');
INSERT INTO `item` VALUES ('101100033', '火焰鸟缰绳碎片1号');
INSERT INTO `item` VALUES ('101100034', '火焰鸟缰绳碎片2号');
INSERT INTO `item` VALUES ('101100035', '火焰鸟缰绳碎片3号');
INSERT INTO `item` VALUES ('101100036', '火焰鸟缰绳碎片4号');
INSERT INTO `item` VALUES ('101100037', '乘龙缰绳碎片1号');
INSERT INTO `item` VALUES ('101100038', '乘龙缰绳碎片2号');
INSERT INTO `item` VALUES ('101100039', '乘龙缰绳碎片3号');
INSERT INTO `item` VALUES ('101100040', '乘龙缰绳碎片4号');
INSERT INTO `item` VALUES ('101100041', '卡比兽缰绳碎片1号');
INSERT INTO `item` VALUES ('101100042', '卡比兽缰绳碎片2号');
INSERT INTO `item` VALUES ('101100043', '卡比兽缰绳碎片3号');
INSERT INTO `item` VALUES ('101100044', '卡比兽缰绳碎片4号');
INSERT INTO `item` VALUES ('101100045', '暴鲤龙缰绳碎片1号');
INSERT INTO `item` VALUES ('101100046', '暴鲤龙缰绳碎片2号');
INSERT INTO `item` VALUES ('101100047', '暴鲤龙缰绳碎片3号');
INSERT INTO `item` VALUES ('101100048', '暴鲤龙缰绳碎片4号');
INSERT INTO `item` VALUES ('101100049', '闪光喷火龙缰绳碎片1号');
INSERT INTO `item` VALUES ('101100050', '闪光喷火龙缰绳碎片2号');
INSERT INTO `item` VALUES ('101100051', '闪光喷火龙缰绳碎片3号');
INSERT INTO `item` VALUES ('101100052', '闪光喷火龙缰绳碎片4号');
INSERT INTO `item` VALUES ('169000001', '随机宝箱');
INSERT INTO `item` VALUES ('169000002', '选择宝箱');
INSERT INTO `item` VALUES ('169000003', '随机数量宝箱');
INSERT INTO `item` VALUES ('169000004', '小弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000005', '小弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000006', '小弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000007', '小弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000008', '中弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000009', '中弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000010', '中弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000011', '中弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000012', '大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000013', '大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000014', '大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000015', '大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000016', '特大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000017', '特大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000018', '特大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000019', '特大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000020', '进化石碎片随机宝箱');
INSERT INTO `item` VALUES ('169000021', '进化石碎片选择宝箱');
INSERT INTO `item` VALUES ('169000022', '绿色绘本宝箱');
INSERT INTO `item` VALUES ('169000023', '蓝色绘本宝箱');
INSERT INTO `item` VALUES ('169000024', '蓝色绘本拼图宝箱');
INSERT INTO `item` VALUES ('169000025', '紫色拼图宝箱1号');
INSERT INTO `item` VALUES ('169000026', '紫色拼图宝箱2号');
INSERT INTO `item` VALUES ('169000027', '紫色拼图宝箱3号');
INSERT INTO `item` VALUES ('169000028', '橙色拼图宝箱');
INSERT INTO `item` VALUES ('169000029', '小弹珠宝箱');
INSERT INTO `item` VALUES ('169000030', '中弹珠宝箱');
INSERT INTO `item` VALUES ('169000031', '大弹珠宝箱');
INSERT INTO `item` VALUES ('169000032', '特大弹珠宝箱');
INSERT INTO `item` VALUES ('169000033', '小弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000034', '中弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000035', '大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000036', '特大弹珠选择宝箱');
INSERT INTO `item` VALUES ('169000037', '进化石碎片宝箱');
INSERT INTO `item` VALUES ('169000038', '进化石选择宝箱');
INSERT INTO `item` VALUES ('169000039', '学习机宝箱');
INSERT INTO `item` VALUES ('169000040', '学习机选择宝箱');
INSERT INTO `item` VALUES ('169000041', '珍惜道具宝箱');
INSERT INTO `item` VALUES ('169000042', '恢复宝箱');
INSERT INTO `item` VALUES ('169000043', '口袋财富宝箱');
INSERT INTO `item` VALUES ('169000044', '口袋体力宝箱');
INSERT INTO `item` VALUES ('169000045', '晋级宝箱');
INSERT INTO `item` VALUES ('169000046', '契约宝箱');
INSERT INTO `item` VALUES ('169000047', '喷火龙缰绳宝箱');

-- ----------------------------
-- Table structure for item_detailed_summary_cache
-- ----------------------------
DROP TABLE IF EXISTS `item_detailed_summary_cache`;
CREATE TABLE `item_detailed_summary_cache` (
  `content` longtext COMMENT '物品 (itemid_1;)(Strx10,)',
  `type1` varchar(255) DEFAULT NULL COMMENT '类型',
  `num` int(11) DEFAULT NULL COMMENT '数量',
  `t_time` datetime DEFAULT NULL COMMENT '时间',
  `type` int(255) DEFAULT NULL COMMENT '1，2 表示获得 ， -1，-2 表示消耗 ',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='########  物品详细物品统计 缓冲表';

-- ----------------------------
-- Table structure for log_db
-- ----------------------------
DROP TABLE IF EXISTS `log_db`;
CREATE TABLE `log_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` longtext COMMENT '数据库查询链接',
  `root` varchar(255) DEFAULT NULL COMMENT 'root 名称',
  `pwd` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='# 日志 db ';

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `account_id` bigint(255) DEFAULT NULL COMMENT '用户id',
  `server_id` int(11) DEFAULT NULL COMMENT '登录服务器',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `register_time` datetime DEFAULT NULL COMMENT '帐号注册时间',
  `last_log_time` datetime DEFAULT NULL COMMENT '帐号最近登录的时间'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `type` int(255) DEFAULT NULL COMMENT '类型 1:邮件，2:GM指令, 3:虚拟支付',
  `log_time` datetime DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for money_summary
-- ----------------------------
DROP TABLE IF EXISTS `money_summary`;
CREATE TABLE `money_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL COMMENT '日期',
  `sj_hd` bigint(255) DEFAULT NULL COMMENT '水晶产出',
  `sj_xh` bigint(255) DEFAULT NULL COMMENT '水晶消耗',
  `sj_lc` bigint(255) DEFAULT NULL COMMENT '水晶留存',
  `ks_hd` bigint(255) DEFAULT NULL COMMENT '矿石产出',
  `ks_xh` bigint(255) DEFAULT NULL COMMENT '矿石消耗',
  `ks_lc` bigint(255) DEFAULT NULL COMMENT '矿石留存',
  `jb_hd` bigint(255) DEFAULT NULL COMMENT '金币产出',
  `jb_xh` bigint(255) DEFAULT NULL COMMENT '金币消耗',
  `jb_lc` bigint(255) DEFAULT NULL COMMENT '金币留存',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='# 货币(产出&消耗)汇总';


-- ----------------------------
-- Table structure for n_pay
-- ----------------------------
DROP TABLE IF EXISTS `n_pay`;
CREATE TABLE `n_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `n_user_pay` int(255) DEFAULT NULL COMMENT '新增付费玩家(去重)',
  `n_user_pay_r` int(255) DEFAULT NULL COMMENT '新增付费玩家(不去重)=付费次数',
  `n_user_pay_money` int(255) DEFAULT NULL COMMENT '新增玩家付费总额',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '平台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


-- ----------------------------
-- Table structure for new_u_change
-- ----------------------------
DROP TABLE IF EXISTS `new_u_change`;
CREATE TABLE `new_u_change` (
  `t_time` datetime DEFAULT NULL COMMENT '时间',
  `new_drive` bigint(255) DEFAULT NULL COMMENT '新增设备',
  `new_reg` bigint(255) DEFAULT NULL COMMENT '新增注册',
  `login_u` bigint(255) DEFAULT NULL COMMENT '登录用户数',
  `valid_u` bigint(255) DEFAULT NULL COMMENT '有效用户',
  `pay_u` bigint(255) DEFAULT NULL COMMENT '付费用户',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ns_dau_summary
-- ----------------------------
DROP TABLE IF EXISTS `ns_dau_summary`;
CREATE TABLE `ns_dau_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `s_id` int(11) DEFAULT NULL COMMENT '服务器 server_id',
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '平台',
  `open_time` date DEFAULT NULL COMMENT '开服时间',
  `dau_day2` int(255) DEFAULT NULL COMMENT '第2日活跃',
  `dau_pay_day2` int(255) DEFAULT NULL COMMENT '第2日活跃付费人数',
  `dau_day3` int(255) DEFAULT NULL COMMENT '第3日活跃人数',
  `dau_pay_day3` int(255) DEFAULT NULL COMMENT '第3日活跃付费人数',
  `dau_day7` int(255) DEFAULT NULL COMMENT '第7日活跃人数',
  `dau_pay_day7` int(255) DEFAULT NULL COMMENT '第7日活跃付费人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='# 新服活跃汇总表';

-- ----------------------------
-- Table structure for ns_income_summary
-- ----------------------------
DROP TABLE IF EXISTS `ns_income_summary`;
CREATE TABLE `ns_income_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `s_id` int(11) DEFAULT NULL COMMENT '服务器 server_id',
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '平台',
  `open_time` date DEFAULT NULL COMMENT '开服时间',
  `reg_count_day2` int(255) DEFAULT NULL COMMENT '前2日注册',
  `reg_pay_count_day2` int(255) DEFAULT NULL COMMENT '前2日注册支付人数',
  `reg_count_day3` int(255) DEFAULT NULL COMMENT '前3日支付人数',
  `reg_pay_count_day3` int(255) DEFAULT NULL COMMENT '前3日注册支付人数',
  `reg_count_day7` int(255) DEFAULT NULL COMMENT '前7日注册',
  `reg_pay_count_day7` int(255) DEFAULT NULL COMMENT '前7日注册支付人数',
  `paySum_day1` int(255) DEFAULT NULL COMMENT '第1日收入',
  `paySum_day2` int(255) DEFAULT NULL COMMENT '第2日收入',
  `paySum_day3` int(255) DEFAULT NULL COMMENT '第3日收入',
  `paySum_day4` int(255) DEFAULT NULL COMMENT '第4日收入',
  `paySum_day5` int(255) DEFAULT NULL COMMENT '第5日收入',
  `paySum_day6` int(255) DEFAULT NULL COMMENT '第6日收入',
  `paySum_day7` int(255) DEFAULT NULL COMMENT '第7日收入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='# 新服收入汇总表';


-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id,自动增长',
  `temp_order_id` bigint(20) NOT NULL COMMENT '临时订单表id',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  `finish_time` datetime NOT NULL COMMENT '订单完成时间',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_price` float NOT NULL COMMENT '商品价格',
  `amount` float NOT NULL COMMENT '实际支付金额,在平台支付完成后由平台通知',
  `server_id` int(11) NOT NULL COMMENT '区服id',
  `account_id` int(11) NOT NULL COMMENT '玩家账号id',
  `account_name` varchar(128) NOT NULL COMMENT '玩家账号名',
  `player_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '玩家角色名',
  `platform` varchar(16) NOT NULL COMMENT '平台标识,玩家账号注册时的平台',
  `platform_order_no` varchar(64) NOT NULL COMMENT '平台订单号',
  `pay_type` varchar(10) DEFAULT NULL COMMENT '支付方式,由平台提供',
  `server_state` int(11) NOT NULL COMMENT '游戏服状态,0正常,1未能连接到游戏服,2游戏服返回异常',
  `platform_order_no_sign` varchar(128) NOT NULL COMMENT '生成方式,platform+platform_order_no,用来检查重复订单',
  `ext` varchar(512) DEFAULT NULL COMMENT '扩展内容',
  PRIMARY KEY (`order_id`),
  KEY `idx_ftime` (`finish_time`),
  KEY `idx_amount` (`amount`),
  KEY `idx_acid_acname_pname` (`account_id`,`account_name`,`player_name`),
  KEY `idx_ platform` (`platform`),
  KEY `idx_platform_order_no_sign` (`platform_order_no_sign`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for pay_lty
-- ----------------------------
DROP TABLE IF EXISTS `pay_lty`;
CREATE TABLE `pay_lty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `server_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `user_add` int(255) DEFAULT NULL,
  `pay_01` int(255) DEFAULT NULL,
  `pay_02` int(255) DEFAULT NULL,
  `pay_03` int(255) DEFAULT NULL,
  `pay_04` int(255) DEFAULT NULL,
  `pay_05` int(255) DEFAULT NULL,
  `pay_06` int(255) DEFAULT NULL,
  `pay_07` int(255) DEFAULT NULL,
  `pay_08` int(255) DEFAULT NULL,
  `pay_09` int(255) DEFAULT NULL,
  `pay_10` int(255) DEFAULT NULL,
  `pay_11` int(255) DEFAULT NULL,
  `pay_12` int(255) DEFAULT NULL,
  `pay_13` int(255) DEFAULT NULL,
  `pay_14` int(255) DEFAULT NULL,
  `pay_15` int(255) DEFAULT NULL,
  `pay_16` int(255) DEFAULT NULL,
  `pay_17` int(255) DEFAULT NULL,
  `pay_18` int(255) DEFAULT NULL,
  `pay_19` int(255) DEFAULT NULL,
  `pay_20` int(255) DEFAULT NULL,
  `pay_21` int(255) DEFAULT NULL,
  `pay_22` int(255) DEFAULT NULL,
  `pay_23` int(255) DEFAULT NULL,
  `pay_24` int(255) DEFAULT NULL,
  `pay_25` int(255) DEFAULT NULL,
  `pay_26` int(255) DEFAULT NULL,
  `pay_27` int(255) DEFAULT NULL,
  `pay_28` int(255) DEFAULT NULL,
  `pay_29` int(255) DEFAULT NULL,
  `pay_30` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='### PAY LTV  30 日支付LVT ';


-- ----------------------------
-- Table structure for preset
-- ----------------------------
DROP TABLE IF EXISTS `preset`;
CREATE TABLE `preset` (
  `id` int(11) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `info` text,
  `remark` char(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


-- ----------------------------
-- Table structure for quest_info
-- ----------------------------
DROP TABLE IF EXISTS `quest_info`;
CREATE TABLE `quest_info` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='########### 任务步骤表';

-- ----------------------------
-- Records of quest_info
-- ----------------------------
INSERT INTO `quest_info` VALUES ('200100002', '挑战劲敌');
INSERT INTO `quest_info` VALUES ('200100003', '开始旅行');
INSERT INTO `quest_info` VALUES ('200100004', '捕捉教学');
INSERT INTO `quest_info` VALUES ('200100005', '1号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100006', '1号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100007', '1号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100008', '1号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100009', '1号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100010', '找到小美');
INSERT INTO `quest_info` VALUES ('200100011', '2号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100012', '2号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100013', '2号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100014', '2号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100015', '2号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100016', '找到童子军');
INSERT INTO `quest_info` VALUES ('200100017', '协助研究');
INSERT INTO `quest_info` VALUES ('200100018', '3号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100019', '3号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100020', '3号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100021', '3号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100022', '3号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100023', '找到童子军');
INSERT INTO `quest_info` VALUES ('200100024', '证明实力');
INSERT INTO `quest_info` VALUES ('200100025', '找到捕虫少年');
INSERT INTO `quest_info` VALUES ('200100026', '常磐市道馆的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100027', '常磐市道馆的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100028', '常磐市道馆的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100029', '常磐市道馆的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100030', '常磐市道馆的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100031', '挑战火箭队精英干部');
INSERT INTO `quest_info` VALUES ('200100032', '奇怪的人');
INSERT INTO `quest_info` VALUES ('200100033', '强大的训练师');
INSERT INTO `quest_info` VALUES ('200100034', '火箭队的来龙去脉');
INSERT INTO `quest_info` VALUES ('200100035', '4号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100036', '4号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100037', '4号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100038', '4号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100039', '4号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100040', '出发到5号路');
INSERT INTO `quest_info` VALUES ('200100041', '5号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100042', '5号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100043', '5号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100044', '5号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100045', '5号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100046', '汇报成果');
INSERT INTO `quest_info` VALUES ('200100047', '修炼的成果');
INSERT INTO `quest_info` VALUES ('200100060', '6号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100061', '6号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100062', '6号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100063', '6号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100064', '6号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100065', '6号道路的战斗（6）');
INSERT INTO `quest_info` VALUES ('200100066', '6号道路的战斗（7）');
INSERT INTO `quest_info` VALUES ('200100067', '6号道路的战斗（8）');
INSERT INTO `quest_info` VALUES ('200100068', '过关斩将');
INSERT INTO `quest_info` VALUES ('200100069', '证明实力（3）');
INSERT INTO `quest_info` VALUES ('200100070', '证明实力（4）');
INSERT INTO `quest_info` VALUES ('200100084', '尼比市');
INSERT INTO `quest_info` VALUES ('200100085', '前往尼比市道馆');
INSERT INTO `quest_info` VALUES ('200100086', '挑战尼比市道馆的馆主');
INSERT INTO `quest_info` VALUES ('200100087', '成功挑战第一个馆主');
INSERT INTO `quest_info` VALUES ('200100088', '首战告捷');
INSERT INTO `quest_info` VALUES ('200100089', '再遇劲敌');
INSERT INTO `quest_info` VALUES ('200100090', '7号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100091', '7号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100092', '7号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100093', '7号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100094', '7号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100095', '7号道路的战斗（6）');
INSERT INTO `quest_info` VALUES ('200100096', '7号道路的战斗（7）');
INSERT INTO `quest_info` VALUES ('200100097', '7号道路的战斗（8）');
INSERT INTO `quest_info` VALUES ('200100098', '7号路的敌人');
INSERT INTO `quest_info` VALUES ('200100099', '证明实力（5）');
INSERT INTO `quest_info` VALUES ('200100100', '出发到8号路');
INSERT INTO `quest_info` VALUES ('200100101', '8号道路的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100102', '8号道路的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100103', '8号道路的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100104', '8号道路的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100105', '8号道路的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100106', '8号道路的战斗（6）');
INSERT INTO `quest_info` VALUES ('200100107', '8号道路的战斗（7）');
INSERT INTO `quest_info` VALUES ('200100108', '8号道路的战斗（8）');
INSERT INTO `quest_info` VALUES ('200100109', '精灵收集者');
INSERT INTO `quest_info` VALUES ('200100110', '捕捉技巧');
INSERT INTO `quest_info` VALUES ('200100117', '9号路的冒险');
INSERT INTO `quest_info` VALUES ('200100118', '9号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100119', '童子军的困难');
INSERT INTO `quest_info` VALUES ('200100120', '童子军的目标');
INSERT INTO `quest_info` VALUES ('200100121', '奇怪的拦路人');
INSERT INTO `quest_info` VALUES ('200100122', '偶遇火箭队');
INSERT INTO `quest_info` VALUES ('200100123', '驱赶恶霸');
INSERT INTO `quest_info` VALUES ('200100124', '10号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100125', '10号的实力验证');
INSERT INTO `quest_info` VALUES ('200100126', '10号的实力验证（2）');
INSERT INTO `quest_info` VALUES ('200100127', '挑战劲敌');
INSERT INTO `quest_info` VALUES ('200100128', '火箭队的阻拦');
INSERT INTO `quest_info` VALUES ('200100129', '11号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100130', '欢迎来到华蓝市');
INSERT INTO `quest_info` VALUES ('200100131', '前往华蓝市道馆');
INSERT INTO `quest_info` VALUES ('200100132', '挑战华蓝市道馆');
INSERT INTO `quest_info` VALUES ('200100133', '大木博士的研究日记');
INSERT INTO `quest_info` VALUES ('200100134', '大木博士的建议');
INSERT INTO `quest_info` VALUES ('200100135', '12号道路的起点');
INSERT INTO `quest_info` VALUES ('200100136', '12号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100137', '战斗结束后');
INSERT INTO `quest_info` VALUES ('200100138', '12号道路的旅程');
INSERT INTO `quest_info` VALUES ('200100139', '童子军的建议');
INSERT INTO `quest_info` VALUES ('200100140', '遇见火箭队');
INSERT INTO `quest_info` VALUES ('200100141', '击败火箭队');
INSERT INTO `quest_info` VALUES ('200100142', '13号道路的旅程');
INSERT INTO `quest_info` VALUES ('200100143', '13号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100144', '找到少年');
INSERT INTO `quest_info` VALUES ('200100145', '少年的无奈');
INSERT INTO `quest_info` VALUES ('200100146', '13号道路的修炼者（1）');
INSERT INTO `quest_info` VALUES ('200100147', '13号道路的修炼者（2）');
INSERT INTO `quest_info` VALUES ('200100148', '13号道路的修炼者（3）');
INSERT INTO `quest_info` VALUES ('200100149', '出发到14号道路');
INSERT INTO `quest_info` VALUES ('200100150', '14号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100151', '火箭队无处不在');
INSERT INTO `quest_info` VALUES ('200100152', '火箭队无处不在（3）');
INSERT INTO `quest_info` VALUES ('200100153', '火箭队无处不在（3）');
INSERT INTO `quest_info` VALUES ('200100154', '14号道路的结束');
INSERT INTO `quest_info` VALUES ('200100155', '15号道路的险阻');
INSERT INTO `quest_info` VALUES ('200100156', '15号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100157', '回去协助少年');
INSERT INTO `quest_info` VALUES ('200100158', '捕虫少年的建议');
INSERT INTO `quest_info` VALUES ('200100159', '需要帮助的少年');
INSERT INTO `quest_info` VALUES ('200100160', '协助短裤少年');
INSERT INTO `quest_info` VALUES ('200100161', '需要帮助的少年（2）');
INSERT INTO `quest_info` VALUES ('200100162', '捕虫少年的捕捉计划');
INSERT INTO `quest_info` VALUES ('200100163', '大木博士的研究');
INSERT INTO `quest_info` VALUES ('200100164', '大木博士的研究（2）');
INSERT INTO `quest_info` VALUES ('200100165', '大木博士的研究（3）');
INSERT INTO `quest_info` VALUES ('200100166', '大木博士的研究（4）');
INSERT INTO `quest_info` VALUES ('200100167', '16号道路的旅程');
INSERT INTO `quest_info` VALUES ('200100168', '16号道路的战斗');
INSERT INTO `quest_info` VALUES ('200100169', '16号道路的朋友');
INSERT INTO `quest_info` VALUES ('200100170', '16号道路的野外');
INSERT INTO `quest_info` VALUES ('200100171', '战斗训练');
INSERT INTO `quest_info` VALUES ('200100172', '捕捉能手');
INSERT INTO `quest_info` VALUES ('200100173', '联盟大师');
INSERT INTO `quest_info` VALUES ('200100174', '四大天王');
INSERT INTO `quest_info` VALUES ('200100175', '精灵恢复');
INSERT INTO `quest_info` VALUES ('200100176', '道馆之战');
INSERT INTO `quest_info` VALUES ('200100178', '捕捉新手');
INSERT INTO `quest_info` VALUES ('200100179', '捕捉小能手');
INSERT INTO `quest_info` VALUES ('200100180', '捕捉爱好者');
INSERT INTO `quest_info` VALUES ('200100181', '捕捉狂热者');
INSERT INTO `quest_info` VALUES ('200100182', '捕捉大师');
INSERT INTO `quest_info` VALUES ('200100183', '捕捉宗师');
INSERT INTO `quest_info` VALUES ('200100184', '新手训练家');
INSERT INTO `quest_info` VALUES ('200100185', '初级训练家');
INSERT INTO `quest_info` VALUES ('200100186', '中级训练家');
INSERT INTO `quest_info` VALUES ('200100187', '高级训练家');
INSERT INTO `quest_info` VALUES ('200100188', '特级训练家');
INSERT INTO `quest_info` VALUES ('200100189', '精灵训练大师');
INSERT INTO `quest_info` VALUES ('200100190', '四大天王');
INSERT INTO `quest_info` VALUES ('200100191', '四大天王');
INSERT INTO `quest_info` VALUES ('200100192', '四大天王');
INSERT INTO `quest_info` VALUES ('200100193', '四大天王');
INSERT INTO `quest_info` VALUES ('200100194', '四大天王');
INSERT INTO `quest_info` VALUES ('200100195', '四大天王');
INSERT INTO `quest_info` VALUES ('200100196', '道馆挑战');
INSERT INTO `quest_info` VALUES ('200100197', '道馆挑战');
INSERT INTO `quest_info` VALUES ('200100198', '道馆挑战');
INSERT INTO `quest_info` VALUES ('200100199', '道馆挑战');
INSERT INTO `quest_info` VALUES ('200100200', '道馆挑战');
INSERT INTO `quest_info` VALUES ('200100201', '道馆挑战');
INSERT INTO `quest_info` VALUES ('200100202', '冠军之路');
INSERT INTO `quest_info` VALUES ('200100203', '冠军之路');
INSERT INTO `quest_info` VALUES ('200100204', '冠军之路');
INSERT INTO `quest_info` VALUES ('200100205', '冠军之路');
INSERT INTO `quest_info` VALUES ('200100206', '冠军之路');
INSERT INTO `quest_info` VALUES ('200100207', '冠军之路');
INSERT INTO `quest_info` VALUES ('200100208', '对战中心');
INSERT INTO `quest_info` VALUES ('200100209', '对战中心');
INSERT INTO `quest_info` VALUES ('200100210', '对战中心');
INSERT INTO `quest_info` VALUES ('200100211', '对战中心');
INSERT INTO `quest_info` VALUES ('200100212', '对战中心');
INSERT INTO `quest_info` VALUES ('200100213', '对战中心');
INSERT INTO `quest_info` VALUES ('200100214', '野外对战专家');
INSERT INTO `quest_info` VALUES ('200100215', '野外对战专家');
INSERT INTO `quest_info` VALUES ('200100216', '野外对战专家');
INSERT INTO `quest_info` VALUES ('200100217', '野外对战专家');
INSERT INTO `quest_info` VALUES ('200100218', '野外对战专家');
INSERT INTO `quest_info` VALUES ('200100219', '野外对战专家');
INSERT INTO `quest_info` VALUES ('200100220', '新手收集者');
INSERT INTO `quest_info` VALUES ('200100221', '初级收集者');
INSERT INTO `quest_info` VALUES ('200100222', '中级收集者');
INSERT INTO `quest_info` VALUES ('200100223', '高级收集者');
INSERT INTO `quest_info` VALUES ('200100224', '特级收集者');
INSERT INTO `quest_info` VALUES ('200100225', '收集大师');
INSERT INTO `quest_info` VALUES ('200100226', '收集图鉴');
INSERT INTO `quest_info` VALUES ('200100227', '收集图鉴');
INSERT INTO `quest_info` VALUES ('200100228', '收集图鉴');
INSERT INTO `quest_info` VALUES ('200100229', '收集图鉴');
INSERT INTO `quest_info` VALUES ('200100230', '收集图鉴');
INSERT INTO `quest_info` VALUES ('200100231', '收集图鉴');


-- ----------------------------
-- Table structure for quest_summary
-- ----------------------------
DROP TABLE IF EXISTS `quest_summary`;
CREATE TABLE `quest_summary` (
  `quest_id` bigint(20) DEFAULT NULL COMMENT '任务id',
  `t_time` datetime DEFAULT NULL COMMENT '创建时间',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `sum_player` bigint(255) DEFAULT NULL COMMENT '玩家数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='##### 任务表汇总';

-- ----------------------------
-- Table structure for report_sql
-- ----------------------------
DROP TABLE IF EXISTS `report_sql`;
CREATE TABLE `report_sql` (
  `id` int(11) NOT NULL,
  `sql` longtext COMMENT 'sql 语句',
  `title_en` longtext COMMENT '英文表头',
  `title_ch` longtext COMMENT '中文表头',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='## 统计报表 sql';

-- ----------------------------
-- Records of report_sql
-- ----------------------------
INSERT INTO `report_sql` VALUES ('1', '(  SELECT  account.platform,  IFNULL(T4.device_reg, 0) AS device_reg,  IFNULL(T.absNewUser, 0) + IFNULL(T7.notInGame, 0) AS newRegisterUser,  T.absNewUser,  T8.DAU,  T2.paySum,  T2.payUser,  T2.payCount,  FORMAT(T2.payCount / T2.payUser, 2) AS avgPay,  FORMAT(T2.paySum / T2.payUser, 2) AS arppu,  T3.newPay,  T3.newPPayCount,  FORMAT(  T3.newPay / T3.newPPayCount,  2  ) AS arpu,  FORMAT(T1.retained1, 2) AS retained1,  FORMAT(T1.retained3, 2) AS retained3,  FORMAT(T1.retained7, 2) AS retained7,  FORMAT(T1.retained14, 2) AS retained14,  FORMAT(T1.retained30, 2) AS retained30  FROM  account  LEFT JOIN (  SELECT  platform,  DATE_FORMAT(reg_time, \"%Y-%m-%d\") AS t_time,  COUNT(  DISTINCT device_reg.deviceUI  ) AS device_reg  FROM  device_reg  WHERE  reg_time {0}   GROUP BY  platform  ) AS T4 ON T4.platform = account.platform  LEFT JOIN (  SELECT  id,  platform,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS absNewUser  FROM  account  WHERE  account.create_time {0}  AND account.prev_srv_id LIKE \"{1}\"  AND account.prev_srv_id NOT LIKE \"0\"  GROUP BY  platform  ) AS T ON T.platform = account.platform  LEFT JOIN (  SELECT  platform,  SUM(retained1) / SUM(dru) * 100 AS retained1,  SUM(retained3) / SUM(dru) * 100 AS retained3,  SUM(retained7) / SUM(dru) * 100 AS retained7,  SUM(retained14) / SUM(dru) * 100 AS retained14,  SUM(retained30) / SUM(dru) * 100 AS retained30  FROM  retention  WHERE  create_date {0}  AND server_id LIKE \"{1}\"  GROUP BY  platform  ) AS T1 ON T1.platform = account.platform  LEFT JOIN (  SELECT  t.platform,  sum(t.amount) AS paySum,  count(DISTINCT t.account_id) AS payUser,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.platform  ) AS T2 ON T2.platform = account.platform  LEFT JOIN (  SELECT  SUM(V2.payNew) AS newPay,  V.platform,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {0}  AND prev_srv_id LIKE \"{1}\"  GROUP BY  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.platform  ) AS T3 ON T3.platform = account.platform  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS notInGame,  platform  FROM  account  WHERE  account.create_time {0}  AND account.prev_srv_id LIKE \"0\"  GROUP BY  platform  ) AS T7 ON T7.platform = account.platform  LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS c_time,  SUM(dau) AS DAU,  platform  FROM  dau_summary  WHERE  t_time {0}  AND server_id LIKE \"{1}\"  GROUP BY  platform  ) AS T8 ON T8.platform = account.platform  WHERE  account.platform LIKE \"{2}\"  GROUP BY  account.platform ) UNION ALL  (  SELECT  \"总计:\" AS platform,  SUM(r.device_reg) AS device_reg,  SUM(r.newRegisterUser) AS newRegisterUser,  SUM(r.absNewUser) AS absNewUser,  SUM(r.DAU) AS DAU,  SUM(r.paySum) AS paySum,  SUM(r.payUser) AS payUser,  SUM(r.payCount) AS payCount,  FORMAT(  SUM(r.payCount) / SUM(r.payUser),  2  ) AS avgPay,  FORMAT(  SUM(r.paySum) / SUM(r.payUser),  2  ) AS arppu,  SUM(r.newPay) AS newPay,  SUM(r.newPPayCount) AS newPPayCount,  FORMAT(  SUM(r.newPay) / SUM(r.newPPayCount),  2  ) AS arpu,  FORMAT(  SUM(r.retained1) / SUM(r.dru) * 100,  2  ) AS retained1,  FORMAT(  SUM(r.retained3) / SUM(r.dru) * 100,  2  ) AS retained3,  FORMAT(  SUM(r.retained7) / SUM(r.dru) * 100,  2  ) AS retained7,  FORMAT(  SUM(r.retained14) / SUM(r.dru) * 100,  2  ) AS retained14,  FORMAT(  SUM(r.retained30) / SUM(r.dru) * 100,  2  ) AS retained30  FROM  (  (  SELECT  account.platform,  IFNULL(T4.device_reg, 0) AS device_reg,  IFNULL(T.absNewUser, 0) + IFNULL(T7.notInGame, 0) AS newRegisterUser,  T.absNewUser,  T8.DAU,  T2.paySum,  T2.payUser,  T3.newPay,  T3.newPPayCount,  T2.payCount,  T1.retained1,  T1.retained3,  T1.retained7,  T1.retained14,  T1.retained30,  T1.dru  FROM  account  LEFT JOIN (  SELECT  platform,  DATE_FORMAT(reg_time, \"%Y-%m-%d\") AS t_time,  COUNT(  DISTINCT device_reg.deviceUI  ) AS device_reg  FROM  device_reg  WHERE  reg_time {0}  GROUP BY  platform  ) AS T4 ON T4.platform = account.platform  LEFT JOIN (  SELECT  id,  platform,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS absNewUser  FROM  account  WHERE  account.create_time {0}  AND account.prev_srv_id LIKE \"{1}\"  AND account.prev_srv_id NOT LIKE \"0\"  GROUP BY  platform  ) AS T ON T.platform = account.platform  LEFT JOIN (  SELECT  platform,  SUM(retained1) AS retained1,  SUM(retained3) AS retained3,  SUM(retained7) AS retained7,  SUM(retained14) AS retained14,  SUM(retained30) AS retained30,  SUM(dru) AS dru  FROM  retention  WHERE  create_date {0}  AND server_id LIKE \"{1}\"  GROUP BY  platform  ) AS T1 ON T1.platform = account.platform  LEFT JOIN (  SELECT  t.platform,  sum(t.amount) AS paySum,  count(DISTINCT t.account_id) AS payUser,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.platform  ) AS T2 ON T2.platform = account.platform  LEFT JOIN (  SELECT  SUM(V2.payNew) AS newPay,  V.platform,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {0}  AND prev_srv_id LIKE \"{1}\"  GROUP BY  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.platform  ) AS T3 ON T3.platform = account.platform  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS notInGame,  platform  FROM  account  WHERE  account.create_time {0}  AND account.prev_srv_id LIKE \"0\"  GROUP BY  platform  ) AS T7 ON T7.platform = account.platform  LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS c_time,  SUM(dau) AS DAU,  platform  FROM  dau_summary  WHERE  t_time {0}  AND server_id LIKE \"{1}\"  GROUP BY  platform  ) AS T8 ON T8.platform = account.platform  WHERE  account.platform LIKE \"{2}\"  GROUP BY  account.platform  )  ) AS r  ) \r\n', 'platform,device_reg,newRegisterUser,absNewUser,DAU,paySum,payUser,payCount,avgPay,arppu,newPay,newPPayCount,arpu,retained1,retained3,retained7,retained14,retained30', '渠道,设备新增,新增注册,绝对新增,DAU,收入,充值人数,充值次数,人均充值次数,arppu,新增付费金额,新增付费人数,注册arpu,次日留存%,3日留存%,7日留存%,14日留存%,30日留存%');
INSERT INTO `report_sql` VALUES ('2', '(  SELECT  T6.t_time,  IFNULL(T4.device_reg, 0) AS device_reg,  IFNULL(T.absNewUser, 0) + IFNULL(T7.notInGame, 0) AS newRegisterUser,  T.absNewUser,  T8.DAU,  FORMAT(SUM(T9.ACU), 0) AS ACU,  IFNULL(SUM(T9.PCU), 0) AS PCU,  T2.payUser1,  T2.paySum,  FORMAT(  T3.newPay / T3.newPPayCount,  2  ) AS arpu,  FORMAT(T2.paySum / T2.payUser1, 2) AS arppu1,  FORMAT(T2.paySum / T8.DAU, 2) AS DAUARP,  FORMAT((T2.payUser1 / T8.DAU) * 100, 2) AS payRate,  T3.newPay,  T3.newPPayCount,  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0) AS oldPaySum,  IFNULL(T2.payUser1, 0) - IFNULL(T3.newPPayCount, 0) AS oldPayCount,  T1.retained1,  T1.retained3,  T1.retained7,  T1.retained14,  T1.retained30  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  DATE_FORMAT(reg_time, \"%Y-%m-%d\") AS t_time,  COUNT(  DISTINCT device_reg.deviceUI  ) AS device_reg  FROM  device_reg  WHERE  platform LIKE \"{3}\"  AND reg_time {1}  GROUP BY  t_time  ) AS T4 ON T4.t_time = T6.t_time  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS absNewUser  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"{2}\"  AND account.prev_srv_id NOT LIKE \"0\"  GROUP BY  t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS notInGame  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"0\"  GROUP BY  t_time  ) AS T7 ON T.t_time = T7.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(create_date, \"%Y-%m-%d\") AS t_time,  FORMAT(  SUM(retained1) / SUM(dru) * 100,  2  ) AS retained1,  FORMAT(  SUM(retained3) / SUM(dru) * 100,  2  ) AS retained3,  FORMAT(  SUM(retained7) / SUM(dru) * 100,  2  ) AS retained7,  FORMAT(  SUM(retained14) / SUM(dru) * 100,  2  ) AS retained14,  FORMAT(  SUM(retained30) / SUM(dru) * 100,  2  ) AS retained30  FROM  retention  WHERE  create_date {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time  ) AS T1 ON T1.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_name) AS payUser1,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (  SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time  ) AS T3 ON T3.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS c_time,  SUM(dau) AS DAU  FROM  dau_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  c_time  ) AS T8 ON T8.c_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS c_time,  SUM(acu) AS ACU,  SUM(pcu) AS PCU  FROM  acu_pcu_summary  WHERE  t_time {1}  AND server_id LIKE \"{2}\"  GROUP BY  c_time  ) AS T9 ON T9.c_time = T6.t_time  GROUP BY  T6.t_time ) UNION  (  SELECT  \"总计:\" AS t_time,  IFNULL(SUM(T4.device_reg), 0) AS device_reg,  IFNULL(SUM(T.absNewUser), 0) + IFNULL(SUM(T7.notInGame), 0) AS newActiveUser,  SUM(T.absNewUser) absNewUser,  SUM(T8.DAU) AS DAU,  FORMAT(SUM(T9.ACU), 0) AS ACU,  IFNULL(SUM(T9.PCU), 0) AS PCU,  SUM(T2.payUser1) AS payUser1,  SUM(T2.paySum) AS paySum,  FORMAT(  SUM(T3.newPay) / SUM(T3.newPPayCount),  2  ) AS arpu,  FORMAT(  SUM(T2.paySum) / SUM(T2.payUser1),  2  ) AS arppu1,  FORMAT(  SUM(T2.paySum) / SUM(T8.DAU),  2  ) AS DAUARP,  FORMAT(  SUM(T2.payUser1) / SUM(T8.DAU) * 100,  2  ) AS payRate,  SUM(T3.newPay) AS newPay,  SUM(T3.newPPayCount) AS newPPayCount,  IFNULL(SUM(T2.paySum), 0) - IFNULL(SUM(T3.newPay), 0) AS oldPaySum,  IFNULL(SUM(T2.payUser1), 0) - IFNULL(SUM(T3.newPPayCount), 0) AS oldPayCount,  FORMAT(  SUM(T1.retained1) / SUM(dru) * 100,  2  ) AS retained1,  FORMAT(  SUM(T1.retained3) / SUM(dru) * 100,  2  ) AS retained3,  FORMAT(  SUM(T1.retained7) / SUM(dru) * 100,  2  ) AS retained7,  FORMAT(  SUM(T1.retained14) / SUM(dru) * 100,  2  ) AS retained14,  FORMAT(  SUM(T1.retained30) / SUM(dru) * 100,  2  ) AS retained30  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  DATE_FORMAT(reg_time, \"%Y-%m-%d\") AS t_time,  COUNT(  DISTINCT device_reg.deviceUI  ) AS device_reg,  \"0\" AS DAU  FROM  device_reg  WHERE  platform LIKE \"{3}\"  AND reg_time {1}  GROUP BY  t_time  ) AS T4 ON T4.t_time = T6.t_time  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS absNewUser  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"{2}\"  AND account.prev_srv_id NOT LIKE \"0\"  GROUP BY  t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS notInGame  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"0\"  GROUP BY  t_time  ) AS T7 ON T.t_time = T7.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(create_date, \"%Y-%m-%d\") AS t_time,  SUM(retained1) AS retained1,  SUM(retained3) AS retained3,  SUM(retained7) AS retained7,  SUM(retained14) AS retained14,  SUM(retained30) AS retained30,  SUM(dru) AS dru  FROM  retention  WHERE  create_date {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time  ) AS T1 ON T1.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_name) AS payUser1,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (  SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time  ) AS T3 ON T3.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  COUNT(playCount) AS countPCU,  SUM(playCount) AS totalPCU,  MAX(playCount) AS ACU  FROM  acu  WHERE  log_time {1}  AND acu.platform LIKE \"{3}\"  AND acu.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T5 ON T5.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS c_time,  SUM(dau) AS DAU  FROM  dau_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  c_time  ) AS T8 ON T8.c_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS c_time,  SUM(acu) AS ACU,  SUM(pcu) AS PCU  FROM  acu_pcu_summary  WHERE  t_time {1}  AND server_id LIKE \"{2}\"  GROUP BY  c_time  ) AS T9 ON T9.c_time = T6.t_time  ) \r\n', 't_time,device_reg,newRegisterUser,absNewUser,DAU,ACU,PCU,payUser1,paySum,arpu,arppu1,DAUARP,payRate,newPay,newPPayCount,oldPaySum,oldPayCount,retained1,retained3,retained7,retained14,retained30', '时间,设备新增,新增注册(含衮服),绝对新增,DAU,ACU,PCU,付费玩家数,付费金额,ARPU,ARPPU,DAUARPU,付费率,新付费总额,新付费玩家数,老用户付费金额,老付费玩家数,次日留存,3日留存,7日留存,14日留存,30日留存');
INSERT INTO `report_sql` VALUES ('3', '(  SELECT  n.s_name,  DATE_FORMAT(n.open_time, \"%Y-%m-%d\") AS t_time,  n.reg_count_day2,  n.reg_count_day3,  n.reg_count_day7,  n.reg_pay_count_day2,  FORMAT(  n.reg_pay_count_day2 / n.reg_count_day2,  2  ) AS payRate2,  (  n.paySum_day1 + n.paySum_day2  ) / n.reg_pay_count_day2 AS arup2,  n.reg_pay_count_day3,  FORMAT(  n.reg_pay_count_day3 / n.reg_count_day3,  2  ) AS payRate3,  (  n.paySum_day1 + n.paySum_day2 + n.paySum_day3  ) / n.reg_pay_count_day3 AS arup3,  n.reg_pay_count_day7,  FORMAT(  n.reg_pay_count_day7 / n.reg_count_day7,  2  ) AS payRate7,  (  IFNULL(n.paySum_day1, 0) + IFNULL(n.paySum_day2, 0) + IFNULL(n.paySum_day3, 0) + IFNULL(n.paySum_day4, 0) + IFNULL(n.paySum_day5, 0) + IFNULL(n.paySum_day6, 0) + IFNULL(n.paySum_day7, 0)  ) / IFNULL(n.reg_pay_count_day7, 0) AS arup7,  n.paySum_day1,  n.paySum_day2,  n.paySum_day3,  n.paySum_day4,  n.paySum_day5,  n.paySum_day6,  n.paySum_day7,  (  IFNULL(n.paySum_day1, 0) + IFNULL(n.paySum_day2, 0) + IFNULL(n.paySum_day3, 0) + IFNULL(n.paySum_day4, 0) + IFNULL(n.paySum_day5, 0) + IFNULL(n.paySum_day6, 0) + IFNULL(n.paySum_day7, 0)  ) AS sum7Day  FROM  ns_income_summary AS n  WHERE  n.open_time {1} ) UNION ALL  (  SELECT  \"总计\" AS s_name,  \"\" AS t_time,  T.reg_count_day2,  T.reg_count_day3,  T.reg_count_day7,  T.reg_pay_count_day2,  FORMAT(  T.reg_pay_count_day2 / T.reg_count_day2,  2  ) AS payRate2,  (  T.paySum_day1 + T.paySum_day2  ) / T.reg_pay_count_day2 AS arup2,  T.reg_pay_count_day3,  FORMAT(  T.reg_pay_count_day3 / T.reg_count_day3,  2  ) AS payRate3,  (  T.paySum_day1 + T.paySum_day2 + T.paySum_day3  ) / T.reg_pay_count_day3 AS arup3,  T.reg_pay_count_day7,  FORMAT(  T.reg_pay_count_day7 / T.reg_count_day7,  2  ) AS payRate7,  (  T.paySum_day1 + T.paySum_day2 + T.paySum_day3 + T.paySum_day4 + T.paySum_day5 + T.paySum_day6 + T.paySum_day7  ) / T.reg_pay_count_day7 AS arup7,  T.paySum_day1,  T.paySum_day2,  T.paySum_day3,  T.paySum_day4,  T.paySum_day5,  T.paySum_day6,  T.paySum_day7,  (  T.paySum_day1 + T.paySum_day2 + T.paySum_day3 + T.paySum_day4 + T.paySum_day5 + T.paySum_day6 + T.paySum_day7  ) AS sum7Day  FROM  (  SELECT  IFNULL(SUM(n.reg_count_day2), 0) AS reg_count_day2,  IFNULL(SUM(n.reg_count_day3), 0) AS reg_count_day3,  IFNULL(SUM(n.reg_count_day7), 0) AS reg_count_day7,  IFNULL(  SUM(n.reg_pay_count_day2),  0  ) AS reg_pay_count_day2,  IFNULL(  SUM(n.reg_pay_count_day3),  0  ) AS reg_pay_count_day3,  IFNULL(  SUM(n.reg_pay_count_day7),  0  ) AS reg_pay_count_day7,  IFNULL(SUM(n.paySum_day1), 0) AS paySum_day1,  IFNULL(SUM(n.paySum_day2), 0) AS paySum_day2,  IFNULL(SUM(n.paySum_day3), 0) AS paySum_day3,  IFNULL(SUM(n.paySum_day4), 0) AS paySum_day4,  IFNULL(SUM(n.paySum_day5), 0) AS paySum_day5,  IFNULL(SUM(n.paySum_day6), 0) AS paySum_day6,  IFNULL(SUM(n.paySum_day7), 0) AS paySum_day7  FROM  ns_income_summary AS n  WHERE  n.open_time {1}  ) AS T  ) \r\n', 's_name,t_time,reg_count_day2,reg_count_day3,reg_count_day7,reg_pay_count_day2,payRate2,arup2,reg_pay_count_day3,payRate3,arup3,reg_pay_count_day7,payRate7,arup7,paySum_day1,paySum_day2,paySum_day3,paySum_day4,paySum_day5,paySum_day6,paySum_day7,sum7Day', '服务器,开服日期,2日注册,3日注册,7日注册,2日付费人数,2日付费率,2日ARPPU,3日付费人数,3日付费率,3日ARPPU,7日付费人数,7日付费率,7日ARPPU,开服第1天,开服第2天,开服第3天,开服第4天,开服第5天,开服第6天,开服第7天,七日收入总计');
INSERT INTO `report_sql` VALUES ('4', '(  SELECT  n.s_name,  DATE_FORMAT(n.open_time, \"%Y-%m-%d\") AS t_time,  n.dau_day2,  n.dau_day3,  n.dau_day7,  FORMAT(  IFNULL(n.dau_pay_day2, 0) / IFNULL(n.dau_day2, 0),  2  ) AS dauRate2,  FORMAT(  IFNULL(n.dau_pay_day3, 0) / IFNULL(n.dau_day3, 0),  2  ) AS dauRate3,  FORMAT(  IFNULL(n.dau_pay_day7, 0) / IFNULL(n.dau_day7, 0),  2  ) AS dauRate7  FROM  ns_dau_summary AS n  WHERE  n.open_time {0} ) UNION ALL  (  SELECT  \"总计:\" AS s_name,  \"\" AS t_time,  T.dau_day2,  T.dau_day3,  T.dau_day7,  FORMAT(  T.dau_pay_day2 / T.dau_day2,  2  ) AS dauRate2,  FORMAT(  T.dau_pay_day3 / T.dau_day3,  2  ) AS dauRate3,  FORMAT(  T.dau_pay_day7 / T.dau_day7,  2  ) AS dauRate7  FROM  (  SELECT  SUM(n.dau_day2) AS dau_day2,  SUM(n.dau_day3) AS dau_day3,  SUM(n.dau_day7) AS dau_day7,  SUM(n.dau_pay_day2) AS dau_pay_day2,  SUM(n.dau_pay_day3) AS dau_pay_day3,  SUM(n.dau_pay_day7) AS dau_pay_day7  FROM  ns_dau_summary AS n  WHERE  n.open_time {0}  ) AS T  ) \r\n', 's_name,t_time,dau_day2,dau_day3,dau_day7,dauRate2,dauRate3,dauRate7', '服务器,开服日期,两日活跃人数,三日活跃人数,七日活跃人数,两日活跃付费率,三日活跃付费率,七日活跃付费率');
INSERT INTO `report_sql` VALUES ('5', 'SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  IFNULL(new_drive, 0) AS new_drive,  IFNULL(SUM(new_reg), 0) AS new_reg,  FORMAT(IFNULL(SUM(new_reg)/SUM(new_drive) * 100 , 0),2) AS reg_change,  IFNULL(SUM(login_u), 0) AS login_u,  FORMAT(IFNULL(SUM(login_u)/SUM(new_reg) * 100 , 0),2) AS login_change,  IFNULL(SUM(valid_u), 0) AS valid_u,  FORMAT(IFNULL(SUM(valid_u)/SUM(login_u) * 100 , 0),2) AS valid_u_change,  IFNULL(SUM(pay_u), 0) AS pay_u,  FORMAT(IFNULL(SUM(pay_u)/SUM(login_u) * 100 , 0),2) AS login_pay_change,  FORMAT(IFNULL(SUM(pay_u)/SUM(valid_u) * 100, 0),2) AS valid_pay_change FROM  new_u_change WHERE  t_time {0} AND platform LIKE \"{2}\" AND server_id LIKE \"{1}\" GROUP BY  d_time \r\n', 'd_time,new_drive,new_reg,reg_change,login_u,login_change,valid_u,valid_u_change,pay_u,login_pay_change,valid_pay_change', '日期,新增设备,新增注册,注册转化率%,登录用户数,登录转化率%,有效用户,有效用户转化率%,付费用户,登录付费转化率%,有效用户付费转化率%');
INSERT INTO `report_sql` VALUES ('6', 'SELECT  T1.t_time,  T.new_account,  T.new_device,  T.f_pay,  T.f_pay_count01 / T.f_pay AS f_retention,  T.now_pay,  T.pay_next_day_login / T.now_pay AS p_retention,  T.new_device_pay,  T.new_device_pay_sum,  T.dau,  T.dau_pay,  T.dau_pay / T.dau * 100 AS dauRate,  T.new_device_pay_sum / T.new_device_pay AS arpu,  T.new_device_pay_sum / T.new_device AS arppu FROM  ({0}) AS T1 LEFT JOIN (  SELECT  DATE_FORMAT(create_date, \"%Y-%m-%d\") AS t_time,  SUM(new_account) AS new_account,  SUM(new_device) AS new_device,  SUM(f_pay) AS f_pay,  SUM(f_pay_count01) AS f_pay_count01,  SUM(now_pay) AS now_pay,  SUM(pay_next_day_login) AS pay_next_day_login,  SUM(new_device_pay) AS new_device_pay,  SUM(new_device_pay_sum) AS new_device_pay_sum,  SUM(dau) AS dau,  SUM(dau_pay) AS dau_pay  FROM  f_pay_retention  WHERE  create_date  {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time ) AS T ON T1.t_time = T.t_time \r\n', 't_time,new_account,new_device,f_pay,f_retention,now_pay,p_retention,new_device_pay,new_device_pay_sum,dau,dau_pay,dauRate,arpu,arppu', '日期,新增账号,新增设备,首付人数,首付次日存留数,当日付费人数,付费次日存留数,新增设备充值人数,新增设备充值总额,日活跃,日活跃付费人数,日活跃付费率%,新增设备ARPU,新增设备ARPPU');
INSERT INTO `report_sql` VALUES ('7', 'SELECT  T.t_time,  T1.user_add AS newUser,  T1.pay_01 AS pay1,  T1.pay_02 AS pay2,  FORMAT(T1.pay_02 / T1.user_add, 2) AS LTV2,  T1.pay_03 AS pay3,  FORMAT(T1.pay_03 / T1.user_add, 2) AS LTV3,  T1.pay_04 AS pay4,  FORMAT(T1.pay_04 / T1.user_add, 2) AS LTV4,  T1.pay_05 AS pay5,  FORMAT(T1.pay_05 / T1.user_add, 2) AS LTV5,  T1.pay_06 AS pay6,  FORMAT(T1.pay_06 / T1.user_add, 2) AS LTV6,  T1.pay_07 AS pay7,  FORMAT(T1.pay_07 / T1.user_add, 2) AS LTV7,  T1.pay_08 AS pay8,  FORMAT(T1.pay_08 / T1.user_add, 2) AS LTV8,  T1.pay_09 AS pay9,  FORMAT(T1.pay_09 / T1.user_add, 2) AS LTV9,  T1.pay_10 AS pay10,  FORMAT(T1.pay_10 / T1.user_add, 2) AS LTV10,  T1.pay_11 AS pay11,  FORMAT(T1.pay_11 / T1.user_add, 2) AS LTV11,  T1.pay_12 AS pay12,  FORMAT(T1.pay_12 / T1.user_add, 2) AS LTV12,  T1.pay_13 AS pay13,  FORMAT(T1.pay_13 / T1.user_add, 2) AS LTV13,  T1.pay_14 AS pay14,  FORMAT(T1.pay_14 / T1.user_add, 2) AS LTV14,  T1.pay_15 AS pay15,  FORMAT(T1.pay_15 / T1.user_add, 2) AS LTV15,  T1.pay_16 AS pay16,  FORMAT(T1.pay_16 / T1.user_add, 2) AS LTV16,  T1.pay_17 AS pay17,  FORMAT(T1.pay_17 / T1.user_add, 2) AS LTV17,  T1.pay_18 AS pay18,  FORMAT(T1.pay_18 / T1.user_add, 2) AS LTV18,  T1.pay_19 AS pay19,  FORMAT(T1.pay_19 / T1.user_add, 2) AS LTV19,  T1.pay_20 AS pay20,  FORMAT(T1.pay_20 / T1.user_add, 2) AS LTV20,  T1.pay_21 AS pay21,  FORMAT(T1.pay_21 / T1.user_add, 2) AS LTV21,  T1.pay_22 AS pay22,  FORMAT(T1.pay_22 / T1.user_add, 2) AS LTV22,  T1.pay_23 AS pay23,  FORMAT(T1.pay_23 / T1.user_add, 2) AS LTV23,  T1.pay_24 AS pay24,  FORMAT(T1.pay_24 / T1.user_add, 2) AS LTV24,  T1.pay_25 AS pay25,  FORMAT(T1.pay_25 / T1.user_add, 2) AS LTV25,  T1.pay_26 AS pay26,  FORMAT(T1.pay_26 / T1.user_add, 2) AS LTV26,  T1.pay_27 AS pay27,  FORMAT(T1.pay_27 / T1.user_add, 2) AS LTV27,  T1.pay_28 AS pay28,  FORMAT(T1.pay_28 / T1.user_add, 2) AS LTV28,  T1.pay_29 AS pay29,  FORMAT(T1.pay_29 / T1.user_add, 2) AS LTV29,  T1.pay_30 AS pay30,  FORMAT(T1.pay_30 / T1.user_add, 2) AS LTV30 FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(p.create_date, \"%Y-%m-%d\") AS t_time,  SUM(user_add) AS user_add,  SUM(pay_01) AS pay_01,  SUM(pay_02) AS pay_02,  SUM(pay_03) AS pay_03,  SUM(pay_04) AS pay_04,  SUM(pay_05) AS pay_05,  SUM(pay_06) AS pay_06,  SUM(pay_07) AS pay_07,  SUM(pay_08) AS pay_08,  SUM(pay_09) AS pay_09,  SUM(pay_10) AS pay_10,  SUM(pay_11) AS pay_11,  SUM(pay_12) AS pay_12,  SUM(pay_13) AS pay_13,  SUM(pay_14) AS pay_14,  SUM(pay_15) AS pay_15,  SUM(pay_16) AS pay_16,  SUM(pay_17) AS pay_17,  SUM(pay_18) AS pay_18,  SUM(pay_19) AS pay_19,  SUM(pay_20) AS pay_20,  SUM(pay_21) AS pay_21,  SUM(pay_22) AS pay_22,  SUM(pay_23) AS pay_23,  SUM(pay_24) AS pay_24,  SUM(pay_25) AS pay_25,  SUM(pay_26) AS pay_26,  SUM(pay_27) AS pay_27,  SUM(pay_28) AS pay_28,  SUM(pay_29) AS pay_29,  SUM(pay_30) AS pay_30  FROM  pay_lty AS p  WHERE  p.create_date {1}  AND p.platform LIKE \"{3}\"  AND p.server_id LIKE \"{2}\"  GROUP BY  t_time,  p.platform,  p.server_id ) AS T1 ON T.t_time = T1.t_time GROUP BY  T.t_time;  \r\n', 't_time,newUser,pay1,pay2,LTV2,pay3,LTV3,pay4,LTV4,pay5,LTV5,pay6,LTV6,pay7,LTV7,pay8,LTV8,pay9,LTV9,pay10,LTV10,pay11,LTV11,pay12,LTV12,pay13,LTV13,pay14,LTV14,pay15,LTV15,pay16,LTV16,pay17,LTV17,pay18,LTV18,pay19,LTV19,pay20,LTV20,pay21,LTV21,pay22,LTV22,pay23,LTV23,pay24,LTV24,pay25,LTV25,pay26,LTV26,pay27,LTV27,pay28,LTV28,pay29,LTV29,pay30,LTV30', '时间,当日新增用户,当日付费金额,2日付费,2日LTV,3日付费,3日LTV,4日付费,4日LTV,5日付费,5日LTV,6日付费,6日LTV,7日付费,7日LTV,8日付费,8日LTV,9日付费,9日LTV,10日付费,10日LTV,11日付费,11日LTV,12日付费,12日LTV,13日付费,13日LTV,14日付费,14日LTV,15日付费,15日LTV,16日付费,16日LTV,17日付费,17日LTV,18日付费,18日LTV,19日付费,19日LTV,20日付费,20日LTV,21日付费,21日LTV,22日付费,22日LTV,23日付费,23日LTV,24日付费,24日LTV,25日付费,25日LTV,26日付费,26日LTV,27日付费,27日LTV,28日付费,28日LTV,29日付费,29日LTV,30日付费,30日LTV');
INSERT INTO `report_sql` VALUES ('8', 'SELECT  t_time as t_data,   count(DISTINCT account_id) AS play_count FROM (SELECT  account_id,  TIMESTAMPDIFF(  HOUR,  CURDATE(),  last_log_time  ) as t_time  FROM  login_log  WHERE  TIMESTAMPDIFF(  DAY,  DATE_FORMAT(last_log_time,\"%Y-%m-%d\"),  CURDATE()   ) = 0  AND platform LIKE \"{0}\"  AND server_id LIKE \"{1}\" ) as T GROUP BY  t_time ORDER BY  t_time desc \r\n', 't_data,play_count', '时间(小时),活跃人数');
INSERT INTO `report_sql` VALUES ('9', 'SELECT  DATE_FORMAT(log_time, \"%Y年%m月%d日\") AS t_date,  SUM(hour_01) AS hour_01,  SUM(hour_02) AS hour_02,  SUM(hour_03) AS hour_03,  SUM(hour_04) AS hour_04,  SUM(hour_05) AS hour_05,  SUM(hour_06) AS hour_06,  SUM(hour_07) AS hour_07,  SUM(hour_08) AS hour_08,  SUM(hour_09) AS hour_09,  SUM(hour_10) AS hour_10,  SUM(hour_11) AS hour_11,  SUM(hour_12) AS hour_12,  SUM(hour_13) AS hour_13,  SUM(hour_14) AS hour_14,  SUM(hour_15) AS hour_15,  SUM(hour_16) AS hour_16,  SUM(hour_17) AS hour_17,  SUM(hour_18) AS hour_18,  SUM(hour_19) AS hour_19,  SUM(hour_20) AS hour_20,  SUM(hour_21) AS hour_21,  SUM(hour_22) AS hour_22,  SUM(hour_23) AS hour_23,  SUM(hour_24) AS hour_24 FROM  game_per_hour_online_summary WHERE  log_time {0} AND server_id LIKE \"{1}\" GROUP BY  t_date \r\n', 't_date,hour_01,hour_02,hour_03,hour_04,hour_05,hour_06,hour_07,hour_08,hour_09,hour_10,hour_11,hour_12,hour_13,hour_14,hour_15,hour_16,hour_17,hour_18,hour_19,hour_20,hour_21,hour_22,hour_23,hour_24', '日期在线人数,00:00,01:00,02:00,03:00,04:00,05:00,06:00,07:00,08:00,09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00,21:00,22:00,23:00');
INSERT INTO `report_sql` VALUES ('10', '(  SELECT  1 AS i_index,  \"0-10\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay <= 10 ) UNION  (  SELECT  2 AS i_index,  \"11-50\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 10  AND T.pay <= 50  ) UNION  (  SELECT  3 AS i_index,  \"51-100\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 50  AND T.pay <= 100  ) UNION  (  SELECT  4 AS i_index,  \"101-200\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 100  AND T.pay <= 200  ) UNION  (  SELECT  5 AS i_index,  \"201-500\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 200  AND T.pay <= 500  ) UNION  (  SELECT  6 AS i_index,  \"501-1000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 500  AND T.pay <= 1000  ) UNION  (  SELECT  7 AS i_index,  \"1001-3000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 1000  AND T.pay <= 3000  ) UNION  (  SELECT  8 AS i_index,  \"3001-7000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 3000  AND T.pay <= 7000  ) UNION  (  SELECT  9 AS i_index,  \"7001-12000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 7000  AND T.pay <= 12000  ) UNION  (  SELECT  10 AS i_index,  \"12001以上\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 12000  ) UNION  (  SELECT  \"总计   \" AS i_index,  \"\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  ) \r\n', 'i_index,t_qj,num', '序号,区间(元),人数');
INSERT INTO `report_sql` VALUES ('11', 'SELECT  T6.t_time,  SUM(T2.newPlayer) AS newPlayer,  T3.newPPayCount,  T3.newPPayCountR,  T3.newPay AS pay_money,  FORMAT(  T3.newPay / T3.newPPayCount,  2  ) AS ARPPU,  FORMAT(  T3.newPPayCount / SUM(T2.newPlayer),  2  ) AS newPayRate FROM  ({0}) AS T6 LEFT JOIN (  SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount,  SUM(V2.newPPCountR) AS newPPayCountR  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  COUNT(t.account_name) AS newPPCountR,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time ) AS T3 ON T3.t_time = T6.t_time LEFT JOIN (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  COUNT(DISTINCT account) AS newPlayer  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time ) AS T2 ON T2.t_time = T6.t_time GROUP BY  T6.t_time \r\n', 't_time,newPlayer,newPPayCount,newPPayCountR,pay_money,ARPPU,newPayRate', '日期,新增玩家数,新增付费人数,新增付费次数,付费金额,ARPPU,新增付费率');
INSERT INTO `report_sql` VALUES ('12', '(  SELECT  T6.t_time,  (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ) AS old_pay,  (  IFNULL(T2.payUserR, 0) - IFNULL(T3.newPPayCountR, 0)  ) AS old_payR,  (  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0)  ) AS oldPaySum,  FORMAT(  (  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0)  ) / (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ),  2  ) AS ARPPU,  IFNULL(T.activeUser, 0) AS activeUser,  FORMAT(  (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ) / IFNULL(T.activeUser, 0) * 100,  2  ) AS payRate  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  SUM(playCount) AS activeUser,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  acu  WHERE  log_time {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_name) AS payUser,  COUNT(t.account_name) AS payUserR  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (   SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount,  SUM(V2.newPPayCountR) AS newPPayCountR  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  COUNT(t.account_name) AS newPPayCountR,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time  ) AS T3 ON T3.t_time = T6.t_time  GROUP BY  T6.t_time ) \r\n', 't_time,old_pay,old_payR,oldPaySum,ARPPU,activeUser,payRate', '日期,付费人数,付费人次,付费金额,ARPPU,当日活跃人数,付费率(%)');
INSERT INTO `report_sql` VALUES ('13', 'SELECT  T.t_time,  T1.pay FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {1}  AND o.platform LIKE \"{2}\"  GROUP BY  t_time ) AS T1 ON T1.t_time = T.t_time UNION ALL  (  SELECT  \"总计\" AS t_time,  SUM(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {1}  AND o.platform LIKE \"{2}\"  ) \r\n', 't_time,pay', '日期,金额(元)');
INSERT INTO `report_sql` VALUES ('14', 'SELECT  T1.t_time, T1.NAME, T1.pay FROM (  SELECT   t.t_time, t.pay, t. NAME  FROM  ( SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(o.amount) AS pay,  o.server_id,  s. NAME  FROM  `order` AS o  LEFT JOIN `server` AS s ON o.server_id = s.id  WHERE  o.platform LIKE \"{1}\"  GROUP BY  t_time, s.id ) AS t  WHERE  t.t_time LIKE \"{0}\" ) AS T1 UNION ALL  (  SELECT  \"总计\" AS t_time,\"\" as NAME, SUM(t.pay) AS pay  FROM  (  SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time, SUM(o.amount) AS pay  FROM  `order` AS o  WHERE  o.platform LIKE \"{1}\" GROUP BY  t_time ) AS t  WHERE  t.t_time LIKE \"{0}\"  )\r\n', 't_time,name,pay', '日期,区服,金额(元)');
INSERT INTO `report_sql` VALUES ('15', 'SELECT  b.id,  b. NAME AS step,  IFNULL(SUM(T.sum_player), 0) AS num FROM  guide_info AS b LEFT JOIN (  SELECT  a.guide_id,  a.sum_player  FROM  guide_summary AS a  WHERE  a.t_time {0}  AND a.platform LIKE \"{2}\"  AND a.server_id LIKE \"{1}\" ) AS T ON T.guide_id = b.id GROUP BY id \r\n', 'id,step,num', '引导ID,引导步骤,人数(设备号)');
INSERT INTO `report_sql` VALUES ('16', 'SELECT  T1.t_time,  SUM(T1.pay_sum) AS pay_sum,  SUM(T1.num) AS num,  SUM(T1.money_6) AS money_6,  SUM(T1.p_6) AS p_6,  SUM(T1.money_30) AS money_30,  SUM(T1.p_30) AS p_30,  SUM(T1.money_68) AS money_68,  SUM(T1.p_68) AS p_68,  SUM(T1.money_98) AS money_98,  SUM(T1.p_98) AS p_98,  SUM(T1.money_198) AS money_198,  SUM(T1.p_198) AS p_198,  SUM(T1.money_328) AS money_328,  SUM(T1.p_328) AS p_328,  SUM(T1.money_648) AS money_648,  SUM(T1.p_648) AS p_648,  SUM(T1.money_988) AS money_988,  SUM(T1.p_988) AS p_988,  SUM(T1.money_1988) AS money_1988,  SUM(T1.p_1988) AS p_1988,  SUM(T1.money_4988) AS money_4988,  SUM(T1.p_4988) AS p_4988,  SUM(T1.money_n) AS money_n,  SUM(T1.p_n) AS p_n,  SUM(T1.money_vip) AS money_vip,  SUM(T1.p_vip) AS p_vip FROM  (  SELECT  T.t_time,  T.sr AS pay_sum,  T.num,  CASE T.goods_id WHEN \'10654595\' THEN T.sr ELSE 0 END AS money_6,  CASE T.goods_id WHEN \'10654595\' THEN T.num ELSE 0 END AS p_6,  CASE T.goods_id WHEN \'10654596\' THEN T.sr ELSE 0 END AS money_30,  CASE T.goods_id WHEN \'10654596\' THEN T.num ELSE 0 END AS p_30,  CASE T.goods_id WHEN \'10654597\' THEN T.sr ELSE 0 END AS money_68,  CASE T.goods_id WHEN \'10654597\' THEN T.num ELSE 0 END AS p_68,  CASE T.goods_id WHEN \'10654598\' THEN T.sr ELSE 0 END AS money_98,  CASE T.goods_id WHEN \'10654598\' THEN T.num ELSE 0 END AS p_98,  CASE T.goods_id WHEN \'10654599\' THEN T.sr ELSE 0 END AS money_198,  CASE T.goods_id WHEN \'10654599\' THEN T.num ELSE 0 END AS p_198,  CASE T.goods_id WHEN \'10654600\' THEN T.sr ELSE 0 END AS money_328,  CASE T.goods_id WHEN \'10654600\' THEN T.num ELSE 0 END AS p_328,  CASE T.goods_id WHEN \'10654601\' THEN T.sr ELSE 0 END AS money_648,  CASE T.goods_id WHEN \'10654601\' THEN T.num ELSE 0 END AS p_648,  CASE T.goods_id WHEN \'10654602\' THEN T.sr ELSE 0 END AS money_988,  CASE T.goods_id WHEN \'10654602\' THEN T.num ELSE 0 END AS p_988,  CASE T.goods_id WHEN \'10654603\' THEN T.sr ELSE 0 END AS money_1988,  CASE T.goods_id WHEN \'10654603\' THEN T.num ELSE 0 END AS p_1988,  CASE T.goods_id WHEN \'10654604\' THEN T.sr ELSE 0 END AS money_4988,  CASE T.goods_id WHEN \'10654604\' THEN T.num ELSE 0 END AS p_4988,  CASE T.goods_id WHEN \'10654605\' THEN T.sr ELSE 0 END AS money_n,  CASE T.goods_id WHEN \'10654605\' THEN T.num ELSE 0 END AS p_n,  CASE T.goods_id WHEN \'10654606\' THEN T.sr ELSE 0 END AS money_vip,  CASE T.goods_id WHEN \'10654606\' THEN T.num ELSE 0 END AS p_vip   FROM  (SELECT  t.t_time,  t.goods_id,  COUNT(t.account_name) as num,  SUM(t.amount) as sr FROM (SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time,  o.finish_time,  o.goods_id,  o.account_name,  o.amount FROM  `order` AS o WHERE  o.finish_time {1} AND o.platform LIKE \"{3}\" AND o.server_id LIKE \"{2}\" GROUP BY  o.account_name,  t_time ORDER BY t_time asc ) as t GROUP BY t.t_time,t.goods_id  ) AS T  ) AS T1 GROUP BY  T1.t_time; \r\n', 't_time,pay_sum,num,money_6,p_6,money_30,p_30,money_68,p_68,money_98,p_98,money_198,p_198,money_328,p_328,money_648,p_648,money_988,p_988,money_1988,p_1988,money_4988,p_4988,money_n,p_n,money_vip,p_vip', '日期,总付费金额,总付费人数,6元,人数,30元,人数,68元,人数,98元,人数,198元,人数,328元,人数,648元,人数,988元,人数,1988元,人数,4988元,人数,普通月卡(25元),人数,VIP月卡(98元),人数\r\n');
INSERT INTO `report_sql` VALUES ('17', 'SELECT  b.id,  b. NAME AS step,  IFNULL(T.player, 0) AS player,  IFNULL(T.num, 0) AS num,  IFNULL(T.win_player, 0) AS win_player,  IFNULL(T.win, 0) AS win FROM  fb_info AS b LEFT JOIN (  SELECT  a.fb_id,  SUM(a.tz_num) AS num,  SUM(a.tz_player) AS player,  SUM(a.tz_win_player_cs) AS win,  SUM(a.tz_win_player) AS win_player  FROM  fb_summary AS a  WHERE  a.t_time {0}  AND a.platform LIKE \"{2}\"  AND a.server_id LIKE \"{1}\"  GROUP BY  a.fb_id ) AS T ON T.fb_id = b.id WHERE  b.type LIKE \"{3}\" \r\n', 'id,step,player,num,win_player,win', '副本ID,副本名称,挑战人数,挑战次数,完成关卡人数,完成副本关卡次数');
INSERT INTO `report_sql` VALUES ('18', '(SELECT  type1,  type2,  IFNULL(SUM(total),0) AS total,  IFNULL(SUM(rc),0) AS rc,  IFNULL(SUM(rs),0) AS rs FROM  crystal_use_summary WHERE  platform LIKE \"{2}\" AND t_time {0} AND server_id LIKE \"{1}\" GROUP BY  type1,  type2 )UNION (SELECT  \"总计:\" AS type1,  \"\" AS type2,  IFNULL(SUM(total),0) AS total,  IFNULL(SUM(rc),0) AS rc,  IFNULL(SUM(rs),0) AS rs FROM  crystal_use_summary WHERE  platform LIKE \"{2}\" AND t_time {0} AND server_id LIKE \"{1}\" ) ', 'type1,type2,total,rc,rs', '消费类型,标题,消费总额,人次,人数');
INSERT INTO `report_sql` VALUES ('19', '(  SELECT  type1,  type2,  IFNULL(SUM(total), 0) AS total,  IFNULL(SUM(rc), 0) AS rc,  IFNULL(SUM(rs), 0) AS rs  FROM  crystal_use_summary  WHERE  platform LIKE \"{2}\"  AND t_time {0}  AND server_id LIKE \"{1}\"  AND isVip > 0  GROUP BY  type1,  type2 ) UNION  (  SELECT  \"总计:\" AS type1,  \"\" AS type2,  IFNULL(SUM(total), 0) AS total,  IFNULL(SUM(rc), 0) AS rc,  IFNULL(SUM(rs), 0) AS rs  FROM  crystal_use_summary  WHERE  platform LIKE \"{2}\"  AND t_time {0}  AND server_id LIKE \"{1}\"  AND isVip > 0  ) \r\n', 'type1,type2,total,rc,rs', '消费类型,标题,消费总额,人次,人数');
INSERT INTO `report_sql` VALUES ('20', 'SELECT  T1.t_time,  IFNULL(T2.tOutput, 0) AS tOutput,  IFNULL(T2.tInput, 0) AS tInput,  IFNULL(T2.tKeep, 0) AS tKeep,  IFNULL(T2.output1, 0) AS output1,  IFNULL(T2.input1, 0) AS input1,  IFNULL(T2.keep1, 0) AS keep1,  IFNULL(T2.output2, 0) AS output2,  IFNULL(T2.input2, 0) AS input2,  IFNULL(T2.keep2, 0) AS keep2,  IFNULL(T2.output3, 0) AS output3,  IFNULL(T2.input3, 0) AS input3,  IFNULL(T2.keep3, 0) AS keep3,  IFNULL(T2.output4, 0) AS output4,  IFNULL(T2.input4, 0) AS input4,  IFNULL(T2.keep4, 0) AS keep4,  IFNULL(T2.output5, 0) AS output5,  IFNULL(T2.input5, 0) AS input5,  IFNULL(T2.keep5, 0) AS keep5,  IFNULL(T2.output6, 0) AS output6,  IFNULL(T2.input6, 0) AS input6,  IFNULL(T2.keep6, 0) AS keep6,  IFNULL(T2.output7, 0) AS output7,  IFNULL(T2.input7, 0) AS input7,  IFNULL(T2.keep7, 0) AS keep7,  IFNULL(T2.output8, 0) AS output8,  IFNULL(T2.input8, 0) AS input8,  IFNULL(T2.keep8, 0) AS keep8,  IFNULL(T2.output9, 0) AS output9,  IFNULL(T2.input9, 0) AS input9,  IFNULL(T2.keep9, 0) AS keep9 FROM  ({0}) AS T1 LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(tOutput) AS tOutput,  SUM(tInput) AS tInput,  SUM(tKeep) AS tKeep,  SUM(output1) AS output1,  SUM(input1) AS input1,  SUM(keep1) AS keep1,  SUM(output2) AS output2,  SUM(input2) AS input2,  SUM(keep2) AS keep2,  SUM(output3) AS output3,  SUM(input3) AS input3,  SUM(keep3) AS keep3,  SUM(output4) AS output4,  SUM(input4) AS input4,  SUM(keep4) AS keep4,  SUM(output5) AS output5,  SUM(input5) AS input5,  SUM(keep5) AS keep5,  SUM(output6) AS output6,  SUM(input6) AS input6,  SUM(keep6) AS keep6,  SUM(output7) AS output7,  SUM(input7) AS input7,  SUM(keep7) AS keep7,  SUM(output8) AS output8,  SUM(input8) AS input8,  SUM(keep8) AS keep8,  SUM(output9) AS output9,  SUM(input9) AS input9,  SUM(keep9) AS keep9  FROM  crystal_level  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T2 ON T2.d_time = T1.t_time \r\n', 't_time,tOutput,tInput,tKeep,output1,input1,keep1,output2,input2,keep2,output3,input3,keep3,output4,input4,keep4,output5,input5,keep5,output6,input6,keep6,output7,input7,keep7,output8,input8,keep8,output9,input9,keep9', '日期,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存');
INSERT INTO `report_sql` VALUES ('21', '(SELECT  T1.t_time,  IFNULL(T2.cz_cs, 0) + IFNULL(T2.hd_cs, 0)+ IFNULL(T2.tg_cs, 0) + IFNULL(T2.yj_cs, 0) + IFNULL(T2.jjc_cs, 0) + IFNULL(T2.shop_cs, 0) AS zj_cs,  IFNULL(T2.cz_sj, 0) + IFNULL(T2.hd_sj, 0) + IFNULL(T2.tg_sj, 0) + IFNULL(T2.yj_sj, 0) + IFNULL(T2.jjc_sj, 0) + IFNULL(T2.shop_sj, 0) AS zj_sj,  IFNULL(T2.cz_cs, 0) AS cz_cs,  IFNULL(T2.cz_sj, 0) AS cz_sj,  IFNULL(T2.hd_cs, 0) AS hd_cs,  IFNULL(T2.hd_sj, 0) AS hd_sj,  IFNULL(T2.tg_cs, 0) AS tg_cs,  IFNULL(T2.tg_sj, 0) AS tg_sj,  IFNULL(T2.yj_cs, 0) AS yj_cs,  IFNULL(T2.yj_sj, 0) AS yj_sj,  IFNULL(T2.jjc_cs, 0) AS jjc_cs,  IFNULL(T2.jjc_sj, 0) AS jjc_sj,  IFNULL(T2.shop_cs, 0) AS shop_cs,  IFNULL(T2.shop_sj, 0) AS shop_sj FROM  ({0}) AS T1 LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(cz_cs) AS cz_cs,  SUM(cz_sj) AS cz_sj,  SUM(hd_cs) AS hd_cs,  SUM(hd_sj) AS hd_sj,  SUM(tg_cs) AS tg_cs,  SUM(tg_sj) AS tg_sj,  SUM(yj_cs) AS yj_cs,  SUM(yj_sj) AS yj_sj,  SUM(jjc_cs) AS jjc_cs,  SUM(jjc_sj) AS jjc_sj,  SUM(shop_cs) AS shop_cs,  SUM(shop_sj) AS shop_sj  FROM  crystal_detailed_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T2 ON T1.t_time = T2.d_time) ', 't_time,zj_cs,zj_sj,cz_cs,cz_sj,hd_cs,hd_sj,tg_cs,tg_sj,yj_cs,yj_sj,jjc_cs,jjc_sj,shop_cs,shop_sj', '日期,次数,水晶,次数,水晶,次数,水晶,次数,水晶,次数,水晶,次数,水晶');
INSERT INTO `report_sql` VALUES ('22', 'SELECT  T.t_time,  IFNULL(T2.cs1, 0) + IFNULL(T2.cs2, 0) + IFNULL(T2.cs3, 0) + IFNULL(T2.cs4, 0) + IFNULL(T2.cs5, 0) + IFNULL(T2.cs6, 0) + IFNULL(T2.cs7, 0) + IFNULL(T2.cs8, 0) + IFNULL(T2.cs9, 0) AS zj_cs,  IFNULL(T2.ks1, 0) + IFNULL(T2.cs2, 0) + IFNULL(T2.cs3, 0) + IFNULL(T2.cs4, 0) + IFNULL(T2.cs5, 0) + IFNULL(T2.cs6, 0) + IFNULL(T2.cs7, 0) + IFNULL(T2.cs8, 0) + IFNULL(T2.cs9, 0) AS zj_ks,  IFNULL(T2.cs1, 0) AS cs1,  IFNULL(T2.ks1, 0) AS ks1,  IFNULL(T2.cs2, 0) AS cs2,  IFNULL(T2.ks2, 0) AS ks2,  IFNULL(T2.cs3, 0) AS cs3,  IFNULL(T2.ks3, 0) AS ks3,  IFNULL(T2.cs4, 0) AS cs4,  IFNULL(T2.ks4, 0) AS ks4,  IFNULL(T2.cs5, 0) AS cs5,  IFNULL(T2.ks5, 0) AS ks5,  IFNULL(T2.cs6, 0) AS cs6,  IFNULL(T2.ks6, 0) AS ks6,  IFNULL(T2.cs7, 0) AS cs7,  IFNULL(T2.ks7, 0) AS ks7,  IFNULL(T2.cs8, 0) AS cs8,  IFNULL(T2.ks8, 0) AS ks8,  IFNULL(T2.cs9, 0) AS cs9,  IFNULL(T2.ks9, 0) AS ks9 FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(cs1) AS cs1,  SUM(ks1) AS ks1,  SUM(cs2) AS cs2,  SUM(ks2) AS ks2,  SUM(cs3) AS cs3,  SUM(ks3) AS ks3,  SUM(cs4) AS cs4,  SUM(ks4) AS ks4,  SUM(cs5) AS cs5,  SUM(ks5) AS ks5,  SUM(cs6) AS cs6,  SUM(ks6) AS ks6,  SUM(cs7) AS cs7,  SUM(ks7) AS ks7,  SUM(cs8) AS cs8,  SUM(ks8) AS ks8,  SUM(cs9) AS cs9,  SUM(ks9) AS ks9  FROM  ore_detailed_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T2 ON T2.d_time = T.t_time \r\n', 't_time,zj_cs,zj_ks,cs1,ks1,cs2,ks2,cs3,ks3,cs4,ks4,cs5,ks5,cs6,ks6,cs7,ks7,cs8,ks8,cs9,ks9', '日期,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿');
INSERT INTO `report_sql` VALUES ('23', 'SELECT  T1.t_time,  SUM(T1.pay_sum) AS pay_sum,  SUM(T1.num) AS num,  SUM(T1.numr) as numr,  SUM(T1.money_6) AS money_6,  SUM(T1.p_6) AS p_6,  SUM(T1.pr_6) AS pr_6,  SUM(T1.money_30) AS money_30,  SUM(T1.p_30) AS p_30,SUM(T1.pr_30) AS pr_30,  SUM(T1.money_68) AS money_68,  SUM(T1.p_68) AS p_68,SUM(T1.pr_68) AS pr_68,  SUM(T1.money_98) AS money_98,  SUM(T1.p_98) AS p_98,SUM(T1.pr_98) AS pr_98,  SUM(T1.money_198) AS money_198,  SUM(T1.p_198) AS p_198,SUM(T1.pr_198) AS pr_198,  SUM(T1.money_328) AS money_328,  SUM(T1.p_328) AS p_328,SUM(T1.pr_328) AS pr_328,  SUM(T1.money_648) AS money_648,  SUM(T1.p_648) AS p_648,SUM(T1.pr_648) AS pr_648,  SUM(T1.money_988) AS money_988,  SUM(T1.p_988) AS p_988,SUM(T1.pr_988) AS pr_988,  SUM(T1.money_1988) AS money_1988,  SUM(T1.p_1988) AS p_1988,SUM(T1.pr_1988) AS pr_1988,  SUM(T1.money_4988) AS money_4988,  SUM(T1.p_4988) AS p_4988,SUM(T1.pr_4988) AS pr_4988,  SUM(T1.money_n) AS money_n,  SUM(T1.p_n) AS p_n,SUM(T1.pr_n) AS pr_n,  SUM(T1.money_vip) AS money_vip,  SUM(T1.p_vip) AS p_vip, SUM(T1.pr_vip) AS pr_vip FROM  (  SELECT  T.t_time,  T.sr AS pay_sum,  T.num,  T.numr,  CASE T.goods_id WHEN \'10654595\' THEN T.sr ELSE 0 END AS money_6,  CASE T.goods_id WHEN \'10654595\' THEN T.num ELSE 0 END AS p_6,  CASE T.goods_id WHEN \'10654595\' THEN T.numr ELSE 0 END AS pr_6,  CASE T.goods_id WHEN \'10654596\' THEN T.sr ELSE 0 END AS money_30,  CASE T.goods_id WHEN \'10654596\' THEN T.num ELSE 0 END AS p_30,  CASE T.goods_id WHEN \'10654596\' THEN T.numr ELSE 0 END AS pr_30,  CASE T.goods_id WHEN \'10654597\' THEN T.sr ELSE 0 END AS money_68,  CASE T.goods_id WHEN \'10654597\' THEN T.num ELSE 0 END AS p_68,  CASE T.goods_id WHEN \'10654597\' THEN T.numr ELSE 0 END AS pr_68,  CASE T.goods_id WHEN \'10654598\' THEN T.sr ELSE 0 END AS money_98,  CASE T.goods_id WHEN \'10654598\' THEN T.num ELSE 0 END AS p_98,  CASE T.goods_id WHEN \'10654598\' THEN T.numr ELSE 0 END AS pr_98,  CASE T.goods_id WHEN \'10654599\' THEN T.sr ELSE 0 END AS money_198,  CASE T.goods_id WHEN \'10654599\' THEN T.num ELSE 0 END AS p_198,  CASE T.goods_id WHEN \'10654599\' THEN T.numr ELSE 0 END AS pr_198,  CASE T.goods_id WHEN \'10654600\' THEN T.sr ELSE 0 END AS money_328,  CASE T.goods_id WHEN \'10654600\' THEN T.num ELSE 0 END AS p_328,  CASE T.goods_id WHEN \'10654600\' THEN T.numr ELSE 0 END AS pr_328,  CASE T.goods_id WHEN \'10654601\' THEN T.sr ELSE 0 END AS money_648,  CASE T.goods_id WHEN \'10654601\' THEN T.num ELSE 0 END AS p_648,  CASE T.goods_id WHEN \'10654601\' THEN T.numr ELSE 0 END AS pr_648,  CASE T.goods_id WHEN \'10654602\' THEN T.sr ELSE 0 END AS money_988,  CASE T.goods_id WHEN \'10654602\' THEN T.num ELSE 0 END AS p_988,  CASE T.goods_id WHEN \'10654602\' THEN T.numr ELSE 0 END AS pr_988,  CASE T.goods_id WHEN \'10654603\' THEN T.sr ELSE 0 END AS money_1988,  CASE T.goods_id WHEN \'10654603\' THEN T.num ELSE 0 END AS p_1988,  CASE T.goods_id WHEN \'10654603\' THEN T.numr ELSE 0 END AS pr_1988,  CASE T.goods_id WHEN \'10654604\' THEN T.sr ELSE 0 END AS money_4988,  CASE T.goods_id WHEN \'10654604\' THEN T.num ELSE 0 END AS p_4988,  CASE T.goods_id WHEN \'10654604\' THEN T.numr ELSE 0 END AS pr_4988,  CASE T.goods_id WHEN \'10654605\' THEN T.sr ELSE 0 END AS money_n,  CASE T.goods_id WHEN \'10654605\' THEN T.num ELSE 0 END AS p_n,  CASE T.goods_id WHEN \'10654605\' THEN T.numr ELSE 0 END AS pr_n,  CASE T.goods_id WHEN \'10654606\' THEN T.sr ELSE 0 END AS money_vip,  CASE T.goods_id WHEN \'10654606\' THEN T.num ELSE 0 END AS p_vip,  CASE T.goods_id WHEN \'10654606\' THEN T.numr ELSE 0 END AS pr_vip   FROM  (  SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time,  o.goods_id,  COUNT(DISTINCT o.account_name) AS num,  COUNT(o.account_name) AS numr,  SUM(o.amount) AS sr  FROM  `order` AS o  WHERE  o.finish_time {1}  AND o.platform LIKE \"{3}\"  AND o.server_id LIKE \"{2}\"  GROUP BY  o.account_name,  o.goods_id,  t_time  ) AS T  ) AS T1 GROUP BY  T1.t_time; \r\n', 't_time,pay_sum,num,numr,money_6,p_6,pr_6,money_30,p_30,pr_30,money_68,p_68,pr_68,money_98,p_98,pr_98,money_198,p_198,pr_198,money_328,p_328,pr_328,money_648,p_648,pr_648,money_988,p_988,pr_988,money_1988,p_1988,pr_1988,money_4988,p_4988,pr_4988,money_n,p_n,pr_n,money_vip,p_vip,pr_vip', '日期,付费金额,付费人数,付费次数,6元,人数,付费次数,30元,人数,付费次数,68元,人数,付费次数,98元,人数,付费次数,198元,人数,付费次数,328元,人数,付费次数,648元,人数,付费次数,988元,人数,付费次数,1988元,人数,付费次数,4988元,人数,付费次数,普通月卡(25元),人数,付费次数,VIP月卡(98元),人数,付费次数\r\n');
INSERT INTO `report_sql` VALUES ('24', '(  SELECT  T.t_time,  IFNULL(T1.sj_hd, 0) AS sj_hd,  IFNULL(T1.sj_xh, 0) AS sj_xh,  IFNULL(T1.sj_lc, 0) AS sj_lc,  IFNULL(T1.jb_hd, 0) AS jb_hd,  IFNULL(T1.jb_xh, 0) AS jb_xh,  IFNULL(T1.jb_lc, 0) AS jb_lc  FROM  ({0}) AS T  LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(sj_hd) AS sj_hd,  SUM(sj_xh) AS sj_xh,  SUM(sj_lc) AS sj_lc,  SUM(jb_hd) AS jb_hd,  SUM(jb_xh) AS jb_xh,  SUM(jb_lc) AS jb_lc  FROM  money_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time  ) AS T1 ON T1.d_time = T.t_time ) UNION  (  SELECT  \"总计:\" AS d_time,  IFNULL(SUM(sj_hd), 0) AS sj_hd,  IFNULL(SUM(sj_xh), 0) AS sj_xh,  IFNULL(SUM(sj_lc), 0) AS sj_lc,  IFNULL(SUM(jb_hd), 0) AS jb_hd,  IFNULL(SUM(jb_xh), 0) AS jb_xh,  IFNULL(SUM(jb_lc), 0) AS jb_lc  FROM  money_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  ) \r\n', 't_time,sj_hd,sj_xh,sj_lc,jb_hd,jb_xh,jb_lc', '日期,产出,消耗,留存,产出,消耗,留存');
INSERT INTO `report_sql` VALUES ('25', 'SELECT  b.id,  b. NAME AS step,  IFNULL(SUM(T.sum_player), 0) AS num FROM  quest_info AS b LEFT JOIN (  SELECT  a.quest_id,  a.sum_player  FROM  quest_summary AS a  WHERE  a.t_time {0}  AND a.platform LIKE \"{2}\"  AND a.server_id LIKE \"{1}\" ) AS T ON T.quest_id = b.id GROUP BY b.id \r\n', 'id,step,num', '任务ID,任务名称,人数(设备号)');
INSERT INTO `report_sql` VALUES ('26', 'SELECT    T.lv as lv,COUNT(T.lv) as sum_lv FROM (SELECT   player_id,  MAX(`level`) as lv FROM logs GROUP BY player_id) as T GROUP BY T.lv;', 'lv,sum_lv', '等级,人数');
INSERT INTO `report_sql` VALUES ('100', 'SELECT  T.t_time,  SUM(T.newPlayer) as newPlayer,  SUM(T.payCount) as payCount,  SUM(T.paySum) as paySum FROM ((SELECT DATE_FORMAT(create_time, \"%Y-%m-%d %H\") AS t_time,COUNT(DISTINCT account) as newPlayer,\"\" as payCount,\"\" as paySum FROM account WHERE create_time {0} AND platform LIKE \"{2}\" AND prev_srv_id LIKE \"{1}\" GROUP BY t_time) UNION ALL (SELECT DATE_FORMAT(finish_time, \"%Y-%m-%d %H\") AS t_time ,\"\" as newPlayer,COUNT(DISTINCT account_name) as payCount,sum(amount) as paySum FROM `order` WHERE finish_time {0} AND platform LIKE \"{2}\" AND server_id LIKE \"{1}\" GROUP BY t_time)) as T GROUP BY T.t_time \r\n', 't_time,newPlayer,payCount,paySum', '时间(小时),绝对新增,总付费玩家数,总付费金额');
INSERT INTO `report_sql` VALUES ('200', '(  SELECT  T6.t_time,  IFNULL(T4.device_reg, 0) AS device_reg,  IFNULL(T.absNewUser, 0) + IFNULL(T7.notInGame, 0) AS newRegisterUser,  T.absNewUser,  T8.DAU,  T2.payUser1,  T2.paySum,  FORMAT(  T3.newPay / T3.newPPayCount,  2  ) AS arpu,  FORMAT(T2.paySum / T2.payUser1, 2) AS arppu1,  FORMAT(T2.paySum / T8.DAU, 2) AS DAUARP,  FORMAT((T2.payUser1 / T8.DAU) * 100, 2) AS payRate,  T3.newPay,  T3.newPPayCount,  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0) AS oldPaySum,  IFNULL(T2.payUser1, 0) - IFNULL(T3.newPPayCount, 0) AS oldPayCount,  FORMAT((T9.retained1/T10.retainedT) * 100,2) as retained1  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  DATE_FORMAT(reg_time, \"%Y-%m-%d\") AS t_time,  COUNT(  DISTINCT device_reg.deviceUI  ) AS device_reg  FROM  device_reg  WHERE  platform LIKE \"{3}\"  AND reg_time {1}  GROUP BY  t_time  ) AS T4 ON T4.t_time = T6.t_time  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS absNewUser  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"{2}\"  AND account.prev_srv_id NOT LIKE \"0\"  GROUP BY  t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT id) AS notInGame  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"0\"  GROUP BY  t_time  ) AS T7 ON T.t_time = T7.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_name) AS payUser1,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (  SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time  ) AS T3 ON T3.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(login_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT account) AS DAU  FROM  account  WHERE  login_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T8 ON T8.t_time = T6.t_time  LEFT JOIN (  SELECT COUNT(DISTINCT account) as retained1,CURDATE() as t_time FROM account WHERE DATEDIFF(create_time,CURDATE()) = -1 AND DATEDIFF(login_time,CURDATE()) = 0  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\" GROUP BY t_time  )As T9 ON T9.t_time = T6.t_time  LEFT JOIN (  SELECT COUNT(DISTINCT account) as retainedT,CURDATE() as t_time FROM account WHERE DATEDIFF(create_time,CURDATE()) = -1 AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\" GROUP BY t_time  )As T10 ON T10.t_time = T6.t_time ) \r\n', 't_time,device_reg,newRegisterUser,absNewUser,DAU,payUser1,paySum,arpu,arppu1,DAUARP,payRate,newPay,newPPayCount,oldPaySum,oldPayCount,retained1', '时间,设备新增,新增注册(含衮服),绝对新增,DAU,付费玩家数,付费金额,ARPU,ARPPU,DAUARPU,付费率,新付费总额,新付费玩家数,老用户付费金额,老付费玩家数,次日留存');



-- ----------------------------
-- Table structure for retention
-- ----------------------------
DROP TABLE IF EXISTS `retention`;
CREATE TABLE `retention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `retained1` int(255) DEFAULT NULL COMMENT '次日留存',
  `retained3` int(255) DEFAULT NULL COMMENT '3日留存',
  `retained7` int(255) DEFAULT NULL COMMENT '7日留存',
  `retained14` int(255) DEFAULT NULL COMMENT '14日留存',
  `retained30` int(255) DEFAULT NULL COMMENT '30日留存',
  `retained31` int(255) DEFAULT NULL COMMENT '31日留存',
  `retained32` int(255) DEFAULT NULL COMMENT '32日留存',
  `create_date` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `dru` int(255) DEFAULT NULL COMMENT '每日新注册用户',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for rmb_crystal
-- ----------------------------
DROP TABLE IF EXISTS `rmb_crystal`;
CREATE TABLE `rmb_crystal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL,
  `c` bigint(255) DEFAULT NULL COMMENT '总充值收入',
  `b` bigint(255) DEFAULT NULL COMMENT '总充值消费次数',
  `p` bigint(255) DEFAULT NULL COMMENT '总充值人数',
  `c1` bigint(255) DEFAULT NULL COMMENT '10元 收入',
  `b1` bigint(255) DEFAULT NULL COMMENT '10元 消费次数',
  `p1` bigint(255) DEFAULT NULL COMMENT '10元 充值人数',
  `c2` bigint(255) DEFAULT NULL COMMENT '68元 收入',
  `b2` bigint(255) DEFAULT NULL COMMENT '68元 消费次数',
  `p2` bigint(255) DEFAULT NULL COMMENT '68元 充值人数',
  `c3` bigint(255) DEFAULT NULL COMMENT '168元 收入',
  `b3` bigint(255) DEFAULT NULL COMMENT '168元 消费次数',
  `p3` bigint(255) DEFAULT NULL COMMENT '168元 充值人数',
  `c4` bigint(255) DEFAULT NULL COMMENT '328元 收入',
  `b4` bigint(255) DEFAULT NULL COMMENT '328元 消费次数',
  `p4` bigint(255) DEFAULT NULL COMMENT '328元 充值人数',
  `c5` bigint(255) DEFAULT NULL COMMENT '648元 收入',
  `b5` bigint(255) DEFAULT NULL COMMENT '648元 消费次数',
  `p5` bigint(255) DEFAULT NULL COMMENT '648元 充值人数',
  `c6` bigint(255) DEFAULT NULL COMMENT '988元 收入',
  `b6` bigint(255) DEFAULT NULL COMMENT '988元 消费次数',
  `p6` bigint(255) DEFAULT NULL COMMENT '988元 充值人数',
  `c7` bigint(255) DEFAULT NULL COMMENT '1988元 收入',
  `b7` bigint(255) DEFAULT NULL COMMENT '1988元 消费次数',
  `p7` bigint(255) DEFAULT NULL COMMENT '1988元 充值人数',
  `c8` bigint(255) DEFAULT NULL COMMENT '4988元 收入',
  `b8` bigint(255) DEFAULT NULL COMMENT '4988元 消费次数',
  `p8` bigint(255) DEFAULT NULL COMMENT '4988元 充值人数',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(255) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='## 人民币购买水晶消费档次统计';

-- ----------------------------
-- Table structure for server_xftp
-- ----------------------------
DROP TABLE IF EXISTS `server_xftp`;
CREATE TABLE `server_xftp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `host` varchar(255) DEFAULT NULL COMMENT '内网ip',
  `port` int(255) DEFAULT NULL COMMENT '端口',
  `user_name` varchar(255) DEFAULT NULL COMMENT 'ftp用户名',
  `password` varchar(255) DEFAULT NULL COMMENT 'ftp 密码',
  `game_path` varchar(255) DEFAULT NULL COMMENT '游戏文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for summary_sql
-- ----------------------------
DROP TABLE IF EXISTS `summary_sql`;
CREATE TABLE `summary_sql` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sql` longtext COMMENT 'sql 汇总脚本',
  `title` longtext COMMENT '表头',
  `table` varchar(255) DEFAULT NULL COMMENT '表名',
  `content` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='# 汇总 sql';

-- ----------------------------
-- Records of summary_sql
-- ----------------------------
INSERT INTO `summary_sql` VALUES ('4', 'SELECT  T.t_time,  T.content AS goodsId,  COUNT(T.player_id) AS count,  T.platform,  T.server_id FROM  (  SELECT  REPLACE (  REPLACE (  content,  substr(  content,  locate(\"),\", content),  CHAR_LENGTH(content) - locate(\"),\", content) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"ID(\", content) + 2  ),  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  player_id,  platform,  srvId as server_id  FROM  `logs` LEFT JOIN account ON player_id = id  WHERE  type1 LIKE \"商城\"  AND type2 LIKE \"购买钻石\"  AND log_time LIKE \"{1}\"  ) AS T GROUP BY  T.content,  T.t_time,  T.platform,  T.server_id \r\n', 't_time,goodsId,count,platform,server_id', 'rmb_crystal', '计费档次购买分析 人民币-购买水晶');
INSERT INTO `summary_sql` VALUES ('6', 'SELECT  t2.t_time,  t2.content AS fb_id,  SUM(t2.ks) AS tz_player,  SUM(t2.ksA) AS tz_num,  SUM(t2.js) AS tz_win_player,  SUM(t2.jsA) AS tz_win_player_cs,  t2.platform,  t2.server_id FROM  (  SELECT  t1.content,  t1.t_time,  t1.platform,  t1.server_id,  CASE  WHEN t1.type2 LIKE \"开始战斗\"  OR t1.type2 LIKE \"扫荡\" THEN  COUNT(DISTINCT t1.player_id)  ELSE  0  END AS ks,  CASE  WHEN t1.type2 LIKE \"开始战斗\"  OR t1.type2 LIKE \"扫荡\" THEN  COUNT(*)  ELSE  0  END AS ksA,  CASE WHEN t1.type2 LIKE \"结束战斗\" OR t1.type2 LIKE \"扫荡\" THEN  COUNT(DISTINCT t1.player_id) ELSE  0 END AS js,  CASE WHEN t1.type2 LIKE \"结束战斗\" OR t1.type2 LIKE \"扫荡\" THEN  COUNT(*) ELSE  0 END AS jsA FROM  (  SELECT  t.content,  t.player_id,  t.t_time,  t.type2,  t.platform,  t.server_id  FROM  (  SELECT  REPLACE (REPLACE (  content,  SUBSTR(  content,  locate(\"),\", content),  CHAR_LENGTH(content) - locate(\"),\", content)+1  ),  \"\"  ),SUBSTR(  content,  1,  locate(\",\", content)  ),  \"\") AS content,  player_id,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  type2,  a.platform,  a.srvId AS server_id  FROM  `logs`  LEFT JOIN account AS a ON a.id = player_id  WHERE  (  type1 LIKE \"冒险\"  OR type1 LIKE \"四大天王\"  )  AND log_time LIKE \"{1}%\"  AND (  type2 LIKE \"结束战斗\"  OR type2 LIKE \"扫荡\"  OR type2 LIKE \"开始战斗\"  )  AND content NOT LIKE \"%遇怪%\"  ) AS t  ) AS t1 GROUP BY  t1.content,  t1.type2,  t1.t_time,  t1.platform,  t1.server_id  ) AS t2 GROUP BY  t2.content,  t2.t_time,  t2.platform,  t2.server_id ', 't_time,fb_id,tz_player,tz_num,tz_win_player,tz_win_player_cs,platform,server_id', 'fb_summary', '副本挑战汇总');
INSERT INTO `summary_sql` VALUES ('12', 'SELECT  home_id AS account_id,  platform,srvId as server_id FROM  account   WHERE id IN(SELECT player_id FROM logs  WHERE type1 LIKE \"系统\" AND type2 LIKE \"上线\" AND log_time LIKE \"{1}%\") GROUP BY  home_id,platform,srvId ', 'account_id,platform,server_id', 'game_login_user_cache', '游戏中登录玩家 缓存表');
INSERT INTO `summary_sql` VALUES ('13', 'SELECT home_id as account_id,  platform,srvId as server_id  FROM account  WHERE id IN  (SELECT player_id FROM logs WHERE type1 LIKE \"任务\" AND type2 LIKE \"完成任务\"  AND content like \"%200100014%\" AND log_time LIKE \"{1}%\") GROUP BY  home_id,platform,srvId \r\n', 'account_id,platform,server_id', 'game_valid_user_cache', '有效用户汇总');
INSERT INTO `summary_sql` VALUES ('14', 'SELECT  platform,  srvId AS server_id,  T.guide_id,  COUNT(DISTINCT home_id) AS sum_player,  T.t_time FROM  account LEFT JOIN (  SELECT  REPLACE (  content,  substr(  content,  1,  LOCATE(\":\", content)  ),  \"\"  ) AS guide_id,  player_id,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  `logs`  WHERE  type1 LIKE \"%新手引导%\"  AND log_time LIKE \"{1}%\" ) AS T ON T.player_id = id WHERE  id IN (  SELECT  player_id  FROM  `logs`  WHERE  type1 LIKE \"%新手引导%\"  AND log_time LIKE \"{1}%\"  ) GROUP BY  platform,  srvId,  T.guide_id ', 'platform,server_id,guide_id,sum_player,t_time', 'guide_summary', '新手引导');
INSERT INTO `summary_sql` VALUES ('15', 'SELECT  platform,  srvId AS server_id,  T.quest_id,  COUNT(DISTINCT home_id) AS sum_player,  T.t_time FROM  account LEFT JOIN (  SELECT  REPLACE (  REPLACE (  content,  SUBSTR(  content,  LOCATE(\"]\", content),  CHAR_LENGTH(content) - LOCATE(\"]\", content) + 1  ),  \"\"  ),  SUBSTR(  content,  1,  LOCATE(\"[\", content)  ),  \"\"  ) AS quest_id,  player_id,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  `logs`  WHERE  type1 LIKE \"任务\"  AND log_time LIKE \"{1}%\" ) AS T ON T.player_id = id WHERE  id IN (  SELECT  player_id  FROM  `logs`  WHERE  type1 LIKE \"任务\"  AND log_time LIKE \"{1}%\"  ) GROUP BY  platform,  srvId,  T.quest_id', 'platform,server_id,quest_id,sum_player,t_time', 'quest_summary', '任务统计');
INSERT INTO `summary_sql` VALUES ('16', 'SELECT  COUNT(DISTINCT home_id) AS dau,  platform,  srvId AS server_id,  \"{1}\" AS t_time FROM  account WHERE  id IN (  SELECT  player_id  FROM  `logs`  WHERE  type1 LIKE \"系统\"  AND (type2 LIKE \"上线\" OR type2 LIKE \"下线\")   AND log_time LIKE \"{1}%\"  ) GROUP BY  platform,  srvId \r\n', 'dau,platform,server_id,t_time', 'dau_summary', 'dau 活跃用户汇总表');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` char(255) NOT NULL,
  `password` char(255) NOT NULL,
  `authority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '权限',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ------------------------------------------------
--           sq统计存储过程
--
-- ------------------------------------------------

-- 用户转化
DELIMITER $$
DROP PROCEDURE IF EXISTS pay_info$$
CREATE PROCEDURE pay_info(IN `today` char(32),IN `platfrom0` char(32))
BEGIN
-- 今天时间
set @today = UNIX_TIMESTAMP(`today`);
set @todayStr = concat(date(from_unixtime(@today)), "%");
-- 计算前天时间
set @yestoday = @today - 1*24*60*60;
set @yestodayStr = concat(date(from_unixtime(@yestoday)), "%");
-- 计算明天时间
set @tomorrow = @today + 24*60*60;

-- 新增充值人数
set @newCount = 0;
select sum(amount) into @newCount from  poke_home.`order` where UNIX_TIMESTAMP(create_time)>=@today and  UNIX_TIMESTAMP(finish_time)<=@tomorrow and  poke_home.`order`.platform LIKE platfrom0;


-- 总充值用户
set @totalUser = 0;
set @totalCount = 0;
select count(distinct account_id) into @totalUser from  poke_home.`order` where UNIX_TIMESTAMP(finish_time)<=@tomorrow and  poke_home.`order`.platform LIKE platfrom0;
select sum(amount) into @totalCount from  poke_home.`order` where UNIX_TIMESTAMP(finish_time)<=@tomorrow and  poke_home.`order`.platform LIKE platfrom0;


-- 当日总充值用户
set @totalDayUser = 0;
-- set @totalDayCount = 0;
select count(distinct account_id) into @totalDayUser from  poke_home.`order` where finish_time LIKE @todayStr and  poke_home.`order`.platform LIKE platfrom0;
-- select sum(amount) into @totalDayCount from `order` where finish_time LIKE @todayStr and `order`.platform LIKE platfrom0;




-- 通过算出前天充值总用户
set @totalUser0 = 0;
select count(distinct account_id) into @totalUser0 from  poke_home.`order` where UNIX_TIMESTAMP(finish_time)<=@today and  poke_home.`order`.platform LIKE platfrom0;
set @newUser = @totalUser-@totalUser0;




select @newUser, @totalDayUser, @totalUser, @newCount, @totalCount;
END
$$
DELIMITER ;


-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS player_acu$$
CREATE PROCEDURE player_acu()
BEGIN 

-- 建议延迟一天执行
-- 按小时 统计活跃人数
DECLARE c_date date;
DECLARE n_date  date;
DECLARE s_id int;
set n_date = CURDATE();
-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
set c_date = date_sub(n_date,INTERVAL 1 day);

set s_id = -100;

-- 插入活跃数据分服务器，分渠道
insert into poke_mgr.acu(platform,server_id,playCount,log_time) SELECT platform,server_id,COUNT(DISTINCT account_id),c_date from poke_home.login_log where DATEDIFF(c_date,last_log_time)=0 GROUP BY platform,server_id;
-- 插入活跃数据分渠道
insert into poke_mgr.acu(platform,server_id,playCount,log_time) SELECT platform,s_id,COUNT(DISTINCT account_id),c_date from poke_home.login_log where DATEDIFF(c_date,last_log_time)=0 GROUP BY platform;
 
if exists(select * from poke_mgr.acu where datediff(c_date,log_time) = 0)
	then
		-- 插入按时间，分服务器，分渠道
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 0 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_01 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 1 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_02 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 2 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_03 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 3 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_04 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 4 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_05 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 5 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_06 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 6 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_07 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 7 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_08 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 8 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_09 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 9 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_10 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 10 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_11 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 11 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_12 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_13 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_14 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 14 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_15 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 15 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_16 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 16 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_17 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 17 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_18 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 18 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_19 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 19 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_20 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 20 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_21 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 21 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_22 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 22 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_23 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 23 GROUP BY platform,server_id) as T ON T.platform = poke_mgr.acu.platform AND T.server_id = poke_mgr.acu.server_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_24 = T.a;
		
		-- 插入按时间，分服务器
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 0 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_01 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 1 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_02 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 2 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_03 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 3 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_04 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 4 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_05 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 5 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_06 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 6 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_07 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 7 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_08 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 8 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_09 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 9 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_10 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 10 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_11 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 11 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_12 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_13 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_14 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 14 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_15 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 15 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_16 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 16 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_17 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 17 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_18 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 18 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_19 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 19 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_20 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 20 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_21 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 21 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_22 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 22 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_23 = T.a;
		UPDATE poke_mgr.acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM poke_home.login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 23 GROUP BY platform) as T ON T.platform = poke_mgr.acu.platform AND poke_mgr.acu.server_id = s_id AND datediff(c_date, poke_mgr.acu.log_time) = 0 set poke_mgr.acu.hour_24 = T.a;
end if;  

END
$$
DELIMITER ;


-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS start_f_pay_retention$$
CREATE PROCEDURE start_f_pay_retention()
BEGIN
	-- 统计新增首次付费 (留存率)---
	-- Routine body goes here...
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);

	--  -100 代表所有服务器
	set s_id = -100;

	-- 创建(设置时间的)前一天要统计的留存数据 
		-- 分渠道，分服
	INSERT INTO poke_mgr.f_pay_retention(create_date,platform,server_id) SELECT  c_date,platform,server_id  FROM  poke_home.`order`  WHERE  DATEDIFF(finish_time, c_date) = 0  GROUP BY  server_id,platform;

	-- 分渠道
	INSERT INTO poke_mgr.f_pay_retention(create_date,platform,server_id) SELECT  c_date,platform,s_id  FROM  poke_home.`order`  WHERE  DATEDIFF(finish_time, c_date) = 0  GROUP BY platform;
	
	
	-- 首付相关数据
	IF exists(select * from poke_mgr.f_pay_retention where datediff(c_date,create_date) = 0)
THEN
		-- 日付费次日留存
		-- 分渠道，服务器
		UPDATE poke_mgr.f_pay_retention AS F  INNER JOIN ( SELECT  count(DISTINCT V2.account_name) AS a,  V2.platform,  V2.server_id FROM  (  SELECT  account_id,  platform,  server_id  FROM  poke_home.login_log  WHERE  DATEDIFF(last_log_time, c_date) = 1  GROUP BY  account_id,  platform,  server_id  ) AS V LEFT JOIN  (  SELECT  id,  account,  platform,  prev_srv_id  FROM  poke_home.account  GROUP BY  id,  account,  platform,  prev_srv_id  ) AS V3 ON V3.id = V.account_id AND V3.platform = V.platform AND V3.prev_srv_id = V.server_id LEFT JOIN (  SELECT  platform,  server_id,  t.account_name  FROM  poke_home.`order` AS t  WHERE  DATEDIFF(finish_time, c_date) = 0  GROUP BY  t.account_name,  t.platform,  t.server_id ) AS V2 ON V3.account = V2.account_name AND V3.platform = V2.platform AND V3.prev_srv_id = V2.server_id GROUP BY V2.platform,V2.server_id )
		AS T2 ON F.platform = T2.platform AND F.server_id = T2.server_id AND datediff( c_date, F.create_date) = 0 SET F.pay_count01 = T2.a;	
		
		-- 分渠道
		UPDATE poke_mgr.f_pay_retention AS F  INNER JOIN ( SELECT  count(DISTINCT V2.account_name) AS a,  V2.platform FROM  (  SELECT  account_id,  platform  FROM  poke_home.login_log  WHERE  DATEDIFF(last_log_time, c_date) = 1  GROUP BY  account_id,  platform  ) AS V LEFT JOIN  (  SELECT  id,  account,  platform  FROM  poke_home.account  GROUP BY  id,  account,  platform,  prev_srv_id  ) AS V3 ON V3.id = V.account_id AND V3.platform = V.platform LEFT JOIN (  SELECT  platform,  t.account_name  FROM  poke_home.`order` AS t  WHERE  DATEDIFF(finish_time, c_date) = 0  GROUP BY  t.account_name,  t.platform ) AS V2 ON V3.account = V2.account_name AND V3.platform = V2.platform  GROUP BY V2.platform )
		AS T2 ON F.platform = T2.platform AND F.server_id = s_id AND datediff( c_date, F.create_date) = 0 SET F.pay_count01 = T2.a;	
		
	
		-- 首付次日留存
		-- 分渠道,分服务器
		UPDATE poke_mgr.f_pay_retention AS F  INNER JOIN (SELECT  V.platform,  count(DISTINCT V2.account_name) AS a,  V.server_id FROM  (  SELECT  platform,  server_id,  account_id  FROM  poke_home.login_log  WHERE  DATEDIFF(last_log_time, c_date) =1  GROUP BY  server_id,  platform,  account_id  ) AS V LEFT JOIN  (  SELECT  id,  account,  platform,  prev_srv_id  FROM  poke_home.account  GROUP BY  id,  account  ) AS V3 ON V3.id = V.account_id AND V3.platform = V.platform AND V3.prev_srv_id = V.server_id LEFT JOIN (  SELECT  T.account_name,  T.platform,  T.server_id  FROM  (  SELECT  DATE_FORMAT(o.finish_time, "%Y-%m-%d") AS t_time,  o.account_name,  o.platform,  o.server_id  FROM  poke_home.`order` AS o  GROUP BY  o.account_name,  o.platform,  o.server_id  ORDER BY  t_time ASC  ) AS T  WHERE  DATEDIFF(T.t_time, c_date) = 0  GROUP BY  T.account_name,T.platform,T.server_id ) AS V2 ON V3.account = V2.account_name AND V3.platform = V2.platform AND V3.prev_srv_id = V2.server_id GROUP BY V2.platform,V2.server_id ) 
		AS T2 ON F.platform = T2.platform AND F.server_id = T2.server_id AND datediff( c_date, F.create_date) = 0 SET F.f_pay_count01 = T2.a;	
		
		-- 分渠道
		UPDATE poke_mgr.f_pay_retention AS F  INNER JOIN (SELECT V4.platform,SUM(V4.a) as a FROM(SELECT  V.platform,  count(DISTINCT V2.account_name) AS a,  V.server_id FROM  (  SELECT  platform,  server_id,  account_id  FROM  poke_home.login_log  WHERE  DATEDIFF(last_log_time, c_date) =1  GROUP BY  server_id,  platform,  account_id  ) AS V LEFT JOIN  (  SELECT  id,  account,  platform,  prev_srv_id  FROM  poke_home.account  GROUP BY  id,  account  ) AS V3 ON V3.id = V.account_id AND V3.platform = V.platform AND V3.prev_srv_id = V.server_id LEFT JOIN (  SELECT  T.account_name,  T.platform,  T.server_id  FROM  (  SELECT  DATE_FORMAT(o.finish_time, "%Y-%m-%d") AS t_time,  o.account_name,  o.platform,  o.server_id  FROM  poke_home.`order` AS o  GROUP BY  o.account_name,  o.platform,  o.server_id  ORDER BY  t_time ASC  ) AS T  WHERE  DATEDIFF(T.t_time, c_date) = 0  GROUP BY  T.account_name,T.platform,T.server_id ) AS V2 ON V3.account = V2.account_name AND V3.platform = V2.platform AND V3.prev_srv_id = V2.server_id GROUP BY V2.platform,V2.server_id) AS V4 GROUP BY V4.platform ) 
		AS T2 ON F.platform = T2.platform AND F.server_id = s_id AND datediff( c_date, F.create_date) = 0 SET F.f_pay_count01 = T2.a;	
		
		
	END IF;
END
$$
DELIMITER ;



-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS start_game_per_hour_online$$
CREATE PROCEDURE start_game_per_hour_online()
BEGIN
	
	DECLARE n_date date;
	DECLARE c_date date;
	DECLARE s_id int DEFAULT 0;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);


-- 统计acu & pcu 
INSERT INTO poke_mgr.acu_pcu_summary(pcu,acu,server_id,t_time)
SELECT
	greatest(a.hour_01,a.hour_02,a.hour_03,a.hour_04,a.hour_05,a.hour_06,a.hour_07,a.hour_08,a.hour_09,a.hour_10,
	a.hour_11,a.hour_12,a.hour_13,a.hour_14,a.hour_15,a.hour_16,a.hour_17,a.hour_18,a.hour_19,a.hour_20,
	a.hour_21,a.hour_22,a.hour_23,a.hour_24) as pcu,
	FORMAT(a.totalSum / 24,2) as acu,
	a.server_id,
	c_date
FROM
(
SELECT 
IFNULL(hour_01,0) as hour_01,
IFNULL(hour_02,0) as hour_02,
IFNULL(hour_03,0) as hour_03,
IFNULL(hour_04,0) as hour_04,
IFNULL(hour_05,0) as hour_05,
IFNULL(hour_06,0) as hour_06,
IFNULL(hour_07,0) as hour_07,
IFNULL(hour_08,0) as hour_08,
IFNULL(hour_09,0) as hour_09,
IFNULL(hour_10,0) as hour_10,
IFNULL(hour_11,0) as hour_11,
IFNULL(hour_12,0) as hour_12,
IFNULL(hour_13,0) as hour_13,
IFNULL(hour_14,0) as hour_14,
IFNULL(hour_15,0) as hour_15,
IFNULL(hour_16,0) as hour_16,
IFNULL(hour_17,0) as hour_17,
IFNULL(hour_18,0) as hour_18,
IFNULL(hour_19,0) as hour_19,
IFNULL(hour_20,0) as hour_20,
IFNULL(hour_21,0) as hour_21,
IFNULL(hour_22,0) as hour_22,
IFNULL(hour_23,0) as hour_23,
IFNULL(hour_24,0) as hour_24,
IFNULL(hour_01,0) + IFNULL(hour_02,0) + IFNULL(hour_03,0) + IFNULL(hour_04,0) + IFNULL(hour_05,0) +
IFNULL(hour_06,0) + IFNULL(hour_07,0) + IFNULL(hour_08,0) + IFNULL(hour_09,0) + IFNULL(hour_10,0) + 
IFNULL(hour_11,0) + IFNULL(hour_12,0) + IFNULL(hour_13,0) + IFNULL(hour_14,0) + IFNULL(hour_15,0) + 
IFNULL(hour_16,0) + IFNULL(hour_17,0) + IFNULL(hour_18,0) + IFNULL(hour_19,0) + IFNULL(hour_20,0) + 
IFNULL(hour_21,0) + IFNULL(hour_22,0) + IFNULL(hour_23,0) + IFNULL(hour_24,0) as totalSum,
server_id
 FROM  poke_mgr.game_per_hour_online_summary WHERE DATEDIFF(log_time,c_date) = 0 ) as a;

-- 清空 当日游戏注册用户数据
DELETE FROM poke_mgr.game_online_user_cache;

END
$$
DELIMITER ;


-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS start_ltv$$
CREATE PROCEDURE start_ltv()
BEGIN
	-- 30 日 LTV 表 ---
	-- Routine body goes here...
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);

	
	-- 创建(设置时间的)前一天要统计的留存数据 
		-- 分渠道，分服
	INSERT INTO poke_mgr.pay_lty(create_date,platform,server_id,user_add) SELECT c_date,account.platform,account.prev_srv_id,COUNT(DISTINCT poke_home.account.account) FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) =0 GROUP BY account.platform,account.prev_srv_id;


	-- 开始计算 首付次日留存,首付7日留存
	
	-- 统计当日付费总 
	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = 0)
THEN
		-- 统计当日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 0 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) = 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date) = 0)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = 0 SET pay_lty.pay_01 = T2.a;
END IF;	

	
	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -1)
THEN
		-- 统计1日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 1 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time,c_date) BETWEEN -1 AND 0  AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date) = -1)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -1 SET pay_lty.pay_02 = T2.a;
	END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -2)
THEN
		-- 统计2日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 2 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -2 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date) = -2)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -2 SET pay_lty.pay_03 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -3)
THEN
		-- 统计3日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 3 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -3 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -3)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -3 SET pay_lty.pay_04 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -4)
THEN
-- 统计4日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 4 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -4 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -4)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -4 SET pay_lty.pay_05 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -5)
THEN
-- 统计5日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 5 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -5 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -5)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -5 SET pay_lty.pay_06 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -6)
THEN
-- 统计6日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 6 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -6 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -6)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -6 SET pay_lty.pay_07 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) =-7)
THEN
-- 统计7日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 7 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -7 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -7)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -7 SET pay_lty.pay_08 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -8)
THEN
-- 统计8日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 8 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -8 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -8)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -8 SET pay_lty.pay_09 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -9)
THEN
-- 统计9日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 9 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -9 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -9)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -9 SET pay_lty.pay_10 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -10)
THEN
-- 统计10日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 10 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -10 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -10)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -10 SET pay_lty.pay_11 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -11)
THEN
-- 统计11日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 11 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -11 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -11)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -11 SET pay_lty.pay_12 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -12)
THEN
-- 统计12日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 12 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -12 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -12)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -12 SET pay_lty.pay_13 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -13)
THEN
-- 统计13日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 13 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -13 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -13)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -13 SET pay_lty.pay_14 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -14)
THEN
-- 统计14日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 14 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -14 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -14)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -14 SET pay_lty.pay_15 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -15)
THEN
-- 统计15日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 15 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date)BETWEEN -15 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -15)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -15 SET pay_lty.pay_16 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -16)
THEN
-- 统计16日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 16 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -16 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -16)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -16 SET pay_lty.pay_17 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -17)
THEN
-- 统计17日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 17 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -17 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -17)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -17 SET pay_lty.pay_18 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -18)
THEN
-- 统计18日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 18 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -18 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -18)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -18 SET pay_lty.pay_19 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -19)
THEN
-- 统计19日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 19 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -19 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -19)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -19 SET pay_lty.pay_20 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -20)
THEN
-- 统计20日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 20 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -20 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -20)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -20 SET pay_lty.pay_21 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -21)
THEN
-- 统计21日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 21 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -21 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -21)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -21 SET pay_lty.pay_22 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -22)
THEN
-- 统计22日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 22 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -22 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -22)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -22 SET pay_lty.pay_23 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -23)
THEN
-- 统计23日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 23 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -23 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -23)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -23 SET pay_lty.pay_24 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -24)
THEN
-- 统计24日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 24 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -24 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -24)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -24 SET pay_lty.pay_25 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -25)
THEN
-- 统计25日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 25 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date)BETWEEN -25 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -25)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -25 SET pay_lty.pay_26 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -26)
THEN
-- 统计26日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 26 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -26 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -26)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -26 SET pay_lty.pay_27 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -27)
THEN
-- 统计27日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 27 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date)BETWEEN -27 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -27)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -27 SET pay_lty.pay_28 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -28)
THEN
-- 统计28日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 28 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -28 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -28)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -28 SET pay_lty.pay_29 = T2.a;
END IF;

	IF exists(select * from poke_mgr.pay_lty where datediff(create_date,c_date) = -29)
THEN
-- 统计29日付费总 
		UPDATE poke_mgr.pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 29 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM poke_home.`order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -29 AND 0 AND t.account_name IN ( SELECT account FROM poke_home.account WHERE DATEDIFF(create_time,c_date ) = -29)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -29 SET pay_lty.pay_30 = T2.a;
END IF;
	
END
$$
DELIMITER ;



-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS start_new_pay$$
CREATE PROCEDURE start_new_pay()
BEGIN
	-- 统计新付费人数 
	-- Routine body goes here...
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);

	--  -100 代表所有服务器
	set s_id = -100;

	-- 创建(设置时间的)前一天要统计的留存数据 
		-- 分渠道，分服
	INSERT INTO poke_mgr.n_pay (create_time,n_user_pay_money,n_user_pay,n_user_pay_r,server_id,platform)SELECT V.t_time, IFNULL(SUM(V2.payNew),0) AS newPay, IFNULL(SUM(V2.newPPCount),0) AS newPPayCount, IFNULL(SUM(V2.newPPCountR),0) AS newPPayCountR, V.prev_srv_id, V.platform FROM ( SELECT DATE_FORMAT( account.create_time, "%Y-%m-%d" ) AS t_time, platform, prev_srv_id, account FROM poke_home.account WHERE DATEDIFF(c_date,create_time) = 0 GROUP BY t_time, prev_srv_id, platform, account ) AS V LEFT JOIN ( SELECT DATE_FORMAT(t.finish_time, "%Y-%m-%d") AS t_time, SUM(t.amount) AS payNew, COUNT(DISTINCT t.account_name) AS newPPCount, COUNT(t.account_name) AS newPPCountR, platform, server_id, t.account_name FROM poke_home.`order` AS t WHERE DATEDIFF(c_date,t.finish_time) = 0 GROUP BY t_time, t.server_id, t.platform, t.account_name ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id AND V.platform = V2.platform AND V.account = V2.account_name GROUP BY V.prev_srv_id,V.platform ;
		-- 分渠道
	INSERT INTO poke_mgr.n_pay (create_time,n_user_pay_money,n_user_pay,n_user_pay_r,server_id,platform)SELECT V.t_time, IFNULL(SUM(V2.payNew),0) AS newPay, IFNULL(SUM(V2.newPPCount),0) AS newPPayCount, IFNULL(SUM(V2.newPPCountR),0) AS newPPayCountR, s_id, V.platform FROM ( SELECT DATE_FORMAT( account.create_time, "%Y-%m-%d" ) AS t_time, platform, prev_srv_id, account FROM poke_home.account WHERE DATEDIFF(c_date,create_time) = 0 GROUP BY t_time, prev_srv_id, platform, account ) AS V LEFT JOIN ( SELECT DATE_FORMAT(t.finish_time, "%Y-%m-%d") AS t_time, SUM(t.amount) AS payNew, COUNT(DISTINCT t.account_name) AS newPPCount, COUNT(t.account_name) AS newPPCountR, platform, server_id, t.account_name FROM poke_home.`order` AS t WHERE DATEDIFF(c_date,t.finish_time) = 0 GROUP BY t_time, t.server_id, t.platform, t.account_name ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id AND V.platform = V2.platform AND V.account = V2.account_name GROUP BY V.platform ;
END
$$
DELIMITER ;

-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS start_new_user_change$$
CREATE PROCEDURE start_new_user_change()
BEGIN
	
	DECLARE n_date date;
	DECLARE c_date date;
	DECLARE s_id int DEFAULT 0;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);
	

	-- 新增注册
	INSERT INTO poke_mgr.new_u_change(t_time,server_id,platform,new_reg) SELECT c_date,prev_srv_id,platform,COUNT(account) as new_reg FROM poke_home.account WHERE  DATEDIFF(account.create_time,c_date) = 0 GROUP BY prev_srv_id,platform;

	IF exists(select * from poke_mgr.new_u_change where datediff(c_date,t_time) = 0)
		THEN

			-- 游戏中登录
			UPDATE poke_mgr.new_u_change INNER JOIN( SELECT COUNT(DISTINCT account_id) AS a,platform, server_id FROM poke_mgr.game_login_user_cache WHERE account_id IN (SELECT id as account_id FROM account WHERE  DATEDIFF(account.create_time,c_date) = 0 ) GROUP BY server_id,platform ) AS T2 ON poke_mgr.new_u_change.platform = T2.platform AND poke_mgr.new_u_change.server_id = T2.server_id AND datediff( c_date, poke_mgr.new_u_change.t_time) = 0 SET poke_mgr.new_u_change.login_u = T2.a;
			-- 游戏中有效用户
			UPDATE poke_mgr.new_u_change INNER JOIN( SELECT COUNT(DISTINCT account_id) AS a,platform, server_id FROM poke_mgr.game_valid_user_cache WHERE account_id IN (SELECT id as account_id FROM account WHERE  DATEDIFF(account.create_time,c_date) = 0 ) GROUP BY server_id,platform ) AS T2 ON poke_mgr.new_u_change.platform = T2.platform AND poke_mgr.new_u_change.server_id = T2.server_id AND datediff( c_date, poke_mgr.new_u_change.t_time) = 0 SET poke_mgr.new_u_change.valid_u = T2.a;
			-- 游戏中付费用户
			UPDATE poke_mgr.new_u_change INNER JOIN( SELECT COUNT(DISTINCT o.account_id) AS a,o.platform, o.server_id FROM poke_home.`order` as o WHERE o.account_id IN (SELECT id as account_id FROM account WHERE  DATEDIFF(account.create_time,c_date) = 0 ) GROUP BY server_id,platform ) AS T2 ON poke_mgr.new_u_change.platform = T2.platform AND poke_mgr.new_u_change.server_id = T2.server_id AND datediff( c_date, poke_mgr.new_u_change.t_time) = 0 SET poke_mgr.new_u_change.pay_u = T2.a;
			-- 激活设备
			UPDATE poke_mgr.new_u_change INNER JOIN(SELECT COUNT(DISTINCT deviceUI) as a,platform FROM poke_home.device_reg WHERE  DATEDIFF(reg_time,c_date) = 0 GROUP BY platform) AS T2 ON poke_mgr.new_u_change.platform = T2.platform AND datediff( c_date, poke_mgr.new_u_change.t_time) = 0 SET poke_mgr.new_u_change.new_drive = T2.a;

		END IF;	
	
		-- 清空 当日游戏注册用户数据
		DELETE FROM poke_mgr.game_login_user_cache;

		-- 清空 当日有效用户注册数据
		DELETE FROM poke_mgr.game_valid_user_cache;
END
$$
DELIMITER ;

-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS start_ns_dau_summary$$
CREATE PROCEDURE start_ns_dau_summary()
BEGIN
	-- #Routine body goes here...
	-- 新服活跃数据
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	-- DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);


	-- 每次检查是否有创建新的服务器， 如果有插入到 ，统计表中
	INSERT INTO poke_mgr.ns_dau_summary (s_id,s_name,open_time,platform) SELECT s.id,s.name,s.open_time,s.platforms from poke_home.`server` AS s WHERE id NOT in(select t.s_id FROM poke_mgr.ns_dau_summary as t);

	-- ---------------------
	-- 开始统计活跃
	-- ---------------------
	
	-- 当存在 开服 2天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 2)
THEN
	-- 第2天活跃人数
	UPDATE poke_mgr.ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT login_log.account_id) AS dau2 FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.login_log ON poke_home.login_log.server_id = t.s_id AND DATEDIFF(poke_home.login_log.last_log_time, t.open_time) = 2 GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_dau_summary.s_id AND T1.s_name = poke_mgr.ns_dau_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_dau_summary.open_time) = 2 SET poke_mgr.ns_dau_summary.dau_day2 = T1.dau2;
	
	-- 第2天活跃付费人数
	UPDATE poke_mgr.ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) =2  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_dau_summary.s_id AND T1.s_name = poke_mgr.ns_dau_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_dau_summary.open_time) = 2 SET poke_mgr.ns_dau_summary.dau_pay_day2 = T1.paySum;

	END IF;

-- 当存在 开服 3天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 3)
THEN
	-- 第3天注册人数
	UPDATE poke_mgr.ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT poke_home.login_log.account_id) AS dau3 FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.login_log ON poke_home.login_log.server_id = t.s_id AND DATEDIFF(poke_home.login_log.last_log_time, t.open_time) = 3 GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_dau_summary.s_id AND T1.s_name = poke_mgr.ns_dau_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_dau_summary.open_time) = 3 SET poke_mgr.ns_dau_summary.dau_day3 = T1.dau3;
	
	-- 第3天活跃付费人数
	UPDATE poke_mgr.ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) =3  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_dau_summary.s_id AND T1.s_name = poke_mgr.ns_dau_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_dau_summary.open_time) = 3 SET poke_mgr.ns_dau_summary.dau_pay_day2 = T1.paySum;

	END IF;

-- 当存在 开服 7天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 7)
THEN
	-- 第7天注册人数
	UPDATE poke_mgr.ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT login_log.account_id) AS dau7 FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.login_log ON poke_home.login_log.server_id = t.s_id AND DATEDIFF(poke_home.login_log.last_log_time, t.open_time) = 7 GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_dau_summary.s_id AND T1.s_name = npoke_mgr.s_dau_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_dau_summary.open_time) = 7 SET poke_mgr.ns_dau_summary.dau_day7 = T1.dau7;
		
	-- 第7天活跃付费人数
	UPDATE poke_mgr.ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) =7  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_dau_summary.s_id AND T1.s_name = poke_mgr.ns_dau_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_dau_summary.open_time) = 7 SET poke_mgr.ns_dau_summary.dau_pay_day2 = T1.paySum;

	END IF;	
	
END
$$
DELIMITER ;

-- --------------------------------------------------------

DELIMITER $$
DROP PROCEDURE IF EXISTS start_ns_income_summary$$
CREATE PROCEDURE start_ns_income_summary()
BEGIN
	-- #Routine body goes here...
	-- 新服收入数据
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	-- DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);


	-- 每次检查是否有创建新的服务器， 如果有插入到 ，统计表中
	INSERT INTO poke_mgr.ns_income_summary (s_id,s_name,open_time,platform) SELECT s.id,s.name,s.open_time,s.platforms from poke_home.`server` AS s WHERE id NOT in(select t.s_id FROM poke_mgr.ns_income_summary as t);

	-- ---------------------
	-- 开始统计收入
	-- ---------------------
		
	-- 当存在 开服 2天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 2)
THEN
	-- 前2天注册人数
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id,t.s_name,COUNT(DISTINCT poke_home.account.id) as regSum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.account ON t.s_id = poke_home.account.prev_srv_id AND DATEDIFF( poke_home.account.create_time, t.open_time) BETWEEN 0 AND 2  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 2 SET poke_mgr.ns_income_summary.reg_count_day2 = T1.regSum;
	
	-- 前2天付费人数
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) BETWEEN 0 AND 2 GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 2 SET poke_mgr.ns_income_summary.reg_pay_count_day2 = T1.paySum;

	-- 第2日付费总额
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 2  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 2 SET poke_mgr.ns_income_summary.paySum_day2 = T1.paySum;
	END IF;

-- 当存在 开服 3天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 3)
THEN
	-- 前3天注册人数
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id,t.s_name,COUNT(DISTINCT poke_home.account.id) as regSum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.account ON t.s_id = poke_home.account.prev_srv_id AND DATEDIFF( poke_home.account.create_time, t.open_time) BETWEEN 0 AND 3  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 3 SET poke_mgr.ns_income_summary.reg_count_day3 = T1.regSum;
	
	-- 前3天付费人数
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) BETWEEN 0 AND 3  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 3 SET poke_mgr.ns_income_summary.reg_pay_count_day3 = T1.paySum;
	
	-- 第3日付费总额
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 3  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) =3 SET poke_mgr.ns_income_summary.paySum_day3 = T1.paySum;
	END IF;

-- 当存在 开服 7天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 7)
THEN
	-- 前7天注册人数
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id,t.s_name,COUNT(DISTINCT poke_home.account.id) as regSum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home. account ON t.s_id = poke_home.account.prev_srv_id AND DATEDIFF( poke_home.account.create_time, t.open_time) BETWEEN 0 AND 7  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 7 SET poke_mgr.ns_income_summary.reg_count_day7 = T1.regSum;
	
	-- 前7天付费人数
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) BETWEEN 0 AND 7  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 7 SET poke_mgr.ns_income_summary.reg_pay_count_day7 = T1.paySum;
	
	-- 第7日付费总额
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 7  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 7 SET poke_mgr.ns_income_summary.paySum_day7 = T1.paySum;
	END IF;

-- 当存在 开服 1天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 1)
THEN
	-- 第1日付费总额
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 1  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 1 SET poke_mgr.ns_income_summary.paySum_day1 = T1.paySum;
	END IF;

-- 当存在 开服 4天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 4)
THEN
	-- 第4日付费总额
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 4  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 4 SET poke_mgr.ns_income_summary.paySum_day4 = T1.paySum;
	END IF;

-- 当存在 开服 5天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 5)
THEN
	-- 第5日付费总额
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 5  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 5 SET poke_mgr.ns_income_summary.paySum_day5 = T1.paySum;
	END IF;


-- 当存在 开服 6天的服务器.
	IF exists(SELECT * FROM poke_mgr.ns_income_summary WHERE DATEDIFF(c_date,open_time) = 6)
THEN
	-- 第6日付费总额
	UPDATE poke_mgr.ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM poke_mgr.ns_income_summary AS t LEFT JOIN poke_home.`order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 6  GROUP BY t.s_id) as T1 ON T1.s_id = poke_mgr.ns_income_summary.s_id AND T1.s_name = poke_mgr.ns_income_summary.s_name AND DATEDIFF(c_date,poke_mgr.ns_income_summary.open_time) = 6 SET poke_mgr.ns_income_summary.paySum_day6 = T1.paySum;
	END IF;
	
	
END
$$
DELIMITER ;

-- --------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS start_retention_player$$
CREATE PROCEDURE start_retention_player()
BEGIN
	-- Routine body goes here...
	-- 玩家留存率统计
	DECLARE n_date  date;
	DECLARE c_date date;
	DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);

	--  -100 代表所有服务器
	set s_id = -100;

	-- 创建(设置时间的)前一天要统计的留存数据 
		-- 分渠道，分服
	INSERT INTO poke_mgr.retention (platform,server_id,dru, create_date) SELECT platform, server_id, count(DISTINCT account_id),c_date FROM poke_home.login_log WHERE datediff(c_date, register_time) = 0 GROUP BY platform,server_id;
		-- 分渠道
	INSERT INTO poke_mgr.retention (platform,server_id,dru, create_date) SELECT platform, s_id, count(DISTINCT account_id),c_date FROM poke_home.login_log WHERE datediff(c_date, register_time) = 0 GROUP BY platform;

	-- 开始统计留存数据 
	--  判断前一天是否有可以根性的数据，
	--  如果有就进行更新
	--  没有就跳过
	-- ---------------------
	-- 与创建日相差一天 (就是次日留存)
	IF exists(select * from poke_mgr.retention where datediff(c_date,create_date) = 1)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前1天新增用户,现在还登录的/创建日前1天新增用户)*100 ) 
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 1 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 1 SET poke_mgr.retention.retained1 = T2.a;
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 1 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 1 SET poke_mgr.retention.retained1 = T2.a;
	END IF;
	-- 3 日留存
	IF exists(select * from poke_mgr.retention where datediff(c_date,create_date) = 2)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前3天新增用户,现在还登录的/创建日前3天新增用户)*100 ) 
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 2 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 2 SET poke_mgr.retention.retained3 = T2.a;
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 2 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 2 SET poke_mgr.retention.retained3 = T2.a;
	
	END IF;
-- 7 日留存
	IF exists(select * from poke_mgr.retention where datediff(c_date,create_date) = 6)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前7天新增用户,现在还登录的/创建日前7天新增用户)*100 ) 
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 6 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 6 SET poke_mgr.retention.retained7 = T2.a;
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 6 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 6 SET poke_mgr.retention.retained7 = T2.a;
		
	END IF;
-- 14 日留存
	IF exists(select * from poke_mgr.retention where datediff(c_date,create_date) = 13)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前14天新增用户,现在还登录的/创建日前14天新增用户)*100 ) 
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 13 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 13 SET poke_mgr.retention.retained14 = T2.a;
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 13 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 13 SET poke_mgr.retention.retained14 = T2.a;
		
	END IF;

-- 30 日留存
	IF exists(select * from poke_mgr.retention where datediff(c_date,create_date) = 29)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前30天新增用户,现在还登录的/创建日前30天新增用户)*100 ) 
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 29 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 29 SET poke_mgr.retention.retained30 = T2.a;
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 29 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 29 SET poke_mgr.retention.retained30 = T2.a;
	END IF;
-- 31 日留存
	IF exists(select * from retention where datediff(c_date,create_date) = 30)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前31天新增用户,现在还登录的/创建日前31天新增用户)*100 ) 
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 30 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 30 SET poke_mgr.retention.retained31 = T2.a;
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 30 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 30 SET poke_mgr.retention.retained31 = T2.a;
	END IF;
-- 32 日留存
	IF exists(select * from poke_mgr.retention where datediff(c_date,create_date) = 31)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前32天新增用户,现在还登录的/创建日前32天新增用户)*100 ) 
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 31 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 31 SET poke_mgr.retention.retained32 = T2.a;
		UPDATE poke_mgr.retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM poke_home.login_log WHERE datediff(c_date, register_time) = 31 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON poke_mgr.retention.platform = T2.platform AND poke_mgr.retention.server_id = T2.server_id AND datediff( c_date, poke_mgr.retention.create_date) = 31 SET poke_mgr.retention.retained32 = T2.a;
	END IF;
END
$$
DELIMITER;

-- --------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS total_pay$$
CREATE PROCEDURE total_pay()
BEGIN
set @total_count = 0;
select sum(amount) into @total_count from poke_home.`order`;

set @today = date(now());

set @today_count = 0;
SELECT sum(amount) into @today_count FROM poke_home.`order` where finish_time LIKE concat(@today, "%");


select @total_count, @today_count;
END
$$
DELIMITER;