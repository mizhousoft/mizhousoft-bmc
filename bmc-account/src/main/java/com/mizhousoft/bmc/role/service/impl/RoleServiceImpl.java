package com.mizhousoft.bmc.role.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
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
import com.mizhousoft.bmc.role.mapper.RoleMapper;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.service.RolePermissionService;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;

/**
 * 角色服务实现
 * 
 * @version
 */
@Service
public class RoleServiceImpl implements RoleService
{
	// 角色持久层业务接口
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private AccountRoleSerivce accountRoleSerivce;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void addRole(Role role, List<Permission> perms) throws BMCException
	{
		List<Role> dbRoles = roleMapper.findByName(role.getDisplayNameCN());
		if (dbRoles.size() > 0)
		{
			throw new BMCException("bmc.role.name.exist.error", "Role does exist, name is " + role.getName() + ".");
		}

		roleMapper.save(role);

		for (Permission perm : perms)
		{
			RolePermission rp = new RolePermission();
			rp.setRoleName(role.getName());
			rp.setPermName(perm.getName());
			rolePermissionService.addRolePermission(rp);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void modifyRole(Role role, List<Permission> perms) throws BMCException
	{
		List<Role> dbRoles = roleMapper.findByName(role.getDisplayNameCN());
		for (Role dbRole : dbRoles)
		{
			if (dbRole.getId() == role.getId())
			{
				continue;
			}

			throw new BMCException("bmc.role.name.exist.error", "Role does exist, name is " + role.getName() + ".");
		}

		roleMapper.update(role);

		rolePermissionService.deleteByRoleName(role.getName());

		for (Permission perm : perms)
		{
			RolePermission rp = new RolePermission();
			rp.setRoleName(role.getName());
			rp.setPermName(perm.getName());
			rolePermissionService.addRolePermission(rp);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void deleteRole(Role role) throws BMCException
	{
		if (role.getType() == RoleType.AdministratorRole.getValue())
		{
			throw new BMCException("bmc.role.administrator.can.not.delete.error", "Administrator Role can not delete.");
		}

		try
		{
			RoleDeleteEvent event = new RoleDeleteEvent(role);
			eventPublisher.publishEvent(event);
		}
		catch (BMCRuntimeException e)
		{
			throw new BMCException(e.getErrorCode(), e.getCodeParams(), e.getMessage(), e);
		}

		roleMapper.delete(role.getId());

		rolePermissionService.deleteByRoleName(role.getName());

		accountRoleSerivce.deleteByRoleId(role.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> queryByIds(List<Integer> ids)
	{
		if (CollectionUtils.isEmpty(ids))
		{
			return Collections.emptyList();
		}

		Set<Integer> idset = new HashSet<>(ids);

		List<Role> roles = new ArrayList<Role>(idset.size());
		idset.forEach(id -> {
			Role role = getById(id);
			if (null != role)
			{
				roles.add(role);
			}
		});

		return roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role loadById(int id) throws BMCException
	{
		Role role = getById(id);
		if (null == role)
		{
			throw new BMCException("bmc.role.not.exist.error", "Role does not exist, id is " + id + ".");
		}

		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getById(int id)
	{
		return roleMapper.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getByRoleName(String name)
	{
		return roleMapper.findOneByName(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Role> queryRoles(RolePageRequest request)
	{
		long total = roleMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<Role> roles = roleMapper.findPageData(rowOffset, request);
		Page<Role> page = PageBuilder.build(roles, request, total);

		return page;
	}
}
