package com.mizhousoft.bmc.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.constant.AccountSecurityConstants;
import com.mizhousoft.bmc.system.domain.IdleTimeout;
import com.mizhousoft.bmc.system.mapper.IdleTimeoutMapper;
import com.mizhousoft.bmc.system.service.IdleTimeoutService;

/**
 * 闲置超时时间服务
 *
 * @version
 */
@Service
public class IdleTimeoutServiceImpl implements IdleTimeoutService
{
	@Autowired
	private IdleTimeoutMapper idleTimeoutMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyIdleTimeout(long accountId, IdleTimeout idleTimeout) throws BMCException
	{
		IdleTimeout it = idleTimeoutMapper.findIdleTimeout(accountId);
		if (null == it)
		{
			idleTimeout.setId(0);
			idleTimeout.setAccountId(accountId);
			idleTimeoutMapper.save(idleTimeout);
		}
		else
		{
			it.setTimeout(idleTimeout.getTimeout());
			idleTimeoutMapper.update(it);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteIdleTimeout(long accountId) throws BMCException
	{
		idleTimeoutMapper.delete(accountId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IdleTimeout queryIdleTimeout(long accountId)
	{
		IdleTimeout idleTimeout = idleTimeoutMapper.findIdleTimeout(accountId);
		if (null == idleTimeout)
		{
			idleTimeout = new IdleTimeout();
			idleTimeout.setId(0);
			idleTimeout.setTimeout(AccountSecurityConstants.DEFAULT_IDLE_TIMEOUT);
			idleTimeout.setAccountId(accountId);
		}

		return idleTimeout;
	}
}
