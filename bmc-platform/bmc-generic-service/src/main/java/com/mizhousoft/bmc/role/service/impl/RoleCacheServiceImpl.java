package com.mizhousoft.bmc.role.service.impl;

import java.util.Collections;
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

	// Map<srvId, Map<roleName, Set<permissionName>>
	private Map<String, Map<String, Set<String>>> srvRolePermMap = new ConcurrentHashMap<>(5);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRolePermissions(String srvId, String roleName, List<Permission> permissions)
	{
		Set<String> list = new HashSet<>(permissions.size());
		permissions.forEach(item -> list.add(item.getName()));

		Map<String, Set<String>> rolePermMap = srvRolePermMap.get(srvId);
		if (null == rolePermMap)
		{
			rolePermMap = new ConcurrentHashMap<>(5);
			srvRolePermMap.put(srvId, rolePermMap);
		}
		rolePermMap.put(roleName, list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refreshRolePermissions(String srvId, String roleName, List<Permission> permissions)
	{
		Set<String> list = new HashSet<>(permissions.size());
		permissions.forEach(item -> list.add(item.getName()));

		Map<String, Set<String>> rolePermMap = srvRolePermMap.get(srvId);
		if (null == rolePermMap)
		{
			rolePermMap = new ConcurrentHashMap<>(5);
			srvRolePermMap.put(srvId, rolePermMap);
		}
		rolePermMap.remove(roleName);
		rolePermMap.put(roleName, list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByRoleName(String srvId, String roleName)
	{
		Map<String, Set<String>> rolePermMap = srvRolePermMap.get(srvId);
		if (null != rolePermMap)
		{
			rolePermMap.remove(roleName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryPermissionByRoleName(String srvId, String roleName)
	{
		Map<String, Set<String>> rolePermMap = srvRolePermMap.get(srvId);
		if (null != rolePermMap)
		{
			Set<String> list = rolePermMap.get(roleName);

			return Collections.unmodifiableSet(SetUtils.emptyIfNull(list));
		}

		return new HashSet<>(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryRoleByPermName(String srvId, String permName)
	{
		Set<String> roleNames = new HashSet<>(4);

		Map<String, Set<String>> rolePermMap = srvRolePermMap.get(srvId);
		if (null != rolePermMap)
		{
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
		}

		return roleNames;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getRoleNamesByPath(String srvId, String requestPath)
	{
		Permission perm = permissionService.getByRequestPath(srvId, requestPath);
		if (null == perm)
		{
			return Collections.emptySet();
		}

		return queryRoleByPermName(srvId, perm.getName());
	}

	@PostConstruct
	public void initialize()
	{
		List<RolePermission> rolePermissions = rolePermissionService.queryAll();

		Map<String, Map<String, Set<String>>> srvRolePermMap = new ConcurrentHashMap<>(5);

		rolePermissions.forEach(item -> {
			Map<String, Set<String>> rolePermMap = srvRolePermMap.get(item.getSrvId());
			if (null == rolePermMap)
			{
				rolePermMap = new ConcurrentHashMap<>(5);
				srvRolePermMap.put(item.getSrvId(), rolePermMap);
			}

			Set<String> list = rolePermMap.get(item.getRoleName());
			if (null == list)
			{
				list = new HashSet<>(50);
				rolePermMap.put(item.getRoleName(), list);
			}

			list.add(item.getPermName());
		});

		srvRolePermMap.forEach((key, value) -> {
			this.srvRolePermMap.put(key, value);

			LOG.info("Load {} role permission successfully, role size is {}.", key, value.size());
		});
	}
}
