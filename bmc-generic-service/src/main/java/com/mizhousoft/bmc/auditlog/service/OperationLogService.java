package com.mizhousoft.bmc.auditlog.service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 操作日志业务接口
 * 
 * @version
 */
public interface OperationLogService
{
	/**
	 * 增加操作日志
	 * 
	 * @param operationLog
	 * @return
	 * @throws AuditLogException
	 */
	long addOperationLog(OperationLog operationLog) throws AuditLogException;

	/**
	 * 分页查找操作日志
	 * 
	 * @param request
	 * @return
	 */
	Page<OperationLog> queryPageData(AuditLogPageRequest request);
}
