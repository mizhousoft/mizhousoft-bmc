package com.mizhousoft.bmc.system.service.impl;

import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.ACCOUNTLOCKTIME;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.ACCOUNTUNUSEDDAY;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.DOMAIN;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.LOCKTIMESTRATEGY;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.LOGINLIMITNUMBER;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.TIMELIMITPERIOD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.dictionary.service.FieldDictService;
import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.bmc.system.service.AccountStrategyService;

/**
 * 帐号策略服务
 *
 * @version
 */
@Service
public class AccountStrategyServiceImpl implements AccountStrategyService, CommandLineRunner
{
	@Autowired
	private FieldDictService fieldDictService;

	private AccountStrategy accountStrategy;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountStrategy getAccountStrategy()
	{
		return accountStrategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyAccountStrategy(AccountStrategy accountStrategy) throws BMCException
	{
		fieldDictService.putValue(DOMAIN, ACCOUNTUNUSEDDAY, accountStrategy.getAccountUnusedDay());
		fieldDictService.putValue(DOMAIN, TIMELIMITPERIOD, accountStrategy.getTimeLimitPeriod());
		fieldDictService.putValue(DOMAIN, LOGINLIMITNUMBER, accountStrategy.getLoginLimitNumber());
		fieldDictService.putValue(DOMAIN, LOCKTIMESTRATEGY, accountStrategy.getLockTimeStrategy());
		fieldDictService.putValue(DOMAIN, ACCOUNTLOCKTIME, accountStrategy.getAccountLockTime());

		this.accountStrategy = accountStrategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run(String... args) throws Exception
	{
		AccountStrategy strategy = new AccountStrategy();
		strategy.setAccountUnusedDay(fieldDictService.getIntValue(DOMAIN, ACCOUNTUNUSEDDAY));
		strategy.setTimeLimitPeriod(fieldDictService.getIntValue(DOMAIN, TIMELIMITPERIOD));
		strategy.setLoginLimitNumber(fieldDictService.getIntValue(DOMAIN, LOGINLIMITNUMBER));
		strategy.setLockTimeStrategy(fieldDictService.getIntValue(DOMAIN, LOCKTIMESTRATEGY));
		strategy.setAccountLockTime(fieldDictService.getIntValue(DOMAIN, ACCOUNTLOCKTIME));

		this.accountStrategy = strategy;
	}
}
