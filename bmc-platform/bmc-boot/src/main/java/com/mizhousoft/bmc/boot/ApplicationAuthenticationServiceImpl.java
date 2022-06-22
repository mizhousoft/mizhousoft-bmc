package com.mizhousoft.bmc.boot;

import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.ApplicationConstants;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;

/**
 * 应用认证服务
 *
 * @version
 */
@Service
public class ApplicationAuthenticationServiceImpl implements ApplicationAuthenticationService
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getServiceId()
	{
		return ApplicationConstants.SERVICE_ID;
	}
}
