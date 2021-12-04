package com.mizhousoft.bmc.authentication.controller;

import java.util.Calendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.authentication.response.AccountResource;
import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.context.SecurityContextHolder;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 抓取我的帐号详情控制器
 *
 * @version
 */
@RestController
public class MyAccountDetailFetchController
{
	@Autowired
	private AccountViewService accountBusinessService;

	@RequestMapping(value = "/account/fetchMyAccountDetail.action", method = RequestMethod.GET)
	public ModelMap fetchMyAccountDetail()
	{
		ModelMap map = new ModelMap();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long id = authentication.getAccountId();
		AccountResource accountRes = new AccountResource();

		accountRes.setName(authentication.getName());

		Set<String> perms = accountBusinessService.getPermByAccountId(id);
		accountRes.setPermissions(perms);

		map.addAttribute("account", accountRes);

		Calendar cal = Calendar.getInstance();
		String[] params = { String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.MONTH) + 1),
		        String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) };
		String nowTimeText = I18nUtils.getMessage("bmc.system.time.text", params);
		map.addAttribute("nowTime", nowTimeText);

		return map;
	}
}
