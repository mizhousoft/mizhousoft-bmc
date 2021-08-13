package com.mizhousoft.bmc.security.service;

import java.util.Set;

/**
 * 访问控制服务
 *
 * @version
 */
public interface AccessControlService
{
	/**
	 * 根据请求路径获取角色
	 * 
	 * @param requestPath
	 * @return
	 */
	Set<String> getRolesByPath(String requestPath);
}
