-- MySQL Script generated by MySQL Workbench
-- Sat Oct  2 16:37:26 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bmc
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bmc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bmc` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `bmc` ;

-- -----------------------------------------------------
-- Table `bmc_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` INT NOT NULL,
  `srvId` VARCHAR(32) NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `displayNameCN` VARCHAR(32) NOT NULL,
  `displayNameUS` VARCHAR(32) NOT NULL,
  `descriptionCN` VARCHAR(255) NULL,
  `descriptionUS` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_account_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_account_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accountId` BIGINT NULL,
  `roleId` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_permission` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `srvId` VARCHAR(32) NOT NULL,
  `type` INT NULL,
  `name` VARCHAR(64) NOT NULL,
  `parentName` VARCHAR(64) NULL,
  `displayNameCN` VARCHAR(50) NULL,
  `displayNameUS` VARCHAR(50) NULL,
  `isAuthz` BIT(1) NULL,
  `descriptionCN` VARCHAR(255) NULL,
  `descriptionUS` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_role_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_role_permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `permName` VARCHAR(64) NOT NULL,
  `roleName` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_history_pwd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_history_pwd` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accountId` BIGINT NOT NULL,
  `historyPwd` VARCHAR(128) NOT NULL,
  `modifyTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_system_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_system_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `logLevel` VARCHAR(16) NULL,
  `source` VARCHAR(50) NULL,
  `baseInfo` VARCHAR(255) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_security_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_security_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `accountName` VARCHAR(32) NOT NULL,
  `logLevel` VARCHAR(16) NULL,
  `operation` VARCHAR(50) NULL,
  `source` VARCHAR(50) NULL,
  `terminal` VARCHAR(50) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_operation_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_operation_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `accountName` VARCHAR(32) NOT NULL,
  `logLevel` VARCHAR(16) NULL,
  `operation` VARCHAR(50) NULL,
  `source` VARCHAR(50) NULL,
  `terminal` VARCHAR(50) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_idletimeout`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_idletimeout` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `accountId` BIGINT NOT NULL,
  `timeout` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_object_identity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_object_identity` (
  `name` VARCHAR(50) NOT NULL,
  `value` BIGINT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_perm_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_perm_resource` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `permName` VARCHAR(64) NULL,
  `path` VARCHAR(128) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `path_UNIQUE` (`path` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_account` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `type` INT NULL DEFAULT 0,
  `password` VARCHAR(128) NULL,
  `salt` VARCHAR(32) NULL,
  `status` INT UNSIGNED NULL DEFAULT 0,
  `phoneNumber` VARCHAR(18) NULL,
  `isFirstLogin` TINYINT(1) NULL,
  `lockTime` DATETIME NULL,
  `lastAccessTime` DATETIME NULL,
  `lastAccessIpAddr` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_api_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_api_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `logLevel` VARCHAR(16) NULL,
  `operation` VARCHAR(50) NULL,
  `source` VARCHAR(50) NULL,
  `terminal` VARCHAR(50) NULL,
  `result` INT NULL,
  `detail` VARCHAR(2048) NULL,
  `addInfo` VARCHAR(2048) NULL,
  `creationTime` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_dict_field`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_dict_field` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `domain` VARCHAR(30) NOT NULL,
  `key_x` VARCHAR(30) NOT NULL,
  `value` VARCHAR(256) NULL,
  `utime` DATETIME NOT NULL,
  `ctime` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bmc_dict_json`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_dict_json` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `key_x` VARCHAR(30) NOT NULL,
  `value` TEXT NULL,
  `utime` DATETIME NOT NULL,
  `ctime` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`key_x` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
