package com.mizhousoft.bmc.auditlog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.bmc.auditlog.domain.SecurityLog;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.response.SecurityLogInfo;
import com.mizhousoft.bmc.auditlog.service.SecurityLogService;
import com.mizhousoft.bmc.auditlog.util.AuditLogI18nUtils;
import com.mizhousoft.bmc.auditlog.util.AuditLogRequestUtils;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.lang.DateUtils;

/**
 * 抓取安全日志控制器
 * 
 * @version
 */
@RestController
public class SecurityLogsFetchController
{
	@Autowired
	private SecurityLogService securityLogService;

	@RequestMapping(value = "/auditlog/fetchSecurityLogs.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelMap fetchSecurityLogs(@RequestBody AuditLogPageRequest request)
	{
		AuditLogRequestUtils.handleRequest(request);

		ModelMap map = new ModelMap();

		Page<SecurityLog> page = securityLogService.queryPageData(request);

		Page<SecurityLogInfo> dataPage = convertDataPage(page, request);
		map.addAttribute("dataPage", dataPage);

		return map;
	}

	/**
	 * 转换日志对象
	 * 
	 * @param page
	 * @param request
	 * @return
	 */
	private Page<SecurityLogInfo> convertDataPage(Page<SecurityLog> page, AuditLogPageRequest request)
	{
		List<SecurityLogInfo> infos = new ArrayList<SecurityLogInfo>(page.getPageNumber());

		page.getContent().forEach(auditLog -> {
			SecurityLogInfo info = new SecurityLogInfo();

			String creationTimeStr = DateUtils.formatYmdhms(auditLog.getCreationTime());
			String logLevelStr = AuditLogI18nUtils.getLevelText(auditLog.getLogLevel());
			String resultStr = AuditLogI18nUtils.getResultText(auditLog.getResult());

			info.setId(auditLog.getId());
			info.setAccountName(auditLog.getAccountName());
			info.setAddInfo(auditLog.getAddInfo());
			info.setDetail(auditLog.getDetail());
			info.setCreationTime(auditLog.getCreationTime());
			info.setCreationTimeStr(creationTimeStr);
			info.setLogLevel(logLevelStr);
			info.setOperation(auditLog.getOperation());
			info.setResult(auditLog.getResult());
			info.setResultStr(resultStr);
			info.setSource(auditLog.getSource());
			info.setTerminal(auditLog.getTerminal());

			infos.add(info);
		});

		Page<SecurityLogInfo> dataPage = PageBuilder.build(infos, request, page.getTotalNumber());
		return dataPage;
	}
}
