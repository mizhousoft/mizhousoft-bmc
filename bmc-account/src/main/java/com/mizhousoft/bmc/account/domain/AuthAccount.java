package com.mizhousoft.bmc.account.domain;

/**
 * 认证的帐号
 *
 * @version
 */
public class AuthAccount extends Account
{
	// 密码
	private String password;

	// 盐值
	private String salt;

	/**
	 * 获取password
	 * 
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * 设置password
	 * 
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * 获取salt
	 * 
	 * @return
	 */
	public String getSalt()
	{
		return salt;
	}

	/**
	 * 设置salt
	 * 
	 * @param salt
	 */
	public void setSalt(String salt)
	{
		this.salt = salt;
	}
}
