package com.mizhousoft.bmc.account.request;

import java.util.Arrays;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.Validator;
import com.mizhousoft.commons.web.util.Assert;
import com.mizhousoft.commons.web.validator.PhoneNumberValidator;

/**
 * 增加帐号请求
 *
 * @version
 */
public class AccountNewRequest implements Validator
{
	// 帐号
	private String name;

	// 状态
	private int status;

	// 密码
	private String password;

	// 确认密码
	private String confirmPassword;

	// 手机号
	private String phoneNumber;

	// 角色ID
	private Integer[] roleIds;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Assert.notBlank("name", name, "bmc.account.name.null.error");
		Assert.size("name", name, 5, 20, "bmc.account.name.size.error");
		Assert.notMatch("name", name, "^[a-zA-Z0-9]+$", "bmc.account.name.pattern.error");

		Assert.notBlank("password", password, "bmc.account.password.null.error");
		Assert.size("password", password, 8, 32, "bmc.account.password.size.error");

		Assert.notBlank("confirmPassword", confirmPassword, "bmc.account.confirm.password.null.error");
		Assert.size("confirmPassword", confirmPassword, 8, 32, "bmc.account.confirm.password.size.error");

		Assert.size("phoneNumber", phoneNumber, 11, 11, "bmc.account.phonenumber.size.error");
		PhoneNumberValidator.validate("phoneNumber", phoneNumber, "bmc.account.phonenumber.pattern.error");
	}

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
