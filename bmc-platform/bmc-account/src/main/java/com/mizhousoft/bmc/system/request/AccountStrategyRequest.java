package com.mizhousoft.bmc.system.request;

import org.hibernate.validator.constraints.Range;

/**
 * 帐号策略请求
 *
 * @version
 */
public class AccountStrategyRequest
{
	// ID
	private int id;

	// 帐号连续未使用天数
	@Range(min = 60, max = 180, message = "{bmc.account.strategy.unusedday.range.error}")
	private int accountUnusedDay;

	// 限定时间段长度
	@Range(min = 5, max = 720, message = "{bmc.account.strategy.timelimit.range.error}")
	private int timeLimitPeriod;

	// 限定时间段内连续登录失败次数
	@Range(min = 5, max = 30, message = "{bmc.account.strategy.loginlimit.range.error}")
	private int loginLimitNumber;

	// 帐号锁定时间策略
	private int lockTimeStrategy;

	// 帐号锁定时长
	@Range(min = 5, max = 60, message = "{bmc.account.strategy.locktime.range.error}")
	private int accountLockTime;

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
	 * 获取accountUnusedDay
	 * 
	 * @return
	 */
	public int getAccountUnusedDay()
	{
		return accountUnusedDay;
	}

	/**
	 * 设置accountUnusedDay
	 * 
	 * @param accountUnusedDay
	 */
	public void setAccountUnusedDay(int accountUnusedDay)
	{
		this.accountUnusedDay = accountUnusedDay;
	}

	/**
	 * 获取timeLimitPeriod
	 * 
	 * @return
	 */
	public int getTimeLimitPeriod()
	{
		return timeLimitPeriod;
	}

	/**
	 * 设置timeLimitPeriod
	 * 
	 * @param timeLimitPeriod
	 */
	public void setTimeLimitPeriod(int timeLimitPeriod)
	{
		this.timeLimitPeriod = timeLimitPeriod;
	}

	/**
	 * 获取loginLimitNumber
	 * 
	 * @return
	 */
	public int getLoginLimitNumber()
	{
		return loginLimitNumber;
	}

	/**
	 * 设置loginLimitNumber
	 * 
	 * @param loginLimitNumber
	 */
	public void setLoginLimitNumber(int loginLimitNumber)
	{
		this.loginLimitNumber = loginLimitNumber;
	}

	/**
	 * 获取lockTimeStrategy
	 * 
	 * @return
	 */
	public int getLockTimeStrategy()
	{
		return lockTimeStrategy;
	}

	/**
	 * 设置lockTimeStrategy
	 * 
	 * @param lockTimeStrategy
	 */
	public void setLockTimeStrategy(int lockTimeStrategy)
	{
		this.lockTimeStrategy = lockTimeStrategy;
	}

	/**
	 * 获取accountLockTime
	 * 
	 * @return
	 */
	public int getAccountLockTime()
	{
		return accountLockTime;
	}

	/**
	 * 设置accountLockTime
	 * 
	 * @param accountLockTime
	 */
	public void setAccountLockTime(int accountLockTime)
	{
		this.accountLockTime = accountLockTime;
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
		builder.append("\", \"accountUnusedDay\":\"");
		builder.append(accountUnusedDay);
		builder.append("\", \"timeLimitPeriod\":\"");
		builder.append(timeLimitPeriod);
		builder.append("\", \"loginLimitNumber\":\"");
		builder.append(loginLimitNumber);
		builder.append("\", \"lockTimeStrategy\":\"");
		builder.append(lockTimeStrategy);
		builder.append("\", \"accountLockTime\":\"");
		builder.append(accountLockTime);
		builder.append("\"}");
		return builder.toString();
	}
}
