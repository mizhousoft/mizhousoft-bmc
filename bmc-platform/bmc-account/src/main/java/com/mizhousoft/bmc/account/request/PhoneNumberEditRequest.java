package com.mizhousoft.bmc.account.request;

import com.mizhousoft.commons.lang.PhoneNumberChecker;
import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.Validator;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 手机号编辑请求
 *
 * @version
 */
public class PhoneNumberEditRequest implements Validator
{
	// 手机号
	private String phoneNumber;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Assert.size("phoneNumber", phoneNumber, 11, 11, "bmc.account.phonenumber.size.error");
		Assert.notMatch("phoneNumber", phoneNumber, PhoneNumberChecker.PHONE_REGEX, "bmc.account.phonenumber.pattern.error");
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
