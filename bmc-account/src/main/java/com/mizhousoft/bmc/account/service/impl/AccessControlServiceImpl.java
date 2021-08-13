package com.mizhousoft.bmc.account.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.service.RoleCacheService;
import com.mizhousoft.bmc.security.service.AccessControlService;

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
	public Set<String> getRolesByPath(String requestPath)
	{
		return roleCacheService.getRoleNamesByPath(requestPath);
	}
}
