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

import com.mizhousoft.bmc.auditlog.domain.OperationLog;
import com.mizhousoft.bmc.auditlog.model.OperationLogInfo;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.service.OperationLogService;
import com.mizhousoft.bmc.auditlog.util.AuditLogI18nUtils;
import com.mizhousoft.bmc.auditlog.util.AuditLogRequestUtils;
import com.mizhousoft.boot.authentication.service.ApplicationAuthenticationService;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

/**
 * 抓取操作日志控制器
 * 
 * @version
 */
@RestController
public class OperationLogsFetchController
{
	@Autowired
	private OperationLogService operationLogService;

	@Autowired
	private ApplicationAuthenticationService applicationAuthService;

	@RequestMapping(value = "/auditlog/fetchOperationLogs.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelMap fetchOperationLogs(@RequestBody AuditLogPageRequest request)
	{
		AuditLogRequestUtils.handleRequest(request);

		String serviceId = applicationAuthService.getServiceId();
		request.setSrvId(serviceId);

		ModelMap map = new ModelMap();

		Page<OperationLog> page = operationLogService.queryPageData(request);

		Page<OperationLogInfo> dataPage = convertDataPage(page, request);
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
	private Page<OperationLogInfo> convertDataPage(Page<OperationLog> page, AuditLogPageRequest request)
	{
		List<OperationLogInfo> infos = new ArrayList<OperationLogInfo>(page.getPageNumber());

		page.getContent().forEach(auditLog -> {
			OperationLogInfo info = new OperationLogInfo();

			String creationTimeStr = LocalDateTimeUtils.formatYmdhms(auditLog.getCreationTime());
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

		Page<OperationLogInfo> dataPage = PageBuilder.build(infos, request, page.getTotalNumber());
		return dataPage;
	}
}
