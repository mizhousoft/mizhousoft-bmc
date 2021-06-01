package com.mizhousoft.bmc.system.mapper;

import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 帐号策略Mapper
 *
 * @version
 */
public interface AccountStrategyMapper extends CrudMapper<AccountStrategy, Integer>
{
	/**
	 * 查找唯一的帐号策略
	 * 
	 * @return
	 */
	AccountStrategy findOne();
}
