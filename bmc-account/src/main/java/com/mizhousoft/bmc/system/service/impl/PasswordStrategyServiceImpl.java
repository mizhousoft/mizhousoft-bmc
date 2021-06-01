package com.mizhousoft.bmc.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.constant.PasswordStrategyConstants;
import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.bmc.system.mapper.PasswordStrategyMapper;
import com.mizhousoft.bmc.system.service.PasswordStrategyService;

/**
 * 密码策略服务
 *
 * @version
 */
@Service
public class PasswordStrategyServiceImpl implements PasswordStrategyService
{
	@Autowired
	private PasswordStrategyMapper passwordStrategyMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PasswordStrategy queryPasswordStrategy()
	{
		PasswordStrategy passwordStrategy = passwordStrategyMapper.findOne();
		if (null == passwordStrategy)
		{
			passwordStrategy = new PasswordStrategy();
			passwordStrategy.setCharAppearSize(PasswordStrategyConstants.CHAR_APPEAR_SIZE);
			passwordStrategy.setHistoryRepeatSize(PasswordStrategyConstants.HISTORY_REPEAT_SIZE);
			passwordStrategy.setModifyTimeInterval(PasswordStrategyConstants.MODIFY_TIME_INTERVAL);
			passwordStrategy.setReminderModifyDay(PasswordStrategyConstants.REMINDER_MODIFY_DAY);
			passwordStrategy.setValidDay(PasswordStrategyConstants.VALID_DAY);
		}

		return passwordStrategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPasswordStrategy(PasswordStrategy passwordStrategy) throws BMCException
	{
		PasswordStrategy strategy = passwordStrategyMapper.findOne();
		if (null == strategy)
		{
			passwordStrategy.setId(0);
			passwordStrategyMapper.save(passwordStrategy);
		}
		else
		{
			passwordStrategy.setId(strategy.getId());
			passwordStrategyMapper.update(passwordStrategy);
		}
	}
}
