package com.mizhousoft.bmc.account.util;

import java.util.HashMap;
import java.util.Map;

import com.mizhousoft.bmc.account.constant.AccountStatus;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 帐号状态国际化
 *
 * @version
 */
public class AccountStatus18nUtils
{
	private static final Map<Integer, String> I18N_MAP = new HashMap<Integer, String>(2);

	static
	{
		I18N_MAP.put(AccountStatus.NOT_CREATED.getValue(), "bmc.account.status.notcreated");
		I18N_MAP.put(AccountStatus.Enable.getValue(), "bmc.account.status.enable");
		I18N_MAP.put(AccountStatus.Disable.getValue(), "bmc.account.status.disable");
		I18N_MAP.put(AccountStatus.Locked.getValue(), "bmc.account.status.locked");
	}

	/**
	 * 获取国际化
	 * 
	 * @param key
	 * @return
	 */
	public static String getText(int key)
	{
		String value = I18N_MAP.get(key);
		String i18nText = I18nUtils.getMessage(value);
		return i18nText;
	}
}
