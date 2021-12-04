package com.mizhousoft.bmc.boot;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.context.SecurityContextHolder;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;
import com.mizhousoft.commons.web.util.WebUtils;

/**
 * 全局Rest异常处理器
 *
 * @version
 */
@RestControllerAdvice(annotations = RestController.class)
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

	@ExceptionHandler(Throwable.class)
	public final ActionResponse handleException(HttpServletRequest req, Throwable e)
	{
		String path = WebUtils.getPathWithinApplication(req);

		long accountId = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication)
		{
			accountId = authentication.getAccountId();
		}

		LOG.error("Service error, request path is {}, account id is {}.", path, accountId, e);

		String error = I18nUtils.getMessage("bmc.system.internal.error");

		ActionResponse response = ActionRespBuilder.buildFailedResp(error);

		return response;
	}
}
