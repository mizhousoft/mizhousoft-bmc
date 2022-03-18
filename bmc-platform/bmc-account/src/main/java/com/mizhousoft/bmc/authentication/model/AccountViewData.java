package com.mizhousoft.bmc.authentication.model;

import java.util.Set;

/**
 * 帐号视图数据
 *
 * @version
 */
public class AccountViewData
{
	// 姓名
	private String name;

	// 权限
	private Set<String> permissions;

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
	 * 获取permissions
	 * 
	 * @return
	 */
	public Set<String> getPermissions()
	{
		return permissions;
	}

	/**
	 * 设置permissions
	 * 
	 * @param permissions
	 */
	public void setPermissions(Set<String> permissions)
	{
		this.permissions = permissions;
	}
}
