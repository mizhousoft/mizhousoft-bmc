package com.mizhousoft.bmc.auditlog.domain;

import java.util.Date;

/**
 * 审计日志基类
 *
 * @version
 */
public abstract class BaseAuditLog
{
	// ID
	private long id;

	// 日志级别
	private String logLevel;

	// 操作来源
	private String source;

	// 操作结果
	private int result;

	// 详细信息
	private String detail;

	// 附加信息
	private String addInfo;

	// 创建时间
	private Date creationTime;

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * 获取logLevel
	 * 
	 * @return
	 */
	public String getLogLevel()
	{
		return logLevel;
	}

	/**
	 * 设置logLevel
	 * 
	 * @param logLevel
	 */
	public void setLogLevel(String logLevel)
	{
		this.logLevel = logLevel;
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
	 * 获取result
	 * 
	 * @return
	 */
	public int getResult()
	{
		return result;
	}

	/**
	 * 设置result
	 * 
	 * @param result
	 */
	public void setResult(int result)
	{
		this.result = result;
	}

	/**
	 * 获取detail
	 * 
	 * @return
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * 设置detail
	 * 
	 * @param detail
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * 获取addInfo
	 * 
	 * @return
	 */
	public String getAddInfo()
	{
		return addInfo;
	}

	/**
	 * 设置addInfo
	 * 
	 * @param addInfo
	 */
	public void setAddInfo(String addInfo)
	{
		this.addInfo = addInfo;
	}

	/**
	 * 获取creationTime
	 * 
	 * @return
	 */
	public Date getCreationTime()
	{
		return creationTime;
	}

	/**
	 * 设置creationTime
	 * 
	 * @param creationTime
	 */
	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}
}
