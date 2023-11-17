package com.mizhousoft.bmc.account.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 手机号编辑请求
 *
 * @version
 */
public class PhoneNumberEditRequest
{
	// 手机号
	@Pattern(regexp = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$", message = "{bmc.account.phonenumber.pattern.error}")
	@Size(min = 11, max = 11, message = "{bmc.account.phonenumber.size.error}")
	private String phoneNumber;

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
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"phoneNumber\":\"");
		builder.append(phoneNumber);
		builder.append("\"}");
		return builder.toString();
	}
}
