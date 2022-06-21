package com.mizhousoft.bmc.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.account.model.AccountInfo;
import com.mizhousoft.bmc.account.request.AccountPageRequest;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 抓取帐号列表控制器
 *
 * @version
 */
@RestController
public class AccountInfoListFetchController
{
	@Autowired
	private AccountViewService accountViewService;

	@RequestMapping(value = "/account/fetchAccountInfoList.action", method = RequestMethod.GET)
	public ModelMap fetchAccountInfoList(AccountPageRequest request)
	{
		ModelMap map = new ModelMap();

		Page<AccountInfo> page = accountViewService.queryAccountInfos(request);
		map.addAttribute("dataPage", page);

		return map;
	}
}
