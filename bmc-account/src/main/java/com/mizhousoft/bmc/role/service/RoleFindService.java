package com.mizhousoft.bmc.role.service;

import java.util.List;

/**
 * 角色查询服务
 *
 * @version
 */
public interface RoleFindService
{
	/**
	 * 根据请求路径获取角色
	 * 
	 * @param requestPath
	 * @return
	 */
	List<String> getRoleNamesByPath(String requestPath);
}
