package com.mizhousoft.bmc.system.service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.domain.IdleTimeout;

/**
 * 闲置超时时间服务
 *
 * @version
 */
public interface IdleTimeoutService
{
	/**
	 * 修改闲置超时时间
	 * 
	 * @param accountId
	 * @param idleTimeout
	 * @throws BMCException
	 */
	void modifyIdleTimeout(long accountId, IdleTimeout idleTimeout) throws BMCException;

	/**
	 * 删除闲置超时时间
	 * 
	 * @param accountId
	 * @throws BMCException
	 */
	void deleteIdleTimeout(long accountId) throws BMCException;

	/**
	 * 查询闲置超时时间
	 * 
	 * @param accountId
	 * @return
	 */
	IdleTimeout queryIdleTimeout(long accountId);
}
