package com.mizhousoft.bmc.role.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Strings;
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
import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.antd.TreeNode;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 编辑角色控制器
 *
 * @version
 */
@RestController
public class EditRoleController extends BaseAuditController
{
	private static final Logger LOG = LoggerFactory.getLogger(EditRoleController.class);

	@Autowired
	private PermissionViewService permissionViewService;

	@Autowired
	private RoleViewService roleViewService;

	@Autowired
	private RoleCacheService roleCacheService;

	@RequestMapping(value = "/role/editRole.action", method = RequestMethod.GET)
	public ModelMap editRole(@RequestParam(name = "id") Integer id)
	{
		ModelMap map = new ModelMap();

		try
		{
			Role role = roleViewService.loadById(id);
			map.put("role", role);

			List<Permission> rolePerms = roleViewService.queryPermissionsByRoleName(role.getName());

			List<Permission> authzPerms = permissionViewService.queryAuthzPermissions();

			Set<String> checkedIds = new HashSet<String>(10);
			Set<Integer> halfCheckedIds = new HashSet<Integer>(10);

			List<TreeNode> treeNodes = buildTreeNodes(authzPerms, rolePerms, checkedIds, halfCheckedIds);
			String treeData = JSONUtils.toJSONString(treeNodes);
			map.put("treeData", treeData);

			map.put("checkedKeys", checkedIds);
		}
		catch (BMCException e)
		{
			LOG.error("Fetch role info failed, role id is " + id + '.');

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			map.putAll(ActionRespBuilder.buildFailedMap(error));
		}
		catch (JSONException e)
		{
			LOG.error("TreeNode Object to json string failed.", e);
		}

		return map;
	}

	@RequestMapping(value = "/role/modifyRole.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionResponse modifyRole(@RequestBody RoleRequest request)
	{
		ActionResponse response = null;
		OperationLog operLog = null;

		try
		{
			request.validate();

			Role role = roleViewService.modifyRole(request);

			List<Permission> permissions = roleViewService.queryPermissionsByRoleName(role.getName());

			roleCacheService.refreshRolePermissions(role.getSrvId(), role.getName(), permissions);

			response = ActionRespBuilder.buildSucceedResp();
			operLog = buildOperLog(AuditLogResult.Success, request.toString(), null);
		}
		catch (BMCException | AssertionException e)
		{
			LOG.error("Modify role failed.", e);

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			response = ActionRespBuilder.buildFailedResp(error);

			operLog = buildOperLog(AuditLogResult.Failure, e.getMessage(), request.toString());
		}

		AuditLogUtils.addOperationLog(operLog);

		return response;
	}

	private List<TreeNode> buildTreeNodes(List<Permission> authzPerms, List<Permission> rolePerms, Set<String> checkedIds,
	        Set<Integer> halfCheckedIds)
	{
		TreeNode rootNode = new TreeNode();

		Map<Integer, Permission> rolePermMap = new HashMap<Integer, Permission>(10);
		rolePerms.forEach(perm -> rolePermMap.put(perm.getId(), perm));

		Permission parentPerm = new Permission();
		parentPerm.setName(null);
		recursiveTree(rootNode, parentPerm, authzPerms, rolePermMap, checkedIds, halfCheckedIds);

		return rootNode.getChildren();
	}

	private void recursiveTree(TreeNode parent, Permission parentPerm, List<Permission> perms, Map<Integer, Permission> rolePermMap,
	        Set<String> checkedIds, Set<Integer> halfCheckedIds)
	{
		List<TreeNode> children = new ArrayList<TreeNode>();

		Iterator<Permission> iter = perms.iterator();
		while (iter.hasNext())
		{
			Permission perm = iter.next();

			if (Strings.CS.equals(perm.getParentName(), parentPerm.getName()))
			{
				TreeNode treeNode = new TreeNode();
				treeNode.setKey(perm.getId() + "");
				treeNode.setTitle(perm.getDisplayNameCN());
				children.add(treeNode);

				recursiveTree(treeNode, perm, perms, rolePermMap, checkedIds, halfCheckedIds);
			}
		}

		if (children.isEmpty())
		{
			if (rolePermMap.containsKey(parentPerm.getId()))
			{
				checkedIds.add(parentPerm.getId() + "");
			}

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
		return "bmc.role.modify.operation";
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
