package com.mizhousoft.bmc.account.request;

import java.util.List;

/**
 * 帐号授权请求
 *
 * @version
 */
public class AccountAuthorizeRequest
{
	// ID
	private long id;

	// 选择的角色ID
	private List<Integer> roleIds;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * 获取roleIds
	 * 
	 * @return
	 */
	public List<Integer> getRoleIds()
	{
		return roleIds;
	}

	/**
	 * 设置roleIds
	 * 
	 * @param roleIds
	 */
	public void setRoleIds(List<Integer> roleIds)
	{
		this.roleIds = roleIds;
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
		builder.append("\", \"roleIds\":\"");
		builder.append(roleIds);
		builder.append("\"}");
		return builder.toString();
	}
}
