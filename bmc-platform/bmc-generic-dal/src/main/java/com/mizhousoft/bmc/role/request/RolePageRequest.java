package com.mizhousoft.bmc.role.request;

import java.util.Set;

import com.mizhousoft.commons.data.domain.PageRequest;

/**
 * 分页请求
 *
 * @version
 */
public class RolePageRequest extends PageRequest
{
	private static final long serialVersionUID = 121711783936567176L;

	// 名称
	private String name;

	// 业务ID
	private Set<String> srvIds;

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
	 * 获取srvIds
	 * 
	 * @return
	 */
	public Set<String> getSrvIds()
	{
		return srvIds;
	}

	/**
	 * 设置srvIds
	 * 
	 * @param srvIds
	 */
	public void setSrvIds(Set<String> srvIds)
	{
		this.srvIds = srvIds;
	}
}
