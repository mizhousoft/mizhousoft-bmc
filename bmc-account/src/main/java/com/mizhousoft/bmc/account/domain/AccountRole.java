package com.mizhousoft.bmc.account.domain;

/**
 * 帐号角色类
 *
 * @version
 */
public class AccountRole
{
	// ID
	private int id;

	// 帐号id
	private long accountId;

	// 角色id
	private int roleId;

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
	 * 获取roleId
	 * 
	 * @return
	 */
	public int getRoleId()
	{
		return roleId;
	}

	/**
	 * 设置roleId
	 * 
	 * @param roleId
	 */
	public void setRoleId(int roleId)
	{
		this.roleId = roleId;
	}
}
