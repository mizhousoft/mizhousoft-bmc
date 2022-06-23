package com.mizhousoft.bmc.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.bmc.role.mapper.RolePermissionMapper;
import com.mizhousoft.bmc.role.service.RolePermissionService;

/**
 * 角色权限服务
 *
 * @version
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService
{
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRolePermission(RolePermission rolePermission) throws BMCException
	{
		rolePermissionMapper.save(rolePermission);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByRoleName(String srvId, String roleName) throws BMCException
	{
		rolePermissionMapper.deleteByRoleName(srvId, roleName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RolePermission> queryByRoleName(String srvId, String roleName)
	{
		return rolePermissionMapper.findByRoleName(srvId, roleName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RolePermission> queryAll()
	{
		return rolePermissionMapper.findAll();
	}
}
