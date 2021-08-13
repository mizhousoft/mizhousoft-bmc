package com.mizhousoft.bmc.account.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.bmc.role.service.RoleFindService;
import com.mizhousoft.bmc.security.service.AccessControlService;

/**
 * 访问控制服务
 *
 * @version
 */
@Service
public class AccessControlServiceImpl implements AccessControlService
{
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleFindService roleFindService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getRolesByPath(String requestPath)
	{
		Permission perm = permissionService.queryByRequestPath(requestPath);
		if (null == perm)
		{
			return Collections.emptyList();
		}

		List<Role> roles = roleFindService.queryRoleByPermName(perm.getName());

		List<String> roleNames = new ArrayList<String>();
		roles.forEach(role -> roleNames.add(role.getName()));

		return roleNames;
	}
}
