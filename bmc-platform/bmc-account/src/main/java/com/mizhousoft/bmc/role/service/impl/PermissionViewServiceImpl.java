package com.mizhousoft.bmc.role.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.bmc.role.service.PermissionViewService;
import com.mizhousoft.boot.authentication.service.AuthenticationServiceProvider;

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
	private AuthenticationServiceProvider authenticationServiceProvider;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryAuthzPermissions()
	{
		Set<String> srvIds = authenticationServiceProvider.listServiceIds();

		List<Permission> permissions = new ArrayList<>(10);
		for (String srvId : srvIds)
		{
			List<Permission> list = permissionService.queryAuthzPermissions(srvId);
			permissions.addAll(list);
		}

		return permissions;
	}

}
