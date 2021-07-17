package com.mizhousoft.bmc.auditlog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
	private static final int CAPACITY_LENGTH = 2048;

	// 操作日志持久化接口
	@Autowired
	private OperationLogMapper operationLogMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long addOperationLog(OperationLog operationLog) throws AuditLogException
	{
		long id = 0;

		if (StringUtils.length(operationLog.getDetail()) > CAPACITY_LENGTH)
		{
			List<String> list = stringSplitToList(operationLog.getDetail(), CAPACITY_LENGTH);
			for (String item : list)
			{
				OperationLog log = new OperationLog();

				BeanUtils.copyProperties(operationLog, log);
				log.setDetail(item);

				operationLogMapper.save(log);
				id = log.getId();
			}
		}
		else if (StringUtils.length(operationLog.getAddInfo()) > CAPACITY_LENGTH)
		{
			List<String> list = stringSplitToList(operationLog.getAddInfo(), CAPACITY_LENGTH);
			for (String item : list)
			{
				OperationLog log = new OperationLog();

				BeanUtils.copyProperties(operationLog, log);
				log.setAddInfo(item);

				operationLogMapper.save(log);
				id = log.getId();
			}
		}
		else
		{
			operationLogMapper.save(operationLog);
			id = operationLog.getId();
		}

		return id;
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

	public List<String> stringSplitToList(String inputText, int splitLength)
	{
		List<String> list = new ArrayList<String>(2);
		if (StringUtils.isBlank(inputText))
		{
			return list;
		}

		inputText = inputText.trim();

		int inputLength = inputText.length();
		int max = inputLength / splitLength;

		for (int index = 0; index <= max; index++)
		{
			int startIndex = index * splitLength;
			int endIndex = (index + 1) * splitLength;

			if (endIndex > inputLength)
			{
				endIndex = inputLength;
			}

			String value = inputText.substring(startIndex, endIndex);
			if (!StringUtils.isBlank(value))
			{
				list.add(value);
			}
		}

		return list;
	}
}
