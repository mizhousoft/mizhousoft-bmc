alter table bmc_perm_resource drop index path_UNIQUE;

alter table bmc_perm_resource add `srvId` VARCHAR(32) NULL after id;

CREATE UNIQUE INDEX PERM_RESOURCE_IDX ON bmc_perm_resource (srvId, path);

update BMC_PERM_RESOURCE set srvId = 'BMC' where permName like 'bmc.%';