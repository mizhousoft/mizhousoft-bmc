package com.mizhousoft.bmc.role.mapper;

import java.util.List;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 角色权限持久层Mapper
 *
 * @version
 */
public interface RolePermissionMapper extends CrudMapper<RolePermission, Integer>
{
	/**
	 * 根据角色名删除
	 * 
	 * @param roleName
	 */
	void deleteByRoleName(String roleName);

	/**
	 * 根据角色名称查询权限
	 * 
	 * @param roleName
	 * @return
	 */
	List<Permission> findPermissionByRoleName(String roleName);
}
