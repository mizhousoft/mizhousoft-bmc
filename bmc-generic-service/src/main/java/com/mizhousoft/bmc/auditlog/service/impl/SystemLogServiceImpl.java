package com.mizhousoft.bmc.auditlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.SystemLog;
import com.mizhousoft.bmc.auditlog.mapper.SystemLogMapper;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.service.SystemLogService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * 系统日志业务实现
 * 
 * @version
 */
@Service
public class SystemLogServiceImpl implements SystemLogService
{
	@Autowired
	private SystemLogMapper systemLogMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addSystemLog(SystemLog systemLog) throws AuditLogException
	{
		systemLogMapper.save(systemLog);
		return systemLog.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<SystemLog> queryPageData(AuditLogPageRequest request)
	{
		long total = systemLogMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<SystemLog> systemLogs = systemLogMapper.findPageData(rowOffset, request);
		Page<SystemLog> dataPage = PageBuilder.build(systemLogs, request, total);

		return dataPage;
	}

	/**
	 * 设置systemLogMapper
	 * 
	 * @param systemLogMapper
	 */
	public void setSystemLogMapper(SystemLogMapper systemLogMapper)
	{
		this.systemLogMapper = systemLogMapper;
	}
}
