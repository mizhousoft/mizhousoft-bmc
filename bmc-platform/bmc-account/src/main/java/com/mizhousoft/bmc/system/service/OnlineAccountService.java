package com.mizhousoft.bmc.system.service;

import org.apache.shiro.session.Session;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.system.model.OnlineAccount;
import com.mizhousoft.boot.authentication.AccountDetails;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.domain.Pageable;

/**
 * 在线帐号服务
 *
 * @version
 */
public interface OnlineAccountService
{
	/**
	 * 分页查询在线帐号
	 * 
	 * @param currentSession
	 * @param pageRequest
	 * @return
	 */
	Page<OnlineAccount> queryOnlineAccounts(Session currentSession, Pageable pageRequest);

	/**
	 * 注销帐号
	 * 
	 * @param accountId
	 * @param randomId
	 * @return
	 * @throws BMCException
	 */
	AccountDetails logoffAccount(long accountId, String randomId) throws BMCException;

	/**
	 * 注销所有帐号
	 */
	void logoffAll();
}
