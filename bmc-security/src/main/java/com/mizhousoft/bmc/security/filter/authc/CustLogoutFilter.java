package com.mizhousoft.bmc.security.filter.authc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;

import com.mizhousoft.bmc.security.util.BMCWebUtils;
import com.mizhousoft.bmc.security.util.ResponseBuilder;

/**
 * 定制注销过滤器
 *
 * @version
 */
public class CustLogoutFilter extends LogoutFilter
{
	/**
	 * 跳转
	 * 
	 * @param request
	 * @param response
	 * @param redirectUrl
	 * @throws Exception
	 */
	protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception
	{
		if (BMCWebUtils.isJSONRequest(request))
		{
			HttpServletResponse httpResp = WebUtils.toHttp(response);
			httpResp.setStatus(HttpServletResponse.SC_OK);

			String respBody = ResponseBuilder.buildSucceed(httpResp.encodeRedirectURL(redirectUrl));
			httpResp.getWriter().write(respBody);
		}
		else
		{
			super.issueRedirect(request, response, redirectUrl);
		}
	}
}
