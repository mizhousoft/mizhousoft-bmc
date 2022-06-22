package com.mizhousoft.bmc.system.service.impl;

import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.ACCOUNTLOCKTIME;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.ACCOUNTUNUSEDDAY;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.DOMAIN;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.LOCKTIMESTRATEGY;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.LOGINLIMITNUMBER;
import static com.mizhousoft.bmc.system.constant.AccountStrategyConstants.TIMELIMITPERIOD;

import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountStrategyServiceImpl implements AccountStrategyService
{
	@Autowired
	private FieldDictService fieldDictService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountStrategy getAccountStrategy(String srvId)
	{
		AccountStrategy strategy = new AccountStrategy();

		strategy.setAccountUnusedDay(fieldDictService.getIntValue(srvId, DOMAIN, ACCOUNTUNUSEDDAY));
		strategy.setTimeLimitPeriod(fieldDictService.getIntValue(srvId, DOMAIN, TIMELIMITPERIOD));
		strategy.setLoginLimitNumber(fieldDictService.getIntValue(srvId, DOMAIN, LOGINLIMITNUMBER));
		strategy.setLockTimeStrategy(fieldDictService.getIntValue(srvId, DOMAIN, LOCKTIMESTRATEGY));
		strategy.setAccountLockTime(fieldDictService.getIntValue(srvId, DOMAIN, ACCOUNTLOCKTIME));

		return strategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyAccountStrategy(String srvId, AccountStrategy accountStrategy) throws BMCException
	{
		fieldDictService.putValue(srvId, DOMAIN, ACCOUNTUNUSEDDAY, accountStrategy.getAccountUnusedDay());
		fieldDictService.putValue(srvId, DOMAIN, TIMELIMITPERIOD, accountStrategy.getTimeLimitPeriod());
		fieldDictService.putValue(srvId, DOMAIN, LOGINLIMITNUMBER, accountStrategy.getLoginLimitNumber());
		fieldDictService.putValue(srvId, DOMAIN, LOCKTIMESTRATEGY, accountStrategy.getLockTimeStrategy());
		fieldDictService.putValue(srvId, DOMAIN, ACCOUNTLOCKTIME, accountStrategy.getAccountLockTime());
	}
}
