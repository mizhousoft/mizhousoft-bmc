package com.mizhousoft.bmc.account.model;

import java.time.LocalDateTime;

/**
 * 认证失败帐号
 *
 * @version
 */
public class AuthFaildAccount
{
	// 认证失败次数
	private int authFailedCount;

	// 认证失败时间
	private LocalDateTime authFailedTime;

	/**
	 * 获取authFailedCount
	 * 
	 * @return
	 */
	public int getAuthFailedCount()
	{
		return authFailedCount;
	}

	/**
	 * 设置authFailedCount
	 * 
	 * @param authFailedCount
	 */
	public void setAuthFailedCount(int authFailedCount)
	{
		this.authFailedCount = authFailedCount;
	}

	/**
	 * 获取authFailedTime
	 * 
	 * @return
	 */
	public LocalDateTime getAuthFailedTime()
	{
		return authFailedTime;
	}

	/**
	 * 设置authFailedTime
	 * 
	 * @param authFailedTime
	 */
	public void setAuthFailedTime(LocalDateTime authFailedTime)
	{
		this.authFailedTime = authFailedTime;
	}
}
