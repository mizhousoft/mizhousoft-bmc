package com.mizhousoft.bmc.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.bmc.role.service.RoleBusinessService;
import com.mizhousoft.bmc.role.service.RolePermissionService;
import com.mizhousoft.bmc.role.service.RoleService;

/**
 * 角色业务服务
 *
 * @version
 */
@Service
public class RoleBusinessServiceImpl implements RoleBusinessService
{
	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private RoleService roleService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> queryRoleByPermName(String permName)
	{
		List<RolePermission> rps = rolePermissionService.queryByPermName(permName);

		List<Role> roles = new ArrayList<Role>(rps.size());
		for (RolePermission rp : rps)
		{
			Role role = roleService.getByRoleName(rp.getRoleName());
			if (null != role)
			{
				roles.add(role);
			}
		}

		return roles;
	}
}
