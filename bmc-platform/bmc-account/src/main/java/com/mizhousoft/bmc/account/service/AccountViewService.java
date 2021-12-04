package com.mizhousoft.bmc.account.service;

import java.util.List;
import java.util.Set;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.domain.AccountInfo;
import com.mizhousoft.bmc.account.domain.AuthAccount;
import com.mizhousoft.bmc.account.request.AccountPageRequest;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 帐号业务服务
 *
 * @version
 */
public interface AccountViewService
{
	/**
	 * 增加帐号
	 * 
	 * @param account
	 * @param roles
	 * @throws BMCException
	 */
	void addAccount(AuthAccount account, List<Role> roles) throws BMCException;

	/**
	 * 删除帐号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void deleteAccount(Account account) throws BMCException;

	/**
	 * 启用帐号
	 * 
	 * @param account
	 * @throws BMCException
	 */
	void enableAccount(Account account) throws BMCException;

	/**
	 * 根据帐号ID获取角色
	 * 
	 * @param accountId
	 * @return
	 */
	List<Role> getRoleByAccountId(long accountId);

	/**
	 * 根据帐号ID获取权限
	 * 
	 * @param accountId
	 * @return
	 */
	Set<String> getPermByAccountId(long accountId);

	/**
	 * 查询帐号信息
	 * 
	 * @param request
	 * @return
	 */
	Page<AccountInfo> queryAccountInfos(AccountPageRequest request);
}
