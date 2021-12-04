package com.mizhousoft.bmc.account.service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.domain.AuthAccount;

/**
 * 帐号服务
 *
 * @version
 */
public interface AccountService
{
	/**
	 * 增加帐号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void addAccount(AuthAccount account) throws BMCException;

	/**
	 * 禁用帐号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void disableAccount(Account account) throws BMCException;

	/**
	 * 启用帐号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void enableAccount(Account account) throws BMCException;

	/**
	 * 解锁帐号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void unlockAccount(Account account) throws BMCException;

	/**
	 * 修改手机号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void modifyPhoneNumber(Account account) throws BMCException;

	/**
	 * 修改最后访问信息
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void modifyLastAccess(Account account) throws BMCException;

	/**
	 * 删除帐号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void deleteAccount(Account account) throws BMCException;

	/**
	 * 查询帐号
	 * 
	 * @param id
	 * @return
	 * @throws BMCException
	 */
	Account loadById(long id) throws BMCException;

	/**
	 * 查询帐号
	 * 
	 * @param id
	 * @return
	 */
	Account getById(long id);
}
