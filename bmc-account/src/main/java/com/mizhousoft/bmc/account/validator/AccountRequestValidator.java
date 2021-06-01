package com.mizhousoft.bmc.account.validator;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.constant.AccountStatus;
import com.mizhousoft.bmc.account.request.AccountNewRequest;

/**
 * 帐号请求校验器
 *
 * @version
 */
public abstract class AccountRequestValidator
{
	/**
	 * 校验
	 * 
	 * @param request
	 * @throws BMCException
	 */
	public static void validate(AccountNewRequest request) throws BMCException
	{
		String accountName = request.getName();
		String password = request.getPassword();
		if (password.contains(accountName) || password.contains(StringUtils.reverse(accountName)))
		{
			throw new BMCException("bmc.account.password.contain.name.error", "Password contains account or reverse account.");
		}

		int status = request.getStatus();
		if (status != AccountStatus.Enable.getValue() && status != AccountStatus.Disable.getValue())
		{
			throw new BMCException("bmc.account.status.error", "status is " + status + '.');
		}

		if (!password.equals(request.getConfirmPassword()))
		{
			throw new BMCException("bmc.account.password.notequals.error", "Account password is not the same with confirm password.");
		}
	}
}
