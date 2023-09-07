package com.mizhousoft.bmc.account.domain;

import java.time.LocalDateTime;

/**
 * 历史密码
 *
 * @version
 */
public class HistoryPassword
{
	// ID
	private int id;

	// 帐号id
	private long accountId;

	// 历史密码
	private String historyPwd;

	// 修改时间
	private LocalDateTime modifyTime;

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
	 * 获取historyPwd
	 * 
	 * @return
	 */
	public String getHistoryPwd()
	{
		return historyPwd;
	}

	/**
	 * 设置historyPwd
	 * 
	 * @param historyPwd
	 */
	public void setHistoryPwd(String historyPwd)
	{
		this.historyPwd = historyPwd;
	}

	/**
	 * 获取modifyTime
	 * 
	 * @return
	 */
	public LocalDateTime getModifyTime()
	{
		return modifyTime;
	}

	/**
	 * 设置modifyTime
	 * 
	 * @param modifyTime
	 */
	public void setModifyTime(LocalDateTime modifyTime)
	{
		this.modifyTime = modifyTime;
	}
}
