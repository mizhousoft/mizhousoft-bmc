package com.mizhousoft.bmc.role.request;

import java.util.Arrays;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 角色请求
 *
 * @version
 */
public class RoleRequest
{
	// ID
	private int id;

	// 角色名
	@Size(min = 2, max = 15, message = "{bmc.role.name.size.error}")
	@Pattern(regexp = "^[a-zA-Z0-9-\\u4e00-\\u9fa5]+$", message = "{bmc.role.name.pattern.error}")
	private String name;

	// 描述
	@Size(min = 0, max = 256, message = "{bmc.role.description.size.error}")
	private String description;

	// 权限ID
	private String[] permIds;

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
	 * 获取description
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * 设置description
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * 获取permIds
	 * 
	 * @return
	 */
	public String[] getPermIds()
	{
		return permIds;
	}

	/**
	 * 设置permIds
	 * 
	 * @param permIds
	 */
	public void setPermIds(String[] permIds)
	{
		this.permIds = permIds;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"name\":\"");
		builder.append(name);
		builder.append("\", \"description\":\"");
		builder.append(description);
		builder.append("\", \"permIds\":\"");
		builder.append(Arrays.toString(permIds));
		builder.append("\"}");
		return builder.toString();
	}
}
