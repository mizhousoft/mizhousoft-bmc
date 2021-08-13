package com.mizhousoft.bmc.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.service.RoleViewService;
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
	private RoleViewService roleViewService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getRolesByPath(String requestPath)
	{
		return roleViewService.getRoleNamesByPath(requestPath);
	}
}
