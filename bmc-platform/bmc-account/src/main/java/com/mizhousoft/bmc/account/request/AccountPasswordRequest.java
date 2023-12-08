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
	// ID
	private long id;

	// 老密码
	private String password;

	// 新密码
	private String newPassword;

	// 确认密码
	private String confirmNewPassword;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Assert.notBlank(password, "bmc.account.password.size.error", "password");
		Assert.size(password, 8, 32, "bmc.account.password.size.error", "password");

		Assert.notBlank(newPassword, "bmc.account.password.size.error", "newPassword");
		Assert.size(newPassword, 8, 32, "bmc.account.password.size.error", "newPassword");

		Assert.notBlank(confirmNewPassword, "bmc.account.confirm.password.size.error", "confirmNewPassword");
		Assert.size(confirmNewPassword, 8, 32, "bmc.account.confirm.password.size.error", "confirmNewPassword");
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
