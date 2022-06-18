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
	 * @param srvId
	 * @param permission
	 * @return
	 */
	List<String> queryByPermission(String srvId, String permission);

	/**
	 * 根据路径获取权限名称
	 * 
	 * @param srvId
	 * @param path
	 * @return
	 */
	String getPermissionByPath(String srvId, String path);
}
