package com.mizhousoft.bmc.system.constant;

/**
 * 帐号策略常量
 *
 * @version
 */
public interface AccountStrategyConstants
{
	// domain
	String DOMAIN = "account-strategy";

	// 帐号连续未使用天数
	String ACCOUNTUNUSEDDAY = "accountUnusedDay";

	// 限定时间段长度
	String TIMELIMITPERIOD = "timeLimitPeriod";

	// 限定时间段内连续登录次数
	String LOGINLIMITNUMBER = "loginLimitNumber";

	// 帐号锁定时间策略
	String LOCKTIMESTRATEGY = "lockTimeStrategy";

	// 帐号锁定时长
	String ACCOUNTLOCKTIME = "accountLockTime";

	// 帐号连续未使用天数
	int ACCOUNT_UNUSED_DAY = 90;

	// 限定时间段长度（分钟）
	int TIME_LIMIT_PERIOD = 10;

	// 限定时间段内连续登录失败次数
	int LOGIN_LIMIT_NUMBER = 5;

	// 帐号锁定时间策略
	int LOCK_TIME_STRATEGY = 1;

	// 帐号锁定时间
	int ACCOUNT_LOCK_TIME = 10;
}
