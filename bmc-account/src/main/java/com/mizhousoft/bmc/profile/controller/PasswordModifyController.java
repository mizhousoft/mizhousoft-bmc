package com.mizhousoft.bmc.profile.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
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
import com.mizhousoft.bmc.system.domain.PasswordStrategy;
import com.mizhousoft.bmc.system.service.PasswordStrategyService;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 修改密码控制器
 *
 * @version
 */
@RestController
public class PasswordModifyController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(PasswordModifyController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountPasswdService accountPasswdService;

	@Autowired
	private PasswordStrategyService passwordStrategyService;

	@RequestMapping(value = "/setting/password/fetchPasswordStrategy.action", method = RequestMethod.GET)
	public ModelMap fetchAccountPasswordStrategy()
	{
		ModelMap map = new ModelMap();

		PasswordStrategy passwordStrategy = passwordStrategyService.queryPasswordStrategy();
		map.put("passwordStrategy", passwordStrategy);

		return map;
	}

	@RequestMapping(value = "/setting/password/modifyPassword.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse modifyPassword(@Valid @RequestBody AccountPasswordRequest request, BindingResult bindingResult)
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

				accountPasswdService.modifyPassword(account, request.getPassword(), request.getNewPassword());

				response = ActionRespBuilder.buildSucceedResp();
				operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
			}
			catch (BMCException e)
			{
				LOG.error("Modify account password failed, message:" + e.getMessage());

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
		return "bmc.account.password.modify.operation";
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
