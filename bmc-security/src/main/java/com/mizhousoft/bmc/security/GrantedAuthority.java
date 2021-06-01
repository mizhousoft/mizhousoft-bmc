package com.mizhousoft.bmc.security;

import java.io.Serializable;

/**
 * 授权
 *
 * @version
 */
public interface GrantedAuthority extends Serializable
{
	/**
	 * 获取授权
	 * 
	 * @return
	 */
	String getAuthority();
}
