package com.mizhousoft.bmc.authentication.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.boot.authentication.AccountDetails;
import com.mizhousoft.boot.authentication.configuration.AuthenticationProperties;
import com.mizhousoft.boot.authentication.service.TwoFactorAuthenticationService;

/**
 * 双因子认证服务
 *
 * @version
 */
@Service
public class TwoFactorAuthenticationServiceImpl implements TwoFactorAuthenticationService
{
	private static final Logger LOG = LoggerFactory.getLogger(TwoFactorAuthenticationServiceImpl.class);

	@Autowired
	private AuthenticationProperties authenticationProperties;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean determineInternalAuthcPass(AccountDetails accountDetails, String lastAccessIpAddr, LocalDateTime lastAccessTime)
	{
		boolean authcPassed = false;

		// 未启用功能直接跳过短信认证
		if (authenticationProperties.isTwoFactorAuthcEnable())
		{
			// 跟最后一次登录访问IP地址一样，并且在7天内，可以不用短信认证
			if (null != lastAccessIpAddr && null != lastAccessTime)
			{
				LocalDateTime date = LocalDateTime.now().minusDays(7);

				if (lastAccessTime.isAfter(date) && StringUtils.equals(accountDetails.getLoginIpAddr(), lastAccessIpAddr))
				{
					authcPassed = true;
				}
				else
				{
					LOG.info(
					        "User must two-factor authentication, user id is {}, last access ip address is {}, last access time is {}, now access ip address {}.",
					        accountDetails.getAccountId(), lastAccessIpAddr, lastAccessTime, accountDetails.getLoginIpAddr());
				}
			}
			else
			{
				LOG.info("User first login system, user id is {}, last access ip address is {}, last access time is {}.",
				        accountDetails.getAccountId(), lastAccessIpAddr, lastAccessTime);
			}
		}
		else
		{
			authcPassed = true;
		}

		return authcPassed;
	}
}
