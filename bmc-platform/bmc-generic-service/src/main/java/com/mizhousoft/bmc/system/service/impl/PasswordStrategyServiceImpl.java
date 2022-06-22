package com.mizhousoft.bmc.system.service.impl;

import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.CHARAPPEARSIZE;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.DOMAIN;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.HISTORYREPEATSIZE;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.MODIFYTIMEINTERVAL;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.REMINDERMODIFYDAY;
import static com.mizhousoft.bmc.system.constant.PasswordStrategyConstants.VALIDDAY;

import org.springframework.beans.factory.annotation.Autowired;
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
public class PasswordStrategyServiceImpl implements PasswordStrategyService
{
	@Autowired
	private FieldDictService fieldDictService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PasswordStrategy getPasswordStrategy(String srvId)
	{
		PasswordStrategy strategy = new PasswordStrategy();

		strategy.setCharAppearSize(fieldDictService.getIntValue(srvId, DOMAIN, CHARAPPEARSIZE));
		strategy.setHistoryRepeatSize(fieldDictService.getIntValue(srvId, DOMAIN, HISTORYREPEATSIZE));
		strategy.setModifyTimeInterval(fieldDictService.getIntValue(srvId, DOMAIN, MODIFYTIMEINTERVAL));
		strategy.setReminderModifyDay(fieldDictService.getIntValue(srvId, DOMAIN, REMINDERMODIFYDAY));
		strategy.setValidDay(fieldDictService.getIntValue(srvId, DOMAIN, VALIDDAY));

		return strategy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPasswordStrategy(String srvId, PasswordStrategy strategy) throws BMCException
	{
		fieldDictService.putValue(srvId, DOMAIN, CHARAPPEARSIZE, strategy.getCharAppearSize());
		fieldDictService.putValue(srvId, DOMAIN, HISTORYREPEATSIZE, strategy.getHistoryRepeatSize());
		fieldDictService.putValue(srvId, DOMAIN, MODIFYTIMEINTERVAL, strategy.getModifyTimeInterval());
		fieldDictService.putValue(srvId, DOMAIN, REMINDERMODIFYDAY, strategy.getReminderModifyDay());
		fieldDictService.putValue(srvId, DOMAIN, VALIDDAY, strategy.getValidDay());
	}
}
