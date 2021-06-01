package com.mizhousoft.bmc.security.context;

/**
 * 存储安全上下文策略
 *
 * @version
 */
public interface SecurityContextHolderStrategy
{
	/**
	 * 清除当前上下文
	 */
	void clearContext();

	/**
	 * 获取当前上下文
	 * 
	 * @return
	 */
	SecurityContext getContext();

	/**
	 * 设置当前上下文
	 * 
	 * @param context
	 */
	void setContext(SecurityContext context);

	/**
	 * 创建空的上下文
	 * 
	 * @return
	 */
	SecurityContext createEmptyContext();
}
