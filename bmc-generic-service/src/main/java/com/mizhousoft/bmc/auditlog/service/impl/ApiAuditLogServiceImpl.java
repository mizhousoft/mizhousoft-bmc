package com.mizhousoft.bmc.auditlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.ApiAuditLog;
import com.mizhousoft.bmc.auditlog.mapper.ApiAuditLogMapper;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.service.ApiAuditLogService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * API日志业务接口
 *
 * @version
 */
@Service
public class ApiAuditLogServiceImpl implements ApiAuditLogService
{
	@Autowired
	private ApiAuditLogMapper apiAuditLogMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addApiAuditLog(ApiAuditLog apiAuditLog) throws AuditLogException
	{
		apiAuditLogMapper.save(apiAuditLog);
		return apiAuditLog.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ApiAuditLog> queryPageData(AuditLogPageRequest request)
	{
		long total = apiAuditLogMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<ApiAuditLog> apiAuditLogs = apiAuditLogMapper.findPageData(rowOffset, request);
		Page<ApiAuditLog> dataPage = PageBuilder.build(apiAuditLogs, request, total);

		return dataPage;
	}
}
