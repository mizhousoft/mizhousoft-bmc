package com.mizhousoft.bmc.auditlog.service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.ApiAuditLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * API日志业务接口
 * 
 * @version
 */
public interface ApiAuditLogService
{
	/**
	 * 增加API日志
	 * 
	 * @param apiAuditLog
	 * @return
	 * @throws AuditLogException
	 */
	long addApiAuditLog(ApiAuditLog apiAuditLog) throws AuditLogException;

	/**
	 * 分页查找API日志
	 * 
	 * @param request
	 * @return
	 */
	Page<ApiAuditLog> queryPageData(AuditLogPageRequest request);
}
