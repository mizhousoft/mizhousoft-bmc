package com.mizhousoft.bmc.auditlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.auditlog.AuditLogException;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.mapper.OperationLogMapper;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.service.OperationLogService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * 操作日志业务实现
 * 
 * @version
 */
@Service
public class OperationLogServiceImpl implements OperationLogService
{
	// 操作日志持久化接口
	@Autowired
	private OperationLogMapper operationLogMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addOperationLog(OperationLog operationLog) throws AuditLogException
	{
		operationLogMapper.save(operationLog);
		return operationLog.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<OperationLog> queryPageData(AuditLogPageRequest request)
	{
		long total = operationLogMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<OperationLog> operationLogs = operationLogMapper.findPageData(rowOffset, request);

		Page<OperationLog> dataPage = PageBuilder.build(operationLogs, request, total);

		return dataPage;
	}
}
