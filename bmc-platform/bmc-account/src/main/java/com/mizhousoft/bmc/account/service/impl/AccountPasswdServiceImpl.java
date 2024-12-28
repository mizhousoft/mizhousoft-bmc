package com.mizhousoft.bmc.account.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.domain.HistoryPassword;
import com.mizhousoft.bmc.account.mapper.AccountMapper;
import com.mizhousoft.bmc.account.model.AuthAccount;
import com.mizhousoft.bmc.account.service.AccountPasswdService;
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.account.service.HistoryPasswordService;
import com.mizhousoft.bmc.account.util.AccountUtils;
import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.bmc.system.service.PasswordStrategyService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.commons.crypto.CryptoException;
import com.mizhousoft.commons.crypto.generator.PBEPasswdGenerator;
import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

/**
 * 帐号密码服务
 *
 * @version
 */
@Service
public class AccountPasswdServiceImpl implements AccountPasswdService
{
	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountService accountService;

	// 密码策略服务
	@Autowired
	private PasswordStrategyService passwordStrategyService;

	// 历史密码服务
	@Autowired
	private HistoryPasswordService historyPasswordService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyFirstLoginPassword(Account account, String oldPasswd, String newPasswd) throws BMCException
	{
		unifiedModifyPassword(account, oldPasswd, newPasswd, true);

		accountMapper.updateFirstLogin(account.getId(), false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPassword(Account account, String oldPasswd, String newPasswd) throws BMCException
	{
		unifiedModifyPassword(account, oldPasswd, newPasswd, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account resetPassword(long id, String password) throws BMCException
	{
		String srvId = applicationAuthService.getServiceId();

		Account account = accountService.loadById(srvId, id);

		String name = account.getName();

		// 管理员帐号不能重置密码
		if (AccountUtils.isSuperAdmin(account.getType()))
		{
			String[] errorParams = { name };
			throw new BMCException("bmc.account.superadmin.not.reset.error", errorParams,
			        "Super admin can not reset password, name is " + name + ".");
		}

		checkPassword(name, password);

		// 查询帐号
		AuthAccount authAccount = accountMapper.findAuthAccount(account.getSrvId(), name);
		if (null == authAccount)
		{
			throw new BMCException("bmc.account.not.exist.error", "Acount not found, name is " + name + ".");
		}

		String encPasswd = null;

		try
		{
			encPasswd = PBEPasswdGenerator.derivePasswd(password, authAccount.getSalt());
		}
		catch (CryptoException e)
		{
			throw new BMCException("bmc.system.internal.error", "Password encrypt failed.");
		}

		accountMapper.updatePassword(account.getId(), encPasswd);

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	private void unifiedModifyPassword(Account account, String oldPasswd, String newPasswd, boolean isFirst) throws BMCException
	{
		String name = account.getName();

		// 校验密码
		checkPassword(name, newPasswd);

		// 查询帐号
		AuthAccount authAccount = accountMapper.findAuthAccount(account.getSrvId(), name);
		if (null == authAccount)
		{
			throw new BMCException("bmc.account.not.exist.error", "Acount not found, name is " + name + ".");
		}

		// 校验新老密码
		String encOldPasswd = null;

		try
		{
			encOldPasswd = PBEPasswdGenerator.derivePasswd(oldPasswd, authAccount.getSalt());
		}
		catch (CryptoException e)
		{
			throw new BMCException("bmc.system.internal.error", "Password encrypt failed, name is " + name + ".");
		}

		String accountPasswd = authAccount.getPassword();
		if (!accountPasswd.equals(encOldPasswd))
		{
			throw new BMCException("bmc.account.old.password.wrong", "Account old password is wrong, name is " + name + ".");
		}

		String encNewPasswd = null;

		try
		{
			encNewPasswd = PBEPasswdGenerator.derivePasswd(newPasswd, authAccount.getSalt());
		}
		catch (CryptoException e)
		{
			throw new BMCException("bmc.system.internal.error", "Password encrypt failed.");
		}

		if (!isFirst)
		{
			checkHistoryPassword(account.getId(), encNewPasswd);
		}

		accountMapper.updatePassword(account.getId(), encNewPasswd);
		historyPasswordService.save(account.getId(), encOldPasswd);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkPassword(String name, String password) throws BMCException
	{
		// 密码不能包含帐号名或倒序的帐号名。
		if (password.contains(name) || password.contains(StringUtils.reverse(name)))
		{
			throw new BMCException("bmc.account.password.contain.name.error", "Password contains account name or reverse account name.");
		}

		if (!Pattern.matches(".*[a-z]+.*", password) || !Pattern.matches(".*[A-Z]+.*", password) || !Pattern.matches(".*[0-9]+.*", password)
		        || !Pattern.matches(".*[~!@#$%^&*()+=-_]+.*", password))
		{
			throw new BMCException("bmc.account.password.char.illegal", "Password does not contain a-z or A-Z or 0-9 or ~!@#$%^&*()_-+=.");
		}

		String srvId = applicationAuthService.getServiceId();

		PasswordStrategy passwdStrategy = passwordStrategyService.getPasswordStrategy(srvId);

		// 检验密码同一个字符出现的次数
		int charAppearSize = passwdStrategy.getCharAppearSize();

		int length = password.length();
		for (int i = 0; i < length; ++i)
		{
			char c = password.charAt(i);
			int count = 0;
			for (int j = 0; j < length; ++j)
			{
				char cc = password.charAt(j);
				if (c == cc)
				{
					count = count + 1;
				}
			}

			if (count > charAppearSize)
			{
				String[] codeParams = { String.valueOf(c), String.valueOf(count), String.valueOf(charAppearSize) };
				throw new BMCException("bmc.account.password.char.appear.exceed.error", codeParams,
				        "The same number of characters appear too much.");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int calcPasswordExpiringDays(long accountId)
	{
		String srvId = applicationAuthService.getServiceId();

		PasswordStrategy passwordStrategy = passwordStrategyService.getPasswordStrategy(srvId);

		List<HistoryPassword> historyPasswords = historyPasswordService.queryHistoryPasswords(accountId, 1);
		if (!CollectionUtils.isEmpty(historyPasswords))
		{
			LocalDateTime modifyTime = historyPasswords.get(0).getModifyTime();
			LocalDate modifyDate = modifyTime.plusDays(passwordStrategy.getValidDay()).toLocalDate();

			LocalDate now = LocalDate.now();

			long hisTime = modifyDate.toEpochDay();
			long nowTime = now.toEpochDay();
			int offset = (int) (hisTime - nowTime);

			return offset;
		}

		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	private void checkHistoryPassword(long id, String newPassword) throws BMCException
	{
		String srvId = applicationAuthService.getServiceId();

		PasswordStrategy passwdStrategy = passwordStrategyService.getPasswordStrategy(srvId);
		int repeatSize = passwdStrategy.getHistoryRepeatSize();

		List<HistoryPassword> historyPasswords = historyPasswordService.queryHistoryPasswords(id, repeatSize);
		if (!CollectionUtils.isEmpty(historyPasswords))
		{
			// 校验修改密码时间间隔
			HistoryPassword latestPassword = historyPasswords.get(0);
			long previousTime = LocalDateTimeUtils.toTimestamp(latestPassword.getModifyTime());
			long currentTime = System.currentTimeMillis();
			long offset = currentTime - previousTime;
			long timeInterval = passwdStrategy.getModifyTimeInterval() * 60 * 1000;
			if (offset < timeInterval)
			{
				String[] errorParams = { String.valueOf(passwdStrategy.getModifyTimeInterval()) };
				throw new BMCException("bmc.account.password.modify.time.interval.short.error", errorParams,
				        "Account password modify time interval is short.");
			}

			// 校验是否与历史密码重复
			for (HistoryPassword historyPassword : historyPasswords)
			{
				if (historyPassword.getHistoryPwd().equals(newPassword))
				{
					String[] errorParams = { String.valueOf(repeatSize) };
					throw new BMCException("bmc.account.password.new.and.history.repeat.error", errorParams,
					        "Account new password and history password repeat.");
				}
			}
		}
	}
}
