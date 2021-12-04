package com.mizhousoft.bmc.system.constant;

/**
 * 密码策略常量
 *
 * @version
 */
public interface PasswordStrategyConstants
{
	// domain
	String DOMAIN = "password-strategy";

	// 密码不能与历史密码重复次数
	String HISTORYREPEATSIZE = "historyRepeatSize";

	// 密码中允许同一个字符出现的次数
	String CHARAPPEARSIZE = "charAppearSize";

	// 密码修改最短时间间隔，单位分钟
	String MODIFYTIMEINTERVAL = "modifyTimeInterval";

	// 密码有效期，单位天
	String VALIDDAY = "validDay";

	// 距离密码到期，提醒用户修改的天数，单位天
	String REMINDERMODIFYDAY = "reminderModifyDay";

	// 密码不能与历史密码重复次数
	int HISTORY_REPEAT_SIZE = 3;

	// 密码中允许同一个字符出现的次数
	int CHAR_APPEAR_SIZE = 2;

	// 修改密码时间间隔
	int MODIFY_TIME_INTERVAL = 5;

	// 提醒修改密码天数
	int REMINDER_MODIFY_DAY = 7;

	// 密码有效期
	int VALID_DAY = 90;
}
