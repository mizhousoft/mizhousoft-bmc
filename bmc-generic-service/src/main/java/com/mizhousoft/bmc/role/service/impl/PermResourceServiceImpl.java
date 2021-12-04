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

	// Map<Permission Name, List<Resource Path>>
	private Map<String, List<String>> permResourceMap = new HashMap<>(0);

	// Map<Resource Path, Permission Name>
	private Map<String, String> resourcePermMap = new HashMap<>(0);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> queryByPermission(String permission)
	{
		List<String> list = permResourceMap.get(permission);

		return Collections.unmodifiableList(ListUtils.emptyIfNull(list));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPermissionByPath(String path)
	{
		return resourcePermMap.get(path);
	}

	@PostConstruct
	public void initialize()
	{
		Map<String, List<String>> permResourceMap = new HashMap<>(100);
		Map<String, String> resourcePermMap = new HashMap<>(100);

		List<PermResource> permReses = permResourceMapper.findAll();
		permReses.forEach(permRes -> {
			resourcePermMap.put(permRes.getPath(), permRes.getPermName());

			List<String> list = permResourceMap.get(permRes.getPermName());
			if (null == list)
			{
				list = new ArrayList<String>(4);
				permResourceMap.put(permRes.getPermName(), list);
			}

			list.add(permRes.getPath());
		});

		this.permResourceMap = permResourceMap;
		this.resourcePermMap = resourcePermMap;

		LOG.info("Load permission size is {}.", this.permResourceMap.size());
		LOG.info("Load resource path size is {}.", this.resourcePermMap.size());
	}
}
