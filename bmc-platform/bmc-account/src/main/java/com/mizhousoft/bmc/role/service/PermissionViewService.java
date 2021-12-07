package com.mizhousoft.bmc.role.service;

import java.util.List;

import com.mizhousoft.bmc.role.domain.Permission;

/**
 * 权限视图服务
 *
 * @version
 */
public interface PermissionViewService
{
	/**
	 * 查询要鉴权的权限
	 * 
	 * @param srvId
	 * @return
	 */
	List<Permission> queryAuthzPermissions();
}
