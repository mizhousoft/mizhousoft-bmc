package com.mizhousoft.bmc.system.service.impl;

import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.CHARAPPEARSIZE;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.DOMAIN;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.HISTORYREPEATSIZE;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.MODIFYTIMEINTERVAL;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.REMINDERMODIFYDAY;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.VALIDDAY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.dictionary.service.FieldDictService;
import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.bmc.system.service.PasswordStrategyService;

/**
 * 密码策略服务
 *
 * @version
 */
@Service
public class PasswordStrategyServiceImpl implements PasswordStrategyService, CommandLineRunner
{
	@Autowired
	private FieldDictService fieldDictService;

	private PasswordStrategy passwordStrategy;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PasswordStrategy getPasswordStrategy()
	{
		return passwordStrategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPasswordStrategy(PasswordStrategy strategy) throws BMCException
	{
		fieldDictService.putValue(DOMAIN, CHARAPPEARSIZE, strategy.getCharAppearSize());
		fieldDictService.putValue(DOMAIN, HISTORYREPEATSIZE, strategy.getHistoryRepeatSize());
		fieldDictService.putValue(DOMAIN, MODIFYTIMEINTERVAL, strategy.getModifyTimeInterval());
		fieldDictService.putValue(DOMAIN, REMINDERMODIFYDAY, strategy.getReminderModifyDay());
		fieldDictService.putValue(DOMAIN, VALIDDAY, strategy.getValidDay());

		this.passwordStrategy = strategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run(String... args) throws Exception
	{
		PasswordStrategy strategy = new PasswordStrategy();
		strategy.setCharAppearSize(fieldDictService.getIntValue(DOMAIN, CHARAPPEARSIZE));
		strategy.setHistoryRepeatSize(fieldDictService.getIntValue(DOMAIN, HISTORYREPEATSIZE));
		strategy.setModifyTimeInterval(fieldDictService.getIntValue(DOMAIN, MODIFYTIMEINTERVAL));
		strategy.setReminderModifyDay(fieldDictService.getIntValue(DOMAIN, REMINDERMODIFYDAY));
		strategy.setValidDay(fieldDictService.getIntValue(DOMAIN, VALIDDAY));

		passwordStrategy = strategy;
	}
}
