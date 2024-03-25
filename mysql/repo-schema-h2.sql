-- -----------------------------------------------------
-- Table `bmc_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_role` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srvId` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `type` INT NOT NULL COMMENT '类型',
  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `displayNameCN` VARCHAR(32) NOT NULL COMMENT '中文名称',
  `displayNameUS` VARCHAR(32) NOT NULL COMMENT '英文名称',
  `descriptionCN` VARCHAR(255) NULL COMMENT '中文描述',
  `descriptionUS` VARCHAR(255) NULL COMMENT '英文描述',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_account_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_account_role` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `accountId` BIGINT NULL COMMENT '帐号ID',
  `roleId` INT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_permission` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srvId` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `type` INT NULL COMMENT '类型',
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `parentName` VARCHAR(64) NULL COMMENT '父名称',
  `displayNameCN` VARCHAR(50) NULL COMMENT '中文名称',
  `displayNameUS` VARCHAR(50) NULL COMMENT '英文名称',
  `isAuthz` INT NULL COMMENT '是否要认证',
  `descriptionCN` VARCHAR(255) NULL COMMENT '中文描述',
  `descriptionUS` VARCHAR(255) NULL COMMENT '英文描述',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_role_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_role_permission` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srvId` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `permName` VARCHAR(64) NOT NULL COMMENT '权限名称',
  `roleName` VARCHAR(32) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_history_pwd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_history_pwd` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `accountId` BIGINT NOT NULL COMMENT '帐号ID',
  `historyPwd` VARCHAR(128) NOT NULL COMMENT '历史密码',
  `modifyTime` DATETIME NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_security_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_security_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srvId` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `accountName` VARCHAR(32) NOT NULL COMMENT '帐号名称',
  `logLevel` VARCHAR(16) NULL COMMENT '日志级别',
  `operation` VARCHAR(50) NULL COMMENT '操作名称',
  `source` VARCHAR(50) NULL COMMENT '来源',
  `terminal` VARCHAR(50) NULL COMMENT '操作终端',
  `result` INT NULL COMMENT '操作结果',
  `detail` VARCHAR(2048) NULL COMMENT '详细信息',
  `addInfo` VARCHAR(2048) NULL COMMENT '附加信息',
  `creationTime` DATETIME NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_operation_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_operation_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srvId` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `accountName` VARCHAR(32) NOT NULL COMMENT '帐号名称',
  `logLevel` VARCHAR(16) NULL COMMENT '日志级别',
  `operation` VARCHAR(50) NULL COMMENT '操作名称',
  `source` VARCHAR(50) NULL COMMENT '来源',
  `terminal` VARCHAR(50) NULL COMMENT '操作终端',
  `result` INT NULL COMMENT '操作结果',
  `detail` VARCHAR(2048) NULL COMMENT '详细信息',
  `addInfo` VARCHAR(2048) NULL COMMENT '附加信息',
  `creationTime` DATETIME NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_idletimeout`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_idletimeout` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `accountId` BIGINT NOT NULL COMMENT '帐号ID',
  `timeout` INT NOT NULL COMMENT '超时时间',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_perm_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_perm_resource` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srvId` VARCHAR(16) NULL COMMENT '业务服务ID',
  `permName` VARCHAR(64) NULL COMMENT '权限名称',
  `path` VARCHAR(128) NULL COMMENT '资源路径',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srvId` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `name` VARCHAR(20) NOT NULL COMMENT '帐号名称',
  `type` INT NULL DEFAULT 0 COMMENT '帐号类型',
  `password` VARCHAR(128) NULL COMMENT '密码',
  `salt` VARCHAR(32) NULL COMMENT '算法盐值',
  `status` INT NULL DEFAULT 0 COMMENT '状态',
  `phoneNumber` VARCHAR(18) NULL COMMENT '手机号',
  `isFirstLogin` INT NULL COMMENT '是否第一次登录',
  `lockTime` DATETIME NULL COMMENT '锁定时间',
  `lastAccessTime` DATETIME NULL COMMENT '最后访问时间',
  `lastAccessIpAddr` VARCHAR(20) NULL COMMENT '最后访问IP地址',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_dict_field`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_dict_field` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srv_id` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `domain` VARCHAR(30) NOT NULL COMMENT '领域',
  `key_x` VARCHAR(30) NOT NULL COMMENT '关键字段',
  `value_x` VARCHAR(256) NULL COMMENT '数值',
  `utime` DATETIME NOT NULL COMMENT '更新时间',
  `ctime` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))


-- -----------------------------------------------------
-- Table `bmc_dict_json`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_dict_json` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `srv_id` VARCHAR(16) NOT NULL COMMENT '业务服务ID',
  `key_x` VARCHAR(30) NOT NULL COMMENT '关键字段',
  `value_x` TEXT NULL COMMENT '数值',
  `utime` DATETIME NOT NULL COMMENT '更新时间',
  `ctime` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))
