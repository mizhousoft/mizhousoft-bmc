package com.mizhousoft.bmc.boot;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.context.SecurityContextHolder;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;
import com.mizhousoft.commons.web.util.WebUtils;

/**
 * 全局异常处理器
 *
 * @version
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Throwable.class)
	private ResponseEntity<String> handleException(HttpServletRequest req, Throwable e)
	{
		String path = WebUtils.getPathWithinApplication(req);

		long accountId = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication)
		{
			accountId = authentication.getAccountId();
		}

		LOG.error("Service error, request path is {}, account id is {}.", path, accountId, e);

		String error = I18nUtils.getMessage("rlp.system.internal.error");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
