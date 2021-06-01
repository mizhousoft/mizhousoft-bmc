package com.mizhousoft.bmc.security;

import org.apache.shiro.util.Assert;

/**
 * 授权
 *
 * @version
 */
public final class SimpleGrantedAuthority implements GrantedAuthority
{
	private static final long serialVersionUID = -5537997536908023420L;

	// 角色
	private final String role;

	/**
	 * 构造函数
	 *
	 * @param role
	 */
	public SimpleGrantedAuthority(String role)
	{
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}

	/**
	 * 获取授权
	 * 
	 * @return
	 */
	@Override
	public String getAuthority()
	{
		return role;
	}

	/**
	 * equals
	 * 
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof SimpleGrantedAuthority)
		{
			return role.equals(((SimpleGrantedAuthority) obj).role);
		}

		return false;
	}

	/**
	 * hashCode
	 * 
	 * @return
	 */
	public int hashCode()
	{
		return this.role.hashCode();
	}

	/**
	 * toString
	 * 
	 * @return
	 */
	public String toString()
	{
		return this.role;
	}
}
