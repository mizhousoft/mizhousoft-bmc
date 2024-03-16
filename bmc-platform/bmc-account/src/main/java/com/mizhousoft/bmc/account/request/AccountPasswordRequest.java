package com.mizhousoft.bmc.account.request;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.Validator;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 帐号密码请求
 *
 * @version
 */
public class AccountPasswordRequest implements Validator
{
	// 老密码
	protected String password;

	// 新密码
	protected String newPassword;

	// 确认密码
	protected String confirmNewPassword;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Assert.notBlank("password", password, "bmc.account.password.null.error");
		Assert.size("password", password, 8, 32, "bmc.account.password.size.error");

		Assert.notBlank("newPassword", newPassword, "bmc.account.password.null.error");
		Assert.size("newPassword", newPassword, 8, 32, "bmc.account.password.size.error");

		Assert.notBlank("confirmNewPassword", confirmNewPassword, "bmc.account.confirm.password.null.error");
		Assert.size("confirmNewPassword", confirmNewPassword, 8, 32, "bmc.account.confirm.password.size.error");
	}

	/**
	 * 获取password
	 * 
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * 设置password
	 * 
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * 获取newPassword
	 * 
	 * @return
	 */
	public String getNewPassword()
	{
		return newPassword;
	}

	/**
	 * 设置newPassword
	 * 
	 * @param newPassword
	 */
	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	/**
	 * 获取confirmNewPassword
	 * 
	 * @return
	 */
	public String getConfirmNewPassword()
	{
		return confirmNewPassword;
	}

	/**
	 * 设置confirmNewPassword
	 * 
	 * @param confirmNewPassword
	 */
	public void setConfirmNewPassword(String confirmNewPassword)
	{
		this.confirmNewPassword = confirmNewPassword;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return null;
	}
}
