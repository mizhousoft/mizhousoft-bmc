package com.mizhousoft.bmc.role.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.role.domain.Permission;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.bmc.role.service.RoleViewService;
import com.mizhousoft.commons.json.JSONException;
import com.mizhousoft.commons.json.JSONUtils;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.antd.TreeNode;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 角色信息抓取控制器
 *
 * @version
 */
@RestController
public class RoleInfoFetchController
{
	private static final Logger LOG = LoggerFactory.getLogger(RoleInfoFetchController.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleViewService roleViewService;

	@RequestMapping(value = "/role/fetchRoleInfo.action", method = RequestMethod.GET)
	public ModelMap fetchRoleInfo(@RequestParam(name = "id") Integer id)
	{
		ModelMap map = new ModelMap();

		try
		{
			Role role = roleService.loadById(id);
			map.put("role", role);

			List<Permission> rolePerms = roleViewService.queryPermissionsByRoleName(role.getName());

			List<TreeNode> treeNodes = buildTreeNodes(rolePerms);
			String treeData = JSONUtils.toJSONString(treeNodes);
			map.put("treeData", treeData);
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

	private List<TreeNode> buildTreeNodes(List<Permission> rolePerms)
	{
		TreeNode rootNode = new TreeNode();

		Permission parentPerm = new Permission();
		parentPerm.setName(null);

		recursiveTree(rootNode, parentPerm, rolePerms);

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
}
