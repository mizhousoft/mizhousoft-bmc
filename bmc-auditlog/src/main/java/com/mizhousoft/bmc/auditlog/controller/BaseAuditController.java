package com.mizhousoft.bmc.auditlog.controller;

import java.util.Date;

import com.mizhousoft.bmc.auditlog.constants.AuditLogLevel;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.security.Authentication;
import com.mizhousoft.bmc.security.context.SecurityContextHolder;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 基类审计控制器
 *
 * @version
 */
public abstract class BaseAuditController
{
	/**
	 * 构建操作日志
	 * 
	 * @param auditResult
	 * @param detail
	 * @param addInfo
	 * @return
	 */
	protected OperationLog buildOperLog(AuditLogResult auditResult, String detail, String addInfo)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		OperationLog operLog = new OperationLog();
		operLog.setAccountName(authentication.getName());
		operLog.setTerminal(authentication.getAccessIPAddress());
		operLog.setCreationTime(new Date());
		operLog.setOperation(I18nUtils.getMessage(getAuditOperation()));
		operLog.setSource(I18nUtils.getMessage(getAuditSource()));
		operLog.setResult(auditResult.getValue());
		operLog.setDetail(detail);
		operLog.setAddInfo(addInfo);

		AuditLogLevel logLevel = auditResult.equals(AuditLogResult.Success) ? AuditLogLevel.INFO : AuditLogLevel.WARN;
		operLog.setLogLevel(logLevel.getValue());

		return operLog;
	}

	/**
	 * 增加API日志
	 * 
	 * @param auditResult
	 * @param detail
	 * @param addInfo
	 * @param host
	 * @return
	 */
	protected OperationLog buildApiLog(AuditLogResult auditResult, String detail, String addInfo, String host)
	{
		OperationLog operLog = new OperationLog();
		operLog.setTerminal(host);
		operLog.setCreationTime(new Date());
		operLog.setOperation(I18nUtils.getMessage(getAuditOperation()));
		operLog.setSource(I18nUtils.getMessage(getAuditSource()));
		operLog.setResult(auditResult.getValue());
		operLog.setDetail(detail);
		operLog.setAddInfo(addInfo);

		AuditLogLevel logLevel = auditResult.equals(AuditLogResult.Success) ? AuditLogLevel.INFO : AuditLogLevel.WARN;
		operLog.setLogLevel(logLevel.getValue());

		return operLog;
	}

	/**
	 * 获取审计操作
	 * 
	 * @return
	 */
	protected abstract String getAuditOperation();

	/**
	 * 获取审计源
	 * 
	 * @return
	 */
	protected abstract String getAuditSource();
}
