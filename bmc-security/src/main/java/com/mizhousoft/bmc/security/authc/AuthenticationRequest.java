package com.mizhousoft.bmc.security.authc;

/**
 * 认证请求
 *
 * @version
 */
public class AuthenticationRequest
{
	// 帐号
	private String account;

	// 密码
	private String password;

	// 验证码
	private String code;

	/**
	 * 获取account
	 * 
	 * @return
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * 设置account
	 * 
	 * @param account
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}

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
	 * 获取code
	 * 
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * 设置code
	 * 
	 * @param code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}
}
