package com.mizhousoft.bmc.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.bmc.security.AccountDetails;
import com.mizhousoft.bmc.security.util.BMCWebUtils;
import com.mizhousoft.bmc.security.util.ResponseBuilder;

/**
 * 密码过期检查过滤器
 *
 * @version
 */
public class PasswordExpiredCheckFilter extends OncePerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(PasswordExpiredCheckFilter.class);

	// 登录URL
	private String loginUrl;

	/**
	 * 过滤
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws ServletException, IOException
	{
		Subject subject = SecurityUtils.getSubject();

		boolean credentialsExpired = false;

		Object principal = subject.getPrincipal();
		if (principal instanceof AccountDetails)
		{
			credentialsExpired = ((AccountDetails) principal).isCredentialsExpired();
		}

		if (credentialsExpired)
		{
			issueRedirect(subject, request, response);
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @param subject
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void issueRedirect(Subject subject, ServletRequest request, ServletResponse response) throws IOException
	{
		LOG.error("Account password has expired, forced to exit the system.");

		// 再次退出
		try
		{
			subject.logout();
		}
		catch (Throwable e)
		{
			LOG.error("Subject logout failed.", e);
		}

		if (BMCWebUtils.isJSONRequest(request))
		{
			HttpServletResponse httpResp = WebUtils.toHttp(response);
			httpResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			String respBody = ResponseBuilder.buildUnauthorized(httpResp.encodeRedirectURL(loginUrl), null);

			httpResp.getWriter().write(respBody);
		}
		else
		{
			WebUtils.issueRedirect(request, response, loginUrl);
		}
	}

	/**
	 * 设置loginUrl
	 * 
	 * @param loginUrl
	 */
	public void setLoginUrl(String loginUrl)
	{
		this.loginUrl = loginUrl;
	}
}
