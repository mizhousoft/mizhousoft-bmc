
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
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC))
ENGINE = InnoDB;

insert into bmc_dict_field(domain, key, value, utime, ctime) select 'account-strategy', 'accountUnusedDay', accountUnusedDay, now(), now() from bmc_account_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'account-strategy', 'timeLimitPeriod', timeLimitPeriod, now(), now() from bmc_account_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'account-strategy', 'loginLimitNumber', loginLimitNumber, now(), now() from bmc_account_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'account-strategy', 'lockTimeStrategy', lockTimeStrategy, now(), now() from bmc_account_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'account-strategy', 'accountLockTime', accountLockTime, now(), now() from bmc_account_strategy limit 1;

insert into bmc_dict_field(domain, key, value, utime, ctime) select 'password-strategy', 'historyRepeatSize', historyRepeatSize, now(), now() from bmc_password_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'password-strategy', 'charAppearSize', charAppearSize, now(), now() from bmc_password_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'password-strategy', 'modifyTimeInterval', modifyTimeInterval, now(), now() from bmc_password_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'password-strategy', 'validDay', validDay, now(), now() from bmc_password_strategy limit 1;
insert into bmc_dict_field(domain, key, value, utime, ctime) select 'password-strategy', 'reminderModifyDay', reminderModifyDay, now(), now() from bmc_password_strategy limit 1;

drop table bmc_account_strategy;
drop table bmc_password_strategy;
