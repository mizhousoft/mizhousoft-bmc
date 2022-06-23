package com.mizhousoft.bmc.role.service;

import java.util.List;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.request.RoleRequest;
import com.mizhousoft.commons.data.domain.Page;

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
	 * @param request
	 * @return
	 * @throws BMCException
	 */
	Role addRole(RoleRequest request) throws BMCException;

	/**
	 * 修改角色
	 * 
	 * @param request
	 * @return
	 * @throws BMCException
	 */
	Role modifyRole(RoleRequest request) throws BMCException;

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @throws BMCException
	 */
	Role deleteRole(int id) throws BMCException;

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @throws BMCException
	 */
	Role loadById(int id) throws BMCException;

	/**
	 * 根据角色名查询权限
	 * 
	 * @param roleName
	 * @return
	 */
	List<Permission> queryPermissionsByRoleName(String roleName);

	/**
	 * 分页查找角色
	 * 
	 * @param request
	 * @return
	 */
	Page<Role> queryPageData(RolePageRequest request);
}
