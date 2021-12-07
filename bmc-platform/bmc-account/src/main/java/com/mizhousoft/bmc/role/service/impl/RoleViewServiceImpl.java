package com.mizhousoft.bmc.role.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.BMCRuntimeException;
import com.mizhousoft.bmc.account.service.AccountRoleSerivce;
import com.mizhousoft.bmc.role.constant.RoleType;
import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.bmc.role.event.RoleDeleteEvent;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.request.RoleRequest;
import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.bmc.role.service.RolePermissionService;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.bmc.role.service.RoleViewService;
import com.mizhousoft.boot.authentication.service.AuthenticationServiceProvider;
import com.mizhousoft.commons.crypto.generator.RandomGenerator;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 角色视图服务
 *
 * @version
 */
@Service
public class RoleViewServiceImpl implements RoleViewService
{
	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private AccountRoleSerivce accountRoleSerivce;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private AuthenticationServiceProvider authenticationServiceProvider;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized Role addRole(RoleRequest request) throws BMCException
	{
		Role role = new Role();

		String mainSrvId = authenticationServiceProvider.getMainServiceId();
		String name = RandomGenerator.genHexString(16, true);

		role.setType(RoleType.GeneralRole.getValue());
		role.setSrvId(mainSrvId);
		role.setName(name);
		role.setDisplayNameCN(request.getName());
		role.setDisplayNameUS(request.getName());
		role.setDescriptionCN(request.getDescription());

		roleService.addRole(role);

		Set<Integer> ids = new HashSet<Integer>(10);
		String[] permIds = request.getPermIds();
		for (String permId : permIds)
		{
			ids.add(Integer.parseInt(permId));
		}
		List<Permission> permissions = permissionService.queryPermissionsByIds(ids, true);

		for (Permission permission : permissions)
		{
			RolePermission rp = new RolePermission();
			rp.setRoleName(role.getName());
			rp.setPermName(permission.getName());
			rolePermissionService.addRolePermission(rp);
		}

		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized Role modifyRole(RoleRequest request) throws BMCException
	{
		Role role = roleService.loadById(request.getId());

		role.setDisplayNameCN(request.getName());
		role.setDisplayNameUS(request.getName());
		role.setDescriptionCN(request.getDescription());
		roleService.modifyRole(role);

		rolePermissionService.deleteByRoleName(role.getName());

		Set<Integer> ids = new HashSet<Integer>(10);
		String[] permIds = request.getPermIds();
		for (String permId : permIds)
		{
			ids.add(Integer.parseInt(permId));
		}

		List<Permission> permissions = permissionService.queryPermissionsByIds(ids, true);
		for (Permission perm : permissions)
		{
			RolePermission rp = new RolePermission();
			rp.setRoleName(role.getName());
			rp.setPermName(perm.getName());
			rolePermissionService.addRolePermission(rp);
		}

		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public synchronized Role deleteRole(int id) throws BMCException
	{
		Role role = roleService.deleteRole(id);
		if (null != role)
		{
			try
			{
				RoleDeleteEvent event = new RoleDeleteEvent(role);
				eventPublisher.publishEvent(event);
			}
			catch (BMCRuntimeException e)
			{
				throw new BMCException(e.getErrorCode(), e.getCodeParams(), e.getMessage(), e);
			}

			rolePermissionService.deleteByRoleName(role.getName());
			accountRoleSerivce.deleteByRoleId(role.getId());
		}

		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryPermissionsByRoleName(String roleName)
	{
		Set<String> srvIds = authenticationServiceProvider.listServiceIds();
		List<RolePermission> rolePerms = rolePermissionService.queryByRoleName(roleName);

		List<Permission> permissions = new ArrayList<>(10);
		for (RolePermission rolePerm : rolePerms)
		{
			for (String srvId : srvIds)
			{
				Permission permission = permissionService.getByName(srvId, rolePerm.getPermName());
				if (null != permission)
				{
					permissions.add(permission);
					break;
				}
			}
		}

		return permissions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Role> queryPageData(RolePageRequest request)
	{
		request.setName(StringUtils.trimToNull(request.getName()));

		Set<String> srvIds = authenticationServiceProvider.listServiceIds();
		request.setSrvIds(srvIds);

		Page<Role> page = roleService.queryPageData(request);

		return page;
	}
}
