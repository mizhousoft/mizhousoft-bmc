package com.mizhousoft.bmc.system.request;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.Validator;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 密码策略请求
 *
 * @version
 */
public class PasswordStrategyReqesut implements Validator
{
	// ID
	private int id;

	// 密码不能与历史密码重复次数
	private int historyRepeatSize;

	// 密码中允许同一个字符出现的次数
	private int charAppearSize;

	// 密码修改最短时间间隔，单位分钟
	private int modifyTimeInterval;

	// 密码有效期，单位天
	private int validDay;

	// 距离密码到期，提醒用户修改的天数，单位天
	private int reminderModifyDay;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Assert.range(historyRepeatSize, 1, 10, "bmc.password.strategy.history.repeatsize.range.error", "historyRepeatSize");

		Assert.range(charAppearSize, 1, 4, "bmc.password.strategy.char.appearsize.range.error", "charAppearSize");

		Assert.range(modifyTimeInterval, 5, 60, "bmc.password.strategy.modifytime.interval.range.error", "modifyTimeInterval");

		Assert.range(validDay, 90, 360, "bmc.password.strategy.validday.range.error", "validDay");

		Assert.range(reminderModifyDay, 5, 15, "bmc.password.strategy.reminder.modifyday.range.error", "reminderModifyDay");
	}

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * 获取historyRepeatSize
	 * 
	 * @return
	 */
	public int getHistoryRepeatSize()
	{
		return historyRepeatSize;
	}

	/**
	 * 设置historyRepeatSize
	 * 
	 * @param historyRepeatSize
	 */
	public void setHistoryRepeatSize(int historyRepeatSize)
	{
		this.historyRepeatSize = historyRepeatSize;
	}

	/**
	 * 获取charAppearSize
	 * 
	 * @return
	 */
	public int getCharAppearSize()
	{
		return charAppearSize;
	}

	/**
	 * 设置charAppearSize
	 * 
	 * @param charAppearSize
	 */
	public void setCharAppearSize(int charAppearSize)
	{
		this.charAppearSize = charAppearSize;
	}

	/**
	 * 获取modifyTimeInterval
	 * 
	 * @return
	 */
	public int getModifyTimeInterval()
	{
		return modifyTimeInterval;
	}

	/**
	 * 设置modifyTimeInterval
	 * 
	 * @param modifyTimeInterval
	 */
	public void setModifyTimeInterval(int modifyTimeInterval)
	{
		this.modifyTimeInterval = modifyTimeInterval;
	}

	/**
	 * 获取validDay
	 * 
	 * @return
	 */
	public int getValidDay()
	{
		return validDay;
	}

	/**
	 * 设置validDay
	 * 
	 * @param validDay
	 */
	public void setValidDay(int validDay)
	{
		this.validDay = validDay;
	}

	/**
	 * 获取reminderModifyDay
	 * 
	 * @return
	 */
	public int getReminderModifyDay()
	{
		return reminderModifyDay;
	}

	/**
	 * 设置reminderModifyDay
	 * 
	 * @param reminderModifyDay
	 */
	public void setReminderModifyDay(int reminderModifyDay)
	{
		this.reminderModifyDay = reminderModifyDay;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"historyRepeatSize\":\"");
		builder.append(historyRepeatSize);
		builder.append("\", \"charAppearSize\":\"");
		builder.append(charAppearSize);
		builder.append("\", \"modifyTimeInterval\":\"");
		builder.append(modifyTimeInterval);
		builder.append("\", \"validDay\":\"");
		builder.append(validDay);
		builder.append("\", \"reminderModifyDay\":\"");
		builder.append(reminderModifyDay);
		builder.append("\"}");
		return builder.toString();
	}
}
