package com.mizhousoft.bmc.system.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.system.model.OnlineAccount;
import com.mizhousoft.bmc.system.request.LogoffAccountRequest;
import com.mizhousoft.bmc.system.service.OnlineAccountService;
import com.mizhousoft.boot.authentication.AccountDetails;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 注销在线帐号控制器
 *
 * @version
 */
@RestController
public class OnlineAccountController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(OnlineAccountController.class);

	@Autowired
	private OnlineAccountService onlineAccountService;

	@RequestMapping(value = "/system/fetchOnlineAccounts.action", method = RequestMethod.GET)
	public ModelMap fetchOnlineAccounts(PageRequest pageRequest)
	{
		Subject subject = SecurityUtils.getSubject();
		Session currentSessison = subject.getSession();

		Page<OnlineAccount> page = onlineAccountService.queryOnlineAccounts(currentSessison, pageRequest);

		ModelMap map = new ModelMap();
		map.addAttribute("dataPage", page);

		return map;
	}

	/**
	 * 注销帐号
	 * 
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/system/logoffOnlineAccount.action", method = RequestMethod.POST)
	public @ResponseBody ActionResponse logoffOnlineAccount(@RequestBody LogoffAccountRequest request, BindingResult result)
	{
		ActionResponse response = null;
		OperationLog log = null;

		try
		{
			long accountId = request.getId();
			String randomId = request.getRandomId();
			AccountDetails accountDetails = onlineAccountService.logoffAccount(accountId, randomId);

			if (null != accountDetails)
			{
				log = buildOperLog(AuditLogResult.Success, "Logoff " + accountDetails.getAccountName() + ".", request.toString());
			}

			response = ActionRespBuilder.buildSucceedResp();
		}
		catch (BMCException e)
		{
			LOG.error("Logoff online account failed.", e);

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			response = ActionRespBuilder.buildFailedResp(error);

			log = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
		}

		AuditLogUtils.addOperationLog(log);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditOperation()
	{
		return "bmc.online.account.logoff.operation";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditSource()
	{
		return "bmc.online.account.source";
	}
}
