package com.mizhousoft.bmc.account.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
import com.mizhousoft.bmc.account.domain.AccountRole;
import com.mizhousoft.bmc.account.event.AccountDeleteEvent;
import com.mizhousoft.bmc.account.mapper.AccountMapper;
import com.mizhousoft.bmc.account.model.AccountInfo;
import com.mizhousoft.bmc.account.model.AuthAccount;
import com.mizhousoft.bmc.account.request.AccountPageRequest;
import com.mizhousoft.bmc.account.service.AccountRoleSerivce;
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.account.service.HistoryPasswordService;
import com.mizhousoft.bmc.account.util.AccountStatus18nUtils;
import com.mizhousoft.bmc.account.util.AccountUtils;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.service.RoleCacheService;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.bmc.system.service.AccountStrategyService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

/**
 * 帐号业务服务
 *
 * @version
 */
@Service
public class AccountViewServiceImpl implements AccountViewService
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
	private RoleCacheService roleCacheService;

	@Autowired
	private HistoryPasswordService historyPasswordService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private AccountStrategyService accountStrategyService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized void addAccount(AuthAccount account, List<Role> roles) throws BMCException
	{
		String serviceId = applicationAuthService.getServiceId();

		account.setSrvId(serviceId);
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
	public synchronized Account deleteAccount(long id) throws BMCException
	{
		String serviceId = applicationAuthService.getServiceId();
		Account account = accountService.deleteAccount(serviceId, id);

		if (null != account)
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

			accountRoleSerivce.deleteByAccountId(account.getId());

			historyPasswordService.delete(account.getId());
		}

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized Account enableAccount(long id) throws BMCException
	{
		String serviceId = applicationAuthService.getServiceId();
		Account account = accountService.loadById(serviceId, id);

		accountService.enableAccount(account);

		LocalDateTime lastAccessTime = account.getLastAccessTime();
		if (null != lastAccessTime)
		{
			AccountStrategy accountStrategy = accountStrategyService.getAccountStrategy(serviceId);
			int unusedDay = accountStrategy.getAccountUnusedDay();

			lastAccessTime = lastAccessTime.plusDays(unusedDay);

			LocalDateTime nowDate = LocalDateTime.now();
			if (nowDate.isAfter(lastAccessTime))
			{
				account.setLastAccessTime(null);
				account.setLastAccessIpAddr(null);

				accountService.modifyLastAccess(account);
			}
		}

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account disableAccount(long id) throws BMCException
	{
		String serviceId = applicationAuthService.getServiceId();
		Account account = accountService.loadById(serviceId, id);

		accountService.disableAccount(account);

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account unlockAccount(long id) throws BMCException
	{
		String serviceId = applicationAuthService.getServiceId();
		Account account = accountService.loadById(serviceId, id);

		accountService.unlockAccount(account);

		return account;
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
			String serviceId = applicationAuthService.getServiceId();

			List<Role> roles = new ArrayList<Role>(accountRoles.size());
			for (AccountRole accountRole : accountRoles)
			{
				Role role = roleService.getById(serviceId, accountRole.getRoleId());
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
			Set<String> list = roleCacheService.queryPermissionByRoleName(role.getSrvId(), role.getName());
			perms.addAll(list);
		}

		return perms;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account loadById(long id) throws BMCException
	{
		String srvId = applicationAuthService.getServiceId();

		return accountService.loadById(srvId, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<AccountInfo> queryAccountInfos(AccountPageRequest request)
	{
		String srvId = applicationAuthService.getServiceId();
		request.setSrvId(srvId);

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
		account.setLastAccessTimeText(LocalDateTimeUtils.formatYmdhms(account.getLastAccessTime()));

		List<Role> roles = getRoleByAccountId(account.getId());
		List<String> roleNames = new ArrayList<String>();
		roles.forEach(role -> roleNames.add(role.getDisplayNameCN()));
		String roleString = StringUtils.join(roleNames.iterator(), ",");
		account.setRoleNames(roleString);
	}

}
