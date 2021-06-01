package com.mizhousoft.bmc.security.context;

/**
 * 存储安全上下文策略
 *
 * @version
 */
public class ThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy
{
	private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();

	/**
	 * 清除当前上下文
	 */
	@Override
	public void clearContext()
	{
		contextHolder.remove();
	}

	/**
	 * 获取当前上下文
	 * 
	 * @return
	 */
	@Override
	public SecurityContext getContext()
	{
		SecurityContext ctx = contextHolder.get();

		if (ctx == null)
		{
			ctx = createEmptyContext();
			contextHolder.set(ctx);
		}

		return ctx;
	}

	/**
	 * 设置当前上下文
	 * 
	 * @param context
	 */
	@Override
	public void setContext(SecurityContext context)
	{
		contextHolder.set(context);
	}

	/**
	 * 创建空的上下文
	 * 
	 * @return
	 */
	@Override
	public SecurityContext createEmptyContext()
	{
		return new SecurityContextImpl();
	}
}
