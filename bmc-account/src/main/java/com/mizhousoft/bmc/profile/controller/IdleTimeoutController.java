package com.mizhousoft.bmc.profile.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.profile.request.IdleTimeoutRequest;
import com.mizhousoft.bmc.security.context.SecurityContext;
import com.mizhousoft.bmc.security.context.SecurityContextHolder;
import com.mizhousoft.bmc.system.domain.IdleTimeout;
import com.mizhousoft.bmc.system.service.IdleTimeoutService;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 修改闲置超时时间控制器
 *
 * @version
 */
@RestController
public class IdleTimeoutController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(IdleTimeoutController.class);

	@Autowired
	private IdleTimeoutService idleTimeoutService;

	@RequestMapping(value = "/setting/idletimeout/fetchIdletimeout.action", method = RequestMethod.GET)
	public ModelMap fetch()
	{
		ModelMap map = new ModelMap();

		SecurityContext securityContext = SecurityContextHolder.getContext();
		long accountId = securityContext.getAuthentication().getAccountId();
		IdleTimeout idleTimeout = idleTimeoutService.queryIdleTimeout(accountId);

		map.put("idleTimeout", idleTimeout);

		return map;
	}

	@RequestMapping(value = "/setting/idletimeout/modifyIdletimeout.action", method = RequestMethod.POST)
	public ActionResponse modify(@Valid @RequestBody IdleTimeoutRequest idleTimeoutRequest, BindingResult result,
	        HttpServletRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			if (result.hasErrors())
			{
				FieldError filedError = result.getFieldError();
				String message = filedError.getDefaultMessage();
				response = ActionRespBuilder.buildFailedResp(message);
				operLog = buildOperLog(AuditLogResult.Failure, filedError.getField() + " filed is invalid.", idleTimeoutRequest.toString());

				LOG.error(filedError.getField() + " filed is invalid.");
			}
			else
			{
				SecurityContext securityContext = SecurityContextHolder.getContext();
				long accountId = securityContext.getAuthentication().getAccountId();

				IdleTimeout idleTimeout = new IdleTimeout();
				idleTimeout.setAccountId(accountId);
				idleTimeout.setTimeout(idleTimeoutRequest.getTimeout());

				idleTimeoutService.modifyIdleTimeout(accountId, idleTimeout);

				Subject subject = SecurityUtils.getSubject();
				Session currentSessison = subject.getSession();
				long maxIdleTimeInMillis = idleTimeoutRequest.getTimeout() * 60 * 1000;
				currentSessison.setTimeout(maxIdleTimeInMillis);
				request.getSession().setMaxInactiveInterval(idleTimeoutRequest.getTimeout() * 60);

				response = ActionRespBuilder.buildSucceedResp();
				operLog = buildOperLog(AuditLogResult.Success, idleTimeoutRequest.toString(), null);
			}
		}
		catch (BMCException e)
		{
			LOG.error("Modify account idle timeout failed.", e);

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			response = ActionRespBuilder.buildFailedResp(error);

			operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), idleTimeoutRequest.toString());
		}

		AuditLogUtils.addOperationLog(operLog);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditOperation()
	{
		return "bmc.idletimeout.modify.operation";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditSource()
	{
		return "bmc.idletimeout.source";
	}
}
