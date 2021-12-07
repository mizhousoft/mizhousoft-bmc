package com.mizhousoft.bmc.role.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.controller.BaseAuditController;
import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RoleRequest;
import com.mizhousoft.bmc.role.service.PermissionViewService;
import com.mizhousoft.bmc.role.service.RoleCacheService;
import com.mizhousoft.bmc.role.service.RoleViewService;
import com.mizhousoft.commons.json.JSONException;
import com.mizhousoft.commons.json.JSONUtils;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.antd.TreeNode;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 创建角色控制器
 *
 * @version
 */
@RestController
public class NewRoleController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(NewRoleController.class);

	@Autowired
	private PermissionViewService permissionViewService;

	@Autowired
	private RoleViewService roleViewService;

	@Autowired
	private RoleCacheService roleCacheService;

	@RequestMapping(value = "/role/newRole.action", method = RequestMethod.GET)
	public ModelMap newRole()
	{
		ModelMap map = new ModelMap();

		try
		{
			List<Permission> permissions = permissionViewService.queryAuthzPermissions();

			List<TreeNode> treeNodes = buildTreeNodes(permissions);
			String treeData = JSONUtils.toJSONString(treeNodes);
			map.put("treeData", treeData);
		}
		catch (JSONException e)
		{
			LOG.error("TreeNode Object to json string failed.", e);
		}

		return map;
	}

	@RequestMapping(value = "/role/addRole.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse addRole(@Valid @RequestBody RoleRequest request, BindingResult bindingResult)
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
				Role role = roleViewService.addRole(request);

				List<Permission> permissions = roleViewService.queryPermissionsByRoleName(role.getName());

				roleCacheService.addRolePermissions(role.getName(), permissions);

				response = ActionRespBuilder.buildSucceedResp();
				operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
			}
			catch (BMCException e)
			{
				LOG.error("New role failed.", e);

				String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
				response = ActionRespBuilder.buildFailedResp(error);

				operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
			}
		}

		AuditLogUtils.addOperationLog(operLog);

		return response;
	}

	private List<TreeNode> buildTreeNodes(List<Permission> permissions)
	{
		Permission parentPerm = new Permission();
		parentPerm.setName(null);

		TreeNode rootNode = new TreeNode();
		recursiveTree(rootNode, parentPerm, permissions);

		return rootNode.getChildren();
	}

	private void recursiveTree(TreeNode parent, Permission parentPerm, List<Permission> perms)
	{
		List<TreeNode> children = new ArrayList<TreeNode>();

		Iterator<Permission> iter = perms.iterator();
		while (iter.hasNext())
		{
			Permission perm = iter.next();

			if (StringUtils.equals(perm.getParentName(), parentPerm.getName()))
			{
				TreeNode treeNode = new TreeNode();
				treeNode.setKey(perm.getId() + "");
				treeNode.setTitle(perm.getDisplayNameCN());
				children.add(treeNode);

				recursiveTree(treeNode, perm, perms);
			}
		}

		if (children.isEmpty())
		{
			parent.setLeaf(true);
		}
		else
		{
			parent.setChildren(children);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAuditOperation()
	{
		return "bmc.role.add.operation";
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
