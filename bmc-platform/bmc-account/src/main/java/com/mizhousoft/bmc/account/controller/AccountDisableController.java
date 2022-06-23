package com.mizhousoft.bmc.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.request.AccountRequest;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.boot.authentication.AccountSessionService;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 禁用帐号控制器
 *
 * @version
 */
@RestController
public class AccountDisableController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(AccountDisableController.class);

	@Autowired
	private AccountViewService accountService;

	@Autowired
	private AccountSessionService accountSessionService;

	@RequestMapping(value = "/account/disableAccount.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse disableAccount(@RequestBody AccountRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			Account account = accountService.disableAccount(request.getId());
			String detail = "Disable " + account.getName() + " account.";
			operLog = buildOperLog(AuditLogResult.Success, detail, account.toString());

			accountSessionService.logoffAccount(account.getId());

			response = ActionRespBuilder.buildSucceedResp();
		}
		catch (BMCException e)
		{
			LOG.error("Disable account failed, message: " + e.getMessage());

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
		return "bmc.account.disable.operation";
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
