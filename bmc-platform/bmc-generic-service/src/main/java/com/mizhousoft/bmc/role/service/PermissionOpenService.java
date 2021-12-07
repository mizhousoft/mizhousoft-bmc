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
	 * @param authorities
	 * @param permissionName
	 * @return
	 */
	boolean hasPermission(Set<String> authorities, String permissionName);
}
