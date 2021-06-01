package com.mizhousoft.bmc.security.mgt;

import org.apache.shiro.SecurityUtils;

/**
 * DefaultWebSecurityManager
 *
 * @version
 */
public class DefaultWebSecurityManager extends org.apache.shiro.web.mgt.DefaultWebSecurityManager
{
	/**
	 * 初始化
	 * 
	 */
	public void init()
	{
		SecurityUtils.setSecurityManager(this);
	}
}
