package com.mizhousoft.bmc.account.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.model.AccountInfo;
import com.mizhousoft.bmc.account.model.AuthAccount;
import com.mizhousoft.bmc.account.request.AccountPageRequest;
import com.mizhousoft.commons.mapper.CrudMapper;

/**
 * 帐号持久层
 *
 * @version
 */
public interface AccountMapper extends CrudMapper<Account, Long>
{
	/**
	 * 更新帐号状态
	 * 
	 * @param id
	 * @param status
	 */
	void updateAccountStatus(@Param("id") long id, @Param("status") int status);

	/**
	 * 更新第一次登录
	 * 
	 * @param id
	 * @param isFirstLogin
	 */
	void updateFirstLogin(@Param("id") long id, @Param("isFirstLogin") boolean isFirstLogin);

	/**
	 * 更新最后访问
	 * 
	 * @param id
	 * @param lastAccessTime
	 * @param lastAccessIpAddr
	 */
	void updateLastAccess(@Param("id") long id, @Param("lastAccessTime") Date lastAccessTime,
	        @Param("lastAccessIpAddr") String lastAccessIpAddr);

	/**
	 * 更新密码
	 * 
	 * @param id
	 * @param password
	 */
	void updatePassword(@Param("id") long id, @Param("password") String password);

	/**
	 * 更新手机号
	 * 
	 * @param id
	 * @param phoneNumber
	 */
	void updatePhoneNumber(@Param("id") long id, @Param("phoneNumber") String phoneNumber);

	/**
	 * 锁定帐号
	 * 
	 * @param id
	 * @param status
	 * @param lockTime
	 */
	void lockAccount(@Param("id") long id, @Param("status") int status, @Param("lockTime") Date lockTime);

	/**
	 * 解锁帐号
	 * 
	 * @param id
	 * @param status
	 */
	void unlockAccount(@Param("id") long id, @Param("status") int status);

	/**
	 * 查询帐号认证信息
	 * 
	 * @param srvId
	 * @param name
	 * @return
	 */
	AuthAccount findAuthAccount(@Param("srvId") String srvId, @Param("name") String name);

	/**
	 * 统计帐号
	 * 
	 * @param request
	 * @return
	 */
	long countAccounts(@Param("request") AccountPageRequest request);

	/**
	 * 查询帐号
	 * 
	 * @param rowOffset
	 * @param request
	 * @return
	 */
	List<AccountInfo> findAccountInfos(@Param("rowOffset") long rowOffset, @Param("request") AccountPageRequest request);
}
