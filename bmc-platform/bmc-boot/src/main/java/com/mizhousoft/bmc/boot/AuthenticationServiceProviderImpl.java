package com.mizhousoft.bmc.boot;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mizhousoft.boot.authentication.service.AuthenticationServiceProvider;

/**
 * 应用服务提供者
 *
 * @version
 */
@Service
public class AuthenticationServiceProviderImpl implements AuthenticationServiceProvider
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMainServiceId()
	{
		return "BMC";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> listServiceIds()
	{
		Set<String> list = new HashSet<>(1);
		list.add("BMC");

		return list;
	}

}
