package com.mizhousoft.bmc.role.service;

import java.util.List;
import java.util.Set;

import com.mizhousoft.bmc.role.domain.Permission;

/**
 * 角色缓存服务
 *
 * @version
 */
public interface RoleCacheService
{
	/**
	 * 增加角色权限
	 * 
	 * @param roleName
	 * @param permissions
	 */
	void addRolePermissions(String roleName, List<Permission> permissions);

	/**
	 * 刷新角色权限
	 * 
	 * @param roleName
	 * @param permissions
	 */
	void refreshRolePermissions(String roleName, List<Permission> permissions);

	/**
	 * 根据角色名删除
	 * 
	 * @param roleName
	 */
	void deleteByRoleName(String roleName);

	/**
	 * 根据角色名查询角色权限
	 * 
	 * @param roleName
	 * @return
	 */
	Set<String> queryPermissionByRoleName(String roleName);

	/**
	 * 根据权限名查询角色权限
	 * 
	 * @param permName
	 * @return
	 */
	Set<String> queryRoleByPermName(String permName);

	/**
	 * 根据请求路径获取角色
	 * 
	 * @param requestPath
	 * @return
	 */
	Set<String> getRoleNamesByPath(String requestPath);
}
