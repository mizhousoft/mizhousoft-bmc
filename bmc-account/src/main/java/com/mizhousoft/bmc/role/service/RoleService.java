package com.mizhousoft.bmc.role.service;

import java.util.List;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 角色服务接口
 * 
 * @version
 */
public interface RoleService
{
	/**
	 * 增加角色
	 * 
	 * @param role
	 * @throws BMCException
	 */
	void addRole(Role role) throws BMCException;

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @throws BMCException
	 */
	void modifyRole(Role role) throws BMCException;

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @throws BMCException
	 */
	void deleteRole(Role role) throws BMCException;

	/**
	 * 根据角色ID查找角色
	 * 
	 * @param ids
	 * @return
	 */
	List<Role> queryByIds(List<Integer> ids);

	/**
	 * 根据角色id查找角色，查找不到，抛出异常
	 * 
	 * @param id
	 * @return
	 * @throws BMCException
	 */
	Role loadById(int id) throws BMCException;

	/**
	 * 根据角色ID查找角色
	 * 
	 * @param id
	 * @return
	 */
	Role getById(int id);

	/**
	 * 分页查找角色
	 * 
	 * @param request
	 * @return
	 */
	Page<Role> queryRoles(RolePageRequest request);
}
