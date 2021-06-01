package com.mizhousoft.bmc.security;

import java.util.Collection;

/**
 * 帐号认证
 *
 * @version
 */
public class AccountAuthentication implements Authentication
{
	private static final long serialVersionUID = -3872720959509253551L;

	// 认证帐号
	private final AccountDetails principal;

	// 是否认证
	private boolean authenticated;

	// 访问IP地址
	private String accessIPAddress;

	/**
	 * 构造函数
	 *
	 * @param principal
	 * @param authorities
	 */
	public AccountAuthentication(AccountDetails principal)
	{
		this.principal = principal;
		setAuthenticated(true); // must use super, as we override
	}

	/**
	 * 获取认证帐号
	 * 
	 * @return
	 */
	@Override
	public AccountDetails getPrincipal()
	{
		return this.principal;
	}

	/**
	 * 获取帐号ID
	 * 
	 * @return
	 */
	@Override
	public long getAccountId()
	{
		return this.principal.getAccountId();
	}

	/**
	 * 是否认证
	 * 
	 * @return
	 */
	@Override
	public boolean isAuthenticated()
	{
		return authenticated;
	}

	/**
	 * 设置认证
	 * 
	 * @param authenticated
	 */
	public void setAuthenticated(boolean authenticated)
	{
		this.authenticated = authenticated;
	}

	/**
	 * 获取访问IP地址
	 * 
	 * @return
	 */
	@Override
	public String getAccessIPAddress()
	{
		return accessIPAddress;
	}

	/**
	 * 设置访问IP地址
	 * 
	 * @param ipAddress
	 */
	public void setAccessIPAddress(String ipAddress)
	{
		this.accessIPAddress = ipAddress;
	}

	/**
	 * 获取认证帐号名字
	 * 
	 * @return
	 */
	@Override
	public String getName()
	{
		return this.principal.getAccountName();
	}

	/**
	 * 获取授权
	 * 
	 * @return
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.principal.getAuthorities();
	}
}
