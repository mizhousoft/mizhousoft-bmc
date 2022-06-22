package com.mizhousoft.bmc.system.controller;

import javax.validation.Valid;

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
import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.bmc.system.request.PasswordStrategyReqesut;
import com.mizhousoft.bmc.system.service.PasswordStrategyService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 修改密码策略控制器
 *
 * @version
 */
@RestController
public class PasswordStrategyController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(PasswordStrategyController.class);

	@Autowired
	private PasswordStrategyService passwordStrategyService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	@RequestMapping(value = "/system/fetchPasswordStrategy.action", method = RequestMethod.GET)
	public ModelMap fetchPasswordStrategy()
	{
		ModelMap map = new ModelMap();

		String serviceId = applicationAuthService.getServiceId();

		PasswordStrategy passwordStrategy = passwordStrategyService.getPasswordStrategy(serviceId);
		map.put("strategy", passwordStrategy);

		return map;
	}

	@RequestMapping(value = "/system/modifyPasswordStrategy.action", method = RequestMethod.POST)
	public ActionResponse modifyPasswordStrategy(@Valid @RequestBody
	PasswordStrategyReqesut request, BindingResult result)
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
				operLog = buildOperLog(AuditLogResult.Failure, filedError.getField() + " filed is invalid.", request.toString());

				LOG.error(filedError.getField() + " filed is invalid.");
			}
			else
			{
				String serviceId = applicationAuthService.getServiceId();

				PasswordStrategy passwordStrategy = new PasswordStrategy();
				passwordStrategy.setId(request.getId());
				passwordStrategy.setHistoryRepeatSize(request.getHistoryRepeatSize());
				passwordStrategy.setCharAppearSize(request.getCharAppearSize());
				passwordStrategy.setModifyTimeInterval(request.getModifyTimeInterval());
				passwordStrategy.setReminderModifyDay(request.getReminderModifyDay());
				passwordStrategy.setValidDay(request.getValidDay());

				passwordStrategyService.modifyPasswordStrategy(serviceId, passwordStrategy);

				response = ActionRespBuilder.buildSucceedResp();
				operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
			}
		}
		catch (BMCException e)
		{
			LOG.error("Modify password strategy failed.", e);

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			response = ActionRespBuilder.buildFailedResp(error);

			operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
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
		return "bmc.password.strategy.modify.operation";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditSource()
	{
		return "bmc.password.strategy.source";
	}
}
