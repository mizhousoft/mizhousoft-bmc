package com.mizhousoft.bmc.role.service;

import java.util.List;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.Role;

/**
 * 角色查询服务
 *
 * @version
 */
public interface RoleViewService
{
	/**
	 * 增加角色
	 * 
	 * @param role
	 * @param perms
	 * @throws BMCException
	 */
	void addRole(Role role, List<Permission> perms) throws BMCException;

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @param perms
	 * @throws BMCException
	 */
	void modifyRole(Role role, List<Permission> perms) throws BMCException;

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @throws BMCException
	 */
	Role deleteRole(int id) throws BMCException;
}
