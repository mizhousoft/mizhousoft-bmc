package com.mizhousoft.bmc.role.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.service.RoleService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 抓取角色数据控制器
 *
 * @version
 */
@RestController
public class RolesFetchController
{
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/role/fetchRoles.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelMap fetchRoles(@RequestBody RolePageRequest request)
	{
		ModelMap map = new ModelMap();

		request.setName(StringUtils.trimToNull(request.getName()));

		Page<Role> page = roleService.queryRoles(request);
		map.addAttribute("dataPage", page);

		return map;
	}
}
