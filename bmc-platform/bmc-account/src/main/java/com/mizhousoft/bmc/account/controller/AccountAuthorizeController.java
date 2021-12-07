package com.mizhousoft.bmc.account.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.request.AccountAuthorizeRequest;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.account.service.AccountRoleSerivce;
import com.mizhousoft.bmc.account.service.AccountService;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 帐号授权控制器
 *
 * @version
 */
@RestController
public class AccountAuthorizeController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(AccountAuthorizeController.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRoleSerivce accountRoleSerivce;

	@Autowired
	private AccountViewService accountViewService;

	@RequestMapping(value = "/account/authorize/fetchRoles.action", method = RequestMethod.GET)
	public ModelMap fetchRoles(RolePageRequest request)
	{
		ModelMap map = new ModelMap();

		Page<Role> page = roleService.queryPageData(request);
		map.addAttribute("dataPage", page);

		return map;
	}

	@RequestMapping(value = "/account/authorize/fetchAccountRoles.action", method = RequestMethod.GET)
	public ModelMap fetchAccountRoles(@RequestParam(name = "accountId") Integer accountId)
	{
		ModelMap map = new ModelMap();

		try
		{
			Account account = accountService.loadById(accountId);
			List<Role> roles = accountViewService.getRoleByAccountId(accountId);

			map.addAttribute("account", account);
			map.addAttribute("selectedRoles", roles);
		}
		catch (BMCException e)
		{
			LOG.error("Fetch account info failed, account id is " + accountId + '.');

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			map.putAll(ActionRespBuilder.buildFailedMap(error));
		}

		return map;
	}

	@RequestMapping(value = "/account/authorizeAccount.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse authorizeAccount(@RequestBody AccountAuthorizeRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			Account account = accountService.loadById(request.getId());

			List<Role> roles = roleService.queryByIds(request.getRoleIds());
			if (roles.size() != request.getRoleIds().size())
			{
				throw new BMCException("bmc.role.not.exist.error", "Role already deleted.");
			}

			accountRoleSerivce.authorizeAccount(account, roles);

			response = ActionRespBuilder.buildSucceedResp();

			String logDetail = buildLogDetail(account, roles);
			operLog = buildOperLog(AuditLogResult.Success, logDetail, request.toString());
		}
		catch (BMCException e)
		{
			LOG.error("Authorize account role failed, message:" + e.getMessage());

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			response = ActionRespBuilder.buildFailedResp(error);

			operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
		}

		AuditLogUtils.addOperationLog(operLog);

		return response;
	}

	/**
	 * 构建日志详情
	 * 
	 * @param account
	 * @param roles
	 * @return
	 */
	private String buildLogDetail(Account account, List<Role> roles)
	{
		StringBuilder buffer = new StringBuilder(100);

		if (CollectionUtils.isEmpty(roles))
		{
			buffer.append("Cancel account ").append(account.getName()).append(" all roles.");
		}
		else
		{
			buffer.append("Grant account ").append(account.getName()).append(" ");

			roles.forEach(role -> buffer.append(role.getName()).append(" "));
			buffer.append("role.");
		}

		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditOperation()
	{
		return "bmc.account.authorize.operation";
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
