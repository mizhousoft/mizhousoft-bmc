package com.mizhousoft.bmc.account.request;

import com.mizhousoft.commons.data.domain.PageRequest;

/**
 * 帐号分页请求
 *
 * @version
 */
public class AccountPageRequest extends PageRequest
{
	private static final long serialVersionUID = 1160261339661491171L;

	// 服务ID
	private String srvId;

	// 状态
	private int status;

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

	/**
	 * 获取status
	 * 
	 * @return
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * 设置status
	 * 
	 * @param status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}
}
