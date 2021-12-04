package com.mizhousoft.bmc.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.domain.AccountRole;
import com.mizhousoft.bmc.account.mapper.AccountRoleMapper;
import com.mizhousoft.bmc.account.service.AccountRoleSerivce;
import com.mizhousoft.bmc.account.util.AccountUtils;
import com.mizhousoft.bmc.role.domain.Role;

/**
 * 帐号角色服务
 *
 * @version
 */
@Service
public class AccountRoleSerivceImpl implements AccountRoleSerivce
{
	@Autowired
	private AccountRoleMapper accountRoleMapper;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void authorizeAccount(Account account, List<Role> roles) throws BMCException
	{
		if (AccountUtils.isSuperAdmin(account.getType()))
		{
			throw new BMCException("bmc.account.superadmin.authorize.error",
			        "Super admin can not authorize, account id is " + account.getId() + '.');
		}

		deleteByAccountId(account.getId());

		for (Role role : roles)
		{
			AccountRole ar = new AccountRole();
			ar.setAccountId(account.getId());
			ar.setRoleId(role.getId());

			addAccountRole(ar);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAccountRole(AccountRole accountRole) throws BMCException
	{
		accountRoleMapper.save(accountRole);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByAccountId(long accountId) throws BMCException
	{
		accountRoleMapper.deleteByAccountId(accountId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByRoleId(int roleId) throws BMCException
	{
		accountRoleMapper.deleteByRoleId(roleId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AccountRole> getByAccountId(long accountId)
	{
		return accountRoleMapper.findByAccountId(accountId);
	}
}
