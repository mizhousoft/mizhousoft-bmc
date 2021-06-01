package com.mizhousoft.bmc.security.util;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import com.mizhousoft.bmc.security.SecurityConstants;

/**
 * WEB工具类
 *
 * @version
 */
public abstract class BMCWebUtils
{
	/**
	 * 是否JSON请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isJSONRequest(ServletRequest request)
	{
		String accept = WebUtils.toHttp(request).getHeader(SecurityConstants.ACCEPT_HEADER);
		if (StringUtils.isBlank(accept))
		{
			return false;
		}

		return accept.contains(MediaType.APPLICATION_JSON_VALUE);
	}
}
