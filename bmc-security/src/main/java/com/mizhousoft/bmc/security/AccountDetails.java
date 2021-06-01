package com.mizhousoft.bmc.security;

import java.io.Serializable;
import java.util.Collection;

/**
 * 帐号
 *
 * @version
 */
public interface AccountDetails extends Serializable
{
	/**
	 * 获取授权
	 * 
	 * @return
	 */
	Collection<? extends GrantedAuthority> getAuthorities();

	/**
	 * 获取帐号ID
	 * 
	 * @return
	 */
	long getAccountId();

	/**
	 * 获取帐号名
	 * 
	 * @return
	 */
	String getAccountName();

	/**
	 * 是否超级帐号
	 * 
	 * @return
	 */
	boolean isSuperAdmin();

	/**
	 * 凭证是否过期
	 * 
	 * @return
	 */
	boolean isCredentialsExpired();

	/**
	 * 是否第一次登录
	 * 
	 * @return
	 */
	boolean isFirstLogin();

	/**
	 * 是否提醒修改密码
	 * 
	 * @return
	 */
	boolean isRemindModifyPasswd();

	/**
	 * 双因子认证是否已通过
	 * 
	 * @return
	 */
	boolean isTwoFactorAuthcPassed();

	/**
	 * 获取登录IP地址
	 * 
	 * @return
	 */
	String getLoginIpAddr();

	/**
	 * 获取闲时超时时间，单位是分钟
	 * 
	 * @return
	 */
	int getSessionIdleTimeout();

	/**
	 * 设置扩展属性
	 * 
	 * @param key
	 * @param value
	 */
	void putExtend(String key, Object value);

	/**
	 * 获取扩展属性
	 * 
	 * @param key
	 * @return
	 */
	<T> T getExtend(String key, Class<T> clazz);

	/**
	 * 移除扩展属性
	 * 
	 * @param key
	 */
	void removeExtend(String key);
}
