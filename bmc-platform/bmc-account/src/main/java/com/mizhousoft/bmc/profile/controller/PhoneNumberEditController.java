package com.mizhousoft.bmc.profile.controller;

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
import com.mizhousoft.bmc.account.request.PhoneNumberEditRequest;
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.context.SecurityContextHolder;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 修改手机号控制器
 *
 * @version
 */
@RestController
public class PhoneNumberEditController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(PhoneNumberEditController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountViewService accountViewService;

	@RequestMapping(value = "/setting/account/modifyPhoneNumber.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse modifyPhoneNumber(@RequestBody PhoneNumberEditRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			request.validate();

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			long id = authentication.getAccountId();

			accountViewService.loadById(id);

			Account account = new Account();
			account.setId(id);
			account.setPhoneNumber(request.getPhoneNumber());

			accountService.modifyPhoneNumber(account);

			response = ActionRespBuilder.buildSucceedResp();
			operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
		}
		catch (BMCException | AssertionException e)
		{
			LOG.error("Modify phone number failed, message:" + e.getMessage());

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
		return "bmc.profile.phonenumber.modify.operation";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditSource()
	{
		return "bmc.profile.source";
	}
}
