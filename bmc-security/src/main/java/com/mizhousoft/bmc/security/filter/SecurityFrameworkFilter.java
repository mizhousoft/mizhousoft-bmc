package com.mizhousoft.bmc.security.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mizhousoft.bmc.security.servlet.SecureHttpServletResponse;

/**
 * WEB安全过滤器
 * 
 * @version
 */
public final class SecurityFrameworkFilter extends AbstractShiroFilter
{
	// 不认证URL，<URL, URL>
	private Map<String, String> nonAuthURIMap;

	// 不更新访问时间URL，<URL, URL>
	private Map<String, String> nonUpdateAccessTimeURIMap;

	/**
	 * {@inheritDoc}
	 */
	protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, final FilterChain chain)
	        throws ServletException, IOException
	{
		HttpServletRequest req = WebUtils.toHttp(servletRequest);
		String requestURI = req.getRequestURI();

		if (nonAuthURIMap.containsKey(requestURI))
		{
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		Throwable t = null;

		try
		{
			final ServletRequest request = prepareServletRequest(servletRequest, servletResponse, chain);
			final ServletResponse response = prepareServletResponse(request, servletResponse, chain);

			final Subject subject = createSubject(request, response);

			// noinspection unchecked
			subject.execute(new Callable<Object>()
			{
				public Object call() throws Exception
				{
					if (!nonUpdateAccessTimeURIMap.containsKey(requestURI))
					{
						updateSessionLastAccessTime(request, response);
					}

					executeChain(request, response, chain);
					return null;
				}
			});
		}
		catch (ExecutionException ex)
		{
			t = ex.getCause();
		}
		catch (Throwable throwable)
		{
			t = throwable;
		}

		if (t != null)
		{
			if (t instanceof ServletException)
			{
				throw (ServletException) t;
			}
			if (t instanceof IOException)
			{
				throw (IOException) t;
			}

			String msg = "Filtered request failed.";
			throw new ServletException(msg, t);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected ServletResponse wrapServletResponse(HttpServletResponse orig, ShiroHttpServletRequest request)
	{
		return new SecureHttpServletResponse(orig, getServletContext(), request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() throws Exception
	{
		String contextPath = this.getServletContext().getContextPath();

		initNonAuthURIMap(contextPath);
		initNonUpdateAccessTimeURIMap(contextPath);

		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

		WebSecurityManager webSecurityManager = context.getBean(WebSecurityManager.class);
		if (null != webSecurityManager)
		{
			setSecurityManager(webSecurityManager);
		}
		else
		{
			throw new SecurityException("WebSecurityManager bean not found.");
		}

		FilterChainResolver filterChainResolver = context.getBean(FilterChainResolver.class);
		if (filterChainResolver != null)
		{
			setFilterChainResolver(filterChainResolver);
		}
		else
		{
			throw new SecurityException("FilterChainResolver bean not found.");
		}
	}

	private void initNonAuthURIMap(String contextPath)
	{
		if (!MapUtils.isEmpty(nonAuthURIMap))
		{
			Map<String, String> valueMap = new HashMap<>(nonAuthURIMap.size());
			Iterator<Entry<String, String>> iter = nonAuthURIMap.entrySet().iterator();
			while (iter.hasNext())
			{
				Entry<String, String> entry = iter.next();

				String value = contextPath + entry.getKey();
				valueMap.put(value, value);
			}

			nonAuthURIMap = valueMap;
		}
		else
		{
			nonAuthURIMap = Collections.emptyMap();
		}
	}

	private void initNonUpdateAccessTimeURIMap(String contextPath)
	{
		if (!MapUtils.isEmpty(nonUpdateAccessTimeURIMap))
		{
			Map<String, String> valueMap = new HashMap<>(nonUpdateAccessTimeURIMap.size());
			Iterator<Entry<String, String>> iter = nonUpdateAccessTimeURIMap.entrySet().iterator();
			while (iter.hasNext())
			{
				Entry<String, String> entry = iter.next();

				String value = contextPath + entry.getKey();
				valueMap.put(value, value);
			}

			nonUpdateAccessTimeURIMap = valueMap;
		}
		else
		{
			nonUpdateAccessTimeURIMap = Collections.emptyMap();
		}
	}

	/**
	 * 设置nonAuthURIMap
	 * 
	 * @param nonAuthURIMap
	 */
	public void setNonAuthURIMap(Map<String, String> nonAuthURIMap)
	{
		this.nonAuthURIMap = nonAuthURIMap;
	}

	/**
	 * 设置nonUpdateAccessTimeURIMap
	 * 
	 * @param nonUpdateAccessTimeURIMap
	 */
	public void setNonUpdateAccessTimeURIMap(Map<String, String> nonUpdateAccessTimeURIMap)
	{
		this.nonUpdateAccessTimeURIMap = nonUpdateAccessTimeURIMap;
	}
}
