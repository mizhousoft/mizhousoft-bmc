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

import com.mizhousoft.bmc.auditlog.domain.SystemLog;
import com.mizhousoft.bmc.auditlog.model.SystemLogInfo;
import com.mizhousoft.bmc.auditlog.request.AuditLogPageRequest;
import com.mizhousoft.bmc.auditlog.service.SystemLogService;
import com.mizhousoft.bmc.auditlog.util.AuditLogI18nUtils;
import com.mizhousoft.bmc.auditlog.util.AuditLogRequestUtils;
import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.lang.DateUtils;

/**
 * 查询系统日志控制器
 * 
 * @version
 */
@RestController
public class SystemLogsFetchController
{
	@Autowired
	private SystemLogService systemLogService;

	@RequestMapping(value = "/auditlog/fetchSystemLogs.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelMap fetchSystemLogs(@RequestBody AuditLogPageRequest request)
	{
		AuditLogRequestUtils.handleRequest(request);

		ModelMap map = new ModelMap();

		Page<SystemLog> page = systemLogService.queryPageData(request);

		Page<SystemLogInfo> dataPage = convertDataPage(page, request);
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
	private Page<SystemLogInfo> convertDataPage(Page<SystemLog> page, AuditLogPageRequest request)
	{
		List<SystemLogInfo> infos = new ArrayList<SystemLogInfo>(page.getPageNumber());

		page.getContent().forEach(auditLog -> {
			SystemLogInfo info = new SystemLogInfo();

			String creationTimeStr = DateUtils.formatYmdhms(auditLog.getCreationTime());
			String logLevelStr = AuditLogI18nUtils.getLevelText(auditLog.getLogLevel());
			String resultStr = AuditLogI18nUtils.getResultText(auditLog.getResult());

			info.setId(auditLog.getId());
			info.setBaseInfo(auditLog.getBaseInfo());
			info.setAddInfo(auditLog.getAddInfo());
			info.setDetail(auditLog.getDetail());
			info.setCreationTime(auditLog.getCreationTime());
			info.setCreationTimeStr(creationTimeStr);
			info.setLogLevel(logLevelStr);
			info.setResult(auditLog.getResult());
			info.setResultStr(resultStr);
			info.setSource(auditLog.getSource());

			infos.add(info);
		});

		Page<SystemLogInfo> dataPage = PageBuilder.build(infos, request, page.getTotalNumber());
		return dataPage;
	}
}
