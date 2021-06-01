package com.mizhousoft.bmc.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.account.domain.AccountRole;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 帐号角色持久层Mapper
 *
 * @version
 */
public interface AccountRoleMapper extends CrudMapper<AccountRole, Integer>
{
	/**
	 * 根据角色id删除
	 * 
	 * @param roleId
	 */
	void deleteByRoleId(@Param("roleId") long roleId);

	/**
	 * 根据帐号id删除
	 * 
	 * @param accountId
	 */
	void deleteByAccountId(@Param("accountId") long accountId);

	/**
	 * 根据帐号ID查询
	 * 
	 * @param accountId
	 * @return
	 */
	List<AccountRole> findByAccountId(long accountId);
}
