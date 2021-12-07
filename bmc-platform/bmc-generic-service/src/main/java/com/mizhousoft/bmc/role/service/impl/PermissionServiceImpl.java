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

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PermissionServiceImpl implements PermissionService
{
	private static final Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class);

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private PermResourceService permResourceService;

	// Map<SrvId, Map<Permission Name, Permission>>
	private Map<String, Map<String, Permission>> srvPermMap = new HashMap<>(0);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryAuthcRequestPaths(String srvId)
	{
		List<String> authcPaths = new ArrayList<String>(10);

		List<Permission> allPerms = queryAllPermissions(srvId);
		allPerms.forEach(perm -> {
			if (perm.isAuthz())
			{
				return;
			}

			List<String> paths = permResourceService.queryByPermission(perm.getName());
			authcPaths.addAll(paths);
		});

		return authcPaths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryAuthzRequestPaths(String srvId)
	{
		List<String> authzPaths = new ArrayList<String>(10);

		List<Permission> allPerms = queryAllPermissions(srvId);
		allPerms.forEach(perm -> {
			if (!perm.isAuthz())
			{
				return;
			}

			List<String> paths = permResourceService.queryByPermission(perm.getName());
			authzPaths.addAll(paths);
		});

		return authzPaths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Permission getByRequestPath(String srvId, String path)
	{
		Map<String, Permission> permissionMap = srvPermMap.get(srvId);
		if (null == permissionMap)
		{
			LOG.warn("Service id {} not found", srvId);
			return null;
		}

		String permName = permResourceService.getPermissionByPath(path);
		if (null == permName)
		{
			LOG.warn("Permission not found, path is {}.", path);
			return null;
		}

		return permissionMap.get(permName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Permission getByName(String srvId, String name)
	{
		Map<String, Permission> permissionMap = srvPermMap.get(srvId);
		if (null == permissionMap)
		{
			LOG.warn("Service id {} not found", srvId);
			return null;
		}

		return permissionMap.get(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryAuthzPermissions(String srvId)
	{
		List<Permission> permissions = queryAllPermissions(srvId);

		List<Permission> authzPerms = permissions.stream().filter(perm -> perm.isAuthz()).collect(Collectors.toList());

		return authzPerms;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> queryPermissionsByIds(Set<Integer> ids, boolean withParent)
	{
		List<Permission> permissions = permissionMapper.findAll();

		Map<Integer, Permission> permMap = new HashMap<Integer, Permission>(10);

		for (Integer id : ids)
		{
			Permission perm = permissions.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
			if (null == perm)
			{
				continue;
			}

			permMap.put(perm.getId(), perm);

			if (withParent && null != perm.getParentName())
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

	private List<Permission> queryAllPermissions(String srvId)
	{
		List<Permission> list = permissionMapper.findAll();

		list = list.stream().filter(item -> item.getSrvId().equals(srvId)).collect(Collectors.toList());

		return list;
	}

	@PostConstruct
	public void initialize()
	{
		List<Permission> permissions = permissionMapper.findAll();

		Map<String, Map<String, Permission>> srvPermMap = new HashMap<>(5);
		for (Permission permission : permissions)
		{
			Map<String, Permission> permissionMap = srvPermMap.get(permission.getSrvId());
			if (null == permissionMap)
			{
				permissionMap = new HashMap<>(100);
				srvPermMap.put(permission.getSrvId(), permissionMap);
			}

			permissionMap.put(permission.getName(), permission);
		}

		this.srvPermMap = srvPermMap;

		LOG.info("Load permission size is {}.", permissions.size());
	}
}
