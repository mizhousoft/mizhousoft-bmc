package com.mizhousoft.bmc.authentication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 登录Controller
 *
 * @version
 */
@Controller
public class LoginController
{
	public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";

	public static final String DEFAULT_LOGIN_FAILURE_LEAVE_COUNT = "loginFailureLeaveCount";

	/**
	 * 登录失败跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login.action", method = RequestMethod.POST)
	public @ResponseBody ActionResponse login(HttpServletRequest request)
	{
		ActionResponse response = null;

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated())
		{
			response = ActionRespBuilder.buildSucceedResp();
		}
		else
		{
			HttpSession session = request.getSession(true);

			String exceptionClassName = (String) request.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if (StringUtils.isNotBlank(exceptionClassName))
			{
				session.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exceptionClassName);
			}

			Integer loginFailureLeaveCount = (Integer) request.getAttribute(DEFAULT_LOGIN_FAILURE_LEAVE_COUNT);
			if (null != loginFailureLeaveCount)
			{
				session.setAttribute(DEFAULT_LOGIN_FAILURE_LEAVE_COUNT, loginFailureLeaveCount);
			}

			String error = null;
			if (DisabledAccountException.class.getName().equals(exceptionClassName))
			{
				error = I18nUtils.getMessage("bmc.account.login.disabled.error", null);
			}
			else if (LockedAccountException.class.getName().equals(exceptionClassName))
			{
				error = I18nUtils.getMessage("bmc.account.login.lock.error", null);
			}
			else
			{
				if (null != loginFailureLeaveCount)
				{
					String[] params = { String.valueOf(loginFailureLeaveCount) };
					error = I18nUtils.getMessage("bmc.account.login.badcredentials.leave.error", params);
				}
				else
				{
					error = I18nUtils.getMessage("bmc.account.login.badcredentials.error", null);
				}

				session.removeAttribute(DEFAULT_LOGIN_FAILURE_LEAVE_COUNT);
			}

			response = ActionRespBuilder.buildFailedResp(error);
		}

		return response;
	}
}
