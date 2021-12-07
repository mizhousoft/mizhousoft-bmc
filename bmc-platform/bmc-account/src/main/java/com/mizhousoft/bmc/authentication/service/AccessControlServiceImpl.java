package com.mizhousoft.bmc.authentication.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.service.RoleCacheService;
import com.mizhousoft.boot.authentication.service.AccessControlService;

/**
 * 访问控制服务
 *
 * @version
 */
@Service
public class AccessControlServiceImpl implements AccessControlService
{
	@Autowired
	private RoleCacheService roleCacheService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getRolesByPath(String serviceId, String requestPath)
	{
		return roleCacheService.getRoleNamesByPath(serviceId, requestPath);
	}
}
