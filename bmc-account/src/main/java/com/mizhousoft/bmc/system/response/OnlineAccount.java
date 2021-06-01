package com.mizhousoft.bmc.system.response;

/**
 * 在线帐号
 *
 * @version
 */
public class OnlineAccount
{
	// ID
	private long id;

	// 帐号名
	private String name;

	// 是否当前帐号
	private boolean currentAccount;

	// IP地址
	private String ipAddress;

	// 登录时间
	private String loginTime;

	// 角色
	private String role;

	// Random ID
	private String randomId;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * 获取name
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 设置name
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 获取currentAccount
	 * 
	 * @return
	 */
	public boolean isCurrentAccount()
	{
		return currentAccount;
	}

	/**
	 * 设置currentAccount
	 * 
	 * @param currentAccount
	 */
	public void setCurrentAccount(boolean currentAccount)
	{
		this.currentAccount = currentAccount;
	}

	/**
	 * 获取ipAddress
	 * 
	 * @return
	 */
	public String getIpAddress()
	{
		return ipAddress;
	}

	/**
	 * 设置ipAddress
	 * 
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	/**
	 * 获取loginTime
	 * 
	 * @return
	 */
	public String getLoginTime()
	{
		return loginTime;
	}

	/**
	 * 设置loginTime
	 * 
	 * @param loginTime
	 */
	public void setLoginTime(String loginTime)
	{
		this.loginTime = loginTime;
	}

	/**
	 * 获取role
	 * 
	 * @return
	 */
	public String getRole()
	{
		return role;
	}

	/**
	 * 设置role
	 * 
	 * @param role
	 */
	public void setRole(String role)
	{
		this.role = role;
	}

	/**
	 * 获取randomId
	 * 
	 * @return
	 */
	public String getRandomId()
	{
		return randomId;
	}

	/**
	 * 设置randomId
	 * 
	 * @param randomId
	 */
	public void setRandomId(String randomId)
	{
		this.randomId = randomId;
	}
}
