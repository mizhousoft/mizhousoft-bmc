package com.mizhousoft.bmc.account.request;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 帐号密码请求
 *
 * @version
 */
public class AccountPasswordResetRequest extends AccountPasswordRequest
{
	// ID
	private long id;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Assert.notBlank("newPassword", newPassword, "bmc.account.password.null.error");
		Assert.size("newPassword", newPassword, 8, 32, "bmc.account.password.size.error");

		Assert.notBlank("confirmNewPassword", confirmNewPassword, "bmc.account.confirm.password.null.error");
		Assert.size("confirmNewPassword", confirmNewPassword, 8, 32, "bmc.account.confirm.password.size.error");
	}

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
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\"}");
		return builder.toString();
	}
}
