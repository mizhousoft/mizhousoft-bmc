package com.mizhousoft.bmc.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.constant.AccountStatus;
import com.mizhousoft.bmc.account.constant.AccountType;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.mapper.AccountMapper;
import com.mizhousoft.bmc.account.model.AuthAccount;
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.account.util.AccountUtils;
import com.mizhousoft.commons.crypto.CryptoException;
import com.mizhousoft.commons.crypto.generator.PBEPasswdGenerator;
import com.mizhousoft.commons.crypto.generator.RandomGenerator;

/**
 * 帐号服务
 *
 * @version
 */
@Service
public class AccountServiceImpl implements AccountService
{
	@Autowired
	private AccountMapper accountMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAccount(AuthAccount account) throws BMCException
	{
		String password = account.getPassword();

		List<Account> dbAccount = accountMapper.findByName(account.getName());
		if (dbAccount.size() > 0)
		{
			throw new BMCException("bmc.account.name.exist.error", "Account does exist, name is " + account.getName() + ".");
		}

		try
		{
			account.setType(AccountType.GeneralAdmin.getValue());
			account.setFirstLogin(true);
			account.setLastAccessTime(null);
			account.setLockTime(null);

			String salt = RandomGenerator.genBase64String(16);
			account.setSalt(salt);

			String encPasswd = PBEPasswdGenerator.derivePasswd(password, salt);
			account.setPassword(encPasswd);
		}
		catch (CryptoException e)
		{
			throw new BMCException("bmc.account.add.failed", "Derive password failed.", e);
		}

		accountMapper.save(account);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disableAccount(Account account) throws BMCException
	{
		if (AccountUtils.isSuperAdmin(account.getType()))
		{
			throw new BMCException("bmc.account.superadmin.disable.error",
			        "Super admin can not disable, account id is " + account.getId() + '.');
		}

		if (!AccountStatus.isEnable(account.getStatus()))
		{
			throw new BMCException("bmc.account.cannot.disable.error",
			        "Account can not disable, account id is " + account.getId() + ", status is " + account.getStatus() + '.');
		}

		accountMapper.updateAccountStatus(account.getId(), AccountStatus.Disable.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enableAccount(Account account) throws BMCException
	{
		if (AccountUtils.isSuperAdmin(account.getType()))
		{
			throw new BMCException("bmc.account.superadmin.enable.error",
			        "Super admin can not enable, account id is " + account.getId() + '.');
		}

		if (!AccountStatus.isDisable(account.getStatus()))
		{
			throw new BMCException("bmc.account.cannot.enable.error",
			        "Account can not enable, account id is " + account.getId() + ", status is " + account.getStatus() + '.');
		}

		accountMapper.updateAccountStatus(account.getId(), AccountStatus.Enable.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unlockAccount(Account account) throws BMCException
	{
		if (AccountUtils.isSuperAdmin(account.getType()))
		{
			throw new BMCException("bmc.account.superadmin.unlock.error",
			        "Super admin can not unlock, account id is " + account.getId() + '.');
		}

		if (!AccountStatus.isLock(account.getStatus()))
		{
			throw new BMCException("bmc.account.cannot.unlock.error",
			        "Account can not unlock, account id is " + account.getId() + ", status is " + account.getStatus() + '.');
		}

		accountMapper.unlockAccount(account.getId(), AccountStatus.Enable.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPhoneNumber(Account account) throws BMCException
	{
		accountMapper.updatePhoneNumber(account.getId(), account.getPhoneNumber());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyLastAccess(Account account) throws BMCException
	{
		accountMapper.updateLastAccess(account.getId(), account.getLastAccessTime(), account.getLastAccessIpAddr());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAccount(Account account) throws BMCException
	{
		if (AccountUtils.isSuperAdmin(account.getType()))
		{
			throw new BMCException("bmc.account.superadmin.delete.error",
			        "Super admin can not delete, account id is " + account.getId() + '.');
		}

		accountMapper.delete(account.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account loadById(long id) throws BMCException
	{
		Account account = getById(id);
		if (null == account)
		{
			throw new BMCException("bmc.account.not.found.error", "Account already has been deleted, id is " + id + '.');
		}

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account getById(long id)
	{
		return accountMapper.findById(id);
	}
}
