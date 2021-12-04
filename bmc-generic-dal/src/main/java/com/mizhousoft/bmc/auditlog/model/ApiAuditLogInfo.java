package com.mizhousoft.bmc.auditlog.model;

import com.mizhousoft.bmc.auditlog.domain.ApiAuditLog;

/**
 * API日志信息
 *
 * @version
 */
public class ApiAuditLogInfo extends ApiAuditLog
{
	// 创建时间
	private String creationTimeStr;

	// 結果
	private String resultStr;

	/**
	 * 获取creationTimeStr
	 * 
	 * @return
	 */
	public String getCreationTimeStr()
	{
		return creationTimeStr;
	}

	/**
	 * 设置creationTimeStr
	 * 
	 * @param creationTimeStr
	 */
	public void setCreationTimeStr(String creationTimeStr)
	{
		this.creationTimeStr = creationTimeStr;
	}

	/**
	 * 获取resultStr
	 * 
	 * @return
	 */
	public String getResultStr()
	{
		return resultStr;
	}

	/**
	 * 设置resultStr
	 * 
	 * @param resultStr
	 */
	public void setResultStr(String resultStr)
	{
		this.resultStr = resultStr;
	}
}
