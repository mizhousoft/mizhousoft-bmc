package com.mizhousoft.bmc.security.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;

/**
 * 安全的Cookie
 * 
 * @version
 */
public class SecureCookie extends SimpleCookie
{
	/**
	 * 构造函数
	 * 
	 */
	public SecureCookie()
	{
		super();
	}

	/**
	 * 构造函数
	 *
	 * @param cookie
	 */
	public SecureCookie(Cookie cookie)
	{
		super(cookie);
	}

	/**
	 * 构造函数
	 * 
	 * @param defaultSessionIdName
	 */
	public SecureCookie(String defaultSessionIdName)
	{
		super(defaultSessionIdName);
	}

	/**
	 * 保存Cookie
	 * 
	 * @param request
	 * @param response
	 */
	public void saveTo(HttpServletRequest request, HttpServletResponse response)
	{
		String name = getName();
		String value = getValue();
		String comment = getComment();
		String domain = getDomain();
		String path = getPath();
		int maxAge = getMaxAge();
		int version = getVersion();
		boolean secure = isSecure();
		boolean httpOnly = isHttpOnly();

		addCookieHeader(response, name, value, comment, domain, path, maxAge, version, secure, httpOnly);
	}

	/**
	 * 增加Cookie到Response Header
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param comment
	 * @param domain
	 * @param path
	 * @param maxAge
	 * @param version
	 * @param secure
	 * @param httpOnly
	 */
	private void addCookieHeader(HttpServletResponse response, String name, String value, String comment, String domain,
	        String path, int maxAge, int version, boolean secure, boolean httpOnly)
	{
		String headerValue = buildHeaderValue(name, value, comment, domain, path, maxAge, version, secure, httpOnly);
		response.addHeader(COOKIE_HEADER_NAME, headerValue);
	}
}
