package com.mizhousoft.bmc.system.service;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.domain.AccountStrategy;

/**
 * 帐号策略服务
 *
 * @version
 */
public interface AccountStrategyService
{
	/**
	 * 查找唯一的帐号策略
	 * 
	 * @return
	 */
	AccountStrategy queryAccountStrategy();

	/**
	 * 修改帐号策略
	 * 
	 * @param accountStrategy
	 * @throws BMCException
	 */
	void modifyAccountStrategy(AccountStrategy accountStrategy) throws BMCException;
}
