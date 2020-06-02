
-- neo数据库

CREATE TABLE `login_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name_index` (`login_name`) USING BTREE
) ENGINE=InnoDB;


CREATE TABLE `thumbs_up_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phones` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url_index` (`url`) USING BTREE
) ENGINE=InnoDB;