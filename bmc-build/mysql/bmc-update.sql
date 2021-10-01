
-- -----------------------------------------------------
-- Table `bmc_dict_field`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bmc_dict_field` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `domain` VARCHAR(30) NOT NULL,
  `key` VARCHAR(30) NOT NULL,
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
  `key` VARCHAR(30) NOT NULL,
  `value` TEXT NULL,
  `utime` DATETIME NOT NULL,
  `ctime` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

