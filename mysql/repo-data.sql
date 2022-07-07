use bmc;

START TRANSACTION;

CREATE UNIQUE INDEX PERM_RESOURCE_IDX ON bmc_perm_resource (srvId, path);
CREATE UNIQUE INDEX DICT_JSON_IDX ON bmc_dict_json (srv_id, key_x);

-- My Account
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.my.accountdetail','/account/fetchMyAccountDetail.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.my.accountdetail',NULL,'我的帐号详情','My Accout Detail',false,NULL,NULL);


-- Personal Setting
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',2,'bmc.personal.settings',NULL,'个人设置','Personal Settings',false,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.setting.myaccount','/setting/account/fetchMyAccountInfo.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.setting.myaccount','/setting/account/modifyPhoneNumber.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.setting.myaccount','bmc.account.setting','我的帐号','My Account',false,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.setting.password','/setting/password/fetchPasswordStrategy.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.setting.password','/setting/password/modifyPassword.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.setting.password','bmc.account.setting','修改密码','Modify Password',false,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.setting.idletimeout','/setting/idletimeout/fetchIdletimeout.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.setting.idletimeout','/setting/idletimeout/modifyIdletimeout.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.setting.idletimeout','bmc.account.setting','闲置时间设置','Idle timeout Setting',false,NULL,NULL);

-- System Management
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',2,'bmc.system.management',NULL,'系统管理','System Management',true,NULL,NULL);


INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.system.management','Administrator');


-- Account Management
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',2,'bmc.account.management','bmc.system.management','帐号管理','Account Management',true,NULL,NULL);

INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.management','Administrator');

-- Account
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.list','/account/fetchAccountInfoList.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.list','bmc.account.management','帐号','Account List',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.new','/account/new/fetchRoles.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.new','/account/addAccount.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.new','bmc.account.list','增加帐号','Add Account',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.authorize','/account/authorize/fetchRoles.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.authorize','/account/authorize/fetchAccountRoles.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.authorize','/account/authorizeAccount.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.authorize','bmc.account.list','授权帐号','Authorize Account',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.disable','/account/disableAccount.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.disable','bmc.account.list','禁用帐号','Disable Account',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.enable','/account/enableAccount.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.enable','bmc.account.list','启用帐号','Enable Account',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.unlock','/account/unlockAccount.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.unlock','bmc.account.list','解锁帐号','Unlock Account',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.password.reset','/account/resetPassword.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.password.reset','bmc.account.list','重置密码','Reset Password',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.delete','/account/deleteAccount.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.delete','bmc.account.list','删除账号','Delete Account',true,NULL,NULL);

INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.list','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.new','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.authorize','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.disable','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.enable','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.unlock','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.password.reset','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.delete','Administrator');

-- Role
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.role.list','/role/fetchRoles.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.role.list','bmc.account.management','角色','Role List',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.role.view','/role/fetchRoleInfo.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.role.view','bmc.role.list','查看角色','View Role',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.role.new','/role/newRole.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.role.new','/role/addRole.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.role.new','bmc.role.list','增加角色','New Role',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.role.edit','/role/editRole.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.role.edit','/role/modifyRole.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.role.edit','bmc.role.list','编辑角色','Edit Role',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.role.delete','/role/deleteRole.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.role.delete','bmc.role.list','删除角色','Delete Role',true,NULL,NULL);


INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.role.list','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.role.view','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.role.new','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.role.edit','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.role.delete','Administrator');

-- Security Management
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',2,'bmc.security.management','bmc.system.management','安全管理','Security Management',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.strategy','/system/fetchAccountStrategy.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.strategy','/system/modifyAccountStrategy.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.strategy','bmc.security.management','帐号策略','Account Strategy',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.password.strategy','/system/fetchPasswordStrategy.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.password.strategy','/system/modifyPasswordStrategy.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.password.strategy','bmc.security.management','密码策略','Password Strategy',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.online','/system/fetchOnlineAccounts.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.account.online','/system/logoffOnlineAccount.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.account.online','bmc.security.management','在线帐号','Online Account',true,NULL,NULL);


INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.security.management','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.strategy','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.password.strategy','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.account.online','Administrator');

-- Audit Log Management
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',2,'bmc.auditlog.management','bmc.system.management','审计日志','Audit Log Management',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.auditlog.operation','/auditlog/fetchOperationLogs.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.auditlog.operation','bmc.auditlog.management','操作日志','Operation Log',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.auditlog.security','/auditlog/fetchSecurityLogs.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.auditlog.security','bmc.auditlog.management','安全日志','Security Log',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.auditlog.system','/auditlog/fetchSystemLogs.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.auditlog.system','bmc.auditlog.management','系统日志','System Log',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.auditlog.api','/auditlog/fetchApiAuditLogs.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.auditlog.api','bmc.auditlog.management','接口日志','API Log',true,NULL,NULL);

INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.running.log','/runninglog/fetchRunningLogNames.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.running.log','/runninglog/fetchRunningLogFileNames.action');
INSERT INTO `bmc_perm_resource` (`srvId`,`permName`,`path`) VALUES ('BMC','bmc.running.log','/runninglog/downloadRunningLogFile.action');
INSERT INTO `bmc_permission` (`srvId`,`type`,`name`,`parentName`,`displayNameCN`,`displayNameUS`,`isAuthz`,`descriptionCN`,`descriptionUS`) 
VALUES ('BMC',1,'bmc.running.log','bmc.auditlog.management','本地日志','Running Log',true,NULL,NULL);

INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.auditlog.management','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.auditlog.operation','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.auditlog.security','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.auditlog.system','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.auditlog.api','Administrator');
INSERT INTO `bmc_role_permission` (`srvId`,`permName`,`roleName`) VALUES ('BMC','bmc.running.log','Administrator');

-- Account data
INSERT INTO `bmc_account` (`srvId`, `name`, `type`, `password`, `salt`, `status`, `isFirstLogin`)
VALUES ('BMC', 'admin', 1, 'YJYZN7mTlJC/KVZcc3lfKrVrtEcELwFixpw+xFuyfeXds+nLNEDsDYFA3ot6jfPZPLzFdOs8E/0f0F+hRj557w==', '6921466447566625716', 2, true);

-- Role data
INSERT INTO `bmc_role` (`type`, `srvId`, `name`, `displayNameCN`, `displayNameUS`, `descriptionCN`, `descriptionUS`) 
VALUES (1, 'BMC', 'Administrator', '系统管理员', 'System Administrator', '系统管理员角色', 'Administrator role');

INSERT INTO `bmc_account_role` (`accountId`, `roleId`) 
VALUES ((select id from bmc_account where name = 'admin'), (select id from bmc_role where name = 'Administrator'));

-- Security data
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'account-strategy', 'accountUnusedDay', 90, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'account-strategy', 'timeLimitPeriod', 10, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'account-strategy', 'loginLimitNumber', 5, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'account-strategy', 'lockTimeStrategy', 1, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'account-strategy', 'accountLockTime', 10, now(), now());

insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'password-strategy', 'historyRepeatSize', 3, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'password-strategy', 'charAppearSize', 2, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'password-strategy', 'modifyTimeInterval', 5, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'password-strategy', 'validDay', 360, now(), now());
insert into bmc_dict_field(srv_id, domain, key_x, value, utime, ctime) values('BMC', 'password-strategy', 'reminderModifyDay', 15, now(), now());

COMMIT;
