package com.mizhousoft.bmc.system.service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.domain.PasswordStrategy;

/**
 * 密码策略服务
 *
 * @version
 */
public interface PasswordStrategyService
{
	/**
	 * 查询唯一的密码策略
	 * 
	 * @param srvId
	 * @return
	 */
	PasswordStrategy getPasswordStrategy(String srvId);

	/**
	 * 修改密码策略
	 * 
	 * @param srvId
	 * @param passwordStrategy
	 * @throws BMCException
	 */
	void modifyPasswordStrategy(String srvId, PasswordStrategy passwordStrategy) throws BMCException;
}
