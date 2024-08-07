package com.mizhousoft.bmc.authentication.controller;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.account.service.AccountViewService;
import com.mizhousoft.bmc.authentication.model.AccountViewData;
import com.mizhousoft.bmc.authentication.service.ApplicationFirstLoadService;
import com.mizhousoft.bmc.role.service.PermissionService;
import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.context.SecurityContextHolder;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
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
	private AccountViewService accountViewService;

	@Autowired(required = false)
	private ApplicationFirstLoadService applicationFirstLoadService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	@Autowired
	private PermissionService permissionService;

	@RequestMapping(value = "/account/fetchMyAccountDetail.action", method = RequestMethod.GET)
	public ModelMap fetchMyAccountDetail()
	{
		ModelMap map = new ModelMap();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long id = authentication.getAccountId();
		AccountViewData viewData = new AccountViewData();

		viewData.setName(authentication.getName());

		String srvId = applicationAuthService.getServiceId();
		Set<String> authcPermIds = permissionService.queryAuthcPermIds(srvId);

		Set<String> perms = accountViewService.getPermByAccountId(id);
		perms.addAll(authcPermIds);

		viewData.setPermissions(perms);

		map.addAttribute("account", viewData);

		Calendar cal = Calendar.getInstance();
		String[] params = { String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.MONTH) + 1),
		        String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) };
		String nowTimeText = I18nUtils.getMessage("bmc.system.time.text", params);
		map.addAttribute("nowTime", nowTimeText);

		if (null != applicationFirstLoadService)
		{
			Map<String, Object> loadedMap = applicationFirstLoadService.obtainLoadedData();
			map.putAll(loadedMap);
		}

		return map;
	}
}
