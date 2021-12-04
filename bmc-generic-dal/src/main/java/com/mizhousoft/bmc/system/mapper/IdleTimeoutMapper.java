package com.mizhousoft.bmc.system.mapper;

import com.mizhousoft.bmc.system.domain.IdleTimeout;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 闲置超时时间持久层Mapper
 *
 * @version
 */
public interface IdleTimeoutMapper extends CrudMapper<IdleTimeout, Integer>
{
	/**
	 * 删除闲置超时时间
	 * 
	 * @param accountId
	 */
	void delete(long accountId);

	/**
	 * 查找闲置超时时间
	 * 
	 * @param accountId
	 * @return
	 */
	IdleTimeout findIdleTimeout(long accountId);
}
