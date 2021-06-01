package com.mizhousoft.bmc.security.service;

import java.util.Date;

import com.mizhousoft.bmc.security.AccountDetails;

/**
 * 双因子认证服务
 *
 * @version
 */
public interface TwoFactorAuthenticationService
{
	/**
	 * 是否启用服务
	 * 
	 * @return
	 */
	boolean isEnable();

	/**
	 * 决策是否内部认证通过
	 * 
	 * @param accountDetails
	 * @param lastAccessIpAddr
	 * @param lastAccessTime
	 * @return
	 */
	boolean determineInternalAuthcPass(AccountDetails accountDetails, String lastAccessIpAddr, Date lastAccessTime);
}
