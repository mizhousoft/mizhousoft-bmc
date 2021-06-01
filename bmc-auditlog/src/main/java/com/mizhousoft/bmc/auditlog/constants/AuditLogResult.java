package com.mizhousoft.bmc.auditlog.constants;

/**
 * 审计结果
 *
 * @version
 */
public enum AuditLogResult
{
    // 成功
	Success(1),
    // 失败
	Failure(2),
    // 部分成功
	PartSuccess(3);

	/**
	 * 构造函数
	 * 
	 * @param value
	 */
	AuditLogResult(int value)
	{
		this.value = value;
	}

	// 值
	private final int value;

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * 判断是否自己
	 * 
	 * @param val
	 * @return
	 */
	public boolean isSelf(int val)
	{
		return (this.getValue() == val);
	}

	/**
	 * 获取AduitLogType
	 * 
	 * @param value
	 * @return
	 */
	public static AuditLogResult getAuditResult(int value)
	{
		AuditLogResult[] enumValues = AuditLogResult.values();
		for (AuditLogResult enumValue : enumValues)
		{
			if (enumValue.isSelf(value))
			{
				return enumValue;
			}
		}

		throw new IllegalArgumentException(value + " is illegal.");
	}
}
