alter table bmc_dict_field add `srv_id` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;
alter table bmc_dict_json add `srv_id` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;

alter table bmc_dict_field modify column `srv_id` VARCHAR(16) NOT NULL;
alter table bmc_dict_json modify column `srv_id` VARCHAR(16) NOT NULL;


alter table bmc_permission modify column `srvId` VARCHAR(16) NOT NULL;
alter table bmc_perm_resource modify column `srvId` VARCHAR(16) NOT NULL;

alter table bmc_system_log add `srvId` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;
alter table bmc_security_log add `srvId` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;
alter table bmc_operation_log add `srvId` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;
alter table bmc_api_log add `srvId` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;

alter table bmc_system_log modify column `srvId` VARCHAR(16) NOT NULL;
alter table bmc_security_log modify column `srvId` VARCHAR(16) NOT NULL;
alter table bmc_operation_log modify column `srvId` VARCHAR(16) NOT NULL;
alter table bmc_api_log modify column `srvId` VARCHAR(16) NOT NULL;


alter table bmc_role_permission add `srvId` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;
alter table bmc_role_permission modify column `srvId` VARCHAR(16) NOT NULL;

alter table bmc_account add `srvId` VARCHAR(16) NOT NULL DEFAULT 'BMC' after id;
alter table bmc_account modify column `srvId` VARCHAR(16) NOT NULL;