-- -----------------------------------------------------
-- Schema bmc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bmc` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `bmc` ;

-- -----------------------------------------------------
-- Table `bmc_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `type` INT NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `displayNameCN` VARCHAR(32) NOT NULL,
  `displayNameUS` VARCHAR(32) NOT NULL,
  `descriptionCN` VARCHAR(255) NULL,
  `descriptionUS` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_account_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_account_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accountId` INT NULL,
  `roleId` INT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `type` INT NULL,
  `name` VARCHAR(64) NOT NULL,
  `parentName` VARCHAR(64) NULL,
  `displayNameCN` VARCHAR(50) NULL,
  `displayNameUS` VARCHAR(50) NULL,
  `isAuthz` INT NULL,
  `descriptionCN` VARCHAR(255) NULL,
  `descriptionUS` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_role_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_role_permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `permName` VARCHAR(64) NOT NULL,
  `roleName` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_history_pwd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_history_pwd` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accountId` INT NOT NULL,
  `historyPwd` VARCHAR(128) NOT NULL,
  `modifyTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_system_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_system_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `logLevel` VARCHAR(16) NULL,
  `source` VARCHAR(50) NULL,
  `baseInfo` VARCHAR(255) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_security_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_security_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `accountName` VARCHAR(32) NOT NULL,
  `logLevel` VARCHAR(16) NULL,
  `operation` VARCHAR(50) NULL,
  `source` VARCHAR(50) NULL,
  `terminal` VARCHAR(50) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_operation_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_operation_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `accountName` VARCHAR(32) NOT NULL,
  `logLevel` VARCHAR(16) NULL,
  `operation` VARCHAR(50) NULL,
  `source` VARCHAR(50) NULL,
  `terminal` VARCHAR(50) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_idletimeout`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_idletimeout` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accountId` INT NOT NULL,
  `timeout` INT NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_object_identity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_object_identity` (
  `name` VARCHAR(50) NOT NULL,
  `value_x` INT NULL,
  PRIMARY KEY (`name`));


-- -----------------------------------------------------
-- Table `bmc_perm_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_perm_resource` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NULL,
  `permName` VARCHAR(64) NULL,
  `path` VARCHAR(128) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `type` INT NULL DEFAULT 0,
  `password` VARCHAR(128) NULL,
  `salt` VARCHAR(32) NULL,
  `status` INT NULL DEFAULT 0,
  `phoneNumber` VARCHAR(18) NULL,
  `isFirstLogin` INT NULL,
  `lockTime` DATETIME NULL,
  `lastAccessTime` DATETIME NULL,
  `lastAccessIpAddr` VARCHAR(20) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_api_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_api_log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(16) NOT NULL,
  `logLevel` VARCHAR(16) NULL,
  `operation` VARCHAR(50) NULL,
  `source` VARCHAR(50) NULL,
  `terminal` VARCHAR(50) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_dict_field`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_dict_field` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srv_id` VARCHAR(16) NOT NULL,
  `domain` VARCHAR(30) NOT NULL,
  `key_x` VARCHAR(30) NOT NULL,
  `value_x` VARCHAR(256) NULL,
  `utime` DATETIME NOT NULL,
  `ctime` DATETIME NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `bmc_dict_json`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_dict_json` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `srv_id` VARCHAR(16) NOT NULL,
  `key_x` VARCHAR(30) NOT NULL,
  `value_x` TEXT NULL,
  `utime` DATETIME NOT NULL,
  `ctime` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

