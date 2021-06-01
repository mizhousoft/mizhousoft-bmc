package com.mizhousoft.bmc.security.service;

import java.util.List;

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
	List<String> getRolesByPath(String requestPath);
}
