package com.mizhousoft.bmc.authentication.listener;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.mizhousoft.bmc.auditlog.constants.AuditLogLevel;
import com.mizhousoft.bmc.auditlog.constants.AuditLogResult;
import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.util.AuditLogUtils;
import com.mizhousoft.boot.authentication.Authentication;
import com.mizhousoft.boot.authentication.event.AccountLogoutEvent;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;

/**
 * 注销事件监听器
 *
 */
@Component
public class AccountLogoutEventListener implements ApplicationListener<AccountLogoutEvent>
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(AccountLogoutEvent event)
	{
		Authentication authentication = event.getAuthentication();

		SecurityLog securityLog = new SecurityLog();

		securityLog.setAccountName(authentication.getName());
		securityLog.setTerminal(authentication.getAccessIPAddress());
		securityLog.setOperation(I18nUtils.getMessage("bmc.account.logout.operation"));
		securityLog.setCreationTime(LocalDateTime.now());
		securityLog.setSource(I18nUtils.getMessage("bmc.account.source"));
		securityLog.setLogLevel(AuditLogLevel.INFO.getValue());
		securityLog.setResult(AuditLogResult.Success.getValue());

		AuditLogUtils.addSecurityLog(securityLog);
	}
}
