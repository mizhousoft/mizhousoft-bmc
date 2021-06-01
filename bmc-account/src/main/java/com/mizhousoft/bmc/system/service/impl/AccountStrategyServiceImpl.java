package com.mizhousoft.bmc.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.constant.AccountStrategyConstants;
import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.bmc.system.mapper.AccountStrategyMapper;
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
	private AccountStrategyMapper accountStrategyMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountStrategy queryAccountStrategy()
	{
		AccountStrategy accountStrategy = accountStrategyMapper.findOne();
		if (null == accountStrategy)
		{
			accountStrategy = new AccountStrategy();
			accountStrategy.setAccountUnusedDay(AccountStrategyConstants.ACCOUNT_UNUSED_DAY);
			accountStrategy.setTimeLimitPeriod(AccountStrategyConstants.TIME_LIMIT_PERIOD);
			accountStrategy.setLoginLimitNumber(AccountStrategyConstants.LOGIN_LIMIT_NUMBER);
			accountStrategy.setLockTimeStrategy(AccountStrategyConstants.LOCK_TIME_STRATEGY);
			accountStrategy.setAccountLockTime(AccountStrategyConstants.ACCOUNT_LOCK_TIME);
		}

		return accountStrategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyAccountStrategy(AccountStrategy accountStrategy) throws BMCException
	{
		AccountStrategy stractegy = accountStrategyMapper.findOne();
		if (null == stractegy)
		{
			accountStrategy.setId(0);
			accountStrategyMapper.save(accountStrategy);
		}
		else
		{
			accountStrategy.setId(stractegy.getId());
			accountStrategyMapper.update(accountStrategy);
		}
	}
}
