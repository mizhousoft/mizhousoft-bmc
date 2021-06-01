package com.mizhousoft.bmc.account.service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;

/**
 * 帐号密码服务
 *
 * @version
 */
public interface AccountPasswdService
{
	/**
	 * 第一次修改密码
	 * 
	 * @param account
	 * @param oldPasswd
	 * @param newPasswd
	 * @throws BMCException
	 */
	void modifyFirstLoginPassword(Account account, String oldPasswd, String newPasswd) throws BMCException;

	/**
	 * 修改密码
	 * 
	 * @param account
	 * @param oldPasswd
	 * @param newPasswd
	 * @throws BMCException
	 */
	void modifyPassword(Account account, String oldPasswd, String newPasswd) throws BMCException;

	/**
	 * 重置密码
	 * 
	 * @param account
	 * @param password
	 * @throws BMCException
	 */
	void resetPassword(Account account, String password) throws BMCException;

	/**
	 * 校验密码
	 * 
	 * @param name
	 * @param password
	 * @throws BMCException
	 */
	void checkPassword(String name, String password) throws BMCException;

	/**
	 * 计算密码即将到期天数
	 * 
	 * @param accountId
	 * @return
	 */
	int calcPasswordExpiringDays(long accountId);
}
