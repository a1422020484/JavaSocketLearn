CREATE DATABASE IF NOT EXISTS `templet`;


CREATE TABLE `templet` (
   `player_id` int(11) DEFAULT NULL,
   `templet_name` varchar(32) DEFAULT NULL,
   `templet_address` varchar(32) DEFAULT NULL,
   `friend_list` text,
   `create_time` date DEFAULT NULL,
   `create_time_l` bigint(32) DEFAULT NULL,
   `friend_map` mediumblob
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8