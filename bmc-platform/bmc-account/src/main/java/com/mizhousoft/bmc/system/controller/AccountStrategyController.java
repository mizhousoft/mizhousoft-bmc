package com.mizhousoft.bmc.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.system.domain.AccountStrategy;
import com.mizhousoft.bmc.system.request.AccountStrategyRequest;
import com.mizhousoft.bmc.system.service.AccountStrategyService;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 修改帐号策略控制器
 *
 * @version
 */
@RestController
public class AccountStrategyController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(AccountStrategyController.class);

	@Autowired
	private AccountStrategyService accountStrategyService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	@RequestMapping(value = "/system/fetchAccountStrategy.action", method = RequestMethod.GET)
	public ModelMap fetchAccountStrategy()
	{
		ModelMap map = new ModelMap();

		String serviceId = applicationAuthService.getServiceId();

		AccountStrategy accountStrategy = accountStrategyService.getAccountStrategy(serviceId);
		map.put("strategy", accountStrategy);

		return map;
	}

	@RequestMapping(value = "/system/modifyAccountStrategy.action", method = RequestMethod.POST)
	public ActionResponse modifyAccountStrategy(@RequestBody AccountStrategyRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			request.validate();

			String serviceId = applicationAuthService.getServiceId();

			AccountStrategy accountStrategy = new AccountStrategy();
			accountStrategy.setId(request.getId());
			accountStrategy.setAccountUnusedDay(request.getAccountUnusedDay());
			accountStrategy.setTimeLimitPeriod(request.getTimeLimitPeriod());
			accountStrategy.setLoginLimitNumber(request.getLoginLimitNumber());
			accountStrategy.setAccountLockTime(request.getAccountLockTime());
			accountStrategy.setLockTimeStrategy(request.getLockTimeStrategy());

			accountStrategyService.modifyAccountStrategy(serviceId, accountStrategy);

			response = ActionRespBuilder.buildSucceedResp();
			operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
		}
		catch (BMCException | AssertionException e)
		{
			LOG.error("Modify account strategy failed.", e);

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
		return "bmc.account.strategy.modify.operation";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditSource()
	{
		return "bmc.account.strategy.source";
	}
}
