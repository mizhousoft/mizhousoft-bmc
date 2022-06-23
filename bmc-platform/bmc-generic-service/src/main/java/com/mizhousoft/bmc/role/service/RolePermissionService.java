package com.mizhousoft.bmc.role.service;

import java.util.List;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.role.domain.RolePermission;

/**
 * 角色权限服务
 *
 * @version
 */
public interface RolePermissionService
{
	/**
	 * 增加角色权限
	 * 
	 * @param rolePermission
	 * @throws BMCException
	 */
	void addRolePermission(RolePermission rolePermission) throws BMCException;

	/**
	 * 根据角色名称删除
	 * 
	 * @param srvId
	 * @param roleName
	 * @throws BMCException
	 */
	void deleteByRoleName(String srvId, String roleName) throws BMCException;

	/**
	 * 根据角色名查询角色
	 * 
	 * @param srvId
	 * @param roleName
	 * @return
	 */
	List<RolePermission> queryByRoleName(String srvId, String roleName);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<RolePermission> queryAll();
}
