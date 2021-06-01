package com.mizhousoft.bmc.account.util;

import com.mizhousoft.bmc.account.constant.AccountType;

/**
 * 帐号工具类
 *
 * @version
 */
public abstract class AccountUtils
{
	/**
	 * 是否超级管理员
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isSuperAdmin(int type)
	{
		return (AccountType.SuperAdmin.getValue() == type);
	}
}
