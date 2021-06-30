package com.mizhousoft.bmc.profile.controller;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.security.Authentication;
import com.mizhousoft.bmc.security.context.SecurityContextHolder;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 密码第一次登录或密码被重置修改控制器
 *
 * @version
 */
@RestController
public class PasswordFirstModifyController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(PasswordFirstModifyController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountPasswdService accountPasswdService;

	@RequestMapping(value = "/setting/password/modifyFirstLoginPassword.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse modifyFirstLoginPassword(@Valid @RequestBody AccountPasswordRequest request, BindingResult bindingResult)
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
					        "Account password is not equals with confirm password.");
				}

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				long id = authentication.getAccountId();

				Account account = accountService.loadById(id);

				accountPasswdService.modifyFirstLoginPassword(account, request.getPassword(), request.getNewPassword());

				logout();

				response = ActionRespBuilder.buildSucceedResp();
				operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
			}
			catch (BMCException e)
			{
				LOG.error("Modify account first password failed, message:" + e.getMessage());

				String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
				response = ActionRespBuilder.buildFailedResp(error);

				operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
			}
		}

		AuditLogUtils.addOperationLog(operLog);

		return response;
	}

	private void logout()
	{
		// 再次退出
		try
		{
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
		}
		catch (Throwable e)
		{
			LOG.error("Subject logout failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditOperation()
	{
		return "bmc.account.password.first.modify.operation";
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