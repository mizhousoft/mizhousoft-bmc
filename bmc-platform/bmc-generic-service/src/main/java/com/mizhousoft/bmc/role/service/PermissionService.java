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
	 * @param srvId
	 * @return
	 */
	List<String> queryAuthcRequestPaths(String srvId);

	/**
	 * 查询要认证和鉴权的请求路径
	 * 
	 * @param srvId
	 * @return
	 */
	List<String> queryAuthzRequestPaths(String srvId);

	/**
	 * 根据请求路径查询权限
	 * 
	 * @param srvId
	 * @param path
	 * @return
	 */
	Permission getByRequestPath(String srvId, String path);

	/**
	 * 根据名称查询权限
	 * 
	 * @param srvId
	 * @param name
	 * @return
	 */
	Permission getByName(String srvId, String name);

	/**
	 * 查询要鉴权的权限
	 * 
	 * @param srvId
	 * @return
	 */
	List<Permission> queryAuthzPermissions(String srvId);

	/**
	 * 根据ID查询权限及父权限
	 * 
	 * @param ids
	 * @param withParent
	 * @return
	 */
	List<Permission> queryPermissionsByIds(Set<Integer> ids, boolean withParent);
}
