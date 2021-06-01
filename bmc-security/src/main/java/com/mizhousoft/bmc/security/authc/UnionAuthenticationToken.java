package com.mizhousoft.bmc.security.authc;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 统一认证Token
 *
 * @version
 */
public class UnionAuthenticationToken extends UsernamePasswordToken
{
	private static final long serialVersionUID = -5248728596513942400L;

	// 验证码
	private String code;

	/**
	 * 构造函数
	 *
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param host
	 */
	public UnionAuthenticationToken(String username, char[] password, boolean rememberMe, String host)
	{
		super(username, password, rememberMe, host);
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
