package com.mizhousoft.bmc.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.account.domain.HistoryPassword;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 历史密码持久层Mapper
 *
 * @version
 */
public interface HistoryPasswordMapper extends CrudMapper<HistoryPassword, Integer>
{
	/**
	 * 删除历史密码
	 * 
	 * @param accountId
	 */
	void delete(@Param("accountId") long accountId);

	/**
	 * 查找历史密码
	 * 
	 * @param accountId
	 * @param topNum
	 * @return
	 */
	List<HistoryPassword> findHistoryPasswords(@Param("accountId") long accountId, @Param("topNum") int topNum);
}
