
-- 04-18
ALTER TABLE `account` ADD COLUMN `accountActived` tinyint(11) NOT NULL DEFAULT 0 COMMENT '是否使用激活码激活';
DROP TABLE IF EXISTS `activationcode`;
CREATE TABLE `activationcode` (
  `code` char(64) NOT NULL,
  `closeTime` datetime DEFAULT NULL,
  `account` varchar(255) NOT NULL,
  `invaild` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 03-03
ALTER TABLE `account` ADD COLUMN `platform_ext` VARCHAR(255) NULL;

--01-21
ALTER TABLE `account`
ADD COLUMN `version`  char(64) NULL DEFAULT '' AFTER `lastip`;







--01-06
ALTER TABLE `server`
ADD COLUMN `opentime`  int(11) NOT NULL DEFAULT 0 COMMENT '定时开服时间' AFTER `platforms`;


--01-05
ALTER TABLE `account_bind`
ADD UNIQUE INDEX `account_srv_id` (`account_id`, `srv_id`) ;




--12-30
CREATE TABLE `preset` (
  `id` int(11) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `info` text,
  `remark` char(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




--12-25
ALTER TABLE `order`
ADD INDEX `idx_account_name` (`account_name`) ;



--12-21
ALTER TABLE `cdkey`
ENGINE=MyISAM;




--12-16
ALTER TABLE `account`
ADD COLUMN `lastip`  char(128) NULL DEFAULT '' AFTER `login_time`;





--12-15
CREATE TABLE `cdkey` (
  `key` char(64) NOT NULL,
  `award` char(64) NOT NULL,
  `global` int(11) NOT NULL DEFAULT '0' COMMENT '是否全局, 1为是, 0为否',
  `enable` int(11) NOT NULL DEFAULT '0' COMMENT '是否领取过, 1为领取过',
  `serverid` int(11) NOT NULL DEFAULT '0' COMMENT '被那个服务器的领取了',
  `playerid` int(11) NOT NULL DEFAULT '0' COMMENT '领取的玩家ID',
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--12-15
ALTER TABLE `server`
ADD COLUMN `platforms`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '平台标签' AFTER `priority`;



--12-05
ALTER TABLE `server`
ADD COLUMN `priority`  int(11) NOT NULL DEFAULT 0 COMMENT '优先级' AFTER `maintainText`;








--12-04
ALTER TABLE `account`
ADD COLUMN `login_time`  timestamp NULL DEFAULT NULL COMMENT '登陆时间' AFTER `systemInfo`;











--12-03
ALTER TABLE `account`
ADD COLUMN `systemInfo`  text NULL COMMENT '设备字段' AFTER `vindicator`;





--11-30
ALTER TABLE `version`
MODIFY COLUMN `url`  char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `version`;
ALTER TABLE `package`
MODIFY COLUMN `resurl`  char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `resversion`;



--11-28
CREATE TABLE `param` (
  `id` bigint(20) NOT NULL,
  `type` char(64) NOT NULL,
  `info` text,
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






--11-9
DROP TABLE IF EXISTS `package`;
CREATE TABLE `package` (
  `id` int(11) NOT NULL,
  `platform` char(64) NOT NULL,
  `version` char(32) NOT NULL,
  `resversion` char(32) NOT NULL,
  `resurl` char(32) NOT NULL,
  `notice` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(11) NOT NULL,
  `platform` char(128) NOT NULL,
  `version` char(32) NOT NULL DEFAULT '',
  `url` char(32) NOT NULL DEFAULT '',
  `notice` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `check` (`platform`,`version`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;











--10-29
ALTER TABLE `section`
ADD COLUMN `platforms`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '平台标签' AFTER `tag`;







--10-28
CREATE TABLE `version` (
`id`  int NOT NULL ,
`platform`  char(128) NOT NULL ,
`version`  char(32) NOT NULL DEFAULT '' ,
`url`  char(32) NOT NULL DEFAULT '' ,
`resversion`  char(32) NOT NULL DEFAULT '' ,
`resurl`  char(32) NOT NULL ,
`notice`  text NOT NULL ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `version`
ADD UNIQUE INDEX `check` (`platform`, `version`, `resversion`) ;







--10-22
ALTER TABLE `account_bind`
ADD COLUMN `iconUrl`  char(32) NOT NULL DEFAULT '' AFTER `create_time`,
ADD COLUMN `iconFrame`  int(11) ZEROFILL NOT NULL DEFAULT 0 AFTER `iconUrl`,
ADD COLUMN `fightingCapacity`  int(11) ZEROFILL NOT NULL DEFAULT 0 AFTER `iconFrame`,
ADD COLUMN `vipLv`  int(11) NOT NULL DEFAULT 0 AFTER `fightingCapacity`;










--10-12
ALTER TABLE `server`
ADD COLUMN `maintainText`  varchar(255) NULL AFTER `operate`;




--10-09
ALTER TABLE `account`
ADD COLUMN `password`  varchar(255) NOT NULL DEFAULT '' COMMENT '密码' AFTER `account`,
ADD COLUMN `platform`  varchar(255) NOT NULL DEFAULT '' COMMENT '平台' AFTER `password`;


ALTER TABLE `account`
DROP INDEX `account` ,
ADD UNIQUE INDEX `account_platform` (`account`, `platform`) USING BTREE ;



--08-19
ALTER TABLE `server`
ADD COLUMN `operate`  tinyint(255) NOT NULL DEFAULT 0 AFTER `recommend`;









-- 7-14
ALTER TABLE `account`
ADD COLUMN `vindicator`  tinyint(11) NULL DEFAULT 0 COMMENT '是否是测试人员' AFTER `vindicator`;


--8-15

ALTER TABLE shield_sys ADD `weixin` int(1) DEFAULT '0';

CREATE TABLE `activity_info` (
  `type` int(11) NOT NULL COMMENT '类型',
  `id` int(11) NOT NULL COMMENT '活动id',
  `start_day_time` int(30) NOT NULL,
  `end_day_time` int(30) NOT NULL,
  `server_id` text NOT NULL COMMENT '区服id',
  `str_data` text NOT NULL COMMENT '服务器信息',
  UNIQUE KEY `priket` (`type`,`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全服充值活动信息'

CREATE TABLE `charge_active` (
  `account_id` int(30) NOT NULL COMMENT '帐号id',
  `account_name` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '帐号名',
  `money` double(30,0) DEFAULT NULL COMMENT '充值金额',
  `player_name` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '玩家名',
  `server_id` int(10) NOT NULL COMMENT '服务器Id',
  `is_send` bit(1) DEFAULT NULL COMMENT '是否发奖',
  `active_id` int(30) DEFAULT NULL COMMENT '所属活动id',
  `active_type` int(10) DEFAULT NULL COMMENT '活动类型',
  UNIQUE KEY `prikey` (`account_id`,`server_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='全服充值排行'




--11-21
ALTER TABLE account ADD `special` int(11) DEFAULT '0' COMMENT '特权0无,1有特权';
ALTER TABLE account ADD `specialcode`  text COMMENT '特权码';


--11-23

CREATE TABLE `accountdevice_reg` (
  `deviceUI` varchar(384) DEFAULT NULL,
  `OS` varchar(2295) DEFAULT NULL,
  `deviceModel` varchar(2295) DEFAULT NULL,
  `systemInfo` text,
  `reg_time` datetime DEFAULT NULL,
  `platform` varchar(2295) DEFAULT NULL,
  `subplatform` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



ALTER TABLE account ADD `games`  VARCHAR(255) DEFAULT '""';
ALTER TABLE account ADD `adPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE account ADD `adSubPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE account ADD `adVersion`  VARCHAR(255) DEFAULT '""';
ALTER TABLE account ADD `idfa`  VARCHAR(255) DEFAULT '""';

ALTER TABLE device_reg  ADD `subPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE device_reg  ADD `games`  VARCHAR(255) DEFAULT '""';
ALTER TABLE device_reg  ADD `adPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE device_reg  ADD `adSubPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE device_reg  ADD `adVersion`  VARCHAR(255) DEFAULT '""';
ALTER TABLE device_reg  ADD `idfa`  VARCHAR(255) DEFAULT '""';

ALTER TABLE `order`  ADD `subPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE `order`  ADD `games`  VARCHAR(255) DEFAULT '""';
ALTER TABLE `order`  ADD `adPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE `order`  ADD `adSubPlatform`  VARCHAR(255) DEFAULT '""';
ALTER TABLE `order`  ADD `adVersion`  VARCHAR(255) DEFAULT '""';

--11-29

ALTER TABLE device_reg  ADD `advType` int(1) DEFAULT '0';

CREATE TABLE `advmodel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `games` varchar(32) DEFAULT NULL,
  `adPlatform` varchar(32) DEFAULT NULL,
  `subPlatform` varchar(32) DEFAULT NULL,
  `version` varchar(32) DEFAULT NULL,
  `device` varchar(64) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `callback_url` text,
  `callback_param` text,
  `aid` text,
  `cid` text,
  `mac_sum` text,
  `mac_sum1` text,
  `androidid_sum` text,
  `ua` text,
  `os` varchar(32) DEFAULT NULL,
  `lbs` text,
  `ip` text,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--12-05
ALTER TABLE account ADD `allowChangePwd` int(1) DEFAULT '0';

ALTER TABLE advmodel ADD `advType` int(1) DEFAULT '0';


--18-01-03

CREATE TABLE `deviceseal` (
  `id` int(11) DEFAULT NULL,
  `del` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
