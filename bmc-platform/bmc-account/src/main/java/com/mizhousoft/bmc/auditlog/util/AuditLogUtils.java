package com.mizhousoft.bmc.auditlog.util;

import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.service.AduitLogService;
import com.mizhousoft.commons.context.util.ServiceContextHolder;

/**
 * 审计日志工具类
 *
 * @version
 */
public abstract class AuditLogUtils
{
	private static final AduitLogService LOGSERVICE = ServiceContextHolder.getService(AduitLogService.class);

	/**
	 * 增加操作日志
	 * 
	 * @param operationLog
	 */
	public static void addOperationLog(OperationLog operationLog)
	{
		if (null == operationLog)
		{
			return;
		}

		LOGSERVICE.addOperationLog(operationLog);
	}

	/**
	 * 增加安全日志
	 * 
	 * @param securityLog
	 */
	public static void addSecurityLog(SecurityLog securityLog)
	{
		if (null == securityLog)
		{
			return;
		}

		LOGSERVICE.addSecurityLog(securityLog);
	}

}
