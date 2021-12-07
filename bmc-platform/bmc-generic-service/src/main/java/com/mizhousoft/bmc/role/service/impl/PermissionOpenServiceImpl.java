package com.mizhousoft.bmc.role.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.service.PermissionOpenService;
import com.mizhousoft.bmc.role.service.RoleCacheService;

/**
 * 权限开放服务
 *
 * @version
 */
@Service
public class PermissionOpenServiceImpl implements PermissionOpenService
{
	@Autowired
	private RoleCacheService roleCacheService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(String srvId, Set<String> authorities, String permissionName)
	{
		Set<String> roleNames = roleCacheService.queryRoleByPermName(permissionName);
		for (String roleName : roleNames)
		{
			if (authorities.contains(roleName))
			{
				return true;
			}
		}

		return false;
	}
}
