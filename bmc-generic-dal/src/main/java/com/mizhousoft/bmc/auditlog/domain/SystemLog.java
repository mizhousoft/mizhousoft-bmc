package com.mizhousoft.bmc.auditlog.domain;

/**
 * 系统日志持久层对象
 * 
 * @version
 */
public class SystemLog extends BaseAuditLog
{
	// 基本信息
	private String baseInfo;

	/**
	 * 获取baseInfo
	 * 
	 * @return
	 */
	public String getBaseInfo()
	{
		return baseInfo;
	}

	/**
	 * 设置baseInfo
	 * 
	 * @param baseInfo
	 */
	public void setBaseInfo(String baseInfo)
	{
		this.baseInfo = baseInfo;
	}
}
