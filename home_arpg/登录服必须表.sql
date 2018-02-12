-- -------------------- 登录服务器必备


-- ----------------------------
--  Table structure for `ban_pay_pt`
-- ----------------------------
DROP TABLE IF EXISTS `ban_pay_pt`;
CREATE TABLE `ban_pay_pt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform` varchar(255) NOT NULL,
  `sub_platform` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pt_subpt` (`platform`,`sub_platform`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ban_ip`
-- ----------------------------
DROP TABLE IF EXISTS `ban_ip`;
CREATE TABLE `ban_ip` (
  `ip` varchar(32) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gift_uc
-- ----------------------------
DROP TABLE IF EXISTS `gift_uc`;
CREATE TABLE `gift_uc` (
  `gift_id` varchar(32) NOT NULL COMMENT 'uc礼包id',
  `type` int(11) NOT NULL COMMENT '礼包类型',
  `content` varchar(5000) NOT NULL COMMENT '奖励内容,格式： 物品id_数量;物品id_数量;',
  `count` int(11) NOT NULL COMMENT '礼包中奖励数量',
  PRIMARY KEY (`gift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gift_uc_records
-- ----------------------------
DROP TABLE IF EXISTS `gift_uc_records`;
CREATE TABLE `gift_uc_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gift_id` varchar(32) NOT NULL COMMENT 'uc礼包id',
  `server_id` int(11) NOT NULL COMMENT '领取到服务器id',
  `player_id` int(11) NOT NULL COMMENT '在游戏服的角色id, role_id',
  `uc_account_id` varchar(255) NOT NULL COMMENT 'uc平台帐号',
  `receive_time` datetime NOT NULL COMMENT '领取时间',
  `unique_key` varchar(64) NOT NULL COMMENT '用来做唯一约束，gift_id+server_id+player_id+day',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key_idx` (`unique_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vindicator_ip
-- ----------------------------
DROP TABLE IF EXISTS `vindicator_ip`;
CREATE TABLE `vindicator_ip` (
  `ip` varchar(255) NOT NULL DEFAULT '' COMMENT 'IP地址',
  `note` varchar(255) DEFAULT '' COMMENT 'IP相关的说明',
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shield_sys
-- ----------------------------
DROP TABLE IF EXISTS `shield_sys`;
CREATE TABLE `shield_sys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(255) DEFAULT NULL,
  `redeemSys` int(255) DEFAULT NULL,
  `webSite` int(255) DEFAULT NULL,
  `contactCust` int(255) DEFAULT NULL,
  `rankingSys` int(255) DEFAULT NULL,
  `monthCard` int(255) DEFAULT NULL,
  `silentDownloadRes` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for account  游戏帐号表
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(128) NOT NULL COMMENT '账号',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `platform` varchar(128) NOT NULL DEFAULT '' COMMENT '平台',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `prev_srv_id` int(11) NOT NULL DEFAULT '0' COMMENT '上次登录的服务器ID',
  `login_key` int(11) NOT NULL DEFAULT '0' COMMENT '登陆Key, 每次登陆都更换.',
  `vindicator` tinyint(11) DEFAULT '0' COMMENT '是否是测试人员',
  `systemInfo` text COMMENT '设备字段',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '登陆时间',
  `lastip` char(128) DEFAULT '',
  `version` char(64) DEFAULT '',
  `platform_ext` varchar(255) DEFAULT NULL,
  `accountActived` tinyint(11) NOT NULL DEFAULT '0' COMMENT '是否使用激活码激活',
  `subPlatform` varchar(255) DEFAULT NULL COMMENT '子渠道',
  `third_user_id` varchar(128) DEFAULT NULL COMMENT '第三方渠道用户id,例,易接的子渠道用户id',
  `cdk_types` text COMMENT '获得cdk 类型 : playerid:[type,type]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_platform` (`account`,`platform`) USING BTREE,
  KEY `vindicator` (`vindicator`) USING BTREE,
  KEY `third_user_id` (`third_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for account_bind
-- ----------------------------
DROP TABLE IF EXISTS `account_bind`;
CREATE TABLE `account_bind` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) NOT NULL,
  `srv_id` int(11) NOT NULL COMMENT '服务器ID',
  `player_id` int(11) NOT NULL,
  `player_name` varchar(255) NOT NULL DEFAULT '',
  `player_lv` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `iconUrl` char(32) NOT NULL DEFAULT '',
  `iconFrame` int(11) unsigned zerofill NOT NULL DEFAULT '00000000000',
  `fightingCapacity` int(11) unsigned zerofill NOT NULL DEFAULT '00000000000',
  `vipLv` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for activationcode 激活码表
-- ----------------------------
DROP TABLE IF EXISTS `activationcode`;
CREATE TABLE `activationcode` (
  `code` char(64) NOT NULL,
  `closeTime` datetime DEFAULT NULL,
  `account` varchar(255) NOT NULL,
  `invaild` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cdkey
-- ----------------------------
DROP TABLE IF EXISTS `cdkey`;
CREATE TABLE `cdkey` (
  `key` char(64) NOT NULL,
  `award` varchar(5000) NOT NULL,
  `global` int(11) NOT NULL DEFAULT '0' COMMENT '是否全局, 1为是, 0为否',
  `enable` int(11) NOT NULL DEFAULT '0' COMMENT '是否领取过, 1为领取过',
  `serverid` int(11) NOT NULL DEFAULT '0' COMMENT '被那个服务器的领取了',
  `playerid` int(11) NOT NULL DEFAULT '0' COMMENT '领取的玩家ID',
  `overTime` datetime DEFAULT NULL COMMENT '礼包过期时间',
  `platformLimit` varchar(255) DEFAULT NULL COMMENT '礼包平台限制 xxx;xxx',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '礼包类型',
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for device_reg
-- ----------------------------
DROP TABLE IF EXISTS `device_reg`;
CREATE TABLE `device_reg` (
  `deviceUI` varchar(255) DEFAULT NULL COMMENT '设备号',
  `OS` varchar(255) DEFAULT NULL COMMENT '系统',
  `deviceModel` varchar(255) DEFAULT NULL,
  `systemInfo` text,
  `reg_time` datetime DEFAULT NULL COMMENT '设备注册时间',
  `platform` varchar(255) DEFAULT NULL COMMENT '平台'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


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


DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL,
  `notice` text NOT NULL,
  `enable` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否启用',
  `s_id` varchar(2000) DEFAULT NULL COMMENT '服务器id s_id,s_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for package
-- ----------------------------
DROP TABLE IF EXISTS `package`;
CREATE TABLE `package` (
  `id` int(11) NOT NULL,
  `platform` char(64) NOT NULL,
  `version` char(32) NOT NULL,
  `resversion` char(32) NOT NULL,
  `resurl` char(255) NOT NULL,
  `notice` text,
  `type` int(255) DEFAULT NULL COMMENT '1.大版本 0小版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for param
-- ----------------------------
DROP TABLE IF EXISTS `param`;
CREATE TABLE `param` (
  `id` bigint(20) NOT NULL,
  `type` char(64) NOT NULL,
  `info` text,
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `id` int(11) NOT NULL,
  `name` char(255) NOT NULL,
  `state` tinyint(11) NOT NULL COMMENT '状态',
  `recommend` tinyint(11) DEFAULT '0' COMMENT '是否推荐',
  `tag` varchar(255) DEFAULT '' COMMENT '备注',
  `platforms` varchar(255) DEFAULT '' COMMENT '平台标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for server
-- ----------------------------
DROP TABLE IF EXISTS `server`;
CREATE TABLE `server` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `remark` varchar(255) NOT NULL DEFAULT '',
  `state` int(255) NOT NULL DEFAULT '0' COMMENT '服务器状态: 0正常 1关闭',
  `section` int(11) NOT NULL DEFAULT '0' COMMENT '区',
  `recommend` int(11) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `operate` tinyint(255) NOT NULL DEFAULT '0',
  `maintainText` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '优先级',
  `platforms` varchar(255) DEFAULT '' COMMENT '平台标签',
  `opentime` int(11) NOT NULL DEFAULT '0' COMMENT '定时开服时间',
  `open_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(11) NOT NULL,
  `platform` char(128) COLLATE utf8_bin NOT NULL,
  `version` char(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `url` char(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `resversion` char(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `resurl` char(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `notice` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `check` (`platform`,`version`,`resversion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;





-- ----------------------------
--  Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id,自动增长',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  `finish_time` datetime NOT NULL COMMENT '订单完成时间',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_price` float NOT NULL COMMENT '商品价格',
  `amount` float NOT NULL COMMENT '实际支付金额,在平台支付完成后由平台通知',
  `server_id` int(11) NOT NULL COMMENT '区服id',
  `account_id` int(11) NOT NULL COMMENT '玩家账号id',
  `account_name` varchar(128) NOT NULL COMMENT '玩家账号名',
  `player_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '玩家角色名',
  `platform` varchar(64) NOT NULL COMMENT '平台标识,玩家账号注册时的平台',
  `platform_order_no` varchar(64) NOT NULL COMMENT '平台订单号',
  `pay_type` varchar(64) DEFAULT NULL COMMENT '支付方式,由平台提供',
  `server_state` int(11) NOT NULL COMMENT '游戏服状态,0正常,1未能连接到游戏服,2游戏服返回异常',
  `platform_order_no_sign` varchar(128) NOT NULL COMMENT '生成方式,platform+platform_order_no,用来检查重复订单',
  `ext` varchar(512) DEFAULT NULL COMMENT '扩展内容',
  PRIMARY KEY (`order_id`),
  KEY `idx_ftime` (`finish_time`),
  KEY `idx_amount` (`amount`),
  KEY `idx_acid_acname_pname` (`account_id`,`account_name`,`player_name`),
  KEY `idx_ platform` (`platform`),
  KEY `idx_platform_order_no_sign` (`platform_order_no_sign`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `order_del`
-- ----------------------------
DROP TABLE IF EXISTS `order_del`;
CREATE TABLE `order_del` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id,自动增长',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  `finish_time` datetime NOT NULL COMMENT '订单完成时间',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_price` float NOT NULL COMMENT '商品价格',
  `amount` float NOT NULL COMMENT '实际支付金额,在平台支付完成后由平台通知',
  `server_id` int(11) NOT NULL COMMENT '区服id',
  `account_id` int(11) NOT NULL COMMENT '玩家账号id',
  `account_name` varchar(128) NOT NULL COMMENT '玩家账号名',
  `player_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '玩家角色名',
  `platform` varchar(64) NOT NULL COMMENT '平台标识,玩家账号注册时的平台',
  `platform_order_no` varchar(64) NOT NULL COMMENT '平台订单号',
  `pay_type` varchar(64) DEFAULT NULL COMMENT '支付方式,由平台提供',
  `server_state` int(11) NOT NULL COMMENT '游戏服状态,0正常,1未能连接到游戏服,2游戏服返回异常',
  `platform_order_no_sign` varchar(128) NOT NULL COMMENT '生成方式,platform+platform_order_no,用来检查重复订单',
  `ext` varchar(512) DEFAULT NULL COMMENT '扩展内容',
  PRIMARY KEY (`order_id`),
  KEY `idx_ftime` (`finish_time`),
  KEY `idx_amount` (`amount`),
  KEY `idx_acid_acname_pname` (`account_id`,`account_name`,`player_name`),
  KEY `idx_ platform` (`platform`),
  KEY `idx_platform_order_no_sign` (`platform_order_no_sign`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `order_del_summary`
-- ----------------------------
DROP TABLE IF EXISTS `order_del_summary`;
CREATE TABLE `order_del_summary` (
  `account_name` varchar(128) NOT NULL COMMENT '玩家账号名',
  `player_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '玩家角色名',
  `amount` float(11,0) NOT NULL COMMENT '总金额',
  `goods_ids` varchar(255) DEFAULT NULL COMMENT '购买过的物品id,以'',''分隔,不重复',
  `receive_time` datetime DEFAULT NULL COMMENT '领取时间,null表示没有领取',
  `server_id` int(11) NOT NULL DEFAULT '0' COMMENT '领取时所在服id',
  `platform` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`account_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `order_temp`
-- ----------------------------
DROP TABLE IF EXISTS `order_temp`;
CREATE TABLE `order_temp` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id,自动增长',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_price` float NOT NULL COMMENT '商品价格',
  `server_id` int(11) NOT NULL COMMENT '区服id',
  `account_id` int(11) NOT NULL COMMENT '玩家账号id',
  `account_name` varchar(128) NOT NULL COMMENT '玩家账号名',
  `player_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '玩家角色名',
  `ext` varchar(512) DEFAULT NULL COMMENT '扩展内容',
  `platform` varchar(16) NOT NULL COMMENT '平台标识,玩家账号注册时的平台',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

