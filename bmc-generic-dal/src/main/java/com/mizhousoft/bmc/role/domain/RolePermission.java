package com.mizhousoft.bmc.role.domain;

/**
 * 角色权限
 *
 * @version
 */
public class RolePermission
{
	// ID
	private int id;

	// 角色名称
	private String roleName;

	// 权限名称
	private String permName;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * 获取roleName
	 * 
	 * @return
	 */
	public String getRoleName()
	{
		return roleName;
	}

	/**
	 * 设置roleName
	 * 
	 * @param roleName
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	/**
	 * 获取permName
	 * 
	 * @return
	 */
	public String getPermName()
	{
		return permName;
	}

	/**
	 * 设置permName
	 * 
	 * @param permName
	 */
	public void setPermName(String permName)
	{
		this.permName = permName;
	}
}
