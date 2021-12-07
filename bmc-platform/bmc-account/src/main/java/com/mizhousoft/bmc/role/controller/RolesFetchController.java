package com.mizhousoft.bmc.role.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.bmc.role.request.RolePageRequest;
import com.mizhousoft.bmc.role.service.RoleViewService;
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
	private RoleViewService roleViewService;

	@RequestMapping(value = "/role/fetchRoles.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelMap fetchRoles(@RequestBody RolePageRequest request)
	{
		ModelMap map = new ModelMap();

		Page<Role> page = roleViewService.queryPageData(request);
		map.addAttribute("dataPage", page);

		return map;
	}
}
