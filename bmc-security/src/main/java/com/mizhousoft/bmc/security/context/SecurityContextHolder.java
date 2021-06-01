package com.mizhousoft.bmc.security.context;

/**
 * 安全上下文存储器
 *
 * @version
 */
public class SecurityContextHolder
{
	private static SecurityContextHolderStrategy strategy = new ThreadLocalSecurityContextHolderStrategy();

	/**
	 * 清除当前上下文
	 */
	public static void clearContext()
	{
		strategy.clearContext();
	}

	/**
	 * 获取当前上下文
	 * 
	 * @return
	 */
	public static SecurityContext getContext()
	{
		return strategy.getContext();
	}

	/**
	 * 设置当前上下文
	 * 
	 * @param context
	 */
	public static void setContext(SecurityContext context)
	{
		strategy.setContext(context);
	}

	/**
	 * 创建空的上下文
	 * 
	 * @return
	 */
	public static SecurityContext createEmptyContext()
	{
		return strategy.createEmptyContext();
	}
}
