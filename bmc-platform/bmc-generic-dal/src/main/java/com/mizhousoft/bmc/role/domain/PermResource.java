package com.mizhousoft.bmc.role.domain;

import java.io.Serializable;

/**
 * 权限资源
 *
 * @version
 */
public class PermResource implements Serializable
{
	private static final long serialVersionUID = 7086095989721728496L;

	// ID
	private int id;

	// 权限名称
	private String permName;

	// 路径
	private String path;

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

	/**
	 * 获取path
	 * 
	 * @return
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * 设置path
	 * 
	 * @param path
	 */
	public void setPath(String path)
	{
		this.path = path;
	}
}
