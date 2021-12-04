package com.mizhousoft.bmc.role.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.service.RoleCacheService;
import com.mizhousoft.bmc.role.service.RoleOpenService;

/**
 * TODO
 *
 * @version
 */
@Service
public class RoleOpenServiceImpl implements RoleOpenService
{
	@Autowired
	private RoleCacheService roleCacheService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(Set<String> authorities, String permissionName)
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
