package com.mizhousoft.bmc.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.request.AccountPasswordRequest;
import com.mizhousoft.bmc.account.service.AccountPasswdService;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.context.SecurityContextHolder;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

import jakarta.validation.Valid;

/**
 * 重置帐号密码控制器
 *
 * @version
 */
@RestController
public class AccountPasswordResetController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(AccountPasswordResetController.class);

	@Autowired
	private AccountPasswdService accountPasswdService;

	@RequestMapping(value = "/account/resetPassword.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse resetPassword(@Valid @RequestBody AccountPasswordRequest request, BindingResult bindingResult)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		if (bindingResult.hasErrors())
		{
			FieldError filedError = bindingResult.getFieldError();
			String message = filedError.getDefaultMessage();
			response = ActionRespBuilder.buildFailedResp(message);
			operLog = buildOperLog(AuditLogResult.Failure, filedError.getField() + " filed is invalid.", request.toString());

			LOG.error(filedError.getField() + " filed is invalid.");
		}
		else
		{
			try
			{
				if (!request.getConfirmNewPassword().equals(request.getNewPassword()))
				{
					throw new BMCException("bmc.account.password.not.equal.confirm.error",
					        "account password is not equals with confirm password.");
				}

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				long id = authentication.getAccountId();
				if (id == request.getId())
				{
					throw new BMCException("bmc.account.reset.self.password.error", "You can not reset self password.");
				}

				Account account = accountPasswdService.resetPassword(request.getId(), request.getNewPassword());

				response = ActionRespBuilder.buildSucceedResp();

				String detail = "Reset " + account.getName() + " account password.";
				operLog = buildOperLog(AuditLogResult.Success, detail, request.toString());
			}
			catch (BMCException e)
			{
				LOG.error("Reset account password failed, message:" + e.getMessage());

				String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
				response = ActionRespBuilder.buildFailedResp(error);

				operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
			}
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
		return "bmc.account.password.reset.operation";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditSource()
	{
		return "bmc.account.source";
	}
}
