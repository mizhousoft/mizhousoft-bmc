drop table bmc_system_log;
drop table bmc_api_log;


delete from bmc_perm_resource where permName = 'bmc.auditlog.system' and path = '/auditlog/fetchSystemLogs.action';
delete from bmc_permission where name = 'bmc.auditlog.system';
delete from bmc_role_permission where permName = 'bmc.auditlog.system';

delete from bmc_perm_resource where permName = 'bmc.auditlog.api' and path = '/auditlog/fetchApiAuditLogs.action';
delete from bmc_permission where name = 'bmc.auditlog.api';
delete from bmc_role_permission where permName = 'bmc.auditlog.api';


drop table bmc_object_identity;