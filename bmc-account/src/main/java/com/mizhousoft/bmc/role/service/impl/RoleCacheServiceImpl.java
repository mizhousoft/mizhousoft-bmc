package com.mizhousoft.bmc.role.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.RolePermission;
import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.bmc.role.service.RoleCacheService;
import com.mizhousoft.bmc.role.service.RolePermissionService;

/**
 * 角色缓存服务
 *
 * @version
 */
@Service
public class RoleCacheServiceImpl implements RoleCacheService
{
	private static final Logger LOG = LoggerFactory.getLogger(RoleCacheServiceImpl.class);

	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private PermissionService permissionService;

	// Map<Role Name, List<Permission Name>>
	private Map<String, Set<String>> rolePermMap = new ConcurrentHashMap<>(5);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRolePermissions(String roleName, List<Permission> permissions)
	{
		Set<String> list = new HashSet<>(permissions.size());
		permissions.forEach(item -> list.add(item.getName()));

		rolePermMap.put(roleName, list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refreshRolePermissions(String roleName, List<Permission> permissions)
	{
		Set<String> list = new HashSet<>(permissions.size());
		permissions.forEach(item -> list.add(item.getName()));

		rolePermMap.remove(roleName);
		rolePermMap.put(roleName, list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByRoleName(String roleName)
	{
		rolePermMap.remove(roleName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryPermissionByRoleName(String roleName)
	{
		Set<String> list = rolePermMap.get(roleName);

		return Collections.unmodifiableSet(SetUtils.emptyIfNull(list));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryRoleByPermName(String permName)
	{
		Set<String> roleNames = new HashSet<>(4);

		Iterator<Entry<String, Set<String>>> iter = rolePermMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<String, Set<String>> entry = iter.next();
			Set<String> permNames = entry.getValue();

			if (permNames.contains(permName))
			{
				roleNames.add(entry.getKey());
			}
		}

		return roleNames;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getRoleNamesByPath(String requestPath)
	{
		Permission perm = permissionService.queryByRequestPath(requestPath);
		if (null == perm)
		{
			return Collections.emptySet();
		}

		Set<String> list = queryRoleByPermName(perm.getName());
		return list;
	}

	@PostConstruct
	public void initialize()
	{
		List<RolePermission> rolePermissions = rolePermissionService.queryAll();

		Map<String, Set<String>> rolePermMap = new HashMap<>(5);
		rolePermissions.forEach(item -> {
			Set<String> list = rolePermMap.get(item.getRoleName());
			if (null == list)
			{
				list = new HashSet<>(50);
				rolePermMap.put(item.getRoleName(), list);
			}

			list.add(item.getPermName());
		});

		rolePermMap.forEach((key, value) -> {
			this.rolePermMap.put(key, value);
		});

		LOG.info("Load role size is {}.", rolePermMap.size());
	}
}
