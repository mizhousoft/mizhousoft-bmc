package com.mizhousoft.bmc.account.constant;

/**
 * 帐号类型
 *
 * @version
 */
public enum AccountType
{
    // 超级管理员帐号，只有1个，不能删除，不能更新
	SuperAdmin(1),
    // 普通帐号，可以多个
	GeneralAdmin(2);

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private AccountType(int val)
	{
		this.value = val;
	}

	/**
	 * 值
	 */
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
}
