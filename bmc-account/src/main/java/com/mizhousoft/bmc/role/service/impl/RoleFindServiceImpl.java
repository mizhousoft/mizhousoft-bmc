package com.mizhousoft.bmc.role.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.bmc.role.service.RoleFindService;
import com.mizhousoft.bmc.role.service.RolePermissionService;

/**
 * 角色查询服务
 *
 * @version
 */
@Service
public class RoleFindServiceImpl implements RoleFindService
{
	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getRoleNamesByPath(String requestPath)
	{
		Permission perm = permissionService.queryByRequestPath(requestPath);
		if (null == perm)
		{
			return Collections.emptyList();
		}

		List<String> roleNames = queryRoleByPermName(perm.getName());
		return roleNames;
	}

	public List<String> queryRoleByPermName(String permName)
	{
		List<RolePermission> rps = rolePermissionService.queryByPermName(permName);

		List<String> roleNames = new ArrayList<String>(rps.size());
		rps.forEach(item -> roleNames.add(item.getRoleName()));

		return roleNames;
	}
}
