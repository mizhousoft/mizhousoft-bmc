package com.mizhousoft.bmc.role.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.role.constant.RoleType;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.mapper.RoleMapper;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.data.util.PageUtils;
import com.mizhousoft.commons.lang.CollectionUtils;

/**
 * 角色服务实现
 * 
 * @version
 */
@Service
public class RoleServiceImpl implements RoleService
{
	private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRole(Role role) throws BMCException
	{
		RolePageRequest request = new RolePageRequest();
		request.setSrvId(role.getSrvId());
		List<Role> list = roleMapper.findPageData(0, request);

		Optional<Role> optional = list.stream().filter(item -> item.getDisplayNameCN().equals(role.getDisplayNameCN())).findFirst();
		if (optional.isPresent())
		{
			throw new BMCException("bmc.role.name.exist.error", "Role does exist, name is " + role.getName() + ".");
		}

		roleMapper.save(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyRole(Role role) throws BMCException
	{
		RolePageRequest request = new RolePageRequest();
		request.setSrvId(role.getSrvId());
		List<Role> list = roleMapper.findPageData(0, request);

		Optional<Role> optional = list.stream()
		        .filter(item -> item.getId() != role.getId() && item.getDisplayNameCN().equals(role.getDisplayNameCN())).findFirst();
		if (optional.isPresent())
		{
			throw new BMCException("bmc.role.name.exist.error", "Role does exist, name is " + role.getName() + ".");
		}

		roleMapper.update(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role deleteRole(String srvId, int id) throws BMCException
	{
		Role role = getById(srvId, id);
		if (null == role)
		{
			return null;
		}

		if (role.getType() == RoleType.AdministratorRole.getValue())
		{
			throw new BMCException("bmc.role.administrator.can.not.delete.error", "Administrator Role can not delete.");
		}

		roleMapper.delete(role.getId());

		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> queryByIds(List<Integer> ids)
	{
		if (CollectionUtils.isEmpty(ids))
		{
			return Collections.emptyList();
		}

		return roleMapper.findByIds(ids);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role loadById(String srvId, int id) throws BMCException
	{
		Role role = getById(srvId, id);
		if (null == role)
		{
			throw new BMCException("bmc.role.not.exist.error", "Role does not exist, id is " + id + ".");
		}

		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getById(String srvId, int id)
	{
		Role role = roleMapper.findById(id);
		if (null != role && !role.getSrvId().equals(srvId))
		{
			LOG.error("Role service id is wrong, id is {}, role srvId is {}, srvId is {}.", id, role.getSrvId(), srvId);
			return null;
		}

		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Role> queryPageData(PageRequest request)
	{
		long total = roleMapper.countTotal(request);
		long rowOffset = PageUtils.calcRowOffset(request, total);

		List<Role> list = roleMapper.findPageData(rowOffset, request);
		Page<Role> page = PageBuilder.build(list, request, total);

		return page;
	}
}
