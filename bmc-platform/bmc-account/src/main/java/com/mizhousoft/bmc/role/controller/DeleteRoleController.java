package com.mizhousoft.bmc.role.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RoleDeleteRequest;
import com.mizhousoft.bmc.role.service.RoleCacheService;
import com.mizhousoft.bmc.role.service.RoleViewService;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 删除角色控制器
 *
 * @version
 */
@RestController
public class DeleteRoleController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(DeleteRoleController.class);

	@Autowired
	private RoleViewService roleViewService;

	@Autowired
	private RoleCacheService roleCacheService;

	@RequestMapping(value = "/role/deleteRole.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse deleteRole(@RequestBody RoleDeleteRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			Role role = roleViewService.deleteRole(request.getId());
			if (null != role)
			{
				roleCacheService.deleteByRoleName(role.getSrvId(), role.getName());

				operLog = buildOperLog(AuditLogResult.Success, role.toString(), null);
			}
			else
			{
				operLog = buildOperLog(AuditLogResult.Success, "Role already has been deleted.", request.toString());
			}

			response = ActionRespBuilder.buildSucceedResp();
		}
		catch (BMCException e)
		{
			LOG.error("Delete role failed, role id is " + request.getId() + '.', e);

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
		return "bmc.role.delete.operation";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditSource()
	{
		return "bmc.role.source";
	}
}
