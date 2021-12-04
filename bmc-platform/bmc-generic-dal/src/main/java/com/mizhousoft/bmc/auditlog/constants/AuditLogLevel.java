package com.mizhousoft.bmc.auditlog.constants;

/**
 * 日志级别
 * 
 * @version
 */
public enum AuditLogLevel
{
    // 一般
	INFO("Info"),
    // 警告
	WARN("Warn"),
    // 危险
	RISK("Risk");

	/**
	 * 构造函数
	 * 
	 * @param value
	 */
	AuditLogLevel(String value)
	{
		this.value = value;
	}

	// 值
	private final String value;

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * 判断是否自己
	 * 
	 * @param val
	 * @return
	 */
	public boolean isSelf(String val)
	{
		return this.getValue().equalsIgnoreCase(val);
	}

	/**
	 * 获取LogLevel
	 * 
	 * @param value
	 * @return
	 */
	public static AuditLogLevel getLogLevel(String value)
	{
		AuditLogLevel[] enumValues = AuditLogLevel.values();
		for (AuditLogLevel enumValue : enumValues)
		{
			if (enumValue.isSelf(value))
			{
				return enumValue;
			}
		}

		throw new IllegalArgumentException(value + " is illegal.");
	}
}
