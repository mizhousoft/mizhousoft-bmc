package com.mizhousoft.bmc.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * 认证信息
 *
 * @version
 */
public interface Authentication extends Principal, Serializable
{
	/**
	 * 获取授权
	 * 
	 * @return
	 */
	Collection<? extends GrantedAuthority> getAuthorities();

	/**
	 * 获取认证帐号
	 * 
	 * @return
	 */
	AccountDetails getPrincipal();

	/**
	 * 获取帐号ID
	 * 
	 * @return
	 */
	long getAccountId();

	/**
	 * 是否认证
	 * 
	 * @return
	 */
	boolean isAuthenticated();

	/**
	 * 获取访问的IP地址
	 * 
	 * @return
	 */
	String getAccessIPAddress();
}
