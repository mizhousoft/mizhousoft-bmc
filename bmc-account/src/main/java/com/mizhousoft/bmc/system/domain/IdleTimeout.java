package com.mizhousoft.bmc.system.domain;

import java.io.Serializable;

/**
 * 闲置超时时间
 *
 * @version
 */
public class IdleTimeout implements Serializable
{
	private static final long serialVersionUID = -6015558076083848927L;

	// ID
	private int id;

	// 帐号Id
	private long accountId;

	// 超时时间
	private int timeout;

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
	 * 获取accountId
	 * 
	 * @return
	 */
	public long getAccountId()
	{
		return accountId;
	}

	/**
	 * 设置accountId
	 * 
	 * @param accountId
	 */
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}

	/**
	 * 获取timeout
	 * 
	 * @return
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * 设置timeout
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}
}
