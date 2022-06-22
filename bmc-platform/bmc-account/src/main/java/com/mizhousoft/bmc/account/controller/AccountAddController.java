package com.mizhousoft.bmc.account.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
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
import com.mizhousoft.bmc.account.model.AuthAccount;
import com.mizhousoft.bmc.account.request.AccountNewRequest;
import com.mizhousoft.bmc.account.service.AccountPasswdService;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.account.validator.AccountRequestValidator;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.bmc.role.service.RoleViewService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 增加帐号控制器
 *
 * @version
 */
@RestController
public class AccountAddController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(AccountAddController.class);

	@Autowired
	private RoleViewService roleViewService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountViewService accountViewService;

	@Autowired
	private AccountPasswdService accountPasswdService;

	@RequestMapping(value = "/account/new/fetchRoles.action", method = RequestMethod.GET)
	public ModelMap fetchRoles(RolePageRequest pageRequest)
	{
		ModelMap map = new ModelMap();

		Page<Role> page = roleViewService.queryPageData(pageRequest);
		map.addAttribute("dataPage", page);

		return map;
	}

	@RequestMapping(value = "/account/addAccount.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse addAccount(@Valid @RequestBody
	AccountNewRequest request, BindingResult bindingResult)
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
				AccountRequestValidator.validate(request);
				accountPasswdService.checkPassword(request.getName(), request.getPassword());

				List<Role> roles = buildAccountRoles(request);

				AuthAccount account = new AuthAccount();
				account.setName(request.getName());
				account.setPassword(request.getPassword());
				account.setStatus(request.getStatus());
				account.setPhoneNumber(request.getPhoneNumber());

				accountViewService.addAccount(account, roles);

				response = ActionRespBuilder.buildSucceedResp();
				operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
			}
			catch (BMCException e)
			{
				LOG.error("Add account failed, message: {}", e.getMessage());

				String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
				response = ActionRespBuilder.buildFailedResp(error);

				operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
			}
		}

		AuditLogUtils.addOperationLog(operLog);

		return response;
	}

	/**
	 * 构建帐号角色
	 * 
	 * @param request
	 * @return
	 * @throws BMCException
	 */
	private List<Role> buildAccountRoles(AccountNewRequest request) throws BMCException
	{
		Integer[] roleIds = request.getRoleIds();
		if (ArrayUtils.isNotEmpty(roleIds))
		{
			List<Integer> rIds = Arrays.asList(roleIds);
			List<Role> roles = roleService.queryByIds(rIds);
			if (roles.size() != roleIds.length)
			{
				throw new BMCException("bmc.role.not.exist.error", "Role not found, role ids are " + Arrays.toString(roleIds) + '.');
			}

			return roles;
		}
		else
		{
			return Collections.emptyList();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditOperation()
	{
		return "bmc.account.add.operation";
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
