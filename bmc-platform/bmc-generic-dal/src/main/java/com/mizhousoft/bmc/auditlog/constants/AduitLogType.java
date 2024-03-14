package com.mizhousoft.bmc.auditlog.constants;

/**
 * 审计日志类型
 *
 * @version
 */
public enum AduitLogType
{
    // 操作
	Operation(1),
    // 安全
	Security(2);

	/**
	 * 构造函数
	 * 
	 * @param value
	 */
	AduitLogType(int value)
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
	public static AduitLogType getAduitLogType(int value)
	{
		AduitLogType[] enumValues = AduitLogType.values();
		for (AduitLogType enumValue : enumValues)
		{
			if (enumValue.isSelf(value))
			{
				return enumValue;
			}
		}

		throw new IllegalArgumentException(value + " is illegal.");
	}
}
