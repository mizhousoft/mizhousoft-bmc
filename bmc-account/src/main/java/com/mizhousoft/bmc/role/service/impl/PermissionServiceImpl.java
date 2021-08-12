package com.mizhousoft.bmc.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.mapper.PermissionMapper;
import com.mizhousoft.bmc.role.service.PermResourceService;
import com.mizhousoft.bmc.role.service.PermissionService;

/**
 * 权限服务实现
 * 
 * @version
 */
@Service
@Order(1)
public class PermissionServiceImpl implements PermissionService, CommandLineRunner
{
	private static final Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class);

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private PermResourceService permResourceService;

	// Map<Permission Name, Permission>
	private Map<String, Permission> permissionMap = new HashMap<>(0);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryAuthcRequestPaths()
	{
		List<String> authcPaths = new ArrayList<String>(10);

		List<Permission> allPerms = queryAllPermissions();
		allPerms.forEach(perm -> {
			if (perm.isAuthz())
			{
				return;
			}

			List<String> paths = permResourceService.queryByPermission(perm.getName());
			if (CollectionUtils.isNotEmpty(paths))
			{
				paths.forEach(path -> {
					authcPaths.add(path);
				});
			}
		});

		return authcPaths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryAuthzRequestPaths()
	{
		List<String> authzPaths = new ArrayList<String>(10);

		List<Permission> allPerms = queryAllPermissions();
		allPerms.forEach(perm -> {
			if (!perm.isAuthz())
			{
				return;
			}

			List<String> paths = permResourceService.queryByPermission(perm.getName());
			if (CollectionUtils.isNotEmpty(paths))
			{
				paths.forEach(path -> {
					authzPaths.add(path);
				});
			}
		});

		return authzPaths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Permission queryByRequestPath(String path)
	{
		String permName = permResourceService.getPermissionByPath(path);
		if (null == permName)
		{
			return null;
		}

		return permissionMap.get(permName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryAllPermissions()
	{
		List<Permission> list = new ArrayList<>(permissionMap.values());

		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryAuthzPermissions()
	{
		List<Permission> permissions = queryAllPermissions();
		List<Permission> authzPerms = permissions.stream().filter(perm -> perm.isAuthz()).collect(Collectors.toList());

		return authzPerms;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryPermissionsWithParentByIds(Set<Integer> ids)
	{
		List<Permission> permissions = queryAllPermissions();

		Map<Integer, Permission> permMap = new HashMap<Integer, Permission>(10);

		for (Integer id : ids)
		{
			Permission perm = permissions.stream().filter(p -> p.getId() == id).findFirst().get();
			if (null == perm)
			{
				continue;
			}

			permMap.put(perm.getId(), perm);

			if (null != perm.getParentName())
			{
				Set<Permission> parents = recursiveCollectParents(perm, permissions);
				parents.forEach(parent -> permMap.put(parent.getId(), parent));
			}
		}

		List<Permission> perms = new ArrayList<Permission>(10);
		Iterator<Entry<Integer, Permission>> iter = permMap.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<Integer, Permission> entry = iter.next();

			Permission p = new Permission();
			BeanUtils.copyProperties(entry.getValue(), p);
			perms.add(p);
		}

		return perms;
	}

	/**
	 * 递归获取父权限
	 * 
	 * @param perm
	 * @param permissions
	 * @return
	 */
	private Set<Permission> recursiveCollectParents(Permission perm, List<Permission> permissions)
	{
		Set<Permission> parents = new HashSet<Permission>(10);
		if (null == perm.getParentName())
		{
			return parents;
		}

		Permission parent = permissions.stream().filter(p -> p.getName().equals(perm.getParentName())).findFirst().get();
		if (null != parent)
		{
			parents.add(parent);

			Set<Permission> ps = recursiveCollectParents(parent, permissions);
			parents.addAll(ps);
		}
		else
		{
			LOG.error("{} parent permission is null.", perm.getName());
		}

		return parents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run(String... args) throws Exception
	{
		List<Permission> permissions = permissionMapper.findAll();

		Map<String, Permission> permissionMap = new HashMap<>(100);
		permissions.forEach(item -> permissionMap.put(item.getName(), item));

		this.permissionMap = permissionMap;

		LOG.info("Load permission size is {}.", permissions.size());
	}
}
