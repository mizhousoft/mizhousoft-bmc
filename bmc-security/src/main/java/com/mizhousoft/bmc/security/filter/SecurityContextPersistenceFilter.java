package com.mizhousoft.bmc.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.bmc.security.AccountAuthentication;
import com.mizhousoft.bmc.security.AccountDetails;
import com.mizhousoft.bmc.security.context.SecurityContextHolder;
import com.mizhousoft.bmc.security.context.SecurityContextImpl;
import com.mizhousoft.bmc.security.util.BMCWebUtils;
import com.mizhousoft.bmc.security.util.ResponseBuilder;

/**
 * SecurityContext持久化过滤器
 *
 * @version
 */
public class SecurityContextPersistenceFilter extends OncePerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(SecurityContextPersistenceFilter.class);

	// 登录URL
	private String loginUrl;

	// 校验访问IP地址
	private boolean checkAccessIpAddr = true;

	/**
	 * 过滤
	 * 
	 * @param req
	 * @param res
	 * @param chain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException
	{
		Subject subject = SecurityUtils.getSubject();

		// 未认证不做处理
		if (!subject.isAuthenticated())
		{
			// 再次退出
			logout(req, res, subject);
			return;
		}

		Object principal = subject.getPrincipal();
		AccountDetails accountDetails = null;
		if (principal instanceof AccountDetails)
		{
			accountDetails = (AccountDetails) principal;
		}
		else
		{
			// 再次退出
			logout(req, res, subject);
			return;
		}

		String remoteIPAddress = com.mizhousoft.commons.web.util.WebUtils.getRemoteIPAddress(req);
		if (StringUtils.isBlank(remoteIPAddress))
		{
			remoteIPAddress = subject.getSession().getHost();
		}

		if (checkAccessIpAddr && !StringUtils.equals(accountDetails.getLoginIpAddr(), remoteIPAddress))
		{
			LOG.error("Account login ip address is {}, access ip address is {} now, force to logout.", accountDetails.getLoginIpAddr(),
			        remoteIPAddress);
			logout(req, res, subject);
			return;
		}

		AccountAuthentication authentication = new AccountAuthentication(accountDetails);
		authentication.setAccessIPAddress(remoteIPAddress);
		authentication.setAuthenticated(subject.isAuthenticated());

		SecurityContextImpl securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(authentication);

		try
		{
			SecurityContextHolder.setContext(securityContext);

			chain.doFilter(req, res);
		}
		finally
		{
			SecurityContextHolder.clearContext();
		}
	}

	/**
	 * 退出
	 * 
	 * @param req
	 * @param res
	 * @param subject
	 * @throws IOException
	 */
	private void logout(ServletRequest req, ServletResponse res, Subject subject) throws IOException
	{
		// 再次退出
		try
		{
			subject.logout();
		}
		catch (Throwable e)
		{
			LOG.error("Subject logout failed.", e);
		}

		if (BMCWebUtils.isJSONRequest(req))
		{
			HttpServletResponse httpResp = WebUtils.toHttp(res);
			httpResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			String respBody = ResponseBuilder.buildUnauthorized(httpResp.encodeRedirectURL(loginUrl), null);

			httpResp.getWriter().write(respBody);
		}
		else
		{
			WebUtils.issueRedirect(req, res, loginUrl);
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

	/**
	 * 设置checkAccessIpAddr
	 * 
	 * @param checkAccessIpAddr
	 */
	public void setCheckAccessIpAddr(boolean checkAccessIpAddr)
	{
		this.checkAccessIpAddr = checkAccessIpAddr;
	}
}
