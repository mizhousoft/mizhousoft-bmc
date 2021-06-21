package com.mizhousoft.bmc.account.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.account.constant.AccountStatus;
import com.mizhousoft.bmc.account.domain.AuthAccount;
import com.mizhousoft.bmc.account.domain.AuthFaildAccount;
import com.mizhousoft.bmc.account.domain.HistoryPassword;
import com.mizhousoft.bmc.account.mapper.AccountMapper;
import com.mizhousoft.bmc.account.service.AccountBusinessService;
import com.mizhousoft.bmc.account.service.HistoryPasswordService;
import com.mizhousoft.bmc.account.util.AccountUtils;
import com.mizhousoft.bmc.auditlog.constants.AuditLogLevel;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.security.AccountDetails;
import com.mizhousoft.bmc.security.GrantedAuthority;
import com.mizhousoft.bmc.security.SimpleGrantedAuthority;
import com.mizhousoft.bmc.security.exception.AccountDisabledException;
import com.mizhousoft.bmc.security.exception.AccountLockedException;
import com.mizhousoft.bmc.security.exception.AuthenticationException;
import com.mizhousoft.bmc.security.exception.BadCredentialsException;
import com.mizhousoft.bmc.security.impl.AccountImpl;
import com.mizhousoft.bmc.security.service.AccountAuthcService;
import com.mizhousoft.bmc.security.service.TwoFactorAuthenticationService;
import com.mizhousoft.bmc.system.constant.AccountStrategyConstants;
import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.bmc.system.service.AccountStrategyService;
import com.mizhousoft.bmc.system.service.PasswordStrategyService;
import com.mizhousoft.commons.crypto.CryptoException;
import com.mizhousoft.commons.crypto.generator.PBEPasswdGenerator;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 帐号认证服务
 *
 * @version
 */
@Service
public class AccountAuthcServiceImpl implements AccountAuthcService, CommandLineRunner
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
	private AccountBusinessService accountBusinessService;

	@Autowired
	private ApplicationContext applicationContext;

	// 认证失败帐号集合
	private Map<String, AuthFaildAccount> authFailedAccountMap = new ConcurrentHashMap<String, AuthFaildAccount>(1000);

	private TwoFactorAuthenticationService twoFactorAuthenticationService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountDetails authenticate(String account, char[] passwd, String host) throws AuthenticationException
	{
		try
		{
			AccountDetails accountDetails = doAuthenticate(account, passwd, host);

			saveLoginLog(account, host, null, true);

			return accountDetails;
		}
		catch (AuthenticationException e)
		{
			saveLoginLog(account, host, e.getMessage(), false);
			throw e;
		}
	}

	private AccountDetails doAuthenticate(String account, char[] passwd, String host) throws AuthenticationException
	{
		AccountStrategy accountStrategy = accountStrategyService.queryAccountStrategy();

		// 查询帐号
		AuthAccount authAccount = queryAuthAccount(account, accountStrategy);

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
			if (null == twoFactorAuthenticationService || !twoFactorAuthenticationService.isEnable() || twoFactorAuthcPassed)
			{
				accountMapper.updateLastAccess(accountDetails.getAccountId(), new Date(), host);
			}
		}
		catch (Throwable e)
		{
			LOG.error("Update last access failed.", e);
		}
	}

	private AuthAccount queryAuthAccount(String account, AccountStrategy accountStrategy) throws AuthenticationException
	{
		AuthAccount authAccount = accountMapper.findAuthAccount(account);
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
			Date lockTime = authAccount.getLockTime();
			int timeLimitPeriod = accountStrategy.getTimeLimitPeriod();
			Calendar cal = Calendar.getInstance();
			cal.setTime(lockTime);
			cal.add(Calendar.MINUTE, timeLimitPeriod);

			Date now = new Date();
			if (cal.getTime().after(now))
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

		Date lastAccessTime = authAccount.getLastAccessTime();
		if (null != lastAccessTime)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(lastAccessTime);
			cal.add(Calendar.DAY_OF_MONTH, unusedDay);

			Date nowDate = new Date();
			if (nowDate.after(cal.getTime()))
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
				accountMapper.lockAccount(authAccount.getId(), AccountStatus.Locked.getValue(), new Date());
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

		List<Role> roles = accountBusinessService.getRoleByAccountId(authAccount.getId());
		for (Role role : roles)
		{
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			grantedAuthorities.add(grantedAuthority);
		}

		AccountImpl accountImpl = new AccountImpl();
		accountImpl.setAccountId(authAccount.getId());
		accountImpl.setAccountName(authAccount.getName());
		accountImpl.setAuthorities(grantedAuthorities);
		accountImpl.setFirstLogin(authAccount.isFirstLogin());
		accountImpl.setSuperAdmin(AccountUtils.isSuperAdmin(authAccount.getType()));
		accountImpl.setLoginIpAddr(host);

		// 初始化帐号凭证选项
		initAccountCredentialOptions(accountImpl);

		if (null != twoFactorAuthenticationService)
		{
			boolean twoFactorAuthcPassed = twoFactorAuthenticationService.determineInternalAuthcPass(accountImpl,
			        authAccount.getLastAccessIpAddr(), authAccount.getLastAccessTime());
			accountImpl.setTwoFactorAuthcPassed(twoFactorAuthcPassed);
		}

		return accountImpl;
	}

	private void initAccountCredentialOptions(AccountImpl accountImpl)
	{
		boolean credentialsExpired = false;
		boolean remindModifyPasswd = false;

		// 判断密码是否过期
		PasswordStrategy passwordStrategy = passwordStrategyService.queryPasswordStrategy();

		int validDay = passwordStrategy.getValidDay();
		int reminderModifyDay = passwordStrategy.getReminderModifyDay();

		List<HistoryPassword> historyPasswords = historyPasswordService.queryHistoryPasswords(accountImpl.getAccountId(), 1);
		if (CollectionUtils.isNotEmpty(historyPasswords))
		{
			Date modifyTime = historyPasswords.get(0).getModifyTime();
			Calendar hisCal = Calendar.getInstance();
			hisCal.setTime(modifyTime);
			hisCal.add(Calendar.DAY_OF_MONTH, validDay);
			hisCal.set(Calendar.HOUR_OF_DAY, 0);
			hisCal.set(Calendar.MINUTE, 0);
			hisCal.set(Calendar.SECOND, 0);
			hisCal.set(Calendar.MILLISECOND, 0);

			Calendar nowCal = Calendar.getInstance();
			nowCal.set(Calendar.HOUR_OF_DAY, 0);
			nowCal.set(Calendar.MINUTE, 0);
			nowCal.set(Calendar.SECOND, 0);
			nowCal.set(Calendar.MILLISECOND, 0);

			long hisTime = hisCal.getTime().getTime();
			long nowTime = nowCal.getTime().getTime();
			if (nowTime > hisTime)
			{
				credentialsExpired = true;
			}
			else
			{
				int offset = (int) ((hisTime - nowTime) / (24 * 60 * 60 * 1000));
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

			AuthFaildAccount authFailedAccount = authFailedAccountMap.get(account);
			if (null == authFailedAccount)
			{
				authFailedAccount = new AuthFaildAccount();
				authFailedAccount.setAuthFailedCount(1);
				authFailedAccount.setAuthFailedTime(new Date());

				authFailedAccountMap.put(account, authFailedAccount);
			}
			else
			{
				Date authFailedTime = authFailedAccount.getAuthFailedTime();
				Calendar cal = Calendar.getInstance();
				cal.setTime(authFailedTime);
				cal.add(Calendar.MINUTE, timeLimitPeriod);

				Date now = new Date();
				if (cal.getTime().after(now))
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
					authFailedAccount.setAuthFailedTime(new Date());
				}

				authFailedAccountMap.remove(account);

				if (!lockAccount)
				{
					authFailedAccountMap.put(account, authFailedAccount);
				}
			}

			return lockAccount;
		}
	}

	private int obtainAuthLeaveCount(String account, AccountStrategy accountStrategy)
	{
		int loginLimitNumber = accountStrategy.getLoginLimitNumber();

		AuthFaildAccount authFaildAccount = authFailedAccountMap.get(account);
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
		authFailedAccountMap.remove(account);
	}

	private void saveLoginLog(String account, String host, String detail, boolean loginSuccess)
	{
		SecurityLog securityLog = new SecurityLog();

		securityLog.setAccountName(account);
		securityLog.setTerminal(host);
		securityLog.setOperation(I18nUtils.getMessage("bmc.account.login.operation"));
		securityLog.setCreationTime(new Date());
		securityLog.setSource(I18nUtils.getMessage("bmc.account.source"));
		securityLog.setDetail(detail);

		if (loginSuccess)
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run(String... args) throws Exception
	{
		Map<String, TwoFactorAuthenticationService> map = applicationContext.getBeansOfType(TwoFactorAuthenticationService.class);
		if (!MapUtils.isEmpty(map))
		{
			TwoFactorAuthenticationService twoFactorAuthenticationService = map.entrySet().iterator().next().getValue();

			this.twoFactorAuthenticationService = twoFactorAuthenticationService;
		}

		if (null == this.twoFactorAuthenticationService)
		{
			LOG.info("Two-factor authentication is not configured.");
		}
		else
		{
			LOG.info("Two-factor authentication loaded successfully.");
		}
	}
}
