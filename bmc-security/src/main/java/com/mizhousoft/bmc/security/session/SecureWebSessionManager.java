package com.mizhousoft.bmc.security.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.bmc.security.servlet.SecureCookie;

/**
 * 会话管理器
 * 
 * @version
 */
public class SecureWebSessionManager extends DefaultWebSessionManager
{
	private static final Logger log = LoggerFactory.getLogger(SecureWebSessionManager.class);

	/**
	 * 启动会话
	 * 
	 * @param session
	 * @param context
	 */
	@Override
	protected void onStart(Session session, SessionContext context)
	{
		if (!WebUtils.isHttp(context))
		{
			log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response "
			        + "pair. No session ID cookie will be set.");
			return;

		}

		HttpServletRequest request = WebUtils.getHttpRequest(context);
		HttpServletResponse response = WebUtils.getHttpResponse(context);

		if (isSessionIdCookieEnabled())
		{
			Serializable sessionId = session.getId();
			storeSessionId(sessionId, request, response);
		}
		else
		{
			log.debug("Session ID cookie is disabled.  No cookie has been set for new session with id {}",
			        session.getId());
		}

		request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
	}

	/**
	 * 存储Session Id
	 * 
	 * @param currentId
	 * @param request
	 * @param response
	 */
	private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response)
	{
		if (currentId == null)
		{
			String msg = "sessionId cannot be null when persisting for subsequent requests.";
			throw new IllegalArgumentException(msg);
		}

		Cookie template = getSessionIdCookie();
		Cookie cookie = new SecureCookie(template);
		String idString = currentId.toString();
		cookie.setValue(idString);
		cookie.saveTo(request, response);
	}
}
