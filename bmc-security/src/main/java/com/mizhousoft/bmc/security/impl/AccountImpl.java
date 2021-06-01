package com.mizhousoft.bmc.security.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.mizhousoft.bmc.security.AccountDetails;
import com.mizhousoft.bmc.security.GrantedAuthority;
import com.mizhousoft.bmc.security.SecurityConstants;

/**
 * 帐号
 *
 * @version
 */
public class AccountImpl implements AccountDetails
{
	private static final long serialVersionUID = -8217716359571352682L;

	// 帐号ID
	private long accountId;

	// 帐号名
	private String accountName;

	// 授权
	private Set<GrantedAuthority> authorities;

	// 是否超级管理员
	private boolean superAdmin;

	// 凭证是否过期
	private boolean credentialsExpired;

	// 是否提醒修改密码
	private boolean remindModifyPasswd;

	// 是否第一次登录
	private boolean firstLogin;

	// 双因子认证是否已通过
	private boolean twoFactorAuthcPassed;

	// 登录IP地址
	private String loginIpAddr;

	// Session闲时超时时间，单位是分钟
	private int sessionIdleTimeout = SecurityConstants.DEFAULT_SESSION_IDLE_TIMEOUT;

	// 扩展对象
	private Map<String, Object> extendMap = new ConcurrentHashMap<>(10);

	/**
	 * 获取accountId
	 * 
	 * @return
	 */
	@Override
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
	 * 获取帐号名
	 * 
	 * @return
	 */
	@Override
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * 设置accountName
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	/**
	 * 设置authorities
	 * 
	 * @param authorities
	 */
	public void setAuthorities(Set<GrantedAuthority> authorities)
	{
		if (authorities == null)
		{
			authorities = Collections.emptySet();
		}

		this.authorities = Collections.unmodifiableSet(authorities);
	}

	/**
	 * 获取授权
	 * 
	 * @return
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{
		return authorities;
	}

	/**
	 * 获取firstLogin
	 * 
	 * @return
	 */
	@Override
	public boolean isFirstLogin()
	{
		return firstLogin;
	}

	/**
	 * 设置是否第一次登录
	 * 
	 * @param isFirstLogin
	 */
	public void setFirstLogin(boolean isFirstLogin)
	{
		firstLogin = isFirstLogin;
	}

	/**
	 * 获取twoFactorAuthcPassed
	 * 
	 * @return
	 */
	public boolean isTwoFactorAuthcPassed()
	{
		return twoFactorAuthcPassed;
	}

	/**
	 * 设置twoFactorAuthcPassed
	 * 
	 * @param twoFactorAuthcPassed
	 */
	public void setTwoFactorAuthcPassed(boolean twoFactorAuthcPassed)
	{
		this.twoFactorAuthcPassed = twoFactorAuthcPassed;
	}

	/**
	 * 获取superAdmin
	 * 
	 * @return
	 */
	@Override
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

	/**
	 * 凭证是否过期
	 * 
	 * @return
	 */
	@Override
	public boolean isCredentialsExpired()
	{
		return credentialsExpired;
	}

	/**
	 * 设置凭证
	 * 
	 * @param credentialsExpired
	 */
	public void setCredentialsExpired(boolean credentialsExpired)
	{
		this.credentialsExpired = credentialsExpired;
	}

	/**
	 * 是否提醒修改密码
	 * 
	 * @return
	 */
	@Override
	public boolean isRemindModifyPasswd()
	{
		return remindModifyPasswd;
	}

	/**
	 * 设置remindModifyPasswd
	 * 
	 * @param remindModifyPasswd
	 */
	public void setRemindModifyPasswd(boolean remindModifyPasswd)
	{
		this.remindModifyPasswd = remindModifyPasswd;
	}

	/**
	 * 获取loginIpAddr
	 * 
	 * @return
	 */
	@Override
	public String getLoginIpAddr()
	{
		return loginIpAddr;
	}

	/**
	 * 设置loginIpAddr
	 * 
	 * @param loginIpAddr
	 */
	public void setLoginIpAddr(String loginIpAddr)
	{
		this.loginIpAddr = loginIpAddr;
	}

	/**
	 * 获取sessionIdleTimeout
	 * 
	 * @return
	 */
	@Override
	public int getSessionIdleTimeout()
	{
		return sessionIdleTimeout;
	}

	/**
	 * 设置sessionIdleTimeout
	 * 
	 * @param sessionIdleTimeout
	 */
	public void setSessionIdleTimeout(int sessionIdleTimeout)
	{
		this.sessionIdleTimeout = sessionIdleTimeout;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putExtend(String key, Object value)
	{
		extendMap.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getExtend(String key, Class<T> clazz)
	{
		Object value = extendMap.get(key);
		if (null == value)
		{
			return null;
		}
		else if (clazz.isInstance(value))
		{
			return clazz.cast(value);
		}

		throw new IllegalArgumentException(clazz.getName() + " is error, value class is " + value.getClass().getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeExtend(String key)
	{
		extendMap.remove(key);
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		return result;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		if (getClass() != obj.getClass())
		{
			return false;
		}

		AccountImpl other = (AccountImpl) obj;
		if (accountName == null)
		{
			if (other.accountName != null)
			{
				return false;
			}
		}
		else if (!accountName.equals(other.accountName))
		{
			return false;
		}

		return true;
	}
}
