package com.mizhousoft.bmc.authentication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.boot.authentication.service.RequestPathService;

/**
 * 请求路径服务
 *
 * @version
 */
@Service(value = "RequestPathServiceImpl")
public class RequestPathServiceImpl implements RequestPathService
{
	@Autowired
	private PermissionService permissionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryAuthcRequestPaths()
	{
		return permissionService.queryAuthcRequestPaths();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryAuthzRequestPaths()
	{
		return permissionService.queryAuthzRequestPaths();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryLoginAuditRequestPaths()
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
	public List<String> queryNonUpdateAccessTimeRequestPaths()
	{
		return new ArrayList<>(0);
	}

}
