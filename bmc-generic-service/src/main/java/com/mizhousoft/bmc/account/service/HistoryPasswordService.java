package com.mizhousoft.bmc.account.service;

import java.util.List;

import com.mizhousoft.bmc.account.domain.HistoryPassword;

/**
 * 历史密码服务
 *
 * @version
 */
public interface HistoryPasswordService
{
	/**
	 * 保存历史密码
	 * 
	 * @param accountId
	 * @param oldPassword
	 */
	void save(long accountId, String oldPassword);

	/**
	 * 删除历史密码
	 * 
	 * @param accountId
	 */
	void delete(long accountId);

	/**
	 * 查询历史密码
	 * 
	 * @param accountId
	 * @param topNum
	 * @return
	 */
	List<HistoryPassword> queryHistoryPasswords(long accountId, int topNum);
}
