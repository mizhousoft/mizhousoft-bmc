package com.mizhousoft.bmc.authentication.model;

import java.util.Map;
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

	// 扩展数据
	private Map<String, Object> extendMap;

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

	/**
	 * 获取extendMap
	 * 
	 * @return
	 */
	public Map<String, Object> getExtendMap()
	{
		return extendMap;
	}

	/**
	 * 设置extendMap
	 * 
	 * @param extendMap
	 */
	public void setExtendMap(Map<String, Object> extendMap)
	{
		this.extendMap = extendMap;
	}
}
