package com.mizhousoft.bmc.auditlog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.auditlog.domain.ApiAuditLog;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.domain.SystemLog;
import com.mizhousoft.bmc.auditlog.service.AduitLogService;
import com.mizhousoft.bmc.auditlog.service.ApiAuditLogService;
import com.mizhousoft.bmc.auditlog.service.OperationLogService;
import com.mizhousoft.bmc.auditlog.service.SecurityLogService;
import com.mizhousoft.bmc.auditlog.service.SystemLogService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;

/**
 * 审计日志服务
 *
 * @version
 */
@Service
public class AduitLogServiceImpl implements AduitLogService
{
	private static final Logger LOG = LoggerFactory.getLogger(AduitLogServiceImpl.class);

	@Autowired
	private OperationLogService operationLogService;

	@Autowired
	private SecurityLogService securityLogService;

	@Autowired
	private SystemLogService systemLogService;

	@Autowired
	private ApiAuditLogService apiAuditLogService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOperationLog(OperationLog operationLog)
	{
		String serviceId = applicationAuthService.getServiceId();
		operationLog.setSrvId(serviceId);

		try
		{
			operationLogService.addOperationLog(operationLog);
		}
		catch (Throwable e)
		{
			LOG.error("Add operation log failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSecurityLog(SecurityLog securityLog)
	{
		String serviceId = applicationAuthService.getServiceId();
		securityLog.setSrvId(serviceId);

		try
		{
			securityLogService.addSecurityLog(securityLog);
		}
		catch (Throwable e)
		{
			LOG.error("Add security log failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSystemLog(SystemLog systemLog)
	{
		String serviceId = applicationAuthService.getServiceId();
		systemLog.setSrvId(serviceId);

		try
		{
			systemLogService.addSystemLog(systemLog);
		}
		catch (Throwable e)
		{
			LOG.error("Add system log failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addApiAuditLog(ApiAuditLog apiAuditLog)
	{
		String serviceId = applicationAuthService.getServiceId();
		apiAuditLog.setSrvId(serviceId);

		try
		{
			apiAuditLogService.addApiAuditLog(apiAuditLog);
		}
		catch (Throwable e)
		{
			LOG.error("Add api log failed.", e);
		}
	}
}
