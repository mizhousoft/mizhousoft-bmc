package com.mizhousoft.bmc.profile.request;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.Validator;
import com.mizhousoft.commons.web.util.Asserts;

/**
 * 闲时超时时间
 *
 * @version
 */
public class IdleTimeoutRequest implements Validator
{
	// 超时时间
	private int timeout;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Asserts.range(timeout, 1, 1440, "bmc.idletimeout.timeout.range.error");
	}

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
