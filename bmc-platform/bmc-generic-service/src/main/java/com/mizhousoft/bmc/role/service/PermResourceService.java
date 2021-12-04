package com.mizhousoft.bmc.role.service;

import java.util.List;

/**
 * 权限资源服务
 *
 * @version
 */
public interface PermResourceService
{
	/**
	 * 根据权限查询
	 * 
	 * @param permission
	 * @return
	 */
	List<String> queryByPermission(String permission);

	/**
	 * 根据路径获取权限名称
	 * 
	 * @param path
	 * @return
	 */
	String getPermissionByPath(String path);
}
