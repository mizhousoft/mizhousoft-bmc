package com.mizhousoft.bmc.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.bmc.role.service.PermissionViewService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;

/**
 * 权限视图服务
 *
 * @version
 */
@Service
public class PermissionViewServiceImpl implements PermissionViewService
{
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryAuthzPermissions()
	{
		String serviceId = applicationAuthService.getServiceId();

		List<Permission> permissions = permissionService.queryAuthzPermissions(serviceId);

		return permissions;
	}
}
