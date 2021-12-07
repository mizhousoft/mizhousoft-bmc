package com.mizhousoft.bmc.role.service;

import java.util.Set;

/**
 * 权限开放服务
 *
 * @version
 */
public interface PermissionOpenService
{
	/**
	 * 是否有权限
	 * 
	 * @param srvId
	 * @param authorities
	 * @param permissionName
	 * @return
	 */
	boolean hasPermission(String srvId, Set<String> authorities, String permissionName);
}
