package com.mizhousoft.bmc.auditlog.service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 安全日志业务接口
 * 
 * @version
 */
public interface SecurityLogService
{
	/**
	 * 增加安全日志
	 * 
	 * @param securityLog
	 * @return
	 * @throws AuditLogException
	 */
	long addSecurityLog(SecurityLog securityLog) throws AuditLogException;

	/**
	 * 分页查找安全日志
	 * 
	 * @param request
	 * @return
	 */
	Page<SecurityLog> queryPageData(AuditLogPageRequest request);
}
