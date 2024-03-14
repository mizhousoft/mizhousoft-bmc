package com.mizhousoft.bmc.auditlog.service;

import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;

/**
 * 审计日志服务
 *
 * @version
 */
public interface AduitLogService
{
	/**
	 * 增加操作日志
	 * 
	 * @param operationLog
	 */
	void addOperationLog(OperationLog operationLog);

	/**
	 * 增加安全日志
	 * 
	 * @param securityLog
	 */
	void addSecurityLog(SecurityLog securityLog);
}
