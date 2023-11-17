package com.mizhousoft.bmc.account.request;

import jakarta.validation.constraints.Size;

/**
 * 帐号密码请求
 *
 * @version
 */
public class AccountPasswordRequest
{
	// ID
	private long id;

	// 老密码
	@Size(min = 8, max = 32, message = "{bmc.account.password.size.error}")
	private String password;

	// 新密码
	@Size(min = 8, max = 32, message = "{bmc.account.password.size.error}")
	private String newPassword;

	// 确认密码
	@Size(min = 8, max = 32, message = "{bmc.account.confirm.password.size.error}")
	private String confirmNewPassword;

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
