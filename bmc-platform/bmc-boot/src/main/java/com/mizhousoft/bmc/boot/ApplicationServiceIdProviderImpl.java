package com.mizhousoft.bmc.boot;

import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.ApplicationConstants;
import com.mizhousoft.boot.authentication.service.ApplicationServiceIdProvider;

/**
 * 应用服务提供者
 *
 * @version
 */
@Service
public class ApplicationServiceIdProviderImpl implements ApplicationServiceIdProvider
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
