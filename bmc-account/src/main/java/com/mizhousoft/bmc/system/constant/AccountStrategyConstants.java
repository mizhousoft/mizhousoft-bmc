package com.mizhousoft.bmc.system.constant;

/**
 * 帐号策略常量
 *
 * @version
 */
public interface AccountStrategyConstants
{
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
