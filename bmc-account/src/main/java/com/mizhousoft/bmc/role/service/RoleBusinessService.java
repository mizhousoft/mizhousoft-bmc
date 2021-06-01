package com.mizhousoft.bmc.role.service;

import java.util.List;

import com.mizhousoft.bmc.role.domain.Role;

/**
 * 角色业务服务
 *
 * @version
 */
public interface RoleBusinessService
{
	/**
	 * 根据权限名称查询角色
	 * 
	 * @param permName
	 * @return
	 */
	List<Role> queryRoleByPermName(String permName);
}
