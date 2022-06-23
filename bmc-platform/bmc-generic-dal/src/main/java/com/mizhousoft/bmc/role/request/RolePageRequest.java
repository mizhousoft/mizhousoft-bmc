package com.mizhousoft.bmc.role.request;

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

	// 服务ID
	private String srvId;

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
	 * 获取srvId
	 * 
	 * @return
	 */
	public String getSrvId()
	{
		return srvId;
	}

	/**
	 * 设置srvId
	 * 
	 * @param srvId
	 */
	public void setSrvId(String srvId)
	{
		this.srvId = srvId;
	}
}
