package com.mizhousoft.bmc.auditlog.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.bmc.auditlog.constants.AuditLogLevel;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;

/**
 * 工具类
 *
 * @version
 */
public abstract class AuditLogRequestUtils
{
	public static void handleRequest(AuditLogPageRequest request)
	{
		String operation = StringUtils.trimToNull(request.getOperation());
		operation = StringUtils.left(operation, 40);
		request.setOperation(operation);

		String accountName = StringUtils.trimToNull(request.getAccountName());
		accountName = StringUtils.left(accountName, 20);
		request.setAccountName(accountName);

		String terminal = StringUtils.trimToNull(request.getTerminal());
		terminal = StringUtils.left(terminal, 20);
		request.setTerminal(terminal);

		String source = StringUtils.trimToNull(request.getSource());
		source = StringUtils.left(source, 40);
		request.setSource(source);

		String baseInfo = StringUtils.trimToNull(request.getBaseInfo());
		baseInfo = StringUtils.left(baseInfo, 40);
		request.setBaseInfo(baseInfo);

		String[] logLevels = request.getLogLevels();
		if (null != logLevels)
		{
			Set<String> levels = new HashSet<>();
			for (String logLevel : logLevels)
			{
				AuditLogLevel level = AuditLogLevel.getLogLevel(logLevel);
				if (null != level)
				{
					levels.add(level.getValue());
				}
			}
			logLevels = levels.toArray(new String[levels.size()]);
			request.setLogLevels(logLevels);
		}

		int[] results = request.getResults();
		if (null != results)
		{
			Set<Integer> rs = new HashSet<>();

			for (int result : results)
			{
				AuditLogResult alr = AuditLogResult.getAuditResult(result);
				if (null != alr)
				{
					rs.add(alr.getValue());
				}
			}

			Integer[] rts = rs.toArray(new Integer[rs.size()]);
			int[] newRs = new int[rts.length];
			for (int i = 0; i < rts.length; ++i)
			{
				newRs[i] = rts[i];
			}

			request.setResults(newRs);
		}
	}
}
