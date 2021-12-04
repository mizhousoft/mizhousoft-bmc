package com.mizhousoft.bmc.role.constant;

/**
 * 角色类型
 *
 * @version
 */
public enum RoleType
{
    // 超级管理员角色，只有1个，不能删除，不能更新
	AdministratorRole(1),
    // 普通角色，可以多个
	GeneralRole(2);

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private RoleType(int val)
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
}
