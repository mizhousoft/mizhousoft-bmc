package com.mizhousoft.bmc.account.request;

import java.util.Arrays;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 增加帐号请求
 *
 * @version
 */
public class AccountNewRequest
{
	// 帐号
	@Size(min = 5, max = 20, message = "{bmc.account.name.size.error}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{bmc.account.name.pattern.error}")
	private String name;

	// 状态
	private int status;

	// 密码
	@Size(min = 8, max = 32, message = "{bmc.account.password.size.error}")
	private String password;

	// 确认密码
	@Size(min = 8, max = 32, message = "{bmc.account.confirm.password.size.error}")
	private String confirmPassword;

	// 手机号
	@Pattern(regexp = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$", message = "{bmc.account.phonenumber.pattern.error}")
	@Size(min = 11, max = 11, message = "{bmc.account.phonenumber.size.error}")
	private String phoneNumber;

	// 角色ID
	private Integer[] roleIds;

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
	 * 获取confirmPassword
	 * 
	 * @return
	 */
	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	/**
	 * 设置confirmPassword
	 * 
	 * @param confirmPassword
	 */
	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	/**
	 * 获取phoneNumber
	 * 
	 * @return
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * 设置phoneNumber
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 获取roleIds
	 * 
	 * @return
	 */
	public Integer[] getRoleIds()
	{
		return roleIds;
	}

	/**
	 * 设置roleIds
	 * 
	 * @param roleIds
	 */
	public void setRoleIds(Integer[] roleIds)
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
		builder.append("{\"name\":\"");
		builder.append(name);
		builder.append("\", \"status\":\"");
		builder.append(status);
		builder.append("\", \"phoneNumber\":\"");
		builder.append(phoneNumber);
		builder.append("\", \"roleIds\":\"");
		builder.append(Arrays.toString(roleIds));
		builder.append("\"}");
		return builder.toString();
	}
}
