package com.mizhousoft.bmc.profile.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.BMCException;
import com.mizhousoft.bmc.account.domain.Account;
import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.role.domain.Role;
import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.context.SecurityContextHolder;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 抓取我的帐号信息
 *
 * @version
 */
@RestController
public class MyAccountInfoFetchController
{
	private static final Logger LOG = LoggerFactory.getLogger(MyAccountInfoFetchController.class);

	@Autowired
	private AccountViewService accountViewService;

	@RequestMapping(value = "/setting/account/fetchMyAccountInfo.action", method = RequestMethod.GET)
	public ModelMap fetchMyAccountInfo()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long id = authentication.getAccountId();

		ModelMap map = new ModelMap();

		try
		{
			Account account = accountViewService.loadById(id);
			map.put("account", account);

			List<Role> roles = accountViewService.getRoleByAccountId(id);
			map.put("roles", roles);
		}
		catch (BMCException e)
		{
			LOG.error("Fetch account failed.", e);

			String error = I18nUtils.getMessage(e.getErrorCode(), e.getCodeParams());
			map.putAll(ActionRespBuilder.buildFailedMap(error));
		}

		return map;
	}
}
