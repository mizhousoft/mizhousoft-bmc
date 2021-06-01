package com.mizhousoft.bmc.auditlog.service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.SystemLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 系统日志业务接口
 * 
 * @version
 */
public interface SystemLogService
{
	/**
	 * 增加系统日志
	 * 
	 * @param systemLog
	 * @return
	 * @throws AuditLogException
	 */
	long addSystemLog(SystemLog systemLog) throws AuditLogException;

	/**
	 * 分页查找系统日志
	 * 
	 * @param request
	 * @return
	 */
	Page<SystemLog> queryPageData(AuditLogPageRequest request);
}
