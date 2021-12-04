package com.mizhousoft.bmc.auditlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.mapper.SecurityLogMapper;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.service.SecurityLogService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * 安全日志业务实现
 * 
 * @version
 */
@Service
public class SecurityLogServiceImpl implements SecurityLogService
{
	@Autowired
	private SecurityLogMapper securityLogMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addSecurityLog(SecurityLog securityLog) throws AuditLogException
	{
		securityLogMapper.save(securityLog);

		return securityLog.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<SecurityLog> queryPageData(AuditLogPageRequest request)
	{
		long total = securityLogMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<SecurityLog> securityLogs = securityLogMapper.findPageData(rowOffset, request);
		Page<SecurityLog> dataPage = PageBuilder.build(securityLogs, request, total);

		return dataPage;
	}
}
