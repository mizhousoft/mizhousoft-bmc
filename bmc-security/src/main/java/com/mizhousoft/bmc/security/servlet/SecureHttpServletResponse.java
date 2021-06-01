package com.mizhousoft.bmc.security.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

/**
 * 安全的HttpServletResponse
 * 
 * @version
 */
public class SecureHttpServletResponse extends ShiroHttpServletResponse
{
	/**
	 * 构造函数
	 * 
	 * @param wrapped
	 * @param context
	 * @param request
	 */
	public SecureHttpServletResponse(HttpServletResponse wrapped, ServletContext context,
	        ShiroHttpServletRequest request)
	{
		super(wrapped, context, request);
	}

	/**
	 * 转换URL
	 * 
	 * @param url
	 * @param sessionId
	 * @return
	 */
	protected String toEncoded(String url, String sessionId)
	{
		return url;
	}
}
