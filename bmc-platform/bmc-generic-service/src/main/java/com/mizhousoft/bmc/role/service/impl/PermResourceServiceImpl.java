package com.mizhousoft.bmc.role.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.role.domain.PermResource;
import com.mizhousoft.bmc.role.mapper.PermResourceMapper;
import com.mizhousoft.bmc.role.service.PermResourceService;

/**
 * 权限资源服务
 *
 * @version
 */
@Service
public class PermResourceServiceImpl implements PermResourceService
{
	private static final Logger LOG = LoggerFactory.getLogger(PermResourceServiceImpl.class);

	@Autowired
	private PermResourceMapper permResourceMapper;

	// Map<srvId-Permission Name, List<Resource Path>>
	private Map<String, List<String>> permResourceMap = new HashMap<>(0);

	// Map<srvId-Resource Path, Permission Name>
	private Map<String, String> resourcePermMap = new HashMap<>(0);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryByPermission(String srvId, String permission)
	{
		String index = buildPermIndex(srvId, permission);

		List<String> list = permResourceMap.get(index);

		return Collections.unmodifiableList(ListUtils.emptyIfNull(list));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPermissionByPath(String srvId, String path)
	{
		String index = buildPathIndex(srvId, path);

		return resourcePermMap.get(index);
	}

	@PostConstruct
	public void initialize()
	{
		Map<String, List<String>> permResourceMap = new HashMap<>(100);
		Map<String, String> resourcePermMap = new HashMap<>(100);

		List<PermResource> permReses = permResourceMapper.findAll();
		permReses.forEach(permRes -> {
			String srvId = permRes.getSrvId();

			String pathIndex = buildPathIndex(srvId, permRes.getPath());
			resourcePermMap.put(pathIndex, permRes.getPermName());

			String permIndex = buildPermIndex(srvId, permRes.getPermName());
			List<String> list = permResourceMap.get(permIndex);
			if (null == list)
			{
				list = new ArrayList<String>(4);
				permResourceMap.put(permIndex, list);
			}

			list.add(permRes.getPath());
		});

		this.permResourceMap = permResourceMap;
		this.resourcePermMap = resourcePermMap;

		LOG.info("Load permission size is {}.", this.permResourceMap.size());
		LOG.info("Load resource path size is {}.", this.resourcePermMap.size());
	}

	private String buildPermIndex(String srvId, String permission)
	{
		return srvId + "-" + permission;
	}

	private String buildPathIndex(String srvId, String path)
	{
		return srvId + "-" + path;
	}
}
