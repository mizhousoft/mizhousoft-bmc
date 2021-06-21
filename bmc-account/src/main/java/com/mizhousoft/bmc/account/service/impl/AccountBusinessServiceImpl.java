package com.mizhousoft.bmc.account.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.BMCRuntimeException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.domain.AccountInfo;
import com.mizhousoft.bmc.account.domain.AccountRole;
import com.mizhousoft.bmc.account.domain.AuthAccount;
import com.mizhousoft.bmc.account.event.AccountDeleteEvent;
import com.mizhousoft.bmc.account.mapper.AccountMapper;
import com.mizhousoft.bmc.account.request.AccountPageRequest;
import com.mizhousoft.bmc.account.service.AccountBusinessService;
import com.mizhousoft.bmc.account.service.AccountRoleSerivce;
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.account.service.HistoryPasswordService;
import com.mizhousoft.bmc.account.util.AccountStatus18nUtils;
import com.mizhousoft.bmc.account.util.AccountUtils;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.bmc.role.service.RolePermissionService;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.bmc.system.service.AccountStrategyService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;
import com.mizhousoft.commons.lang.DateUtils;

/**
 * 帐号业务服务
 *
 * @version
 */
@Service
public class AccountBusinessServiceImpl implements AccountBusinessService
{
	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRoleSerivce accountRoleSerivce;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private HistoryPasswordService historyPasswordService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private AccountStrategyService accountStrategyService;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized void addAccount(AuthAccount account, List<Role> roles) throws BMCException
	{
		accountService.addAccount(account);

		if (null != roles)
		{
			for (Role role : roles)
			{
				AccountRole ar = new AccountRole();
				ar.setAccountId(account.getId());
				ar.setRoleId(role.getId());

				accountRoleSerivce.addAccountRole(ar);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized void deleteAccount(Account account) throws BMCException
	{
		try
		{
			AccountDeleteEvent event = new AccountDeleteEvent(account);
			eventPublisher.publishEvent(event);
		}
		catch (BMCRuntimeException e)
		{
			throw new BMCException(e.getErrorCode(), e.getCodeParams(), e.getMessage(), e);
		}

		accountService.deleteAccount(account);

		accountRoleSerivce.deleteByAccountId(account.getId());

		historyPasswordService.delete(account.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized void enableAccount(Account account) throws BMCException
	{
		accountService.enableAccount(account);

		Date lastAccessTime = account.getLastAccessTime();
		if (null != lastAccessTime)
		{
			AccountStrategy accountStrategy = accountStrategyService.queryAccountStrategy();
			int unusedDay = accountStrategy.getAccountUnusedDay();

			Calendar cal = Calendar.getInstance();
			cal.setTime(lastAccessTime);
			cal.add(Calendar.DAY_OF_MONTH, unusedDay);

			Date nowDate = new Date();
			if (nowDate.after(cal.getTime()))
			{
				account.setLastAccessTime(null);
				account.setLastAccessIpAddr(null);

				accountService.modifyLastAccess(account);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> getRoleByAccountId(long accountId)
	{
		List<AccountRole> accountRoles = accountRoleSerivce.getByAccountId(accountId);
		if (accountRoles.isEmpty())
		{
			return Collections.emptyList();
		}
		else
		{
			List<Role> roles = new ArrayList<Role>(accountRoles.size());
			for (AccountRole accountRole : accountRoles)
			{
				Role role = roleService.getById(accountRole.getRoleId());
				if (null != role)
				{
					roles.add(role);
				}
			}

			return roles;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getPermByAccountId(long accountId)
	{
		Set<String> perms = new HashSet<String>(100);

		List<Role> roles = getRoleByAccountId(accountId);
		for (Role role : roles)
		{
			List<RolePermission> rps = rolePermissionService.queryByRoleName(role.getName());
			rps.forEach(rp -> perms.add(rp.getPermName()));
		}

		return perms;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(Set<String> authorities, String permissionName)
	{
		List<RolePermission> list = rolePermissionService.queryByPermName(permissionName);
		for (RolePermission item : list)
		{
			if (authorities.contains(item.getRoleName()))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<AccountInfo> queryAccountInfos(AccountPageRequest request)
	{
		long total = accountMapper.countAccounts(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<AccountInfo> accounts = accountMapper.findAccountInfos(rowOffset, request);
		accounts.forEach(account -> processAccountData(account));

		Page<AccountInfo> page = PageBuilder.build(accounts, request, total);

		return page;
	}

	private void processAccountData(AccountInfo account)
	{
		if (AccountUtils.isSuperAdmin(account.getType()))
		{
			account.setSuperAdmin(true);
		}

		String statusText = AccountStatus18nUtils.getText(account.getStatus());
		account.setStatusText(statusText);
		account.setLastAccessTimeText(DateUtils.formatYmdhms(account.getLastAccessTime()));

		List<Role> roles = getRoleByAccountId(account.getId());
		List<String> roleNames = new ArrayList<String>();
		roles.forEach(role -> roleNames.add(role.getDisplayNameCN()));
		String roleString = StringUtils.join(roleNames.iterator(), ",");
		account.setRoleNames(roleString);
	}
}
