-- 2016-06-22
ALTER TABLE package ADD type int;  -- 支持大小版本

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device_reg
-- ----------------------------
DROP TABLE IF EXISTS `device_reg`;
CREATE TABLE `device_reg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceUI` varchar(255) DEFAULT NULL COMMENT '设备号',
  `OS` varchar(255) DEFAULT NULL COMMENT '系统',
  `deviceModel` varchar(255) DEFAULT NULL,
  `systemInfo` text,
  `reg_time` datetime DEFAULT NULL COMMENT '设备注册时间',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

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

-- 2016-05


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
INSERT INTO `quest_info` VALUES ('200100048', '常磐森林的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100049', '常磐森林的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100050', '常磐森林的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100051', '常磐森林的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100052', '常磐森林的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100053', '常磐森林的战斗（6）');
INSERT INTO `quest_info` VALUES ('200100054', '常磐森林的战斗（7）');
INSERT INTO `quest_info` VALUES ('200100055', '常磐森林的战斗（8）');
INSERT INTO `quest_info` VALUES ('200100056', '常磐森林的秘密');
INSERT INTO `quest_info` VALUES ('200100057', '保护常磐森林');
INSERT INTO `quest_info` VALUES ('200100058', '常磐森林的资源');
INSERT INTO `quest_info` VALUES ('200100059', '常磐森林的感谢信');
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
INSERT INTO `quest_info` VALUES ('200100071', '初入地鼠洞');
INSERT INTO `quest_info` VALUES ('200100072', '地鼠洞的战斗（1）');
INSERT INTO `quest_info` VALUES ('200100073', '地鼠洞的战斗（2）');
INSERT INTO `quest_info` VALUES ('200100074', '地鼠洞的战斗（3）');
INSERT INTO `quest_info` VALUES ('200100075', '地鼠洞的战斗（4）');
INSERT INTO `quest_info` VALUES ('200100076', '地鼠洞的战斗（5）');
INSERT INTO `quest_info` VALUES ('200100077', '地鼠洞的战斗（6）');
INSERT INTO `quest_info` VALUES ('200100078', '地鼠洞的战斗（7）');
INSERT INTO `quest_info` VALUES ('200100079', '地鼠洞的战斗（8）');
INSERT INTO `quest_info` VALUES ('200100080', '地鼠洞中的遗留物');
INSERT INTO `quest_info` VALUES ('200100081', '地鼠洞中的短裤少年');
INSERT INTO `quest_info` VALUES ('200100082', '走失的地鼠');
INSERT INTO `quest_info` VALUES ('200100083', '危险的地鼠洞');
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
INSERT INTO `quest_info` VALUES ('200100111', '初进月见山');
INSERT INTO `quest_info` VALUES ('200100112', '重要的遗忘');
INSERT INTO `quest_info` VALUES ('200100113', '协助抓捕');
INSERT INTO `quest_info` VALUES ('200100114', '月见山和月亮石');
INSERT INTO `quest_info` VALUES ('200100115', '收集遗落道具');
INSERT INTO `quest_info` VALUES ('200100116', '月见山的战斗');
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
-- Table structure for game_valid_user_cache
-- ----------------------------
DROP TABLE IF EXISTS `game_valid_user_cache`;
CREATE TABLE `game_valid_user_cache` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) DEFAULT NULL,
  `server_id` int(11) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='###########有效用户表  缓存表，用完就会删掉数据';

-- ----------------------------
-- Table structure for game_login_user_cache
-- ----------------------------
DROP TABLE IF EXISTS `game_login_user_cache`;
CREATE TABLE `game_login_user_cache` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) DEFAULT NULL,
  `server_id` int(11) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='############### 登录游戏帐号 缓存表';


-- ----------------------------
-- Table structure for new_u_change
-- ----------------------------

DROP TABLE IF EXISTS `new_u_change`;
CREATE TABLE `new_u_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL COMMENT '时间',
  `new_drive` bigint(255) DEFAULT NULL COMMENT '新增设备',
  `new_reg` bigint(255) DEFAULT NULL COMMENT '新增注册',
  `reg_change` bigint(255) DEFAULT NULL COMMENT '注册转化率',
  `login_u` bigint(255) DEFAULT NULL COMMENT '登录用户数',
  `login_change` bigint(255) DEFAULT NULL COMMENT '登录转化率',
  `valid_u` bigint(255) DEFAULT NULL COMMENT '有效用户',
  `valid_u_change` bigint(255) DEFAULT NULL COMMENT '有效用户转化率',
  `pay_u` bigint(255) DEFAULT NULL COMMENT '付费用户',
  `login_pay_change` bigint(255) DEFAULT NULL COMMENT '登录付费转化率',
  `valid_pay_change` bigint(255) DEFAULT NULL COMMENT '有效用户付费转化率',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
INSERT INTO `guide_info` VALUES ('800100002', '扭蛋抽取&角色上阵引导');
INSERT INTO `guide_info` VALUES ('800100003', '初场战斗引导');
INSERT INTO `guide_info` VALUES ('800100004', '精灵恢复引导');
INSERT INTO `guide_info` VALUES ('800100005', '精灵抓捕引导');


-- ----------------------------
-- 水晶使用汇总
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='######### 水晶使用汇总 ########';



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
-- 副本挑战汇总
-- ----------------------------
DROP TABLE IF EXISTS `fb_summary`;
CREATE TABLE `fb_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fb_id` bigint(20) DEFAULT NULL COMMENT '副本id',
  `t_time` datetime DEFAULT NULL COMMENT '创建时间',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `tz_player` bigint(255) DEFAULT NULL COMMENT '挑战玩家数',
  `tz_num` bigint(20) DEFAULT NULL COMMENT '挑战次数',
  `tz_win_player` bigint(255) DEFAULT NULL COMMENT '挑战完成玩家数',
  `tz_win_player_cs` bigint(255) DEFAULT NULL COMMENT '挑战完成次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='# 副本挑战汇总\r\n';


-- ----------------------------
-- 日志 db
-- ----------------------------
DROP TABLE IF EXISTS `log_db`;
CREATE TABLE `log_db` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` longtext COMMENT '数据库查询链接',
  `root` varchar(255) DEFAULT NULL COMMENT 'root 名称',
  `pwd` varchar(255) DEFAULT NULL COMMENT '密码',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务端id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='# 日志 db ';

-- ----------------------------
-- 汇总数据库 sql
-- ----------------------------
DROP TABLE IF EXISTS `summary_sql`;
CREATE TABLE `summary_sql` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sql` longtext COMMENT 'sql 汇总脚本',
  `title` longtext COMMENT '表头',
  `table` varchar(255) DEFAULT NULL COMMENT '表名',
  `content` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='# 汇总 sql';

-- ----------------------------
-- Records of summary_sql
-- ----------------------------
INSERT INTO `summary_sql` VALUES ('1', 'SELECT  T1.t_time,  IFNULL(T2.content, 0) AS tOutput,  IFNULL(T3.content, 0) + IFNULL(T4.content, 0) + IFNULL(T5.content, 0) + IFNULL(T6.content, 0) AS tInput,  IFNULL(T3.content, 0) + IFNULL(T4.content, 0) + IFNULL(T5.content, 0) + IFNULL(T6.content, 0) - IFNULL(T2.content, 0) AS tKeep,  IFNULL(T2.c1, 0) AS output1,  IFNULL(T3.c1, 0) + IFNULL(T4.c1, 0) + IFNULL(T5.c1, 0) + IFNULL(T6.c1, 0) AS input1,  IFNULL(T3.c1, 0) + IFNULL(T4.c1, 0) + IFNULL(T5.c1, 0) + IFNULL(T6.c1, 0) - IFNULL(T2.c1, 0) AS keep1,  IFNULL(T2.c2, 0) AS output2,  IFNULL(T3.c2, 0) + IFNULL(T4.c2, 0) + IFNULL(T5.c2, 0) + IFNULL(T6.c2, 0) AS input2,  IFNULL(T3.c2, 0) + IFNULL(T4.c2, 0) + IFNULL(T5.c2, 0) + IFNULL(T6.c2, 0) - IFNULL(T2.c2, 0) AS keep2,  IFNULL(T2.c3, 0) AS output3,  IFNULL(T3.c3, 0) + IFNULL(T4.c3, 0) + IFNULL(T5.c3, 0) + IFNULL(T6.c3, 0) AS input3,  IFNULL(T3.c3, 0) + IFNULL(T4.c3, 0) + IFNULL(T5.c3, 0) + IFNULL(T6.c3, 0) - IFNULL(T2.c3, 0) AS keep3,  IFNULL(T2.c4, 0) AS output4,  IFNULL(T3.c4, 0) + IFNULL(T4.c4, 0) + IFNULL(T5.c4, 0) + IFNULL(T6.c4, 0) AS input4,  IFNULL(T3.c4, 0) + IFNULL(T4.c4, 0) + IFNULL(T5.c4, 0) + IFNULL(T6.c4, 0) - IFNULL(T2.c4, 0) AS keep4,  IFNULL(T2.c5, 0) AS output5,  IFNULL(T3.c5, 0) + IFNULL(T4.c5, 0) + IFNULL(T5.c5, 0) + IFNULL(T6.c5, 0) AS input5,  IFNULL(T3.c5, 0) + IFNULL(T4.c5, 0) + IFNULL(T5.c5, 0) + IFNULL(T6.c5, 0) - IFNULL(T2.c5, 0) AS keep5,  IFNULL(T2.c5, 0) AS output6,  IFNULL(T3.c6, 0) + IFNULL(T4.c6, 0) + IFNULL(T5.c6, 0) + IFNULL(T6.c6, 0) AS input6,  IFNULL(T3.c6, 0) + IFNULL(T4.c6, 0) + IFNULL(T5.c6, 0) + IFNULL(T6.c6, 0) - IFNULL(T2.c6, 0) AS keep6,  IFNULL(T2.c7, 0) AS output7,  IFNULL(T3.c7, 0) + IFNULL(T4.c7, 0) + IFNULL(T5.c7, 0) + IFNULL(T6.c7, 0) AS input7,  IFNULL(T3.c7, 0) + IFNULL(T4.c7, 0) + IFNULL(T5.c7, 0) + IFNULL(T6.c7, 0) - IFNULL(T2.c7, 0) AS keep7,  IFNULL(T2.c8, 0) AS output8,  IFNULL(T3.c8, 0) + IFNULL(T4.c8, 0) + IFNULL(T5.c8, 0) + IFNULL(T6.c8, 0) AS input8,  IFNULL(T3.c8, 0) + IFNULL(T4.c8, 0) + IFNULL(T5.c8, 0) + IFNULL(T6.c8, 0) - IFNULL(T2.c8, 0) AS keep8,  IFNULL(T2.c9, 0) AS output9,  IFNULL(T3.c9, 0) + IFNULL(T4.c9, 0) + IFNULL(T5.c9, 0) + IFNULL(T6.c9, 0) AS input9,  IFNULL(T3.c9, 0) + IFNULL(T4.c9, 0) + IFNULL(T5.c9, 0) + IFNULL(T6.c9, 0) - IFNULL(T2.c9, 0) AS keep9 FROM  ({0}) AS T1 LEFT JOIN (  SELECT  T.t_time,  SUM(T.content) AS content,  SUM(T.c1) AS c1,  SUM(T.c2) AS c2,  SUM(T.c3) AS c3,  SUM(T.c4) AS c4,  SUM(T.c5) AS c5,  SUM(T.c6) AS c6,  SUM(T.c7) AS c7,  SUM(T.c8) AS c8,  SUM(T.c9) AS c9  FROM  (  SELECT  t1.t_time,  SUM(t1.content) AS content,  CASE  WHEN t1. LEVEL BETWEEN 0  AND 10 THEN  SUM(t1.content)  ELSE  0  END AS c1,  CASE  WHEN t1. LEVEL BETWEEN 11  AND 20 THEN  SUM(t1.content)  ELSE  0  END AS c2,  CASE  WHEN t1. LEVEL BETWEEN 21  AND 30 THEN  SUM(t1.content)  ELSE  0  END AS c3,  CASE WHEN t1. LEVEL BETWEEN 31 AND 40 THEN  SUM(t1.content) ELSE  0 END AS c4,  CASE WHEN t1. LEVEL BETWEEN 41 AND 50 THEN  SUM(t1.content) ELSE  0 END AS c5,  CASE WHEN t1. LEVEL BETWEEN 51 AND 60 THEN  SUM(t1.content) ELSE  0 END AS c6,  CASE WHEN t1. LEVEL BETWEEN 61 AND 70 THEN  SUM(t1.content) ELSE  0 END AS c7,  CASE WHEN t1. LEVEL BETWEEN 71 AND 80 THEN  SUM(t1.content) ELSE  0 END AS c8,  CASE WHEN t1. LEVEL > 80 THEN  SUM(t1.content) ELSE  0 END AS c9 FROM  (  SELECT  t.t_time,  REPLACE (  t.content,  substr(  content,  locate(\";\", t.content),  CHAR_LENGTH(t.content) - locate(\";\", t.content) + 1  ),  \"\"  ) AS content,  t. LEVEL  FROM  (  SELECT  REPLACE (  content,  substr(  content,  1,  locate(\"100000004_\", content) + 9  ),  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  LEVEL  FROM  logs  WHERE  content LIKE \"%消耗%100000004_%\"  AND log_time LIKE \"{1}%\"  ) AS t  ) AS t1 GROUP BY  t1.t_time,  t1. LEVEL  ) AS T  GROUP BY  T.t_time ) AS T2 ON T1.t_time = T2.t_time LEFT JOIN (  SELECT  T.t_time,  SUM(T.content) AS content,  SUM(T.c1) AS c1,  SUM(T.c2) AS c2,  SUM(T.c3) AS c3,  SUM(T.c4) AS c4,  SUM(T.c5) AS c5,  SUM(T.c6) AS c6,  SUM(T.c7) AS c7,  SUM(T.c8) AS c8,  SUM(T.c9) AS c9  FROM  (  SELECT  t1.t_time,  SUM(t1.content) AS content,  CASE  WHEN t1. LEVEL BETWEEN 0  AND 10 THEN  SUM(t1.content)  ELSE  0  END AS c1,  CASE  WHEN t1. LEVEL BETWEEN 11  AND 20 THEN  SUM(t1.content)  ELSE  0  END AS c2,  CASE  WHEN t1. LEVEL BETWEEN 21  AND 30 THEN  SUM(t1.content)  ELSE  0  END AS c3,  CASE WHEN t1. LEVEL BETWEEN 31 AND 40 THEN  SUM(t1.content) ELSE  0 END AS c4,  CASE WHEN t1. LEVEL BETWEEN 41 AND 50 THEN  SUM(t1.content) ELSE  0 END AS c5,  CASE WHEN t1. LEVEL BETWEEN 51 AND 60 THEN  SUM(t1.content) ELSE  0 END AS c6,  CASE WHEN t1. LEVEL BETWEEN 61 AND 70 THEN  SUM(t1.content) ELSE  0 END AS c7,  CASE WHEN t1. LEVEL BETWEEN 71 AND 80 THEN  SUM(t1.content) ELSE  0 END AS c8,  CASE WHEN t1. LEVEL > 80 THEN  SUM(t1.content) ELSE  0 END AS c9 FROM  (  SELECT  t.t_time,  REPLACE (  t.content,  substr(  content,  locate(\";\", t.content),  CHAR_LENGTH(t.content) - locate(\";\", t.content) + 1  ),  \"\"  ) AS content,  t. LEVEL  FROM  (  SELECT  REPLACE (  content,  substr(  content,  1,  locate(\"100000004_\", content) + 9  ),  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  LEVEL  FROM  logs  WHERE  content LIKE \"%获得%100000004_%\"  AND log_time LIKE \"{1}%\"  ) AS t  ) AS t1 GROUP BY  t1.t_time,  t1. LEVEL  ) AS T  GROUP BY  T.t_time ) AS T3 ON T3.t_time = T1.t_time LEFT JOIN (  SELECT  T.t_time,  SUM(T.content) AS content,  SUM(T.c1) AS c1,  SUM(T.c2) AS c2,  SUM(T.c3) AS c3,  SUM(T.c4) AS c4,  SUM(T.c5) AS c5,  SUM(T.c6) AS c6,  SUM(T.c7) AS c7,  SUM(T.c8) AS c8,  SUM(T.c9) AS c9  FROM  (  SELECT  t1.t_time,  SUM(t1.content) AS content,  CASE  WHEN t1. LEVEL BETWEEN 0  AND 10 THEN  SUM(t1.content)  ELSE  0  END AS c1,  CASE  WHEN t1. LEVEL BETWEEN 11  AND 20 THEN  SUM(t1.content)  ELSE  0  END AS c2,  CASE  WHEN t1. LEVEL BETWEEN 21  AND 30 THEN  SUM(t1.content)  ELSE  0  END AS c3,  CASE WHEN t1. LEVEL BETWEEN 31 AND 40 THEN  SUM(t1.content) ELSE  0 END AS c4,  CASE WHEN t1. LEVEL BETWEEN 41 AND 50 THEN  SUM(t1.content) ELSE  0 END AS c5,  CASE WHEN t1. LEVEL BETWEEN 51 AND 60 THEN  SUM(t1.content) ELSE  0 END AS c6,  CASE WHEN t1. LEVEL BETWEEN 61 AND 70 THEN  SUM(t1.content) ELSE  0 END AS c7,  CASE WHEN t1. LEVEL BETWEEN 71 AND 80 THEN  SUM(t1.content) ELSE  0 END AS c8,  CASE WHEN t1. LEVEL > 80 THEN  SUM(t1.content) ELSE  0 END AS c9 FROM  (  SELECT  REPLACE (  REPLACE (  content,  substr(  content,  locate(\", vip\", content),  CHAR_LENGTH(content) - locate(\", vip\", content) + 1  ),  \"\"  ),  \"购买钻石数量\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  LEVEL  FROM  logs  WHERE  type1 LIKE \"商城\" AND type2 LIKE \"购买钻石\"  AND log_time LIKE \"{1}%\"  ) AS t1 GROUP BY  t1.t_time,  t1. LEVEL  ) AS T  GROUP BY  T.t_time ) AS T4 ON T4.t_time = T1.t_time LEFT JOIN (  SELECT  T.t_time,  SUM(T.content) AS content,  SUM(T.c1) AS c1,  SUM(T.c2) AS c2,  SUM(T.c3) AS c3,  SUM(T.c4) AS c4,  SUM(T.c5) AS c5,  SUM(T.c6) AS c6,  SUM(T.c7) AS c7,  SUM(T.c8) AS c8,  SUM(T.c9) AS c9  FROM  (  SELECT  t1.t_time,  SUM(t1.content) AS content,  CASE  WHEN t1. LEVEL BETWEEN 0  AND 10 THEN  SUM(t1.content)  ELSE  0  END AS c1,  CASE  WHEN t1. LEVEL BETWEEN 11  AND 20 THEN  SUM(t1.content)  ELSE  0  END AS c2,  CASE  WHEN t1. LEVEL BETWEEN 21  AND 30 THEN  SUM(t1.content)  ELSE  0  END AS c3,  CASE WHEN t1. LEVEL BETWEEN 31 AND 40 THEN  SUM(t1.content) ELSE  0 END AS c4,  CASE WHEN t1. LEVEL BETWEEN 41 AND 50 THEN  SUM(t1.content) ELSE  0 END AS c5,  CASE WHEN t1. LEVEL BETWEEN 51 AND 60 THEN  SUM(t1.content) ELSE  0 END AS c6,  CASE WHEN t1. LEVEL BETWEEN 61 AND 70 THEN  SUM(t1.content) ELSE  0 END AS c7,  CASE WHEN t1. LEVEL BETWEEN 71 AND 80 THEN  SUM(t1.content) ELSE  0 END AS c8,  CASE WHEN t1. LEVEL > 80 THEN  SUM(t1.content) ELSE  0 END AS c9 FROM  (  SELECT  REPLACE (  REPLACE (  REPLACE (  REPLACE (  content,  substr(  content,  locate(\",金币\", content),  CHAR_LENGTH(content) - locate(\",金币\", content) + 1  ),  \"\"  ),  substr(  content,  locate(  \",低级经验药水\",  content  ),  CHAR_LENGTH(content) - locate(  \",低级经验药水\",  content  ) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"无暇的水晶x\", content) + 5  ),  \"\"  ),  \".\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  LEVEL  FROM  logs  WHERE  type1 LIKE \"活动\" AND type2 LIKE \"领取奖励\"  AND log_time LIKE \"{1}%\"  ) AS t1 GROUP BY  t1.t_time,  t1. LEVEL  ) AS T  GROUP BY  T.t_time ) AS T5 ON T5.t_time = T1.t_time LEFT JOIN (  SELECT  T.t_time,  SUM(T.content) AS content,  SUM(T.c1) AS c1,  SUM(T.c2) AS c2,  SUM(T.c3) AS c3,  SUM(T.c4) AS c4,  SUM(T.c5) AS c5,  SUM(T.c6) AS c6,  SUM(T.c7) AS c7,  SUM(T.c8) AS c8,  SUM(T.c9) AS c9  FROM  (  SELECT  t1.t_time,  SUM(t1.content) AS content,  CASE  WHEN t1. LEVEL BETWEEN 0  AND 10 THEN  SUM(t1.content)  ELSE  0  END AS c1,  CASE  WHEN t1. LEVEL BETWEEN 11  AND 20 THEN  SUM(t1.content)  ELSE  0  END AS c2,  CASE  WHEN t1. LEVEL BETWEEN 21  AND 30 THEN  SUM(t1.content)  ELSE  0  END AS c3,  CASE WHEN t1. LEVEL BETWEEN 31 AND 40 THEN  SUM(t1.content) ELSE  0 END AS c4,  CASE WHEN t1. LEVEL BETWEEN 41 AND 50 THEN  SUM(t1.content) ELSE  0 END AS c5,  CASE WHEN t1. LEVEL BETWEEN 51 AND 60 THEN  SUM(t1.content) ELSE  0 END AS c6,  CASE WHEN t1. LEVEL BETWEEN 61 AND 70 THEN  SUM(t1.content) ELSE  0 END AS c7,  CASE WHEN t1. LEVEL BETWEEN 71 AND 80 THEN  SUM(t1.content) ELSE  0 END AS c8,  CASE WHEN t1. LEVEL > 80 THEN  SUM(t1.content) ELSE  0 END AS c9 FROM  (  SELECT  REPLACE (  REPLACE (  REPLACE (  content,  substr(  content,  locate(\",金币\", content),  CHAR_LENGTH(content) - locate(\",金币\", content) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"无暇的水晶x\", content) + 5  ),  \"\"  ),  \".\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  LEVEL  FROM  logs  WHERE  type1 LIKE \"邮件\" AND type2 LIKE \"领取邮件奖励\"  AND log_time LIKE \"{1}%\"  ) AS t1 GROUP BY  t1.t_time,  t1. LEVEL  ) AS T  GROUP BY  T.t_time ) AS T6 ON T6.t_time = T1.t_time \r\n', 't_time,tOutput,tInput,tKeep,output1,input1,keep1,output2,input2,keep2,output3,input3,keep3,output4,input4,keep4,output5,input5,keep5,output6,input6,keep6,output7,input7,keep7,output8,input8,keep8,output9,input9,keep9', 'crystal_level', '水晶产出&消耗(等级汇总)');
INSERT INTO `summary_sql` VALUES ('2', 'SELECT  T1.t_time,  IFNULL(T4.cs, 0) AS cz_cs,  IFNULL(T4.sum, 0) AS cz_sj,  IFNULL(T3.cs, 0) AS hd_cs,  IFNULL(T3.sum, 0) AS hd_sj,  IFNULL(T5.cs, 0) AS tg_cs,  IFNULL(T5.sum, 0) AS tg_sj,  IFNULL(T7.cs, 0) AS yj_cs,  IFNULL(T7.sum, 0) AS yj_sj,  IFNULL(T2.cs, 0) AS jjc_cs,  IFNULL(T2.sum, 0) AS jjc_sj,  IFNULL(T6.cs, 0) AS shop_cs,  IFNULL(T6.sum, 0) AS shop_sj FROM  ({0}) AS T1 LEFT JOIN (  SELECT  T.t_time,  COUNT(T.t_time) AS cs,  SUM(T.content) AS sum  FROM  (  SELECT  REPLACE (  REPLACE (  REPLACE (  content,  substr(  content,  locate(\",金币\", content),  CHAR_LENGTH(content) - locate(\",金币\", content) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"无暇的水晶x\", content) + 5  ),  \"\"  ),  \".\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  logs  WHERE  type1 LIKE \"邮件\"  AND type2 LIKE \"领取邮件奖励\"  AND content LIKE \"%竞技场%\"  AND content LIKE \"%获得:无暇的水晶%\"  AND log_time LIKE \"{1}%\"  ) AS T  GROUP BY  T.t_time ) AS T2 ON T1.t_time = T2.t_time LEFT JOIN (  SELECT  T.t_time,  COUNT(T.t_time) AS cs,  SUM(T.content) AS sum  FROM  (  SELECT  REPLACE (  REPLACE (  REPLACE (  REPLACE (  content,  substr(  content,  locate(\",金币\", content),  CHAR_LENGTH(content) - locate(\",金币\", content) + 1  ),  \"\"  ),  substr(  content,  locate(  \",低级经验药水\",  content  ),  CHAR_LENGTH(content) - locate(  \",低级经验药水\",  content  ) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"无暇的水晶x\", content) + 5  ),  \"\"  ),  \".\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  logs  WHERE  type1 LIKE \"活动\"   AND type2 LIKE \"领取奖励\"   AND content LIKE \"%活动%\"  AND content LIKE \"%无暇的水晶%\"  AND log_time LIKE \"{1}%\"  ) AS T  GROUP BY  T.t_time ) AS T3 ON T1.t_time = T3.t_time LEFT JOIN (  SELECT  T.t_time,  COUNT(T.t_time) AS cs,  SUM(T.content) AS sum  FROM  (  SELECT  REPLACE (  REPLACE (  content,  substr(  content,  locate(\", vip\", content),  CHAR_LENGTH(content) - locate(\", vip\", content) + 1  ),  \"\"  ),  \"购买钻石数量\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  logs  WHERE  type1 LIKE \"商城\"  AND type2 LIKE \"购买钻石\"   AND log_time LIKE \"{1}%\"  ) AS T  GROUP BY  T.t_time ) AS T4 ON T1.t_time = T4.t_time LEFT JOIN (  SELECT  T.t_time,  COUNT(T.t_time) AS cs,  SUM(T.content) AS sum  FROM  (  SELECT  t.t_time,  REPLACE (  t.content,  substr(  content,  locate(\";\", t.content),  CHAR_LENGTH(t.content) - locate(\";\", t.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  REPLACE (  content,  substr(  content,  1,  locate(\"100000004_\", content) + 9  ),  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  logs  WHERE  content LIKE \"%获得%100000004_%\"  AND type1 LIKE \"冒险\"   AND (type2 LIKE \"开始战斗\" OR type2 LIKE \"领取章节奖励\")  AND log_time LIKE \"{1}%\"  ) AS t  ) AS T  GROUP BY  T.t_time ) AS T5 ON T5.t_time = T1.t_time LEFT JOIN (  SELECT  T.t_time,  COUNT(T.t_time) AS cs,  SUM(T.content) AS sum  FROM  (  SELECT  t.t_time,  REPLACE (  t.content,  substr(  content,  locate(\";\", t.content),  CHAR_LENGTH(t.content) - locate(\";\", t.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  REPLACE (  content,  substr(  content,  1,  locate(\"100000004_\", content) + 9  ),  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  logs  WHERE  content LIKE \"%获得%100000004_%\"  AND type1 LIKE \"黑市商店\" AND type2 LIKE \"购买\"  AND log_time LIKE \"{1}%\"  ) AS t  ) AS T  GROUP BY  T.t_time ) AS T6 ON T6.t_time = T1.t_time LEFT JOIN (  SELECT  T.t_time,  COUNT(T.t_time) AS cs,  SUM(T.content) AS sum  FROM  (  SELECT  REPLACE (  REPLACE (  REPLACE (  content,  substr(  content,  locate(\",金币\", content),  CHAR_LENGTH(content) - locate(\",金币\", content) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"无暇的水晶x\", content) + 5  ),  \"\"  ),  \".\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  logs  WHERE  content NOT LIKE \"%竞技场%\"  AND type1 LIKE \"邮件\" AND type2 LIKE \"领取邮件奖励\"  AND content LIKE \"%获得:无暇的水晶%\"  AND log_time LIKE \"{1}%\"  ) AS T  GROUP BY  T.t_time ) AS T7 ON T1.t_time = T7.t_time GROUP BY  T1.t_time \r\n', 't_time,cz_cs,cz_sj,hd_cs, hd_sj,tg_cs,tg_sj,yj_cs,yj_sj,jjc_cs,jjc_sj,shop_cs,shop_sj', 'crystal_detailed_summary', '水晶产出详细统计');
INSERT INTO `summary_sql` VALUES ('3', 'SELECT  T.t_time,  IFNULL(T1.cs, 0) AS cs1,  IFNULL(T1.content, 0) AS ks1,  IFNULL(T2.cs, 0) AS cs2,  IFNULL(T2.content, 0) AS ks2,  IFNULL(T3.cs, 0) AS cs3,  IFNULL(T3.content, 0) AS ks3,  IFNULL(T4.cs, 0) AS cs4,  IFNULL(T4.content, 0) AS ks4,  IFNULL(T5.cs, 0) AS cs5,  IFNULL(T5.content, 0) AS ks5,  IFNULL(T6.cs, 0) AS cs6,  IFNULL(T6.content, 0) AS ks6,  IFNULL(T7.cs, 0) AS cs7,  IFNULL(T7.content, 0) AS ks7,  IFNULL(T8.cs, 0) AS cs8,  IFNULL(T8.content, 0) AS ks8,  IFNULL(T9.cs, 0) AS cs9,  IFNULL(T9.content, 0) AS ks9 FROM  ({0}) AS T LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (  content,  SUBSTR(  content,  locate(\";),已使用\", content),  CHAR_LENGTH(content) - locate(\";),已使用\", content) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"100000005_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  type1 LIKE \"矿洞\"  AND type2 LIKE \"购买矿石\"  AND log_time LIKE \"{1}%\"  ) AS t  GROUP BY  t.t_time ) AS T1 ON T1.t_time = T.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (  content,  substr(  content,  1,  locate(\"100000005\", content) + 9  ),  \"\"  ),  \";)\",  \"\"  ) AS content  FROM  logs  WHERE  type1 LIKE \"VIP商店\" AND ( type2 LIKE \"购买\" OR type2 LIKE \"刷新\")  AND content LIKE \"%获得%100000005%\"  AND log_time LIKE \"{1}%\"  ) AS t  GROUP BY  t.t_time ) AS T2 ON T1.t_time = T2.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (  content,  substr(  content,  1,  locate(\"100000005\", content) + 9  ),  \"\"  ),  \";)\",  \"\"  ) AS content  FROM  logs  WHERE  type1 LIKE \"黑市商店\"  AND type2 LIKE \"购买\"  AND content LIKE \"%获得%100000005%\"  AND log_time LIKE \"{1}%\"  ) AS t  GROUP BY  t.t_time ) AS T3 ON T1.t_time = T3.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (  content,  substr(  content,  1,  locate(\"100000005\", content) + 9  ),  \"\"  ),  \";)\",  \"\"  ) AS content  FROM  logs  WHERE  type1 LIKE \"冒险者商店\"   AND type2 LIKE \"购买\"  AND content LIKE \"%获得%100000005%\"  AND log_time LIKE \"{1}%\"  ) AS t  GROUP BY  t.t_time ) AS T4 ON T4.t_time = T1.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (  REPLACE (  content,  substr(  content,  locate(\",无暇的水晶\", content),  CHAR_LENGTH(content) - locate(\",无暇的水晶\", content) + 1  ),  \"\"  ),  substr(  content,  1,  locate(\"辉晶矿x\", content) + 3  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  logs  WHERE  type1 LIKE \"活动\"  AND type2 LIKE \"领取奖励\"  AND content LIKE \"%辉晶矿%\"  AND log_time LIKE \"{1}%\"  ) AS t  GROUP BY  t.t_time ) AS T5 ON T5.t_time = T1.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\",\", t1.content),  CHAR_LENGTH(t1.content) - locate(\",\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"辉晶矿x\", content) + 3  ),  \"\"  ) AS content  FROM  logs  WHERE  type1 LIKE \"邮件\"   AND type2 LIKE \"领取邮件奖励\"  AND content LIKE \"%辉晶矿%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t  GROUP BY  t.t_time ) AS T6 ON T6.t_time = T1.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000005_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  (  type1 LIKE \"掠夺战\"   OR type2 LIKE \"开始战斗\"   OR type2 LIKE \"防守\"  OR type2 LIKE \"反击\"  OR type2 LIKE \"刷新版图\"  OR type2 LIKE \"开宝箱\"  )  AND content LIKE \"%获得%100000005%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t  GROUP BY  t.t_time ) AS T7 ON T7.t_time = T1.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (content, \"获得(\", \"\"),  \")\",  \"\"  ) AS content  FROM  logs  WHERE   type1 LIKE \"卡牌\"   AND type2 LIKE \"装备一键强化\"  AND log_time LIKE \"{1}%\"  ) AS t  GROUP BY  t.t_time ) AS T8 ON T8.t_time = T1.t_time LEFT JOIN (  SELECT  t.t_time,  SUM(t.content) AS content,  COUNT(*) AS cs  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000005_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  (type1 LIKE \"冒险\"  OR type2 LIKE \"开始战斗\"  OR type2 LIKE \"结束战斗\"  OR type2 LIKE \"领取章节奖励\"  OR type2 LIKE \"扫荡\")  AND content LIKE \"%获得%100000005%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t  GROUP BY  t.t_time ) AS T9 ON T9.t_time = T1.t_time \r\n', 't_time,cs1,ks1,cs2,ks2,cs3,ks3,cs4,ks4,cs5,ks5,cs6,ks6,cs7,ks7,cs8,ks8,cs9,ks9', 'ore_detailed_summary', '辉晶矿产出详细统计');
INSERT INTO `summary_sql` VALUES ('4', 'SELECT  T2.t_time,  IFNULL(SUM(T1.c), 0) AS c,  IFNULL(SUM(T1.b), 0) AS b,  IFNULL(SUM(T1.p), 0) AS p,  IFNULL(SUM(T1.c1), 0) AS c1,  IFNULL(SUM(T1.b1), 0) AS b1,  IFNULL(SUM(T1.p1), 0) AS p1,  IFNULL(SUM(T1.c2), 0) AS c2,  IFNULL(SUM(T1.b2), 0) AS b2,  IFNULL(SUM(T1.p2), 0) AS p2,  IFNULL(SUM(T1.c3), 0) AS c3,  IFNULL(SUM(T1.b3), 0) AS b3,  IFNULL(SUM(T1.p3), 0) AS p3,  IFNULL(SUM(T1.c4), 0) AS c4,  IFNULL(SUM(T1.b4), 0) AS b4,  IFNULL(SUM(T1.p4), 0) AS p4,  IFNULL(SUM(T1.c5), 0) AS c5,  IFNULL(SUM(T1.b5), 0) AS b5,  IFNULL(SUM(T1.p5), 0) AS p5,  IFNULL(SUM(T1.c6), 0) AS c6,  IFNULL(SUM(T1.b6), 0) AS b6,  IFNULL(SUM(T1.p6), 0) AS p6,  IFNULL(SUM(T1.c7), 0) AS c7,  IFNULL(SUM(T1.b7), 0) AS b7,  IFNULL(SUM(T1.p7), 0) AS p7,  IFNULL(SUM(T1.c8), 0) AS c8,  IFNULL(SUM(T1.b8), 0) AS b8,  IFNULL(SUM(T1.p8), 0) AS p8 FROM  ({0}) AS T2 LEFT JOIN (  SELECT  T.t_time,  SUM(T.content) AS c,  count(T.content) AS b,  count(DISTINCT T.player_id) AS p,  CASE  WHEN T.content BETWEEN 0  AND 100 THEN  SUM(T.content)  ELSE  0  END AS c1,  CASE WHEN T.content BETWEEN 0 AND 100 THEN  count(T.content) ELSE  0 END AS b1,  CASE WHEN T.content BETWEEN 0 AND 100 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p1,  CASE WHEN T.content BETWEEN 101 AND 680 THEN  SUM(T.content) ELSE  0 END AS c2,  CASE WHEN T.content BETWEEN 101 AND 680 THEN  count(T.content) ELSE  0 END AS b2,  CASE WHEN T.content BETWEEN 101 AND 680 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p2,  CASE WHEN T.content BETWEEN 681 AND 1680 THEN  SUM(T.content) ELSE  0 END AS c3,  CASE WHEN T.content BETWEEN 681 AND 1680 THEN  count(T.content) ELSE  0 END AS b3,  CASE WHEN T.content BETWEEN 681 AND 1680 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p3,  CASE WHEN T.content BETWEEN 1681 AND 3280 THEN  SUM(T.content) ELSE  0 END AS c4,  CASE WHEN T.content BETWEEN 1681 AND 3280 THEN  count(T.content) ELSE  0 END AS b4,  CASE WHEN T.content BETWEEN 1681 AND 3280 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p4,  CASE WHEN T.content BETWEEN 3281 AND 6480 THEN  SUM(T.content) ELSE  0 END AS c5,  CASE WHEN T.content BETWEEN 3281 AND 6480 THEN  count(T.content) ELSE  0 END AS b5,  CASE WHEN T.content BETWEEN 3281 AND 6480 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p5,  CASE WHEN T.content BETWEEN 6481 AND 9880 THEN  SUM(T.content) ELSE  0 END AS c6,  CASE WHEN T.content BETWEEN 6481 AND 9880 THEN  count(T.content) ELSE  0 END AS b6,  CASE WHEN T.content BETWEEN 6481 AND 9880 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p6,  CASE WHEN T.content BETWEEN 9881 AND 19880 THEN  SUM(T.content) ELSE  0 END AS c7,  CASE WHEN T.content BETWEEN 9881 AND 19880 THEN  count(T.content) ELSE  0 END AS b7,  CASE WHEN T.content BETWEEN 9881 AND 19880 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p7,  CASE WHEN T.content BETWEEN 19881 AND 49880 THEN  SUM(T.content) ELSE  0 END AS c8,  CASE WHEN T.content BETWEEN 19881 AND 49880 THEN  count(T.content) ELSE  0 END AS b8,  CASE WHEN T.content BETWEEN 19881 AND 49880 THEN  count(DISTINCT T.player_id) ELSE  0 END AS p8 FROM  (  SELECT  REPLACE (  REPLACE (  content,  substr(  content,  locate(\", vip\", content),  CHAR_LENGTH(content) - locate(\", vip\", content) + 1  ),  \"\"  ),  \"购买钻石数量\",  \"\"  ) AS content,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  player_id  FROM  logs  WHERE  type1 LIKE \"商城\"  AND type2 LIKE \"购买钻石\"  AND log_time LIKE \"{1}%\"  ) AS T GROUP BY  T.t_time,  T.content ) AS T1 ON T2.t_time = T1.t_time GROUP BY  T1.t_time \r\n', 't_time,c,b,p,c1,b1,p1,c2,b2,p2,c3,b3,p3,c4,b4,p4,c5,b5,p5,c6,b6,p6,c7,b7,p7,c8,b8,p8', 'rmb_crystal', '计费档次购买分析 人民币-购买水晶');
INSERT INTO `summary_sql` VALUES ('5', 'SELECT  T.t_time,  IFNULL(T1.content, 0) + IFNULL(T2.content, 0) AS sj_hd,  IFNULL(T3.content, 0) + IFNULL(T4.content, 0) AS sj_xh,  IFNULL(T1.content, 0) + IFNULL(T2.content, 0) - IFNULL(T3.content, 0) - IFNULL(T4.content, 0) AS sj_lc,  IFNULL(T5.content, 0) + IFNULL(T6.content, 0) AS ks_hd,  IFNULL(T11.content, 0) AS ks_xh,  IFNULL(T5.content, 0) + IFNULL(T6.content, 0) - IFNULL(T11.content, 0) AS ks_lc,  IFNULL(T7.content, 0) + IFNULL(T8.content, 0) AS jb_hd,  IFNULL(T9.content, 0) + IFNULL(T10.content, 0) AS jb_xh,  IFNULL(T7.content, 0) + IFNULL(T8.content, 0) - IFNULL(T9.content, 0) - IFNULL(T10.content, 0) AS jb_lc FROM  ({0}) AS T LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  REPLACE (  t1.content,  substr(  t1.content,  locate(\",\", t1.content),  CHAR_LENGTH(t1.content) - locate(\",\", t1.content)  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"无暇的水晶x\", content) + 5  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%获得%无暇的水晶%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T1 ON T.t_time = T1.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000004_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%获得%100000004%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T2 ON T2.t_time = T.t_time LEFT JOIN (  SELECT  t3.t_time,  SUM(t3.content) AS content  FROM  (  SELECT  t2.t_time,  REPLACE (  REPLACE (  t2.content,  substr(  t2.content,  locate(\"获得\", t2.content),  CHAR_LENGTH(t2.content) - locate(\"获得\", t2.content) + 1  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  REPLACE (  t1.content,  substr(  t1.content,  locate(\" \", t1.content),  CHAR_LENGTH(t1.content) - locate(\" \", t1.content) + 1  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"无暇的水晶x\", content) + 5  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%消耗%无暇的水晶%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  ) AS t3  GROUP BY  t3.t_time ) AS T3 ON T.t_time = T3.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000004_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%消耗%100000004%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T4 ON T.t_time = T4.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  REPLACE (  t1.content,  substr(  t1.content,  locate(\",\", t1.content),  CHAR_LENGTH(t1.content) - locate(\",\", t1.content) + 1  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"辉晶矿x\", content) + 3  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%获得%辉晶矿%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T5 ON T5.t_time = T.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000005_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%获得%100000005%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T6 ON T6.t_time = T.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  REPLACE (  t1.content,  substr(  t1.content,  locate(\",\", t1.content),  CHAR_LENGTH(t1.content) - locate(\",\", t1.content) + 1  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"金币x\", content) + 2  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%获得%金币x%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T7 ON T7.t_time = T.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000001_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%获得%100000001%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T8 ON T8.t_time = T.t_time LEFT JOIN (  SELECT  t3.t_time,  SUM(t3.content) AS content  FROM  (  SELECT  t2.t_time,  REPLACE (  REPLACE (  t2.content,  substr(  t2.content,  locate(\"获得\", t2.content),  CHAR_LENGTH(t2.content) - locate(\"获得\", t2.content) + 1  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  REPLACE (  t1.content,  substr(  t1.content,  locate(\" \", t1.content),  CHAR_LENGTH(t1.content) - locate(\" \", t1.content) + 1  ),  \"\"  ),  \".\",  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"金币x\", content) + 2  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%消耗%金币x%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  ) AS t3  GROUP BY  t3.t_time ) AS T9 ON T9.t_time = T.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000001_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%消耗%100000001%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T10 ON T10.t_time = T.t_time LEFT JOIN (  SELECT  t2.t_time,  SUM(t2.content) AS content  FROM  (  SELECT  t1.t_time,  REPLACE (  t1.content,  substr(  t1.content,  locate(\";\", t1.content),  CHAR_LENGTH(t1.content) - locate(\";\", t1.content) + 1  ),  \"\"  ) AS content  FROM  (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  content,  substr(  content,  1,  locate(\"100000005_\", content) + 9  ),  \"\"  ) AS content  FROM  logs  WHERE  content LIKE \"%消耗(100000005%\"  AND log_time LIKE \"{1}%\"  ) AS t1  ) AS t2  GROUP BY  t2.t_time ) AS T11 ON T11.t_time = T.t_time \r\n', 't_time,sj_hd,sj_xh,sj_lc,ks_hd,ks_xh,ks_lc,jb_hd,jb_xh,jb_lc', 'money_summary', '货币(产出&消耗)汇总');

-- ----------------------------
-- 货币(产出&消耗)汇总
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='# 货币(产出&消耗)汇总';

-- ----------------------------
-- 人民币购买水晶消费档次统计
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='## 人民币购买水晶消费档次统计';

-- ----------------------------
-- 辉晶矿详细产出统计
-- ----------------------------
DROP TABLE IF EXISTS `ore_detailed_summary`;
CREATE TABLE `ore_detailed_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL COMMENT '日期',
  `cs1` bigint(255) DEFAULT NULL COMMENT '矿石购买次数',
  `ks1` bigint(255) DEFAULT NULL COMMENT '矿石购买辉晶矿',
  `cs2` bigint(255) DEFAULT NULL COMMENT 'VIP商店购买次数',
  `ks2` bigint(255) DEFAULT NULL COMMENT 'VIP商店辉晶矿',
  `cs3` bigint(255) DEFAULT NULL COMMENT '黑市商店购买次数',
  `ks3` bigint(255) DEFAULT NULL COMMENT '黑市商辉晶矿',
  `cs4` bigint(255) DEFAULT NULL COMMENT '冒险者商店购买次数',
  `ks4` bigint(255) DEFAULT NULL COMMENT '冒险者商店辉晶矿',
  `cs5` bigint(255) DEFAULT NULL COMMENT '活动领取次数',
  `ks5` bigint(255) DEFAULT NULL COMMENT '活动领取辉晶矿',
  `cs6` bigint(255) DEFAULT NULL COMMENT '邮件次数',
  `ks6` bigint(255) DEFAULT NULL COMMENT '邮件辉晶矿',
  `cs7` bigint(255) DEFAULT NULL COMMENT '掠夺买次数',
  `ks7` bigint(255) DEFAULT NULL COMMENT '掠夺买辉晶矿',
  `cs8` bigint(255) DEFAULT NULL COMMENT '矿洞产出次数',
  `ks8` bigint(255) DEFAULT NULL COMMENT '矿洞产出辉晶矿',
  `cs9` bigint(255) DEFAULT NULL COMMENT '冒险副本次数',
  `ks9` bigint(255) DEFAULT NULL COMMENT '冒险副本辉晶矿',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='# 辉晶矿详细产出统计';


-- ----------------------------
-- 水晶产出&消耗&留存 (等级汇总)
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='# 水晶产出&消耗&留存 (等级汇总)';

-- ----------------------------
-- 水晶明细汇总
-- ----------------------------
DROP TABLE IF EXISTS `crystal_detailed_summary`;
CREATE TABLE `crystal_detailed_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_time` datetime DEFAULT NULL COMMENT '时间',
  `cz_cs` bigint(255) DEFAULT NULL COMMENT '充值次数',
  `cz_sj` bigint(255) DEFAULT NULL COMMENT '充值水晶',
  `hd_cs` bigint(255) DEFAULT NULL COMMENT '活动次数',
  `hd_sj` bigint(255) DEFAULT NULL COMMENT '活动水晶',
  `tg_cs` bigint(255) DEFAULT NULL COMMENT '通关次数',
  `tg_sj` bigint(255) DEFAULT NULL COMMENT '通关水晶',
  `yj_cs` bigint(255) DEFAULT NULL COMMENT '邮件次数',
  `yj_sj` bigint(255) DEFAULT NULL COMMENT '邮件水晶',
  `jjc_cs` bigint(255) DEFAULT NULL,
  `jjc_sj` bigint(255) DEFAULT NULL,
  `shop_cs` bigint(255) DEFAULT NULL COMMENT '商店次数',
  `shop_sj` bigint(255) DEFAULT NULL COMMENT '商店水晶',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `server_id` int(11) DEFAULT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='# 水晶明细汇总';



-- -- 表 account 中插入字段
ALTER TABLE account ADD platform_ext varchar(255);
ALTER TABLE account ADD login_time timestamp;
ALTER TABLE account ADD lastip char(128);
ALTER TABLE account ADD version char(64);
-- -- 表server中插入一个字段
ALTER TABLE server ADD open_time datetime;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('1', '充值返还', '充值水晶 数量{%s}', '为了回馈各位奋战在希冀大陆上的勇者，光之女神决定将其私藏的极为稀有的“无暇水晶”派送给各位进行了充值的玩家，数量有限，加紧行动哦', '可在下方列表中查看充值返利奖励情况', '1', '0', 'activity_banner_01', '1');
INSERT INTO `activity` VALUES ('2', '龙之馈赠', '无', '龙之一族已经在被封印千年之久的回廊之内逐渐苏醒，勇者们，是时候拿起手中的利剑，去斩杀那邪恶的巨龙，夺取那些隐藏在回廊深处的诱人宝藏了', '在活动时间段内，玩家通关任意难度的龙之回廊关卡，即可获得双倍掉落奖励', '2', '0', 'activity_banner_02', '9');
INSERT INTO `activity` VALUES ('3', '突破星空', '无', '每隔一段时间，宇宙间那无尽星尘的能量便会充斥于十二星宫之中，在此期间内，如能成功突破十二星宫，便可以获得双倍的奖励', '在活动时间段内，玩家通关任意难度的十二星宫关卡，即可获得双倍掉落奖励', '3', '0', 'activity_banner_03', '10');
INSERT INTO `activity` VALUES ('4', '胜者为王', '无', '号外！号外！王国向各位勇者颁布了最新的法案，鼓励各位勇者积极扩张领土，尽快建立起对抗混沌势力的根据地，在此期间，王国奖励那些在掠夺战中取得重大成绩的勇者们', '在活动时间段内，玩家进行“掠夺战”相关活动，即可获得双倍奖励', '4', '0', 'activity_banner_04', '11');
INSERT INTO `activity` VALUES ('5', '新版狂欢', '连续登录{%s}天', '为了庆祝新版本开放，在此期间，玩家只要登陆游戏即可领取对应天数的特殊奖励，奖励内容丰富多彩哦，请各位玩家加紧领取', '可在下方列表中查看新版本每日奖励情况', '5', '0', 'activity_banner_05', '1');
INSERT INTO `activity` VALUES ('6', '冲级送壕礼', '升到{%s}级', '混沌势力已经肆虐整个希冀大陆，为了有足够的战斗与混沌领主决一死战，勇者们，赶紧提升自己的战力吧，王国的军需官将奖励那些把自己变得更强的勇者们', '可在下方列表中查看达到相应等级段所能领取到的具体奖励', '6', '0', 'activity_banner_06', '2');
INSERT INTO `activity` VALUES ('7', '体力领取', '无', '各位勇者是否在冒险途中感到疲累了呢？请前往光之神殿接受女神的祝福吧，她将用那温暖的柔光，抚慰各位勇者疲惫的心灵', '每天指定时间段内，即可在下方免费领取60点体力', '7', '200200230', 'activity_banner_07', '3');
INSERT INTO `activity` VALUES ('8', '专享月卡', '无', '这张闪着金光的小卡片，将给予勇者冒险途中极大的便利，据说其可激活存在于异次元空间的神秘水晶宝库', '当各位勇者在“商城”中成功购买月卡之后，即可在下方领取每日的专属奖励（各类月卡不计入相应充值活动，但加对应充值金额的VIP等级经验）', '8', '200200231', 'activity_banner_08', '5');
INSERT INTO `activity` VALUES ('9', '首冲礼包', '无', '充值任意金额即可获得首冲大礼包', '充值任意金额即可获得首冲大礼包', '9', '0', null, '0');
INSERT INTO `activity` VALUES ('11', '每日充值', '充值水晶{%s}', '每日充值达到相对应的额度，即可领取下列表中对应档位的奖励', '每档每天仅限领取一次哦', '11', '0', 'activity_banner_09', '1');
INSERT INTO `activity` VALUES ('12', '单笔充值', '充值水晶{%s}', '当玩家每次单笔充值达到特定额度时，即可领取相对应额度的奖励', '奖励将不限领取次数哦，冲得越多领得越多，赶紧行动吧！', '12', '0', 'activity_banner_10', '1');
INSERT INTO `activity` VALUES ('13', '水晶累计消耗', '充值水晶{%s}', '亲爱的玩家们，只要在游戏的过程之中，消耗特定数量的水晶，并且达到相对应的档位，便可以领取与之相对应的奖励！', '档位越高，奖励愈加丰富，当每档只能领取一次哦', '13', '0', 'activity_banner_11', '1');
INSERT INTO `activity` VALUES ('14', '物品兑换', '兑换需要物品{%s}数量{%s}', '为了驱除危害人世间的年兽，需要请各位英雄们在关卡或者副本中收集“大红灯笼”以及“喜庆鞭炮”来作为对付年兽的利器，春之女神将准备丰富的大礼回馈各位英雄', null, '14', '2', 'activity_banner_14', '1');
INSERT INTO `activity` VALUES ('15', '排行榜奖励', '排行榜{%s}名', '那些孜孜不倦追求最强的勇者们，将可以在活动时间结束之后，根据其所在战斗力排行榜当中的名次，领取对应的奖励', null, '15', '4', null, '1');
INSERT INTO `activity` VALUES ('21', '10连抽奖励', '水晶召唤十连次数{%s}', '欢乐十连抽，现已开启！是否苦恼还在苦恼没有强力的英雄角色助战！是否想得到超稀有超珍贵的SS级强力英雄，赶紧来进行十连抽，您的愿望都能通过十连抽一一满足你！在此期间，只要您进行十连抽，还可以参加丰富的活动，领取价值不菲的奖励哦！', null, '21', '0', 'activity_banner_26', '1');
INSERT INTO `activity` VALUES ('22', '战力排行榜', '排行榜{%s}名', '那些孜孜不倦追求最强的勇者们，将可以在活动时间结束之后，根据其所在战斗力排行榜当中的名次，领取对应的奖励', '各排名奖励将在结算后自动发送到玩家的邮箱之中', '15', '3', 'activity_banner_13', '6');
INSERT INTO `activity` VALUES ('23', '掠夺排行榜', '排行榜{%s}名', '那些征战在战场上的勇者们，将可以在活动时间结束之后，根据其所在掠夺战排行榜当中的名次，领取对应的奖励', '各排名奖励将在结算后自动发送到玩家的邮箱之中', '15', '2', 'activity_banner_14', '7');
INSERT INTO `activity` VALUES ('24', '收集度排行榜', '排行榜{%s}名', '那些热衷于收集不同角色的玩家们，将可以在活动时间结束之后，根据其所在竞技场排行榜当中的名次，领取对应的奖励', '各排名奖励将在结算后自动发送到玩家的邮箱之中', '15', '4', 'activity_banner_21', '8');
INSERT INTO `activity` VALUES ('25', '新春充值返利', '充值水晶{%s}', '在春节假期期间，只要每天充值达到特定的额度，便可以获得一定比例的返还，充得越多返得越多！\r\n1.每日累计充值100-999元返利15%；\r\n2.每日累计充值1000-2999元，返利20%；\r\n3.每日累计充值3000-4999元，返利25%；\r\n4.每日累计充值5000元以上，返利30%', '每天的充值额度都将被重置，且对应的奖励将在每天凌晨0点准时发送到邮箱之中', '19', '0', 'activity_banner_20', '0');
INSERT INTO `activity` VALUES ('26', '新春狂欢活动', '兑换需要物品{%s}数量{%s}', '玩家们可以通过上交不同数量的“大红灯笼”以及“喜庆鞭炮”来兑换不同档位的奖励（每档兑换不限次数）为了驱除危害人世间的年兽，需要请各位英雄们在关卡或者副本中收集“大红灯笼”以及“喜庆鞭炮”来作为对付年兽的利器，春之女神将准备丰富的大礼回馈各位英雄             ', '玩家们可以通过上交不同数量的“大红灯笼”以及“喜庆鞭炮”来兑换不同档位的奖励（每档兑换不限次数）', '14', '9999', 'activity_banner_19', '0');
INSERT INTO `activity` VALUES ('27', '情人节兑换活动', '兑换需要物品{%s}数量{%s}', '在情人节期间，各关卡以及副本的怪物将掉落代表了甜蜜气息的“鲜艳玫瑰”以及“甜蜜巧克力”，只要收集特定数量的物品，便可以向爱之女神兑换相对应档次的奖励哦！赶紧行动吧', '玩家们可以通过上交不同数量的“鲜艳玫瑰”以及“甜蜜巧克力”来兑换不同档位的奖励（每档兑换不限次数）', '14', '9999', 'activity_banner_23', '0');
INSERT INTO `activity` VALUES ('28', '情人节单笔充值', '充值水晶{%s}', '奖励将不限领取次数哦，冲得越多领得越多，赶紧行动吧！', '奖励将不限领取次数哦，冲得越多领得越多，赶紧行动吧！', '14', '0', 'activity_banner_24', '0');
INSERT INTO `activity` VALUES ('29', '成长计划活动', '达到VIP等级条件{%s}消耗水晶{%s}', '购买成长计划，享受飞一般的超值返利，玩家只需达到VIP2级，且消耗1000数量的无暇水晶便可在下方购得成长计划，当玩家达到特定等级之后，即可领取丰厚的水晶返利！总返还额度高达20倍！', '成长计划无时间限定，玩家只要达到特定的等级，即可领取相对应的奖励，赶紧升级吧！', '22', '1000', 'activity_banner_27', '2');
INSERT INTO `activity` VALUES ('30', '角色收集活动（品阶）', '收集品阶{%s}个数{%s}', '只要收集特定品阶特定数量的角色，便可领取超稀有的宝箱奖励，宝箱之中或许隐藏着前所未见的SS级新英雄哦！马上行动吧！', '当特定品阶的英雄达到数量要求之后，便可领取对应档次的相关奖励', '23', '0', 'activity_banner_28', '5');
INSERT INTO `activity` VALUES ('31', '角色收集活动（星数）', '收集星数{%s}个数{%s}', '只要收集特定星数特定数量的角色，便可领取超稀有的宝箱奖励，宝箱之中或许隐藏着前所未见的SS级新英雄哦！马上行动吧！', '当特定星数的英雄达到数量要求之后，便可领取对应档次的相关奖励', '24', '0', 'activity_banner_29', '4');
INSERT INTO `activity` VALUES ('32', 'VIP月卡', '无', '购买彰显身份的VIP专属月卡，便可以每日领取数量众多的超值水晶奖励，总返还额度高达9倍的返还额度！', '在商城之中购买VIP月卡，即可激活每日VIP月卡领取特权！（各类月卡不计入相应充值活动，但加对应充值金额的VIP等级经验）', '25', '200200273', 'activity_banner_30', '6');
INSERT INTO `activity` VALUES ('33', 'VIP特权', '无', '成为尊贵的VIP玩家，即可享受每天领取特殊物资奖励的特权，这些稀有的物资，将大大增强你队伍的战斗力，加快游戏的进程哦！', '只能领取对应VIP等级的奖励，且每天仅限一次，记得领取哟！', '28', '0', 'activity_banner_31', '7');
INSERT INTO `activity` VALUES ('34', '每日首充（一）', '充值水晶{%s}', '玩家只要每日进行一档10元首充，便可以免费领取丰厚的奖励！每日仅限一次，请赶紧行动哦！', '每天仅限领取一次，请记得及时领取哦，超过12点后本活动将被重置', '11', '0', 'activity_banner_32', '3');
INSERT INTO `activity` VALUES ('35', '每日首充（二）', '充值水晶{%s}', '玩家只要每日进行一档10元首充，便可以免费领取丰厚的奖励！每日仅限一次，请赶紧行动哦！', '每天仅限领取一次，请记得及时领取哦，超过12点后本活动将被重置', '11', '0', 'activity_banner_32', '3');
INSERT INTO `activity` VALUES ('36', '每日首充（三）', '充值水晶{%s}', '玩家只要每日进行一档10元首充，便可以免费领取丰厚的奖励！每日仅限一次，请赶紧行动哦！', '每天仅限领取一次，请记得及时领取哦，超过12点后本活动将被重置', '11', '0', 'activity_banner_32', '3');
INSERT INTO `activity` VALUES ('37', '竞技场排行榜', '排行榜{%s}名', '那些孜孜不倦追求排行榜王座顶端勇者们，将可以在活动时间结束之后，根据其所在战斗力排行榜当中的名次，领取对应的奖励', '各排名奖励将在结算后自动发送到玩家的邮箱之中', '15', '1', 'activity_banner_25', '7');
INSERT INTO `activity` VALUES ('38', '神秘英雄限时抢购', '购买 {%s} 数量{%s} ', 'SS级以及S级英雄碎片已经上架，限时购买开启中！！想要强力的角色？还是想提升星数让战斗力突破天际？那就请赶紧兑换这千载难逢的英雄碎片吧', '在活动期间，玩家可以无限量购买已上架的英雄碎片，待活动结束之后，所有英雄碎片将下架，欲购从速', '14', '9999', 'activity_banner_22\r\n', '2');


-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) DEFAULT NULL COMMENT '物品名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=400100157 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('100000001', '金币');
INSERT INTO `item` VALUES ('100000002', '账号经验值');
INSERT INTO `item` VALUES ('100000003', '角色经验值');
INSERT INTO `item` VALUES ('100000004', '无暇的水晶');
INSERT INTO `item` VALUES ('100000005', '辉晶矿');
INSERT INTO `item` VALUES ('100000006', '行动力');
INSERT INTO `item` VALUES ('100000007', '决斗通行证');
INSERT INTO `item` VALUES ('100000008', '战争号角');
INSERT INTO `item` VALUES ('100000009', '荣耀纹章');
INSERT INTO `item` VALUES ('100000010', '友情值');
INSERT INTO `item` VALUES ('100000011', '公会之证');
INSERT INTO `item` VALUES ('100000012', '遗忘之钥');
INSERT INTO `item` VALUES ('100000013', '成就点');
INSERT INTO `item` VALUES ('100000014', '星尘碎屑');
INSERT INTO `item` VALUES ('100000015', '物理英魂碎片');
INSERT INTO `item` VALUES ('100000016', '魔法英魂碎片');
INSERT INTO `item` VALUES ('100000017', '守卫英魂碎片');
INSERT INTO `item` VALUES ('100000018', '全能英魂碎片');
INSERT INTO `item` VALUES ('100000019', '羁绊值');
INSERT INTO `item` VALUES ('100000020', 'VIP4体验券');
INSERT INTO `item` VALUES ('100000021', '奥义之书');
INSERT INTO `item` VALUES ('100000022', '技能之书');
INSERT INTO `item` VALUES ('100000023', '符文强化石');
INSERT INTO `item` VALUES ('100100001', '风化战斧');
INSERT INTO `item` VALUES ('100100002', '铸铁战斧');
INSERT INTO `item` VALUES ('100100003', '铜制双刃斧');
INSERT INTO `item` VALUES ('100100004', '秘银双刃斧');
INSERT INTO `item` VALUES ('100100005', '魔晶长刃斧');
INSERT INTO `item` VALUES ('100100006', '陨星长刃斧');
INSERT INTO `item` VALUES ('100100007', '赫拉克勒斯之斧');
INSERT INTO `item` VALUES ('100100008', '风化长剑');
INSERT INTO `item` VALUES ('100100009', '铸铁长剑');
INSERT INTO `item` VALUES ('100100010', '铜制宽刃剑');
INSERT INTO `item` VALUES ('100100011', '秘银宽刃剑');
INSERT INTO `item` VALUES ('100100012', '魔晶双刃剑');
INSERT INTO `item` VALUES ('100100013', '陨星双刃剑');
INSERT INTO `item` VALUES ('100100014', '无尽星海之剑');
INSERT INTO `item` VALUES ('100100015', '风化魔导书');
INSERT INTO `item` VALUES ('100100016', '古旧魔导书');
INSERT INTO `item` VALUES ('100100017', '铜制秘典');
INSERT INTO `item` VALUES ('100100018', '秘银秘典');
INSERT INTO `item` VALUES ('100100019', '魔晶圣典');
INSERT INTO `item` VALUES ('100100020', '陨星圣典');
INSERT INTO `item` VALUES ('100100021', '光辉十字之书');
INSERT INTO `item` VALUES ('100100022', '风化手杖');
INSERT INTO `item` VALUES ('100100023', '古旧手杖');
INSERT INTO `item` VALUES ('100100024', '铜制长杖');
INSERT INTO `item` VALUES ('100100025', '秘银长杖');
INSERT INTO `item` VALUES ('100100026', '魔晶魔杖');
INSERT INTO `item` VALUES ('100100027', '陨星魔杖');
INSERT INTO `item` VALUES ('100100028', '无尽法力之杖');
INSERT INTO `item` VALUES ('100100029', '风化短剑&圆盾');
INSERT INTO `item` VALUES ('100100030', '铸铁短剑&圆盾');
INSERT INTO `item` VALUES ('100100031', '铜制阔剑&重盾');
INSERT INTO `item` VALUES ('100100032', '秘银阔剑&重盾');
INSERT INTO `item` VALUES ('100100033', '魔晶利刃&鸢盾');
INSERT INTO `item` VALUES ('100100034', '陨星利刃&鸢盾');
INSERT INTO `item` VALUES ('100100035', '阿瓦隆的护佑');
INSERT INTO `item` VALUES ('100100036', '风化骑兵枪');
INSERT INTO `item` VALUES ('100100037', '铸铁骑兵枪');
INSERT INTO `item` VALUES ('100100038', '铜制骑士枪');
INSERT INTO `item` VALUES ('100100039', '秘银骑士枪');
INSERT INTO `item` VALUES ('100100040', '魔晶征服长枪');
INSERT INTO `item` VALUES ('100100041', '陨星征服长枪');
INSERT INTO `item` VALUES ('100100042', '无双战神之枪');
INSERT INTO `item` VALUES ('100100043', '风化渔叉');
INSERT INTO `item` VALUES ('100100044', '铸铁渔叉');
INSERT INTO `item` VALUES ('100100045', '铜制战戟');
INSERT INTO `item` VALUES ('100100046', '秘银战戟');
INSERT INTO `item` VALUES ('100100047', '魔晶三叉戟');
INSERT INTO `item` VALUES ('100100048', '陨星三叉戟');
INSERT INTO `item` VALUES ('100100049', '海神之戟');
INSERT INTO `item` VALUES ('100100050', '风化长矛');
INSERT INTO `item` VALUES ('100100051', '铸铁长矛');
INSERT INTO `item` VALUES ('100100052', '铜制长枪');
INSERT INTO `item` VALUES ('100100053', '秘银长枪');
INSERT INTO `item` VALUES ('100100054', '魔晶战戈');
INSERT INTO `item` VALUES ('100100055', '陨星战戈');
INSERT INTO `item` VALUES ('100100056', '昆古尼尔之枪');
INSERT INTO `item` VALUES ('100100057', '风化短弓');
INSERT INTO `item` VALUES ('100100058', '铸铁短弓');
INSERT INTO `item` VALUES ('100100059', '铜制长弓');
INSERT INTO `item` VALUES ('100100060', '秘银长弓');
INSERT INTO `item` VALUES ('100100061', '魔晶战斗弓');
INSERT INTO `item` VALUES ('100100062', '陨星战斗弓');
INSERT INTO `item` VALUES ('100100063', '凤凰炎羽弓');
INSERT INTO `item` VALUES ('100100064', '风化指虎');
INSERT INTO `item` VALUES ('100100065', '铸铁指虎');
INSERT INTO `item` VALUES ('100100066', '铜制护拳');
INSERT INTO `item` VALUES ('100100067', '秘银护拳');
INSERT INTO `item` VALUES ('100100068', '魔晶重拳');
INSERT INTO `item` VALUES ('100100069', '陨星重拳');
INSERT INTO `item` VALUES ('100100070', '奔雷迅影之拳');
INSERT INTO `item` VALUES ('100100071', '风化法球');
INSERT INTO `item` VALUES ('100100072', '古旧法球');
INSERT INTO `item` VALUES ('100100073', '镶铜水晶球');
INSERT INTO `item` VALUES ('100100074', '镶银水晶球');
INSERT INTO `item` VALUES ('100100075', '魔晶魔导器');
INSERT INTO `item` VALUES ('100100076', '陨星魔导器');
INSERT INTO `item` VALUES ('100100077', '远古封印之器');
INSERT INTO `item` VALUES ('100100078', '风化步兵对剑');
INSERT INTO `item` VALUES ('100100079', '铸铁步兵对剑');
INSERT INTO `item` VALUES ('100100080', '铜制骑兵对剑');
INSERT INTO `item` VALUES ('100100081', '秘银骑兵对剑');
INSERT INTO `item` VALUES ('100100082', '魔晶贵族对剑');
INSERT INTO `item` VALUES ('100100083', '陨星贵族对剑');
INSERT INTO `item` VALUES ('100100084', '绝影·闪光');
INSERT INTO `item` VALUES ('100100085', '风化飘带');
INSERT INTO `item` VALUES ('100100086', '古旧飘带');
INSERT INTO `item` VALUES ('100100087', '铜环丝带');
INSERT INTO `item` VALUES ('100100088', '银环丝带');
INSERT INTO `item` VALUES ('100100089', '魔晶缎带');
INSERT INTO `item` VALUES ('100100090', '陨星缎带');
INSERT INTO `item` VALUES ('100100091', '西风之轻抚');
INSERT INTO `item` VALUES ('100100092', '风化打刀');
INSERT INTO `item` VALUES ('100100093', '铸铁打刀');
INSERT INTO `item` VALUES ('100100094', '铜制太刀');
INSERT INTO `item` VALUES ('100100095', '秘银太刀');
INSERT INTO `item` VALUES ('100100096', '魔晶野太刀');
INSERT INTO `item` VALUES ('100100097', '陨星野太刀');
INSERT INTO `item` VALUES ('100100098', '一文字·刹那');
INSERT INTO `item` VALUES ('100100099', '风化短刀');
INSERT INTO `item` VALUES ('100100100', '铸铁短刀');
INSERT INTO `item` VALUES ('100100101', '铜制胁差');
INSERT INTO `item` VALUES ('100100102', '秘银胁差');
INSERT INTO `item` VALUES ('100100103', '魔晶忍刃');
INSERT INTO `item` VALUES ('100100104', '陨星忍刃');
INSERT INTO `item` VALUES ('100100105', '秘刃·暗啸');
INSERT INTO `item` VALUES ('100100106', '风化鲁特琴');
INSERT INTO `item` VALUES ('100100107', '古旧鲁特琴');
INSERT INTO `item` VALUES ('100100108', '镶铜曼陀林');
INSERT INTO `item` VALUES ('100100109', '镶银曼陀林');
INSERT INTO `item` VALUES ('100100110', '魔晶西奥伯琴');
INSERT INTO `item` VALUES ('100100111', '陨星西奥伯琴');
INSERT INTO `item` VALUES ('100100112', '热情之吉他');
INSERT INTO `item` VALUES ('100100113', '风化小刀');
INSERT INTO `item` VALUES ('100100114', '铸铁小刀');
INSERT INTO `item` VALUES ('100100115', '铜制短匕');
INSERT INTO `item` VALUES ('100100116', '秘银短匕');
INSERT INTO `item` VALUES ('100100117', '魔晶大马士革刀');
INSERT INTO `item` VALUES ('100100118', '陨星大马士革刀');
INSERT INTO `item` VALUES ('100100119', '闪光无影刃');
INSERT INTO `item` VALUES ('100100120', '风化长镰');
INSERT INTO `item` VALUES ('100100121', '铸铁长镰');
INSERT INTO `item` VALUES ('100100122', '铜制巨镰');
INSERT INTO `item` VALUES ('100100123', '秘银巨镰');
INSERT INTO `item` VALUES ('100100124', '魔晶魔镰');
INSERT INTO `item` VALUES ('100100125', '陨星魔镰');
INSERT INTO `item` VALUES ('100100126', '深渊之呼唤');
INSERT INTO `item` VALUES ('100100127', '风化细剑');
INSERT INTO `item` VALUES ('100100128', '铸铁细剑');
INSERT INTO `item` VALUES ('100100129', '铜制袖剑');
INSERT INTO `item` VALUES ('100100130', '秘银袖剑');
INSERT INTO `item` VALUES ('100100131', '魔晶刺刃');
INSERT INTO `item` VALUES ('100100132', '陨星刺刃');
INSERT INTO `item` VALUES ('100100133', '邪影之牙');
INSERT INTO `item` VALUES ('100100134', '风化笼柄剑');
INSERT INTO `item` VALUES ('100100135', '铸铁笼柄剑');
INSERT INTO `item` VALUES ('100100136', '铜质带柄弯刀');
INSERT INTO `item` VALUES ('100100137', '秘银带柄弯刀');
INSERT INTO `item` VALUES ('100100138', '魔晶仪式佩剑');
INSERT INTO `item` VALUES ('100100139', '陨星仪式佩剑');
INSERT INTO `item` VALUES ('100100140', '胜利誓约之剑');
INSERT INTO `item` VALUES ('100100141', '风化残符');
INSERT INTO `item` VALUES ('100100142', '古旧残符');
INSERT INTO `item` VALUES ('100100143', '铜纹符咒');
INSERT INTO `item` VALUES ('100100144', '银纹符咒');
INSERT INTO `item` VALUES ('100100145', '魔晶降魔符');
INSERT INTO `item` VALUES ('100100146', '陨星降魔符');
INSERT INTO `item` VALUES ('100100147', '秘符·急急如律令');
INSERT INTO `item` VALUES ('100100148', '蛋白之戒·强攻');
INSERT INTO `item` VALUES ('100100149', '猫眼之戒·强攻');
INSERT INTO `item` VALUES ('100100150', '孔雀之戒·强攻');
INSERT INTO `item` VALUES ('100100151', '珊瑚之戒·强攻');
INSERT INTO `item` VALUES ('100100152', '水晶之戒·强攻');
INSERT INTO `item` VALUES ('100100153', '钻石之戒·强攻');
INSERT INTO `item` VALUES ('100100154', '辉光之戒·强攻');
INSERT INTO `item` VALUES ('100100155', '蛋白之戒·魔能');
INSERT INTO `item` VALUES ('100100156', '猫眼之戒·魔能');
INSERT INTO `item` VALUES ('100100157', '孔雀之戒·魔能');
INSERT INTO `item` VALUES ('100100158', '珊瑚之戒·魔能');
INSERT INTO `item` VALUES ('100100159', '水晶之戒·魔能');
INSERT INTO `item` VALUES ('100100160', '钻石之戒·魔能');
INSERT INTO `item` VALUES ('100100161', '辉光之戒·魔能');
INSERT INTO `item` VALUES ('100100162', '蛋白之戒·坚守');
INSERT INTO `item` VALUES ('100100163', '猫眼之戒·坚守');
INSERT INTO `item` VALUES ('100100164', '孔雀之戒·坚守');
INSERT INTO `item` VALUES ('100100165', '珊瑚之戒·坚守');
INSERT INTO `item` VALUES ('100100166', '水晶之戒·坚守');
INSERT INTO `item` VALUES ('100100167', '钻石之戒·坚守');
INSERT INTO `item` VALUES ('100100168', '辉光之戒·坚守');
INSERT INTO `item` VALUES ('100100169', '蛋白之戒·驭魔');
INSERT INTO `item` VALUES ('100100170', '猫眼之戒·驭魔');
INSERT INTO `item` VALUES ('100100171', '孔雀之戒·驭魔');
INSERT INTO `item` VALUES ('100100172', '珊瑚之戒·驭魔');
INSERT INTO `item` VALUES ('100100173', '水晶之戒·驭魔');
INSERT INTO `item` VALUES ('100100174', '钻石之戒·驭魔');
INSERT INTO `item` VALUES ('100100175', '辉光之戒·驭魔');
INSERT INTO `item` VALUES ('100100176', '蛋白之戒·光速');
INSERT INTO `item` VALUES ('100100177', '猫眼之戒·光速');
INSERT INTO `item` VALUES ('100100178', '孔雀之戒·光速');
INSERT INTO `item` VALUES ('100100179', '珊瑚之戒·光速');
INSERT INTO `item` VALUES ('100100180', '水晶之戒·光速');
INSERT INTO `item` VALUES ('100100181', '钻石之戒·光速');
INSERT INTO `item` VALUES ('100100182', '辉光之戒·光速');
INSERT INTO `item` VALUES ('100100183', '蛋白之戒·强运');
INSERT INTO `item` VALUES ('100100184', '猫眼之戒·强运');
INSERT INTO `item` VALUES ('100100185', '孔雀之戒·强运');
INSERT INTO `item` VALUES ('100100186', '珊瑚之戒·强运');
INSERT INTO `item` VALUES ('100100187', '水晶之戒·强运');
INSERT INTO `item` VALUES ('100100188', '钻石之戒·强运');
INSERT INTO `item` VALUES ('100100189', '辉光之戒·强运');
INSERT INTO `item` VALUES ('100100190', '蛋白之戒·蓄能');
INSERT INTO `item` VALUES ('100100191', '猫眼之戒·蓄能');
INSERT INTO `item` VALUES ('100100192', '孔雀之戒·蓄能');
INSERT INTO `item` VALUES ('100100193', '珊瑚之戒·蓄能');
INSERT INTO `item` VALUES ('100100194', '水晶之戒·蓄能');
INSERT INTO `item` VALUES ('100100195', '钻石之戒·蓄能');
INSERT INTO `item` VALUES ('100100196', '辉光之戒·蓄能');
INSERT INTO `item` VALUES ('100100197', '蛋白之戒·破甲');
INSERT INTO `item` VALUES ('100100198', '猫眼之戒·破甲');
INSERT INTO `item` VALUES ('100100199', '孔雀之戒·破甲');
INSERT INTO `item` VALUES ('100100200', '珊瑚之戒·破甲');
INSERT INTO `item` VALUES ('100100201', '水晶之戒·破甲');
INSERT INTO `item` VALUES ('100100202', '钻石之戒·破甲');
INSERT INTO `item` VALUES ('100100203', '辉光之戒·破甲');
INSERT INTO `item` VALUES ('100100204', '蛋白之戒·识破');
INSERT INTO `item` VALUES ('100100205', '猫眼之戒·识破');
INSERT INTO `item` VALUES ('100100206', '孔雀之戒·识破');
INSERT INTO `item` VALUES ('100100207', '珊瑚之戒·识破');
INSERT INTO `item` VALUES ('100100208', '水晶之戒·识破');
INSERT INTO `item` VALUES ('100100209', '钻石之戒·识破');
INSERT INTO `item` VALUES ('100100210', '辉光之戒·识破');
INSERT INTO `item` VALUES ('100100211', '蛋白之戒·迅捷');
INSERT INTO `item` VALUES ('100100212', '猫眼之戒·迅捷');
INSERT INTO `item` VALUES ('100100213', '孔雀之戒·迅捷');
INSERT INTO `item` VALUES ('100100214', '珊瑚之戒·迅捷');
INSERT INTO `item` VALUES ('100100215', '水晶之戒·迅捷');
INSERT INTO `item` VALUES ('100100216', '钻石之戒·迅捷');
INSERT INTO `item` VALUES ('100100217', '辉光之戒·迅捷');
INSERT INTO `item` VALUES ('100100218', '蛋白之戒·强固');
INSERT INTO `item` VALUES ('100100219', '猫眼之戒·强固');
INSERT INTO `item` VALUES ('100100220', '孔雀之戒·强固');
INSERT INTO `item` VALUES ('100100221', '珊瑚之戒·强固');
INSERT INTO `item` VALUES ('100100222', '水晶之戒·强固');
INSERT INTO `item` VALUES ('100100223', '钻石之戒·强固');
INSERT INTO `item` VALUES ('100100224', '辉光之戒·强固');
INSERT INTO `item` VALUES ('100100225', '蛋白之戒·强命');
INSERT INTO `item` VALUES ('100100226', '猫眼之戒·强命');
INSERT INTO `item` VALUES ('100100227', '孔雀之戒·强命');
INSERT INTO `item` VALUES ('100100228', '珊瑚之戒·强命');
INSERT INTO `item` VALUES ('100100229', '水晶之戒·强命');
INSERT INTO `item` VALUES ('100100230', '钻石之戒·强命');
INSERT INTO `item` VALUES ('100100231', '辉光之戒·强命');
INSERT INTO `item` VALUES ('100100232', '蛋白吊坠·蛮神');
INSERT INTO `item` VALUES ('100100233', '猫眼吊坠·蛮神');
INSERT INTO `item` VALUES ('100100234', '孔雀吊坠·蛮神');
INSERT INTO `item` VALUES ('100100235', '珊瑚吊坠·蛮神');
INSERT INTO `item` VALUES ('100100236', '水晶吊坠·蛮神');
INSERT INTO `item` VALUES ('100100237', '钻石吊坠·蛮神');
INSERT INTO `item` VALUES ('100100238', '辉光吊坠·蛮神');
INSERT INTO `item` VALUES ('100100239', '蛋白吊坠·神射');
INSERT INTO `item` VALUES ('100100240', '猫眼吊坠·神射');
INSERT INTO `item` VALUES ('100100241', '孔雀吊坠·神射');
INSERT INTO `item` VALUES ('100100242', '珊瑚吊坠·神射');
INSERT INTO `item` VALUES ('100100243', '水晶吊坠·神射');
INSERT INTO `item` VALUES ('100100244', '钻石吊坠·神射');
INSERT INTO `item` VALUES ('100100245', '辉光吊坠·神射');
INSERT INTO `item` VALUES ('100100246', '蛋白吊坠·超碾');
INSERT INTO `item` VALUES ('100100247', '猫眼吊坠·超碾');
INSERT INTO `item` VALUES ('100100248', '孔雀吊坠·超碾');
INSERT INTO `item` VALUES ('100100249', '珊瑚吊坠·超碾');
INSERT INTO `item` VALUES ('100100250', '水晶吊坠·超碾');
INSERT INTO `item` VALUES ('100100251', '钻石吊坠·超碾');
INSERT INTO `item` VALUES ('100100252', '辉光吊坠·超碾');
INSERT INTO `item` VALUES ('100100253', '蛋白吊坠·灵猿');
INSERT INTO `item` VALUES ('100100254', '猫眼吊坠·灵猿');
INSERT INTO `item` VALUES ('100100255', '孔雀吊坠·灵猿');
INSERT INTO `item` VALUES ('100100256', '珊瑚吊坠·灵猿');
INSERT INTO `item` VALUES ('100100257', '水晶吊坠·灵猿');
INSERT INTO `item` VALUES ('100100258', '钻石吊坠·灵猿');
INSERT INTO `item` VALUES ('100100259', '辉光吊坠·灵猿');
INSERT INTO `item` VALUES ('100100260', '蛋白吊坠·巨神');
INSERT INTO `item` VALUES ('100100261', '猫眼吊坠·巨神');
INSERT INTO `item` VALUES ('100100262', '孔雀吊坠·巨神');
INSERT INTO `item` VALUES ('100100263', '珊瑚吊坠·巨神');
INSERT INTO `item` VALUES ('100100264', '水晶吊坠·巨神');
INSERT INTO `item` VALUES ('100100265', '钻石吊坠·巨神');
INSERT INTO `item` VALUES ('100100266', '辉光吊坠·巨神');
INSERT INTO `item` VALUES ('100100267', '风化战斗重盔');
INSERT INTO `item` VALUES ('100100268', '白钢战斗重盔');
INSERT INTO `item` VALUES ('100100269', '蓝铜战斗重盔');
INSERT INTO `item` VALUES ('100100270', '真银战斗重盔');
INSERT INTO `item` VALUES ('100100271', '黑金战斗重盔');
INSERT INTO `item` VALUES ('100100272', '黑曜石战斗重盔');
INSERT INTO `item` VALUES ('100100273', '辉光战斗重盔');
INSERT INTO `item` VALUES ('100100274', '风化魔法王冠');
INSERT INTO `item` VALUES ('100100275', '白钢魔法王冠');
INSERT INTO `item` VALUES ('100100276', '蓝铜魔法王冠');
INSERT INTO `item` VALUES ('100100277', '真银魔法王冠');
INSERT INTO `item` VALUES ('100100278', '黑金魔法王冠');
INSERT INTO `item` VALUES ('100100279', '黑曜石魔法王冠');
INSERT INTO `item` VALUES ('100100280', '辉光魔法王冠');
INSERT INTO `item` VALUES ('100100281', '风化强固面甲');
INSERT INTO `item` VALUES ('100100282', '白钢强固面甲');
INSERT INTO `item` VALUES ('100100283', '蓝铜强固面甲');
INSERT INTO `item` VALUES ('100100284', '真银强固面甲');
INSERT INTO `item` VALUES ('100100285', '黑金强固面甲');
INSERT INTO `item` VALUES ('100100286', '黑曜石强固面甲');
INSERT INTO `item` VALUES ('100100287', '辉光强固面甲');
INSERT INTO `item` VALUES ('100100288', '风化御魔头冠');
INSERT INTO `item` VALUES ('100100289', '白钢御魔头冠');
INSERT INTO `item` VALUES ('100100290', '蓝铜御魔头冠');
INSERT INTO `item` VALUES ('100100291', '真银御魔头冠');
INSERT INTO `item` VALUES ('100100292', '黑金御魔头冠');
INSERT INTO `item` VALUES ('100100293', '黑曜石御魔头冠');
INSERT INTO `item` VALUES ('100100294', '辉光御魔头冠');
INSERT INTO `item` VALUES ('100100295', '风化领主重盔');
INSERT INTO `item` VALUES ('100100296', '白钢领主重盔');
INSERT INTO `item` VALUES ('100100297', '蓝铜领主重盔');
INSERT INTO `item` VALUES ('100100298', '真银领主重盔');
INSERT INTO `item` VALUES ('100100299', '黑金领主重盔');
INSERT INTO `item` VALUES ('100100300', '黑曜石领主重盔');
INSERT INTO `item` VALUES ('100100301', '辉光领主重盔');
INSERT INTO `item` VALUES ('100100302', '风化骑士战盔');
INSERT INTO `item` VALUES ('100100303', '白钢骑士战盔');
INSERT INTO `item` VALUES ('100100304', '蓝铜骑士战盔');
INSERT INTO `item` VALUES ('100100305', '真银骑士战盔');
INSERT INTO `item` VALUES ('100100306', '黑金骑士战盔');
INSERT INTO `item` VALUES ('100100307', '黑曜石骑士战盔');
INSERT INTO `item` VALUES ('100100308', '辉光骑士战盔');
INSERT INTO `item` VALUES ('100100309', '风化强袭骑盔');
INSERT INTO `item` VALUES ('100100310', '硬革强袭骑盔');
INSERT INTO `item` VALUES ('100100311', '蛇鳞强袭骑盔');
INSERT INTO `item` VALUES ('100100312', '刃狼强袭骑盔');
INSERT INTO `item` VALUES ('100100313', '雪熊强袭骑盔');
INSERT INTO `item` VALUES ('100100314', '龙鳞强袭骑盔');
INSERT INTO `item` VALUES ('100100315', '辉光强袭骑盔');
INSERT INTO `item` VALUES ('100100316', '风化铭文兜帽');
INSERT INTO `item` VALUES ('100100317', '硬革铭文兜帽');
INSERT INTO `item` VALUES ('100100318', '蛇鳞铭文兜帽');
INSERT INTO `item` VALUES ('100100319', '刃狼铭文兜帽');
INSERT INTO `item` VALUES ('100100320', '雪熊铭文兜帽');
INSERT INTO `item` VALUES ('100100321', '龙鳞铭文兜帽');
INSERT INTO `item` VALUES ('100100322', '辉光铭文兜帽');
INSERT INTO `item` VALUES ('100100323', '风化护目镜');
INSERT INTO `item` VALUES ('100100324', '硬革护目镜');
INSERT INTO `item` VALUES ('100100325', '蛇鳞护目镜');
INSERT INTO `item` VALUES ('100100326', '刃狼护目镜');
INSERT INTO `item` VALUES ('100100327', '雪熊护目镜');
INSERT INTO `item` VALUES ('100100328', '龙鳞护目镜');
INSERT INTO `item` VALUES ('100100329', '辉光护目镜');
INSERT INTO `item` VALUES ('100100330', '风化魔化面罩');
INSERT INTO `item` VALUES ('100100331', '硬革魔化面罩');
INSERT INTO `item` VALUES ('100100332', '蛇鳞魔化面罩');
INSERT INTO `item` VALUES ('100100333', '刃狼魔化面罩');
INSERT INTO `item` VALUES ('100100334', '雪熊魔化面罩');
INSERT INTO `item` VALUES ('100100335', '龙鳞魔化面罩');
INSERT INTO `item` VALUES ('100100336', '辉光魔化面罩');
INSERT INTO `item` VALUES ('100100337', '风化精制盔帽');
INSERT INTO `item` VALUES ('100100338', '硬革精制盔帽');
INSERT INTO `item` VALUES ('100100339', '蛇鳞精制盔帽');
INSERT INTO `item` VALUES ('100100340', '刃狼精制盔帽');
INSERT INTO `item` VALUES ('100100341', '雪熊精制盔帽');
INSERT INTO `item` VALUES ('100100342', '龙鳞精制盔帽');
INSERT INTO `item` VALUES ('100100343', '辉光精制盔帽');
INSERT INTO `item` VALUES ('100100344', '风化灵巧围巾');
INSERT INTO `item` VALUES ('100100345', '硬革灵巧围巾');
INSERT INTO `item` VALUES ('100100346', '蛇鳞灵巧围巾');
INSERT INTO `item` VALUES ('100100347', '刃狼灵巧围巾');
INSERT INTO `item` VALUES ('100100348', '雪熊灵巧围巾');
INSERT INTO `item` VALUES ('100100349', '龙鳞灵巧围巾');
INSERT INTO `item` VALUES ('100100350', '辉光灵巧围巾');
INSERT INTO `item` VALUES ('100100351', '风化游侠头巾');
INSERT INTO `item` VALUES ('100100352', '麻制游侠头巾');
INSERT INTO `item` VALUES ('100100353', '棉制游侠头巾');
INSERT INTO `item` VALUES ('100100354', '丝绸游侠头巾');
INSERT INTO `item` VALUES ('100100355', '符文游侠头巾');
INSERT INTO `item` VALUES ('100100356', '梦幻游侠头巾');
INSERT INTO `item` VALUES ('100100357', '辉光游侠头巾');
INSERT INTO `item` VALUES ('100100358', '风化巫术之帽');
INSERT INTO `item` VALUES ('100100359', '麻制巫术之帽');
INSERT INTO `item` VALUES ('100100360', '棉制巫术之帽');
INSERT INTO `item` VALUES ('100100361', '丝绸巫术之帽');
INSERT INTO `item` VALUES ('100100362', '符文巫术之帽');
INSERT INTO `item` VALUES ('100100363', '梦幻巫术之帽');
INSERT INTO `item` VALUES ('100100364', '辉光巫术之帽');
INSERT INTO `item` VALUES ('100100365', '风化三角帽');
INSERT INTO `item` VALUES ('100100366', '麻制三角帽');
INSERT INTO `item` VALUES ('100100367', '棉制三角帽');
INSERT INTO `item` VALUES ('100100368', '丝绸三角帽');
INSERT INTO `item` VALUES ('100100369', '符文三角帽');
INSERT INTO `item` VALUES ('100100370', '梦幻三角帽');
INSERT INTO `item` VALUES ('100100371', '辉光三角帽');
INSERT INTO `item` VALUES ('100100372', '风化贤者之帽');
INSERT INTO `item` VALUES ('100100373', '麻制贤者之帽');
INSERT INTO `item` VALUES ('100100374', '棉制贤者之帽');
INSERT INTO `item` VALUES ('100100375', '丝绸贤者之帽');
INSERT INTO `item` VALUES ('100100376', '符文贤者之帽');
INSERT INTO `item` VALUES ('100100377', '梦幻贤者之帽');
INSERT INTO `item` VALUES ('100100378', '辉光贤者之帽');
INSERT INTO `item` VALUES ('100100379', '风化宽边帽');
INSERT INTO `item` VALUES ('100100380', '麻制宽边帽');
INSERT INTO `item` VALUES ('100100381', '棉制宽边帽');
INSERT INTO `item` VALUES ('100100382', '丝绸宽边帽');
INSERT INTO `item` VALUES ('100100383', '符文宽边帽');
INSERT INTO `item` VALUES ('100100384', '梦幻宽边帽');
INSERT INTO `item` VALUES ('100100385', '辉光宽边帽');
INSERT INTO `item` VALUES ('100100386', '风化贵族花边帽');
INSERT INTO `item` VALUES ('100100387', '麻制贵族花边帽');
INSERT INTO `item` VALUES ('100100388', '棉制贵族花边帽');
INSERT INTO `item` VALUES ('100100389', '丝绸贵族花边帽');
INSERT INTO `item` VALUES ('100100390', '符文贵族花边帽');
INSERT INTO `item` VALUES ('100100391', '梦幻贵族花边帽');
INSERT INTO `item` VALUES ('100100392', '辉光贵族花边帽');
INSERT INTO `item` VALUES ('100100393', '风化重铠');
INSERT INTO `item` VALUES ('100100394', '白钢重铠');
INSERT INTO `item` VALUES ('100100395', '蓝铜重铠');
INSERT INTO `item` VALUES ('100100396', '真银重铠');
INSERT INTO `item` VALUES ('100100397', '黑金重铠');
INSERT INTO `item` VALUES ('100100398', '黑曜石重铠');
INSERT INTO `item` VALUES ('100100399', '辉光重铠');
INSERT INTO `item` VALUES ('100100400', '风化锁子甲');
INSERT INTO `item` VALUES ('100100401', '白钢锁子甲');
INSERT INTO `item` VALUES ('100100402', '蓝铜锁子甲');
INSERT INTO `item` VALUES ('100100403', '真银锁子甲');
INSERT INTO `item` VALUES ('100100404', '黑金锁子甲');
INSERT INTO `item` VALUES ('100100405', '黑曜石锁子甲');
INSERT INTO `item` VALUES ('100100406', '辉光锁子甲');
INSERT INTO `item` VALUES ('100100407', '风化战铠');
INSERT INTO `item` VALUES ('100100408', '白钢战铠');
INSERT INTO `item` VALUES ('100100409', '蓝铜战铠');
INSERT INTO `item` VALUES ('100100410', '真银战铠');
INSERT INTO `item` VALUES ('100100411', '黑金战铠');
INSERT INTO `item` VALUES ('100100412', '黑曜石战铠');
INSERT INTO `item` VALUES ('100100413', '辉光战铠');
INSERT INTO `item` VALUES ('100100414', '风化鳞铠');
INSERT INTO `item` VALUES ('100100415', '白钢鳞铠');
INSERT INTO `item` VALUES ('100100416', '蓝铜鳞铠');
INSERT INTO `item` VALUES ('100100417', '真银鳞铠');
INSERT INTO `item` VALUES ('100100418', '黑金鳞铠');
INSERT INTO `item` VALUES ('100100419', '黑曜石鳞铠');
INSERT INTO `item` VALUES ('100100420', '辉光鳞铠');
INSERT INTO `item` VALUES ('100100421', '风化游侠斗篷');
INSERT INTO `item` VALUES ('100100422', '硬革游侠斗篷');
INSERT INTO `item` VALUES ('100100423', '蛇鳞游侠斗篷');
INSERT INTO `item` VALUES ('100100424', '刃狼游侠斗篷');
INSERT INTO `item` VALUES ('100100425', '雪熊游侠斗篷');
INSERT INTO `item` VALUES ('100100426', '龙鳞游侠斗篷');
INSERT INTO `item` VALUES ('100100427', '辉光游侠斗篷');
INSERT INTO `item` VALUES ('100100428', '风化贤者披肩');
INSERT INTO `item` VALUES ('100100429', '硬革贤者披肩');
INSERT INTO `item` VALUES ('100100430', '蛇鳞贤者披肩');
INSERT INTO `item` VALUES ('100100431', '刃狼贤者披肩');
INSERT INTO `item` VALUES ('100100432', '雪熊贤者披肩');
INSERT INTO `item` VALUES ('100100433', '龙鳞贤者披肩');
INSERT INTO `item` VALUES ('100100434', '辉光贤者披肩');
INSERT INTO `item` VALUES ('100100435', '风化紧身衣');
INSERT INTO `item` VALUES ('100100436', '硬革紧身衣');
INSERT INTO `item` VALUES ('100100437', '蛇鳞紧身衣');
INSERT INTO `item` VALUES ('100100438', '刃狼紧身衣');
INSERT INTO `item` VALUES ('100100439', '雪熊紧身衣');
INSERT INTO `item` VALUES ('100100440', '龙鳞紧身衣');
INSERT INTO `item` VALUES ('100100441', '辉光紧身衣');
INSERT INTO `item` VALUES ('100100442', '风化披风');
INSERT INTO `item` VALUES ('100100443', '硬革披风');
INSERT INTO `item` VALUES ('100100444', '蛇鳞披风');
INSERT INTO `item` VALUES ('100100445', '刃狼披风');
INSERT INTO `item` VALUES ('100100446', '雪熊披风');
INSERT INTO `item` VALUES ('100100447', '龙鳞披风');
INSERT INTO `item` VALUES ('100100448', '辉光披风');
INSERT INTO `item` VALUES ('100100449', '风化袖罩衣');
INSERT INTO `item` VALUES ('100100450', '麻布袖罩衣');
INSERT INTO `item` VALUES ('100100451', '棉布袖罩衣');
INSERT INTO `item` VALUES ('100100452', '丝绸袖罩衣');
INSERT INTO `item` VALUES ('100100453', '符文袖罩衣');
INSERT INTO `item` VALUES ('100100454', '梦幻袖罩衣');
INSERT INTO `item` VALUES ('100100455', '辉光袖罩衣');
INSERT INTO `item` VALUES ('100100456', '风化长袍');
INSERT INTO `item` VALUES ('100100457', '麻布长袍');
INSERT INTO `item` VALUES ('100100458', '棉布长袍');
INSERT INTO `item` VALUES ('100100459', '丝绸长袍');
INSERT INTO `item` VALUES ('100100460', '符文长袍');
INSERT INTO `item` VALUES ('100100461', '梦幻长袍');
INSERT INTO `item` VALUES ('100100462', '辉光长袍');
INSERT INTO `item` VALUES ('100100463', '风化厚袍');
INSERT INTO `item` VALUES ('100100464', '麻布厚袍');
INSERT INTO `item` VALUES ('100100465', '棉布厚袍');
INSERT INTO `item` VALUES ('100100466', '丝绸厚袍');
INSERT INTO `item` VALUES ('100100467', '符文厚袍');
INSERT INTO `item` VALUES ('100100468', '梦幻厚袍');
INSERT INTO `item` VALUES ('100100469', '辉光厚袍');
INSERT INTO `item` VALUES ('100100470', '风化短袍');
INSERT INTO `item` VALUES ('100100471', '麻布短袍');
INSERT INTO `item` VALUES ('100100472', '棉布短袍');
INSERT INTO `item` VALUES ('100100473', '丝绸短袍');
INSERT INTO `item` VALUES ('100100474', '符文短袍');
INSERT INTO `item` VALUES ('100100475', '梦幻短袍');
INSERT INTO `item` VALUES ('100100476', '辉光短袍');
INSERT INTO `item` VALUES ('100100477', '风化战靴');
INSERT INTO `item` VALUES ('100100478', '白钢战靴');
INSERT INTO `item` VALUES ('100100479', '蓝铜战靴');
INSERT INTO `item` VALUES ('100100480', '真银战靴');
INSERT INTO `item` VALUES ('100100481', '黑金战靴');
INSERT INTO `item` VALUES ('100100482', '黑曜石战靴');
INSERT INTO `item` VALUES ('100100483', '辉光战靴');
INSERT INTO `item` VALUES ('100100484', '风化胫甲');
INSERT INTO `item` VALUES ('100100485', '白钢胫甲');
INSERT INTO `item` VALUES ('100100486', '蓝铜胫甲');
INSERT INTO `item` VALUES ('100100487', '真银胫甲');
INSERT INTO `item` VALUES ('100100488', '黑金胫甲');
INSERT INTO `item` VALUES ('100100489', '黑曜石胫甲');
INSERT INTO `item` VALUES ('100100490', '辉光胫甲');
INSERT INTO `item` VALUES ('100100491', '风化锁甲靴');
INSERT INTO `item` VALUES ('100100492', '白钢锁甲靴');
INSERT INTO `item` VALUES ('100100493', '蓝铜锁甲靴');
INSERT INTO `item` VALUES ('100100494', '真银锁甲靴');
INSERT INTO `item` VALUES ('100100495', '黑金锁甲靴');
INSERT INTO `item` VALUES ('100100496', '黑曜石锁甲靴');
INSERT INTO `item` VALUES ('100100497', '辉光锁甲靴');
INSERT INTO `item` VALUES ('100100498', '风化铠靴');
INSERT INTO `item` VALUES ('100100499', '白钢铠靴');
INSERT INTO `item` VALUES ('100100500', '蓝铜铠靴');
INSERT INTO `item` VALUES ('100100501', '真银铠靴');
INSERT INTO `item` VALUES ('100100502', '黑金铠靴');
INSERT INTO `item` VALUES ('100100503', '黑曜石铠靴');
INSERT INTO `item` VALUES ('100100504', '辉光铠靴');
INSERT INTO `item` VALUES ('100100505', '风化长靴');
INSERT INTO `item` VALUES ('100100506', '硬革长靴');
INSERT INTO `item` VALUES ('100100507', '蛇鳞长靴');
INSERT INTO `item` VALUES ('100100508', '刃狼长靴');
INSERT INTO `item` VALUES ('100100509', '雪熊长靴');
INSERT INTO `item` VALUES ('100100510', '龙鳞长靴');
INSERT INTO `item` VALUES ('100100511', '辉光长靴');
INSERT INTO `item` VALUES ('100100512', '风化高筒靴');
INSERT INTO `item` VALUES ('100100513', '硬革高筒靴');
INSERT INTO `item` VALUES ('100100514', '蛇鳞高筒靴');
INSERT INTO `item` VALUES ('100100515', '刃狼高筒靴');
INSERT INTO `item` VALUES ('100100516', '雪熊高筒靴');
INSERT INTO `item` VALUES ('100100517', '龙鳞高筒靴');
INSERT INTO `item` VALUES ('100100518', '辉光高筒靴');
INSERT INTO `item` VALUES ('100100519', '风化狩猎靴');
INSERT INTO `item` VALUES ('100100520', '硬革狩猎靴');
INSERT INTO `item` VALUES ('100100521', '蛇鳞狩猎靴');
INSERT INTO `item` VALUES ('100100522', '刃狼狩猎靴');
INSERT INTO `item` VALUES ('100100523', '雪熊狩猎靴');
INSERT INTO `item` VALUES ('100100524', '龙鳞狩猎靴');
INSERT INTO `item` VALUES ('100100525', '辉光狩猎靴');
INSERT INTO `item` VALUES ('100100526', '风化尖头鞋');
INSERT INTO `item` VALUES ('100100527', '硬革尖头鞋');
INSERT INTO `item` VALUES ('100100528', '蛇鳞尖头鞋');
INSERT INTO `item` VALUES ('100100529', '刃狼尖头鞋');
INSERT INTO `item` VALUES ('100100530', '雪熊尖头鞋');
INSERT INTO `item` VALUES ('100100531', '龙鳞尖头鞋');
INSERT INTO `item` VALUES ('100100532', '辉光尖头鞋');
INSERT INTO `item` VALUES ('100100533', '风化短靴');
INSERT INTO `item` VALUES ('100100534', '麻布短靴');
INSERT INTO `item` VALUES ('100100535', '棉布短靴');
INSERT INTO `item` VALUES ('100100536', '丝绸短靴');
INSERT INTO `item` VALUES ('100100537', '符文短靴');
INSERT INTO `item` VALUES ('100100538', '梦幻短靴');
INSERT INTO `item` VALUES ('100100539', '辉光短靴');
INSERT INTO `item` VALUES ('100100540', '风化礼鞋');
INSERT INTO `item` VALUES ('100100541', '麻布礼鞋');
INSERT INTO `item` VALUES ('100100542', '棉布礼鞋');
INSERT INTO `item` VALUES ('100100543', '丝绸礼鞋');
INSERT INTO `item` VALUES ('100100544', '符文礼鞋');
INSERT INTO `item` VALUES ('100100545', '梦幻礼鞋');
INSERT INTO `item` VALUES ('100100546', '辉光礼鞋');
INSERT INTO `item` VALUES ('100100547', '风化凉鞋');
INSERT INTO `item` VALUES ('100100548', '麻布凉鞋');
INSERT INTO `item` VALUES ('100100549', '棉布凉鞋');
INSERT INTO `item` VALUES ('100100550', '丝绸凉鞋');
INSERT INTO `item` VALUES ('100100551', '符文凉鞋');
INSERT INTO `item` VALUES ('100100552', '梦幻凉鞋');
INSERT INTO `item` VALUES ('100100553', '辉光凉鞋');
INSERT INTO `item` VALUES ('100100554', '风化拖靴');
INSERT INTO `item` VALUES ('100100555', '麻布拖靴');
INSERT INTO `item` VALUES ('100100556', '棉布拖靴');
INSERT INTO `item` VALUES ('100100557', '丝绸拖靴');
INSERT INTO `item` VALUES ('100100558', '符文拖靴');
INSERT INTO `item` VALUES ('100100559', '梦幻拖靴');
INSERT INTO `item` VALUES ('100100560', '辉光拖靴');
INSERT INTO `item` VALUES ('100100561', '召唤卷·指挥官');
INSERT INTO `item` VALUES ('100100562', '结霜圣诞帽');
INSERT INTO `item` VALUES ('100100563', '冬幕圣诞袜');
INSERT INTO `item` VALUES ('100100564', '神奇气球');
INSERT INTO `item` VALUES ('100100565', '欢乐彩带');
INSERT INTO `item` VALUES ('100100566', '大红灯笼');
INSERT INTO `item` VALUES ('100100567', '喜庆鞭炮');
INSERT INTO `item` VALUES ('100100568', '鲜艳玫瑰');
INSERT INTO `item` VALUES ('100100569', '甜蜜巧克力');
INSERT INTO `item` VALUES ('100200003', '测试金币礼包');
INSERT INTO `item` VALUES ('100200004', '测试行动力药水');
INSERT INTO `item` VALUES ('100200005', '低级经验药水');
INSERT INTO `item` VALUES ('100200006', '中级经验药水');
INSERT INTO `item` VALUES ('100200007', '高级经验药水');
INSERT INTO `item` VALUES ('100300001', '改名卡');
INSERT INTO `item` VALUES ('100300002', '抽取测试道具（白）-1');
INSERT INTO `item` VALUES ('100300003', '抽取测试道具（白）-2');
INSERT INTO `item` VALUES ('100300004', '抽取测试道具（白）-3');
INSERT INTO `item` VALUES ('100300005', '抽取测试道具（白）-4');
INSERT INTO `item` VALUES ('100300006', '抽取测试道具（白）-5');
INSERT INTO `item` VALUES ('100300007', '抽取测试道具（绿）-1');
INSERT INTO `item` VALUES ('100300008', '抽取测试道具（绿）-2');
INSERT INTO `item` VALUES ('100300009', '抽取测试道具（绿）-3');
INSERT INTO `item` VALUES ('100300010', '抽取测试道具（绿）-4');
INSERT INTO `item` VALUES ('100300011', '抽取测试道具（绿）-5');
INSERT INTO `item` VALUES ('100300012', '抽取测试道具（蓝）-1');
INSERT INTO `item` VALUES ('100300013', '抽取测试道具（蓝）-2');
INSERT INTO `item` VALUES ('100300014', '抽取测试道具（蓝）-3');
INSERT INTO `item` VALUES ('100300015', '抽取测试道具（蓝）-4');
INSERT INTO `item` VALUES ('100300016', '抽取测试道具（蓝）-5');
INSERT INTO `item` VALUES ('100300017', '抽取测试道具（紫）-1');
INSERT INTO `item` VALUES ('100300018', '抽取测试道具（紫）-2');
INSERT INTO `item` VALUES ('100300019', '抽取测试道具（紫）-3');
INSERT INTO `item` VALUES ('100300020', '抽取测试道具（紫）-4');
INSERT INTO `item` VALUES ('100300021', '抽取测试道具（紫）-5');
INSERT INTO `item` VALUES ('100300022', '抽取测试道具（橙）-1');
INSERT INTO `item` VALUES ('100300023', '抽取测试道具（橙）-2');
INSERT INTO `item` VALUES ('100300024', '抽取测试道具（橙）-3');
INSERT INTO `item` VALUES ('100300025', '抽取测试道具（橙）-4');
INSERT INTO `item` VALUES ('100300026', '抽取测试道具（橙）-5');
INSERT INTO `item` VALUES ('100300027', '一袋神秘的货物');
INSERT INTO `item` VALUES ('100300028', '一袋神秘的货物');
INSERT INTO `item` VALUES ('100300029', '一袋神秘的货物');
INSERT INTO `item` VALUES ('100300030', '一袋神秘的货物');
INSERT INTO `item` VALUES ('100300031', '一袋神秘的货物');
INSERT INTO `item` VALUES ('100300032', '附魔之铸铁矿');
INSERT INTO `item` VALUES ('100300033', '附魔之青铜矿');
INSERT INTO `item` VALUES ('100300034', '附魔之秘银矿');
INSERT INTO `item` VALUES ('100300035', '附魔之魔晶矿');
INSERT INTO `item` VALUES ('100300036', '附魔之陨星矿');
INSERT INTO `item` VALUES ('100300037', '附魔之辉光矿');
INSERT INTO `item` VALUES ('100300038', '淬火之铸铁矿');
INSERT INTO `item` VALUES ('100300039', '淬火之青铜矿');
INSERT INTO `item` VALUES ('100300040', '淬火之秘银矿');
INSERT INTO `item` VALUES ('100300041', '淬火之魔晶矿');
INSERT INTO `item` VALUES ('100300042', '淬火之陨星矿');
INSERT INTO `item` VALUES ('100300043', '淬火之辉光矿');
INSERT INTO `item` VALUES ('100300044', '暗淡之力量宝石');
INSERT INTO `item` VALUES ('100300045', '柔和之力量宝石');
INSERT INTO `item` VALUES ('100300046', '明澈之力量宝石');
INSERT INTO `item` VALUES ('100300047', '闪烁之力量宝石');
INSERT INTO `item` VALUES ('100300048', '闪耀之力量宝石');
INSERT INTO `item` VALUES ('100300049', '暗淡之魔法宝石');
INSERT INTO `item` VALUES ('100300050', '柔和之魔法宝石');
INSERT INTO `item` VALUES ('100300051', '明澈之魔法宝石');
INSERT INTO `item` VALUES ('100300052', '闪烁之魔法宝石');
INSERT INTO `item` VALUES ('100300053', '闪耀之魔法宝石');
INSERT INTO `item` VALUES ('100300054', '暗淡之防御宝石');
INSERT INTO `item` VALUES ('100300055', '柔和之防御宝石');
INSERT INTO `item` VALUES ('100300056', '明澈之防御宝石');
INSERT INTO `item` VALUES ('100300057', '闪烁之防御宝石');
INSERT INTO `item` VALUES ('100300058', '闪耀之防御宝石');
INSERT INTO `item` VALUES ('100300059', '暗淡之辅助宝石');
INSERT INTO `item` VALUES ('100300060', '柔和之辅助宝石');
INSERT INTO `item` VALUES ('100300061', '明澈之辅助宝石');
INSERT INTO `item` VALUES ('100300062', '闪烁之辅助宝石');
INSERT INTO `item` VALUES ('100300063', '闪耀之辅助宝石');
INSERT INTO `item` VALUES ('100300064', '白钢之锭');
INSERT INTO `item` VALUES ('100300065', '蓝铜之锭');
INSERT INTO `item` VALUES ('100300066', '真银之锭');
INSERT INTO `item` VALUES ('100300067', '黑金之锭');
INSERT INTO `item` VALUES ('100300068', '黑耀石之锭');
INSERT INTO `item` VALUES ('100300069', '神秘光芒之锭');
INSERT INTO `item` VALUES ('100300070', '坚硬之革');
INSERT INTO `item` VALUES ('100300071', '巨蟒之鳞');
INSERT INTO `item` VALUES ('100300072', '刃狼之革');
INSERT INTO `item` VALUES ('100300073', '雪熊之皮');
INSERT INTO `item` VALUES ('100300074', '魔龙之鳞');
INSERT INTO `item` VALUES ('100300075', '神秘光芒之羽');
INSERT INTO `item` VALUES ('100300076', '粗糙麻布');
INSERT INTO `item` VALUES ('100300077', '厚实棉布');
INSERT INTO `item` VALUES ('100300078', '细腻丝绸');
INSERT INTO `item` VALUES ('100300079', '神秘符文布');
INSERT INTO `item` VALUES ('100300080', '无尽梦幻布');
INSERT INTO `item` VALUES ('100300081', '神秘光芒之布');
INSERT INTO `item` VALUES ('100300082', '学徒之头盔模具');
INSERT INTO `item` VALUES ('100300083', '工匠之头盔模具');
INSERT INTO `item` VALUES ('100300084', '巧匠之头盔模具');
INSERT INTO `item` VALUES ('100300085', '大师之头盔模具');
INSERT INTO `item` VALUES ('100300086', '宗师之头盔模具');
INSERT INTO `item` VALUES ('100300087', '学徒之内衬');
INSERT INTO `item` VALUES ('100300088', '工匠之内衬');
INSERT INTO `item` VALUES ('100300089', '巧匠之内衬');
INSERT INTO `item` VALUES ('100300090', '大师之内衬');
INSERT INTO `item` VALUES ('100300091', '宗师之内衬');
INSERT INTO `item` VALUES ('100300092', '学徒之靴面');
INSERT INTO `item` VALUES ('100300093', '工匠之靴面');
INSERT INTO `item` VALUES ('100300094', '巧匠之靴面');
INSERT INTO `item` VALUES ('100300095', '大师之靴面');
INSERT INTO `item` VALUES ('100300096', '宗师之靴面');
INSERT INTO `item` VALUES ('100300097', '神秘猫眼石');
INSERT INTO `item` VALUES ('100300098', '泛光孔雀石');
INSERT INTO `item` VALUES ('100300099', '完整珊瑚丛');
INSERT INTO `item` VALUES ('100300100', '无暇水晶簇');
INSERT INTO `item` VALUES ('100300101', '闪亮的钻石');
INSERT INTO `item` VALUES ('100300102', '圣洁辉光石');
INSERT INTO `item` VALUES ('100300103', '学徒之圆环');
INSERT INTO `item` VALUES ('100300104', '工匠之圆环');
INSERT INTO `item` VALUES ('100300105', '巧匠之圆环');
INSERT INTO `item` VALUES ('100300106', '大师之圆环');
INSERT INTO `item` VALUES ('100300107', '宗师之圆环');
INSERT INTO `item` VALUES ('100300108', '学徒之底座');
INSERT INTO `item` VALUES ('100300109', '工匠之底座');
INSERT INTO `item` VALUES ('100300110', '巧匠之底座');
INSERT INTO `item` VALUES ('100300111', '大师之底座');
INSERT INTO `item` VALUES ('100300112', '宗师之底座');
INSERT INTO `item` VALUES ('100300113', '图纸—秘银双刃斧');
INSERT INTO `item` VALUES ('100300114', '图纸—魔晶长刃斧');
INSERT INTO `item` VALUES ('100300115', '图纸—陨星长刃斧');
INSERT INTO `item` VALUES ('100300116', '图纸—尘封之斧');
INSERT INTO `item` VALUES ('100300117', '图纸—秘银宽刃剑');
INSERT INTO `item` VALUES ('100300118', '图纸—魔晶双刃剑');
INSERT INTO `item` VALUES ('100300119', '图纸—陨星双刃剑');
INSERT INTO `item` VALUES ('100300120', '图纸—尘封之剑');
INSERT INTO `item` VALUES ('100300121', '图纸—秘银秘典');
INSERT INTO `item` VALUES ('100300122', '图纸—魔晶圣典');
INSERT INTO `item` VALUES ('100300123', '图纸—陨星圣典');
INSERT INTO `item` VALUES ('100300124', '图纸—尘封之书');
INSERT INTO `item` VALUES ('100300125', '图纸—秘银长杖');
INSERT INTO `item` VALUES ('100300126', '图纸—魔晶魔杖');
INSERT INTO `item` VALUES ('100300127', '图纸—陨星魔杖');
INSERT INTO `item` VALUES ('100300128', '图纸—尘封之杖');
INSERT INTO `item` VALUES ('100300129', '图纸—秘银重盾');
INSERT INTO `item` VALUES ('100300130', '图纸—魔晶鸢盾');
INSERT INTO `item` VALUES ('100300131', '图纸—陨星鸢盾');
INSERT INTO `item` VALUES ('100300132', '图纸—尘封之剑盾');
INSERT INTO `item` VALUES ('100300133', '图纸—秘银骑士枪');
INSERT INTO `item` VALUES ('100300134', '图纸—魔晶征服枪');
INSERT INTO `item` VALUES ('100300135', '图纸—陨星征服枪');
INSERT INTO `item` VALUES ('100300136', '图纸—尘封之枪');
INSERT INTO `item` VALUES ('100300137', '图纸—秘银战戟');
INSERT INTO `item` VALUES ('100300138', '图纸—魔晶三叉戟');
INSERT INTO `item` VALUES ('100300139', '图纸—陨星三叉戟');
INSERT INTO `item` VALUES ('100300140', '图纸—尘封之戟');
INSERT INTO `item` VALUES ('100300141', '图纸—秘银长枪');
INSERT INTO `item` VALUES ('100300142', '图纸—魔晶战戈');
INSERT INTO `item` VALUES ('100300143', '图纸—陨星战戈');
INSERT INTO `item` VALUES ('100300144', '图纸—尘封之矛');
INSERT INTO `item` VALUES ('100300145', '图纸—秘银长弓');
INSERT INTO `item` VALUES ('100300146', '图纸—魔晶战斗弓');
INSERT INTO `item` VALUES ('100300147', '图纸—陨星战斗弓');
INSERT INTO `item` VALUES ('100300148', '图纸—尘封之弓');
INSERT INTO `item` VALUES ('100300149', '图纸—秘银护拳');
INSERT INTO `item` VALUES ('100300150', '图纸—魔晶重拳');
INSERT INTO `item` VALUES ('100300151', '图纸—陨星重拳');
INSERT INTO `item` VALUES ('100300152', '图纸—尘封之拳');
INSERT INTO `item` VALUES ('100300153', '图纸—镶银水晶球');
INSERT INTO `item` VALUES ('100300154', '图纸—魔晶魔导器');
INSERT INTO `item` VALUES ('100300155', '图纸—陨星魔导器');
INSERT INTO `item` VALUES ('100300156', '图纸—尘封之器');
INSERT INTO `item` VALUES ('100300157', '图纸—秘银骑兵对剑');
INSERT INTO `item` VALUES ('100300158', '图纸—魔晶贵族对剑');
INSERT INTO `item` VALUES ('100300159', '图纸—陨星贵族对剑');
INSERT INTO `item` VALUES ('100300160', '图纸—尘封之对剑');
INSERT INTO `item` VALUES ('100300161', '图纸—银环丝带');
INSERT INTO `item` VALUES ('100300162', '图纸—魔晶缎带');
INSERT INTO `item` VALUES ('100300163', '图纸—陨星缎带');
INSERT INTO `item` VALUES ('100300164', '图纸—尘封之带');
INSERT INTO `item` VALUES ('100300165', '图纸—秘银太刀');
INSERT INTO `item` VALUES ('100300166', '图纸—魔晶野太刀');
INSERT INTO `item` VALUES ('100300167', '图纸—陨星野太刀');
INSERT INTO `item` VALUES ('100300168', '图纸—尘封之太刀');
INSERT INTO `item` VALUES ('100300169', '图纸—秘银胁差');
INSERT INTO `item` VALUES ('100300170', '图纸—魔晶忍刃');
INSERT INTO `item` VALUES ('100300171', '图纸—陨星忍刃');
INSERT INTO `item` VALUES ('100300172', '图纸—尘封之忍刀');
INSERT INTO `item` VALUES ('100300173', '图纸—镶银曼陀林');
INSERT INTO `item` VALUES ('100300174', '图纸—魔晶西奥伯琴');
INSERT INTO `item` VALUES ('100300175', '图纸—陨星西奥伯琴');
INSERT INTO `item` VALUES ('100300176', '图纸—尘封之琴');
INSERT INTO `item` VALUES ('100300177', '图纸—秘银短匕');
INSERT INTO `item` VALUES ('100300178', '图纸—魔晶大马士革刀');
INSERT INTO `item` VALUES ('100300179', '图纸—陨星大马士革刀');
INSERT INTO `item` VALUES ('100300180', '图纸—尘封之匕');
INSERT INTO `item` VALUES ('100300181', '图纸—秘银巨镰');
INSERT INTO `item` VALUES ('100300182', '图纸—魔晶魔镰');
INSERT INTO `item` VALUES ('100300183', '图纸—陨星魔镰');
INSERT INTO `item` VALUES ('100300184', '图纸—尘封之镰');
INSERT INTO `item` VALUES ('100300185', '图纸—秘银袖剑');
INSERT INTO `item` VALUES ('100300186', '图纸—魔晶刺刃');
INSERT INTO `item` VALUES ('100300187', '图纸—陨星刺刃');
INSERT INTO `item` VALUES ('100300188', '图纸—尘封之短剑');
INSERT INTO `item` VALUES ('100300189', '图纸—秘银带柄弯刀');
INSERT INTO `item` VALUES ('100300190', '图纸—魔晶仪式佩剑');
INSERT INTO `item` VALUES ('100300191', '图纸—陨星仪式佩剑');
INSERT INTO `item` VALUES ('100300192', '图纸—尘封之佩剑');
INSERT INTO `item` VALUES ('100300193', '图纸—银纹符咒');
INSERT INTO `item` VALUES ('100300194', '图纸—魔晶降魔符');
INSERT INTO `item` VALUES ('100300195', '图纸—陨星降魔符');
INSERT INTO `item` VALUES ('100300196', '图纸—尘封之符');
INSERT INTO `item` VALUES ('100300197', '图纸—珊瑚之戒·强攻');
INSERT INTO `item` VALUES ('100300198', '图纸—水晶之戒·强攻');
INSERT INTO `item` VALUES ('100300199', '图纸—钻石之戒·强攻');
INSERT INTO `item` VALUES ('100300200', '图纸—辉光之戒·强攻');
INSERT INTO `item` VALUES ('100300201', '图纸—珊瑚之戒·魔能');
INSERT INTO `item` VALUES ('100300202', '图纸—水晶之戒·魔能');
INSERT INTO `item` VALUES ('100300203', '图纸—钻石之戒·魔能');
INSERT INTO `item` VALUES ('100300204', '图纸—辉光之戒·魔能');
INSERT INTO `item` VALUES ('100300205', '图纸—珊瑚之戒·坚守');
INSERT INTO `item` VALUES ('100300206', '图纸—水晶之戒·坚守');
INSERT INTO `item` VALUES ('100300207', '图纸—钻石之戒·坚守');
INSERT INTO `item` VALUES ('100300208', '图纸—辉光之戒·坚守');
INSERT INTO `item` VALUES ('100300209', '图纸—珊瑚之戒·驭魔');
INSERT INTO `item` VALUES ('100300210', '图纸—水晶之戒·驭魔');
INSERT INTO `item` VALUES ('100300211', '图纸—钻石之戒·驭魔');
INSERT INTO `item` VALUES ('100300212', '图纸—辉光之戒·驭魔');
INSERT INTO `item` VALUES ('100300213', '图纸—珊瑚之戒·光速');
INSERT INTO `item` VALUES ('100300214', '图纸—水晶之戒·光速');
INSERT INTO `item` VALUES ('100300215', '图纸—钻石之戒·光速');
INSERT INTO `item` VALUES ('100300216', '图纸—辉光之戒·光速');
INSERT INTO `item` VALUES ('100300217', '图纸—珊瑚之戒·强运');
INSERT INTO `item` VALUES ('100300218', '图纸—水晶之戒·强运');
INSERT INTO `item` VALUES ('100300219', '图纸—钻石之戒·强运');
INSERT INTO `item` VALUES ('100300220', '图纸—辉光之戒·强运');
INSERT INTO `item` VALUES ('100300221', '图纸—珊瑚之戒·蓄能');
INSERT INTO `item` VALUES ('100300222', '图纸—水晶之戒·蓄能');
INSERT INTO `item` VALUES ('100300223', '图纸—钻石之戒·蓄能');
INSERT INTO `item` VALUES ('100300224', '图纸—辉光之戒·蓄能');
INSERT INTO `item` VALUES ('100300225', '图纸—珊瑚之戒·破甲');
INSERT INTO `item` VALUES ('100300226', '图纸—水晶之戒·破甲');
INSERT INTO `item` VALUES ('100300227', '图纸—钻石之戒·破甲');
INSERT INTO `item` VALUES ('100300228', '图纸—辉光之戒·破甲');
INSERT INTO `item` VALUES ('100300229', '图纸—珊瑚之戒·识破');
INSERT INTO `item` VALUES ('100300230', '图纸—水晶之戒·识破');
INSERT INTO `item` VALUES ('100300231', '图纸—钻石之戒·识破');
INSERT INTO `item` VALUES ('100300232', '图纸—辉光之戒·识破');
INSERT INTO `item` VALUES ('100300233', '图纸—珊瑚之戒·迅捷');
INSERT INTO `item` VALUES ('100300234', '图纸—水晶之戒·迅捷');
INSERT INTO `item` VALUES ('100300235', '图纸—钻石之戒·迅捷');
INSERT INTO `item` VALUES ('100300236', '图纸—辉光之戒·迅捷');
INSERT INTO `item` VALUES ('100300237', '图纸—珊瑚之戒·强固');
INSERT INTO `item` VALUES ('100300238', '图纸—水晶之戒·强固');
INSERT INTO `item` VALUES ('100300239', '图纸—钻石之戒·强固');
INSERT INTO `item` VALUES ('100300240', '图纸—辉光之戒·强固');
INSERT INTO `item` VALUES ('100300241', '图纸—珊瑚之戒·强命');
INSERT INTO `item` VALUES ('100300242', '图纸—水晶之戒·强命');
INSERT INTO `item` VALUES ('100300243', '图纸—钻石之戒·强命');
INSERT INTO `item` VALUES ('100300244', '图纸—辉光之戒·强命');
INSERT INTO `item` VALUES ('100300245', '图纸—珊瑚吊坠·蛮神');
INSERT INTO `item` VALUES ('100300246', '图纸—水晶吊坠·蛮神');
INSERT INTO `item` VALUES ('100300247', '图纸—钻石吊坠·蛮神');
INSERT INTO `item` VALUES ('100300248', '图纸—辉光吊坠·蛮神');
INSERT INTO `item` VALUES ('100300249', '图纸—珊瑚吊坠·神射');
INSERT INTO `item` VALUES ('100300250', '图纸—水晶吊坠·神射');
INSERT INTO `item` VALUES ('100300251', '图纸—钻石吊坠·神射');
INSERT INTO `item` VALUES ('100300252', '图纸—辉光吊坠·神射');
INSERT INTO `item` VALUES ('100300253', '图纸—珊瑚吊坠·超碾');
INSERT INTO `item` VALUES ('100300254', '图纸—水晶吊坠·超碾');
INSERT INTO `item` VALUES ('100300255', '图纸—钻石吊坠·超碾');
INSERT INTO `item` VALUES ('100300256', '图纸—辉光吊坠·超碾');
INSERT INTO `item` VALUES ('100300257', '图纸—珊瑚吊坠·灵猿');
INSERT INTO `item` VALUES ('100300258', '图纸—水晶吊坠·灵猿');
INSERT INTO `item` VALUES ('100300259', '图纸—钻石吊坠·灵猿');
INSERT INTO `item` VALUES ('100300260', '图纸—辉光吊坠·灵猿');
INSERT INTO `item` VALUES ('100300261', '图纸—珊瑚吊坠·巨神');
INSERT INTO `item` VALUES ('100300262', '图纸—水晶吊坠·巨神');
INSERT INTO `item` VALUES ('100300263', '图纸—钻石吊坠·巨神');
INSERT INTO `item` VALUES ('100300264', '图纸—辉光吊坠·巨神');
INSERT INTO `item` VALUES ('100300265', '图纸—真银战斗重盔');
INSERT INTO `item` VALUES ('100300266', '图纸—黑金战斗重盔');
INSERT INTO `item` VALUES ('100300267', '图纸—黑曜石战斗重盔');
INSERT INTO `item` VALUES ('100300268', '图纸—辉光战斗重盔');
INSERT INTO `item` VALUES ('100300269', '图纸—真银魔法王冠');
INSERT INTO `item` VALUES ('100300270', '图纸—黑金魔法王冠');
INSERT INTO `item` VALUES ('100300271', '图纸—黑曜石魔法王冠');
INSERT INTO `item` VALUES ('100300272', '图纸—辉光魔法王冠');
INSERT INTO `item` VALUES ('100300273', '图纸—真银强固面甲');
INSERT INTO `item` VALUES ('100300274', '图纸—黑金强固面甲');
INSERT INTO `item` VALUES ('100300275', '图纸—黑曜石强固面甲');
INSERT INTO `item` VALUES ('100300276', '图纸—辉光强固面甲');
INSERT INTO `item` VALUES ('100300277', '图纸—真银御魔头冠');
INSERT INTO `item` VALUES ('100300278', '图纸—黑金御魔头冠');
INSERT INTO `item` VALUES ('100300279', '图纸—黑曜石御魔头冠');
INSERT INTO `item` VALUES ('100300280', '图纸—辉光御魔头冠');
INSERT INTO `item` VALUES ('100300281', '图纸—真银领主重盔');
INSERT INTO `item` VALUES ('100300282', '图纸—黑金领主重盔');
INSERT INTO `item` VALUES ('100300283', '图纸—黑曜石领主重盔');
INSERT INTO `item` VALUES ('100300284', '图纸—辉光领主重盔');
INSERT INTO `item` VALUES ('100300285', '图纸—真银骑士战盔');
INSERT INTO `item` VALUES ('100300286', '图纸—黑金骑士战盔');
INSERT INTO `item` VALUES ('100300287', '图纸—黑曜石骑士战盔');
INSERT INTO `item` VALUES ('100300288', '图纸—辉光骑士战盔');
INSERT INTO `item` VALUES ('100300289', '图纸—刃狼强袭骑盔');
INSERT INTO `item` VALUES ('100300290', '图纸—雪熊强袭骑盔');
INSERT INTO `item` VALUES ('100300291', '图纸—龙鳞强袭骑盔');
INSERT INTO `item` VALUES ('100300292', '图纸—辉光强袭骑盔');
INSERT INTO `item` VALUES ('100300293', '图纸—刃狼铭文兜帽');
INSERT INTO `item` VALUES ('100300294', '图纸—雪熊铭文兜帽');
INSERT INTO `item` VALUES ('100300295', '图纸—龙鳞铭文兜帽');
INSERT INTO `item` VALUES ('100300296', '图纸—辉光铭文兜帽');
INSERT INTO `item` VALUES ('100300297', '图纸—刃狼护目镜');
INSERT INTO `item` VALUES ('100300298', '图纸—雪熊护目镜');
INSERT INTO `item` VALUES ('100300299', '图纸—龙鳞护目镜');
INSERT INTO `item` VALUES ('100300300', '图纸—辉光护目镜');
INSERT INTO `item` VALUES ('100300301', '图纸—刃狼魔化面罩');
INSERT INTO `item` VALUES ('100300302', '图纸—雪熊魔化面罩');
INSERT INTO `item` VALUES ('100300303', '图纸—龙鳞魔化面罩');
INSERT INTO `item` VALUES ('100300304', '图纸—辉光魔化面罩');
INSERT INTO `item` VALUES ('100300305', '图纸—刃狼精制盔帽');
INSERT INTO `item` VALUES ('100300306', '图纸—雪熊精制盔帽');
INSERT INTO `item` VALUES ('100300307', '图纸—龙鳞精制盔帽');
INSERT INTO `item` VALUES ('100300308', '图纸—辉光精制盔帽');
INSERT INTO `item` VALUES ('100300309', '图纸—刃狼灵巧围巾');
INSERT INTO `item` VALUES ('100300310', '图纸—雪熊灵巧围巾');
INSERT INTO `item` VALUES ('100300311', '图纸—龙鳞灵巧围巾');
INSERT INTO `item` VALUES ('100300312', '图纸—辉光灵巧围巾');
INSERT INTO `item` VALUES ('100300313', '图纸—丝绸游侠头巾');
INSERT INTO `item` VALUES ('100300314', '图纸—符文游侠头巾');
INSERT INTO `item` VALUES ('100300315', '图纸—梦幻游侠头巾');
INSERT INTO `item` VALUES ('100300316', '图纸—辉光游侠头巾');
INSERT INTO `item` VALUES ('100300317', '图纸—丝绸巫术之帽');
INSERT INTO `item` VALUES ('100300318', '图纸—符文巫术之帽');
INSERT INTO `item` VALUES ('100300319', '图纸—梦幻巫术之帽');
INSERT INTO `item` VALUES ('100300320', '图纸—辉光巫术之帽');
INSERT INTO `item` VALUES ('100300321', '图纸—丝绸三角帽');
INSERT INTO `item` VALUES ('100300322', '图纸—符文三角帽');
INSERT INTO `item` VALUES ('100300323', '图纸—梦幻三角帽');
INSERT INTO `item` VALUES ('100300324', '图纸—辉光三角帽');
INSERT INTO `item` VALUES ('100300325', '图纸—丝绸贤者之帽');
INSERT INTO `item` VALUES ('100300326', '图纸—符文贤者之帽');
INSERT INTO `item` VALUES ('100300327', '图纸—梦幻贤者之帽');
INSERT INTO `item` VALUES ('100300328', '图纸—辉光贤者之帽');
INSERT INTO `item` VALUES ('100300329', '图纸—丝绸宽边帽');
INSERT INTO `item` VALUES ('100300330', '图纸—符文宽边帽');
INSERT INTO `item` VALUES ('100300331', '图纸—梦幻宽边帽');
INSERT INTO `item` VALUES ('100300332', '图纸—辉光宽边帽');
INSERT INTO `item` VALUES ('100300333', '图纸—丝绸贵族花边帽');
INSERT INTO `item` VALUES ('100300334', '图纸—符文贵族花边帽');
INSERT INTO `item` VALUES ('100300335', '图纸—梦幻贵族花边帽');
INSERT INTO `item` VALUES ('100300336', '图纸—辉光贵族花边帽');
INSERT INTO `item` VALUES ('100300337', '图纸—真银重铠');
INSERT INTO `item` VALUES ('100300338', '图纸—黑金重铠');
INSERT INTO `item` VALUES ('100300339', '图纸—黑曜石重铠');
INSERT INTO `item` VALUES ('100300340', '图纸—辉光重铠');
INSERT INTO `item` VALUES ('100300341', '图纸—真银锁子甲');
INSERT INTO `item` VALUES ('100300342', '图纸—黑金锁子甲');
INSERT INTO `item` VALUES ('100300343', '图纸—黑曜石锁子甲');
INSERT INTO `item` VALUES ('100300344', '图纸—辉光锁子甲');
INSERT INTO `item` VALUES ('100300345', '图纸—真银战铠');
INSERT INTO `item` VALUES ('100300346', '图纸—黑金战铠');
INSERT INTO `item` VALUES ('100300347', '图纸—黑曜石战铠');
INSERT INTO `item` VALUES ('100300348', '图纸—辉光战铠');
INSERT INTO `item` VALUES ('100300349', '图纸—真银鳞铠');
INSERT INTO `item` VALUES ('100300350', '图纸—黑金鳞铠');
INSERT INTO `item` VALUES ('100300351', '图纸—黑曜石鳞铠');
INSERT INTO `item` VALUES ('100300352', '图纸—辉光鳞铠');
INSERT INTO `item` VALUES ('100300353', '图纸—刃狼游侠斗篷');
INSERT INTO `item` VALUES ('100300354', '图纸—雪熊游侠斗篷');
INSERT INTO `item` VALUES ('100300355', '图纸—龙鳞游侠斗篷');
INSERT INTO `item` VALUES ('100300356', '图纸—辉光游侠斗篷');
INSERT INTO `item` VALUES ('100300357', '图纸—刃狼贤者披肩');
INSERT INTO `item` VALUES ('100300358', '图纸—雪熊贤者披肩');
INSERT INTO `item` VALUES ('100300359', '图纸—龙鳞贤者披肩');
INSERT INTO `item` VALUES ('100300360', '图纸—辉光贤者披肩');
INSERT INTO `item` VALUES ('100300361', '图纸—刃狼紧身衣');
INSERT INTO `item` VALUES ('100300362', '图纸—雪熊紧身衣');
INSERT INTO `item` VALUES ('100300363', '图纸—龙鳞紧身衣');
INSERT INTO `item` VALUES ('100300364', '图纸—辉光紧身衣');
INSERT INTO `item` VALUES ('100300365', '图纸—刃狼披风');
INSERT INTO `item` VALUES ('100300366', '图纸—雪熊披风');
INSERT INTO `item` VALUES ('100300367', '图纸—龙鳞披风');
INSERT INTO `item` VALUES ('100300368', '图纸—辉光披风');
INSERT INTO `item` VALUES ('100300369', '图纸—丝绸袖罩衣');
INSERT INTO `item` VALUES ('100300370', '图纸—符文袖罩衣');
INSERT INTO `item` VALUES ('100300371', '图纸—梦幻袖罩衣');
INSERT INTO `item` VALUES ('100300372', '图纸—辉光袖罩衣');
INSERT INTO `item` VALUES ('100300373', '图纸—丝绸长袍');
INSERT INTO `item` VALUES ('100300374', '图纸—符文长袍');
INSERT INTO `item` VALUES ('100300375', '图纸—梦幻长袍');
INSERT INTO `item` VALUES ('100300376', '图纸—辉光长袍');
INSERT INTO `item` VALUES ('100300377', '图纸—丝绸厚袍');
INSERT INTO `item` VALUES ('100300378', '图纸—符文厚袍');
INSERT INTO `item` VALUES ('100300379', '图纸—梦幻厚袍');
INSERT INTO `item` VALUES ('100300380', '图纸—辉光厚袍');
INSERT INTO `item` VALUES ('100300381', '图纸—丝绸短袍');
INSERT INTO `item` VALUES ('100300382', '图纸—符文短袍');
INSERT INTO `item` VALUES ('100300383', '图纸—梦幻短袍');
INSERT INTO `item` VALUES ('100300384', '图纸—辉光短袍');
INSERT INTO `item` VALUES ('100300385', '图纸—真银战靴');
INSERT INTO `item` VALUES ('100300386', '图纸—黑金战靴');
INSERT INTO `item` VALUES ('100300387', '图纸—黑曜石战靴');
INSERT INTO `item` VALUES ('100300388', '图纸—辉光战靴');
INSERT INTO `item` VALUES ('100300389', '图纸—真银胫甲');
INSERT INTO `item` VALUES ('100300390', '图纸—黑金胫甲');
INSERT INTO `item` VALUES ('100300391', '图纸—黑曜石胫甲');
INSERT INTO `item` VALUES ('100300392', '图纸—辉光胫甲');
INSERT INTO `item` VALUES ('100300393', '图纸—真银锁甲靴');
INSERT INTO `item` VALUES ('100300394', '图纸—黑金锁甲靴');
INSERT INTO `item` VALUES ('100300395', '图纸—黑曜石锁甲靴');
INSERT INTO `item` VALUES ('100300396', '图纸—辉光锁甲靴');
INSERT INTO `item` VALUES ('100300397', '图纸—真银铠靴');
INSERT INTO `item` VALUES ('100300398', '图纸—黑金铠靴');
INSERT INTO `item` VALUES ('100300399', '图纸—黑曜石铠靴');
INSERT INTO `item` VALUES ('100300400', '图纸—辉光铠靴');
INSERT INTO `item` VALUES ('100300401', '图纸—刃狼长靴');
INSERT INTO `item` VALUES ('100300402', '图纸—雪熊长靴');
INSERT INTO `item` VALUES ('100300403', '图纸—龙鳞长靴');
INSERT INTO `item` VALUES ('100300404', '图纸—辉光长靴');
INSERT INTO `item` VALUES ('100300405', '图纸—刃狼高筒靴');
INSERT INTO `item` VALUES ('100300406', '图纸—雪熊高筒靴');
INSERT INTO `item` VALUES ('100300407', '图纸—龙鳞高筒靴');
INSERT INTO `item` VALUES ('100300408', '图纸—辉光高筒靴');
INSERT INTO `item` VALUES ('100300409', '图纸—刃狼狩猎靴');
INSERT INTO `item` VALUES ('100300410', '图纸—雪熊狩猎靴');
INSERT INTO `item` VALUES ('100300411', '图纸—龙鳞狩猎靴');
INSERT INTO `item` VALUES ('100300412', '图纸—辉光狩猎靴');
INSERT INTO `item` VALUES ('100300413', '图纸—刃狼尖头鞋');
INSERT INTO `item` VALUES ('100300414', '图纸—雪熊尖头鞋');
INSERT INTO `item` VALUES ('100300415', '图纸—龙鳞尖头鞋');
INSERT INTO `item` VALUES ('100300416', '图纸—辉光尖头鞋');
INSERT INTO `item` VALUES ('100300417', '图纸—丝绸短靴');
INSERT INTO `item` VALUES ('100300418', '图纸—符文短靴');
INSERT INTO `item` VALUES ('100300419', '图纸—梦幻短靴');
INSERT INTO `item` VALUES ('100300420', '图纸—辉光短靴');
INSERT INTO `item` VALUES ('100300421', '图纸—丝绸礼鞋');
INSERT INTO `item` VALUES ('100300422', '图纸—符文礼鞋');
INSERT INTO `item` VALUES ('100300423', '图纸—梦幻礼鞋');
INSERT INTO `item` VALUES ('100300424', '图纸—辉光礼鞋');
INSERT INTO `item` VALUES ('100300425', '图纸—丝绸凉鞋');
INSERT INTO `item` VALUES ('100300426', '图纸—符文凉鞋');
INSERT INTO `item` VALUES ('100300427', '图纸—梦幻凉鞋');
INSERT INTO `item` VALUES ('100300428', '图纸—辉光凉鞋');
INSERT INTO `item` VALUES ('100300429', '图纸—丝绸拖靴');
INSERT INTO `item` VALUES ('100300430', '图纸—符文拖靴');
INSERT INTO `item` VALUES ('100300431', '图纸—梦幻拖靴');
INSERT INTO `item` VALUES ('100300432', '图纸—辉光拖靴');
INSERT INTO `item` VALUES ('100300433', '图纸碎片—秘银双刃斧');
INSERT INTO `item` VALUES ('100300434', '图纸碎片—魔晶长刃斧');
INSERT INTO `item` VALUES ('100300435', '图纸碎片—陨星长刃斧');
INSERT INTO `item` VALUES ('100300436', '图纸碎片—尘封之斧');
INSERT INTO `item` VALUES ('100300437', '图纸碎片—秘银宽刃剑');
INSERT INTO `item` VALUES ('100300438', '图纸碎片—魔晶双刃剑');
INSERT INTO `item` VALUES ('100300439', '图纸碎片—陨星双刃剑');
INSERT INTO `item` VALUES ('100300440', '图纸碎片—尘封之剑');
INSERT INTO `item` VALUES ('100300441', '图纸碎片—秘银秘典');
INSERT INTO `item` VALUES ('100300442', '图纸碎片—魔晶圣典');
INSERT INTO `item` VALUES ('100300443', '图纸碎片—陨星圣典');
INSERT INTO `item` VALUES ('100300444', '图纸碎片—尘封之书');
INSERT INTO `item` VALUES ('100300445', '图纸碎片—秘银长杖');
INSERT INTO `item` VALUES ('100300446', '图纸碎片—魔晶魔杖');
INSERT INTO `item` VALUES ('100300447', '图纸碎片—陨星魔杖');
INSERT INTO `item` VALUES ('100300448', '图纸碎片—尘封之杖');
INSERT INTO `item` VALUES ('100300449', '图纸碎片—秘银重盾');
INSERT INTO `item` VALUES ('100300450', '图纸碎片—魔晶鸢盾');
INSERT INTO `item` VALUES ('100300451', '图纸碎片—陨星鸢盾');
INSERT INTO `item` VALUES ('100300452', '图纸碎片—尘封之剑盾');
INSERT INTO `item` VALUES ('100300453', '图纸碎片—秘银骑士枪');
INSERT INTO `item` VALUES ('100300454', '图纸碎片—魔晶征服枪');
INSERT INTO `item` VALUES ('100300455', '图纸碎片—陨星征服枪');
INSERT INTO `item` VALUES ('100300456', '图纸碎片—尘封之枪');
INSERT INTO `item` VALUES ('100300457', '图纸碎片—秘银战戟');
INSERT INTO `item` VALUES ('100300458', '图纸碎片—魔晶三叉戟');
INSERT INTO `item` VALUES ('100300459', '图纸碎片—陨星三叉戟');
INSERT INTO `item` VALUES ('100300460', '图纸碎片—尘封之戟');
INSERT INTO `item` VALUES ('100300461', '图纸碎片—秘银长枪');
INSERT INTO `item` VALUES ('100300462', '图纸碎片—魔晶战戈');
INSERT INTO `item` VALUES ('100300463', '图纸碎片—陨星战戈');
INSERT INTO `item` VALUES ('100300464', '图纸碎片—尘封之矛');
INSERT INTO `item` VALUES ('100300465', '图纸碎片—秘银长弓');
INSERT INTO `item` VALUES ('100300466', '图纸碎片—魔晶战斗弓');
INSERT INTO `item` VALUES ('100300467', '图纸碎片—陨星战斗弓');
INSERT INTO `item` VALUES ('100300468', '图纸碎片—尘封之弓');
INSERT INTO `item` VALUES ('100300469', '图纸碎片—秘银护拳');
INSERT INTO `item` VALUES ('100300470', '图纸碎片—魔晶重拳');
INSERT INTO `item` VALUES ('100300471', '图纸碎片—陨星重拳');
INSERT INTO `item` VALUES ('100300472', '图纸碎片—尘封之拳');
INSERT INTO `item` VALUES ('100300473', '图纸碎片—镶银水晶球');
INSERT INTO `item` VALUES ('100300474', '图纸碎片—魔晶魔导器');
INSERT INTO `item` VALUES ('100300475', '图纸碎片—陨星魔导器');
INSERT INTO `item` VALUES ('100300476', '图纸碎片—尘封之器');
INSERT INTO `item` VALUES ('100300477', '图纸碎片—秘银骑兵对剑');
INSERT INTO `item` VALUES ('100300478', '图纸碎片—魔晶贵族对剑');
INSERT INTO `item` VALUES ('100300479', '图纸碎片—陨星贵族对剑');
INSERT INTO `item` VALUES ('100300480', '图纸碎片—尘封之对剑');
INSERT INTO `item` VALUES ('100300481', '图纸碎片—银环丝带');
INSERT INTO `item` VALUES ('100300482', '图纸碎片—魔晶缎带');
INSERT INTO `item` VALUES ('100300483', '图纸碎片—陨星缎带');
INSERT INTO `item` VALUES ('100300484', '图纸碎片—尘封之带');
INSERT INTO `item` VALUES ('100300485', '图纸碎片—秘银太刀');
INSERT INTO `item` VALUES ('100300486', '图纸碎片—魔晶野太刀');
INSERT INTO `item` VALUES ('100300487', '图纸碎片—陨星野太刀');
INSERT INTO `item` VALUES ('100300488', '图纸碎片—尘封之太刀');
INSERT INTO `item` VALUES ('100300489', '图纸碎片—秘银胁差');
INSERT INTO `item` VALUES ('100300490', '图纸碎片—魔晶忍刃');
INSERT INTO `item` VALUES ('100300491', '图纸碎片—陨星忍刃');
INSERT INTO `item` VALUES ('100300492', '图纸碎片—尘封之忍刀');
INSERT INTO `item` VALUES ('100300493', '图纸碎片—镶银曼陀林');
INSERT INTO `item` VALUES ('100300494', '图纸碎片—魔晶西奥伯琴');
INSERT INTO `item` VALUES ('100300495', '图纸碎片—陨星西奥伯琴');
INSERT INTO `item` VALUES ('100300496', '图纸碎片—尘封之琴');
INSERT INTO `item` VALUES ('100300497', '图纸碎片—秘银短匕');
INSERT INTO `item` VALUES ('100300498', '图纸碎片—魔晶大马士革刀');
INSERT INTO `item` VALUES ('100300499', '图纸碎片—陨星大马士革刀');
INSERT INTO `item` VALUES ('100300500', '图纸碎片—尘封之匕');
INSERT INTO `item` VALUES ('100300501', '图纸碎片—秘银巨镰');
INSERT INTO `item` VALUES ('100300502', '图纸碎片—魔晶魔镰');
INSERT INTO `item` VALUES ('100300503', '图纸碎片—陨星魔镰');
INSERT INTO `item` VALUES ('100300504', '图纸碎片—尘封之镰');
INSERT INTO `item` VALUES ('100300505', '图纸碎片—秘银袖剑');
INSERT INTO `item` VALUES ('100300506', '图纸碎片—魔晶刺刃');
INSERT INTO `item` VALUES ('100300507', '图纸碎片—陨星刺刃');
INSERT INTO `item` VALUES ('100300508', '图纸碎片—尘封之短剑');
INSERT INTO `item` VALUES ('100300509', '图纸碎片—秘银带柄弯刀');
INSERT INTO `item` VALUES ('100300510', '图纸碎片—魔晶仪式佩剑');
INSERT INTO `item` VALUES ('100300511', '图纸碎片—陨星仪式佩剑');
INSERT INTO `item` VALUES ('100300512', '图纸碎片—尘封之佩剑');
INSERT INTO `item` VALUES ('100300513', '图纸碎片—银纹符咒');
INSERT INTO `item` VALUES ('100300514', '图纸碎片—魔晶降魔符');
INSERT INTO `item` VALUES ('100300515', '图纸碎片—陨星降魔符');
INSERT INTO `item` VALUES ('100300516', '图纸碎片—尘封之符');
INSERT INTO `item` VALUES ('100300517', '图纸碎片—珊瑚之戒·强攻');
INSERT INTO `item` VALUES ('100300518', '图纸碎片—水晶之戒·强攻');
INSERT INTO `item` VALUES ('100300519', '图纸碎片—钻石之戒·强攻');
INSERT INTO `item` VALUES ('100300520', '图纸碎片—辉光之戒·强攻');
INSERT INTO `item` VALUES ('100300521', '图纸碎片—珊瑚之戒·魔能');
INSERT INTO `item` VALUES ('100300522', '图纸碎片—水晶之戒·魔能');
INSERT INTO `item` VALUES ('100300523', '图纸碎片—钻石之戒·魔能');
INSERT INTO `item` VALUES ('100300524', '图纸碎片—辉光之戒·魔能');
INSERT INTO `item` VALUES ('100300525', '图纸碎片—珊瑚之戒·坚守');
INSERT INTO `item` VALUES ('100300526', '图纸碎片—水晶之戒·坚守');
INSERT INTO `item` VALUES ('100300527', '图纸碎片—钻石之戒·坚守');
INSERT INTO `item` VALUES ('100300528', '图纸碎片—辉光之戒·坚守');
INSERT INTO `item` VALUES ('100300529', '图纸碎片—珊瑚之戒·驭魔');
INSERT INTO `item` VALUES ('100300530', '图纸碎片—水晶之戒·驭魔');
INSERT INTO `item` VALUES ('100300531', '图纸碎片—钻石之戒·驭魔');
INSERT INTO `item` VALUES ('100300532', '图纸碎片—辉光之戒·驭魔');
INSERT INTO `item` VALUES ('100300533', '图纸碎片—珊瑚之戒·光速');
INSERT INTO `item` VALUES ('100300534', '图纸碎片—水晶之戒·光速');
INSERT INTO `item` VALUES ('100300535', '图纸碎片—钻石之戒·光速');
INSERT INTO `item` VALUES ('100300536', '图纸碎片—辉光之戒·光速');
INSERT INTO `item` VALUES ('100300537', '图纸碎片—珊瑚之戒·强运');
INSERT INTO `item` VALUES ('100300538', '图纸碎片—水晶之戒·强运');
INSERT INTO `item` VALUES ('100300539', '图纸碎片—钻石之戒·强运');
INSERT INTO `item` VALUES ('100300540', '图纸碎片—辉光之戒·强运');
INSERT INTO `item` VALUES ('100300541', '图纸碎片—珊瑚之戒·蓄能');
INSERT INTO `item` VALUES ('100300542', '图纸碎片—水晶之戒·蓄能');
INSERT INTO `item` VALUES ('100300543', '图纸碎片—钻石之戒·蓄能');
INSERT INTO `item` VALUES ('100300544', '图纸碎片—辉光之戒·蓄能');
INSERT INTO `item` VALUES ('100300545', '图纸碎片—珊瑚之戒·破甲');
INSERT INTO `item` VALUES ('100300546', '图纸碎片—水晶之戒·破甲');
INSERT INTO `item` VALUES ('100300547', '图纸碎片—钻石之戒·破甲');
INSERT INTO `item` VALUES ('100300548', '图纸碎片—辉光之戒·破甲');
INSERT INTO `item` VALUES ('100300549', '图纸碎片—珊瑚之戒·识破');
INSERT INTO `item` VALUES ('100300550', '图纸碎片—水晶之戒·识破');
INSERT INTO `item` VALUES ('100300551', '图纸碎片—钻石之戒·识破');
INSERT INTO `item` VALUES ('100300552', '图纸碎片—辉光之戒·识破');
INSERT INTO `item` VALUES ('100300553', '图纸碎片—珊瑚之戒·迅捷');
INSERT INTO `item` VALUES ('100300554', '图纸碎片—水晶之戒·迅捷');
INSERT INTO `item` VALUES ('100300555', '图纸碎片—钻石之戒·迅捷');
INSERT INTO `item` VALUES ('100300556', '图纸碎片—辉光之戒·迅捷');
INSERT INTO `item` VALUES ('100300557', '图纸碎片—珊瑚之戒·强固');
INSERT INTO `item` VALUES ('100300558', '图纸碎片—水晶之戒·强固');
INSERT INTO `item` VALUES ('100300559', '图纸碎片—钻石之戒·强固');
INSERT INTO `item` VALUES ('100300560', '图纸碎片—辉光之戒·强固');
INSERT INTO `item` VALUES ('100300561', '图纸碎片—珊瑚之戒·强命');
INSERT INTO `item` VALUES ('100300562', '图纸碎片—水晶之戒·强命');
INSERT INTO `item` VALUES ('100300563', '图纸碎片—钻石之戒·强命');
INSERT INTO `item` VALUES ('100300564', '图纸碎片—辉光之戒·强命');
INSERT INTO `item` VALUES ('100300565', '图纸碎片—珊瑚吊坠·蛮神');
INSERT INTO `item` VALUES ('100300566', '图纸碎片—水晶吊坠·蛮神');
INSERT INTO `item` VALUES ('100300567', '图纸碎片—钻石吊坠·蛮神');
INSERT INTO `item` VALUES ('100300568', '图纸碎片—辉光吊坠·蛮神');
INSERT INTO `item` VALUES ('100300569', '图纸碎片—珊瑚吊坠·神射');
INSERT INTO `item` VALUES ('100300570', '图纸碎片—水晶吊坠·神射');
INSERT INTO `item` VALUES ('100300571', '图纸碎片—钻石吊坠·神射');
INSERT INTO `item` VALUES ('100300572', '图纸碎片—辉光吊坠·神射');
INSERT INTO `item` VALUES ('100300573', '图纸碎片—珊瑚吊坠·超碾');
INSERT INTO `item` VALUES ('100300574', '图纸碎片—水晶吊坠·超碾');
INSERT INTO `item` VALUES ('100300575', '图纸碎片—钻石吊坠·超碾');
INSERT INTO `item` VALUES ('100300576', '图纸碎片—辉光吊坠·超碾');
INSERT INTO `item` VALUES ('100300577', '图纸碎片—珊瑚吊坠·灵猿');
INSERT INTO `item` VALUES ('100300578', '图纸碎片—水晶吊坠·灵猿');
INSERT INTO `item` VALUES ('100300579', '图纸碎片—钻石吊坠·灵猿');
INSERT INTO `item` VALUES ('100300580', '图纸碎片—辉光吊坠·灵猿');
INSERT INTO `item` VALUES ('100300581', '图纸碎片—珊瑚吊坠·巨神');
INSERT INTO `item` VALUES ('100300582', '图纸碎片—水晶吊坠·巨神');
INSERT INTO `item` VALUES ('100300583', '图纸碎片—钻石吊坠·巨神');
INSERT INTO `item` VALUES ('100300584', '图纸碎片—辉光吊坠·巨神');
INSERT INTO `item` VALUES ('100300585', '图纸碎片—真银战斗重盔');
INSERT INTO `item` VALUES ('100300586', '图纸碎片—黑金战斗重盔');
INSERT INTO `item` VALUES ('100300587', '图纸碎片—黑曜石战斗重盔');
INSERT INTO `item` VALUES ('100300588', '图纸碎片—辉光战斗重盔');
INSERT INTO `item` VALUES ('100300589', '图纸碎片—真银魔法王冠');
INSERT INTO `item` VALUES ('100300590', '图纸碎片—黑金魔法王冠');
INSERT INTO `item` VALUES ('100300591', '图纸碎片—黑曜石魔法王冠');
INSERT INTO `item` VALUES ('100300592', '图纸碎片—辉光魔法王冠');
INSERT INTO `item` VALUES ('100300593', '图纸碎片—真银强固面甲');
INSERT INTO `item` VALUES ('100300594', '图纸碎片—黑金强固面甲');
INSERT INTO `item` VALUES ('100300595', '图纸碎片—黑曜石强固面甲');
INSERT INTO `item` VALUES ('100300596', '图纸碎片—辉光强固面甲');
INSERT INTO `item` VALUES ('100300597', '图纸碎片—真银御魔头冠');
INSERT INTO `item` VALUES ('100300598', '图纸碎片—黑金御魔头冠');
INSERT INTO `item` VALUES ('100300599', '图纸碎片—黑曜石御魔头冠');
INSERT INTO `item` VALUES ('100300600', '图纸碎片—辉光御魔头冠');
INSERT INTO `item` VALUES ('100300601', '图纸碎片—真银领主重盔');
INSERT INTO `item` VALUES ('100300602', '图纸碎片—黑金领主重盔');
INSERT INTO `item` VALUES ('100300603', '图纸碎片—黑曜石领主重盔');
INSERT INTO `item` VALUES ('100300604', '图纸碎片—辉光领主重盔');
INSERT INTO `item` VALUES ('100300605', '图纸碎片—真银骑士战盔');
INSERT INTO `item` VALUES ('100300606', '图纸碎片—黑金骑士战盔');
INSERT INTO `item` VALUES ('100300607', '图纸碎片—黑曜石骑士战盔');
INSERT INTO `item` VALUES ('100300608', '图纸碎片—辉光骑士战盔');
INSERT INTO `item` VALUES ('100300609', '图纸碎片—刃狼强袭骑盔');
INSERT INTO `item` VALUES ('100300610', '图纸碎片—雪熊强袭骑盔');
INSERT INTO `item` VALUES ('100300611', '图纸碎片—龙鳞强袭骑盔');
INSERT INTO `item` VALUES ('100300612', '图纸碎片—辉光强袭骑盔');
INSERT INTO `item` VALUES ('100300613', '图纸碎片—刃狼铭文兜帽');
INSERT INTO `item` VALUES ('100300614', '图纸碎片—雪熊铭文兜帽');
INSERT INTO `item` VALUES ('100300615', '图纸碎片—龙鳞铭文兜帽');
INSERT INTO `item` VALUES ('100300616', '图纸碎片—辉光铭文兜帽');
INSERT INTO `item` VALUES ('100300617', '图纸碎片—刃狼护目镜');
INSERT INTO `item` VALUES ('100300618', '图纸碎片—雪熊护目镜');
INSERT INTO `item` VALUES ('100300619', '图纸碎片—龙鳞护目镜');
INSERT INTO `item` VALUES ('100300620', '图纸碎片—辉光护目镜');
INSERT INTO `item` VALUES ('100300621', '图纸碎片—刃狼魔化面罩');
INSERT INTO `item` VALUES ('100300622', '图纸碎片—雪熊魔化面罩');
INSERT INTO `item` VALUES ('100300623', '图纸碎片—龙鳞魔化面罩');
INSERT INTO `item` VALUES ('100300624', '图纸碎片—辉光魔化面罩');
INSERT INTO `item` VALUES ('100300625', '图纸碎片—刃狼精制盔帽');
INSERT INTO `item` VALUES ('100300626', '图纸碎片—雪熊精制盔帽');
INSERT INTO `item` VALUES ('100300627', '图纸碎片—龙鳞精制盔帽');
INSERT INTO `item` VALUES ('100300628', '图纸碎片—辉光精制盔帽');
INSERT INTO `item` VALUES ('100300629', '图纸碎片—刃狼灵巧围巾');
INSERT INTO `item` VALUES ('100300630', '图纸碎片—雪熊灵巧围巾');
INSERT INTO `item` VALUES ('100300631', '图纸碎片—龙鳞灵巧围巾');
INSERT INTO `item` VALUES ('100300632', '图纸碎片—辉光灵巧围巾');
INSERT INTO `item` VALUES ('100300633', '图纸碎片—丝绸游侠头巾');
INSERT INTO `item` VALUES ('100300634', '图纸碎片—符文游侠头巾');
INSERT INTO `item` VALUES ('100300635', '图纸碎片—梦幻游侠头巾');
INSERT INTO `item` VALUES ('100300636', '图纸碎片—辉光游侠头巾');
INSERT INTO `item` VALUES ('100300637', '图纸碎片—丝绸巫术之帽');
INSERT INTO `item` VALUES ('100300638', '图纸碎片—符文巫术之帽');
INSERT INTO `item` VALUES ('100300639', '图纸碎片—梦幻巫术之帽');
INSERT INTO `item` VALUES ('100300640', '图纸碎片—辉光巫术之帽');
INSERT INTO `item` VALUES ('100300641', '图纸碎片—丝绸三角帽');
INSERT INTO `item` VALUES ('100300642', '图纸碎片—符文三角帽');
INSERT INTO `item` VALUES ('100300643', '图纸碎片—梦幻三角帽');
INSERT INTO `item` VALUES ('100300644', '图纸碎片—辉光三角帽');
INSERT INTO `item` VALUES ('100300645', '图纸碎片—丝绸贤者之帽');
INSERT INTO `item` VALUES ('100300646', '图纸碎片—符文贤者之帽');
INSERT INTO `item` VALUES ('100300647', '图纸碎片—梦幻贤者之帽');
INSERT INTO `item` VALUES ('100300648', '图纸碎片—辉光贤者之帽');
INSERT INTO `item` VALUES ('100300649', '图纸碎片—丝绸宽边帽');
INSERT INTO `item` VALUES ('100300650', '图纸碎片—符文宽边帽');
INSERT INTO `item` VALUES ('100300651', '图纸碎片—梦幻宽边帽');
INSERT INTO `item` VALUES ('100300652', '图纸碎片—辉光宽边帽');
INSERT INTO `item` VALUES ('100300653', '图纸碎片—丝绸贵族花边帽');
INSERT INTO `item` VALUES ('100300654', '图纸碎片—符文贵族花边帽');
INSERT INTO `item` VALUES ('100300655', '图纸碎片—梦幻贵族花边帽');
INSERT INTO `item` VALUES ('100300656', '图纸碎片—辉光贵族花边帽');
INSERT INTO `item` VALUES ('100300657', '图纸碎片—真银重铠');
INSERT INTO `item` VALUES ('100300658', '图纸碎片—黑金重铠');
INSERT INTO `item` VALUES ('100300659', '图纸碎片—黑曜石重铠');
INSERT INTO `item` VALUES ('100300660', '图纸碎片—辉光重铠');
INSERT INTO `item` VALUES ('100300661', '图纸碎片—真银锁子甲');
INSERT INTO `item` VALUES ('100300662', '图纸碎片—黑金锁子甲');
INSERT INTO `item` VALUES ('100300663', '图纸碎片—黑曜石锁子甲');
INSERT INTO `item` VALUES ('100300664', '图纸碎片—辉光锁子甲');
INSERT INTO `item` VALUES ('100300665', '图纸碎片—真银战铠');
INSERT INTO `item` VALUES ('100300666', '图纸碎片—黑金战铠');
INSERT INTO `item` VALUES ('100300667', '图纸碎片—黑曜石战铠');
INSERT INTO `item` VALUES ('100300668', '图纸碎片—辉光战铠');
INSERT INTO `item` VALUES ('100300669', '图纸碎片—真银鳞铠');
INSERT INTO `item` VALUES ('100300670', '图纸碎片—黑金鳞铠');
INSERT INTO `item` VALUES ('100300671', '图纸碎片—黑曜石鳞铠');
INSERT INTO `item` VALUES ('100300672', '图纸碎片—辉光鳞铠');
INSERT INTO `item` VALUES ('100300673', '图纸碎片—刃狼游侠斗篷');
INSERT INTO `item` VALUES ('100300674', '图纸碎片—雪熊游侠斗篷');
INSERT INTO `item` VALUES ('100300675', '图纸碎片—龙鳞游侠斗篷');
INSERT INTO `item` VALUES ('100300676', '图纸碎片—辉光游侠斗篷');
INSERT INTO `item` VALUES ('100300677', '图纸碎片—刃狼贤者披肩');
INSERT INTO `item` VALUES ('100300678', '图纸碎片—雪熊贤者披肩');
INSERT INTO `item` VALUES ('100300679', '图纸碎片—龙鳞贤者披肩');
INSERT INTO `item` VALUES ('100300680', '图纸碎片—辉光贤者披肩');
INSERT INTO `item` VALUES ('100300681', '图纸碎片—刃狼紧身衣');
INSERT INTO `item` VALUES ('100300682', '图纸碎片—雪熊紧身衣');
INSERT INTO `item` VALUES ('100300683', '图纸碎片—龙鳞紧身衣');
INSERT INTO `item` VALUES ('100300684', '图纸碎片—辉光紧身衣');
INSERT INTO `item` VALUES ('100300685', '图纸碎片—刃狼披风');
INSERT INTO `item` VALUES ('100300686', '图纸碎片—雪熊披风');
INSERT INTO `item` VALUES ('100300687', '图纸碎片—龙鳞披风');
INSERT INTO `item` VALUES ('100300688', '图纸碎片—辉光披风');
INSERT INTO `item` VALUES ('100300689', '图纸碎片—丝绸袖罩衣');
INSERT INTO `item` VALUES ('100300690', '图纸碎片—符文袖罩衣');
INSERT INTO `item` VALUES ('100300691', '图纸碎片—梦幻袖罩衣');
INSERT INTO `item` VALUES ('100300692', '图纸碎片—辉光袖罩衣');
INSERT INTO `item` VALUES ('100300693', '图纸碎片—丝绸长袍');
INSERT INTO `item` VALUES ('100300694', '图纸碎片—符文长袍');
INSERT INTO `item` VALUES ('100300695', '图纸碎片—梦幻长袍');
INSERT INTO `item` VALUES ('100300696', '图纸碎片—辉光长袍');
INSERT INTO `item` VALUES ('100300697', '图纸碎片—丝绸厚袍');
INSERT INTO `item` VALUES ('100300698', '图纸碎片—符文厚袍');
INSERT INTO `item` VALUES ('100300699', '图纸碎片—梦幻厚袍');
INSERT INTO `item` VALUES ('100300700', '图纸碎片—辉光厚袍');
INSERT INTO `item` VALUES ('100300701', '图纸碎片—丝绸短袍');
INSERT INTO `item` VALUES ('100300702', '图纸碎片—符文短袍');
INSERT INTO `item` VALUES ('100300703', '图纸碎片—梦幻短袍');
INSERT INTO `item` VALUES ('100300704', '图纸碎片—辉光短袍');
INSERT INTO `item` VALUES ('100300705', '图纸碎片—真银战靴');
INSERT INTO `item` VALUES ('100300706', '图纸碎片—黑金战靴');
INSERT INTO `item` VALUES ('100300707', '图纸碎片—黑曜石战靴');
INSERT INTO `item` VALUES ('100300708', '图纸碎片—辉光战靴');
INSERT INTO `item` VALUES ('100300709', '图纸碎片—真银胫甲');
INSERT INTO `item` VALUES ('100300710', '图纸碎片—黑金胫甲');
INSERT INTO `item` VALUES ('100300711', '图纸碎片—黑曜石胫甲');
INSERT INTO `item` VALUES ('100300712', '图纸碎片—辉光胫甲');
INSERT INTO `item` VALUES ('100300713', '图纸碎片—真银锁甲靴');
INSERT INTO `item` VALUES ('100300714', '图纸碎片—黑金锁甲靴');
INSERT INTO `item` VALUES ('100300715', '图纸碎片—黑曜石锁甲靴');
INSERT INTO `item` VALUES ('100300716', '图纸碎片—辉光锁甲靴');
INSERT INTO `item` VALUES ('100300717', '图纸碎片—真银铠靴');
INSERT INTO `item` VALUES ('100300718', '图纸碎片—黑金铠靴');
INSERT INTO `item` VALUES ('100300719', '图纸碎片—黑曜石铠靴');
INSERT INTO `item` VALUES ('100300720', '图纸碎片—辉光铠靴');
INSERT INTO `item` VALUES ('100300721', '图纸碎片—刃狼长靴');
INSERT INTO `item` VALUES ('100300722', '图纸碎片—雪熊长靴');
INSERT INTO `item` VALUES ('100300723', '图纸碎片—龙鳞长靴');
INSERT INTO `item` VALUES ('100300724', '图纸碎片—辉光长靴');
INSERT INTO `item` VALUES ('100300725', '图纸碎片—刃狼高筒靴');
INSERT INTO `item` VALUES ('100300726', '图纸碎片—雪熊高筒靴');
INSERT INTO `item` VALUES ('100300727', '图纸碎片—龙鳞高筒靴');
INSERT INTO `item` VALUES ('100300728', '图纸碎片—辉光高筒靴');
INSERT INTO `item` VALUES ('100300729', '图纸碎片—刃狼狩猎靴');
INSERT INTO `item` VALUES ('100300730', '图纸碎片—雪熊狩猎靴');
INSERT INTO `item` VALUES ('100300731', '图纸碎片—龙鳞狩猎靴');
INSERT INTO `item` VALUES ('100300732', '图纸碎片—辉光狩猎靴');
INSERT INTO `item` VALUES ('100300733', '图纸碎片—刃狼尖头鞋');
INSERT INTO `item` VALUES ('100300734', '图纸碎片—雪熊尖头鞋');
INSERT INTO `item` VALUES ('100300735', '图纸碎片—龙鳞尖头鞋');
INSERT INTO `item` VALUES ('100300736', '图纸碎片—辉光尖头鞋');
INSERT INTO `item` VALUES ('100300737', '图纸碎片—丝绸短靴');
INSERT INTO `item` VALUES ('100300738', '图纸碎片—符文短靴');
INSERT INTO `item` VALUES ('100300739', '图纸碎片—梦幻短靴');
INSERT INTO `item` VALUES ('100300740', '图纸碎片—辉光短靴');
INSERT INTO `item` VALUES ('100300741', '图纸碎片—丝绸礼鞋');
INSERT INTO `item` VALUES ('100300742', '图纸碎片—符文礼鞋');
INSERT INTO `item` VALUES ('100300743', '图纸碎片—梦幻礼鞋');
INSERT INTO `item` VALUES ('100300744', '图纸碎片—辉光礼鞋');
INSERT INTO `item` VALUES ('100300745', '图纸碎片—丝绸凉鞋');
INSERT INTO `item` VALUES ('100300746', '图纸碎片—符文凉鞋');
INSERT INTO `item` VALUES ('100300747', '图纸碎片—梦幻凉鞋');
INSERT INTO `item` VALUES ('100300748', '图纸碎片—辉光凉鞋');
INSERT INTO `item` VALUES ('100300749', '图纸碎片—丝绸拖靴');
INSERT INTO `item` VALUES ('100300750', '图纸碎片—符文拖靴');
INSERT INTO `item` VALUES ('100300751', '图纸碎片—梦幻拖靴');
INSERT INTO `item` VALUES ('100300752', '图纸碎片—辉光拖靴');
INSERT INTO `item` VALUES ('100300753', '图纸—真银骑士战盔');
INSERT INTO `item` VALUES ('100300754', '图纸—黑金骑士战盔');
INSERT INTO `item` VALUES ('100300755', '图纸—黑曜石骑士战盔');
INSERT INTO `item` VALUES ('100300756', '图纸—辉光骑士战盔');
INSERT INTO `item` VALUES ('100300757', '图纸—丝绸巫术之帽');
INSERT INTO `item` VALUES ('100300758', '图纸—符文巫术之帽');
INSERT INTO `item` VALUES ('100300759', '图纸—梦幻巫术之帽');
INSERT INTO `item` VALUES ('100300760', '图纸—辉光巫术之帽');
INSERT INTO `item` VALUES ('100300761', '图纸—真银战斗重盔');
INSERT INTO `item` VALUES ('100300762', '图纸—黑金战斗重盔');
INSERT INTO `item` VALUES ('100300763', '图纸—黑曜石战斗重盔');
INSERT INTO `item` VALUES ('100300764', '图纸—辉光战斗重盔');
INSERT INTO `item` VALUES ('100300765', '图纸—丝绸贵族花边帽');
INSERT INTO `item` VALUES ('100300766', '图纸—符文贵族花边帽');
INSERT INTO `item` VALUES ('100300767', '图纸—梦幻贵族花边帽');
INSERT INTO `item` VALUES ('100300768', '图纸—辉光贵族花边帽');
INSERT INTO `item` VALUES ('100300769', '图纸—真银战铠');
INSERT INTO `item` VALUES ('100300770', '图纸—黑金战铠');
INSERT INTO `item` VALUES ('100300771', '图纸—黑曜石战铠');
INSERT INTO `item` VALUES ('100300772', '图纸—辉光战铠');
INSERT INTO `item` VALUES ('100300773', '图纸—丝绸长袍');
INSERT INTO `item` VALUES ('100300774', '图纸—符文长袍');
INSERT INTO `item` VALUES ('100300775', '图纸—梦幻长袍');
INSERT INTO `item` VALUES ('100300776', '图纸—辉光长袍');
INSERT INTO `item` VALUES ('100300777', '图纸—真银重铠');
INSERT INTO `item` VALUES ('100300778', '图纸—黑金重铠');
INSERT INTO `item` VALUES ('100300779', '图纸—黑曜石重铠');
INSERT INTO `item` VALUES ('100300780', '图纸—辉光重铠');
INSERT INTO `item` VALUES ('100300781', '图纸—刃狼贤者披肩');
INSERT INTO `item` VALUES ('100300782', '图纸—雪熊贤者披肩');
INSERT INTO `item` VALUES ('100300783', '图纸—龙鳞贤者披肩');
INSERT INTO `item` VALUES ('100300784', '图纸—辉光贤者披肩');
INSERT INTO `item` VALUES ('100300785', '图纸—真银战靴');
INSERT INTO `item` VALUES ('100300786', '图纸—黑金战靴');
INSERT INTO `item` VALUES ('100300787', '图纸—黑曜石战靴');
INSERT INTO `item` VALUES ('100300788', '图纸—辉光战靴');
INSERT INTO `item` VALUES ('100300789', '图纸—丝绸长靴');
INSERT INTO `item` VALUES ('100300790', '图纸—符文长靴');
INSERT INTO `item` VALUES ('100300791', '图纸—梦幻长靴');
INSERT INTO `item` VALUES ('100300792', '图纸—辉光长靴');
INSERT INTO `item` VALUES ('100300793', '图纸—真银胫甲');
INSERT INTO `item` VALUES ('100300794', '图纸—黑金胫甲');
INSERT INTO `item` VALUES ('100300795', '图纸—黑曜石胫甲');
INSERT INTO `item` VALUES ('100300796', '图纸—辉光胫甲');
INSERT INTO `item` VALUES ('100300797', '图纸—丝绸短靴');
INSERT INTO `item` VALUES ('100300798', '图纸—符文短靴');
INSERT INTO `item` VALUES ('100300799', '图纸—梦幻短靴');
INSERT INTO `item` VALUES ('100300800', '图纸—辉光短靴');
INSERT INTO `item` VALUES ('100300801', '图纸—秘银宽刃剑');
INSERT INTO `item` VALUES ('100300802', '图纸—魔晶双刃剑');
INSERT INTO `item` VALUES ('100300803', '图纸—陨星双刃剑');
INSERT INTO `item` VALUES ('100300804', '图纸—无尽星海之剑');
INSERT INTO `item` VALUES ('100300805', '图纸—秘银长杖');
INSERT INTO `item` VALUES ('100300806', '图纸—魔晶魔杖');
INSERT INTO `item` VALUES ('100300807', '图纸—陨星魔杖');
INSERT INTO `item` VALUES ('100300808', '图纸—无尽法力之杖');
INSERT INTO `item` VALUES ('100300809', '图纸—秘银阔剑&重盾');
INSERT INTO `item` VALUES ('100300810', '图纸—魔晶利刃&鸢盾');
INSERT INTO `item` VALUES ('100300811', '图纸—陨星利刃&鸢盾');
INSERT INTO `item` VALUES ('100300812', '图纸—阿瓦隆的护佑');
INSERT INTO `item` VALUES ('100300813', '图纸—镶银水晶球');
INSERT INTO `item` VALUES ('100300814', '图纸—魔晶魔导器');
INSERT INTO `item` VALUES ('100300815', '图纸—陨星魔导器');
INSERT INTO `item` VALUES ('100300816', '图纸—远古封印之器');
INSERT INTO `item` VALUES ('100300817', '图纸—珊瑚之戒·物理');
INSERT INTO `item` VALUES ('100300818', '图纸—水晶之戒·物理');
INSERT INTO `item` VALUES ('100300819', '图纸—钻石之戒·物理');
INSERT INTO `item` VALUES ('100300820', '图纸—辉光之戒·物理');
INSERT INTO `item` VALUES ('100300821', '图纸—珊瑚之戒·魔法');
INSERT INTO `item` VALUES ('100300822', '图纸—水晶之戒·魔法');
INSERT INTO `item` VALUES ('100300823', '图纸—钻石之戒·魔法');
INSERT INTO `item` VALUES ('100300824', '图纸—辉光之戒·魔法');
INSERT INTO `item` VALUES ('100300825', '图纸—珊瑚之戒·防御');
INSERT INTO `item` VALUES ('100300826', '图纸—水晶之戒·防御');
INSERT INTO `item` VALUES ('100300827', '图纸—钻石之戒·防御');
INSERT INTO `item` VALUES ('100300828', '图纸—辉光之戒·防御');
INSERT INTO `item` VALUES ('100300829', '图纸—珊瑚之戒·特殊');
INSERT INTO `item` VALUES ('100300830', '图纸—水晶之戒·特殊');
INSERT INTO `item` VALUES ('100300831', '图纸—钻石之戒·特殊');
INSERT INTO `item` VALUES ('100300832', '图纸—辉光之戒·特殊');
INSERT INTO `item` VALUES ('100300833', '图纸—珊瑚吊坠·物理');
INSERT INTO `item` VALUES ('100300834', '图纸—水晶吊坠·物理');
INSERT INTO `item` VALUES ('100300835', '图纸—钻石吊坠·物理');
INSERT INTO `item` VALUES ('100300836', '图纸—辉光吊坠·物理');
INSERT INTO `item` VALUES ('100300837', '图纸—珊瑚吊坠·魔法');
INSERT INTO `item` VALUES ('100300838', '图纸—水晶吊坠·魔法');
INSERT INTO `item` VALUES ('100300839', '图纸—钻石吊坠·魔法');
INSERT INTO `item` VALUES ('100300840', '图纸—辉光吊坠·魔法');
INSERT INTO `item` VALUES ('100300841', '图纸—珊瑚吊坠·防御');
INSERT INTO `item` VALUES ('100300842', '图纸—水晶吊坠·防御');
INSERT INTO `item` VALUES ('100300843', '图纸—钻石吊坠·防御');
INSERT INTO `item` VALUES ('100300844', '图纸—辉光吊坠·防御');
INSERT INTO `item` VALUES ('100300845', '图纸—珊瑚吊坠·特殊');
INSERT INTO `item` VALUES ('100300846', '图纸—水晶吊坠·特殊');
INSERT INTO `item` VALUES ('100300847', '图纸—钻石吊坠·特殊');
INSERT INTO `item` VALUES ('100300848', '图纸—辉光吊坠·特殊');
INSERT INTO `item` VALUES ('100300849', '图纸碎片—真银骑士战盔');
INSERT INTO `item` VALUES ('100300850', '图纸碎片—黑金骑士战盔');
INSERT INTO `item` VALUES ('100300851', '图纸碎片—黑曜石骑士战盔');
INSERT INTO `item` VALUES ('100300852', '图纸碎片—辉光骑士战盔');
INSERT INTO `item` VALUES ('100300853', '图纸碎片—丝绸巫术之帽');
INSERT INTO `item` VALUES ('100300854', '图纸碎片—符文巫术之帽');
INSERT INTO `item` VALUES ('100300855', '图纸碎片—梦幻巫术之帽');
INSERT INTO `item` VALUES ('100300856', '图纸碎片—辉光巫术之帽');
INSERT INTO `item` VALUES ('100300857', '图纸碎片—真银战斗重盔');
INSERT INTO `item` VALUES ('100300858', '图纸碎片—黑金战斗重盔');
INSERT INTO `item` VALUES ('100300859', '图纸碎片—黑曜石战斗重盔');
INSERT INTO `item` VALUES ('100300860', '图纸碎片—辉光战斗重盔');
INSERT INTO `item` VALUES ('100300861', '图纸碎片—丝绸贵族花边帽');
INSERT INTO `item` VALUES ('100300862', '图纸碎片—符文贵族花边帽');
INSERT INTO `item` VALUES ('100300863', '图纸碎片—梦幻贵族花边帽');
INSERT INTO `item` VALUES ('100300864', '图纸碎片—辉光贵族花边帽');
INSERT INTO `item` VALUES ('100300865', '图纸碎片—真银战铠');
INSERT INTO `item` VALUES ('100300866', '图纸碎片—黑金战铠');
INSERT INTO `item` VALUES ('100300867', '图纸碎片—黑曜石战铠');
INSERT INTO `item` VALUES ('100300868', '图纸碎片—辉光战铠');
INSERT INTO `item` VALUES ('100300869', '图纸碎片—丝绸长袍');
INSERT INTO `item` VALUES ('100300870', '图纸碎片—符文长袍');
INSERT INTO `item` VALUES ('100300871', '图纸碎片—梦幻长袍');
INSERT INTO `item` VALUES ('100300872', '图纸碎片—辉光长袍');
INSERT INTO `item` VALUES ('100300873', '图纸碎片—真银重铠');
INSERT INTO `item` VALUES ('100300874', '图纸碎片—黑金重铠');
INSERT INTO `item` VALUES ('100300875', '图纸碎片—黑曜石重铠');
INSERT INTO `item` VALUES ('100300876', '图纸碎片—辉光重铠');
INSERT INTO `item` VALUES ('100300877', '图纸碎片—刃狼贤者披肩');
INSERT INTO `item` VALUES ('100300878', '图纸碎片—雪熊贤者披肩');
INSERT INTO `item` VALUES ('100300879', '图纸碎片—龙鳞贤者披肩');
INSERT INTO `item` VALUES ('100300880', '图纸碎片—辉光贤者披肩');
INSERT INTO `item` VALUES ('100300881', '图纸碎片—真银战靴');
INSERT INTO `item` VALUES ('100300882', '图纸碎片—黑金战靴');
INSERT INTO `item` VALUES ('100300883', '图纸碎片—黑曜石战靴');
INSERT INTO `item` VALUES ('100300884', '图纸碎片—辉光战靴');
INSERT INTO `item` VALUES ('100300885', '图纸碎片—丝绸长靴');
INSERT INTO `item` VALUES ('100300886', '图纸碎片—符文长靴');
INSERT INTO `item` VALUES ('100300887', '图纸碎片—梦幻长靴');
INSERT INTO `item` VALUES ('100300888', '图纸碎片—辉光长靴');
INSERT INTO `item` VALUES ('100300889', '图纸碎片—真银胫甲');
INSERT INTO `item` VALUES ('100300890', '图纸碎片—黑金胫甲');
INSERT INTO `item` VALUES ('100300891', '图纸碎片—黑曜石胫甲');
INSERT INTO `item` VALUES ('100300892', '图纸碎片—辉光胫甲');
INSERT INTO `item` VALUES ('100300893', '图纸碎片—丝绸短靴');
INSERT INTO `item` VALUES ('100300894', '图纸碎片—符文短靴');
INSERT INTO `item` VALUES ('100300895', '图纸碎片—梦幻短靴');
INSERT INTO `item` VALUES ('100300896', '图纸碎片—辉光短靴');
INSERT INTO `item` VALUES ('100300897', '图纸碎片—秘银宽刃剑');
INSERT INTO `item` VALUES ('100300898', '图纸碎片—魔晶双刃剑');
INSERT INTO `item` VALUES ('100300899', '图纸碎片—陨星双刃剑');
INSERT INTO `item` VALUES ('100300900', '图纸碎片—无尽星海之剑');
INSERT INTO `item` VALUES ('100300901', '图纸碎片—秘银长杖');
INSERT INTO `item` VALUES ('100300902', '图纸碎片—魔晶魔杖');
INSERT INTO `item` VALUES ('100300903', '图纸碎片—陨星魔杖');
INSERT INTO `item` VALUES ('100300904', '图纸碎片—无尽法力之杖');
INSERT INTO `item` VALUES ('100300905', '图纸碎片—秘银阔剑&重盾');
INSERT INTO `item` VALUES ('100300906', '图纸碎片—魔晶利刃&鸢盾');
INSERT INTO `item` VALUES ('100300907', '图纸碎片—陨星利刃&鸢盾');
INSERT INTO `item` VALUES ('100300908', '图纸碎片—阿瓦隆的护佑');
INSERT INTO `item` VALUES ('100300909', '图纸碎片—镶银水晶球');
INSERT INTO `item` VALUES ('100300910', '图纸碎片—魔晶魔导器');
INSERT INTO `item` VALUES ('100300911', '图纸碎片—陨星魔导器');
INSERT INTO `item` VALUES ('100300912', '图纸碎片—远古封印之器');
INSERT INTO `item` VALUES ('100300913', '图纸碎片—珊瑚之戒·物理');
INSERT INTO `item` VALUES ('100300914', '图纸碎片—水晶之戒·物理');
INSERT INTO `item` VALUES ('100300915', '图纸碎片—钻石之戒·物理');
INSERT INTO `item` VALUES ('100300916', '图纸碎片—辉光之戒·物理');
INSERT INTO `item` VALUES ('100300917', '图纸碎片—珊瑚之戒·魔法');
INSERT INTO `item` VALUES ('100300918', '图纸碎片—水晶之戒·魔法');
INSERT INTO `item` VALUES ('100300919', '图纸碎片—钻石之戒·魔法');
INSERT INTO `item` VALUES ('100300920', '图纸碎片—辉光之戒·魔法');
INSERT INTO `item` VALUES ('100300921', '图纸碎片—珊瑚之戒·防御');
INSERT INTO `item` VALUES ('100300922', '图纸碎片—水晶之戒·防御');
INSERT INTO `item` VALUES ('100300923', '图纸碎片—钻石之戒·防御');
INSERT INTO `item` VALUES ('100300924', '图纸碎片—辉光之戒·防御');
INSERT INTO `item` VALUES ('100300925', '图纸碎片—珊瑚之戒·特殊');
INSERT INTO `item` VALUES ('100300926', '图纸碎片—水晶之戒·特殊');
INSERT INTO `item` VALUES ('100300927', '图纸碎片—钻石之戒·特殊');
INSERT INTO `item` VALUES ('100300928', '图纸碎片—辉光之戒·特殊');
INSERT INTO `item` VALUES ('100300929', '图纸碎片—珊瑚吊坠·物理');
INSERT INTO `item` VALUES ('100300930', '图纸碎片—水晶吊坠·物理');
INSERT INTO `item` VALUES ('100300931', '图纸碎片—钻石吊坠·物理');
INSERT INTO `item` VALUES ('100300932', '图纸碎片—辉光吊坠·物理');
INSERT INTO `item` VALUES ('100300933', '图纸碎片—珊瑚吊坠·魔法');
INSERT INTO `item` VALUES ('100300934', '图纸碎片—水晶吊坠·魔法');
INSERT INTO `item` VALUES ('100300935', '图纸碎片—钻石吊坠·魔法');
INSERT INTO `item` VALUES ('100300936', '图纸碎片—辉光吊坠·魔法');
INSERT INTO `item` VALUES ('100300937', '图纸碎片—珊瑚吊坠·防御');
INSERT INTO `item` VALUES ('100300938', '图纸碎片—水晶吊坠·防御');
INSERT INTO `item` VALUES ('100300939', '图纸碎片—钻石吊坠·防御');
INSERT INTO `item` VALUES ('100300940', '图纸碎片—辉光吊坠·防御');
INSERT INTO `item` VALUES ('100300941', '图纸碎片—珊瑚吊坠·特殊');
INSERT INTO `item` VALUES ('100300942', '图纸碎片—水晶吊坠·特殊');
INSERT INTO `item` VALUES ('100300943', '图纸碎片—钻石吊坠·特殊');
INSERT INTO `item` VALUES ('100300944', '图纸碎片—辉光吊坠·特殊');
INSERT INTO `item` VALUES ('100400001', '测试卡牌');
INSERT INTO `item` VALUES ('100400011', '暗淡之红光晶体');
INSERT INTO `item` VALUES ('100400012', '荧光之红光晶体');
INSERT INTO `item` VALUES ('100400013', '闪光之红光晶体');
INSERT INTO `item` VALUES ('100400014', '耀眼之红光晶体');
INSERT INTO `item` VALUES ('100400015', '辉光之红光晶体');
INSERT INTO `item` VALUES ('100400021', '暗淡之蓝光晶体');
INSERT INTO `item` VALUES ('100400022', '荧光之蓝光晶体');
INSERT INTO `item` VALUES ('100400023', '闪光之蓝光晶体');
INSERT INTO `item` VALUES ('100400024', '耀眼之蓝光晶体');
INSERT INTO `item` VALUES ('100400025', '辉光之蓝光晶体');
INSERT INTO `item` VALUES ('100400031', '暗淡之黄光晶体');
INSERT INTO `item` VALUES ('100400032', '荧光之黄光晶体');
INSERT INTO `item` VALUES ('100400033', '闪光之黄光晶体');
INSERT INTO `item` VALUES ('100400034', '耀眼之黄光晶体');
INSERT INTO `item` VALUES ('100400035', '辉光之黄光晶体');
INSERT INTO `item` VALUES ('100400041', '暗淡之绿光晶体');
INSERT INTO `item` VALUES ('100400042', '荧光之绿光晶体');
INSERT INTO `item` VALUES ('100400043', '闪光之绿光晶体');
INSERT INTO `item` VALUES ('100400044', '耀眼之绿光晶体');
INSERT INTO `item` VALUES ('100400045', '辉光之绿光晶体');
INSERT INTO `item` VALUES ('100400050', '史莱姆');
INSERT INTO `item` VALUES ('100400051', '小树精');
INSERT INTO `item` VALUES ('100400052', '蟒蛇');
INSERT INTO `item` VALUES ('100400053', '地精斧手');
INSERT INTO `item` VALUES ('100400054', '短刃幼狼');
INSERT INTO `item` VALUES ('100400055', '平原狮鹫');
INSERT INTO `item` VALUES ('100400056', '豺狼斥候');
INSERT INTO `item` VALUES ('100400057', '狮子格斗家');
INSERT INTO `item` VALUES ('100400058', '沙漠之蝎');
INSERT INTO `item` VALUES ('100400059', '沙人');
INSERT INTO `item` VALUES ('100400060', '木乃伊');
INSERT INTO `item` VALUES ('100400061', '蜥蜴兵士');
INSERT INTO `item` VALUES ('100400062', '火焰魔花');
INSERT INTO `item` VALUES ('100400063', '火焰巨兽');
INSERT INTO `item` VALUES ('100400064', '烈焰雏鸟');
INSERT INTO `item` VALUES ('100400065', '烈焰侍卫');
INSERT INTO `item` VALUES ('100400066', '冰元素');
INSERT INTO `item` VALUES ('100400067', '雪怪');
INSERT INTO `item` VALUES ('100400068', '雪熊');
INSERT INTO `item` VALUES ('100400069', '雪地隐居者');
INSERT INTO `item` VALUES ('100400070', '蝙蝠');
INSERT INTO `item` VALUES ('100400071', '魅魔');
INSERT INTO `item` VALUES ('100400072', '骷髅弓兵');
INSERT INTO `item` VALUES ('100400073', '骷髅使徒');
INSERT INTO `item` VALUES ('100400074', '漂浮幽灵');
INSERT INTO `item` VALUES ('100400075', '幽灵剑士');
INSERT INTO `item` VALUES ('100400076', '幽灵弓手');
INSERT INTO `item` VALUES ('100400077', '黑暗神官');
INSERT INTO `item` VALUES ('100400078', '宫廷乐师·法莱恩');
INSERT INTO `item` VALUES ('100400079', '幻术师·无月');
INSERT INTO `item` VALUES ('100400080', '神射手·切斯塔');
INSERT INTO `item` VALUES ('100400081', '史莱姆');
INSERT INTO `item` VALUES ('100400082', '幻术师·铃铃');
INSERT INTO `item` VALUES ('100400083', '女剑士1阶');
INSERT INTO `item` VALUES ('100400084', '男弓手1阶');
INSERT INTO `item` VALUES ('100400085', '女双剑1阶');
INSERT INTO `item` VALUES ('100400086', '女盗贼1阶');
INSERT INTO `item` VALUES ('100400087', '男吟游1阶');
INSERT INTO `item` VALUES ('100400088', '男舞者1阶');
INSERT INTO `item` VALUES ('100400089', '唤灵者·艾菲');
INSERT INTO `item` VALUES ('100400090', '死亡骑士·兰斯洛特');
INSERT INTO `item` VALUES ('100400091', '盗剑士·艾温');
INSERT INTO `item` VALUES ('100400092', '影舞��·樱');
INSERT INTO `item` VALUES ('100400093', '驱魔师·无月');
INSERT INTO `item` VALUES ('100400094', '扒手·萝丝');
INSERT INTO `item` VALUES ('100400095', '剑士·菲欧娜');
INSERT INTO `item` VALUES ('100400096', '漫游舞者·沙加');
INSERT INTO `item` VALUES ('100400097', '剑舞者·维拉');
INSERT INTO `item` VALUES ('100400098', '女弓手');
INSERT INTO `item` VALUES ('100400099', '幼年长须豹');
INSERT INTO `item` VALUES ('100400100', '深海仆从');
INSERT INTO `item` VALUES ('100400101', '沙滩猴妖');
INSERT INTO `item` VALUES ('100400102', '硬壳乌龟');
INSERT INTO `item` VALUES ('100400103', '邪浪哨兵');
INSERT INTO `item` VALUES ('100400104', '盾卫·阿尔托利亚');
INSERT INTO `item` VALUES ('100400105', '史莱姆士兵');
INSERT INTO `item` VALUES ('100400106', '树精');
INSERT INTO `item` VALUES ('100400107', '巨蟒');
INSERT INTO `item` VALUES ('100400108', '地精战士');
INSERT INTO `item` VALUES ('100400109', '长刃狼');
INSERT INTO `item` VALUES ('100400110', '凶猛狮鹫');
INSERT INTO `item` VALUES ('100400111', '豺狼弓手');
INSERT INTO `item` VALUES ('100400112', '狮子拳师');
INSERT INTO `item` VALUES ('100400113', '沙漠巨蝎');
INSERT INTO `item` VALUES ('100400114', '流沙巨人');
INSERT INTO `item` VALUES ('100400115', '苏醒者');
INSERT INTO `item` VALUES ('100400116', '蜥蜴护卫');
INSERT INTO `item` VALUES ('100400117', '烈焰魔花');
INSERT INTO `item` VALUES ('100400118', '邪焰巨兽');
INSERT INTO `item` VALUES ('100400119', '烈火鸟');
INSERT INTO `item` VALUES ('100400120', '烈焰祭司');
INSERT INTO `item` VALUES ('100400121', '冰晶之灵');
INSERT INTO `item` VALUES ('100400122', '巨型雪怪');
INSERT INTO `item` VALUES ('100400123', '巨型雪熊');
INSERT INTO `item` VALUES ('100400124', '雪地斥候');
INSERT INTO `item` VALUES ('100400125', '吸血蝙蝠');
INSERT INTO `item` VALUES ('100400126', '欢愉之女');
INSERT INTO `item` VALUES ('100400127', '骷髅射手');
INSERT INTO `item` VALUES ('100400128', '骷髅使徒');
INSERT INTO `item` VALUES ('100400129', '魅影邪灵');
INSERT INTO `item` VALUES ('100400130', '幽灵剑客');
INSERT INTO `item` VALUES ('100400131', '幽灵射手');
INSERT INTO `item` VALUES ('100400132', '黑暗邪使');
INSERT INTO `item` VALUES ('100400133', '深海卫士');
INSERT INTO `item` VALUES ('100400134', '利爪猴妖');
INSERT INTO `item` VALUES ('100400135', '硬甲魔龟');
INSERT INTO `item` VALUES ('100400136', '邪浪水手');
INSERT INTO `item` VALUES ('100400137', '史莱姆骑士');
INSERT INTO `item` VALUES ('100400138', '树精长者');
INSERT INTO `item` VALUES ('100400139', '剧毒巨蟒');
INSERT INTO `item` VALUES ('100400140', '地精狂战士');
INSERT INTO `item` VALUES ('100400141', '利刃恶狼');
INSERT INTO `item` VALUES ('100400142', '利爪狮鹫');
INSERT INTO `item` VALUES ('100400143', '豺狼神射手');
INSERT INTO `item` VALUES ('100400144', '雄狮之拳');
INSERT INTO `item` VALUES ('100400145', '沙漠毒蝎');
INSERT INTO `item` VALUES ('100400146', '沙漠巨像');
INSERT INTO `item` VALUES ('100400147', '远古封印者');
INSERT INTO `item` VALUES ('100400148', '蜥蜴勇士');
INSERT INTO `item` VALUES ('100400149', '熔岩魔花');
INSERT INTO `item` VALUES ('100400150', '魔能邪焰巨兽');
INSERT INTO `item` VALUES ('100400151', '灼热之羽');
INSERT INTO `item` VALUES ('100400152', '烈焰主教');
INSERT INTO `item` VALUES ('100400153', '冰脊之灵');
INSERT INTO `item` VALUES ('100400154', '凶恶雪怪');
INSERT INTO `item` VALUES ('100400155', '雪熊护卫');
INSERT INTO `item` VALUES ('100400156', '雪山护卫');
INSERT INTO `item` VALUES ('100400157', '暗影蝙蝠');
INSERT INTO `item` VALUES ('100400158', '邪魅之女');
INSERT INTO `item` VALUES ('100400159', '骷髅神射手');
INSERT INTO `item` VALUES ('100400160', '骷髅魔导师');
INSERT INTO `item` VALUES ('100400161', '诅咒恶灵');
INSERT INTO `item` VALUES ('100400162', '深渊护卫');
INSERT INTO `item` VALUES ('100400163', '邪影神射手');
INSERT INTO `item` VALUES ('100400164', '混沌侍从');
INSERT INTO `item` VALUES ('100400165', '深海统领');
INSERT INTO `item` VALUES ('100400166', '巫毒猴妖');
INSERT INTO `item` VALUES ('100400167', '尖甲巨龟');
INSERT INTO `item` VALUES ('100400168', '邪浪藏匿者');
INSERT INTO `item` VALUES ('100400169', '领主·法布雷');
INSERT INTO `item` VALUES ('100400170', '骷髅魔导师');
INSERT INTO `item` VALUES ('100400171', '光之元素');
INSERT INTO `item` VALUES ('100400172', '光之元素');
INSERT INTO `item` VALUES ('100400173', '光之元素');
INSERT INTO `item` VALUES ('100400174', '光之元素');
INSERT INTO `item` VALUES ('100400175', '光之元素');
INSERT INTO `item` VALUES ('100400200', '克雷斯(灵魂石)');
INSERT INTO `item` VALUES ('100400201', '菲欧娜(灵魂石)');
INSERT INTO `item` VALUES ('100400202', '切斯塔(灵魂石)');
INSERT INTO `item` VALUES ('100400203', '伊莲娜(灵魂石)');
INSERT INTO `item` VALUES ('100400204', '凯姆(灵魂石)');
INSERT INTO `item` VALUES ('100400205', '莉莉丝(灵魂石)');
INSERT INTO `item` VALUES ('100400206', '维克多(灵魂石)');
INSERT INTO `item` VALUES ('100400207', '凯瑟琳(灵魂石)');
INSERT INTO `item` VALUES ('100400208', '兰斯洛特(灵魂石)');
INSERT INTO `item` VALUES ('100400209', '玛丽洛丝(灵魂石)');
INSERT INTO `item` VALUES ('100400210', '隼(灵魂石)');
INSERT INTO `item` VALUES ('100400211', '樱(灵魂石)');
INSERT INTO `item` VALUES ('100400212', '罗杰(灵魂石)');
INSERT INTO `item` VALUES ('100400213', '萝丝(灵魂石)');
INSERT INTO `item` VALUES ('100400214', '艾温(灵魂石)');
INSERT INTO `item` VALUES ('100400215', '维拉(灵魂石)');
INSERT INTO `item` VALUES ('100400216', '海恩(灵魂石)');
INSERT INTO `item` VALUES ('100400217', '杰茜卡(灵魂石)');
INSERT INTO `item` VALUES ('100400218', '雷斯塔(灵魂石)');
INSERT INTO `item` VALUES ('100400219', '艾菲(灵魂石)');
INSERT INTO `item` VALUES ('100400220', '伊达尔(灵魂石)');
INSERT INTO `item` VALUES ('100400221', '艾莉莎(灵魂石)');
INSERT INTO `item` VALUES ('100400222', '无月(灵魂石)');
INSERT INTO `item` VALUES ('100400223', '铃铃(灵魂石)');
INSERT INTO `item` VALUES ('100400224', '宫本豪(灵魂石)');
INSERT INTO `item` VALUES ('100400225', '訚千代(灵魂石)');
INSERT INTO `item` VALUES ('100400226', '亚利斯塔尔(灵魂石)');
INSERT INTO `item` VALUES ('100400227', '阿尔托利亚(灵魂石)');
INSERT INTO `item` VALUES ('100400228', '阿修罗(灵魂石)');
INSERT INTO `item` VALUES ('100400229', '丽(灵魂石)');
INSERT INTO `item` VALUES ('100400230', '格斯(灵魂石)');
INSERT INTO `item` VALUES ('100400231', '丽娜(灵魂石)');
INSERT INTO `item` VALUES ('100400232', '法莱恩(灵魂石)');
INSERT INTO `item` VALUES ('100400233', '缪丝(灵魂石)');
INSERT INTO `item` VALUES ('100400234', '沙加(灵魂石)');
INSERT INTO `item` VALUES ('100400235', '菲(灵魂石)');
INSERT INTO `item` VALUES ('100400236', '雷恩(灵魂石)');
INSERT INTO `item` VALUES ('100400237', '雪莉(灵魂石)');
INSERT INTO `item` VALUES ('100400238', '尤里(灵魂石)');
INSERT INTO `item` VALUES ('100400239', '敏特(灵魂石)');
INSERT INTO `item` VALUES ('100400240', '法布雷(灵魂石)');
INSERT INTO `item` VALUES ('100400241', '艾莉莎(灵魂石)');
INSERT INTO `item` VALUES ('100400242', '蟒蛇(灵魂石)');
INSERT INTO `item` VALUES ('100400243', '短刃幼狼(灵魂石)');
INSERT INTO `item` VALUES ('100400244', '平原狮鹫(灵魂石)');
INSERT INTO `item` VALUES ('100400245', '豺狼斥候(灵魂石)');
INSERT INTO `item` VALUES ('100400246', '火焰巨兽(灵魂石)');
INSERT INTO `item` VALUES ('100400247', '骷髅弓兵(灵魂石)');
INSERT INTO `item` VALUES ('100400248', '幽灵剑士(灵魂石)');
INSERT INTO `item` VALUES ('100400249', '幽灵弓手(灵魂石)');
INSERT INTO `item` VALUES ('100400250', '木乃伊(灵魂石)');
INSERT INTO `item` VALUES ('100400251', '火焰魔花(灵魂石)');
INSERT INTO `item` VALUES ('100400252', '冰元素(灵魂石)');
INSERT INTO `item` VALUES ('100400253', '骷髅使徒(灵魂石)');
INSERT INTO `item` VALUES ('100400254', '漂浮幽灵(灵魂石)');
INSERT INTO `item` VALUES ('100400255', '史莱姆(灵魂石)');
INSERT INTO `item` VALUES ('100400256', '地精斧手(灵魂石)');
INSERT INTO `item` VALUES ('100400257', '狮子格斗家(灵魂石)');
INSERT INTO `item` VALUES ('100400258', '沙漠之蝎(灵魂石)');
INSERT INTO `item` VALUES ('100400259', '蜥蜴兵士(灵魂石)');
INSERT INTO `item` VALUES ('100400260', '雪熊(灵魂石)');
INSERT INTO `item` VALUES ('100400261', '魅魔(灵魂石)');
INSERT INTO `item` VALUES ('100400262', '小树精(灵魂石)');
INSERT INTO `item` VALUES ('100400263', '沙人(灵魂石)');
INSERT INTO `item` VALUES ('100400264', '烈焰雏鸟(灵魂石)');
INSERT INTO `item` VALUES ('100400265', '烈焰侍卫(灵魂石)');
INSERT INTO `item` VALUES ('100400266', '雪怪(灵魂石)');
INSERT INTO `item` VALUES ('100400267', '雪地隐居者(灵魂石)');
INSERT INTO `item` VALUES ('100400268', '蝙蝠(灵魂石)');
INSERT INTO `item` VALUES ('100400269', '黑暗神官(灵魂石)');
INSERT INTO `item` VALUES ('100400270', '幼年长须豹(灵魂石)');
INSERT INTO `item` VALUES ('100400271', '深海仆从(灵魂石)');
INSERT INTO `item` VALUES ('100400272', '沙滩猴妖(灵魂石)');
INSERT INTO `item` VALUES ('100400273', '硬壳乌龟(灵魂石)');
INSERT INTO `item` VALUES ('100400274', '邪浪哨兵(灵魂石)');
INSERT INTO `item` VALUES ('100400275', '萨菲罗斯（灵魂石）');
INSERT INTO `item` VALUES ('100400276', '克劳德（灵魂石）');
INSERT INTO `item` VALUES ('100400300', '见习魔剑士·克雷斯');
INSERT INTO `item` VALUES ('100400301', '见习剑士·菲欧娜');
INSERT INTO `item` VALUES ('100400302', '弓手·切斯塔');
INSERT INTO `item` VALUES ('100400303', '弓手·伊莲娜');
INSERT INTO `item` VALUES ('100400304', '驭龙者·凯姆');
INSERT INTO `item` VALUES ('100400305', '驭龙者·莉莉丝');
INSERT INTO `item` VALUES ('100400306', '刺客·维克多');
INSERT INTO `item` VALUES ('100400307', '刺客·凯瑟琳');
INSERT INTO `item` VALUES ('100400308', '黑骑士·兰斯洛特');
INSERT INTO `item` VALUES ('100400309', '黑骑士·玛丽洛丝');
INSERT INTO `item` VALUES ('100400310', '下忍·隼');
INSERT INTO `item` VALUES ('100400311', '下忍·樱');
INSERT INTO `item` VALUES ('100400312', '扒手·罗杰');
INSERT INTO `item` VALUES ('100400313', '扒手·萝丝');
INSERT INTO `item` VALUES ('100400314', '盗剑士·艾温');
INSERT INTO `item` VALUES ('100400315', '盗剑士·维拉');
INSERT INTO `item` VALUES ('100400316', '巫术学徒·海恩');
INSERT INTO `item` VALUES ('100400317', '巫术学徒·杰茜卡');
INSERT INTO `item` VALUES ('100400318', '初阶召唤师·雷斯塔');
INSERT INTO `item` VALUES ('100400319', '初阶召唤师·艾菲');
INSERT INTO `item` VALUES ('100400320', '死灵法师·伊达尔');
INSERT INTO `item` VALUES ('100400321', '死灵法师·艾莉莎');
INSERT INTO `item` VALUES ('100400322', '幻术师·无月');
INSERT INTO `item` VALUES ('100400323', '幻术师·铃铃');
INSERT INTO `item` VALUES ('100400324', '浪人·宫本豪');
INSERT INTO `item` VALUES ('100400325', '浪人·訚千代');
INSERT INTO `item` VALUES ('100400326', '盾卫·亚利斯塔尔');
INSERT INTO `item` VALUES ('100400327', '盾卫·阿尔托利亚');
INSERT INTO `item` VALUES ('100400328', '格斗家·阿修罗');
INSERT INTO `item` VALUES ('100400329', '格斗家·丽');
INSERT INTO `item` VALUES ('100400330', '佣兵·格斯');
INSERT INTO `item` VALUES ('100400331', '佣兵·丽娜');
INSERT INTO `item` VALUES ('100400332', '街头艺人·法莱恩');
INSERT INTO `item` VALUES ('100400333', '街头艺人·缪丝');
INSERT INTO `item` VALUES ('100400334', '见习舞者·沙加');
INSERT INTO `item` VALUES ('100400335', '见习舞者·菲');
INSERT INTO `item` VALUES ('100400336', '天马骑手·雷恩');
INSERT INTO `item` VALUES ('100400337', '天马骑手·雪莉');
INSERT INTO `item` VALUES ('100400338', '服侍·尤里');
INSERT INTO `item` VALUES ('100400339', '服侍·敏特');
INSERT INTO `item` VALUES ('100400340', '少校·法布雷');
INSERT INTO `item` VALUES ('100400341', '少校·艾莉莎');
INSERT INTO `item` VALUES ('100400342', '骷髅弓兵');
INSERT INTO `item` VALUES ('100400343', '幽灵剑士');
INSERT INTO `item` VALUES ('100400344', '幽灵弓手');
INSERT INTO `item` VALUES ('100400345', '骷髅使徒');
INSERT INTO `item` VALUES ('100400346', '雪熊');
INSERT INTO `item` VALUES ('100400347', '硬壳乌龟');
INSERT INTO `item` VALUES ('100400348', '萨菲罗斯');
INSERT INTO `item` VALUES ('100400349', '克劳德');
INSERT INTO `item` VALUES ('100500001', '测试碎片');
INSERT INTO `item` VALUES ('100500002', '风化骑士战盔');
INSERT INTO `item` VALUES ('100500003', '白钢骑士战盔');
INSERT INTO `item` VALUES ('100500004', '蓝铜骑士战盔');
INSERT INTO `item` VALUES ('100500005', '真银骑士战盔');
INSERT INTO `item` VALUES ('100500006', '黑金骑士战盔');
INSERT INTO `item` VALUES ('100500007', '黑曜石骑士战盔');
INSERT INTO `item` VALUES ('100500008', '辉光骑士战盔');
INSERT INTO `item` VALUES ('100500009', '风化巫术之帽');
INSERT INTO `item` VALUES ('100500010', '麻制巫术之帽');
INSERT INTO `item` VALUES ('100500011', '棉制巫术之帽');
INSERT INTO `item` VALUES ('100500012', '丝绸巫术之帽');
INSERT INTO `item` VALUES ('100500013', '符文巫术之帽');
INSERT INTO `item` VALUES ('100500014', '梦幻巫术之帽');
INSERT INTO `item` VALUES ('100500015', '辉光巫术之帽');
INSERT INTO `item` VALUES ('100500016', '风化战斗重盔');
INSERT INTO `item` VALUES ('100500017', '白钢战斗重盔');
INSERT INTO `item` VALUES ('100500018', '蓝铜战斗重盔');
INSERT INTO `item` VALUES ('100500019', '真银战斗重盔');
INSERT INTO `item` VALUES ('100500020', '黑金战斗重盔');
INSERT INTO `item` VALUES ('100500021', '黑曜石战斗重盔');
INSERT INTO `item` VALUES ('100500022', '辉光战斗重盔');
INSERT INTO `item` VALUES ('100500023', '风化贵族花边帽');
INSERT INTO `item` VALUES ('100500024', '麻制贵族花边帽');
INSERT INTO `item` VALUES ('100500025', '棉制贵族花边帽');
INSERT INTO `item` VALUES ('100500026', '丝绸贵族花边帽');
INSERT INTO `item` VALUES ('100500027', '符文贵族花边帽');
INSERT INTO `item` VALUES ('100500028', '梦幻贵族花边帽');
INSERT INTO `item` VALUES ('100500029', '辉光贵族花边帽');
INSERT INTO `item` VALUES ('100500030', '风化战铠');
INSERT INTO `item` VALUES ('100500031', '白钢战铠');
INSERT INTO `item` VALUES ('100500032', '蓝铜战铠');
INSERT INTO `item` VALUES ('100500033', '真银战铠');
INSERT INTO `item` VALUES ('100500034', '黑金战铠');
INSERT INTO `item` VALUES ('100500035', '黑曜石战铠');
INSERT INTO `item` VALUES ('100500036', '辉光战铠');
INSERT INTO `item` VALUES ('100500037', '风化长袍');
INSERT INTO `item` VALUES ('100500038', '麻布长袍');
INSERT INTO `item` VALUES ('100500039', '棉布长袍');
INSERT INTO `item` VALUES ('100500040', '丝绸长袍');
INSERT INTO `item` VALUES ('100500041', '符文长袍');
INSERT INTO `item` VALUES ('100500042', '梦幻长袍');
INSERT INTO `item` VALUES ('100500043', '辉光长袍');
INSERT INTO `item` VALUES ('100500044', '风化重铠');
INSERT INTO `item` VALUES ('100500045', '白钢重铠');
INSERT INTO `item` VALUES ('100500046', '蓝铜重铠');
INSERT INTO `item` VALUES ('100500047', '真银重铠');
INSERT INTO `item` VALUES ('100500048', '黑金重铠');
INSERT INTO `item` VALUES ('100500049', '黑曜石重铠');
INSERT INTO `item` VALUES ('100500050', '辉光重铠');
INSERT INTO `item` VALUES ('100500051', '风化贤者披肩');
INSERT INTO `item` VALUES ('100500052', '硬革贤者披肩');
INSERT INTO `item` VALUES ('100500053', '蛇鳞贤者披肩');
INSERT INTO `item` VALUES ('100500054', '刃狼贤者披肩');
INSERT INTO `item` VALUES ('100500055', '雪熊贤者披肩');
INSERT INTO `item` VALUES ('100500056', '龙鳞贤者披肩');
INSERT INTO `item` VALUES ('100500057', '辉光贤者披肩');
INSERT INTO `item` VALUES ('100500058', '风化战靴');
INSERT INTO `item` VALUES ('100500059', '白钢战靴');
INSERT INTO `item` VALUES ('100500060', '蓝铜战靴');
INSERT INTO `item` VALUES ('100500061', '真银战靴');
INSERT INTO `item` VALUES ('100500062', '黑金战靴');
INSERT INTO `item` VALUES ('100500063', '黑曜石战靴');
INSERT INTO `item` VALUES ('100500064', '辉光战靴');
INSERT INTO `item` VALUES ('100500065', '风化长靴');
INSERT INTO `item` VALUES ('100500066', '麻布长靴');
INSERT INTO `item` VALUES ('100500067', '棉布长靴');
INSERT INTO `item` VALUES ('100500068', '丝绸长靴');
INSERT INTO `item` VALUES ('100500069', '符文长靴');
INSERT INTO `item` VALUES ('100500070', '梦幻长靴');
INSERT INTO `item` VALUES ('100500071', '辉光长靴');
INSERT INTO `item` VALUES ('100500072', '风化胫甲');
INSERT INTO `item` VALUES ('100500073', '白钢胫甲');
INSERT INTO `item` VALUES ('100500074', '蓝铜胫甲');
INSERT INTO `item` VALUES ('100500075', '真银胫甲');
INSERT INTO `item` VALUES ('100500076', '黑金胫甲');
INSERT INTO `item` VALUES ('100500077', '黑曜石胫甲');
INSERT INTO `item` VALUES ('100500078', '辉光胫甲');
INSERT INTO `item` VALUES ('100500079', '风化短靴');
INSERT INTO `item` VALUES ('100500080', '麻布短靴');
INSERT INTO `item` VALUES ('100500081', '棉布短靴');
INSERT INTO `item` VALUES ('100500082', '丝绸短靴');
INSERT INTO `item` VALUES ('100500083', '符文短靴');
INSERT INTO `item` VALUES ('100500084', '梦幻短靴');
INSERT INTO `item` VALUES ('100500085', '辉光短靴');
INSERT INTO `item` VALUES ('100500086', '风化长剑');
INSERT INTO `item` VALUES ('100500087', '铸铁长剑');
INSERT INTO `item` VALUES ('100500088', '铜制宽刃剑');
INSERT INTO `item` VALUES ('100500089', '秘银宽刃剑');
INSERT INTO `item` VALUES ('100500090', '魔晶双刃剑');
INSERT INTO `item` VALUES ('100500091', '陨星双刃剑');
INSERT INTO `item` VALUES ('100500092', '无尽星海之剑');
INSERT INTO `item` VALUES ('100500093', '风化手杖');
INSERT INTO `item` VALUES ('100500094', '古旧手杖');
INSERT INTO `item` VALUES ('100500095', '铜制长杖');
INSERT INTO `item` VALUES ('100500096', '秘银长杖');
INSERT INTO `item` VALUES ('100500097', '魔晶魔杖');
INSERT INTO `item` VALUES ('100500098', '陨星魔杖');
INSERT INTO `item` VALUES ('100500099', '无尽法力之杖');
INSERT INTO `item` VALUES ('100500100', '风化短剑&圆盾');
INSERT INTO `item` VALUES ('100500101', '铸铁短剑&圆盾');
INSERT INTO `item` VALUES ('100500102', '铜制阔剑&重盾');
INSERT INTO `item` VALUES ('100500103', '秘银阔剑&重盾');
INSERT INTO `item` VALUES ('100500104', '魔晶利刃&鸢盾');
INSERT INTO `item` VALUES ('100500105', '陨星利刃&鸢盾');
INSERT INTO `item` VALUES ('100500106', '阿瓦隆的护佑');
INSERT INTO `item` VALUES ('100500107', '风化法球');
INSERT INTO `item` VALUES ('100500108', '古旧法球');
INSERT INTO `item` VALUES ('100500109', '镶铜水晶球');
INSERT INTO `item` VALUES ('100500110', '镶银水晶球');
INSERT INTO `item` VALUES ('100500111', '魔晶魔导器');
INSERT INTO `item` VALUES ('100500112', '陨星魔导器');
INSERT INTO `item` VALUES ('100500113', '远古封印之器');
INSERT INTO `item` VALUES ('100500114', '蛋白之戒·物理');
INSERT INTO `item` VALUES ('100500115', '猫眼之戒·物理');
INSERT INTO `item` VALUES ('100500116', '孔雀之戒·物理');
INSERT INTO `item` VALUES ('100500117', '珊瑚之戒·物理');
INSERT INTO `item` VALUES ('100500118', '水晶之戒·物理');
INSERT INTO `item` VALUES ('100500119', '钻石之戒·物理');
INSERT INTO `item` VALUES ('100500120', '辉光之戒·物理');
INSERT INTO `item` VALUES ('100500121', '蛋白之戒·魔法');
INSERT INTO `item` VALUES ('100500122', '猫眼之戒·魔法');
INSERT INTO `item` VALUES ('100500123', '孔雀之戒·魔法');
INSERT INTO `item` VALUES ('100500124', '珊瑚之戒·魔法');
INSERT INTO `item` VALUES ('100500125', '水晶之戒·魔法');
INSERT INTO `item` VALUES ('100500126', '钻石之戒·魔法');
INSERT INTO `item` VALUES ('100500127', '辉光之戒·魔法');
INSERT INTO `item` VALUES ('100500128', '蛋白之戒·防御');
INSERT INTO `item` VALUES ('100500129', '猫眼之戒·防御');
INSERT INTO `item` VALUES ('100500130', '孔雀之戒·防御');
INSERT INTO `item` VALUES ('100500131', '珊瑚之戒·防御');
INSERT INTO `item` VALUES ('100500132', '水晶之戒·防御');
INSERT INTO `item` VALUES ('100500133', '钻石之戒·防御');
INSERT INTO `item` VALUES ('100500134', '辉光之戒·防御');
INSERT INTO `item` VALUES ('100500135', '蛋白之戒·特殊');
INSERT INTO `item` VALUES ('100500136', '猫眼之戒·特殊');
INSERT INTO `item` VALUES ('100500137', '孔雀之戒·特殊');
INSERT INTO `item` VALUES ('100500138', '珊瑚之戒·特殊');
INSERT INTO `item` VALUES ('100500139', '水晶之戒·特殊');
INSERT INTO `item` VALUES ('100500140', '钻石之戒·特殊');
INSERT INTO `item` VALUES ('100500141', '辉光之戒·特殊');
INSERT INTO `item` VALUES ('100500142', '蛋白吊坠·物理');
INSERT INTO `item` VALUES ('100500143', '猫眼吊坠·物理');
INSERT INTO `item` VALUES ('100500144', '孔雀吊坠·物理');
INSERT INTO `item` VALUES ('100500145', '珊瑚吊坠·物理');
INSERT INTO `item` VALUES ('100500146', '水晶吊坠·物理');
INSERT INTO `item` VALUES ('100500147', '钻石吊坠·物理');
INSERT INTO `item` VALUES ('100500148', '辉光吊坠·物理');
INSERT INTO `item` VALUES ('100500149', '蛋白吊坠·魔法');
INSERT INTO `item` VALUES ('100500150', '猫眼吊坠·魔法');
INSERT INTO `item` VALUES ('100500151', '孔雀吊坠·魔法');
INSERT INTO `item` VALUES ('100500152', '珊瑚吊坠·魔法');
INSERT INTO `item` VALUES ('100500153', '水晶吊坠·魔法');
INSERT INTO `item` VALUES ('100500154', '钻石吊坠·魔法');
INSERT INTO `item` VALUES ('100500155', '辉光吊坠·魔法');
INSERT INTO `item` VALUES ('100500156', '蛋白吊坠·防御');
INSERT INTO `item` VALUES ('100500157', '猫眼吊坠·防御');
INSERT INTO `item` VALUES ('100500158', '孔雀吊坠·防御');
INSERT INTO `item` VALUES ('100500159', '珊瑚吊坠·防御');
INSERT INTO `item` VALUES ('100500160', '水晶吊坠·防御');
INSERT INTO `item` VALUES ('100500161', '钻石吊坠·防御');
INSERT INTO `item` VALUES ('100500162', '辉光吊坠·防御');
INSERT INTO `item` VALUES ('100500163', '蛋白吊坠·特殊');
INSERT INTO `item` VALUES ('100500164', '猫眼吊坠·特殊');
INSERT INTO `item` VALUES ('100500165', '孔雀吊坠·特殊');
INSERT INTO `item` VALUES ('100500166', '珊瑚吊坠·特殊');
INSERT INTO `item` VALUES ('100500167', '水晶吊坠·特殊');
INSERT INTO `item` VALUES ('100500168', '钻石吊坠·特殊');
INSERT INTO `item` VALUES ('100500169', '辉光吊坠·特殊');
INSERT INTO `item` VALUES ('169000001', '普通金币宝箱');
INSERT INTO `item` VALUES ('169000002', '优质金币宝箱');
INSERT INTO `item` VALUES ('169000003', '极品金币宝箱');
INSERT INTO `item` VALUES ('169000004', '普通矿石宝箱');
INSERT INTO `item` VALUES ('169000005', '优质矿石宝箱');
INSERT INTO `item` VALUES ('169000006', '极品矿石宝箱');
INSERT INTO `item` VALUES ('169000007', '普通荣耀宝箱');
INSERT INTO `item` VALUES ('169000008', '优质荣耀宝箱');
INSERT INTO `item` VALUES ('169000009', '极品荣耀宝箱');
INSERT INTO `item` VALUES ('169000010', '普通星尘宝箱');
INSERT INTO `item` VALUES ('169000011', '优质星尘宝箱');
INSERT INTO `item` VALUES ('169000012', '极品星尘宝箱');
INSERT INTO `item` VALUES ('169000013', '普通水晶宝箱');
INSERT INTO `item` VALUES ('169000014', '优质水晶宝箱');
INSERT INTO `item` VALUES ('169000015', '极品水晶宝箱');
INSERT INTO `item` VALUES ('169000016', '白色头盔宝箱');
INSERT INTO `item` VALUES ('169000017', '白色护甲宝箱');
INSERT INTO `item` VALUES ('169000018', '白色鞋子宝箱');
INSERT INTO `item` VALUES ('169000019', '白色武器宝箱');
INSERT INTO `item` VALUES ('169000020', '白色戒指宝箱');
INSERT INTO `item` VALUES ('169000021', '白色项链宝箱');
INSERT INTO `item` VALUES ('169000022', '绿色头盔宝箱');
INSERT INTO `item` VALUES ('169000023', '绿色护甲宝箱');
INSERT INTO `item` VALUES ('169000024', '绿色鞋子宝箱');
INSERT INTO `item` VALUES ('169000025', '绿色武器宝箱');
INSERT INTO `item` VALUES ('169000026', '绿色戒指宝箱');
INSERT INTO `item` VALUES ('169000027', '绿色项链宝箱');
INSERT INTO `item` VALUES ('169000028', '蓝色头盔宝箱');
INSERT INTO `item` VALUES ('169000029', '蓝色护甲宝箱');
INSERT INTO `item` VALUES ('169000030', '蓝色鞋子宝箱');
INSERT INTO `item` VALUES ('169000031', '蓝色武器宝箱');
INSERT INTO `item` VALUES ('169000032', '蓝色戒指宝箱');
INSERT INTO `item` VALUES ('169000033', '蓝色项链宝箱');
INSERT INTO `item` VALUES ('169000034', '紫色头盔宝箱');
INSERT INTO `item` VALUES ('169000035', '紫色护甲宝箱');
INSERT INTO `item` VALUES ('169000036', '紫色鞋子宝箱');
INSERT INTO `item` VALUES ('169000037', '紫色武器宝箱');
INSERT INTO `item` VALUES ('169000038', '紫色戒指宝箱');
INSERT INTO `item` VALUES ('169000039', '紫色项链宝箱');
INSERT INTO `item` VALUES ('169000040', '紫色+1头盔宝箱');
INSERT INTO `item` VALUES ('169000041', '紫色+1护甲宝箱');
INSERT INTO `item` VALUES ('169000042', '紫色+1鞋子宝箱');
INSERT INTO `item` VALUES ('169000043', '紫色+1武器宝箱');
INSERT INTO `item` VALUES ('169000044', '紫色+1戒指宝箱');
INSERT INTO `item` VALUES ('169000045', '紫色+1项链宝箱');
INSERT INTO `item` VALUES ('169000046', '紫色+2头盔宝箱');
INSERT INTO `item` VALUES ('169000047', '紫色+2护甲宝箱');
INSERT INTO `item` VALUES ('169000048', '紫色+2鞋子宝箱');
INSERT INTO `item` VALUES ('169000049', '紫色+2武器宝箱');
INSERT INTO `item` VALUES ('169000050', '紫色+2戒指宝箱');
INSERT INTO `item` VALUES ('169000051', '紫色+2项链宝箱');
INSERT INTO `item` VALUES ('169000052', '橙色头盔宝箱');
INSERT INTO `item` VALUES ('169000053', '橙色护甲宝箱');
INSERT INTO `item` VALUES ('169000054', '橙色鞋子宝箱');
INSERT INTO `item` VALUES ('169000055', '橙色武器宝箱');
INSERT INTO `item` VALUES ('169000056', '橙色戒指宝箱');
INSERT INTO `item` VALUES ('169000057', '橙色项链宝箱');
INSERT INTO `item` VALUES ('169000058', '白色物理装备宝箱');
INSERT INTO `item` VALUES ('169000059', '白色魔法装备宝箱');
INSERT INTO `item` VALUES ('169000060', '白色防御装备宝箱');
INSERT INTO `item` VALUES ('169000061', '白色辅助装备宝箱');
INSERT INTO `item` VALUES ('169000062', '绿色物理装备宝箱');
INSERT INTO `item` VALUES ('169000063', '绿色魔法装备宝箱');
INSERT INTO `item` VALUES ('169000064', '绿色防御装备宝箱');
INSERT INTO `item` VALUES ('169000065', '绿色辅助装备宝箱');
INSERT INTO `item` VALUES ('169000066', '蓝色物理装备宝箱');
INSERT INTO `item` VALUES ('169000067', '蓝色魔法装备宝箱');
INSERT INTO `item` VALUES ('169000068', '蓝色防御装备宝箱');
INSERT INTO `item` VALUES ('169000069', '蓝色辅助装备宝箱');
INSERT INTO `item` VALUES ('169000070', '紫色物理装备宝箱');
INSERT INTO `item` VALUES ('169000071', '紫色魔法装备宝箱');
INSERT INTO `item` VALUES ('169000072', '紫色防御装备宝箱');
INSERT INTO `item` VALUES ('169000073', '紫色辅助装备宝箱');
INSERT INTO `item` VALUES ('169000074', '紫色+1物理装备宝箱');
INSERT INTO `item` VALUES ('169000075', '紫色+1魔法装备宝箱');
INSERT INTO `item` VALUES ('169000076', '紫色+1防御装备宝箱');
INSERT INTO `item` VALUES ('169000077', '紫色+1辅助装备宝箱');
INSERT INTO `item` VALUES ('169000078', '紫色+2物理装备宝箱');
INSERT INTO `item` VALUES ('169000079', '紫色+2魔法装备宝箱');
INSERT INTO `item` VALUES ('169000080', '紫色+2防御装备宝箱');
INSERT INTO `item` VALUES ('169000081', '紫色+2辅助装备宝箱');
INSERT INTO `item` VALUES ('169000082', '橙色物理装备宝箱');
INSERT INTO `item` VALUES ('169000083', '橙色魔法装备宝箱');
INSERT INTO `item` VALUES ('169000084', '橙色防御装备宝箱');
INSERT INTO `item` VALUES ('169000085', '橙色辅助装备宝箱');
INSERT INTO `item` VALUES ('169000086', '物理型套装礼包（白）');
INSERT INTO `item` VALUES ('169000087', '魔法型套装礼包（白）');
INSERT INTO `item` VALUES ('169000088', '防御型套装礼包（白）');
INSERT INTO `item` VALUES ('169000089', '辅助型套装礼包（白）');
INSERT INTO `item` VALUES ('169000090', '物理型套装礼包（绿）');
INSERT INTO `item` VALUES ('169000091', '魔法型套装礼包（绿）');
INSERT INTO `item` VALUES ('169000092', '防御型套装礼包（绿）');
INSERT INTO `item` VALUES ('169000093', '辅助型套装礼包（绿）');
INSERT INTO `item` VALUES ('169000094', '物理型套装礼包（蓝）');
INSERT INTO `item` VALUES ('169000095', '魔法型套装礼包（蓝）');
INSERT INTO `item` VALUES ('169000096', '防御型套装礼包（蓝）');
INSERT INTO `item` VALUES ('169000097', '辅助型套装礼包（蓝）');
INSERT INTO `item` VALUES ('169000098', '物理型套装礼包（紫）');
INSERT INTO `item` VALUES ('169000099', '魔法型套装礼包（紫）');
INSERT INTO `item` VALUES ('169000100', '防御型套装礼包（紫）');
INSERT INTO `item` VALUES ('169000101', '辅助型套装礼包（紫）');
INSERT INTO `item` VALUES ('169000102', '物理型套装礼包（紫+2）');
INSERT INTO `item` VALUES ('169000103', '魔法型套装礼包（紫+2）');
INSERT INTO `item` VALUES ('169000104', '防御型套装礼包（紫+2）');
INSERT INTO `item` VALUES ('169000105', '辅助型套装礼包（紫+2）');
INSERT INTO `item` VALUES ('169000106', '物理型套装礼包（紫+3）');
INSERT INTO `item` VALUES ('169000107', '魔法型套装礼包（紫+3）');
INSERT INTO `item` VALUES ('169000108', '防御型套装礼包（紫+3）');
INSERT INTO `item` VALUES ('169000109', '辅助型套装礼包（紫+3）');
INSERT INTO `item` VALUES ('169000110', '物理型套装礼包（橙）');
INSERT INTO `item` VALUES ('169000111', '魔法型套装礼包（橙）');
INSERT INTO `item` VALUES ('169000112', '防御型套装礼包（橙）');
INSERT INTO `item` VALUES ('169000113', '辅助型套装礼包（橙）');
INSERT INTO `item` VALUES ('169000114', 'SS英雄宝箱');
INSERT INTO `item` VALUES ('169000115', 'S级英雄宝箱');
INSERT INTO `item` VALUES ('169000116', 'SS级英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000117', 'S级英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000118', '新英雄宝箱');
INSERT INTO `item` VALUES ('169000119', '初级S级英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000120', '初级SS级英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000121', '中级S级英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000122', '中级SS级英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000123', '绿色材料宝箱');
INSERT INTO `item` VALUES ('169000124', '蓝色武器材料宝箱');
INSERT INTO `item` VALUES ('169000125', '蓝色防具材料宝箱');
INSERT INTO `item` VALUES ('169000126', '紫色武器材料宝箱');
INSERT INTO `item` VALUES ('169000127', '紫色防具材料宝箱');
INSERT INTO `item` VALUES ('169000128', '紫色+1武器材料宝箱');
INSERT INTO `item` VALUES ('169000129', '紫色+1防具材料宝箱');
INSERT INTO `item` VALUES ('169000130', '紫色+2武器材料宝箱');
INSERT INTO `item` VALUES ('169000131', '紫色+2防具材料宝箱');
INSERT INTO `item` VALUES ('169000132', '橙色武器材料宝箱');
INSERT INTO `item` VALUES ('169000133', '橙色防具材料宝箱');
INSERT INTO `item` VALUES ('169000134', '紫色头盔图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000135', '紫色护甲图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000136', '紫色鞋子图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000137', '紫色武器图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000138', '紫色戒指图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000139', '紫色项链图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000140', '紫色+1头盔图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000141', '紫色+1护甲图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000142', '紫色+1鞋子图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000143', '紫色+1武器图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000144', '紫色+1戒指图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000145', '紫色+1项链图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000146', '紫色+2头盔图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000147', '紫色+2护甲图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000148', '紫色+2鞋子图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000149', '紫色+2武器图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000150', '紫色+2戒指图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000151', '紫色+2项链图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000152', '橙色头盔图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000153', '橙色护甲图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000154', '橙色鞋子图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000155', '橙色武器图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000156', '橙色戒指图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000157', '橙色项链图纸碎片宝箱');
INSERT INTO `item` VALUES ('169000158', '普通技能书宝箱');
INSERT INTO `item` VALUES ('169000159', '优质技能书宝箱');
INSERT INTO `item` VALUES ('169000160', '极品技能书宝箱');
INSERT INTO `item` VALUES ('169000161', '普通合体技能书宝箱');
INSERT INTO `item` VALUES ('169000162', '优质合体技能书宝箱');
INSERT INTO `item` VALUES ('169000163', '极品合体技能书宝箱');
INSERT INTO `item` VALUES ('169000164', '附魔矿宝箱');
INSERT INTO `item` VALUES ('169000165', '淬火矿宝箱');
INSERT INTO `item` VALUES ('169000166', '力量宝石宝箱');
INSERT INTO `item` VALUES ('169000167', '魔法宝石宝箱');
INSERT INTO `item` VALUES ('169000168', '防御宝石宝箱');
INSERT INTO `item` VALUES ('169000169', '辅助宝石宝箱');
INSERT INTO `item` VALUES ('169000170', '锭矿宝箱');
INSERT INTO `item` VALUES ('169000171', '麻布宝箱');
INSERT INTO `item` VALUES ('169000172', '头盔材料宝箱');
INSERT INTO `item` VALUES ('169000173', '内衬材料宝箱');
INSERT INTO `item` VALUES ('169000174', '鞋面材料宝箱');
INSERT INTO `item` VALUES ('169000175', '饰品核心材料宝箱');
INSERT INTO `item` VALUES ('169000176', '戒指材料宝箱');
INSERT INTO `item` VALUES ('169000177', '饰品材料宝箱');
INSERT INTO `item` VALUES ('169000178', 'SS级女性英雄宝箱');
INSERT INTO `item` VALUES ('169000179', 'S级女性英雄宝箱');
INSERT INTO `item` VALUES ('169000180', 'SS级女性英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000181', 'S级女性英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000182', '中级SS级女性英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000183', '中级S级女性英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000184', '初级SS级女性英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000185', '初级S级女性英雄碎片宝箱');
INSERT INTO `item` VALUES ('169000186', 'SS级英雄选择宝箱');
INSERT INTO `item` VALUES ('169000187', 'S级英雄选择宝箱');
INSERT INTO `item` VALUES ('169000188', '5级物理符文');
INSERT INTO `item` VALUES ('169000189', '5级魔法符文');
INSERT INTO `item` VALUES ('169000190', '5级物防符文');
INSERT INTO `item` VALUES ('169000191', '5级魔防符文');
INSERT INTO `item` VALUES ('169000192', '5级生命符文');
INSERT INTO `item` VALUES ('169000193', '5级速度符文');
INSERT INTO `item` VALUES ('169000194', '5级暴击符文');
INSERT INTO `item` VALUES ('169000195', '5级闪避符文');
INSERT INTO `item` VALUES ('169000196', '4级物理符文');
INSERT INTO `item` VALUES ('169000197', '4级魔法符文');
INSERT INTO `item` VALUES ('169000198', '4级物防符文');
INSERT INTO `item` VALUES ('169000199', '4级魔防符文');
INSERT INTO `item` VALUES ('169000200', '4级生命符文');
INSERT INTO `item` VALUES ('169000201', '4级速度符文');
INSERT INTO `item` VALUES ('169000202', '4级暴击符文');
INSERT INTO `item` VALUES ('169000203', '3级物理符文');
INSERT INTO `item` VALUES ('169000204', '3级魔法符文');
INSERT INTO `item` VALUES ('169000205', '3级物防符文');
INSERT INTO `item` VALUES ('169000206', '3级魔防符文');
INSERT INTO `item` VALUES ('169000207', '3级生命符文');
INSERT INTO `item` VALUES ('169000208', '2级物防符文');
INSERT INTO `item` VALUES ('169000209', '2级魔防符文');
INSERT INTO `item` VALUES ('169000210', '2级生命符文');
INSERT INTO `item` VALUES ('169000211', '1级物防符文');
INSERT INTO `item` VALUES ('169000212', '1级魔防符文');
INSERT INTO `item` VALUES ('169000213', '1级生命符文');
INSERT INTO `item` VALUES ('400100001', '5级1号符文');
INSERT INTO `item` VALUES ('400100002', '5级2号符文');
INSERT INTO `item` VALUES ('400100003', '5级3号符文');
INSERT INTO `item` VALUES ('400100004', '5级4号符文');
INSERT INTO `item` VALUES ('400100005', '5级5号符文');
INSERT INTO `item` VALUES ('400100006', '5级6号符文');
INSERT INTO `item` VALUES ('400100007', '5级1号符文');
INSERT INTO `item` VALUES ('400100008', '5级2号符文');
INSERT INTO `item` VALUES ('400100009', '5级3号符文');
INSERT INTO `item` VALUES ('400100010', '5级4号符文');
INSERT INTO `item` VALUES ('400100011', '5级5号符文');
INSERT INTO `item` VALUES ('400100012', '5级6号符文');
INSERT INTO `item` VALUES ('400100013', '5级1号符文');
INSERT INTO `item` VALUES ('400100014', '5级2号符文');
INSERT INTO `item` VALUES ('400100015', '5级3号符文');
INSERT INTO `item` VALUES ('400100016', '5级4号符文');
INSERT INTO `item` VALUES ('400100017', '5级5号符文');
INSERT INTO `item` VALUES ('400100018', '5级6号符文');
INSERT INTO `item` VALUES ('400100019', '5级1号符文');
INSERT INTO `item` VALUES ('400100020', '5级2号符文');
INSERT INTO `item` VALUES ('400100021', '5级3号符文');
INSERT INTO `item` VALUES ('400100022', '5级4号符文');
INSERT INTO `item` VALUES ('400100023', '5级5号符文');
INSERT INTO `item` VALUES ('400100024', '5级6号符文');
INSERT INTO `item` VALUES ('400100025', '5级1号符文');
INSERT INTO `item` VALUES ('400100026', '5级2号符文');
INSERT INTO `item` VALUES ('400100027', '5级3号符文');
INSERT INTO `item` VALUES ('400100028', '5级4号符文');
INSERT INTO `item` VALUES ('400100029', '5级5号符文');
INSERT INTO `item` VALUES ('400100030', '5级6号符文');
INSERT INTO `item` VALUES ('400100031', '5级1号符文');
INSERT INTO `item` VALUES ('400100032', '5级2号符文');
INSERT INTO `item` VALUES ('400100033', '5级3号符文');
INSERT INTO `item` VALUES ('400100034', '5级4号符文');
INSERT INTO `item` VALUES ('400100035', '5级5号符文');
INSERT INTO `item` VALUES ('400100036', '5级6号符文');
INSERT INTO `item` VALUES ('400100037', '5级1号符文');
INSERT INTO `item` VALUES ('400100038', '5级2号符文');
INSERT INTO `item` VALUES ('400100039', '5级3号符文');
INSERT INTO `item` VALUES ('400100040', '5级4号符文');
INSERT INTO `item` VALUES ('400100041', '5级5号符文');
INSERT INTO `item` VALUES ('400100042', '5级6号符文');
INSERT INTO `item` VALUES ('400100043', '5级1号符文');
INSERT INTO `item` VALUES ('400100044', '5级2号符文');
INSERT INTO `item` VALUES ('400100045', '5级3号符文');
INSERT INTO `item` VALUES ('400100046', '5级4号符文');
INSERT INTO `item` VALUES ('400100047', '5级5号符文');
INSERT INTO `item` VALUES ('400100048', '5级6号符文');
INSERT INTO `item` VALUES ('400100049', '4级1号符文');
INSERT INTO `item` VALUES ('400100050', '4级2号符文');
INSERT INTO `item` VALUES ('400100051', '4级3号符文');
INSERT INTO `item` VALUES ('400100052', '4级4号符文');
INSERT INTO `item` VALUES ('400100053', '4级5号符文');
INSERT INTO `item` VALUES ('400100054', '4级6号符文');
INSERT INTO `item` VALUES ('400100055', '4级1号符文');
INSERT INTO `item` VALUES ('400100056', '4级2号符文');
INSERT INTO `item` VALUES ('400100057', '4级3号符文');
INSERT INTO `item` VALUES ('400100058', '4级4号符文');
INSERT INTO `item` VALUES ('400100059', '4级5号符文');
INSERT INTO `item` VALUES ('400100060', '4级6号符文');
INSERT INTO `item` VALUES ('400100061', '4级1号符文');
INSERT INTO `item` VALUES ('400100062', '4级2号符文');
INSERT INTO `item` VALUES ('400100063', '4级3号符文');
INSERT INTO `item` VALUES ('400100064', '4级4号符文');
INSERT INTO `item` VALUES ('400100065', '4级5号符文');
INSERT INTO `item` VALUES ('400100066', '4级6号符文');
INSERT INTO `item` VALUES ('400100067', '4级1号符文');
INSERT INTO `item` VALUES ('400100068', '4级2号符文');
INSERT INTO `item` VALUES ('400100069', '4级3号符文');
INSERT INTO `item` VALUES ('400100070', '4级4号符文');
INSERT INTO `item` VALUES ('400100071', '4级5号符文');
INSERT INTO `item` VALUES ('400100072', '4级6号符文');
INSERT INTO `item` VALUES ('400100073', '4级1号符文');
INSERT INTO `item` VALUES ('400100074', '4级2号符文');
INSERT INTO `item` VALUES ('400100075', '4级3号符文');
INSERT INTO `item` VALUES ('400100076', '4级4号符文');
INSERT INTO `item` VALUES ('400100077', '4级5号符文');
INSERT INTO `item` VALUES ('400100078', '4级6号符文');
INSERT INTO `item` VALUES ('400100079', '4级1号符文');
INSERT INTO `item` VALUES ('400100080', '4级2号符文');
INSERT INTO `item` VALUES ('400100081', '4级3号符文');
INSERT INTO `item` VALUES ('400100082', '4级4号符文');
INSERT INTO `item` VALUES ('400100083', '4级5号符文');
INSERT INTO `item` VALUES ('400100084', '4级6号符文');
INSERT INTO `item` VALUES ('400100085', '4级1号符文');
INSERT INTO `item` VALUES ('400100086', '4级2号符文');
INSERT INTO `item` VALUES ('400100087', '4级3号符文');
INSERT INTO `item` VALUES ('400100088', '4级4号符文');
INSERT INTO `item` VALUES ('400100089', '4级5号符文');
INSERT INTO `item` VALUES ('400100090', '4级6号符文');
INSERT INTO `item` VALUES ('400100091', '3级1号符文');
INSERT INTO `item` VALUES ('400100092', '3级2号符文');
INSERT INTO `item` VALUES ('400100093', '3级3号符文');
INSERT INTO `item` VALUES ('400100094', '3级4号符文');
INSERT INTO `item` VALUES ('400100095', '3级5号符文');
INSERT INTO `item` VALUES ('400100096', '3级6号符文');
INSERT INTO `item` VALUES ('400100097', '3级1号符文');
INSERT INTO `item` VALUES ('400100098', '3级2号符文');
INSERT INTO `item` VALUES ('400100099', '3级3号符文');
INSERT INTO `item` VALUES ('400100100', '3级4号符文');
INSERT INTO `item` VALUES ('400100101', '3级5号符文');
INSERT INTO `item` VALUES ('400100102', '3级6号符文');
INSERT INTO `item` VALUES ('400100103', '3级1号符文');
INSERT INTO `item` VALUES ('400100104', '3级2号符文');
INSERT INTO `item` VALUES ('400100105', '3级3号符文');
INSERT INTO `item` VALUES ('400100106', '3级4号符文');
INSERT INTO `item` VALUES ('400100107', '3级5号符文');
INSERT INTO `item` VALUES ('400100108', '3级6号符文');
INSERT INTO `item` VALUES ('400100109', '3级1号符文');
INSERT INTO `item` VALUES ('400100110', '3级2号符文');
INSERT INTO `item` VALUES ('400100111', '3级3号符文');
INSERT INTO `item` VALUES ('400100112', '3级4号符文');
INSERT INTO `item` VALUES ('400100113', '3级5号符文');
INSERT INTO `item` VALUES ('400100114', '3级6号符文');
INSERT INTO `item` VALUES ('400100115', '3级1号符文');
INSERT INTO `item` VALUES ('400100116', '3级2号符文');
INSERT INTO `item` VALUES ('400100117', '3级3号符文');
INSERT INTO `item` VALUES ('400100118', '3级4号符文');
INSERT INTO `item` VALUES ('400100119', '3级5号符文');
INSERT INTO `item` VALUES ('400100120', '3级6号符文');
INSERT INTO `item` VALUES ('400100121', '2级1号符文');
INSERT INTO `item` VALUES ('400100122', '2级2号符文');
INSERT INTO `item` VALUES ('400100123', '2级3号符文');
INSERT INTO `item` VALUES ('400100124', '2级4号符文');
INSERT INTO `item` VALUES ('400100125', '2级5号符文');
INSERT INTO `item` VALUES ('400100126', '2级6号符文');
INSERT INTO `item` VALUES ('400100127', '2级1号符文');
INSERT INTO `item` VALUES ('400100128', '2级2号符文');
INSERT INTO `item` VALUES ('400100129', '2级3号符文');
INSERT INTO `item` VALUES ('400100130', '2级4号符文');
INSERT INTO `item` VALUES ('400100131', '2级5号符文');
INSERT INTO `item` VALUES ('400100132', '2级6号符文');
INSERT INTO `item` VALUES ('400100133', '2级1号符文');
INSERT INTO `item` VALUES ('400100134', '2级2号符文');
INSERT INTO `item` VALUES ('400100135', '2级3号符文');
INSERT INTO `item` VALUES ('400100136', '2级4号符文');
INSERT INTO `item` VALUES ('400100137', '2级5号符文');
INSERT INTO `item` VALUES ('400100138', '2级6号符文');
INSERT INTO `item` VALUES ('400100139', '1级1号符文');
INSERT INTO `item` VALUES ('400100140', '1级2号符文');
INSERT INTO `item` VALUES ('400100141', '1级3号符文');
INSERT INTO `item` VALUES ('400100142', '1级4号符文');
INSERT INTO `item` VALUES ('400100143', '1级5号符文');
INSERT INTO `item` VALUES ('400100144', '1级6号符文');
INSERT INTO `item` VALUES ('400100145', '1级1号符文');
INSERT INTO `item` VALUES ('400100146', '1级2号符文');
INSERT INTO `item` VALUES ('400100147', '1级3号符文');
INSERT INTO `item` VALUES ('400100148', '1级4号符文');
INSERT INTO `item` VALUES ('400100149', '1级5号符文');
INSERT INTO `item` VALUES ('400100150', '1级6号符文');
INSERT INTO `item` VALUES ('400100151', '1级1号符文');
INSERT INTO `item` VALUES ('400100152', '1级2号符文');
INSERT INTO `item` VALUES ('400100153', '1级3号符文');
INSERT INTO `item` VALUES ('400100154', '1级4号符文');
INSERT INTO `item` VALUES ('400100155', '1级5号符文');
INSERT INTO `item` VALUES ('400100156', '1级6号符文');


DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(255) DEFAULT NULL COMMENT '用户id',
  `server_id` int(11) DEFAULT NULL COMMENT '登录服务器',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台',
  `register_time` datetime DEFAULT NULL COMMENT '帐号注册时间',
  `last_log_time` datetime DEFAULT NULL COMMENT '帐号最近登录的时间',
  `isEffective` int(255) unsigned zerofill NOT NULL COMMENT '是否是有效的用户 1 有效 0 无效 （登录游戏至少有进行3局关卡战斗的用户）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL COMMENT '菜单栏位',
  `url` varchar(255) DEFAULT NULL COMMENT '对应的url',
  `pid` int(11) DEFAULT NULL COMMENT '父节点id',
  `authority` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '首页', null, '-1', '0');
INSERT INTO `menu` VALUES ('2', '服务器', null, '-1', '2');
INSERT INTO `menu` VALUES ('3', '服务器列表', 'server/list?mainView=true', '2', '2');
INSERT INTO `menu` VALUES ('4', '区列表', 'server/section/list?mainView=true', '2', '2');
INSERT INTO `menu` VALUES ('5', '版本号', 'server/version/list?mainView=true', '2', '2');
INSERT INTO `menu` VALUES ('6', '资源包', 'server/package/list?mainView=true', '2', '2');
INSERT INTO `menu` VALUES ('7', '操作', null, '-1', '2');
INSERT INTO `menu` VALUES ('8', '邮件', 'mail/list?mainView=true', '7', '2');
INSERT INTO `menu` VALUES ('9', 'GM指令', 'mail/gm/index?mainView=true', '7', '2');
INSERT INTO `menu` VALUES ('10', '游戏参数', 'mail/param/index?mainView=true', '7', '2');
INSERT INTO `menu` VALUES ('11', '虚拟支付', 'mail/vpay/index?mainView=true', '7', '2');
INSERT INTO `menu` VALUES ('12', '公告', null, '-1', '2');
INSERT INTO `menu` VALUES ('13', '黑名单', 'notice/blacklist/list?mainView=true', '12', '2');
INSERT INTO `menu` VALUES ('14', '走马灯', 'notice/list?mainView=true', '12', '2');
INSERT INTO `menu` VALUES ('15', '登陆公告', 'notice/notice?mainView=true', '12', '2');
INSERT INTO `menu` VALUES ('16', '统计', null, '-1', '3');
INSERT INTO `menu` VALUES ('17', '总统计', 'statistics/total?mainView=true', '16', '3');
INSERT INTO `menu` VALUES ('18', '服务器统计', 'statistics/server?mainView=true', '16', '3');
INSERT INTO `menu` VALUES ('19', '系统log', 'statistics/systemLog/show?mainView=true', '16', '3');
INSERT INTO `menu` VALUES ('20', '汇总表', null, '16', '3');
INSERT INTO `menu` VALUES ('21', '日报汇总表', 'statistics/channelReport/total?type=2&mainView=true', '20', '3');
INSERT INTO `menu` VALUES ('22', '渠道汇总表', 'statistics/channelReport/total?type=1&mainView=true', '20', '3');
INSERT INTO `menu` VALUES ('23', '新服数据指标', null, '16', '3');
INSERT INTO `menu` VALUES ('24', '新服收入报表', 'statistics/totalByTime/total?type=3&mainView=true', '23', '3');
INSERT INTO `menu` VALUES ('25', '新服活跃数', 'statistics/totalByTime/total?type=4&mainView=true', '23', '3');
INSERT INTO `menu` VALUES ('26', '用户转化指标', 'statistics/channelReport/total?type=5&mainView=true', '23', '3');
INSERT INTO `menu` VALUES ('27', '充值玩家留存分析表', 'statistics/channelReport/total?type=6&mainView=true', '23', '3');
INSERT INTO `menu` VALUES ('28', '用户LTV分析表', 'statistics/channelReport/total?type=7&mainView=true', '23', '3');
INSERT INTO `menu` VALUES ('30', '活动', 'activity/list?mainView=true', '-1', '2');
INSERT INTO `menu` VALUES ('31', '权限', null, '-1', '0');
INSERT INTO `menu` VALUES ('32', '封号', 'power/banned/list?mainView=true', '31', '2');
INSERT INTO `menu` VALUES ('33', '维护账号', 'power/vindicator/list?mainView=true', '31', '2');
INSERT INTO `menu` VALUES ('34', '读取角色信息', 'power/info/show?mainView=true', '31', '2');
INSERT INTO `menu` VALUES ('35', '日志', 'power/log/show?mainView=true', '31', '0');
INSERT INTO `menu` VALUES ('36', 'bug查看', 'power/bug/show?mainView=true', '31', '2');
INSERT INTO `menu` VALUES ('37', '服务器操作', 'power/server/show?mainView=true', '31', '2');
INSERT INTO `menu` VALUES ('38', '修改玩家账号密码', 'power/accountPassword/show?mainView=true', '31', '2');
INSERT INTO `menu` VALUES ('39', '管理', null, '-1', '0');
INSERT INTO `menu` VALUES ('40', '账号', 'account/list?mainView=true', '39', '10');
INSERT INTO `menu` VALUES ('41', '修改密碼', 'account/changePwd?mainView=true', '39', '0');
INSERT INTO `menu` VALUES ('42', '退出', 'login/tologout', '39', '0');
INSERT INTO `menu` VALUES ('43', '统计分析数据', null, '16', '3');
INSERT INTO `menu` VALUES ('44', '日活跃分布图(事实数据)', 'statistics/activeReport/total?type=8&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('45', '小时在线人数（分区按日）', 'statistics/channelReport/total?type=9&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('46', '充值区间分析', 'statistics/channelReport/total?type=10&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('48', '新增付费人数及金额(分区按日)', 'statistics/channelReport/total?type=11&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('49', '老玩家付费人数及金额(分区按日)', 'statistics/channelReport/total?type=12&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('50', '充值汇总分服查询', 'statistics/inComeReport/total?type=13&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('51', '充值汇总分服查询(子表)', 'statistics/inComeReport2/total?type=14&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('52', '任务引导分析', 'statistics/channelReport/total?type=15&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('53', '玩家首付周期', 'statistics/channelReport/total?type=16&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('54', '玩家首付等级', 'statistics/channelReport/total?type=15&mainView=true', '43', '3');
INSERT INTO `menu` VALUES ('55', '副本挑战', '', '43', '3');
INSERT INTO `menu` VALUES ('56', '玩家水晶消耗', null, '43', '3');
INSERT INTO `menu` VALUES ('57', '全服玩家消费数据', 'statistics/channelReport/total?type=18&mainView=true', '56', '3');
INSERT INTO `menu` VALUES ('58', 'vip玩家消费数据', 'statistics/channelReport/total?type=19&mainView=true', '56', '3');
INSERT INTO `menu` VALUES ('59', '详细物品消耗及产出', '', '43', '3');
INSERT INTO `menu` VALUES ('60', '详细物品消耗统计', 'statistics/channelReport/total?type=15&mainView=true', '59', '3');
INSERT INTO `menu` VALUES ('61', '详细物品产出统计', 'statistics/channelReport/total?type=15&mainView=true', '59', '3');
INSERT INTO `menu` VALUES ('62', '货币产出及消耗汇总', null, '43', '3');
INSERT INTO `menu` VALUES ('63', '水晶产出&消耗(等级汇总)', 'statistics/crystalLevel/total?type=20&mainView=true', '62', '3');
INSERT INTO `menu` VALUES ('64', '水晶产出详细统计', 'statistics/crystalOutput/total?type=21&mainView=true', '62', '3');
INSERT INTO `menu` VALUES ('65', '物品计费档次购买分析', 'statistics/channelReport/total?type=15&mainView=true', '62', '3');
INSERT INTO `menu` VALUES ('66', '冒险副本', 'statistics/FBReport/total?type=17&fb=1&mainView=true', '55', '3');
INSERT INTO `menu` VALUES ('67', '龙之回廊', 'statistics/FBReport/total?type=17&fb=2&mainView=true', '55', '3');
INSERT INTO `menu` VALUES ('68', '十二星宫', 'statistics/FBReport/total?type=17&fb=3&mainView=true', '55', '3');
INSERT INTO `menu` VALUES ('69', '辉晶矿产出详细统计', 'statistics/oreOutput/total?type=22&mainView=true', '62', '3');
INSERT INTO `menu` VALUES ('70', '计费档次购买分析(水晶)', 'statistics/crystalCost/total?type=23&mainView=true', '62', '3');
INSERT INTO `menu` VALUES ('71', '货币(产出&消耗)汇总', 'statistics/moneySummary/total?type=24&mainView=true', '62', '3');

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
INSERT INTO `report_sql` VALUES ('1', '(  SELECT  account.platform,  T.newActiveUser,  T.newRegisterUser,  T.absNewUser,  T2.paySum,  T2.payUser,  T2.payCount,  FORMAT(T2.payCount / T2.payUser, 2) AS avgPay,  FORMAT(T2.paySum / T2.payUser, 2) AS arppu,  T3.newPay,  T3.newPPayCount,  FORMAT(  T3.newPay / T3.newPPayCount,  2  ) AS arpu,  FORMAT(T1.retained1, 2) AS retained1,  FORMAT(T1.retained3, 2) AS retained3,  FORMAT(T1.retained7, 2) AS retained7,  FORMAT(T1.retained14, 2) AS retained14,  FORMAT(T1.retained30, 2) AS retained30  FROM  account  LEFT JOIN (  SELECT  t2.platform,  COUNT(DISTINCT t2.uuid) AS newActiveUser,  COUNT(DISTINCT t2.id) AS newRegisterUser,  COUNT(DISTINCT t2.uuid, t2.id) AS absNewUser  FROM  (  SELECT  id,  platform,  REPLACE (  REPLACE (  systemInfo,  SUBSTR(  systemInfo,  LOCATE(\"&gpuID\", systemInfo),  CHAR_LENGTH(systemInfo) - LOCATE(\"&gpuID\", systemInfo) + 1  ),  \"\"  ),  SUBSTR(  systemInfo,  1,  LOCATE(\"deviceUI=\", systemInfo) + 8  ),  \"\"  ) AS uuid  FROM  account  WHERE  account.create_time {0}  AND account.prev_srv_id LIKE \"{1}\"  ) AS t2  GROUP BY  t2.platform  ) AS T ON T.platform = account.platform  LEFT JOIN (  SELECT  platform,  SUM(retained1) / SUM(dru) * 100 AS retained1,  SUM(retained3) / SUM(dru) * 100 AS retained3,  SUM(retained7) / SUM(dru) * 100 AS retained7,  SUM(retained14) / SUM(dru) * 100 AS retained14,  SUM(retained30) / SUM(dru) * 100 AS retained30  FROM  retention  WHERE  create_date {0}  AND server_id LIKE \"{1}\"  GROUP BY  platform  ) AS T1 ON T1.platform = account.platform  LEFT JOIN (  SELECT  t.platform,  sum(t.amount) AS paySum,  count(DISTINCT t.account_id) AS payUser,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.platform  ) AS T2 ON T2.platform = account.platform  LEFT JOIN (  SELECT  SUM(V2.payNew) AS newPay,  V.platform,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {0}  AND prev_srv_id LIKE \"{1}\"  GROUP BY  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.platform  ) AS T3 ON T3.platform = account.platform  WHERE  account.platform LIKE \"{2}\"  GROUP BY  account.platform ) UNION ALL  (  SELECT  \"总计:\" AS platform,  SUM(r.newActiveUser) AS newActiveUser,  SUM(r.newRegisterUser) AS newRegisterUser,  SUM(r.absNewUser) AS absNewUser,  SUM(r.paySum) AS paySum,  SUM(r.payUser) AS payUser,  SUM(r.payCount) AS payCount,  FORMAT(  SUM(r.payCount) / SUM(r.payUser),  2  ) AS avgPay,  FORMAT(  SUM(r.paySum) / SUM(r.payUser),  2  ) AS arppu,  SUM(r.newPay) AS newPay,  SUM(r.newPPayCount) AS newPPayCount,  FORMAT(  SUM(r.newPay) / SUM(r.newPPayCount),  2  ) AS arpu,  FORMAT(  SUM(r.retained1) / SUM(r.dru) * 100,  2  ) AS retained1,  FORMAT(  SUM(r.retained3) / SUM(r.dru) * 100,  2  ) AS retained3,  FORMAT(  SUM(r.retained7) / SUM(r.dru) * 100,  2  ) AS retained7,  FORMAT(  SUM(r.retained14) / SUM(r.dru) * 100,  2  ) AS retained14,  FORMAT(  SUM(r.retained30) / SUM(r.dru) * 100,  2  ) AS retained30  FROM  (  (  SELECT  account.platform,  T.newActiveUser,  T.newRegisterUser,  T.absNewUser,  T2.paySum,  T2.payUser,  T3.newPay,  T3.newPPayCount,  T2.payCount,  T1.retained1,  T1.retained3,  T1.retained7,  T1.retained14,  T1.retained30,  T1.dru  FROM  account  LEFT JOIN (  SELECT  t2.platform,  COUNT(DISTINCT t2.uuid) AS newActiveUser,  COUNT(DISTINCT t2.id) AS newRegisterUser,  COUNT(DISTINCT t2.uuid, t2.id) AS absNewUser  FROM  (  SELECT  id,  platform,  REPLACE (  REPLACE (  systemInfo,  SUBSTR(  systemInfo,  LOCATE(\"&gpuID\", systemInfo),  CHAR_LENGTH(systemInfo) - LOCATE(\"&gpuID\", systemInfo) + 1  ),  \"\"  ),  SUBSTR(  systemInfo,  1,  LOCATE(\"deviceUI=\", systemInfo) + 8  ),  \"\"  ) AS uuid  FROM  account  WHERE  account.create_time {0}  AND account.prev_srv_id LIKE \"{1}\"  ) AS t2  GROUP BY  t2.platform  ) AS T ON T.platform = account.platform  LEFT JOIN (  SELECT  platform,  SUM(retained1) AS retained1,  SUM(retained3) AS retained3,  SUM(retained7) AS retained7,  SUM(retained14) AS retained14,  SUM(retained30) AS retained30,  SUM(dru) AS dru  FROM  retention  WHERE  create_date  {0}  AND server_id LIKE \"{1}\"  GROUP BY  platform  ) AS T1 ON T1.platform = account.platform  LEFT JOIN (  SELECT  t.platform,  sum(t.amount) AS paySum,  count(DISTINCT t.account_id) AS payUser,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.platform  ) AS T2 ON T2.platform = account.platform  LEFT JOIN (  SELECT  SUM(V2.payNew) AS newPay,  V.platform,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {0}  AND prev_srv_id LIKE \"{1}\"  GROUP BY  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.server_id LIKE \"{1}\"  GROUP BY  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.platform  ) AS T3 ON T3.platform = account.platform  WHERE  account.platform LIKE \"{2}\"  GROUP BY  account.platform  )  ) AS r  ) \r\n', 'platform,newActiveUser,newRegisterUser,absNewUser,paySum,payUser,payCount,avgPay,arppu,newPay,newPPayCount,arpu,retained1,retained3,retained7,retained14,retained30', '渠道,设备新增,新增注册,绝对新增,收入,充值人数,充值次数,人均充值次数,arppu,新增付费金额,新增付费人数,注册arpu,次日留存%,3日留存%,7日留存%,14日留存%,30日留存%');
INSERT INTO `report_sql` VALUES ('2', '(  SELECT  T6.t_time,  T4.DAU,  T.newActiveUser,  T.newRegisterUser,  T.absNewUser,  SUM(T5.acu) AS ACU,  T5.pcu,  T2.payUser1,  T2.paySum,  FORMAT(  T3.newPay / T3.newPPayCount,  2  ) AS arpu,  FORMAT(T2.paySum / T2.payUser1, 2) AS arppu1,  FORMAT(T2.paySum / T4.DAU, 2) AS DAUARP,  FORMAT((T2.payUser1 / T4.DAU) * 100, 2) AS payRate,  T3.newPay,  T3.newPPayCount,  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0) AS oldPaySum,  IFNULL(T2.payUser1, 0) - IFNULL(T3.newPPayCount, 0) AS oldPayCount,  T1.retained1,  T1.retained3,  T1.retained7,  T1.retained14,  T1.retained30  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  DATE_FORMAT(last_log_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT account_id) AS DAU  FROM  login_log  WHERE  platform LIKE \"{3}\"  AND last_log_time {1}  AND server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T4 ON T4.t_time = T6.t_time  LEFT JOIN (  SELECT  t2.t_time,  COUNT(DISTINCT t2.uuid) AS newActiveUser,  COUNT(DISTINCT t2.id) AS newRegisterUser,  COUNT(DISTINCT t2.uuid, t2.id) AS absNewUser  FROM  (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (  systemInfo,  SUBSTR(  systemInfo,  LOCATE(\"&gpuID\", systemInfo),  CHAR_LENGTH(systemInfo) - LOCATE(\"&gpuID\", systemInfo) + 1  ),  \"\"  ),  SUBSTR(  systemInfo,  1,  LOCATE(\"deviceUI=\", systemInfo) + 8  ),  \"\"  ) AS uuid  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"{2}\"  ) AS t2  GROUP BY  t2.t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(create_date, \"%Y-%m-%d\") AS t_time,  FORMAT(  SUM(retained1) / SUM(dru) * 100,  2  ) AS retained1,  FORMAT(  SUM(retained3) / SUM(dru) * 100,  2  ) AS retained3,  FORMAT(  SUM(retained7) / SUM(dru) * 100,  2  ) AS retained7,  FORMAT(  SUM(retained14) / SUM(dru) * 100,  2  ) AS retained14,  FORMAT(  SUM(retained30) / SUM(dru) * 100,  2  ) AS retained30  FROM  retention  WHERE  create_date {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time  ) AS T1 ON T1.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_id) AS payUser1,  COUNT(t.account_id) AS payUser2,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (  SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time  ) AS T3 ON T3.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  COUNT(playCount) AS acu,  MAX(playCount) AS pcu  FROM  acu  WHERE  log_time {1}  AND acu.platform LIKE \"{3}\"  AND acu.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T5 ON T5.t_time = T6.t_time  GROUP BY  T6.t_time ) UNION  (  SELECT  \"总计:\" AS t_time,  SUM(T4.DAU) AS DAU,  SUM(T.newActiveUser) AS newActiveUser,  SUM(T.newRegisterUser) AS newActiveUser,  SUM(T.absNewUser) absNewUser,  SUM(T5.acu) AS ACU,  SUM(T5.pcu) AS pcu,  SUM(T2.payUser1) AS payUser1,  SUM(T2.paySum) AS paySum,  FORMAT(  SUM(T3.newPay) / SUM(T3.newPPayCount),  2  ) AS arpu,  FORMAT(  SUM(T2.paySum) / SUM(T2.payUser1),  2  ) AS arppu1,  FORMAT(  SUM(T2.paySum) / SUM(T4.DAU),  2  ) AS DAUARP,  FORMAT(  SUM(T2.payUser1) / SUM(T4.DAU) * 100,  2  ) AS payRate,  SUM(T3.newPay) AS newPay,  SUM(T3.newPPayCount) AS newPPayCount,  IFNULL(SUM(T2.paySum), 0) - IFNULL(SUM(T3.newPay), 0) AS oldPaySum,  IFNULL(SUM(T2.payUser1), 0) - IFNULL(SUM(T3.newPPayCount), 0) AS oldPayCount,  FORMAT(  SUM(T1.retained1) / SUM(dru) * 100,  2  ) AS retained1,  FORMAT(  SUM(T1.retained3) / SUM(dru) * 100,  2  ) AS retained3,  FORMAT(  SUM(T1.retained7) / SUM(dru) * 100,  2  ) AS retained7,  FORMAT(  SUM(T1.retained14) / SUM(dru) * 100,  2  ) AS retained14,  FORMAT(  SUM(T1.retained30) / SUM(dru) * 100,  2  ) AS retained30  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  DATE_FORMAT(last_log_time, \"%Y-%m-%d\") AS t_time,  COUNT(DISTINCT account_id) AS DAU  FROM  login_log  WHERE  platform LIKE \"{3}\"  AND last_log_time {1}  AND server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T4 ON T4.t_time = T6.t_time  LEFT JOIN (  SELECT  t2.t_time,  COUNT(DISTINCT t2.uuid) AS newActiveUser,  COUNT(DISTINCT t2.id) AS newRegisterUser,  COUNT(DISTINCT t2.uuid, t2.id) AS absNewUser  FROM  (  SELECT  id,  DATE_FORMAT(create_time, \"%Y-%m-%d\") AS t_time,  REPLACE (  REPLACE (  systemInfo,  SUBSTR(  systemInfo,  LOCATE(\"&gpuID\", systemInfo),  CHAR_LENGTH(systemInfo) - LOCATE(\"&gpuID\", systemInfo) + 1  ),  \"\"  ),  SUBSTR(  systemInfo,  1,  LOCATE(\"deviceUI=\", systemInfo) + 8  ),  \"\"  ) AS uuid  FROM  account  WHERE  account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"{2}\"  ) AS t2  GROUP BY  t2.t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(create_date, \"%Y-%m-%d\") AS t_time,  SUM(retained1) AS retained1,  SUM(retained3) AS retained3,  SUM(retained7) AS retained7,  SUM(retained14) AS retained14,  SUM(retained30) AS retained30,  SUM(dru) AS dru  FROM  retention  WHERE  create_date {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time  ) AS T1 ON T1.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_id) AS payUser1,  COUNT(t.account_id) AS payUser2,  count(*) AS payCount  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (  SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time  ) AS T3 ON T3.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time,  COUNT(playCount) AS acu,  MAX(playCount) AS pcu  FROM  acu  WHERE  log_time {1}  AND acu.platform LIKE \"{3}\"  AND acu.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T5 ON T5.t_time = T6.t_time  ) \r\n', 't_time,DAU,newActiveUser,newRegisterUser,absNewUser,ACU,pcu,payUser1,paySum,arpu,arppu1,DAUARP,payRate,newPay,newPPayCount,oldPaySum,oldPayCount,retained1,retained3,retained7,retained14,retained30', '时间,DAU,设备新增,新增注册(含衮服),绝对新增,ACU,PCU,付费玩家数,付费金额,ARPU,ARPPU,DAUARPU,付费率,新付费总额,新付费玩家数,老用户付费金额,老付费玩家数,次日留存,3日留存,7日留存,14日留存,30日留存');
INSERT INTO `report_sql` VALUES ('3', '(  SELECT  n.s_name,  DATE_FORMAT(n.open_time, \"%Y-%m-%d\") AS t_time,  n.reg_count_day2,  n.reg_count_day3,  n.reg_count_day7,  n.reg_pay_count_day2,  FORMAT(  n.reg_pay_count_day2 / n.reg_count_day2,  2  ) AS payRate2,  (  n.paySum_day1 + n.paySum_day2  ) / n.reg_pay_count_day2 AS arup2,  n.reg_pay_count_day3,  FORMAT(  n.reg_pay_count_day3 / n.reg_count_day3,  2  ) AS payRate3,  (  n.paySum_day1 + n.paySum_day2 + n.paySum_day3  ) / n.reg_pay_count_day3 AS arup3,  n.reg_pay_count_day7,  FORMAT(  n.reg_pay_count_day7 / n.reg_count_day7,  2  ) AS payRate7,  (  IFNULL(n.paySum_day1, 0) + IFNULL(n.paySum_day2, 0) + IFNULL(n.paySum_day3, 0) + IFNULL(n.paySum_day4, 0) + IFNULL(n.paySum_day5, 0) + IFNULL(n.paySum_day6, 0) + IFNULL(n.paySum_day7, 0)  ) / IFNULL(n.reg_pay_count_day7, 0) AS arup7,  n.paySum_day1,  n.paySum_day2,  n.paySum_day3,  n.paySum_day4,  n.paySum_day5,  n.paySum_day6,  n.paySum_day7,  (  IFNULL(n.paySum_day1, 0) + IFNULL(n.paySum_day2, 0) + IFNULL(n.paySum_day3, 0) + IFNULL(n.paySum_day4, 0) + IFNULL(n.paySum_day5, 0) + IFNULL(n.paySum_day6, 0) + IFNULL(n.paySum_day7, 0)  ) AS sum7Day  FROM  ns_income_summary AS n  WHERE  n.open_time {1} ) UNION ALL  (  SELECT  \"总计\" AS s_name,  \"\" AS t_time,  T.reg_count_day2,  T.reg_count_day3,  T.reg_count_day7,  T.reg_pay_count_day2,  FORMAT(  T.reg_pay_count_day2 / T.reg_count_day2,  2  ) AS payRate2,  (  T.paySum_day1 + T.paySum_day2  ) / T.reg_pay_count_day2 AS arup2,  T.reg_pay_count_day3,  FORMAT(  T.reg_pay_count_day3 / T.reg_count_day3,  2  ) AS payRate3,  (  T.paySum_day1 + T.paySum_day2 + T.paySum_day3  ) / T.reg_pay_count_day3 AS arup3,  T.reg_pay_count_day7,  FORMAT(  T.reg_pay_count_day7 / T.reg_count_day7,  2  ) AS payRate7,  (  T.paySum_day1 + T.paySum_day2 + T.paySum_day3 + T.paySum_day4 + T.paySum_day5 + T.paySum_day6 + T.paySum_day7  ) / T.reg_pay_count_day7 AS arup7,  T.paySum_day1,  T.paySum_day2,  T.paySum_day3,  T.paySum_day4,  T.paySum_day5,  T.paySum_day6,  T.paySum_day7,  (  T.paySum_day1 + T.paySum_day2 + T.paySum_day3 + T.paySum_day4 + T.paySum_day5 + T.paySum_day6 + T.paySum_day7  ) AS sum7Day  FROM  (  SELECT  IFNULL(SUM(n.reg_count_day2), 0) AS reg_count_day2,  IFNULL(SUM(n.reg_count_day3), 0) AS reg_count_day3,  IFNULL(SUM(n.reg_count_day7), 0) AS reg_count_day7,  IFNULL(  SUM(n.reg_pay_count_day2),  0  ) AS reg_pay_count_day2,  IFNULL(  SUM(n.reg_pay_count_day3),  0  ) AS reg_pay_count_day3,  IFNULL(  SUM(n.reg_pay_count_day7),  0  ) AS reg_pay_count_day7,  IFNULL(SUM(n.paySum_day1), 0) AS paySum_day1,  IFNULL(SUM(n.paySum_day2), 0) AS paySum_day2,  IFNULL(SUM(n.paySum_day3), 0) AS paySum_day3,  IFNULL(SUM(n.paySum_day4), 0) AS paySum_day4,  IFNULL(SUM(n.paySum_day5), 0) AS paySum_day5,  IFNULL(SUM(n.paySum_day6), 0) AS paySum_day6,  IFNULL(SUM(n.paySum_day7), 0) AS paySum_day7  FROM  ns_income_summary AS n  WHERE  n.open_time {1}  ) AS T  ) \r\n', 's_name,t_time,reg_count_day2,reg_count_day3,reg_count_day7,reg_pay_count_day2,payRate2,arup2,reg_pay_count_day3,payRate3,arup3,reg_pay_count_day7,payRate7,arup7,paySum_day1,paySum_day2,paySum_day3,paySum_day4,paySum_day5,paySum_day6,paySum_day7,sum7Day', '服务器,开服日期,2日注册,3日注册,7日注册,2日付费人数,2日付费率,2日ARPPU,3日付费人数,3日付费率,3日ARPPU,7日付费人数,7日付费率,7日ARPPU,开服第1天,开服第2天,开服第3天,开服第4天,开服第5天,开服第6天,开服第7天,七日收入总计');
INSERT INTO `report_sql` VALUES ('4', '(  SELECT  n.s_name,  DATE_FORMAT(n.open_time, \"%Y-%m-%d\") AS t_time,  n.dau_day2,  n.dau_day3,  n.dau_day7,  FORMAT(  IFNULL(n.dau_pay_day2, 0) / IFNULL(n.dau_day2, 0),  2  ) AS dauRate2,  FORMAT(  IFNULL(n.dau_pay_day3, 0) / IFNULL(n.dau_day3, 0),  2  ) AS dauRate3,  FORMAT(  IFNULL(n.dau_pay_day7, 0) / IFNULL(n.dau_day7, 0),  2  ) AS dauRate7  FROM  ns_dau_summary AS n  WHERE  n.open_time {0} ) UNION ALL  (  SELECT  \"总计:\" AS s_name,  \"\" AS t_time,  T.dau_day2,  T.dau_day3,  T.dau_day7,  FORMAT(  T.dau_pay_day2 / T.dau_day2,  2  ) AS dauRate2,  FORMAT(  T.dau_pay_day3 / T.dau_day3,  2  ) AS dauRate3,  FORMAT(  T.dau_pay_day7 / T.dau_day7,  2  ) AS dauRate7  FROM  (  SELECT  SUM(n.dau_day2) AS dau_day2,  SUM(n.dau_day3) AS dau_day3,  SUM(n.dau_day7) AS dau_day7,  SUM(n.dau_pay_day2) AS dau_pay_day2,  SUM(n.dau_pay_day3) AS dau_pay_day3,  SUM(n.dau_pay_day7) AS dau_pay_day7  FROM  ns_dau_summary AS n  WHERE  n.open_time {0}  ) AS T  ) \r\n', 's_name,t_time,dau_day2,dau_day3,dau_day7,dauRate2,dauRate3,dauRate7', '服务器,开服日期,两日活跃人数,三日活跃人数,七日活跃人数,两日活跃付费率,三日活跃付费率,七日活跃付费率');
INSERT INTO `report_sql` VALUES ('5', '(  SELECT  T. NAME,  T.t_time,  A.newRegisterUser,  L.activeCount,  FORMAT(  L.activeCount / A.newRegisterUser * 100,  2  ) AS dauRate,  L2.effectiveActiveCount,  FORMAT(  L2.effectiveActiveCount / L.activeCount * 100,  2  ) AS effectiveRegRate,  P.payUserCount,  FORMAT(  P.payUserCount / L2.effectiveActiveCount * 100,  2  ) AS payRate  FROM  (  SELECT  DATE_FORMAT(t.open_time, \"%Y-%m-%d\") AS t_time,  t. NAME,  t.id  FROM  `server` AS t  WHERE  t.platforms LIKE \"{2}\"  AND t.id LIKE \"{1}\"  GROUP BY  t.id  ) AS T  LEFT JOIN (  SELECT  COUNT(DISTINCT account.id) AS newRegisterUser,  account.prev_srv_id  FROM  account  WHERE  account.create_time {0}  AND account.platform LIKE \"{2}\"  AND account.prev_srv_id LIKE \"{1}\"  GROUP BY  account.prev_srv_id  ) AS A ON A.prev_srv_id = T.id  LEFT JOIN (  SELECT  COUNT(DISTINCT account.id) AS activeCount,  account.prev_srv_id  FROM  account  WHERE  account.login_time {0}  AND account.platform LIKE \"{2}\"  AND account.prev_srv_id LIKE \"{1}\"  GROUP BY  account.prev_srv_id  ) AS L ON L.prev_srv_id = T.id  LEFT JOIN (  SELECT  server_id,  count(DISTINCT t.account_id) AS payUserCount  FROM  `order` AS t  WHERE  t.finish_time {0}  AND t.platform LIKE \"{2}\"  AND t.server_id LIKE \"{1}\"  GROUP BY  t.server_id  ) AS P ON P.server_id = T.id  LEFT JOIN (  SELECT  COUNT(DISTINCT account.id) AS effectiveActiveCount,  account.prev_srv_id  FROM  account  WHERE  account.login_time {0}  AND account.platform LIKE \"{2}\"  AND account.prev_srv_id LIKE \"{1}\"  AND account.isEffective LIKE \'1\'  GROUP BY  account.prev_srv_id  ) AS L2 ON L2.prev_srv_id = T.id  GROUP BY  T. NAME,  T.id ) \r\n', 'NAME,t_time,newRegisterUser,activeCount,dauRate,effectiveActiveCount,effectiveRegRate,payUserCount,payRate', '服务器,开服日期,注册用户数,活跃用户数,活跃转化率,有效注册用户数,有效注册率,付费用户数,付费转化率');
INSERT INTO `report_sql` VALUES ('6', 'SELECT  T1.t_time,  T.new_account,  T.new_device,  T.f_pay,  T.f_pay_count01 / T.f_pay AS f_retention,  T.now_pay,  T.pay_next_day_login / T.now_pay AS p_retention,  T.new_device_pay,  T.new_device_pay_sum,  T.dau,  T.dau_pay,  T.dau_pay / T.dau * 100 AS dauRate,  T.new_device_pay_sum / T.new_device_pay AS arpu,  T.new_device_pay_sum / T.new_device AS arppu FROM  ({0}) AS T1 LEFT JOIN (  SELECT  DATE_FORMAT(create_date, \"%Y-%m-%d\") AS t_time,  SUM(new_account) AS new_account,  SUM(new_device) AS new_device,  SUM(f_pay) AS f_pay,  SUM(f_pay_count01) AS f_pay_count01,  SUM(now_pay) AS now_pay,  SUM(pay_next_day_login) AS pay_next_day_login,  SUM(new_device_pay) AS new_device_pay,  SUM(new_device_pay_sum) AS new_device_pay_sum,  SUM(dau) AS dau,  SUM(dau_pay) AS dau_pay  FROM  f_pay_retention  WHERE  create_date  {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time ) AS T ON T1.t_time = T.t_time \r\n', 't_time,new_account,new_device,f_pay,f_retention,now_pay,p_retention,new_device_pay,new_device_pay_sum,dau,dau_pay,dauRate,arpu,arppu', '日期,新增账号,新增设备,首付人数,首付次日存留数,当日付费人数,付费次日存留数,新增设备充值人数,新增设备充值总额,日活跃,日活跃付费人数,日活跃付费率%,新增设备ARPU,新增设备ARPPU');
INSERT INTO `report_sql` VALUES ('7', 'SELECT  T.t_time,  T1.user_add AS newUser,  T1.pay_01 AS pay1,  T1.pay_02 AS pay2,  FORMAT(T1.pay_02 / T1.user_add, 2) AS LTV2,  T1.pay_03 AS pay3,  FORMAT(T1.pay_03 / T1.user_add, 2) AS LTV3,  T1.pay_04 AS pay4,  FORMAT(T1.pay_04 / T1.user_add, 2) AS LTV4,  T1.pay_05 AS pay5,  FORMAT(T1.pay_05 / T1.user_add, 2) AS LTV5,  T1.pay_06 AS pay6,  FORMAT(T1.pay_06 / T1.user_add, 2) AS LTV6,  T1.pay_07 AS pay7,  FORMAT(T1.pay_07 / T1.user_add, 2) AS LTV7,  T1.pay_08 AS pay8,  FORMAT(T1.pay_08 / T1.user_add, 2) AS LTV8,  T1.pay_09 AS pay9,  FORMAT(T1.pay_09 / T1.user_add, 2) AS LTV9,  T1.pay_10 AS pay10,  FORMAT(T1.pay_10 / T1.user_add, 2) AS LTV10,  T1.pay_11 AS pay11,  FORMAT(T1.pay_11 / T1.user_add, 2) AS LTV11,  T1.pay_12 AS pay12,  FORMAT(T1.pay_12 / T1.user_add, 2) AS LTV12,  T1.pay_13 AS pay13,  FORMAT(T1.pay_13 / T1.user_add, 2) AS LTV13,  T1.pay_14 AS pay14,  FORMAT(T1.pay_14 / T1.user_add, 2) AS LTV14,  T1.pay_15 AS pay15,  FORMAT(T1.pay_15 / T1.user_add, 2) AS LTV15,  T1.pay_16 AS pay16,  FORMAT(T1.pay_16 / T1.user_add, 2) AS LTV16,  T1.pay_17 AS pay17,  FORMAT(T1.pay_17 / T1.user_add, 2) AS LTV17,  T1.pay_18 AS pay18,  FORMAT(T1.pay_18 / T1.user_add, 2) AS LTV18,  T1.pay_19 AS pay19,  FORMAT(T1.pay_19 / T1.user_add, 2) AS LTV19,  T1.pay_20 AS pay20,  FORMAT(T1.pay_20 / T1.user_add, 2) AS LTV20,  T1.pay_21 AS pay21,  FORMAT(T1.pay_21 / T1.user_add, 2) AS LTV21,  T1.pay_22 AS pay22,  FORMAT(T1.pay_22 / T1.user_add, 2) AS LTV22,  T1.pay_23 AS pay23,  FORMAT(T1.pay_23 / T1.user_add, 2) AS LTV23,  T1.pay_24 AS pay24,  FORMAT(T1.pay_24 / T1.user_add, 2) AS LTV24,  T1.pay_25 AS pay25,  FORMAT(T1.pay_25 / T1.user_add, 2) AS LTV25,  T1.pay_26 AS pay26,  FORMAT(T1.pay_26 / T1.user_add, 2) AS LTV26,  T1.pay_27 AS pay27,  FORMAT(T1.pay_27 / T1.user_add, 2) AS LTV27,  T1.pay_28 AS pay28,  FORMAT(T1.pay_28 / T1.user_add, 2) AS LTV28,  T1.pay_29 AS pay29,  FORMAT(T1.pay_29 / T1.user_add, 2) AS LTV29,  T1.pay_30 AS pay30,  FORMAT(T1.pay_30 / T1.user_add, 2) AS LTV30 FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(p.create_date, \"%Y-%m-%d\") AS t_time,  SUM(user_add) AS user_add,  SUM(pay_01) AS pay_01,  SUM(pay_02) AS pay_02,  SUM(pay_03) AS pay_03,  SUM(pay_04) AS pay_04,  SUM(pay_05) AS pay_05,  SUM(pay_06) AS pay_06,  SUM(pay_07) AS pay_07,  SUM(pay_08) AS pay_08,  SUM(pay_09) AS pay_09,  SUM(pay_10) AS pay_10,  SUM(pay_11) AS pay_11,  SUM(pay_12) AS pay_12,  SUM(pay_13) AS pay_13,  SUM(pay_14) AS pay_14,  SUM(pay_15) AS pay_15,  SUM(pay_16) AS pay_16,  SUM(pay_17) AS pay_17,  SUM(pay_18) AS pay_18,  SUM(pay_19) AS pay_19,  SUM(pay_20) AS pay_20,  SUM(pay_21) AS pay_21,  SUM(pay_22) AS pay_22,  SUM(pay_23) AS pay_23,  SUM(pay_24) AS pay_24,  SUM(pay_25) AS pay_25,  SUM(pay_26) AS pay_26,  SUM(pay_27) AS pay_27,  SUM(pay_28) AS pay_28,  SUM(pay_29) AS pay_29,  SUM(pay_30) AS pay_30  FROM  pay_lty AS p  WHERE  p.create_date {1}  AND p.platform LIKE \"{3}\"  AND p.server_id LIKE \"{2}\"  GROUP BY  t_time,  p.platform,  p.server_id ) AS T1 ON T.t_time = T1.t_time GROUP BY  T.t_time;  \r\n', 't_time,newUser,pay1,pay2,LTV2,pay3,LTV3,pay4,LTV4,pay5,LTV5,pay6,LTV6,pay7,LTV7,pay8,LTV8,pay9,LTV9,pay10,LTV10,pay11,LTV11,pay12,LTV12,pay13,LTV13,pay14,LTV14,pay15,LTV15,pay16,LTV16,pay17,LTV17,pay18,LTV18,pay19,LTV19,pay20,LTV20,pay21,LTV21,pay22,LTV22,pay23,LTV23,pay24,LTV24,pay25,LTV25,pay26,LTV26,pay27,LTV27,pay28,LTV28,pay29,LTV29,pay30,LTV30', '时间,当日新增用户,当日付费金额,2日付费,2日LTV,3日付费,3日LTV,4日付费,4日LTV,5日付费,5日LTV,6日付费,6日LTV,7日付费,7日LTV,8日付费,8日LTV,9日付费,9日LTV,10日付费,10日LTV,11日付费,11日LTV,12日付费,12日LTV,13日付费,13日LTV,14日付费,14日LTV,15日付费,15日LTV,16日付费,16日LTV,17日付费,17日LTV,18日付费,18日LTV,19日付费,19日LTV,20日付费,20日LTV,21日付费,21日LTV,22日付费,22日LTV,23日付费,23日LTV,24日付费,24日LTV,25日付费,25日LTV,26日付费,26日LTV,27日付费,27日LTV,28日付费,28日LTV,29日付费,29日LTV,30日付费,30日LTV');
INSERT INTO `report_sql` VALUES ('8', 'SELECT  t_time as t_data,   count(DISTINCT account_id) AS play_count FROM (SELECT  account_id,  TIMESTAMPDIFF(  HOUR,  CURDATE(),  last_log_time  ) as t_time  FROM  login_log  WHERE  TIMESTAMPDIFF(  DAY,  DATE_FORMAT(last_log_time,\"%Y-%m-%d\"),  CURDATE()   ) = 0  AND platform LIKE \"{0}\"  AND server_id LIKE \"{1}\" ) as T GROUP BY  t_time ORDER BY t_time ASC \r\n', 't_data,play_count', '时间(小时),活跃人数');
INSERT INTO `report_sql` VALUES ('9', 'SELECT  DATE_FORMAT(log_time, \"%Y年%m月%d日\") AS t_date,  SUM(hour_01) AS hour_01,  SUM(hour_02) AS hour_02,  SUM(hour_03) AS hour_03,  SUM(hour_04) AS hour_04,  SUM(hour_05) AS hour_05,  SUM(hour_06) AS hour_06,  SUM(hour_07) AS hour_07,  SUM(hour_08) AS hour_08,  SUM(hour_09) AS hour_09,  SUM(hour_10) AS hour_10,  SUM(hour_11) AS hour_11,  SUM(hour_12) AS hour_12,  SUM(hour_13) AS hour_13,  SUM(hour_14) AS hour_14,  SUM(hour_15) AS hour_15,  SUM(hour_16) AS hour_16,  SUM(hour_17) AS hour_17,  SUM(hour_18) AS hour_18,  SUM(hour_19) AS hour_19,  SUM(hour_20) AS hour_20,  SUM(hour_21) AS hour_21,  SUM(hour_22) AS hour_22,  SUM(hour_23) AS hour_23,  SUM(hour_24) AS hour_24 FROM  acu WHERE  log_time {0} AND platform LIKE \"{2}\" AND server_id LIKE \"{1}\" GROUP BY  t_date \r\n', 't_date,hour_01,hour_02,hour_03,hour_04,hour_05,hour_06,hour_07,hour_08,hour_09,hour_10,hour_11,hour_12,hour_13,hour_14,hour_15,hour_16,hour_17,hour_18,hour_19,hour_20,hour_21,hour_22,hour_23,hour_24', '日期在线人数,00:00,01:00,02:00,03:00,04:00,05:00,06:00,07:00,08:00,09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00,21:00,22:00,23:00');
INSERT INTO `report_sql` VALUES ('10', '(  SELECT  1 AS i_index,  \"0-10\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay <= 10 ) UNION  (  SELECT  2 AS i_index,  \"11-50\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 10  AND T.pay <= 50  ) UNION  (  SELECT  3 AS i_index,  \"51-100\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 50  AND T.pay <= 100  ) UNION  (  SELECT  4 AS i_index,  \"101-200\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 100  AND T.pay <= 200  ) UNION  (  SELECT  5 AS i_index,  \"201-500\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 200  AND T.pay <= 500  ) UNION  (  SELECT  6 AS i_index,  \"501-1000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 500  AND T.pay <= 1000  ) UNION  (  SELECT  7 AS i_index,  \"1001-3000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 1000  AND T.pay <= 3000  ) UNION  (  SELECT  8 AS i_index,  \"3001-7000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 3000  AND T.pay <= 7000  ) UNION  (  SELECT  9 AS i_index,  \"7001-12000\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 7000  AND T.pay <= 12000  ) UNION  (  SELECT  10 AS i_index,  \"12001以上\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  WHERE  T.pay > 12000  ) UNION  (  SELECT  \"总计   \" AS i_index,  \"\" AS t_qj,  COUNT(T.pay) AS num  FROM  (  SELECT  sum(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {0}  AND o.platform LIKE \"{2}\"  AND o.server_id LIKE \"{1}\"  GROUP BY  account_id  ) AS T  ) \r\n', 'i_index,t_qj,num', '序号,区间(元),人数');
INSERT INTO `report_sql` VALUES ('11', 'SELECT  T6.t_time,  SUM(T2.newPlayer) AS newPlayer,  T3.newPPayCount,  T3.newPPayCountR,  T3.newPay AS pay_money,  FORMAT(  T3.newPay / T3.newPPayCount,  2  ) AS ARPPU,  FORMAT(  T3.newPPayCount / SUM(T2.newPlayer),  2  ) AS newPayRate FROM  ({0}) AS T6 LEFT JOIN (  SELECT  V.t_time,  SUM(V2.payNew) AS newPay,  SUM(V2.newPPCount) AS newPPayCount,  SUM(V2.newPPCountR) AS newPPayCountR  FROM  (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  account  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time,  prev_srv_id,  platform,  account  ) AS V  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS payNew,  COUNT(DISTINCT t.account_name) AS newPPCount,  COUNT(t.account_name) AS newPPCountR,  platform,  server_id,  t.account_name  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time,  t.server_id,  t.platform,  t.account_name  ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id  AND V.platform = V2.platform  AND V.account = V2.account_name  GROUP BY  V.t_time ) AS T3 ON T3.t_time = T6.t_time LEFT JOIN (  SELECT  DATE_FORMAT(  account.create_time,  \"%Y-%m-%d\"  ) AS t_time,  platform,  prev_srv_id,  COUNT(DISTINCT account) AS newPlayer  FROM  account  WHERE  create_time {1}  AND platform LIKE \"{3}\"  AND prev_srv_id LIKE \"{2}\"  GROUP BY  t_time ) AS T2 ON T2.t_time = T6.t_time GROUP BY  T6.t_time \r\n', 't_time,newPlayer,newPPayCount,newPPayCountR,pay_money,ARPPU,newPayRate', '日期,新增玩家数,新增付费人数,新增付费次数,付费金额,ARPPU,新增付费率');
INSERT INTO `report_sql` VALUES ('12', '(  SELECT  T6.t_time,  (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ) AS old_pay,  (  IFNULL(T2.payUserR, 0) - IFNULL(T3.newPPayCountR, 0)  ) AS old_payR,  (  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0)  ) AS oldPaySum,  FORMAT(  (  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0)  ) / (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ),  2  ) AS ARPPU,  IFNULL(T.activeUser, 0) AS activeUser,  FORMAT(  (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ) / IFNULL(T.activeUser, 0) * 100,  2  ) AS payRate  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  SUM(playCount) AS activeUser,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  acu  WHERE  log_time {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_id) AS payUser,  COUNT(t.account_id) AS payUserR  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS newPay,  COUNT(DISTINCT t.account_id) AS newPPayCount,  COUNT(t.account_id) AS newPPayCountR  FROM  `order` AS t  LEFT JOIN account ON account.account = t.account_name  WHERE  t.finish_time {1}  AND account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"{2}\"  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  AND DATEDIFF(  account.create_time,  t.finish_time  ) = 0  GROUP BY  t_time  ) AS T3 ON T3.t_time = T6.t_time  GROUP BY  T6.t_time ) UNION  (  SELECT  \"总计\" AS t_time,  SUM(old_pay) AS old_pay,  SUM(old_payR) AS old_payR,  SUM(oldPaySum) AS oldPaySum,  FORMAT(  SUM(oldPaySum) / SUM(old_pay),  2  ) AS ARPPU,  SUM(activeUser) AS activeUser,  FORMAT(  SUM(old_pay) / SUM(activeUser),  2  ) AS payRate  FROM  (  SELECT  T6.t_time,  (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ) AS old_pay,  (  IFNULL(T2.payUserR, 0) - IFNULL(T3.newPPayCountR, 0)  ) AS old_payR,  (  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0)  ) AS oldPaySum,  FORMAT(  (  IFNULL(T2.paySum, 0) - IFNULL(T3.newPay, 0)  ) / (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ),  2  ) AS ARPPU,  IFNULL(T.activeUser, 0) AS activeUser,  FORMAT(  (  IFNULL(T2.payUser, 0) - IFNULL(T3.newPPayCount, 0)  ) / IFNULL(T.activeUser, 0) * 100,  2  ) AS payRate  FROM  ({0}) AS T6  LEFT JOIN (  SELECT  SUM(playCount) AS activeUser,  DATE_FORMAT(log_time, \"%Y-%m-%d\") AS t_time  FROM  acu  WHERE  log_time {1}  AND platform LIKE \"{3}\"  AND server_id {4}  GROUP BY  t_time  ) AS T ON T.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS paySum,  COUNT(DISTINCT t.account_id) AS payUser,  COUNT(t.account_id) AS payUserR  FROM  `order` AS t  WHERE  t.finish_time {1}  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  GROUP BY  t_time  ) AS T2 ON T2.t_time = T6.t_time  LEFT JOIN (  SELECT  DATE_FORMAT(t.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(t.amount) AS newPay,  COUNT(DISTINCT t.account_id) AS newPPayCount,  COUNT(t.account_id) AS newPPayCountR  FROM  `order` AS t  LEFT JOIN account ON account.account = t.account_name  WHERE  t.finish_time {1}  AND account.create_time {1}  AND account.platform LIKE \"{3}\"  AND account.prev_srv_id LIKE \"{2}\"  AND t.platform LIKE \"{3}\"  AND t.server_id LIKE \"{2}\"  AND DATEDIFF(  account.create_time,  t.finish_time  ) = 0  GROUP BY  t_time  ) AS T3 ON T3.t_time = T6.t_time  GROUP BY  T6.t_time  ) AS T  ) \r\n', 't_time,old_pay,old_payR,oldPaySum,ARPPU,activeUser,payRate', '日期,付费人数,付费人次,付费金额,ARPPU,当日活跃人数,付费率(%)');
INSERT INTO `report_sql` VALUES ('13', 'SELECT  T.t_time,  T1.pay FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {1}  AND o.platform LIKE \"{2}\"  GROUP BY  t_time ) AS T1 ON T1.t_time = T.t_time UNION ALL  (  SELECT  \"总计\" AS t_time,  SUM(o.amount) AS pay  FROM  `order` AS o  WHERE  o.finish_time {1}  AND o.platform LIKE \"{2}\"  ) \r\n', 't_time,pay', '日期,金额(元)');
INSERT INTO `report_sql` VALUES ('14', 'SELECT  T1.t_time, T1.NAME, T1.pay FROM (  SELECT   t.t_time, t.pay, t. NAME  FROM  ( SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time,  SUM(o.amount) AS pay,  o.server_id,  s. NAME  FROM  `order` AS o  LEFT JOIN `server` AS s ON o.server_id = s.id  WHERE  o.platform LIKE \"{1}\"  GROUP BY  t_time, s.id ) AS t  WHERE  t.t_time LIKE \"{0}\" ) AS T1 UNION ALL  (  SELECT  \"总计\" AS t_time,\"\" as NAME, SUM(t.pay) AS pay  FROM  (  SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time, SUM(o.amount) AS pay  FROM  `order` AS o  WHERE  o.platform LIKE \"{1}\" GROUP BY  t_time ) AS t  WHERE  t.t_time LIKE \"{0}\"  )\r\n', 't_time,name,pay', '日期,区服,金额(元)');
INSERT INTO `report_sql` VALUES ('15', '(SELECT  b.id,  b. NAME AS step,  IFNULL(T.num, 0) AS num FROM  guide_info AS b LEFT JOIN (  SELECT  a.guide_id,  SUM(a.sum_player) AS num  FROM  guide_log AS a  WHERE  a.create_time {0}  AND a.platform LIKE \"{2}\"  AND a.server_id LIKE \"{1}\"  GROUP BY  a.guide_id ) AS T ON T.guide_id = b.id)  ', 'id,step,num', '引导ID,引导步骤,人数(设备号)');
INSERT INTO `report_sql` VALUES ('16', 'SELECT  T1.t_time,  SUM(T1.num) AS num,  SUM(T1.pay_sum) AS pay_sum,  SUM(T1.money_10) AS money_10,  SUM(T1.p_10) AS p_10,  SUM(T1.money_68) AS money_68,  SUM(T1.p_68) AS p_68,  SUM(T1.money_168) AS money_168,  SUM(T1.p_168) AS p_168,  SUM(T1.money_328) AS money_328,  SUM(T1.p_328) AS p_328,  SUM(T1.money_648) AS money_648,  SUM(T1.p_648) AS p_648,  SUM(T1.money_988) AS money_988,  SUM(T1.p_988) AS p_988,  SUM(T1.money_y_25) AS money_y_25,  SUM(T1.p_y_25) AS p_y_25,  SUM(T1.money_y_58) AS money_y_58,  SUM(T1.p_y_58) AS p_y_58,  SUM(T1.money_1998) AS money_1998,  SUM(T1.p_1998) AS p_1998,  SUM(T1.money_4998) AS money_4998,  SUM(T1.p_4998) AS p_4998,  SUM(T1.money_vip) AS money_vip,  SUM(T1.p_vip) AS p_vip FROM  (  SELECT  T.t_time,  T.sr AS pay_sum,  num,  CASE T.goods_id  WHEN \'200100008\' THEN  T.sr  ELSE  0  END AS money_10,  CASE T.goods_id  WHEN \'200100008\' THEN  T.num  ELSE  0  END AS p_10,  CASE T.goods_id WHEN \'200100009\' THEN  T.sr ELSE  0 END AS money_68,  CASE T.goods_id WHEN \'200100009\' THEN  T.num ELSE  0 END AS p_68,  CASE T.goods_id WHEN \'200100010\' THEN  T.sr ELSE  0 END AS money_168,  CASE T.goods_id WHEN \'200100010\' THEN  T.num ELSE  0 END AS P_168,  CASE T.goods_id WHEN \'200100011\' THEN  T.sr ELSE  0 END AS money_328,  CASE T.goods_id WHEN \'200100011\' THEN  T.num ELSE  0 END AS p_328,  CASE T.goods_id WHEN \'200100012\' THEN  T.sr ELSE  0 END AS money_648,  CASE T.goods_id WHEN \'200100012\' THEN  T.num ELSE  0 END AS p_648,  CASE T.goods_id WHEN \'200100013\' THEN  T.sr ELSE  0 END AS money_988,  CASE T.goods_id WHEN \'200100013\' THEN  T.num ELSE  0 END AS p_988,  CASE T.goods_id WHEN \'200100023\' THEN  T.sr ELSE  0 END AS money_y_25,  CASE T.goods_id WHEN \'200100023\' THEN  T.num ELSE  0 END AS p_y_25,  CASE T.goods_id WHEN \'200100025\' THEN  T.sr ELSE  0 END AS money_y_58,  CASE T.goods_id WHEN \'200100025\' THEN  T.num ELSE  0 END AS p_y_58,  CASE T.goods_id WHEN \'200100026\' THEN  T.sr ELSE  0 END AS money_1998,  CASE T.goods_id WHEN \'200100026\' THEN  T.num ELSE  0 END AS p_1998,  CASE T.goods_id WHEN \'200100027\' THEN  T.sr ELSE  0 END AS money_4998,  CASE T.goods_id WHEN \'200100027\' THEN  T.num ELSE  0 END AS p_4998,  CASE T.goods_id WHEN \'200100028\' THEN  T.sr ELSE  0 END AS money_vip,  CASE T.goods_id WHEN \'200100028\' THEN  T.num ELSE  0 END AS p_vip FROM  (  SELECT  DATE_FORMAT(o.finish_time, \"%Y-%m-%d\") AS t_time,  o.goods_id,  COUNT(DISTINCT o.account_name) AS num,  SUM(o.amount) AS sr  FROM  `order` AS o  WHERE  o.finish_time {1}  AND o.platform LIKE \"{3}\"  AND o.server_id LIKE \"{2}\"  GROUP BY  o.goods_id,  t_time  ) AS T  ) AS T1 GROUP BY  T1.t_time;  \r\n', 't_time,num,pay_sum,money_10,p_10,money_68,p_68,money_168,p_168,money_328,p_328,money_648,p_648,money_988,p_988,money_y_25,p_y_25,money_y_58,p_y_58,money_1998,p_1998,.money_4998,p_4998,money_vip,p_vip', '日期,付费人数,付费金额,10元,人数,68元,人数,168元,人数,328元,人数,648元,人数,988元,人数,月卡(25元),人数,终生月卡(58元),人数,1988元,人数,4988元,人数,vip月卡(98元),人数\r\n');
INSERT INTO `report_sql` VALUES ('17', 'SELECT  b.id,  b. NAME AS step,  IFNULL(T.player, 0) AS player,  IFNULL(T.num, 0) AS num,  IFNULL(T.win_player, 0) AS win_player,  IFNULL(T.win, 0) AS win FROM  fb_info AS b LEFT JOIN (  SELECT  a.fb_id,  SUM(a.tz_num) AS num,  SUM(a.tz_player) AS player,  SUM(a.tz_win_player_cs) AS win,  SUM(a.tz_win_player) AS win_player  FROM  fb_summary AS a  WHERE  a.t_time {0}  AND a.platform LIKE \"{2}\"  AND a.server_id LIKE \"{1}\"  GROUP BY  a.fb_id ) AS T ON T.fb_id = b.id WHERE  b.type LIKE \"{3}\" \r\n', 'id,step,player,num,win_player,win', '副本ID,副本名称,挑战人数,挑战次数,完成关卡人数,完成副本关卡次数');
INSERT INTO `report_sql` VALUES ('18', '(SELECT  type1,  type2,  IFNULL(SUM(total),0) AS total,  IFNULL(SUM(rc),0) AS rc,  IFNULL(SUM(rs),0) AS rs FROM  crystal_use_summary WHERE  platform LIKE \"{2}\" AND t_time {0} AND server_id LIKE \"{1}\" GROUP BY  type1,  type2 )UNION (SELECT  \"总计:\" AS type1,  \"\" AS type2,  IFNULL(SUM(total),0) AS total,  IFNULL(SUM(rc),0) AS rc,  IFNULL(SUM(rs),0) AS rs FROM  crystal_use_summary WHERE  platform LIKE \"{2}\" AND t_time {0} AND server_id LIKE \"{1}\" ) ', 'type1,type2,total,rc,rs', '消费类型,标题,消费总额,人次,人数');
INSERT INTO `report_sql` VALUES ('19', '(  SELECT  type1,  type2,  IFNULL(SUM(total), 0) AS total,  IFNULL(SUM(rc), 0) AS rc,  IFNULL(SUM(rs), 0) AS rs  FROM  crystal_use_summary  WHERE  platform LIKE \"{2}\"  AND t_time {0}  AND server_id LIKE \"{1}\"  AND isVip > 0  GROUP BY  type1,  type2 ) UNION  (  SELECT  \"总计:\" AS type1,  \"\" AS type2,  IFNULL(SUM(total), 0) AS total,  IFNULL(SUM(rc), 0) AS rc,  IFNULL(SUM(rs), 0) AS rs  FROM  crystal_use_summary  WHERE  platform LIKE \"{2}\"  AND t_time {0}  AND server_id LIKE \"{1}\"  AND isVip > 0  ) \r\n', 'type1,type2,total,rc,rs', '消费类型,标题,消费总额,人次,人数');
INSERT INTO `report_sql` VALUES ('20', 'SELECT  T1.t_time,  IFNULL(T2.tOutput, 0) AS tOutput,  IFNULL(T2.tInput, 0) AS tInput,  IFNULL(T2.tKeep, 0) AS tKeep,  IFNULL(T2.output1, 0) AS output1,  IFNULL(T2.input1, 0) AS input1,  IFNULL(T2.keep1, 0) AS keep1,  IFNULL(T2.output2, 0) AS output2,  IFNULL(T2.input2, 0) AS input2,  IFNULL(T2.keep2, 0) AS keep2,  IFNULL(T2.output3, 0) AS output3,  IFNULL(T2.input3, 0) AS input3,  IFNULL(T2.keep3, 0) AS keep3,  IFNULL(T2.output4, 0) AS output4,  IFNULL(T2.input4, 0) AS input4,  IFNULL(T2.keep4, 0) AS keep4,  IFNULL(T2.output5, 0) AS output5,  IFNULL(T2.input5, 0) AS input5,  IFNULL(T2.keep5, 0) AS keep5,  IFNULL(T2.output6, 0) AS output6,  IFNULL(T2.input6, 0) AS input6,  IFNULL(T2.keep6, 0) AS keep6,  IFNULL(T2.output7, 0) AS output7,  IFNULL(T2.input7, 0) AS input7,  IFNULL(T2.keep7, 0) AS keep7,  IFNULL(T2.output8, 0) AS output8,  IFNULL(T2.input8, 0) AS input8,  IFNULL(T2.keep8, 0) AS keep8,  IFNULL(T2.output9, 0) AS output9,  IFNULL(T2.input9, 0) AS input9,  IFNULL(T2.keep9, 0) AS keep9 FROM  ({0}) AS T1 LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(tOutput) AS tOutput,  SUM(tInput) AS tInput,  SUM(tKeep) AS tKeep,  SUM(output1) AS output1,  SUM(input1) AS input1,  SUM(keep1) AS keep1,  SUM(output2) AS output2,  SUM(input2) AS input2,  SUM(keep2) AS keep2,  SUM(output3) AS output3,  SUM(input3) AS input3,  SUM(keep3) AS keep3,  SUM(output4) AS output4,  SUM(input4) AS input4,  SUM(keep4) AS keep4,  SUM(output5) AS output5,  SUM(input5) AS input5,  SUM(keep5) AS keep5,  SUM(output6) AS output6,  SUM(input6) AS input6,  SUM(keep6) AS keep6,  SUM(output7) AS output7,  SUM(input7) AS input7,  SUM(keep7) AS keep7,  SUM(output8) AS output8,  SUM(input8) AS input8,  SUM(keep8) AS keep8,  SUM(output9) AS output9,  SUM(input9) AS input9,  SUM(keep9) AS keep9  FROM  crystal_level  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T2 ON T2.d_time = T1.t_time \r\n', 't_time,tOutput,tInput,tKeep,output1,input1,keep1,output2,input2,keep2,output3,input3,keep3,output4,input4,keep4,output5,input5,keep5,output6,input6,keep6,output7,input7,keep7,output8,input8,keep8,output9,input9,keep9', '日期,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存,总产出,消耗,留存');
INSERT INTO `report_sql` VALUES ('21', '(SELECT  T1.t_time,  IFNULL(T2.cz_cs, 0) + IFNULL(T2.hd_cs, 0)+ IFNULL(T2.tg_cs, 0) + IFNULL(T2.yj_cs, 0) + IFNULL(T2.jjc_cs, 0) + IFNULL(T2.shop_cs, 0) AS zj_cs,  IFNULL(T2.cz_sj, 0) + IFNULL(T2.hd_sj, 0) + IFNULL(T2.tg_sj, 0) + IFNULL(T2.yj_sj, 0) + IFNULL(T2.jjc_sj, 0) + IFNULL(T2.shop_sj, 0) AS zj_sj,  IFNULL(T2.cz_cs, 0) AS cz_cs,  IFNULL(T2.cz_sj, 0) AS cz_sj,  IFNULL(T2.hd_cs, 0) AS hd_cs,  IFNULL(T2.hd_sj, 0) AS hd_sj,  IFNULL(T2.tg_cs, 0) AS tg_cs,  IFNULL(T2.tg_sj, 0) AS tg_sj,  IFNULL(T2.yj_cs, 0) AS yj_cs,  IFNULL(T2.yj_sj, 0) AS yj_sj,  IFNULL(T2.jjc_cs, 0) AS jjc_cs,  IFNULL(T2.jjc_sj, 0) AS jjc_sj,  IFNULL(T2.shop_cs, 0) AS shop_cs,  IFNULL(T2.shop_sj, 0) AS shop_sj FROM  ({0}) AS T1 LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(cz_cs) AS cz_cs,  SUM(cz_sj) AS cz_sj,  SUM(hd_cs) AS hd_cs,  SUM(hd_sj) AS hd_sj,  SUM(tg_cs) AS tg_cs,  SUM(tg_sj) AS tg_sj,  SUM(yj_cs) AS yj_cs,  SUM(yj_sj) AS yj_sj,  SUM(jjc_cs) AS jjc_cs,  SUM(jjc_sj) AS jjc_sj,  SUM(shop_cs) AS shop_cs,  SUM(shop_sj) AS shop_sj  FROM  crystal_detailed_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T2 ON T1.t_time = T2.d_time) ', 't_time,zj_cs,zj_sj,cz_cs,cz_sj,hd_cs,hd_sj,tg_cs,tg_sj,yj_cs,yj_sj,jjc_cs,jjc_sj,shop_cs,shop_sj', '日期,次数,水晶,次数,水晶,次数,水晶,次数,水晶,次数,水晶,次数,水晶');
INSERT INTO `report_sql` VALUES ('22', 'SELECT  T.t_time,  IFNULL(T2.cs1, 0) + IFNULL(T2.cs2, 0) + IFNULL(T2.cs3, 0) + IFNULL(T2.cs4, 0) + IFNULL(T2.cs5, 0) + IFNULL(T2.cs6, 0) + IFNULL(T2.cs7, 0) + IFNULL(T2.cs8, 0) + IFNULL(T2.cs9, 0) AS zj_cs,  IFNULL(T2.ks1, 0) + IFNULL(T2.cs2, 0) + IFNULL(T2.cs3, 0) + IFNULL(T2.cs4, 0) + IFNULL(T2.cs5, 0) + IFNULL(T2.cs6, 0) + IFNULL(T2.cs7, 0) + IFNULL(T2.cs8, 0) + IFNULL(T2.cs9, 0) AS zj_ks,  IFNULL(T2.cs1, 0) AS cs1,  IFNULL(T2.ks1, 0) AS ks1,  IFNULL(T2.cs2, 0) AS cs2,  IFNULL(T2.ks2, 0) AS ks2,  IFNULL(T2.cs3, 0) AS cs3,  IFNULL(T2.ks3, 0) AS ks3,  IFNULL(T2.cs4, 0) AS cs4,  IFNULL(T2.ks4, 0) AS ks4,  IFNULL(T2.cs5, 0) AS cs5,  IFNULL(T2.ks5, 0) AS ks5,  IFNULL(T2.cs6, 0) AS cs6,  IFNULL(T2.ks6, 0) AS ks6,  IFNULL(T2.cs7, 0) AS cs7,  IFNULL(T2.ks7, 0) AS ks7,  IFNULL(T2.cs8, 0) AS cs8,  IFNULL(T2.ks8, 0) AS ks8,  IFNULL(T2.cs9, 0) AS cs9,  IFNULL(T2.ks9, 0) AS ks9 FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(cs1) AS cs1,  SUM(ks1) AS ks1,  SUM(cs2) AS cs2,  SUM(ks2) AS ks2,  SUM(cs3) AS cs3,  SUM(ks3) AS ks3,  SUM(cs4) AS cs4,  SUM(ks4) AS ks4,  SUM(cs5) AS cs5,  SUM(ks5) AS ks5,  SUM(cs6) AS cs6,  SUM(ks6) AS ks6,  SUM(cs7) AS cs7,  SUM(ks7) AS ks7,  SUM(cs8) AS cs8,  SUM(ks8) AS ks8,  SUM(cs9) AS cs9,  SUM(ks9) AS ks9  FROM  ore_detailed_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T2 ON T2.d_time = T.t_time \r\n', 't_time,zj_cs,zj_ks,cs1,ks1,cs2,ks2,cs3,ks3,cs4,ks4,cs5,ks5,cs6,ks6,cs7,ks7,cs8,ks8,cs9,ks9', '日期,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿,次数,辉晶矿');
INSERT INTO `report_sql` VALUES ('23', 'SELECT  T.t_time,  IFNULL(T1.c, 0) AS c,  IFNULL(T1.b, 0) AS b,  IFNULL(T1.p, 0) AS p,  IFNULL(T1.c1, 0) AS c1,  IFNULL(T1.b1, 0) AS b1,  IFNULL(T1.p1, 0) AS p1,  IFNULL(T1.c2, 0) AS c2,  IFNULL(T1.b2, 0) AS b2,  IFNULL(T1.p2, 0) AS p2,  IFNULL(T1.c3, 0) AS c3,  IFNULL(T1.b3, 0) AS b3,  IFNULL(T1.p3, 0) AS p3,  IFNULL(T1.c4, 0) AS c4,  IFNULL(T1.b4, 0) AS b4,  IFNULL(T1.p4, 0) AS p4,  IFNULL(T1.c5, 0) AS c5,  IFNULL(T1.b5, 0) AS b5,  IFNULL(T1.p5, 0) AS p5,  IFNULL(T1.c6, 0) AS c6,  IFNULL(T1.b6, 0) AS b6,  IFNULL(T1.p6, 0) AS p6,  IFNULL(T1.c7, 0) AS c7,  IFNULL(T1.b7, 0) AS b7,  IFNULL(T1.p7, 0) AS p7,  IFNULL(T1.c8, 0) AS c8,  IFNULL(T1.b8, 0) AS b8,  IFNULL(T1.p8, 0) AS p8 FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(c) AS c,  SUM(b) AS b,  SUM(p) AS p,  SUM(c1) AS c1,  SUM(b1) AS b1,  SUM(p1) AS p1,  SUM(c2) AS c2,  SUM(b2) AS b2,  SUM(p2) AS p2,  SUM(c3) AS c3,  SUM(b3) AS b3,  SUM(p3) AS p3,  SUM(c4) AS c4,  SUM(b4) AS b4,  SUM(p4) AS p4,  SUM(c5) AS c5,  SUM(b5) AS b5,  SUM(p5) AS p5,  SUM(c6) AS c6,  SUM(b6) AS b6,  SUM(p6) AS p6,  SUM(c7) AS c7,  SUM(b7) AS b7,  SUM(p7) AS p7,  SUM(c8) AS c8,  SUM(b8) AS b8,  SUM(p8) AS p8  FROM  rmb_crystal  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T1 ON T1.d_time = T.t_time ', 't_time,c,b,p,c1,b1,p1,c2,b2,p2,c3,b3,p3,c4,b4,p4,c5,b5,p5,c6,b6,p6,c7,b7,p7,c8,b8,p8', '日期,收入,消费次数,充值人数,收入,消费次数,充值人数,收入,消费次数,充值人数,收入,消费次数,充值人数,收入,消费次数,充值人数,收入,消费次数,充值人数,收入,消费次数,充值人数,收入,消费次数,充值人数,收入,消费次数,充值人数');
INSERT INTO `report_sql` VALUES ('24', '(SELECT  T.t_time,  IFNULL(T1.sj_hd, 0) AS sj_hd,  IFNULL(T1.sj_xh, 0) AS sj_xh,  IFNULL(T1.sj_lc, 0) AS sj_lc,  IFNULL(T1.ks_hd, 0) AS ks_hd,  IFNULL(T1.ks_xh, 0) AS ks_xh,  IFNULL(T1.ks_lc, 0) AS ks_lc,  IFNULL(T1.jb_hd, 0) AS jb_hd,  IFNULL(T1.jb_xh, 0) AS jb_xh,  IFNULL(T1.jb_lc, 0) AS jb_lc FROM  ({0}) AS T LEFT JOIN (  SELECT  DATE_FORMAT(t_time, \"%Y-%m-%d\") AS d_time,  SUM(sj_hd) AS sj_hd,  SUM(sj_xh) AS sj_xh,  SUM(sj_lc) AS sj_lc,  SUM(ks_hd) AS ks_hd,  SUM(ks_xh) AS ks_xh,  SUM(ks_lc) AS ks_lc,  SUM(jb_hd) AS jb_hd,  SUM(jb_xh) AS jb_xh,  SUM(jb_lc) AS jb_lc  FROM  money_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\"  GROUP BY  d_time ) AS T1 ON T1.d_time = T.t_time) UNION ( SELECT  \"总计:\" AS d_time,  IFNULL(SUM(sj_hd), 0) AS sj_hd,  IFNULL(SUM(sj_xh), 0) AS sj_xh,  IFNULL(SUM(sj_lc), 0) AS sj_lc,  IFNULL(SUM(ks_hd), 0) AS ks_hd,  IFNULL(SUM(ks_xh), 0) AS ks_xh,  IFNULL(SUM(ks_lc), 0) AS ks_lc,  IFNULL(SUM(jb_hd), 0) AS jb_hd,  IFNULL(SUM(jb_xh), 0) AS jb_xh,  IFNULL(SUM(jb_lc), 0) AS jb_lc  FROM  money_summary  WHERE  t_time {1}  AND platform LIKE \"{3}\"  AND server_id LIKE \"{2}\" ) \r\n', 't_time,sj_hd,sj_xh,sj_lc,ks_hd,ks_xh,ks_lc,jb_hd,jb_xh,jb_lc', '日期,产出,消耗,留存,产出,消耗,留存,产出,消耗,留存');







-- #############################
------------
-- 存储过程 中用到的表
-------------

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=1428 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for f_pay_retention
-- ----------------------------
DROP TABLE IF EXISTS `f_pay_retention`;
CREATE TABLE `f_pay_retention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '统计日时间',
  `server_id` int(255) DEFAULT NULL COMMENT '服务器id',
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '平台',
  `f_pay` int(255) DEFAULT NULL COMMENT '首次付费人数(去重)',
  `f_pay_r` int(255) DEFAULT NULL COMMENT '首次付费人数(不去重)=(付费次数)',
  `f_pay_money` int(255) DEFAULT NULL COMMENT '首付金额',
  `f_pay_count01` int(255) DEFAULT NULL COMMENT '首付次日付费人数(今天首付，第二天还登录的)',
  `f_pay_count07` int(255) DEFAULT NULL COMMENT '首付7日付费留存',
  `new_account` int(255) DEFAULT NULL COMMENT '新增帐号数',
  `new_device` int(255) DEFAULT NULL COMMENT '新增设备数',
  `now_pay` int(255) DEFAULT NULL COMMENT '当日付费人数',
  `pay_next_day_login` int(255) DEFAULT NULL COMMENT '付费第二日留存(今日付费第2日还登录的)',
  `new_device_pay` int(255) DEFAULT NULL COMMENT '新设备支付人数',
  `new_device_pay_sum` int(255) DEFAULT NULL COMMENT '新设备支付总额',
  `dau` int(255) DEFAULT NULL COMMENT '活跃数',
  `dau_pay` int(255) DEFAULT NULL COMMENT '活跃付费人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='# 新服收入汇总表';


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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='# 新服活跃汇总表';


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
) ENGINE=InnoDB AUTO_INCREMENT=562 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='### PAY LTV  30 日支付LVT ';


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
) ENGINE=InnoDB AUTO_INCREMENT=708 DEFAULT CHARSET=utf8;

------------
-- 存储过程
-------------

-- --------------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS player_acu$$
CREATE PROCEDURE player_acu()
BEGIN 

# 建议延迟一天执行
# 按小时 统计活跃人数
DECLARE c_date date;
DECLARE n_date  date;
DECLARE s_id int;
set n_date = CURDATE();
-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
set c_date = date_sub(n_date,INTERVAL 1 day);

set s_id = -100;

# 插入活跃数据分服务器，分渠道
insert into acu(platform,server_id,playCount,log_time) SELECT platform,server_id,COUNT(DISTINCT account_id),c_date from login_log where DATEDIFF(c_date,last_log_time)=0 GROUP BY platform,server_id;
# 插入活跃数据分渠道
insert into acu(platform,server_id,playCount,log_time) SELECT platform,s_id,COUNT(DISTINCT account_id),c_date from login_log where DATEDIFF(c_date,last_log_time)=0 GROUP BY platform;
 
if exists(select * from acu where datediff(c_date,log_time) = 0)
	then
		-- 插入按时间，分服务器，分渠道
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 0 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_01 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 1 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_02 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 2 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_03 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 3 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_04 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 4 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_05 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 5 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_06 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 6 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_07 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 7 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_08 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 8 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_09 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 9 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_10 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 10 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_11 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 11 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_12 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_13 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_14 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 14 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_15 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 15 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_16 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 16 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_17 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 17 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_18 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 18 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_19 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 19 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_20 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 20 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_21 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 21 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_22 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 22 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_23 = T.a;
		UPDATE acu inner JOIN(SELECT platform,server_id,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 23 GROUP BY platform,server_id) as T ON T.platform = acu.platform AND T.server_id = acu.server_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_24 = T.a;
		
		-- 插入按时间，分服务器
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 0 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_01 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 1 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_02 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 2 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_03 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 3 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_04 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 4 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_05 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 5 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_06 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 6 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_07 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 7 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_08 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 8 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_09 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 9 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_10 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 10 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_11 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 11 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_12 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_13 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 13 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_14 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 14 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_15 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 15 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_16 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 16 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_17 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 17 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_18 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 18 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_19 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 19 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_20 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 20 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_21 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 21 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_22 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 22 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_23 = T.a;
		UPDATE acu inner JOIN(SELECT platform,count(DISTINCT account_id) as a FROM login_log WHERE ( 24 - TIMESTAMPDIFF( HOUR, last_log_time, CURDATE() ) ) = 23 GROUP BY platform) as T ON T.platform = acu.platform AND acu.server_id = s_id AND datediff(c_date, acu.log_time) = 0 set acu.hour_24 = T.a;
end if;  

END
$$

DELIMITER ;

-- ---------------------------------------- #############################################

DELIMITER $$
-- 按小时统计活跃
DROP PROCEDURE IF EXISTS start_fist_pay$$
CREATE PROCEDURE start_fist_pay()
BEGIN
	-- 统计新增首次付费 (留存率)---
	#Routine body goes here...
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
	INSERT INTO f_pay_retention(create_date,f_pay,f_pay_r,f_pay_money,platform,server_id) SELECT DATE_FORMAT( t.finish_time, "%Y-%m-%d" ) AS t_time, count(DISTINCT t.account_name), count(t.account_name),SUM(amount), t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time,c_date) = 0 AND t.account_name NOT IN(SELECT account FROM account WHERE DATEDIFF(create_time,c_date) < 0)GROUP BY t_time,t.platform,t.server_id;
	-- 分渠道
	INSERT INTO f_pay_retention(create_date,f_pay,f_pay_r,f_pay_money,platform,server_id) SELECT DATE_FORMAT( t.finish_time, "%Y-%m-%d" ) AS t_time, count(DISTINCT t.account_name), count(t.account_name),SUM(amount), t.platform, s_id FROM `order` AS t WHERE DATEDIFF(t.finish_time,c_date) = 0 AND t.account_name NOT IN(SELECT account FROM account WHERE DATEDIFF(create_time,c_date) < 0)GROUP BY t_time,t.platform;

	-- 开始计算 首付次日留存,首付7日留存
	
	IF exists(select * from f_pay_retention where datediff(c_date,create_date) = 0)
THEN
		-- 首付次日留存,分渠道，分服务器
		UPDATE f_pay_retention INNER JOIN ( SELECT  IFNULL(count(o.account_name),0) as a,o.platform,o.server_id FROM `order` AS o WHERE DATEDIFF(o.finish_time,c_date) = 0 AND o.account_name IN(SELECT t.account_name FROM `order` AS t WHERE DATEDIFF(t.finish_time,c_date) = 1 AND t.account_name NOT IN(SELECT account FROM account WHERE DATEDIFF(account.create_time,c_date) < 1))GROUP BY o.platform,o.server_id) AS T2 ON f_pay_retention.platform = T2.platform AND f_pay_retention.server_id = T2.server_id AND datediff( c_date, f_pay_retention.create_date) = 0 SET f_pay_retention.f_pay_count01 = T2.a;
		-- 首付次日留存,分渠道
		UPDATE f_pay_retention INNER JOIN ( SELECT  IFNULL(count(o.account_name),0) as a,o.platform,s_id FROM `order` AS o WHERE DATEDIFF(o.finish_time,c_date) = 0 AND o.account_name IN(SELECT t.account_name FROM `order` AS t WHERE DATEDIFF(t.finish_time,c_date) = 1 AND t.account_name NOT IN(SELECT account FROM account WHERE DATEDIFF(account.create_time,c_date) < 1))GROUP BY o.platform) AS T2 ON f_pay_retention.platform = T2.platform AND datediff( c_date, f_pay_retention.create_date) = 0 SET f_pay_retention.f_pay_count01 = T2.a;
		
		-- 首付7日留存,分渠道，分服务器
		UPDATE f_pay_retention INNER JOIN ( SELECT  IFNULL(count(o.account_name),0) as a,o.platform,o.server_id FROM `order` AS o WHERE DATEDIFF(o.finish_time,c_date) = 0 AND o.account_name IN(SELECT t.account_name FROM `order` AS t WHERE DATEDIFF(t.finish_time,c_date) = 7 AND t.account_name NOT IN(SELECT account FROM account WHERE DATEDIFF(account.create_time,c_date) < 7))GROUP BY o.platform,o.server_id) AS T2 ON f_pay_retention.platform = T2.platform AND f_pay_retention.server_id = T2.server_id AND datediff( c_date, create_date) = 0 SET f_pay_retention.f_pay_count01 = T2.a;
		-- 首付7日留存,分渠道
		UPDATE f_pay_retention INNER JOIN ( SELECT  IFNULL(count(o.account_name),0) as a,o.platform,s_id FROM `order` AS o WHERE DATEDIFF(o.finish_time,c_date) = 0 AND o.account_name IN(SELECT t.account_name FROM `order` AS t WHERE DATEDIFF(t.finish_time,c_date) = 7 AND t.account_name NOT IN(SELECT account FROM account WHERE DATEDIFF(account.create_time,c_date) < 7))GROUP BY o.platform) AS T2 ON f_pay_retention.platform = T2.platform AND datediff( c_date, f_pay_retention.create_date) = 0 SET f_pay_retention.f_pay_count01 = T2.a;
		
	END IF;
	
END
$$

DELIMITER ;


-- ----------------------------- ########################################

DELIMITER $$
DROP PROCEDURE IF EXISTS start_ltv$$
CREATE PROCEDURE start_ltv()
BEGIN
	-- 30 日 LTV 表 ---
	#Routine body goes here...
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);

	
	-- 创建(设置时间的)前一天要统计的留存数据 
		-- 分渠道，分服
	INSERT INTO pay_lty(create_date,platform,server_id,user_add) SELECT c_date,account.platform,account.prev_srv_id,COUNT(DISTINCT account.account) FROM account WHERE DATEDIFF(create_time,c_date ) =0 GROUP BY account.platform,account.prev_srv_id;


	-- 开始计算 首付次日留存,首付7日留存
	
	-- 统计当日付费总 
	IF exists(select * from pay_lty where datediff(create_date,c_date) = 0)
THEN
		-- 统计当日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 0 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) = 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date) = 0)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = 0 SET pay_lty.pay_01 = T2.a;
END IF;	

	
	IF exists(select * from pay_lty where datediff(create_date,c_date) = -1)
THEN
		-- 统计1日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 1 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time,c_date) BETWEEN -1 AND 0  AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date) = -1)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -1 SET pay_lty.pay_02 = T2.a;
	END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -2)
THEN
		-- 统计2日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 2 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -2 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date) = -2)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -2 SET pay_lty.pay_03 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -3)
THEN
		-- 统计3日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 3 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -3 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -3)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -3 SET pay_lty.pay_04 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -4)
THEN
-- 统计4日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 4 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -4 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -4)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -4 SET pay_lty.pay_05 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -5)
THEN
-- 统计5日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 5 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -5 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -5)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -5 SET pay_lty.pay_06 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -6)
THEN
-- 统计6日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 6 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -6 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -6)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -6 SET pay_lty.pay_07 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) =-7)
THEN
-- 统计7日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 7 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -7 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -7)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -7 SET pay_lty.pay_08 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -8)
THEN
-- 统计8日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 8 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -8 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -8)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -8 SET pay_lty.pay_09 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -9)
THEN
-- 统计9日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 9 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -9 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -9)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -9 SET pay_lty.pay_10 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -10)
THEN
-- 统计10日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 10 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -10 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -10)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -10 SET pay_lty.pay_11 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -11)
THEN
-- 统计11日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 11 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -11 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -11)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -11 SET pay_lty.pay_12 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -12)
THEN
-- 统计12日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 12 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -12 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -12)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -12 SET pay_lty.pay_13 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -13)
THEN
-- 统计13日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 13 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -13 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -13)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -13 SET pay_lty.pay_14 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -14)
THEN
-- 统计14日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 14 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -14 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -14)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -14 SET pay_lty.pay_15 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -15)
THEN
-- 统计15日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 15 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date)BETWEEN -15 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -15)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -15 SET pay_lty.pay_16 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -16)
THEN
-- 统计16日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 16 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -16 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -16)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -16 SET pay_lty.pay_17 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -17)
THEN
-- 统计17日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 17 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -17 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -17)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -17 SET pay_lty.pay_18 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -18)
THEN
-- 统计18日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 18 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -18 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -18)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -18 SET pay_lty.pay_19 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -19)
THEN
-- 统计19日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 19 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -19 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -19)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -19 SET pay_lty.pay_20 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -20)
THEN
-- 统计20日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 20 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -20 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -20)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -20 SET pay_lty.pay_21 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -21)
THEN
-- 统计21日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 21 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -21 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -21)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -21 SET pay_lty.pay_22 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -22)
THEN
-- 统计22日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 22 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -22 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -22)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -22 SET pay_lty.pay_23 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -23)
THEN
-- 统计23日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 23 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -23 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -23)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -23 SET pay_lty.pay_24 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -24)
THEN
-- 统计24日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 24 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -24 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -24)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -24 SET pay_lty.pay_25 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -25)
THEN
-- 统计25日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 25 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date)BETWEEN -25 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -25)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -25 SET pay_lty.pay_26 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -26)
THEN
-- 统计26日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 26 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -26 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -26)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -26 SET pay_lty.pay_27 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -27)
THEN
-- 统计27日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 27 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date)BETWEEN -27 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -27)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -27 SET pay_lty.pay_28 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -28)
THEN
-- 统计28日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 28 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -28 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -28)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -28 SET pay_lty.pay_29 = T2.a;
END IF;

	IF exists(select * from pay_lty where datediff(create_date,c_date) = -29)
THEN
-- 统计29日付费总 
		UPDATE pay_lty INNER JOIN (SELECT SUBDATE(c_date,INTERVAL 29 DAY) AS t_time, SUM(amount) as a, t.platform, t.server_id FROM `order` AS t WHERE DATEDIFF(t.finish_time, c_date) BETWEEN -29 AND 0 AND t.account_name IN ( SELECT account FROM account WHERE DATEDIFF(create_time,c_date ) = -29)GROUP BY t_time, t.platform, t.server_id) AS T2 ON pay_lty.platform = T2.platform AND pay_lty.server_id = T2.server_id AND datediff( pay_lty.create_date,c_date ) = -29 SET pay_lty.pay_30 = T2.a;
END IF;
	
END
$$

DELIMITER ;


-- ----------------------------- ########################################
DELIMITER $$
DROP PROCEDURE IF EXISTS start_new_pay$$
CREATE PROCEDURE start_new_pay()
BEGIN
	-- 统计新付费人数 
	#Routine body goes here...
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
	INSERT INTO n_pay (create_time,n_user_pay_money,n_user_pay,n_user_pay_r,server_id,platform)SELECT V.t_time, IFNULL(SUM(V2.payNew),0) AS newPay, IFNULL(SUM(V2.newPPCount),0) AS newPPayCount, IFNULL(SUM(V2.newPPCountR),0) AS newPPayCountR, V.prev_srv_id, V.platform FROM ( SELECT DATE_FORMAT( account.create_time, "%Y-%m-%d" ) AS t_time, platform, prev_srv_id, account FROM account WHERE DATEDIFF(c_date,create_time) = 0 GROUP BY t_time, prev_srv_id, platform, account ) AS V LEFT JOIN ( SELECT DATE_FORMAT(t.finish_time, "%Y-%m-%d") AS t_time, SUM(t.amount) AS payNew, COUNT(DISTINCT t.account_name) AS newPPCount, COUNT(t.account_name) AS newPPCountR, platform, server_id, t.account_name FROM `order` AS t WHERE DATEDIFF(c_date,t.finish_time) = 0 GROUP BY t_time, t.server_id, t.platform, t.account_name ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id AND V.platform = V2.platform AND V.account = V2.account_name GROUP BY V.prev_srv_id,V.platform ;
		-- 分渠道
	INSERT INTO n_pay (create_time,n_user_pay_money,n_user_pay,n_user_pay_r,server_id,platform)SELECT V.t_time, IFNULL(SUM(V2.payNew),0) AS newPay, IFNULL(SUM(V2.newPPCount),0) AS newPPayCount, IFNULL(SUM(V2.newPPCountR),0) AS newPPayCountR, s_id, V.platform FROM ( SELECT DATE_FORMAT( account.create_time, "%Y-%m-%d" ) AS t_time, platform, prev_srv_id, account FROM account WHERE DATEDIFF(c_date,create_time) = 0 GROUP BY t_time, prev_srv_id, platform, account ) AS V LEFT JOIN ( SELECT DATE_FORMAT(t.finish_time, "%Y-%m-%d") AS t_time, SUM(t.amount) AS payNew, COUNT(DISTINCT t.account_name) AS newPPCount, COUNT(t.account_name) AS newPPCountR, platform, server_id, t.account_name FROM `order` AS t WHERE DATEDIFF(c_date,t.finish_time) = 0 GROUP BY t_time, t.server_id, t.platform, t.account_name ) AS V2 ON V.t_time = V2.t_time  AND V.prev_srv_id = V2.server_id AND V.platform = V2.platform AND V.account = V2.account_name GROUP BY V.platform ;
END
$$

DELIMITER ;

-- ----------------------------- ########################################
DELIMITER $$
DROP PROCEDURE IF EXISTS start_ns_dau_summary$$
CREATE PROCEDURE start_ns_dau_summary()
BEGIN
	#Routine body goes here...
	-- 新服活跃数据
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	-- DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);


	-- 每次检查是否有创建新的服务器， 如果有插入到 ，统计表中
	INSERT INTO ns_dau_summary (s_id,s_name,open_time,platform) SELECT s.id,s.name,s.open_time,s.platforms from `server` AS s WHERE id NOT in(select t.s_id FROM ns_dau_summary as t);

	-- ---------------------
	-- 开始统计活跃
	-- ---------------------
	
	-- 当存在 开服 2天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 2)
THEN
	-- 第2天活跃人数
	UPDATE ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT login_log.account_id) AS dau2 FROM ns_income_summary AS t LEFT JOIN login_log ON login_log.server_id = t.s_id AND DATEDIFF(login_log.last_log_time, t.open_time) = 2 GROUP BY t.s_id) as T1 ON T1.s_id = ns_dau_summary.s_id AND T1.s_name = ns_dau_summary.s_name AND DATEDIFF(c_date,ns_dau_summary.open_time) = 2 SET ns_dau_summary.dau_day2 = T1.dau2;
	
	-- 第2天活跃付费人数
	UPDATE ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) =2  GROUP BY t.s_id) as T1 ON T1.s_id = ns_dau_summary.s_id AND T1.s_name = ns_dau_summary.s_name AND DATEDIFF(c_date,ns_dau_summary.open_time) = 2 SET ns_dau_summary.dau_pay_day2 = T1.paySum;

	END IF;

-- 当存在 开服 3天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 3)
THEN
	-- 第3天注册人数
	UPDATE ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT login_log.account_id) AS dau3 FROM ns_income_summary AS t LEFT JOIN login_log ON login_log.server_id = t.s_id AND DATEDIFF(login_log.last_log_time, t.open_time) = 3 GROUP BY t.s_id) as T1 ON T1.s_id = ns_dau_summary.s_id AND T1.s_name = ns_dau_summary.s_name AND DATEDIFF(c_date,ns_dau_summary.open_time) = 3 SET ns_dau_summary.dau_day3 = T1.dau3;
	
	-- 第3天活跃付费人数
	UPDATE ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) =3  GROUP BY t.s_id) as T1 ON T1.s_id = ns_dau_summary.s_id AND T1.s_name = ns_dau_summary.s_name AND DATEDIFF(c_date,ns_dau_summary.open_time) = 3 SET ns_dau_summary.dau_pay_day2 = T1.paySum;

	END IF;

-- 当存在 开服 7天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 7)
THEN
	-- 第7天注册人数
	UPDATE ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT login_log.account_id) AS dau7 FROM ns_income_summary AS t LEFT JOIN login_log ON login_log.server_id = t.s_id AND DATEDIFF(login_log.last_log_time, t.open_time) = 7 GROUP BY t.s_id) as T1 ON T1.s_id = ns_dau_summary.s_id AND T1.s_name = ns_dau_summary.s_name AND DATEDIFF(c_date,ns_dau_summary.open_time) = 7 SET ns_dau_summary.dau_day7 = T1.dau7;
		
	-- 第7天活跃付费人数
	UPDATE ns_dau_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) =7  GROUP BY t.s_id) as T1 ON T1.s_id = ns_dau_summary.s_id AND T1.s_name = ns_dau_summary.s_name AND DATEDIFF(c_date,ns_dau_summary.open_time) = 7 SET ns_dau_summary.dau_pay_day2 = T1.paySum;

	END IF;	
	
END
$$

DELIMITER ;


-- ----------------------------- ########################################
DELIMITER $$
DROP PROCEDURE IF EXISTS start_ns_income_summary$$
CREATE PROCEDURE start_ns_income_summary()
BEGIN
	#Routine body goes here...
	-- 新服收入数据
	-- 读取传入进来的参数
	DECLARE n_date  date;
	DECLARE c_date date;
	-- DECLARE s_id int;
	set n_date = CURDATE();
	-- 这里为创建日，  eg 比如 2016-04-26 为 now_date 那么创建日为 2016-04-25。
	set c_date = date_sub(n_date,INTERVAL 1 day);


	-- 每次检查是否有创建新的服务器， 如果有插入到 ，统计表中
	INSERT INTO ns_income_summary (s_id,s_name,open_time,platform) SELECT s.id,s.name,s.open_time,s.platforms from `server` AS s WHERE id NOT in(select t.s_id FROM ns_income_summary as t);

	-- ---------------------
	-- 开始统计收入
	-- ---------------------
		
	-- 当存在 开服 2天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 2)
THEN
	-- 前2天注册人数
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id,t.s_name,COUNT(DISTINCT account.id) as regSum FROM ns_income_summary AS t LEFT JOIN account ON t.s_id = account.prev_srv_id AND DATEDIFF( account.create_time, t.open_time) BETWEEN 0 AND 2  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 2 SET ns_income_summary.reg_count_day2 = T1.regSum;
	
	-- 前2天付费人数
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) BETWEEN 0 AND 2 GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 2 SET ns_income_summary.reg_pay_count_day2 = T1.paySum;

	-- 第2日付费总额
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 2  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 2 SET ns_income_summary.paySum_day2 = T1.paySum;
	END IF;

-- 当存在 开服 3天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 3)
THEN
	-- 前3天注册人数
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id,t.s_name,COUNT(DISTINCT account.id) as regSum FROM ns_income_summary AS t LEFT JOIN account ON t.s_id = account.prev_srv_id AND DATEDIFF( account.create_time, t.open_time) BETWEEN 0 AND 3  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 3 SET ns_income_summary.reg_count_day3 = T1.regSum;
	
	-- 前3天付费人数
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) BETWEEN 0 AND 3  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 3 SET ns_income_summary.reg_pay_count_day3 = T1.paySum;
	
	-- 第3日付费总额
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 3  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) =3 SET ns_income_summary.paySum_day3 = T1.paySum;
	END IF;

-- 当存在 开服 7天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 7)
THEN
	-- 前7天注册人数
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id,t.s_name,COUNT(DISTINCT account.id) as regSum FROM ns_income_summary AS t LEFT JOIN account ON t.s_id = account.prev_srv_id AND DATEDIFF( account.create_time, t.open_time) BETWEEN 0 AND 7  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 7 SET ns_income_summary.reg_count_day7 = T1.regSum;
	
	-- 前7天付费人数
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, COUNT(DISTINCT o.account_name) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) BETWEEN 0 AND 7  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 7 SET ns_income_summary.reg_pay_count_day7 = T1.paySum;
	
	-- 第7日付费总额
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 7  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 7 SET ns_income_summary.paySum_day7 = T1.paySum;
	END IF;

-- 当存在 开服 1天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 1)
THEN
	-- 第1日付费总额
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 1  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 1 SET ns_income_summary.paySum_day1 = T1.paySum;
	END IF;

-- 当存在 开服 4天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 4)
THEN
	-- 第4日付费总额
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 4  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 4 SET ns_income_summary.paySum_day4 = T1.paySum;
	END IF;

-- 当存在 开服 5天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 5)
THEN
	-- 第5日付费总额
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 5  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 5 SET ns_income_summary.paySum_day5 = T1.paySum;
	END IF;


-- 当存在 开服 6天的服务器.
	IF exists(SELECT * FROM ns_income_summary WHERE DATEDIFF(c_date,open_time) = 6)
THEN
	-- 第6日付费总额
	UPDATE ns_income_summary INNER JOIN(SELECT t.s_id, t.s_name, SUM(o.amount) as paySum FROM ns_income_summary AS t LEFT JOIN `order` as o ON t.s_id = o.server_id AND DATEDIFF( o.finish_time, t.open_time) = 6  GROUP BY t.s_id) as T1 ON T1.s_id = ns_income_summary.s_id AND T1.s_name = ns_income_summary.s_name AND DATEDIFF(c_date,ns_income_summary.open_time) = 6 SET ns_income_summary.paySum_day6 = T1.paySum;
	END IF;
	
	
END
$$

DELIMITER ;


-- ----------------------------- ########################################
DELIMITER $$
DROP PROCEDURE IF EXISTS start_retention_player$$
CREATE PROCEDURE start_retention_player()
BEGIN
#Routine body goes here...
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
	INSERT INTO retention (platform,server_id,dru, create_date) SELECT platform, server_id, count(DISTINCT account_id),c_date FROM login_log WHERE datediff(c_date, register_time) = 0 GROUP BY platform,server_id;
		-- 分渠道
	INSERT INTO retention (platform,server_id,dru, create_date) SELECT platform, s_id, count(DISTINCT account_id),c_date FROM login_log WHERE datediff(c_date, register_time) = 0 GROUP BY platform;

	-- 开始统计留存数据 
	--  判断前一天是否有可以根性的数据，
	--  如果有就进行更新
	--  没有就跳过
	-- ---------------------
	-- 与创建日相差一天 (就是次日留存)
	IF exists(select * from retention where datediff(c_date,create_date) = 1)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前1天新增用户,现在还登录的/创建日前1天新增用户)*100 ) 
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM login_log WHERE datediff(c_date, register_time) = 1 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 1 SET retention.retained1 = T2.a;
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM login_log WHERE datediff(c_date, register_time) = 1 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 1 SET retention.retained1 = T2.a;
	END IF;
	-- 3 日留存
	IF exists(select * from retention where datediff(c_date,create_date) = 2)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前3天新增用户,现在还登录的/创建日前3天新增用户)*100 ) 
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM login_log WHERE datediff(c_date, register_time) = 2 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 2 SET retention.retained3 = T2.a;
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM login_log WHERE datediff(c_date, register_time) = 2 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 2 SET retention.retained3 = T2.a;
	
	END IF;
-- 7 日留存
	IF exists(select * from retention where datediff(c_date,create_date) = 6)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前7天新增用户,现在还登录的/创建日前7天新增用户)*100 ) 
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM login_log WHERE datediff(c_date, register_time) = 6 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 6 SET retention.retained7 = T2.a;
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM login_log WHERE datediff(c_date, register_time) = 6 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 6 SET retention.retained7 = T2.a;
		
	END IF;
-- 14 日留存
	IF exists(select * from retention where datediff(c_date,create_date) = 13)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前14天新增用户,现在还登录的/创建日前14天新增用户)*100 ) 
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM login_log WHERE datediff(c_date, register_time) = 13 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 13 SET retention.retained14 = T2.a;
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM login_log WHERE datediff(c_date, register_time) = 13 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 13 SET retention.retained14 = T2.a;
		
	END IF;

-- 30 日留存
	IF exists(select * from retention where datediff(c_date,create_date) = 29)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前30天新增用户,现在还登录的/创建日前30天新增用户)*100 ) 
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM login_log WHERE datediff(c_date, register_time) = 29 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 29 SET retention.retained30 = T2.a;
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM login_log WHERE datediff(c_date, register_time) = 29 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 29 SET retention.retained30 = T2.a;
	END IF;
-- 31 日留存
	IF exists(select * from retention where datediff(c_date,create_date) = 30)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前31天新增用户,现在还登录的/创建日前31天新增用户)*100 ) 
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM login_log WHERE datediff(c_date, register_time) = 30 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 30 SET retention.retained31 = T2.a;
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM login_log WHERE datediff(c_date, register_time) = 30 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 30 SET retention.retained31 = T2.a;
	END IF;
-- 32 日留存
	IF exists(select * from retention where datediff(c_date,create_date) = 31)
THEN
		-- 结构为  update retention set retainedx = (select  ( select 创建日前32天新增用户,现在还登录的/创建日前32天新增用户)*100 ) 
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, server_id FROM login_log WHERE datediff(c_date, register_time) = 31 AND datediff(c_date, last_log_time) = 0 GROUP BY platform, server_id) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 31 SET retention.retained32 = T2.a;
		UPDATE retention INNER JOIN ( SELECT count(DISTINCT account_id) AS a, platform, s_id as server_id FROM login_log WHERE datediff(c_date, register_time) = 31 AND datediff(c_date, last_log_time) = 0 GROUP BY platform) AS T2 ON retention.platform = T2.platform AND retention.server_id = T2.server_id AND datediff( c_date, retention.create_date) = 31 SET retention.retained32 = T2.a;
	END IF;
END
$$

DELIMITER ;



-- ----------------------------- ########################################
-- 用户转化
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
	INSERT INTO new_u_change(t_time,server_id,platform,new_reg) SELECT c_date,prev_srv_id,platform,COUNT(account) as new_reg FROM account WHERE  DATEDIFF(account.create_time,c_date) = 0 GROUP BY prev_srv_id,platform;

	IF exists(select * from new_u_change where datediff(c_date,t_time) = 0)
		THEN

			-- 游戏中登录
			UPDATE new_u_change INNER JOIN( SELECT COUNT(DISTINCT account_id) AS a,platform, server_id FROM game_login_user_cache WHERE account_id IN (SELECT id as account_id FROM account WHERE  DATEDIFF(account.create_time,c_date) = 0 ) GROUP BY server_id,platform ) AS T2 ON new_u_change.platform = T2.platform AND new_u_change.server_id = T2.server_id AND datediff( c_date, new_u_change.t_time) = 0 SET new_u_change.login_u = T2.a;
			-- 游戏中有效用户
			UPDATE new_u_change INNER JOIN( SELECT COUNT(DISTINCT account_id) AS a,platform, server_id FROM game_valid_user_cache WHERE account_id IN (SELECT id as account_id FROM account WHERE  DATEDIFF(account.create_time,c_date) = 0 ) GROUP BY server_id,platform ) AS T2 ON new_u_change.platform = T2.platform AND new_u_change.server_id = T2.server_id AND datediff( c_date, new_u_change.t_time) = 0 SET new_u_change.valid_u = T2.a;
			-- 游戏中付费用户
			UPDATE new_u_change INNER JOIN( SELECT COUNT(DISTINCT o.account_id) AS a,o.platform, o.server_id FROM `order` as o WHERE o.account_id IN (SELECT id as account_id FROM account WHERE  DATEDIFF(account.create_time,c_date) = 0 ) GROUP BY server_id,platform ) AS T2 ON new_u_change.platform = T2.platform AND new_u_change.server_id = T2.server_id AND datediff( c_date, new_u_change.t_time) = 0 SET new_u_change.pay_u = T2.a;
			-- 激活设备
			UPDATE new_u_change INNER JOIN(SELECT COUNT(DISTINCT deviceUUID) as a,platform FROM device_reg WHERE  DATEDIFF(reg_time,c_date) = 0 GROUP BY platform) AS T2 ON new_u_change.platform = T2.platform AND datediff( c_date, new_u_change.t_time) = 0 SET new_u_change.new_drive = T2.a;

		END IF;	
	
		-- 清空 当日游戏注册用户数据
		DELETE FROM game_login_user_cache;

		-- 清空 当日有效用户注册数据
		DELETE FROM game_valid_user_cache;
END
$$
DELIMITER ;


-- ----------------------------- ########################################
-- 用户转化
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

-- 游戏每小时在线统计	
INSERT INTO game_per_hour_online_summary(platform,server_id,log_time,
hour_01,hour_02,hour_03,hour_04,hour_05,hour_06,hour_07,hour_08,hour_09,hour_10,
hour_11,hour_12,hour_13,hour_14,hour_15,hour_16,hour_17,hour_18,hour_19,hour_20,
hour_21,hour_22,hour_23,hour_24)
SELECT
	T1.platform,
	T1.server_id,
	c_date as t_time,
	SUM(T1.t1) as hour_01,
	SUM(T1.t2) as hour_02,
	SUM(T1.t3) as hour_03,
	SUM(T1.t4) as hour_04,
	SUM(T1.t5) as hour_05,
	SUM(T1.t6) as hour_06,
	SUM(T1.t7) as hour_07,
	SUM(T1.t8) as hour_08,
	SUM(T1.t9) as hour_09,
	SUM(T1.t10) as hour_10,
	SUM(T1.t11) as hour_11,
	SUM(T1.t12) as hour_12,
	SUM(T1.t13) as hour_13,
	SUM(T1.t14) as hour_14,
	SUM(T1.t15) as hour_15,
	SUM(T1.t16) as hour_16,
	SUM(T1.t17) as hour_17,
	SUM(T1.t18) as hour_18,
	SUM(T1.t19) as hour_19,
	SUM(T1.t20) as hour_20,
	SUM(T1.t21) as hour_21,
	SUM(T1.t22) as hour_22,
	SUM(T1.t23) as hour_23,
	SUM(T1.t24) as hour_24
FROM
(SELECT 
	CASE  WHEN  1 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t1 ,
	CASE  WHEN  2 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t2,
	CASE  WHEN  3 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t3,
	CASE  WHEN  4 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t4,
	CASE  WHEN  5 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t5,
	CASE  WHEN  6 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t6,
	CASE  WHEN  7 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t7,
	CASE  WHEN  8 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t8,
	CASE  WHEN  9 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t9,
	CASE  WHEN  10 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t10,
	CASE  WHEN  11 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t11,
	CASE  WHEN  12 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t12,
	CASE  WHEN  13 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t13,
	CASE  WHEN  14 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t14,
	CASE  WHEN  15 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t15 ,
	CASE  WHEN  16 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t16,
	CASE  WHEN  17 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t17,
	CASE  WHEN  18 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t18,
	CASE  WHEN  19 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t19,
	CASE  WHEN  20 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t20,
	CASE  WHEN  21 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t21,
	CASE  WHEN  22 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t22,
	CASE  WHEN  23 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t23,
	CASE  WHEN  24 BETWEEN l_time AND o_time  THEN  COUNT(DISTINCT T.home_id) ELSE 0 END as t24,
	platform,server_id
FROM
(SELECT
	home_id,
	IFNULL(DATE_FORMAT(l_time,"%h"),0) as l_time,
	IFNULL(DATE_FORMAT(o_time,"%h"),24) as o_time,
	server_id,
	platform
FROM game_online_user_cache) AS T GROUP BY T.l_time,T.o_time,T.server_id,T.platform
)AS T1 GROUP BY T1.server_id,T1.platform;


-- 统计acu & pcu 

INSERT INTO acu_pcu_summary(acu,pcu,platform,server_id,t_time)
SELECT
	greatest(a.hour_01,a.hour_02,a.hour_03,a.hour_04,a.hour_05,a.hour_06,a.hour_07,a.hour_08,a.hour_09,a.hour_10,
	a.hour_11,a.hour_12,a.hour_13,a.hour_14,a.hour_15,a.hour_16,a.hour_17,a.hour_18,a.hour_19,a.hour_20,
	a.hour_21,a.hour_22,a.hour_23,a.hour_24) as acu,
	FORMAT(a.totalSum / 24,2) as pcu,
	a.platform,
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
platform,
server_id
 FROM  game_per_hour_online_summary WHERE DATEDIFF(log_time,c_date) = 0 ) as a;

-- 清空 当日游戏注册用户数据
DELETE FROM game_online_user_cache;

END
$$
DELIMITER ;
