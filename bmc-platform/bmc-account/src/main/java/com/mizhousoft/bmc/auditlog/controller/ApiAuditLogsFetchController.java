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

import com.mizhousoft.bmc.auditlog.domain.ApiAuditLog;
import com.mizhousoft.bmc.auditlog.model.ApiAuditLogInfo;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.service.ApiAuditLogService;
import com.mizhousoft.bmc.auditlog.util.AuditLogI18nUtils;
import com.mizhousoft.bmc.auditlog.util.AuditLogRequestUtils;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.lang.DateUtils;

/**
 * 抓取API日志控制器
 * 
 * @version
 */
@RestController
public class ApiAuditLogsFetchController
{
	@Autowired
	private ApiAuditLogService apiAuditLogService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	@RequestMapping(value = "/auditlog/fetchApiAuditLogs.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelMap fetchApiAuditLogs(@RequestBody
	AuditLogPageRequest request)
	{
		AuditLogRequestUtils.handleRequest(request);

		String serviceId = applicationAuthService.getServiceId();
		request.setSrvId(serviceId);

		ModelMap map = new ModelMap();

		Page<ApiAuditLog> page = apiAuditLogService.queryPageData(request);

		Page<ApiAuditLogInfo> dataPage = convertDataPage(page, request);
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
	private Page<ApiAuditLogInfo> convertDataPage(Page<ApiAuditLog> page, AuditLogPageRequest request)
	{
		List<ApiAuditLogInfo> infos = new ArrayList<>(page.getPageNumber());

		page.getContent().forEach(auditLog -> {
			ApiAuditLogInfo info = new ApiAuditLogInfo();

			String creationTimeStr = DateUtils.formatYmdhms(auditLog.getCreationTime());
			String logLevelStr = AuditLogI18nUtils.getLevelText(auditLog.getLogLevel());
			String resultStr = AuditLogI18nUtils.getResultText(auditLog.getResult());

			info.setId(auditLog.getId());
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

		Page<ApiAuditLogInfo> dataPage = PageBuilder.build(infos, request, page.getTotalNumber());
		return dataPage;
	}
}
