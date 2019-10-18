CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ci` VARCHAR(100) NULL,
  `name` VARCHAR(45) NULL,
  `mdn` VARCHAR(45) NULL,
  `birthday` DATETIME NULL,
  `address_detail` VARCHAR(200) NULL,
  `address_basic` VARCHAR(200) NULL,
  `email` VARCHAR(200) NULL,
  `gender` VARCHAR(45) NULL,
  `user_type` VARCHAR(20) NOT NULL DEFAULT 'REAL',
  `auth_type` VARCHAR(20) NOT NULL DEFAULT 'SYRUP',
  `image_version` VARCHAR(200) NULL,
  `zip_code` VARCHAR(45) NULL,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NULL,
  `deleted_date` DATETIME NULL,
  `data_deleted_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `ci_idx` (`ci` ASC)
)ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_sso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sso_serial` varchar(200) NOT NULL,
  `sso_type` varchar(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_sso_serial_type_UNIQUE` (`sso_serial`,`sso_type`),
  CONSTRAINT `fk_user_sso_user1_idx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_loginable` (
  `login_id` varchar(15) NOT NULL,
  `password` varchar(200) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME,
  PRIMARY KEY (`login_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  CONSTRAINT `fk_user_loginable_user1_idx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_agreement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `clause_id` int(11) NOT NULL,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_clause_id_UNIQUE` (`user_id`,`clause_id`),
  KEY `fk_user_agreement_user1_idx_idx` (`user_id`),
  CONSTRAINT `fk_user_agreement_user1_idx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agreement_clauses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `is_mandatory` tinyint(1) NOT NULL DEFAULT '0',
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('1', '(필수) 서비스 이용약관 ', '(필수) 서비스 이용약관 ', '1');
INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('2', '(공통) 개인정보 수집/이용 동의', '(공통) 개인정보 수집/이용 동의', '0');
INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('3', '(필수) 개인정보 수집/이용 동의', '(필수) 개인정보 수집/이용 동의', '1');
INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('4', '(선택) 개인정보 수집/이용 동의 ', '(선택) 개인정보 수집/이용 동의 ', '0');
INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('5', '(선택) 혜택알림 수신동의 ', '(선택) 혜택알림 수신동의 ', '0');
INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('6', '(선택) 위치정보 수집/이용 및 위치기반서비스 이용약관 동의 ', '(선택) 위치정보 수집/이용 및 위치기반서비스 이용약관 동의 ', '0');
INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('7', '(공통) 오픈소스 라이센스', '(공통) 오픈소스 라이센스', '0');
INSERT INTO `agreement_clauses` (`id`, `title`, `description`, `is_mandatory`) VALUES ('8', '(필수) 14세 이상 확인', '(필수) 14세 이상 확인', '1');

CREATE TABLE `user_sign_history` (
  `user_id` INT NOT NULL ,
  `id`  BIGINT UNSIGNED NOT NULL,
  `type` ENUM('IN', 'OUT') NOT NULL ,
  `sign_date` DATETIME NOT NULL ,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk__idx` (`user_id` ASC),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `shoppingmall` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `banner_url` VARCHAR(1000) NULL,
  `detail_url` VARCHAR(1000) NULL,
  `display_order` INT(5) NULL,
  `banner_w` INT(11) NULL,
  `banner_h` INT(11) NULL,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;