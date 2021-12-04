package com.mizhousoft.bmc.auditlog.util;

import java.util.HashMap;
import java.util.Map;

import com.mizhousoft.bmc.auditlog.constants.AuditLogLevel;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 日志结果工具类
 *
 * @version
 */
public abstract class AuditLogI18nUtils
{
	private static final Map<Integer, String> RESULT_I18N_MAP = new HashMap<Integer, String>(3);

	private static final Map<String, String> LEVEL_I18N_MAP = new HashMap<String, String>(3);

	static
	{
		RESULT_I18N_MAP.put(AuditLogResult.Success.getValue(), "bmc.auditlog.result.successful");
		RESULT_I18N_MAP.put(AuditLogResult.Failure.getValue(), "bmc.auditlog.result.failure");
		RESULT_I18N_MAP.put(AuditLogResult.PartSuccess.getValue(), "bmc.auditlog.result.partsuccessful");

		LEVEL_I18N_MAP.put(AuditLogLevel.RISK.getValue(), "bmc.auditlog.loglevel.risk");
		LEVEL_I18N_MAP.put(AuditLogLevel.WARN.getValue(), "bmc.auditlog.loglevel.warn");
		LEVEL_I18N_MAP.put(AuditLogLevel.INFO.getValue(), "bmc.auditlog.loglevel.info");
	}

	/**
	 * 获取日志级别国际化
	 * 
	 * @param logLevel
	 * @return
	 */
	public static String getLevelText(String logLevel)
	{
		String logI18nKey = LEVEL_I18N_MAP.get(logLevel);
		String i18nText = I18nUtils.getMessage(logI18nKey);
		return i18nText;
	}

	/**
	 * 转换审计结果
	 * 
	 * @param auditResult
	 * @return
	 */
	public static String getResultText(int auditResult)
	{
		String key = RESULT_I18N_MAP.get(auditResult);
		String i18nText = I18nUtils.getMessage(key);
		return i18nText;
	}
}
