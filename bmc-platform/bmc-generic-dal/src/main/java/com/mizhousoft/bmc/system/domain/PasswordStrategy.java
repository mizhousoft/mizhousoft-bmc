package com.mizhousoft.bmc.system.domain;

/**
 * 密码策略
 *
 * @version
 */
public class PasswordStrategy 
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
}
