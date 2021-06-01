package com.mizhousoft.bmc.account.domain;

/**
 * 帐号信息
 *
 * @version
 */
public class AccountInfo extends Account
{
	// 状态
	private String statusText;

	// 角色字符串
	private String roleNames;

	/// 最后访问时间
	private String lastAccessTimeText;

	// 是否超级管理员
	private boolean superAdmin;

	/**
	 * 获取statusText
	 * 
	 * @return
	 */
	public String getStatusText()
	{
		return statusText;
	}

	/**
	 * 设置statusText
	 * 
	 * @param statusText
	 */
	public void setStatusText(String statusText)
	{
		this.statusText = statusText;
	}

	/**
	 * 获取roleNames
	 * 
	 * @return
	 */
	public String getRoleNames()
	{
		return roleNames;
	}

	/**
	 * 设置roleNames
	 * 
	 * @param roleNames
	 */
	public void setRoleNames(String roleNames)
	{
		this.roleNames = roleNames;
	}

	/**
	 * 获取lastAccessTimeText
	 * 
	 * @return
	 */
	public String getLastAccessTimeText()
	{
		return lastAccessTimeText;
	}

	/**
	 * 设置lastAccessTimeText
	 * 
	 * @param lastAccessTimeText
	 */
	public void setLastAccessTimeText(String lastAccessTimeText)
	{
		this.lastAccessTimeText = lastAccessTimeText;
	}

	/**
	 * 获取superAdmin
	 * 
	 * @return
	 */
	public boolean isSuperAdmin()
	{
		return superAdmin;
	}

	/**
	 * 设置superAdmin
	 * 
	 * @param superAdmin
	 */
	public void setSuperAdmin(boolean superAdmin)
	{
		this.superAdmin = superAdmin;
	}
}
