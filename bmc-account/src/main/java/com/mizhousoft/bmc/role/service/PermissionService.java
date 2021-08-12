package com.mizhousoft.bmc.role.service;

import java.util.List;
import java.util.Set;

import com.mizhousoft.bmc.role.domain.Permission;

/**
 * 权限服务接口
 * 
 * @version
 */
public interface PermissionService
{
	/**
	 * 查询仅仅要认证的请求路径
	 * 
	 * @return
	 */
	List<String> queryAuthcRequestPaths();

	/**
	 * 查询要认证和鉴权的请求路径
	 * 
	 * @return
	 */
	List<String> queryAuthzRequestPaths();

	/**
	 * 根据请求路径查询权限
	 * 
	 * @param path
	 * @return
	 */
	Permission queryByRequestPath(String path);

	/**
	 * 查询所有权限
	 * 
	 * @return
	 */
	List<Permission> queryAllPermissions();

	/**
	 * 查询要鉴权的权限
	 * 
	 * @return
	 */
	List<Permission> queryAuthzPermissions();

	/**
	 * 根据ID查询权限及父权限
	 * 
	 * @param ids
	 * @return
	 */
	List<Permission> queryPermissionsWithParentByIds(Set<Integer> ids);
}
