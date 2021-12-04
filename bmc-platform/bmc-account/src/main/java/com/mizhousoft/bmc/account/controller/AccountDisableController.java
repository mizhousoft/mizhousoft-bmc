package com.mizhousoft.bmc.account.controller;

import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
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
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.boot.authentication.AccountDetails;
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
	private AccountService accountService;

	@Autowired
	private SessionDAO sessionDAO;

	@RequestMapping(value = "/account/disableAccount.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse disableAccount(@RequestBody AccountRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			Account account = accountService.loadById(request.getId());
			if (null != account)
			{
				accountService.disableAccount(account);
				String detail = "Disable " + account.getName() + " account.";
				operLog = buildOperLog(AuditLogResult.Success, detail, account.toString());

				logoutSessionAccount(account);
			}
			else
			{
				operLog = buildOperLog(AuditLogResult.Success, "Account already has been deleted.", request.toString());
			}

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

	private void logoutSessionAccount(Account account)
	{
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for (Session session : sessions)
		{
			Object simplePrincipalCollection = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if (null != simplePrincipalCollection && simplePrincipalCollection instanceof SimplePrincipalCollection)
			{
				Object primaryPrincipal = ((SimplePrincipalCollection) simplePrincipalCollection).getPrimaryPrincipal();
				if (primaryPrincipal instanceof AccountDetails)
				{
					if (((AccountDetails) primaryPrincipal).getAccountId() == account.getId())
					{
						sessionDAO.delete(session);
						LOG.info("Force to logoff session, account id is " + account.getId() + '.');
					}
				}
			}
		}
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
