package com.mizhousoft.bmc.auditlog.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mizhousoft.commons.data.domain.PageRequest;

/**
 * 审计日志分页请求
 *
 * @version
 */
public class AuditLogPageRequest extends PageRequest
{
	private static final long serialVersionUID = -2461181741359339018L;

	// 服务ID
	private String srvId;

	// 日志级别
	private String[] logLevels;

	// 结果
	private int[] results;

	// 开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private LocalDateTime beginTime;

	// 结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private LocalDateTime endTime;

	// 操作
	private String operation;

	// 帐号
	private String accountName;

	// 终端
	private String terminal;

	// 来源
	private String source;

	// 基本信息
	private String baseInfo;

	/**
	 * 获取srvId
	 * 
	 * @return
	 */
	public String getSrvId()
	{
		return srvId;
	}

	/**
	 * 设置srvId
	 * 
	 * @param srvId
	 */
	public void setSrvId(String srvId)
	{
		this.srvId = srvId;
	}

	/**
	 * 获取logLevels
	 * 
	 * @return
	 */
	public String[] getLogLevels()
	{
		return logLevels;
	}

	/**
	 * 设置logLevels
	 * 
	 * @param logLevels
	 */
	public void setLogLevels(String[] logLevels)
	{
		this.logLevels = logLevels;
	}

	/**
	 * 获取results
	 * 
	 * @return
	 */
	public int[] getResults()
	{
		return results;
	}

	/**
	 * 设置results
	 * 
	 * @param results
	 */
	public void setResults(int[] results)
	{
		this.results = results;
	}

	/**
	 * 获取beginTime
	 * 
	 * @return
	 */
	public LocalDateTime getBeginTime()
	{
		return beginTime;
	}

	/**
	 * 设置beginTime
	 * 
	 * @param beginTime
	 */
	public void setBeginTime(LocalDateTime beginTime)
	{
		this.beginTime = beginTime;
	}

	/**
	 * 获取endTime
	 * 
	 * @return
	 */
	public LocalDateTime getEndTime()
	{
		return endTime;
	}

	/**
	 * 设置endTime
	 * 
	 * @param endTime
	 */
	public void setEndTime(LocalDateTime endTime)
	{
		this.endTime = endTime;
	}

	/**
	 * 获取operation
	 * 
	 * @return
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * 设置operation
	 * 
	 * @param operation
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * 获取accountName
	 * 
	 * @return
	 */
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * 设置accountName
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	/**
	 * 获取terminal
	 * 
	 * @return
	 */
	public String getTerminal()
	{
		return terminal;
	}

	/**
	 * 设置terminal
	 * 
	 * @param terminal
	 */
	public void setTerminal(String terminal)
	{
		this.terminal = terminal;
	}

	/**
	 * 获取source
	 * 
	 * @return
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * 设置source
	 * 
	 * @param source
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

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
