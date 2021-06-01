package com.mizhousoft.bmc.system.mapper;

import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 密码策略Mapper
 *
 * @version
 */
public interface PasswordStrategyMapper extends CrudMapper<PasswordStrategy, Integer>
{
	/**
	 * 查找唯一的密码策略
	 * 
	 * @return
	 */
	PasswordStrategy findOne();
}
