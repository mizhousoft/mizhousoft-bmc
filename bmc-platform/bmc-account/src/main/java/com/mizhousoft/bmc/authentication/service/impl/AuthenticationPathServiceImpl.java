package com.mizhousoft.bmc.authentication.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.boot.authentication.service.AuthenticationPathService;

/**
 * 请求路径服务
 *
 * @version
 */
@Service
public class AuthenticationPathServiceImpl implements AuthenticationPathService
{
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getAuthcRequestPaths()
	{
		String serviceId = applicationAuthService.getServiceId();

		return permissionService.queryAuthcRequestPaths(serviceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getAuthzRequestPaths()
	{
		String serviceId = applicationAuthService.getServiceId();

		return permissionService.queryAuthzRequestPaths(serviceId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getLoginRequestPaths()
	{
		List<String> requestPaths = new ArrayList<>(10);

		requestPaths.add("/setting/password/modifyFirstLoginPassword.action");
		requestPaths.add("/setting/password/modifyExpiredPassword.action");
		requestPaths.add("/setting/password/fetchPasswordExpiringDays.action");
		requestPaths.add("/setting/password/modifyExpiringPassword.action");
		requestPaths.add("/unauthorized.action");

		return requestPaths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getNonUpdateAccessTimeRequestPaths()
	{
		return new ArrayList<>(0);
	}

}
