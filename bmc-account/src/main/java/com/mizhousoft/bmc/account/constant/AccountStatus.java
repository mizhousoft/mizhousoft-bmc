package com.mizhousoft.bmc.account.constant;

/**
 * 帐号状态
 *
 * @version
 */
public enum AccountStatus
{
    // 未创建
	NOT_CREATED(1),
    // 启用
	Enable(2),
    // 禁用
	Disable(3),
    // 锁定
	Locked(4);

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private AccountStatus(int val)
	{
		this.value = val;
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
	 * 获取状态
	 * 
	 * @param status
	 * @return
	 */
	public AccountStatus get(int status)
	{
		AccountStatus[] values = AccountStatus.values();
		for (AccountStatus value : values)
		{
			if (value.getValue() == status)
			{
				return value;
			}
		}

		return null;
	}

	/**
	 * 是否启用状态
	 * 
	 * @param status
	 * @return
	 */
	public static boolean isEnable(int status)
	{
		return (Enable.getValue() == status);
	}

	/**
	 * 是否禁用状态
	 * 
	 * @param status
	 * @return
	 */
	public static boolean isDisable(int status)
	{
		return (Disable.getValue() == status);
	}

	/**
	 * 是否锁定状态
	 * 
	 * @param status
	 * @return
	 */
	public static boolean isLock(int status)
	{
		return (Locked.getValue() == status);
	}
}
