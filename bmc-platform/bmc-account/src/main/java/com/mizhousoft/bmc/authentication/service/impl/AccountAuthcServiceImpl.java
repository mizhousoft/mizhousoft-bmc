package com.mizhousoft.bmc.authentication.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mizhousoft.bmc.account.constant.AccountStatus;
import com.mizhousoft.bmc.account.domain.HistoryPassword;
import com.mizhousoft.bmc.account.mapper.AccountMapper;
import com.mizhousoft.bmc.account.model.AuthAccount;
import com.mizhousoft.bmc.account.model.AuthFaildAccount;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.account.service.HistoryPasswordService;
import com.mizhousoft.bmc.account.util.AccountUtils;
import com.mizhousoft.bmc.auditlog.constants.AuditLogLevel;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.system.constant.AccountStrategyConstants;
import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.bmc.system.domain.IdleTimeout;
import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.bmc.system.service.AccountStrategyService;
import com.mizhousoft.bmc.system.service.IdleTimeoutService;
import com.mizhousoft.bmc.system.service.PasswordStrategyService;
import com.mizhousoft.boot.authentication.AccountDetails;
import com.mizhousoft.boot.authentication.GrantedAuthority;
import com.mizhousoft.boot.authentication.SimpleGrantedAuthority;
import com.mizhousoft.boot.authentication.configuration.AuthenticationProperties;
import com.mizhousoft.boot.authentication.exception.AccountDisabledException;
import com.mizhousoft.boot.authentication.exception.AccountLockedException;
import com.mizhousoft.boot.authentication.exception.AuthenticationException;
import com.mizhousoft.boot.authentication.exception.BadCredentialsException;
import com.mizhousoft.boot.authentication.impl.AccountImpl;
import com.mizhousoft.boot.authentication.service.AccountAuthcService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.boot.authentication.service.TwoFactorAuthenticationService;
import com.mizhousoft.commons.crypto.CryptoException;
import com.mizhousoft.commons.crypto.generator.PBEPasswdGenerator;
import com.mizhousoft.commons.crypto.generator.RandomGenerator;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 帐号认证服务
 *
 * @version
 */
@Service
public class AccountAuthcServiceImpl implements AccountAuthcService
{
	private static final Logger LOG = LoggerFactory.getLogger(AccountAuthcServiceImpl.class);

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountStrategyService accountStrategyService;

	@Autowired
	private PasswordStrategyService passwordStrategyService;

	@Autowired
	private HistoryPasswordService historyPasswordService;

	@Autowired
	private AccountViewService accountViewService;

	@Autowired
	private IdleTimeoutService idleTimeoutService;

	@Autowired
	private AuthenticationProperties authenticationProperties;

	@Autowired
	private TwoFactorAuthenticationService twoFactorAuthenticationService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	// 认证失败帐号集合
	private Cache<String, AuthFaildAccount> authFailedAccountMap = Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountDetails authenticate(String account, char[] passwd, String host) throws AuthenticationException
	{
		try
		{
			AccountDetails accountDetails = doAuthenticate(account, passwd, host);

			saveSecurityLog(account, host, null, true);

			return accountDetails;
		}
		catch (AuthenticationException e)
		{
			saveSecurityLog(account, host, e.getMessage(), false);
			throw e;
		}
	}

	private AccountDetails doAuthenticate(String account, char[] passwd, String host) throws AuthenticationException
	{
		String serviceId = applicationAuthService.getServiceId();

		AccountStrategy accountStrategy = accountStrategyService.getAccountStrategy(serviceId);

		// 查询帐号
		AuthAccount authAccount = queryAuthAccount(serviceId, account, accountStrategy);

		AccountDetails accountDetails = null;

		try
		{
			// 判断帐号是否锁定
			if (AccountStatus.isLock(authAccount.getStatus()))
			{
				processLockedAccount(authAccount, accountStrategy);
			}
			// 判断帐号是否禁用
			else if (AccountStatus.isDisable(authAccount.getStatus()))
			{
				throw new AccountDisabledException(account + " is disabled.");
			}

			// 处理连续未使用的帐号
			handleContinueUnusedAccount(authAccount, accountStrategy);

			// 校验帐号密码
			verifyAccountPasswd(passwd, authAccount, accountStrategy);

			// 清理认证失败的帐号
			cleanAuthFailedAccount(account);

			// 构建认证帐号信息
			accountDetails = buildAccountImpl(authAccount, host);
			return accountDetails;
		}
		finally
		{
			updateLastAccess(accountDetails, host);
		}
	}

	private void updateLastAccess(AccountDetails accountDetails, String host)
	{
		try
		{
			if (null == accountDetails)
			{
				return;
			}

			boolean twoFactorAuthcPassed = accountDetails.isTwoFactorAuthcPassed();
			if (!authenticationProperties.isTwoFactorAuthcEnable() || twoFactorAuthcPassed)
			{
				accountMapper.updateLastAccess(accountDetails.getAccountId(), LocalDateTime.now(), host);
			}
		}
		catch (Throwable e)
		{
			LOG.error("Update last access failed.", e);
		}
	}

	private AuthAccount queryAuthAccount(String serviceId, String account, AccountStrategy accountStrategy) throws AuthenticationException
	{
		AuthAccount authAccount = accountMapper.findAuthAccount(serviceId, account);
		if (null == authAccount)
		{
			boolean lockAccount = processAuthFailedAccount(account, accountStrategy);
			if (lockAccount)
			{
				throw new AccountLockedException(account + " is locked.");
			}

			int leaveCount = obtainAuthLeaveCount(account, accountStrategy);
			String[] params = { String.valueOf(leaveCount) };

			throw new BadCredentialsException(null, params, account + " not found.");
		}

		return authAccount;
	}

	private void processLockedAccount(AuthAccount authAccount, AccountStrategy accountStrategy) throws AuthenticationException
	{
		String account = authAccount.getName();

		if (accountStrategy.getLockTimeStrategy() == AccountStrategyConstants.LOCK_TIME_STRATEGY)
		{
			int timeLimitPeriod = accountStrategy.getTimeLimitPeriod();
			LocalDateTime lockTime = authAccount.getLockTime().plusMinutes(timeLimitPeriod);

			LocalDateTime now = LocalDateTime.now();
			if (lockTime.isAfter(now))
			{
				throw new AccountLockedException(account + " is locked.");
			}
			else
			{
				accountMapper.unlockAccount(authAccount.getId(), AccountStatus.Enable.getValue());
				LOG.info("Account unlock time has arrived, automatically unlock {}.", account);
			}
		}
		else
		{
			throw new AccountLockedException(account + " is locked.");
		}
	}

	private void handleContinueUnusedAccount(AuthAccount authAccount, AccountStrategy accountStrategy) throws AuthenticationException
	{
		int unusedDay = accountStrategy.getAccountUnusedDay();

		LocalDateTime lastAccessTime = authAccount.getLastAccessTime();
		if (null != lastAccessTime)
		{
			lastAccessTime = lastAccessTime.plusDays(unusedDay);

			LocalDateTime nowDate = LocalDateTime.now();
			if (nowDate.isAfter(lastAccessTime))
			{
				if (!AccountUtils.isSuperAdmin(authAccount.getType()))
				{
					accountMapper.updateAccountStatus(authAccount.getId(), AccountStatus.Disable.getValue());
					LOG.warn("Account for {} days did not log in, lock automatically {}.", unusedDay, authAccount.getName());

					throw new AccountDisabledException(authAccount.getName() + " is disabled.");
				}
			}
		}
	}

	private void verifyAccountPasswd(char[] passwd, AuthAccount authAccount, AccountStrategy accountStrategy) throws AuthenticationException
	{
		String account = authAccount.getName();
		String encPasswd = null;

		try
		{
			encPasswd = PBEPasswdGenerator.derivePasswd(new String(passwd), authAccount.getSalt());
		}
		catch (CryptoException e)
		{
			throw new BadCredentialsException("Password encrypt failed.");
		}

		String checkPwd = authAccount.getPassword();
		if (!checkPwd.equals(encPasswd))
		{
			boolean lockAccount = processAuthFailedAccount(account, accountStrategy);
			if (lockAccount)
			{
				accountMapper.lockAccount(authAccount.getId(), AccountStatus.Locked.getValue(), LocalDateTime.now());
				throw new AccountLockedException("Lock Account, name is " + account + ".");
			}

			int leaveCount = obtainAuthLeaveCount(account, accountStrategy);
			String[] params = { String.valueOf(leaveCount) };

			throw new BadCredentialsException(null, params,
			        "Account crediential is wrong, name is " + account + ", there are " + leaveCount + " more chances to log in.");
		}
	}

	private AccountImpl buildAccountImpl(AuthAccount authAccount, String host)
	{
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>(5);

		List<Role> roles = accountViewService.getRoleByAccountId(authAccount.getId());
		for (Role role : roles)
		{
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			grantedAuthorities.add(grantedAuthority);
		}

		IdleTimeout idleTimeout = idleTimeoutService.queryIdleTimeout(authAccount.getId());

		AccountImpl accountImpl = new AccountImpl();
		accountImpl.setAccountId(authAccount.getId());
		accountImpl.setAccountName(authAccount.getName());
		accountImpl.setAuthorities(grantedAuthorities);
		accountImpl.setFirstLogin(authAccount.isFirstLogin());
		accountImpl.setSuperAdmin(AccountUtils.isSuperAdmin(authAccount.getType()));
		accountImpl.setLoginIpAddr(host);
		accountImpl.setSessionIdleTimeout(idleTimeout.getTimeout());
		accountImpl.setCsrfToken(RandomGenerator.genHexString(32, true));

		// 初始化帐号凭证选项
		initAccountCredentialOptions(accountImpl);

		boolean twoFactorAuthcPassed = twoFactorAuthenticationService.determineInternalAuthcPass(accountImpl,
		        authAccount.getLastAccessIpAddr(), authAccount.getLastAccessTime());
		accountImpl.setTwoFactorAuthcPassed(twoFactorAuthcPassed);

		return accountImpl;
	}

	private void initAccountCredentialOptions(AccountImpl accountImpl)
	{
		boolean credentialsExpired = false;
		boolean remindModifyPasswd = false;

		String serviceId = applicationAuthService.getServiceId();

		// 判断密码是否过期
		PasswordStrategy passwordStrategy = passwordStrategyService.getPasswordStrategy(serviceId);

		int validDay = passwordStrategy.getValidDay();
		int reminderModifyDay = passwordStrategy.getReminderModifyDay();

		List<HistoryPassword> historyPasswords = historyPasswordService.queryHistoryPasswords(accountImpl.getAccountId(), 1);
		if (CollectionUtils.isNotEmpty(historyPasswords))
		{
			LocalDateTime modifyTime = historyPasswords.get(0).getModifyTime();

			long hisTime = modifyTime.plusDays(validDay).toLocalDate().toEpochDay();
			long nowTime = LocalDate.now().toEpochDay();
			if (nowTime > hisTime)
			{
				credentialsExpired = true;
			}
			else
			{
				int offset = (int) (hisTime - nowTime);
				if (offset > 0 && offset <= reminderModifyDay)
				{
					remindModifyPasswd = true;
				}
			}
		}

		accountImpl.setCredentialsExpired(credentialsExpired);
		accountImpl.setRemindModifyPasswd(remindModifyPasswd);
	}

	private boolean processAuthFailedAccount(String account, AccountStrategy accountStrategy)
	{
		int timeLimitPeriod = accountStrategy.getTimeLimitPeriod();
		int loginLimitNumber = accountStrategy.getLoginLimitNumber();

		synchronized (authFailedAccountMap)
		{
			boolean lockAccount = false;

			AuthFaildAccount authFailedAccount = authFailedAccountMap.getIfPresent(account);
			if (null == authFailedAccount)
			{
				authFailedAccount = new AuthFaildAccount();
				authFailedAccount.setAuthFailedCount(1);
				authFailedAccount.setAuthFailedTime(LocalDateTime.now());

				authFailedAccountMap.put(account, authFailedAccount);
			}
			else
			{
				LocalDateTime authFailedTime = authFailedAccount.getAuthFailedTime().plusMinutes(timeLimitPeriod);

				LocalDateTime now = LocalDateTime.now();
				if (authFailedTime.isAfter(now))
				{
					int authFailedCount = authFailedAccount.getAuthFailedCount() + 1;
					if (authFailedCount >= loginLimitNumber)
					{
						lockAccount = true;
					}
					else
					{
						authFailedAccount.setAuthFailedCount(authFailedCount);
					}
				}
				else
				{
					authFailedAccount.setAuthFailedCount(1);
					authFailedAccount.setAuthFailedTime(LocalDateTime.now());
				}
			}

			return lockAccount;
		}
	}

	private int obtainAuthLeaveCount(String account, AccountStrategy accountStrategy)
	{
		int loginLimitNumber = accountStrategy.getLoginLimitNumber();

		AuthFaildAccount authFaildAccount = authFailedAccountMap.getIfPresent(account);
		if (null != authFaildAccount)
		{
			int authFailedCount = authFaildAccount.getAuthFailedCount();

			int count = loginLimitNumber - authFailedCount;
			return count;
		}

		return 0;
	}

	private void cleanAuthFailedAccount(String account)
	{
		authFailedAccountMap.invalidate(account);
	}

	private void saveSecurityLog(String account, String host, String detail, boolean succeed)
	{
		SecurityLog securityLog = new SecurityLog();

		securityLog.setAccountName(account);
		securityLog.setTerminal(host);
		securityLog.setOperation(I18nUtils.getMessage("bmc.account.login.operation"));
		securityLog.setCreationTime(LocalDateTime.now());
		securityLog.setSource(I18nUtils.getMessage("bmc.account.source"));
		securityLog.setDetail(detail);

		if (succeed)
		{
			securityLog.setLogLevel(AuditLogLevel.INFO.getValue());
			securityLog.setResult(AuditLogResult.Success.getValue());
		}
		else
		{
			securityLog.setLogLevel(AuditLogLevel.RISK.getValue());
			securityLog.setResult(AuditLogResult.Failure.getValue());
		}

		AuditLogUtils.addSecurityLog(securityLog);
	}
}
