package com.mizhousoft.bmc.profile.request;

import org.hibernate.validator.constraints.Range;

/**
 * 闲时超时时间
 *
 * @version
 */
public class IdleTimeoutRequest
{
	// 超时时间
	@Range(min = 1, max = 120, message = "{bmc.idletimeout.timeout.range.error}")
	private int timeout;

	/**
	 * 获取timeout
	 * 
	 * @return
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * 设置timeout
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"timeout\":\"");
		builder.append(timeout);
		builder.append("\"}");
		return builder.toString();
	}
}
