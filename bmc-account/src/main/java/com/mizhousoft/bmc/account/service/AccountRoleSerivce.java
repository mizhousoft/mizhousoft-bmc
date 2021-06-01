package com.mizhousoft.bmc.account.service;

import java.util.List;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.domain.AccountRole;
import com.mizhousoft.bmc.role.domain.Role;

/**
 * 帐号角色服务
 *
 * @version
 */
public interface AccountRoleSerivce
{
	/**
	 * 授权帐号
	 * 
	 * @param account
	 * @param roles
	 * @throws BMCException
	 */
	void authorizeAccount(Account account, List<Role> roles) throws BMCException;

	/**
	 * 增加帐号角色
	 * 
	 * @param accountRole
	 * @throws BMCException
	 */
	void addAccountRole(AccountRole accountRole) throws BMCException;

	/**
	 * 根据帐号ID删除
	 * 
	 * @param accountId
	 */
	void deleteByAccountId(long accountId) throws BMCException;

	/**
	 * 根据角色ID删除
	 * 
	 * @param roleId
	 */
	void deleteByRoleId(int roleId) throws BMCException;

	/**
	 * 根据帐号ID获取帐号角色
	 * 
	 * @param accountId
	 * @return
	 */
	List<AccountRole> getByAccountId(long accountId);
}
